/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TntBlock;
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

public class FireBlock
extends AbstractFireBlock {
    public static final IntProperty AGE = Properties.AGE_15;
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    public static final BooleanProperty UP = ConnectingBlock.UP;
    private static final Map<Direction, BooleanProperty> DIRECTION_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream().filter(entry -> entry.getKey() != Direction.DOWN).collect(Util.toMap());
    private static final VoxelShape field_26653 = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape field_26654 = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape field_26655 = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape field_26656 = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape field_26657 = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> field_26658;
    private final Object2IntMap<Block> burnChances = new Object2IntOpenHashMap();
    private final Object2IntMap<Block> spreadChances = new Object2IntOpenHashMap();

    public FireBlock(AbstractBlock.Settings settings) {
        super(settings, 1.0f);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0)).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(UP, false));
        this.field_26658 = ImmutableMap.copyOf(this.stateManager.getStates().stream().filter(blockState -> blockState.get(AGE) == 0).collect(Collectors.toMap(Function.identity(), FireBlock::method_31016)));
    }

    private static VoxelShape method_31016(BlockState blockState) {
        VoxelShape voxelShape = VoxelShapes.empty();
        if (blockState.get(UP).booleanValue()) {
            voxelShape = field_26653;
        }
        if (blockState.get(NORTH).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, field_26656);
        }
        if (blockState.get(SOUTH).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, field_26657);
        }
        if (blockState.get(EAST).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, field_26655);
        }
        if (blockState.get(WEST).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, field_26654);
        }
        return voxelShape.isEmpty() ? BASE_SHAPE : voxelShape;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (this.canPlaceAt(state, world, pos)) {
            return this.method_24855(world, pos, state.get(AGE));
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.field_26658.get(state.with(AGE, 0));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getStateForPosition(ctx.getWorld(), ctx.getBlockPos());
    }

    protected BlockState getStateForPosition(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState _snowman2 = world.getBlockState(blockPos);
        if (this.isFlammable(_snowman2) || _snowman2.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
            return this.getDefaultState();
        }
        BlockState _snowman3 = this.getDefaultState();
        for (Direction direction : Direction.values()) {
            BooleanProperty booleanProperty = DIRECTION_PROPERTIES.get(direction);
            if (booleanProperty == null) continue;
            _snowman3 = (BlockState)_snowman3.with(booleanProperty, this.isFlammable(world.getBlockState(pos.offset(direction))));
        }
        return _snowman3;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP) || this.areBlocksAroundFlammable(world, pos);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean bl;
        world.getBlockTickScheduler().schedule(pos, this, FireBlock.method_26155(world.random));
        if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            return;
        }
        if (!state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false);
        }
        BlockState blockState = world.getBlockState(pos.down());
        boolean _snowman2 = blockState.isIn(world.getDimension().getInfiniburnBlocks());
        int _snowman3 = state.get(AGE);
        if (!_snowman2 && world.isRaining() && this.isRainingAround(world, pos) && random.nextFloat() < 0.2f + (float)_snowman3 * 0.03f) {
            world.removeBlock(pos, false);
            return;
        }
        int _snowman4 = Math.min(15, _snowman3 + random.nextInt(3) / 2);
        if (_snowman3 != _snowman4) {
            state = (BlockState)state.with(AGE, _snowman4);
            world.setBlockState(pos, state, 4);
        }
        if (!_snowman2) {
            if (!this.areBlocksAroundFlammable(world, pos)) {
                BlockPos blockPos = pos.down();
                if (!world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP) || _snowman3 > 3) {
                    world.removeBlock(pos, false);
                }
                return;
            }
            if (_snowman3 == 15 && random.nextInt(4) == 0 && !this.isFlammable(world.getBlockState(pos.down()))) {
                world.removeBlock(pos, false);
                return;
            }
        }
        int _snowman5 = (bl = world.hasHighHumidity(pos)) ? -50 : 0;
        this.trySpreadingFire(world, pos.east(), 300 + _snowman5, random, _snowman3);
        this.trySpreadingFire(world, pos.west(), 300 + _snowman5, random, _snowman3);
        this.trySpreadingFire(world, pos.down(), 250 + _snowman5, random, _snowman3);
        this.trySpreadingFire(world, pos.up(), 250 + _snowman5, random, _snowman3);
        this.trySpreadingFire(world, pos.north(), 300 + _snowman5, random, _snowman3);
        this.trySpreadingFire(world, pos.south(), 300 + _snowman5, random, _snowman3);
        BlockPos.Mutable _snowman6 = new BlockPos.Mutable();
        for (int i = -1; i <= 1; ++i) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                for (_snowman = -1; _snowman <= 4; ++_snowman) {
                    if (i == 0 && _snowman == 0 && _snowman == 0) continue;
                    _snowman = 100;
                    if (_snowman > 1) {
                        _snowman += (_snowman - 1) * 100;
                    }
                    _snowman6.set(pos, i, _snowman, _snowman);
                    _snowman = this.getBurnChance(world, _snowman6);
                    if (_snowman <= 0) continue;
                    _snowman = (_snowman + 40 + world.getDifficulty().getId() * 7) / (_snowman3 + 30);
                    if (bl) {
                        _snowman /= 2;
                    }
                    if (_snowman <= 0 || random.nextInt(_snowman) > _snowman || world.isRaining() && this.isRainingAround(world, _snowman6)) continue;
                    _snowman = Math.min(15, _snowman3 + random.nextInt(5) / 4);
                    world.setBlockState(_snowman6, this.method_24855(world, _snowman6, _snowman), 3);
                }
            }
        }
    }

    protected boolean isRainingAround(World world, BlockPos pos) {
        return world.hasRain(pos) || world.hasRain(pos.west()) || world.hasRain(pos.east()) || world.hasRain(pos.north()) || world.hasRain(pos.south());
    }

    private int getSpreadChance(BlockState state) {
        if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED).booleanValue()) {
            return 0;
        }
        return this.spreadChances.getInt((Object)state.getBlock());
    }

    private int getBurnChance(BlockState state) {
        if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED).booleanValue()) {
            return 0;
        }
        return this.burnChances.getInt((Object)state.getBlock());
    }

    private void trySpreadingFire(World world, BlockPos pos, int spreadFactor, Random rand, int currentAge) {
        int n = this.getSpreadChance(world.getBlockState(pos));
        if (rand.nextInt(spreadFactor) < n) {
            BlockState blockState = world.getBlockState(pos);
            if (rand.nextInt(currentAge + 10) < 5 && !world.hasRain(pos)) {
                int n2 = Math.min(currentAge + rand.nextInt(5) / 4, 15);
                world.setBlockState(pos, this.method_24855(world, pos, n2), 3);
            } else {
                world.removeBlock(pos, false);
            }
            Block _snowman2 = blockState.getBlock();
            if (_snowman2 instanceof TntBlock) {
                TntBlock cfr_ignored_0 = (TntBlock)_snowman2;
                TntBlock.primeTnt(world, pos);
            }
        }
    }

    private BlockState method_24855(WorldAccess worldAccess, BlockPos blockPos, int n) {
        BlockState blockState = FireBlock.getState(worldAccess, blockPos);
        if (blockState.isOf(Blocks.FIRE)) {
            return (BlockState)blockState.with(AGE, n);
        }
        return blockState;
    }

    private boolean areBlocksAroundFlammable(BlockView world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (!this.isFlammable(world.getBlockState(pos.offset(direction)))) continue;
            return true;
        }
        return false;
    }

    private int getBurnChance(WorldView worldView, BlockPos pos) {
        if (!worldView.isAir(pos)) {
            return 0;
        }
        int _snowman2 = 0;
        for (Direction direction : Direction.values()) {
            BlockState blockState = worldView.getBlockState(pos.offset(direction));
            _snowman2 = Math.max(this.getBurnChance(blockState), _snowman2);
        }
        return _snowman2;
    }

    @Override
    protected boolean isFlammable(BlockState state) {
        return this.getBurnChance(state) > 0;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        world.getBlockTickScheduler().schedule(pos, this, FireBlock.method_26155(world.random));
    }

    private static int method_26155(Random random) {
        return 30 + random.nextInt(10);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
    }

    private void registerFlammableBlock(Block block, int burnChance, int spreadChance) {
        this.burnChances.put((Object)block, burnChance);
        this.spreadChances.put((Object)block, spreadChance);
    }

    public static void registerDefaultFlammables() {
        FireBlock fireBlock = (FireBlock)Blocks.FIRE;
        fireBlock.registerFlammableBlock(Blocks.OAK_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_OAK_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_SPRUCE_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_BIRCH_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_JUNGLE_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_ACACIA_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_DARK_OAK_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_OAK_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_SPRUCE_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_BIRCH_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_JUNGLE_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_ACACIA_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_DARK_OAK_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.OAK_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.OAK_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BOOKSHELF, 30, 20);
        fireBlock.registerFlammableBlock(Blocks.TNT, 15, 100);
        fireBlock.registerFlammableBlock(Blocks.GRASS, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.FERN, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.DEAD_BUSH, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.SUNFLOWER, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.LILAC, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.ROSE_BUSH, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.PEONY, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.TALL_GRASS, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.LARGE_FERN, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.DANDELION, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.POPPY, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.BLUE_ORCHID, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.ALLIUM, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.AZURE_BLUET, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.RED_TULIP, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.ORANGE_TULIP, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.WHITE_TULIP, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.PINK_TULIP, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.OXEYE_DAISY, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.CORNFLOWER, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.LILY_OF_THE_VALLEY, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.WITHER_ROSE, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.WHITE_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.ORANGE_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.MAGENTA_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.LIGHT_BLUE_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.YELLOW_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.LIME_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.PINK_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.GRAY_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.LIGHT_GRAY_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.CYAN_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.PURPLE_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BLUE_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BROWN_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.GREEN_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.RED_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BLACK_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.VINE, 15, 100);
        fireBlock.registerFlammableBlock(Blocks.COAL_BLOCK, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.HAY_BLOCK, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.TARGET, 15, 20);
        fireBlock.registerFlammableBlock(Blocks.WHITE_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.ORANGE_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.MAGENTA_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.LIGHT_BLUE_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.YELLOW_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.LIME_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.PINK_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.GRAY_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.LIGHT_GRAY_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.CYAN_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.PURPLE_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.BLUE_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.BROWN_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.GREEN_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.RED_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.BLACK_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.DRIED_KELP_BLOCK, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO, 60, 60);
        fireBlock.registerFlammableBlock(Blocks.SCAFFOLDING, 60, 60);
        fireBlock.registerFlammableBlock(Blocks.LECTERN, 30, 20);
        fireBlock.registerFlammableBlock(Blocks.COMPOSTER, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SWEET_BERRY_BUSH, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.BEEHIVE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BEE_NEST, 30, 20);
    }
}

