import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

public class dht extends eoo {
   protected BooleanConsumer a;
   private final nr b;
   private final nr c;
   private int p;

   public dht(BooleanConsumer var1, nr var2, nr var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void b() {
      this.a(new dlj(this.k / 2 - 105, j(9), 100, 20, nq.e, var1 -> this.a.accept(true)));
      this.a(new dlj(this.k / 2 + 5, j(9), 100, 20, nq.f, var1 -> this.a.accept(false)));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.b, this.k / 2, j(3), 16777215);
      a(_snowman, this.o, this.c, this.k / 2, j(5), 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void d() {
      super.d();
      if (--this.p == 0) {
         for (dlh _snowman : this.m) {
            _snowman.o = true;
         }
      }
   }
}
