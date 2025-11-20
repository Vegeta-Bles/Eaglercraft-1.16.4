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
import net.minecraft.block.RailBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.MineshaftFeature;

public class MineshaftGenerator {
    private static MineshaftPart pickPiece(List<StructurePiece> pieces, Random random, int x, int y, int z, @Nullable Direction orientation, int chainLength, MineshaftFeature.Type type) {
        int n = random.nextInt(100);
        if (n >= 80) {
            BlockBox blockBox = MineshaftCrossing.getBoundingBox(pieces, random, x, y, z, orientation);
            if (blockBox != null) {
                return new MineshaftCrossing(chainLength, blockBox, orientation, type);
            }
        } else if (n >= 70) {
            BlockBox blockBox = MineshaftStairs.getBoundingBox(pieces, random, x, y, z, orientation);
            if (blockBox != null) {
                return new MineshaftStairs(chainLength, blockBox, orientation, type);
            }
        } else {
            BlockBox blockBox = MineshaftCorridor.getBoundingBox(pieces, random, x, y, z, orientation);
            if (blockBox != null) {
                return new MineshaftCorridor(chainLength, random, blockBox, orientation, type);
            }
        }
        return null;
    }

    private static MineshaftPart pieceGenerator(StructurePiece start, List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
        if (chainLength > 8) {
            return null;
        }
        if (Math.abs(x - start.getBoundingBox().minX) > 80 || Math.abs(z - start.getBoundingBox().minZ) > 80) {
            return null;
        }
        MineshaftFeature.Type type = ((MineshaftPart)start).mineshaftType;
        MineshaftPart _snowman2 = MineshaftGenerator.pickPiece(pieces, random, x, y, z, orientation, chainLength + 1, type);
        if (_snowman2 != null) {
            pieces.add(_snowman2);
            _snowman2.fillOpenings(start, pieces, random);
        }
        return _snowman2;
    }

    public static class MineshaftStairs
    extends MineshaftPart {
        public MineshaftStairs(int chainLength, BlockBox boundingBox, Direction orientation, MineshaftFeature.Type type) {
            super(StructurePieceType.MINESHAFT_STAIRS, chainLength, type);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
        }

        public MineshaftStairs(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.MINESHAFT_STAIRS, compoundTag);
        }

