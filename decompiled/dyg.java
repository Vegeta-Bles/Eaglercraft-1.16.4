import java.util.Random;
import java.util.stream.Stream;

public abstract class dyg {
   private static final dci a = new dci(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   protected final dwt c;
   protected double d;
   protected double e;
   protected double f;
   protected double g;
   protected double h;
   protected double i;
   protected double j;
   protected double k;
   protected double l;
   private dci b = a;
   protected boolean m;
   protected boolean n = true;
   private boolean B;
   protected boolean o;
   protected float p = 0.6F;
   protected float q = 1.8F;
   protected final Random r = new Random();
   protected int s;
   protected int t;
   protected float u;
   protected float v = 1.0F;
   protected float w = 1.0F;
   protected float x = 1.0F;
   protected float y = 1.0F;
   protected float z;
   protected float A;

   protected dyg(dwt var1, double var2, double var4, double var6) {
      this.c = _snowman;
      this.a(0.2F, 0.2F);
      this.b(_snowman, _snowman, _snowman);
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.t = (int)(4.0F / (this.r.nextFloat() * 0.9F + 0.1F));
   }

   public dyg(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this(_snowman, _snowman, _snowman, _snowman);
      this.j = _snowman + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.k = _snowman + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.l = _snowman + (Math.random() * 2.0 - 1.0) * 0.4F;
      float _snowman = (float)(Math.random() + Math.random() + 1.0) * 0.15F;
      float _snowmanx = afm.a(this.j * this.j + this.k * this.k + this.l * this.l);
      this.j = this.j / (double)_snowmanx * (double)_snowman * 0.4F;
      this.k = this.k / (double)_snowmanx * (double)_snowman * 0.4F + 0.1F;
      this.l = this.l / (double)_snowmanx * (double)_snowman * 0.4F;
   }

   public dyg c(float var1) {
      this.j *= (double)_snowman;
      this.k = (this.k - 0.1F) * (double)_snowman + 0.1F;
      this.l *= (double)_snowman;
      return this;
   }

   public dyg d(float var1) {
      this.a(0.2F * _snowman, 0.2F * _snowman);
      return this;
   }

   public void a(float var1, float var2, float var3) {
      this.v = _snowman;
      this.w = _snowman;
      this.x = _snowman;
   }

   protected void e(float var1) {
      this.y = _snowman;
   }

   public void a(int var1) {
      this.t = _snowman;
   }

   public int i() {
      return this.t;
   }

   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ >= this.t) {
         this.j();
      } else {
         this.k = this.k - 0.04 * (double)this.u;
         this.a(this.j, this.k, this.l);
         this.j *= 0.98F;
         this.k *= 0.98F;
         this.l *= 0.98F;
         if (this.m) {
            this.j *= 0.7F;
            this.l *= 0.7F;
         }
      }
   }

   public abstract void a(dfq var1, djk var2, float var3);

   public abstract dyk b();

   @Override
   public String toString() {
      return this.getClass().getSimpleName()
         + ", Pos ("
         + this.g
         + ","
         + this.h
         + ","
         + this.i
         + "), RGBA ("
         + this.v
         + ","
         + this.w
         + ","
         + this.x
         + ","
         + this.y
         + "), Age "
         + this.s;
   }

   public void j() {
      this.o = true;
   }

   protected void a(float var1, float var2) {
      if (_snowman != this.p || _snowman != this.q) {
         this.p = _snowman;
         this.q = _snowman;
         dci _snowman = this.m();
         double _snowmanx = (_snowman.a + _snowman.d - (double)_snowman) / 2.0;
         double _snowmanxx = (_snowman.c + _snowman.f - (double)_snowman) / 2.0;
         this.a(new dci(_snowmanx, _snowman.b, _snowmanxx, _snowmanx + (double)this.p, _snowman.b + (double)this.q, _snowmanxx + (double)this.p));
      }
   }

   public void b(double var1, double var3, double var5) {
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      float _snowman = this.p / 2.0F;
      float _snowmanx = this.q;
      this.a(new dci(_snowman - (double)_snowman, _snowman, _snowman - (double)_snowman, _snowman + (double)_snowman, _snowman + (double)_snowmanx, _snowman + (double)_snowman));
   }

   public void a(double var1, double var3, double var5) {
      if (!this.B) {
         double _snowman = _snowman;
         double _snowmanx = _snowman;
         double _snowmanxx = _snowman;
         if (this.n && (_snowman != 0.0 || _snowman != 0.0 || _snowman != 0.0)) {
            dcn _snowmanxxx = aqa.a(null, new dcn(_snowman, _snowman, _snowman), this.m(), this.c, dcs.a(), new afo<>(Stream.empty()));
            _snowman = _snowmanxxx.b;
            _snowman = _snowmanxxx.c;
            _snowman = _snowmanxxx.d;
         }

         if (_snowman != 0.0 || _snowman != 0.0 || _snowman != 0.0) {
            this.a(this.m().d(_snowman, _snowman, _snowman));
            this.k();
         }

         if (Math.abs(_snowmanx) >= 1.0E-5F && Math.abs(_snowman) < 1.0E-5F) {
            this.B = true;
         }

         this.m = _snowmanx != _snowman && _snowmanx < 0.0;
         if (_snowman != _snowman) {
            this.j = 0.0;
         }

         if (_snowmanxx != _snowman) {
            this.l = 0.0;
         }
      }
   }

   protected void k() {
      dci _snowman = this.m();
      this.g = (_snowman.a + _snowman.d) / 2.0;
      this.h = _snowman.b;
      this.i = (_snowman.c + _snowman.f) / 2.0;
   }

   protected int a(float var1) {
      fx _snowman = new fx(this.g, this.h, this.i);
      return this.c.C(_snowman) ? eae.a(this.c, _snowman) : 0;
   }

   public boolean l() {
      return !this.o;
   }

   public dci m() {
      return this.b;
   }

   public void a(dci var1) {
      this.b = _snowman;
   }
}
