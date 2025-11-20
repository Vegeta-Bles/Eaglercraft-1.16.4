package net.minecraft.block.entity;

import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.enums.PistonType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Boxes;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PistonBlockEntity extends BlockEntity implements Tickable {
   private BlockState pushedBlock;
   private Direction facing;
   private boolean extending;
   private boolean source;
   private static final ThreadLocal<Direction> field_12205 = ThreadLocal.withInitial(() -> null);
   private float progress;
   private float lastProgress;
   private long savedWorldTime;
   private int field_26705;

   public PistonBlockEntity() {
      super(BlockEntityType.PISTON);
   }

   public PistonBlockEntity(BlockState pushedBlock, Direction facing, boolean extending, boolean source) {
      this();
      this.pushedBlock = pushedBlock;
      this.facing = facing;
      this.extending = extending;
      this.source = source;
   }

   @Override
   public CompoundTag toInitialChunkDataTag() {
      return this.toTag(new CompoundTag());
   }

   public boolean isExtending() {
      return this.extending;
   }

   public Direction getFacing() {
      return this.facing;
   }

   public boolean isSource() {
      return this.source;
   }

   public float getProgress(float tickDelta) {
      if (tickDelta > 1.0F) {
         tickDelta = 1.0F;
      }

      return MathHelper.lerp(tickDelta, this.lastProgress, this.progress);
   }

   @Environment(EnvType.CLIENT)
   public float getRenderOffsetX(float tickDelta) {
      return (float)this.facing.getOffsetX() * this.getAmountExtended(this.getProgress(tickDelta));
   }

   @Environment(EnvType.CLIENT)
   public float getRenderOffsetY(float tickDelta) {
      return (float)this.facing.getOffsetY() * this.getAmountExtended(this.getProgress(tickDelta));
   }

   @Environment(EnvType.CLIENT)
   public float getRenderOffsetZ(float tickDelta) {
      return (float)this.facing.getOffsetZ() * this.getAmountExtended(this.getProgress(tickDelta));
   }

   private float getAmountExtended(float progress) {
      return this.extending ? progress - 1.0F : 1.0F - progress;
   }

   private BlockState getHeadBlockState() {
      return !this.isExtending() && this.isSource() && this.pushedBlock.getBlock() instanceof PistonBlock
         ? Blocks.PISTON_HEAD
            .getDefaultState()
            .with(PistonHeadBlock.SHORT, Boolean.valueOf(this.progress > 0.25F))
            .with(PistonHeadBlock.TYPE, this.pushedBlock.isOf(Blocks.STICKY_PISTON) ? PistonType.STICKY : PistonType.DEFAULT)
            .with(PistonHeadBlock.FACING, this.pushedBlock.get(PistonBlock.FACING))
         : this.pushedBlock;
   }

   private void pushEntities(float nextProgress) {
      Direction lv = this.getMovementDirection();
      double d = (double)(nextProgress - this.progress);
      VoxelShape lv2 = this.getHeadBlockState().getCollisionShape(this.world, this.getPos());
      if (!lv2.isEmpty()) {
         Box lv3 = this.offsetHeadBox(lv2.getBoundingBox());
         List<Entity> list = this.world.getOtherEntities(null, Boxes.stretch(lv3, lv, d).union(lv3));
         if (!list.isEmpty()) {
            List<Box> list2 = lv2.getBoundingBoxes();
            boolean bl = this.pushedBlock.isOf(Blocks.SLIME_BLOCK);
            Iterator var10 = list.iterator();

            while (true) {
               Entity lv4;
               while (true) {
                  if (!var10.hasNext()) {
                     return;
                  }

                  lv4 = (Entity)var10.next();
                  if (lv4.getPistonBehavior() != PistonBehavior.IGNORE) {
                     if (!bl) {
                        break;
                     }

                     if (!(lv4 instanceof ServerPlayerEntity)) {
                        Vec3d lv5 = lv4.getVelocity();
                        double e = lv5.x;
                        double g = lv5.y;
                        double h = lv5.z;
                        switch (lv.getAxis()) {
                           case X:
                              e = (double)lv.getOffsetX();
                              break;
                           case Y:
                              g = (double)lv.getOffsetY();
                              break;
                           case Z:
                              h = (double)lv.getOffsetZ();
                        }

                        lv4.setVelocity(e, g, h);
                        break;
                     }
                  }
               }

               double i = 0.0;

               for (Box lv6 : list2) {
                  Box lv7 = Boxes.stretch(this.offsetHeadBox(lv6), lv, d);
                  Box lv8 = lv4.getBoundingBox();
                  if (lv7.intersects(lv8)) {
                     i = Math.max(i, getIntersectionSize(lv7, lv, lv8));
                     if (i >= d) {
                        break;
                     }
                  }
               }

               if (!(i <= 0.0)) {
                  i = Math.min(i, d) + 0.01;
                  method_23672(lv, lv4, i, lv);
                  if (!this.extending && this.source) {
                     this.push(lv4, lv, d);
                  }
               }
            }
         }
      }
   }

   private static void method_23672(Direction arg, Entity arg2, double d, Direction arg3) {
      field_12205.set(arg);
      arg2.move(MovementType.PISTON, new Vec3d(d * (double)arg3.getOffsetX(), d * (double)arg3.getOffsetY(), d * (double)arg3.getOffsetZ()));
      field_12205.set(null);
   }

   private void method_23674(float f) {
      if (this.isPushingHoneyBlock()) {
         Direction lv = this.getMovementDirection();
         if (lv.getAxis().isHorizontal()) {
            double d = this.pushedBlock.getCollisionShape(this.world, this.pos).getMax(Direction.Axis.Y);
            Box lv2 = this.offsetHeadBox(new Box(0.0, d, 0.0, 1.0, 1.5000000999999998, 1.0));
            double e = (double)(f - this.progress);

            for (Entity lv3 : this.world.getOtherEntities((Entity)null, lv2, arg2 -> method_23671(lv2, arg2))) {
               method_23672(lv, lv3, e, lv);
            }
         }
      }
   }

   private static boolean method_23671(Box arg, Entity arg2) {
      return arg2.getPistonBehavior() == PistonBehavior.NORMAL
         && arg2.isOnGround()
         && arg2.getX() >= arg.minX
         && arg2.getX() <= arg.maxX
         && arg2.getZ() >= arg.minZ
         && arg2.getZ() <= arg.maxZ;
   }

   private boolean isPushingHoneyBlock() {
      return this.pushedBlock.isOf(Blocks.HONEY_BLOCK);
   }

   public Direction getMovementDirection() {
      return this.extending ? this.facing : this.facing.getOpposite();
   }

   private static double getIntersectionSize(Box arg, Direction arg2, Box arg3) {
      switch (arg2) {
         case EAST:
            return arg.maxX - arg3.minX;
         case WEST:
            return arg3.maxX - arg.minX;
         case UP:
         default:
            return arg.maxY - arg3.minY;
         case DOWN:
            return arg3.maxY - arg.minY;
         case SOUTH:
            return arg.maxZ - arg3.minZ;
         case NORTH:
            return arg3.maxZ - arg.minZ;
      }
   }

   private Box offsetHeadBox(Box box) {
      double d = (double)this.getAmountExtended(this.progress);
      return box.offset(
         (double)this.pos.getX() + d * (double)this.facing.getOffsetX(),
         (double)this.pos.getY() + d * (double)this.facing.getOffsetY(),
         (double)this.pos.getZ() + d * (double)this.facing.getOffsetZ()
      );
   }

   private void push(Entity entity, Direction direction, double amount) {
      Box lv = entity.getBoundingBox();
      Box lv2 = VoxelShapes.fullCube().getBoundingBox().offset(this.pos);
      if (lv.intersects(lv2)) {
         Direction lv3 = direction.getOpposite();
         double e = getIntersectionSize(lv2, lv3, lv) + 0.01;
         double f = getIntersectionSize(lv2, lv3, lv.intersection(lv2)) + 0.01;
         if (Math.abs(e - f) < 0.01) {
            e = Math.min(e, amount) + 0.01;
            method_23672(direction, entity, e, lv3);
         }
      }
   }

   public BlockState getPushedBlock() {
      return this.pushedBlock;
   }

   public void finish() {
      if (this.world != null && (this.lastProgress < 1.0F || this.world.isClient)) {
         this.progress = 1.0F;
         this.lastProgress = this.progress;
         this.world.removeBlockEntity(this.pos);
         this.markRemoved();
         if (this.world.getBlockState(this.pos).isOf(Blocks.MOVING_PISTON)) {
            BlockState lv;
            if (this.source) {
               lv = Blocks.AIR.getDefaultState();
            } else {
               lv = Block.postProcessState(this.pushedBlock, this.world, this.pos);
            }

            this.world.setBlockState(this.pos, lv, 3);
            this.world.updateNeighbor(this.pos, lv.getBlock(), this.pos);
         }
      }
   }

   @Override
   public void tick() {
      this.savedWorldTime = this.world.getTime();
      this.lastProgress = this.progress;
      if (this.lastProgress >= 1.0F) {
         if (this.world.isClient && this.field_26705 < 5) {
            this.field_26705++;
         } else {
            this.world.removeBlockEntity(this.pos);
            this.markRemoved();
            if (this.pushedBlock != null && this.world.getBlockState(this.pos).isOf(Blocks.MOVING_PISTON)) {
               BlockState lv = Block.postProcessState(this.pushedBlock, this.world, this.pos);
               if (lv.isAir()) {
                  this.world.setBlockState(this.pos, this.pushedBlock, 84);
                  Block.replace(this.pushedBlock, lv, this.world, this.pos, 3);
               } else {
                  if (lv.contains(Properties.WATERLOGGED) && lv.get(Properties.WATERLOGGED)) {
                     lv = lv.with(Properties.WATERLOGGED, Boolean.valueOf(false));
                  }

                  this.world.setBlockState(this.pos, lv, 67);
                  this.world.updateNeighbor(this.pos, lv.getBlock(), this.pos);
               }
            }
         }
      } else {
         float f = this.progress + 0.5F;
         this.pushEntities(f);
         this.method_23674(f);
         this.progress = f;
         if (this.progress >= 1.0F) {
            this.progress = 1.0F;
         }
      }
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      this.pushedBlock = NbtHelper.toBlockState(tag.getCompound("blockState"));
      this.facing = Direction.byId(tag.getInt("facing"));
      this.progress = tag.getFloat("progress");
      this.lastProgress = this.progress;
      this.extending = tag.getBoolean("extending");
      this.source = tag.getBoolean("source");
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      tag.put("blockState", NbtHelper.fromBlockState(this.pushedBlock));
      tag.putInt("facing", this.facing.getId());
      tag.putFloat("progress", this.lastProgress);
      tag.putBoolean("extending", this.extending);
      tag.putBoolean("source", this.source);
      return tag;
   }

   public VoxelShape getCollisionShape(BlockView world, BlockPos pos) {
      VoxelShape lv;
      if (!this.extending && this.source) {
         lv = this.pushedBlock.with(PistonBlock.EXTENDED, Boolean.valueOf(true)).getCollisionShape(world, pos);
      } else {
         lv = VoxelShapes.empty();
      }

      Direction lv3 = field_12205.get();
      if ((double)this.progress < 1.0 && lv3 == this.getMovementDirection()) {
         return lv;
      } else {
         BlockState lv4;
         if (this.isSource()) {
            lv4 = Blocks.PISTON_HEAD
               .getDefaultState()
               .with(PistonHeadBlock.FACING, this.facing)
               .with(PistonHeadBlock.SHORT, Boolean.valueOf(this.extending != 1.0F - this.progress < 0.25F));
         } else {
            lv4 = this.pushedBlock;
         }

         float f = this.getAmountExtended(this.progress);
         double d = (double)((float)this.facing.getOffsetX() * f);
         double e = (double)((float)this.facing.getOffsetY() * f);
         double g = (double)((float)this.facing.getOffsetZ() * f);
         return VoxelShapes.union(lv, lv4.getCollisionShape(world, pos).offset(d, e, g));
      }
   }

   public long getSavedWorldTime() {
      return this.savedWorldTime;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public double getSquaredRenderDistance() {
      return 68.0;
   }
}
