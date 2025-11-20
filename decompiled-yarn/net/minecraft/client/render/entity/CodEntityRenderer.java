package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.CodEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CodEntityRenderer extends MobEntityRenderer<CodEntity, CodEntityModel<CodEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/fish/cod.png");

   public CodEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new CodEntityModel<>(), 0.3F);
   }

   public Identifier getTexture(CodEntity _snowman) {
      return TEXTURE;
   }

   protected void setupTransforms(CodEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowmanxxxxx = 4.3F * MathHelper.sin(0.6F * _snowman);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxx));
      if (!_snowman.isTouchingWater()) {
         _snowman.translate(0.1F, 0.1F, -0.1F);
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
      }
   }
}
