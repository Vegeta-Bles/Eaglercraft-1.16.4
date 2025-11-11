import java.io.IOException;

public class sy implements oj<sa> {
   private boolean a;

   public sy() {
   }

   public sy(bft var1) {
      this.a = _snowman.b;
   }

   @Override
   public void a(nf var1) throws IOException {
      byte _snowman = _snowman.readByte();
      this.a = (_snowman & 2) != 0;
   }

   @Override
   public void b(nf var1) throws IOException {
      byte _snowman = 0;
      if (this.a) {
         _snowman = (byte)(_snowman | 2);
      }

      _snowman.writeByte(_snowman);
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   public boolean b() {
      return this.a;
   }
}
