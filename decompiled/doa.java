public class doa extends dot {
   private final nr a;
   private dlu b = dlu.a;
   private final dot c;
   private int p;

   public doa(dot var1, nr var2, nr var3) {
      super(_snowman);
      this.c = _snowman;
      this.a = _snowman;
   }

   @Override
   public boolean as_() {
      return false;
   }

   @Override
   protected void b() {
      this.b = dlu.a(this.o, this.a, this.k - 50);
      this.p = this.b.a() * 9;
      this.a(new dlj(this.k / 2 - 100, Math.min(this.l / 2 + this.p / 2 + 9, this.l - 30), 200, 20, new of("gui.toMenu"), var1 -> this.i.a(this.c)));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, this.l / 2 - this.p / 2 - 9 * 2, 11184810);
      this.b.a(_snowman, this.k / 2, this.l / 2 - this.p / 2);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
