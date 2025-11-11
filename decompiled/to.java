import java.io.IOException;

public class to implements oj<sa> {
   private fx a;
   private cdj.a b;
   private cfo c;
   private String d;
   private fx e;
   private fx f;
   private byg g;
   private bzm h;
   private String i;
   private boolean j;
   private boolean k;
   private boolean l;
   private float m;
   private long n;

   public to() {
   }

   public to(
      fx var1,
      cdj.a var2,
      cfo var3,
      String var4,
      fx var5,
      fx var6,
      byg var7,
      bzm var8,
      String var9,
      boolean var10,
      boolean var11,
      boolean var12,
      float var13,
      long var14
   ) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.m = _snowman;
      this.n = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e();
      this.b = _snowman.a(cdj.a.class);
      this.c = _snowman.a(cfo.class);
      this.d = _snowman.e(32767);
      int _snowman = 48;
      this.e = new fx(afm.a(_snowman.readByte(), -48, 48), afm.a(_snowman.readByte(), -48, 48), afm.a(_snowman.readByte(), -48, 48));
      int _snowmanx = 48;
      this.f = new fx(afm.a(_snowman.readByte(), 0, 48), afm.a(_snowman.readByte(), 0, 48), afm.a(_snowman.readByte(), 0, 48));
      this.g = _snowman.a(byg.class);
      this.h = _snowman.a(bzm.class);
      this.i = _snowman.e(12);
      this.m = afm.a(_snowman.readFloat(), 0.0F, 1.0F);
      this.n = _snowman.j();
      int _snowmanxx = _snowman.readByte();
      this.j = (_snowmanxx & 1) != 0;
      this.k = (_snowmanxx & 2) != 0;
      this.l = (_snowmanxx & 4) != 0;
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.a(this.b);
      _snowman.a(this.c);
      _snowman.a(this.d);
      _snowman.writeByte(this.e.u());
      _snowman.writeByte(this.e.v());
      _snowman.writeByte(this.e.w());
      _snowman.writeByte(this.f.u());
      _snowman.writeByte(this.f.v());
      _snowman.writeByte(this.f.w());
      _snowman.a(this.g);
      _snowman.a(this.h);
      _snowman.a(this.i);
      _snowman.writeFloat(this.m);
      _snowman.b(this.n);
      int _snowman = 0;
      if (this.j) {
         _snowman |= 1;
      }

      if (this.k) {
         _snowman |= 2;
      }

      if (this.l) {
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

   public cdj.a c() {
      return this.b;
   }

   public cfo d() {
      return this.c;
   }

   public String e() {
      return this.d;
   }

   public fx f() {
      return this.e;
   }

   public fx g() {
      return this.f;
   }

   public byg h() {
      return this.g;
   }

   public bzm i() {
      return this.h;
   }

   public String j() {
      return this.i;
   }

   public boolean k() {
      return this.j;
   }

   public boolean l() {
      return this.k;
   }

   public boolean m() {
      return this.l;
   }

   public float n() {
      return this.m;
   }

   public long o() {
      return this.n;
   }
}
