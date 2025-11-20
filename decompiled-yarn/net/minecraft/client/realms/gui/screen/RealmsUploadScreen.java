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

   public RealmsUploadScreen(long worldId, int slotId, RealmsResetWorldScreen parent, LevelSummary _snowman, Runnable _snowman) {
      this.worldId = worldId;
      this.slotId = slotId;
      this.parent = parent;
      this.selectedLevel = _snowman;
      this.uploadStatus = new UploadStatus();
      this.narrationRateLimiter = RateLimiter.create(0.1F);
      this.field_22728 = _snowman;
   }

   @Override
   public void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.backButton = this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 42, 200, 20, ScreenTexts.BACK, _snowman -> this.onBack()));
      this.backButton.visible = false;
      this.cancelButton = this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 42, 200, 20, ScreenTexts.CANCEL, _snowman -> this.onCancel()));
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
         for (int _snowman = 0; _snowman < this.field_20503.length; _snowman++) {
            drawCenteredText(matrices, this.textRenderer, this.field_20503[_snowman], this.width / 2, 110 + 12 * _snowman, 16711680);
         }
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   private void drawDots(MatrixStack _snowman) {
      int _snowmanx = this.textRenderer.getWidth(this.status);
      this.textRenderer.draw(_snowman, DOTS[this.animTick / 10 % DOTS.length], (float)(this.width / 2 + _snowmanx / 2 + 5), 50.0F, 16777215);
   }

   private void drawProgressBar(MatrixStack _snowman) {
      double _snowmanx = Math.min((double)this.uploadStatus.bytesWritten / (double)this.uploadStatus.totalBytes, 1.0);
      this.progress = String.format(Locale.ROOT, "%.1f", _snowmanx * 100.0);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableTexture();
      double _snowmanxx = (double)(this.width / 2 - 100);
      double _snowmanxxx = 0.5;
      Tessellator _snowmanxxxx = Tessellator.getInstance();
      BufferBuilder _snowmanxxxxx = _snowmanxxxx.getBuffer();
      _snowmanxxxxx.begin(7, VertexFormats.POSITION_COLOR);
      _snowmanxxxxx.vertex(_snowmanxx - 0.5, 95.5, 0.0).color(217, 210, 210, 255).next();
      _snowmanxxxxx.vertex(_snowmanxx + 200.0 * _snowmanx + 0.5, 95.5, 0.0).color(217, 210, 210, 255).next();
      _snowmanxxxxx.vertex(_snowmanxx + 200.0 * _snowmanx + 0.5, 79.5, 0.0).color(217, 210, 210, 255).next();
      _snowmanxxxxx.vertex(_snowmanxx - 0.5, 79.5, 0.0).color(217, 210, 210, 255).next();
      _snowmanxxxxx.vertex(_snowmanxx, 95.0, 0.0).color(128, 128, 128, 255).next();
      _snowmanxxxxx.vertex(_snowmanxx + 200.0 * _snowmanx, 95.0, 0.0).color(128, 128, 128, 255).next();
      _snowmanxxxxx.vertex(_snowmanxx + 200.0 * _snowmanx, 80.0, 0.0).color(128, 128, 128, 255).next();
      _snowmanxxxxx.vertex(_snowmanxx, 80.0, 0.0).color(128, 128, 128, 255).next();
      _snowmanxxxx.draw();
      RenderSystem.enableTexture();
      drawCenteredString(_snowman, this.textRenderer, this.progress + " %", this.width / 2, 84, 16777215);
   }

   private void drawUploadSpeed(MatrixStack _snowman) {
      if (this.animTick % 20 == 0) {
         if (this.previousWrittenBytes != null) {
            long _snowmanx = Util.getMeasuringTimeMs() - this.previousTimeSnapshot;
            if (_snowmanx == 0L) {
               _snowmanx = 1L;
            }

            this.bytesPersSecond = 1000L * (this.uploadStatus.bytesWritten - this.previousWrittenBytes) / _snowmanx;
            this.drawUploadSpeed0(_snowman, this.bytesPersSecond);
         }

         this.previousWrittenBytes = this.uploadStatus.bytesWritten;
         this.previousTimeSnapshot = Util.getMeasuringTimeMs();
      } else {
         this.drawUploadSpeed0(_snowman, this.bytesPersSecond);
      }
   }

   private void drawUploadSpeed0(MatrixStack _snowman, long _snowman) {
      if (_snowman > 0L) {
         int _snowmanxx = this.textRenderer.getWidth(this.progress);
         String _snowmanxxx = "(" + SizeUnit.getUserFriendlyString(_snowman) + "/s)";
         this.textRenderer.draw(_snowman, _snowmanxxx, (float)(this.width / 2 + _snowmanxx / 2 + 15), 84.0F, 16777215);
      }
   }

   @Override
   public void tick() {
      super.tick();
      this.animTick++;
      if (this.status != null && this.narrationRateLimiter.tryAcquire(1)) {
         List<String> _snowman = Lists.newArrayList();
         _snowman.add(this.status.getString());
         if (this.progress != null) {
            _snowman.add(this.progress + "%");
         }

         if (this.field_20503 != null) {
            Stream.of(this.field_20503).map(Text::getString).forEach(_snowman::add);
         }

         Realms.narrateNow(String.join(System.lineSeparator(), _snowman));
      }
   }

   private void upload() {
      this.uploadStarted = true;
      new Thread(
            () -> {
               File _snowman = null;
               RealmsClient _snowmanx = RealmsClient.createRealmsClient();
               long _snowmanxx = this.worldId;

               try {
                  if (!UPLOAD_LOCK.tryLock(1L, TimeUnit.SECONDS)) {
                     this.status = new TranslatableText("mco.upload.close.failure");
                  } else {
                     UploadInfo _snowmanxxx = null;

                     for (int _snowmanxxxx = 0; _snowmanxxxx < 20; _snowmanxxxx++) {
                        try {
                           if (this.cancelled) {
                              this.uploadCancelled();
                              return;
                           }

                           _snowmanxxx = _snowmanx.upload(_snowmanxx, UploadTokenCache.get(_snowmanxx));
                           if (_snowmanxxx != null) {
                              break;
                           }
                        } catch (RetryCallException var20) {
                           Thread.sleep((long)(var20.delaySeconds * 1000));
                        }
                     }

                     if (_snowmanxxx == null) {
                        this.status = new TranslatableText("mco.upload.close.failure");
                     } else {
                        UploadTokenCache.put(_snowmanxx, _snowmanxxx.getToken());
                        if (!_snowmanxxx.isWorldClosed()) {
                           this.status = new TranslatableText("mco.upload.close.failure");
                        } else if (this.cancelled) {
                           this.uploadCancelled();
                        } else {
                           File _snowmanxxxx = new File(this.client.runDirectory.getAbsolutePath(), "saves");
                           _snowman = this.tarGzipArchive(new File(_snowmanxxxx, this.selectedLevel.getName()));
                           if (this.cancelled) {
                              this.uploadCancelled();
                           } else if (this.verify(_snowman)) {
                              this.status = new TranslatableText("mco.upload.uploading", this.selectedLevel.getDisplayName());
                              FileUpload _snowmanxxxxx = new FileUpload(
                                 _snowman, this.worldId, this.slotId, _snowmanxxx, this.client.getSession(), SharedConstants.getGameVersion().getName(), this.uploadStatus
                              );
                              _snowmanxxxxx.upload(_snowmanxxxxxx -> {
                                 if (_snowmanxxxxxx.statusCode >= 200 && _snowmanxxxxxx.statusCode < 300) {
                                    this.uploadFinished = true;
                                    this.status = new TranslatableText("mco.upload.done");
                                    this.backButton.setMessage(ScreenTexts.DONE);
                                    UploadTokenCache.invalidate(_snowman);
                                 } else if (_snowmanxxxxxx.statusCode == 400 && _snowmanxxxxxx.errorMessage != null) {
                                    this.method_27460(new TranslatableText("mco.upload.failed", _snowmanxxxxxx.errorMessage));
                                 } else {
                                    this.method_27460(new TranslatableText("mco.upload.failed", _snowmanxxxxxx.statusCode));
                                 }
                              });

                              while (!_snowmanxxxxx.isFinished()) {
                                 if (this.cancelled) {
                                    _snowmanxxxxx.cancel();
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
                              long _snowmanxxxxx = _snowman.length();
                              SizeUnit _snowmanxxxxxx = SizeUnit.getLargestUnit(_snowmanxxxxx);
                              SizeUnit _snowmanxxxxxxx = SizeUnit.getLargestUnit(5368709120L);
                              if (SizeUnit.humanReadableSize(_snowmanxxxxx, _snowmanxxxxxx).equals(SizeUnit.humanReadableSize(5368709120L, _snowmanxxxxxxx))
                                 && _snowmanxxxxxx != SizeUnit.B) {
                                 SizeUnit _snowmanxxxxxxxx = SizeUnit.values()[_snowmanxxxxxx.ordinal() - 1];
                                 this.method_27460(
                                    new TranslatableText("mco.upload.size.failure.line1", this.selectedLevel.getDisplayName()),
                                    new TranslatableText(
                                       "mco.upload.size.failure.line2",
                                       SizeUnit.humanReadableSize(_snowmanxxxxx, _snowmanxxxxxxxx),
                                       SizeUnit.humanReadableSize(5368709120L, _snowmanxxxxxxxx)
                                    )
                                 );
                              } else {
                                 this.method_27460(
                                    new TranslatableText("mco.upload.size.failure.line1", this.selectedLevel.getDisplayName()),
                                    new TranslatableText(
                                       "mco.upload.size.failure.line2",
                                       SizeUnit.humanReadableSize(_snowmanxxxxx, _snowmanxxxxxx),
                                       SizeUnit.humanReadableSize(5368709120L, _snowmanxxxxxxx)
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
                     if (_snowman != null) {
                        LOGGER.debug("Deleting file " + _snowman.getAbsolutePath());
                        _snowman.delete();
                     }
                  } else {
                     return;
                  }
               }
            }
         )
         .start();
   }

   private void method_27460(Text... _snowman) {
      this.field_20503 = _snowman;
   }

   private void uploadCancelled() {
      this.status = new TranslatableText("mco.upload.cancelled");
      LOGGER.debug("Upload was cancelled");
   }

   private boolean verify(File archive) {
      return archive.length() < 5368709120L;
   }

   private File tarGzipArchive(File pathToDirectoryFile) throws IOException {
      TarArchiveOutputStream _snowman = null;

      File var4;
      try {
         File _snowmanx = File.createTempFile("realms-upload-file", ".tar.gz");
         _snowman = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream(_snowmanx)));
         _snowman.setLongFileMode(3);
         this.addFileToTarGz(_snowman, pathToDirectoryFile.getAbsolutePath(), "world", true);
         _snowman.finish();
         var4 = _snowmanx;
      } finally {
         if (_snowman != null) {
            _snowman.close();
         }
      }

      return var4;
   }

   private void addFileToTarGz(TarArchiveOutputStream tOut, String path, String base, boolean root) throws IOException {
      if (!this.cancelled) {
         File _snowman = new File(path);
         String _snowmanx = root ? base : base + _snowman.getName();
         TarArchiveEntry _snowmanxx = new TarArchiveEntry(_snowman, _snowmanx);
         tOut.putArchiveEntry(_snowmanxx);
         if (_snowman.isFile()) {
            IOUtils.copy(new FileInputStream(_snowman), tOut);
            tOut.closeArchiveEntry();
         } else {
            tOut.closeArchiveEntry();
            File[] _snowmanxxx = _snowman.listFiles();
            if (_snowmanxxx != null) {
               for (File _snowmanxxxx : _snowmanxxx) {
                  this.addFileToTarGz(tOut, _snowmanxxxx.getAbsolutePath(), _snowmanx + "/", false);
               }
            }
         }
      }
   }
}
