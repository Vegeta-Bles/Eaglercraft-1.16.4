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

   public BambooBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
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
      VoxelShape _snowman = state.get(LEAVES) == BambooLeaves.LARGE ? LARGE_LEAVES_SHAPE : SMALL_LEAVES_SHAPE;
      Vec3d _snowmanx = state.getModelOffset(world, pos);
      return _snowman.offset(_snowmanx.x, _snowmanx.y, _snowmanx.z);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      Vec3d _snowman = state.getModelOffset(world, pos);
      return NO_LEAVES_SHAPE.offset(_snowman.x, _snowman.y, _snowman.z);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      FluidState _snowman = ctx.getWorld().getFluidState(ctx.getBlockPos());
      if (!_snowman.isEmpty()) {
         return null;
      } else {
         BlockState _snowmanx = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
         if (_snowmanx.isIn(BlockTags.BAMBOO_PLANTABLE_ON)) {
            if (_snowmanx.isOf(Blocks.BAMBOO_SAPLING)) {
               return this.getDefaultState().with(AGE, Integer.valueOf(0));
            } else if (_snowmanx.isOf(Blocks.BAMBOO)) {
               int _snowmanxx = _snowmanx.get(AGE) > 0 ? 1 : 0;
               return this.getDefaultState().with(AGE, Integer.valueOf(_snowmanxx));
            } else {
               BlockState _snowmanxx = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
               return !_snowmanxx.isOf(Blocks.BAMBOO) && !_snowmanxx.isOf(Blocks.BAMBOO_SAPLING)
                  ? Blocks.BAMBOO_SAPLING.getDefaultState()
                  : this.getDefaultState().with(AGE, _snowmanxx.get(AGE));
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
            int _snowman = this.countBambooBelow(world, pos) + 1;
            if (_snowman < 16) {
               this.updateLeaves(state, world, pos, random, _snowman);
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
      int _snowman = this.countBambooAbove(world, pos);
      int _snowmanx = this.countBambooBelow(world, pos);
      return _snowman + _snowmanx + 1 < 16 && world.getBlockState(pos.up(_snowman)).get(STAGE) != 1;
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      int _snowman = this.countBambooAbove(world, pos);
      int _snowmanx = this.countBambooBelow(world, pos);
      int _snowmanxx = _snowman + _snowmanx + 1;
      int _snowmanxxx = 1 + random.nextInt(2);

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
         BlockPos _snowmanxxxxx = pos.up(_snowman);
         BlockState _snowmanxxxxxx = world.getBlockState(_snowmanxxxxx);
         if (_snowmanxx >= 16 || _snowmanxxxxxx.get(STAGE) == 1 || !world.isAir(_snowmanxxxxx.up())) {
            return;
         }

         this.updateLeaves(_snowmanxxxxxx, world, _snowmanxxxxx, random, _snowmanxx);
         _snowman++;
         _snowmanxx++;
      }
   }

   @Override
   public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
      return player.getMainHandStack().getItem() instanceof SwordItem ? 1.0F : super.calcBlockBreakingDelta(state, player, world, pos);
   }

   protected void updateLeaves(BlockState state, World world, BlockPos pos, Random random, int height) {
      BlockState _snowman = world.getBlockState(pos.down());
      BlockPos _snowmanx = pos.down(2);
      BlockState _snowmanxx = world.getBlockState(_snowmanx);
      BambooLeaves _snowmanxxx = BambooLeaves.NONE;
      if (height >= 1) {
         if (!_snowman.isOf(Blocks.BAMBOO) || _snowman.get(LEAVES) == BambooLeaves.NONE) {
            _snowmanxxx = BambooLeaves.SMALL;
         } else if (_snowman.isOf(Blocks.BAMBOO) && _snowman.get(LEAVES) != BambooLeaves.NONE) {
            _snowmanxxx = BambooLeaves.LARGE;
            if (_snowmanxx.isOf(Blocks.BAMBOO)) {
               world.setBlockState(pos.down(), _snowman.with(LEAVES, BambooLeaves.SMALL), 3);
               world.setBlockState(_snowmanx, _snowmanxx.with(LEAVES, BambooLeaves.NONE), 3);
            }
         }
      }

      int _snowmanxxxx = state.get(AGE) != 1 && !_snowmanxx.isOf(Blocks.BAMBOO) ? 0 : 1;
      int _snowmanxxxxx = (height < 11 || !(random.nextFloat() < 0.25F)) && height != 15 ? 0 : 1;
      world.setBlockState(pos.up(), this.getDefaultState().with(AGE, Integer.valueOf(_snowmanxxxx)).with(LEAVES, _snowmanxxx).with(STAGE, Integer.valueOf(_snowmanxxxxx)), 3);
   }

   protected int countBambooAbove(BlockView world, BlockPos pos) {
      int _snowman = 0;

      while (_snowman < 16 && world.getBlockState(pos.up(_snowman + 1)).isOf(Blocks.BAMBOO)) {
         _snowman++;
      }

      return _snowman;
   }

   protected int countBambooBelow(BlockView world, BlockPos pos) {
      int _snowman = 0;

      while (_snowman < 16 && world.getBlockState(pos.down(_snowman + 1)).isOf(Blocks.BAMBOO)) {
         _snowman++;
      }

      return _snowman;
   }
}
