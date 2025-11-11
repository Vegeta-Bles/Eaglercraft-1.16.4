import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class dfe {
   private final dfe.a a;
   private final String b;
   private final int c;
   private int d;

   private dfe(dfe.a var1, int var2, String var3) {
      this.a = _snowman;
      this.c = _snowman;
      this.b = _snowman;
   }

   public void a(dfd var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      this.d++;
      dem.d(_snowman.a(), this.c);
   }

   public void a() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      this.d--;
      if (this.d <= 0) {
         dem.d(this.c);
         this.a.c().remove(this.b);
      }
   }

   public String b() {
      return this.b;
   }

   public static dfe a(dfe.a var0, String var1, InputStream var2, String var3) throws IOException {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      String _snowman = dex.b(_snowman);
      if (_snowman == null) {
         throw new IOException("Could not load program " + _snowman.a());
      } else {
         int _snowmanx = dem.e(_snowman.d());
         dem.a(_snowmanx, _snowman);
         dem.f(_snowmanx);
         if (dem.e(_snowmanx, 35713) == 0) {
            String _snowmanxx = StringUtils.trim(dem.i(_snowmanx, 32768));
            throw new IOException("Couldn't compile " + _snowman.a() + " program (" + _snowman + ", " + _snowman + ") : " + _snowmanxx);
         } else {
            dfe _snowmanxx = new dfe(_snowman, _snowmanx, _snowman);
            _snowman.c().put(_snowman, _snowmanxx);
            return _snowmanxx;
         }
      }
   }

   public static enum a {
      a("vertex", ".vsh", 35633),
      b("fragment", ".fsh", 35632);

      private final String c;
      private final String d;
      private final int e;
      private final Map<String, dfe> f = Maps.newHashMap();

      private a(String var3, String var4, int var5) {
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }

      public String a() {
         return this.c;
      }

      public String b() {
         return this.d;
      }

      private int d() {
         return this.e;
      }

      public Map<String, dfe> c() {
         return this.f;
      }
   }
}
