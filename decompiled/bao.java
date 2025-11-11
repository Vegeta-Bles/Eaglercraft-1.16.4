import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class bao extends azz implements aqs {
   private static final us<Boolean> bo = uv.a(bao.class, uu.i);
   private float bp;
   private float bq;
   private int br;
   private static final afh bs = afu.a(20, 39);
   private int bt;
   private UUID bu;

   public bao(aqe<? extends bao> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   public apy a(aag var1, apy var2) {
      return aqe.al.a(_snowman);
   }

   @Override
   public boolean k(bmb var1) {
      return false;
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(0, new avp(this));
      this.bk.a(1, new bao.c());
      this.bk.a(1, new bao.d());
      this.bk.a(4, new avu(this, 1.25));
      this.bk.a(5, new awt(this, 1.0));
      this.bk.a(6, new awd(this, bfw.class, 6.0F));
      this.bk.a(7, new aws(this));
      this.bl.a(1, new bao.b());
      this.bl.a(2, new bao.a());
      this.bl.a(3, new axq<>(this, bfw.class, 10, true, false, this::a_));
      this.bl.a(4, new axq<>(this, bah.class, 10, true, true, null));
      this.bl.a(5, new axw<>(this, false));
   }

   public static ark.a eK() {
      return aqn.p().a(arl.a, 30.0).a(arl.b, 20.0).a(arl.d, 0.25).a(arl.f, 6.0);
   }

   public static boolean c(aqe<bao> var0, bry var1, aqp var2, fx var3, Random var4) {
      Optional<vj<bsv>> _snowman = _snowman.i(_snowman);
      return !Objects.equals(_snowman, Optional.of(btb.k)) && !Objects.equals(_snowman, Optional.of(btb.Y)) ? b(_snowman, _snowman, _snowman, _snowman, _snowman) : _snowman.b(_snowman, 0) > 8 && _snowman.d_(_snowman.c()).a(bup.cD);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.a((aag)this.l, _snowman);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      this.c(_snowman);
   }

   @Override
   public void G_() {
      this.a_(bs.a(this.J));
   }

   @Override
   public void a_(int var1) {
      this.bt = _snowman;
   }

   @Override
   public int E_() {
      return this.bt;
   }

   @Override
   public void a(@Nullable UUID var1) {
      this.bu = _snowman;
   }

   @Override
   public UUID F_() {
      return this.bu;
   }

   @Override
   protected adp I() {
      return this.w_() ? adq.lI : adq.lH;
   }

   @Override
   protected adp e(apk var1) {
      return adq.lK;
   }

   @Override
   protected adp dq() {
      return adq.lJ;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.lL, 0.15F, 1.0F);
   }

   protected void eL() {
      if (this.br <= 0) {
         this.a(adq.lM, 1.0F, this.dH());
         this.br = 40;
      }
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bo, false);
   }

   @Override
   public void j() {
      super.j();
      if (this.l.v) {
         if (this.bq != this.bp) {
            this.x_();
         }

         this.bp = this.bq;
         if (this.eM()) {
            this.bq = afm.a(this.bq + 1.0F, 0.0F, 6.0F);
         } else {
            this.bq = afm.a(this.bq - 1.0F, 0.0F, 6.0F);
         }
      }

      if (this.br > 0) {
         this.br--;
      }

      if (!this.l.v) {
         this.a((aag)this.l, true);
      }
   }

   @Override
   public aqb a(aqx var1) {
      if (this.bq > 0.0F) {
         float _snowman = this.bq / 6.0F;
         float _snowmanx = 1.0F + _snowman;
         return super.a(_snowman).a(1.0F, _snowmanx);
      } else {
         return super.a(_snowman);
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

   public boolean eM() {
      return this.R.a(bo);
   }

   public void t(boolean var1) {
      this.R.b(bo, _snowman);
   }

   public float y(float var1) {
      return afm.g(_snowman, this.bp, this.bq) / 6.0F;
   }

   @Override
   protected float dM() {
      return 0.98F;
   }

   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (_snowman == null) {
         _snowman = new apy.a(1.0F);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   class a extends axq<bfw> {
      public a() {
         super(bao.this, bfw.class, 20, true, true, null);
      }

      @Override
      public boolean a() {
         if (bao.this.w_()) {
            return false;
         } else {
            if (super.a()) {
               for (bao _snowman : bao.this.l.a(bao.class, bao.this.cc().c(8.0, 4.0, 8.0))) {
                  if (_snowman.w_()) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      @Override
      protected double k() {
         return super.k() * 0.5;
      }
   }

   class b extends axp {
      public b() {
         super(bao.this);
      }

      @Override
      public void c() {
         super.c();
         if (bao.this.w_()) {
            this.g();
            this.d();
         }
      }

      @Override
      protected void a(aqn var1, aqm var2) {
         if (_snowman instanceof bao && !_snowman.w_()) {
            super.a(_snowman, _snowman);
         }
      }
   }

   class c extends awf {
      public c() {
         super(bao.this, 1.25, true);
      }

      @Override
      protected void a(aqm var1, double var2) {
         double _snowman = this.a(_snowman);
         if (_snowman <= _snowman && this.h()) {
            this.g();
            this.a.B(_snowman);
            bao.this.t(false);
         } else if (_snowman <= _snowman * 2.0) {
            if (this.h()) {
               bao.this.t(false);
               this.g();
            }

            if (this.j() <= 10) {
               bao.this.t(true);
               bao.this.eL();
            }
         } else {
            this.g();
            bao.this.t(false);
         }
      }

      @Override
      public void d() {
         bao.this.t(false);
         super.d();
      }

      @Override
      protected double a(aqm var1) {
         return (double)(4.0F + _snowman.cy());
      }
   }

   class d extends awp {
      public d() {
         super(bao.this, 2.0);
      }

      @Override
      public boolean a() {
         return !bao.this.w_() && !bao.this.bq() ? false : super.a();
      }
   }
}
