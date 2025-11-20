package net.minecraft.world.dimension;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.class_5459;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.WorldAccess;

public class AreaHelper {
   private static final AbstractBlock.ContextPredicate IS_VALID_FRAME_BLOCK = (_snowman, _snowmanx, _snowmanxx) -> _snowman.isOf(Blocks.OBSIDIAN);
   private final WorldAccess world;
   private final Direction.Axis axis;
   private final Direction negativeDir;
   private int foundPortalBlocks;
   @Nullable
   private BlockPos lowerCorner;
   private int height;
   private int width;

   public static Optional<AreaHelper> method_30485(WorldAccess _snowman, BlockPos _snowman, Direction.Axis _snowman) {
      return method_30486(_snowman, _snowman, _snowmanxxx -> _snowmanxxx.isValid() && _snowmanxxx.foundPortalBlocks == 0, _snowman);
   }

   public static Optional<AreaHelper> method_30486(WorldAccess _snowman, BlockPos _snowman, Predicate<AreaHelper> _snowman, Direction.Axis _snowman) {
      Optional<AreaHelper> _snowmanxxxx = Optional.of(new AreaHelper(_snowman, _snowman, _snowman)).filter(_snowman);
      if (_snowmanxxxx.isPresent()) {
         return _snowmanxxxx;
      } else {
         Direction.Axis _snowmanxxxxx = _snowman == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
         return Optional.of(new AreaHelper(_snowman, _snowman, _snowmanxxxxx)).filter(_snowman);
      }
   }

   public AreaHelper(WorldAccess world, BlockPos _snowman, Direction.Axis _snowman) {
      this.world = world;
      this.axis = _snowman;
      this.negativeDir = _snowman == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
      this.lowerCorner = this.method_30492(_snowman);
      if (this.lowerCorner == null) {
         this.lowerCorner = _snowman;
         this.width = 1;
         this.height = 1;
      } else {
         this.width = this.method_30495();
         if (this.width > 0) {
            this.height = this.method_30496();
         }
      }
   }

   @Nullable
   private BlockPos method_30492(BlockPos _snowman) {
      int _snowmanx = Math.max(0, _snowman.getY() - 21);

      while (_snowman.getY() > _snowmanx && validStateInsidePortal(this.world.getBlockState(_snowman.down()))) {
         _snowman = _snowman.down();
      }

      Direction _snowmanxx = this.negativeDir.getOpposite();
      int _snowmanxxx = this.method_30493(_snowman, _snowmanxx) - 1;
      return _snowmanxxx < 0 ? null : _snowman.offset(_snowmanxx, _snowmanxxx);
   }

   private int method_30495() {
      int _snowman = this.method_30493(this.lowerCorner, this.negativeDir);
      return _snowman >= 2 && _snowman <= 21 ? _snowman : 0;
   }

   private int method_30493(BlockPos _snowman, Direction _snowman) {
      BlockPos.Mutable _snowmanxx = new BlockPos.Mutable();

      for (int _snowmanxxx = 0; _snowmanxxx <= 21; _snowmanxxx++) {
         _snowmanxx.set(_snowman).move(_snowman, _snowmanxxx);
         BlockState _snowmanxxxx = this.world.getBlockState(_snowmanxx);
         if (!validStateInsidePortal(_snowmanxxxx)) {
            if (IS_VALID_FRAME_BLOCK.test(_snowmanxxxx, this.world, _snowmanxx)) {
               return _snowmanxxx;
            }
            break;
         }

         BlockState _snowmanxxxxx = this.world.getBlockState(_snowmanxx.move(Direction.DOWN));
         if (!IS_VALID_FRAME_BLOCK.test(_snowmanxxxxx, this.world, _snowmanxx)) {
            break;
         }
      }

      return 0;
   }

   private int method_30496() {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();
      int _snowmanx = this.method_30490(_snowman);
      return _snowmanx >= 3 && _snowmanx <= 21 && this.method_30491(_snowman, _snowmanx) ? _snowmanx : 0;
   }

   private boolean method_30491(BlockPos.Mutable _snowman, int _snowman) {
      for (int _snowmanxx = 0; _snowmanxx < this.width; _snowmanxx++) {
         BlockPos.Mutable _snowmanxxx = _snowman.set(this.lowerCorner).move(Direction.UP, _snowman).move(this.negativeDir, _snowmanxx);
         if (!IS_VALID_FRAME_BLOCK.test(this.world.getBlockState(_snowmanxxx), this.world, _snowmanxxx)) {
            return false;
         }
      }

      return true;
   }

   private int method_30490(BlockPos.Mutable _snowman) {
      for (int _snowmanx = 0; _snowmanx < 21; _snowmanx++) {
         _snowman.set(this.lowerCorner).move(Direction.UP, _snowmanx).move(this.negativeDir, -1);
         if (!IS_VALID_FRAME_BLOCK.test(this.world.getBlockState(_snowman), this.world, _snowman)) {
            return _snowmanx;
         }

         _snowman.set(this.lowerCorner).move(Direction.UP, _snowmanx).move(this.negativeDir, this.width);
         if (!IS_VALID_FRAME_BLOCK.test(this.world.getBlockState(_snowman), this.world, _snowman)) {
            return _snowmanx;
         }

         for (int _snowmanxx = 0; _snowmanxx < this.width; _snowmanxx++) {
            _snowman.set(this.lowerCorner).move(Direction.UP, _snowmanx).move(this.negativeDir, _snowmanxx);
            BlockState _snowmanxxx = this.world.getBlockState(_snowman);
            if (!validStateInsidePortal(_snowmanxxx)) {
               return _snowmanx;
            }

            if (_snowmanxxx.isOf(Blocks.NETHER_PORTAL)) {
               this.foundPortalBlocks++;
            }
         }
      }

      return 21;
   }

