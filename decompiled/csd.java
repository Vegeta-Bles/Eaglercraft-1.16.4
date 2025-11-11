import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;

public class csd extends csy {
   public static final Codec<csd> a = Codec.FLOAT.fieldOf("mossiness").xmap(csd::new, var0 -> var0.b).codec();
   private final float b;

   public csd(float var1) {
      this.b = _snowman;
   }

   @Nullable
   @Override
   public ctb.c a(brz var1, fx var2, fx var3, ctb.c var4, ctb.c var5, csx var6) {
      Random _snowman = _snowman.b(_snowman.a);
      ceh _snowmanx = _snowman.b;
      fx _snowmanxx = _snowman.a;
      ceh _snowmanxxx = null;
      if (_snowmanx.a(bup.du) || _snowmanx.a(bup.b) || _snowmanx.a(bup.dx)) {
         _snowmanxxx = this.a(_snowman);
      } else if (_snowmanx.a(aed.D)) {
         _snowmanxxx = this.a(_snowman, _snowman.b);
      } else if (_snowmanx.a(aed.E)) {
         _snowmanxxx = this.b(_snowman);
      } else if (_snowmanx.a(aed.F)) {
         _snowmanxxx = this.c(_snowman);
      } else if (_snowmanx.a(bup.bK)) {
         _snowmanxxx = this.d(_snowman);
      }

      return _snowmanxxx != null ? new ctb.c(_snowmanxx, _snowmanxxx, _snowman.c) : _snowman;
   }

   @Nullable
   private ceh a(Random var1) {
      if (_snowman.nextFloat() >= 0.5F) {
         return null;
      } else {
         ceh[] _snowman = new ceh[]{bup.dw.n(), a(_snowman, bup.dS)};
         ceh[] _snowmanx = new ceh[]{bup.dv.n(), a(_snowman, bup.lf)};
         return this.a(_snowman, _snowman, _snowmanx);
      }
   }

   @Nullable
   private ceh a(Random var1, ceh var2) {
      gc _snowman = _snowman.c(cak.a);
      cff _snowmanx = _snowman.c(cak.b);
      if (_snowman.nextFloat() >= 0.5F) {
         return null;
      } else {
         ceh[] _snowmanxx = new ceh[]{bup.hQ.n(), bup.hX.n()};
         ceh[] _snowmanxxx = new ceh[]{bup.lf.n().a(cak.a, _snowman).a(cak.b, _snowmanx), bup.lt.n()};
         return this.a(_snowman, _snowmanxx, _snowmanxxx);
      }
   }

   @Nullable
   private ceh b(Random var1) {
      return _snowman.nextFloat() < this.b ? bup.lt.n() : null;
   }

   @Nullable
   private ceh c(Random var1) {
      return _snowman.nextFloat() < this.b ? bup.lH.n() : null;
   }

   @Nullable
   private ceh d(Random var1) {
      return _snowman.nextFloat() < 0.15F ? bup.ni.n() : null;
   }

   private static ceh a(Random var0, buo var1) {
      return _snowman.n().a(cak.a, gc.c.a.a(_snowman)).a(cak.b, cff.values()[_snowman.nextInt(cff.values().length)]);
   }

   private ceh a(Random var1, ceh[] var2, ceh[] var3) {
      return _snowman.nextFloat() < this.b ? a(_snowman, _snowman) : a(_snowman, _snowman);
   }

   private static ceh a(Random var0, ceh[] var1) {
      return _snowman[_snowman.nextInt(_snowman.length)];
   }

   @Override
   protected cta<?> a() {
      return cta.g;
   }
}
