import java.util.UUID;
import javax.annotation.Nullable;

public abstract class bgm extends aqa {
   private UUID b;
   private int c;
   private boolean d;

   bgm(aqe<? extends bgm> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public void b(@Nullable aqa var1) {
      if (_snowman != null) {
         this.b = _snowman.bS();
         this.c = _snowman.Y();
      }
   }

   @Nullable
   public aqa v() {
      if (this.b != null && this.l instanceof aag) {
         return ((aag)this.l).a(this.b);
      } else {
         return this.c != 0 ? this.l.a(this.c) : null;
      }
   }

   @Override
   protected void b(md var1) {
      if (this.b != null) {
         _snowman.a("Owner", this.b);
      }

      if (this.d) {
         _snowman.a("LeftOwner", true);
      }
   }

   @Override
   protected void a(md var1) {
      if (_snowman.b("Owner")) {
         this.b = _snowman.a("Owner");
      }

      this.d = _snowman.q("LeftOwner");
   }

   @Override
   public void j() {
      if (!this.d) {
         this.d = this.h();
      }

      super.j();
   }

   private boolean h() {
      aqa _snowman = this.v();
      if (_snowman != null) {
         for (aqa _snowmanx : this.l.a(this, this.cc().b(this.cC()).g(1.0), var0 -> !var0.a_() && var0.aT())) {
            if (_snowmanx.cr() == _snowman.cr()) {
               return false;
            }
         }
      }

      return true;
   }

   public void c(double var1, double var3, double var5, float var7, float var8) {
      dcn _snowman = new dcn(_snowman, _snowman, _snowman)
         .d()
         .b(this.J.nextGaussian() * 0.0075F * (double)_snowman, this.J.nextGaussian() * 0.0075F * (double)_snowman, this.J.nextGaussian() * 0.0075F * (double)_snowman)
         .a((double)_snowman);
      this.f(_snowman);
      float _snowmanx = afm.a(c(_snowman));
      this.p = (float)(afm.d(_snowman.b, _snowman.d) * 180.0F / (float)Math.PI);
      this.q = (float)(afm.d(_snowman.c, (double)_snowmanx) * 180.0F / (float)Math.PI);
      this.r = this.p;
      this.s = this.q;
   }

   public void a(aqa var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = -afm.a(_snowman * (float) (Math.PI / 180.0)) * afm.b(_snowman * (float) (Math.PI / 180.0));
      float _snowmanx = -afm.a((_snowman + _snowman) * (float) (Math.PI / 180.0));
      float _snowmanxx = afm.b(_snowman * (float) (Math.PI / 180.0)) * afm.b(_snowman * (float) (Math.PI / 180.0));
      this.c((double)_snowman, (double)_snowmanx, (double)_snowmanxx, _snowman, _snowman);
      dcn _snowmanxxx = _snowman.cC();
      this.f(this.cC().b(_snowmanxxx.b, _snowman.ao() ? 0.0 : _snowmanxxx.c, _snowmanxxx.d));
   }

   protected void a(dcl var1) {
      dcl.a _snowman = _snowman.c();
      if (_snowman == dcl.a.c) {
         this.a((dck)_snowman);
      } else if (_snowman == dcl.a.b) {
         this.a((dcj)_snowman);
      }
   }

   protected void a(dck var1) {
   }

   protected void a(dcj var1) {
      ceh _snowman = this.l.d_(_snowman.a());
      _snowman.a(this.l, _snowman, _snowman, this);
   }

   @Override
   public void k(double var1, double var3, double var5) {
      this.n(_snowman, _snowman, _snowman);
      if (this.s == 0.0F && this.r == 0.0F) {
         float _snowman = afm.a(_snowman * _snowman + _snowman * _snowman);
         this.q = (float)(afm.d(_snowman, (double)_snowman) * 180.0F / (float)Math.PI);
         this.p = (float)(afm.d(_snowman, _snowman) * 180.0F / (float)Math.PI);
         this.s = this.q;
         this.r = this.p;
         this.b(this.cD(), this.cE(), this.cH(), this.p, this.q);
      }
   }

   protected boolean a(aqa var1) {
      if (!_snowman.a_() && _snowman.aX() && _snowman.aT()) {
         aqa _snowman = this.v();
         return _snowman == null || this.d || !_snowman.x(_snowman);
      } else {
         return false;
      }
   }

   protected void x() {
      dcn _snowman = this.cC();
      float _snowmanx = afm.a(c(_snowman));
      this.q = e(this.s, (float)(afm.d(_snowman.c, (double)_snowmanx) * 180.0F / (float)Math.PI));
      this.p = e(this.r, (float)(afm.d(_snowman.b, _snowman.d) * 180.0F / (float)Math.PI));
   }

   protected static float e(float var0, float var1) {
      while (_snowman - _snowman < -180.0F) {
         _snowman -= 360.0F;
      }

      while (_snowman - _snowman >= 180.0F) {
         _snowman += 360.0F;
      }

      return afm.g(0.2F, _snowman, _snowman);
   }
}
