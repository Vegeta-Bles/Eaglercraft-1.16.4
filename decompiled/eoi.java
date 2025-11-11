public class eoi extends eoo {
   private final nr a;
   private final nr b;
   private dlu c = dlu.a;
   private final dot p;
   private int q;

   public eoi(dot var1, nr var2, nr var3) {
      this.p = _snowman;
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void b() {
      djz _snowman = djz.C();
      _snowman.d(false);
      _snowman.P().b();
      eoj.a(this.a.getString() + ": " + this.b.getString());
      this.c = dlu.a(this.o, this.b, this.k - 50);
      this.q = this.c.a() * 9;
      this.a(new dlj(this.k / 2 - 100, this.l / 2 + this.q / 2 + 9, 200, 20, nq.h, var2 -> _snowman.a(this.p)));
   }

   @Override
   public void at_() {
      djz.C().a(this.p);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.a, this.k / 2, this.l / 2 - this.q / 2 - 9 * 2, 11184810);
      this.c.a(_snowman, this.k / 2, this.l / 2 - this.q / 2);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
