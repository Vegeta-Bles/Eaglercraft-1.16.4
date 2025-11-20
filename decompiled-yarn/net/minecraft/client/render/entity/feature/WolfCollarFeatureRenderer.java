package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

public class WolfCollarFeatureRenderer extends FeatureRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
   private static final Identifier SKIN = new Identifier("textures/entity/wolf/wolf_collar.png");

   public WolfCollarFeatureRenderer(FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, WolfEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (_snowman.isTamed() && !_snowman.isInvisible()) {
         float[] _snowmanxxxxxxxxxx = _snowman.getCollarColor().getColorComponents();
         renderModel(this.getContextModel(), SKIN, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxx[0], _snowmanxxxxxxxxxx[1], _snowmanxxxxxxxxxx[2]);
      }
   }
}
