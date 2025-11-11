import java.io.IOException;

public class oo implements oj<om> {
   private int a;
   private double b;
   private double c;
   private double d;
   private int e;

   public oo() {
   }

   public oo(aqg var1) {
      this.a = _snowman.Y();
      this.b = _snowman.cD();
      this.c = _snowman.cE();
      this.d = _snowman.cH();
      this.e = _snowman.g();
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.readDouble();
      this.c = _snowman.readDouble();
      this.d = _snowman.readDouble();
      this.e = _snowman.readShort();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.writeDouble(this.b);
      _snowman.writeDouble(this.c);
      _snowman.writeDouble(this.d);
      _snowman.writeShort(this.e);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public double c() {
      return this.b;
   }

   public double d() {
      return this.c;
   }

   public double e() {
      return this.d;
   }

   public int f() {
      return this.e;
   }
}
