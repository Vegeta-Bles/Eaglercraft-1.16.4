import java.io.IOException;

public class ekl extends ack<int[]> {
   private static final vk a = new vk("textures/colormap/foliage.png");

   public ekl() {
   }

   protected int[] a(ach var1, anw var2) {
      try {
         return eko.a(_snowman, a);
      } catch (IOException var4) {
         throw new IllegalStateException("Failed to load foliage color texture", var4);
      }
   }

   protected void a(int[] var1, ach var2, anw var3) {
      brr.a(_snowman);
   }
}
