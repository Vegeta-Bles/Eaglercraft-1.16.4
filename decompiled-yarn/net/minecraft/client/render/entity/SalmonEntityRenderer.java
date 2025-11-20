package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.SalmonEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SalmonEntityRenderer extends MobEntityRenderer<SalmonEntity, SalmonEntityModel<SalmonEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/fish/salmon.png");

   public SalmonEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new SalmonEntityModel<>(), 0.4F);
   }

   public Identifier getTexture(SalmonEntity _snowman) {
      return TEXTURE;
   }

   protected void setupTransforms(SalmonEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowmanxxxxx = 1.0F;
      float _snowmanxxxxxx = 1.0F;
      if (!_snowman.isTouchingWater()) {
         _snowmanxxxxx = 1.3F;
         _snowmanxxxxxx = 1.7F;
      }

      float _snowmanxxxxxxx = _snowmanxxxxx * 4.3F * MathHelper.sin(_snowmanxxxxxx * 0.6F * _snowman);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxx));
      _snowman.translate(0.0, 0.0, -0.4F);
      if (!_snowman.isTouchingWater()) {
         _snowman.translate(0.2F, 0.1F, 0.0);
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
      }
   }
}
