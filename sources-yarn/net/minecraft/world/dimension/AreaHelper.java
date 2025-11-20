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
   private static final AbstractBlock.ContextPredicate IS_VALID_FRAME_BLOCK = (arg, arg2, arg3) -> arg.isOf(Blocks.OBSIDIAN);
   private final WorldAccess world;
   private final Direction.Axis axis;
   private final Direction negativeDir;
   private int foundPortalBlocks;
   @Nullable
   private BlockPos lowerCorner;
   private int height;
   private int width;

   public static Optional<AreaHelper> method_30485(WorldAccess arg, BlockPos arg2, Direction.Axis arg3) {
      return method_30486(arg, arg2, argx -> argx.isValid() && argx.foundPortalBlocks == 0, arg3);
   }

   public static Optional<AreaHelper> method_30486(WorldAccess arg, BlockPos arg2, Predicate<AreaHelper> predicate, Direction.Axis arg3) {
      Optional<AreaHelper> optional = Optional.of(new AreaHelper(arg, arg2, arg3)).filter(predicate);
      if (optional.isPresent()) {
         return optional;
      } else {
         Direction.Axis lv = arg3 == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
         return Optional.of(new AreaHelper(arg, arg2, lv)).filter(predicate);
      }
   }

   public AreaHelper(WorldAccess world, BlockPos arg2, Direction.Axis arg3) {
      this.world = world;
      this.axis = arg3;
      this.negativeDir = arg3 == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
      this.lowerCorner = this.method_30492(arg2);
      if (this.lowerCorner == null) {
         this.lowerCorner = arg2;
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
   private BlockPos method_30492(BlockPos arg) {
      int i = Math.max(0, arg.getY() - 21);

      while (arg.getY() > i && validStateInsidePortal(this.world.getBlockState(arg.down()))) {
         arg = arg.down();
      }

      Direction lv = this.negativeDir.getOpposite();
      int j = this.method_30493(arg, lv) - 1;
      return j < 0 ? null : arg.offset(lv, j);
   }

   private int method_30495() {
      int i = this.method_30493(this.lowerCorner, this.negativeDir);
      return i >= 2 && i <= 21 ? i : 0;
   }

   private int method_30493(BlockPos arg, Direction arg2) {
      BlockPos.Mutable lv = new BlockPos.Mutable();

      for (int i = 0; i <= 21; i++) {
         lv.set(arg).move(arg2, i);
         BlockState lv2 = this.world.getBlockState(lv);
         if (!validStateInsidePortal(lv2)) {
            if (IS_VALID_FRAME_BLOCK.test(lv2, this.world, lv)) {
               return i;
            }
            break;
         }

         BlockState lv3 = this.world.getBlockState(lv.move(Direction.DOWN));
         if (!IS_VALID_FRAME_BLOCK.test(lv3, this.world, lv)) {
            break;
         }
      }

      return 0;
   }

   private int method_30496() {
      BlockPos.Mutable lv = new BlockPos.Mutable();
      int i = this.method_30490(lv);
      return i >= 3 && i <= 21 && this.method_30491(lv, i) ? i : 0;
   }

   private boolean method_30491(BlockPos.Mutable arg, int i) {
      for (int j = 0; j < this.width; j++) {
         BlockPos.Mutable lv = arg.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, j);
         if (!IS_VALID_FRAME_BLOCK.test(this.world.getBlockState(lv), this.world, lv)) {
            return false;
         }
      }

      return true;
   }

   private int method_30490(BlockPos.Mutable arg) {
      for (int i = 0; i < 21; i++) {
         arg.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, -1);
         if (!IS_VALID_FRAME_BLOCK.test(this.world.getBlockState(arg), this.world, arg)) {
            return i;
         }

         arg.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, this.width);
         if (!IS_VALID_FRAME_BLOCK.test(this.world.getBlockState(arg), this.world, arg)) {
            return i;
         }

         for (int j = 0; j < this.width; j++) {
            arg.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, j);
            BlockState lv = this.world.getBlockState(arg);
            if (!validStateInsidePortal(lv)) {
               return i;
            }

            if (lv.isOf(Blocks.NETHER_PORTAL)) {
               this.foundPortalBlocks++;
            }
         }
      }

      return 21;
   }

   private static boolean validStateInsidePortal(BlockState arg) {
      return arg.isAir() || arg.isIn(BlockTags.FIRE) || arg.isOf(Blocks.NETHER_PORTAL);
   }

   public boolean isValid() {
      return this.lowerCorner != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
   }

   public void createPortal() {
      BlockState lv = Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, this.axis);
      BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1))
         .forEach(arg2 -> this.world.setBlockState(arg2, lv, 18));
   }

   public boolean wasAlreadyValid() {
      return this.isValid() && this.foundPortalBlocks == this.width * this.height;
   }

   public static Vec3d method_30494(class_5459.class_5460 arg, Direction.Axis arg2, Vec3d arg3, EntityDimensions arg4) {
      double d = (double)arg.field_25937 - (double)arg4.width;
      double e = (double)arg.field_25938 - (double)arg4.height;
      BlockPos lv = arg.field_25936;
      double g;
      if (d > 0.0) {
         float f = (float)lv.getComponentAlongAxis(arg2) + arg4.width / 2.0F;
         g = MathHelper.clamp(MathHelper.getLerpProgress(arg3.getComponentAlongAxis(arg2) - (double)f, 0.0, d), 0.0, 1.0);
      } else {
         g = 0.5;
      }

      double i;
      if (e > 0.0) {
         Direction.Axis lv2 = Direction.Axis.Y;
         i = MathHelper.clamp(MathHelper.getLerpProgress(arg3.getComponentAlongAxis(lv2) - (double)lv.getComponentAlongAxis(lv2), 0.0, e), 0.0, 1.0);
      } else {
         i = 0.0;
      }

      Direction.Axis lv3 = arg2 == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
      double k = arg3.getComponentAlongAxis(lv3) - ((double)lv.getComponentAlongAxis(lv3) + 0.5);
      return new Vec3d(g, i, k);
   }

   public static TeleportTarget method_30484(
      ServerWorld arg, class_5459.class_5460 arg2, Direction.Axis arg3, Vec3d arg4, EntityDimensions arg5, Vec3d arg6, float f, float g
   ) {
      BlockPos lv = arg2.field_25936;
      BlockState lv2 = arg.getBlockState(lv);
      Direction.Axis lv3 = lv2.get(Properties.HORIZONTAL_AXIS);
      double d = (double)arg2.field_25937;
      double e = (double)arg2.field_25938;
      int i = arg3 == lv3 ? 0 : 90;
      Vec3d lv4 = arg3 == lv3 ? arg6 : new Vec3d(arg6.z, arg6.y, -arg6.x);
      double h = (double)arg5.width / 2.0 + (d - (double)arg5.width) * arg4.getX();
      double j = (e - (double)arg5.height) * arg4.getY();
      double k = 0.5 + arg4.getZ();
      boolean bl = lv3 == Direction.Axis.X;
      Vec3d lv5 = new Vec3d((double)lv.getX() + (bl ? h : k), (double)lv.getY() + j, (double)lv.getZ() + (bl ? k : h));
      return new TeleportTarget(lv5, lv4, f + (float)i, g);
   }
}
