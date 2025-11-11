public class dfn extends dfl {
   private final dfq g;
   private final b h;
   private final a i;
   private float j;
   private float k;
   private float l;
   private int m;
   private int n;
   private int o;
   private float p;
   private float q;
   private float r;

   public dfn(dfq var1, b var2, a var3) {
      this.g = _snowman;
      this.h = _snowman.d();
      this.h.c();
      this.i = _snowman.d();
      this.i.f();
      this.a();
   }

   private void a() {
      this.j = 0.0F;
      this.k = 0.0F;
      this.l = 0.0F;
      this.m = 0;
      this.n = 10;
      this.o = 15728880;
      this.p = 0.0F;
      this.q = 1.0F;
      this.r = 0.0F;
   }

   @Override
   public void d() {
      g _snowman = new g(this.p, this.q, this.r);
      _snowman.a(this.i);
      gc _snowmanx = gc.a(_snowman.a(), _snowman.b(), _snowman.c());
      h _snowmanxx = new h(this.j, this.k, this.l, 1.0F);
      _snowmanxx.a(this.h);
      _snowmanxx.a(g.d.a(180.0F));
      _snowmanxx.a(g.b.a(-90.0F));
      _snowmanxx.a(_snowmanx.b());
      float _snowmanxxx = -_snowmanxx.a();
      float _snowmanxxxx = -_snowmanxx.b();
      this.g
         .a((double)this.j, (double)this.k, (double)this.l)
         .a(1.0F, 1.0F, 1.0F, 1.0F)
         .a(_snowmanxxx, _snowmanxxxx)
         .a(this.m, this.n)
         .a(this.o)
         .b(this.p, this.q, this.r)
         .d();
      this.a();
   }

   @Override
   public dfq a(double var1, double var3, double var5) {
      this.j = (float)_snowman;
      this.k = (float)_snowman;
      this.l = (float)_snowman;
      return this;
   }

   @Override
   public dfq a(int var1, int var2, int var3, int var4) {
      return this;
   }

   @Override
   public dfq a(float var1, float var2) {
      return this;
   }

   @Override
   public dfq a(int var1, int var2) {
      this.m = _snowman;
      this.n = _snowman;
      return this;
   }

   @Override
   public dfq b(int var1, int var2) {
      this.o = _snowman | _snowman << 16;
      return this;
   }

   @Override
   public dfq b(float var1, float var2, float var3) {
      this.p = _snowman;
      this.q = _snowman;
      this.r = _snowman;
      return this;
   }
}
