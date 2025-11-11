import java.util.Collection;

public class bdc extends bdq implements aqy {
   private static final us<Integer> b = uv.a(bdc.class, uu.b);
   private static final us<Boolean> c = uv.a(bdc.class, uu.i);
   private static final us<Boolean> d = uv.a(bdc.class, uu.i);
   private int bo;
   private int bp;
   private int bq = 30;
   private int br = 3;
   private int bs;

   public bdc(aqe<? extends bdc> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      this.bk.a(1, new avp(this));
      this.bk.a(2, new axd(this));
      this.bk.a(3, new avd<>(this, bak.class, 6.0F, 1.0, 1.2));
      this.bk.a(3, new avd<>(this, bab.class, 6.0F, 1.0, 1.2));
      this.bk.a(4, new awf(this, 1.0, false));
      this.bk.a(5, new axk(this, 0.8));
      this.bk.a(6, new awd(this, bfw.class, 8.0F));
      this.bk.a(6, new aws(this));
      this.bl.a(1, new axq<>(this, bfw.class, true));
      this.bl.a(2, new axp(this));
   }

   public static ark.a m() {
      return bdq.eR().a(arl.d, 0.25);
   }

   @Override
   public int bP() {
      return this.A() == null ? 3 : 3 + (int)(this.dk() - 1.0F);
   }

   @Override
   public boolean b(float var1, float var2) {
      boolean _snowman = super.b(_snowman, _snowman);
      this.bp = (int)((float)this.bp + _snowman * 1.5F);
      if (this.bp > this.bq - 5) {
         this.bp = this.bq - 5;
      }

      return _snowman;
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, -1);
      this.R.a(c, false);
      this.R.a(d, false);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.R.a(c)) {
         _snowman.a("powered", true);
      }

      _snowman.a("Fuse", (short)this.bq);
      _snowman.a("ExplosionRadius", (byte)this.br);
      _snowman.a("ignited", this.eL());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.R.b(c, _snowman.q("powered"));
      if (_snowman.c("Fuse", 99)) {
         this.bq = _snowman.g("Fuse");
      }

      if (_snowman.c("ExplosionRadius", 99)) {
         this.br = _snowman.f("ExplosionRadius");
      }

      if (_snowman.q("ignited")) {
         this.eM();
      }
   }

   @Override
   public void j() {
      if (this.aX()) {
         this.bo = this.bp;
         if (this.eL()) {
            this.a(1);
         }

         int _snowman = this.eK();
         if (_snowman > 0 && this.bp == 0) {
            this.a(adq.cp, 1.0F, 0.5F);
         }

         this.bp += _snowman;
         if (this.bp < 0) {
            this.bp = 0;
         }

         if (this.bp >= this.bq) {
            this.bp = this.bq;
            this.eP();
         }
      }

      super.j();
   }

   @Override
   protected adp e(apk var1) {
      return adq.co;
   }

   @Override
   protected adp dq() {
      return adq.cn;
   }

   @Override
   protected void a(apk var1, int var2, boolean var3) {
      super.a(_snowman, _snowman, _snowman);
      aqa _snowman = _snowman.k();
      if (_snowman != this && _snowman instanceof bdc) {
         bdc _snowmanx = (bdc)_snowman;
         if (_snowmanx.eN()) {
            _snowmanx.eO();
            this.a(bmd.pi);
         }
      }
   }

   @Override
   public boolean B(aqa var1) {
      return true;
   }

   @Override
   public boolean S_() {
      return this.R.a(c);
   }

   public float y(float var1) {
      return afm.g(_snowman, (float)this.bo, (float)this.bp) / (float)(this.bq - 2);
   }

   public int eK() {
      return this.R.a(b);
   }

   public void a(int var1) {
      this.R.b(b, _snowman);
   }

   @Override
   public void a(aag var1, aql var2) {
      super.a(_snowman, _snowman);
      this.R.b(c, true);
   }

   @Override
   protected aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() == bmd.ka) {
         this.l.a(_snowman, this.cD(), this.cE(), this.cH(), adq.eo, this.cu(), 1.0F, this.J.nextFloat() * 0.4F + 0.8F);
         if (!this.l.v) {
            this.eM();
            _snowman.a(1, _snowman, var1x -> var1x.d(_snowman));
         }

         return aou.a(this.l.v);
      } else {
         return super.b(_snowman, _snowman);
      }
   }

   private void eP() {
      if (!this.l.v) {
         brp.a _snowman = this.l.V().b(brt.b) ? brp.a.c : brp.a.a;
         float _snowmanx = this.S_() ? 2.0F : 1.0F;
         this.aH = true;
         this.l.a(this, this.cD(), this.cE(), this.cH(), (float)this.br * _snowmanx, _snowman);
         this.ad();
         this.eS();
      }
   }

   private void eS() {
      Collection<apu> _snowman = this.dh();
      if (!_snowman.isEmpty()) {
         apz _snowmanx = new apz(this.l, this.cD(), this.cE(), this.cH());
         _snowmanx.a(2.5F);
         _snowmanx.b(-0.5F);
         _snowmanx.d(10);
         _snowmanx.b(_snowmanx.m() / 2);
         _snowmanx.c(-_snowmanx.g() / (float)_snowmanx.m());

         for (apu _snowmanxx : _snowman) {
            _snowmanx.a(new apu(_snowmanxx));
         }

         this.l.c(_snowmanx);
      }
   }

   public boolean eL() {
      return this.R.a(d);
   }

   public void eM() {
      this.R.b(d, true);
   }

   public boolean eN() {
      return this.S_() && this.bs < 1;
   }

   public void eO() {
      this.bs++;
   }
}
