import java.io.IOException;
import java.util.UUID;

public class or implements oj<om> {
   private int a;
   private UUID b;
   private double c;
   private double d;
   private double e;
   private byte f;
   private byte g;

   public or() {
   }

   public or(bfw var1) {
      this.a = _snowman.Y();
      this.b = _snowman.eA().getId();
      this.c = _snowman.cD();
      this.d = _snowman.cE();
      this.e = _snowman.cH();
      this.f = (byte)((int)(_snowman.p * 256.0F / 360.0F));
      this.g = (byte)((int)(_snowman.q * 256.0F / 360.0F));
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.k();
      this.c = _snowman.readDouble();
      this.d = _snowman.readDouble();
      this.e = _snowman.readDouble();
      this.f = _snowman.readByte();
      this.g = _snowman.readByte();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.a(this.b);
      _snowman.writeDouble(this.c);
      _snowman.writeDouble(this.d);
      _snowman.writeDouble(this.e);
      _snowman.writeByte(this.f);
      _snowman.writeByte(this.g);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public UUID c() {
      return this.b;
   }

   public double d() {
      return this.c;
   }

   public double e() {
      return this.d;
   }

   public double f() {
      return this.e;
   }

   public byte g() {
      return this.f;
   }

   public byte h() {
      return this.g;
   }
}
