import java.util.Random;
import javax.annotation.Nullable;

public class baq extends azz {
   private static final us<Integer> bo = uv.a(baq.class, uu.b);
   private static final vk bp = new vk("killer_bunny");
   private int bq;
   private int br;
   private boolean bs;
   private int bt;
   private int bu;

   public baq(aqe<? extends baq> var1, brx var2) {
      super(_snowman, _snowman);
      this.bi = new baq.d(this);
      this.bh = new baq.e(this);
      this.i(0.0);
   }

   @Override
   protected void o() {
      this.bk.a(1, new avp(this));
      this.bk.a(1, new baq.f(this, 2.2));
      this.bk.a(2, new avi(this, 0.8));
      this.bk.a(3, new axf(this, 1.0, bon.a(bmd.oY, bmd.pd, bup.bp), false));
      this.bk.a(4, new baq.b<>(this, bfw.class, 8.0F, 2.2, 2.2));
      this.bk.a(4, new baq.b<>(this, baz.class, 10.0F, 2.2, 2.2));
      this.bk.a(4, new baq.b<>(this, bdq.class, 4.0F, 2.2, 2.2));
      this.bk.a(5, new baq.g(this));
      this.bk.a(6, new axk(this, 0.6));
      this.bk.a(11, new awd(this, bfw.class, 10.0F));
   }

   @Override
   protected float dJ() {
      if (!this.u && (!this.bh.b() || !(this.bh.e() > this.cE() + 0.5))) {
         cxd _snowman = this.bj.k();
         if (_snowman != null && !_snowman.c()) {
            dcn _snowmanx = _snowman.a(this);
            if (_snowmanx.c > this.cE() + 0.5) {
               return 0.5F;
            }
         }

         return this.bh.c() <= 0.6 ? 0.2F : 0.3F;
      } else {
         return 0.5F;
      }
   }

   @Override
   protected void dK() {
      super.dK();
      double _snowman = this.bh.c();
      if (_snowman > 0.0) {
         double _snowmanx = c(this.cC());
         if (_snowmanx < 0.01) {
            this.a(0.1F, new dcn(0.0, 0.0, 1.0));
         }
      }

      if (!this.l.v) {
         this.l.a(this, (byte)1);
      }
   }

   public float y(float var1) {
      return this.br == 0 ? 0.0F : ((float)this.bq + _snowman) / (float)this.br;
   }

   public void i(double var1) {
      this.x().a(_snowman);
      this.bh.a(this.bh.d(), this.bh.e(), this.bh.f(), _snowman);
   }

   @Override
   public void o(boolean var1) {
      super.o(_snowman);
      if (_snowman) {
         this.a(this.eM(), this.dG(), ((this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F) * 0.8F);
      }
   }

   public void eK() {
      this.o(true);
      this.br = 10;
      this.bq = 0;
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bo, 0);
   }

   @Override
   public void N() {
      if (this.bt > 0) {
         this.bt--;
      }

      if (this.bu > 0) {
         this.bu = this.bu - this.J.nextInt(3);
         if (this.bu < 0) {
            this.bu = 0;
         }
      }

      if (this.t) {
         if (!this.bs) {
            this.o(false);
            this.eW();
         }

         if (this.eN() == 99 && this.bt == 0) {
            aqm _snowman = this.A();
            if (_snowman != null && this.h((aqa)_snowman) < 16.0) {
               this.b(_snowman.cD(), _snowman.cH());
               this.bh.a(_snowman.cD(), _snowman.cE(), _snowman.cH(), this.bh.c());
               this.eK();
               this.bs = true;
            }
         }

         baq.d _snowman = (baq.d)this.bi;
         if (!_snowman.c()) {
            if (this.bh.b() && this.bt == 0) {
               cxd _snowmanx = this.bj.k();
               dcn _snowmanxx = new dcn(this.bh.d(), this.bh.e(), this.bh.f());
               if (_snowmanx != null && !_snowmanx.c()) {
                  _snowmanxx = _snowmanx.a(this);
               }

               this.b(_snowmanxx.b, _snowmanxx.d);
               this.eK();
            }
         } else if (!_snowman.d()) {
            this.eO();
         }
      }

      this.bs = this.t;
   }

   @Override
   public boolean aO() {
      return false;
   }

