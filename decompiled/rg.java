import java.io.IOException;

public class rg implements oj<om> {
   private String a;
   private nr b;
   private ddq.a c;
   private int d;

   public rg() {
   }

   public rg(ddk var1, int var2) {
      this.a = _snowman.b();
      this.b = _snowman.d();
      this.c = _snowman.f();
      this.d = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e(16);
      this.d = _snowman.readByte();
      if (this.d == 0 || this.d == 2) {
         this.b = _snowman.h();
         this.c = _snowman.a(ddq.a.class);
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.writeByte(this.d);
      if (this.d == 0 || this.d == 2) {
         _snowman.a(this.b);
         _snowman.a(this.c);
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public String b() {
      return this.a;
   }

   public nr c() {
      return this.b;
   }

   public int d() {
      return this.d;
   }

   public ddq.a e() {
      return this.c;
   }
}
