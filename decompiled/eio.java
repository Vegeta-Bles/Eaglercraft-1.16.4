public class eio extends eit<bbe, dus<bbe>> {
   private static final vk[] a = new vk[]{
      new vk("textures/entity/llama/decor/white.png"),
      new vk("textures/entity/llama/decor/orange.png"),
      new vk("textures/entity/llama/decor/magenta.png"),
      new vk("textures/entity/llama/decor/light_blue.png"),
      new vk("textures/entity/llama/decor/yellow.png"),
      new vk("textures/entity/llama/decor/lime.png"),
      new vk("textures/entity/llama/decor/pink.png"),
      new vk("textures/entity/llama/decor/gray.png"),
      new vk("textures/entity/llama/decor/light_gray.png"),
      new vk("textures/entity/llama/decor/cyan.png"),
      new vk("textures/entity/llama/decor/purple.png"),
      new vk("textures/entity/llama/decor/blue.png"),
      new vk("textures/entity/llama/decor/brown.png"),
      new vk("textures/entity/llama/decor/green.png"),
      new vk("textures/entity/llama/decor/red.png"),
      new vk("textures/entity/llama/decor/black.png")
   };
   private static final vk b = new vk("textures/entity/llama/decor/trader_llama.png");
   private final dus<bbe> c = new dus<>(0.5F);

   public eio(egk<bbe, dus<bbe>> var1) {
      super(_snowman);
   }

   public void a(dfm var1, eag var2, int var3, bbe var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      bkx _snowman = _snowman.fy();
      vk _snowmanx;
      if (_snowman != null) {
         _snowmanx = a[_snowman.b()];
      } else {
         if (!_snowman.fu()) {
            return;
         }

         _snowmanx = b;
      }

      this.aC_().a(this.c);
      this.c.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      dfq _snowmanxx = _snowman.getBuffer(eao.d(_snowmanx));
      this.c.a(_snowman, _snowmanxx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
