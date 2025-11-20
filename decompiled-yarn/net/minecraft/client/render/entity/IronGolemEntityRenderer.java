package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.IronGolemCrackFeatureRenderer;
import net.minecraft.client.render.entity.feature.IronGolemFlowerFeatureRenderer;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;

public class IronGolemEntityRenderer extends MobEntityRenderer<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/iron_golem/iron_golem.png");

   public IronGolemEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new IronGolemEntityModel<>(), 0.7F);
      this.addFeature(new IronGolemCrackFeatureRenderer(this));
      this.addFeature(new IronGolemFlowerFeatureRenderer(this));
   }

   public Identifier getTexture(IronGolemEntity _snowman) {
      return TEXTURE;
   }

   protected void setupTransforms(IronGolemEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (!((double)_snowman.limbDistance < 0.01)) {
         float _snowmanxxxxx = 13.0F;
         float _snowmanxxxxxx = _snowman.limbAngle - _snowman.limbDistance * (1.0F - _snowman) + 6.0F;
         float _snowmanxxxxxxx = (Math.abs(_snowmanxxxxxx % 13.0F - 6.5F) - 3.25F) / 3.25F;
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(6.5F * _snowmanxxxxxxx));
      }
   }
}
