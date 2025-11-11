public class eem extends eeu<bgd> {
   private static final vk a = new vk("textures/entity/enderdragon/dragon_fireball.png");
   private static final eao e = eao.d(a);

   public eem(eet var1) {
      super(_snowman);
   }

   protected int a(bgd var1, fx var2) {
      return 15;
   }

   public void a(bgd var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      _snowman.a(2.0F, 2.0F, 2.0F);
      _snowman.a(this.b.b());
      _snowman.a(g.d.a(180.0F));
      dfm.a _snowman = _snowman.c();
      b _snowmanx = _snowman.a();
      a _snowmanxx = _snowman.b();
      dfq _snowmanxxx = _snowman.getBuffer(e);
      a(_snowmanxxx, _snowmanx, _snowmanxx, _snowman, 0.0F, 0, 0, 1);
      a(_snowmanxxx, _snowmanx, _snowmanxx, _snowman, 1.0F, 0, 1, 1);
      a(_snowmanxxx, _snowmanx, _snowmanxx, _snowman, 1.0F, 1, 1, 0);
      a(_snowmanxxx, _snowmanx, _snowmanxx, _snowman, 0.0F, 1, 0, 0);
      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static void a(dfq var0, b var1, a var2, int var3, float var4, int var5, int var6, int var7) {
      _snowman.a(_snowman, _snowman - 0.5F, (float)_snowman - 0.25F, 0.0F).a(255, 255, 255, 255).a((float)_snowman, (float)_snowman).b(ejw.a).a(_snowman).a(_snowman, 0.0F, 1.0F, 0.0F).d();
   }

   public vk a(bgd var1) {
      return a;
   }
}
