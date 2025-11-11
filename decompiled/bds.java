import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;

public class bds extends aqh implements bdi {
   private static final us<Integer> b = uv.a(bds.class, uu.b);
   private dcn c = dcn.a;
   private fx d = fx.b;
   private bds.a bo = bds.a.a;

   public bds(aqe<? extends bds> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 5;
      this.bh = new bds.g(this);
      this.g = new bds.f(this);
   }

   @Override
   protected auu r() {
      return new bds.d(this);
   }

   @Override
   protected void o() {
      this.bk.a(1, new bds.c());
      this.bk.a(2, new bds.i());
      this.bk.a(3, new bds.e());
      this.bl.a(1, new bds.b());
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, 0);
   }

   public void a(int var1) {
      this.R.b(b, afm.a(_snowman, 0, 64));
   }

   private void eJ() {
      this.x_();
      this.a(arl.f).a((double)(6 + this.m()));
   }

   @Override
   public int m() {
      return this.R.a(b);
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return _snowman.b * 0.35F;
   }

   @Override
   public void a(us<?> var1) {
      if (b.equals(_snowman)) {
         this.eJ();
      }

      super.a(_snowman);
   }

   @Override
   protected boolean L() {
      return true;
   }

   @Override
   public void j() {
      super.j();
      if (this.l.v) {
         float _snowman = afm.b((float)(this.Y() * 3 + this.K) * 0.13F + (float) Math.PI);
         float _snowmanx = afm.b((float)(this.Y() * 3 + this.K + 1) * 0.13F + (float) Math.PI);
         if (_snowman > 0.0F && _snowmanx <= 0.0F) {
            this.l.a(this.cD(), this.cE(), this.cH(), adq.kK, this.cu(), 0.95F + this.J.nextFloat() * 0.05F, 0.95F + this.J.nextFloat() * 0.05F, false);
         }

         int _snowmanxx = this.m();
         float _snowmanxxx = afm.b(this.p * (float) (Math.PI / 180.0)) * (1.3F + 0.21F * (float)_snowmanxx);
         float _snowmanxxxx = afm.a(this.p * (float) (Math.PI / 180.0)) * (1.3F + 0.21F * (float)_snowmanxx);
         float _snowmanxxxxx = (0.3F + _snowman * 0.45F) * ((float)_snowmanxx * 0.2F + 1.0F);
         this.l.a(hh.N, this.cD() + (double)_snowmanxxx, this.cE() + (double)_snowmanxxxxx, this.cH() + (double)_snowmanxxxx, 0.0, 0.0, 0.0);
         this.l.a(hh.N, this.cD() - (double)_snowmanxxx, this.cE() + (double)_snowmanxxxxx, this.cH() - (double)_snowmanxxxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public void k() {
      if (this.aX() && this.eG()) {
         this.f(8);
      }

      super.k();
   }

   @Override
   protected void N() {
      super.N();
   }

   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.d = this.cB().b(5);
      this.a(0);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.e("AX")) {
         this.d = new fx(_snowman.h("AX"), _snowman.h("AY"), _snowman.h("AZ"));
      }

      this.a(_snowman.h("Size"));
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("AX", this.d.u());
      _snowman.b("AY", this.d.v());
      _snowman.b("AZ", this.d.w());
      _snowman.b("Size", this.m());
   }

   @Override
   public boolean a(double var1) {
      return true;
   }

   @Override
   public adr cu() {
      return adr.f;
   }

   @Override
   protected adp I() {
      return adq.kH;
   }

   @Override
   protected adp e(apk var1) {
      return adq.kL;
   }

   @Override
   protected adp dq() {
      return adq.kJ;
   }

   @Override
   public aqq dC() {
      return aqq.b;
   }

   @Override
   protected float dG() {
      return 1.0F;
   }

   @Override
   public boolean a(aqe<?> var1) {
      return true;
   }

   @Override
   public aqb a(aqx var1) {
      int _snowman = this.m();
      aqb _snowmanx = super.a(_snowman);
      float _snowmanxx = (_snowmanx.a + 0.2F * (float)_snowman) / _snowmanx.a;
      return _snowmanx.a(_snowmanxx);
   }

   static enum a {
      a,
      b;

      private a() {
      }
   }

   class b extends avv {
      private final azg b = new azg().a(64.0);
      private int c = 20;

      private b() {
      }

      @Override
      public boolean a() {
         if (this.c > 0) {
            this.c--;
            return false;
         } else {
            this.c = 60;
            List<bfw> _snowman = bds.this.l.a(this.b, bds.this, bds.this.cc().c(16.0, 64.0, 16.0));
            if (!_snowman.isEmpty()) {
               _snowman.sort(Comparator.comparing(aqa::cE).reversed());

               for (bfw _snowmanx : _snowman) {
                  if (bds.this.a(_snowmanx, azg.a)) {
                     bds.this.h(_snowmanx);
                     return true;
                  }
               }
            }

            return false;
         }
      }

      @Override
      public boolean b() {
         aqm _snowman = bds.this.A();
         return _snowman != null ? bds.this.a(_snowman, azg.a) : false;
      }
   }

   class c extends avv {
      private int b;

      private c() {
      }

      @Override
      public boolean a() {
         aqm _snowman = bds.this.A();
         return _snowman != null ? bds.this.a(bds.this.A(), azg.a) : false;
      }

      @Override
      public void c() {
         this.b = 10;
         bds.this.bo = bds.a.a;
         this.g();
      }

      @Override
      public void d() {
         bds.this.d = bds.this.l.a(chn.a.e, bds.this.d).b(10 + bds.this.J.nextInt(20));
      }

      @Override
      public void e() {
         if (bds.this.bo == bds.a.a) {
            this.b--;
            if (this.b <= 0) {
               bds.this.bo = bds.a.b;
               this.g();
               this.b = (8 + bds.this.J.nextInt(4)) * 20;
               bds.this.a(adq.kM, 10.0F, 0.95F + bds.this.J.nextFloat() * 0.1F);
            }
         }
      }

      private void g() {
         bds.this.d = bds.this.A().cB().b(20 + bds.this.J.nextInt(20));
         if (bds.this.d.v() < bds.this.l.t_()) {
            bds.this.d = new fx(bds.this.d.u(), bds.this.l.t_() + 1, bds.this.d.w());
         }
      }
   }

   class d extends auu {
      public d(aqn var2) {
         super(_snowman);
      }

      @Override
      public void a() {
         bds.this.aC = bds.this.aA;
         bds.this.aA = bds.this.p;
      }
   }

   class e extends bds.h {
      private float c;
      private float d;
      private float e;
      private float f;

      private e() {
      }

      @Override
      public boolean a() {
         return bds.this.A() == null || bds.this.bo == bds.a.a;
      }

      @Override
      public void c() {
         this.d = 5.0F + bds.this.J.nextFloat() * 10.0F;
         this.e = -4.0F + bds.this.J.nextFloat() * 9.0F;
         this.f = bds.this.J.nextBoolean() ? 1.0F : -1.0F;
         this.h();
      }

      @Override
      public void e() {
         if (bds.this.J.nextInt(350) == 0) {
            this.e = -4.0F + bds.this.J.nextFloat() * 9.0F;
         }

         if (bds.this.J.nextInt(250) == 0) {
            this.d++;
            if (this.d > 15.0F) {
               this.d = 5.0F;
               this.f = -this.f;
            }
         }

         if (bds.this.J.nextInt(450) == 0) {
            this.c = bds.this.J.nextFloat() * 2.0F * (float) Math.PI;
            this.h();
         }

         if (this.g()) {
            this.h();
         }

         if (bds.this.c.c < bds.this.cE() && !bds.this.l.w(bds.this.cB().c(1))) {
            this.e = Math.max(1.0F, this.e);
            this.h();
         }

         if (bds.this.c.c > bds.this.cE() && !bds.this.l.w(bds.this.cB().b(1))) {
            this.e = Math.min(-1.0F, this.e);
            this.h();
         }
      }

      private void h() {
         if (fx.b.equals(bds.this.d)) {
            bds.this.d = bds.this.cB();
         }

         this.c = this.c + this.f * 15.0F * (float) (Math.PI / 180.0);
         bds.this.c = dcn.b(bds.this.d).b((double)(this.d * afm.b(this.c)), (double)(-4.0F + this.e), (double)(this.d * afm.a(this.c)));
      }
   }

   class f extends ava {
      public f(aqn var2) {
         super(_snowman);
      }

      @Override
      public void a() {
      }
   }

   class g extends avb {
      private float j = 0.1F;

      public g(aqn var2) {
         super(_snowman);
      }

      @Override
      public void a() {
         if (bds.this.u) {
            bds.this.p += 180.0F;
            this.j = 0.1F;
         }

         float _snowman = (float)(bds.this.c.b - bds.this.cD());
         float _snowmanx = (float)(bds.this.c.c - bds.this.cE());
         float _snowmanxx = (float)(bds.this.c.d - bds.this.cH());
         double _snowmanxxx = (double)afm.c(_snowman * _snowman + _snowmanxx * _snowmanxx);
         double _snowmanxxxx = 1.0 - (double)afm.e(_snowmanx * 0.7F) / _snowmanxxx;
         _snowman = (float)((double)_snowman * _snowmanxxxx);
         _snowmanxx = (float)((double)_snowmanxx * _snowmanxxxx);
         _snowmanxxx = (double)afm.c(_snowman * _snowman + _snowmanxx * _snowmanxx);
         double _snowmanxxxxx = (double)afm.c(_snowman * _snowman + _snowmanxx * _snowmanxx + _snowmanx * _snowmanx);
         float _snowmanxxxxxx = bds.this.p;
         float _snowmanxxxxxxx = (float)afm.d((double)_snowmanxx, (double)_snowman);
         float _snowmanxxxxxxxx = afm.g(bds.this.p + 90.0F);
         float _snowmanxxxxxxxxx = afm.g(_snowmanxxxxxxx * (180.0F / (float)Math.PI));
         bds.this.p = afm.d(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, 4.0F) - 90.0F;
         bds.this.aA = bds.this.p;
         if (afm.d(_snowmanxxxxxx, bds.this.p) < 3.0F) {
            this.j = afm.c(this.j, 1.8F, 0.005F * (1.8F / this.j));
         } else {
            this.j = afm.c(this.j, 0.2F, 0.025F);
         }

         float _snowmanxxxxxxxxxx = (float)(-(afm.d((double)(-_snowmanx), _snowmanxxx) * 180.0F / (float)Math.PI));
         bds.this.q = _snowmanxxxxxxxxxx;
         float _snowmanxxxxxxxxxxx = bds.this.p + 90.0F;
         double _snowmanxxxxxxxxxxxx = (double)(this.j * afm.b(_snowmanxxxxxxxxxxx * (float) (Math.PI / 180.0))) * Math.abs((double)_snowman / _snowmanxxxxx);
         double _snowmanxxxxxxxxxxxxx = (double)(this.j * afm.a(_snowmanxxxxxxxxxxx * (float) (Math.PI / 180.0))) * Math.abs((double)_snowmanxx / _snowmanxxxxx);
         double _snowmanxxxxxxxxxxxxxx = (double)(this.j * afm.a(_snowmanxxxxxxxxxx * (float) (Math.PI / 180.0))) * Math.abs((double)_snowmanx / _snowmanxxxxx);
         dcn _snowmanxxxxxxxxxxxxxxx = bds.this.cC();
         bds.this.f(_snowmanxxxxxxxxxxxxxxx.e(new dcn(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx).d(_snowmanxxxxxxxxxxxxxxx).a(0.2)));
      }
   }

   abstract class h extends avv {
      public h() {
         this.a(EnumSet.of(avv.a.a));
      }

      protected boolean g() {
         return bds.this.c.c(bds.this.cD(), bds.this.cE(), bds.this.cH()) < 4.0;
      }
   }

   class i extends bds.h {
      private i() {
      }

      @Override
      public boolean a() {
         return bds.this.A() != null && bds.this.bo == bds.a.b;
      }

      @Override
      public boolean b() {
         aqm _snowman = bds.this.A();
         if (_snowman == null) {
            return false;
         } else if (!_snowman.aX()) {
            return false;
         } else if (!(_snowman instanceof bfw) || !((bfw)_snowman).a_() && !((bfw)_snowman).b_()) {
            if (!this.a()) {
               return false;
            } else {
               if (bds.this.K % 20 == 0) {
                  List<bab> _snowmanx = bds.this.l.a(bab.class, bds.this.cc().g(16.0), aqd.a);
                  if (!_snowmanx.isEmpty()) {
                     for (bab _snowmanxx : _snowmanx) {
                        _snowmanxx.eZ();
                     }

                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }

      @Override
      public void c() {
      }

      @Override
      public void d() {
         bds.this.h(null);
         bds.this.bo = bds.a.a;
      }

      @Override
      public void e() {
         aqm _snowman = bds.this.A();
         bds.this.c = new dcn(_snowman.cD(), _snowman.e(0.5), _snowman.cH());
         if (bds.this.cc().g(0.2F).c(_snowman.cc())) {
            bds.this.B(_snowman);
            bds.this.bo = bds.a.a;
            if (!bds.this.aA()) {
               bds.this.l.c(1039, bds.this.cB(), 0);
            }
         } else if (bds.this.u || bds.this.an > 0) {
            bds.this.bo = bds.a.a;
         }
      }
   }
}
