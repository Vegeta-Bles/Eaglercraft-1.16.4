import java.util.Random;
import javax.annotation.Nullable;

public class beb extends bdq {
   private static final us<Byte> b = uv.a(beb.class, uu.a);

   public beb(aqe<? extends beb> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      this.bk.a(1, new avp(this));
      this.bk.a(3, new awb(this, 0.4F));
      this.bk.a(4, new beb.a(this));
      this.bk.a(5, new axk(this, 0.8));
      this.bk.a(6, new awd(this, bfw.class, 8.0F));
      this.bk.a(6, new aws(this));
      this.bl.a(1, new axp(this));
      this.bl.a(2, new beb.c<>(this, bfw.class));
      this.bl.a(3, new beb.c<>(this, bai.class));
   }

   @Override
   public double bc() {
      return (double)(this.cz() * 0.5F);
   }

   @Override
   protected ayj b(brx var1) {
      return new ayk(this, _snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, (byte)0);
   }

   @Override
   public void j() {
      super.j();
      if (!this.l.v) {
         this.t(this.u);
      }
   }

   public static ark.a eK() {
      return bdq.eR().a(arl.a, 16.0).a(arl.d, 0.3F);
   }

   @Override
   protected adp I() {
      return adq.oA;
   }

   @Override
   protected adp e(apk var1) {
      return adq.oC;
   }

   @Override
   protected adp dq() {
      return adq.oB;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.oD, 0.15F, 1.0F);
   }

   @Override
   public boolean c_() {
      return this.eL();
   }

   @Override
   public void a(ceh var1, dcn var2) {
      if (!_snowman.a(bup.aQ)) {
         super.a(_snowman, _snowman);
      }
   }

   @Override
   public aqq dC() {
      return aqq.c;
   }

   @Override
   public boolean d(apu var1) {
      return _snowman.a() == apw.s ? false : super.d(_snowman);
   }

   public boolean eL() {
      return (this.R.a(b) & 1) != 0;
   }

   public void t(boolean var1) {
      byte _snowman = this.R.a(b);
      if (_snowman) {
         _snowman = (byte)(_snowman | 1);
      } else {
         _snowman = (byte)(_snowman & -2);
      }

      this.R.b(b, _snowman);
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      _snowman = super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.u_().nextInt(100) == 0) {
         bdy _snowman = aqe.av.a(this.l);
         _snowman.b(this.cD(), this.cE(), this.cH(), this.p, 0.0F);
         _snowman.a(_snowman, _snowman, _snowman, null, null);
         _snowman.m(this);
      }

      if (_snowman == null) {
         _snowman = new beb.b();
         if (_snowman.ad() == aor.d && _snowman.u_().nextFloat() < 0.1F * _snowman.d()) {
            ((beb.b)_snowman).a(_snowman.u_());
         }
      }

      if (_snowman instanceof beb.b) {
         aps _snowman = ((beb.b)_snowman).a;
         if (_snowman != null) {
            this.c(new apu(_snowman, Integer.MAX_VALUE));
         }
      }

      return _snowman;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 0.65F;
   }

   static class a extends awf {
      public a(beb var1) {
         super(_snowman, 1.0, true);
      }

      @Override
      public boolean a() {
         return super.a() && !this.a.bs();
      }

      @Override
      public boolean b() {
         float _snowman = this.a.aR();
         if (_snowman >= 0.5F && this.a.cY().nextInt(100) == 0) {
            this.a.h(null);
            return false;
         } else {
            return super.b();
         }
      }

      @Override
      protected double a(aqm var1) {
         return (double)(4.0F + _snowman.cy());
      }
   }

   public static class b implements arc {
      public aps a;

      public b() {
      }

      public void a(Random var1) {
         int _snowman = _snowman.nextInt(5);
         if (_snowman <= 1) {
            this.a = apw.a;
         } else if (_snowman <= 2) {
            this.a = apw.e;
         } else if (_snowman <= 3) {
            this.a = apw.j;
         } else if (_snowman <= 4) {
            this.a = apw.n;
         }
      }
   }

   static class c<T extends aqm> extends axq<T> {
      public c(beb var1, Class<T> var2) {
         super(_snowman, _snowman, true);
      }

      @Override
      public boolean a() {
         float _snowman = this.e.aR();
         return _snowman >= 0.5F ? false : super.a();
      }
   }
}
