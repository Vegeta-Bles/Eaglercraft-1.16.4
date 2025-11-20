package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.Identifier;

public class CowEntityRenderer extends MobEntityRenderer<CowEntity, CowEntityModel<CowEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/cow/cow.png");

   public CowEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new CowEntityModel<>(), 0.7F);
   }

   public Identifier getTexture(CowEntity _snowman) {
      return TEXTURE;
   }
}
