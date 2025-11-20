package net.minecraft.block;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ChestBlock extends AbstractChestBlock<ChestBlockEntity> implements Waterloggable {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   public static final EnumProperty<ChestType> CHEST_TYPE = Properties.CHEST_TYPE;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   protected static final VoxelShape DOUBLE_NORTH_SHAPE = Block.createCuboidShape(1.0, 0.0, 0.0, 15.0, 14.0, 15.0);
   protected static final VoxelShape DOUBLE_SOUTH_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 16.0);
   protected static final VoxelShape DOUBLE_WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   protected static final VoxelShape DOUBLE_EAST_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 16.0, 14.0, 15.0);
   protected static final VoxelShape SINGLE_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   private static final DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<Inventory>> INVENTORY_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<Inventory>>() {
      public Optional<Inventory> getFromBoth(ChestBlockEntity _snowman, ChestBlockEntity _snowman) {
         return Optional.of(new DoubleInventory(_snowman, _snowman));
      }

      public Optional<Inventory> getFrom(ChestBlockEntity _snowman) {
         return Optional.of(_snowman);
      }

      public Optional<Inventory> getFallback() {
         return Optional.empty();
      }
   };
   private static final DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>> NAME_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>>() {
      public Optional<NamedScreenHandlerFactory> getFromBoth(ChestBlockEntity _snowman, ChestBlockEntity _snowman) {
         final Inventory _snowmanxx = new DoubleInventory(_snowman, _snowman);
         return Optional.of(new NamedScreenHandlerFactory() {
            @Nullable
            @Override
            public ScreenHandler createMenu(int _snowman, PlayerInventory _snowman, PlayerEntity _snowman) {
               if (_snowman.checkUnlocked(_snowman) && _snowman.checkUnlocked(_snowman)) {
                  _snowman.checkLootInteraction(_snowman.player);
                  _snowman.checkLootInteraction(_snowman.player);
                  return GenericContainerScreenHandler.createGeneric9x6(_snowman, _snowman, _snowman);
               } else {
                  return null;
               }
            }

            @Override
            public Text getDisplayName() {
               if (_snowman.hasCustomName()) {
                  return _snowman.getDisplayName();
               } else {
                  return (Text)(_snowman.hasCustomName() ? _snowman.getDisplayName() : new TranslatableText("container.chestDouble"));
               }
            }
         });
      }

      public Optional<NamedScreenHandlerFactory> getFrom(ChestBlockEntity _snowman) {
         return Optional.of(_snowman);
      }

      public Optional<NamedScreenHandlerFactory> getFallback() {
         return Optional.empty();
      }
   };

   protected ChestBlock(AbstractBlock.Settings _snowman, Supplier<BlockEntityType<? extends ChestBlockEntity>> _snowman) {
      super(_snowman, _snowman);
      this.setDefaultState(
         this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(CHEST_TYPE, ChestType.SINGLE).with(WATERLOGGED, Boolean.valueOf(false))
      );
   }

   public static DoubleBlockProperties.Type getDoubleBlockType(BlockState state) {
      ChestType _snowman = state.get(CHEST_TYPE);
      if (_snowman == ChestType.SINGLE) {
         return DoubleBlockProperties.Type.SINGLE;
      } else {
         return _snowman == ChestType.RIGHT ? DoubleBlockProperties.Type.FIRST : DoubleBlockProperties.Type.SECOND;
      }
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (state.get(WATERLOGGED)) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      if (newState.isOf(this) && direction.getAxis().isHorizontal()) {
         ChestType _snowman = newState.get(CHEST_TYPE);
         if (state.get(CHEST_TYPE) == ChestType.SINGLE
            && _snowman != ChestType.SINGLE
            && state.get(FACING) == newState.get(FACING)
            && getFacing(newState) == direction.getOpposite()) {
            return state.with(CHEST_TYPE, _snowman.getOpposite());
         }
      } else if (getFacing(state) == direction) {
         return state.with(CHEST_TYPE, ChestType.SINGLE);
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      if (state.get(CHEST_TYPE) == ChestType.SINGLE) {
         return SINGLE_SHAPE;
      } else {
         switch (getFacing(state)) {
            case NORTH:
            default:
               return DOUBLE_NORTH_SHAPE;
            case SOUTH:
               return DOUBLE_SOUTH_SHAPE;
            case WEST:
               return DOUBLE_WEST_SHAPE;
            case EAST:
               return DOUBLE_EAST_SHAPE;
         }
      }
   }

   public static Direction getFacing(BlockState state) {
      Direction _snowman = state.get(FACING);
      return state.get(CHEST_TYPE) == ChestType.LEFT ? _snowman.rotateYClockwise() : _snowman.rotateYCounterclockwise();
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      ChestType _snowman = ChestType.SINGLE;
      Direction _snowmanx = ctx.getPlayerFacing().getOpposite();
      FluidState _snowmanxx = ctx.getWorld().getFluidState(ctx.getBlockPos());
      boolean _snowmanxxx = ctx.shouldCancelInteraction();
      Direction _snowmanxxxx = ctx.getSide();
      if (_snowmanxxxx.getAxis().isHorizontal() && _snowmanxxx) {
         Direction _snowmanxxxxx = this.getNeighborChestDirection(ctx, _snowmanxxxx.getOpposite());
         if (_snowmanxxxxx != null && _snowmanxxxxx.getAxis() != _snowmanxxxx.getAxis()) {
            _snowmanx = _snowmanxxxxx;
            _snowman = _snowmanxxxxx.rotateYCounterclockwise() == _snowmanxxxx.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
         }
      }

      if (_snowman == ChestType.SINGLE && !_snowmanxxx) {
         if (_snowmanx == this.getNeighborChestDirection(ctx, _snowmanx.rotateYClockwise())) {
            _snowman = ChestType.LEFT;
         } else if (_snowmanx == this.getNeighborChestDirection(ctx, _snowmanx.rotateYCounterclockwise())) {
            _snowman = ChestType.RIGHT;
         }
      }

      return this.getDefaultState().with(FACING, _snowmanx).with(CHEST_TYPE, _snowman).with(WATERLOGGED, Boolean.valueOf(_snowmanxx.getFluid() == Fluids.WATER));
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   @Nullable
   private Direction getNeighborChestDirection(ItemPlacementContext ctx, Direction dir) {
      BlockState _snowman = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(dir));
      return _snowman.isOf(this) && _snowman.get(CHEST_TYPE) == ChestType.SINGLE ? _snowman.get(FACING) : null;
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
      if (itemStack.hasCustomName()) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof ChestBlockEntity) {
            ((ChestBlockEntity)_snowman).setCustomName(itemStack.getName());
         }
      }
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!state.isOf(newState.getBlock())) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof Inventory) {
            ItemScatterer.spawn(world, pos, (Inventory)_snowman);
            world.updateComparators(pos, this);
         }

         super.onStateReplaced(state, world, pos, newState, moved);
      }
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (world.isClient) {
         return ActionResult.SUCCESS;
      } else {
         NamedScreenHandlerFactory _snowman = this.createScreenHandlerFactory(state, world, pos);
         if (_snowman != null) {
            player.openHandledScreen(_snowman);
            player.incrementStat(this.getOpenStat());
            PiglinBrain.onGuardedBlockInteracted(player, true);
         }

         return ActionResult.CONSUME;
      }
   }

   protected Stat<Identifier> getOpenStat() {
      return Stats.CUSTOM.getOrCreateStat(Stats.OPEN_CHEST);
   }

   @Nullable
   public static Inventory getInventory(ChestBlock block, BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
      return block.getBlockEntitySource(state, world, pos, ignoreBlocked).apply(INVENTORY_RETRIEVER).orElse(null);
   }

   @Override
   public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(
      BlockState state, World world, BlockPos pos, boolean ignoreBlocked
   ) {
      BiPredicate<WorldAccess, BlockPos> _snowman;
      if (ignoreBlocked) {
         _snowman = (_snowmanx, _snowmanxx) -> false;
      } else {
         _snowman = ChestBlock::isChestBlocked;
      }

      return DoubleBlockProperties.toPropertySource(
         this.entityTypeRetriever.get(), ChestBlock::getDoubleBlockType, ChestBlock::getFacing, FACING, state, world, pos, _snowman
      );
   }

   @Nullable
   @Override
   public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
      return this.getBlockEntitySource(state, world, pos, false).apply(NAME_RETRIEVER).orElse(null);
   }

   public static DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Float2FloatFunction> getAnimationProgressRetriever(ChestAnimationProgress _snowman) {
      return new DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Float2FloatFunction>() {
         public Float2FloatFunction getFromBoth(ChestBlockEntity _snowman, ChestBlockEntity _snowman) {
            return _snowmanxxxxx -> Math.max(_snowman.getAnimationProgress(_snowmanxxxxx), _snowman.getAnimationProgress(_snowmanxxxxx));
         }

         public Float2FloatFunction getFrom(ChestBlockEntity _snowman) {
            return _snowman::getAnimationProgress;
         }

         public Float2FloatFunction getFallback() {
            return _snowman::getAnimationProgress;
         }
      };
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new ChestBlockEntity();
   }

   public static boolean isChestBlocked(WorldAccess world, BlockPos pos) {
      return hasBlockOnTop(world, pos) || hasOcelotOnTop(world, pos);
   }

   private static boolean hasBlockOnTop(BlockView world, BlockPos pos) {
      BlockPos _snowman = pos.up();
      return world.getBlockState(_snowman).isSolidBlock(world, _snowman);
   }

   private static boolean hasOcelotOnTop(WorldAccess world, BlockPos pos) {
      List<CatEntity> _snowman = world.getNonSpectatingEntities(
         CatEntity.class,
         new Box((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1))
      );
      if (!_snowman.isEmpty()) {
         for (CatEntity _snowmanx : _snowman) {
            if (_snowmanx.isInSittingPose()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean hasComparatorOutput(BlockState state) {
      return true;
   }

   @Override
   public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
      return ScreenHandler.calculateComparatorOutput(getInventory(this, state, world, pos, false));
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
      builder.add(FACING, CHEST_TYPE, WATERLOGGED);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
