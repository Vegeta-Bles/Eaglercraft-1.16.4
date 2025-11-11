import com.mojang.blaze3d.systems.RenderSystem;

public class dll extends dld {
   private static final vk a = new vk("textures/gui/checkbox.png");
   private boolean b;
   private final boolean c;

   public dll(int var1, int var2, int var3, int var4, nr var5, boolean var6) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, true);
   }

   public dll(int var1, int var2, int var3, int var4, nr var5, boolean var6, boolean var7) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void b() {
      this.b = !this.b;
   }

   public boolean a() {
      return this.b;
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
      djz _snowman = djz.C();
      _snowman.M().a(a);
      RenderSystem.enableDepthTest();
      dku _snowmanx = _snowman.g;
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.q);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(dem.r.l, dem.j.j);
      a(_snowman, this.l, this.m, this.j() ? 20.0F : 0.0F, this.b ? 20.0F : 0.0F, 20, this.k, 64, 64);
      this.a(_snowman, _snowman, _snowman, _snowman);
      if (this.c) {
         b(_snowman, _snowmanx, this.i(), this.l + 24, this.m + (this.k - 8) / 2, 14737632 | afm.f(this.q * 255.0F) << 24);
      }
   }
}
