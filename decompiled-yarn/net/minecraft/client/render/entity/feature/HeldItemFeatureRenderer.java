package net.minecraft.client.render.entity.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;

public class HeldItemFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ModelWithArms> extends FeatureRenderer<T, M> {
   public HeldItemFeatureRenderer(FeatureRendererContext<T, M> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      boolean _snowmanxxxxxxxxxx = _snowman.getMainArm() == Arm.RIGHT;
      ItemStack _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx ? _snowman.getOffHandStack() : _snowman.getMainHandStack();
      ItemStack _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx ? _snowman.getMainHandStack() : _snowman.getOffHandStack();
      if (!_snowmanxxxxxxxxxxx.isEmpty() || !_snowmanxxxxxxxxxxxx.isEmpty()) {
         _snowman.push();
         if (this.getContextModel().child) {
            float _snowmanxxxxxxxxxxxxx = 0.5F;
            _snowman.translate(0.0, 0.75, 0.0);
            _snowman.scale(0.5F, 0.5F, 0.5F);
         }

         this.renderItem(_snowman, _snowmanxxxxxxxxxxxx, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, Arm.RIGHT, _snowman, _snowman, _snowman);
         this.renderItem(_snowman, _snowmanxxxxxxxxxxx, ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, Arm.LEFT, _snowman, _snowman, _snowman);
         _snowman.pop();
      }
   }

   private void renderItem(
      LivingEntity entity,
      ItemStack stack,
      ModelTransformation.Mode transformationMode,
      Arm arm,
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light
   ) {
      if (!stack.isEmpty()) {
         matrices.push();
         this.getContextModel().setArmAngle(arm, matrices);
         matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
         matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
         boolean _snowman = arm == Arm.LEFT;
         matrices.translate((double)((float)(_snowman ? -1 : 1) / 16.0F), 0.125, -0.625);
         MinecraftClient.getInstance().getHeldItemRenderer().renderItem(entity, stack, transformationMode, _snowman, matrices, vertexConsumers, light);
         matrices.pop();
      }
   }
}
