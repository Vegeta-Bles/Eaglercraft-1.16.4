public enum fv {
   a {
      @Override
      public int a(int var1, int var2, int var3, gc.a var4) {
         return _snowman.a(_snowman, _snowman, _snowman);
      }

      @Override
      public gc.a a(gc.a var1) {
         return _snowman;
      }

      @Override
      public fv a() {
         return this;
      }
   },
   b {
      @Override
      public int a(int var1, int var2, int var3, gc.a var4) {
         return _snowman.a(_snowman, _snowman, _snowman);
      }

      @Override
      public gc.a a(gc.a var1) {
         return d[Math.floorMod(_snowman.ordinal() + 1, 3)];
      }

      @Override
      public fv a() {
         return c;
      }
   },
   c {
      @Override
      public int a(int var1, int var2, int var3, gc.a var4) {
         return _snowman.a(_snowman, _snowman, _snowman);
      }

      @Override
      public gc.a a(gc.a var1) {
         return d[Math.floorMod(_snowman.ordinal() - 1, 3)];
      }

      @Override
      public fv a() {
         return b;
      }
   };

   public static final gc.a[] d = gc.a.values();
   public static final fv[] e = values();

   private fv() {
   }

   public abstract int a(int var1, int var2, int var3, gc.a var4);

   public abstract gc.a a(gc.a var1);

   public abstract fv a();

   public static fv a(gc.a var0, gc.a var1) {
      return e[Math.floorMod(_snowman.ordinal() - _snowman.ordinal(), 3)];
   }
}
