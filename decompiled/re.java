import java.io.IOException;

public class re implements oj<om> {
   private float a;
   private int b;
   private int c;

   public re() {
   }

   public re(float var1, int var2, int var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readFloat();
      this.c = _snowman.i();
      this.b = _snowman.i();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeFloat(this.a);
      _snowman.d(this.c);
      _snowman.d(this.b);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public float b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }
}
