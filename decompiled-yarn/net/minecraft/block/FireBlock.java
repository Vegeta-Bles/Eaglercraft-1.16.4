package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class FireBlock extends AbstractFireBlock {
   public static final IntProperty AGE = Properties.AGE_15;
   public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
   public static final BooleanProperty EAST = ConnectingBlock.EAST;
   public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
   public static final BooleanProperty WEST = ConnectingBlock.WEST;
   public static final BooleanProperty UP = ConnectingBlock.UP;
   private static final Map<Direction, BooleanProperty> DIRECTION_PROPERTIES = ConnectingBlock.FACING_PROPERTIES
      .entrySet()
      .stream()
      .filter(_snowman -> _snowman.getKey() != Direction.DOWN)
      .collect(Util.toMap());
   private static final VoxelShape field_26653 = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape field_26654 = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape field_26655 = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape field_26656 = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape field_26657 = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private final Map<BlockState, VoxelShape> field_26658;
   private final Object2IntMap<Block> burnChances = new Object2IntOpenHashMap();
   private final Object2IntMap<Block> spreadChances = new Object2IntOpenHashMap();

   public FireBlock(AbstractBlock.Settings _snowman) {
      super(_snowman, 1.0F);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(AGE, Integer.valueOf(0))
            .with(NORTH, Boolean.valueOf(false))
            .with(EAST, Boolean.valueOf(false))
            .with(SOUTH, Boolean.valueOf(false))
            .with(WEST, Boolean.valueOf(false))
            .with(UP, Boolean.valueOf(false))
      );
      this.field_26658 = ImmutableMap.copyOf(
         this.stateManager.getStates().stream().filter(_snowmanx -> _snowmanx.get(AGE) == 0).collect(Collectors.toMap(Function.identity(), FireBlock::method_31016))
      );
   }

   private static VoxelShape method_31016(BlockState _snowman) {
      VoxelShape _snowmanx = VoxelShapes.empty();
      if (_snowman.get(UP)) {
         _snowmanx = field_26653;
      }

      if (_snowman.get(NORTH)) {
         _snowmanx = VoxelShapes.union(_snowmanx, field_26656);
      }

      if (_snowman.get(SOUTH)) {
         _snowmanx = VoxelShapes.union(_snowmanx, field_26657);
      }

      if (_snowman.get(EAST)) {
         _snowmanx = VoxelShapes.union(_snowmanx, field_26655);
      }

      if (_snowman.get(WEST)) {
         _snowmanx = VoxelShapes.union(_snowmanx, field_26654);
      }

      return _snowmanx.isEmpty() ? BASE_SHAPE : _snowmanx;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return this.canPlaceAt(state, world, pos) ? this.method_24855(world, pos, state.get(AGE)) : Blocks.AIR.getDefaultState();
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.field_26658.get(state.with(AGE, Integer.valueOf(0)));
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.getStateForPosition(ctx.getWorld(), ctx.getBlockPos());
   }

   protected BlockState getStateForPosition(BlockView world, BlockPos pos) {
      BlockPos _snowman = pos.down();
      BlockState _snowmanx = world.getBlockState(_snowman);
      if (!this.isFlammable(_snowmanx) && !_snowmanx.isSideSolidFullSquare(world, _snowman, Direction.UP)) {
         BlockState _snowmanxx = this.getDefaultState();

         for (Direction _snowmanxxx : Direction.values()) {
            BooleanProperty _snowmanxxxx = DIRECTION_PROPERTIES.get(_snowmanxxx);
            if (_snowmanxxxx != null) {
               _snowmanxx = _snowmanxx.with(_snowmanxxxx, Boolean.valueOf(this.isFlammable(world.getBlockState(pos.offset(_snowmanxxx)))));
            }
         }

         return _snowmanxx;
      } else {
         return this.getDefaultState();
      }
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockPos _snowman = pos.down();
      return world.getBlockState(_snowman).isSideSolidFullSquare(world, _snowman, Direction.UP) || this.areBlocksAroundFlammable(world, pos);
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      world.getBlockTickScheduler().schedule(pos, this, method_26155(world.random));
      if (world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
         if (!state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false);
         }

         BlockState _snowman = world.getBlockState(pos.down());
         boolean _snowmanx = _snowman.isIn(world.getDimension().getInfiniburnBlocks());
         int _snowmanxx = state.get(AGE);
         if (!_snowmanx && world.isRaining() && this.isRainingAround(world, pos) && random.nextFloat() < 0.2F + (float)_snowmanxx * 0.03F) {
            world.removeBlock(pos, false);
         } else {
            int _snowmanxxx = Math.min(15, _snowmanxx + random.nextInt(3) / 2);
            if (_snowmanxx != _snowmanxxx) {
               state = state.with(AGE, Integer.valueOf(_snowmanxxx));
               world.setBlockState(pos, state, 4);
            }

            if (!_snowmanx) {
               if (!this.areBlocksAroundFlammable(world, pos)) {
                  BlockPos _snowmanxxxx = pos.down();
                  if (!world.getBlockState(_snowmanxxxx).isSideSolidFullSquare(world, _snowmanxxxx, Direction.UP) || _snowmanxx > 3) {
                     world.removeBlock(pos, false);
                  }

                  return;
               }

               if (_snowmanxx == 15 && random.nextInt(4) == 0 && !this.isFlammable(world.getBlockState(pos.down()))) {
                  world.removeBlock(pos, false);
                  return;
               }
            }

            boolean _snowmanxxxx = world.hasHighHumidity(pos);
            int _snowmanxxxxx = _snowmanxxxx ? -50 : 0;
            this.trySpreadingFire(world, pos.east(), 300 + _snowmanxxxxx, random, _snowmanxx);
            this.trySpreadingFire(world, pos.west(), 300 + _snowmanxxxxx, random, _snowmanxx);
            this.trySpreadingFire(world, pos.down(), 250 + _snowmanxxxxx, random, _snowmanxx);
            this.trySpreadingFire(world, pos.up(), 250 + _snowmanxxxxx, random, _snowmanxx);
            this.trySpreadingFire(world, pos.north(), 300 + _snowmanxxxxx, random, _snowmanxx);
            this.trySpreadingFire(world, pos.south(), 300 + _snowmanxxxxx, random, _snowmanxx);
            BlockPos.Mutable _snowmanxxxxxx = new BlockPos.Mutable();

            for (int _snowmanxxxxxxx = -1; _snowmanxxxxxxx <= 1; _snowmanxxxxxxx++) {
               for (int _snowmanxxxxxxxx = -1; _snowmanxxxxxxxx <= 1; _snowmanxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxx = -1; _snowmanxxxxxxxxx <= 4; _snowmanxxxxxxxxx++) {
                     if (_snowmanxxxxxxx != 0 || _snowmanxxxxxxxxx != 0 || _snowmanxxxxxxxx != 0) {
                        int _snowmanxxxxxxxxxx = 100;
                        if (_snowmanxxxxxxxxx > 1) {
                           _snowmanxxxxxxxxxx += (_snowmanxxxxxxxxx - 1) * 100;
                        }

                        _snowmanxxxxxx.set(pos, _snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx);
                        int _snowmanxxxxxxxxxxx = this.getBurnChance(world, _snowmanxxxxxx);
                        if (_snowmanxxxxxxxxxxx > 0) {
                           int _snowmanxxxxxxxxxxxx = (_snowmanxxxxxxxxxxx + 40 + world.getDifficulty().getId() * 7) / (_snowmanxx + 30);
                           if (_snowmanxxxx) {
                              _snowmanxxxxxxxxxxxx /= 2;
                           }

                           if (_snowmanxxxxxxxxxxxx > 0
                              && random.nextInt(_snowmanxxxxxxxxxx) <= _snowmanxxxxxxxxxxxx
                              && (!world.isRaining() || !this.isRainingAround(world, _snowmanxxxxxx))) {
                              int _snowmanxxxxxxxxxxxxx = Math.min(15, _snowmanxx + random.nextInt(5) / 4);
                              world.setBlockState(_snowmanxxxxxx, this.method_24855(world, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxx), 3);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected boolean isRainingAround(World world, BlockPos pos) {
      return world.hasRain(pos) || world.hasRain(pos.west()) || world.hasRain(pos.east()) || world.hasRain(pos.north()) || world.hasRain(pos.south());
   }

   private int getSpreadChance(BlockState state) {
      return state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED) ? 0 : this.spreadChances.getInt(state.getBlock());
   }

   private int getBurnChance(BlockState state) {
      return state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED) ? 0 : this.burnChances.getInt(state.getBlock());
   }

   private void trySpreadingFire(World world, BlockPos pos, int spreadFactor, Random rand, int currentAge) {
      int _snowman = this.getSpreadChance(world.getBlockState(pos));
      if (rand.nextInt(spreadFactor) < _snowman) {
         BlockState _snowmanx = world.getBlockState(pos);
         if (rand.nextInt(currentAge + 10) < 5 && !world.hasRain(pos)) {
            int _snowmanxx = Math.min(currentAge + rand.nextInt(5) / 4, 15);
            world.setBlockState(pos, this.method_24855(world, pos, _snowmanxx), 3);
         } else {
            world.removeBlock(pos, false);
         }

         Block _snowmanxx = _snowmanx.getBlock();
         if (_snowmanxx instanceof TntBlock) {
            TntBlock.primeTnt(world, pos);
         }
      }
   }

   private BlockState method_24855(WorldAccess _snowman, BlockPos _snowman, int _snowman) {
      BlockState _snowmanxxx = getState(_snowman, _snowman);
      return _snowmanxxx.isOf(Blocks.FIRE) ? _snowmanxxx.with(AGE, Integer.valueOf(_snowman)) : _snowmanxxx;
   }

   private boolean areBlocksAroundFlammable(BlockView world, BlockPos pos) {
      for (Direction _snowman : Direction.values()) {
         if (this.isFlammable(world.getBlockState(pos.offset(_snowman)))) {
            return true;
         }
      }

      return false;
   }

   private int getBurnChance(WorldView _snowman, BlockPos pos) {
      if (!_snowman.isAir(pos)) {
         return 0;
      } else {
         int _snowmanx = 0;

         for (Direction _snowmanxx : Direction.values()) {
            BlockState _snowmanxxx = _snowman.getBlockState(pos.offset(_snowmanxx));
            _snowmanx = Math.max(this.getBurnChance(_snowmanxxx), _snowmanx);
         }

         return _snowmanx;
      }
   }

   @Override
   protected boolean isFlammable(BlockState state) {
      return this.getBurnChance(state) > 0;
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      super.onBlockAdded(state, world, pos, oldState, notify);
      world.getBlockTickScheduler().schedule(pos, this, method_26155(world.random));
   }

   private static int method_26155(Random _snowman) {
      return 30 + _snowman.nextInt(10);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
   }

   private void registerFlammableBlock(Block block, int burnChance, int spreadChance) {
      this.burnChances.put(block, burnChance);
      this.spreadChances.put(block, spreadChance);
   }

   public static void registerDefaultFlammables() {
      FireBlock _snowman = (FireBlock)Blocks.FIRE;
      _snowman.registerFlammableBlock(Blocks.OAK_PLANKS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.SPRUCE_PLANKS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.BIRCH_PLANKS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.JUNGLE_PLANKS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.ACACIA_PLANKS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.DARK_OAK_PLANKS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.OAK_SLAB, 5, 20);
      _snowman.registerFlammableBlock(Blocks.SPRUCE_SLAB, 5, 20);
      _snowman.registerFlammableBlock(Blocks.BIRCH_SLAB, 5, 20);
      _snowman.registerFlammableBlock(Blocks.JUNGLE_SLAB, 5, 20);
      _snowman.registerFlammableBlock(Blocks.ACACIA_SLAB, 5, 20);
      _snowman.registerFlammableBlock(Blocks.DARK_OAK_SLAB, 5, 20);
      _snowman.registerFlammableBlock(Blocks.OAK_FENCE_GATE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.SPRUCE_FENCE_GATE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.BIRCH_FENCE_GATE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.JUNGLE_FENCE_GATE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.ACACIA_FENCE_GATE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.OAK_FENCE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.SPRUCE_FENCE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.BIRCH_FENCE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.JUNGLE_FENCE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.DARK_OAK_FENCE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.ACACIA_FENCE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.OAK_STAIRS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.BIRCH_STAIRS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.SPRUCE_STAIRS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.JUNGLE_STAIRS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.ACACIA_STAIRS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.DARK_OAK_STAIRS, 5, 20);
      _snowman.registerFlammableBlock(Blocks.OAK_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.SPRUCE_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.BIRCH_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.JUNGLE_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.ACACIA_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.DARK_OAK_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_OAK_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_SPRUCE_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_BIRCH_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_JUNGLE_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_ACACIA_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_DARK_OAK_LOG, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_OAK_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_SPRUCE_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_BIRCH_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_JUNGLE_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_ACACIA_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.STRIPPED_DARK_OAK_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.OAK_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.SPRUCE_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.BIRCH_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.JUNGLE_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.ACACIA_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.DARK_OAK_WOOD, 5, 5);
      _snowman.registerFlammableBlock(Blocks.OAK_LEAVES, 30, 60);
      _snowman.registerFlammableBlock(Blocks.SPRUCE_LEAVES, 30, 60);
      _snowman.registerFlammableBlock(Blocks.BIRCH_LEAVES, 30, 60);
      _snowman.registerFlammableBlock(Blocks.JUNGLE_LEAVES, 30, 60);
      _snowman.registerFlammableBlock(Blocks.ACACIA_LEAVES, 30, 60);
      _snowman.registerFlammableBlock(Blocks.DARK_OAK_LEAVES, 30, 60);
      _snowman.registerFlammableBlock(Blocks.BOOKSHELF, 30, 20);
      _snowman.registerFlammableBlock(Blocks.TNT, 15, 100);
      _snowman.registerFlammableBlock(Blocks.GRASS, 60, 100);
      _snowman.registerFlammableBlock(Blocks.FERN, 60, 100);
      _snowman.registerFlammableBlock(Blocks.DEAD_BUSH, 60, 100);
      _snowman.registerFlammableBlock(Blocks.SUNFLOWER, 60, 100);
      _snowman.registerFlammableBlock(Blocks.LILAC, 60, 100);
      _snowman.registerFlammableBlock(Blocks.ROSE_BUSH, 60, 100);
      _snowman.registerFlammableBlock(Blocks.PEONY, 60, 100);
      _snowman.registerFlammableBlock(Blocks.TALL_GRASS, 60, 100);
      _snowman.registerFlammableBlock(Blocks.LARGE_FERN, 60, 100);
      _snowman.registerFlammableBlock(Blocks.DANDELION, 60, 100);
      _snowman.registerFlammableBlock(Blocks.POPPY, 60, 100);
      _snowman.registerFlammableBlock(Blocks.BLUE_ORCHID, 60, 100);
      _snowman.registerFlammableBlock(Blocks.ALLIUM, 60, 100);
      _snowman.registerFlammableBlock(Blocks.AZURE_BLUET, 60, 100);
      _snowman.registerFlammableBlock(Blocks.RED_TULIP, 60, 100);
      _snowman.registerFlammableBlock(Blocks.ORANGE_TULIP, 60, 100);
      _snowman.registerFlammableBlock(Blocks.WHITE_TULIP, 60, 100);
      _snowman.registerFlammableBlock(Blocks.PINK_TULIP, 60, 100);
      _snowman.registerFlammableBlock(Blocks.OXEYE_DAISY, 60, 100);
      _snowman.registerFlammableBlock(Blocks.CORNFLOWER, 60, 100);
      _snowman.registerFlammableBlock(Blocks.LILY_OF_THE_VALLEY, 60, 100);
      _snowman.registerFlammableBlock(Blocks.WITHER_ROSE, 60, 100);
      _snowman.registerFlammableBlock(Blocks.WHITE_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.ORANGE_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.MAGENTA_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.LIGHT_BLUE_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.YELLOW_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.LIME_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.PINK_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.GRAY_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.LIGHT_GRAY_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.CYAN_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.PURPLE_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.BLUE_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.BROWN_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.GREEN_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.RED_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.BLACK_WOOL, 30, 60);
      _snowman.registerFlammableBlock(Blocks.VINE, 15, 100);
      _snowman.registerFlammableBlock(Blocks.COAL_BLOCK, 5, 5);
      _snowman.registerFlammableBlock(Blocks.HAY_BLOCK, 60, 20);
      _snowman.registerFlammableBlock(Blocks.TARGET, 15, 20);
      _snowman.registerFlammableBlock(Blocks.WHITE_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.ORANGE_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.MAGENTA_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.LIGHT_BLUE_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.YELLOW_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.LIME_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.PINK_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.GRAY_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.LIGHT_GRAY_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.CYAN_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.PURPLE_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.BLUE_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.BROWN_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.GREEN_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.RED_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.BLACK_CARPET, 60, 20);
      _snowman.registerFlammableBlock(Blocks.DRIED_KELP_BLOCK, 30, 60);
      _snowman.registerFlammableBlock(Blocks.BAMBOO, 60, 60);
      _snowman.registerFlammableBlock(Blocks.SCAFFOLDING, 60, 60);
      _snowman.registerFlammableBlock(Blocks.LECTERN, 30, 20);
      _snowman.registerFlammableBlock(Blocks.COMPOSTER, 5, 20);
      _snowman.registerFlammableBlock(Blocks.SWEET_BERRY_BUSH, 60, 100);
      _snowman.registerFlammableBlock(Blocks.BEEHIVE, 5, 20);
      _snowman.registerFlammableBlock(Blocks.BEE_NEST, 30, 20);
   }
}
