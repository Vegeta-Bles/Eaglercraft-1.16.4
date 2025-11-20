package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ChickenEntityRenderer extends MobEntityRenderer<ChickenEntity, ChickenEntityModel<ChickenEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/chicken.png");

   public ChickenEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new ChickenEntityModel<>(), 0.3F);
   }

   public Identifier getTexture(ChickenEntity _snowman) {
      return TEXTURE;
   }

   protected float getAnimationProgress(ChickenEntity _snowman, float _snowman) {
      float _snowmanxx = MathHelper.lerp(_snowman, _snowman.prevFlapProgress, _snowman.flapProgress);
      float _snowmanxxx = MathHelper.lerp(_snowman, _snowman.prevMaxWingDeviation, _snowman.maxWingDeviation);
      return (MathHelper.sin(_snowmanxx) + 1.0F) * _snowmanxxx;
   }
}