   private void b(double var1, double var3) {
      this.p = (float)(afm.d(_snowman - this.cH(), _snowman - this.cD()) * 180.0F / (float)Math.PI) - 90.0F;
   }

   private void eO() {
      ((baq.d)this.bi).a(true);
   }

   private void eU() {
      ((baq.d)this.bi).a(false);
   }

   private void eV() {
      if (this.bh.c() < 2.2) {
         this.bt = 10;
      } else {
         this.bt = 1;
      }
   }

   private void eW() {
      this.eV();
      this.eU();
   }

   @Override
   public void k() {
      super.k();
      if (this.bq != this.br) {
         this.bq++;
      } else if (this.br != 0) {
         this.bq = 0;
         this.br = 0;
         this.o(false);
      }
   }

   public static ark.a eL() {
      return aqn.p().a(arl.a, 3.0).a(arl.d, 0.3F);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("RabbitType", this.eN());
      _snowman.b("MoreCarrotTicks", this.bu);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.t(_snowman.h("RabbitType"));
      this.bu = _snowman.h("MoreCarrotTicks");
   }

   protected adp eM() {
      return adq.mc;
   }

   @Override
   protected adp I() {
      return adq.lY;
   }

   @Override
   protected adp e(apk var1) {
      return adq.mb;
   }

   @Override
   protected adp dq() {
      return adq.ma;
   }

   @Override
   public boolean B(aqa var1) {
      if (this.eN() == 99) {
         this.a(adq.lZ, 1.0F, (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
         return _snowman.a(apk.c(this), 8.0F);
      } else {
         return _snowman.a(apk.c(this), 3.0F);
      }
   }

   @Override
   public adr cu() {
      return this.eN() == 99 ? adr.f : adr.g;
   }

   @Override
   public boolean a(apk var1, float var2) {
      return this.b(_snowman) ? false : super.a(_snowman, _snowman);
   }

   private boolean b(blx var1) {
      return _snowman == bmd.oY || _snowman == bmd.pd || _snowman == bup.bp.h();
   }

   public baq b(aag var1, apy var2) {
      baq _snowman = aqe.ao.a(_snowman);
      int _snowmanx = this.a(_snowman);
      if (this.J.nextInt(20) != 0) {
         if (_snowman instanceof baq && this.J.nextBoolean()) {
            _snowmanx = ((baq)_snowman).eN();
         } else {
            _snowmanx = this.eN();
         }
      }

      _snowman.t(_snowmanx);
      return _snowman;
   }

   @Override
   public boolean k(bmb var1) {
      return this.b(_snowman.b());
   }

   public int eN() {
      return this.R.a(bo);
   }

   public void t(int var1) {
      if (_snowman == 99) {
         this.a(arl.i).a(8.0);
         this.bk.a(4, new baq.a(this));
         this.bl.a(1, new axp(this).a());
         this.bl.a(2, new axq<>(this, bfw.class, true));
         this.bl.a(2, new axq<>(this, baz.class, true));
         if (!this.S()) {
            this.a(new of(x.a("entity", bp)));
         }
      }

      this.R.b(bo, _snowman);
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      int _snowman = this.a(_snowman);
      if (_snowman instanceof baq.c) {
         _snowman = ((baq.c)_snowman).a;
      } else {
         _snowman = new baq.c(_snowman);
      }

      this.t(_snowman);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private int a(bry var1) {
      bsv _snowman = _snowman.v(this.cB());
      int _snowmanx = this.J.nextInt(100);
      if (_snowman.c() == bsv.e.c) {
         return _snowmanx < 80 ? 1 : 3;
      } else if (_snowman.t() == bsv.b.m) {
         return 4;
      } else {
         return _snowmanx < 50 ? 0 : (_snowmanx < 90 ? 5 : 2);
      }
   }

   public static boolean c(aqe<baq> var0, bry var1, aqp var2, fx var3, Random var4) {
      ceh _snowman = _snowman.d_(_snowman.c());
      return (_snowman.a(bup.i) || _snowman.a(bup.cC) || _snowman.a(bup.C)) && _snowman.b(_snowman, 0) > 8;
   }

   private boolean eX() {
      return this.bu == 0;
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 1) {
         this.aP();
         this.br = 10;
         this.bq = 0;
      } else {
         super.a(_snowman);
      }
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, (double)(0.6F * this.ce()), (double)(this.cy() * 0.4F));
   }

   static class a extends awf {
      public a(baq var1) {
         super(_snowman, 1.4, true);
      }

      @Override
      protected double a(aqm var1) {
         return (double)(4.0F + _snowman.cy());
      }
   }

   static class b<T extends aqm> extends avd<T> {
      private final baq i;

      public b(baq var1, Class<T> var2, float var3, double var4, double var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman);
         this.i = _snowman;
      }

      @Override
      public boolean a() {
         return this.i.eN() != 99 && super.a();
      }
   }

