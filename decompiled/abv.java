public enum abv {
   a("old"),
   b("new"),
   c("compatible");

   private final nr d;
   private final nr e;

   private abv(String var3) {
      this.d = new of("pack.incompatible." + _snowman).a(k.h);
      this.e = new of("pack.incompatible.confirm." + _snowman);
   }

   public boolean a() {
      return this == c;
   }

   public static abv a(int var0) {
      if (_snowman < w.a().getPackVersion()) {
         return a;
      } else {
         return _snowman > w.a().getPackVersion() ? b : c;
      }
   }

   public nr b() {
      return this.d;
   }

   public nr c() {
      return this.e;
   }
}
