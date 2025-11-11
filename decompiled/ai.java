public enum ai {
   a("task", 0, k.k),
   b("challenge", 26, k.f),
   c("goal", 52, k.k);

   private final String d;
   private final int e;
   private final k f;
   private final nr g;

   private ai(String var3, int var4, k var5) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = new of("advancements.toast." + _snowman);
   }

   public String a() {
      return this.d;
   }

   public int b() {
      return this.e;
   }

   public static ai a(String var0) {
      for (ai _snowman : values()) {
         if (_snowman.d.equals(_snowman)) {
            return _snowman;
         }
      }

      throw new IllegalArgumentException("Unknown frame type '" + _snowman + "'");
   }

   public k c() {
      return this.f;
   }

   public nr d() {
      return this.g;
   }
}
