package net.minecraft.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenshotUtils {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

   public static void saveScreenshot(File gameDirectory, int framebufferWidth, int framebufferHeight, Framebuffer framebuffer, Consumer<Text> messageReceiver) {
      saveScreenshot(gameDirectory, null, framebufferWidth, framebufferHeight, framebuffer, messageReceiver);
   }

   public static void saveScreenshot(
      File gameDirectory, @Nullable String fileName, int framebufferWidth, int framebufferHeight, Framebuffer framebuffer, Consumer<Text> messageReceiver
   ) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> saveScreenshotInner(gameDirectory, fileName, framebufferWidth, framebufferHeight, framebuffer, messageReceiver));
      } else {
         saveScreenshotInner(gameDirectory, fileName, framebufferWidth, framebufferHeight, framebuffer, messageReceiver);
      }
   }

   private static void saveScreenshotInner(
      File gameDirectory, @Nullable String fileName, int framebufferWidth, int framebufferHeight, Framebuffer framebuffer, Consumer<Text> messageReceiver
   ) {
      NativeImage _snowman = takeScreenshot(framebufferWidth, framebufferHeight, framebuffer);
      File _snowmanx = new File(gameDirectory, "screenshots");
      _snowmanx.mkdir();
      File _snowmanxx;
      if (fileName == null) {
         _snowmanxx = getScreenshotFilename(_snowmanx);
      } else {
         _snowmanxx = new File(_snowmanx, fileName);
      }

      Util.getIoWorkerExecutor()
         .execute(
            () -> {
               try {
                  _snowman.writeFile(_snowman);
                  Text _snowmanxxx = new LiteralText(_snowman.getName())
                     .formatted(Formatting.UNDERLINE)
                     .styled(_snowmanxxxx -> _snowmanxxxx.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, _snowman.getAbsolutePath())));
                  messageReceiver.accept(new TranslatableText("screenshot.success", _snowmanxxx));
               } catch (Exception var7x) {
                  LOGGER.warn("Couldn't save screenshot", var7x);
                  messageReceiver.accept(new TranslatableText("screenshot.failure", var7x.getMessage()));
               } finally {
                  _snowman.close();
               }
            }
         );
   }

   public static NativeImage takeScreenshot(int width, int height, Framebuffer framebuffer) {
      width = framebuffer.textureWidth;
      height = framebuffer.textureHeight;
      NativeImage _snowman = new NativeImage(width, height, false);
      RenderSystem.bindTexture(framebuffer.getColorAttachment());
      _snowman.loadFromTextureImage(0, true);
      _snowman.mirrorVertically();
      return _snowman;
   }

   private static File getScreenshotFilename(File directory) {
      String _snowman = DATE_FORMAT.format(new Date());
      int _snowmanx = 1;

      while (true) {
         File _snowmanxx = new File(directory, _snowman + (_snowmanx == 1 ? "" : "_" + _snowmanx) + ".png");
         if (!_snowmanxx.exists()) {
            return _snowmanxx;
         }

         _snowmanx++;
      }
   }
}
