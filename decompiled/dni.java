import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dni implements deb {
   private static final Logger a = LogManager.getLogger();
   private final ach b;
   private final byte[] c;
   private final String d;
   private final Map<vk, det> e = Maps.newHashMap();

   public dni(ach var1, byte[] var2, String var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;

      for (int _snowman = 0; _snowman < 256; _snowman++) {
         int _snowmanx = _snowman * 256;
         vk _snowmanxx = this.b(_snowmanx);

         try (
            acg _snowmanxxx = this.b.a(_snowmanxx);
            det _snowmanxxxx = det.a(det.a.a, _snowmanxxx.b());
         ) {
            if (_snowmanxxxx.a() == 256 && _snowmanxxxx.b() == 256) {
               for (int _snowmanxxxxx = 0; _snowmanxxxxx < 256; _snowmanxxxxx++) {
                  byte _snowmanxxxxxx = _snowman[_snowmanx + _snowmanxxxxx];
                  if (_snowmanxxxxxx != 0 && a(_snowmanxxxxxx) > b(_snowmanxxxxxx)) {
                     _snowman[_snowmanx + _snowmanxxxxx] = 0;
                  }
               }
               continue;
            }
         } catch (IOException var43) {
         }

         Arrays.fill(_snowman, _snowmanx, _snowmanx + 256, (byte)0);
      }
   }

   @Override
   public void close() {
      this.e.values().forEach(det::close);
   }

   private vk b(int var1) {
      vk _snowman = new vk(String.format(this.d, String.format("%02x", _snowman / 256)));
      return new vk(_snowman.b(), "textures/" + _snowman.a());
   }

   @Nullable
   @Override
   public dec a(int var1) {
      if (_snowman >= 0 && _snowman <= 65535) {
         byte _snowman = this.c[_snowman];
         if (_snowman != 0) {
            det _snowmanx = this.e.computeIfAbsent(this.b(_snowman), this::a);
            if (_snowmanx != null) {
               int _snowmanxx = a(_snowman);
               return new dni.b(_snowman % 16 * 16 + _snowmanxx, (_snowman & 0xFF) / 16 * 16, b(_snowman) - _snowmanxx, 16, _snowmanx);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Override
   public IntSet a() {
      IntSet _snowman = new IntOpenHashSet();

      for (int _snowmanx = 0; _snowmanx < 65535; _snowmanx++) {
         if (this.c[_snowmanx] != 0) {
            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
   }

   @Nullable
   private det a(vk var1) {
      try (acg _snowman = this.b.a(_snowman)) {
         return det.a(det.a.a, _snowman.b());
      } catch (IOException var16) {
         a.error("Couldn't load texture {}", _snowman, var16);
         return null;
      }
   }

   private static int a(byte var0) {
      return _snowman >> 4 & 15;
   }

   private static int b(byte var0) {
      return (_snowman & 15) + 1;
   }

   public static class a implements dng {
      private final vk a;
      private final String b;

      public a(vk var1, String var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public static dng a(JsonObject var0) {
         return new dni.a(new vk(afd.h(_snowman, "sizes")), afd.h(_snowman, "template"));
      }

      @Nullable
      @Override
      public deb a(ach var1) {
         try (acg _snowman = djz.C().N().a(this.a)) {
            byte[] _snowmanx = new byte[65536];
            _snowman.b().read(_snowmanx);
            return new dni(_snowman, _snowmanx, this.b);
         } catch (IOException var17) {
            dni.a.error("Cannot load {}, unicode glyphs will not render correctly", this.a);
            return null;
         }
      }
   }

   static class b implements dec {
      private final int a;
      private final int b;
      private final int c;
      private final int d;
      private final det e;

      private b(int var1, int var2, int var3, int var4, det var5) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }

      @Override
      public float g() {
         return 2.0F;
      }

      @Override
      public int d() {
         return this.a;
      }

      @Override
      public int e() {
         return this.b;
      }

      @Override
      public float getAdvance() {
         return (float)(this.a / 2 + 1);
      }

      @Override
      public void a(int var1, int var2) {
         this.e.a(0, _snowman, _snowman, this.c, this.d, this.a, this.b, false, false);
      }

      @Override
      public boolean f() {
         return this.e.c().a() > 1;
      }

      @Override
      public float c() {
         return 0.5F;
      }

      @Override
      public float b() {
         return 0.5F;
      }
   }
}
