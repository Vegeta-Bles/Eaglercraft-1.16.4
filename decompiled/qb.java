import java.io.IOException;

public class qb implements oj<om> {
   private double a;
   private double b;
   private double c;
   private float d;
   private float e;

   public qb() {
   }

   public qb(aqa var1) {
      this.a = _snowman.cD();
      this.b = _snowman.cE();
      this.c = _snowman.cH();
      this.d = _snowman.p;
      this.e = _snowman.q;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readDouble();
      this.b = _snowman.readDouble();
      this.c = _snowman.readDouble();
      this.d = _snowman.readFloat();
      this.e = _snowman.readFloat();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeDouble(this.a);
      _snowman.writeDouble(this.b);
      _snowman.writeDouble(this.c);
      _snowman.writeFloat(this.d);
      _snowman.writeFloat(this.e);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public double b() {
      return this.a;
   }

   public double c() {
      return this.b;
   }

   public double d() {
      return this.c;
   }

   public float e() {
      return this.d;
   }

   public float f() {
      return this.e;
   }
}
