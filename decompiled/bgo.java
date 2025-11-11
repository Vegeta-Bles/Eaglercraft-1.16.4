import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;

public class bgo extends bgm {
   private aqa b;
   @Nullable
   private gc c;
   private int d;
   private double e;
   private double f;
   private double g;
   @Nullable
   private UUID ag;

   public bgo(aqe<? extends bgo> var1, brx var2) {
      super(_snowman, _snowman);
      this.H = true;
   }

   public bgo(brx var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this(aqe.at, _snowman);
      this.b(_snowman, _snowman, _snowman, this.p, this.q);
      this.n(_snowman, _snowman, _snowman);
   }

   public bgo(brx var1, aqm var2, aqa var3, gc.a var4) {
      this(aqe.at, _snowman);
      this.b(_snowman);
      fx _snowman = _snowman.cB();
      double _snowmanx = (double)_snowman.u() + 0.5;
      double _snowmanxx = (double)_snowman.v() + 0.5;
      double _snowmanxxx = (double)_snowman.w() + 0.5;
      this.b(_snowmanx, _snowmanxx, _snowmanxxx, this.p, this.q);
      this.b = _snowman;
      this.c = gc.b;
      this.a(_snowman);
   }

   @Override
   public adr cu() {
      return adr.f;
   }

   @Override
   protected void b(md var1) {
      super.b(_snowman);
      if (this.b != null) {
         _snowman.a("Target", this.b.bS());
      }

      if (this.c != null) {
         _snowman.b("Dir", this.c.c());
      }

      _snowman.b("Steps", this.d);
      _snowman.a("TXD", this.e);
      _snowman.a("TYD", this.f);
      _snowman.a("TZD", this.g);
   }

   @Override
   protected void a(md var1) {
      super.a(_snowman);
      this.d = _snowman.h("Steps");
      this.e = _snowman.k("TXD");
      this.f = _snowman.k("TYD");
      this.g = _snowman.k("TZD");
      if (_snowman.c("Dir", 99)) {
         this.c = gc.a(_snowman.h("Dir"));
      }

      if (_snowman.b("Target")) {
         this.ag = _snowman.a("Target");
      }
   }

   @Override
   protected void e() {
   }

   private void a(@Nullable gc var1) {
      this.c = _snowman;
   }

   private void a(@Nullable gc.a var1) {
      double _snowman = 0.5;
      fx _snowmanx;
      if (this.b == null) {
         _snowmanx = this.cB().c();
      } else {
         _snowman = (double)this.b.cz() * 0.5;
         _snowmanx = new fx(this.b.cD(), this.b.cE() + _snowman, this.b.cH());
      }

      double _snowmanxx = (double)_snowmanx.u() + 0.5;
      double _snowmanxxx = (double)_snowmanx.v() + _snowman;
      double _snowmanxxxx = (double)_snowmanx.w() + 0.5;
      gc _snowmanxxxxx = null;
      if (!_snowmanx.a(this.cA(), 2.0)) {
         fx _snowmanxxxxxx = this.cB();
         List<gc> _snowmanxxxxxxx = Lists.newArrayList();
         if (_snowman != gc.a.a) {
            if (_snowmanxxxxxx.u() < _snowmanx.u() && this.l.w(_snowmanxxxxxx.g())) {
               _snowmanxxxxxxx.add(gc.f);
            } else if (_snowmanxxxxxx.u() > _snowmanx.u() && this.l.w(_snowmanxxxxxx.f())) {
               _snowmanxxxxxxx.add(gc.e);
            }
         }

         if (_snowman != gc.a.b) {
            if (_snowmanxxxxxx.v() < _snowmanx.v() && this.l.w(_snowmanxxxxxx.b())) {
               _snowmanxxxxxxx.add(gc.b);
            } else if (_snowmanxxxxxx.v() > _snowmanx.v() && this.l.w(_snowmanxxxxxx.c())) {
               _snowmanxxxxxxx.add(gc.a);
            }
         }

         if (_snowman != gc.a.c) {
            if (_snowmanxxxxxx.w() < _snowmanx.w() && this.l.w(_snowmanxxxxxx.e())) {
               _snowmanxxxxxxx.add(gc.d);
            } else if (_snowmanxxxxxx.w() > _snowmanx.w() && this.l.w(_snowmanxxxxxx.d())) {
               _snowmanxxxxxxx.add(gc.c);
            }
         }

         _snowmanxxxxx = gc.a(this.J);
         if (_snowmanxxxxxxx.isEmpty()) {
            for (int _snowmanxxxxxxxx = 5; !this.l.w(_snowmanxxxxxx.a(_snowmanxxxxx)) && _snowmanxxxxxxxx > 0; _snowmanxxxxxxxx--) {
               _snowmanxxxxx = gc.a(this.J);
            }
         } else {
            _snowmanxxxxx = _snowmanxxxxxxx.get(this.J.nextInt(_snowmanxxxxxxx.size()));
         }

         _snowmanxx = this.cD() + (double)_snowmanxxxxx.i();
         _snowmanxxx = this.cE() + (double)_snowmanxxxxx.j();
         _snowmanxxxx = this.cH() + (double)_snowmanxxxxx.k();
      }

      this.a(_snowmanxxxxx);
      double _snowmanxxxxxxxx = _snowmanxx - this.cD();
      double _snowmanxxxxxxxxx = _snowmanxxx - this.cE();
      double _snowmanxxxxxxxxxx = _snowmanxxxx - this.cH();
      double _snowmanxxxxxxxxxxx = (double)afm.a(_snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx);
      if (_snowmanxxxxxxxxxxx == 0.0) {
         this.e = 0.0;
         this.f = 0.0;
         this.g = 0.0;
      } else {
         this.e = _snowmanxxxxxxxx / _snowmanxxxxxxxxxxx * 0.15;
         this.f = _snowmanxxxxxxxxx / _snowmanxxxxxxxxxxx * 0.15;
         this.g = _snowmanxxxxxxxxxx / _snowmanxxxxxxxxxxx * 0.15;
      }

      this.Z = true;
      this.d = 10 + this.J.nextInt(5) * 10;
   }

