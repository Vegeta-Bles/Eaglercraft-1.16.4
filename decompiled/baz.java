import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class baz extends are implements aqs {
   private static final us<Boolean> br = uv.a(baz.class, uu.i);
   private static final us<Integer> bs = uv.a(baz.class, uu.b);
   private static final us<Integer> bt = uv.a(baz.class, uu.b);
   public static final Predicate<aqm> bq = var0 -> {
      aqe<?> _snowman = var0.X();
      return _snowman == aqe.ar || _snowman == aqe.ao || _snowman == aqe.C;
   };
   private float bu;
   private float bv;
   private boolean bw;
   private boolean bx;
   private float by;
   private float bz;
   private static final afh bA = afu.a(20, 39);
   private UUID bB;

   public baz(aqe<? extends baz> var1, brx var2) {
      super(_snowman, _snowman);
      this.u(false);
   }

   @Override
   protected void o() {
      this.bk.a(1, new avp(this));
      this.bk.a(2, new axb(this));
      this.bk.a(3, new baz.a<>(this, bbe.class, 24.0F, 1.5, 1.5));
      this.bk.a(4, new awb(this, 0.4F));
      this.bk.a(5, new awf(this, 1.0, true));
      this.bk.a(6, new avt(this, 1.0, 10.0F, 2.0F, false));
      this.bk.a(7, new avi(this, 1.0));
      this.bk.a(8, new axk(this, 1.0));
      this.bk.a(9, new ave(this, 8.0F));
      this.bk.a(10, new awd(this, bfw.class, 8.0F));
      this.bk.a(10, new aws(this));
      this.bl.a(1, new axu(this));
      this.bl.a(2, new axv(this));
      this.bl.a(3, new axp(this).a());
      this.bl.a(4, new axq<>(this, bfw.class, 10, true, false, this::a_));
      this.bl.a(5, new axt<>(this, azz.class, false, bq));
      this.bl.a(6, new axt<>(this, bax.class, false, bax.bo));
      this.bl.a(7, new axq<>(this, bcz.class, false));
      this.bl.a(8, new axw<>(this, true));
   }

   public static ark.a eU() {
      return aqn.p().a(arl.d, 0.3F).a(arl.a, 8.0).a(arl.f, 2.0);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(br, false);
      this.R.a(bs, bkx.o.b());
      this.R.a(bt, 0);
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.rh, 0.15F, 1.0F);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("CollarColor", (byte)this.eX().b());
      this.c(_snowman);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("CollarColor", 99)) {
         this.a(bkx.a(_snowman.h("CollarColor")));
      }

      this.a((aag)this.l, _snowman);
   }

   @Override
   protected adp I() {
      if (this.H_()) {
         return adq.rc;
      } else if (this.J.nextInt(3) == 0) {
         return this.eK() && this.dk() < 10.0F ? adq.ri : adq.rf;
      } else {
         return adq.ra;
      }
   }

   @Override
   protected adp e(apk var1) {
      return adq.re;
   }

   @Override
   protected adp dq() {
      return adq.rb;
   }

   @Override
   protected float dG() {
      return 0.4F;
   }

   @Override
   public void k() {
      super.k();
      if (!this.l.v && this.bw && !this.bx && !this.eI() && this.t) {
         this.bx = true;
         this.by = 0.0F;
         this.bz = 0.0F;
         this.l.a(this, (byte)8);
      }

      if (!this.l.v) {
         this.a((aag)this.l, true);
      }
   }

   @Override
   public void j() {
      super.j();
      if (this.aX()) {
         this.bv = this.bu;
         if (this.eY()) {
            this.bu = this.bu + (1.0F - this.bu) * 0.4F;
         } else {
            this.bu = this.bu + (0.0F - this.bu) * 0.4F;
         }

         if (this.aG()) {
            this.bw = true;
            if (this.bx && !this.l.v) {
               this.l.a(this, (byte)56);
               this.eZ();
            }
         } else if ((this.bw || this.bx) && this.bx) {
            if (this.by == 0.0F) {
               this.a(adq.rg, this.dG(), (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
            }

            this.bz = this.by;
            this.by += 0.05F;
            if (this.bz >= 2.0F) {
               this.bw = false;
               this.bx = false;
               this.bz = 0.0F;
               this.by = 0.0F;
            }

            if (this.by > 0.4F) {
               float _snowman = (float)this.cE();
               int _snowmanx = (int)(afm.a((this.by - 0.4F) * (float) Math.PI) * 7.0F);
               dcn _snowmanxx = this.cC();

               for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx; _snowmanxxx++) {
                  float _snowmanxxxx = (this.J.nextFloat() * 2.0F - 1.0F) * this.cy() * 0.5F;
                  float _snowmanxxxxx = (this.J.nextFloat() * 2.0F - 1.0F) * this.cy() * 0.5F;
                  this.l.a(hh.Z, this.cD() + (double)_snowmanxxxx, (double)(_snowman + 0.8F), this.cH() + (double)_snowmanxxxxx, _snowmanxx.b, _snowmanxx.c, _snowmanxx.d);
               }
            }
         }
      }
   }

   private void eZ() {
      this.bx = false;
      this.by = 0.0F;
      this.bz = 0.0F;
   }

   @Override
   public void a(apk var1) {
      this.bw = false;
      this.bx = false;
      this.bz = 0.0F;
      this.by = 0.0F;
      super.a(_snowman);
   }

   public boolean eV() {
      return this.bw;
   }

   public float y(float var1) {
      return Math.min(0.5F + afm.g(_snowman, this.bz, this.by) / 2.0F * 0.5F, 1.0F);
   }

   public float g(float var1, float var2) {
      float _snowman = (afm.g(_snowman, this.bz, this.by) + _snowman) / 1.8F;
      if (_snowman < 0.0F) {
         _snowman = 0.0F;
      } else if (_snowman > 1.0F) {
         _snowman = 1.0F;
      }

      return afm.a(_snowman * (float) Math.PI) * afm.a(_snowman * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
   }

   public float z(float var1) {
      return afm.g(_snowman, this.bv, this.bu) * 0.15F * (float) Math.PI;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return _snowman.b * 0.8F;
   }

   @Override
   public int O() {
      return this.eM() ? 20 : super.O();
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         aqa _snowman = _snowman.k();
         this.w(false);
         if (_snowman != null && !(_snowman instanceof bfw) && !(_snowman instanceof bga)) {
            _snowman = (_snowman + 1.0F) / 2.0F;
         }

         return super.a(_snowman, _snowman);
      }
   }

   @Override
   public boolean B(aqa var1) {
      boolean _snowman = _snowman.a(apk.c(this), (float)((int)this.b(arl.f)));
      if (_snowman) {
         this.a(this, _snowman);
      }

      return _snowman;
   }

   @Override
   public void u(boolean var1) {
      super.u(_snowman);
      if (_snowman) {
         this.a(arl.a).a(20.0);
         this.c(20.0F);
      } else {
         this.a(arl.a).a(8.0);
      }

      this.a(arl.f).a(4.0);
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      blx _snowmanx = _snowman.b();
      if (this.l.v) {
         boolean _snowmanxx = this.i(_snowman) || this.eK() || _snowmanx == bmd.mL && !this.eK() && !this.H_();
         return _snowmanxx ? aou.b : aou.c;
      } else {
         if (this.eK()) {
            if (this.k(_snowman) && this.dk() < this.dx()) {
               if (!_snowman.bC.d) {
                  _snowman.g(1);
               }

               this.b((float)_snowmanx.t().a());
               return aou.a;
            }

            if (!(_snowmanx instanceof bky)) {
               aou _snowmanxx = super.b(_snowman, _snowman);
               if ((!_snowmanxx.a() || this.w_()) && this.i(_snowman)) {
                  this.w(!this.eO());
                  this.aQ = false;
                  this.bj.o();
                  this.h(null);
                  return aou.a;
               }

               return _snowmanxx;
            }

            bkx _snowmanxx = ((bky)_snowmanx).d();
            if (_snowmanxx != this.eX()) {
               this.a(_snowmanxx);
               if (!_snowman.bC.d) {
                  _snowman.g(1);
               }

               return aou.a;
            }
         } else if (_snowmanx == bmd.mL && !this.H_()) {
            if (!_snowman.bC.d) {
               _snowman.g(1);
            }

            if (this.J.nextInt(3) == 0) {
               this.f(_snowman);
               this.bj.o();
               this.h(null);
               this.w(true);
               this.l.a(this, (byte)7);
            } else {
               this.l.a(this, (byte)6);
            }

            return aou.a;
         }

         return super.b(_snowman, _snowman);
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 8) {
         this.bx = true;
         this.by = 0.0F;
         this.bz = 0.0F;
      } else if (_snowman == 56) {
         this.eZ();
      } else {
         super.a(_snowman);
      }
   }

   public float eW() {
      if (this.H_()) {
         return 1.5393804F;
      } else {
         return this.eK() ? (0.55F - (this.dx() - this.dk()) * 0.02F) * (float) Math.PI : (float) (Math.PI / 5);
      }
   }

   @Override
   public boolean k(bmb var1) {
      blx _snowman = _snowman.b();
      return _snowman.s() && _snowman.t().c();
   }

   @Override
   public int eq() {
      return 8;
   }

   @Override
   public int E_() {
      return this.R.a(bt);
   }

   @Override
   public void a_(int var1) {
      this.R.b(bt, _snowman);
   }

   @Override
   public void G_() {
      this.a_(bA.a(this.J));
   }

   @Nullable
   @Override
   public UUID F_() {
      return this.bB;
   }

   @Override
   public void a(@Nullable UUID var1) {
      this.bB = _snowman;
   }

   public bkx eX() {
      return bkx.a(this.R.a(bs));
   }

   public void a(bkx var1) {
      this.R.b(bs, _snowman.b());
   }

   public baz b(aag var1, apy var2) {
      baz _snowman = aqe.aW.a(_snowman);
      UUID _snowmanx = this.A_();
      if (_snowmanx != null) {
         _snowman.b(_snowmanx);
         _snowman.u(true);
      }

      return _snowman;
   }

   public void x(boolean var1) {
      this.R.b(br, _snowman);
   }

   @Override
   public boolean a(azz var1) {
      if (_snowman == this) {
         return false;
      } else if (!this.eK()) {
         return false;
      } else if (!(_snowman instanceof baz)) {
         return false;
      } else {
         baz _snowman = (baz)_snowman;
         if (!_snowman.eK()) {
            return false;
         } else {
            return _snowman.eM() ? false : this.eS() && _snowman.eS();
         }
      }
   }

   public boolean eY() {
      return this.R.a(br);
   }

   @Override
   public boolean a(aqm var1, aqm var2) {
      if (_snowman instanceof bdc || _snowman instanceof bdk) {
         return false;
      } else if (_snowman instanceof baz) {
         baz _snowman = (baz)_snowman;
         return !_snowman.eK() || _snowman.eN() != _snowman;
      } else if (_snowman instanceof bfw && _snowman instanceof bfw && !((bfw)_snowman).a((bfw)_snowman)) {
         return false;
      } else {
         return _snowman instanceof bbb && ((bbb)_snowman).eW() ? false : !(_snowman instanceof are) || !((are)_snowman).eK();
      }
   }

   @Override
   public boolean a(bfw var1) {
      return !this.H_() && super.a(_snowman);
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, (double)(0.6F * this.ce()), (double)(this.cy() * 0.4F));
   }

   class a<T extends aqm> extends avd<T> {
      private final baz j;

      public a(baz var2, Class<T> var3, float var4, double var5, double var7) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman);
         this.j = _snowman;
      }

      @Override
      public boolean a() {
         return super.a() && this.b instanceof bbe ? !this.j.eK() && this.a((bbe)this.b) : false;
      }

      private boolean a(bbe var1) {
         return _snowman.fv() >= baz.this.J.nextInt(5);
      }

      @Override
      public void c() {
         baz.this.h(null);
         super.c();
      }

      @Override
      public void e() {
         baz.this.h(null);
         super.e();
      }
   }
}
