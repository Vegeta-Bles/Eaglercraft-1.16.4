import java.io.IOException;

public class rp implements oj<om> {
   private nr a;
   private nr b;

   public rp() {
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.h();
      this.b = _snowman.h();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.a(this.b);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public nr b() {
      return this.a;
   }

   public nr c() {
      return this.b;
   }
}
