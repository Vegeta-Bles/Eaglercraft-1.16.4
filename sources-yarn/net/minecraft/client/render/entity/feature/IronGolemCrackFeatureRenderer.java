package net.minecraft.client.render.entity.feature;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class IronGolemCrackFeatureRenderer extends FeatureRenderer<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> {
   private static final Map<IronGolemEntity.Crack, Identifier> DAMAGE_TO_TEXTURE = ImmutableMap.of(
      IronGolemEntity.Crack.LOW,
      new Identifier("textures/entity/iron_golem/iron_golem_crackiness_low.png"),
      IronGolemEntity.Crack.MEDIUM,
      new Identifier("textures/entity/iron_golem/iron_golem_crackiness_medium.png"),
      IronGolemEntity.Crack.HIGH,
      new Identifier("textures/entity/iron_golem/iron_golem_crackiness_high.png")
   );

   public IronGolemCrackFeatureRenderer(FeatureRendererContext<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, IronGolemEntity arg3, float f, float g, float h, float j, float k, float l) {
      if (!arg3.isInvisible()) {
         IronGolemEntity.Crack lv = arg3.getCrack();
         if (lv != IronGolemEntity.Crack.NONE) {
            Identifier lv2 = DAMAGE_TO_TEXTURE.get(lv);
            renderModel(this.getContextModel(), lv2, arg, arg2, i, arg3, 1.0F, 1.0F, 1.0F);
         }
      }
   }
}
