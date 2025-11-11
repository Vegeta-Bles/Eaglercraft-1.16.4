import java.io.IOException;

public class tn implements oj<sa> {
   private fx a;
   private vk b;
   private vk c;
   private vk d;
   private String e;
   private ccz.a f;

   public tn() {
   }

   public tn(fx var1, vk var2, vk var3, vk var4, String var5, ccz.a var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e();
      this.b = _snowman.p();
      this.c = _snowman.p();
      this.d = _snowman.p();
      this.e = _snowman.e(32767);
      this.f = ccz.a.a(_snowman.e(32767)).orElse(ccz.a.b);
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.a(this.b);
      _snowman.a(this.c);
      _snowman.a(this.d);
      _snowman.a(this.e);
      _snowman.a(this.f.a());
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   public fx b() {
      return this.a;
   }

   public vk c() {
      return this.b;
   }

   public vk d() {
      return this.c;
   }

   public vk e() {
      return this.d;
   }

   public String f() {
      return this.e;
   }

   public ccz.a g() {
      return this.f;
   }
}
