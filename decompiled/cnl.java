import com.mojang.datafixers.Products.P2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Random;
import java.util.Set;

public abstract class cnl {
   public static final Codec<cnl> d = gm.aX.dispatch(cnl::a, cnm::a);
   protected final afw e;
   protected final afw f;

   protected static <P extends cnl> P2<Mu<P>, afw, afw> b(Instance<P> var0) {
      return _snowman.group(afw.a(0, 8, 8).fieldOf("radius").forGetter(var0x -> var0x.e), afw.a(0, 8, 8).fieldOf("offset").forGetter(var0x -> var0x.f));
   }

   public cnl(afw var1, afw var2) {
      this.e = _snowman;
      this.f = _snowman;
   }

   protected abstract cnm<?> a();

   public void a(bsb var1, Random var2, cmz var3, int var4, cnl.b var5, int var6, int var7, Set<fx> var8, cra var9) {
      this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a(_snowman), _snowman);
   }

   protected abstract void a(bsb var1, Random var2, cmz var3, int var4, cnl.b var5, int var6, int var7, Set<fx> var8, int var9, cra var10);

   public abstract int a(Random var1, int var2, cmz var3);

   public int a(Random var1, int var2) {
      return this.e.a(_snowman);
   }

   private int a(Random var1) {
      return this.f.a(_snowman);
   }

   protected abstract boolean a(Random var1, int var2, int var3, int var4, int var5, boolean var6);

   protected boolean b(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      int _snowman;
      int _snowmanx;
      if (_snowman) {
         _snowman = Math.min(Math.abs(_snowman), Math.abs(_snowman - 1));
         _snowmanx = Math.min(Math.abs(_snowman), Math.abs(_snowman - 1));
      } else {
         _snowman = Math.abs(_snowman);
         _snowmanx = Math.abs(_snowman);
      }

      return this.a(_snowman, _snowman, _snowman, _snowmanx, _snowman, _snowman);
   }

   protected void a(bsb var1, Random var2, cmz var3, fx var4, int var5, Set<fx> var6, int var7, boolean var8, cra var9) {
      int _snowman = _snowman ? 1 : 0;
      fx.a _snowmanx = new fx.a();

      for (int _snowmanxx = -_snowman; _snowmanxx <= _snowman + _snowman; _snowmanxx++) {
         for (int _snowmanxxx = -_snowman; _snowmanxxx <= _snowman + _snowman; _snowmanxxx++) {
            if (!this.b(_snowman, _snowmanxx, _snowman, _snowmanxxx, _snowman, _snowman)) {
               _snowmanx.a(_snowman, _snowmanxx, _snowman, _snowmanxxx);
               if (cld.e(_snowman, _snowmanx)) {
                  _snowman.a(_snowmanx, _snowman.c.a(_snowman, _snowmanx), 19);
                  _snowman.c(new cra(_snowmanx, _snowmanx));
                  _snowman.add(_snowmanx.h());
               }
            }
         }
      }
   }

   public static final class b {
      private final fx a;
      private final int b;
      private final boolean c;

      public b(fx var1, int var2, boolean var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public fx a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public boolean c() {
         return this.c;
      }
   }
}
