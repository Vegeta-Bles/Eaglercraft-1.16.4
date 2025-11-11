import java.util.List;
import java.util.UUID;

public class beg extends bhc implements bdu {
   private static final UUID b = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
   private static final arj bo = new arj(b, "Drinking speed penalty", -0.25, arj.a.a);
   private static final us<Boolean> bp = uv.a(beg.class, uu.i);
   private int bq;
   private axs<bhc> br;
   private axr<bfw> bs;

   public beg(aqe<? extends beg> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      super.o();
      this.br = new axs<>(this, bhc.class, true, var1 -> var1 != null && this.fb() && var1.X() != aqe.aS);
      this.bs = new axr<>(this, bfw.class, 10, true, false, null);
      this.bk.a(1, new avp(this));
      this.bk.a(2, new awv(this, 1.0, 60, 10.0F));
      this.bk.a(2, new axk(this, 1.0));
      this.bk.a(3, new awd(this, bfw.class, 8.0F));
      this.bk.a(3, new aws(this));
      this.bl.a(1, new axp(this, bhc.class));
      this.bl.a(2, this.br);
      this.bl.a(3, this.bs);
   }

   @Override
   protected void e() {
      super.e();
      this.ab().a(bp, false);
   }

   @Override
   protected adp I() {
      return adq.qK;
   }

   @Override
   protected adp e(apk var1) {
      return adq.qO;
   }

   @Override
   protected adp dq() {
      return adq.qM;
   }

   public void v(boolean var1) {
      this.ab().b(bp, _snowman);
   }

   public boolean m() {
      return this.ab().a(bp);
   }

   public static ark.a eK() {
      return bdq.eR().a(arl.a, 26.0).a(arl.d, 0.25);
   }

   @Override
   public void k() {
      if (!this.l.v && this.aX()) {
         this.br.j();
         if (this.br.h() <= 0) {
            this.bs.a(true);
         } else {
            this.bs.a(false);
         }

         if (this.m()) {
            if (this.bq-- <= 0) {
               this.v(false);
               bmb _snowman = this.dD();
               this.a(aqf.a, bmb.b);
               if (_snowman.b() == bmd.nv) {
                  List<apu> _snowmanx = bnv.a(_snowman);
                  if (_snowmanx != null) {
                     for (apu _snowmanxx : _snowmanx) {
                        this.c(new apu(_snowmanxx));
                     }
                  }
               }

               this.a(arl.d).d(bo);
            }
         } else {
            bnt _snowman = null;
            if (this.J.nextFloat() < 0.15F && this.a(aef.b) && !this.a(apw.m)) {
               _snowman = bnw.x;
            } else if (this.J.nextFloat() < 0.15F && (this.bq() || this.dm() != null && this.dm().p()) && !this.a(apw.l)) {
               _snowman = bnw.m;
            } else if (this.J.nextFloat() < 0.05F && this.dk() < this.dx()) {
               _snowman = bnw.z;
            } else if (this.J.nextFloat() < 0.5F && this.A() != null && !this.a(apw.a) && this.A().h(this) > 121.0) {
               _snowman = bnw.o;
            }

            if (_snowman != null) {
               this.a(aqf.a, bnv.a(new bmb(bmd.nv), _snowman));
               this.bq = this.dD().k();
               this.v(true);
               if (!this.aA()) {
                  this.l.a(null, this.cD(), this.cE(), this.cH(), adq.qN, this.cu(), 1.0F, 0.8F + this.J.nextFloat() * 0.4F);
               }

               arh _snowmanx = this.a(arl.d);
               _snowmanx.d(bo);
               _snowmanx.b(bo);
            }
         }

         if (this.J.nextFloat() < 7.5E-4F) {
            this.l.a(this, (byte)15);
         }
      }

      super.k();
   }

   @Override
   public adp eL() {
      return adq.qL;
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 15) {
         for (int _snowman = 0; _snowman < this.J.nextInt(35) + 10; _snowman++) {
            this.l
               .a(
                  hh.aa,
                  this.cD() + this.J.nextGaussian() * 0.13F,
                  this.cc().e + 0.5 + this.J.nextGaussian() * 0.13F,
                  this.cH() + this.J.nextGaussian() * 0.13F,
                  0.0,
                  0.0,
                  0.0
               );
         }
      } else {
         super.a(_snowman);
      }
   }

   @Override
   protected float d(apk var1, float var2) {
      _snowman = super.d(_snowman, _snowman);
      if (_snowman.k() == this) {
         _snowman = 0.0F;
      }

      if (_snowman.t()) {
         _snowman = (float)((double)_snowman * 0.15);
      }

      return _snowman;
   }

   @Override
   public void a(aqm var1, float var2) {
      if (!this.m()) {
         dcn _snowman = _snowman.cC();
         double _snowmanx = _snowman.cD() + _snowman.b - this.cD();
         double _snowmanxx = _snowman.cG() - 1.1F - this.cE();
         double _snowmanxxx = _snowman.cH() + _snowman.d - this.cH();
         float _snowmanxxxx = afm.a(_snowmanx * _snowmanx + _snowmanxxx * _snowmanxxx);
         bnt _snowmanxxxxx = bnw.B;
         if (_snowman instanceof bhc) {
            if (_snowman.dk() <= 4.0F) {
               _snowmanxxxxx = bnw.z;
            } else {
               _snowmanxxxxx = bnw.G;
            }

            this.h(null);
         } else if (_snowmanxxxx >= 8.0F && !_snowman.a(apw.b)) {
            _snowmanxxxxx = bnw.r;
         } else if (_snowman.dk() >= 8.0F && !_snowman.a(apw.s)) {
            _snowmanxxxxx = bnw.D;
         } else if (_snowmanxxxx <= 3.0F && !_snowman.a(apw.r) && this.J.nextFloat() < 0.25F) {
            _snowmanxxxxx = bnw.M;
         }

         bgx _snowmanxxxxxx = new bgx(this.l, this);
         _snowmanxxxxxx.b(bnv.a(new bmb(bmd.qj), _snowmanxxxxx));
         _snowmanxxxxxx.q -= -20.0F;
         _snowmanxxxxxx.c(_snowmanx, _snowmanxx + (double)(_snowmanxxxx * 0.2F), _snowmanxxx, 0.75F, 8.0F);
         if (!this.aA()) {
            this.l.a(null, this.cD(), this.cE(), this.cH(), adq.qP, this.cu(), 1.0F, 0.8F + this.J.nextFloat() * 0.4F);
         }

         this.l.c(_snowmanxxxxxx);
      }
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 1.62F;
   }

   @Override
   public void a(int var1, boolean var2) {
   }

   @Override
   public boolean eN() {
      return false;
   }
}
