import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class deq {
   public static synchronized ByteBuffer a(int var0) {
      return ByteBuffer.allocateDirect(_snowman).order(ByteOrder.nativeOrder());
   }

   public static FloatBuffer b(int var0) {
      return a(_snowman << 2).asFloatBuffer();
   }
}
