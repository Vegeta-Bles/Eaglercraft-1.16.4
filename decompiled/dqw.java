import com.mojang.blaze3d.systems.RenderSystem;

public class dqw extends dqm<bjs> {
   private static final vk A = new vk("textures/gui/container/smithing.png");

   public dqw(bjs var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman, A);
      this.p = 60;
      this.q = 18;
   }

   @Override
   protected void b(dfm var1, int var2, int var3) {
      RenderSystem.disableBlend();
      super.b(_snowman, _snowman, _snowman);
   }
}
