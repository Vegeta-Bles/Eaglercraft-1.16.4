package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LargeTropicalFishEntityModel;
import net.minecraft.client.render.entity.model.SmallTropicalFishEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.TropicalFishEntity;

public class TropicalFishColorFeatureRenderer extends FeatureRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> {
   private final SmallTropicalFishEntityModel<TropicalFishEntity> smallModel = new SmallTropicalFishEntityModel<>(0.008F);
   private final LargeTropicalFishEntityModel<TropicalFishEntity> largeModel = new LargeTropicalFishEntityModel<>(0.008F);

   public TropicalFishColorFeatureRenderer(FeatureRendererContext<TropicalFishEntity, EntityModel<TropicalFishEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, TropicalFishEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      EntityModel<TropicalFishEntity> _snowmanxxxxxxxxxx = (EntityModel<TropicalFishEntity>)(_snowman.getShape() == 0 ? this.smallModel : this.largeModel);
      float[] _snowmanxxxxxxxxxxx = _snowman.getPatternColorComponents();
      render(this.getContextModel(), _snowmanxxxxxxxxxx, _snowman.getVarietyId(), _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxx[0], _snowmanxxxxxxxxxxx[1], _snowmanxxxxxxxxxxx[2]);
   }
}
