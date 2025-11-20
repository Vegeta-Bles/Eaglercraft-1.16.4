package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.SwordItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class BambooBlock extends Block implements Fertilizable {
   protected static final VoxelShape SMALL_LEAVES_SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
   protected static final VoxelShape LARGE_LEAVES_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   protected static final VoxelShape NO_LEAVES_SHAPE = Block.createCuboidShape(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
   public static final IntProperty AGE = Properties.AGE_1;
   public static final EnumProperty<BambooLeaves> LEAVES = Properties.BAMBOO_LEAVES;
   public static final IntProperty STAGE = Properties.STAGE;

   public BambooBlock(AbstractBlock.Settings arg) {
      super(arg);
      this.setDefaultState(this.stateManager.getDefaultState().with(AGE, Integer.valueOf(0)).with(LEAVES, BambooLeaves.NONE).with(STAGE, Integer.valueOf(0)));
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(AGE, LEAVES, STAGE);
   }

   @Override
   public AbstractBlock.OffsetType getOffsetType() {
      return AbstractBlock.OffsetType.XZ;
   }

   @Override
   public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
      return true;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      VoxelShape lv = state.get(LEAVES) == BambooLeaves.LARGE ? LARGE_LEAVES_SHAPE : SMALL_LEAVES_SHAPE;
      Vec3d lv2 = state.getModelOffset(world, pos);
      return lv.offset(lv2.x, lv2.y, lv2.z);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      Vec3d lv = state.getModelOffset(world, pos);
      return NO_LEAVES_SHAPE.offset(lv.x, lv.y, lv.z);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      FluidState lv = ctx.getWorld().getFluidState(ctx.getBlockPos());
      if (!lv.isEmpty()) {
         return null;
      } else {
         BlockState lv2 = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
         if (lv2.isIn(BlockTags.BAMBOO_PLANTABLE_ON)) {
            if (lv2.isOf(Blocks.BAMBOO_SAPLING)) {
               return this.getDefaultState().with(AGE, Integer.valueOf(0));
            } else if (lv2.isOf(Blocks.BAMBOO)) {
               int i = lv2.get(AGE) > 0 ? 1 : 0;
               return this.getDefaultState().with(AGE, Integer.valueOf(i));
            } else {
               BlockState lv3 = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
               return !lv3.isOf(Blocks.BAMBOO) && !lv3.isOf(Blocks.BAMBOO_SAPLING)
                  ? Blocks.BAMBOO_SAPLING.getDefaultState()
                  : this.getDefaultState().with(AGE, lv3.get(AGE));
            }
         } else {
            return null;
         }
      }
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!state.canPlaceAt(world, pos)) {
         world.breakBlock(pos, true);
      }
   }

   @Override
   public boolean hasRandomTicks(BlockState state) {
      return state.get(STAGE) == 0;
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (state.get(STAGE) == 0) {
         if (random.nextInt(3) == 0 && world.isAir(pos.up()) && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            int i = this.countBambooBelow(world, pos) + 1;
            if (i < 16) {
               this.updateLeaves(state, world, pos, random, i);
            }
         }
      }
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return world.getBlockState(pos.down()).isIn(BlockTags.BAMBOO_PLANTABLE_ON);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (!state.canPlaceAt(world, pos)) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }

      if (direction == Direction.UP && newState.isOf(Blocks.BAMBOO) && newState.get(AGE) > state.get(AGE)) {
         world.setBlockState(pos, state.cycle(AGE), 2);
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      int i = this.countBambooAbove(world, pos);
      int j = this.countBambooBelow(world, pos);
      return i + j + 1 < 16 && world.getBlockState(pos.up(i)).get(STAGE) != 1;
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      int i = this.countBambooAbove(world, pos);
      int j = this.countBambooBelow(world, pos);
      int k = i + j + 1;
      int l = 1 + random.nextInt(2);

      for (int m = 0; m < l; m++) {
         BlockPos lv = pos.up(i);
         BlockState lv2 = world.getBlockState(lv);
         if (k >= 16 || lv2.get(STAGE) == 1 || !world.isAir(lv.up())) {
            return;
         }

         this.updateLeaves(lv2, world, lv, random, k);
         i++;
         k++;
      }
   }

   @Override
   public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
      return player.getMainHandStack().getItem() instanceof SwordItem ? 1.0F : super.calcBlockBreakingDelta(state, player, world, pos);
   }

   protected void updateLeaves(BlockState state, World world, BlockPos pos, Random random, int height) {
      BlockState lv = world.getBlockState(pos.down());
      BlockPos lv2 = pos.down(2);
      BlockState lv3 = world.getBlockState(lv2);
      BambooLeaves lv4 = BambooLeaves.NONE;
      if (height >= 1) {
         if (!lv.isOf(Blocks.BAMBOO) || lv.get(LEAVES) == BambooLeaves.NONE) {
            lv4 = BambooLeaves.SMALL;
         } else if (lv.isOf(Blocks.BAMBOO) && lv.get(LEAVES) != BambooLeaves.NONE) {
            lv4 = BambooLeaves.LARGE;
            if (lv3.isOf(Blocks.BAMBOO)) {
               world.setBlockState(pos.down(), lv.with(LEAVES, BambooLeaves.SMALL), 3);
               world.setBlockState(lv2, lv3.with(LEAVES, BambooLeaves.NONE), 3);
            }
         }
      }

      int j = state.get(AGE) != 1 && !lv3.isOf(Blocks.BAMBOO) ? 0 : 1;
      int k = (height < 11 || !(random.nextFloat() < 0.25F)) && height != 15 ? 0 : 1;
      world.setBlockState(pos.up(), this.getDefaultState().with(AGE, Integer.valueOf(j)).with(LEAVES, lv4).with(STAGE, Integer.valueOf(k)), 3);
   }

   protected int countBambooAbove(BlockView world, BlockPos pos) {
      int i = 0;

      while (i < 16 && world.getBlockState(pos.up(i + 1)).isOf(Blocks.BAMBOO)) {
         i++;
      }

      return i;
   }

   protected int countBambooBelow(BlockView world, BlockPos pos) {
      int i = 0;

      while (i < 16 && world.getBlockState(pos.down(i + 1)).isOf(Blocks.BAMBOO)) {
         i++;
      }

      return i;
   }
}
