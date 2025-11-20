package net.minecraft.client.realms.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.FileUpload;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.SizeUnit;
import net.minecraft.client.realms.UploadStatus;
import net.minecraft.client.realms.dto.UploadInfo;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.exception.RetryCallException;
import net.minecraft.client.realms.util.UploadTokenCache;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class RealmsUploadScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ReentrantLock UPLOAD_LOCK = new ReentrantLock();
   private static final String[] DOTS = new String[]{"", ".", ". .", ". . ."};
   private static final Text field_26526 = new TranslatableText("mco.upload.verifying");
   private final RealmsResetWorldScreen parent;
   private final LevelSummary selectedLevel;
   private final long worldId;
   private final int slotId;
   private final UploadStatus uploadStatus;
   private final RateLimiter narrationRateLimiter;
   private volatile Text[] field_20503;
   private volatile Text status = new TranslatableText("mco.upload.preparing");
   private volatile String progress;
   private volatile boolean cancelled;
   private volatile boolean uploadFinished;
   private volatile boolean showDots = true;
   private volatile boolean uploadStarted;
   private ButtonWidget backButton;
   private ButtonWidget cancelButton;
   private int animTick;
   private Long previousWrittenBytes;
   private Long previousTimeSnapshot;
   private long bytesPersSecond;
   private final Runnable field_22728;

   public RealmsUploadScreen(long worldId, int slotId, RealmsResetWorldScreen parent, LevelSummary arg2, Runnable runnable) {
      this.worldId = worldId;
      this.slotId = slotId;
      this.parent = parent;
      this.selectedLevel = arg2;
      this.uploadStatus = new UploadStatus();
      this.narrationRateLimiter = RateLimiter.create(0.1F);
      this.field_22728 = runnable;
   }

   @Override
   public void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.backButton = this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 42, 200, 20, ScreenTexts.BACK, arg -> this.onBack()));
      this.backButton.visible = false;
      this.cancelButton = this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 42, 200, 20, ScreenTexts.CANCEL, arg -> this.onCancel()));
      if (!this.uploadStarted) {
         if (this.parent.slot == -1) {
            this.upload();
         } else {
            this.parent.switchSlot(() -> {
               if (!this.uploadStarted) {
                  this.uploadStarted = true;
                  this.client.openScreen(this);
                  this.upload();
               }
            });
         }
      }
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
   }

   private void onBack() {
      this.field_22728.run();
   }

   private void onCancel() {
      this.cancelled = true;
      this.client.openScreen(this.parent);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         if (this.showDots) {
            this.onCancel();
         } else {
            this.onBack();
         }

         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      if (!this.uploadFinished && this.uploadStatus.bytesWritten != 0L && this.uploadStatus.bytesWritten == this.uploadStatus.totalBytes) {
         this.status = field_26526;
         this.cancelButton.active = false;
      }

      drawCenteredText(matrices, this.textRenderer, this.status, this.width / 2, 50, 16777215);
      if (this.showDots) {
         this.drawDots(matrices);
      }

      if (this.uploadStatus.bytesWritten != 0L && !this.cancelled) {
         this.drawProgressBar(matrices);
         this.drawUploadSpeed(matrices);
      }

      if (this.field_20503 != null) {
         for (int k = 0; k < this.field_20503.length; k++) {
            drawCenteredText(matrices, this.textRenderer, this.field_20503[k], this.width / 2, 110 + 12 * k, 16711680);
         }
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   private void drawDots(MatrixStack arg) {
      int i = this.textRenderer.getWidth(this.status);
      this.textRenderer.draw(arg, DOTS[this.animTick / 10 % DOTS.length], (float)(this.width / 2 + i / 2 + 5), 50.0F, 16777215);
   }

   private void drawProgressBar(MatrixStack arg) {
      double d = Math.min((double)this.uploadStatus.bytesWritten / (double)this.uploadStatus.totalBytes, 1.0);
      this.progress = String.format(Locale.ROOT, "%.1f", d * 100.0);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableTexture();
      double e = (double)(this.width / 2 - 100);
      double f = 0.5;
      Tessellator lv = Tessellator.getInstance();
      BufferBuilder lv2 = lv.getBuffer();
      lv2.begin(7, VertexFormats.POSITION_COLOR);
      lv2.vertex(e - 0.5, 95.5, 0.0).color(217, 210, 210, 255).next();
      lv2.vertex(e + 200.0 * d + 0.5, 95.5, 0.0).color(217, 210, 210, 255).next();
      lv2.vertex(e + 200.0 * d + 0.5, 79.5, 0.0).color(217, 210, 210, 255).next();
      lv2.vertex(e - 0.5, 79.5, 0.0).color(217, 210, 210, 255).next();
      lv2.vertex(e, 95.0, 0.0).color(128, 128, 128, 255).next();
      lv2.vertex(e + 200.0 * d, 95.0, 0.0).color(128, 128, 128, 255).next();
      lv2.vertex(e + 200.0 * d, 80.0, 0.0).color(128, 128, 128, 255).next();
      lv2.vertex(e, 80.0, 0.0).color(128, 128, 128, 255).next();
      lv.draw();
      RenderSystem.enableTexture();
      drawCenteredString(arg, this.textRenderer, this.progress + " %", this.width / 2, 84, 16777215);
   }

   private void drawUploadSpeed(MatrixStack arg) {
      if (this.animTick % 20 == 0) {
         if (this.previousWrittenBytes != null) {
            long l = Util.getMeasuringTimeMs() - this.previousTimeSnapshot;
            if (l == 0L) {
               l = 1L;
            }

            this.bytesPersSecond = 1000L * (this.uploadStatus.bytesWritten - this.previousWrittenBytes) / l;
            this.drawUploadSpeed0(arg, this.bytesPersSecond);
         }

         this.previousWrittenBytes = this.uploadStatus.bytesWritten;
         this.previousTimeSnapshot = Util.getMeasuringTimeMs();
      } else {
         this.drawUploadSpeed0(arg, this.bytesPersSecond);
      }
   }

   private void drawUploadSpeed0(MatrixStack arg, long l) {
      if (l > 0L) {
         int i = this.textRenderer.getWidth(this.progress);
         String string = "(" + SizeUnit.getUserFriendlyString(l) + "/s)";
         this.textRenderer.draw(arg, string, (float)(this.width / 2 + i / 2 + 15), 84.0F, 16777215);
      }
   }

   @Override
   public void tick() {
      super.tick();
      this.animTick++;
      if (this.status != null && this.narrationRateLimiter.tryAcquire(1)) {
         List<String> list = Lists.newArrayList();
         list.add(this.status.getString());
         if (this.progress != null) {
            list.add(this.progress + "%");
         }

         if (this.field_20503 != null) {
            Stream.of(this.field_20503).map(Text::getString).forEach(list::add);
         }

         Realms.narrateNow(String.join(System.lineSeparator(), list));
      }
   }

   private void upload() {
      this.uploadStarted = true;
      new Thread(
            () -> {
               File file = null;
               RealmsClient lv = RealmsClient.createRealmsClient();
               long l = this.worldId;

               try {
                  if (!UPLOAD_LOCK.tryLock(1L, TimeUnit.SECONDS)) {
                     this.status = new TranslatableText("mco.upload.close.failure");
                  } else {
                     UploadInfo lv2 = null;

                     for (int i = 0; i < 20; i++) {
                        try {
                           if (this.cancelled) {
                              this.uploadCancelled();
                              return;
                           }

                           lv2 = lv.upload(l, UploadTokenCache.get(l));
                           if (lv2 != null) {
                              break;
                           }
                        } catch (RetryCallException var20) {
                           Thread.sleep((long)(var20.delaySeconds * 1000));
                        }
                     }

                     if (lv2 == null) {
                        this.status = new TranslatableText("mco.upload.close.failure");
                     } else {
                        UploadTokenCache.put(l, lv2.getToken());
                        if (!lv2.isWorldClosed()) {
                           this.status = new TranslatableText("mco.upload.close.failure");
                        } else if (this.cancelled) {
                           this.uploadCancelled();
                        } else {
                           File file2 = new File(this.client.runDirectory.getAbsolutePath(), "saves");
                           file = this.tarGzipArchive(new File(file2, this.selectedLevel.getName()));
                           if (this.cancelled) {
                              this.uploadCancelled();
                           } else if (this.verify(file)) {
                              this.status = new TranslatableText("mco.upload.uploading", this.selectedLevel.getDisplayName());
                              FileUpload lv7 = new FileUpload(
                                 file, this.worldId, this.slotId, lv2, this.client.getSession(), SharedConstants.getGameVersion().getName(), this.uploadStatus
                              );
                              lv7.upload(arg -> {
                                 if (arg.statusCode >= 200 && arg.statusCode < 300) {
                                    this.uploadFinished = true;
                                    this.status = new TranslatableText("mco.upload.done");
                                    this.backButton.setMessage(ScreenTexts.DONE);
                                    UploadTokenCache.invalidate(l);
                                 } else if (arg.statusCode == 400 && arg.errorMessage != null) {
                                    this.method_27460(new TranslatableText("mco.upload.failed", arg.errorMessage));
                                 } else {
                                    this.method_27460(new TranslatableText("mco.upload.failed", arg.statusCode));
                                 }
                              });

                              while (!lv7.isFinished()) {
                                 if (this.cancelled) {
                                    lv7.cancel();
                                    this.uploadCancelled();
                                    return;
                                 }

                                 try {
                                    Thread.sleep(500L);
                                 } catch (InterruptedException var19) {
                                    LOGGER.error("Failed to check Realms file upload status");
                                 }
                              }
                           } else {
                              long m = file.length();
                              SizeUnit lv4 = SizeUnit.getLargestUnit(m);
                              SizeUnit lv5 = SizeUnit.getLargestUnit(5368709120L);
                              if (SizeUnit.humanReadableSize(m, lv4).equals(SizeUnit.humanReadableSize(5368709120L, lv5)) && lv4 != SizeUnit.B) {
                                 SizeUnit lv6 = SizeUnit.values()[lv4.ordinal() - 1];
                                 this.method_27460(
                                    new TranslatableText("mco.upload.size.failure.line1", this.selectedLevel.getDisplayName()),
                                    new TranslatableText(
                                       "mco.upload.size.failure.line2", SizeUnit.humanReadableSize(m, lv6), SizeUnit.humanReadableSize(5368709120L, lv6)
                                    )
                                 );
                              } else {
                                 this.method_27460(
                                    new TranslatableText("mco.upload.size.failure.line1", this.selectedLevel.getDisplayName()),
                                    new TranslatableText(
                                       "mco.upload.size.failure.line2", SizeUnit.humanReadableSize(m, lv4), SizeUnit.humanReadableSize(5368709120L, lv5)
                                    )
                                 );
                              }
                           }
                        }
                     }
                  }
               } catch (IOException var21) {
                  this.method_27460(new TranslatableText("mco.upload.failed", var21.getMessage()));
               } catch (RealmsServiceException var22) {
                  this.method_27460(new TranslatableText("mco.upload.failed", var22.toString()));
               } catch (InterruptedException var23) {
                  LOGGER.error("Could not acquire upload lock");
               } finally {
                  this.uploadFinished = true;
                  if (UPLOAD_LOCK.isHeldByCurrentThread()) {
                     UPLOAD_LOCK.unlock();
                     this.showDots = false;
                     this.backButton.visible = true;
                     this.cancelButton.visible = false;
                     if (file != null) {
                        LOGGER.debug("Deleting file " + file.getAbsolutePath());
                        file.delete();
                     }
                  } else {
                     return;
                  }
               }
            }
         )
         .start();
   }

   private void method_27460(Text... args) {
      this.field_20503 = args;
   }

   private void uploadCancelled() {
      this.status = new TranslatableText("mco.upload.cancelled");
      LOGGER.debug("Upload was cancelled");
   }

   private boolean verify(File archive) {
      return archive.length() < 5368709120L;
   }

   private File tarGzipArchive(File pathToDirectoryFile) throws IOException {
      TarArchiveOutputStream tarArchiveOutputStream = null;

      File var4;
      try {
         File file2 = File.createTempFile("realms-upload-file", ".tar.gz");
         tarArchiveOutputStream = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream(file2)));
         tarArchiveOutputStream.setLongFileMode(3);
         this.addFileToTarGz(tarArchiveOutputStream, pathToDirectoryFile.getAbsolutePath(), "world", true);
         tarArchiveOutputStream.finish();
         var4 = file2;
      } finally {
         if (tarArchiveOutputStream != null) {
            tarArchiveOutputStream.close();
         }
      }

      return var4;
   }

   private void addFileToTarGz(TarArchiveOutputStream tOut, String path, String base, boolean root) throws IOException {
      if (!this.cancelled) {
         File file = new File(path);
         String string3 = root ? base : base + file.getName();
         TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(file, string3);
         tOut.putArchiveEntry(tarArchiveEntry);
         if (file.isFile()) {
            IOUtils.copy(new FileInputStream(file), tOut);
            tOut.closeArchiveEntry();
         } else {
            tOut.closeArchiveEntry();
            File[] files = file.listFiles();
            if (files != null) {
               for (File file2 : files) {
                  this.addFileToTarGz(tOut, file2.getAbsolutePath(), string3 + "/", false);
               }
            }
         }
      }
   }
}
