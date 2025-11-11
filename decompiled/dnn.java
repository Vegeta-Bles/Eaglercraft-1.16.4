public class dnn extends dot {
   private final Runnable c;
   protected final nr a;
   private dlu p = dlu.a;
   protected final nr b;
   private int q;

   public dnn(Runnable var1, nr var2, nr var3) {
      this(_snowman, _snowman, _snowman, nq.h);
   }

   public dnn(Runnable var1, nr var2, nr var3, nr var4) {
      super(_snowman);
      this.c = _snowman;
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   protected void b() {
      super.b();
      this.a(new dlj(this.k / 2 - 100, this.l / 6 + 168, 200, 20, this.b, var1 -> this.c.run()));
      this.p = dlu.a(this.o, this.a, this.k - 50);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 70, 16777215);
      this.p.a(_snowman, this.k / 2, 90);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void d() {
      super.d();
      if (--this.q == 0) {
         for (dlh _snowman : this.m) {
            _snowman.o = true;
         }
      }
   }
}
