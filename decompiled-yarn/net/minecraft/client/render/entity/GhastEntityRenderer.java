package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.GhastEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.util.Identifier;

public class GhastEntityRenderer extends MobEntityRenderer<GhastEntity, GhastEntityModel<GhastEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/ghast/ghast.png");
   private static final Identifier ANGRY_TEXTURE = new Identifier("textures/entity/ghast/ghast_shooting.png");

   public GhastEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new GhastEntityModel<>(), 1.5F);
   }

   public Identifier getTexture(GhastEntity _snowman) {
      return _snowman.isShooting() ? ANGRY_TEXTURE : TEXTURE;
   }

   protected void scale(GhastEntity _snowman, MatrixStack _snowman, float _snowman) {
      float _snowmanxxx = 1.0F;
      float _snowmanxxxx = 4.5F;
      float _snowmanxxxxx = 4.5F;
      _snowman.scale(4.5F, 4.5F, 4.5F);
   }
}
