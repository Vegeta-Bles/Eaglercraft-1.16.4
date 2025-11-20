package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class SheepWoolFeatureRenderer extends FeatureRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
   private static final Identifier SKIN = new Identifier("textures/entity/sheep/sheep_fur.png");
   private final SheepWoolEntityModel<SheepEntity> model = new SheepWoolEntityModel<>();

   public SheepWoolFeatureRenderer(FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, SheepEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (!_snowman.isSheared() && !_snowman.isInvisible()) {
         float _snowmanxxxxxxxxxx;
         float _snowmanxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxx;
         if (_snowman.hasCustomName() && "jeb_".equals(_snowman.getName().asString())) {
            int _snowmanxxxxxxxxxxxxx = 25;
            int _snowmanxxxxxxxxxxxxxx = _snowman.age / 25 + _snowman.getEntityId();
            int _snowmanxxxxxxxxxxxxxxx = DyeColor.values().length;
            int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx % _snowmanxxxxxxxxxxxxxxx;
            int _snowmanxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxx + 1) % _snowmanxxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxx = ((float)(_snowman.age % 25) + _snowman) / 25.0F;
            float[] _snowmanxxxxxxxxxxxxxxxxxxx = SheepEntity.getRgbColor(DyeColor.byId(_snowmanxxxxxxxxxxxxxxxx));
            float[] _snowmanxxxxxxxxxxxxxxxxxxxx = SheepEntity.getRgbColor(DyeColor.byId(_snowmanxxxxxxxxxxxxxxxxx));
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx[0] * (1.0F - _snowmanxxxxxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxxxxxxx[0] * _snowmanxxxxxxxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx[1] * (1.0F - _snowmanxxxxxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxxxxxxx[1] * _snowmanxxxxxxxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx[2] * (1.0F - _snowmanxxxxxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxxxxxxx[2] * _snowmanxxxxxxxxxxxxxxxxxx;
         } else {
            float[] _snowmanxxxxxxxxxxxxx = SheepEntity.getRgbColor(_snowman.getColor());
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxx[0];
            _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx[1];
            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx[2];
         }

         render(this.getContextModel(), this.model, SKIN, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
      }
   }
}
