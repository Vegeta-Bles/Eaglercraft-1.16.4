import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.util.UUIDTypeAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dir {
   private static final Map<String, dir.a> a = Maps.newHashMap();
   private static final Map<String, Boolean> b = Maps.newHashMap();
   private static final Map<String, String> c = Maps.newHashMap();
   private static final Logger d = LogManager.getLogger();
   private static final vk e = new vk("textures/gui/presets/isles.png");

   public static void a(String var0, @Nullable String var1) {
      if (_snowman == null) {
         djz.C().M().a(e);
      } else {
         int _snowman = b(_snowman, _snowman);
         RenderSystem.bindTexture(_snowman);
      }
   }

   public static void a(String var0, Runnable var1) {
      RenderSystem.pushTextureAttributes();

      try {
         a(_snowman);
         _snowman.run();
      } finally {
         RenderSystem.popAttributes();
      }
   }

   private static void a(UUID var0) {
      djz.C().M().a(ekj.a(_snowman));
   }

   private static void a(final String var0) {
      UUID _snowman = UUIDTypeAdapter.fromString(_snowman);
      if (a.containsKey(_snowman)) {
         RenderSystem.bindTexture(a.get(_snowman).b);
      } else if (b.containsKey(_snowman)) {
         if (!b.get(_snowman)) {
            a(_snowman);
         } else if (c.containsKey(_snowman)) {
            int _snowmanx = b(_snowman, c.get(_snowman));
            RenderSystem.bindTexture(_snowmanx);
         } else {
            a(_snowman);
         }
      } else {
         b.put(_snowman, false);
         a(_snowman);
         Thread _snowmanx = new Thread("Realms Texture Downloader") {
            @Override
            public void run() {
               Map<Type, MinecraftProfileTexture> _snowman = dis.b(_snowman);
               if (_snowman.containsKey(Type.SKIN)) {
                  MinecraftProfileTexture _snowmanx = _snowman.get(Type.SKIN);
                  String _snowmanxx = _snowmanx.getUrl();
                  HttpURLConnection _snowmanxxx = null;
                  dir.d.debug("Downloading http texture from {}", _snowmanxx);

                  try {
                     try {
                        _snowmanxxx = (HttpURLConnection)new URL(_snowmanxx).openConnection(djz.C().L());
                        _snowmanxxx.setDoInput(true);
                        _snowmanxxx.setDoOutput(false);
                        _snowmanxxx.connect();
                        if (_snowmanxxx.getResponseCode() / 100 != 2) {
                           dir.b.remove(_snowman);
                           return;
                        }

                        BufferedImage _snowmanxxxx;
                        try {
                           _snowmanxxxx = ImageIO.read(_snowmanxxx.getInputStream());
                        } catch (Exception var17) {
                           dir.b.remove(_snowman);
                           return;
                        } finally {
                           IOUtils.closeQuietly(_snowmanxxx.getInputStream());
                        }

                        _snowmanxxxx = new dit().a(_snowmanxxxx);
                        ByteArrayOutputStream _snowmanxxxxx = new ByteArrayOutputStream();
                        ImageIO.write(_snowmanxxxx, "png", _snowmanxxxxx);
                        dir.c.put(_snowman, new Base64().encodeToString(_snowmanxxxxx.toByteArray()));
                        dir.b.put(_snowman, true);
                     } catch (Exception var19) {
                        dir.d.error("Couldn't download http texture", var19);
                        dir.b.remove(_snowman);
                     }
                  } finally {
                     if (_snowmanxxx != null) {
                        _snowmanxxx.disconnect();
                     }
                  }
               } else {
                  dir.b.put(_snowman, true);
               }
            }
         };
         _snowmanx.setDaemon(true);
         _snowmanx.start();
      }
   }

   private static int b(String var0, String var1) {
      int _snowman;
      if (a.containsKey(_snowman)) {
         dir.a _snowmanx = a.get(_snowman);
         if (_snowmanx.a.equals(_snowman)) {
            return _snowmanx.b;
         }

         RenderSystem.deleteTexture(_snowmanx.b);
         _snowman = _snowmanx.b;
      } else {
         _snowman = dem.M();
      }

      IntBuffer _snowmanx = null;
      int _snowmanxx = 0;
      int _snowmanxxx = 0;

      try {
         InputStream _snowmanxxxx = new ByteArrayInputStream(new Base64().decode(_snowman));

         BufferedImage _snowmanxxxxx;
         try {
            _snowmanxxxxx = ImageIO.read(_snowmanxxxx);
         } finally {
            IOUtils.closeQuietly(_snowmanxxxx);
         }

         _snowmanxx = _snowmanxxxxx.getWidth();
         _snowmanxxx = _snowmanxxxxx.getHeight();
         int[] var8 = new int[_snowmanxx * _snowmanxxx];
         _snowmanxxxxx.getRGB(0, 0, _snowmanxx, _snowmanxxx, var8, 0, _snowmanxx);
         _snowmanx = ByteBuffer.allocateDirect(4 * _snowmanxx * _snowmanxxx).order(ByteOrder.nativeOrder()).asIntBuffer();
         _snowmanx.put(var8);
         ((Buffer)_snowmanx).flip();
      } catch (IOException var12) {
         var12.printStackTrace();
      }

      RenderSystem.activeTexture(33984);
      RenderSystem.bindTexture(_snowman);
      dex.a(_snowmanx, _snowmanxx, _snowmanxxx);
      a.put(_snowman, new dir.a(_snowman, _snowman));
      return _snowman;
   }

   public static class a {
      private final String a;
      private final int b;

      public a(String var1, int var2) {
         this.a = _snowman;
         this.b = _snowman;
      }
   }
}
