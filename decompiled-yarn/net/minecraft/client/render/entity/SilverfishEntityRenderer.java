package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.SilverfishEntityModel;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.util.Identifier;

public class SilverfishEntityRenderer extends MobEntityRenderer<SilverfishEntity, SilverfishEntityModel<SilverfishEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/silverfish.png");

   public SilverfishEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new SilverfishEntityModel<>(), 0.3F);
   }

   protected float getLyingAngle(SilverfishEntity _snowman) {
      return 180.0F;
   }

   public Identifier getTexture(SilverfishEntity _snowman) {
      return TEXTURE;
   }
}
