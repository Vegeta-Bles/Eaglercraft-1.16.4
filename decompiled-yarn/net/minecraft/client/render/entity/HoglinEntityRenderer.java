package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.HoglinEntityModel;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.util.Identifier;

public class HoglinEntityRenderer extends MobEntityRenderer<HoglinEntity, HoglinEntityModel<HoglinEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/hoglin/hoglin.png");

   public HoglinEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new HoglinEntityModel<>(), 0.7F);
   }

   public Identifier getTexture(HoglinEntity _snowman) {
      return TEXTURE;
   }

   protected boolean isShaking(HoglinEntity _snowman) {
      return _snowman.canConvert();
   }
}
