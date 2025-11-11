import java.io.IOException;

public class tv implements oj<tw> {
   private int a;
   private String b;
   private int c;
   private ne d;

   public tv() {
   }

   public tv(String var1, int var2, ne var3) {
      this.a = w.a().getProtocolVersion();
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.e(255);
      this.c = _snowman.readUnsignedShort();
      this.d = ne.a(_snowman.i());
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.a(this.b);
      _snowman.writeShort(this.c);
      _snowman.d(this.d.a());
   }

   public void a(tw var1) {
      _snowman.a(this);
   }

   public ne b() {
      return this.d;
   }

   public int c() {
      return this.a;
   }
}
