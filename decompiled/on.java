import java.io.IOException;
import java.util.UUID;

public class on implements oj<om> {
   private int a;
   private UUID b;
   private double c;
   private double d;
   private double e;
   private int f;
   private int g;
   private int h;
   private int i;
   private int j;
   private aqe<?> k;
   private int l;

   public on() {
   }

   public on(int var1, UUID var2, double var3, double var5, double var7, float var9, float var10, aqe<?> var11, int var12, dcn var13) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.i = afm.d(_snowman * 256.0F / 360.0F);
      this.j = afm.d(_snowman * 256.0F / 360.0F);
      this.k = _snowman;
      this.l = _snowman;
      this.f = (int)(afm.a(_snowman.b, -3.9, 3.9) * 8000.0);
      this.g = (int)(afm.a(_snowman.c, -3.9, 3.9) * 8000.0);
      this.h = (int)(afm.a(_snowman.d, -3.9, 3.9) * 8000.0);
   }

   public on(aqa var1) {
      this(_snowman, 0);
   }

   public on(aqa var1, int var2) {
      this(_snowman.Y(), _snowman.bS(), _snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman.q, _snowman.p, _snowman.X(), _snowman, _snowman.cC());
   }

   public on(aqa var1, aqe<?> var2, int var3, fx var4) {
      this(_snowman.Y(), _snowman.bS(), (double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), _snowman.q, _snowman.p, _snowman, _snowman, _snowman.cC());
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.k();
      this.k = gm.S.a(_snowman.i());
      this.c = _snowman.readDouble();
      this.d = _snowman.readDouble();
      this.e = _snowman.readDouble();
      this.i = _snowman.readByte();
      this.j = _snowman.readByte();
      this.l = _snowman.readInt();
      this.f = _snowman.readShort();
      this.g = _snowman.readShort();
      this.h = _snowman.readShort();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.a(this.b);
      _snowman.d(gm.S.a(this.k));
      _snowman.writeDouble(this.c);
      _snowman.writeDouble(this.d);
      _snowman.writeDouble(this.e);
      _snowman.writeByte(this.i);
      _snowman.writeByte(this.j);
      _snowman.writeInt(this.l);
      _snowman.writeShort(this.f);
      _snowman.writeShort(this.g);
      _snowman.writeShort(this.h);
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

   public double g() {
      return (double)this.f / 8000.0;
   }

   public double h() {
      return (double)this.g / 8000.0;
   }

   public double i() {
      return (double)this.h / 8000.0;
   }

   public int j() {
      return this.i;
   }

   public int k() {
      return this.j;
   }

   public aqe<?> l() {
      return this.k;
   }

   public int m() {
      return this.l;
   }
}
