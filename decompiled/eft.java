public class eft extends eeu<bgl> {
   private static final vk a = new vk("textures/entity/llama/spit.png");
   private final dut<bgl> e = new dut<>();

   public eft(eet var1) {
      super(_snowman);
   }

   public void a(bgl var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      _snowman.a(0.0, 0.15F, 0.0);
      _snowman.a(g.d.a(afm.g(_snowman, _snowman.r, _snowman.p) - 90.0F));
      _snowman.a(g.f.a(afm.g(_snowman, _snowman.s, _snowman.q)));
      this.e.a(_snowman, _snowman, 0.0F, -0.1F, 0.0F, 0.0F);
      dfq _snowman = _snowman.getBuffer(this.e.a(a));
      this.e.a(_snowman, _snowman, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public vk a(bgl var1) {
      return a;
   }
}
