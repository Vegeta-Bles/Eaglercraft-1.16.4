package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.MooshroomEntity;

@Environment(EnvType.CLIENT)
public class MooshroomMushroomFeatureRenderer<T extends MooshroomEntity> extends FeatureRenderer<T, CowEntityModel<T>> {
   public MooshroomMushroomFeatureRenderer(FeatureRendererContext<T, CowEntityModel<T>> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, T arg3, float f, float g, float h, float j, float k, float l) {
      if (!arg3.isBaby() && !arg3.isInvisible()) {
         BlockRenderManager lv = MinecraftClient.getInstance().getBlockRenderManager();
         BlockState lv2 = arg3.getMooshroomType().getMushroomState();
         int m = LivingEntityRenderer.getOverlay(arg3, 0.0F);
         arg.push();
         arg.translate(0.2F, -0.35F, 0.5);
         arg.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-48.0F));
         arg.scale(-1.0F, -1.0F, 1.0F);
         arg.translate(-0.5, -0.5, -0.5);
         lv.renderBlockAsEntity(lv2, arg, arg2, i, m);
         arg.pop();
         arg.push();
         arg.translate(0.2F, -0.35F, 0.5);
         arg.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(42.0F));
         arg.translate(0.1F, 0.0, -0.6F);
         arg.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-48.0F));
         arg.scale(-1.0F, -1.0F, 1.0F);
         arg.translate(-0.5, -0.5, -0.5);
         lv.renderBlockAsEntity(lv2, arg, arg2, i, m);
         arg.pop();
         arg.push();
         this.getContextModel().getHead().rotate(arg);
         arg.translate(0.0, -0.7F, -0.2F);
         arg.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-78.0F));
         arg.scale(-1.0F, -1.0F, 1.0F);
         arg.translate(-0.5, -0.5, -0.5);
         lv.renderBlockAsEntity(lv2, arg, arg2, i, m);
         arg.pop();
      }
   }
}
