/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  javax.annotation.Nullable
 */
package net.minecraft.structure;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public abstract class StructurePiece {
    protected static final BlockState AIR = Blocks.CAVE_AIR.getDefaultState();
    protected BlockBox boundingBox;
    @Nullable
    private Direction facing;
    private BlockMirror mirror;
    private BlockRotation rotation;
    protected int chainLength;
    private final StructurePieceType type;
    private static final Set<Block> BLOCKS_NEEDING_POST_PROCESSING = ImmutableSet.builder().add((Object)Blocks.NETHER_BRICK_FENCE).add((Object)Blocks.TORCH).add((Object)Blocks.WALL_TORCH).add((Object)Blocks.OAK_FENCE).add((Object)Blocks.SPRUCE_FENCE).add((Object)Blocks.DARK_OAK_FENCE).add((Object)Blocks.ACACIA_FENCE).add((Object)Blocks.BIRCH_FENCE).add((Object)Blocks.JUNGLE_FENCE).add((Object)Blocks.LADDER).add((Object)Blocks.IRON_BARS).build();

    protected StructurePiece(StructurePieceType type, int length) {
        this.type = type;
        this.chainLength = length;
    }

    public StructurePiece(StructurePieceType type, CompoundTag tag) {
        this(type, tag.getInt("GD"));
        int n;
        if (tag.contains("BB")) {
            this.boundingBox = new BlockBox(tag.getIntArray("BB"));
        }
        this.setOrientation((n = tag.getInt("O")) == -1 ? null : Direction.fromHorizontal(n));
    }

    public final CompoundTag getTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("id", Registry.STRUCTURE_PIECE.getId(this.getType()).toString());
        compoundTag.put("BB", this.boundingBox.toNbt());
        Direction _snowman2 = this.getFacing();
        compoundTag.putInt("O", _snowman2 == null ? -1 : _snowman2.getHorizontal());
        compoundTag.putInt("GD", this.chainLength);
        this.toNbt(compoundTag);
        return compoundTag;
    }

    protected abstract void toNbt(CompoundTag var1);

    public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
    }

    public abstract boolean generate(StructureWorldAccess var1, StructureAccessor var2, ChunkGenerator var3, Random var4, BlockBox var5, ChunkPos var6, BlockPos var7);

    public BlockBox getBoundingBox() {
        return this.boundingBox;
    }

    public int getChainLength() {
        return this.chainLength;
    }

    public boolean intersectsChunk(ChunkPos chunkPos, int offset) {
        int n = chunkPos.x << 4;
        _snowman = chunkPos.z << 4;
        return this.boundingBox.intersectsXZ(n - offset, _snowman - offset, n + 15 + offset, _snowman + 15 + offset);
    }

    public static StructurePiece getOverlappingPiece(List<StructurePiece> pieces, BlockBox blockBox) {
        for (StructurePiece structurePiece : pieces) {
            if (structurePiece.getBoundingBox() == null || !structurePiece.getBoundingBox().intersects(blockBox)) continue;
            return structurePiece;
        }
        return null;
    }

    protected boolean isTouchingLiquid(BlockView blockView, BlockBox blockBox) {
        int n = Math.max(this.boundingBox.minX - 1, blockBox.minX);
        _snowman = Math.max(this.boundingBox.minY - 1, blockBox.minY);
        _snowman = Math.max(this.boundingBox.minZ - 1, blockBox.minZ);
        _snowman = Math.min(this.boundingBox.maxX + 1, blockBox.maxX);
        _snowman = Math.min(this.boundingBox.maxY + 1, blockBox.maxY);
        _snowman = Math.min(this.boundingBox.maxZ + 1, blockBox.maxZ);
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        for (_snowman = n; _snowman <= _snowman; ++_snowman) {
            for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                if (blockView.getBlockState(_snowman2.set(_snowman, _snowman, _snowman)).getMaterial().isLiquid()) {
                    return true;
                }
                if (!blockView.getBlockState(_snowman2.set(_snowman, _snowman, _snowman)).getMaterial().isLiquid()) continue;
                return true;
            }
        }
        for (_snowman = n; _snowman <= _snowman; ++_snowman) {
            for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                if (blockView.getBlockState(_snowman2.set(_snowman, _snowman, _snowman)).getMaterial().isLiquid()) {
                    return true;
                }
                if (!blockView.getBlockState(_snowman2.set(_snowman, _snowman, _snowman)).getMaterial().isLiquid()) continue;
                return true;
            }
        }
        for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
            for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                if (blockView.getBlockState(_snowman2.set(n, _snowman, _snowman)).getMaterial().isLiquid()) {
                    return true;
                }
                if (!blockView.getBlockState(_snowman2.set(_snowman, _snowman, _snowman)).getMaterial().isLiquid()) continue;
                return true;
            }
        }
        return false;
    }

    protected int applyXTransform(int x, int z) {
        Direction direction = this.getFacing();
        if (direction == null) {
            return x;
        }
        switch (direction) {
            case NORTH: 
            case SOUTH: {
                return this.boundingBox.minX + x;
            }
            case WEST: {
                return this.boundingBox.maxX - z;
            }
            case EAST: {
                return this.boundingBox.minX + z;
            }
        }
        return x;
    }

    protected int applyYTransform(int y) {
        if (this.getFacing() == null) {
            return y;
        }
        return y + this.boundingBox.minY;
    }

    protected int applyZTransform(int x, int z) {
        Direction direction = this.getFacing();
        if (direction == null) {
            return z;
        }
        switch (direction) {
            case NORTH: {
                return this.boundingBox.maxZ - z;
            }
            case SOUTH: {
                return this.boundingBox.minZ + z;
            }
            case WEST: 
            case EAST: {
                return this.boundingBox.minZ + x;
            }
        }
        return z;
    }

    protected void addBlock(StructureWorldAccess structureWorldAccess, BlockState block, int x, int y, int z, BlockBox blockBox) {
        BlockPos blockPos = new BlockPos(this.applyXTransform(x, z), this.applyYTransform(y), this.applyZTransform(x, z));
        if (!blockBox.contains(blockPos)) {
            return;
        }
        if (this.mirror != BlockMirror.NONE) {
            block = block.mirror(this.mirror);
        }
        if (this.rotation != BlockRotation.NONE) {
            block = block.rotate(this.rotation);
        }
        structureWorldAccess.setBlockState(blockPos, block, 2);
        FluidState _snowman2 = structureWorldAccess.getFluidState(blockPos);
        if (!_snowman2.isEmpty()) {
            structureWorldAccess.getFluidTickScheduler().schedule(blockPos, _snowman2.getFluid(), 0);
        }
        if (BLOCKS_NEEDING_POST_PROCESSING.contains(block.getBlock())) {
            structureWorldAccess.getChunk(blockPos).markBlockForPostProcessing(blockPos);
        }
    }

    protected BlockState getBlockAt(BlockView blockView, int x, int y, int z, BlockBox blockBox) {
        int n = this.applyXTransform(x, z);
        BlockPos _snowman2 = new BlockPos(n, _snowman = this.applyYTransform(y), _snowman = this.applyZTransform(x, z));
        if (!blockBox.contains(_snowman2)) {
            return Blocks.AIR.getDefaultState();
        }
        return blockView.getBlockState(_snowman2);
    }

    protected boolean isUnderSeaLevel(WorldView worldView, int x, int z, int y, BlockBox blockBox) {
        int n = this.applyXTransform(x, y);
        BlockPos _snowman2 = new BlockPos(n, _snowman = this.applyYTransform(z + 1), _snowman = this.applyZTransform(x, y));
        if (!blockBox.contains(_snowman2)) {
            return false;
        }
        return _snowman < worldView.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, n, _snowman);
    }

    protected void fill(StructureWorldAccess structureWorldAccess, BlockBox bounds, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        for (int i = minY; i <= maxY; ++i) {
            for (_snowman = minX; _snowman <= maxX; ++_snowman) {
                for (_snowman = minZ; _snowman <= maxZ; ++_snowman) {
                    this.addBlock(structureWorldAccess, Blocks.AIR.getDefaultState(), _snowman, i, _snowman, bounds);
                }
            }
        }
    }

    protected void fillWithOutline(StructureWorldAccess structureWorldAccess, BlockBox blockBox, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState outline, BlockState inside, boolean cantReplaceAir) {
        for (int i = minY; i <= maxY; ++i) {
            for (_snowman = minX; _snowman <= maxX; ++_snowman) {
                for (_snowman = minZ; _snowman <= maxZ; ++_snowman) {
                    if (cantReplaceAir && this.getBlockAt(structureWorldAccess, _snowman, i, _snowman, blockBox).isAir()) continue;
                    if (i == minY || i == maxY || _snowman == minX || _snowman == maxX || _snowman == minZ || _snowman == maxZ) {
                        this.addBlock(structureWorldAccess, outline, _snowman, i, _snowman, blockBox);
                        continue;
                    }
                    this.addBlock(structureWorldAccess, inside, _snowman, i, _snowman, blockBox);
                }
            }
        }
    }

    protected void fillWithOutline(StructureWorldAccess structureWorldAccess, BlockBox blockBox, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean cantReplaceAir, Random random, BlockRandomizer blockRandomizer) {
        for (int i = minY; i <= maxY; ++i) {
            for (_snowman = minX; _snowman <= maxX; ++_snowman) {
                for (_snowman = minZ; _snowman <= maxZ; ++_snowman) {
                    if (cantReplaceAir && this.getBlockAt(structureWorldAccess, _snowman, i, _snowman, blockBox).isAir()) continue;
                    blockRandomizer.setBlock(random, _snowman, i, _snowman, i == minY || i == maxY || _snowman == minX || _snowman == maxX || _snowman == minZ || _snowman == maxZ);
                    this.addBlock(structureWorldAccess, blockRandomizer.getBlock(), _snowman, i, _snowman, blockBox);
                }
            }
        }
    }

    protected void fillWithOutlineUnderSeaLevel(StructureWorldAccess structureWorldAccess, BlockBox blockBox, Random random, float blockChance, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState outline, BlockState inside, boolean cantReplaceAir, boolean stayBelowSeaLevel) {
        for (int i = minY; i <= maxY; ++i) {
            for (_snowman = minX; _snowman <= maxX; ++_snowman) {
                for (_snowman = minZ; _snowman <= maxZ; ++_snowman) {
                    if (random.nextFloat() > blockChance || cantReplaceAir && this.getBlockAt(structureWorldAccess, _snowman, i, _snowman, blockBox).isAir() || stayBelowSeaLevel && !this.isUnderSeaLevel(structureWorldAccess, _snowman, i, _snowman, blockBox)) continue;
                    if (i == minY || i == maxY || _snowman == minX || _snowman == maxX || _snowman == minZ || _snowman == maxZ) {
                        this.addBlock(structureWorldAccess, outline, _snowman, i, _snowman, blockBox);
                        continue;
                    }
                    this.addBlock(structureWorldAccess, inside, _snowman, i, _snowman, blockBox);
                }
            }
        }
    }

    protected void addBlockWithRandomThreshold(StructureWorldAccess structureWorldAccess, BlockBox bounds, Random random, float threshold, int x, int y, int z, BlockState blockState) {
        if (random.nextFloat() < threshold) {
            this.addBlock(structureWorldAccess, blockState, x, y, z, bounds);
        }
    }

    protected void fillHalfEllipsoid(StructureWorldAccess structureWorldAccess, BlockBox bounds, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState block, boolean cantReplaceAir) {
        float f = maxX - minX + 1;
        _snowman = maxY - minY + 1;
        _snowman = maxZ - minZ + 1;
        _snowman = (float)minX + f / 2.0f;
        _snowman = (float)minZ + _snowman / 2.0f;
        for (int i = minY; i <= maxY; ++i) {
            float f2 = (float)(i - minY) / _snowman;
            for (int j = minX; j <= maxX; ++j) {
                float f3 = ((float)j - _snowman) / (f * 0.5f);
                for (int k = minZ; k <= maxZ; ++k) {
                    float f4 = ((float)k - _snowman) / (_snowman * 0.5f);
                    if (cantReplaceAir && this.getBlockAt(structureWorldAccess, j, i, k, bounds).isAir() || !((_snowman = f3 * f3 + f2 * f2 + f4 * f4) <= 1.05f)) continue;
                    this.addBlock(structureWorldAccess, block, j, i, k, bounds);
                }
            }
        }
    }

    protected void fillDownwards(StructureWorldAccess structureWorldAccess, BlockState blockState, int x, int y, int z, BlockBox blockBox) {
        int n = this.applyXTransform(x, z);
        if (!blockBox.contains(new BlockPos(n, _snowman = this.applyYTransform(y), _snowman = this.applyZTransform(x, z)))) {
            return;
        }
        while ((structureWorldAccess.isAir(new BlockPos(n, _snowman, _snowman)) || structureWorldAccess.getBlockState(new BlockPos(n, _snowman, _snowman)).getMaterial().isLiquid()) && _snowman > 1) {
            structureWorldAccess.setBlockState(new BlockPos(n, _snowman, _snowman), blockState, 2);
            --_snowman;
        }
    }

    protected boolean addChest(StructureWorldAccess structureWorldAccess, BlockBox boundingBox, Random random, int x, int y, int z, Identifier lootTableId) {
        BlockPos blockPos = new BlockPos(this.applyXTransform(x, z), this.applyYTransform(y), this.applyZTransform(x, z));
        return this.addChest(structureWorldAccess, boundingBox, random, blockPos, lootTableId, null);
    }

    public static BlockState orientateChest(BlockView blockView, BlockPos blockPos, BlockState blockState) {
        Object object = null;
        for (Object object2 : Direction.Type.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.offset((Direction)object2);
            BlockState _snowman2 = blockView.getBlockState(blockPos2);
            if (_snowman2.isOf(Blocks.CHEST)) {
                return blockState;
            }
            if (!_snowman2.isOpaqueFullCube(blockView, blockPos2)) continue;
            if (object == null) {
                object = object2;
                continue;
            }
            object = null;
            break;
        }
        if (object != null) {
            return (BlockState)blockState.with(HorizontalFacingBlock.FACING, ((Direction)object).getOpposite());
        }
        Direction _snowman3 = blockState.get(HorizontalFacingBlock.FACING);
        object2 = blockPos.offset(_snowman3);
        if (blockView.getBlockState((BlockPos)object2).isOpaqueFullCube(blockView, (BlockPos)object2)) {
            _snowman3 = _snowman3.getOpposite();
            object2 = blockPos.offset(_snowman3);
        }
        if (blockView.getBlockState((BlockPos)object2).isOpaqueFullCube(blockView, (BlockPos)object2)) {
            _snowman3 = _snowman3.rotateYClockwise();
            object2 = blockPos.offset(_snowman3);
        }
        if (blockView.getBlockState((BlockPos)object2).isOpaqueFullCube(blockView, (BlockPos)object2)) {
            _snowman3 = _snowman3.getOpposite();
            object2 = blockPos.offset(_snowman3);
        }
        return (BlockState)blockState.with(HorizontalFacingBlock.FACING, _snowman3);
    }

    protected boolean addChest(ServerWorldAccess serverWorldAccess, BlockBox boundingBox, Random random, BlockPos pos, Identifier lootTableId, @Nullable BlockState block) {
        if (!boundingBox.contains(pos) || serverWorldAccess.getBlockState(pos).isOf(Blocks.CHEST)) {
            return false;
        }
        if (block == null) {
            block = StructurePiece.orientateChest(serverWorldAccess, pos, Blocks.CHEST.getDefaultState());
        }
        serverWorldAccess.setBlockState(pos, block, 2);
        BlockEntity blockEntity = serverWorldAccess.getBlockEntity(pos);
        if (blockEntity instanceof ChestBlockEntity) {
            ((ChestBlockEntity)blockEntity).setLootTable(lootTableId, random.nextLong());
        }
        return true;
    }

    protected boolean addDispenser(StructureWorldAccess structureWorldAccess, BlockBox boundingBox, Random random, int x, int y, int z, Direction facing, Identifier lootTableId) {
        BlockPos blockPos = new BlockPos(this.applyXTransform(x, z), this.applyYTransform(y), this.applyZTransform(x, z));
        if (boundingBox.contains(blockPos) && !structureWorldAccess.getBlockState(blockPos).isOf(Blocks.DISPENSER)) {
            this.addBlock(structureWorldAccess, (BlockState)Blocks.DISPENSER.getDefaultState().with(DispenserBlock.FACING, facing), x, y, z, boundingBox);
            BlockEntity blockEntity = structureWorldAccess.getBlockEntity(blockPos);
            if (blockEntity instanceof DispenserBlockEntity) {
                ((DispenserBlockEntity)blockEntity).setLootTable(lootTableId, random.nextLong());
            }
            return true;
        }
        return false;
    }

    public void translate(int x, int y, int z) {
        this.boundingBox.move(x, y, z);
    }

    @Nullable
    public Direction getFacing() {
        return this.facing;
    }

    public void setOrientation(@Nullable Direction orientation) {
        this.facing = orientation;
        if (orientation == null) {
            this.rotation = BlockRotation.NONE;
            this.mirror = BlockMirror.NONE;
        } else {
            switch (orientation) {
                case SOUTH: {
                    this.mirror = BlockMirror.LEFT_RIGHT;
                    this.rotation = BlockRotation.NONE;
                    break;
                }
                case WEST: {
                    this.mirror = BlockMirror.LEFT_RIGHT;
                    this.rotation = BlockRotation.CLOCKWISE_90;
                    break;
                }
                case EAST: {
                    this.mirror = BlockMirror.NONE;
                    this.rotation = BlockRotation.CLOCKWISE_90;
                    break;
                }
                default: {
                    this.mirror = BlockMirror.NONE;
                    this.rotation = BlockRotation.NONE;
                }
            }
        }
    }

    public BlockRotation getRotation() {
        return this.rotation;
    }

    public StructurePieceType getType() {
        return this.type;
    }

    public static abstract class BlockRandomizer {
        protected BlockState block = Blocks.AIR.getDefaultState();

        protected BlockRandomizer() {
        }

        public abstract void setBlock(Random var1, int var2, int var3, int var4, boolean var5);

        public BlockState getBlock() {
            return this.block;
        }
    }
}

