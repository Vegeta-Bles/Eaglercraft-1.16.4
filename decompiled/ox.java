import java.io.IOException;

public class ox implements oj<om> {
   private fx a;
   private int b;
   private int c;
   private buo d;

   public ox() {
   }

   public ox(fx var1, buo var2, int var3, int var4) {
      this.a = _snowman;
      this.d = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e();
      this.b = _snowman.readUnsignedByte();
      this.c = _snowman.readUnsignedByte();
      this.d = gm.Q.a(_snowman.i());
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.writeByte(this.b);
      _snowman.writeByte(this.c);
      _snowman.d(gm.Q.a(this.d));
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

   public int d() {
      return this.c;
   }

   public buo e() {
      return this.d;
   }
}
