import javax.annotation.Nullable;

public abstract class apy extends aqu {
   private static final us<Boolean> bo = uv.a(apy.class, uu.i);
   protected int b;
   protected int c;
   protected int d;

   protected apy(aqe<? extends apy> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (_snowman == null) {
         _snowman = new apy.a(true);
      }

      apy.a _snowman = (apy.a)_snowman;
      if (_snowman.c() && _snowman.a() > 0 && this.J.nextFloat() <= _snowman.d()) {
         this.c_(-24000);
      }

      _snowman.b();
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   public abstract apy a(aag var1, apy var2);

   @Override
   protected void e() {
      super.e();
      this.R.a(bo, false);
   }

   public boolean f() {
      return false;
   }

   public int i() {
      if (this.l.v) {
         return this.R.a(bo) ? -1 : 1;
      } else {
         return this.b;
      }
   }

   public void a(int var1, boolean var2) {
      int _snowman = this.i();
      _snowman += _snowman * 20;
      if (_snowman > 0) {
         _snowman = 0;
      }

      int _snowmanx = _snowman - _snowman;
      this.c_(_snowman);
      if (_snowman) {
         this.c += _snowmanx;
         if (this.d == 0) {
            this.d = 40;
         }
      }

      if (this.i() == 0) {
         this.c_(this.c);
      }
   }

   public void a(int var1) {
      this.a(_snowman, false);
   }

   public void c_(int var1) {
      int _snowman = this.b;
      this.b = _snowman;
      if (_snowman < 0 && _snowman >= 0 || _snowman >= 0 && _snowman < 0) {
         this.R.b(bo, _snowman < 0);
         this.m();
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Age", this.i());
      _snowman.b("ForcedAge", this.c);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.c_(_snowman.h("Age"));
      this.c = _snowman.h("ForcedAge");
   }

   @Override
   public void a(us<?> var1) {
      if (bo.equals(_snowman)) {
         this.x_();
      }

      super.a(_snowman);
   }

   @Override
   public void k() {
      super.k();
      if (this.l.v) {
         if (this.d > 0) {
            if (this.d % 4 == 0) {
               this.l.a(hh.E, this.d(1.0), this.cF() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
            }

            this.d--;
         }
      } else if (this.aX()) {
         int _snowman = this.i();
         if (_snowman < 0) {
            this.c_(++_snowman);
         } else if (_snowman > 0) {
            this.c_(--_snowman);
         }
      }
   }

   @Override
   protected void m() {
   }

   @Override
   public boolean w_() {
      return this.i() < 0;
   }

   @Override
   public void a(boolean var1) {
      this.c_(_snowman ? -24000 : 0);
   }

   public static class a implements arc {
      private int a;
      private final boolean b;
      private final float c;

      private a(boolean var1, float var2) {
         this.b = _snowman;
         this.c = _snowman;
      }

      public a(boolean var1) {
         this(_snowman, 0.05F);
      }

      public a(float var1) {
         this(true, _snowman);
      }

      public int a() {
         return this.a;
      }

      public void b() {
         this.a++;
      }

      public boolean c() {
         return this.b;
      }

      public float d() {
         return this.c;
      }
   }
}
