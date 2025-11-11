public class efp extends eeu<bcq> {
   private static final vk a = new vk("textures/entity/lead_knot.png");
   private final duq<bcq> e = new duq<>();

   public efp(eet var1) {
      super(_snowman);
   }

   public void a(bcq var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      _snowman.a(-1.0F, -1.0F, 1.0F);
      this.e.a(_snowman, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      dfq _snowman = _snowman.getBuffer(this.e.a(a));
      this.e.a(_snowman, _snowman, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public vk a(bcq var1) {
      return a;
   }
}
