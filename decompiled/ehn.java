public class ehn extends eeu<bgz> {
   private static final vk a = new vk("textures/entity/wither/wither_invulnerable.png");
   private static final vk e = new vk("textures/entity/wither/wither.png");
   private final dvt f = new dvt();

   public ehn(eet var1) {
      super(_snowman);
   }

   protected int a(bgz var1, fx var2) {
      return 15;
   }

   public void a(bgz var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      _snowman.a(-1.0F, -1.0F, 1.0F);
      float _snowman = afm.j(_snowman.r, _snowman.p, _snowman);
      float _snowmanx = afm.g(_snowman, _snowman.s, _snowman.q);
      dfq _snowmanxx = _snowman.getBuffer(this.f.a(this.a(_snowman)));
      this.f.a(0.0F, _snowman, _snowmanx);
      this.f.a(_snowman, _snowmanxx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public vk a(bgz var1) {
      return _snowman.k() ? a : e;
   }
}