        public static BlockBox getBoundingBox(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation) {
            BlockBox blockBox = new BlockBox(x, y - 5, z, x, y + 3 - 1, z);
            switch (orientation) {
                default: {
                    blockBox.maxX = x + 3 - 1;
                    blockBox.minZ = z - 8;
                    break;
                }
                case SOUTH: {
                    blockBox.maxX = x + 3 - 1;
                    blockBox.maxZ = z + 8;
                    break;
                }
                case WEST: {
                    blockBox.minX = x - 8;
                    blockBox.maxZ = z + 3 - 1;
                    break;
                }
                case EAST: {
                    blockBox.maxX = x + 8;
                    blockBox.maxZ = z + 3 - 1;
                }
            }
            if (StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return blockBox;
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            int n = this.getChainLength();
            Direction _snowman2 = this.getFacing();
            if (_snowman2 != null) {
                switch (_snowman2) {
                    default: {
                        MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, n);
                        break;
                    }
                    case SOUTH: {
                        MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                        break;
                    }
                    case WEST: {
                        MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, Direction.WEST, n);
                        break;
                    }
                    case EAST: {
                        MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, Direction.EAST, n);
                    }
                }
            }
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.isTouchingLiquid(structureWorldAccess, boundingBox)) {
                return false;
            }
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 5, 0, 2, 7, 1, AIR, AIR, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 7, 2, 2, 8, AIR, AIR, false);
            for (int i = 0; i < 5; ++i) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, AIR, AIR, false);
            }
            return true;
        }
    }

    public static class MineshaftCrossing
    extends MineshaftPart {
        private final Direction direction;
        private final boolean twoFloors;

        public MineshaftCrossing(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.MINESHAFT_CROSSING, compoundTag);
            this.twoFloors = compoundTag.getBoolean("tf");
            this.direction = Direction.fromHorizontal(compoundTag.getInt("D"));
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("tf", this.twoFloors);
            tag.putInt("D", this.direction.getHorizontal());
        }

        public MineshaftCrossing(int chainLength, BlockBox boundingBox, @Nullable Direction orientation, MineshaftFeature.Type type) {
            super(StructurePieceType.MINESHAFT_CROSSING, chainLength, type);
            this.direction = orientation;
            this.boundingBox = boundingBox;
            this.twoFloors = boundingBox.getBlockCountY() > 3;
        }

        public static BlockBox getBoundingBox(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation) {
            BlockBox blockBox = new BlockBox(x, y, z, x, y + 3 - 1, z);
            if (random.nextInt(4) == 0) {
                blockBox.maxY += 4;
            }
            switch (orientation) {
                default: {
                    blockBox.minX = x - 1;
                    blockBox.maxX = x + 3;
                    blockBox.minZ = z - 4;
                    break;
                }
                case SOUTH: {
                    blockBox.minX = x - 1;
                    blockBox.maxX = x + 3;
                    blockBox.maxZ = z + 3 + 1;
                    break;
                }
                case WEST: {
                    blockBox.minX = x - 4;
                    blockBox.minZ = z - 1;
                    blockBox.maxZ = z + 3;
                    break;
                }
                case EAST: {
                    blockBox.maxX = x + 3 + 1;
                    blockBox.minZ = z - 1;
                    blockBox.maxZ = z + 3;
                }
            }
            if (StructurePiece.getOverlappingPiece(pieces, blockBox) != null) {
                return null;
            }
            return blockBox;
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            int n = this.getChainLength();
            switch (this.direction) {
                default: {
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, n);
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, n);
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, n);
                    break;
                }
                case SOUTH: {
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, n);
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, n);
                    break;
                }
                case WEST: {
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, n);
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, n);
                    break;
                }
                case EAST: {
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, n);
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, n);
                }
            }
            if (this.twoFloors) {
                if (random.nextBoolean()) {
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, Direction.NORTH, n);
                }
                if (random.nextBoolean()) {
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, Direction.WEST, n);
                }
                if (random.nextBoolean()) {
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, Direction.EAST, n);
                }
                if (random.nextBoolean()) {
                    MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                }
            }
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.isTouchingLiquid(structureWorldAccess, boundingBox)) {
                return false;
            }
            BlockState blockState = this.getPlanksType();
            if (this.twoFloors) {
                this.fillWithOutline(structureWorldAccess, boundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, AIR, AIR, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, AIR, AIR, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, AIR, AIR, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, AIR, AIR, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, AIR, AIR, false);
            } else {
                this.fillWithOutline(structureWorldAccess, boundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, AIR, AIR, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, AIR, AIR, false);
            }
            this.generateCrossingPilliar(structureWorldAccess, boundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
            this.generateCrossingPilliar(structureWorldAccess, boundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);
            this.generateCrossingPilliar(structureWorldAccess, boundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
            this.generateCrossingPilliar(structureWorldAccess, boundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);
            for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i) {
                for (_snowman = this.boundingBox.minZ; _snowman <= this.boundingBox.maxZ; ++_snowman) {
                    if (!this.getBlockAt(structureWorldAccess, i, this.boundingBox.minY - 1, _snowman, boundingBox).isAir() || !this.isUnderSeaLevel(structureWorldAccess, i, this.boundingBox.minY - 1, _snowman, boundingBox)) continue;
                    this.addBlock(structureWorldAccess, blockState, i, this.boundingBox.minY - 1, _snowman, boundingBox);
                }
            }
            return true;
        }

        private void generateCrossingPilliar(StructureWorldAccess structureWorldAccess, BlockBox boundingBox, int x, int minY, int z, int maxY) {
            if (!this.getBlockAt(structureWorldAccess, x, maxY + 1, z, boundingBox).isAir()) {
                this.fillWithOutline(structureWorldAccess, boundingBox, x, minY, z, x, maxY, z, this.getPlanksType(), AIR, false);
            }
        }
    }

    public static class MineshaftCorridor
    extends MineshaftPart {
        private final boolean hasRails;
        private final boolean hasCobwebs;
        private boolean hasSpawner;
        private final int length;

        public MineshaftCorridor(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.MINESHAFT_CORRIDOR, compoundTag);
            this.hasRails = compoundTag.getBoolean("hr");
            this.hasCobwebs = compoundTag.getBoolean("sc");
            this.hasSpawner = compoundTag.getBoolean("hps");
            this.length = compoundTag.getInt("Num");
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putBoolean("hr", this.hasRails);
            tag.putBoolean("sc", this.hasCobwebs);
            tag.putBoolean("hps", this.hasSpawner);
            tag.putInt("Num", this.length);
        }

        public MineshaftCorridor(int chainLength, Random random, BlockBox boundingBox, Direction orientation, MineshaftFeature.Type type) {
            super(StructurePieceType.MINESHAFT_CORRIDOR, chainLength, type);
            this.setOrientation(orientation);
            this.boundingBox = boundingBox;
            this.hasRails = random.nextInt(3) == 0;
            this.hasCobwebs = !this.hasRails && random.nextInt(23) == 0;
            this.length = this.getFacing().getAxis() == Direction.Axis.Z ? boundingBox.getBlockCountZ() / 5 : boundingBox.getBlockCountX() / 5;
        }

        public static BlockBox getBoundingBox(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation) {
            int n;
            BlockBox blockBox = new BlockBox(x, y, z, x, y + 3 - 1, z);
            for (n = random.nextInt(3) + 2; n > 0; --n) {
                _snowman = n * 5;
                switch (orientation) {
                    default: {
                        blockBox.maxX = x + 3 - 1;
                        blockBox.minZ = z - (_snowman - 1);
                        break;
                    }
                    case SOUTH: {
                        blockBox.maxX = x + 3 - 1;
                        blockBox.maxZ = z + _snowman - 1;
                        break;
                    }
                    case WEST: {
                        blockBox.minX = x - (_snowman - 1);
                        blockBox.maxZ = z + 3 - 1;
                        break;
                    }
                    case EAST: {
                        blockBox.maxX = x + _snowman - 1;
                        blockBox.maxZ = z + 3 - 1;
                    }
                }
                if (StructurePiece.getOverlappingPiece(pieces, blockBox) == null) break;
            }
            if (n > 0) {
                return blockBox;
            }
            return null;
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            block24: {
                int n = this.getChainLength();
                _snowman = random.nextInt(4);
                Direction _snowman2 = this.getFacing();
                if (_snowman2 != null) {
                    switch (_snowman2) {
                        default: {
                            if (_snowman <= 1) {
                                MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ - 1, _snowman2, n);
                                break;
                            }
                            if (_snowman == 2) {
                                MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, Direction.WEST, n);
                                break;
                            }
                            MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, Direction.EAST, n);
                            break;
                        }
                        case SOUTH: {
                            if (_snowman <= 1) {
                                MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ + 1, _snowman2, n);
                                break;
                            }
                            if (_snowman == 2) {
                                MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ - 3, Direction.WEST, n);
                                break;
                            }
                            MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ - 3, Direction.EAST, n);
                            break;
                        }
                        case WEST: {
                            if (_snowman <= 1) {
                                MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, _snowman2, n);
                                break;
                            }
                            if (_snowman == 2) {
                                MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ - 1, Direction.NORTH, n);
                                break;
                            }
                            MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                            break;
                        }
                        case EAST: {
                            if (_snowman <= 1) {
                                MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, _snowman2, n);
                                break;
                            }
                            if (_snowman == 2) {
                                MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ - 1, Direction.NORTH, n);
                                break;
                            }
                            MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                        }
                    }
                }
                if (n >= 8) break block24;
                if (_snowman2 == Direction.NORTH || _snowman2 == Direction.SOUTH) {
                    _snowman = this.boundingBox.minZ + 3;
                    while (_snowman + 3 <= this.boundingBox.maxZ) {
                        _snowman = random.nextInt(5);
                        if (_snowman == 0) {
                            MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, _snowman, Direction.WEST, n + 1);
                        } else if (_snowman == 1) {
                            MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, _snowman, Direction.EAST, n + 1);
                        }
                        _snowman += 5;
                    }
                } else {
                    _snowman = this.boundingBox.minX + 3;
                    while (_snowman + 3 <= this.boundingBox.maxX) {
                        _snowman = random.nextInt(5);
                        if (_snowman == 0) {
                            MineshaftGenerator.pieceGenerator(start, pieces, random, _snowman, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, n + 1);
                        } else if (_snowman == 1) {
                            MineshaftGenerator.pieceGenerator(start, pieces, random, _snowman, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, n + 1);
                        }
                        _snowman += 5;
                    }
                }
            }
        }

        @Override
        protected boolean addChest(StructureWorldAccess structureWorldAccess, BlockBox boundingBox, Random random, int x, int y, int z, Identifier lootTableId) {
            BlockPos blockPos = new BlockPos(this.applyXTransform(x, z), this.applyYTransform(y), this.applyZTransform(x, z));
            if (boundingBox.contains(blockPos) && structureWorldAccess.getBlockState(blockPos).isAir() && !structureWorldAccess.getBlockState(blockPos.down()).isAir()) {
                BlockState blockState = (BlockState)Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, random.nextBoolean() ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST);
                this.addBlock(structureWorldAccess, blockState, x, y, z, boundingBox);
                ChestMinecartEntity _snowman2 = new ChestMinecartEntity(structureWorldAccess.toServerWorld(), (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
                _snowman2.setLootTable(lootTableId, random.nextLong());
                structureWorldAccess.spawnEntity(_snowman2);
                return true;
            }
            return false;
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            if (this.isTouchingLiquid(structureWorldAccess, boundingBox)) {
                return false;
            }
            boolean bl = false;
            int _snowman2 = 2;
            _snowman = false;
            int _snowman3 = 2;
            int _snowman4 = this.length * 5 - 1;
            BlockState _snowman5 = this.getPlanksType();
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 0, 0, 2, 1, _snowman4, AIR, AIR, false);
            this.fillWithOutlineUnderSeaLevel(structureWorldAccess, boundingBox, random, 0.8f, 0, 2, 0, 2, 2, _snowman4, AIR, AIR, false, false);
            if (this.hasCobwebs) {
                this.fillWithOutlineUnderSeaLevel(structureWorldAccess, boundingBox, random, 0.6f, 0, 0, 0, 2, 1, _snowman4, Blocks.COBWEB.getDefaultState(), AIR, false, true);
            }
            for (n = 0; n < this.length; ++n) {
                i = 2 + n * 5;
                this.generateSupports(structureWorldAccess, boundingBox, 0, 0, i, 2, 2, random);
                this.addCobwebsUnderground(structureWorldAccess, boundingBox, random, 0.1f, 0, 2, i - 1);
                this.addCobwebsUnderground(structureWorldAccess, boundingBox, random, 0.1f, 2, 2, i - 1);
                this.addCobwebsUnderground(structureWorldAccess, boundingBox, random, 0.1f, 0, 2, i + 1);
                this.addCobwebsUnderground(structureWorldAccess, boundingBox, random, 0.1f, 2, 2, i + 1);
                this.addCobwebsUnderground(structureWorldAccess, boundingBox, random, 0.05f, 0, 2, i - 2);
                this.addCobwebsUnderground(structureWorldAccess, boundingBox, random, 0.05f, 2, 2, i - 2);
                this.addCobwebsUnderground(structureWorldAccess, boundingBox, random, 0.05f, 0, 2, i + 2);
                this.addCobwebsUnderground(structureWorldAccess, boundingBox, random, 0.05f, 2, 2, i + 2);
                if (random.nextInt(100) == 0) {
                    this.addChest(structureWorldAccess, boundingBox, random, 2, 0, i - 1, LootTables.ABANDONED_MINESHAFT_CHEST);
                }
                if (random.nextInt(100) == 0) {
                    this.addChest(structureWorldAccess, boundingBox, random, 0, 0, i + 1, LootTables.ABANDONED_MINESHAFT_CHEST);
                }
                if (!this.hasCobwebs || this.hasSpawner) continue;
                _snowman = this.applyYTransform(0);
                _snowman = i - 1 + random.nextInt(3);
                _snowman7 = this.applyXTransform(1, _snowman);
                BlockPos blockPos2 = new BlockPos(_snowman7, _snowman, _snowman = this.applyZTransform(1, _snowman));
                if (!boundingBox.contains(blockPos2) || !this.isUnderSeaLevel(structureWorldAccess, 1, 0, _snowman, boundingBox)) continue;
                this.hasSpawner = true;
                structureWorldAccess.setBlockState(blockPos2, Blocks.SPAWNER.getDefaultState(), 2);
                BlockEntity _snowman6 = structureWorldAccess.getBlockEntity(blockPos2);
                if (!(_snowman6 instanceof MobSpawnerBlockEntity)) continue;
                ((MobSpawnerBlockEntity)_snowman6).getLogic().setEntityId(EntityType.CAVE_SPIDER);
            }
            for (n = 0; n <= 2; ++n) {
                for (i = 0; i <= _snowman4; ++i) {
                    _snowman = -1;
                    BlockState blockState = this.getBlockAt(structureWorldAccess, n, -1, i, boundingBox);
                    if (!blockState.isAir() || !this.isUnderSeaLevel(structureWorldAccess, n, -1, i, boundingBox)) continue;
                    int _snowman7 = -1;
                    this.addBlock(structureWorldAccess, _snowman5, n, -1, i, boundingBox);
                }
            }
            if (this.hasRails) {
                _snowman = (BlockState)Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.NORTH_SOUTH);
                for (int i = 0; i <= _snowman4; ++i) {
                    BlockState blockState = this.getBlockAt(structureWorldAccess, 1, -1, i, boundingBox);
                    if (blockState.isAir() || !blockState.isOpaqueFullCube(structureWorldAccess, new BlockPos(this.applyXTransform(1, i), this.applyYTransform(-1), this.applyZTransform(1, i)))) continue;
                    float _snowman8 = this.isUnderSeaLevel(structureWorldAccess, 1, 0, i, boundingBox) ? 0.7f : 0.9f;
                    this.addBlockWithRandomThreshold(structureWorldAccess, boundingBox, random, _snowman8, 1, 0, i, _snowman);
                }
            }
            return true;
        }

        private void generateSupports(StructureWorldAccess structureWorldAccess, BlockBox boundingBox, int minX, int minY, int z, int maxY, int maxX, Random random) {
            if (!this.isSolidCeiling(structureWorldAccess, boundingBox, minX, maxX, maxY, z)) {
                return;
            }
            BlockState blockState = this.getPlanksType();
            _snowman = this.getFenceType();
            this.fillWithOutline(structureWorldAccess, boundingBox, minX, minY, z, minX, maxY - 1, z, (BlockState)_snowman.with(FenceBlock.WEST, true), AIR, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, maxX, minY, z, maxX, maxY - 1, z, (BlockState)_snowman.with(FenceBlock.EAST, true), AIR, false);
            if (random.nextInt(4) == 0) {
                this.fillWithOutline(structureWorldAccess, boundingBox, minX, maxY, z, minX, maxY, z, blockState, AIR, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, maxX, maxY, z, maxX, maxY, z, blockState, AIR, false);
            } else {
                this.fillWithOutline(structureWorldAccess, boundingBox, minX, maxY, z, maxX, maxY, z, blockState, AIR, false);
                this.addBlockWithRandomThreshold(structureWorldAccess, boundingBox, random, 0.05f, minX + 1, maxY, z - 1, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.NORTH));
                this.addBlockWithRandomThreshold(structureWorldAccess, boundingBox, random, 0.05f, minX + 1, maxY, z + 1, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.SOUTH));
            }
        }

        private void addCobwebsUnderground(StructureWorldAccess structureWorldAccess, BlockBox boundingBox, Random random, float threshold, int x, int y, int z) {
            if (this.isUnderSeaLevel(structureWorldAccess, x, y, z, boundingBox)) {
                this.addBlockWithRandomThreshold(structureWorldAccess, boundingBox, random, threshold, x, y, z, Blocks.COBWEB.getDefaultState());
            }
        }
    }

    public static class MineshaftRoom
    extends MineshaftPart {
        private final List<BlockBox> entrances = Lists.newLinkedList();

        public MineshaftRoom(int chainLength, Random random, int x, int z, MineshaftFeature.Type type) {
            super(StructurePieceType.MINESHAFT_ROOM, chainLength, type);
            this.mineshaftType = type;
            this.boundingBox = new BlockBox(x, 50, z, x + 7 + random.nextInt(6), 54 + random.nextInt(6), z + 7 + random.nextInt(6));
        }

        public MineshaftRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.MINESHAFT_ROOM, compoundTag);
            ListTag listTag = compoundTag.getList("Entrances", 11);
            for (int i = 0; i < listTag.size(); ++i) {
                this.entrances.add(new BlockBox(listTag.getIntArray(i)));
            }
        }

        @Override
        public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
            int n;
            BlockBox _snowman2;
            MineshaftPart mineshaftPart;
            int n2 = this.getChainLength();
            _snowman = this.boundingBox.getBlockCountY() - 3 - 1;
            if (_snowman <= 0) {
                _snowman = 1;
            }
            for (n = 0; n < this.boundingBox.getBlockCountX() && (n += random.nextInt(this.boundingBox.getBlockCountX())) + 3 <= this.boundingBox.getBlockCountX(); n += 4) {
                mineshaftPart = MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + n, this.boundingBox.minY + random.nextInt(_snowman) + 1, this.boundingBox.minZ - 1, Direction.NORTH, n2);
                if (mineshaftPart == null) continue;
                _snowman2 = mineshaftPart.getBoundingBox();
                this.entrances.add(new BlockBox(_snowman2.minX, _snowman2.minY, this.boundingBox.minZ, _snowman2.maxX, _snowman2.maxY, this.boundingBox.minZ + 1));
            }
            for (n = 0; n < this.boundingBox.getBlockCountX() && (n += random.nextInt(this.boundingBox.getBlockCountX())) + 3 <= this.boundingBox.getBlockCountX(); n += 4) {
                mineshaftPart = MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX + n, this.boundingBox.minY + random.nextInt(_snowman) + 1, this.boundingBox.maxZ + 1, Direction.SOUTH, n2);
                if (mineshaftPart == null) continue;
                _snowman2 = mineshaftPart.getBoundingBox();
                this.entrances.add(new BlockBox(_snowman2.minX, _snowman2.minY, this.boundingBox.maxZ - 1, _snowman2.maxX, _snowman2.maxY, this.boundingBox.maxZ));
            }
            for (n = 0; n < this.boundingBox.getBlockCountZ() && (n += random.nextInt(this.boundingBox.getBlockCountZ())) + 3 <= this.boundingBox.getBlockCountZ(); n += 4) {
                mineshaftPart = MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + random.nextInt(_snowman) + 1, this.boundingBox.minZ + n, Direction.WEST, n2);
                if (mineshaftPart == null) continue;
                _snowman2 = mineshaftPart.getBoundingBox();
                this.entrances.add(new BlockBox(this.boundingBox.minX, _snowman2.minY, _snowman2.minZ, this.boundingBox.minX + 1, _snowman2.maxY, _snowman2.maxZ));
            }
            for (n = 0; n < this.boundingBox.getBlockCountZ() && (n += random.nextInt(this.boundingBox.getBlockCountZ())) + 3 <= this.boundingBox.getBlockCountZ(); n += 4) {
                mineshaftPart = MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + random.nextInt(_snowman) + 1, this.boundingBox.minZ + n, Direction.EAST, n2);
                if (mineshaftPart == null) continue;
                _snowman2 = mineshaftPart.getBoundingBox();
                this.entrances.add(new BlockBox(this.boundingBox.maxX - 1, _snowman2.minY, _snowman2.minZ, this.boundingBox.maxX, _snowman2.maxY, _snowman2.maxZ));
            }
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            StructureWorldAccess structureWorldAccess2;
            if (this.isTouchingLiquid(structureWorldAccess2, boundingBox)) {
                return false;
            }
            this.fillWithOutline(structureWorldAccess2, boundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.DIRT.getDefaultState(), AIR, true);
            this.fillWithOutline(structureWorldAccess2, boundingBox, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, AIR, AIR, false);
            for (BlockBox blockBox : this.entrances) {
                this.fillWithOutline(structureWorldAccess2, boundingBox, blockBox.minX, blockBox.maxY - 2, blockBox.minZ, blockBox.maxX, blockBox.maxY, blockBox.maxZ, AIR, AIR, false);
            }
            this.fillHalfEllipsoid(structureWorldAccess2, boundingBox, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, AIR, false);
            return true;
        }

        @Override
        public void translate(int x, int y, int z) {
            super.translate(x, y, z);
            for (BlockBox blockBox : this.entrances) {
                blockBox.move(x, y, z);
            }
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            ListTag listTag = new ListTag();
            for (BlockBox blockBox : this.entrances) {
                listTag.add(blockBox.toNbt());
            }
            tag.put("Entrances", listTag);
        }
    }

    static abstract class MineshaftPart
    extends StructurePiece {
        protected MineshaftFeature.Type mineshaftType;

        public MineshaftPart(StructurePieceType structurePieceType, int chainLength, MineshaftFeature.Type type) {
            super(structurePieceType, chainLength);
            this.mineshaftType = type;
        }

        public MineshaftPart(StructurePieceType structurePieceType, CompoundTag compoundTag) {
            super(structurePieceType, compoundTag);
            this.mineshaftType = MineshaftFeature.Type.byIndex(compoundTag.getInt("MST"));
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            tag.putInt("MST", this.mineshaftType.ordinal());
        }

        protected BlockState getPlanksType() {
            switch (this.mineshaftType) {
                default: {
                    return Blocks.OAK_PLANKS.getDefaultState();
                }
                case MESA: 
            }
            return Blocks.DARK_OAK_PLANKS.getDefaultState();
        }

        protected BlockState getFenceType() {
            switch (this.mineshaftType) {
                default: {
                    return Blocks.OAK_FENCE.getDefaultState();
                }
                case MESA: 
            }
            return Blocks.DARK_OAK_FENCE.getDefaultState();
        }

        protected boolean isSolidCeiling(BlockView blockView, BlockBox boundingBox, int minX, int maxX, int y, int z) {
            for (int i = minX; i <= maxX; ++i) {
                if (!this.getBlockAt(blockView, i, y + 1, z, boundingBox).isAir()) continue;
                return false;
            }
            return true;
        }
    }
}

