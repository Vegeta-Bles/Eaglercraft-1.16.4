import java.io.IOException;

public class ph implements oj<om> {
   private int a;
   private int b;
   private int c;

   public ph() {
   }

   public ph(int var1, int var2, int var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readUnsignedByte();
      this.b = _snowman.readShort();
      this.c = _snowman.readShort();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeByte(this.a);
      _snowman.writeShort(this.b);
      _snowman.writeShort(this.c);
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }
}
