package net.minecraft.client.render.entity;

import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;

public class ArrowEntityRenderer extends ProjectileEntityRenderer<ArrowEntity> {
   public static final Identifier TEXTURE = new Identifier("textures/entity/projectiles/arrow.png");
   public static final Identifier TIPPED_TEXTURE = new Identifier("textures/entity/projectiles/tipped_arrow.png");

   public ArrowEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public Identifier getTexture(ArrowEntity _snowman) {
      return _snowman.getColor() > 0 ? TIPPED_TEXTURE : TEXTURE;
   }
}
