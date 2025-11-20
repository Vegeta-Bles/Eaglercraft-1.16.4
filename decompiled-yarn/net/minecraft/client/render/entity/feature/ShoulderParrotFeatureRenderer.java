package net.minecraft.client.render.entity.feature;

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

public class ShoulderParrotFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
   private final ParrotEntityModel model = new ParrotEntityModel();

   public ShoulderParrotFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      this.renderShoulderParrot(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, true);
      this.renderShoulderParrot(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false);
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
      CompoundTag _snowman = leftShoulder ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
      EntityType.get(_snowman.getString("id")).filter(_snowmanx -> _snowmanx == EntityType.PARROT).ifPresent(_snowmanxxxxxxxxxx -> {
         matrices.push();
         matrices.translate(leftShoulder ? 0.4F : -0.4F, player.isInSneakingPose() ? -1.3F : -1.5, 0.0);
         VertexConsumer _snowmanx = vertexConsumers.getBuffer(this.model.getLayer(ParrotEntityRenderer.TEXTURES[_snowman.getInt("Variant")]));
         this.model.poseOnShoulder(matrices, _snowmanx, light, OverlayTexture.DEFAULT_UV, limbAngle, limbDistance, headYaw, headPitch, player.age);
         matrices.pop();
      });
   }
}
