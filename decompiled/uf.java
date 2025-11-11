import java.io.IOException;
import javax.annotation.Nullable;

public class uf implements oj<ue> {
   private int a;
   private nf b;

   public uf() {
   }

   public uf(int var1, @Nullable nf var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      if (_snowman.readBoolean()) {
         int _snowman = _snowman.readableBytes();
         if (_snowman < 0 || _snowman > 1048576) {
            throw new IOException("Payload may not be larger than 1048576 bytes");
         }

         this.b = new nf(_snowman.readBytes(_snowman));
      } else {
         this.b = null;
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      if (this.b != null) {
         _snowman.writeBoolean(true);
         _snowman.writeBytes(this.b.copy());
      } else {
         _snowman.writeBoolean(false);
      }
   }

   public void a(ue var1) {
      _snowman.a(this);
   }
}
