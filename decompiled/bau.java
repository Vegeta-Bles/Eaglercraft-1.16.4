import javax.annotation.Nullable;

public class bau extends azx implements arb, bdu {
   private static final us<Byte> b = uv.a(bau.class, uu.a);

   public bau(aqe<? extends bau> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      this.bk.a(1, new awv(this, 1.25, 20, 10.0F));
      this.bk.a(2, new axk(this, 1.0, 1.0000001E-5F));
      this.bk.a(3, new awd(this, bfw.class, 6.0F));
      this.bk.a(4, new aws(this));
      this.bl.a(1, new axq<>(this, aqn.class, 10, true, false, var0 -> var0 instanceof bdi));
   }

   public static ark.a m() {
      return aqn.p().a(arl.a, 4.0).a(arl.d, 0.2F);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, (byte)16);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("Pumpkin", this.eK());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.e("Pumpkin")) {
         this.t(_snowman.q("Pumpkin"));
      }
   }

   @Override
   public boolean dO() {
      return true;
   }

   @Override
   public void k() {
      super.k();
      if (!this.l.v) {
         int _snowman = afm.c(this.cD());
         int _snowmanx = afm.c(this.cE());
         int _snowmanxx = afm.c(this.cH());
         if (this.l.v(new fx(_snowman, 0, _snowmanxx)).a(new fx(_snowman, _snowmanx, _snowmanxx)) > 1.0F) {
            this.a(apk.c, 1.0F);
         }

         if (!this.l.V().b(brt.b)) {
            return;
         }

         ceh _snowmanxxx = bup.cC.n();

         for (int _snowmanxxxx = 0; _snowmanxxxx < 4; _snowmanxxxx++) {
            _snowman = afm.c(this.cD() + (double)((float)(_snowmanxxxx % 2 * 2 - 1) * 0.25F));
            _snowmanx = afm.c(this.cE());
            _snowmanxx = afm.c(this.cH() + (double)((float)(_snowmanxxxx / 2 % 2 * 2 - 1) * 0.25F));
            fx _snowmanxxxxx = new fx(_snowman, _snowmanx, _snowmanxx);
            if (this.l.d_(_snowmanxxxxx).g() && this.l.v(_snowmanxxxxx).a(_snowmanxxxxx) < 0.8F && _snowmanxxx.a((brz)this.l, _snowmanxxxxx)) {
               this.l.a(_snowmanxxxxx, _snowmanxxx);
            }
         }
      }
   }

   @Override
   public void a(aqm var1, float var2) {
      bgq _snowman = new bgq(this.l, this);
      double _snowmanx = _snowman.cG() - 1.1F;
      double _snowmanxx = _snowman.cD() - this.cD();
      double _snowmanxxx = _snowmanx - _snowman.cE();
      double _snowmanxxxx = _snowman.cH() - this.cH();
      float _snowmanxxxxx = afm.a(_snowmanxx * _snowmanxx + _snowmanxxxx * _snowmanxxxx) * 0.2F;
      _snowman.c(_snowmanxx, _snowmanxxx + (double)_snowmanxxxxx, _snowmanxxxx, 1.6F, 12.0F);
      this.a(adq.ov, 1.0F, 0.4F / (this.cY().nextFloat() * 0.4F + 0.8F));
      this.l.c(_snowman);
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 1.7F;
   }

   @Override
   protected aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() == bmd.ng && this.K_()) {
         this.a(adr.h);
         if (!this.l.v) {
            _snowman.a(1, _snowman, var1x -> var1x.d(_snowman));
         }

         return aou.a(this.l.v);
      } else {
         return aou.c;
      }
   }

   @Override
   public void a(adr var1) {
      this.l.a(null, this, adq.ow, _snowman, 1.0F, 1.0F);
      if (!this.l.s_()) {
         this.t(false);
         this.a(new bmb(bmd.dj), 1.7F);
      }
   }

   @Override
   public boolean K_() {
      return this.aX() && this.eK();
   }

   public boolean eK() {
      return (this.R.a(b) & 16) != 0;
   }

   public void t(boolean var1) {
      byte _snowman = this.R.a(b);
      if (_snowman) {
         this.R.b(b, (byte)(_snowman | 16));
      } else {
         this.R.b(b, (byte)(_snowman & -17));
      }
   }

   @Nullable
   @Override
   protected adp I() {
      return adq.os;
   }

   @Nullable
   @Override
   protected adp e(apk var1) {
      return adq.ou;
   }

   @Nullable
   @Override
   protected adp dq() {
      return adq.ot;
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, (double)(0.75F * this.ce()), (double)(this.cy() * 0.4F));
   }
}
