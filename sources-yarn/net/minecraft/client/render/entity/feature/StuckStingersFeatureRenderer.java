package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class StuckStingersFeatureRenderer<T extends LivingEntity, M extends PlayerEntityModel<T>> extends StuckObjectsFeatureRenderer<T, M> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/bee/bee_stinger.png");

   public StuckStingersFeatureRenderer(LivingEntityRenderer<T, M> arg) {
      super(arg);
   }

   @Override
   protected int getObjectCount(T entity) {
      return entity.getStingerCount();
   }

   @Override
   protected void renderObject(
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light,
      Entity entity,
      float directionX,
      float directionY,
      float directionZ,
      float tickDelta
   ) {
      float k = MathHelper.sqrt(directionX * directionX + directionZ * directionZ);
      float l = (float)(Math.atan2((double)directionX, (double)directionZ) * 180.0F / (float)Math.PI);
      float m = (float)(Math.atan2((double)directionY, (double)k) * 180.0F / (float)Math.PI);
      matrices.translate(0.0, 0.0, 0.0);
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(l - 90.0F));
      matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(m));
      float n = 0.0F;
      float o = 0.125F;
      float p = 0.0F;
      float q = 0.0625F;
      float r = 0.03125F;
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(45.0F));
      matrices.scale(0.03125F, 0.03125F, 0.03125F);
      matrices.translate(2.5, 0.0, 0.0);
      VertexConsumer lv = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));

      for (int s = 0; s < 4; s++) {
         matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
         MatrixStack.Entry lv2 = matrices.peek();
         Matrix4f lv3 = lv2.getModel();
         Matrix3f lv4 = lv2.getNormal();
         produceVertex(lv, lv3, lv4, -4.5F, -1, 0.0F, 0.0F, light);
         produceVertex(lv, lv3, lv4, 4.5F, -1, 0.125F, 0.0F, light);
         produceVertex(lv, lv3, lv4, 4.5F, 1, 0.125F, 0.0625F, light);
         produceVertex(lv, lv3, lv4, -4.5F, 1, 0.0F, 0.0625F, light);
      }
   }

   private static void produceVertex(
      VertexConsumer vertexConsumer, Matrix4f vertexTransform, Matrix3f normalTransform, float x, int y, float u, float v, int light
   ) {
      vertexConsumer.vertex(vertexTransform, x, (float)y, 0.0F)
         .color(255, 255, 255, 255)
         .texture(u, v)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(light)
         .normal(normalTransform, 0.0F, 1.0F, 0.0F)
         .next();
   }
}
