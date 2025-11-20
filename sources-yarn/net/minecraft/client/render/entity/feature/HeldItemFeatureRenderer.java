package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class HeldItemFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ModelWithArms> extends FeatureRenderer<T, M> {
   public HeldItemFeatureRenderer(FeatureRendererContext<T, M> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, T arg3, float f, float g, float h, float j, float k, float l) {
      boolean bl = arg3.getMainArm() == Arm.RIGHT;
      ItemStack lv = bl ? arg3.getOffHandStack() : arg3.getMainHandStack();
      ItemStack lv2 = bl ? arg3.getMainHandStack() : arg3.getOffHandStack();
      if (!lv.isEmpty() || !lv2.isEmpty()) {
         arg.push();
         if (this.getContextModel().child) {
            float m = 0.5F;
            arg.translate(0.0, 0.75, 0.0);
            arg.scale(0.5F, 0.5F, 0.5F);
         }

         this.renderItem(arg3, lv2, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, Arm.RIGHT, arg, arg2, i);
         this.renderItem(arg3, lv, ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, Arm.LEFT, arg, arg2, i);
         arg.pop();
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
         boolean bl = arm == Arm.LEFT;
         matrices.translate((double)((float)(bl ? -1 : 1) / 16.0F), 0.125, -0.625);
         MinecraftClient.getInstance().getHeldItemRenderer().renderItem(entity, stack, transformationMode, bl, matrices, vertexConsumers, light);
         matrices.pop();
      }
   }
}
