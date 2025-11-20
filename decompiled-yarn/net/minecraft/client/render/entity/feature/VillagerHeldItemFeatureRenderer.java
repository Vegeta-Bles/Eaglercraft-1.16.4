package net.minecraft.client.render.entity.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class VillagerHeldItemFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
   public VillagerHeldItemFeatureRenderer(FeatureRendererContext<T, M> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      _snowman.push();
      _snowman.translate(0.0, 0.4F, -0.4F);
      _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
      ItemStack _snowmanxxxxxxxxxx = _snowman.getEquippedStack(EquipmentSlot.MAINHAND);
      MinecraftClient.getInstance().getHeldItemRenderer().renderItem(_snowman, _snowmanxxxxxxxxxx, ModelTransformation.Mode.GROUND, false, _snowman, _snowman, _snowman);
      _snowman.pop();
   }
}
