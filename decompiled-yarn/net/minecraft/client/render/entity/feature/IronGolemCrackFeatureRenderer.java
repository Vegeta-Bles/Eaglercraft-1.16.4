package net.minecraft.client.render.entity.feature;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;

public class IronGolemCrackFeatureRenderer extends FeatureRenderer<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> {
   private static final Map<IronGolemEntity.Crack, Identifier> DAMAGE_TO_TEXTURE = ImmutableMap.of(
      IronGolemEntity.Crack.LOW,
      new Identifier("textures/entity/iron_golem/iron_golem_crackiness_low.png"),
      IronGolemEntity.Crack.MEDIUM,
      new Identifier("textures/entity/iron_golem/iron_golem_crackiness_medium.png"),
      IronGolemEntity.Crack.HIGH,
      new Identifier("textures/entity/iron_golem/iron_golem_crackiness_high.png")
   );

   public IronGolemCrackFeatureRenderer(FeatureRendererContext<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, IronGolemEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (!_snowman.isInvisible()) {
         IronGolemEntity.Crack _snowmanxxxxxxxxxx = _snowman.getCrack();
         if (_snowmanxxxxxxxxxx != IronGolemEntity.Crack.NONE) {
            Identifier _snowmanxxxxxxxxxxx = DAMAGE_TO_TEXTURE.get(_snowmanxxxxxxxxxx);
            renderModel(this.getContextModel(), _snowmanxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F);
         }
      }
   }
}
