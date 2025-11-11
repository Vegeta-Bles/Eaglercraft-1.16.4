import java.util.List;
import org.apache.commons.lang3.mutable.MutableInt;

public class cch extends ccj implements cdm {
   private long g;
   public int a;
   public boolean b;
   public gc c;
   private List<aqm> h;
   private boolean i;
   private int j;

   public cch() {
      super(cck.D);
   }

   @Override
   public boolean a_(int var1, int var2) {
      if (_snowman == 1) {
         this.f();
         this.j = 0;
         this.c = gc.a(_snowman);
         this.a = 0;
         this.b = true;
         return true;
      } else {
         return super.a_(_snowman, _snowman);
      }
   }

   @Override
   public void aj_() {
      if (this.b) {
         this.a++;
      }

      if (this.a >= 50) {
         this.b = false;
         this.a = 0;
      }

      if (this.a >= 5 && this.j == 0 && this.h()) {
         this.i = true;
         this.d();
      }

      if (this.i) {
         if (this.j < 40) {
            this.j++;
         } else {
            this.a(this.d);
            this.b(this.d);
            this.i = false;
         }
      }
   }

   private void d() {
      this.d.a(null, this.o(), adq.aK, adr.e, 1.0F, 1.0F);
   }

   public void a(gc var1) {
      fx _snowman = this.o();
      this.c = _snowman;
      if (this.b) {
         this.a = 0;
      } else {
         this.b = true;
      }

      this.d.a(_snowman, this.p().b(), 1, _snowman.c());
   }

   private void f() {
      fx _snowman = this.o();
      if (this.d.T() > this.g + 60L || this.h == null) {
         this.g = this.d.T();
         dci _snowmanx = new dci(_snowman).g(48.0);
         this.h = this.d.a(aqm.class, _snowmanx);
      }

      if (!this.d.v) {
         for (aqm _snowmanx : this.h) {
            if (_snowmanx.aX() && !_snowmanx.y && _snowman.a(_snowmanx.cA(), 32.0)) {
               _snowmanx.cJ().a(ayd.C, this.d.T());
            }
         }
      }
   }

   private boolean h() {
      fx _snowman = this.o();

      for (aqm _snowmanx : this.h) {
         if (_snowmanx.aX() && !_snowmanx.y && _snowman.a(_snowmanx.cA(), 32.0) && _snowmanx.X().a(aee.c)) {
            return true;
         }
      }

      return false;
   }

   private void a(brx var1) {
      if (!_snowman.v) {
         this.h.stream().filter(this::a).forEach(this::b);
      }
   }

   private void b(brx var1) {
      if (_snowman.v) {
         fx _snowman = this.o();
         MutableInt _snowmanx = new MutableInt(16700985);
         int _snowmanxx = (int)this.h.stream().filter(var1x -> _snowman.a(var1x.cA(), 48.0)).count();
         this.h.stream().filter(this::a).forEach(var4x -> {
            float _snowmanxxx = 1.0F;
            float _snowmanx = afm.a((var4x.cD() - (double)_snowman.u()) * (var4x.cD() - (double)_snowman.u()) + (var4x.cH() - (double)_snowman.w()) * (var4x.cH() - (double)_snowman.w()));
            double _snowmanxx = (double)((float)_snowman.u() + 0.5F) + (double)(1.0F / _snowmanx) * (var4x.cD() - (double)_snowman.u());
            double _snowmanxxx = (double)((float)_snowman.w() + 0.5F) + (double)(1.0F / _snowmanx) * (var4x.cH() - (double)_snowman.w());
            int _snowmanxxxx = afm.a((_snowman - 21) / -2, 3, 15);

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx; _snowmanxxxxx++) {
               int _snowmanxxxxxx = _snowman.addAndGet(5);
               double _snowmanxxxxxxx = (double)aez.a.b(_snowmanxxxxxx) / 255.0;
               double _snowmanxxxxxxxx = (double)aez.a.c(_snowmanxxxxxx) / 255.0;
               double _snowmanxxxxxxxxx = (double)aez.a.d(_snowmanxxxxxx) / 255.0;
               _snowman.a(hh.u, _snowmanxx, (double)((float)_snowman.v() + 0.5F), _snowmanxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            }
         });
      }
   }

   private boolean a(aqm var1) {
      return _snowman.aX() && !_snowman.y && this.o().a(_snowman.cA(), 48.0) && _snowman.X().a(aee.c);
   }

   private void b(aqm var1) {
      _snowman.c(new apu(apw.x, 60));
   }
}
