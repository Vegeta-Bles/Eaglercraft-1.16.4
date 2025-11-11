import java.io.IOException;

public class ow implements oj<om> {
   private fx a;
   private int b;
   private md c;

   public ow() {
   }

   public ow(fx var1, int var2, md var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e();
      this.b = _snowman.readUnsignedByte();
      this.c = _snowman.l();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.writeByte((byte)this.b);
      _snowman.a(this.c);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public fx b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public md d() {
      return this.c;
   }
}
