package net.minecraft.client.render.entity;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class HuskEntityRenderer extends ZombieEntityRenderer {
   private static final Identifier TEXTURE = new Identifier("textures/entity/zombie/husk.png");

   public HuskEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   protected void scale(ZombieEntity _snowman, MatrixStack _snowman, float _snowman) {
      float _snowmanxxx = 1.0625F;
      _snowman.scale(1.0625F, 1.0625F, 1.0625F);
      super.scale(_snowman, _snowman, _snowman);
   }

   @Override
   public Identifier getTexture(ZombieEntity _snowman) {
      return TEXTURE;
   }
}
