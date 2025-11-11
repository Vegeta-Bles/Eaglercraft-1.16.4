public enum aqi {
   a(new of("options.mainHand.left")),
   b(new of("options.mainHand.right"));

   private final nr c;

   private aqi(nr var3) {
      this.c = _snowman;
   }

   public aqi a() {
      return this == a ? b : a;
   }

   @Override
   public String toString() {
      return this.c.getString();
   }

   public nr b() {
      return this.c;
   }
}
