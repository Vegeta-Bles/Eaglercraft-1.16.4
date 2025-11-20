package net.minecraft.client.render.entity;

import java.util.Random;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FallingBlockEntityRenderer extends EntityRenderer<FallingBlockEntity> {
   public FallingBlockEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
      this.shadowRadius = 0.5F;
   }

   public void render(FallingBlockEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      BlockState _snowmanxxxxxx = _snowman.getBlockState();
      if (_snowmanxxxxxx.getRenderType() == BlockRenderType.MODEL) {
         World _snowmanxxxxxxx = _snowman.getWorldClient();
         if (_snowmanxxxxxx != _snowmanxxxxxxx.getBlockState(_snowman.getBlockPos()) && _snowmanxxxxxx.getRenderType() != BlockRenderType.INVISIBLE) {
            _snowman.push();
            BlockPos _snowmanxxxxxxxx = new BlockPos(_snowman.getX(), _snowman.getBoundingBox().maxY, _snowman.getZ());
            _snowman.translate(-0.5, 0.0, -0.5);
            BlockRenderManager _snowmanxxxxxxxxx = MinecraftClient.getInstance().getBlockRenderManager();
            _snowmanxxxxxxxxx.getModelRenderer()
               .render(
                  _snowmanxxxxxxx,
                  _snowmanxxxxxxxxx.getModel(_snowmanxxxxxx),
                  _snowmanxxxxxx,
                  _snowmanxxxxxxxx,
                  _snowman,
                  _snowman.getBuffer(RenderLayers.getMovingBlockLayer(_snowmanxxxxxx)),
                  false,
                  new Random(),
                  _snowmanxxxxxx.getRenderingSeed(_snowman.getFallingBlockPos()),
                  OverlayTexture.DEFAULT_UV
               );
            _snowman.pop();
            super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         }
      }
   }

   public Identifier getTexture(FallingBlockEntity _snowman) {
      return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
   }
}
