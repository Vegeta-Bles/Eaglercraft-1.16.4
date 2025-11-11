import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class dzn extends dzj {
   public dzn(dwt var1, GameProfile var2) {
      super(_snowman, _snowman);
      this.G = 1.0F;
      this.H = true;
   }

   @Override
   public boolean a(double var1) {
      double _snowman = this.cc().a() * 10.0;
      if (Double.isNaN(_snowman)) {
         _snowman = 1.0;
      }

      _snowman *= 64.0 * bW();
      return _snowman < _snowman * _snowman;
   }

   @Override
   public boolean a(apk var1, float var2) {
      return true;
   }

   @Override
   public void j() {
      super.j();
      this.a(this, false);
   }

   @Override
   public void k() {
      if (this.aU > 0) {
         double _snowman = this.cD() + (this.aV - this.cD()) / (double)this.aU;
         double _snowmanx = this.cE() + (this.aW - this.cE()) / (double)this.aU;
         double _snowmanxx = this.cH() + (this.aX - this.cH()) / (double)this.aU;
         this.p = (float)((double)this.p + afm.g(this.aY - (double)this.p) / (double)this.aU);
         this.q = (float)((double)this.q + (this.aZ - (double)this.q) / (double)this.aU);
         this.aU--;
         this.d(_snowman, _snowmanx, _snowmanxx);
         this.a(this.p, this.q);
      }

      if (this.bb > 0) {
         this.aC = (float)((double)this.aC + afm.g(this.ba - (double)this.aC) / (double)this.bb);
         this.bb--;
      }

      this.bs = this.bt;
      this.dA();
      float _snowman;
      if (this.t && !this.dl()) {
         _snowman = Math.min(0.1F, afm.a(c(this.cC())));
      } else {
         _snowman = 0.0F;
      }

      if (!this.t && !this.dl()) {
         float _snowmanx = (float)Math.atan(-this.cC().c * 0.2F) * 15.0F;
      } else {
         float _snowmanx = 0.0F;
      }

      this.bt = this.bt + (_snowman - this.bt) * 0.4F;
      this.l.Z().a("push");
      this.dQ();
      this.l.Z().c();
   }

   @Override
   protected void eu() {
   }

   @Override
   public void a(nr var1, UUID var2) {
      djz _snowman = djz.C();
      if (!_snowman.a(_snowman)) {
         _snowman.j.c().a(_snowman);
      }
   }
}
