package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.Identifier;

public class BeeEntityRenderer extends MobEntityRenderer<BeeEntity, BeeEntityModel<BeeEntity>> {
   private static final Identifier ANGRY_TEXTURE = new Identifier("textures/entity/bee/bee_angry.png");
   private static final Identifier ANGRY_NECTAR_TEXTURE = new Identifier("textures/entity/bee/bee_angry_nectar.png");
   private static final Identifier PASSIVE_TEXTURE = new Identifier("textures/entity/bee/bee.png");
   private static final Identifier NECTAR_TEXTURE = new Identifier("textures/entity/bee/bee_nectar.png");

   public BeeEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new BeeEntityModel<>(), 0.4F);
   }

   public Identifier getTexture(BeeEntity _snowman) {
      if (_snowman.hasAngerTime()) {
         return _snowman.hasNectar() ? ANGRY_NECTAR_TEXTURE : ANGRY_TEXTURE;
      } else {
         return _snowman.hasNectar() ? NECTAR_TEXTURE : PASSIVE_TEXTURE;
      }
   }
}
