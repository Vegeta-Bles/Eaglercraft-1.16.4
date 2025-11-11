import java.util.EnumSet;
import javax.annotation.Nullable;

public class bee extends bdq {
   protected static final us<Byte> b = uv.a(bee.class, uu.a);
   private aqn c;
   @Nullable
   private fx d;
   private boolean bo;
   private int bp;

   public bee(aqe<? extends bee> var1, brx var2) {
      super(_snowman, _snowman);
      this.bh = new bee.c(this);
      this.f = 3;
   }

   @Override
   public void a(aqr var1, dcn var2) {
      super.a(_snowman, _snowman);
      this.ay();
   }

   @Override
   public void j() {
      this.H = true;
      super.j();
      this.H = false;
      this.e(true);
      if (this.bo && --this.bp <= 0) {
         this.bp = 20;
         this.a(apk.i, 1.0F);
      }
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(0, new avp(this));
      this.bk.a(4, new bee.a());
      this.bk.a(8, new bee.d());
      this.bk.a(9, new awd(this, bfw.class, 3.0F, 1.0F));
      this.bk.a(10, new awd(this, aqn.class, 8.0F));
      this.bl.a(1, new axp(this, bhc.class).a());
      this.bl.a(2, new bee.b(this));
      this.bl.a(3, new axq<>(this, bfw.class, true));
   }

   public static ark.a m() {
      return bdq.eR().a(arl.a, 14.0).a(arl.f, 4.0);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, (byte)0);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.e("BoundX")) {
         this.d = new fx(_snowman.h("BoundX"), _snowman.h("BoundY"), _snowman.h("BoundZ"));
      }

      if (_snowman.e("LifeTicks")) {
         this.a(_snowman.h("LifeTicks"));
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.d != null) {
         _snowman.b("BoundX", this.d.u());
         _snowman.b("BoundY", this.d.v());
         _snowman.b("BoundZ", this.d.w());
      }

      if (this.bo) {
         _snowman.b("LifeTicks", this.bp);
      }
   }

   public aqn eK() {
      return this.c;
   }

   @Nullable
   public fx eL() {
      return this.d;
   }

   public void g(@Nullable fx var1) {
      this.d = _snowman;
   }

   private boolean b(int var1) {
      int _snowman = this.R.a(b);
      return (_snowman & _snowman) != 0;
   }

   private void a(int var1, boolean var2) {
      int _snowman = this.R.a(b);
      if (_snowman) {
         _snowman |= _snowman;
      } else {
         _snowman &= ~_snowman;
      }

      this.R.b(b, (byte)(_snowman & 0xFF));
   }

   public boolean eM() {
      return this.b(1);
   }

   public void t(boolean var1) {
      this.a(1, _snowman);
   }

   public void a(aqn var1) {
      this.c = _snowman;
   }

   public void a(int var1) {
      this.bo = true;
      this.bp = _snowman;
   }

   @Override
   protected adp I() {
      return adq.pO;
   }

   @Override
   protected adp dq() {
      return adq.pQ;
   }

   @Override
   protected adp e(apk var1) {
      return adq.pR;
   }

   @Override
   public float aR() {
      return 1.0F;
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.a(_snowman);
      this.b(_snowman);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void a(aos var1) {
      this.a(aqf.a, new bmb(bmd.kA));
      this.a(aqf.a, 0.0F);
   }

   class a extends avv {
      public a() {
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         return bee.this.A() != null && !bee.this.u().b() && bee.this.J.nextInt(7) == 0 ? bee.this.h(bee.this.A()) > 4.0 : false;
      }

      @Override
      public boolean b() {
         return bee.this.u().b() && bee.this.eM() && bee.this.A() != null && bee.this.A().aX();
      }

      @Override
      public void c() {
         aqm _snowman = bee.this.A();
         dcn _snowmanx = _snowman.j(1.0F);
         bee.this.bh.a(_snowmanx.b, _snowmanx.c, _snowmanx.d, 1.0);
         bee.this.t(true);
         bee.this.a(adq.pP, 1.0F, 1.0F);
      }

      @Override
      public void d() {
         bee.this.t(false);
      }

      @Override
      public void e() {
         aqm _snowman = bee.this.A();
         if (bee.this.cc().c(_snowman.cc())) {
            bee.this.B(_snowman);
            bee.this.t(false);
         } else {
            double _snowmanx = bee.this.h(_snowman);
            if (_snowmanx < 9.0) {
               dcn _snowmanxx = _snowman.j(1.0F);
               bee.this.bh.a(_snowmanxx.b, _snowmanxx.c, _snowmanxx.d, 1.0);
            }
         }
      }
   }

   class b extends axx {
      private final azg b = new azg().c().e();

      public b(aqu var2) {
         super(_snowman, false);
      }

      @Override
      public boolean a() {
         return bee.this.c != null && bee.this.c.A() != null && this.a(bee.this.c.A(), this.b);
      }

      @Override
      public void c() {
         bee.this.h(bee.this.c.A());
         super.c();
      }
   }

   class c extends avb {
      public c(bee var2) {
         super(_snowman);
      }

      @Override
      public void a() {
         if (this.h == avb.a.b) {
            dcn _snowman = new dcn(this.b - bee.this.cD(), this.c - bee.this.cE(), this.d - bee.this.cH());
            double _snowmanx = _snowman.f();
            if (_snowmanx < bee.this.cc().a()) {
               this.h = avb.a.a;
               bee.this.f(bee.this.cC().a(0.5));
            } else {
               bee.this.f(bee.this.cC().e(_snowman.a(this.e * 0.05 / _snowmanx)));
               if (bee.this.A() == null) {
                  dcn _snowmanxx = bee.this.cC();
                  bee.this.p = -((float)afm.d(_snowmanxx.b, _snowmanxx.d)) * (180.0F / (float)Math.PI);
                  bee.this.aA = bee.this.p;
               } else {
                  double _snowmanxx = bee.this.A().cD() - bee.this.cD();
                  double _snowmanxxx = bee.this.A().cH() - bee.this.cH();
                  bee.this.p = -((float)afm.d(_snowmanxx, _snowmanxxx)) * (180.0F / (float)Math.PI);
                  bee.this.aA = bee.this.p;
               }
            }
         }
      }
   }

   class d extends avv {
      public d() {
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         return !bee.this.u().b() && bee.this.J.nextInt(7) == 0;
      }

      @Override
      public boolean b() {
         return false;
      }

      @Override
      public void e() {
         fx _snowman = bee.this.eL();
         if (_snowman == null) {
            _snowman = bee.this.cB();
         }

         for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
            fx _snowmanxx = _snowman.b(bee.this.J.nextInt(15) - 7, bee.this.J.nextInt(11) - 5, bee.this.J.nextInt(15) - 7);
            if (bee.this.l.w(_snowmanxx)) {
               bee.this.bh.a((double)_snowmanxx.u() + 0.5, (double)_snowmanxx.v() + 0.5, (double)_snowmanxx.w() + 0.5, 0.25);
               if (bee.this.A() == null) {
                  bee.this.t().a((double)_snowmanxx.u() + 0.5, (double)_snowmanxx.v() + 0.5, (double)_snowmanxx.w() + 0.5, 180.0F, 20.0F);
               }
               break;
            }
         }
      }
   }
}
