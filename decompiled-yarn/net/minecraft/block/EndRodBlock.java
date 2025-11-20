package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EndRodBlock extends FacingBlock {
   protected static final VoxelShape Y_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
   protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(6.0, 6.0, 0.0, 10.0, 10.0, 16.0);
   protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0, 6.0, 6.0, 16.0, 10.0, 10.0);

   protected EndRodBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.with(FACING, mirror.apply(state.get(FACING)));
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      switch (state.get(FACING).getAxis()) {
         case X:
         default:
            return X_SHAPE;
         case Z:
            return Z_SHAPE;
         case Y:
            return Y_SHAPE;
      }
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      Direction _snowman = ctx.getSide();
      BlockState _snowmanx = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(_snowman.getOpposite()));
      return _snowmanx.isOf(this) && _snowmanx.get(FACING) == _snowman ? this.getDefaultState().with(FACING, _snowman.getOpposite()) : this.getDefaultState().with(FACING, _snowman);
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      Direction _snowman = state.get(FACING);
      double _snowmanx = (double)pos.getX() + 0.55 - (double)(random.nextFloat() * 0.1F);
      double _snowmanxx = (double)pos.getY() + 0.55 - (double)(random.nextFloat() * 0.1F);
      double _snowmanxxx = (double)pos.getZ() + 0.55 - (double)(random.nextFloat() * 0.1F);
      double _snowmanxxxx = (double)(0.4F - (random.nextFloat() + random.nextFloat()) * 0.4F);
      if (random.nextInt(5) == 0) {
         world.addParticle(
            ParticleTypes.END_ROD,
            _snowmanx + (double)_snowman.getOffsetX() * _snowmanxxxx,
            _snowmanxx + (double)_snowman.getOffsetY() * _snowmanxxxx,
            _snowmanxxx + (double)_snowman.getOffsetZ() * _snowmanxxxx,
            random.nextGaussian() * 0.005,
            random.nextGaussian() * 0.005,
            random.nextGaussian() * 0.005
         );
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING);
   }

   @Override
   public PistonBehavior getPistonBehavior(BlockState state) {
      return PistonBehavior.NORMAL;
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
