public class eed extends eeu<bhn> {
   private static final vk[] e = new vk[]{
      new vk("textures/entity/boat/oak.png"),
      new vk("textures/entity/boat/spruce.png"),
      new vk("textures/entity/boat/birch.png"),
      new vk("textures/entity/boat/jungle.png"),
      new vk("textures/entity/boat/acacia.png"),
      new vk("textures/entity/boat/dark_oak.png")
   };
   protected final dtn a = new dtn();

   public eed(eet var1) {
      super(_snowman);
      this.c = 0.8F;
   }

   public void a(bhn var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      _snowman.a(0.0, 0.375, 0.0);
      _snowman.a(g.d.a(180.0F - _snowman));
      float _snowman = (float)_snowman.n() - _snowman;
      float _snowmanx = _snowman.m() - _snowman;
      if (_snowmanx < 0.0F) {
         _snowmanx = 0.0F;
      }

      if (_snowman > 0.0F) {
         _snowman.a(g.b.a(afm.a(_snowman) * _snowman * _snowmanx / 10.0F * (float)_snowman.o()));
      }

      float _snowmanxx = _snowman.b(_snowman);
      if (!afm.a(_snowmanxx, 0.0F)) {
         _snowman.a(new d(new g(1.0F, 0.0F, 1.0F), _snowman.b(_snowman), true));
      }

      _snowman.a(-1.0F, -1.0F, 1.0F);
      _snowman.a(g.d.a(90.0F));
      this.a.a(_snowman, _snowman, 0.0F, -0.1F, 0.0F, 0.0F);
      dfq _snowmanxxx = _snowman.getBuffer(this.a.a(this.a(_snowman)));
      this.a.a(_snowman, _snowmanxxx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
      if (!_snowman.aI()) {
         dfq _snowmanxxxx = _snowman.getBuffer(eao.j());
         this.a.c().a(_snowman, _snowmanxxxx, _snowman, ejw.a);
      }

      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public vk a(bhn var1) {
      return e[_snowman.p().ordinal()];
   }
}
