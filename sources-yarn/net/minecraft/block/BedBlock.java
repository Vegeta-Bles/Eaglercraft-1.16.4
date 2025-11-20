package net.minecraft.block;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
   @Environment(EnvType.CLIENT)
   public static Direction getDirection(BlockView world, BlockPos pos) {
      BlockState lv = world.getBlockState(pos);
      return lv.getBlock() instanceof BedBlock ? lv.get(FACING) : null;
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
            BlockPos lv = pos.offset(state.get(FACING).getOpposite());
            if (world.getBlockState(lv).isOf(this)) {
               world.removeBlock(lv, false);
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
            player.trySleep(pos).ifLeft(arg2 -> {
               if (arg2 != null) {
                  player.sendMessage(arg2.toText(), true);
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
      List<VillagerEntity> list = world.getEntitiesByClass(VillagerEntity.class, new Box(pos), LivingEntity::isSleeping);
      if (list.isEmpty()) {
         return false;
      } else {
         list.get(0).wakeUp();
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
      Vec3d lv = entity.getVelocity();
      if (lv.y < 0.0) {
         double d = entity instanceof LivingEntity ? 1.0 : 0.8;
         entity.setVelocity(lv.x, -lv.y * 0.66F * d, lv.z);
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
         BedPart lv = state.get(PART);
         if (lv == BedPart.FOOT) {
            BlockPos lv2 = pos.offset(getDirectionTowardsOtherPart(lv, state.get(FACING)));
            BlockState lv3 = world.getBlockState(lv2);
            if (lv3.getBlock() == this && lv3.get(PART) == BedPart.HEAD) {
               world.setBlockState(lv2, Blocks.AIR.getDefaultState(), 35);
               world.syncWorldEvent(player, 2001, lv2, Block.getRawIdFromState(lv3));
            }
         }
      }

      super.onBreak(world, pos, state, player);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      Direction lv = ctx.getPlayerFacing();
      BlockPos lv2 = ctx.getBlockPos();
      BlockPos lv3 = lv2.offset(lv);
      return ctx.getWorld().getBlockState(lv3).canReplace(ctx) ? this.getDefaultState().with(FACING, lv) : null;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      Direction lv = getOppositePartDirection(state).getOpposite();
      switch (lv) {
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
      Direction lv = state.get(FACING);
      return state.get(PART) == BedPart.HEAD ? lv.getOpposite() : lv;
   }

   @Environment(EnvType.CLIENT)
   public static DoubleBlockProperties.Type getBedPart(BlockState state) {
      BedPart lv = state.get(PART);
      return lv == BedPart.HEAD ? DoubleBlockProperties.Type.FIRST : DoubleBlockProperties.Type.SECOND;
   }

   private static boolean method_30839(BlockView arg, BlockPos arg2) {
      return arg.getBlockState(arg2.down()).getBlock() instanceof BedBlock;
   }

   public static Optional<Vec3d> findWakeUpPosition(EntityType<?> type, CollisionView arg2, BlockPos pos, float f) {
      Direction lv = arg2.getBlockState(pos).get(FACING);
      Direction lv2 = lv.rotateYClockwise();
      Direction lv3 = lv2.method_30928(f) ? lv2.getOpposite() : lv2;
      if (method_30839(arg2, pos)) {
         return method_30835(type, arg2, pos, lv, lv3);
      } else {
         int[][] is = method_30838(lv, lv3);
         Optional<Vec3d> optional = method_30836(type, arg2, pos, is, true);
         return optional.isPresent() ? optional : method_30836(type, arg2, pos, is, false);
      }
   }

   private static Optional<Vec3d> method_30835(EntityType<?> arg, CollisionView arg2, BlockPos arg3, Direction arg4, Direction arg5) {
      int[][] is = method_30840(arg4, arg5);
      Optional<Vec3d> optional = method_30836(arg, arg2, arg3, is, true);
      if (optional.isPresent()) {
         return optional;
      } else {
         BlockPos lv = arg3.down();
         Optional<Vec3d> optional2 = method_30836(arg, arg2, lv, is, true);
         if (optional2.isPresent()) {
            return optional2;
         } else {
            int[][] js = method_30837(arg4);
            Optional<Vec3d> optional3 = method_30836(arg, arg2, arg3, js, true);
            if (optional3.isPresent()) {
               return optional3;
            } else {
               Optional<Vec3d> optional4 = method_30836(arg, arg2, arg3, is, false);
               if (optional4.isPresent()) {
                  return optional4;
               } else {
                  Optional<Vec3d> optional5 = method_30836(arg, arg2, lv, is, false);
                  return optional5.isPresent() ? optional5 : method_30836(arg, arg2, arg3, js, false);
               }
            }
         }
      }
   }

   private static Optional<Vec3d> method_30836(EntityType<?> arg, CollisionView arg2, BlockPos arg3, int[][] is, boolean bl) {
      BlockPos.Mutable lv = new BlockPos.Mutable();

      for (int[] js : is) {
         lv.set(arg3.getX() + js[0], arg3.getY(), arg3.getZ() + js[1]);
         Vec3d lv2 = Dismounting.method_30769(arg, arg2, lv, bl);
         if (lv2 != null) {
            return Optional.of(lv2);
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
         BlockPos lv = pos.offset(state.get(FACING));
         world.setBlockState(lv, state.with(PART, BedPart.HEAD), 3);
         world.updateNeighbors(pos, Blocks.AIR);
         state.updateNeighbors(world, pos, 3);
      }
   }

   @Environment(EnvType.CLIENT)
   public DyeColor getColor() {
      return this.color;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public long getRenderingSeed(BlockState state, BlockPos pos) {
      BlockPos lv = pos.offset(state.get(FACING), state.get(PART) == BedPart.HEAD ? 0 : 1);
      return MathHelper.hashCode(lv.getX(), pos.getY(), lv.getZ());
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   private static int[][] method_30838(Direction arg, Direction arg2) {
      return (int[][])ArrayUtils.addAll(method_30840(arg, arg2), method_30837(arg));
   }

   private static int[][] method_30840(Direction arg, Direction arg2) {
      return new int[][]{
         {arg2.getOffsetX(), arg2.getOffsetZ()},
         {arg2.getOffsetX() - arg.getOffsetX(), arg2.getOffsetZ() - arg.getOffsetZ()},
         {arg2.getOffsetX() - arg.getOffsetX() * 2, arg2.getOffsetZ() - arg.getOffsetZ() * 2},
         {-arg.getOffsetX() * 2, -arg.getOffsetZ() * 2},
         {-arg2.getOffsetX() - arg.getOffsetX() * 2, -arg2.getOffsetZ() - arg.getOffsetZ() * 2},
         {-arg2.getOffsetX() - arg.getOffsetX(), -arg2.getOffsetZ() - arg.getOffsetZ()},
         {-arg2.getOffsetX(), -arg2.getOffsetZ()},
         {-arg2.getOffsetX() + arg.getOffsetX(), -arg2.getOffsetZ() + arg.getOffsetZ()},
         {arg.getOffsetX(), arg.getOffsetZ()},
         {arg2.getOffsetX() + arg.getOffsetX(), arg2.getOffsetZ() + arg.getOffsetZ()}
      };
   }

   private static int[][] method_30837(Direction arg) {
      return new int[][]{{0, 0}, {-arg.getOffsetX(), -arg.getOffsetZ()}};
   }
}
