import java.io.IOException;

public class ekm extends ack<int[]> {
   private static final vk a = new vk("textures/colormap/grass.png");

   public ekm() {
   }

   protected int[] a(ach var1, anw var2) {
      try {
         return eko.a(_snowman, a);
      } catch (IOException var4) {
         throw new IllegalStateException("Failed to load grass color texture", var4);
      }
   }

   protected void a(int[] var1, ach var2, anw var3) {
      brv.a(_snowman);
   }
}
