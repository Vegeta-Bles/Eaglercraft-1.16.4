public class egb extends efw<bam, duz> {
   public static final vk[] a = new vk[]{
      new vk("textures/entity/parrot/parrot_red_blue.png"),
      new vk("textures/entity/parrot/parrot_blue.png"),
      new vk("textures/entity/parrot/parrot_green.png"),
      new vk("textures/entity/parrot/parrot_yellow_blue.png"),
      new vk("textures/entity/parrot/parrot_grey.png")
   };

   public egb(eet var1) {
      super(_snowman, new duz(), 0.3F);
   }

   public vk a(bam var1) {
      return a[_snowman.eW()];
   }

   public float a(bam var1, float var2) {
      float _snowman = afm.g(_snowman, _snowman.bt, _snowman.bq);
      float _snowmanx = afm.g(_snowman, _snowman.bs, _snowman.br);
      return (afm.a(_snowman) + 1.0F) * _snowmanx;
   }
}
