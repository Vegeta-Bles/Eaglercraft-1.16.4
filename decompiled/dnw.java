public class dnw extends dot {
   private dlu a;
   private final Runnable b;

   public dnw(Runnable var1) {
      super(new of("datapackFailure.title"));
      this.a = dlu.a;
      this.b = _snowman;
   }

   @Override
   protected void b() {
      super.b();
      this.a = dlu.a(this.o, this.w(), this.k - 50);
      this.a(new dlj(this.k / 2 - 155, this.l / 6 + 96, 150, 20, new of("datapackFailure.safeMode"), var1 -> this.b.run()));
      this.a(new dlj(this.k / 2 - 155 + 160, this.l / 6 + 96, 150, 20, new of("gui.toTitle"), var1 -> this.i.a(null)));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.a.a(_snowman, this.k / 2, 70);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean as_() {
      return false;
   }
}
