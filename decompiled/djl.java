public enum djl {
   a(true, false),
   b(false, false),
   c(false, true);

   private static final djl[] d = values();
   private boolean e;
   private boolean f;

   private djl(boolean var3, boolean var4) {
      this.e = _snowman;
      this.f = _snowman;
   }

   public boolean a() {
      return this.e;
   }

   public boolean b() {
      return this.f;
   }

   public djl c() {
      return d[(this.ordinal() + 1) % d.length];
   }
}
