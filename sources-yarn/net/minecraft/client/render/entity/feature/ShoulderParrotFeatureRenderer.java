package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ParrotEntityRenderer;
import net.minecraft.client.render.entity.model.ParrotEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

@Environment(EnvType.CLIENT)
public class ShoulderParrotFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
   private final ParrotEntityModel model = new ParrotEntityModel();

   public ShoulderParrotFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, T arg3, float f, float g, float h, float j, float k, float l) {
      this.renderShoulderParrot(arg, arg2, i, arg3, f, g, k, l, true);
      this.renderShoulderParrot(arg, arg2, i, arg3, f, g, k, l, false);
   }

   private void renderShoulderParrot(
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light,
      T player,
      float limbAngle,
      float limbDistance,
      float headYaw,
      float headPitch,
      boolean leftShoulder
   ) {
      CompoundTag lv = leftShoulder ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
      EntityType.get(lv.getString("id")).filter(arg -> arg == EntityType.PARROT).ifPresent(arg5 -> {
         matrices.push();
         matrices.translate(leftShoulder ? 0.4F : -0.4F, player.isInSneakingPose() ? -1.3F : -1.5, 0.0);
         VertexConsumer lvx = vertexConsumers.getBuffer(this.model.getLayer(ParrotEntityRenderer.TEXTURES[lv.getInt("Variant")]));
         this.model.poseOnShoulder(matrices, lvx, light, OverlayTexture.DEFAULT_UV, limbAngle, limbDistance, headYaw, headPitch, player.age);
         matrices.pop();
      });
   }
}