   @Override
   public void cI() {
      if (this.l.ad() == aor.a) {
         this.ad();
      }
   }

   @Override
   public void j() {
      super.j();
      if (!this.l.v) {
         if (this.b == null && this.ag != null) {
            this.b = ((aag)this.l).a(this.ag);
            if (this.b == null) {
               this.ag = null;
            }
         }

         if (this.b == null || !this.b.aX() || this.b instanceof bfw && ((bfw)this.b).a_()) {
            if (!this.aB()) {
               this.f(this.cC().b(0.0, -0.04, 0.0));
            }
         } else {
            this.e = afm.a(this.e * 1.025, -1.0, 1.0);
            this.f = afm.a(this.f * 1.025, -1.0, 1.0);
            this.g = afm.a(this.g * 1.025, -1.0, 1.0);
            dcn _snowman = this.cC();
            this.f(_snowman.b((this.e - _snowman.b) * 0.2, (this.f - _snowman.c) * 0.2, (this.g - _snowman.d) * 0.2));
         }

         dcl _snowman = bgn.a(this, this::a);
         if (_snowman.c() != dcl.a.a) {
            this.a(_snowman);
         }
      }

      this.ay();
      dcn _snowman = this.cC();
      this.d(this.cD() + _snowman.b, this.cE() + _snowman.c, this.cH() + _snowman.d);
      bgn.a(this, 0.5F);
      if (this.l.v) {
         this.l.a(hh.t, this.cD() - _snowman.b, this.cE() - _snowman.c + 0.15, this.cH() - _snowman.d, 0.0, 0.0, 0.0);
      } else if (this.b != null && !this.b.y) {
         if (this.d > 0) {
            this.d--;
            if (this.d == 0) {
               this.a(this.c == null ? null : this.c.n());
            }
         }

         if (this.c != null) {
            fx _snowmanx = this.cB();
            gc.a _snowmanxx = this.c.n();
            if (this.l.a(_snowmanx.a(this.c), this)) {
               this.a(_snowmanxx);
            } else {
               fx _snowmanxxx = this.b.cB();
               if (_snowmanxx == gc.a.a && _snowmanx.u() == _snowmanxxx.u() || _snowmanxx == gc.a.c && _snowmanx.w() == _snowmanxxx.w() || _snowmanxx == gc.a.b && _snowmanx.v() == _snowmanxxx.v()) {
                  this.a(_snowmanxx);
               }
            }
         }
      }
   }

   @Override
   protected boolean a(aqa var1) {
      return super.a(_snowman) && !_snowman.H;
   }

   @Override
   public boolean bq() {
      return false;
   }

   @Override
   public boolean a(double var1) {
      return _snowman < 16384.0;
   }

   @Override
   public float aR() {
      return 1.0F;
   }

   @Override
   protected void a(dck var1) {
      super.a(_snowman);
      aqa _snowman = _snowman.a();
      aqa _snowmanx = this.v();
      aqm _snowmanxx = _snowmanx instanceof aqm ? (aqm)_snowmanx : null;
      boolean _snowmanxxx = _snowman.a(apk.a(this, _snowmanxx).c(), 4.0F);
      if (_snowmanxxx) {
         this.a(_snowmanxx, _snowman);
         if (_snowman instanceof aqm) {
            ((aqm)_snowman).c(new apu(apw.y, 200));
         }
      }
   }

   @Override
   protected void a(dcj var1) {
      super.a(_snowman);
      ((aag)this.l).a(hh.w, this.cD(), this.cE(), this.cH(), 2, 0.2, 0.2, 0.2, 0.0);
      this.a(adq.nf, 1.0F, 1.0F);
   }

   @Override
   protected void a(dcl var1) {
      super.a(_snowman);
      this.ad();
   }

   @Override
   public boolean aT() {
      return true;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (!this.l.v) {
         this.a(adq.ng, 1.0F, 1.0F);
         ((aag)this.l).a(hh.g, this.cD(), this.cE(), this.cH(), 15, 0.2, 0.2, 0.2, 0.0);
         this.ad();
      }

      return true;
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }
}
