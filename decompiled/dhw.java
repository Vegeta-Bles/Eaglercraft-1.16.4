public class dhw extends eoo {
   private final dot a;
   private nr b;
   private nr c;

   public dhw(dhi var1, dot var2) {
      this.a = _snowman;
      this.a(_snowman);
   }

   public dhw(nr var1, dot var2) {
      this.a = _snowman;
      this.a(_snowman);
   }

   public dhw(nr var1, nr var2, dot var3) {
      this.a = _snowman;
      this.a(_snowman, _snowman);
   }

   private void a(dhi var1) {
      if (_snowman.c == -1) {
         this.b = new oe("An error occurred (" + _snowman.a + "):");
         this.c = new oe(_snowman.b);
      } else {
         this.b = new oe("Realms (" + _snowman.c + "):");
         String _snowman = "mco.errorMessage." + _snowman.c;
         this.c = (nr)(ekx.a(_snowman) ? new of(_snowman) : nr.a(_snowman.d));
      }
   }

   private void a(nr var1) {
      this.b = new oe("An error occurred: ");
      this.c = _snowman;
   }

   private void a(nr var1, nr var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void b() {
      eoj.a(this.b.getString() + ": " + this.c.getString());
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l - 52, 200, 20, new oe("Ok"), var1 -> this.i.a(this.a))));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.b, this.k / 2, 80, 16777215);
      a(_snowman, this.o, this.c, this.k / 2, 100, 16711680);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
