package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WallRedstoneTorchBlock extends RedstoneTorchBlock {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

   protected WallRedstoneTorchBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(LIT, Boolean.valueOf(true)));
   }

   @Override
   public String getTranslationKey() {
      return this.asItem().getTranslationKey();
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return WallTorchBlock.getBoundingShape(state);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return Blocks.WALL_TORCH.canPlaceAt(state, world, pos);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return Blocks.WALL_TORCH.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState _snowman = Blocks.WALL_TORCH.getPlacementState(ctx);
      return _snowman == null ? null : this.getDefaultState().with(FACING, _snowman.get(FACING));
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(LIT)) {
         Direction _snowman = state.get(FACING).getOpposite();
         double _snowmanx = 0.27;
         double _snowmanxx = (double)pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2 + 0.27 * (double)_snowman.getOffsetX();
         double _snowmanxxx = (double)pos.getY() + 0.7 + (random.nextDouble() - 0.5) * 0.2 + 0.22;
         double _snowmanxxxx = (double)pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2 + 0.27 * (double)_snowman.getOffsetZ();
         world.addParticle(this.particle, _snowmanxx, _snowmanxxx, _snowmanxxxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected boolean shouldUnpower(World world, BlockPos pos, BlockState state) {
      Direction _snowman = state.get(FACING).getOpposite();
      return world.isEmittingRedstonePower(pos.offset(_snowman), _snowman);
   }

   @Override
   public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return state.get(LIT) && state.get(FACING) != direction ? 15 : 0;
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return Blocks.WALL_TORCH.rotate(state, rotation);
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return Blocks.WALL_TORCH.mirror(state, mirror);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, LIT);
   }
}
