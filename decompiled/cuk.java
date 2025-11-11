public class cuk extends cgb {
   public cuk() {
      super(128);
   }

   public cuk(cgb var1, int var2) {
      super(128);
      System.arraycopy(_snowman.a(), _snowman * 128, this.a, 0, 128);
   }

   @Override
   protected int b(int var1, int var2, int var3) {
      return _snowman << 4 | _snowman;
   }

   @Override
   public byte[] a() {
      byte[] _snowman = new byte[2048];

      for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
         System.arraycopy(this.a, 0, _snowman, _snowmanx * 128, 128);
      }

      return _snowman;
   }
}
