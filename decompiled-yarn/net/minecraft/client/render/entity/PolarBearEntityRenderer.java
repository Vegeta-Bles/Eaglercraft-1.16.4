package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.PolarBearEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.Identifier;

public class PolarBearEntityRenderer extends MobEntityRenderer<PolarBearEntity, PolarBearEntityModel<PolarBearEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/bear/polarbear.png");

   public PolarBearEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new PolarBearEntityModel<>(), 0.9F);
   }

   public Identifier getTexture(PolarBearEntity _snowman) {
      return TEXTURE;
   }

   protected void scale(PolarBearEntity _snowman, MatrixStack _snowman, float _snowman) {
      _snowman.scale(1.2F, 1.2F, 1.2F);
      super.scale(_snowman, _snowman, _snowman);
   }
}
