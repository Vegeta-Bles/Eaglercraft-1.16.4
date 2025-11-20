package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.TropicalFishColorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LargeTropicalFishEntityModel;
import net.minecraft.client.render.entity.model.SmallTropicalFishEntityModel;
import net.minecraft.client.render.entity.model.TintableCompositeModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TropicalFishEntityRenderer extends MobEntityRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> {
   private final SmallTropicalFishEntityModel<TropicalFishEntity> smallModel = new SmallTropicalFishEntityModel<>(0.0F);
   private final LargeTropicalFishEntityModel<TropicalFishEntity> largeModel = new LargeTropicalFishEntityModel<>(0.0F);

   public TropicalFishEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new SmallTropicalFishEntityModel<>(0.0F), 0.15F);
      this.addFeature(new TropicalFishColorFeatureRenderer(this));
   }

   public Identifier getTexture(TropicalFishEntity _snowman) {
      return _snowman.getShapeId();
   }

   public void render(TropicalFishEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      TintableCompositeModel<TropicalFishEntity> _snowmanxxxxxx = (TintableCompositeModel<TropicalFishEntity>)(_snowman.getShape() == 0 ? this.smallModel : this.largeModel);
      this.model = _snowmanxxxxxx;
      float[] _snowmanxxxxxxx = _snowman.getBaseColorComponents();
      _snowmanxxxxxx.setColorMultiplier(_snowmanxxxxxxx[0], _snowmanxxxxxxx[1], _snowmanxxxxxxx[2]);
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowmanxxxxxx.setColorMultiplier(1.0F, 1.0F, 1.0F);
   }

   protected void setupTransforms(TropicalFishEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowmanxxxxx = 4.3F * MathHelper.sin(0.6F * _snowman);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxx));
      if (!_snowman.isTouchingWater()) {
         _snowman.translate(0.2F, 0.1F, 0.0);
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
      }
   }
}