   private static boolean validStateInsidePortal(BlockState _snowman) {
      return _snowman.isAir() || _snowman.isIn(BlockTags.FIRE) || _snowman.isOf(Blocks.NETHER_PORTAL);
   }

   public boolean isValid() {
      return this.lowerCorner != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
   }

   public void createPortal() {
      BlockState _snowman = Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, this.axis);
      BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1))
         .forEach(_snowmanx -> this.world.setBlockState(_snowmanx, _snowman, 18));
   }

   public boolean wasAlreadyValid() {
      return this.isValid() && this.foundPortalBlocks == this.width * this.height;
   }

   public static Vec3d method_30494(class_5459.class_5460 _snowman, Direction.Axis _snowman, Vec3d _snowman, EntityDimensions _snowman) {
      double _snowmanxxxx = (double)_snowman.field_25937 - (double)_snowman.width;
      double _snowmanxxxxx = (double)_snowman.field_25938 - (double)_snowman.height;
      BlockPos _snowmanxxxxxx = _snowman.field_25936;
      double _snowmanxxxxxxx;
      if (_snowmanxxxx > 0.0) {
         float _snowmanxxxxxxxx = (float)_snowmanxxxxxx.getComponentAlongAxis(_snowman) + _snowman.width / 2.0F;
         _snowmanxxxxxxx = MathHelper.clamp(MathHelper.getLerpProgress(_snowman.getComponentAlongAxis(_snowman) - (double)_snowmanxxxxxxxx, 0.0, _snowmanxxxx), 0.0, 1.0);
      } else {
         _snowmanxxxxxxx = 0.5;
      }

      double _snowmanxxxxxxxx;
      if (_snowmanxxxxx > 0.0) {
         Direction.Axis _snowmanxxxxxxxxx = Direction.Axis.Y;
         _snowmanxxxxxxxx = MathHelper.clamp(
            MathHelper.getLerpProgress(_snowman.getComponentAlongAxis(_snowmanxxxxxxxxx) - (double)_snowmanxxxxxx.getComponentAlongAxis(_snowmanxxxxxxxxx), 0.0, _snowmanxxxxx), 0.0, 1.0
         );
      } else {
         _snowmanxxxxxxxx = 0.0;
      }

      Direction.Axis _snowmanxxxxxxxxx = _snowman == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
      double _snowmanxxxxxxxxxx = _snowman.getComponentAlongAxis(_snowmanxxxxxxxxx) - ((double)_snowmanxxxxxx.getComponentAlongAxis(_snowmanxxxxxxxxx) + 0.5);
      return new Vec3d(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx);
   }

   public static TeleportTarget method_30484(ServerWorld _snowman, class_5459.class_5460 _snowman, Direction.Axis _snowman, Vec3d _snowman, EntityDimensions _snowman, Vec3d _snowman, float _snowman, float _snowman) {
      BlockPos _snowmanxxxxxxxx = _snowman.field_25936;
      BlockState _snowmanxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxx);
      Direction.Axis _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.get(Properties.HORIZONTAL_AXIS);
      double _snowmanxxxxxxxxxxx = (double)_snowman.field_25937;
      double _snowmanxxxxxxxxxxxx = (double)_snowman.field_25938;
      int _snowmanxxxxxxxxxxxxx = _snowman == _snowmanxxxxxxxxxx ? 0 : 90;
      Vec3d _snowmanxxxxxxxxxxxxxx = _snowman == _snowmanxxxxxxxxxx ? _snowman : new Vec3d(_snowman.z, _snowman.y, -_snowman.x);
      double _snowmanxxxxxxxxxxxxxxx = (double)_snowman.width / 2.0 + (_snowmanxxxxxxxxxxx - (double)_snowman.width) * _snowman.getX();
      double _snowmanxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxx - (double)_snowman.height) * _snowman.getY();
      double _snowmanxxxxxxxxxxxxxxxxx = 0.5 + _snowman.getZ();
      boolean _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx == Direction.Axis.X;
      Vec3d _snowmanxxxxxxxxxxxxxxxxxxx = new Vec3d(
         (double)_snowmanxxxxxxxx.getX() + (_snowmanxxxxxxxxxxxxxxxxxx ? _snowmanxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxx),
         (double)_snowmanxxxxxxxx.getY() + _snowmanxxxxxxxxxxxxxxxx,
         (double)_snowmanxxxxxxxx.getZ() + (_snowmanxxxxxxxxxxxxxxxxxx ? _snowmanxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxx)
      );
      return new TeleportTarget(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowman + (float)_snowmanxxxxxxxxxxxxx, _snowman);
   }
}
