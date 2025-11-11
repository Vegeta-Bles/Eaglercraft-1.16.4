import java.io.IOException;
import java.util.UUID;

public class op implements oj<om> {
   private int a;
   private UUID b;
   private int c;
   private double d;
   private double e;
   private double f;
   private int g;
   private int h;
   private int i;
   private byte j;
   private byte k;
   private byte l;

   public op() {
   }

   public op(aqm var1) {
      this.a = _snowman.Y();
      this.b = _snowman.bS();
      this.c = gm.S.a(_snowman.X());
      this.d = _snowman.cD();
      this.e = _snowman.cE();
      this.f = _snowman.cH();
      this.j = (byte)((int)(_snowman.p * 256.0F / 360.0F));
      this.k = (byte)((int)(_snowman.q * 256.0F / 360.0F));
      this.l = (byte)((int)(_snowman.aC * 256.0F / 360.0F));
      double _snowman = 3.9;
      dcn _snowmanx = _snowman.cC();
      double _snowmanxx = afm.a(_snowmanx.b, -3.9, 3.9);
      double _snowmanxxx = afm.a(_snowmanx.c, -3.9, 3.9);
      double _snowmanxxxx = afm.a(_snowmanx.d, -3.9, 3.9);
      this.g = (int)(_snowmanxx * 8000.0);
      this.h = (int)(_snowmanxxx * 8000.0);
      this.i = (int)(_snowmanxxxx * 8000.0);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.k();
      this.c = _snowman.i();
      this.d = _snowman.readDouble();
      this.e = _snowman.readDouble();
      this.f = _snowman.readDouble();
      this.j = _snowman.readByte();
      this.k = _snowman.readByte();
      this.l = _snowman.readByte();
      this.g = _snowman.readShort();
      this.h = _snowman.readShort();
      this.i = _snowman.readShort();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.a(this.b);
      _snowman.d(this.c);
      _snowman.writeDouble(this.d);
      _snowman.writeDouble(this.e);
      _snowman.writeDouble(this.f);
      _snowman.writeByte(this.j);
      _snowman.writeByte(this.k);
      _snowman.writeByte(this.l);
      _snowman.writeShort(this.g);
      _snowman.writeShort(this.h);
      _snowman.writeShort(this.i);
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

   public int d() {
      return this.c;
   }

   public double e() {
      return this.d;
   }

   public double f() {
      return this.e;
   }

   public double g() {
      return this.f;
   }

   public int h() {
      return this.g;
   }

   public int i() {
      return this.h;
   }

   public int j() {
      return this.i;
   }

   public byte k() {
      return this.j;
   }

   public byte l() {
      return this.k;
   }

   public byte m() {
      return this.l;
   }
}
