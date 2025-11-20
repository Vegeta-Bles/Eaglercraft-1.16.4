package net.minecraft.client.render.entity;

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

public class DragonFireballEntityRenderer extends EntityRenderer<DragonFireballEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/enderdragon/dragon_fireball.png");
   private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);

   public DragonFireballEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   protected int getBlockLight(DragonFireballEntity _snowman, BlockPos _snowman) {
      return 15;
   }

   public void render(DragonFireballEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      _snowman.scale(2.0F, 2.0F, 2.0F);
      _snowman.multiply(this.dispatcher.getRotation());
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
      MatrixStack.Entry _snowmanxxxxxx = _snowman.peek();
      Matrix4f _snowmanxxxxxxx = _snowmanxxxxxx.getModel();
      Matrix3f _snowmanxxxxxxxx = _snowmanxxxxxx.getNormal();
      VertexConsumer _snowmanxxxxxxxxx = _snowman.getBuffer(LAYER);
      produceVertex(_snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman, 0.0F, 0, 0, 1);
      produceVertex(_snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman, 1.0F, 0, 1, 1);
      produceVertex(_snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman, 1.0F, 1, 1, 0);
      produceVertex(_snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman, 0.0F, 1, 0, 0);
      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
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

   public Identifier getTexture(DragonFireballEntity _snowman) {
      return TEXTURE;
   }
}
