import java.io.IOException;

public class rf implements oj<om> {
   private float a;
   private int b;
   private float c;

   public rf() {
   }

   public rf(float var1, int var2, float var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readFloat();
      this.b = _snowman.i();
      this.c = _snowman.readFloat();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeFloat(this.a);
      _snowman.d(this.b);
      _snowman.writeFloat(this.c);
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

   public float d() {
      return this.c;
   }
}
