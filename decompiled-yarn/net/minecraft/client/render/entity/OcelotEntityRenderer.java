package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.OcelotEntityModel;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.util.Identifier;

public class OcelotEntityRenderer extends MobEntityRenderer<OcelotEntity, OcelotEntityModel<OcelotEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/cat/ocelot.png");

   public OcelotEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new OcelotEntityModel<>(0.0F), 0.4F);
   }

   public Identifier getTexture(OcelotEntity _snowman) {
      return TEXTURE;
   }
}
