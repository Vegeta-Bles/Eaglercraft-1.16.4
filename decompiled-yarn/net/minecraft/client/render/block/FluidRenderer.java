package net.minecraft.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;

public class FluidRenderer {
   private final Sprite[] lavaSprites = new Sprite[2];
   private final Sprite[] waterSprites = new Sprite[2];
   private Sprite waterOverlaySprite;

   public FluidRenderer() {
   }

   protected void onResourceReload() {
      this.lavaSprites[0] = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.LAVA.getDefaultState()).getSprite();
      this.lavaSprites[1] = ModelLoader.LAVA_FLOW.getSprite();
      this.waterSprites[0] = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.WATER.getDefaultState()).getSprite();
      this.waterSprites[1] = ModelLoader.WATER_FLOW.getSprite();
      this.waterOverlaySprite = ModelLoader.WATER_OVERLAY.getSprite();
   }

   private static boolean isSameFluid(BlockView world, BlockPos pos, Direction side, FluidState state) {
      BlockPos _snowman = pos.offset(side);
      FluidState _snowmanx = world.getFluidState(_snowman);
      return _snowmanx.getFluid().matchesType(state.getFluid());
   }

   private static boolean method_29710(BlockView _snowman, Direction _snowman, float _snowman, BlockPos _snowman, BlockState _snowman) {
      if (_snowman.isOpaque()) {
         VoxelShape _snowmanxxxxx = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, (double)_snowman, 1.0);
         VoxelShape _snowmanxxxxxx = _snowman.getCullingShape(_snowman, _snowman);
         return VoxelShapes.isSideCovered(_snowmanxxxxx, _snowmanxxxxxx, _snowman);
      } else {
         return false;
      }
   }

   private static boolean isSideCovered(BlockView world, BlockPos pos, Direction direction, float maxDeviation) {
      BlockPos _snowman = pos.offset(direction);
      BlockState _snowmanx = world.getBlockState(_snowman);
      return method_29710(world, direction, maxDeviation, _snowman, _snowmanx);
   }

   private static boolean method_29709(BlockView _snowman, BlockPos _snowman, BlockState _snowman, Direction _snowman) {
      return method_29710(_snowman, _snowman.getOpposite(), 1.0F, _snowman, _snowman);
   }

   public static boolean method_29708(BlockRenderView _snowman, BlockPos _snowman, FluidState _snowman, BlockState _snowman, Direction _snowman) {
      return !method_29709(_snowman, _snowman, _snowman, _snowman) && !isSameFluid(_snowman, _snowman, _snowman, _snowman);
   }

   public boolean render(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, FluidState state) {
      boolean _snowman = state.isIn(FluidTags.LAVA);
      Sprite[] _snowmanx = _snowman ? this.lavaSprites : this.waterSprites;
      BlockState _snowmanxx = world.getBlockState(pos);
      int _snowmanxxx = _snowman ? 16777215 : BiomeColors.getWaterColor(world, pos);
      float _snowmanxxxx = (float)(_snowmanxxx >> 16 & 0xFF) / 255.0F;
      float _snowmanxxxxx = (float)(_snowmanxxx >> 8 & 0xFF) / 255.0F;
      float _snowmanxxxxxx = (float)(_snowmanxxx & 0xFF) / 255.0F;
      boolean _snowmanxxxxxxx = !isSameFluid(world, pos, Direction.UP, state);
      boolean _snowmanxxxxxxxx = method_29708(world, pos, state, _snowmanxx, Direction.DOWN) && !isSideCovered(world, pos, Direction.DOWN, 0.8888889F);
      boolean _snowmanxxxxxxxxx = method_29708(world, pos, state, _snowmanxx, Direction.NORTH);
      boolean _snowmanxxxxxxxxxx = method_29708(world, pos, state, _snowmanxx, Direction.SOUTH);
      boolean _snowmanxxxxxxxxxxx = method_29708(world, pos, state, _snowmanxx, Direction.WEST);
      boolean _snowmanxxxxxxxxxxxx = method_29708(world, pos, state, _snowmanxx, Direction.EAST);
      if (!_snowmanxxxxxxx && !_snowmanxxxxxxxx && !_snowmanxxxxxxxxxxxx && !_snowmanxxxxxxxxxxx && !_snowmanxxxxxxxxx && !_snowmanxxxxxxxxxx) {
         return false;
      } else {
         boolean _snowmanxxxxxxxxxxxxx = false;
         float _snowmanxxxxxxxxxxxxxx = world.getBrightness(Direction.DOWN, true);
         float _snowmanxxxxxxxxxxxxxxx = world.getBrightness(Direction.UP, true);
         float _snowmanxxxxxxxxxxxxxxxx = world.getBrightness(Direction.NORTH, true);
         float _snowmanxxxxxxxxxxxxxxxxx = world.getBrightness(Direction.WEST, true);
         float _snowmanxxxxxxxxxxxxxxxxxx = this.getNorthWestCornerFluidHeight(world, pos, state.getFluid());
         float _snowmanxxxxxxxxxxxxxxxxxxx = this.getNorthWestCornerFluidHeight(world, pos.south(), state.getFluid());
         float _snowmanxxxxxxxxxxxxxxxxxxxx = this.getNorthWestCornerFluidHeight(world, pos.east().south(), state.getFluid());
         float _snowmanxxxxxxxxxxxxxxxxxxxxx = this.getNorthWestCornerFluidHeight(world, pos.east(), state.getFluid());
         double _snowmanxxxxxxxxxxxxxxxxxxxxxx = (double)(pos.getX() & 15);
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (double)(pos.getY() & 15);
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = (double)(pos.getZ() & 15);
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = 0.001F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx ? 0.001F : 0.0F;
         if (_snowmanxxxxxxx
            && !isSideCovered(
               world, pos, Direction.UP, Math.min(Math.min(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx), Math.min(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx))
            )) {
            _snowmanxxxxxxxxxxxxx = true;
            _snowmanxxxxxxxxxxxxxxxxxx -= 0.001F;
            _snowmanxxxxxxxxxxxxxxxxxxx -= 0.001F;
            _snowmanxxxxxxxxxxxxxxxxxxxx -= 0.001F;
            _snowmanxxxxxxxxxxxxxxxxxxxxx -= 0.001F;
            Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = state.getVelocity(world, pos);
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.x == 0.0 && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.z == 0.0) {
               Sprite _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx[0];
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameU(0.0);
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameV(0.0);
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameV(16.0);
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameU(16.0);
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            } else {
               Sprite _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx[1];
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)MathHelper.atan2(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.z, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.x)
                  - (float) (Math.PI / 2);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 8.0F;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameU(
                  (double)(8.0F + (-_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 16.0F)
               );
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameV(
                  (double)(8.0F + (-_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 16.0F)
               );
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameU(
                  (double)(8.0F + (-_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 16.0F)
               );
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameV(
                  (double)(8.0F + (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 16.0F)
               );
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameU(
                  (double)(8.0F + (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 16.0F)
               );
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameV(
                  (double)(8.0F + (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 16.0F)
               );
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameU(
                  (double)(8.0F + (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 16.0F)
               );
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameV(
                  (double)(8.0F + (-_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 16.0F)
               );
            }

            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               )
               / 4.0F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                     + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                     + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                     + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               )
               / 4.0F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)_snowmanx[0].getWidth() / (_snowmanx[0].getMaxU() - _snowmanx[0].getMinU());
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)_snowmanx[0].getHeight() / (_snowmanx[0].getMaxV() - _snowmanx[0].getMinV());
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 4.0F
               / Math.max(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getLight(world, pos);
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx * _snowmanxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxx;
            this.vertex(
               vertexConsumer,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               vertexConsumer,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               vertexConsumer,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               vertexConsumer,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            if (state.method_15756(world, pos.up())) {
               this.vertex(
                  vertexConsumer,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
               this.vertex(
                  vertexConsumer,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1.0,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
               this.vertex(
                  vertexConsumer,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1.0,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
               this.vertex(
                  vertexConsumer,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
            }
         }

         if (_snowmanxxxxxxxx) {
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx[0].getMinU();
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx[0].getMaxU();
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx[0].getMinV();
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx[0].getMaxV();
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getLight(world, pos.down());
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * _snowmanxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * _snowmanxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxx;
            this.vertex(
               vertexConsumer,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               vertexConsumer,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               vertexConsumer,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               vertexConsumer,
               _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            _snowmanxxxxxxxxxxxxx = true;
         }

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            Direction _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == 0) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1.0;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.001F;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.001F;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Direction.NORTH;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx;
            } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == 1) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1.0;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0 - 0.001F;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0 - 0.001F;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Direction.SOUTH;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx;
            } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == 2) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.001F;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.001F;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Direction.WEST;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx;
            } else {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1.0 - 0.001F;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1.0 - 0.001F;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Direction.EAST;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
            }

            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               && !isSideCovered(
                  world,
                  pos,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  Math.max(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
               )) {
               _snowmanxxxxxxxxxxxxx = true;
               BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = pos.offset(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
               Sprite _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx[1];
               if (!_snowman) {
                  Block _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx).getBlock();
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx instanceof TransparentBlock
                     || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx instanceof LeavesBlock) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.waterOverlaySprite;
                  }
               }

               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameU(0.0);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameU(8.0);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameV(
                  (double)((1.0F - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 16.0F * 0.5F)
               );
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameV(
                  (double)((1.0F - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 16.0F * 0.5F)
               );
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFrameV(8.0);
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getLight(world, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < 2
                  ? _snowmanxxxxxxxxxxxxxxxx
                  : _snowmanxxxxxxxxxxxxxxxxx;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx
                  * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  * _snowmanxxxx;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx
                  * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  * _snowmanxxxxx;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx
                  * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  * _snowmanxxxxxx;
               this.vertex(
                  vertexConsumer,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
               this.vertex(
                  vertexConsumer,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
               this.vertex(
                  vertexConsumer,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
               this.vertex(
                  vertexConsumer,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != this.waterOverlaySprite) {
                  this.vertex(
                     vertexConsumer,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  );
                  this.vertex(
                     vertexConsumer,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  );
                  this.vertex(
                     vertexConsumer,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  );
                  this.vertex(
                     vertexConsumer,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  );
               }
            }
         }

         return _snowmanxxxxxxxxxxxxx;
      }
   }

   private void vertex(VertexConsumer vertexConsumer, double x, double y, double z, float red, float green, float blue, float u, float v, int light) {
      vertexConsumer.vertex(x, y, z).color(red, green, blue, 1.0F).texture(u, v).light(light).normal(0.0F, 1.0F, 0.0F).next();
   }

   private int getLight(BlockRenderView world, BlockPos pos) {
      int _snowman = WorldRenderer.getLightmapCoordinates(world, pos);
      int _snowmanx = WorldRenderer.getLightmapCoordinates(world, pos.up());
      int _snowmanxx = _snowman & 0xFF;
      int _snowmanxxx = _snowmanx & 0xFF;
      int _snowmanxxxx = _snowman >> 16 & 0xFF;
      int _snowmanxxxxx = _snowmanx >> 16 & 0xFF;
      return (_snowmanxx > _snowmanxxx ? _snowmanxx : _snowmanxxx) | (_snowmanxxxx > _snowmanxxxxx ? _snowmanxxxx : _snowmanxxxxx) << 16;
   }

   private float getNorthWestCornerFluidHeight(BlockView world, BlockPos pos, Fluid fluid) {
      int _snowman = 0;
      float _snowmanx = 0.0F;

      for (int _snowmanxx = 0; _snowmanxx < 4; _snowmanxx++) {
         BlockPos _snowmanxxx = pos.add(-(_snowmanxx & 1), 0, -(_snowmanxx >> 1 & 1));
         if (world.getFluidState(_snowmanxxx.up()).getFluid().matchesType(fluid)) {
            return 1.0F;
         }

         FluidState _snowmanxxxx = world.getFluidState(_snowmanxxx);
         if (_snowmanxxxx.getFluid().matchesType(fluid)) {
            float _snowmanxxxxx = _snowmanxxxx.getHeight(world, _snowmanxxx);
            if (_snowmanxxxxx >= 0.8F) {
               _snowmanx += _snowmanxxxxx * 10.0F;
               _snowman += 10;
            } else {
               _snowmanx += _snowmanxxxxx;
               _snowman++;
            }
         } else if (!world.getBlockState(_snowmanxxx).getMaterial().isSolid()) {
            _snowman++;
         }
      }

      return _snowmanx / (float)_snowman;
   }
}
