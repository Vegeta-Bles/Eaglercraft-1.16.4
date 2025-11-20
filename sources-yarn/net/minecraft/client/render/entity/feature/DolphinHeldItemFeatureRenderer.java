package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.DolphinEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class DolphinHeldItemFeatureRenderer extends FeatureRenderer<DolphinEntity, DolphinEntityModel<DolphinEntity>> {
   public DolphinHeldItemFeatureRenderer(FeatureRendererContext<DolphinEntity, DolphinEntityModel<DolphinEntity>> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, DolphinEntity arg3, float f, float g, float h, float j, float k, float l) {
      boolean bl = arg3.getMainArm() == Arm.RIGHT;
      arg.push();
      float m = 1.0F;
      float n = -1.0F;
      float o = MathHelper.abs(arg3.pitch) / 60.0F;
      if (arg3.pitch < 0.0F) {
         arg.translate(0.0, (double)(1.0F - o * 0.5F), (double)(-1.0F + o * 0.5F));
      } else {
         arg.translate(0.0, (double)(1.0F + o * 0.8F), (double)(-1.0F + o * 0.2F));
      }

      ItemStack lv = bl ? arg3.getMainHandStack() : arg3.getOffHandStack();
      MinecraftClient.getInstance().getHeldItemRenderer().renderItem(arg3, lv, ModelTransformation.Mode.GROUND, false, arg, arg2, i);
      arg.pop();
   }
}
