package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.EndermiteEntityModel;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.util.Identifier;

public class EndermiteEntityRenderer extends MobEntityRenderer<EndermiteEntity, EndermiteEntityModel<EndermiteEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/endermite.png");

   public EndermiteEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new EndermiteEntityModel<>(), 0.3F);
   }

   protected float getLyingAngle(EndermiteEntity _snowman) {
      return 180.0F;
   }

   public Identifier getTexture(EndermiteEntity _snowman) {
      return TEXTURE;
   }
}
