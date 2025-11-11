public class egv extends efw<bav, dvx<bav>> {
   private static final vk a = new vk("textures/entity/squid.png");

   public egv(eet var1) {
      super(_snowman, new dvx<>(), 0.7F);
   }

   public vk a(bav var1) {
      return a;
   }

   protected void a(bav var1, dfm var2, float var3, float var4, float var5) {
      float _snowman = afm.g(_snowman, _snowman.c, _snowman.b);
      float _snowmanx = afm.g(_snowman, _snowman.bo, _snowman.d);
      _snowman.a(0.0, 0.5, 0.0);
      _snowman.a(g.d.a(180.0F - _snowman));
      _snowman.a(g.b.a(_snowman));
      _snowman.a(g.d.a(_snowmanx));
      _snowman.a(0.0, -1.2F, 0.0);
   }

   protected float a(bav var1, float var2) {
      return afm.g(_snowman, _snowman.bs, _snowman.br);
   }
}
