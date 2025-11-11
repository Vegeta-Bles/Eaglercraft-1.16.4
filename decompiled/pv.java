import java.io.IOException;

public class pv implements oj<om> {
   private double a;
   private double b;
   private double c;
   private float d;
   private float e;
   private float f;
   private float g;
   private int h;
   private boolean i;
   private hf j;

   public pv() {
   }

   public <T extends hf> pv(T var1, boolean var2, double var3, double var5, double var7, float var9, float var10, float var11, float var12, int var13) {
      this.j = _snowman;
      this.i = _snowman;
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      hg<?> _snowman = gm.V.a(_snowman.readInt());
      if (_snowman == null) {
         _snowman = hh.c;
      }

      this.i = _snowman.readBoolean();
      this.a = _snowman.readDouble();
      this.b = _snowman.readDouble();
      this.c = _snowman.readDouble();
      this.d = _snowman.readFloat();
      this.e = _snowman.readFloat();
      this.f = _snowman.readFloat();
      this.g = _snowman.readFloat();
      this.h = _snowman.readInt();
      this.j = this.a(_snowman, (hg<hf>)_snowman);
   }

   private <T extends hf> T a(nf var1, hg<T> var2) {
      return _snowman.d().b(_snowman, _snowman);
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeInt(gm.V.a(this.j.b()));
      _snowman.writeBoolean(this.i);
      _snowman.writeDouble(this.a);
      _snowman.writeDouble(this.b);
      _snowman.writeDouble(this.c);
      _snowman.writeFloat(this.d);
      _snowman.writeFloat(this.e);
      _snowman.writeFloat(this.f);
      _snowman.writeFloat(this.g);
      _snowman.writeInt(this.h);
      this.j.a(_snowman);
   }

   public boolean b() {
      return this.i;
   }

   public double c() {
      return this.a;
   }

   public double d() {
      return this.b;
   }

   public double e() {
      return this.c;
   }

   public float f() {
      return this.d;
   }

   public float g() {
      return this.e;
   }

   public float h() {
      return this.f;
   }

   public float i() {
      return this.g;
   }

   public int j() {
      return this.h;
   }

   public hf k() {
      return this.j;
   }

   public void a(om var1) {
      _snowman.a(this);
   }
}
