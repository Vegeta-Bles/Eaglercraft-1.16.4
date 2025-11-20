package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.WitchHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.WitchEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WitchEntityRenderer extends MobEntityRenderer<WitchEntity, WitchEntityModel<WitchEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/witch.png");

   public WitchEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new WitchEntityModel<>(0.0F), 0.5F);
      this.addFeature(new WitchHeldItemFeatureRenderer<>(this));
   }

   public void render(WitchEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      this.model.setLiftingNose(!arg.getMainHandStack().isEmpty());
      super.render(arg, f, g, arg2, arg3, i);
   }

   public Identifier getTexture(WitchEntity arg) {
      return TEXTURE;
   }

   protected void scale(WitchEntity arg, MatrixStack arg2, float f) {
      float g = 0.9375F;
      arg2.scale(0.9375F, 0.9375F, 0.9375F);
   }
}
