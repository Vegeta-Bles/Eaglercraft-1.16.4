package net.minecraft.client.render.entity;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class WitherSkeletonEntityRenderer extends SkeletonEntityRenderer {
   private static final Identifier TEXTURE = new Identifier("textures/entity/skeleton/wither_skeleton.png");

   public WitherSkeletonEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   @Override
   public Identifier getTexture(AbstractSkeletonEntity _snowman) {
      return TEXTURE;
   }

   protected void scale(AbstractSkeletonEntity _snowman, MatrixStack _snowman, float _snowman) {
      _snowman.scale(1.2F, 1.2F, 1.2F);
   }
}
