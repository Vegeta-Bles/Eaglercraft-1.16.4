package net.minecraft.block;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.CollisionView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import org.apache.commons.lang3.ArrayUtils;

public class BedBlock extends HorizontalFacingBlock implements BlockEntityProvider {
   public static final EnumProperty<BedPart> PART = Properties.BED_PART;
   public static final BooleanProperty OCCUPIED = Properties.OCCUPIED;
   protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0, 3.0, 0.0, 16.0, 9.0, 16.0);
   protected static final VoxelShape LEG_1_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 3.0, 3.0);
   protected static final VoxelShape LEG_2_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 3.0, 3.0, 16.0);
   protected static final VoxelShape LEG_3_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 3.0, 3.0);
   protected static final VoxelShape LEG_4_SHAPE = Block.createCuboidShape(13.0, 0.0, 13.0, 16.0, 3.0, 16.0);
   protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_1_SHAPE, LEG_3_SHAPE);
   protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_2_SHAPE, LEG_4_SHAPE);
   protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_1_SHAPE, LEG_2_SHAPE);
   protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_3_SHAPE, LEG_4_SHAPE);
   private final DyeColor color;

   public BedBlock(DyeColor color, AbstractBlock.Settings settings) {
      super(settings);
      this.color = color;
      this.setDefaultState(this.stateManager.getDefaultState().with(PART, BedPart.FOOT).with(OCCUPIED, Boolean.valueOf(false)));
   }

   @Nullable
   public static Direction getDirection(BlockView world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos);
      return _snowman.getBlock() instanceof BedBlock ? _snowman.get(FACING) : null;
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (world.isClient) {
         return ActionResult.CONSUME;
      } else {
         if (state.get(PART) != BedPart.HEAD) {
            pos = pos.offset(state.get(FACING));
            state = world.getBlockState(pos);
            if (!state.isOf(this)) {
               return ActionResult.CONSUME;
            }
         }

         if (!isOverworld(world)) {
            world.removeBlock(pos, false);
            BlockPos _snowman = pos.offset(state.get(FACING).getOpposite());
            if (world.getBlockState(_snowman).isOf(this)) {
               world.removeBlock(_snowman, false);
            }

            world.createExplosion(
               null,
               DamageSource.badRespawnPoint(),
               null,
               (double)pos.getX() + 0.5,
               (double)pos.getY() + 0.5,
               (double)pos.getZ() + 0.5,
               5.0F,
               true,
               Explosion.DestructionType.DESTROY
            );
            return ActionResult.SUCCESS;
         } else if (state.get(OCCUPIED)) {
            if (!this.isFree(world, pos)) {
               player.sendMessage(new TranslatableText("block.minecraft.bed.occupied"), true);
            }

            return ActionResult.SUCCESS;
         } else {
            player.trySleep(pos).ifLeft(_snowmanx -> {
               if (_snowmanx != null) {
                  player.sendMessage(_snowmanx.toText(), true);
               }
            });
            return ActionResult.SUCCESS;
         }
      }
   }

   public static boolean isOverworld(World world) {
      return world.getDimension().isBedWorking();
   }

   private boolean isFree(World world, BlockPos pos) {
      List<VillagerEntity> _snowman = world.getEntitiesByClass(VillagerEntity.class, new Box(pos), LivingEntity::isSleeping);
      if (_snowman.isEmpty()) {
         return false;
      } else {
         _snowman.get(0).wakeUp();
         return true;
      }
   }

   @Override
   public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
      super.onLandedUpon(world, pos, entity, distance * 0.5F);
   }

   @Override
   public void onEntityLand(BlockView world, Entity entity) {
      if (entity.bypassesLandingEffects()) {
         super.onEntityLand(world, entity);
      } else {
         this.bounceEntity(entity);
      }
   }

   private void bounceEntity(Entity entity) {
      Vec3d _snowman = entity.getVelocity();
      if (_snowman.y < 0.0) {
         double _snowmanx = entity instanceof LivingEntity ? 1.0 : 0.8;
         entity.setVelocity(_snowman.x, -_snowman.y * 0.66F * _snowmanx, _snowman.z);
      }
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (direction == getDirectionTowardsOtherPart(state.get(PART), state.get(FACING))) {
         return newState.isOf(this) && newState.get(PART) != state.get(PART) ? state.with(OCCUPIED, newState.get(OCCUPIED)) : Blocks.AIR.getDefaultState();
      } else {
         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      }
   }

   private static Direction getDirectionTowardsOtherPart(BedPart part, Direction direction) {
      return part == BedPart.FOOT ? direction : direction.getOpposite();
   }

   @Override
   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!world.isClient && player.isCreative()) {
         BedPart _snowman = state.get(PART);
         if (_snowman == BedPart.FOOT) {
            BlockPos _snowmanx = pos.offset(getDirectionTowardsOtherPart(_snowman, state.get(FACING)));
            BlockState _snowmanxx = world.getBlockState(_snowmanx);
            if (_snowmanxx.getBlock() == this && _snowmanxx.get(PART) == BedPart.HEAD) {
               world.setBlockState(_snowmanx, Blocks.AIR.getDefaultState(), 35);
               world.syncWorldEvent(player, 2001, _snowmanx, Block.getRawIdFromState(_snowmanxx));
            }
         }
      }

      super.onBreak(world, pos, state, player);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      Direction _snowman = ctx.getPlayerFacing();
      BlockPos _snowmanx = ctx.getBlockPos();
      BlockPos _snowmanxx = _snowmanx.offset(_snowman);
      return ctx.getWorld().getBlockState(_snowmanxx).canReplace(ctx) ? this.getDefaultState().with(FACING, _snowman) : null;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      Direction _snowman = getOppositePartDirection(state).getOpposite();
      switch (_snowman) {
         case NORTH:
            return NORTH_SHAPE;
         case SOUTH:
            return SOUTH_SHAPE;
         case WEST:
            return WEST_SHAPE;
         default:
            return EAST_SHAPE;
      }
   }

   public static Direction getOppositePartDirection(BlockState state) {
      Direction _snowman = state.get(FACING);
      return state.get(PART) == BedPart.HEAD ? _snowman.getOpposite() : _snowman;
   }

   public static DoubleBlockProperties.Type getBedPart(BlockState state) {
      BedPart _snowman = state.get(PART);
      return _snowman == BedPart.HEAD ? DoubleBlockProperties.Type.FIRST : DoubleBlockProperties.Type.SECOND;
   }

   private static boolean method_30839(BlockView _snowman, BlockPos _snowman) {
      return _snowman.getBlockState(_snowman.down()).getBlock() instanceof BedBlock;
   }

   public static Optional<Vec3d> findWakeUpPosition(EntityType<?> type, CollisionView _snowman, BlockPos pos, float _snowman) {
      Direction _snowmanxx = _snowman.getBlockState(pos).get(FACING);
      Direction _snowmanxxx = _snowmanxx.rotateYClockwise();
      Direction _snowmanxxxx = _snowmanxxx.method_30928(_snowman) ? _snowmanxxx.getOpposite() : _snowmanxxx;
      if (method_30839(_snowman, pos)) {
         return method_30835(type, _snowman, pos, _snowmanxx, _snowmanxxxx);
      } else {
         int[][] _snowmanxxxxx = method_30838(_snowmanxx, _snowmanxxxx);
         Optional<Vec3d> _snowmanxxxxxx = method_30836(type, _snowman, pos, _snowmanxxxxx, true);
         return _snowmanxxxxxx.isPresent() ? _snowmanxxxxxx : method_30836(type, _snowman, pos, _snowmanxxxxx, false);
      }
   }

   private static Optional<Vec3d> method_30835(EntityType<?> _snowman, CollisionView _snowman, BlockPos _snowman, Direction _snowman, Direction _snowman) {
      int[][] _snowmanxxxxx = method_30840(_snowman, _snowman);
      Optional<Vec3d> _snowmanxxxxxx = method_30836(_snowman, _snowman, _snowman, _snowmanxxxxx, true);
      if (_snowmanxxxxxx.isPresent()) {
         return _snowmanxxxxxx;
      } else {
         BlockPos _snowmanxxxxxxx = _snowman.down();
         Optional<Vec3d> _snowmanxxxxxxxx = method_30836(_snowman, _snowman, _snowmanxxxxxxx, _snowmanxxxxx, true);
         if (_snowmanxxxxxxxx.isPresent()) {
            return _snowmanxxxxxxxx;
         } else {
            int[][] _snowmanxxxxxxxxx = method_30837(_snowman);
            Optional<Vec3d> _snowmanxxxxxxxxxx = method_30836(_snowman, _snowman, _snowman, _snowmanxxxxxxxxx, true);
            if (_snowmanxxxxxxxxxx.isPresent()) {
               return _snowmanxxxxxxxxxx;
            } else {
               Optional<Vec3d> _snowmanxxxxxxxxxxx = method_30836(_snowman, _snowman, _snowman, _snowmanxxxxx, false);
               if (_snowmanxxxxxxxxxxx.isPresent()) {
                  return _snowmanxxxxxxxxxxx;
               } else {
                  Optional<Vec3d> _snowmanxxxxxxxxxxxx = method_30836(_snowman, _snowman, _snowmanxxxxxxx, _snowmanxxxxx, false);
                  return _snowmanxxxxxxxxxxxx.isPresent() ? _snowmanxxxxxxxxxxxx : method_30836(_snowman, _snowman, _snowman, _snowmanxxxxxxxxx, false);
               }
            }
         }
      }
   }

   private static Optional<Vec3d> method_30836(EntityType<?> _snowman, CollisionView _snowman, BlockPos _snowman, int[][] _snowman, boolean _snowman) {
      BlockPos.Mutable _snowmanxxxxx = new BlockPos.Mutable();

      for (int[] _snowmanxxxxxx : _snowman) {
         _snowmanxxxxx.set(_snowman.getX() + _snowmanxxxxxx[0], _snowman.getY(), _snowman.getZ() + _snowmanxxxxxx[1]);
         Vec3d _snowmanxxxxxxx = Dismounting.method_30769(_snowman, _snowman, _snowmanxxxxx, _snowman);
         if (_snowmanxxxxxxx != null) {
            return Optional.of(_snowmanxxxxxxx);
         }
      }

      return Optional.empty();
   }

   @Override
   public PistonBehavior getPistonBehavior(BlockState state) {
      return PistonBehavior.DESTROY;
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, PART, OCCUPIED);
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new BedBlockEntity(this.color);
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
      super.onPlaced(world, pos, state, placer, itemStack);
      if (!world.isClient) {
         BlockPos _snowman = pos.offset(state.get(FACING));
         world.setBlockState(_snowman, state.with(PART, BedPart.HEAD), 3);
         world.updateNeighbors(pos, Blocks.AIR);
         state.updateNeighbors(world, pos, 3);
      }
   }

   public DyeColor getColor() {
      return this.color;
   }

   @Override
   public long getRenderingSeed(BlockState state, BlockPos pos) {
      BlockPos _snowman = pos.offset(state.get(FACING), state.get(PART) == BedPart.HEAD ? 0 : 1);
      return MathHelper.hashCode(_snowman.getX(), pos.getY(), _snowman.getZ());
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   private static int[][] method_30838(Direction _snowman, Direction _snowman) {
      return (int[][])ArrayUtils.addAll(method_30840(_snowman, _snowman), method_30837(_snowman));
   }

   private static int[][] method_30840(Direction _snowman, Direction _snowman) {
      return new int[][]{
         {_snowman.getOffsetX(), _snowman.getOffsetZ()},
         {_snowman.getOffsetX() - _snowman.getOffsetX(), _snowman.getOffsetZ() - _snowman.getOffsetZ()},
         {_snowman.getOffsetX() - _snowman.getOffsetX() * 2, _snowman.getOffsetZ() - _snowman.getOffsetZ() * 2},
         {-_snowman.getOffsetX() * 2, -_snowman.getOffsetZ() * 2},
         {-_snowman.getOffsetX() - _snowman.getOffsetX() * 2, -_snowman.getOffsetZ() - _snowman.getOffsetZ() * 2},
         {-_snowman.getOffsetX() - _snowman.getOffsetX(), -_snowman.getOffsetZ() - _snowman.getOffsetZ()},
         {-_snowman.getOffsetX(), -_snowman.getOffsetZ()},
         {-_snowman.getOffsetX() + _snowman.getOffsetX(), -_snowman.getOffsetZ() + _snowman.getOffsetZ()},
         {_snowman.getOffsetX(), _snowman.getOffsetZ()},
         {_snowman.getOffsetX() + _snowman.getOffsetX(), _snowman.getOffsetZ() + _snowman.getOffsetZ()}
      };
   }

   private static int[][] method_30837(Direction _snowman) {
      return new int[][]{{0, 0}, {-_snowman.getOffsetX(), -_snowman.getOffsetZ()}};
   }
}
