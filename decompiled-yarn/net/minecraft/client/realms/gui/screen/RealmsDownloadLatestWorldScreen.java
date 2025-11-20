package net.minecraft.client.realms.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.FileDownload;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.SizeUnit;
import net.minecraft.client.realms.dto.WorldDownload;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsDownloadLatestWorldScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ReentrantLock downloadLock = new ReentrantLock();
   private final Screen parent;
   private final WorldDownload worldDownload;
   private final Text downloadTitle;
   private final RateLimiter narrationRateLimiter;
   private ButtonWidget field_22694;
   private final String worldName;
   private final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus;
   private volatile Text field_20494;
   private volatile Text status = new TranslatableText("mco.download.preparing");
   private volatile String progress;
   private volatile boolean cancelled;
   private volatile boolean showDots = true;
   private volatile boolean finished;
   private volatile boolean extracting;
   private Long previousWrittenBytes;
   private Long previousTimeSnapshot;
   private long bytesPersSecond;
   private int animTick;
   private static final String[] DOTS = new String[]{"", ".", ". .", ". . ."};
   private int dotIndex;
   private boolean checked;
   private final BooleanConsumer field_22693;

   public RealmsDownloadLatestWorldScreen(Screen parent, WorldDownload worldDownload, String worldName, BooleanConsumer _snowman) {
      this.field_22693 = _snowman;
      this.parent = parent;
      this.worldName = worldName;
      this.worldDownload = worldDownload;
      this.downloadStatus = new RealmsDownloadLatestWorldScreen.DownloadStatus();
      this.downloadTitle = new TranslatableText("mco.download.title");
      this.narrationRateLimiter = RateLimiter.create(0.1F);
   }

   @Override
   public void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.field_22694 = this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 42, 200, 20, ScreenTexts.CANCEL, _snowman -> {
         this.cancelled = true;
         this.backButtonClicked();
      }));
      this.checkDownloadSize();
   }

   private void checkDownloadSize() {
      if (!this.finished) {
         if (!this.checked && this.getContentLength(this.worldDownload.downloadLink) >= 5368709120L) {
            Text _snowman = new TranslatableText("mco.download.confirmation.line1", SizeUnit.getUserFriendlyString(5368709120L));
            Text _snowmanx = new TranslatableText("mco.download.confirmation.line2");
            this.client.openScreen(new RealmsLongConfirmationScreen(_snowmanxx -> {
               this.checked = true;
               this.client.openScreen(this);
               this.downloadSave();
            }, RealmsLongConfirmationScreen.Type.Warning, _snowman, _snowmanx, false));
         } else {
            this.downloadSave();
         }
      }
   }

   private long getContentLength(String downloadLink) {
      FileDownload _snowman = new FileDownload();
      return _snowman.contentLength(downloadLink);
   }

   @Override
   public void tick() {
      super.tick();
      this.animTick++;
      if (this.status != null && this.narrationRateLimiter.tryAcquire(1)) {
         List<Text> _snowman = Lists.newArrayList();
         _snowman.add(this.downloadTitle);
         _snowman.add(this.status);
         if (this.progress != null) {
            _snowman.add(new LiteralText(this.progress + "%"));
            _snowman.add(new LiteralText(SizeUnit.getUserFriendlyString(this.bytesPersSecond) + "/s"));
         }

         if (this.field_20494 != null) {
            _snowman.add(this.field_20494);
         }

         String _snowmanx = _snowman.stream().map(Text::getString).collect(Collectors.joining("\n"));
         Realms.narrateNow(_snowmanx);
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.cancelled = true;
         this.backButtonClicked();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private void backButtonClicked() {
      if (this.finished && this.field_22693 != null && this.field_20494 == null) {
         this.field_22693.accept(true);
      }

      this.client.openScreen(this.parent);
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      drawCenteredText(matrices, this.textRenderer, this.downloadTitle, this.width / 2, 20, 16777215);
      drawCenteredText(matrices, this.textRenderer, this.status, this.width / 2, 50, 16777215);
      if (this.showDots) {
         this.drawDots(matrices);
      }

      if (this.downloadStatus.bytesWritten != 0L && !this.cancelled) {
         this.drawProgressBar(matrices);
         this.drawDownloadSpeed(matrices);
      }

      if (this.field_20494 != null) {
         drawCenteredText(matrices, this.textRenderer, this.field_20494, this.width / 2, 110, 16711680);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   private void drawDots(MatrixStack _snowman) {
      int _snowmanx = this.textRenderer.getWidth(this.status);
      if (this.animTick % 10 == 0) {
         this.dotIndex++;
      }

      this.textRenderer.draw(_snowman, DOTS[this.dotIndex % DOTS.length], (float)(this.width / 2 + _snowmanx / 2 + 5), 50.0F, 16777215);
   }

   private void drawProgressBar(MatrixStack _snowman) {
      double _snowmanx = Math.min((double)this.downloadStatus.bytesWritten / (double)this.downloadStatus.totalBytes, 1.0);
      this.progress = String.format(Locale.ROOT, "%.1f", _snowmanx * 100.0);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableTexture();
      Tessellator _snowmanxx = Tessellator.getInstance();
      BufferBuilder _snowmanxxx = _snowmanxx.getBuffer();
      _snowmanxxx.begin(7, VertexFormats.POSITION_COLOR);
      double _snowmanxxxx = (double)(this.width / 2 - 100);
      double _snowmanxxxxx = 0.5;
      _snowmanxxx.vertex(_snowmanxxxx - 0.5, 95.5, 0.0).color(217, 210, 210, 255).next();
      _snowmanxxx.vertex(_snowmanxxxx + 200.0 * _snowmanx + 0.5, 95.5, 0.0).color(217, 210, 210, 255).next();
      _snowmanxxx.vertex(_snowmanxxxx + 200.0 * _snowmanx + 0.5, 79.5, 0.0).color(217, 210, 210, 255).next();
      _snowmanxxx.vertex(_snowmanxxxx - 0.5, 79.5, 0.0).color(217, 210, 210, 255).next();
      _snowmanxxx.vertex(_snowmanxxxx, 95.0, 0.0).color(128, 128, 128, 255).next();
      _snowmanxxx.vertex(_snowmanxxxx + 200.0 * _snowmanx, 95.0, 0.0).color(128, 128, 128, 255).next();
      _snowmanxxx.vertex(_snowmanxxxx + 200.0 * _snowmanx, 80.0, 0.0).color(128, 128, 128, 255).next();
      _snowmanxxx.vertex(_snowmanxxxx, 80.0, 0.0).color(128, 128, 128, 255).next();
      _snowmanxx.draw();
      RenderSystem.enableTexture();
      drawCenteredString(_snowman, this.textRenderer, this.progress + " %", this.width / 2, 84, 16777215);
   }

   private void drawDownloadSpeed(MatrixStack _snowman) {
      if (this.animTick % 20 == 0) {
         if (this.previousWrittenBytes != null) {
            long _snowmanx = Util.getMeasuringTimeMs() - this.previousTimeSnapshot;
            if (_snowmanx == 0L) {
               _snowmanx = 1L;
            }

            this.bytesPersSecond = 1000L * (this.downloadStatus.bytesWritten - this.previousWrittenBytes) / _snowmanx;
            this.drawDownloadSpeed0(_snowman, this.bytesPersSecond);
         }

         this.previousWrittenBytes = this.downloadStatus.bytesWritten;
         this.previousTimeSnapshot = Util.getMeasuringTimeMs();
      } else {
         this.drawDownloadSpeed0(_snowman, this.bytesPersSecond);
      }
   }

   private void drawDownloadSpeed0(MatrixStack _snowman, long _snowman) {
      if (_snowman > 0L) {
         int _snowmanxx = this.textRenderer.getWidth(this.progress);
         String _snowmanxxx = "(" + SizeUnit.getUserFriendlyString(_snowman) + "/s)";
         this.textRenderer.draw(_snowman, _snowmanxxx, (float)(this.width / 2 + _snowmanxx / 2 + 15), 84.0F, 16777215);
      }
   }

   private void downloadSave() {
      new Thread(() -> {
         try {
            try {
               if (!downloadLock.tryLock(1L, TimeUnit.SECONDS)) {
                  this.status = new TranslatableText("mco.download.failed");
                  return;
               }

               if (this.cancelled) {
                  this.downloadCancelled();
                  return;
               }

               this.status = new TranslatableText("mco.download.downloading", this.worldName);
               FileDownload _snowman = new FileDownload();
               _snowman.contentLength(this.worldDownload.downloadLink);
               _snowman.downloadWorld(this.worldDownload, this.worldName, this.downloadStatus, this.client.getLevelStorage());

               while (!_snowman.isFinished()) {
                  if (_snowman.isError()) {
                     _snowman.cancel();
                     this.field_20494 = new TranslatableText("mco.download.failed");
                     this.field_22694.setMessage(ScreenTexts.DONE);
                     return;
                  }

                  if (_snowman.isExtracting()) {
                     if (!this.extracting) {
                        this.status = new TranslatableText("mco.download.extracting");
                     }

                     this.extracting = true;
                  }

                  if (this.cancelled) {
                     _snowman.cancel();
                     this.downloadCancelled();
                     return;
                  }

                  try {
                     Thread.sleep(500L);
                  } catch (InterruptedException var8) {
                     LOGGER.error("Failed to check Realms backup download status");
                  }
               }

               this.finished = true;
               this.status = new TranslatableText("mco.download.done");
               this.field_22694.setMessage(ScreenTexts.DONE);
               return;
            } catch (InterruptedException var9) {
               LOGGER.error("Could not acquire upload lock");
            } catch (Exception var10) {
               this.field_20494 = new TranslatableText("mco.download.failed");
               var10.printStackTrace();
            }
         } finally {
            if (!downloadLock.isHeldByCurrentThread()) {
               return;
            } else {
               downloadLock.unlock();
               this.showDots = false;
               this.finished = true;
            }
         }
      }).start();
   }

   private void downloadCancelled() {
      this.status = new TranslatableText("mco.download.cancelled");
   }

   public class DownloadStatus {
      public volatile long bytesWritten;
      public volatile long totalBytes;

      public DownloadStatus() {
      }
   }
}
