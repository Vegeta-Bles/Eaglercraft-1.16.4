package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.IronGolemEntity;

@Environment(EnvType.CLIENT)
public class IronGolemFlowerFeatureRenderer extends FeatureRenderer<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> {
   public IronGolemFlowerFeatureRenderer(FeatureRendererContext<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, IronGolemEntity arg3, float f, float g, float h, float j, float k, float l) {
      if (arg3.getLookingAtVillagerTicks() != 0) {
         arg.push();
         ModelPart lv = this.getContextModel().getRightArm();
         lv.rotate(arg);
         arg.translate(-1.1875, 1.0625, -0.9375);
         arg.translate(0.5, 0.5, 0.5);
         float m = 0.5F;
         arg.scale(0.5F, 0.5F, 0.5F);
         arg.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
         arg.translate(-0.5, -0.5, -0.5);
         MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(Blocks.POPPY.getDefaultState(), arg, arg2, i, OverlayTexture.DEFAULT_UV);
         arg.pop();
      }
   }
}
