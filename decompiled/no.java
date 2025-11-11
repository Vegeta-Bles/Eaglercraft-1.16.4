public enum no {
   a((byte)0, false),
   b((byte)1, true),
   c((byte)2, true);

   private final byte d;
   private final boolean e;

   private no(byte var3, boolean var4) {
      this.d = _snowman;
      this.e = _snowman;
   }

   public byte a() {
      return this.d;
   }

   public static no a(byte var0) {
      for (no _snowman : values()) {
         if (_snowman == _snowman.d) {
            return _snowman;
         }
      }

      return a;
   }

   public boolean b() {
      return this.e;
   }
}
