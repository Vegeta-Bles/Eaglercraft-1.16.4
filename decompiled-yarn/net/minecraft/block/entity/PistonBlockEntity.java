package net.minecraft.block.entity;

import java.util.Iterator;
import java.util.List;
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

   public float getRenderOffsetX(float tickDelta) {
      return (float)this.facing.getOffsetX() * this.getAmountExtended(this.getProgress(tickDelta));
   }

   public float getRenderOffsetY(float tickDelta) {
      return (float)this.facing.getOffsetY() * this.getAmountExtended(this.getProgress(tickDelta));
   }

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
      Direction _snowman = this.getMovementDirection();
      double _snowmanx = (double)(nextProgress - this.progress);
      VoxelShape _snowmanxx = this.getHeadBlockState().getCollisionShape(this.world, this.getPos());
      if (!_snowmanxx.isEmpty()) {
         Box _snowmanxxx = this.offsetHeadBox(_snowmanxx.getBoundingBox());
         List<Entity> _snowmanxxxx = this.world.getOtherEntities(null, Boxes.stretch(_snowmanxxx, _snowman, _snowmanx).union(_snowmanxxx));
         if (!_snowmanxxxx.isEmpty()) {
            List<Box> _snowmanxxxxx = _snowmanxx.getBoundingBoxes();
            boolean _snowmanxxxxxx = this.pushedBlock.isOf(Blocks.SLIME_BLOCK);
            Iterator var10 = _snowmanxxxx.iterator();

            while (true) {
               Entity _snowmanxxxxxxx;
               while (true) {
                  if (!var10.hasNext()) {
                     return;
                  }

                  _snowmanxxxxxxx = (Entity)var10.next();
                  if (_snowmanxxxxxxx.getPistonBehavior() != PistonBehavior.IGNORE) {
                     if (!_snowmanxxxxxx) {
                        break;
                     }

                     if (!(_snowmanxxxxxxx instanceof ServerPlayerEntity)) {
                        Vec3d _snowmanxxxxxxxx = _snowmanxxxxxxx.getVelocity();
                        double _snowmanxxxxxxxxx = _snowmanxxxxxxxx.x;
                        double _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.y;
                        double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.z;
                        switch (_snowman.getAxis()) {
                           case X:
                              _snowmanxxxxxxxxx = (double)_snowman.getOffsetX();
                              break;
                           case Y:
                              _snowmanxxxxxxxxxx = (double)_snowman.getOffsetY();
                              break;
                           case Z:
                              _snowmanxxxxxxxxxxx = (double)_snowman.getOffsetZ();
                        }

                        _snowmanxxxxxxx.setVelocity(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                        break;
                     }
                  }
               }

               double _snowmanxxxxxxxx = 0.0;

               for (Box _snowmanxxxxxxxxx : _snowmanxxxxx) {
                  Box _snowmanxxxxxxxxxx = Boxes.stretch(this.offsetHeadBox(_snowmanxxxxxxxxx), _snowman, _snowmanx);
                  Box _snowmanxxxxxxxxxxx = _snowmanxxxxxxx.getBoundingBox();
                  if (_snowmanxxxxxxxxxx.intersects(_snowmanxxxxxxxxxxx)) {
                     _snowmanxxxxxxxx = Math.max(_snowmanxxxxxxxx, getIntersectionSize(_snowmanxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxx));
                     if (_snowmanxxxxxxxx >= _snowmanx) {
                        break;
                     }
                  }
               }

               if (!(_snowmanxxxxxxxx <= 0.0)) {
                  _snowmanxxxxxxxx = Math.min(_snowmanxxxxxxxx, _snowmanx) + 0.01;
                  method_23672(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman);
                  if (!this.extending && this.source) {
                     this.push(_snowmanxxxxxxx, _snowman, _snowmanx);
                  }
               }
            }
         }
      }
   }

   private static void method_23672(Direction _snowman, Entity _snowman, double _snowman, Direction _snowman) {
      field_12205.set(_snowman);
      _snowman.move(MovementType.PISTON, new Vec3d(_snowman * (double)_snowman.getOffsetX(), _snowman * (double)_snowman.getOffsetY(), _snowman * (double)_snowman.getOffsetZ()));
      field_12205.set(null);
   }

   private void method_23674(float _snowman) {
      if (this.isPushingHoneyBlock()) {
         Direction _snowmanx = this.getMovementDirection();
         if (_snowmanx.getAxis().isHorizontal()) {
            double _snowmanxx = this.pushedBlock.getCollisionShape(this.world, this.pos).getMax(Direction.Axis.Y);
            Box _snowmanxxx = this.offsetHeadBox(new Box(0.0, _snowmanxx, 0.0, 1.0, 1.5000000999999998, 1.0));
            double _snowmanxxxx = (double)(_snowman - this.progress);

            for (Entity _snowmanxxxxx : this.world.getOtherEntities((Entity)null, _snowmanxxx, _snowmanxxxxxx -> method_23671(_snowman, _snowmanxxxxxx))) {
               method_23672(_snowmanx, _snowmanxxxxx, _snowmanxxxx, _snowmanx);
            }
         }
      }
   }

   private static boolean method_23671(Box _snowman, Entity _snowman) {
      return _snowman.getPistonBehavior() == PistonBehavior.NORMAL
         && _snowman.isOnGround()
         && _snowman.getX() >= _snowman.minX
         && _snowman.getX() <= _snowman.maxX
         && _snowman.getZ() >= _snowman.minZ
         && _snowman.getZ() <= _snowman.maxZ;
   }

   private boolean isPushingHoneyBlock() {
      return this.pushedBlock.isOf(Blocks.HONEY_BLOCK);
   }

   public Direction getMovementDirection() {
      return this.extending ? this.facing : this.facing.getOpposite();
   }

   private static double getIntersectionSize(Box _snowman, Direction _snowman, Box _snowman) {
      switch (_snowman) {
         case EAST:
            return _snowman.maxX - _snowman.minX;
         case WEST:
            return _snowman.maxX - _snowman.minX;
         case UP:
         default:
            return _snowman.maxY - _snowman.minY;
         case DOWN:
            return _snowman.maxY - _snowman.minY;
         case SOUTH:
            return _snowman.maxZ - _snowman.minZ;
         case NORTH:
            return _snowman.maxZ - _snowman.minZ;
      }
   }

   private Box offsetHeadBox(Box box) {
      double _snowman = (double)this.getAmountExtended(this.progress);
      return box.offset(
         (double)this.pos.getX() + _snowman * (double)this.facing.getOffsetX(),
         (double)this.pos.getY() + _snowman * (double)this.facing.getOffsetY(),
         (double)this.pos.getZ() + _snowman * (double)this.facing.getOffsetZ()
      );
   }

   private void push(Entity entity, Direction direction, double amount) {
      Box _snowman = entity.getBoundingBox();
      Box _snowmanx = VoxelShapes.fullCube().getBoundingBox().offset(this.pos);
      if (_snowman.intersects(_snowmanx)) {
         Direction _snowmanxx = direction.getOpposite();
         double _snowmanxxx = getIntersectionSize(_snowmanx, _snowmanxx, _snowman) + 0.01;
         double _snowmanxxxx = getIntersectionSize(_snowmanx, _snowmanxx, _snowman.intersection(_snowmanx)) + 0.01;
         if (Math.abs(_snowmanxxx - _snowmanxxxx) < 0.01) {
            _snowmanxxx = Math.min(_snowmanxxx, amount) + 0.01;
            method_23672(direction, entity, _snowmanxxx, _snowmanxx);
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
            BlockState _snowman;
            if (this.source) {
               _snowman = Blocks.AIR.getDefaultState();
            } else {
               _snowman = Block.postProcessState(this.pushedBlock, this.world, this.pos);
            }

            this.world.setBlockState(this.pos, _snowman, 3);
            this.world.updateNeighbor(this.pos, _snowman.getBlock(), this.pos);
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
               BlockState _snowman = Block.postProcessState(this.pushedBlock, this.world, this.pos);
               if (_snowman.isAir()) {
                  this.world.setBlockState(this.pos, this.pushedBlock, 84);
                  Block.replace(this.pushedBlock, _snowman, this.world, this.pos, 3);
               } else {
                  if (_snowman.contains(Properties.WATERLOGGED) && _snowman.get(Properties.WATERLOGGED)) {
                     _snowman = _snowman.with(Properties.WATERLOGGED, Boolean.valueOf(false));
                  }

                  this.world.setBlockState(this.pos, _snowman, 67);
                  this.world.updateNeighbor(this.pos, _snowman.getBlock(), this.pos);
               }
            }
         }
      } else {
         float _snowman = this.progress + 0.5F;
         this.pushEntities(_snowman);
         this.method_23674(_snowman);
         this.progress = _snowman;
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
      VoxelShape _snowman;
      if (!this.extending && this.source) {
         _snowman = this.pushedBlock.with(PistonBlock.EXTENDED, Boolean.valueOf(true)).getCollisionShape(world, pos);
      } else {
         _snowman = VoxelShapes.empty();
      }

      Direction _snowmanx = field_12205.get();
      if ((double)this.progress < 1.0 && _snowmanx == this.getMovementDirection()) {
         return _snowman;
      } else {
         BlockState _snowmanxx;
         if (this.isSource()) {
            _snowmanxx = Blocks.PISTON_HEAD
               .getDefaultState()
               .with(PistonHeadBlock.FACING, this.facing)
               .with(PistonHeadBlock.SHORT, Boolean.valueOf(this.extending != 1.0F - this.progress < 0.25F));
         } else {
            _snowmanxx = this.pushedBlock;
         }

         float _snowmanxxx = this.getAmountExtended(this.progress);
         double _snowmanxxxx = (double)((float)this.facing.getOffsetX() * _snowmanxxx);
         double _snowmanxxxxx = (double)((float)this.facing.getOffsetY() * _snowmanxxx);
         double _snowmanxxxxxx = (double)((float)this.facing.getOffsetZ() * _snowmanxxx);
         return VoxelShapes.union(_snowman, _snowmanxx.getCollisionShape(world, pos).offset(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx));
      }
   }

   public long getSavedWorldTime() {
      return this.savedWorldTime;
   }

   @Override
   public double getSquaredRenderDistance() {
      return 68.0;
   }
}
