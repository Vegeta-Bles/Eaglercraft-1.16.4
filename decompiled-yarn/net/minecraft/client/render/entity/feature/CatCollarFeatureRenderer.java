package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.Identifier;

public class CatCollarFeatureRenderer extends FeatureRenderer<CatEntity, CatEntityModel<CatEntity>> {
   private static final Identifier SKIN = new Identifier("textures/entity/cat/cat_collar.png");
   private final CatEntityModel<CatEntity> model = new CatEntityModel<>(0.01F);

   public CatCollarFeatureRenderer(FeatureRendererContext<CatEntity, CatEntityModel<CatEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, CatEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (_snowman.isTamed()) {
         float[] _snowmanxxxxxxxxxx = _snowman.getCollarColor().getColorComponents();
         render(this.getContextModel(), this.model, SKIN, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxx[0], _snowmanxxxxxxxxxx[1], _snowmanxxxxxxxxxx[2]);
      }
   }
}
