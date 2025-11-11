import java.io.IOException;

public class pn implements oj<om> {
   private int a;
   private byte b;

   public pn() {
   }

   public pn(aqa var1, byte var2) {
      this.a = _snowman.Y();
      this.b = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readInt();
      this.b = _snowman.readByte();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeInt(this.a);
      _snowman.writeByte(this.b);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public aqa a(brx var1) {
      return _snowman.a(this.a);
   }

   public byte b() {
      return this.b;
   }
}
