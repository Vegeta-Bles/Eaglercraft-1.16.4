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
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.EntityType;
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

public class StrongholdGenerator {
    private static final PieceData[] ALL_PIECES = new PieceData[]{new PieceData(Corridor.class, 40, 0), new PieceData(PrisonHall.class, 5, 5), new PieceData(LeftTurn.class, 20, 0), new PieceData(RightTurn.class, 20, 0), new PieceData(SquareRoom.class, 10, 6), new PieceData(Stairs.class, 5, 5), new PieceData(SpiralStaircase.class, 5, 5), new PieceData(FiveWayCrossing.class, 5, 4), new PieceData(ChestCorridor.class, 5, 4), new PieceData(Library.class, 10, 2){

        public boolean canGenerate(int chainLength) {
            return super.canGenerate(chainLength) && chainLength > 4;
        }
    }, new PieceData(PortalRoom.class, 20, 1){

        public boolean canGenerate(int chainLength) {
            return super.canGenerate(chainLength) && chainLength > 5;
        }
    }};
    private static List<PieceData> possiblePieces;
    private static Class<? extends Piece> activePieceType;
    private static int totalWeight;
    private static final StoneBrickRandomizer STONE_BRICK_RANDOMIZER;

    public static void init() {
        possiblePieces = Lists.newArrayList();
        for (PieceData pieceData : ALL_PIECES) {
            pieceData.generatedCount = 0;
            possiblePieces.add(pieceData);
        }
        activePieceType = null;
    }

    private static boolean checkRemainingPieces() {
        boolean bl = false;
        totalWeight = 0;
        for (PieceData pieceData2 : possiblePieces) {
            PieceData pieceData2;
            if (pieceData2.limit > 0 && pieceData2.generatedCount < pieceData2.limit) {
                bl = true;
            }
            totalWeight += pieceData2.weight;
        }
        return bl;
    }

