import java.io.IOException;

public class tk implements oj<sa> {
   private fx a;
   private String b;
   private boolean c;
   private boolean d;
   private boolean e;
   private cco.a f;

   public tk() {
   }

   public tk(fx var1, String var2, cco.a var3, boolean var4, boolean var5, boolean var6) {
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
      this.b = _snowman.e(32767);
      this.f = _snowman.a(cco.a.class);
      int _snowman = _snowman.readByte();
      this.c = (_snowman & 1) != 0;
      this.d = (_snowman & 2) != 0;
      this.e = (_snowman & 4) != 0;
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.a(this.b);
      _snowman.a(this.f);
      int _snowman = 0;
      if (this.c) {
         _snowman |= 1;
      }

      if (this.d) {
         _snowman |= 2;
      }

      if (this.e) {
         _snowman |= 4;
      }

      _snowman.writeByte(_snowman);
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   public fx b() {
      return this.a;
   }

   public String c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }

   public boolean e() {
      return this.d;
   }

   public boolean f() {
      return this.e;
   }

   public cco.a g() {
      return this.f;
   }
}
