import javax.annotation.Nullable;

public class bcw extends aqa {
   private static final us<Integer> b = uv.a(bcw.class, uu.b);
   @Nullable
   private aqm c;
   private int d = 80;

   public bcw(aqe<? extends bcw> var1, brx var2) {
      super(_snowman, _snowman);
      this.i = true;
   }

   public bcw(brx var1, double var2, double var4, double var6, @Nullable aqm var8) {
      this(aqe.am, _snowman);
      this.d(_snowman, _snowman, _snowman);
      double _snowman = _snowman.t.nextDouble() * (float) (Math.PI * 2);
      this.n(-Math.sin(_snowman) * 0.02, 0.2F, -Math.cos(_snowman) * 0.02);
      this.a(80);
      this.m = _snowman;
      this.n = _snowman;
      this.o = _snowman;
      this.c = _snowman;
   }

   @Override
   protected void e() {
      this.R.a(b, 80);
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   public boolean aT() {
      return !this.y;
   }

   @Override
   public void j() {
      if (!this.aB()) {
         this.f(this.cC().b(0.0, -0.04, 0.0));
      }

      this.a(aqr.a, this.cC());
      this.f(this.cC().a(0.98));
      if (this.t) {
         this.f(this.cC().d(0.7, -0.5, 0.7));
      }

      this.d--;
      if (this.d <= 0) {
         this.ad();
         if (!this.l.v) {
            this.k();
         }
      } else {
         this.aK();
         if (this.l.v) {
            this.l.a(hh.S, this.cD(), this.cE() + 0.5, this.cH(), 0.0, 0.0, 0.0);
         }
      }
   }

   private void k() {
      float _snowman = 4.0F;
      this.l.a(this, this.cD(), this.e(0.0625), this.cH(), 4.0F, brp.a.b);
   }

   @Override
   protected void b(md var1) {
      _snowman.a("Fuse", (short)this.i());
   }

   @Override
   protected void a(md var1) {
      this.a(_snowman.g("Fuse"));
   }

   @Nullable
   public aqm g() {
      return this.c;
   }

   @Override
   protected float a(aqx var1, aqb var2) {
      return 0.15F;
   }

   public void a(int var1) {
      this.R.b(b, _snowman);
      this.d = _snowman;
   }

   @Override
   public void a(us<?> var1) {
      if (b.equals(_snowman)) {
         this.d = this.h();
      }
   }

   public int h() {
      return this.R.a(b);
   }

   public int i() {
      return this.d;
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }
}
