import com.mojang.blaze3d.systems.RenderSystem;

public class dml extends dkw implements dst {
   private static final vk b = new vk("textures/gui/widgets.png");
   public static final vk a = new vk("textures/gui/spectator_widgets.png");
   private final djz c;
   private long d;
   private dsq e;

   public dml(djz var1) {
      this.c = _snowman;
   }

   public void a(int var1) {
      this.d = x.b();
      if (this.e != null) {
         this.e.b(_snowman);
      } else {
         this.e = new dsq(this);
      }
   }

   private float c() {
      long _snowman = this.d - x.b() + 5000L;
      return afm.a((float)_snowman / 2000.0F, 0.0F, 1.0F);
   }

   public void a(dfm var1, float var2) {
      if (this.e != null) {
         float _snowman = this.c();
         if (_snowman <= 0.0F) {
            this.e.d();
         } else {
            int _snowmanx = this.c.aD().o() / 2;
            int _snowmanxx = this.v();
            this.d(-90);
            int _snowmanxxx = afm.d((float)this.c.aD().p() - 22.0F * _snowman);
            dsu _snowmanxxxx = this.e.f();
            this.a(_snowman, _snowman, _snowmanx, _snowmanxxx, _snowmanxxxx);
            this.d(_snowmanxx);
         }
      }
   }

   protected void a(dfm var1, float var2, int var3, int var4, dsu var5) {
      RenderSystem.enableRescaleNormal();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowman);
      this.c.M().a(b);
      this.b(_snowman, _snowman - 91, _snowman, 0, 0, 182, 22);
      if (_snowman.b() >= 0) {
         this.b(_snowman, _snowman - 91 - 1 + _snowman.b() * 20, _snowman - 1, 0, 22, 24, 22);
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.a(_snowman, _snowman, this.c.aD().o() / 2 - 90 + _snowman * 20 + 2, (float)(_snowman + 3), _snowman, _snowman.a(_snowman));
      }

      RenderSystem.disableRescaleNormal();
      RenderSystem.disableBlend();
   }

   private void a(dfm var1, int var2, int var3, float var4, float var5, dss var6) {
      this.c.M().a(a);
      if (_snowman != dsq.a) {
         int _snowman = (int)(_snowman * 255.0F);
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)_snowman, _snowman, 0.0F);
         float _snowmanx = _snowman.aB_() ? 1.0F : 0.25F;
         RenderSystem.color4f(_snowmanx, _snowmanx, _snowmanx, _snowman);
         _snowman.a(_snowman, _snowmanx, _snowman);
         RenderSystem.popMatrix();
         if (_snowman > 3 && _snowman.aB_()) {
            nr _snowmanxx = this.c.k.aC[_snowman].j();
            this.c.g.a(_snowman, _snowmanxx, (float)(_snowman + 19 - 2 - this.c.g.a(_snowmanxx)), _snowman + 6.0F + 3.0F, 16777215 + (_snowman << 24));
         }
      }
   }

   public void a(dfm var1) {
      int _snowman = (int)(this.c() * 255.0F);
      if (_snowman > 3 && this.e != null) {
         dss _snowmanx = this.e.b();
         nr _snowmanxx = _snowmanx == dsq.a ? this.e.c().b() : _snowmanx.aA_();
         if (_snowmanxx != null) {
            int _snowmanxxx = (this.c.aD().o() - this.c.g.a(_snowmanxx)) / 2;
            int _snowmanxxxx = this.c.aD().p() - 35;
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            this.c.g.a(_snowman, _snowmanxx, (float)_snowmanxxx, (float)_snowmanxxxx, 16777215 + (_snowman << 24));
            RenderSystem.disableBlend();
            RenderSystem.popMatrix();
         }
      }
   }

   @Override
   public void a(dsq var1) {
      this.e = null;
      this.d = 0L;
   }

   public boolean a() {
      return this.e != null;
   }

   public void a(double var1) {
      int _snowman = this.e.e() + (int)_snowman;

      while (_snowman >= 0 && _snowman <= 8 && (this.e.a(_snowman) == dsq.a || !this.e.a(_snowman).aB_())) {
         _snowman = (int)((double)_snowman + _snowman);
      }

      if (_snowman >= 0 && _snowman <= 8) {
         this.e.b(_snowman);
         this.d = x.b();
      }
   }

   public void b() {
      this.d = x.b();
      if (this.a()) {
         int _snowman = this.e.e();
         if (_snowman != -1) {
            this.e.b(_snowman);
         }
      } else {
         this.e = new dsq(this);
      }
   }
}
