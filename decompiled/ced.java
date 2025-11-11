import java.util.Iterator;
import java.util.List;

public class ced extends ccj implements cdm {
   private ceh a;
   private gc b;
   private boolean c;
   private boolean g;
   private static final ThreadLocal<gc> h = ThreadLocal.withInitial(() -> null);
   private float i;
   private float j;
   private long k;
   private int l;

   public ced() {
      super(cck.j);
   }

   public ced(ceh var1, gc var2, boolean var3, boolean var4) {
      this();
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.g = _snowman;
   }

   @Override
   public md b() {
      return this.a(new md());
   }

   public boolean d() {
      return this.c;
   }

   public gc f() {
      return this.b;
   }

   public boolean h() {
      return this.g;
   }

   public float a(float var1) {
      if (_snowman > 1.0F) {
         _snowman = 1.0F;
      }

      return afm.g(_snowman, this.j, this.i);
   }

   public float b(float var1) {
      return (float)this.b.i() * this.e(this.a(_snowman));
   }

   public float c(float var1) {
      return (float)this.b.j() * this.e(this.a(_snowman));
   }

   public float d(float var1) {
      return (float)this.b.k() * this.e(this.a(_snowman));
   }

   private float e(float var1) {
      return this.c ? _snowman - 1.0F : 1.0F - _snowman;
   }

   private ceh x() {
      return !this.d() && this.h() && this.a.b() instanceof cea
         ? bup.aX.n().a(ceb.c, Boolean.valueOf(this.i > 0.25F)).a(ceb.b, this.a.a(bup.aP) ? cfi.b : cfi.a).a(ceb.a, this.a.c(cea.a))
         : this.a;
   }

