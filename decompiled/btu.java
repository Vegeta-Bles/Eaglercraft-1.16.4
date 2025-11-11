import java.util.Random;
import javax.annotation.Nullable;

public class btu extends buo implements buq {
   protected static final ddh a = buo.a(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
   protected static final ddh b = buo.a(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   protected static final ddh c = buo.a(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
   public static final cfg d = cex.ae;
   public static final cfe<ceu> e = cex.aN;
   public static final cfg f = cex.aA;

   public btu(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(d, Integer.valueOf(0)).a(e, ceu.a).a(f, Integer.valueOf(0)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(d, e, f);
   }

   @Override
   public ceg.b ah_() {
      return ceg.b.b;
   }

   @Override
   public boolean b(ceh var1, brc var2, fx var3) {
      return true;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      ddh _snowman = _snowman.c(e) == ceu.c ? b : a;
      dcn _snowmanx = _snowman.n(_snowman, _snowman);
      return _snowman.a(_snowmanx.b, _snowmanx.c, _snowmanx.d);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      dcn _snowman = _snowman.n(_snowman, _snowman);
      return c.a(_snowman.b, _snowman.c, _snowman.d);
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      cux _snowman = _snowman.p().b(_snowman.a());
      if (!_snowman.c()) {
         return null;
      } else {
         ceh _snowmanx = _snowman.p().d_(_snowman.a().c());
         if (_snowmanx.a(aed.ac)) {
            if (_snowmanx.a(bup.kX)) {
               return this.n().a(d, Integer.valueOf(0));
            } else if (_snowmanx.a(bup.kY)) {
               int _snowmanxx = _snowmanx.c(d) > 0 ? 1 : 0;
               return this.n().a(d, Integer.valueOf(_snowmanxx));
            } else {
               ceh _snowmanxx = _snowman.p().d_(_snowman.a().b());
               return !_snowmanxx.a(bup.kY) && !_snowmanxx.a(bup.kX) ? bup.kX.n() : this.n().a(d, _snowmanxx.c(d));
            }
         } else {
            return null;
         }
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (!_snowman.a(_snowman, _snowman)) {
         _snowman.b(_snowman, true);
      }
   }

   @Override
   public boolean a_(ceh var1) {
      return _snowman.c(f) == 0;
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.c(f) == 0) {
         if (_snowman.nextInt(3) == 0 && _snowman.w(_snowman.b()) && _snowman.b(_snowman.b(), 0) >= 9) {
            int _snowman = this.b(_snowman, _snowman) + 1;
            if (_snowman < 16) {
               this.a(_snowman, _snowman, _snowman, _snowman, _snowman);
            }
         }
      }
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return _snowman.d_(_snowman.c()).a(aed.ac);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (!_snowman.a(_snowman, _snowman)) {
         _snowman.J().a(_snowman, this, 1);
      }

      if (_snowman == gc.b && _snowman.a(bup.kY) && _snowman.c(d) > _snowman.c(d)) {
         _snowman.a(_snowman, _snowman.a(d), 2);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      int _snowman = this.a(_snowman, _snowman);
      int _snowmanx = this.b(_snowman, _snowman);
      return _snowman + _snowmanx + 1 < 16 && _snowman.d_(_snowman.b(_snowman)).c(f) != 1;
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      int _snowman = this.a(_snowman, _snowman);
      int _snowmanx = this.b(_snowman, _snowman);
      int _snowmanxx = _snowman + _snowmanx + 1;
      int _snowmanxxx = 1 + _snowman.nextInt(2);

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
         fx _snowmanxxxxx = _snowman.b(_snowman);
         ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxxxx);
         if (_snowmanxx >= 16 || _snowmanxxxxxx.c(f) == 1 || !_snowman.w(_snowmanxxxxx.b())) {
            return;
         }

         this.a(_snowmanxxxxxx, _snowman, _snowmanxxxxx, _snowman, _snowmanxx);
         _snowman++;
         _snowmanxx++;
      }
   }

   @Override
   public float a(ceh var1, bfw var2, brc var3, fx var4) {
      return _snowman.dD().b() instanceof bnf ? 1.0F : super.a(_snowman, _snowman, _snowman, _snowman);
   }

   protected void a(ceh var1, brx var2, fx var3, Random var4, int var5) {
      ceh _snowman = _snowman.d_(_snowman.c());
      fx _snowmanx = _snowman.c(2);
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      ceu _snowmanxxx = ceu.a;
      if (_snowman >= 1) {
         if (!_snowman.a(bup.kY) || _snowman.c(e) == ceu.a) {
            _snowmanxxx = ceu.b;
         } else if (_snowman.a(bup.kY) && _snowman.c(e) != ceu.a) {
            _snowmanxxx = ceu.c;
            if (_snowmanxx.a(bup.kY)) {
               _snowman.a(_snowman.c(), _snowman.a(e, ceu.b), 3);
               _snowman.a(_snowmanx, _snowmanxx.a(e, ceu.a), 3);
            }
         }
      }

      int _snowmanxxxx = _snowman.c(d) != 1 && !_snowmanxx.a(bup.kY) ? 0 : 1;
      int _snowmanxxxxx = (_snowman < 11 || !(_snowman.nextFloat() < 0.25F)) && _snowman != 15 ? 0 : 1;
      _snowman.a(_snowman.b(), this.n().a(d, Integer.valueOf(_snowmanxxxx)).a(e, _snowmanxxx).a(f, Integer.valueOf(_snowmanxxxxx)), 3);
   }

   protected int a(brc var1, fx var2) {
      int _snowman = 0;

      while (_snowman < 16 && _snowman.d_(_snowman.b(_snowman + 1)).a(bup.kY)) {
         _snowman++;
      }

      return _snowman;
   }

   protected int b(brc var1, fx var2) {
      int _snowman = 0;

      while (_snowman < 16 && _snowman.d_(_snowman.c(_snowman + 1)).a(bup.kY)) {
         _snowman++;
      }

      return _snowman;
   }
}
