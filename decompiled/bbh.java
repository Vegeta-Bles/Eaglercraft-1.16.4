import javax.annotation.Nullable;

public class bbh extends bbb {
   private final bbi bw = new bbi(this);
   private boolean bx;
   private int by;

   public bbh(aqe<? extends bbh> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public static ark.a eL() {
      return fi().a(arl.a, 15.0).a(arl.d, 0.2F);
   }

   @Override
   protected void eK() {
      this.a(arl.m).a(this.fq());
   }

   @Override
   protected void eV() {
   }

   @Override
   protected adp I() {
      super.I();
      return this.a(aef.b) ? adq.ny : adq.nu;
   }

   @Override
   protected adp dq() {
      super.dq();
      return adq.nv;
   }

   @Override
   protected adp e(apk var1) {
      super.e(_snowman);
      return adq.nw;
   }

   @Override
   protected adp av() {
      if (this.t) {
         if (!this.bs()) {
            return adq.nB;
         }

         this.bv++;
         if (this.bv > 5 && this.bv % 3 == 0) {
            return adq.nz;
         }

         if (this.bv <= 5) {
            return adq.nB;
         }
      }

      return adq.nx;
   }

   @Override
   protected void d(float var1) {
      if (this.t) {
         super.d(0.3F);
      } else {
         super.d(Math.min(0.1F, _snowman * 25.0F));
      }
   }

   @Override
   protected void fn() {
      if (this.aE()) {
         this.a(adq.nA, 0.4F, 1.0F);
      } else {
         super.fn();
      }
   }

   @Override
   public aqq dC() {
      return aqq.b;
   }

   @Override
   public double bc() {
      return super.bc() - 0.1875;
   }

   @Override
   public void k() {
      super.k();
      if (this.eM() && this.by++ >= 18000) {
         this.ad();
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("SkeletonTrap", this.eM());
      _snowman.b("SkeletonTrapTime", this.by);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.t(_snowman.q("SkeletonTrap"));
      this.by = _snowman.h("SkeletonTrapTime");
   }

   @Override
   public boolean bt() {
      return true;
   }

   @Override
   protected float dM() {
      return 0.96F;
   }

   public boolean eM() {
      return this.bx;
   }

   public void t(boolean var1) {
      if (_snowman != this.bx) {
         this.bx = _snowman;
         if (_snowman) {
            this.bk.a(1, this.bw);
         } else {
            this.bk.a(this.bw);
         }
      }
   }

   @Nullable
   @Override
   public apy a(aag var1, apy var2) {
      return aqe.aw.a(_snowman);
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (!this.eW()) {
         return aou.c;
      } else if (this.w_()) {
         return super.b(_snowman, _snowman);
      } else if (_snowman.eq()) {
         this.f(_snowman);
         return aou.a(this.l.v);
      } else if (this.bs()) {
         return super.b(_snowman, _snowman);
      } else {
         if (!_snowman.a()) {
            if (_snowman.b() == bmd.lO && !this.M_()) {
               this.f(_snowman);
               return aou.a(this.l.v);
            }

            aou _snowmanx = _snowman.a(_snowman, this, _snowman);
            if (_snowmanx.a()) {
               return _snowmanx;
            }
         }

         this.h(_snowman);
         return aou.a(this.l.v);
      }
   }
}
