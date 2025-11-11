import java.io.IOException;

public class rc implements oj<om> {
   private int a;
   private int b;
   private int c;
   private int d;

   public rc() {
   }

   public rc(aqa var1) {
      this(_snowman.Y(), _snowman.cC());
   }

   public rc(int var1, dcn var2) {
      this.a = _snowman;
      double _snowman = 3.9;
      double _snowmanx = afm.a(_snowman.b, -3.9, 3.9);
      double _snowmanxx = afm.a(_snowman.c, -3.9, 3.9);
      double _snowmanxxx = afm.a(_snowman.d, -3.9, 3.9);
      this.b = (int)(_snowmanx * 8000.0);
      this.c = (int)(_snowmanxx * 8000.0);
      this.d = (int)(_snowmanxxx * 8000.0);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.readShort();
      this.c = _snowman.readShort();
      this.d = _snowman.readShort();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.writeShort(this.b);
      _snowman.writeShort(this.c);
      _snowman.writeShort(this.d);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }
}
