import java.util.Random;
import javax.annotation.Nullable;

public abstract class cvd extends cuv {
   public cvd() {
   }

   @Override
   public cuw d() {
      return cuy.b;
   }

   @Override
   public cuw e() {
      return cuy.c;
   }

   @Override
   public blx a() {
      return bmd.lL;
   }

   @Override
   public void a(brx var1, fx var2, cux var3, Random var4) {
      if (!_snowman.b() && !_snowman.c(a)) {
         if (_snowman.nextInt(64) == 0) {
            _snowman.a((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, adq.qC, adr.e, _snowman.nextFloat() * 0.25F + 0.75F, _snowman.nextFloat() + 0.5F, false);
         }
      } else if (_snowman.nextInt(10) == 0) {
         _snowman.a(hh.Y, (double)_snowman.u() + _snowman.nextDouble(), (double)_snowman.v() + _snowman.nextDouble(), (double)_snowman.w() + _snowman.nextDouble(), 0.0, 0.0, 0.0);
      }
   }

   @Nullable
   @Override
   public hf i() {
      return hh.m;
   }

   @Override
   protected boolean f() {
      return true;
   }

   @Override
   protected void a(bry var1, fx var2, ceh var3) {
      ccj _snowman = _snowman.b().q() ? _snowman.c(_snowman) : null;
      buo.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public int b(brz var1) {
      return 4;
   }

   @Override
   public ceh b(cux var1) {
      return bup.A.n().a(byb.a, Integer.valueOf(e(_snowman)));
   }

   @Override
   public boolean a(cuw var1) {
      return _snowman == cuy.c || _snowman == cuy.b;
   }

   @Override
   public int c(brz var1) {
      return 1;
   }

   @Override
   public int a(brz var1) {
      return 5;
   }

   @Override
   public boolean a(cux var1, brc var2, fx var3, cuw var4, gc var5) {
      return _snowman == gc.a && !_snowman.a(aef.b);
   }

   @Override
   protected float c() {
      return 100.0F;
   }

   public static class a extends cvd {
      public a() {
      }

      @Override
      protected void a(cei.a<cuw, cux> var1) {
         super.a(_snowman);
         _snowman.a(b);
      }

      @Override
      public int d(cux var1) {
         return _snowman.c(b);
      }

      @Override
      public boolean c(cux var1) {
         return false;
      }
   }

   public static class b extends cvd {
      public b() {
      }

      @Override
      public int d(cux var1) {
         return 8;
      }

      @Override
      public boolean c(cux var1) {
         return true;
      }
   }
}
