package net.minecraft.client.render.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class StructureBlockBlockEntityRenderer extends BlockEntityRenderer<StructureBlockBlockEntity> {
   public StructureBlockBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(StructureBlockBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      if (MinecraftClient.getInstance().player.isCreativeLevelTwoOp() || MinecraftClient.getInstance().player.isSpectator()) {
         BlockPos _snowmanxxxxxx = _snowman.getOffset();
         BlockPos _snowmanxxxxxxx = _snowman.getSize();
         if (_snowmanxxxxxxx.getX() >= 1 && _snowmanxxxxxxx.getY() >= 1 && _snowmanxxxxxxx.getZ() >= 1) {
            if (_snowman.getMode() == StructureBlockMode.SAVE || _snowman.getMode() == StructureBlockMode.LOAD) {
               double _snowmanxxxxxxxx = (double)_snowmanxxxxxx.getX();
               double _snowmanxxxxxxxxx = (double)_snowmanxxxxxx.getZ();
               double _snowmanxxxxxxxxxx = (double)_snowmanxxxxxx.getY();
               double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx + (double)_snowmanxxxxxxx.getY();
               double _snowmanxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxx;
               switch (_snowman.getMirror()) {
                  case LEFT_RIGHT:
                     _snowmanxxxxxxxxxxxx = (double)_snowmanxxxxxxx.getX();
                     _snowmanxxxxxxxxxxxxx = (double)(-_snowmanxxxxxxx.getZ());
                     break;
                  case FRONT_BACK:
                     _snowmanxxxxxxxxxxxx = (double)(-_snowmanxxxxxxx.getX());
                     _snowmanxxxxxxxxxxxxx = (double)_snowmanxxxxxxx.getZ();
                     break;
                  default:
                     _snowmanxxxxxxxxxxxx = (double)_snowmanxxxxxxx.getX();
                     _snowmanxxxxxxxxxxxxx = (double)_snowmanxxxxxxx.getZ();
               }

               double _snowmanxxxxxxxx;
               double _snowmanxxxxxxxxx;
               double _snowmanxxxxxxxxxx;
               double _snowmanxxxxxxxxxxx;
               switch (_snowman.getRotation()) {
                  case CLOCKWISE_90:
                     _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxx < 0.0 ? _snowmanxxxxxxxx : _snowmanxxxxxxxx + 1.0;
                     _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxx < 0.0 ? _snowmanxxxxxxxxx + 1.0 : _snowmanxxxxxxxxx;
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxx - _snowmanxxxxxxxxxxxxx;
                     _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxx;
                     break;
                  case CLOCKWISE_180:
                     _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxx < 0.0 ? _snowmanxxxxxxxx : _snowmanxxxxxxxx + 1.0;
                     _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxx < 0.0 ? _snowmanxxxxxxxxx : _snowmanxxxxxxxxx + 1.0;
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxx - _snowmanxxxxxxxxxxxx;
                     _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxxx;
                     break;
                  case COUNTERCLOCKWISE_90:
                     _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxx < 0.0 ? _snowmanxxxxxxxx + 1.0 : _snowmanxxxxxxxx;
                     _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxx < 0.0 ? _snowmanxxxxxxxxx : _snowmanxxxxxxxxx + 1.0;
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxx + _snowmanxxxxxxxxxxxxx;
                     _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxx;
                     break;
                  default:
                     _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxx < 0.0 ? _snowmanxxxxxxxx + 1.0 : _snowmanxxxxxxxx;
                     _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxx < 0.0 ? _snowmanxxxxxxxxx + 1.0 : _snowmanxxxxxxxxx;
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxx + _snowmanxxxxxxxxxxxx;
                     _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxx;
               }

               float _snowmanxxxxxxxx = 1.0F;
               float _snowmanxxxxxxxxx = 0.9F;
               float _snowmanxxxxxxxxxx = 0.5F;
               VertexConsumer _snowmanxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getLines());
               if (_snowman.getMode() == StructureBlockMode.SAVE || _snowman.shouldShowBoundingBox()) {
                  WorldRenderer.drawBox(
                     _snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx, 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F
                  );
               }

               if (_snowman.getMode() == StructureBlockMode.SAVE && _snowman.shouldShowAir()) {
                  this.method_3585(_snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxx, true, _snowman);
                  this.method_3585(_snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxx, false, _snowman);
               }
            }
         }
      }
   }

   private void method_3585(StructureBlockBlockEntity _snowman, VertexConsumer _snowman, BlockPos _snowman, boolean _snowman, MatrixStack _snowman) {
      BlockView _snowmanxxxxx = _snowman.getWorld();
      BlockPos _snowmanxxxxxx = _snowman.getPos();
      BlockPos _snowmanxxxxxxx = _snowmanxxxxxx.add(_snowman);

      for (BlockPos _snowmanxxxxxxxx : BlockPos.iterate(_snowmanxxxxxxx, _snowmanxxxxxxx.add(_snowman.getSize()).add(-1, -1, -1))) {
         BlockState _snowmanxxxxxxxxx = _snowmanxxxxx.getBlockState(_snowmanxxxxxxxx);
         boolean _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.isAir();
         boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.isOf(Blocks.STRUCTURE_VOID);
         if (_snowmanxxxxxxxxxx || _snowmanxxxxxxxxxxx) {
            float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx ? 0.05F : 0.0F;
            double _snowmanxxxxxxxxxxxxx = (double)((float)(_snowmanxxxxxxxx.getX() - _snowmanxxxxxx.getX()) + 0.45F - _snowmanxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxx = (double)((float)(_snowmanxxxxxxxx.getY() - _snowmanxxxxxx.getY()) + 0.45F - _snowmanxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxx = (double)((float)(_snowmanxxxxxxxx.getZ() - _snowmanxxxxxx.getZ()) + 0.45F - _snowmanxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxx = (double)((float)(_snowmanxxxxxxxx.getX() - _snowmanxxxxxx.getX()) + 0.55F + _snowmanxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxxx = (double)((float)(_snowmanxxxxxxxx.getY() - _snowmanxxxxxx.getY()) + 0.55F + _snowmanxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxxxx = (double)((float)(_snowmanxxxxxxxx.getZ() - _snowmanxxxxxx.getZ()) + 0.55F + _snowmanxxxxxxxxxxxx);
            if (_snowman) {
               WorldRenderer.drawBox(
                  _snowman,
                  _snowman,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx,
                  0.0F,
                  0.0F,
                  0.0F,
                  1.0F,
                  0.0F,
                  0.0F,
                  0.0F
               );
            } else if (_snowmanxxxxxxxxxx) {
               WorldRenderer.drawBox(
                  _snowman,
                  _snowman,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx,
                  0.5F,
                  0.5F,
                  1.0F,
                  1.0F,
                  0.5F,
                  0.5F,
                  1.0F
               );
            } else {
               WorldRenderer.drawBox(
                  _snowman,
                  _snowman,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx,
                  1.0F,
                  0.25F,
                  0.25F,
                  1.0F,
                  1.0F,
                  0.25F,
                  0.25F
               );
            }
         }
      }
   }

   public boolean rendersOutsideBoundingBox(StructureBlockBlockEntity _snowman) {
      return true;
   }
}
