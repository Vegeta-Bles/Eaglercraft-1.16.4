package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.ParrotEntityModel;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ParrotEntityRenderer extends MobEntityRenderer<ParrotEntity, ParrotEntityModel> {
   public static final Identifier[] TEXTURES = new Identifier[]{
      new Identifier("textures/entity/parrot/parrot_red_blue.png"),
      new Identifier("textures/entity/parrot/parrot_blue.png"),
      new Identifier("textures/entity/parrot/parrot_green.png"),
      new Identifier("textures/entity/parrot/parrot_yellow_blue.png"),
      new Identifier("textures/entity/parrot/parrot_grey.png")
   };

   public ParrotEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new ParrotEntityModel(), 0.3F);
   }

   public Identifier getTexture(ParrotEntity _snowman) {
      return TEXTURES[_snowman.getVariant()];
   }

   public float getAnimationProgress(ParrotEntity _snowman, float _snowman) {
      float _snowmanxx = MathHelper.lerp(_snowman, _snowman.prevFlapProgress, _snowman.flapProgress);
      float _snowmanxxx = MathHelper.lerp(_snowman, _snowman.prevMaxWingDeviation, _snowman.maxWingDeviation);
      return (MathHelper.sin(_snowmanxx) + 1.0F) * _snowmanxxx;
   }
}
