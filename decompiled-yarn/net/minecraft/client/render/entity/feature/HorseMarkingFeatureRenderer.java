package net.minecraft.client.render.entity.feature;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.HorseMarking;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class HorseMarkingFeatureRenderer extends FeatureRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {
   private static final Map<HorseMarking, Identifier> TEXTURES = Util.make(Maps.newEnumMap(HorseMarking.class), _snowman -> {
      _snowman.put(HorseMarking.NONE, null);
      _snowman.put(HorseMarking.WHITE, new Identifier("textures/entity/horse/horse_markings_white.png"));
      _snowman.put(HorseMarking.WHITE_FIELD, new Identifier("textures/entity/horse/horse_markings_whitefield.png"));
      _snowman.put(HorseMarking.WHITE_DOTS, new Identifier("textures/entity/horse/horse_markings_whitedots.png"));
      _snowman.put(HorseMarking.BLACK_DOTS, new Identifier("textures/entity/horse/horse_markings_blackdots.png"));
   });

   public HorseMarkingFeatureRenderer(FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, HorseEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      Identifier _snowmanxxxxxxxxxx = TEXTURES.get(_snowman.getMarking());
      if (_snowmanxxxxxxxxxx != null && !_snowman.isInvisible()) {
         VertexConsumer _snowmanxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntityTranslucent(_snowmanxxxxxxxxxx));
         this.getContextModel().render(_snowman, _snowmanxxxxxxxxxxx, _snowman, LivingEntityRenderer.getOverlay(_snowman, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
