package net.minecraft.world;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public interface BlockView {
   @Nullable
   BlockEntity getBlockEntity(BlockPos pos);

   BlockState getBlockState(BlockPos pos);

   FluidState getFluidState(BlockPos pos);

   default int getLuminance(BlockPos pos) {
      return this.getBlockState(pos).getLuminance();
   }

   default int getMaxLightLevel() {
      return 15;
   }

   default int getHeight() {
      return 256;
   }

   default Stream<BlockState> method_29546(Box _snowman) {
      return BlockPos.stream(_snowman).map(this::getBlockState);
   }

   default BlockHitResult raycast(RaycastContext context) {
      return raycast(context, (_snowman, _snowmanx) -> {
         BlockState _snowmanxx = this.getBlockState(_snowmanx);
         FluidState _snowmanxxx = this.getFluidState(_snowmanx);
         Vec3d _snowmanxxxx = _snowman.getStart();
         Vec3d _snowmanxxxxx = _snowman.getEnd();
         VoxelShape _snowmanxxxxxx = _snowman.getBlockShape(_snowmanxx, this, _snowmanx);
         BlockHitResult _snowmanxxxxxxx = this.raycastBlock(_snowmanxxxx, _snowmanxxxxx, _snowmanx, _snowmanxxxxxx, _snowmanxx);
         VoxelShape _snowmanxxxxxxxx = _snowman.getFluidShape(_snowmanxxx, this, _snowmanx);
         BlockHitResult _snowmanxxxxxxxxx = _snowmanxxxxxxxx.raycast(_snowmanxxxx, _snowmanxxxxx, _snowmanx);
         double _snowmanxxxxxxxxxx = _snowmanxxxxxxx == null ? Double.MAX_VALUE : _snowman.getStart().squaredDistanceTo(_snowmanxxxxxxx.getPos());
         double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx == null ? Double.MAX_VALUE : _snowman.getStart().squaredDistanceTo(_snowmanxxxxxxxxx.getPos());
         return _snowmanxxxxxxxxxx <= _snowmanxxxxxxxxxxx ? _snowmanxxxxxxx : _snowmanxxxxxxxxx;
      }, _snowman -> {
         Vec3d _snowmanx = _snowman.getStart().subtract(_snowman.getEnd());
         return BlockHitResult.createMissed(_snowman.getEnd(), Direction.getFacing(_snowmanx.x, _snowmanx.y, _snowmanx.z), new BlockPos(_snowman.getEnd()));
      });
   }

   @Nullable
   default BlockHitResult raycastBlock(Vec3d start, Vec3d end, BlockPos pos, VoxelShape shape, BlockState state) {
      BlockHitResult _snowman = shape.raycast(start, end, pos);
      if (_snowman != null) {
         BlockHitResult _snowmanx = state.getRaycastShape(this, pos).raycast(start, end, pos);
         if (_snowmanx != null && _snowmanx.getPos().subtract(start).lengthSquared() < _snowman.getPos().subtract(start).lengthSquared()) {
            return _snowman.withSide(_snowmanx.getSide());
         }
      }

      return _snowman;
   }

   default double getDismountHeight(VoxelShape blockCollisionShape, Supplier<VoxelShape> belowBlockCollisionShapeGetter) {
      if (!blockCollisionShape.isEmpty()) {
         return blockCollisionShape.getMax(Direction.Axis.Y);
      } else {
         double _snowman = belowBlockCollisionShapeGetter.get().getMax(Direction.Axis.Y);
         return _snowman >= 1.0 ? _snowman - 1.0 : Double.NEGATIVE_INFINITY;
      }
   }

   default double getDismountHeight(BlockPos pos) {
      return this.getDismountHeight(this.getBlockState(pos).getCollisionShape(this, pos), () -> {
         BlockPos _snowmanx = pos.down();
         return this.getBlockState(_snowmanx).getCollisionShape(this, _snowmanx);
      });
   }

   static <T> T raycast(RaycastContext _snowman, BiFunction<RaycastContext, BlockPos, T> context, Function<RaycastContext, T> blockRaycaster) {
      Vec3d _snowmanx = _snowman.getStart();
      Vec3d _snowmanxx = _snowman.getEnd();
      if (_snowmanx.equals(_snowmanxx)) {
         return blockRaycaster.apply(_snowman);
      } else {
         double _snowmanxxx = MathHelper.lerp(-1.0E-7, _snowmanxx.x, _snowmanx.x);
         double _snowmanxxxx = MathHelper.lerp(-1.0E-7, _snowmanxx.y, _snowmanx.y);
         double _snowmanxxxxx = MathHelper.lerp(-1.0E-7, _snowmanxx.z, _snowmanx.z);
         double _snowmanxxxxxx = MathHelper.lerp(-1.0E-7, _snowmanx.x, _snowmanxx.x);
         double _snowmanxxxxxxx = MathHelper.lerp(-1.0E-7, _snowmanx.y, _snowmanxx.y);
         double _snowmanxxxxxxxx = MathHelper.lerp(-1.0E-7, _snowmanx.z, _snowmanxx.z);
         int _snowmanxxxxxxxxx = MathHelper.floor(_snowmanxxxxxx);
         int _snowmanxxxxxxxxxx = MathHelper.floor(_snowmanxxxxxxx);
         int _snowmanxxxxxxxxxxx = MathHelper.floor(_snowmanxxxxxxxx);
         BlockPos.Mutable _snowmanxxxxxxxxxxxx = new BlockPos.Mutable(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
         T _snowmanxxxxxxxxxxxxx = context.apply(_snowman, _snowmanxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxx != null) {
            return _snowmanxxxxxxxxxxxxx;
         } else {
            double _snowmanxxxxxxxxxxxxxx = _snowmanxxx - _snowmanxxxxxx;
            double _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx - _snowmanxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxx - _snowmanxxxxxxxx;
            int _snowmanxxxxxxxxxxxxxxxxx = MathHelper.sign(_snowmanxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxx = MathHelper.sign(_snowmanxxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxxx = MathHelper.sign(_snowmanxxxxxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx == 0 ? Double.MAX_VALUE : (double)_snowmanxxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx == 0 ? Double.MAX_VALUE : (double)_snowmanxxxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx == 0 ? Double.MAX_VALUE : (double)_snowmanxxxxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx
               * (_snowmanxxxxxxxxxxxxxxxxx > 0 ? 1.0 - MathHelper.fractionalPart(_snowmanxxxxxx) : MathHelper.fractionalPart(_snowmanxxxxxx));
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx
               * (_snowmanxxxxxxxxxxxxxxxxxx > 0 ? 1.0 - MathHelper.fractionalPart(_snowmanxxxxxxx) : MathHelper.fractionalPart(_snowmanxxxxxxx));
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx
               * (_snowmanxxxxxxxxxxxxxxxxxxx > 0 ? 1.0 - MathHelper.fractionalPart(_snowmanxxxxxxxx) : MathHelper.fractionalPart(_snowmanxxxxxxxx));

            while (_snowmanxxxxxxxxxxxxxxxxxxxxxxx <= 1.0 || _snowmanxxxxxxxxxxxxxxxxxxxxxxxx <= 1.0 || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx <= 1.0) {
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) {
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxx;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxx;
                  } else {
                     _snowmanxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxx;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxxxx;
                  }
               } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxxx;
               } else {
                  _snowmanxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxxxx;
               }

               T _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = context.apply(_snowman, _snowmanxxxxxxxxxxxx.set(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx));
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx != null) {
                  return _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
               }
            }

            return blockRaycaster.apply(_snowman);
         }
      }
   }
}
