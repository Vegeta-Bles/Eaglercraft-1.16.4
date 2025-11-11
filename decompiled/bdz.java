import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class bdz extends aqn implements bdi {
   private static final us<Integer> bo = uv.a(bdz.class, uu.b);
   public float b;
   public float c;
   public float d;
   private boolean bp;

   public bdz(aqe<? extends bdz> var1, brx var2) {
      super(_snowman, _snowman);
      this.bh = new bdz.d(this);
   }

   @Override
   protected void o() {
      this.bk.a(1, new bdz.b(this));
      this.bk.a(2, new bdz.a(this));
      this.bk.a(3, new bdz.e(this));
      this.bk.a(5, new bdz.c(this));
      this.bl.a(1, new axq<>(this, bfw.class, 10, true, false, var1 -> Math.abs(var1.cE() - this.cE()) <= 4.0));
      this.bl.a(3, new axq<>(this, bai.class, true));
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bo, 1);
   }

   protected void a(int var1, boolean var2) {
      this.R.b(bo, _snowman);
      this.af();
      this.x_();
      this.a(arl.a).a((double)(_snowman * _snowman));
      this.a(arl.d).a((double)(0.2F + 0.1F * (float)_snowman));
      this.a(arl.f).a((double)_snowman);
      if (_snowman) {
         this.c(this.dx());
      }

      this.f = _snowman;
   }

   public int eP() {
      return this.R.a(bo);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Size", this.eP() - 1);
      _snowman.a("wasOnGround", this.bp);
   }

   @Override
   public void a(md var1) {
      int _snowman = _snowman.h("Size");
      if (_snowman < 0) {
         _snowman = 0;
      }

      this.a(_snowman + 1, false);
      super.a(_snowman);
      this.bp = _snowman.q("wasOnGround");
   }

   public boolean eQ() {
      return this.eP() <= 1;
   }

   protected hf eI() {
      return hh.J;
   }

   @Override
   protected boolean L() {
      return this.eP() > 0;
   }

   @Override
   public void j() {
      this.c = this.c + (this.b - this.c) * 0.5F;
      this.d = this.c;
      super.j();
      if (this.t && !this.bp) {
         int _snowman = this.eP();

         for (int _snowmanx = 0; _snowmanx < _snowman * 8; _snowmanx++) {
            float _snowmanxx = this.J.nextFloat() * (float) (Math.PI * 2);
            float _snowmanxxx = this.J.nextFloat() * 0.5F + 0.5F;
            float _snowmanxxxx = afm.a(_snowmanxx) * (float)_snowman * 0.5F * _snowmanxxx;
            float _snowmanxxxxx = afm.b(_snowmanxx) * (float)_snowman * 0.5F * _snowmanxxx;
            this.l.a(this.eI(), this.cD() + (double)_snowmanxxxx, this.cE(), this.cH() + (double)_snowmanxxxxx, 0.0, 0.0, 0.0);
         }

         this.a(this.eN(), this.dG(), ((this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F) / 0.8F);
         this.b = -0.5F;
      } else if (!this.t && this.bp) {
         this.b = 1.0F;
      }

      this.bp = this.t;
      this.eK();
   }

   protected void eK() {
      this.b *= 0.6F;
   }

   protected int eJ() {
      return this.J.nextInt(20) + 10;
   }

   @Override
   public void x_() {
      double _snowman = this.cD();
      double _snowmanx = this.cE();
      double _snowmanxx = this.cH();
      super.x_();
      this.d(_snowman, _snowmanx, _snowmanxx);
   }

   @Override
   public void a(us<?> var1) {
      if (bo.equals(_snowman)) {
         this.x_();
         this.p = this.aC;
         this.aA = this.aC;
         if (this.aE() && this.J.nextInt(20) == 0) {
            this.aM();
         }
      }

      super.a(_snowman);
   }

   @Override
   public aqe<? extends bdz> X() {
      return (aqe<? extends bdz>)super.X();
   }

   @Override
   public void ad() {
      int _snowman = this.eP();
      if (!this.l.v && _snowman > 1 && this.dl()) {
         nr _snowmanx = this.T();
         boolean _snowmanxx = this.eD();
         float _snowmanxxx = (float)_snowman / 4.0F;
         int _snowmanxxxx = _snowman / 2;
         int _snowmanxxxxx = 2 + this.J.nextInt(3);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx; _snowmanxxxxxx++) {
            float _snowmanxxxxxxx = ((float)(_snowmanxxxxxx % 2) - 0.5F) * _snowmanxxx;
            float _snowmanxxxxxxxx = ((float)(_snowmanxxxxxx / 2) - 0.5F) * _snowmanxxx;
            bdz _snowmanxxxxxxxxx = this.X().a(this.l);
            if (this.eu()) {
               _snowmanxxxxxxxxx.es();
            }

            _snowmanxxxxxxxxx.a(_snowmanx);
            _snowmanxxxxxxxxx.q(_snowmanxx);
            _snowmanxxxxxxxxx.m(this.bM());
            _snowmanxxxxxxxxx.a(_snowmanxxxx, true);
            _snowmanxxxxxxxxx.b(this.cD() + (double)_snowmanxxxxxxx, this.cE() + 0.5, this.cH() + (double)_snowmanxxxxxxxx, this.J.nextFloat() * 360.0F, 0.0F);
            this.l.c(_snowmanxxxxxxxxx);
         }
      }

      super.ad();
   }

   @Override
   public void i(aqa var1) {
      super.i(_snowman);
      if (_snowman instanceof bai && this.eL()) {
         this.i((aqm)_snowman);
      }
   }

   @Override
   public void a_(bfw var1) {
      if (this.eL()) {
         this.i((aqm)_snowman);
      }
   }

   protected void i(aqm var1) {
      if (this.aX()) {
         int _snowman = this.eP();
         if (this.h((aqa)_snowman) < 0.6 * (double)_snowman * 0.6 * (double)_snowman && this.D(_snowman) && _snowman.a(apk.c(this), this.eM())) {
            this.a(adq.nF, 1.0F, (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
            this.a(this, _snowman);
         }
      }
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 0.625F * _snowman.b;
   }

   protected boolean eL() {
      return !this.eQ() && this.dS();
   }

   protected float eM() {
      return (float)this.b(arl.f);
   }

   @Override
   protected adp e(apk var1) {
      return this.eQ() ? adq.ok : adq.nH;
   }

   @Override
   protected adp dq() {
      return this.eQ() ? adq.oj : adq.nG;
   }

   protected adp eN() {
      return this.eQ() ? adq.om : adq.nJ;
   }

   @Override
   protected vk J() {
      return this.eP() == 1 ? this.X().i() : cyq.a;
   }

   public static boolean c(aqe<bdz> var0, bry var1, aqp var2, fx var3, Random var4) {
      if (_snowman.ad() != aor.a) {
         if (Objects.equals(_snowman.i(_snowman), Optional.of(btb.g)) && _snowman.v() > 50 && _snowman.v() < 70 && _snowman.nextFloat() < 0.5F && _snowman.nextFloat() < _snowman.af() && _snowman.B(_snowman) <= _snowman.nextInt(8)
            )
          {
            return a(_snowman, _snowman, _snowman, _snowman, _snowman);
         }

         if (!(_snowman instanceof bsr)) {
            return false;
         }

         brd _snowman = new brd(_snowman);
         boolean _snowmanx = chx.a(_snowman.b, _snowman.c, ((bsr)_snowman).C(), 987234911L).nextInt(10) == 0;
         if (_snowman.nextInt(10) == 0 && _snowmanx && _snowman.v() < 40) {
            return a(_snowman, _snowman, _snowman, _snowman, _snowman);
         }
      }

      return false;
   }

   @Override
   protected float dG() {
      return 0.4F * (float)this.eP();
   }

   @Override
   public int O() {
      return 0;
   }

   protected boolean eR() {
      return this.eP() > 0;
   }

   @Override
   protected void dK() {
      dcn _snowman = this.cC();
      this.n(_snowman.b, (double)this.dJ(), _snowman.d);
      this.Z = true;
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      int _snowman = this.J.nextInt(3);
      if (_snowman < 2 && this.J.nextFloat() < 0.5F * _snowman.d()) {
         _snowman++;
      }

      int _snowmanx = 1 << _snowman;
      this.a(_snowmanx, true);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private float m() {
      float _snowman = this.eQ() ? 1.4F : 0.8F;
      return ((this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F) * _snowman;
   }

   protected adp eO() {
      return this.eQ() ? adq.ol : adq.nI;
   }

   @Override
   public aqb a(aqx var1) {
      return super.a(_snowman).a(0.255F * (float)this.eP());
   }

   static class a extends avv {
      private final bdz a;
      private int b;

      public a(bdz var1) {
         this.a = _snowman;
         this.a(EnumSet.of(avv.a.b));
      }

      @Override
      public boolean a() {
         aqm _snowman = this.a.A();
         if (_snowman == null) {
            return false;
         } else if (!_snowman.aX()) {
            return false;
         } else {
            return _snowman instanceof bfw && ((bfw)_snowman).bC.a ? false : this.a.u() instanceof bdz.d;
         }
      }

      @Override
      public void c() {
         this.b = 300;
         super.c();
      }

      @Override
      public boolean b() {
         aqm _snowman = this.a.A();
         if (_snowman == null) {
            return false;
         } else if (!_snowman.aX()) {
            return false;
         } else {
            return _snowman instanceof bfw && ((bfw)_snowman).bC.a ? false : --this.b > 0;
         }
      }

      @Override
      public void e() {
         this.a.a(this.a.A(), 10.0F, 10.0F);
         ((bdz.d)this.a.u()).a(this.a.p, this.a.eL());
      }
   }

   static class b extends avv {
      private final bdz a;

      public b(bdz var1) {
         this.a = _snowman;
         this.a(EnumSet.of(avv.a.c, avv.a.a));
         _snowman.x().d(true);
      }

      @Override
      public boolean a() {
         return (this.a.aE() || this.a.aQ()) && this.a.u() instanceof bdz.d;
      }

      @Override
      public void e() {
         if (this.a.cY().nextFloat() < 0.8F) {
            this.a.v().a();
         }

         ((bdz.d)this.a.u()).a(1.2);
      }
   }

   static class c extends avv {
      private final bdz a;

      public c(bdz var1) {
         this.a = _snowman;
         this.a(EnumSet.of(avv.a.c, avv.a.a));
      }

      @Override
      public boolean a() {
         return !this.a.br();
      }

      @Override
      public void e() {
         ((bdz.d)this.a.u()).a(1.0);
      }
   }

   static class d extends avb {
      private float i;
      private int j;
      private final bdz k;
      private boolean l;

      public d(bdz var1) {
         super(_snowman);
         this.k = _snowman;
         this.i = 180.0F * _snowman.p / (float) Math.PI;
      }

      public void a(float var1, boolean var2) {
         this.i = _snowman;
         this.l = _snowman;
      }

      public void a(double var1) {
         this.e = _snowman;
         this.h = avb.a.b;
      }

      @Override
      public void a() {
         this.a.p = this.a(this.a.p, this.i, 90.0F);
         this.a.aC = this.a.p;
         this.a.aA = this.a.p;
         if (this.h != avb.a.b) {
            this.a.t(0.0F);
         } else {
            this.h = avb.a.a;
            if (this.a.ao()) {
               this.a.q((float)(this.e * this.a.b(arl.d)));
               if (this.j-- <= 0) {
                  this.j = this.k.eJ();
                  if (this.l) {
                     this.j /= 3;
                  }

                  this.k.v().a();
                  if (this.k.eR()) {
                     this.k.a(this.k.eO(), this.k.dG(), this.k.m());
                  }
               } else {
                  this.k.aR = 0.0F;
                  this.k.aT = 0.0F;
                  this.a.q(0.0F);
               }
            } else {
               this.a.q((float)(this.e * this.a.b(arl.d)));
            }
         }
      }
   }

   static class e extends avv {
      private final bdz a;
      private float b;
      private int c;

      public e(bdz var1) {
         this.a = _snowman;
         this.a(EnumSet.of(avv.a.b));
      }

      @Override
      public boolean a() {
         return this.a.A() == null && (this.a.t || this.a.aE() || this.a.aQ() || this.a.a(apw.y)) && this.a.u() instanceof bdz.d;
      }

      @Override
      public void e() {
         if (--this.c <= 0) {
            this.c = 40 + this.a.cY().nextInt(60);
            this.b = (float)this.a.cY().nextInt(360);
         }

         ((bdz.d)this.a.u()).a(this.b, false);
      }
   }
}
