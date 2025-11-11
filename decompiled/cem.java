import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cem {
   private final Predicate<cel>[][][] a;
   private final int b;
   private final int c;
   private final int d;

   public cem(Predicate<cel>[][][] var1) {
      this.a = _snowman;
      this.b = _snowman.length;
      if (this.b > 0) {
         this.c = _snowman[0].length;
         if (this.c > 0) {
            this.d = _snowman[0][0].length;
         } else {
            this.d = 0;
         }
      } else {
         this.c = 0;
         this.d = 0;
      }
   }

   public int a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   @Nullable
   private cem.b a(fx var1, gc var2, gc var3, LoadingCache<fx, cel> var4) {
      for (int _snowman = 0; _snowman < this.d; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < this.c; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < this.b; _snowmanxx++) {
               if (!this.a[_snowmanxx][_snowmanx][_snowman].test((cel)_snowman.getUnchecked(a(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxx)))) {
                  return null;
               }
            }
         }
      }

      return new cem.b(_snowman, _snowman, _snowman, _snowman, this.d, this.c, this.b);
   }

   @Nullable
   public cem.b a(brz var1, fx var2) {
      LoadingCache<fx, cel> _snowman = a(_snowman, false);
      int _snowmanx = Math.max(Math.max(this.d, this.c), this.b);

      for (fx _snowmanxx : fx.a(_snowman, _snowman.b(_snowmanx - 1, _snowmanx - 1, _snowmanx - 1))) {
         for (gc _snowmanxxx : gc.values()) {
            for (gc _snowmanxxxx : gc.values()) {
               if (_snowmanxxxx != _snowmanxxx && _snowmanxxxx != _snowmanxxx.f()) {
                  cem.b _snowmanxxxxx = this.a(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman);
                  if (_snowmanxxxxx != null) {
                     return _snowmanxxxxx;
                  }
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache<fx, cel> a(brz var0, boolean var1) {
      return CacheBuilder.newBuilder().build(new cem.a(_snowman, _snowman));
   }

   protected static fx a(fx var0, gc var1, gc var2, int var3, int var4, int var5) {
      if (_snowman != _snowman && _snowman != _snowman.f()) {
         gr _snowman = new gr(_snowman.i(), _snowman.j(), _snowman.k());
         gr _snowmanx = new gr(_snowman.i(), _snowman.j(), _snowman.k());
         gr _snowmanxx = _snowman.d(_snowmanx);
         return _snowman.b(_snowmanx.u() * -_snowman + _snowmanxx.u() * _snowman + _snowman.u() * _snowman, _snowmanx.v() * -_snowman + _snowmanxx.v() * _snowman + _snowman.v() * _snowman, _snowmanx.w() * -_snowman + _snowmanxx.w() * _snowman + _snowman.w() * _snowman);
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   static class a extends CacheLoader<fx, cel> {
      private final brz a;
      private final boolean b;

      public a(brz var1, boolean var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public cel a(fx var1) throws Exception {
         return new cel(this.a, _snowman, this.b);
      }
   }

   public static class b {
      private final fx a;
      private final gc b;
      private final gc c;
      private final LoadingCache<fx, cel> d;
      private final int e;
      private final int f;
      private final int g;

      public b(fx var1, gc var2, gc var3, LoadingCache<fx, cel> var4, int var5, int var6, int var7) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
      }

      public fx a() {
         return this.a;
      }

      public gc b() {
         return this.b;
      }

      public gc c() {
         return this.c;
      }

      public cel a(int var1, int var2, int var3) {
         return (cel)this.d.getUnchecked(cem.a(this.a, this.b(), this.c(), _snowman, _snowman, _snowman));
      }

      @Override
      public String toString() {
         return MoreObjects.toStringHelper(this).add("up", this.c).add("forwards", this.b).add("frontTopLeft", this.a).toString();
      }
   }
}
