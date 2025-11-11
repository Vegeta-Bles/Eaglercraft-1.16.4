import java.io.IOException;

public class rs implements oj<om> {
   private int a;
   private double b;
   private double c;
   private double d;
   private byte e;
   private byte f;
   private boolean g;

   public rs() {
   }

   public rs(aqa var1) {
      this.a = _snowman.Y();
      this.b = _snowman.cD();
      this.c = _snowman.cE();
      this.d = _snowman.cH();
      this.e = (byte)((int)(_snowman.p * 256.0F / 360.0F));
      this.f = (byte)((int)(_snowman.q * 256.0F / 360.0F));
      this.g = _snowman.ao();
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.readDouble();
      this.c = _snowman.readDouble();
      this.d = _snowman.readDouble();
      this.e = _snowman.readByte();
      this.f = _snowman.readByte();
      this.g = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.writeDouble(this.b);
      _snowman.writeDouble(this.c);
      _snowman.writeDouble(this.d);
      _snowman.writeByte(this.e);
      _snowman.writeByte(this.f);
      _snowman.writeBoolean(this.g);
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

   public byte f() {
      return this.e;
   }

   public byte g() {
      return this.f;
   }

   public boolean h() {
      return this.g;
   }
}
