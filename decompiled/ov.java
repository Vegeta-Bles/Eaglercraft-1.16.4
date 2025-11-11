import java.io.IOException;

public class ov implements oj<om> {
   private int a;
   private fx b;
   private int c;

   public ov() {
   }

   public ov(int var1, fx var2, int var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.e();
      this.c = _snowman.readUnsignedByte();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.a(this.b);
      _snowman.writeByte(this.c);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public fx c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }
}
