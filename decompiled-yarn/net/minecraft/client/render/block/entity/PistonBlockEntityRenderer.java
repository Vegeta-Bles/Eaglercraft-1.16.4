package net.minecraft.client.render.block.entity;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.block.enums.PistonType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PistonBlockEntityRenderer extends BlockEntityRenderer<PistonBlockEntity> {
   private final BlockRenderManager manager = MinecraftClient.getInstance().getBlockRenderManager();

   public PistonBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(PistonBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      World _snowmanxxxxxx = _snowman.getWorld();
      if (_snowmanxxxxxx != null) {
         BlockPos _snowmanxxxxxxx = _snowman.getPos().offset(_snowman.getMovementDirection().getOpposite());
         BlockState _snowmanxxxxxxxx = _snowman.getPushedBlock();
         if (!_snowmanxxxxxxxx.isAir()) {
            BlockModelRenderer.enableBrightnessCache();
            _snowman.push();
            _snowman.translate((double)_snowman.getRenderOffsetX(_snowman), (double)_snowman.getRenderOffsetY(_snowman), (double)_snowman.getRenderOffsetZ(_snowman));
            if (_snowmanxxxxxxxx.isOf(Blocks.PISTON_HEAD) && _snowman.getProgress(_snowman) <= 4.0F) {
               _snowmanxxxxxxxx = _snowmanxxxxxxxx.with(PistonHeadBlock.SHORT, Boolean.valueOf(_snowman.getProgress(_snowman) <= 0.5F));
               this.method_3575(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman, _snowman, _snowmanxxxxxx, false, _snowman);
            } else if (_snowman.isSource() && !_snowman.isExtending()) {
               PistonType _snowmanxxxxxxxxx = _snowmanxxxxxxxx.isOf(Blocks.STICKY_PISTON) ? PistonType.STICKY : PistonType.DEFAULT;
               BlockState _snowmanxxxxxxxxxx = Blocks.PISTON_HEAD
                  .getDefaultState()
                  .with(PistonHeadBlock.TYPE, _snowmanxxxxxxxxx)
                  .with(PistonHeadBlock.FACING, _snowmanxxxxxxxx.get(PistonBlock.FACING));
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.with(PistonHeadBlock.SHORT, Boolean.valueOf(_snowman.getProgress(_snowman) >= 0.5F));
               this.method_3575(_snowmanxxxxxxx, _snowmanxxxxxxxxxx, _snowman, _snowman, _snowmanxxxxxx, false, _snowman);
               BlockPos _snowmanxxxxxxxxxxx = _snowmanxxxxxxx.offset(_snowman.getMovementDirection());
               _snowman.pop();
               _snowman.push();
               _snowmanxxxxxxxx = _snowmanxxxxxxxx.with(PistonBlock.EXTENDED, Boolean.valueOf(true));
               this.method_3575(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxx, _snowman, _snowman, _snowmanxxxxxx, true, _snowman);
            } else {
               this.method_3575(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman, _snowman, _snowmanxxxxxx, false, _snowman);
            }

            _snowman.pop();
            BlockModelRenderer.disableBrightnessCache();
         }
      }
   }

   private void method_3575(BlockPos _snowman, BlockState _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, World _snowman, boolean _snowman, int _snowman) {
      RenderLayer _snowmanxxxxxxx = RenderLayers.getMovingBlockLayer(_snowman);
      VertexConsumer _snowmanxxxxxxxx = _snowman.getBuffer(_snowmanxxxxxxx);
      this.manager.getModelRenderer().render(_snowman, this.manager.getModel(_snowman), _snowman, _snowman, _snowman, _snowmanxxxxxxxx, _snowman, new Random(), _snowman.getRenderingSeed(_snowman), _snowman);
   }
}
