/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.Properties;
import net.minecraft.util.EightWayDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.PalettedContainer;
import net.minecraft.world.chunk.WorldChunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpgradeData {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final UpgradeData NO_UPGRADE_DATA = new UpgradeData();
    private static final EightWayDirection[] EIGHT_WAYS = EightWayDirection.values();
    private final EnumSet<EightWayDirection> sidesToUpgrade = EnumSet.noneOf(EightWayDirection.class);
    private final int[][] centerIndicesToUpgrade = new int[16][];
    private static final Map<Block, Logic> BLOCK_TO_LOGIC = new IdentityHashMap<Block, Logic>();
    private static final Set<Logic> CALLBACK_LOGICS = Sets.newHashSet();

    private UpgradeData() {
    }

    public UpgradeData(CompoundTag tag) {
        this();
        if (tag.contains("Indices", 10)) {
            CompoundTag compoundTag = tag.getCompound("Indices");
            for (int i = 0; i < this.centerIndicesToUpgrade.length; ++i) {
                String string = String.valueOf(i);
                if (!compoundTag.contains(string, 11)) continue;
                this.centerIndicesToUpgrade[i] = compoundTag.getIntArray(string);
            }
        }
        int n = tag.getInt("Sides");
        for (EightWayDirection eightWayDirection : EightWayDirection.values()) {
            if ((n & 1 << eightWayDirection.ordinal()) == 0) continue;
            this.sidesToUpgrade.add(eightWayDirection);
        }
    }

    public void upgrade(WorldChunk chunk) {
        this.upgradeCenter(chunk);
        for (EightWayDirection eightWayDirection : EIGHT_WAYS) {
            UpgradeData.upgradeSide(chunk, eightWayDirection);
        }
        World world = chunk.getWorld();
        CALLBACK_LOGICS.forEach(logic -> logic.postUpdate(world));
    }

    private static void upgradeSide(WorldChunk chunk, EightWayDirection side) {
        World world = chunk.getWorld();
        if (!chunk.getUpgradeData().sidesToUpgrade.remove((Object)side)) {
            return;
        }
        Set<Direction> _snowman2 = side.getDirections();
        boolean _snowman3 = false;
        int _snowman4 = 15;
        boolean _snowman5 = _snowman2.contains(Direction.EAST);
        boolean _snowman6 = _snowman2.contains(Direction.WEST);
        boolean _snowman7 = _snowman2.contains(Direction.SOUTH);
        boolean _snowman8 = _snowman2.contains(Direction.NORTH);
        boolean _snowman9 = _snowman2.size() == 1;
        ChunkPos _snowman10 = chunk.getPos();
        int _snowman11 = _snowman10.getStartX() + (_snowman9 && (_snowman8 || _snowman7) ? 1 : (_snowman6 ? 0 : 15));
        int _snowman12 = _snowman10.getStartX() + (_snowman9 && (_snowman8 || _snowman7) ? 14 : (_snowman6 ? 0 : 15));
        int _snowman13 = _snowman10.getStartZ() + (_snowman9 && (_snowman5 || _snowman6) ? 1 : (_snowman8 ? 0 : 15));
        int _snowman14 = _snowman10.getStartZ() + (_snowman9 && (_snowman5 || _snowman6) ? 14 : (_snowman8 ? 0 : 15));
        Direction[] _snowman15 = Direction.values();
        BlockPos.Mutable _snowman16 = new BlockPos.Mutable();
        for (BlockPos blockPos : BlockPos.iterate(_snowman11, 0, _snowman13, _snowman12, world.getHeight() - 1, _snowman14)) {
            BlockState blockState;
            BlockState blockState2 = blockState = world.getBlockState(blockPos);
            for (Direction direction : _snowman15) {
                _snowman16.set(blockPos, direction);
                blockState2 = UpgradeData.applyAdjacentBlock(blockState2, direction, world, blockPos, _snowman16);
            }
            Block.replace(blockState, blockState2, world, blockPos, 18);
        }
    }

    private static BlockState applyAdjacentBlock(BlockState oldState, Direction dir, WorldAccess world, BlockPos currentPos, BlockPos otherPos) {
        return BLOCK_TO_LOGIC.getOrDefault(oldState.getBlock(), BuiltinLogic.DEFAULT).getUpdatedState(oldState, dir, world.getBlockState(otherPos), world, currentPos, otherPos);
    }

    private void upgradeCenter(WorldChunk chunk) {
        int n;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        _snowman = new BlockPos.Mutable();
        ChunkPos _snowman2 = chunk.getPos();
        World _snowman3 = chunk.getWorld();
        for (n = 0; n < 16; ++n) {
            ChunkSection chunkSection = chunk.getSectionArray()[n];
            int[] _snowman4 = this.centerIndicesToUpgrade[n];
            this.centerIndicesToUpgrade[n] = null;
            if (chunkSection == null || _snowman4 == null || _snowman4.length <= 0) continue;
            Direction[] _snowman5 = Direction.values();
            PalettedContainer<BlockState> _snowman6 = chunkSection.getContainer();
            for (int n2 : _snowman4) {
                BlockState blockState;
                _snowman = n2 & 0xF;
                _snowman = n2 >> 8 & 0xF;
                _snowman = n2 >> 4 & 0xF;
                mutable.set(_snowman2.getStartX() + _snowman, (n << 4) + _snowman, _snowman2.getStartZ() + _snowman);
                BlockState blockState2 = blockState = _snowman6.get(n2);
                for (Direction direction : _snowman5) {
                    _snowman.set(mutable, direction);
                    if (mutable.getX() >> 4 != _snowman2.x || mutable.getZ() >> 4 != _snowman2.z) continue;
                    blockState2 = UpgradeData.applyAdjacentBlock(blockState2, direction, _snowman3, mutable, _snowman);
                }
                Block.replace(blockState, blockState2, _snowman3, mutable, 18);
            }
        }
        for (n = 0; n < this.centerIndicesToUpgrade.length; ++n) {
            if (this.centerIndicesToUpgrade[n] != null) {
                LOGGER.warn("Discarding update data for section {} for chunk ({} {})", (Object)n, (Object)_snowman2.x, (Object)_snowman2.z);
            }
            this.centerIndicesToUpgrade[n] = null;
        }
    }

    public boolean isDone() {
        for (int[] nArray : this.centerIndicesToUpgrade) {
            if (nArray == null) continue;
            return false;
        }
        return this.sidesToUpgrade.isEmpty();
    }

    public CompoundTag toTag() {
        CompoundTag compoundTag;
        int _snowman2;
        CompoundTag compoundTag2 = new CompoundTag();
        compoundTag = new CompoundTag();
        for (_snowman2 = 0; _snowman2 < this.centerIndicesToUpgrade.length; ++_snowman2) {
            String string = String.valueOf(_snowman2);
            if (this.centerIndicesToUpgrade[_snowman2] == null || this.centerIndicesToUpgrade[_snowman2].length == 0) continue;
            compoundTag.putIntArray(string, this.centerIndicesToUpgrade[_snowman2]);
        }
        if (!compoundTag.isEmpty()) {
            compoundTag2.put("Indices", compoundTag);
        }
        _snowman2 = 0;
        for (EightWayDirection eightWayDirection : this.sidesToUpgrade) {
            _snowman2 |= 1 << eightWayDirection.ordinal();
        }
        compoundTag2.putByte("Sides", (byte)_snowman2);
        return compoundTag2;
    }

    static enum BuiltinLogic implements Logic
    {
        BLACKLIST(new Block[]{Blocks.OBSERVER, Blocks.NETHER_PORTAL, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL, Blocks.DRAGON_EGG, Blocks.GRAVEL, Blocks.SAND, Blocks.RED_SAND, Blocks.OAK_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.ACACIA_SIGN, Blocks.JUNGLE_SIGN, Blocks.DARK_OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.DARK_OAK_WALL_SIGN}){

            public BlockState getUpdatedState(BlockState blockState, Direction direction, BlockState blockState2, WorldAccess worldAccess, BlockPos blockPos, BlockPos blockPos2) {
                return blockState;
            }
        }
        ,
        DEFAULT(new Block[0]){

            public BlockState getUpdatedState(BlockState blockState, Direction direction, BlockState blockState2, WorldAccess worldAccess, BlockPos blockPos, BlockPos blockPos2) {
                return blockState.getStateForNeighborUpdate(direction, worldAccess.getBlockState(blockPos2), worldAccess, blockPos, blockPos2);
            }
        }
        ,
        CHEST(new Block[]{Blocks.CHEST, Blocks.TRAPPED_CHEST}){

            public BlockState getUpdatedState(BlockState blockState3, Direction direction, BlockState blockState2, WorldAccess worldAccess, BlockPos blockPos, BlockPos blockPos2) {
                BlockState blockState3;
                if (blockState2.isOf(blockState3.getBlock()) && direction.getAxis().isHorizontal() && blockState3.get(ChestBlock.CHEST_TYPE) == ChestType.SINGLE && blockState2.get(ChestBlock.CHEST_TYPE) == ChestType.SINGLE) {
                    Direction direction2 = blockState3.get(ChestBlock.FACING);
                    if (direction.getAxis() != direction2.getAxis() && direction2 == blockState2.get(ChestBlock.FACING)) {
                        ChestType chestType = direction == direction2.rotateYClockwise() ? ChestType.LEFT : ChestType.RIGHT;
                        worldAccess.setBlockState(blockPos2, (BlockState)blockState2.with(ChestBlock.CHEST_TYPE, chestType.getOpposite()), 18);
                        if (direction2 == Direction.NORTH || direction2 == Direction.EAST) {
                            BlockEntity blockEntity = worldAccess.getBlockEntity(blockPos);
                            _snowman = worldAccess.getBlockEntity(blockPos2);
                            if (blockEntity instanceof ChestBlockEntity && _snowman instanceof ChestBlockEntity) {
                                ChestBlockEntity.copyInventory((ChestBlockEntity)blockEntity, (ChestBlockEntity)_snowman);
                            }
                        }
                        return (BlockState)blockState3.with(ChestBlock.CHEST_TYPE, chestType);
                    }
                }
                return blockState3;
            }
        }
        ,
        LEAVES(true, new Block[]{Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES}){
            private final ThreadLocal<List<ObjectSet<BlockPos>>> distanceToPositions = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity((int)7));

            @Override
            public BlockState getUpdatedState(BlockState blockState, Direction direction, BlockState blockState2, WorldAccess worldAccess, BlockPos blockPos, BlockPos blockPos2) {
                BlockState blockState3 = blockState.getStateForNeighborUpdate(direction, worldAccess.getBlockState(blockPos2), worldAccess, blockPos, blockPos2);
                if (blockState != blockState3) {
                    int n = blockState3.get(Properties.DISTANCE_1_7);
                    List<ObjectSet<BlockPos>> _snowman2 = this.distanceToPositions.get();
                    if (_snowman2.isEmpty()) {
                        for (_snowman = 0; _snowman < 7; ++_snowman) {
                            _snowman2.add((ObjectSet<BlockPos>)new ObjectOpenHashSet());
                        }
                    }
                    _snowman2.get(n).add((Object)blockPos.toImmutable());
                }
                return blockState;
            }

            @Override
            public void postUpdate(WorldAccess world) {
                BlockPos.Mutable mutable = new BlockPos.Mutable();
                List<ObjectSet<BlockPos>> _snowman2 = this.distanceToPositions.get();
                for (int i = 2; i < _snowman2.size(); ++i) {
                    _snowman = i - 1;
                    ObjectSet<BlockPos> objectSet = _snowman2.get(_snowman);
                    _snowman = _snowman2.get(i);
                    for (BlockPos blockPos : objectSet) {
                        BlockState blockState = world.getBlockState(blockPos);
                        if (blockState.get(Properties.DISTANCE_1_7) < _snowman) continue;
                        world.setBlockState(blockPos, (BlockState)blockState.with(Properties.DISTANCE_1_7, _snowman), 18);
                        if (i == 7) continue;
                        for (Direction direction : DIRECTIONS) {
                            mutable.set(blockPos, direction);
                            BlockState blockState2 = world.getBlockState(mutable);
                            if (!blockState2.contains(Properties.DISTANCE_1_7) || blockState.get(Properties.DISTANCE_1_7) <= i) continue;
                            _snowman.add((Object)mutable.toImmutable());
                        }
                    }
                }
                _snowman2.clear();
            }
        }
        ,
        STEM_BLOCK(new Block[]{Blocks.MELON_STEM, Blocks.PUMPKIN_STEM}){

            public BlockState getUpdatedState(BlockState blockState, Direction direction, BlockState blockState2, WorldAccess worldAccess, BlockPos blockPos, BlockPos blockPos2) {
                if (blockState.get(StemBlock.AGE) == 7 && blockState2.isOf(_snowman = ((StemBlock)blockState.getBlock()).getGourdBlock())) {
                    return (BlockState)_snowman.getAttachedStem().getDefaultState().with(HorizontalFacingBlock.FACING, direction);
                }
                return blockState;
            }
        };

        public static final Direction[] DIRECTIONS;

        private BuiltinLogic(Block ... blocks) {
            this(false, blocks);
        }

        private BuiltinLogic(boolean bl2, Block ... blockArray) {
            boolean bl2;
            for (Block block : blockArray) {
                BLOCK_TO_LOGIC.put(block, this);
            }
            if (bl2) {
                CALLBACK_LOGICS.add(this);
            }
        }

        static {
            DIRECTIONS = Direction.values();
        }
    }

    public static interface Logic {
        public BlockState getUpdatedState(BlockState var1, Direction var2, BlockState var3, WorldAccess var4, BlockPos var5, BlockPos var6);

        default public void postUpdate(WorldAccess world) {
        }
    }
}

