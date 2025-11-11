import java.util.Random;
import javax.annotation.Nullable;

public class awy extends awj {
   private final buo g;
   private final aqn h;
   private int i;

   public awy(buo var1, aqu var2, double var3, int var5) {
      super(_snowman, _snowman, 24, _snowman);
      this.g = _snowman;
      this.h = _snowman;
   }

   @Override
   public boolean a() {
      if (!this.h.l.V().b(brt.b)) {
         return false;
      } else if (this.c > 0) {
         this.c--;
         return false;
      } else if (this.n()) {
         this.c = 20;
         return true;
      } else {
         this.c = this.a(this.a);
         return false;
      }
   }

   private boolean n() {
      return this.e != null && this.a((brz)this.a.l, this.e) ? true : this.m();
   }

   @Override
   public void d() {
      super.d();
      this.h.C = 1.0F;
   }

   @Override
   public void c() {
      super.c();
      this.i = 0;
   }

   public void a(bry var1, fx var2) {
   }

   public void a(brx var1, fx var2) {
   }

   @Override
   public void e() {
      super.e();
      brx _snowman = this.h.l;
      fx _snowmanx = this.h.cB();
      fx _snowmanxx = this.a(_snowmanx, _snowman);
      Random _snowmanxxx = this.h.cY();
      if (this.l() && _snowmanxx != null) {
         if (this.i > 0) {
            dcn _snowmanxxxx = this.h.cC();
            this.h.n(_snowmanxxxx.b, 0.3, _snowmanxxxx.d);
            if (!_snowman.v) {
               double _snowmanxxxxx = 0.08;
               ((aag)_snowman)
                  .a(
                     new he(hh.I, new bmb(bmd.mg)),
                     (double)_snowmanxx.u() + 0.5,
                     (double)_snowmanxx.v() + 0.7,
                     (double)_snowmanxx.w() + 0.5,
                     3,
                     ((double)_snowmanxxx.nextFloat() - 0.5) * 0.08,
                     ((double)_snowmanxxx.nextFloat() - 0.5) * 0.08,
                     ((double)_snowmanxxx.nextFloat() - 0.5) * 0.08,
                     0.15F
                  );
            }
         }

         if (this.i % 2 == 0) {
            dcn _snowmanxxxx = this.h.cC();
            this.h.n(_snowmanxxxx.b, -0.3, _snowmanxxxx.d);
            if (this.i % 6 == 0) {
               this.a((bry)_snowman, this.e);
            }
         }

         if (this.i > 60) {
            _snowman.a(_snowmanxx, false);
            if (!_snowman.v) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < 20; _snowmanxxxx++) {
                  double _snowmanxxxxx = _snowmanxxx.nextGaussian() * 0.02;
                  double _snowmanxxxxxx = _snowmanxxx.nextGaussian() * 0.02;
                  double _snowmanxxxxxxx = _snowmanxxx.nextGaussian() * 0.02;
                  ((aag)_snowman).a(hh.P, (double)_snowmanxx.u() + 0.5, (double)_snowmanxx.v(), (double)_snowmanxx.w() + 0.5, 1, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, 0.15F);
               }

               this.a(_snowman, _snowmanxx);
            }
         }

         this.i++;
      }
   }

   @Nullable
   private fx a(fx var1, brc var2) {
      if (_snowman.d_(_snowman).a(this.g)) {
         return _snowman;
      } else {
         fx[] _snowman = new fx[]{_snowman.c(), _snowman.f(), _snowman.g(), _snowman.d(), _snowman.e(), _snowman.c().c()};

         for (fx _snowmanx : _snowman) {
            if (_snowman.d_(_snowmanx).a(this.g)) {
               return _snowmanx;
            }
         }

         return null;
      }
   }

   @Override
   protected boolean a(brz var1, fx var2) {
      cfw _snowman = _snowman.a(_snowman.u() >> 4, _snowman.w() >> 4, cga.m, false);
      return _snowman == null ? false : _snowman.d_(_snowman).a(this.g) && _snowman.d_(_snowman.b()).g() && _snowman.d_(_snowman.b(2)).g();
   }
}
