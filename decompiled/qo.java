import java.io.IOException;

public class qo implements oj<om> {
   private String a;
   private String b;

   public qo() {
   }

   public qo(String var1, String var2) {
      this.a = _snowman;
      this.b = _snowman;
      if (_snowman.length() > 40) {
         throw new IllegalArgumentException("Hash is too long (max 40, was " + _snowman.length() + ")");
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e(32767);
      this.b = _snowman.e(40);
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.a(this.b);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public String b() {
      return this.a;
   }

   public String c() {
      return this.b;
   }
}
