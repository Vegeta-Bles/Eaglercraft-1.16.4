public class egz extends eeu<bgy> {
   public static final vk a = new vk("textures/entity/trident.png");
   private final dvz e = new dvz();

   public egz(eet var1) {
      super(_snowman);
   }

   public void a(bgy var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      _snowman.a(g.d.a(afm.g(_snowman, _snowman.r, _snowman.p) - 90.0F));
      _snowman.a(g.f.a(afm.g(_snowman, _snowman.s, _snowman.q) + 90.0F));
      dfq _snowman = efo.c(_snowman, this.e.a(this.a(_snowman)), false, _snowman.u());
      this.e.a(_snowman, _snowman, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public vk a(bgy var1) {
      return a;
   }
}
