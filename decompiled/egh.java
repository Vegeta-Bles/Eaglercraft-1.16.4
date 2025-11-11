public class egh extends efw<bap, duc<bap>> {
   private static final vk a = new vk("textures/entity/fish/pufferfish.png");
   private int g;
   private final dvh<bap> h = new dvh<>();
   private final dvg<bap> i = new dvg<>();
   private final dvf<bap> j = new dvf<>();

   public egh(eet var1) {
      super(_snowman, new dvf<>(), 0.2F);
      this.g = 3;
   }

   public vk a(bap var1) {
      return a;
   }

   public void a(bap var1, float var2, float var3, dfm var4, eag var5, int var6) {
      int _snowman = _snowman.eN();
      if (_snowman != this.g) {
         if (_snowman == 0) {
            this.e = this.h;
         } else if (_snowman == 1) {
            this.e = this.i;
         } else {
            this.e = this.j;
         }
      }

      this.g = _snowman;
      this.c = 0.1F + 0.1F * (float)_snowman;
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   protected void a(bap var1, dfm var2, float var3, float var4, float var5) {
      _snowman.a(0.0, (double)(afm.b(_snowman * 0.05F) * 0.08F), 0.0);
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
