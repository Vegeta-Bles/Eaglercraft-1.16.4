import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ade {
   private final ByteArrayOutputStream a;
   private final DataOutputStream b;

   public ade(int var1) {
      this.a = new ByteArrayOutputStream(_snowman);
      this.b = new DataOutputStream(this.a);
   }

   public void a(byte[] var1) throws IOException {
      this.b.write(_snowman, 0, _snowman.length);
   }

   public void a(String var1) throws IOException {
      this.b.writeBytes(_snowman);
      this.b.write(0);
   }

   public void a(int var1) throws IOException {
      this.b.write(_snowman);
   }

   public void a(short var1) throws IOException {
      this.b.writeShort(Short.reverseBytes(_snowman));
   }

   public byte[] a() {
      return this.a.toByteArray();
   }

   public void b() {
      this.a.reset();
   }
}
