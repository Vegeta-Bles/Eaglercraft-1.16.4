/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class NetherFortressGenerator {
    private static final PieceData[] ALL_BRIDGE_PIECES = new PieceData[]{new PieceData(Bridge.class, 30, 0, true), new PieceData(BridgeCrossing.class, 10, 4), new PieceData(BridgeSmallCrossing.class, 10, 4), new PieceData(BridgeStairs.class, 10, 3), new PieceData(BridgePlatform.class, 5, 2), new PieceData(CorridorExit.class, 5, 1)};
    private static final PieceData[] ALL_CORRIDOR_PIECES = new PieceData[]{new PieceData(SmallCorridor.class, 25, 0, true), new PieceData(CorridorCrossing.class, 15, 5), new PieceData(CorridorRightTurn.class, 5, 10), new PieceData(CorridorLeftTurn.class, 5, 10), new PieceData(CorridorStairs.class, 10, 3, true), new PieceData(CorridorBalcony.class, 7, 2), new PieceData(CorridorNetherWartsRoom.class, 5, 2)};

    private static Piece createPiece(PieceData pieceData, List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
        Class<? extends Piece> clazz = pieceData.pieceType;
        Piece _snowman2 = null;
        if (clazz == Bridge.class) {
            _snowman2 = Bridge.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (clazz == BridgeCrossing.class) {
            _snowman2 = BridgeCrossing.create(pieces, x, y, z, orientation, chainLength);
        } else if (clazz == BridgeSmallCrossing.class) {
            _snowman2 = BridgeSmallCrossing.create(pieces, x, y, z, orientation, chainLength);
        } else if (clazz == BridgeStairs.class) {
            _snowman2 = BridgeStairs.create(pieces, x, y, z, chainLength, orientation);
        } else if (clazz == BridgePlatform.class) {
            _snowman2 = BridgePlatform.create(pieces, x, y, z, chainLength, orientation);
        } else if (clazz == CorridorExit.class) {
            _snowman2 = CorridorExit.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (clazz == SmallCorridor.class) {
            _snowman2 = SmallCorridor.create(pieces, x, y, z, orientation, chainLength);
        } else if (clazz == CorridorRightTurn.class) {
            _snowman2 = CorridorRightTurn.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (clazz == CorridorLeftTurn.class) {
            _snowman2 = CorridorLeftTurn.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (clazz == CorridorStairs.class) {
            _snowman2 = CorridorStairs.create(pieces, x, y, z, orientation, chainLength);
        } else if (clazz == CorridorBalcony.class) {
            _snowman2 = CorridorBalcony.create(pieces, x, y, z, orientation, chainLength);
        } else if (clazz == CorridorCrossing.class) {
            _snowman2 = CorridorCrossing.create(pieces, x, y, z, orientation, chainLength);
        } else if (clazz == CorridorNetherWartsRoom.class) {
            _snowman2 = CorridorNetherWartsRoom.create(pieces, x, y, z, orientation, chainLength);
        }
        return _snowman2;
    }

    public static class CorridorBalcony
    extends Piece {
        public CorridorBalcony(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_BALCONY, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public CorridorBalcony(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_BALCONY, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            int n = 1;
            Direction _snowman2 = this.getFacing();
            if (_snowman2 == Direction.WEST || _snowman2 == Direction.NORTH) {
                n = 5;
            }
            this.fillNWOpening((Start)start, pieces, random, 0, n, random.nextInt(8) > 0);
            this.fillSEOpening((Start)start, pieces, random, 0, n, random.nextInt(8) > 0);
        }

        public static CorridorBalcony create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -3, 0, 0, 9, 7, 9, orientation);
            if (!CorridorBalcony.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new CorridorBalcony(chainLength, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            _snowman = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 8, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 8, 5, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 6, 0, 8, 6, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 2, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 2, 0, 8, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 0, 1, 4, 0, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 7, 3, 0, 7, 4, 0, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 4, 8, 2, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 4, 2, 2, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 4, 7, 2, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 8, 7, 3, 8, _snowman, _snowman, false);
            this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true)).with(FenceBlock.SOUTH, true), 0, 3, 8, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.SOUTH, true), 8, 3, 8, boundingBox);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 6, 0, 3, 7, blockState, blockState, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 8, 3, 6, 8, 3, 7, blockState, blockState, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 4, 0, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 8, 3, 4, 8, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 5, 2, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 3, 5, 7, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 4, 5, 1, 5, 5, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 7, 4, 5, 7, 5, 5, _snowman, _snowman, false);
            for (int i = 0; i <= 5; ++i) {
                for (_snowman = 0; _snowman <= 8; ++_snowman) {
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), _snowman, -1, i, boundingBox);
                }
            }
            return true;
        }
    }

    public static class CorridorStairs
    extends Piece {
        public CorridorStairs(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_STAIRS, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public CorridorStairs(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_STAIRS, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 1, 0, true);
        }

        public static CorridorStairs create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, -7, 0, 5, 14, 10, orientation);
            if (!CorridorStairs.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new CorridorStairs(chainLength, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            BlockState blockState = (BlockState)Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
            _snowman = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            for (int i = 0; i <= 9; ++i) {
                _snowman = Math.max(1, 7 - i);
                _snowman = Math.min(Math.max(_snowman + 5, 14 - i), 13);
                _snowman = i;
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, _snowman, 4, _snowman, _snowman, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, _snowman + 1, _snowman, 3, _snowman - 1, _snowman, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                if (i <= 6) {
                    this.addBlock(structureWorldAccess, blockState, 1, _snowman + 1, _snowman, boundingBox);
                    this.addBlock(structureWorldAccess, blockState, 2, _snowman + 1, _snowman, boundingBox);
                    this.addBlock(structureWorldAccess, blockState, 3, _snowman + 1, _snowman, boundingBox);
                }
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, _snowman, _snowman, 4, _snowman, _snowman, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, _snowman + 1, _snowman, 0, _snowman - 1, _snowman, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 4, _snowman + 1, _snowman, 4, _snowman - 1, _snowman, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
                if ((i & 1) == 0) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, _snowman + 2, _snowman, 0, _snowman + 3, _snowman, _snowman, _snowman, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 4, _snowman + 2, _snowman, 4, _snowman + 3, _snowman, _snowman, _snowman, false);
                }
                for (_snowman = 0; _snowman <= 4; ++_snowman) {
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), _snowman, -1, _snowman, boundingBox);
                }
            }
            return true;
        }
    }

    public static class CorridorLeftTurn
    extends Piece {
        private boolean containsChest;

        public CorridorLeftTurn(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_LEFT_TURN, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
            this.containsChest = random.nextInt(3) == 0;
        }

        public CorridorLeftTurn(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_LEFT_TURN, compoundTag);
            this.containsChest = compoundTag.getBoolean("Chest");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("Chest", this.containsChest);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillNWOpening((Start)start, pieces, random, 0, 1, true);
        }

        public static CorridorLeftTurn create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
            if (!CorridorLeftTurn.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new CorridorLeftTurn(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            _snowman = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 3, 1, 4, 4, 1, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 3, 3, 4, 4, 3, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 4, 3, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 4, 1, 4, 4, blockState, blockState, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 3, 3, 4, 3, 4, 4, blockState, blockState, false);
            if (this.containsChest && boundingBox.contains(new BlockPos(this.applyXTransform(3, 3), this.applyYTransform(2), this.applyZTransform(3, 3)))) {
                this.containsChest = false;
                this.addChest(structureWorldAccess, boundingBox, random, 3, 2, 3, LootTables.NETHER_BRIDGE_CHEST);
            }
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            for (int i = 0; i <= 4; ++i) {
                for (_snowman = 0; _snowman <= 4; ++_snowman) {
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, _snowman, boundingBox);
                }
            }
            return true;
        }
    }

    public static class CorridorRightTurn
    extends Piece {
        private boolean containsChest;

        public CorridorRightTurn(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_RIGHT_TURN, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
            this.containsChest = random.nextInt(3) == 0;
        }

        public CorridorRightTurn(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_RIGHT_TURN, compoundTag);
            this.containsChest = compoundTag.getBoolean("Chest");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("Chest", this.containsChest);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillSEOpening((Start)start, pieces, random, 0, 1, true);
        }

        public static CorridorRightTurn create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
            if (!CorridorRightTurn.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new CorridorRightTurn(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            _snowman = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 1, 0, 4, 1, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 3, 0, 4, 3, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 4, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 4, 1, 4, 4, blockState, blockState, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 3, 3, 4, 3, 4, 4, blockState, blockState, false);
            if (this.containsChest && boundingBox.contains(new BlockPos(this.applyXTransform(1, 3), this.applyYTransform(2), this.applyZTransform(1, 3)))) {
                this.containsChest = false;
                this.addChest(structureWorldAccess, boundingBox, random, 1, 2, 3, LootTables.NETHER_BRIDGE_CHEST);
            }
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            for (int i = 0; i <= 4; ++i) {
                for (_snowman = 0; _snowman <= 4; ++_snowman) {
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, _snowman, boundingBox);
                }
            }
            return true;
        }
    }

    public static class CorridorCrossing
    extends Piece {
        public CorridorCrossing(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_CROSSING, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public CorridorCrossing(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_CROSSING, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 1, 0, true);
            this.fillNWOpening((Start)start, pieces, random, 0, 1, true);
            this.fillSEOpening((Start)start, pieces, random, 0, 1, true);
        }

        public static CorridorCrossing create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
            if (!CorridorCrossing.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new CorridorCrossing(chainLength, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 4, 0, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 2, 4, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            for (int i = 0; i <= 4; ++i) {
                for (_snowman = 0; _snowman <= 4; ++_snowman) {
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, _snowman, boundingBox);
                }
            }
            return true;
        }
    }

    public static class SmallCorridor
    extends Piece {
        public SmallCorridor(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_SMALL_CORRIDOR, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public SmallCorridor(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_SMALL_CORRIDOR, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 1, 0, true);
        }

        public static SmallCorridor create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
            if (!SmallCorridor.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new SmallCorridor(chainLength, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 1, 0, 4, 1, blockState, blockState, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 3, 0, 4, 3, blockState, blockState, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 3, 1, 4, 4, 1, blockState, blockState, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 3, 3, 4, 4, 3, blockState, blockState, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            for (int i = 0; i <= 4; ++i) {
                for (_snowman = 0; _snowman <= 4; ++_snowman) {
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, _snowman, boundingBox);
                }
            }
            return true;
        }
    }

    public static class CorridorNetherWartsRoom
    extends Piece {
        public CorridorNetherWartsRoom(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_NETHER_WARTS_ROOM, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public CorridorNetherWartsRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_NETHER_WARTS_ROOM, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 5, 3, true);
            this.fillForwardOpening((Start)start, pieces, random, 5, 11, true);
        }

        public static CorridorNetherWartsRoom create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainlength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -5, -3, 0, 13, 14, 13, orientation);
            if (!CorridorNetherWartsRoom.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new CorridorNetherWartsRoom(chainlength, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            int n2;
            StructureWorldAccess structureWorldAccess2;
            int n3;
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 5, 0, 12, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            _snowman = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            _snowman = (BlockState)_snowman.with(FenceBlock.WEST, true);
            _snowman = (BlockState)_snowman.with(FenceBlock.EAST, true);
            for (n3 = 1; n3 <= 11; n3 += 2) {
                this.fillWithOutline(structureWorldAccess2, boundingBox, n3, 10, 0, n3, 11, 0, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, n3, 10, 12, n3, 11, 12, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 10, n3, 0, 11, n3, _snowman, _snowman, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 12, 10, n3, 12, 11, n3, _snowman, _snowman, false);
                this.addBlock(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n3, 13, 0, boundingBox);
                this.addBlock(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n3, 13, 12, boundingBox);
                this.addBlock(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), 0, 13, n3, boundingBox);
                this.addBlock(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), 12, 13, n3, boundingBox);
                if (n3 == 11) continue;
                this.addBlock(structureWorldAccess2, blockState, n3 + 1, 13, 0, boundingBox);
                this.addBlock(structureWorldAccess2, blockState, n3 + 1, 13, 12, boundingBox);
                this.addBlock(structureWorldAccess2, _snowman, 0, 13, n3 + 1, boundingBox);
                this.addBlock(structureWorldAccess2, _snowman, 12, 13, n3 + 1, boundingBox);
            }
            this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.EAST, true), 0, 13, 0, boundingBox);
            this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.EAST, true), 0, 13, 12, boundingBox);
            this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.WEST, true), 12, 13, 12, boundingBox);
            this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.WEST, true), 12, 13, 0, boundingBox);
            for (n3 = 3; n3 <= 9; n3 += 2) {
                this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 7, n3, 1, 8, n3, _snowman, _snowman, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 11, 7, n3, 11, 8, n3, _snowman, _snowman, false);
            }
            BlockState blockState2 = (BlockState)Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
            for (n2 = 0; n2 <= 6; ++n2) {
                _snowman = n2 + 4;
                for (n = 5; n <= 7; ++n) {
                    this.addBlock(structureWorldAccess2, blockState2, n, 5 + n2, _snowman, boundingBox);
                }
                if (_snowman >= 5 && _snowman <= 8) {
                    this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 5, _snowman, 7, n2 + 4, _snowman, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
                } else if (_snowman >= 9 && _snowman <= 10) {
                    this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 8, _snowman, 7, n2 + 4, _snowman, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
                }
                if (n2 < 1) continue;
                this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 6 + n2, _snowman, 7, 9 + n2, _snowman, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            }
            for (n2 = 5; n2 <= 7; ++n2) {
                this.addBlock(structureWorldAccess2, blockState2, n2, 12, 11, boundingBox);
            }
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 6, 7, 5, 7, 7, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 6, 7, 7, 7, 7, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 13, 12, 7, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 5, 2, 3, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 5, 9, 3, 5, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 5, 4, 2, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 9, 5, 2, 10, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 9, 5, 9, 10, 5, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 10, 5, 4, 10, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            BlockState _snowman2 = (BlockState)blockState2.with(StairsBlock.FACING, Direction.EAST);
            BlockState _snowman3 = (BlockState)blockState2.with(StairsBlock.FACING, Direction.WEST);
            this.addBlock(structureWorldAccess2, _snowman3, 4, 5, 2, boundingBox);
            this.addBlock(structureWorldAccess2, _snowman3, 4, 5, 3, boundingBox);
            this.addBlock(structureWorldAccess2, _snowman3, 4, 5, 9, boundingBox);
            this.addBlock(structureWorldAccess2, _snowman3, 4, 5, 10, boundingBox);
            this.addBlock(structureWorldAccess2, _snowman2, 8, 5, 2, boundingBox);
            this.addBlock(structureWorldAccess2, _snowman2, 8, 5, 3, boundingBox);
            this.addBlock(structureWorldAccess2, _snowman2, 8, 5, 9, boundingBox);
            this.addBlock(structureWorldAccess2, _snowman2, 8, 5, 10, boundingBox);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND.getDefaultState(), Blocks.SOUL_SAND.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND.getDefaultState(), Blocks.SOUL_SAND.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART.getDefaultState(), Blocks.NETHER_WART.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART.getDefaultState(), Blocks.NETHER_WART.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            for (n = 4; n <= 8; ++n) {
                for (_snowman = 0; _snowman <= 2; ++_snowman) {
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, -1, _snowman, boundingBox);
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, -1, 12 - _snowman, boundingBox);
                }
            }
            for (n = 0; n <= 2; ++n) {
                for (_snowman = 4; _snowman <= 8; ++_snowman) {
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, -1, _snowman, boundingBox);
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), 12 - n, -1, _snowman, boundingBox);
                }
            }
            return true;
        }
    }

    public static class CorridorExit
    extends Piece {
        public CorridorExit(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_EXIT, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public CorridorExit(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_EXIT, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 5, 3, true);
        }

        public static CorridorExit create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -5, -3, 0, 13, 14, 13, orientation);
            if (!CorridorExit.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new CorridorExit(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            StructureWorldAccess structureWorldAccess2;
            int n;
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 5, 0, 12, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 8, 0, 7, 8, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            _snowman = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            for (n = 1; n <= 11; n += 2) {
                this.fillWithOutline(structureWorldAccess2, boundingBox, n, 10, 0, n, 11, 0, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, n, 10, 12, n, 11, 12, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 10, n, 0, 11, n, _snowman, _snowman, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 12, 10, n, 12, 11, n, _snowman, _snowman, false);
                this.addBlock(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, 13, 0, boundingBox);
                this.addBlock(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, 13, 12, boundingBox);
                this.addBlock(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), 0, 13, n, boundingBox);
                this.addBlock(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), 12, 13, n, boundingBox);
                if (n == 11) continue;
                this.addBlock(structureWorldAccess2, blockState, n + 1, 13, 0, boundingBox);
                this.addBlock(structureWorldAccess2, blockState, n + 1, 13, 12, boundingBox);
                this.addBlock(structureWorldAccess2, _snowman, 0, 13, n + 1, boundingBox);
                this.addBlock(structureWorldAccess2, _snowman, 12, 13, n + 1, boundingBox);
            }
            this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.EAST, true), 0, 13, 0, boundingBox);
            this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.EAST, true), 0, 13, 12, boundingBox);
            this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.WEST, true), 12, 13, 12, boundingBox);
            this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.WEST, true), 12, 13, 0, boundingBox);
            for (n = 3; n <= 9; n += 2) {
                this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 7, n, 1, 8, n, (BlockState)_snowman.with(FenceBlock.WEST, true), (BlockState)_snowman.with(FenceBlock.WEST, true), false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 11, 7, n, 11, 8, n, (BlockState)_snowman.with(FenceBlock.EAST, true), (BlockState)_snowman.with(FenceBlock.EAST, true), false);
            }
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            for (n = 4; n <= 8; ++n) {
                for (_snowman = 0; _snowman <= 2; ++_snowman) {
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, -1, _snowman, boundingBox);
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, -1, 12 - _snowman, boundingBox);
                }
            }
            for (n = 0; n <= 2; ++n) {
                for (_snowman = 4; _snowman <= 8; ++_snowman) {
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, -1, _snowman, boundingBox);
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), 12 - n, -1, _snowman, boundingBox);
                }
            }
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 5, 5, 7, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 6, 1, 6, 6, 4, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.addBlock(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), 6, 0, 6, boundingBox);
            this.addBlock(structureWorldAccess2, Blocks.LAVA.getDefaultState(), 6, 5, 6, boundingBox);
            BlockPos _snowman2 = new BlockPos(this.applyXTransform(6, 6), this.applyYTransform(5), this.applyZTransform(6, 6));
            if (boundingBox.contains(_snowman2)) {
                structureWorldAccess2.getFluidTickScheduler().schedule(_snowman2, Fluids.LAVA, 0);
            }
            return true;
        }
    }

    public static class BridgePlatform
    extends Piece {
        private boolean hasBlazeSpawner;

        public BridgePlatform(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE_PLATFORM, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public BridgePlatform(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE_PLATFORM, compoundTag);
            this.hasBlazeSpawner = compoundTag.getBoolean("Mob");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("Mob", this.hasBlazeSpawner);
        }

        public static BridgePlatform create(List<StructurePiece> pieces, int x, int y, int z, int chainLength, Direction orientation) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -2, 0, 0, 7, 8, 9, orientation);
            if (!BridgePlatform.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new BridgePlatform(chainLength, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 6, 7, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 0, 0, 5, 1, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 1, 5, 2, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 2, 5, 3, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 4, 3, 5, 4, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 0, 1, 4, 2, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 2, 0, 5, 4, 2, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 5, 2, 1, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 5, 2, 5, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 5, 3, 0, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 5, 3, 6, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 5, 8, 5, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            _snowman = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.addBlock(structureWorldAccess, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true), 1, 6, 3, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true), 5, 6, 3, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true)).with(FenceBlock.NORTH, true), 0, 6, 3, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.NORTH, true), 6, 6, 3, boundingBox);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 6, 4, 0, 6, 7, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 6, 4, 6, 6, 7, _snowman, _snowman, false);
            this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true)).with(FenceBlock.SOUTH, true), 0, 6, 8, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.SOUTH, true), 6, 6, 8, boundingBox);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 6, 8, 5, 6, 8, blockState, blockState, false);
            this.addBlock(structureWorldAccess, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true), 1, 7, 8, boundingBox);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 7, 8, 4, 7, 8, blockState, blockState, false);
            this.addBlock(structureWorldAccess, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true), 5, 7, 8, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true), 2, 8, 8, boundingBox);
            this.addBlock(structureWorldAccess, blockState, 3, 8, 8, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true), 4, 8, 8, boundingBox);
            if (!this.hasBlazeSpawner && boundingBox.contains(_snowman = new BlockPos(this.applyXTransform(3, 5), this.applyYTransform(5), this.applyZTransform(3, 5)))) {
                this.hasBlazeSpawner = true;
                structureWorldAccess.setBlockState(_snowman, Blocks.SPAWNER.getDefaultState(), 2);
                BlockEntity blockEntity = structureWorldAccess.getBlockEntity(_snowman);
                if (blockEntity instanceof MobSpawnerBlockEntity) {
                    ((MobSpawnerBlockEntity)blockEntity).getLogic().setEntityId(EntityType.BLAZE);
                }
            }
            for (int i = 0; i <= 6; ++i) {
                for (_snowman = 0; _snowman <= 6; ++_snowman) {
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, _snowman, boundingBox);
                }
            }
            return true;
        }
    }

    public static class BridgeStairs
    extends Piece {
        public BridgeStairs(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE_STAIRS, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public BridgeStairs(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE_STAIRS, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillSEOpening((Start)start, pieces, random, 6, 2, false);
        }

        public static BridgeStairs create(List<StructurePiece> pieces, int x, int y, int z, int chainlength, Direction orientation) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -2, 0, 0, 7, 11, 7, orientation);
            if (!BridgeStairs.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new BridgeStairs(chainlength, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 6, 10, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 1, 8, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 2, 0, 6, 8, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 1, 0, 8, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 2, 1, 6, 8, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 6, 5, 8, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            _snowman = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 2, 0, 5, 4, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 3, 2, 6, 5, 2, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 3, 4, 6, 5, 4, _snowman, _snowman, false);
            this.addBlock(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), 5, 2, 5, boundingBox);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 2, 5, 4, 3, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 3, 2, 5, 3, 4, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 2, 5, 2, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 5, 1, 6, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 7, 1, 5, 7, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 8, 2, 6, 8, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 6, 0, 4, 8, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 5, 0, 4, 5, 0, blockState, blockState, false);
            for (int i = 0; i <= 6; ++i) {
                for (_snowman = 0; _snowman <= 6; ++_snowman) {
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, _snowman, boundingBox);
                }
            }
            return true;
        }
    }

    public static class BridgeSmallCrossing
    extends Piece {
        public BridgeSmallCrossing(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE_SMALL_CROSSING, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public BridgeSmallCrossing(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE_SMALL_CROSSING, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 2, 0, false);
            this.fillNWOpening((Start)start, pieces, random, 0, 2, false);
            this.fillSEOpening((Start)start, pieces, random, 0, 2, false);
        }

        public static BridgeSmallCrossing create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -2, 0, 0, 7, 9, 7, orientation);
            if (!BridgeSmallCrossing.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new BridgeSmallCrossing(chainLength, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 6, 7, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 1, 6, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 6, 1, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 2, 0, 6, 6, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 2, 6, 6, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 0, 6, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 5, 0, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 2, 0, 6, 6, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 2, 5, 6, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            _snowman = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 6, 0, 4, 6, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 5, 0, 4, 5, 0, blockState, blockState, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 6, 6, 4, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 5, 6, 4, 5, 6, blockState, blockState, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 6, 2, 0, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 5, 2, 0, 5, 4, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 6, 2, 6, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 5, 2, 6, 5, 4, _snowman, _snowman, false);
            for (int i = 0; i <= 6; ++i) {
                for (_snowman = 0; _snowman <= 6; ++_snowman) {
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, _snowman, boundingBox);
                }
            }
            return true;
        }
    }

    public static class BridgeCrossing
    extends Piece {
        public BridgeCrossing(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        protected BridgeCrossing(Random random, int x, int z) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, 0);
            this.setOrientation(Direction.Type.HORIZONTAL.random(random));
            this.boundingBox = this.getFacing().getAxis() == Direction.Axis.Z ? new BlockBox(x, 64, z, x + 19 - 1, 73, z + 19 - 1) : new BlockBox(x, 64, z, x + 19 - 1, 73, z + 19 - 1);
        }

        protected BridgeCrossing(StructurePieceType structurePieceType, CompoundTag compoundTag) {
            super(structurePieceType, compoundTag);
        }

        public BridgeCrossing(StructureManager structureManager, CompoundTag compoundTag) {
            this(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 8, 3, false);
            this.fillNWOpening((Start)start, pieces, random, 3, 8, false);
            this.fillSEOpening((Start)start, pieces, random, 3, 8, false);
        }

        public static BridgeCrossing create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -8, -3, 0, 19, 10, 19, orientation);
            if (!BridgeCrossing.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new BridgeCrossing(chainLength, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            StructureWorldAccess structureWorldAccess2;
            int n;
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 3, 0, 11, 4, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 3, 7, 18, 4, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 5, 0, 10, 7, 18, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 5, 8, 18, 7, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 5, 0, 7, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 5, 11, 7, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 11, 5, 0, 11, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 11, 5, 11, 11, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 5, 7, 7, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 11, 5, 7, 18, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 5, 11, 7, 5, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 11, 5, 11, 18, 5, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 2, 0, 11, 2, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 2, 13, 11, 2, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 0, 0, 11, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 0, 15, 11, 1, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            for (n = 7; n <= 11; ++n) {
                for (_snowman = 0; _snowman <= 2; ++_snowman) {
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, -1, _snowman, boundingBox);
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, -1, 18 - _snowman, boundingBox);
                }
            }
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 2, 7, 5, 2, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 13, 2, 7, 18, 2, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 0, 7, 3, 1, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 15, 0, 7, 18, 1, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            for (n = 0; n <= 2; ++n) {
                for (_snowman = 7; _snowman <= 11; ++_snowman) {
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), n, -1, _snowman, boundingBox);
                    this.fillDownwards(structureWorldAccess2, Blocks.NETHER_BRICKS.getDefaultState(), 18 - n, -1, _snowman, boundingBox);
                }
            }
            return true;
        }
    }

    public static class BridgeEnd
    extends Piece {
        private final int seed;

        public BridgeEnd(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE_END, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
            this.seed = random.nextInt();
        }

        public BridgeEnd(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE_END, compoundTag);
            this.seed = compoundTag.getInt("Seed");
        }

        public static BridgeEnd create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, -3, 0, 5, 10, 8, orientation);
            if (!BridgeEnd.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new BridgeEnd(chainLength, random, blockBox, orientation);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putInt("Seed", this.seed);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            int _snowman2;
            Random random2 = new Random(this.seed);
            for (_snowman2 = 0; _snowman2 <= 4; ++_snowman2) {
                for (n = 3; n <= 4; ++n) {
                    _snowman = random2.nextInt(8);
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman2, n, 0, _snowman2, n, _snowman, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
                }
            }
            _snowman2 = random2.nextInt(8);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 5, 0, 0, 5, _snowman2, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            _snowman2 = random2.nextInt(8);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 5, 0, 4, 5, _snowman2, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            for (_snowman2 = 0; _snowman2 <= 4; ++_snowman2) {
                n = random2.nextInt(5);
                this.fillWithOutline(structureWorldAccess, boundingBox, _snowman2, 2, 0, _snowman2, 2, n, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            }
            for (_snowman2 = 0; _snowman2 <= 4; ++_snowman2) {
                for (n = 0; n <= 1; ++n) {
                    _snowman = random2.nextInt(3);
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman2, n, 0, _snowman2, n, _snowman, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
                }
            }
            return true;
        }
    }

    public static class Bridge
    extends Piece {
        public Bridge(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public Bridge(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_BRIDGE, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 1, 3, false);
        }

        public static Bridge create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, -3, 0, 5, 10, 19, orientation);
            if (!Bridge.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new Bridge(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 5, 0, 3, 7, 18, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
            for (int i = 0; i <= 4; ++i) {
                for (_snowman = 0; _snowman <= 2; ++_snowman) {
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, _snowman, boundingBox);
                    this.fillDownwards(structureWorldAccess, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, 18 - _snowman, boundingBox);
                }
            }
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            _snowman = (BlockState)blockState.with(FenceBlock.EAST, true);
            _snowman = (BlockState)blockState.with(FenceBlock.WEST, true);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 1, 0, 4, 1, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 4, 0, 4, 4, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 14, 0, 4, 14, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 17, 0, 4, 17, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 1, 1, 4, 4, 1, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 3, 4, 4, 4, 4, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 3, 14, 4, 4, 14, _snowman, _snowman, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 1, 17, 4, 4, 17, _snowman, _snowman, false);
            return true;
        }
    }

    public static class Start
    extends BridgeCrossing {
        public PieceData lastPiece;
        public List<PieceData> bridgePieces;
        public List<PieceData> corridorPieces;
        public final List<StructurePiece> pieces = Lists.newArrayList();

        public Start(Random random, int n, int n2) {
            super(random, n, n2);
            this.bridgePieces = Lists.newArrayList();
            for (PieceData pieceData : ALL_BRIDGE_PIECES) {
                pieceData.generatedCount = 0;
                this.bridgePieces.add(pieceData);
            }
            this.corridorPieces = Lists.newArrayList();
            for (PieceData pieceData : ALL_CORRIDOR_PIECES) {
                pieceData.generatedCount = 0;
                this.corridorPieces.add(pieceData);
            }
        }

        public Start(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.NETHER_FORTRESS_START, compoundTag);
        }
    }

    static abstract class Piece
    extends StructurePiece {
        protected Piece(StructurePieceType structurePieceType, int n) {
            super(structurePieceType, n);
        }

        public Piece(StructurePieceType structurePieceType, CompoundTag compoundTag) {
            super(structurePieceType, compoundTag);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
        }

        private int checkRemainingPieces(List<PieceData> possiblePieces) {
            boolean bl = false;
            int _snowman2 = 0;
            for (PieceData pieceData2 : possiblePieces) {
                PieceData pieceData2;
                if (pieceData2.limit > 0 && pieceData2.generatedCount < pieceData2.limit) {
                    bl = true;
                }
                _snowman2 += pieceData2.weight;
            }
            return bl ? _snowman2 : -1;
        }

        private Piece pickPiece(Start start, List<PieceData> possiblePieces, List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            int n = this.checkRemainingPieces(possiblePieces);
            boolean _snowman2 = n > 0 && chainLength <= 30;
            _snowman = 0;
            block0: while (_snowman < 5 && _snowman2) {
                ++_snowman;
                _snowman = random.nextInt(n);
                for (PieceData pieceData : possiblePieces) {
                    if ((_snowman -= pieceData.weight) >= 0) continue;
                    if (!pieceData.canGenerate(chainLength) || pieceData == start.lastPiece && !pieceData.repeatable) continue block0;
                    Piece piece = NetherFortressGenerator.createPiece(pieceData, pieces, random, x, y, z, orientation, chainLength);
                    if (piece == null) continue;
                    ++pieceData.generatedCount;
                    start.lastPiece = pieceData;
                    if (!pieceData.canGenerate()) {
                        possiblePieces.remove(pieceData);
                    }
                    return piece;
                }
            }
            return BridgeEnd.create(pieces, random, x, y, z, orientation, chainLength);
        }

        private StructurePiece pieceGenerator(Start start, List<StructurePiece> pieces, Random random, int x, int y, int z, @Nullable Direction orientation, int chainLength, boolean inside) {
            if (Math.abs(x - start.getBoundingBox().minX) > 112 || Math.abs(z - start.getBoundingBox().minZ) > 112) {
                return BridgeEnd.create(pieces, random, x, y, z, orientation, chainLength);
            }
            List<PieceData> list = start.bridgePieces;
            if (inside) {
                list = start.corridorPieces;
            }
            if ((_snowman = this.pickPiece(start, list, pieces, random, x, y, z, orientation, chainLength + 1)) != null) {
                pieces.add(_snowman);
                start.pieces.add(_snowman);
            }
            return _snowman;
        }

        @Nullable
        protected StructurePiece fillForwardOpening(Start start, List<StructurePiece> pieces, Random random, int leftRightOffset, int heightOffset, boolean inside) {
            Direction direction = this.getFacing();
            if (direction != null) {
                switch (direction) {
                    case NORTH: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.minZ - 1, direction, this.getChainLength(), inside);
                    }
                    case SOUTH: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.maxZ + 1, direction, this.getChainLength(), inside);
                    }
                    case WEST: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, direction, this.getChainLength(), inside);
                    }
                    case EAST: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, direction, this.getChainLength(), inside);
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece fillNWOpening(Start start, List<StructurePiece> pieces, Random random, int heightOffset, int leftRightOffset, boolean inside) {
            Direction direction = this.getFacing();
            if (direction != null) {
                switch (direction) {
                    case NORTH: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, Direction.WEST, this.getChainLength(), inside);
                    }
                    case SOUTH: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, Direction.WEST, this.getChainLength(), inside);
                    }
                    case WEST: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.minZ - 1, Direction.NORTH, this.getChainLength(), inside);
                    }
                    case EAST: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.minZ - 1, Direction.NORTH, this.getChainLength(), inside);
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece fillSEOpening(Start start, List<StructurePiece> pieces, Random random, int heightOffset, int leftRightOffset, boolean inside) {
            Direction direction = this.getFacing();
            if (direction != null) {
                switch (direction) {
                    case NORTH: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, Direction.EAST, this.getChainLength(), inside);
                    }
                    case SOUTH: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, Direction.EAST, this.getChainLength(), inside);
                    }
                    case WEST: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getChainLength(), inside);
                    }
                    case EAST: {
                        return this.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getChainLength(), inside);
                    }
                }
            }
            return null;
        }

        protected static boolean isInbounds(BlockBox boundingBox) {
            return boundingBox != null && boundingBox.minY > 10;
        }
    }

    static class PieceData {
        public final Class<? extends Piece> pieceType;
        public final int weight;
        public int generatedCount;
        public final int limit;
        public final boolean repeatable;

        public PieceData(Class<? extends Piece> pieceType, int weight, int limit, boolean repeatable) {
            this.pieceType = pieceType;
            this.weight = weight;
            this.limit = limit;
            this.repeatable = repeatable;
        }

        public PieceData(Class<? extends Piece> pieceType, int weight, int limit) {
            this(pieceType, weight, limit, false);
        }

        public boolean canGenerate(int chainLength) {
            return this.limit == 0 || this.generatedCount < this.limit;
        }

        public boolean canGenerate() {
            return this.limit == 0 || this.generatedCount < this.limit;
        }
    }
}

