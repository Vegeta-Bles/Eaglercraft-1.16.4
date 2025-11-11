import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class chn {
   private static final Predicate<ceh> a = var0 -> !var0.g();
   private static final Predicate<ceh> b = var0 -> var0.c().c();
   private final aer c = new aer(9, 256);
   private final Predicate<ceh> d;
   private final cfw e;

   public chn(cfw var1, chn.a var2) {
      this.d = _snowman.e();
      this.e = _snowman;
   }

   public static void a(cfw var0, Set<chn.a> var1) {
      int _snowman = _snowman.size();
      ObjectList<chn> _snowmanx = new ObjectArrayList(_snowman);
      ObjectListIterator<chn> _snowmanxx = _snowmanx.iterator();
      int _snowmanxxx = _snowman.b() + 16;
      fx.a _snowmanxxxx = new fx.a();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 16; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
            for (chn.a _snowmanxxxxxxx : _snowman) {
               _snowmanx.add(_snowman.a(_snowmanxxxxxxx));
            }

            for (int _snowmanxxxxxxx = _snowmanxxx - 1; _snowmanxxxxxxx >= 0; _snowmanxxxxxxx--) {
               _snowmanxxxx.d(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx);
               ceh _snowmanxxxxxxxx = _snowman.d_(_snowmanxxxx);
               if (!_snowmanxxxxxxxx.a(bup.a)) {
                  while (_snowmanxx.hasNext()) {
                     chn _snowmanxxxxxxxxx = (chn)_snowmanxx.next();
                     if (_snowmanxxxxxxxxx.d.test(_snowmanxxxxxxxx)) {
                        _snowmanxxxxxxxxx.a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx + 1);
                        _snowmanxx.remove();
                     }
                  }

                  if (_snowmanx.isEmpty()) {
                     break;
                  }

                  _snowmanxx.back(_snowman);
               }
            }
         }
      }
   }

   public boolean a(int var1, int var2, int var3, ceh var4) {
      int _snowman = this.a(_snowman, _snowman);
      if (_snowman <= _snowman - 2) {
         return false;
      } else {
         if (this.d.test(_snowman)) {
            if (_snowman >= _snowman) {
               this.a(_snowman, _snowman, _snowman + 1);
               return true;
            }
         } else if (_snowman - 1 == _snowman) {
            fx.a _snowmanx = new fx.a();

            for (int _snowmanxx = _snowman - 1; _snowmanxx >= 0; _snowmanxx--) {
               _snowmanx.d(_snowman, _snowmanxx, _snowman);
               if (this.d.test(this.e.d_(_snowmanx))) {
                  this.a(_snowman, _snowman, _snowmanxx + 1);
                  return true;
               }
            }

            this.a(_snowman, _snowman, 0);
            return true;
         }

         return false;
      }
   }

   public int a(int var1, int var2) {
      return this.a(c(_snowman, _snowman));
   }

   private int a(int var1) {
      return this.c.a(_snowman);
   }

   private void a(int var1, int var2, int var3) {
      this.c.b(c(_snowman, _snowman), _snowman);
   }

   public void a(long[] var1) {
      System.arraycopy(_snowman, 0, this.c.a(), 0, _snowman.length);
   }

   public long[] a() {
      return this.c.a();
   }

   private static int c(int var0, int var1) {
      return _snowman + _snowman * 16;
   }

   public static enum a implements afs {
      a("WORLD_SURFACE_WG", chn.b.a, chn.a),
      b("WORLD_SURFACE", chn.b.c, chn.a),
      c("OCEAN_FLOOR_WG", chn.b.a, chn.b),
      d("OCEAN_FLOOR", chn.b.b, chn.b),
      e("MOTION_BLOCKING", chn.b.c, var0 -> var0.c().c() || !var0.m().c()),
      f("MOTION_BLOCKING_NO_LEAVES", chn.b.b, var0 -> (var0.c().c() || !var0.m().c()) && !(var0.b() instanceof bxx));

      public static final Codec<chn.a> g = afs.a(chn.a::values, chn.a::a);
      private final String h;
      private final chn.b i;
      private final Predicate<ceh> j;
      private static final Map<String, chn.a> k = x.a(Maps.newHashMap(), var0 -> {
         for (chn.a _snowman : values()) {
            var0.put(_snowman.h, _snowman);
         }
      });

      private a(String var3, chn.b var4, Predicate<ceh> var5) {
         this.h = _snowman;
         this.i = _snowman;
         this.j = _snowman;
      }

      public String b() {
         return this.h;
      }

      public boolean c() {
         return this.i == chn.b.c;
      }

      public boolean d() {
         return this.i != chn.b.a;
      }

      @Nullable
      public static chn.a a(String var0) {
         return k.get(_snowman);
      }

      public Predicate<ceh> e() {
         return this.j;
      }

      @Override
      public String a() {
         return this.h;
      }
   }

   public static enum b {
      a,
      b,
      c;

      private b() {
      }
   }
}
