package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class SnowmanPumpkinFeatureRenderer extends FeatureRenderer<SnowGolemEntity, SnowGolemEntityModel<SnowGolemEntity>> {
   public SnowmanPumpkinFeatureRenderer(FeatureRendererContext<SnowGolemEntity, SnowGolemEntityModel<SnowGolemEntity>> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, SnowGolemEntity arg3, float f, float g, float h, float j, float k, float l) {
      if (!arg3.isInvisible() && arg3.hasPumpkin()) {
         arg.push();
         this.getContextModel().getTopSnowball().rotate(arg);
         float m = 0.625F;
         arg.translate(0.0, -0.34375, 0.0);
         arg.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
         arg.scale(0.625F, -0.625F, -0.625F);
         ItemStack lv = new ItemStack(Blocks.CARVED_PUMPKIN);
         MinecraftClient.getInstance()
            .getItemRenderer()
            .renderItem(arg3, lv, ModelTransformation.Mode.HEAD, false, arg, arg2, arg3.world, i, LivingEntityRenderer.getOverlay(arg3, 0.0F));
         arg.pop();
      }
   }
}
