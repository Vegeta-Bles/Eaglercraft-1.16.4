package net.minecraft.client.render.entity;

import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.util.Identifier;

public class AreaEffectCloudEntityRenderer extends EntityRenderer<AreaEffectCloudEntity> {
   public AreaEffectCloudEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public Identifier getTexture(AreaEffectCloudEntity _snowman) {
      return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
   }
}
