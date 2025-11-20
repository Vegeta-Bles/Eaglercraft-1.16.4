package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.SlimeOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SlimeEntityRenderer extends MobEntityRenderer<SlimeEntity, SlimeEntityModel<SlimeEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/slime/slime.png");

   public SlimeEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new SlimeEntityModel<>(16), 0.25F);
      this.addFeature(new SlimeOverlayFeatureRenderer<>(this));
   }

   public void render(SlimeEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      this.shadowRadius = 0.25F * (float)arg.getSize();
      super.render(arg, f, g, arg2, arg3, i);
   }

   protected void scale(SlimeEntity arg, MatrixStack arg2, float f) {
      float g = 0.999F;
      arg2.scale(0.999F, 0.999F, 0.999F);
      arg2.translate(0.0, 0.001F, 0.0);
      float h = (float)arg.getSize();
      float i = MathHelper.lerp(f, arg.lastStretch, arg.stretch) / (h * 0.5F + 1.0F);
      float j = 1.0F / (i + 1.0F);
      arg2.scale(j * h, 1.0F / j * h, j * h);
   }

   public Identifier getTexture(SlimeEntity arg) {
      return TEXTURE;
   }
}
