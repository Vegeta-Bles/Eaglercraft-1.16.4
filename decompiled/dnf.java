import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dnf implements deb {
   private static final Logger a = LogManager.getLogger();
   private final det b;
   private final Int2ObjectMap<dnf.b> c;

   private dnf(det var1, Int2ObjectMap<dnf.b> var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void close() {
      this.b.close();
   }

   @Nullable
   @Override
   public dec a(int var1) {
      return (dec)this.c.get(_snowman);
   }

   @Override
   public IntSet a() {
      return IntSets.unmodifiable(this.c.keySet());
   }

   public static class a implements dng {
      private final vk a;
      private final List<int[]> b;
      private final int c;
      private final int d;

      public a(vk var1, int var2, int var3, List<int[]> var4) {
         this.a = new vk(_snowman.b(), "textures/" + _snowman.a());
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public static dnf.a a(JsonObject var0) {
         int _snowman = afd.a(_snowman, "height", 8);
         int _snowmanx = afd.n(_snowman, "ascent");
         if (_snowmanx > _snowman) {
            throw new JsonParseException("Ascent " + _snowmanx + " higher than height " + _snowman);
         } else {
            List<int[]> _snowmanxx = Lists.newArrayList();
            JsonArray _snowmanxxx = afd.u(_snowman, "chars");

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.size(); _snowmanxxxx++) {
               String _snowmanxxxxx = afd.a(_snowmanxxx.get(_snowmanxxxx), "chars[" + _snowmanxxxx + "]");
               int[] _snowmanxxxxxx = _snowmanxxxxx.codePoints().toArray();
               if (_snowmanxxxx > 0) {
                  int _snowmanxxxxxxx = _snowmanxx.get(0).length;
                  if (_snowmanxxxxxx.length != _snowmanxxxxxxx) {
                     throw new JsonParseException(
                        "Elements of chars have to be the same length (found: " + _snowmanxxxxxx.length + ", expected: " + _snowmanxxxxxxx + "), pad with space or \\u0000"
                     );
                  }
               }

               _snowmanxx.add(_snowmanxxxxxx);
            }

            if (!_snowmanxx.isEmpty() && _snowmanxx.get(0).length != 0) {
               return new dnf.a(new vk(afd.h(_snowman, "file")), _snowman, _snowmanx, _snowmanxx);
            } else {
               throw new JsonParseException("Expected to find data in chars, found none.");
            }
         }
      }

      @Nullable
      @Override
      public deb a(ach var1) {
         try (acg _snowman = _snowman.a(this.a)) {
            det _snowmanx = det.a(det.a.a, _snowman.b());
            int _snowmanxx = _snowmanx.a();
            int _snowmanxxx = _snowmanx.b();
            int _snowmanxxxx = _snowmanxx / this.b.get(0).length;
            int _snowmanxxxxx = _snowmanxxx / this.b.size();
            float _snowmanxxxxxx = (float)this.c / (float)_snowmanxxxxx;
            Int2ObjectMap<dnf.b> _snowmanxxxxxxx = new Int2ObjectOpenHashMap();

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < this.b.size(); _snowmanxxxxxxxx++) {
               int _snowmanxxxxxxxxx = 0;

               for (int _snowmanxxxxxxxxxx : this.b.get(_snowmanxxxxxxxx)) {
                  int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx++;
                  if (_snowmanxxxxxxxxxx != 0 && _snowmanxxxxxxxxxx != 32) {
                     int _snowmanxxxxxxxxxxxx = this.a(_snowmanx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxx);
                     dnf.b _snowmanxxxxxxxxxxxxx = (dnf.b)_snowmanxxxxxxx.put(
                        _snowmanxxxxxxxxxx,
                        new dnf.b(
                           _snowmanxxxxxx,
                           _snowmanx,
                           _snowmanxxxxxxxxxxx * _snowmanxxxx,
                           _snowmanxxxxxxxx * _snowmanxxxxx,
                           _snowmanxxxx,
                           _snowmanxxxxx,
                           (int)(0.5 + (double)((float)_snowmanxxxxxxxxxxxx * _snowmanxxxxxx)) + 1,
                           this.d
                        )
                     );
                     if (_snowmanxxxxxxxxxxxxx != null) {
                        dnf.a.warn("Codepoint '{}' declared multiple times in {}", Integer.toHexString(_snowmanxxxxxxxxxx), this.a);
                     }
                  }
               }
            }

            return new dnf(_snowmanx, _snowmanxxxxxxx);
         } catch (IOException var30) {
            throw new RuntimeException(var30.getMessage());
         }
      }

      private int a(det var1, int var2, int var3, int var4, int var5) {
         int _snowman;
         for (_snowman = _snowman - 1; _snowman >= 0; _snowman--) {
            int _snowmanx = _snowman * _snowman + _snowman;

            for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
               int _snowmanxxx = _snowman * _snowman + _snowmanxx;
               if (_snowman.b(_snowmanx, _snowmanxxx) != 0) {
                  return _snowman + 1;
               }
            }
         }

         return _snowman + 1;
      }
   }

   static final class b implements dec {
      private final float a;
      private final det b;
      private final int c;
      private final int d;
      private final int e;
      private final int f;
      private final int g;
      private final int h;

      private b(float var1, det var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
      }

      @Override
      public float g() {
         return 1.0F / this.a;
      }

      @Override
      public int d() {
         return this.e;
      }

      @Override
      public int e() {
         return this.f;
      }

      @Override
      public float getAdvance() {
         return (float)this.g;
      }

      @Override
      public float l() {
         return dec.super.l() + 7.0F - (float)this.h;
      }

      @Override
      public void a(int var1, int var2) {
         this.b.a(0, _snowman, _snowman, this.c, this.d, this.e, this.f, false, false);
      }

      @Override
      public boolean f() {
         return this.b.c().a() > 1;
      }
   }
}