   private void f(float var1) {
      gc _snowman = this.j();
      double _snowmanx = (double)(_snowman - this.i);
      ddh _snowmanxx = this.x().k(this.d, this.o());
      if (!_snowmanxx.b()) {
         dci _snowmanxxx = this.a(_snowmanxx.a());
         List<aqa> _snowmanxxxx = this.d.a(null, cec.a(_snowmanxxx, _snowman, _snowmanx).b(_snowmanxxx));
         if (!_snowmanxxxx.isEmpty()) {
            List<dci> _snowmanxxxxx = _snowmanxx.d();
            boolean _snowmanxxxxxx = this.a.a(bup.gn);
            Iterator var10 = _snowmanxxxx.iterator();

            while (true) {
               aqa _snowmanxxxxxxx;
               while (true) {
                  if (!var10.hasNext()) {
                     return;
                  }

                  _snowmanxxxxxxx = (aqa)var10.next();
                  if (_snowmanxxxxxxx.y_() != cvc.d) {
                     if (!_snowmanxxxxxx) {
                        break;
                     }

                     if (!(_snowmanxxxxxxx instanceof aah)) {
                        dcn _snowmanxxxxxxxx = _snowmanxxxxxxx.cC();
                        double _snowmanxxxxxxxxx = _snowmanxxxxxxxx.b;
                        double _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.c;
                        double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.d;
                        switch (_snowman.n()) {
                           case a:
                              _snowmanxxxxxxxxx = (double)_snowman.i();
                              break;
                           case b:
                              _snowmanxxxxxxxxxx = (double)_snowman.j();
                              break;
                           case c:
                              _snowmanxxxxxxxxxxx = (double)_snowman.k();
                        }

                        _snowmanxxxxxxx.n(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                        break;
                     }
                  }
               }

               double _snowmanxxxxxxxx = 0.0;

               for (dci _snowmanxxxxxxxxx : _snowmanxxxxx) {
                  dci _snowmanxxxxxxxxxx = cec.a(this.a(_snowmanxxxxxxxxx), _snowman, _snowmanx);
                  dci _snowmanxxxxxxxxxxx = _snowmanxxxxxxx.cc();
                  if (_snowmanxxxxxxxxxx.c(_snowmanxxxxxxxxxxx)) {
                     _snowmanxxxxxxxx = Math.max(_snowmanxxxxxxxx, a(_snowmanxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxx));
                     if (_snowmanxxxxxxxx >= _snowmanx) {
                        break;
                     }
                  }
               }

               if (!(_snowmanxxxxxxxx <= 0.0)) {
                  _snowmanxxxxxxxx = Math.min(_snowmanxxxxxxxx, _snowmanx) + 0.01;
                  a(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman);
                  if (!this.c && this.g) {
                     this.a(_snowmanxxxxxxx, _snowman, _snowmanx);
                  }
               }
            }
         }
      }
   }

   private static void a(gc var0, aqa var1, double var2, gc var4) {
      h.set(_snowman);
      _snowman.a(aqr.c, new dcn(_snowman * (double)_snowman.i(), _snowman * (double)_snowman.j(), _snowman * (double)_snowman.k()));
      h.set(null);
   }

   private void g(float var1) {
      if (this.y()) {
         gc _snowman = this.j();
         if (_snowman.n().d()) {
            double _snowmanx = this.a.k(this.d, this.e).c(gc.a.b);
            dci _snowmanxx = this.a(new dci(0.0, _snowmanx, 0.0, 1.0, 1.5000000999999998, 1.0));
            double _snowmanxxx = (double)(_snowman - this.i);

            for (aqa _snowmanxxxx : this.d.a((aqa)null, _snowmanxx, var1x -> a(_snowman, var1x))) {
               a(_snowman, _snowmanxxxx, _snowmanxxx, _snowman);
            }
         }
      }
   }

   private static boolean a(dci var0, aqa var1) {
      return _snowman.y_() == cvc.a && _snowman.ao() && _snowman.cD() >= _snowman.a && _snowman.cD() <= _snowman.d && _snowman.cH() >= _snowman.c && _snowman.cH() <= _snowman.f;
   }

   private boolean y() {
      return this.a.a(bup.ne);
   }

   public gc j() {
      return this.c ? this.b : this.b.f();
   }

   private static double a(dci var0, gc var1, dci var2) {
      switch (_snowman) {
         case f:
            return _snowman.d - _snowman.a;
         case e:
            return _snowman.d - _snowman.a;
         case b:
         default:
            return _snowman.e - _snowman.b;
         case a:
            return _snowman.e - _snowman.b;
         case d:
            return _snowman.f - _snowman.c;
         case c:
            return _snowman.f - _snowman.c;
      }
   }

   private dci a(dci var1) {
      double _snowman = (double)this.e(this.i);
      return _snowman.d((double)this.e.u() + _snowman * (double)this.b.i(), (double)this.e.v() + _snowman * (double)this.b.j(), (double)this.e.w() + _snowman * (double)this.b.k());
   }

   private void a(aqa var1, gc var2, double var3) {
      dci _snowman = _snowman.cc();
      dci _snowmanx = dde.b().a().a(this.e);
      if (_snowman.c(_snowmanx)) {
         gc _snowmanxx = _snowman.f();
         double _snowmanxxx = a(_snowmanx, _snowmanxx, _snowman) + 0.01;
         double _snowmanxxxx = a(_snowmanx, _snowmanxx, _snowman.a(_snowmanx)) + 0.01;
         if (Math.abs(_snowmanxxx - _snowmanxxxx) < 0.01) {
            _snowmanxxx = Math.min(_snowmanxxx, _snowman) + 0.01;
            a(_snowman, _snowman, _snowmanxxx, _snowmanxx);
         }
      }
   }

   public ceh k() {
      return this.a;
   }

   public void l() {
      if (this.d != null && (this.j < 1.0F || this.d.v)) {
         this.i = 1.0F;
         this.j = this.i;
         this.d.o(this.e);
         this.al_();
         if (this.d.d_(this.e).a(bup.bo)) {
            ceh _snowman;
            if (this.g) {
               _snowman = bup.a.n();
            } else {
               _snowman = buo.b(this.a, (bry)this.d, this.e);
            }

            this.d.a(this.e, _snowman, 3);
            this.d.a(this.e, _snowman.b(), this.e);
         }
      }
   }

   @Override
   public void aj_() {
      this.k = this.d.T();
      this.j = this.i;
      if (this.j >= 1.0F) {
         if (this.d.v && this.l < 5) {
            this.l++;
         } else {
            this.d.o(this.e);
            this.al_();
            if (this.a != null && this.d.d_(this.e).a(bup.bo)) {
               ceh _snowman = buo.b(this.a, (bry)this.d, this.e);
               if (_snowman.g()) {
                  this.d.a(this.e, this.a, 84);
                  buo.a(this.a, _snowman, this.d, this.e, 3);
               } else {
                  if (_snowman.b(cex.C) && _snowman.c(cex.C)) {
                     _snowman = _snowman.a(cex.C, Boolean.valueOf(false));
                  }

                  this.d.a(this.e, _snowman, 67);
                  this.d.a(this.e, _snowman.b(), this.e);
               }
            }
         }
      } else {
         float _snowman = this.i + 0.5F;
         this.f(_snowman);
         this.g(_snowman);
         this.i = _snowman;
         if (this.i >= 1.0F) {
            this.i = 1.0F;
         }
      }
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.a = mp.c(_snowman.p("blockState"));
      this.b = gc.a(_snowman.h("facing"));
      this.i = _snowman.j("progress");
      this.j = this.i;
      this.c = _snowman.q("extending");
      this.g = _snowman.q("source");
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      _snowman.a("blockState", mp.a(this.a));
      _snowman.b("facing", this.b.c());
      _snowman.a("progress", this.j);
      _snowman.a("extending", this.c);
      _snowman.a("source", this.g);
      return _snowman;
   }

   public ddh a(brc var1, fx var2) {
      ddh _snowman;
      if (!this.c && this.g) {
         _snowman = this.a.a(cea.b, Boolean.valueOf(true)).k(_snowman, _snowman);
      } else {
         _snowman = dde.a();
      }

      gc _snowmanx = h.get();
      if ((double)this.i < 1.0 && _snowmanx == this.j()) {
         return _snowman;
      } else {
         ceh _snowmanxx;
         if (this.h()) {
            _snowmanxx = bup.aX.n().a(ceb.a, this.b).a(ceb.c, Boolean.valueOf(this.c != 1.0F - this.i < 0.25F));
         } else {
            _snowmanxx = this.a;
         }

         float _snowmanxxx = this.e(this.i);
         double _snowmanxxxx = (double)((float)this.b.i() * _snowmanxxx);
         double _snowmanxxxxx = (double)((float)this.b.j() * _snowmanxxx);
         double _snowmanxxxxxx = (double)((float)this.b.k() * _snowmanxxx);
         return dde.a(_snowman, _snowmanxx.k(_snowman, _snowman).a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx));
      }
   }

   public long m() {
      return this.k;
   }

   @Override
   public double i() {
      return 68.0;
   }
}
