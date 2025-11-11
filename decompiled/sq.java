import java.io.IOException;

public class sq implements oj<sa> {
   private fx a;
   private int b;
   private boolean c;

   public sq() {
   }

   public sq(fx var1, int var2, boolean var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e();
      this.b = _snowman.i();
      this.c = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.d(this.b);
      _snowman.writeBoolean(this.c);
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   public fx b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }
}
