public abstract class biq {
   private int a;

   public biq() {
   }

   public static biq a(final bil var0, final int var1) {
      return new biq() {
         @Override
         public int b() {
            return _snowman.a(_snowman);
         }

         @Override
         public void a(int var1x) {
            _snowman.a(_snowman, _snowman);
         }
      };
   }

   public static biq a(final int[] var0, final int var1) {
      return new biq() {
         @Override
         public int b() {
            return _snowman[_snowman];
         }

         @Override
         public void a(int var1x) {
            _snowman[_snowman] = _snowman;
         }
      };
   }

   public static biq a() {
      return new biq() {
         private int a;

         @Override
         public int b() {
            return this.a;
         }

         @Override
         public void a(int var1) {
            this.a = _snowman;
         }
      };
   }

   public abstract int b();

   public abstract void a(int var1);

   public boolean c() {
      int _snowman = this.b();
      boolean _snowmanx = _snowman != this.a;
      this.a = _snowman;
      return _snowmanx;
   }
}
