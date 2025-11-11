public class egn extends eeu<bgo> {
   private static final vk a = new vk("textures/entity/shulker/spark.png");
   private static final eao e = eao.h(a);
   private final dvp<bgo> f = new dvp<>();

   public egn(eet var1) {
      super(_snowman);
   }

   protected int a(bgo var1, fx var2) {
      return 15;
   }

   public void a(bgo var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      float _snowman = afm.j(_snowman.r, _snowman.p, _snowman);
      float _snowmanx = afm.g(_snowman, _snowman.s, _snowman.q);
      float _snowmanxx = (float)_snowman.K + _snowman;
      _snowman.a(0.0, 0.15F, 0.0);
      _snowman.a(g.d.a(afm.a(_snowmanxx * 0.1F) * 180.0F));
      _snowman.a(g.b.a(afm.b(_snowmanxx * 0.1F) * 180.0F));
      _snowman.a(g.f.a(afm.a(_snowmanxx * 0.15F) * 360.0F));
      _snowman.a(-0.5F, -0.5F, 0.5F);
      this.f.a(_snowman, 0.0F, 0.0F, 0.0F, _snowman, _snowmanx);
      dfq _snowmanxxx = _snowman.getBuffer(this.f.a(a));
      this.f.a(_snowman, _snowmanxxx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.a(1.5F, 1.5F, 1.5F);
      dfq _snowmanxxxx = _snowman.getBuffer(e);
      this.f.a(_snowman, _snowmanxxxx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 0.15F);
      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public vk a(bgo var1) {
      return a;
   }
}
