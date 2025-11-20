package net.minecraft.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.texture.PaintingManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class PaintingEntityRenderer extends EntityRenderer<PaintingEntity> {
   public PaintingEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(PaintingEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - _snowman));
      PaintingMotive _snowmanxxxxxx = _snowman.motive;
      float _snowmanxxxxxxx = 0.0625F;
      _snowman.scale(0.0625F, 0.0625F, 0.0625F);
      VertexConsumer _snowmanxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntitySolid(this.getTexture(_snowman)));
      PaintingManager _snowmanxxxxxxxxx = MinecraftClient.getInstance().getPaintingManager();
      this.method_4074(_snowman, _snowmanxxxxxxxx, _snowman, _snowmanxxxxxx.getWidth(), _snowmanxxxxxx.getHeight(), _snowmanxxxxxxxxx.getPaintingSprite(_snowmanxxxxxx), _snowmanxxxxxxxxx.getBackSprite());
      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(PaintingEntity _snowman) {
      return MinecraftClient.getInstance().getPaintingManager().getBackSprite().getAtlas().getId();
   }

   private void method_4074(MatrixStack _snowman, VertexConsumer _snowman, PaintingEntity _snowman, int _snowman, int _snowman, Sprite _snowman, Sprite _snowman) {
      MatrixStack.Entry _snowmanxxxxxxx = _snowman.peek();
      Matrix4f _snowmanxxxxxxxx = _snowmanxxxxxxx.getModel();
      Matrix3f _snowmanxxxxxxxxx = _snowmanxxxxxxx.getNormal();
      float _snowmanxxxxxxxxxx = (float)(-_snowman) / 2.0F;
      float _snowmanxxxxxxxxxxx = (float)(-_snowman) / 2.0F;
      float _snowmanxxxxxxxxxxxx = 0.5F;
      float _snowmanxxxxxxxxxxxxx = _snowman.getMinU();
      float _snowmanxxxxxxxxxxxxxx = _snowman.getMaxU();
      float _snowmanxxxxxxxxxxxxxxx = _snowman.getMinV();
      float _snowmanxxxxxxxxxxxxxxxx = _snowman.getMaxV();
      float _snowmanxxxxxxxxxxxxxxxxx = _snowman.getMinU();
      float _snowmanxxxxxxxxxxxxxxxxxx = _snowman.getMaxU();
      float _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.getMinV();
      float _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman.getFrameV(1.0);
      float _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.getMinU();
      float _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getFrameU(1.0);
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getMinV();
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getMaxV();
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman / 16;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman / 16;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = 16.0 / (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 16.0 / (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx + (float)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1) * 16);
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx + (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 16);
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx + (float)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1) * 16);
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx + (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 16);
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.floor(_snowman.getX());
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.floor(
               _snowman.getY() + (double)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0F / 16.0F)
            );
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.floor(_snowman.getZ());
            Direction _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getHorizontalFacing();
            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.NORTH) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.floor(
                  _snowman.getX() + (double)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0F / 16.0F)
               );
            }

            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.WEST) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.floor(
                  _snowman.getZ() - (double)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0F / 16.0F)
               );
            }

            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.SOUTH) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.floor(
                  _snowman.getX() - (double)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0F / 16.0F)
               );
            }

            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.EAST) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.floor(
                  _snowman.getZ() + (double)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0F / 16.0F)
               );
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = WorldRenderer.getLightmapCoordinates(
               _snowman.world, new BlockPos(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            );
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getFrameU(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            );
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getFrameU(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx - (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1))
            );
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getFrameV(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx * (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            );
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getFrameV(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx * (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx - (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1))
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               0,
               -1,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               0,
               -1,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               0,
               -1,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               0,
               -1,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxx,
               0.5F,
               0,
               0,
               1,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxx,
               0.5F,
               0,
               0,
               1,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxx,
               0.5F,
               0,
               0,
               1,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxx,
               0.5F,
               0,
               0,
               1,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               1,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               1,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxx,
               0.5F,
               0,
               1,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxx,
               0.5F,
               0,
               1,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxx,
               0.5F,
               0,
               -1,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxx,
               0.5F,
               0,
               -1,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               -1,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               -1,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
               0.5F,
               -1,
               0,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
               0.5F,
               -1,
               0,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               -1,
               0,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               -1,
               0,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               1,
               0,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               1,
               0,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
               0.5F,
               1,
               0,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.method_23188(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
               0.5F,
               1,
               0,
               0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
         }
      }
   }

   private void method_23188(Matrix4f _snowman, Matrix3f _snowman, VertexConsumer _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      _snowman.vertex(_snowman, _snowman, _snowman, _snowman).color(255, 255, 255, 255).texture(_snowman, _snowman).overlay(OverlayTexture.DEFAULT_UV).light(_snowman).normal(_snowman, (float)_snowman, (float)_snowman, (float)_snowman).next();
   }
}