    private static Piece createPiece(Class<? extends Piece> pieceType, List<StructurePiece> pieces, Random random, int x, int y, int z, @Nullable Direction orientation, int chainLength) {
        Piece piece = null;
        if (pieceType == Corridor.class) {
            piece = Corridor.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (pieceType == PrisonHall.class) {
            piece = PrisonHall.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (pieceType == LeftTurn.class) {
            piece = LeftTurn.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (pieceType == RightTurn.class) {
            piece = RightTurn.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (pieceType == SquareRoom.class) {
            piece = SquareRoom.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (pieceType == Stairs.class) {
            piece = Stairs.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (pieceType == SpiralStaircase.class) {
            piece = SpiralStaircase.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (pieceType == FiveWayCrossing.class) {
            piece = FiveWayCrossing.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (pieceType == ChestCorridor.class) {
            piece = ChestCorridor.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (pieceType == Library.class) {
            piece = Library.create(pieces, random, x, y, z, orientation, chainLength);
        } else if (pieceType == PortalRoom.class) {
            piece = PortalRoom.create(pieces, x, y, z, orientation, chainLength);
        }
        return piece;
    }

    private static Piece pickPiece(Start start, List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
        if (!StrongholdGenerator.checkRemainingPieces()) {
            return null;
        }
        if (activePieceType != null) {
            Piece piece = StrongholdGenerator.createPiece(activePieceType, pieces, random, x, y, z, orientation, chainLength);
            activePieceType = null;
            if (piece != null) {
                return piece;
            }
        }
        int n = 0;
        block0: while (n < 5) {
            ++n;
            _snowman = random.nextInt(totalWeight);
            for (PieceData pieceData : possiblePieces) {
                if ((_snowman -= pieceData.weight) >= 0) continue;
                if (!pieceData.canGenerate(chainLength) || pieceData == start.lastPiece) continue block0;
                Piece piece = StrongholdGenerator.createPiece(pieceData.pieceType, pieces, random, x, y, z, orientation, chainLength);
                if (piece == null) continue;
                ++pieceData.generatedCount;
                start.lastPiece = pieceData;
                if (!pieceData.canGenerate()) {
                    possiblePieces.remove(pieceData);
                }
                return piece;
            }
        }
        BlockBox blockBox = SmallCorridor.create(pieces, random, x, y, z, orientation);
        if (blockBox != null && blockBox.minY > 1) {
            return new SmallCorridor(chainLength, blockBox, orientation);
        }
        return null;
    }

    private static StructurePiece pieceGenerator(Start start, List<StructurePiece> pieces, Random random, int x, int y, int z, @Nullable Direction orientation, int chainLength) {
        if (chainLength > 50) {
            return null;
        }
        if (Math.abs(x - start.getBoundingBox().minX) > 112 || Math.abs(z - start.getBoundingBox().minZ) > 112) {
            return null;
        }
        Piece piece = StrongholdGenerator.pickPiece(start, pieces, random, x, y, z, orientation, chainLength + 1);
        if (piece != null) {
            pieces.add(piece);
            start.pieces.add(piece);
        }
        return piece;
    }

    static {
        STONE_BRICK_RANDOMIZER = new StoneBrickRandomizer();
    }

    static class StoneBrickRandomizer
    extends StructurePiece.BlockRandomizer {
        private StoneBrickRandomizer() {
        }

        @Override
        public void setBlock(Random random, int x, int y, int z, boolean placeBlock) {
            float f;
            this.block = placeBlock ? ((f = random.nextFloat()) < 0.2f ? Blocks.CRACKED_STONE_BRICKS.getDefaultState() : (f < 0.5f ? Blocks.MOSSY_STONE_BRICKS.getDefaultState() : (f < 0.55f ? Blocks.INFESTED_STONE_BRICKS.getDefaultState() : Blocks.STONE_BRICKS.getDefaultState()))) : Blocks.CAVE_AIR.getDefaultState();
        }
    }

    public static class PortalRoom
    extends Piece {
        private boolean spawnerPlaced;

        public PortalRoom(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_PORTAL_ROOM, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public PortalRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_PORTAL_ROOM, compoundTag);
            this.spawnerPlaced = compoundTag.getBoolean("Mob");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("Mob", this.spawnerPlaced);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            if (start != null) {
                ((Start)start).portalRoom = this;
            }
        }

        public static PortalRoom create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 11, 8, 16, orientation);
            if (!PortalRoom.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new PortalRoom(chainLength, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 10, 7, 15, false, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess, random, boundingBox, Piece.EntranceType.GRATES, 4, 1, 0);
            int n = 6;
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, n, 1, 1, n, 14, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 9, n, 1, 9, n, 14, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, n, 1, 8, n, 2, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, n, 14, 8, n, 14, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 1, 2, 1, 4, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 8, 1, 1, 9, 1, 4, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 1, 1, 1, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 9, 1, 1, 9, 1, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 3, 1, 8, 7, 1, 12, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 1, 9, 6, 1, 11, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
            BlockState _snowman2 = (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true);
            BlockState _snowman3 = (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true);
            for (_snowman = 3; _snowman < 14; _snowman += 2) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, _snowman, 0, 4, _snowman, _snowman2, _snowman2, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 10, 3, _snowman, 10, 4, _snowman, _snowman2, _snowman2, false);
            }
            for (_snowman = 2; _snowman < 9; _snowman += 2) {
                this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, 3, 15, _snowman, 4, 15, _snowman3, _snowman3, false);
            }
            BlockState _snowman4 = (BlockState)Blocks.STONE_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 1, 5, 6, 1, 7, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 2, 6, 6, 2, 7, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 3, 7, 6, 3, 7, false, random, STONE_BRICK_RANDOMIZER);
            for (_snowman = 4; _snowman <= 6; ++_snowman) {
                this.addBlock(structureWorldAccess, _snowman4, _snowman, 1, 4, boundingBox);
                this.addBlock(structureWorldAccess, _snowman4, _snowman, 2, 5, boundingBox);
                this.addBlock(structureWorldAccess, _snowman4, _snowman, 3, 6, boundingBox);
            }
            BlockState _snowman5 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.NORTH);
            BlockState _snowman6 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.SOUTH);
            BlockState _snowman7 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.EAST);
            BlockState _snowman8 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.WEST);
            boolean _snowman9 = true;
            boolean[] _snowman10 = new boolean[12];
            for (_snowman = 0; _snowman < _snowman10.length; ++_snowman) {
                _snowman10[_snowman] = random.nextFloat() > 0.9f;
                _snowman9 &= _snowman10[_snowman];
            }
            this.addBlock(structureWorldAccess, (BlockState)_snowman5.with(EndPortalFrameBlock.EYE, _snowman10[0]), 4, 3, 8, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman5.with(EndPortalFrameBlock.EYE, _snowman10[1]), 5, 3, 8, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman5.with(EndPortalFrameBlock.EYE, _snowman10[2]), 6, 3, 8, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman6.with(EndPortalFrameBlock.EYE, _snowman10[3]), 4, 3, 12, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman6.with(EndPortalFrameBlock.EYE, _snowman10[4]), 5, 3, 12, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman6.with(EndPortalFrameBlock.EYE, _snowman10[5]), 6, 3, 12, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman7.with(EndPortalFrameBlock.EYE, _snowman10[6]), 3, 3, 9, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman7.with(EndPortalFrameBlock.EYE, _snowman10[7]), 3, 3, 10, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman7.with(EndPortalFrameBlock.EYE, _snowman10[8]), 3, 3, 11, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman8.with(EndPortalFrameBlock.EYE, _snowman10[9]), 7, 3, 9, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman8.with(EndPortalFrameBlock.EYE, _snowman10[10]), 7, 3, 10, boundingBox);
            this.addBlock(structureWorldAccess, (BlockState)_snowman8.with(EndPortalFrameBlock.EYE, _snowman10[11]), 7, 3, 11, boundingBox);
            if (_snowman9) {
                BlockState blockState = Blocks.END_PORTAL.getDefaultState();
                this.addBlock(structureWorldAccess, blockState, 4, 3, 9, boundingBox);
                this.addBlock(structureWorldAccess, blockState, 5, 3, 9, boundingBox);
                this.addBlock(structureWorldAccess, blockState, 6, 3, 9, boundingBox);
                this.addBlock(structureWorldAccess, blockState, 4, 3, 10, boundingBox);
                this.addBlock(structureWorldAccess, blockState, 5, 3, 10, boundingBox);
                this.addBlock(structureWorldAccess, blockState, 6, 3, 10, boundingBox);
                this.addBlock(structureWorldAccess, blockState, 4, 3, 11, boundingBox);
                this.addBlock(structureWorldAccess, blockState, 5, 3, 11, boundingBox);
                this.addBlock(structureWorldAccess, blockState, 6, 3, 11, boundingBox);
            }
            if (!this.spawnerPlaced) {
                n = this.applyYTransform(3);
                BlockPos _snowman11 = new BlockPos(this.applyXTransform(5, 6), n, this.applyZTransform(5, 6));
                if (boundingBox.contains(_snowman11)) {
                    this.spawnerPlaced = true;
                    structureWorldAccess.setBlockState(_snowman11, Blocks.SPAWNER.getDefaultState(), 2);
                    BlockEntity blockEntity = structureWorldAccess.getBlockEntity(_snowman11);
                    if (blockEntity instanceof MobSpawnerBlockEntity) {
                        ((MobSpawnerBlockEntity)blockEntity).getLogic().setEntityId(EntityType.SILVERFISH);
                    }
                }
            }
            return true;
        }
    }

    public static class FiveWayCrossing
    extends Piece {
        private final boolean lowerLeftExists;
        private final boolean upperLeftExists;
        private final boolean lowerRightExists;
        private final boolean upperRightExists;

        public FiveWayCrossing(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_FIVE_WAY_CROSSING, chainLength);
            this.setOrientation(orientation);
            this.entryDoor = this.getRandomEntrance(random);
            this.boundingBox = boundingBox;
            this.lowerLeftExists = random.nextBoolean();
            this.upperLeftExists = random.nextBoolean();
            this.lowerRightExists = random.nextBoolean();
            this.upperRightExists = random.nextInt(3) > 0;
        }

        public FiveWayCrossing(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_FIVE_WAY_CROSSING, compoundTag);
            this.lowerLeftExists = compoundTag.getBoolean("leftLow");
            this.upperLeftExists = compoundTag.getBoolean("leftHigh");
            this.lowerRightExists = compoundTag.getBoolean("rightLow");
            this.upperRightExists = compoundTag.getBoolean("rightHigh");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("leftLow", this.lowerLeftExists);
            tag.putBoolean("leftHigh", this.upperLeftExists);
            tag.putBoolean("rightLow", this.lowerRightExists);
            tag.putBoolean("rightHigh", this.upperRightExists);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            int n = 3;
            _snowman = 5;
            Direction _snowman2 = this.getFacing();
            if (_snowman2 == Direction.WEST || _snowman2 == Direction.NORTH) {
                n = 8 - n;
                _snowman = 8 - _snowman;
            }
            this.fillForwardOpening((Start)start, pieces, random, 5, 1);
            if (this.lowerLeftExists) {
                this.fillNWOpening((Start)start, pieces, random, n, 1);
            }
            if (this.upperLeftExists) {
                this.fillNWOpening((Start)start, pieces, random, _snowman, 7);
            }
            if (this.lowerRightExists) {
                this.fillSEOpening((Start)start, pieces, random, n, 1);
            }
            if (this.upperRightExists) {
                this.fillSEOpening((Start)start, pieces, random, _snowman, 7);
            }
        }

        public static FiveWayCrossing create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -4, -3, 0, 10, 9, 11, orientation);
            if (!FiveWayCrossing.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new FiveWayCrossing(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 9, 8, 10, true, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess, random, boundingBox, this.entryDoor, 4, 3, 0);
            if (this.lowerLeftExists) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 1, 0, 5, 3, AIR, AIR, false);
            }
            if (this.lowerRightExists) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 9, 3, 1, 9, 5, 3, AIR, AIR, false);
            }
            if (this.upperLeftExists) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 5, 7, 0, 7, 9, AIR, AIR, false);
            }
            if (this.upperRightExists) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 9, 5, 7, 9, 7, 9, AIR, AIR, false);
            }
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 10, 7, 3, 10, AIR, AIR, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 1, 8, 2, 6, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 1, 5, 4, 4, 9, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 8, 1, 5, 8, 4, 9, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 4, 7, 3, 4, 9, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 5, 3, 3, 6, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 4, 3, 3, 4, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 4, 6, 3, 4, 6, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 7, 7, 1, 8, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 9, 7, 1, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 2, 7, 7, 2, 7, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 4, 5, 7, 4, 5, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 8, 5, 7, 8, 5, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 5, 7, 7, 5, 9, (BlockState)Blocks.SMOOTH_STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE), (BlockState)Blocks.SMOOTH_STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE), false);
            this.addBlock(structureWorldAccess, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.SOUTH), 6, 5, 6, boundingBox);
            return true;
        }
    }

    public static class Library
    extends Piece {
        private final boolean tall;

        public Library(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_LIBRARY, chainLength);
            this.setOrientation(orientation);
            this.entryDoor = this.getRandomEntrance(random);
            this.boundingBox = boundingBox;
            this.tall = boundingBox.getBlockCountY() > 6;
        }

        public Library(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_LIBRARY, compoundTag);
            this.tall = compoundTag.getBoolean("Tall");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("Tall", this.tall);
        }

        public static Library create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 11, 15, orientation);
            if (!(Library.isInbounds(blockBox) && StructurePiece.getOverlappingPiece(pieces, blockBox) == null || Library.isInbounds(blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 6, 15, orientation)) && StructurePiece.getOverlappingPiece(pieces, blockBox) == null)) {
                return null;
            }
            return new Library(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n = 11;
            if (!this.tall) {
                n = 6;
            }
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 13, n - 1, 14, true, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess, random, boundingBox, this.entryDoor, 4, 1, 0);
            this.fillWithOutlineUnderSeaLevel(structureWorldAccess, boundingBox, random, 0.07f, 2, 1, 1, 11, 4, 13, Blocks.COBWEB.getDefaultState(), Blocks.COBWEB.getDefaultState(), false, false);
            boolean _snowman2 = true;
            _snowman = 12;
            for (_snowman = 1; _snowman <= 13; ++_snowman) {
                if ((_snowman - 1) % 4 == 0) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, _snowman, 1, 4, _snowman, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 12, 1, _snowman, 12, 4, _snowman, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                    this.addBlock(structureWorldAccess, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.EAST), 2, 3, _snowman, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.WEST), 11, 3, _snowman, boundingBox);
                    if (!this.tall) continue;
                    this.fillWithOutline(structureWorldAccess, boundingBox, 1, 6, _snowman, 1, 9, _snowman, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 12, 6, _snowman, 12, 9, _snowman, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                    continue;
                }
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, _snowman, 1, 4, _snowman, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 12, 1, _snowman, 12, 4, _snowman, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                if (!this.tall) continue;
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 6, _snowman, 1, 9, _snowman, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 12, 6, _snowman, 12, 9, _snowman, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
            }
            for (_snowman = 3; _snowman < 12; _snowman += 2) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 3, 1, _snowman, 4, 3, _snowman, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, _snowman, 7, 3, _snowman, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 9, 1, _snowman, 10, 3, _snowman, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
            }
            if (this.tall) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 5, 1, 3, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 10, 5, 1, 12, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 4, 5, 1, 9, 5, 2, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 4, 5, 12, 9, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.addBlock(structureWorldAccess, Blocks.OAK_PLANKS.getDefaultState(), 9, 5, 11, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.OAK_PLANKS.getDefaultState(), 8, 5, 11, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.OAK_PLANKS.getDefaultState(), 9, 5, 10, boundingBox);
                BlockState blockState = (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
                _snowman = (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
                this.fillWithOutline(structureWorldAccess, boundingBox, 3, 6, 3, 3, 6, 11, _snowman, _snowman, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 10, 6, 3, 10, 6, 9, _snowman, _snowman, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 4, 6, 2, 9, 6, 2, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 4, 6, 12, 7, 6, 12, blockState, blockState, false);
                this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.EAST, true), 3, 6, 2, boundingBox);
                this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.EAST, true), 3, 6, 12, boundingBox);
                this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.WEST, true), 10, 6, 2, boundingBox);
                for (int i = 0; i <= 2; ++i) {
                    this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.WEST, true), 8 + i, 6, 12 - i, boundingBox);
                    if (i == 2) continue;
                    this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.EAST, true), 8 + i, 6, 11 - i, boundingBox);
                }
                BlockState blockState2 = (BlockState)Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.SOUTH);
                this.addBlock(structureWorldAccess, blockState2, 10, 1, 13, boundingBox);
                this.addBlock(structureWorldAccess, blockState2, 10, 2, 13, boundingBox);
                this.addBlock(structureWorldAccess, blockState2, 10, 3, 13, boundingBox);
                this.addBlock(structureWorldAccess, blockState2, 10, 4, 13, boundingBox);
                this.addBlock(structureWorldAccess, blockState2, 10, 5, 13, boundingBox);
                this.addBlock(structureWorldAccess, blockState2, 10, 6, 13, boundingBox);
                this.addBlock(structureWorldAccess, blockState2, 10, 7, 13, boundingBox);
                int _snowman3 = 7;
                int _snowman4 = 7;
                _snowman = (BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.EAST, true);
                this.addBlock(structureWorldAccess, _snowman, 6, 9, 7, boundingBox);
                _snowman = (BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.WEST, true);
                this.addBlock(structureWorldAccess, _snowman, 7, 9, 7, boundingBox);
                this.addBlock(structureWorldAccess, _snowman, 6, 8, 7, boundingBox);
                this.addBlock(structureWorldAccess, _snowman, 7, 8, 7, boundingBox);
                _snowman = (BlockState)((BlockState)_snowman.with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
                this.addBlock(structureWorldAccess, _snowman, 6, 7, 7, boundingBox);
                this.addBlock(structureWorldAccess, _snowman, 7, 7, 7, boundingBox);
                this.addBlock(structureWorldAccess, _snowman, 5, 7, 7, boundingBox);
                this.addBlock(structureWorldAccess, _snowman, 8, 7, 7, boundingBox);
                this.addBlock(structureWorldAccess, (BlockState)_snowman.with(FenceBlock.NORTH, true), 6, 7, 6, boundingBox);
                this.addBlock(structureWorldAccess, (BlockState)_snowman.with(FenceBlock.SOUTH, true), 6, 7, 8, boundingBox);
                this.addBlock(structureWorldAccess, (BlockState)_snowman.with(FenceBlock.NORTH, true), 7, 7, 6, boundingBox);
                this.addBlock(structureWorldAccess, (BlockState)_snowman.with(FenceBlock.SOUTH, true), 7, 7, 8, boundingBox);
                _snowman = Blocks.TORCH.getDefaultState();
                this.addBlock(structureWorldAccess, _snowman, 5, 8, 7, boundingBox);
                this.addBlock(structureWorldAccess, _snowman, 8, 8, 7, boundingBox);
                this.addBlock(structureWorldAccess, _snowman, 6, 8, 6, boundingBox);
                this.addBlock(structureWorldAccess, _snowman, 6, 8, 8, boundingBox);
                this.addBlock(structureWorldAccess, _snowman, 7, 8, 6, boundingBox);
                this.addBlock(structureWorldAccess, _snowman, 7, 8, 8, boundingBox);
            }
            this.addChest(structureWorldAccess, boundingBox, random, 3, 3, 5, LootTables.STRONGHOLD_LIBRARY_CHEST);
            if (this.tall) {
                this.addBlock(structureWorldAccess, AIR, 12, 9, 1, boundingBox);
                this.addChest(structureWorldAccess, boundingBox, random, 12, 8, 1, LootTables.STRONGHOLD_LIBRARY_CHEST);
            }
            return true;
        }
    }

    public static class PrisonHall
    extends Piece {
        public PrisonHall(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_PRISON_HALL, chainLength);
            this.setOrientation(orientation);
            this.entryDoor = this.getRandomEntrance(random);
            this.boundingBox = boundingBox;
        }

        public PrisonHall(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_PRISON_HALL, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 1, 1);
        }

        public static PrisonHall create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, -1, 0, 9, 5, 11, orientation);
            if (!PrisonHall.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new PrisonHall(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            StructureWorldAccess structureWorldAccess2;
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 0, 0, 8, 4, 10, true, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess2, random, boundingBox, this.entryDoor, 1, 1, 0);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 1, 10, 3, 3, 10, AIR, AIR, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 1, 1, 4, 3, 1, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 1, 3, 4, 3, 3, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 1, 7, 4, 3, 7, false, random, STONE_BRICK_RANDOMIZER);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 1, 9, 4, 3, 9, false, random, STONE_BRICK_RANDOMIZER);
            for (int i = 1; i <= 3; ++i) {
                this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, i, 4, boundingBox);
                this.addBlock(structureWorldAccess2, (BlockState)((BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true)).with(PaneBlock.EAST, true), 4, i, 5, boundingBox);
                this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, i, 6, boundingBox);
                this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true), 5, i, 5, boundingBox);
                this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true), 6, i, 5, boundingBox);
                this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true), 7, i, 5, boundingBox);
            }
            this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, 3, 2, boundingBox);
            this.addBlock(structureWorldAccess2, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, 3, 8, boundingBox);
            BlockState _snowman2 = (BlockState)Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.WEST);
            BlockState _snowman3 = (BlockState)((BlockState)Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.WEST)).with(DoorBlock.HALF, DoubleBlockHalf.UPPER);
            this.addBlock(structureWorldAccess2, _snowman2, 4, 1, 2, boundingBox);
            this.addBlock(structureWorldAccess2, _snowman3, 4, 2, 2, boundingBox);
            this.addBlock(structureWorldAccess2, _snowman2, 4, 1, 8, boundingBox);
            this.addBlock(structureWorldAccess2, _snowman3, 4, 2, 8, boundingBox);
            return true;
        }
    }

    public static class SquareRoom
    extends Piece {
        protected final int roomType;

        public SquareRoom(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_SQUARE_ROOM, chainLength);
            this.setOrientation(orientation);
            this.entryDoor = this.getRandomEntrance(random);
            this.boundingBox = boundingBox;
            this.roomType = random.nextInt(5);
        }

        public SquareRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_SQUARE_ROOM, compoundTag);
            this.roomType = compoundTag.getInt("Type");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putInt("Type", this.roomType);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 4, 1);
            this.fillNWOpening((Start)start, pieces, random, 1, 4);
            this.fillSEOpening((Start)start, pieces, random, 1, 4);
        }

        public static SquareRoom create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 11, 7, 11, orientation);
            if (!SquareRoom.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new SquareRoom(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 0, 0, 10, 6, 10, true, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess2, random, boundingBox, this.entryDoor, 4, 1, 0);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 1, 10, 6, 3, 10, AIR, AIR, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 1, 4, 0, 3, 6, AIR, AIR, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 10, 1, 4, 10, 3, 6, AIR, AIR, false);
            switch (this.roomType) {
                default: {
                    break;
                }
                case 0: {
                    this.addBlock(structureWorldAccess2, Blocks.STONE_BRICKS.getDefaultState(), 5, 1, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.STONE_BRICKS.getDefaultState(), 5, 2, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.STONE_BRICKS.getDefaultState(), 5, 3, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.WEST), 4, 3, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.EAST), 6, 3, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.SOUTH), 5, 3, 4, boundingBox);
                    this.addBlock(structureWorldAccess2, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.NORTH), 5, 3, 6, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 4, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 6, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 4, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 6, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 5, 1, 4, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 5, 1, 6, boundingBox);
                    break;
                }
                case 1: {
                    StructureWorldAccess structureWorldAccess2;
                    for (int i = 0; i < 5; ++i) {
                        this.addBlock(structureWorldAccess2, Blocks.STONE_BRICKS.getDefaultState(), 3, 1, 3 + i, boundingBox);
                        this.addBlock(structureWorldAccess2, Blocks.STONE_BRICKS.getDefaultState(), 7, 1, 3 + i, boundingBox);
                        this.addBlock(structureWorldAccess2, Blocks.STONE_BRICKS.getDefaultState(), 3 + i, 1, 3, boundingBox);
                        this.addBlock(structureWorldAccess2, Blocks.STONE_BRICKS.getDefaultState(), 3 + i, 1, 7, boundingBox);
                    }
                    this.addBlock(structureWorldAccess2, Blocks.STONE_BRICKS.getDefaultState(), 5, 1, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.STONE_BRICKS.getDefaultState(), 5, 2, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.STONE_BRICKS.getDefaultState(), 5, 3, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.WATER.getDefaultState(), 5, 4, 5, boundingBox);
                    break;
                }
                case 2: {
                    int n;
                    StructureWorldAccess structureWorldAccess2;
                    for (n = 1; n <= 9; ++n) {
                        this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 1, 3, n, boundingBox);
                        this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 9, 3, n, boundingBox);
                    }
                    for (n = 1; n <= 9; ++n) {
                        this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), n, 3, 1, boundingBox);
                        this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), n, 3, 9, boundingBox);
                    }
                    this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 4, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 6, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 4, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 6, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 4, 1, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 6, 1, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 4, 3, 5, boundingBox);
                    this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 6, 3, 5, boundingBox);
                    for (n = 1; n <= 3; ++n) {
                        this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 4, n, 4, boundingBox);
                        this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 6, n, 4, boundingBox);
                        this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 4, n, 6, boundingBox);
                        this.addBlock(structureWorldAccess2, Blocks.COBBLESTONE.getDefaultState(), 6, n, 6, boundingBox);
                    }
                    this.addBlock(structureWorldAccess2, Blocks.TORCH.getDefaultState(), 5, 3, 5, boundingBox);
                    for (n = 2; n <= 8; ++n) {
                        this.addBlock(structureWorldAccess2, Blocks.OAK_PLANKS.getDefaultState(), 2, 3, n, boundingBox);
                        this.addBlock(structureWorldAccess2, Blocks.OAK_PLANKS.getDefaultState(), 3, 3, n, boundingBox);
                        if (n <= 3 || n >= 7) {
                            this.addBlock(structureWorldAccess2, Blocks.OAK_PLANKS.getDefaultState(), 4, 3, n, boundingBox);
                            this.addBlock(structureWorldAccess2, Blocks.OAK_PLANKS.getDefaultState(), 5, 3, n, boundingBox);
                            this.addBlock(structureWorldAccess2, Blocks.OAK_PLANKS.getDefaultState(), 6, 3, n, boundingBox);
                        }
                        this.addBlock(structureWorldAccess2, Blocks.OAK_PLANKS.getDefaultState(), 7, 3, n, boundingBox);
                        this.addBlock(structureWorldAccess2, Blocks.OAK_PLANKS.getDefaultState(), 8, 3, n, boundingBox);
                    }
                    BlockState blockState = (BlockState)Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.WEST);
                    this.addBlock(structureWorldAccess2, blockState, 9, 1, 3, boundingBox);
                    this.addBlock(structureWorldAccess2, blockState, 9, 2, 3, boundingBox);
                    this.addBlock(structureWorldAccess2, blockState, 9, 3, 3, boundingBox);
                    this.addChest(structureWorldAccess2, boundingBox, random, 3, 4, 8, LootTables.STRONGHOLD_CROSSING_CHEST);
                }
            }
            return true;
        }
    }

    public static class RightTurn
    extends Turn {
        public RightTurn(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_RIGHT_TURN, chainLength);
            this.setOrientation(orientation);
            this.entryDoor = this.getRandomEntrance(random);
            this.boundingBox = boundingBox;
        }

        public RightTurn(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_RIGHT_TURN, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            Direction direction = this.getFacing();
            if (direction == Direction.NORTH || direction == Direction.EAST) {
                this.fillSEOpening((Start)start, pieces, random, 1, 1);
            } else {
                this.fillNWOpening((Start)start, pieces, random, 1, 1);
            }
        }

        public static RightTurn create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 5, orientation);
            if (!RightTurn.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new RightTurn(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 4, 4, 4, true, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess, random, boundingBox, this.entryDoor, 1, 1, 0);
            Direction direction = this.getFacing();
            if (direction == Direction.NORTH || direction == Direction.EAST) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 4, 1, 1, 4, 3, 3, AIR, AIR, false);
            } else {
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 1, 0, 3, 3, AIR, AIR, false);
            }
            return true;
        }
    }

    public static class LeftTurn
    extends Turn {
        public LeftTurn(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_LEFT_TURN, chainLength);
            this.setOrientation(orientation);
            this.entryDoor = this.getRandomEntrance(random);
            this.boundingBox = boundingBox;
        }

        public LeftTurn(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_LEFT_TURN, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            Direction direction = this.getFacing();
            if (direction == Direction.NORTH || direction == Direction.EAST) {
                this.fillNWOpening((Start)start, pieces, random, 1, 1);
            } else {
                this.fillSEOpening((Start)start, pieces, random, 1, 1);
            }
        }

        public static LeftTurn create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 5, orientation);
            if (!LeftTurn.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new LeftTurn(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 4, 4, 4, true, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess, random, boundingBox, this.entryDoor, 1, 1, 0);
            Direction direction = this.getFacing();
            if (direction == Direction.NORTH || direction == Direction.EAST) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 1, 0, 3, 3, AIR, AIR, false);
            } else {
                this.fillWithOutline(structureWorldAccess, boundingBox, 4, 1, 1, 4, 3, 3, AIR, AIR, false);
            }
            return true;
        }
    }

    public static abstract class Turn
    extends Piece {
        protected Turn(StructurePieceType structurePieceType, int n) {
            super(structurePieceType, n);
        }

        public Turn(StructurePieceType structurePieceType, CompoundTag compoundTag) {
            super(structurePieceType, compoundTag);
        }
    }

    public static class Stairs
    extends Piece {
        public Stairs(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_STAIRS, chainLength);
            this.setOrientation(orientation);
            this.entryDoor = this.getRandomEntrance(random);
            this.boundingBox = boundingBox;
        }

        public Stairs(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_STAIRS, compoundTag);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 1, 1);
        }

        public static Stairs create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, -7, 0, 5, 11, 8, orientation);
            if (!Stairs.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new Stairs(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 4, 10, 7, true, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess, random, boundingBox, this.entryDoor, 1, 7, 0);
            this.generateEntrance(structureWorldAccess, random, boundingBox, Piece.EntranceType.OPENING, 1, 1, 7);
            BlockState blockState = (BlockState)Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
            for (int i = 0; i < 6; ++i) {
                this.addBlock(structureWorldAccess, blockState, 1, 6 - i, 1 + i, boundingBox);
                this.addBlock(structureWorldAccess, blockState, 2, 6 - i, 1 + i, boundingBox);
                this.addBlock(structureWorldAccess, blockState, 3, 6 - i, 1 + i, boundingBox);
                if (i >= 5) continue;
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 1, 5 - i, 1 + i, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 2, 5 - i, 1 + i, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 3, 5 - i, 1 + i, boundingBox);
            }
            return true;
        }
    }

    public static class ChestCorridor
    extends Piece {
        private boolean chestGenerated;

        public ChestCorridor(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_CHEST_CORRIDOR, chainLength);
            this.setOrientation(orientation);
            this.entryDoor = this.getRandomEntrance(random);
            this.boundingBox = boundingBox;
        }

        public ChestCorridor(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_CHEST_CORRIDOR, compoundTag);
            this.chestGenerated = compoundTag.getBoolean("Chest");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("Chest", this.chestGenerated);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 1, 1);
        }

        public static ChestCorridor create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainlength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 7, orientation);
            if (!ChestCorridor.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new ChestCorridor(chainlength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 0, 0, 4, 4, 6, true, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess2, random, boundingBox, this.entryDoor, 1, 1, 0);
            this.generateEntrance(structureWorldAccess2, random, boundingBox, Piece.EntranceType.OPENING, 1, 1, 6);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 3, 1, 2, 3, 1, 4, Blocks.STONE_BRICKS.getDefaultState(), Blocks.STONE_BRICKS.getDefaultState(), false);
            this.addBlock(structureWorldAccess2, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 1, 1, boundingBox);
            this.addBlock(structureWorldAccess2, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 1, 5, boundingBox);
            this.addBlock(structureWorldAccess2, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 2, 2, boundingBox);
            this.addBlock(structureWorldAccess2, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 2, 4, boundingBox);
            for (int i = 2; i <= 4; ++i) {
                this.addBlock(structureWorldAccess2, Blocks.STONE_BRICK_SLAB.getDefaultState(), 2, 1, i, boundingBox);
            }
            if (!this.chestGenerated && boundingBox.contains(new BlockPos(this.applyXTransform(3, 3), this.applyYTransform(2), this.applyZTransform(3, 3)))) {
                StructureWorldAccess structureWorldAccess2;
                this.chestGenerated = true;
                this.addChest(structureWorldAccess2, boundingBox, random, 3, 2, 3, LootTables.STRONGHOLD_CORRIDOR_CHEST);
            }
            return true;
        }
    }

    public static class Corridor
    extends Piece {
        private final boolean leftExitExists;
        private final boolean rightExitExists;

        public Corridor(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_CORRIDOR, chainLength);
            this.setOrientation(orientation);
            this.entryDoor = this.getRandomEntrance(random);
            this.boundingBox = boundingBox;
            this.leftExitExists = random.nextInt(2) == 0;
            this.rightExitExists = random.nextInt(2) == 0;
        }

        public Corridor(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_CORRIDOR, compoundTag);
            this.leftExitExists = compoundTag.getBoolean("Left");
            this.rightExitExists = compoundTag.getBoolean("Right");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("Left", this.leftExitExists);
            tag.putBoolean("Right", this.rightExitExists);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            this.fillForwardOpening((Start)start, pieces, random, 1, 1);
            if (this.leftExitExists) {
                this.fillNWOpening((Start)start, pieces, random, 1, 2);
            }
            if (this.rightExitExists) {
                this.fillSEOpening((Start)start, pieces, random, 1, 2);
            }
        }

        public static Corridor create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 7, orientation);
            if (!Corridor.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new Corridor(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 4, 4, 6, true, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess, random, boundingBox, this.entryDoor, 1, 1, 0);
            this.generateEntrance(structureWorldAccess, random, boundingBox, Piece.EntranceType.OPENING, 1, 1, 6);
            BlockState blockState = (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.EAST);
            _snowman = (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.WEST);
            this.addBlockWithRandomThreshold(structureWorldAccess, boundingBox, random, 0.1f, 1, 2, 1, blockState);
            this.addBlockWithRandomThreshold(structureWorldAccess, boundingBox, random, 0.1f, 3, 2, 1, _snowman);
            this.addBlockWithRandomThreshold(structureWorldAccess, boundingBox, random, 0.1f, 1, 2, 5, blockState);
            this.addBlockWithRandomThreshold(structureWorldAccess, boundingBox, random, 0.1f, 3, 2, 5, _snowman);
            if (this.leftExitExists) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 2, 0, 3, 4, AIR, AIR, false);
            }
            if (this.rightExitExists) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 4, 1, 2, 4, 3, 4, AIR, AIR, false);
            }
            return true;
        }
    }

    public static class Start
    extends SpiralStaircase {
        public PieceData lastPiece;
        @Nullable
        public PortalRoom portalRoom;
        public final List<StructurePiece> pieces = Lists.newArrayList();

        public Start(Random random, int n, int n2) {
            super(StructurePieceType.STRONGHOLD_START, 0, random, n, n2);
        }

        public Start(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_START, compoundTag);
        }
    }

    public static class SpiralStaircase
    extends Piece {
        private final boolean isStructureStart;

        public SpiralStaircase(StructurePieceType structurePieceType, int chainLength, Random random, int x, int z) {
            super(structurePieceType, chainLength);
            this.isStructureStart = true;
            this.setOrientation(Direction.Type.HORIZONTAL.random(random));
            this.entryDoor = Piece.EntranceType.OPENING;
            this.boundingBox = this.getFacing().getAxis() == Direction.Axis.Z ? new BlockBox(x, 64, z, x + 5 - 1, 74, z + 5 - 1) : new BlockBox(x, 64, z, x + 5 - 1, 74, z + 5 - 1);
        }

        public SpiralStaircase(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_SPIRAL_STAIRCASE, chainLength);
            this.isStructureStart = false;
            this.setOrientation(orientation);
            this.entryDoor = this.getRandomEntrance(random);
            this.boundingBox = boundingBox;
        }

        public SpiralStaircase(StructurePieceType structurePieceType, CompoundTag compoundTag) {
            super(structurePieceType, compoundTag);
            this.isStructureStart = compoundTag.getBoolean("Source");
        }

        public SpiralStaircase(StructureManager structureManager, CompoundTag compoundTag) {
            this(StructurePieceType.STRONGHOLD_SPIRAL_STAIRCASE, compoundTag);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("Source", this.isStructureStart);
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            if (this.isStructureStart) {
                activePieceType = FiveWayCrossing.class;
            }
            this.fillForwardOpening((Start)start, pieces, random, 1, 1);
        }

        public static SpiralStaircase create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -1, -7, 0, 5, 11, 5, orientation);
            if (!SpiralStaircase.isInbounds(blockBox) || StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return new SpiralStaircase(chainLength, random, blockBox, orientation);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 4, 10, 4, true, random, STONE_BRICK_RANDOMIZER);
            this.generateEntrance(structureWorldAccess, random, boundingBox, this.entryDoor, 1, 7, 0);
            this.generateEntrance(structureWorldAccess, random, boundingBox, Piece.EntranceType.OPENING, 1, 1, 4);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 2, 6, 1, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 1, 5, 1, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 6, 1, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 1, 5, 2, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 1, 4, 3, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 5, 3, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 2, 4, 3, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 3, 3, 3, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 3, 4, 3, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 3, 3, 2, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 3, 2, 1, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 3, 3, 1, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 2, 2, 1, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 1, 1, 1, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 2, 1, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 1, 1, 2, boundingBox);
            this.addBlock(structureWorldAccess, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 1, 3, boundingBox);
            return true;
        }
    }

    public static class SmallCorridor
    extends Piece {
        private final int length;

        public SmallCorridor(int chainLength, BlockBox boundingBox, Direction orientation) {
            super(StructurePieceType.STRONGHOLD_SMALL_CORRIDOR, chainLength);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
            this.length = orientation == Direction.NORTH || orientation == Direction.SOUTH ? boundingBox.getBlockCountZ() : boundingBox.getBlockCountX();
        }

        public SmallCorridor(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.STRONGHOLD_SMALL_CORRIDOR, compoundTag);
            this.length = compoundTag.getInt("Steps");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putInt("Steps", this.length);
        }

        public static BlockBox create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation) {
            int n = 3;
            BlockBox _snowman2 = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 4, orientation);
            StructurePiece _snowman3 = StructurePiece.getOverlappingPiece(pieces, _snowman2);
            if (_snowman3 == null) {
                return null;
            }
            if (_snowman3.getBoundingBox().minY == _snowman2.minY) {
                for (_snowman = 3; _snowman >= 1; --_snowman) {
                    _snowman2 = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, _snowman - 1, orientation);
                    if (_snowman3.getBoundingBox().intersects(_snowman2)) continue;
                    return BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, _snowman, orientation);
                }
            }
            return null;
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            for (int i = 0; i < this.length; ++i) {
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 0, 0, i, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 1, 0, i, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 2, 0, i, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 3, 0, i, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 4, 0, i, boundingBox);
                for (_snowman = 1; _snowman <= 3; ++_snowman) {
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 0, _snowman, i, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.CAVE_AIR.getDefaultState(), 1, _snowman, i, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.CAVE_AIR.getDefaultState(), 2, _snowman, i, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.CAVE_AIR.getDefaultState(), 3, _snowman, i, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 4, _snowman, i, boundingBox);
                }
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 0, 4, i, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 1, 4, i, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 2, 4, i, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 3, 4, i, boundingBox);
                this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), 4, 4, i, boundingBox);
            }
            return true;
        }
    }

    static abstract class Piece
    extends StructurePiece {
        protected EntranceType entryDoor = EntranceType.OPENING;

        protected Piece(StructurePieceType structurePieceType, int n) {
            super(structurePieceType, n);
        }

        public Piece(StructurePieceType structurePieceType, CompoundTag compoundTag) {
            super(structurePieceType, compoundTag);
            this.entryDoor = EntranceType.valueOf(compoundTag.getString("EntryDoor"));
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            tag.putString("EntryDoor", this.entryDoor.name());
        }

        protected void generateEntrance(StructureWorldAccess structureWorldAccess, Random random, BlockBox boundingBox, EntranceType type, int x, int y, int z) {
            switch (type) {
                case OPENING: {
                    this.fillWithOutline(structureWorldAccess, boundingBox, x, y, z, x + 3 - 1, y + 3 - 1, z, AIR, AIR, false);
                    break;
                }
                case WOOD_DOOR: {
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x, y, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x, y + 1, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x, y + 2, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x + 1, y + 2, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y + 2, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y + 1, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.OAK_DOOR.getDefaultState(), x + 1, y, z, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)Blocks.OAK_DOOR.getDefaultState().with(DoorBlock.HALF, DoubleBlockHalf.UPPER), x + 1, y + 1, z, boundingBox);
                    break;
                }
                case GRATES: {
                    this.addBlock(structureWorldAccess, Blocks.CAVE_AIR.getDefaultState(), x + 1, y, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.CAVE_AIR.getDefaultState(), x + 1, y + 1, z, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true), x, y, z, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true), x, y + 1, z, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), x, y + 2, z, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), x + 1, y + 2, z, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), x + 2, y + 2, z, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true), x + 2, y + 1, z, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true), x + 2, y, z, boundingBox);
                    break;
                }
                case IRON_DOOR: {
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x, y, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x, y + 1, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x, y + 2, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x + 1, y + 2, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y + 2, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y + 1, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y, z, boundingBox);
                    this.addBlock(structureWorldAccess, Blocks.IRON_DOOR.getDefaultState(), x + 1, y, z, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.HALF, DoubleBlockHalf.UPPER), x + 1, y + 1, z, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)Blocks.STONE_BUTTON.getDefaultState().with(AbstractButtonBlock.FACING, Direction.NORTH), x + 2, y + 1, z + 1, boundingBox);
                    this.addBlock(structureWorldAccess, (BlockState)Blocks.STONE_BUTTON.getDefaultState().with(AbstractButtonBlock.FACING, Direction.SOUTH), x + 2, y + 1, z - 1, boundingBox);
                }
            }
        }

        protected EntranceType getRandomEntrance(Random random) {
            int n = random.nextInt(5);
            switch (n) {
                default: {
                    return EntranceType.OPENING;
                }
                case 2: {
                    return EntranceType.WOOD_DOOR;
                }
                case 3: {
                    return EntranceType.GRATES;
                }
                case 4: 
            }
            return EntranceType.IRON_DOOR;
        }

        @Nullable
        protected StructurePiece fillForwardOpening(Start start, List<StructurePiece> pieces, Random random, int leftRightOffset, int heightOffset) {
            Direction direction = this.getFacing();
            if (direction != null) {
                switch (direction) {
                    case NORTH: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.minZ - 1, direction, this.getChainLength());
                    }
                    case SOUTH: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.maxZ + 1, direction, this.getChainLength());
                    }
                    case WEST: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, direction, this.getChainLength());
                    }
                    case EAST: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, direction, this.getChainLength());
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece fillNWOpening(Start start, List<StructurePiece> pieces, Random random, int heightOffset, int leftRightOffset) {
            Direction direction = this.getFacing();
            if (direction != null) {
                switch (direction) {
                    case NORTH: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, Direction.WEST, this.getChainLength());
                    }
                    case SOUTH: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, Direction.WEST, this.getChainLength());
                    }
                    case WEST: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.minZ - 1, Direction.NORTH, this.getChainLength());
                    }
                    case EAST: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.minZ - 1, Direction.NORTH, this.getChainLength());
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece fillSEOpening(Start start, List<StructurePiece> pieces, Random random, int heightOffset, int leftRightOffset) {
            Direction direction = this.getFacing();
            if (direction != null) {
                switch (direction) {
                    case NORTH: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, Direction.EAST, this.getChainLength());
                    }
                    case SOUTH: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + heightOffset, this.boundingBox.minZ + leftRightOffset, Direction.EAST, this.getChainLength());
                    }
                    case WEST: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getChainLength());
                    }
                    case EAST: {
                        return StrongholdGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + leftRightOffset, this.boundingBox.minY + heightOffset, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getChainLength());
                    }
                }
            }
            return null;
        }

        protected static boolean isInbounds(BlockBox boundingBox) {
            return boundingBox != null && boundingBox.minY > 10;
        }

        public static enum EntranceType {
            OPENING,
            WOOD_DOOR,
            GRATES,
            IRON_DOOR;

        }
    }

    static class PieceData {
        public final Class<? extends Piece> pieceType;
        public final int weight;
        public int generatedCount;
        public final int limit;

        public PieceData(Class<? extends Piece> pieceType, int weight, int limit) {
            this.pieceType = pieceType;
            this.weight = weight;
            this.limit = limit;
        }

        public boolean canGenerate(int chainLength) {
            return this.limit == 0 || this.generatedCount < this.limit;
        }

        public boolean canGenerate() {
            return this.limit == 0 || this.generatedCount < this.limit;
        }
    }
}