   public static class c extends apy.a {
      public final int a;

      public c(int var1) {
         super(1.0F);
         this.a = _snowman;
      }
   }

   public class d extends auz {
      private final baq c;
      private boolean d;

      public d(baq var2) {
         super(_snowman);
         this.c = _snowman;
      }

      public boolean c() {
         return this.a;
      }

      public boolean d() {
         return this.d;
      }

      public void a(boolean var1) {
         this.d = _snowman;
      }

      @Override
      public void b() {
         if (this.a) {
            this.c.eK();
            this.a = false;
         }
      }
   }

   static class e extends avb {
      private final baq i;
      private double j;

      public e(baq var1) {
         super(_snowman);
         this.i = _snowman;
      }

      @Override
      public void a() {
         if (this.i.t && !this.i.aQ && !((baq.d)this.i.bi).c()) {
            this.i.i(0.0);
         } else if (this.b()) {
            this.i.i(this.j);
         }

         super.a();
      }

      @Override
      public void a(double var1, double var3, double var5, double var7) {
         if (this.i.aE()) {
            _snowman = 1.5;
         }

         super.a(_snowman, _snowman, _snowman, _snowman);
         if (_snowman > 0.0) {
            this.j = _snowman;
         }
      }
   }

   static class f extends awp {
      private final baq g;

      public f(baq var1, double var2) {
         super(_snowman, _snowman);
         this.g = _snowman;
      }

      @Override
      public void e() {
         super.e();
         this.g.i(this.b);
      }
   }

   static class g extends awj {
      private final baq g;
      private boolean h;
      private boolean i;

      public g(baq var1) {
         super(_snowman, 0.7F, 16);
         this.g = _snowman;
      }

      @Override
      public boolean a() {
         if (this.c <= 0) {
            if (!this.g.l.V().b(brt.b)) {
               return false;
            }

            this.i = false;
            this.h = this.g.eX();
            this.h = true;
         }

         return super.a();
      }

      @Override
      public boolean b() {
         return this.i && super.b();
      }

      @Override
      public void e() {
         super.e();
         this.g.t().a((double)this.e.u() + 0.5, (double)(this.e.v() + 1), (double)this.e.w() + 0.5, 10.0F, (float)this.g.O());
         if (this.l()) {
            brx _snowman = this.g.l;
            fx _snowmanx = this.e.b();
            ceh _snowmanxx = _snowman.d_(_snowmanx);
            buo _snowmanxxx = _snowmanxx.b();
            if (this.i && _snowmanxxx instanceof buz) {
               Integer _snowmanxxxx = _snowmanxx.c(buz.b);
               if (_snowmanxxxx == 0) {
                  _snowman.a(_snowmanx, bup.a.n(), 2);
                  _snowman.a(_snowmanx, true, this.g);
               } else {
                  _snowman.a(_snowmanx, _snowmanxx.a(buz.b, Integer.valueOf(_snowmanxxxx - 1)), 2);
                  _snowman.c(2001, _snowmanx, buo.i(_snowmanxx));
               }

               this.g.bu = 40;
            }

            this.i = false;
            this.c = 10;
         }
      }

      @Override
      protected boolean a(brz var1, fx var2) {
         buo _snowman = _snowman.d_(_snowman).b();
         if (_snowman == bup.bX && this.h && !this.i) {
            _snowman = _snowman.b();
            ceh _snowmanx = _snowman.d_(_snowman);
            _snowman = _snowmanx.b();
            if (_snowman instanceof buz && ((buz)_snowman).h(_snowmanx)) {
               this.i = true;
               return true;
            }
         }

         return false;
      }
   }
}
