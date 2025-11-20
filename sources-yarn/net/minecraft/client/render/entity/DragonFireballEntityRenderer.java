package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class DragonFireballEntityRenderer extends EntityRenderer<DragonFireballEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/enderdragon/dragon_fireball.png");
   private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);

   public DragonFireballEntityRenderer(EntityRenderDispatcher arg) {
      super(arg);
   }

   protected int getBlockLight(DragonFireballEntity arg, BlockPos arg2) {
      return 15;
   }

   public void render(DragonFireballEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      arg2.push();
      arg2.scale(2.0F, 2.0F, 2.0F);
      arg2.multiply(this.dispatcher.getRotation());
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
      MatrixStack.Entry lv = arg2.peek();
      Matrix4f lv2 = lv.getModel();
      Matrix3f lv3 = lv.getNormal();
      VertexConsumer lv4 = arg3.getBuffer(LAYER);
      produceVertex(lv4, lv2, lv3, i, 0.0F, 0, 0, 1);
      produceVertex(lv4, lv2, lv3, i, 1.0F, 0, 1, 1);
      produceVertex(lv4, lv2, lv3, i, 1.0F, 1, 1, 0);
      produceVertex(lv4, lv2, lv3, i, 0.0F, 1, 0, 0);
      arg2.pop();
      super.render(arg, f, g, arg2, arg3, i);
   }

   private static void produceVertex(
      VertexConsumer vertexConsumer, Matrix4f modelMatrix, Matrix3f normalMatrix, int light, float x, int y, int textureU, int textureV
   ) {
      vertexConsumer.vertex(modelMatrix, x - 0.5F, (float)y - 0.25F, 0.0F)
         .color(255, 255, 255, 255)
         .texture((float)textureU, (float)textureV)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(light)
         .normal(normalMatrix, 0.0F, 1.0F, 0.0F)
         .next();
   }

   public Identifier getTexture(DragonFireballEntity arg) {
      return TEXTURE;
   }
}
