package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.SnowmanPumpkinFeatureRenderer;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.util.Identifier;

public class SnowGolemEntityRenderer extends MobEntityRenderer<SnowGolemEntity, SnowGolemEntityModel<SnowGolemEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/snow_golem.png");

   public SnowGolemEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new SnowGolemEntityModel<>(), 0.5F);
      this.addFeature(new SnowmanPumpkinFeatureRenderer(this));
   }

   public Identifier getTexture(SnowGolemEntity _snowman) {
      return TEXTURE;
   }
}
