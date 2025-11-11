import java.io.IOException;

public class tz implements oj<ty> {
   private int a;
   private vk b;
   private nf c;

   public tz() {
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.p();
      int _snowman = _snowman.readableBytes();
      if (_snowman >= 0 && _snowman <= 1048576) {
         this.c = new nf(_snowman.readBytes(_snowman));
      } else {
         throw new IOException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.a(this.b);
      _snowman.writeBytes(this.c.copy());
   }

   public void a(ty var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }
}
