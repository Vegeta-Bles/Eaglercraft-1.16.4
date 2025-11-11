import javax.annotation.Nullable;

public class cgb {
   @Nullable
   protected byte[] a;

   public cgb() {
   }

   public cgb(byte[] var1) {
      this.a = _snowman;
      if (_snowman.length != 2048) {
         throw (IllegalArgumentException)x.c(new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + _snowman.length));
      }
   }

   protected cgb(int var1) {
      this.a = new byte[_snowman];
   }

   public int a(int var1, int var2, int var3) {
      return this.b(this.b(_snowman, _snowman, _snowman));
   }

   public void a(int var1, int var2, int var3, int var4) {
      this.a(this.b(_snowman, _snowman, _snowman), _snowman);
   }

   protected int b(int var1, int var2, int var3) {
      return _snowman << 8 | _snowman << 4 | _snowman;
   }

   private int b(int var1) {
      if (this.a == null) {
         return 0;
      } else {
         int _snowman = this.d(_snowman);
         return this.c(_snowman) ? this.a[_snowman] & 15 : this.a[_snowman] >> 4 & 15;
      }
   }

   private void a(int var1, int var2) {
      if (this.a == null) {
         this.a = new byte[2048];
      }

      int _snowman = this.d(_snowman);
      if (this.c(_snowman)) {
         this.a[_snowman] = (byte)(this.a[_snowman] & 240 | _snowman & 15);
      } else {
         this.a[_snowman] = (byte)(this.a[_snowman] & 15 | (_snowman & 15) << 4);
      }
   }

   private boolean c(int var1) {
      return (_snowman & 1) == 0;
   }

   private int d(int var1) {
      return _snowman >> 1;
   }

   public byte[] a() {
      if (this.a == null) {
         this.a = new byte[2048];
      }

      return this.a;
   }

   public cgb b() {
      return this.a == null ? new cgb() : new cgb((byte[])this.a.clone());
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();

      for (int _snowmanx = 0; _snowmanx < 4096; _snowmanx++) {
         _snowman.append(Integer.toHexString(this.b(_snowmanx)));
         if ((_snowmanx & 15) == 15) {
            _snowman.append("\n");
         }

         if ((_snowmanx & 0xFF) == 255) {
            _snowman.append("\n");
         }
      }

      return _snowman.toString();
   }

   public boolean c() {
      return this.a == null;
   }
}
