import java.io.IOException;

public class sm implements oj<sa> {
   public static final vk a = new vk("brand");
   private vk b;
   private nf c;

   public sm() {
   }

   public sm(vk var1, nf var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.b = _snowman.p();
      int _snowman = _snowman.readableBytes();
      if (_snowman >= 0 && _snowman <= 32767) {
         this.c = new nf(_snowman.readBytes(_snowman));
      } else {
         throw new IOException("Payload may not be larger than 32767 bytes");
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.b);
      _snowman.writeBytes(this.c);
   }

   public void a(sa var1) {
      _snowman.a(this);
      if (this.c != null) {
         this.c.release();
      }
   }
}
