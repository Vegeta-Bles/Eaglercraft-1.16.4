import com.mojang.blaze3d.systems.RenderSystem;

public class dql extends dqe<biz> implements drv {
   private static final vk B = new vk("textures/gui/recipe_button.png");
   private float C;
   private float D;
   private final drp E = new drp();
   private boolean F;
   private boolean G;
   private boolean H;

   public dql(bfw var1) {
      super(_snowman.bo, _snowman.bm, new of("container.crafting"));
      this.n = true;
      this.p = 97;
   }

   @Override
   public void d() {
      if (this.i.q.g()) {
         this.i.a(new dqc(this.i.s));
      } else {
         this.E.g();
      }
   }

   @Override
   protected void b() {
      if (this.i.q.g()) {
         this.i.a(new dqc(this.i.s));
      } else {
         super.b();
         this.G = this.k < 379;
         this.E.a(this.k, this.l, this.i, this.G, this.t);
         this.F = true;
         this.w = this.E.a(this.G, this.k, this.b);
         this.e.add(this.E);
         this.b(this.E);
         this.a(new dlr(this.w + 104, this.l / 2 - 22, 20, 18, 0, 0, 19, B, var1 -> {
            this.E.a(this.G);
            this.E.e();
            this.w = this.E.a(this.G, this.k, this.b);
            ((dlr)var1).a(this.w + 104, this.l / 2 - 22);
            this.H = true;
         }));
      }
   }

   @Override
   protected void b(dfm var1, int var2, int var3) {
      this.o.b(_snowman, this.d, (float)this.p, (float)this.q, 4210752);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.A = !this.E.f();
      if (this.E.f() && this.G) {
         this.a(_snowman, _snowman, _snowman, _snowman);
         this.E.a(_snowman, _snowman, _snowman, _snowman);
      } else {
         this.E.a(_snowman, _snowman, _snowman, _snowman);
         super.a(_snowman, _snowman, _snowman, _snowman);
         this.E.a(_snowman, this.w, this.x, false, _snowman);
      }

      this.a(_snowman, _snowman, _snowman);
      this.E.c(_snowman, this.w, this.x, _snowman, _snowman);
      this.C = (float)_snowman;
      this.D = (float)_snowman;
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(a);
      int _snowman = this.w;
      int _snowmanx = this.x;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
      a(_snowman + 51, _snowmanx + 75, 30, (float)(_snowman + 51) - this.C, (float)(_snowmanx + 75 - 50) - this.D, this.i.s);
   }

   public static void a(int var0, int var1, int var2, float var3, float var4, aqm var5) {
      float _snowman = (float)Math.atan((double)(_snowman / 40.0F));
      float _snowmanx = (float)Math.atan((double)(_snowman / 40.0F));
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)_snowman, (float)_snowman, 1050.0F);
      RenderSystem.scalef(1.0F, 1.0F, -1.0F);
      dfm _snowmanxx = new dfm();
      _snowmanxx.a(0.0, 0.0, 1000.0);
      _snowmanxx.a((float)_snowman, (float)_snowman, (float)_snowman);
      d _snowmanxxx = g.f.a(180.0F);
      d _snowmanxxxx = g.b.a(_snowmanx * 20.0F);
      _snowmanxxx.a(_snowmanxxxx);
      _snowmanxx.a(_snowmanxxx);
      float _snowmanxxxxx = _snowman.aA;
      float _snowmanxxxxxx = _snowman.p;
      float _snowmanxxxxxxx = _snowman.q;
      float _snowmanxxxxxxxx = _snowman.aD;
      float _snowmanxxxxxxxxx = _snowman.aC;
      _snowman.aA = 180.0F + _snowman * 20.0F;
      _snowman.p = 180.0F + _snowman * 40.0F;
      _snowman.q = -_snowmanx * 20.0F;
      _snowman.aC = _snowman.p;
      _snowman.aD = _snowman.p;
      eet _snowmanxxxxxxxxxx = djz.C().ac();
      _snowmanxxxx.e();
      _snowmanxxxxxxxxxx.a(_snowmanxxxx);
      _snowmanxxxxxxxxxx.a(false);
      eag.a _snowmanxxxxxxxxxxx = djz.C().aE().b();
      RenderSystem.runAsFancy(() -> _snowman.a(_snowman, 0.0, 0.0, 0.0, 0.0F, 1.0F, _snowman, _snowman, 15728880));
      _snowmanxxxxxxxxxxx.a();
      _snowmanxxxxxxxxxx.a(true);
      _snowman.aA = _snowmanxxxxx;
      _snowman.p = _snowmanxxxxxx;
      _snowman.q = _snowmanxxxxxxx;
      _snowman.aD = _snowmanxxxxxxxx;
      _snowman.aC = _snowmanxxxxxxxxx;
      RenderSystem.popMatrix();
   }

   @Override
   protected boolean a(int var1, int var2, int var3, int var4, double var5, double var7) {
      return (!this.G || !this.E.f()) && super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.E.a(_snowman, _snowman, _snowman)) {
         this.a(this.E);
         return true;
      } else {
         return this.G && this.E.f() ? false : super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean c(double var1, double var3, int var5) {
      if (this.H) {
         this.H = false;
         return true;
      } else {
         return super.c(_snowman, _snowman, _snowman);
      }
   }

   @Override
   protected boolean a(double var1, double var3, int var5, int var6, int var7) {
      boolean _snowman = _snowman < (double)_snowman || _snowman < (double)_snowman || _snowman >= (double)(_snowman + this.b) || _snowman >= (double)(_snowman + this.c);
      return this.E.a(_snowman, _snowman, this.w, this.x, this.b, this.c, _snowman) && _snowman;
   }

   @Override
   protected void a(bjr var1, int var2, int var3, bik var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.E.a(_snowman);
   }

   @Override
   public void az_() {
      this.E.h();
   }

   @Override
   public void e() {
      if (this.F) {
         this.E.d();
      }

      super.e();
   }

   @Override
   public drp k() {
      return this.E;
   }
}
