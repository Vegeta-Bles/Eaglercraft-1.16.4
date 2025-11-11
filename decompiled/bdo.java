import javax.annotation.Nullable;

public class bdo extends bea implements bdu {
   private int bo;
   private final dcn[][] bp;

   public bdo(aqe<? extends bdo> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 5;
      this.bp = new dcn[2][4];

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         this.bp[0][_snowman] = dcn.a;
         this.bp[1][_snowman] = dcn.a;
      }
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(0, new avp(this));
      this.bk.a(1, new bea.b());
      this.bk.a(4, new bdo.b());
      this.bk.a(5, new bdo.a());
      this.bk.a(6, new aww<>(this, 0.5, 20, 15.0F));
      this.bk.a(8, new awt(this, 0.6));
      this.bk.a(9, new awd(this, bfw.class, 3.0F, 1.0F));
      this.bk.a(10, new awd(this, aqn.class, 8.0F));
      this.bl.a(1, new axp(this, bhc.class).a());
      this.bl.a(2, new axq<>(this, bfw.class, true).a(300));
      this.bl.a(3, new axq<>(this, bfe.class, false).a(300));
      this.bl.a(3, new axq<>(this, bai.class, false).a(300));
   }

   public static ark.a eK() {
      return bdq.eR().a(arl.d, 0.5).a(arl.b, 18.0).a(arl.a, 32.0);
   }

   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.a(aqf.a, new bmb(bmd.kc));
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void e() {
      super.e();
   }

   @Override
   public dci cd() {
      return this.cc().c(3.0, 0.0, 3.0);
   }

   @Override
   public void k() {
      super.k();
      if (this.l.v && this.bF()) {
         this.bo--;
         if (this.bo < 0) {
            this.bo = 0;
         }

         if (this.an == 1 || this.K % 1200 == 0) {
            this.bo = 3;
            float _snowman = -6.0F;
            int _snowmanx = 13;

            for (int _snowmanxx = 0; _snowmanxx < 4; _snowmanxx++) {
               this.bp[0][_snowmanxx] = this.bp[1][_snowmanxx];
               this.bp[1][_snowmanxx] = new dcn(
                  (double)(-6.0F + (float)this.J.nextInt(13)) * 0.5,
                  (double)Math.max(0, this.J.nextInt(6) - 4),
                  (double)(-6.0F + (float)this.J.nextInt(13)) * 0.5
               );
            }

            for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
               this.l.a(hh.f, this.d(0.5), this.cF(), this.f(0.5), 0.0, 0.0, 0.0);
            }

            this.l.a(this.cD(), this.cE(), this.cH(), adq.gs, this.cu(), 1.0F, 1.0F, false);
         } else if (this.an == this.ao - 1) {
            this.bo = 3;

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.bp[0][_snowman] = this.bp[1][_snowman];
               this.bp[1][_snowman] = new dcn(0.0, 0.0, 0.0);
            }
         }
      }
   }

   @Override
   public adp eL() {
      return adq.go;
   }

   public dcn[] y(float var1) {
      if (this.bo <= 0) {
         return this.bp[1];
      } else {
         double _snowman = (double)(((float)this.bo - _snowman) / 3.0F);
         _snowman = Math.pow(_snowman, 0.25);
         dcn[] _snowmanx = new dcn[4];

         for (int _snowmanxx = 0; _snowmanxx < 4; _snowmanxx++) {
            _snowmanx[_snowmanxx] = this.bp[1][_snowmanxx].a(1.0 - _snowman).e(this.bp[0][_snowmanxx].a(_snowman));
         }

         return _snowmanx;
      }
   }

   @Override
   public boolean r(aqa var1) {
      if (super.r(_snowman)) {
         return true;
      } else {
         return _snowman instanceof aqm && ((aqm)_snowman).dC() == aqq.d ? this.bG() == null && _snowman.bG() == null : false;
      }
   }

   @Override
   protected adp I() {
      return adq.go;
   }

   @Override
   protected adp dq() {
      return adq.gq;
   }

   @Override
   protected adp e(apk var1) {
      return adq.gr;
   }

   @Override
   protected adp eM() {
      return adq.gp;
   }

   @Override
   public void a(int var1, boolean var2) {
   }

   @Override
   public void a(aqm var1, float var2) {
      bmb _snowman = this.f(this.b(bgn.a(this, bmd.kc)));
      bga _snowmanx = bgn.a(this, _snowman, _snowman);
      double _snowmanxx = _snowman.cD() - this.cD();
      double _snowmanxxx = _snowman.e(0.3333333333333333) - _snowmanx.cE();
      double _snowmanxxxx = _snowman.cH() - this.cH();
      double _snowmanxxxxx = (double)afm.a(_snowmanxx * _snowmanxx + _snowmanxxxx * _snowmanxxxx);
      _snowmanx.c(_snowmanxx, _snowmanxxx + _snowmanxxxxx * 0.2F, _snowmanxxxx, 1.6F, (float)(14 - this.l.ad().a() * 4));
      this.a(adq.nD, 1.0F, 1.0F / (this.cY().nextFloat() * 0.4F + 0.8F));
      this.l.c(_snowmanx);
   }

   @Override
   public bcy.a m() {
      if (this.eW()) {
         return bcy.a.c;
      } else {
         return this.eF() ? bcy.a.d : bcy.a.a;
      }
   }

   class a extends bea.c {
      private int e;

      private a() {
      }

      @Override
      public boolean a() {
         if (!super.a()) {
            return false;
         } else if (bdo.this.A() == null) {
            return false;
         } else {
            return bdo.this.A().Y() == this.e ? false : bdo.this.l.d(bdo.this.cB()).a((float)aor.c.ordinal());
         }
      }

      @Override
      public void c() {
         super.c();
         this.e = bdo.this.A().Y();
      }

      @Override
      protected int g() {
         return 20;
      }

      @Override
      protected int h() {
         return 180;
      }

      @Override
      protected void j() {
         bdo.this.A().c(new apu(apw.o, 400));
      }

      @Override
      protected adp k() {
         return adq.gt;
      }

      @Override
      protected bea.a l() {
         return bea.a.f;
      }
   }

   class b extends bea.c {
      private b() {
      }

      @Override
      public boolean a() {
         return !super.a() ? false : !bdo.this.a(apw.n);
      }

      @Override
      protected int g() {
         return 20;
      }

      @Override
      protected int h() {
         return 340;
      }

      @Override
      protected void j() {
         bdo.this.c(new apu(apw.n, 1200));
      }

      @Nullable
      @Override
      protected adp k() {
         return adq.gu;
      }

      @Override
      protected bea.a l() {
         return bea.a.e;
      }
   }
}
