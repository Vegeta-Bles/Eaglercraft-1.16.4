import java.io.IOException;

public class tp implements oj<sa> {
   private fx a;
   private String[] b;

   public tp() {
   }

   public tp(fx var1, String var2, String var3, String var4, String var5) {
      this.a = _snowman;
      this.b = new String[]{_snowman, _snowman, _snowman, _snowman};
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e();
      this.b = new String[4];

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         this.b[_snowman] = _snowman.e(384);
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         _snowman.a(this.b[_snowman]);
      }
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   public fx b() {
      return this.a;
   }

   public String[] c() {
      return this.b;
   }
}
