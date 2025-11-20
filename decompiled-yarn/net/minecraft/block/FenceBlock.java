package net.minecraft.block;

import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.state.StateManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class FenceBlock extends HorizontalConnectingBlock {
   private final VoxelShape[] cullingShapes;

   public FenceBlock(AbstractBlock.Settings _snowman) {
      super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, _snowman);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(NORTH, Boolean.valueOf(false))
            .with(EAST, Boolean.valueOf(false))
            .with(SOUTH, Boolean.valueOf(false))
            .with(WEST, Boolean.valueOf(false))
            .with(WATERLOGGED, Boolean.valueOf(false))
      );
      this.cullingShapes = this.createShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
   }

   @Override
   public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
      return this.cullingShapes[this.getShapeIndex(state)];
   }

   @Override
   public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.getOutlineShape(state, world, pos, context);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   public boolean canConnect(BlockState state, boolean neighborIsFullSquare, Direction dir) {
      Block _snowman = state.getBlock();
      boolean _snowmanx = this.isFence(_snowman);
      boolean _snowmanxx = _snowman instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, dir);
      return !cannotConnect(_snowman) && neighborIsFullSquare || _snowmanx || _snowmanxx;
   }

   private boolean isFence(Block block) {
      return block.isIn(BlockTags.FENCES) && block.isIn(BlockTags.WOODEN_FENCES) == this.getDefaultState().isIn(BlockTags.WOODEN_FENCES);
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (world.isClient) {
         ItemStack _snowman = player.getStackInHand(hand);
         return _snowman.getItem() == Items.LEAD ? ActionResult.SUCCESS : ActionResult.PASS;
      } else {
         return LeadItem.attachHeldMobsToBlock(player, world, pos);
      }
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockView _snowman = ctx.getWorld();
      BlockPos _snowmanx = ctx.getBlockPos();
      FluidState _snowmanxx = ctx.getWorld().getFluidState(ctx.getBlockPos());
      BlockPos _snowmanxxx = _snowmanx.north();
      BlockPos _snowmanxxxx = _snowmanx.east();
      BlockPos _snowmanxxxxx = _snowmanx.south();
      BlockPos _snowmanxxxxxx = _snowmanx.west();
      BlockState _snowmanxxxxxxx = _snowman.getBlockState(_snowmanxxx);
      BlockState _snowmanxxxxxxxx = _snowman.getBlockState(_snowmanxxxx);
      BlockState _snowmanxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxx);
      BlockState _snowmanxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxx);
      return super.getPlacementState(ctx)
         .with(NORTH, Boolean.valueOf(this.canConnect(_snowmanxxxxxxx, _snowmanxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxx, Direction.SOUTH), Direction.SOUTH)))
         .with(EAST, Boolean.valueOf(this.canConnect(_snowmanxxxxxxxx, _snowmanxxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxxx, Direction.WEST), Direction.WEST)))
         .with(SOUTH, Boolean.valueOf(this.canConnect(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxxxx, Direction.NORTH), Direction.NORTH)))
         .with(WEST, Boolean.valueOf(this.canConnect(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxxxxx, Direction.EAST), Direction.EAST)))
         .with(WATERLOGGED, Boolean.valueOf(_snowmanxx.getFluid() == Fluids.WATER));
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (state.get(WATERLOGGED)) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return direction.getAxis().getType() == Direction.Type.HORIZONTAL
         ? state.with(
            FACING_PROPERTIES.get(direction),
            Boolean.valueOf(this.canConnect(newState, newState.isSideSolidFullSquare(world, posFrom, direction.getOpposite()), direction.getOpposite()))
         )
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
   }
}
