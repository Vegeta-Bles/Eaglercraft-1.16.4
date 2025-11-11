public class efl extends efw<bai, duo<bai>> {
   private static final vk a = new vk("textures/entity/iron_golem/iron_golem.png");

   public efl(eet var1) {
      super(_snowman, new duo<>(), 0.7F);
      this.a(new eil(this));
      this.a(new eim(this));
   }

   public vk a(bai var1) {
      return a;
   }

   protected void a(bai var1, dfm var2, float var3, float var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (!((double)_snowman.av < 0.01)) {
         float _snowman = 13.0F;
         float _snowmanx = _snowman.aw - _snowman.av * (1.0F - _snowman) + 6.0F;
         float _snowmanxx = (Math.abs(_snowmanx % 13.0F - 6.5F) - 3.25F) / 3.25F;
         _snowman.a(g.f.a(6.5F * _snowmanxx));
      }
   }
}
