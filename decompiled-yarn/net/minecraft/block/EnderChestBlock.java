package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class EnderChestBlock extends AbstractChestBlock<EnderChestBlockEntity> implements Waterloggable {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   private static final Text CONTAINER_NAME = new TranslatableText("container.enderchest");

   protected EnderChestBlock(AbstractBlock.Settings _snowman) {
      super(_snowman, () -> BlockEntityType.ENDER_CHEST);
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.valueOf(false)));
   }

   @Override
   public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(
      BlockState state, World world, BlockPos pos, boolean ignoreBlocked
   ) {
      return DoubleBlockProperties.PropertyRetriever::getFallback;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      FluidState _snowman = ctx.getWorld().getFluidState(ctx.getBlockPos());
      return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(WATERLOGGED, Boolean.valueOf(_snowman.getFluid() == Fluids.WATER));
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      EnderChestInventory _snowman = player.getEnderChestInventory();
      BlockEntity _snowmanx = world.getBlockEntity(pos);
      if (_snowman != null && _snowmanx instanceof EnderChestBlockEntity) {
         BlockPos _snowmanxx = pos.up();
         if (world.getBlockState(_snowmanxx).isSolidBlock(world, _snowmanxx)) {
            return ActionResult.success(world.isClient);
         } else if (world.isClient) {
            return ActionResult.SUCCESS;
         } else {
            EnderChestBlockEntity _snowmanxxx = (EnderChestBlockEntity)_snowmanx;
            _snowman.setActiveBlockEntity(_snowmanxxx);
            player.openHandledScreen(
               new SimpleNamedScreenHandlerFactory((_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx) -> GenericContainerScreenHandler.createGeneric9x3(_snowmanxxxx, _snowmanxxxxx, _snowman), CONTAINER_NAME)
            );
            player.incrementStat(Stats.OPEN_ENDERCHEST);
            PiglinBrain.onGuardedBlockInteracted(player, true);
            return ActionResult.CONSUME;
         }
      } else {
         return ActionResult.success(world.isClient);
      }
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new EnderChestBlockEntity();
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      for (int _snowman = 0; _snowman < 3; _snowman++) {
         int _snowmanx = random.nextInt(2) * 2 - 1;
         int _snowmanxx = random.nextInt(2) * 2 - 1;
         double _snowmanxxx = (double)pos.getX() + 0.5 + 0.25 * (double)_snowmanx;
         double _snowmanxxxx = (double)((float)pos.getY() + random.nextFloat());
         double _snowmanxxxxx = (double)pos.getZ() + 0.5 + 0.25 * (double)_snowmanxx;
         double _snowmanxxxxxx = (double)(random.nextFloat() * (float)_snowmanx);
         double _snowmanxxxxxxx = ((double)random.nextFloat() - 0.5) * 0.125;
         double _snowmanxxxxxxxx = (double)(random.nextFloat() * (float)_snowmanxx);
         world.addParticle(ParticleTypes.PORTAL, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
      }
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation(state.get(FACING)));
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, WATERLOGGED);
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (state.get(WATERLOGGED)) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
