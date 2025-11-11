import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bal extends azz {
   private static final us<Integer> bp = uv.a(bal.class, uu.b);
   private static final us<Integer> bq = uv.a(bal.class, uu.b);
   private static final us<Integer> br = uv.a(bal.class, uu.b);
   private static final us<Byte> bs = uv.a(bal.class, uu.a);
   private static final us<Byte> bt = uv.a(bal.class, uu.a);
   private static final us<Byte> bu = uv.a(bal.class, uu.a);
   private static final azg bv = new azg().a(8.0).b().a();
   private boolean bw;
   private boolean bx;
   public int bo;
   private dcn by;
   private float bz;
   private float bA;
   private float bB;
   private float bC;
   private float bD;
   private float bE;
   private bal.g bF;
   private static final Predicate<bcv> bG = var0 -> {
      blx _snowman = var0.g().b();
      return (_snowman == bup.kY.h() || _snowman == bup.cW.h()) && var0.aX() && !var0.p();
   };

   public bal(aqe<? extends bal> var1, brx var2) {
      super(_snowman, _snowman);
      this.bh = new bal.h(this);
      if (!this.w_()) {
         this.p(true);
      }
   }

   @Override
   public boolean e(bmb var1) {
      aqf _snowman = aqn.j(_snowman);
      return !this.b(_snowman).a() ? false : _snowman == aqf.a && super.e(_snowman);
   }

   public int eK() {
      return this.R.a(bp);
   }

   public void t(int var1) {
      this.R.b(bp, _snowman);
   }

   public boolean eL() {
      return this.w(2);
   }

   public boolean eM() {
      return this.w(8);
   }

   public void t(boolean var1) {
      this.d(8, _snowman);
   }

   public boolean eN() {
      return this.w(16);
   }

   public void u(boolean var1) {
      this.d(16, _snowman);
   }

   public boolean eO() {
      return this.R.a(br) > 0;
   }

   public void v(boolean var1) {
      this.R.b(br, _snowman ? 1 : 0);
   }

   private int fk() {
      return this.R.a(br);
   }

   private void v(int var1) {
      this.R.b(br, _snowman);
   }

   public void w(boolean var1) {
      this.d(2, _snowman);
      if (!_snowman) {
         this.u(0);
      }
   }

   public int eU() {
      return this.R.a(bq);
   }

   public void u(int var1) {
      this.R.b(bq, _snowman);
   }

   public bal.a eV() {
      return bal.a.a(this.R.a(bs));
   }

   public void a(bal.a var1) {
      if (_snowman.a() > 6) {
         _snowman = bal.a.a(this.J);
      }

      this.R.b(bs, (byte)_snowman.a());
   }

   public bal.a eW() {
      return bal.a.a(this.R.a(bt));
   }

   public void b(bal.a var1) {
      if (_snowman.a() > 6) {
         _snowman = bal.a.a(this.J);
      }

      this.R.b(bt, (byte)_snowman.a());
   }

   public boolean eX() {
      return this.w(4);
   }

   public void x(boolean var1) {
      this.d(4, _snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bp, 0);
      this.R.a(bq, 0);
      this.R.a(bs, (byte)0);
      this.R.a(bt, (byte)0);
      this.R.a(bu, (byte)0);
      this.R.a(br, 0);
   }

   private boolean w(int var1) {
      return (this.R.a(bu) & _snowman) != 0;
   }

   private void d(int var1, boolean var2) {
      byte _snowman = this.R.a(bu);
      if (_snowman) {
         this.R.b(bu, (byte)(_snowman | _snowman));
      } else {
         this.R.b(bu, (byte)(_snowman & ~_snowman));
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("MainGene", this.eV().b());
      _snowman.a("HiddenGene", this.eW().b());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.a(bal.a.a(_snowman.l("MainGene")));
      this.b(bal.a.a(_snowman.l("HiddenGene")));
   }

   @Nullable
   @Override
   public apy a(aag var1, apy var2) {
      bal _snowman = aqe.ae.a(_snowman);
      if (_snowman instanceof bal) {
         _snowman.a(this, (bal)_snowman);
      }

      _snowman.fg();
      return _snowman;
   }

   @Override
   protected void o() {
      this.bk.a(0, new avp(this));
      this.bk.a(2, new bal.i(this, 2.0));
      this.bk.a(2, new bal.d(this, 1.0));
      this.bk.a(3, new bal.b(this, 1.2F, true));
      this.bk.a(4, new axf(this, 1.0, bon.a(bup.kY.h()), false));
      this.bk.a(6, new bal.c<>(this, bfw.class, 8.0F, 2.0, 2.0));
      this.bk.a(6, new bal.c<>(this, bdq.class, 4.0F, 2.0, 2.0));
      this.bk.a(7, new bal.k());
      this.bk.a(8, new bal.f(this));
      this.bk.a(8, new bal.l(this));
      this.bF = new bal.g(this, bfw.class, 6.0F);
      this.bk.a(9, this.bF);
      this.bk.a(10, new aws(this));
      this.bk.a(12, new bal.j(this));
      this.bk.a(13, new avu(this, 1.25));
      this.bk.a(14, new axk(this, 1.0));
      this.bl.a(1, new bal.e(this).a(new Class[0]));
   }

   public static ark.a eY() {
      return aqn.p().a(arl.d, 0.15F).a(arl.f, 6.0);
   }

   public bal.a eZ() {
      return bal.a.b(this.eV(), this.eW());
   }

   public boolean fa() {
      return this.eZ() == bal.a.b;
   }

   public boolean fb() {
      return this.eZ() == bal.a.c;
   }

   public boolean fc() {
      return this.eZ() == bal.a.d;
   }

   public boolean fe() {
      return this.eZ() == bal.a.f;
   }

   @Override
   public boolean eF() {
      return this.eZ() == bal.a.g;
   }

   @Override
   public boolean a(bfw var1) {
      return false;
   }

   @Override
   public boolean B(aqa var1) {
      this.a(adq.jU, 1.0F, 1.0F);
      if (!this.eF()) {
         this.bx = true;
      }

      return super.B(_snowman);
   }

   @Override
   public void j() {
      super.j();
      if (this.fb()) {
         if (this.l.W() && !this.aE()) {
            this.t(true);
            this.v(false);
         } else if (!this.eO()) {
            this.t(false);
         }
      }

      if (this.A() == null) {
         this.bw = false;
         this.bx = false;
      }

      if (this.eK() > 0) {
         if (this.A() != null) {
            this.a(this.A(), 90.0F, 90.0F);
         }

         if (this.eK() == 29 || this.eK() == 14) {
            this.a(adq.jQ, 1.0F, 1.0F);
         }

         this.t(this.eK() - 1);
      }

      if (this.eL()) {
         this.u(this.eU() + 1);
         if (this.eU() > 20) {
            this.w(false);
            this.fr();
         } else if (this.eU() == 1) {
            this.a(adq.jK, 1.0F, 1.0F);
         }
      }

      if (this.eX()) {
         this.fq();
      } else {
         this.bo = 0;
      }

      if (this.eM()) {
         this.q = 0.0F;
      }

      this.fn();
      this.fl();
      this.fo();
      this.fp();
   }

   public boolean ff() {
      return this.fb() && this.l.W();
   }

   private void fl() {
      if (!this.eO() && this.eM() && !this.ff() && !this.b(aqf.a).a() && this.J.nextInt(80) == 1) {
         this.v(true);
      } else if (this.b(aqf.a).a() || !this.eM()) {
         this.v(false);
      }

      if (this.eO()) {
         this.fm();
         if (!this.l.v && this.fk() > 80 && this.J.nextInt(20) == 1) {
            if (this.fk() > 100 && this.l(this.b(aqf.a))) {
               if (!this.l.v) {
                  this.a(aqf.a, bmb.b);
               }

               this.t(false);
            }

            this.v(false);
            return;
         }

         this.v(this.fk() + 1);
      }
   }

   private void fm() {
      if (this.fk() % 5 == 0) {
         this.a(adq.jO, 0.5F + 0.5F * (float)this.J.nextInt(2), (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);

         for (int _snowman = 0; _snowman < 6; _snowman++) {
            dcn _snowmanx = new dcn(((double)this.J.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double)this.J.nextFloat() - 0.5) * 0.1);
            _snowmanx = _snowmanx.a(-this.q * (float) (Math.PI / 180.0));
            _snowmanx = _snowmanx.b(-this.p * (float) (Math.PI / 180.0));
            double _snowmanxx = (double)(-this.J.nextFloat()) * 0.6 - 0.3;
            dcn _snowmanxxx = new dcn(((double)this.J.nextFloat() - 0.5) * 0.8, _snowmanxx, 1.0 + ((double)this.J.nextFloat() - 0.5) * 0.4);
            _snowmanxxx = _snowmanxxx.b(-this.aA * (float) (Math.PI / 180.0));
            _snowmanxxx = _snowmanxxx.b(this.cD(), this.cG() + 1.0, this.cH());
            this.l.a(new he(hh.I, this.b(aqf.a)), _snowmanxxx.b, _snowmanxxx.c, _snowmanxxx.d, _snowmanx.b, _snowmanx.c + 0.05, _snowmanx.d);
         }
      }
   }

   private void fn() {
      this.bA = this.bz;
      if (this.eM()) {
         this.bz = Math.min(1.0F, this.bz + 0.15F);
      } else {
         this.bz = Math.max(0.0F, this.bz - 0.19F);
      }
   }

   private void fo() {
      this.bC = this.bB;
      if (this.eN()) {
         this.bB = Math.min(1.0F, this.bB + 0.15F);
      } else {
         this.bB = Math.max(0.0F, this.bB - 0.19F);
      }
   }

   private void fp() {
      this.bE = this.bD;
      if (this.eX()) {
         this.bD = Math.min(1.0F, this.bD + 0.15F);
      } else {
         this.bD = Math.max(0.0F, this.bD - 0.19F);
      }
   }

   public float y(float var1) {
      return afm.g(_snowman, this.bA, this.bz);
   }

   public float z(float var1) {
      return afm.g(_snowman, this.bC, this.bB);
   }

   public float A(float var1) {
      return afm.g(_snowman, this.bE, this.bD);
   }

   private void fq() {
      this.bo++;
      if (this.bo > 32) {
         this.x(false);
      } else {
         if (!this.l.v) {
            dcn _snowman = this.cC();
            if (this.bo == 1) {
               float _snowmanx = this.p * (float) (Math.PI / 180.0);
               float _snowmanxx = this.w_() ? 0.1F : 0.2F;
               this.by = new dcn(_snowman.b + (double)(-afm.a(_snowmanx) * _snowmanxx), 0.0, _snowman.d + (double)(afm.b(_snowmanx) * _snowmanxx));
               this.f(this.by.b(0.0, 0.27, 0.0));
            } else if ((float)this.bo != 7.0F && (float)this.bo != 15.0F && (float)this.bo != 23.0F) {
               this.n(this.by.b, _snowman.c, this.by.d);
            } else {
               this.n(0.0, this.t ? 0.27 : _snowman.c, 0.0);
            }
         }
      }
   }

   private void fr() {
      dcn _snowman = this.cC();
      this.l
         .a(
            hh.T,
            this.cD() - (double)(this.cy() + 1.0F) * 0.5 * (double)afm.a(this.aA * (float) (Math.PI / 180.0)),
            this.cG() - 0.1F,
            this.cH() + (double)(this.cy() + 1.0F) * 0.5 * (double)afm.b(this.aA * (float) (Math.PI / 180.0)),
            _snowman.b,
            0.0,
            _snowman.d
         );
      this.a(adq.jL, 1.0F, 1.0F);

      for (bal _snowmanx : this.l.a(bal.class, this.cc().g(10.0))) {
         if (!_snowmanx.w_() && _snowmanx.t && !_snowmanx.aE() && _snowmanx.fh()) {
            _snowmanx.dK();
         }
      }

      if (!this.l.s_() && this.J.nextInt(700) == 0 && this.l.V().b(brt.e)) {
         this.a(bmd.md);
      }
   }

   @Override
   protected void b(bcv var1) {
      if (this.b(aqf.a).a() && bG.test(_snowman)) {
         this.a(_snowman);
         bmb _snowman = _snowman.g();
         this.a(aqf.a, _snowman);
         this.bm[aqf.a.b()] = 2.0F;
         this.a(_snowman, _snowman.E());
         _snowman.ad();
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      this.t(false);
      return super.a(_snowman, _snowman);
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.a(bal.a.a(this.J));
      this.b(bal.a.a(this.J));
      this.fg();
      if (_snowman == null) {
         _snowman = new apy.a(0.2F);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void a(bal var1, @Nullable bal var2) {
      if (_snowman == null) {
         if (this.J.nextBoolean()) {
            this.a(_snowman.fs());
            this.b(bal.a.a(this.J));
         } else {
            this.a(bal.a.a(this.J));
            this.b(_snowman.fs());
         }
      } else if (this.J.nextBoolean()) {
         this.a(_snowman.fs());
         this.b(_snowman.fs());
      } else {
         this.a(_snowman.fs());
         this.b(_snowman.fs());
      }

      if (this.J.nextInt(32) == 0) {
         this.a(bal.a.a(this.J));
      }

      if (this.J.nextInt(32) == 0) {
         this.b(bal.a.a(this.J));
      }
   }

   private bal.a fs() {
      return this.J.nextBoolean() ? this.eV() : this.eW();
   }

   public void fg() {
      if (this.fe()) {
         this.a(arl.a).a(10.0);
      }

      if (this.fa()) {
         this.a(arl.d).a(0.07F);
      }
   }

   private void ft() {
      if (!this.aE()) {
         this.t(0.0F);
         this.x().o();
         this.t(true);
      }
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (this.ff()) {
         return aou.c;
      } else if (this.eN()) {
         this.u(false);
         return aou.a(this.l.v);
      } else if (this.k(_snowman)) {
         if (this.A() != null) {
            this.bw = true;
         }

         if (this.w_()) {
            this.a(_snowman, _snowman);
            this.a((int)((float)(-this.i() / 20) * 0.1F), true);
         } else if (!this.l.v && this.i() == 0 && this.eP()) {
            this.a(_snowman, _snowman);
            this.g(_snowman);
         } else {
            if (this.l.v || this.eM() || this.aE()) {
               return aou.c;
            }

            this.ft();
            this.v(true);
            bmb _snowmanx = this.b(aqf.a);
            if (!_snowmanx.a() && !_snowman.bC.d) {
               this.a(_snowmanx);
            }

            this.a(aqf.a, new bmb(_snowman.b(), 1));
            this.a(_snowman, _snowman);
         }

         return aou.a;
      } else {
         return aou.c;
      }
   }

   @Nullable
   @Override
   protected adp I() {
      if (this.eF()) {
         return adq.jR;
      } else {
         return this.fb() ? adq.jS : adq.jM;
      }
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.jP, 0.15F, 1.0F);
   }

   @Override
   public boolean k(bmb var1) {
      return _snowman.b() == bup.kY.h();
   }

   private boolean l(bmb var1) {
      return this.k(_snowman) || _snowman.b() == bup.cW.h();
   }

   @Nullable
   @Override
   protected adp dq() {
      return adq.jN;
   }

   @Nullable
   @Override
   protected adp e(apk var1) {
      return adq.jT;
   }

   public boolean fh() {
      return !this.eN() && !this.ff() && !this.eO() && !this.eX() && !this.eM();
   }

   public static enum a {
      a(0, "normal", false),
      b(1, "lazy", false),
      c(2, "worried", false),
      d(3, "playful", false),
      e(4, "brown", true),
      f(5, "weak", true),
      g(6, "aggressive", false);

      private static final bal.a[] h = Arrays.stream(values()).sorted(Comparator.comparingInt(bal.a::a)).toArray(bal.a[]::new);
      private final int i;
      private final String j;
      private final boolean k;

      private a(int var3, String var4, boolean var5) {
         this.i = _snowman;
         this.j = _snowman;
         this.k = _snowman;
      }

      public int a() {
         return this.i;
      }

      public String b() {
         return this.j;
      }

      public boolean c() {
         return this.k;
      }

      private static bal.a b(bal.a var0, bal.a var1) {
         if (_snowman.c()) {
            return _snowman == _snowman ? _snowman : a;
         } else {
            return _snowman;
         }
      }

      public static bal.a a(int var0) {
         if (_snowman < 0 || _snowman >= h.length) {
            _snowman = 0;
         }

         return h[_snowman];
      }

      public static bal.a a(String var0) {
         for (bal.a _snowman : values()) {
            if (_snowman.j.equals(_snowman)) {
               return _snowman;
            }
         }

         return a;
      }

      public static bal.a a(Random var0) {
         int _snowman = _snowman.nextInt(16);
         if (_snowman == 0) {
            return b;
         } else if (_snowman == 1) {
            return c;
         } else if (_snowman == 2) {
            return d;
         } else if (_snowman == 4) {
            return g;
         } else if (_snowman < 9) {
            return f;
         } else {
            return _snowman < 11 ? e : a;
         }
      }
   }

   static class b extends awf {
      private final bal b;

      public b(bal var1, double var2, boolean var4) {
         super(_snowman, _snowman, _snowman);
         this.b = _snowman;
      }

      @Override
      public boolean a() {
         return this.b.fh() && super.a();
      }
   }

   static class c<T extends aqm> extends avd<T> {
      private final bal i;

      public c(bal var1, Class<T> var2, float var3, double var4, double var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, aqd.g::test);
         this.i = _snowman;
      }

      @Override
      public boolean a() {
         return this.i.fb() && this.i.fh() && super.a();
      }
   }

   class d extends avi {
      private final bal e;
      private int f;

      public d(bal var2, double var3) {
         super(_snowman, _snowman);
         this.e = _snowman;
      }

      @Override
      public boolean a() {
         if (!super.a() || this.e.eK() != 0) {
            return false;
         } else if (!this.h()) {
            if (this.f <= this.e.K) {
               this.e.t(32);
               this.f = this.e.K + 600;
               if (this.e.dS()) {
                  bfw _snowman = this.b.a(bal.bv, this.e);
                  this.e.bF.a(_snowman);
               }
            }

            return false;
         } else {
            return true;
         }
      }

      private boolean h() {
         fx _snowman = this.e.cB();
         fx.a _snowmanx = new fx.a();

         for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < 8; _snowmanxxx++) {
               for (int _snowmanxxxx = 0; _snowmanxxxx <= _snowmanxxx; _snowmanxxxx = _snowmanxxxx > 0 ? -_snowmanxxxx : 1 - _snowmanxxxx) {
                  for (int _snowmanxxxxx = _snowmanxxxx < _snowmanxxx && _snowmanxxxx > -_snowmanxxx ? _snowmanxxx : 0; _snowmanxxxxx <= _snowmanxxx; _snowmanxxxxx = _snowmanxxxxx > 0 ? -_snowmanxxxxx : 1 - _snowmanxxxxx) {
                     _snowmanx.a(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxxxx);
                     if (this.b.d_(_snowmanx).a(bup.kY)) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   static class e extends axp {
      private final bal a;

      public e(bal var1, Class<?>... var2) {
         super(_snowman, _snowman);
         this.a = _snowman;
      }

      @Override
      public boolean b() {
         if (!this.a.bw && !this.a.bx) {
            return super.b();
         } else {
            this.a.h(null);
            return false;
         }
      }

      @Override
      protected void a(aqn var1, aqm var2) {
         if (_snowman instanceof bal && ((bal)_snowman).eF()) {
            _snowman.h(_snowman);
         }
      }
   }

   static class f extends avv {
      private final bal a;
      private int b;

      public f(bal var1) {
         this.a = _snowman;
      }

      @Override
      public boolean a() {
         return this.b < this.a.K && this.a.fa() && this.a.fh() && this.a.J.nextInt(400) == 1;
      }

      @Override
      public boolean b() {
         return !this.a.aE() && (this.a.fa() || this.a.J.nextInt(600) != 1) ? this.a.J.nextInt(2000) != 1 : false;
      }

      @Override
      public void c() {
         this.a.u(true);
         this.b = 0;
      }

      @Override
      public void d() {
         this.a.u(false);
         this.b = this.a.K + 200;
      }
   }

   static class g extends awd {
      private final bal g;

      public g(bal var1, Class<? extends aqm> var2, float var3) {
         super(_snowman, _snowman, _snowman);
         this.g = _snowman;
      }

      public void a(aqm var1) {
         this.b = _snowman;
      }

      @Override
      public boolean b() {
         return this.b != null && super.b();
      }

      @Override
      public boolean a() {
         if (this.a.cY().nextFloat() >= this.d) {
            return false;
         } else {
            if (this.b == null) {
               if (this.e == bfw.class) {
                  this.b = this.a.l.a(this.f, this.a, this.a.cD(), this.a.cG(), this.a.cH());
               } else {
                  this.b = this.a.l.b(this.e, this.f, this.a, this.a.cD(), this.a.cG(), this.a.cH(), this.a.cc().c((double)this.c, 3.0, (double)this.c));
               }
            }

            return this.g.fh() && this.b != null;
         }
      }

      @Override
      public void e() {
         if (this.b != null) {
            super.e();
         }
      }
   }

   static class h extends avb {
      private final bal i;

      public h(bal var1) {
         super(_snowman);
         this.i = _snowman;
      }

      @Override
      public void a() {
         if (this.i.fh()) {
            super.a();
         }
      }
   }

   static class i extends awp {
      private final bal g;

      public i(bal var1, double var2) {
         super(_snowman, _snowman);
         this.g = _snowman;
      }

      @Override
      public boolean a() {
         if (!this.g.bq()) {
            return false;
         } else {
            fx _snowman = this.a(this.a.l, this.a, 5, 4);
            if (_snowman != null) {
               this.c = (double)_snowman.u();
               this.d = (double)_snowman.v();
               this.e = (double)_snowman.w();
               return true;
            } else {
               return this.g();
            }
         }
      }

      @Override
      public boolean b() {
         if (this.g.eM()) {
            this.g.x().o();
            return false;
         } else {
            return super.b();
         }
      }
   }

   static class j extends avv {
      private final bal a;

      public j(bal var1) {
         this.a = _snowman;
         this.a(EnumSet.of(avv.a.a, avv.a.b, avv.a.c));
      }

      @Override
      public boolean a() {
         if ((this.a.w_() || this.a.fc()) && this.a.t) {
            if (!this.a.fh()) {
               return false;
            } else {
               float _snowman = this.a.p * (float) (Math.PI / 180.0);
               int _snowmanx = 0;
               int _snowmanxx = 0;
               float _snowmanxxx = -afm.a(_snowman);
               float _snowmanxxxx = afm.b(_snowman);
               if ((double)Math.abs(_snowmanxxx) > 0.5) {
                  _snowmanx = (int)((float)_snowmanx + _snowmanxxx / Math.abs(_snowmanxxx));
               }

               if ((double)Math.abs(_snowmanxxxx) > 0.5) {
                  _snowmanxx = (int)((float)_snowmanxx + _snowmanxxxx / Math.abs(_snowmanxxxx));
               }

               if (this.a.l.d_(this.a.cB().b(_snowmanx, -1, _snowmanxx)).g()) {
                  return true;
               } else {
                  return this.a.fc() && this.a.J.nextInt(60) == 1 ? true : this.a.J.nextInt(500) == 1;
               }
            }
         } else {
            return false;
         }
      }

      @Override
      public boolean b() {
         return false;
      }

      @Override
      public void c() {
         this.a.x(true);
      }

      @Override
      public boolean C_() {
         return false;
      }
   }

   class k extends avv {
      private int b;

      public k() {
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         if (this.b <= bal.this.K && !bal.this.w_() && !bal.this.aE() && bal.this.fh() && bal.this.eK() <= 0) {
            List<bcv> _snowman = bal.this.l.a(bcv.class, bal.this.cc().c(6.0, 6.0, 6.0), bal.bG);
            return !_snowman.isEmpty() || !bal.this.b(aqf.a).a();
         } else {
            return false;
         }
      }

      @Override
      public boolean b() {
         return !bal.this.aE() && (bal.this.fa() || bal.this.J.nextInt(600) != 1) ? bal.this.J.nextInt(2000) != 1 : false;
      }

      @Override
      public void e() {
         if (!bal.this.eM() && !bal.this.b(aqf.a).a()) {
            bal.this.ft();
         }
      }

      @Override
      public void c() {
         List<bcv> _snowman = bal.this.l.a(bcv.class, bal.this.cc().c(8.0, 8.0, 8.0), bal.bG);
         if (!_snowman.isEmpty() && bal.this.b(aqf.a).a()) {
            bal.this.x().a(_snowman.get(0), 1.2F);
         } else if (!bal.this.b(aqf.a).a()) {
            bal.this.ft();
         }

         this.b = 0;
      }

      @Override
      public void d() {
         bmb _snowman = bal.this.b(aqf.a);
         if (!_snowman.a()) {
            bal.this.a(_snowman);
            bal.this.a(aqf.a, bmb.b);
            int _snowmanx = bal.this.fa() ? bal.this.J.nextInt(50) + 10 : bal.this.J.nextInt(150) + 10;
            this.b = bal.this.K + _snowmanx * 20;
         }

         bal.this.t(false);
      }
   }

   static class l extends avv {
      private final bal a;

      public l(bal var1) {
         this.a = _snowman;
      }

      @Override
      public boolean a() {
         if (this.a.w_() && this.a.fh()) {
            return this.a.fe() && this.a.J.nextInt(500) == 1 ? true : this.a.J.nextInt(6000) == 1;
         } else {
            return false;
         }
      }

      @Override
      public boolean b() {
         return false;
      }

      @Override
      public void c() {
         this.a.w(true);
      }
   }
}
