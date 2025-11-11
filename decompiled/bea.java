import java.util.EnumSet;
import javax.annotation.Nullable;

public abstract class bea extends bcy {
   private static final us<Byte> bo = uv.a(bea.class, uu.a);
   protected int b;
   private bea.a bp = bea.a.a;

   protected bea(aqe<? extends bea> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bo, (byte)0);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.b = _snowman.h("SpellTicks");
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("SpellTicks", this.b);
   }

   @Override
   public bcy.a m() {
      if (this.eW()) {
         return bcy.a.c;
      } else {
         return this.fd() ? bcy.a.g : bcy.a.a;
      }
   }

   public boolean eW() {
      return this.l.v ? this.R.a(bo) > 0 : this.b > 0;
   }

   public void a(bea.a var1) {
      this.bp = _snowman;
      this.R.b(bo, (byte)_snowman.g);
   }

   protected bea.a eX() {
      return !this.l.v ? this.bp : bea.a.a(this.R.a(bo));
   }

   @Override
   protected void N() {
      super.N();
      if (this.b > 0) {
         this.b--;
      }
   }

   @Override
   public void j() {
      super.j();
      if (this.l.v && this.eW()) {
         bea.a _snowman = this.eX();
         double _snowmanx = _snowman.h[0];
         double _snowmanxx = _snowman.h[1];
         double _snowmanxxx = _snowman.h[2];
         float _snowmanxxxx = this.aA * (float) (Math.PI / 180.0) + afm.b((float)this.K * 0.6662F) * 0.25F;
         float _snowmanxxxxx = afm.b(_snowmanxxxx);
         float _snowmanxxxxxx = afm.a(_snowmanxxxx);
         this.l.a(hh.u, this.cD() + (double)_snowmanxxxxx * 0.6, this.cE() + 1.8, this.cH() + (double)_snowmanxxxxxx * 0.6, _snowmanx, _snowmanxx, _snowmanxxx);
         this.l.a(hh.u, this.cD() - (double)_snowmanxxxxx * 0.6, this.cE() + 1.8, this.cH() - (double)_snowmanxxxxxx * 0.6, _snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   protected int eY() {
      return this.b;
   }

   protected abstract adp eM();

   public static enum a {
      a(0, 0.0, 0.0, 0.0),
      b(1, 0.7, 0.7, 0.8),
      c(2, 0.4, 0.3, 0.35),
      d(3, 0.7, 0.5, 0.2),
      e(4, 0.3, 0.3, 0.8),
      f(5, 0.1, 0.1, 0.2);

      private final int g;
      private final double[] h;

      private a(int var3, double var4, double var6, double var8) {
         this.g = _snowman;
         this.h = new double[]{_snowman, _snowman, _snowman};
      }

      public static bea.a a(int var0) {
         for (bea.a _snowman : values()) {
            if (_snowman == _snowman.g) {
               return _snowman;
            }
         }

         return a;
      }
   }

   public class b extends avv {
      public b() {
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }

      @Override
      public boolean a() {
         return bea.this.eY() > 0;
      }

      @Override
      public void c() {
         super.c();
         bea.this.bj.o();
      }

      @Override
      public void d() {
         super.d();
         bea.this.a(bea.a.a);
      }

      @Override
      public void e() {
         if (bea.this.A() != null) {
            bea.this.t().a(bea.this.A(), (float)bea.this.Q(), (float)bea.this.O());
         }
      }
   }

   public abstract class c extends avv {
      protected int b;
      protected int c;

      protected c() {
      }

      @Override
      public boolean a() {
         aqm _snowman = bea.this.A();
         if (_snowman == null || !_snowman.aX()) {
            return false;
         } else {
            return bea.this.eW() ? false : bea.this.K >= this.c;
         }
      }

      @Override
      public boolean b() {
         aqm _snowman = bea.this.A();
         return _snowman != null && _snowman.aX() && this.b > 0;
      }

      @Override
      public void c() {
         this.b = this.m();
         bea.this.b = this.g();
         this.c = bea.this.K + this.h();
         adp _snowman = this.k();
         if (_snowman != null) {
            bea.this.a(_snowman, 1.0F, 1.0F);
         }

         bea.this.a(this.l());
      }

      @Override
      public void e() {
         this.b--;
         if (this.b == 0) {
            this.j();
            bea.this.a(bea.this.eM(), 1.0F, 1.0F);
         }
      }

      protected abstract void j();

      protected int m() {
         return 20;
      }

      protected abstract int g();

      protected abstract int h();

      @Nullable
      protected abstract adp k();

      protected abstract bea.a l();
   }
}
