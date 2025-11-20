/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 */
package net.minecraft.structure;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ElderGuardianEntity;
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

public class OceanMonumentGenerator {

    static class DoubleYZRoomFactory
    implements PieceFactory {
        private DoubleYZRoomFactory() {
        }

        @Override
        public boolean canGenerate(PieceSetting setting) {
            if (setting.neighborPresences[Direction.NORTH.getId()] && !setting.neighbors[Direction.NORTH.getId()].used && setting.neighborPresences[Direction.UP.getId()] && !setting.neighbors[Direction.UP.getId()].used) {
                PieceSetting pieceSetting = setting.neighbors[Direction.NORTH.getId()];
                return pieceSetting.neighborPresences[Direction.UP.getId()] && !pieceSetting.neighbors[Direction.UP.getId()].used;
            }
            return false;
        }

        @Override
        public Piece generate(Direction direction, PieceSetting setting, Random random) {
            setting.used = true;
            setting.neighbors[Direction.NORTH.getId()].used = true;
            setting.neighbors[Direction.UP.getId()].used = true;
            setting.neighbors[Direction.NORTH.getId()].neighbors[Direction.UP.getId()].used = true;
            return new DoubleYZRoom(direction, setting);
        }
    }

    static class DoubleXYRoomFactory
    implements PieceFactory {
        private DoubleXYRoomFactory() {
        }

        @Override
        public boolean canGenerate(PieceSetting setting) {
            if (setting.neighborPresences[Direction.EAST.getId()] && !setting.neighbors[Direction.EAST.getId()].used && setting.neighborPresences[Direction.UP.getId()] && !setting.neighbors[Direction.UP.getId()].used) {
                PieceSetting pieceSetting = setting.neighbors[Direction.EAST.getId()];
                return pieceSetting.neighborPresences[Direction.UP.getId()] && !pieceSetting.neighbors[Direction.UP.getId()].used;
            }
            return false;
        }

        @Override
        public Piece generate(Direction direction, PieceSetting setting, Random random) {
            setting.used = true;
            setting.neighbors[Direction.EAST.getId()].used = true;
            setting.neighbors[Direction.UP.getId()].used = true;
            setting.neighbors[Direction.EAST.getId()].neighbors[Direction.UP.getId()].used = true;
            return new DoubleXYRoom(direction, setting);
        }
    }

    static class DoubleZRoomFactory
    implements PieceFactory {
        private DoubleZRoomFactory() {
        }

        @Override
        public boolean canGenerate(PieceSetting setting) {
            return setting.neighborPresences[Direction.NORTH.getId()] && !setting.neighbors[Direction.NORTH.getId()].used;
        }

        @Override
        public Piece generate(Direction direction, PieceSetting setting, Random random) {
            PieceSetting pieceSetting = setting;
            if (!setting.neighborPresences[Direction.NORTH.getId()] || setting.neighbors[Direction.NORTH.getId()].used) {
                pieceSetting = setting.neighbors[Direction.SOUTH.getId()];
            }
            pieceSetting.used = true;
            pieceSetting.neighbors[Direction.NORTH.getId()].used = true;
            return new DoubleZRoom(direction, pieceSetting);
        }
    }

    static class DoubleXRoomFactory
    implements PieceFactory {
        private DoubleXRoomFactory() {
        }

        @Override
        public boolean canGenerate(PieceSetting setting) {
            return setting.neighborPresences[Direction.EAST.getId()] && !setting.neighbors[Direction.EAST.getId()].used;
        }

        @Override
        public Piece generate(Direction direction, PieceSetting setting, Random random) {
            setting.used = true;
            setting.neighbors[Direction.EAST.getId()].used = true;
            return new DoubleXRoom(direction, setting);
        }
    }

    static class DoubleYRoomFactory
    implements PieceFactory {
        private DoubleYRoomFactory() {
        }

        @Override
        public boolean canGenerate(PieceSetting setting) {
            return setting.neighborPresences[Direction.UP.getId()] && !setting.neighbors[Direction.UP.getId()].used;
        }

        @Override
        public Piece generate(Direction direction, PieceSetting setting, Random random) {
            setting.used = true;
            setting.neighbors[Direction.UP.getId()].used = true;
            return new DoubleYRoom(direction, setting);
        }
    }

    static class SimpleRoomTopFactory
    implements PieceFactory {
        private SimpleRoomTopFactory() {
        }

        @Override
        public boolean canGenerate(PieceSetting setting) {
            return !setting.neighborPresences[Direction.WEST.getId()] && !setting.neighborPresences[Direction.EAST.getId()] && !setting.neighborPresences[Direction.NORTH.getId()] && !setting.neighborPresences[Direction.SOUTH.getId()] && !setting.neighborPresences[Direction.UP.getId()];
        }

        @Override
        public Piece generate(Direction direction, PieceSetting setting, Random random) {
            setting.used = true;
            return new SimpleRoomTop(direction, setting);
        }
    }

    static class SimpleRoomFactory
    implements PieceFactory {
        private SimpleRoomFactory() {
        }

        @Override
        public boolean canGenerate(PieceSetting setting) {
            return true;
        }

        @Override
        public Piece generate(Direction direction, PieceSetting setting, Random random) {
            setting.used = true;
            return new SimpleRoom(direction, setting, random);
        }
    }

    static interface PieceFactory {
        public boolean canGenerate(PieceSetting var1);

        public Piece generate(Direction var1, PieceSetting var2, Random var3);
    }

    static class PieceSetting {
        private final int roomIndex;
        private final PieceSetting[] neighbors = new PieceSetting[6];
        private final boolean[] neighborPresences = new boolean[6];
        private boolean used;
        private boolean field_14484;
        private int field_14483;

        public PieceSetting(int index) {
            this.roomIndex = index;
        }

        public void setNeighbor(Direction direction, PieceSetting pieceSetting) {
            this.neighbors[direction.getId()] = pieceSetting;
            pieceSetting.neighbors[direction.getOpposite().getId()] = this;
        }

        public void checkNeighborStates() {
            for (int i = 0; i < 6; ++i) {
                this.neighborPresences[i] = this.neighbors[i] != null;
            }
        }

        public boolean method_14783(int n) {
            if (this.field_14484) {
                return true;
            }
            this.field_14483 = n;
            for (_snowman = 0; _snowman < 6; ++_snowman) {
                if (this.neighbors[_snowman] == null || !this.neighborPresences[_snowman] || this.neighbors[_snowman].field_14483 == n || !this.neighbors[_snowman].method_14783(n)) continue;
                return true;
            }
            return false;
        }

        public boolean isAboveLevelThree() {
            return this.roomIndex >= 75;
        }

        public int countNeighbors() {
            int n = 0;
            for (_snowman = 0; _snowman < 6; ++_snowman) {
                if (!this.neighborPresences[_snowman]) continue;
                ++n;
            }
            return n;
        }
    }

    public static class Penthouse
    extends Piece {
        public Penthouse(Direction direction, BlockBox blockBox) {
            super(StructurePieceType.OCEAN_MONUMENT_PENTHOUSE, direction, blockBox);
        }

        public Penthouse(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_PENTHOUSE, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            StructureWorldAccess structureWorldAccess2;
            int _snowman2;
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, -1, 2, 11, -1, 11, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, -1, 0, 1, -1, 11, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 12, -1, 0, 13, -1, 11, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, -1, 0, 11, -1, 1, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, -1, 12, 11, -1, 13, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 0, 0, 0, 0, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 13, 0, 0, 13, 0, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 0, 0, 12, 0, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 0, 13, 12, 0, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            for (_snowman2 = 2; _snowman2 <= 11; _snowman2 += 3) {
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 0, 0, _snowman2, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 13, 0, _snowman2, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, _snowman2, 0, 0, boundingBox);
            }
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 0, 3, 4, 0, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 9, 0, 3, 11, 0, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 4, 0, 9, 9, 0, 11, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 5, 0, 8, boundingBox);
            this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 8, 0, 8, boundingBox);
            this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 10, 0, 10, boundingBox);
            this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 3, 0, 10, boundingBox);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 3, 0, 3, 3, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 10, 0, 3, 10, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 6, 0, 10, 7, 0, 10, DARK_PRISMARINE, DARK_PRISMARINE, false);
            _snowman2 = 3;
            for (int i = 0; i < 2; ++i) {
                for (_snowman = 2; _snowman <= 8; _snowman += 3) {
                    this.fillWithOutline(structureWorldAccess2, boundingBox, _snowman2, 0, _snowman, _snowman2, 2, _snowman, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                _snowman2 = 10;
            }
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 0, 10, 5, 2, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 0, 10, 8, 2, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 6, -1, 7, 7, -1, 8, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.setAirAndWater(structureWorldAccess2, boundingBox, 6, -1, 3, 7, -1, 4);
            this.method_14772(structureWorldAccess2, boundingBox, 6, 1, 6);
            return true;
        }
    }

    public static class WingRoom
    extends Piece {
        private int field_14481;

        public WingRoom(Direction direction, BlockBox blockBox, int n) {
            super(StructurePieceType.OCEAN_MONUMENT_WING_ROOM, direction, blockBox);
            this.field_14481 = n & 1;
        }

        public WingRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_WING_ROOM, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            StructureWorldAccess structureWorldAccess2;
            if (this.field_14481 == 0) {
                int n;
                for (n = 0; n < 4; ++n) {
                    this.fillWithOutline(structureWorldAccess2, boundingBox, 10 - n, 3 - n, 20 - n, 12 + n, 3 - n, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 0, 6, 15, 0, 16, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 6, 0, 6, 6, 3, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 16, 0, 6, 16, 3, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 1, 7, 7, 1, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 15, 1, 7, 15, 1, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 1, 6, 9, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 13, 1, 6, 15, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 1, 7, 9, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 13, 1, 7, 14, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 9, 0, 5, 13, 0, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 10, 0, 7, 12, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 0, 10, 8, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 14, 0, 10, 14, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, false);
                for (n = 18; n >= 7; n -= 3) {
                    this.addBlock(structureWorldAccess2, SEA_LANTERN, 6, 3, n, boundingBox);
                    this.addBlock(structureWorldAccess2, SEA_LANTERN, 16, 3, n, boundingBox);
                }
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 10, 0, 10, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 12, 0, 10, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 10, 0, 12, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 12, 0, 12, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 8, 3, 6, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 14, 3, 6, boundingBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 4, 2, 4, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 4, 1, 4, boundingBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 4, 0, 4, boundingBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 18, 2, 4, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 18, 1, 4, boundingBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 18, 0, 4, boundingBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 4, 2, 18, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 4, 1, 18, boundingBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 4, 0, 18, boundingBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 18, 2, 18, boundingBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 18, 1, 18, boundingBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 18, 0, 18, boundingBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 9, 7, 20, boundingBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 13, 7, 20, boundingBox);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 6, 0, 21, 7, 4, 21, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 15, 0, 21, 16, 4, 21, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.method_14772(structureWorldAccess2, boundingBox, 11, 2, 16);
            } else if (this.field_14481 == 1) {
                int n;
                this.fillWithOutline(structureWorldAccess2, boundingBox, 9, 3, 18, 13, 3, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 9, 0, 18, 9, 2, 18, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 13, 0, 18, 13, 2, 18, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                int _snowman2 = 9;
                int _snowman3 = 20;
                int _snowman4 = 5;
                for (n = 0; n < 2; ++n) {
                    this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, _snowman2, 6, 20, boundingBox);
                    this.addBlock(structureWorldAccess2, SEA_LANTERN, _snowman2, 5, 20, boundingBox);
                    this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, _snowman2, 4, 20, boundingBox);
                    _snowman2 = 13;
                }
                this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 3, 7, 15, 3, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                _snowman2 = 10;
                for (n = 0; n < 2; ++n) {
                    this.fillWithOutline(structureWorldAccess2, boundingBox, _snowman2, 0, 10, _snowman2, 6, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess2, boundingBox, _snowman2, 0, 12, _snowman2, 6, 12, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.addBlock(structureWorldAccess2, SEA_LANTERN, _snowman2, 0, 10, boundingBox);
                    this.addBlock(structureWorldAccess2, SEA_LANTERN, _snowman2, 0, 12, boundingBox);
                    this.addBlock(structureWorldAccess2, SEA_LANTERN, _snowman2, 4, 10, boundingBox);
                    this.addBlock(structureWorldAccess2, SEA_LANTERN, _snowman2, 4, 12, boundingBox);
                    _snowman2 = 12;
                }
                _snowman2 = 8;
                for (n = 0; n < 2; ++n) {
                    this.fillWithOutline(structureWorldAccess2, boundingBox, _snowman2, 0, 7, _snowman2, 2, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess2, boundingBox, _snowman2, 0, 14, _snowman2, 2, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    _snowman2 = 14;
                }
                this.fillWithOutline(structureWorldAccess2, boundingBox, 8, 3, 8, 8, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 14, 3, 8, 14, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.method_14772(structureWorldAccess2, boundingBox, 11, 5, 13);
            }
            return true;
        }
    }

    public static class CoreRoom
    extends Piece {
        public CoreRoom(Direction direction, PieceSetting pieceSetting) {
            super(StructurePieceType.OCEAN_MONUMENT_CORE_ROOM, 1, direction, pieceSetting, 2, 2, 2);
        }

        public CoreRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_CORE_ROOM, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.method_14771(structureWorldAccess, boundingBox, 1, 8, 0, 14, 8, 14, PRISMARINE);
            int n = 7;
            BlockState _snowman2 = PRISMARINE_BRICKS;
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 7, 0, 0, 7, 15, _snowman2, _snowman2, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 15, 7, 0, 15, 7, 15, _snowman2, _snowman2, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 7, 0, 15, 7, 0, _snowman2, _snowman2, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 7, 15, 14, 7, 15, _snowman2, _snowman2, false);
            for (n = 1; n <= 6; ++n) {
                _snowman2 = PRISMARINE_BRICKS;
                if (n == 2 || n == 6) {
                    _snowman2 = PRISMARINE;
                }
                for (_snowman = 0; _snowman <= 15; _snowman += 15) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, n, 0, _snowman, n, 1, _snowman2, _snowman2, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, n, 6, _snowman, n, 9, _snowman2, _snowman2, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, n, 14, _snowman, n, 15, _snowman2, _snowman2, false);
                }
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, n, 0, 1, n, 0, _snowman2, _snowman2, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 6, n, 0, 9, n, 0, _snowman2, _snowman2, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 14, n, 0, 14, n, 0, _snowman2, _snowman2, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, n, 15, 14, n, 15, _snowman2, _snowman2, false);
            }
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 3, 6, 9, 6, 9, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState(), false);
            for (n = 3; n <= 6; n += 3) {
                for (_snowman = 6; _snowman <= 9; _snowman += 3) {
                    this.addBlock(structureWorldAccess, SEA_LANTERN, _snowman, n, 6, boundingBox);
                    this.addBlock(structureWorldAccess, SEA_LANTERN, _snowman, n, 9, boundingBox);
                }
            }
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 6, 5, 2, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 9, 5, 2, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 10, 1, 6, 10, 2, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 10, 1, 9, 10, 2, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 5, 6, 2, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 9, 1, 5, 9, 2, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 10, 6, 2, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 9, 1, 10, 9, 2, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 2, 5, 5, 6, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 2, 10, 5, 6, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 10, 2, 5, 10, 6, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 10, 2, 10, 10, 6, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 7, 1, 5, 7, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 10, 7, 1, 10, 7, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 7, 9, 5, 7, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 10, 7, 9, 10, 7, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 7, 5, 6, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 7, 10, 6, 7, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 9, 7, 5, 14, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 9, 7, 10, 14, 7, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 1, 2, 2, 1, 3, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 3, 1, 2, 3, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 13, 1, 2, 13, 1, 3, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 12, 1, 2, 12, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 1, 12, 2, 1, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 3, 1, 13, 3, 1, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 13, 1, 12, 13, 1, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 12, 1, 13, 12, 1, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            return true;
        }
    }

    public static class DoubleYZRoom
    extends Piece {
        public DoubleYZRoom(Direction direction, PieceSetting pieceSetting) {
            super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_Z_ROOM, 1, direction, pieceSetting, 1, 2, 2);
        }

        public DoubleYZRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_Z_ROOM, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            PieceSetting pieceSetting;
            BlockState blockState;
            int n;
            PieceSetting pieceSetting2 = this.setting.neighbors[Direction.NORTH.getId()];
            pieceSetting = this.setting;
            _snowman = pieceSetting2.neighbors[Direction.UP.getId()];
            _snowman = pieceSetting.neighbors[Direction.UP.getId()];
            if (this.setting.roomIndex / 25 > 0) {
                this.method_14774(structureWorldAccess, boundingBox, 0, 8, pieceSetting2.neighborPresences[Direction.DOWN.getId()]);
                this.method_14774(structureWorldAccess, boundingBox, 0, 0, pieceSetting.neighborPresences[Direction.DOWN.getId()]);
            }
            if (_snowman.neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess, boundingBox, 1, 8, 1, 6, 8, 7, PRISMARINE);
            }
            if (_snowman.neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess, boundingBox, 1, 8, 8, 6, 8, 14, PRISMARINE);
            }
            for (n = 1; n <= 7; ++n) {
                blockState = PRISMARINE_BRICKS;
                if (n == 2 || n == 6) {
                    blockState = PRISMARINE;
                }
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, n, 0, 0, n, 15, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 7, n, 0, 7, n, 15, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, n, 0, 6, n, 0, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, n, 15, 6, n, 15, blockState, blockState, false);
            }
            for (n = 1; n <= 7; ++n) {
                blockState = DARK_PRISMARINE;
                if (n == 2 || n == 6) {
                    blockState = SEA_LANTERN;
                }
                this.fillWithOutline(structureWorldAccess, boundingBox, 3, n, 7, 4, n, 8, blockState, blockState, false);
            }
            if (pieceSetting.neighborPresences[Direction.SOUTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 3, 1, 0, 4, 2, 0);
            }
            if (pieceSetting.neighborPresences[Direction.EAST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 7, 1, 3, 7, 2, 4);
            }
            if (pieceSetting.neighborPresences[Direction.WEST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 0, 1, 3, 0, 2, 4);
            }
            if (pieceSetting2.neighborPresences[Direction.NORTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 3, 1, 15, 4, 2, 15);
            }
            if (pieceSetting2.neighborPresences[Direction.WEST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 0, 1, 11, 0, 2, 12);
            }
            if (pieceSetting2.neighborPresences[Direction.EAST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 7, 1, 11, 7, 2, 12);
            }
            if (_snowman.neighborPresences[Direction.SOUTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 3, 5, 0, 4, 6, 0);
            }
            if (_snowman.neighborPresences[Direction.EAST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 7, 5, 3, 7, 6, 4);
                this.fillWithOutline(structureWorldAccess, boundingBox, 5, 4, 2, 6, 4, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 2, 6, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 5, 6, 3, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }
            if (_snowman.neighborPresences[Direction.WEST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 0, 5, 3, 0, 6, 4);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 4, 2, 2, 4, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 2, 1, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 5, 1, 3, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }
            if (_snowman.neighborPresences[Direction.NORTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 3, 5, 15, 4, 6, 15);
            }
            if (_snowman.neighborPresences[Direction.WEST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 0, 5, 11, 0, 6, 12);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 4, 10, 2, 4, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 10, 1, 3, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 13, 1, 3, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }
            if (_snowman.neighborPresences[Direction.EAST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 7, 5, 11, 7, 6, 12);
                this.fillWithOutline(structureWorldAccess, boundingBox, 5, 4, 10, 6, 4, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 10, 6, 3, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 13, 6, 3, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }
            return true;
        }
    }

    public static class DoubleXYRoom
    extends Piece {
        public DoubleXYRoom(Direction direction, PieceSetting pieceSetting) {
            super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_Y_ROOM, 1, direction, pieceSetting, 2, 2, 1);
        }

        public DoubleXYRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_Y_ROOM, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            StructureWorldAccess structureWorldAccess2;
            PieceSetting pieceSetting = this.setting.neighbors[Direction.EAST.getId()];
            _snowman = this.setting;
            _snowman = _snowman.neighbors[Direction.UP.getId()];
            _snowman = pieceSetting.neighbors[Direction.UP.getId()];
            if (this.setting.roomIndex / 25 > 0) {
                this.method_14774(structureWorldAccess2, boundingBox, 8, 0, pieceSetting.neighborPresences[Direction.DOWN.getId()]);
                this.method_14774(structureWorldAccess2, boundingBox, 0, 0, _snowman.neighborPresences[Direction.DOWN.getId()]);
            }
            if (_snowman.neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess2, boundingBox, 1, 8, 1, 7, 8, 6, PRISMARINE);
            }
            if (_snowman.neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess2, boundingBox, 8, 8, 1, 14, 8, 6, PRISMARINE);
            }
            for (int i = 1; i <= 7; ++i) {
                BlockState blockState = PRISMARINE_BRICKS;
                if (i == 2 || i == 6) {
                    blockState = PRISMARINE;
                }
                this.fillWithOutline(structureWorldAccess2, boundingBox, 0, i, 0, 0, i, 7, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 15, i, 0, 15, i, 7, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 1, i, 0, 15, i, 0, blockState, blockState, false);
                this.fillWithOutline(structureWorldAccess2, boundingBox, 1, i, 7, 14, i, 7, blockState, blockState, false);
            }
            this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 1, 3, 2, 7, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 3, 1, 2, 4, 7, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 3, 1, 5, 4, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 13, 1, 3, 13, 7, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 11, 1, 2, 12, 7, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 11, 1, 5, 12, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 1, 3, 5, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 10, 1, 3, 10, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 7, 2, 10, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 5, 2, 5, 7, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 10, 5, 2, 10, 7, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 5, 5, 5, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 10, 5, 5, 10, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 6, 6, 2, boundingBox);
            this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 9, 6, 2, boundingBox);
            this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 6, 6, 5, boundingBox);
            this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 9, 6, 5, boundingBox);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 4, 3, 6, 4, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 9, 4, 3, 10, 4, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.addBlock(structureWorldAccess2, SEA_LANTERN, 5, 4, 2, boundingBox);
            this.addBlock(structureWorldAccess2, SEA_LANTERN, 5, 4, 5, boundingBox);
            this.addBlock(structureWorldAccess2, SEA_LANTERN, 10, 4, 2, boundingBox);
            this.addBlock(structureWorldAccess2, SEA_LANTERN, 10, 4, 5, boundingBox);
            if (_snowman.neighborPresences[Direction.SOUTH.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 3, 1, 0, 4, 2, 0);
            }
            if (_snowman.neighborPresences[Direction.NORTH.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 3, 1, 7, 4, 2, 7);
            }
            if (_snowman.neighborPresences[Direction.WEST.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 0, 1, 3, 0, 2, 4);
            }
            if (pieceSetting.neighborPresences[Direction.SOUTH.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 11, 1, 0, 12, 2, 0);
            }
            if (pieceSetting.neighborPresences[Direction.NORTH.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 11, 1, 7, 12, 2, 7);
            }
            if (pieceSetting.neighborPresences[Direction.EAST.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 15, 1, 3, 15, 2, 4);
            }
            if (_snowman.neighborPresences[Direction.SOUTH.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 3, 5, 0, 4, 6, 0);
            }
            if (_snowman.neighborPresences[Direction.NORTH.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 3, 5, 7, 4, 6, 7);
            }
            if (_snowman.neighborPresences[Direction.WEST.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 0, 5, 3, 0, 6, 4);
            }
            if (_snowman.neighborPresences[Direction.SOUTH.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 11, 5, 0, 12, 6, 0);
            }
            if (_snowman.neighborPresences[Direction.NORTH.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 11, 5, 7, 12, 6, 7);
            }
            if (_snowman.neighborPresences[Direction.EAST.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 15, 5, 3, 15, 6, 4);
            }
            return true;
        }
    }

    public static class DoubleZRoom
    extends Piece {
        public DoubleZRoom(Direction direction, PieceSetting pieceSetting) {
            super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Z_ROOM, 1, direction, pieceSetting, 1, 1, 2);
        }

        public DoubleZRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Z_ROOM, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            PieceSetting pieceSetting = this.setting.neighbors[Direction.NORTH.getId()];
            _snowman = this.setting;
            if (this.setting.roomIndex / 25 > 0) {
                this.method_14774(structureWorldAccess, boundingBox, 0, 8, pieceSetting.neighborPresences[Direction.DOWN.getId()]);
                this.method_14774(structureWorldAccess, boundingBox, 0, 0, _snowman.neighborPresences[Direction.DOWN.getId()]);
            }
            if (_snowman.neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess, boundingBox, 1, 4, 1, 6, 4, 7, PRISMARINE);
            }
            if (pieceSetting.neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess, boundingBox, 1, 4, 8, 6, 4, 14, PRISMARINE);
            }
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 0, 0, 3, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 7, 3, 0, 7, 3, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 0, 7, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 15, 6, 3, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 0, 2, 15, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 7, 2, 0, 7, 2, 15, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 0, 7, 2, 0, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 15, 6, 2, 15, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 0, 0, 1, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 7, 1, 0, 7, 1, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 0, 7, 1, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 15, 6, 1, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 1, 1, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 1, 6, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 1, 1, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 3, 1, 6, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 13, 1, 1, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 13, 6, 1, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 13, 1, 3, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 3, 13, 6, 3, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 1, 6, 2, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 6, 5, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 1, 9, 2, 3, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 9, 5, 3, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 3, 2, 6, 4, 2, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 3, 2, 9, 4, 2, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 2, 7, 2, 2, 8, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 2, 7, 5, 2, 8, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.addBlock(structureWorldAccess, SEA_LANTERN, 2, 2, 5, boundingBox);
            this.addBlock(structureWorldAccess, SEA_LANTERN, 5, 2, 5, boundingBox);
            this.addBlock(structureWorldAccess, SEA_LANTERN, 2, 2, 10, boundingBox);
            this.addBlock(structureWorldAccess, SEA_LANTERN, 5, 2, 10, boundingBox);
            this.addBlock(structureWorldAccess, PRISMARINE_BRICKS, 2, 3, 5, boundingBox);
            this.addBlock(structureWorldAccess, PRISMARINE_BRICKS, 5, 3, 5, boundingBox);
            this.addBlock(structureWorldAccess, PRISMARINE_BRICKS, 2, 3, 10, boundingBox);
            this.addBlock(structureWorldAccess, PRISMARINE_BRICKS, 5, 3, 10, boundingBox);
            if (_snowman.neighborPresences[Direction.SOUTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 3, 1, 0, 4, 2, 0);
            }
            if (_snowman.neighborPresences[Direction.EAST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 7, 1, 3, 7, 2, 4);
            }
            if (_snowman.neighborPresences[Direction.WEST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 0, 1, 3, 0, 2, 4);
            }
            if (pieceSetting.neighborPresences[Direction.NORTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 3, 1, 15, 4, 2, 15);
            }
            if (pieceSetting.neighborPresences[Direction.WEST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 0, 1, 11, 0, 2, 12);
            }
            if (pieceSetting.neighborPresences[Direction.EAST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 7, 1, 11, 7, 2, 12);
            }
            return true;
        }
    }

    public static class DoubleXRoom
    extends Piece {
        public DoubleXRoom(Direction direction, PieceSetting pieceSetting) {
            super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_ROOM, 1, direction, pieceSetting, 2, 1, 1);
        }

        public DoubleXRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_ROOM, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            PieceSetting pieceSetting = this.setting.neighbors[Direction.EAST.getId()];
            _snowman = this.setting;
            if (this.setting.roomIndex / 25 > 0) {
                this.method_14774(structureWorldAccess, boundingBox, 8, 0, pieceSetting.neighborPresences[Direction.DOWN.getId()]);
                this.method_14774(structureWorldAccess, boundingBox, 0, 0, _snowman.neighborPresences[Direction.DOWN.getId()]);
            }
            if (_snowman.neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess, boundingBox, 1, 4, 1, 7, 4, 6, PRISMARINE);
            }
            if (pieceSetting.neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess, boundingBox, 8, 4, 1, 14, 4, 6, PRISMARINE);
            }
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 0, 0, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 15, 3, 0, 15, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 0, 15, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 7, 14, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 0, 2, 7, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 15, 2, 0, 15, 2, 7, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 0, 15, 2, 0, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 7, 14, 2, 7, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 0, 0, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 15, 1, 0, 15, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 0, 15, 1, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 7, 14, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 0, 10, 1, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 2, 0, 9, 2, 3, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 3, 0, 10, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.addBlock(structureWorldAccess, SEA_LANTERN, 6, 2, 3, boundingBox);
            this.addBlock(structureWorldAccess, SEA_LANTERN, 9, 2, 3, boundingBox);
            if (_snowman.neighborPresences[Direction.SOUTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 3, 1, 0, 4, 2, 0);
            }
            if (_snowman.neighborPresences[Direction.NORTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 3, 1, 7, 4, 2, 7);
            }
            if (_snowman.neighborPresences[Direction.WEST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 0, 1, 3, 0, 2, 4);
            }
            if (pieceSetting.neighborPresences[Direction.SOUTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 11, 1, 0, 12, 2, 0);
            }
            if (pieceSetting.neighborPresences[Direction.NORTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 11, 1, 7, 12, 2, 7);
            }
            if (pieceSetting.neighborPresences[Direction.EAST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 15, 1, 3, 15, 2, 4);
            }
            return true;
        }
    }

    public static class DoubleYRoom
    extends Piece {
        public DoubleYRoom(Direction direction, PieceSetting pieceSetting) {
            super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_ROOM, 1, direction, pieceSetting, 1, 2, 1);
        }

        public DoubleYRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_ROOM, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.setting.roomIndex / 25 > 0) {
                this.method_14774(structureWorldAccess, boundingBox, 0, 0, this.setting.neighborPresences[Direction.DOWN.getId()]);
            }
            if ((_snowman = this.setting.neighbors[Direction.UP.getId()]).neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess, boundingBox, 1, 8, 1, 6, 8, 6, PRISMARINE);
            }
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 4, 0, 0, 4, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 7, 4, 0, 7, 4, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 4, 0, 6, 4, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 4, 7, 6, 4, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 4, 1, 2, 4, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 4, 2, 1, 4, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 4, 1, 5, 4, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 4, 2, 6, 4, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 2, 4, 5, 2, 4, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 4, 5, 1, 4, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 4, 5, 5, 4, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 4, 5, 6, 4, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            PieceSetting pieceSetting = this.setting;
            for (int i = 1; i <= 5; i += 4) {
                _snowman = 0;
                if (pieceSetting.neighborPresences[Direction.SOUTH.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 2, i, _snowman, 2, i + 2, _snowman, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 5, i, _snowman, 5, i + 2, _snowman, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 3, i + 2, _snowman, 4, i + 2, _snowman, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                } else {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, i, _snowman, 7, i + 2, _snowman, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, i + 1, _snowman, 7, i + 1, _snowman, PRISMARINE, PRISMARINE, false);
                }
                _snowman = 7;
                if (pieceSetting.neighborPresences[Direction.NORTH.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 2, i, _snowman, 2, i + 2, _snowman, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 5, i, _snowman, 5, i + 2, _snowman, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 3, i + 2, _snowman, 4, i + 2, _snowman, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                } else {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, i, _snowman, 7, i + 2, _snowman, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, i + 1, _snowman, 7, i + 1, _snowman, PRISMARINE, PRISMARINE, false);
                }
                _snowman = 0;
                if (pieceSetting.neighborPresences[Direction.WEST.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, i, 2, _snowman, i + 2, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, i, 5, _snowman, i + 2, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, i + 2, 3, _snowman, i + 2, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                } else {
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, i, 0, _snowman, i + 2, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, i + 1, 0, _snowman, i + 1, 7, PRISMARINE, PRISMARINE, false);
                }
                _snowman = 7;
                if (pieceSetting.neighborPresences[Direction.EAST.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, i, 2, _snowman, i + 2, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, i, 5, _snowman, i + 2, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, i + 2, 3, _snowman, i + 2, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                } else {
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, i, 0, _snowman, i + 2, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, _snowman, i + 1, 0, _snowman, i + 1, 7, PRISMARINE, PRISMARINE, false);
                }
                pieceSetting = _snowman;
            }
            return true;
        }
    }

    public static class SimpleRoomTop
    extends Piece {
        public SimpleRoomTop(Direction direction, PieceSetting pieceSetting) {
            super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_TOP_ROOM, 1, direction, pieceSetting, 1, 1, 1);
        }

        public SimpleRoomTop(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_TOP_ROOM, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            StructureWorldAccess structureWorldAccess2;
            if (this.setting.roomIndex / 25 > 0) {
                this.method_14774(structureWorldAccess2, boundingBox, 0, 0, this.setting.neighborPresences[Direction.DOWN.getId()]);
            }
            if (this.setting.neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess2, boundingBox, 1, 4, 1, 6, 4, 6, PRISMARINE);
            }
            for (int i = 1; i <= 6; ++i) {
                for (_snowman = 1; _snowman <= 6; ++_snowman) {
                    if (random.nextInt(3) == 0) continue;
                    _snowman = 2 + (random.nextInt(4) == 0 ? 0 : 1);
                    BlockState blockState = Blocks.WET_SPONGE.getDefaultState();
                    this.fillWithOutline(structureWorldAccess2, boundingBox, i, _snowman, _snowman, i, 3, _snowman, blockState, blockState, false);
                }
            }
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 1, 0, 0, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 1, 0, 7, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 1, 0, 6, 1, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 1, 7, 6, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 3, 0, 0, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 3, 0, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 3, 0, 6, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 3, 7, 6, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(structureWorldAccess2, boundingBox, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            if (this.setting.neighborPresences[Direction.SOUTH.getId()]) {
                this.setAirAndWater(structureWorldAccess2, boundingBox, 3, 1, 0, 4, 2, 0);
            }
            return true;
        }
    }

    public static class SimpleRoom
    extends Piece {
        private int field_14480;

        public SimpleRoom(Direction direction, PieceSetting pieceSetting, Random random) {
            super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_ROOM, 1, direction, pieceSetting, 1, 1, 1);
            this.field_14480 = random.nextInt(3);
        }

        public SimpleRoom(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_ROOM, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.setting.roomIndex / 25 > 0) {
                this.method_14774(structureWorldAccess, boundingBox, 0, 0, this.setting.neighborPresences[Direction.DOWN.getId()]);
            }
            if (this.setting.neighbors[Direction.UP.getId()] == null) {
                this.method_14771(structureWorldAccess, boundingBox, 1, 4, 1, 6, 4, 6, PRISMARINE);
            }
            boolean bl = _snowman = this.field_14480 != 0 && random.nextBoolean() && !this.setting.neighborPresences[Direction.DOWN.getId()] && !this.setting.neighborPresences[Direction.UP.getId()] && this.setting.countNeighbors() > 1;
            if (this.field_14480 == 0) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 0, 2, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 0, 2, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 0, 2, 2, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 0, 2, 2, 0, PRISMARINE, PRISMARINE, false);
                this.addBlock(structureWorldAccess, SEA_LANTERN, 1, 2, 1, boundingBox);
                this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 0, 7, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 5, 3, 0, 7, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 7, 2, 0, 7, 2, 2, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 5, 2, 0, 6, 2, 0, PRISMARINE, PRISMARINE, false);
                this.addBlock(structureWorldAccess, SEA_LANTERN, 6, 2, 1, boundingBox);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 5, 2, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 5, 2, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 5, 0, 2, 7, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 7, 2, 2, 7, PRISMARINE, PRISMARINE, false);
                this.addBlock(structureWorldAccess, SEA_LANTERN, 1, 2, 6, boundingBox);
                this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 5, 7, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 5, 3, 5, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 7, 2, 5, 7, 2, 7, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 5, 2, 7, 6, 2, 7, PRISMARINE, PRISMARINE, false);
                this.addBlock(structureWorldAccess, SEA_LANTERN, 6, 2, 6, boundingBox);
                if (this.setting.neighborPresences[Direction.SOUTH.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 3, 3, 0, 4, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                } else {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 3, 3, 0, 4, 3, 1, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 3, 2, 0, 4, 2, 0, PRISMARINE, PRISMARINE, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 3, 1, 0, 4, 1, 1, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                if (this.setting.neighborPresences[Direction.NORTH.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 3, 3, 7, 4, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                } else {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 3, 3, 6, 4, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 3, 2, 7, 4, 2, 7, PRISMARINE, PRISMARINE, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 3, 1, 6, 4, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                if (this.setting.neighborPresences[Direction.WEST.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 3, 0, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                } else {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 3, 1, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 3, 0, 2, 4, PRISMARINE, PRISMARINE, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 3, 1, 1, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                if (this.setting.neighborPresences[Direction.EAST.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 7, 3, 3, 7, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                } else {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 6, 3, 3, 7, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 7, 2, 3, 7, 2, 4, PRISMARINE, PRISMARINE, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 3, 7, 1, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
            } else if (this.field_14480 == 1) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 2, 1, 2, 2, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 2, 1, 5, 2, 3, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 5, 5, 3, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 2, 5, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.addBlock(structureWorldAccess, SEA_LANTERN, 2, 2, 2, boundingBox);
                this.addBlock(structureWorldAccess, SEA_LANTERN, 2, 2, 5, boundingBox);
                this.addBlock(structureWorldAccess, SEA_LANTERN, 5, 2, 5, boundingBox);
                this.addBlock(structureWorldAccess, SEA_LANTERN, 5, 2, 2, boundingBox);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 0, 1, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 1, 0, 3, 1, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 7, 1, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 6, 0, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 7, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 7, 1, 6, 7, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 6, 1, 0, 7, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 7, 1, 1, 7, 3, 1, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.addBlock(structureWorldAccess, PRISMARINE, 1, 2, 0, boundingBox);
                this.addBlock(structureWorldAccess, PRISMARINE, 0, 2, 1, boundingBox);
                this.addBlock(structureWorldAccess, PRISMARINE, 1, 2, 7, boundingBox);
                this.addBlock(structureWorldAccess, PRISMARINE, 0, 2, 6, boundingBox);
                this.addBlock(structureWorldAccess, PRISMARINE, 6, 2, 7, boundingBox);
                this.addBlock(structureWorldAccess, PRISMARINE, 7, 2, 6, boundingBox);
                this.addBlock(structureWorldAccess, PRISMARINE, 6, 2, 0, boundingBox);
                this.addBlock(structureWorldAccess, PRISMARINE, 7, 2, 1, boundingBox);
                if (!this.setting.neighborPresences[Direction.SOUTH.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 0, 6, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 0, 6, 2, 0, PRISMARINE, PRISMARINE, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 0, 6, 1, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                if (!this.setting.neighborPresences[Direction.NORTH.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 7, 6, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 7, 6, 2, 7, PRISMARINE, PRISMARINE, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 7, 6, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                if (!this.setting.neighborPresences[Direction.WEST.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 1, 0, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 1, 0, 2, 6, PRISMARINE, PRISMARINE, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 1, 0, 1, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                if (!this.setting.neighborPresences[Direction.EAST.getId()]) {
                    this.fillWithOutline(structureWorldAccess, boundingBox, 7, 3, 1, 7, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 7, 2, 1, 7, 2, 6, PRISMARINE, PRISMARINE, false);
                    this.fillWithOutline(structureWorldAccess, boundingBox, 7, 1, 1, 7, 1, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
            } else if (this.field_14480 == 2) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 0, 0, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 7, 1, 0, 7, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 0, 6, 1, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 7, 6, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 0, 0, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 7, 3, 0, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 0, 6, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 1, 3, 7, 6, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
                if (this.setting.neighborPresences[Direction.SOUTH.getId()]) {
                    this.setAirAndWater(structureWorldAccess, boundingBox, 3, 1, 0, 4, 2, 0);
                }
                if (this.setting.neighborPresences[Direction.NORTH.getId()]) {
                    this.setAirAndWater(structureWorldAccess, boundingBox, 3, 1, 7, 4, 2, 7);
                }
                if (this.setting.neighborPresences[Direction.WEST.getId()]) {
                    this.setAirAndWater(structureWorldAccess, boundingBox, 0, 1, 3, 0, 2, 4);
                }
                if (this.setting.neighborPresences[Direction.EAST.getId()]) {
                    this.setAirAndWater(structureWorldAccess, boundingBox, 7, 1, 3, 7, 2, 4);
                }
            }
            if (_snowman) {
                this.fillWithOutline(structureWorldAccess, boundingBox, 3, 1, 3, 4, 1, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 3, 2, 3, 4, 2, 4, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, boundingBox, 3, 3, 3, 4, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }
            return true;
        }
    }

    public static class Entry
    extends Piece {
        public Entry(Direction direction, PieceSetting pieceSetting) {
            super(StructurePieceType.OCEAN_MONUMENT_ENTRY_ROOM, 1, direction, pieceSetting, 1, 1, 1);
        }

        public Entry(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_ENTRY_ROOM, compoundTag);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 3, 0, 2, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 3, 0, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 2, 0, 1, 2, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 6, 2, 0, 7, 2, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 0, 0, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 7, 1, 0, 7, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 0, 1, 7, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 1, 1, 0, 2, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(structureWorldAccess, boundingBox, 5, 1, 0, 6, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            if (this.setting.neighborPresences[Direction.NORTH.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 3, 1, 7, 4, 2, 7);
            }
            if (this.setting.neighborPresences[Direction.WEST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 0, 1, 3, 1, 2, 4);
            }
            if (this.setting.neighborPresences[Direction.EAST.getId()]) {
                this.setAirAndWater(structureWorldAccess, boundingBox, 6, 1, 3, 7, 2, 4);
            }
            return true;
        }
    }

    public static class Base
    extends Piece {
        private PieceSetting field_14464;
        private PieceSetting field_14466;
        private final List<Piece> field_14465 = Lists.newArrayList();

        public Base(Random random, int n, int n2, Direction direction) {
            super(StructurePieceType.OCEAN_MONUMENT_BASE, 0);
            this.setOrientation(direction);
            Direction direction2 = this.getFacing();
            this.boundingBox = direction2.getAxis() == Direction.Axis.Z ? new BlockBox(n, 39, n2, n + 58 - 1, 61, n2 + 58 - 1) : new BlockBox(n, 39, n2, n + 58 - 1, 61, n2 + 58 - 1);
            List<PieceSetting> list = this.method_14760(random);
            this.field_14464.used = true;
            this.field_14465.add(new Entry(direction2, this.field_14464));
            this.field_14465.add(new CoreRoom(direction2, this.field_14466));
            ArrayList _snowman2 = Lists.newArrayList();
            _snowman2.add(new DoubleXYRoomFactory());
            _snowman2.add(new DoubleYZRoomFactory());
            _snowman2.add(new DoubleZRoomFactory());
            _snowman2.add(new DoubleXRoomFactory());
            _snowman2.add(new DoubleYRoomFactory());
            _snowman2.add(new SimpleRoomTopFactory());
            _snowman2.add(new SimpleRoomFactory());
            block0: for (PieceSetting pieceSetting : list) {
                if (pieceSetting.used || pieceSetting.isAboveLevelThree()) continue;
                for (Object object2 : _snowman2) {
                    if (!object2.canGenerate(pieceSetting)) continue;
                    this.field_14465.add(object2.generate(direction2, pieceSetting, random));
                    continue block0;
                }
            }
            int n3 = this.boundingBox.minY;
            int _snowman5 = this.applyXTransform(9, 22);
            int _snowman6 = this.applyZTransform(9, 22);
            for (Piece piece : this.field_14465) {
                piece.getBoundingBox().move(_snowman5, n3, _snowman6);
            }
            BlockBox _snowman7 = BlockBox.create(this.applyXTransform(1, 1), this.applyYTransform(1), this.applyZTransform(1, 1), this.applyXTransform(23, 21), this.applyYTransform(8), this.applyZTransform(23, 21));
            BlockBox blockBox = BlockBox.create(this.applyXTransform(34, 1), this.applyYTransform(1), this.applyZTransform(34, 1), this.applyXTransform(56, 21), this.applyYTransform(8), this.applyZTransform(56, 21));
            BlockBox _snowman3 = BlockBox.create(this.applyXTransform(22, 22), this.applyYTransform(13), this.applyZTransform(22, 22), this.applyXTransform(35, 35), this.applyYTransform(17), this.applyZTransform(35, 35));
            int _snowman4 = random.nextInt();
            this.field_14465.add(new WingRoom(direction2, _snowman7, _snowman4++));
            this.field_14465.add(new WingRoom(direction2, blockBox, _snowman4++));
            this.field_14465.add(new Penthouse(direction2, _snowman3));
        }

        public Base(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.OCEAN_MONUMENT_BASE, compoundTag);
        }

        private List<PieceSetting> method_14760(Random random) {
            int n;
            PieceSetting[] pieceSettingArray = new PieceSetting[75];
            for (int n2 = 0; n2 < 5; ++n2) {
                for (int i = 0; i < 4; ++i) {
                    _snowman = 0;
                    _snowman = Base.getIndex(n2, 0, i);
                    pieceSettingArray[_snowman] = new PieceSetting(_snowman);
                }
            }
            for (n = 0; n < 5; ++n) {
                for (i = 0; i < 4; ++i) {
                    _snowman = 1;
                    _snowman = Base.getIndex(n, 1, i);
                    pieceSettingArray[_snowman] = new PieceSetting(_snowman);
                }
            }
            for (n = 1; n < 4; ++n) {
                for (i = 0; i < 2; ++i) {
                    _snowman = 2;
                    _snowman = Base.getIndex(n, 2, i);
                    pieceSettingArray[_snowman] = new PieceSetting(_snowman);
                }
            }
            this.field_14464 = pieceSettingArray[TWO_ZERO_ZERO_INDEX];
            for (n = 0; n < 5; ++n) {
                for (i = 0; i < 5; ++i) {
                    for (_snowman = 0; _snowman < 3; ++_snowman) {
                        _snowman = Base.getIndex(n, _snowman, i);
                        if (pieceSettingArray[_snowman] == null) continue;
                        for (Direction direction : Direction.values()) {
                            int n2 = n + direction.getOffsetX();
                            _snowman = _snowman + direction.getOffsetY();
                            _snowman = i + direction.getOffsetZ();
                            if (n2 < 0 || n2 >= 5 || _snowman < 0 || _snowman >= 5 || _snowman < 0 || _snowman >= 3 || pieceSettingArray[_snowman = Base.getIndex(n2, _snowman, _snowman)] == null) continue;
                            if (_snowman == i) {
                                pieceSettingArray[_snowman].setNeighbor(direction, pieceSettingArray[_snowman]);
                                continue;
                            }
                            pieceSettingArray[_snowman].setNeighbor(direction.getOpposite(), pieceSettingArray[_snowman]);
                        }
                    }
                }
            }
            PieceSetting pieceSetting = new PieceSetting(1003);
            PieceSetting pieceSetting2 = new PieceSetting(1001);
            _snowman = new PieceSetting(1002);
            pieceSettingArray[TWO_TWO_ZERO_INDEX].setNeighbor(Direction.UP, pieceSetting);
            pieceSettingArray[ZERO_ONE_ZERO_INDEX].setNeighbor(Direction.SOUTH, pieceSetting2);
            pieceSettingArray[FOUR_ONE_ZERO_INDEX].setNeighbor(Direction.SOUTH, _snowman);
            pieceSetting.used = true;
            pieceSetting2.used = true;
            _snowman.used = true;
            this.field_14464.field_14484 = true;
            this.field_14466 = pieceSettingArray[Base.getIndex(random.nextInt(4), 0, 2)];
            this.field_14466.used = true;
            this.field_14466.neighbors[Direction.EAST.getId()].used = true;
            this.field_14466.neighbors[Direction.NORTH.getId()].used = true;
            this.field_14466.neighbors[Direction.EAST.getId()].neighbors[Direction.NORTH.getId()].used = true;
            this.field_14466.neighbors[Direction.UP.getId()].used = true;
            this.field_14466.neighbors[Direction.EAST.getId()].neighbors[Direction.UP.getId()].used = true;
            this.field_14466.neighbors[Direction.NORTH.getId()].neighbors[Direction.UP.getId()].used = true;
            this.field_14466.neighbors[Direction.EAST.getId()].neighbors[Direction.NORTH.getId()].neighbors[Direction.UP.getId()].used = true;
            ArrayList _snowman2 = Lists.newArrayList();
            for (PieceSetting pieceSetting3 : pieceSettingArray) {
                if (pieceSetting3 == null) continue;
                pieceSetting3.checkNeighborStates();
                _snowman2.add(pieceSetting3);
            }
            pieceSetting.checkNeighborStates();
            Collections.shuffle(_snowman2, random);
            int _snowman3 = 1;
            for (PieceSetting pieceSetting22 : _snowman2) {
                int n3 = 0;
                for (int i = 0; n3 < 2 && i < 5; ++i) {
                    _snowman = random.nextInt(6);
                    if (!pieceSetting22.neighborPresences[_snowman]) continue;
                    _snowman = Direction.byId(_snowman).getOpposite().getId();
                    ((PieceSetting)pieceSetting22).neighborPresences[_snowman] = false;
                    ((PieceSetting)((PieceSetting)pieceSetting22).neighbors[_snowman]).neighborPresences[_snowman] = false;
                    if (pieceSetting22.method_14783(_snowman3++) && pieceSetting22.neighbors[_snowman].method_14783(_snowman3++)) {
                        ++n3;
                        continue;
                    }
                    ((PieceSetting)pieceSetting22).neighborPresences[_snowman] = true;
                    ((PieceSetting)((PieceSetting)pieceSetting22).neighbors[_snowman]).neighborPresences[_snowman] = true;
                }
            }
            _snowman2.add(pieceSetting);
            _snowman2.add(pieceSetting2);
            _snowman2.add(_snowman);
            return _snowman2;
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n = Math.max(structureWorldAccess.getSeaLevel(), 64) - this.boundingBox.minY;
            this.setAirAndWater(structureWorldAccess, boundingBox, 0, 0, 0, 58, n, 58);
            this.method_14761(false, 0, structureWorldAccess, random, boundingBox);
            this.method_14761(true, 33, structureWorldAccess, random, boundingBox);
            this.method_14763(structureWorldAccess, random, boundingBox);
            this.method_14762(structureWorldAccess, random, boundingBox);
            this.method_14765(structureWorldAccess, random, boundingBox);
            this.method_14764(structureWorldAccess, random, boundingBox);
            this.method_14766(structureWorldAccess, random, boundingBox);
            this.method_14767(structureWorldAccess, random, boundingBox);
            for (_snowman = 0; _snowman < 7; ++_snowman) {
                _snowman = 0;
                while (_snowman < 7) {
                    if (_snowman == 0 && _snowman == 3) {
                        _snowman = 6;
                    }
                    _snowman = _snowman * 9;
                    _snowman = _snowman * 9;
                    for (_snowman = 0; _snowman < 4; ++_snowman) {
                        for (_snowman = 0; _snowman < 4; ++_snowman) {
                            this.addBlock(structureWorldAccess, PRISMARINE_BRICKS, _snowman + _snowman, 0, _snowman + _snowman, boundingBox);
                            this.fillDownwards(structureWorldAccess, PRISMARINE_BRICKS, _snowman + _snowman, -1, _snowman + _snowman, boundingBox);
                        }
                    }
                    if (_snowman == 0 || _snowman == 6) {
                        ++_snowman;
                        continue;
                    }
                    _snowman += 6;
                }
            }
            for (_snowman = 0; _snowman < 5; ++_snowman) {
                this.setAirAndWater(structureWorldAccess, boundingBox, -1 - _snowman, 0 + _snowman * 2, -1 - _snowman, -1 - _snowman, 23, 58 + _snowman);
                this.setAirAndWater(structureWorldAccess, boundingBox, 58 + _snowman, 0 + _snowman * 2, -1 - _snowman, 58 + _snowman, 23, 58 + _snowman);
                this.setAirAndWater(structureWorldAccess, boundingBox, 0 - _snowman, 0 + _snowman * 2, -1 - _snowman, 57 + _snowman, 23, -1 - _snowman);
                this.setAirAndWater(structureWorldAccess, boundingBox, 0 - _snowman, 0 + _snowman * 2, 58 + _snowman, 57 + _snowman, 23, 58 + _snowman);
            }
            for (Piece piece : this.field_14465) {
                if (!piece.getBoundingBox().intersects(boundingBox)) continue;
                piece.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);
            }
            return true;
        }

        private void method_14761(boolean bl, int n, StructureWorldAccess structureWorldAccess, Random random, BlockBox blockBox) {
            int n2 = 24;
            if (this.method_14775(blockBox, n, 0, n + 23, 20)) {
                this.fillWithOutline(structureWorldAccess, blockBox, n + 0, 0, 0, n + 24, 0, 20, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess, blockBox, n + 0, 1, 0, n + 24, 10, 20);
                for (_snowman = 0; _snowman < 4; ++_snowman) {
                    this.fillWithOutline(structureWorldAccess, blockBox, n + _snowman, _snowman + 1, _snowman, n + _snowman, _snowman + 1, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, blockBox, n + _snowman + 7, _snowman + 5, _snowman + 7, n + _snowman + 7, _snowman + 5, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, blockBox, n + 17 - _snowman, _snowman + 5, _snowman + 7, n + 17 - _snowman, _snowman + 5, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, blockBox, n + 24 - _snowman, _snowman + 1, _snowman, n + 24 - _snowman, _snowman + 1, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, blockBox, n + _snowman + 1, _snowman + 1, _snowman, n + 23 - _snowman, _snowman + 1, _snowman, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, blockBox, n + _snowman + 8, _snowman + 5, _snowman + 7, n + 16 - _snowman, _snowman + 5, _snowman + 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                this.fillWithOutline(structureWorldAccess, blockBox, n + 4, 4, 4, n + 6, 4, 20, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 7, 4, 4, n + 17, 4, 6, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 18, 4, 4, n + 20, 4, 20, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 11, 8, 11, n + 13, 8, 20, PRISMARINE, PRISMARINE, false);
                this.addBlock(structureWorldAccess, field_14470, n + 12, 9, 12, blockBox);
                this.addBlock(structureWorldAccess, field_14470, n + 12, 9, 15, blockBox);
                this.addBlock(structureWorldAccess, field_14470, n + 12, 9, 18, blockBox);
                _snowman = n + (bl ? 19 : 5);
                _snowman = n + (bl ? 5 : 19);
                for (_snowman = 20; _snowman >= 5; _snowman -= 3) {
                    this.addBlock(structureWorldAccess, field_14470, _snowman, 5, _snowman, blockBox);
                }
                for (_snowman = 19; _snowman >= 7; _snowman -= 3) {
                    this.addBlock(structureWorldAccess, field_14470, _snowman, 5, _snowman, blockBox);
                }
                for (_snowman = 0; _snowman < 4; ++_snowman) {
                    _snowman = bl ? n + 24 - (17 - _snowman * 3) : n + 17 - _snowman * 3;
                    this.addBlock(structureWorldAccess, field_14470, _snowman, 5, 5, blockBox);
                }
                this.addBlock(structureWorldAccess, field_14470, _snowman, 5, 5, blockBox);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 11, 1, 12, n + 13, 7, 12, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 12, 1, 11, n + 12, 7, 13, PRISMARINE, PRISMARINE, false);
            }
        }

        private void method_14763(StructureWorldAccess structureWorldAccess, Random random, BlockBox blockBox) {
            if (this.method_14775(blockBox, 22, 5, 35, 17)) {
                this.setAirAndWater(structureWorldAccess, blockBox, 25, 0, 0, 32, 8, 20);
                for (int i = 0; i < 4; ++i) {
                    this.fillWithOutline(structureWorldAccess, blockBox, 24, 2, 5 + i * 4, 24, 4, 5 + i * 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, blockBox, 22, 4, 5 + i * 4, 23, 4, 5 + i * 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.addBlock(structureWorldAccess, PRISMARINE_BRICKS, 25, 5, 5 + i * 4, blockBox);
                    this.addBlock(structureWorldAccess, PRISMARINE_BRICKS, 26, 6, 5 + i * 4, blockBox);
                    this.addBlock(structureWorldAccess, SEA_LANTERN, 26, 5, 5 + i * 4, blockBox);
                    this.fillWithOutline(structureWorldAccess, blockBox, 33, 2, 5 + i * 4, 33, 4, 5 + i * 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess, blockBox, 34, 4, 5 + i * 4, 35, 4, 5 + i * 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.addBlock(structureWorldAccess, PRISMARINE_BRICKS, 32, 5, 5 + i * 4, blockBox);
                    this.addBlock(structureWorldAccess, PRISMARINE_BRICKS, 31, 6, 5 + i * 4, blockBox);
                    this.addBlock(structureWorldAccess, SEA_LANTERN, 31, 5, 5 + i * 4, blockBox);
                    this.fillWithOutline(structureWorldAccess, blockBox, 27, 6, 5 + i * 4, 30, 6, 5 + i * 4, PRISMARINE, PRISMARINE, false);
                }
            }
        }

        private void method_14762(StructureWorldAccess structureWorldAccess2, Random random, BlockBox blockBox) {
            if (this.method_14775(blockBox, 15, 20, 42, 21)) {
                StructureWorldAccess structureWorldAccess2;
                int n;
                this.fillWithOutline(structureWorldAccess2, blockBox, 15, 0, 21, 42, 0, 21, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess2, blockBox, 26, 1, 21, 31, 3, 21);
                this.fillWithOutline(structureWorldAccess2, blockBox, 21, 12, 21, 36, 12, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 17, 11, 21, 40, 11, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 16, 10, 21, 41, 10, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 15, 7, 21, 42, 9, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 16, 6, 21, 41, 6, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 17, 5, 21, 40, 5, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 21, 4, 21, 36, 4, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 22, 3, 21, 26, 3, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 31, 3, 21, 35, 3, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 23, 2, 21, 25, 2, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 32, 2, 21, 34, 2, 21, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 28, 4, 20, 29, 4, 21, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 27, 3, 21, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 30, 3, 21, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 26, 2, 21, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 31, 2, 21, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 25, 1, 21, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 32, 1, 21, blockBox);
                for (n = 0; n < 7; ++n) {
                    this.addBlock(structureWorldAccess2, DARK_PRISMARINE, 28 - n, 6 + n, 21, blockBox);
                    this.addBlock(structureWorldAccess2, DARK_PRISMARINE, 29 + n, 6 + n, 21, blockBox);
                }
                for (n = 0; n < 4; ++n) {
                    this.addBlock(structureWorldAccess2, DARK_PRISMARINE, 28 - n, 9 + n, 21, blockBox);
                    this.addBlock(structureWorldAccess2, DARK_PRISMARINE, 29 + n, 9 + n, 21, blockBox);
                }
                this.addBlock(structureWorldAccess2, DARK_PRISMARINE, 28, 12, 21, blockBox);
                this.addBlock(structureWorldAccess2, DARK_PRISMARINE, 29, 12, 21, blockBox);
                for (n = 0; n < 3; ++n) {
                    this.addBlock(structureWorldAccess2, DARK_PRISMARINE, 22 - n * 2, 8, 21, blockBox);
                    this.addBlock(structureWorldAccess2, DARK_PRISMARINE, 22 - n * 2, 9, 21, blockBox);
                    this.addBlock(structureWorldAccess2, DARK_PRISMARINE, 35 + n * 2, 8, 21, blockBox);
                    this.addBlock(structureWorldAccess2, DARK_PRISMARINE, 35 + n * 2, 9, 21, blockBox);
                }
                this.setAirAndWater(structureWorldAccess2, blockBox, 15, 13, 21, 42, 15, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 15, 1, 21, 15, 6, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 16, 1, 21, 16, 5, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 17, 1, 21, 20, 4, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 21, 1, 21, 21, 3, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 22, 1, 21, 22, 2, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 23, 1, 21, 24, 1, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 42, 1, 21, 42, 6, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 41, 1, 21, 41, 5, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 37, 1, 21, 40, 4, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 36, 1, 21, 36, 3, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 33, 1, 21, 34, 1, 21);
                this.setAirAndWater(structureWorldAccess2, blockBox, 35, 1, 21, 35, 2, 21);
            }
        }

        private void method_14765(StructureWorldAccess structureWorldAccess2, Random random, BlockBox blockBox) {
            if (this.method_14775(blockBox, 21, 21, 36, 36)) {
                StructureWorldAccess structureWorldAccess2;
                this.fillWithOutline(structureWorldAccess2, blockBox, 21, 0, 22, 36, 0, 36, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess2, blockBox, 21, 1, 22, 36, 23, 36);
                for (int i = 0; i < 4; ++i) {
                    this.fillWithOutline(structureWorldAccess2, blockBox, 21 + i, 13 + i, 21 + i, 36 - i, 13 + i, 21 + i, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess2, blockBox, 21 + i, 13 + i, 36 - i, 36 - i, 13 + i, 36 - i, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess2, blockBox, 21 + i, 13 + i, 22 + i, 21 + i, 13 + i, 35 - i, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                    this.fillWithOutline(structureWorldAccess2, blockBox, 36 - i, 13 + i, 22 + i, 36 - i, 13 + i, 35 - i, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                this.fillWithOutline(structureWorldAccess2, blockBox, 25, 16, 25, 32, 16, 32, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 25, 17, 25, 25, 19, 25, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 32, 17, 25, 32, 19, 25, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 25, 17, 32, 25, 19, 32, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 32, 17, 32, 32, 19, 32, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 26, 20, 26, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 27, 21, 27, blockBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 27, 20, 27, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 26, 20, 31, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 27, 21, 30, blockBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 27, 20, 30, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 31, 20, 31, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 30, 21, 30, blockBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 30, 20, 30, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 31, 20, 26, blockBox);
                this.addBlock(structureWorldAccess2, PRISMARINE_BRICKS, 30, 21, 27, blockBox);
                this.addBlock(structureWorldAccess2, SEA_LANTERN, 30, 20, 27, blockBox);
                this.fillWithOutline(structureWorldAccess2, blockBox, 28, 21, 27, 29, 21, 27, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 27, 21, 28, 27, 21, 29, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 28, 21, 30, 29, 21, 30, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox, 30, 21, 28, 30, 21, 29, PRISMARINE, PRISMARINE, false);
            }
        }

        private void method_14764(StructureWorldAccess structureWorldAccess2, Random random, BlockBox blockBox2) {
            BlockBox blockBox2;
            StructureWorldAccess structureWorldAccess2;
            int n;
            if (this.method_14775(blockBox2, 0, 21, 6, 58)) {
                this.fillWithOutline(structureWorldAccess2, blockBox2, 0, 0, 21, 6, 0, 57, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess2, blockBox2, 0, 1, 21, 6, 7, 57);
                this.fillWithOutline(structureWorldAccess2, blockBox2, 4, 4, 21, 6, 4, 53, PRISMARINE, PRISMARINE, false);
                for (n = 0; n < 4; ++n) {
                    this.fillWithOutline(structureWorldAccess2, blockBox2, n, n + 1, 21, n, n + 1, 57 - n, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                for (n = 23; n < 53; n += 3) {
                    this.addBlock(structureWorldAccess2, field_14470, 5, 5, n, blockBox2);
                }
                this.addBlock(structureWorldAccess2, field_14470, 5, 5, 52, blockBox2);
                for (n = 0; n < 4; ++n) {
                    this.fillWithOutline(structureWorldAccess2, blockBox2, n, n + 1, 21, n, n + 1, 57 - n, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                this.fillWithOutline(structureWorldAccess2, blockBox2, 4, 1, 52, 6, 3, 52, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox2, 5, 1, 51, 5, 3, 53, PRISMARINE, PRISMARINE, false);
            }
            if (this.method_14775(blockBox2, 51, 21, 58, 58)) {
                this.fillWithOutline(structureWorldAccess2, blockBox2, 51, 0, 21, 57, 0, 57, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess2, blockBox2, 51, 1, 21, 57, 7, 57);
                this.fillWithOutline(structureWorldAccess2, blockBox2, 51, 4, 21, 53, 4, 53, PRISMARINE, PRISMARINE, false);
                for (n = 0; n < 4; ++n) {
                    this.fillWithOutline(structureWorldAccess2, blockBox2, 57 - n, n + 1, 21, 57 - n, n + 1, 57 - n, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                for (n = 23; n < 53; n += 3) {
                    this.addBlock(structureWorldAccess2, field_14470, 52, 5, n, blockBox2);
                }
                this.addBlock(structureWorldAccess2, field_14470, 52, 5, 52, blockBox2);
                this.fillWithOutline(structureWorldAccess2, blockBox2, 51, 1, 52, 53, 3, 52, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess2, blockBox2, 52, 1, 51, 52, 3, 53, PRISMARINE, PRISMARINE, false);
            }
            if (this.method_14775(blockBox2, 0, 51, 57, 57)) {
                this.fillWithOutline(structureWorldAccess2, blockBox2, 7, 0, 51, 50, 0, 57, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess2, blockBox2, 7, 1, 51, 50, 10, 57);
                for (n = 0; n < 4; ++n) {
                    this.fillWithOutline(structureWorldAccess2, blockBox2, n + 1, n + 1, 57 - n, 56 - n, n + 1, 57 - n, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
            }
        }

        private void method_14766(StructureWorldAccess structureWorldAccess2, Random random, BlockBox blockBox2) {
            BlockBox blockBox2;
            int n;
            if (this.method_14775(blockBox2, 7, 21, 13, 50)) {
                this.fillWithOutline(structureWorldAccess2, blockBox2, 7, 0, 21, 13, 0, 50, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess2, blockBox2, 7, 1, 21, 13, 10, 50);
                this.fillWithOutline(structureWorldAccess2, blockBox2, 11, 8, 21, 13, 8, 53, PRISMARINE, PRISMARINE, false);
                for (n = 0; n < 4; ++n) {
                    this.fillWithOutline(structureWorldAccess2, blockBox2, n + 7, n + 5, 21, n + 7, n + 5, 54, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                for (n = 21; n <= 45; n += 3) {
                    this.addBlock(structureWorldAccess2, field_14470, 12, 9, n, blockBox2);
                }
            }
            if (this.method_14775(blockBox2, 44, 21, 50, 54)) {
                this.fillWithOutline(structureWorldAccess2, blockBox2, 44, 0, 21, 50, 0, 50, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess2, blockBox2, 44, 1, 21, 50, 10, 50);
                this.fillWithOutline(structureWorldAccess2, blockBox2, 44, 8, 21, 46, 8, 53, PRISMARINE, PRISMARINE, false);
                for (n = 0; n < 4; ++n) {
                    this.fillWithOutline(structureWorldAccess2, blockBox2, 50 - n, n + 5, 21, 50 - n, n + 5, 54, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                for (n = 21; n <= 45; n += 3) {
                    this.addBlock(structureWorldAccess2, field_14470, 45, 9, n, blockBox2);
                }
            }
            if (this.method_14775(blockBox2, 8, 44, 49, 54)) {
                StructureWorldAccess structureWorldAccess2;
                this.fillWithOutline(structureWorldAccess2, blockBox2, 14, 0, 44, 43, 0, 50, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess2, blockBox2, 14, 1, 44, 43, 10, 50);
                for (n = 12; n <= 45; n += 3) {
                    this.addBlock(structureWorldAccess2, field_14470, n, 9, 45, blockBox2);
                    this.addBlock(structureWorldAccess2, field_14470, n, 9, 52, blockBox2);
                    if (n != 12 && n != 18 && n != 24 && n != 33 && n != 39 && n != 45) continue;
                    this.addBlock(structureWorldAccess2, field_14470, n, 9, 47, blockBox2);
                    this.addBlock(structureWorldAccess2, field_14470, n, 9, 50, blockBox2);
                    this.addBlock(structureWorldAccess2, field_14470, n, 10, 45, blockBox2);
                    this.addBlock(structureWorldAccess2, field_14470, n, 10, 46, blockBox2);
                    this.addBlock(structureWorldAccess2, field_14470, n, 10, 51, blockBox2);
                    this.addBlock(structureWorldAccess2, field_14470, n, 10, 52, blockBox2);
                    this.addBlock(structureWorldAccess2, field_14470, n, 11, 47, blockBox2);
                    this.addBlock(structureWorldAccess2, field_14470, n, 11, 50, blockBox2);
                    this.addBlock(structureWorldAccess2, field_14470, n, 12, 48, blockBox2);
                    this.addBlock(structureWorldAccess2, field_14470, n, 12, 49, blockBox2);
                }
                for (n = 0; n < 3; ++n) {
                    this.fillWithOutline(structureWorldAccess2, blockBox2, 8 + n, 5 + n, 54, 49 - n, 5 + n, 54, PRISMARINE, PRISMARINE, false);
                }
                this.fillWithOutline(structureWorldAccess2, blockBox2, 11, 8, 54, 46, 8, 54, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess2, blockBox2, 14, 8, 44, 43, 8, 53, PRISMARINE, PRISMARINE, false);
            }
        }

        private void method_14767(StructureWorldAccess structureWorldAccess, Random random, BlockBox blockBox2) {
            BlockBox blockBox2;
            int n;
            if (this.method_14775(blockBox2, 14, 21, 20, 43)) {
                this.fillWithOutline(structureWorldAccess, blockBox2, 14, 0, 21, 20, 0, 43, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess, blockBox2, 14, 1, 22, 20, 14, 43);
                this.fillWithOutline(structureWorldAccess, blockBox2, 18, 12, 22, 20, 12, 39, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, blockBox2, 18, 12, 21, 20, 12, 21, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                for (n = 0; n < 4; ++n) {
                    this.fillWithOutline(structureWorldAccess, blockBox2, n + 14, n + 9, 21, n + 14, n + 9, 43 - n, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                for (n = 23; n <= 39; n += 3) {
                    this.addBlock(structureWorldAccess, field_14470, 19, 13, n, blockBox2);
                }
            }
            if (this.method_14775(blockBox2, 37, 21, 43, 43)) {
                this.fillWithOutline(structureWorldAccess, blockBox2, 37, 0, 21, 43, 0, 43, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess, blockBox2, 37, 1, 22, 43, 14, 43);
                this.fillWithOutline(structureWorldAccess, blockBox2, 37, 12, 22, 39, 12, 39, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, blockBox2, 37, 12, 21, 39, 12, 21, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                for (n = 0; n < 4; ++n) {
                    this.fillWithOutline(structureWorldAccess, blockBox2, 43 - n, n + 9, 21, 43 - n, n + 9, 43 - n, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                for (n = 23; n <= 39; n += 3) {
                    this.addBlock(structureWorldAccess, field_14470, 38, 13, n, blockBox2);
                }
            }
            if (this.method_14775(blockBox2, 15, 37, 42, 43)) {
                this.fillWithOutline(structureWorldAccess, blockBox2, 21, 0, 37, 36, 0, 43, PRISMARINE, PRISMARINE, false);
                this.setAirAndWater(structureWorldAccess, blockBox2, 21, 1, 37, 36, 14, 43);
                this.fillWithOutline(structureWorldAccess, blockBox2, 21, 12, 37, 36, 12, 39, PRISMARINE, PRISMARINE, false);
                for (n = 0; n < 4; ++n) {
                    this.fillWithOutline(structureWorldAccess, blockBox2, 15 + n, n + 9, 43 - n, 42 - n, n + 9, 43 - n, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                }
                for (n = 21; n <= 36; n += 3) {
                    this.addBlock(structureWorldAccess, field_14470, n, 13, 38, blockBox2);
                }
            }
        }
    }

    public static abstract class Piece
    extends StructurePiece {
        protected static final BlockState PRISMARINE = Blocks.PRISMARINE.getDefaultState();
        protected static final BlockState PRISMARINE_BRICKS = Blocks.PRISMARINE_BRICKS.getDefaultState();
        protected static final BlockState DARK_PRISMARINE = Blocks.DARK_PRISMARINE.getDefaultState();
        protected static final BlockState field_14470 = PRISMARINE_BRICKS;
        protected static final BlockState SEA_LANTERN = Blocks.SEA_LANTERN.getDefaultState();
        protected static final BlockState WATER = Blocks.WATER.getDefaultState();
        protected static final Set<Block> ICE_BLOCKS = ImmutableSet.builder().add((Object)Blocks.ICE).add((Object)Blocks.PACKED_ICE).add((Object)Blocks.BLUE_ICE).add((Object)WATER.getBlock()).build();
        protected static final int TWO_ZERO_ZERO_INDEX = Piece.getIndex(2, 0, 0);
        protected static final int TWO_TWO_ZERO_INDEX = Piece.getIndex(2, 2, 0);
        protected static final int ZERO_ONE_ZERO_INDEX = Piece.getIndex(0, 1, 0);
        protected static final int FOUR_ONE_ZERO_INDEX = Piece.getIndex(4, 1, 0);
        protected PieceSetting setting;

        protected static final int getIndex(int x, int y, int z) {
            return y * 25 + z * 5 + x;
        }

        public Piece(StructurePieceType structurePieceType, int n) {
            super(structurePieceType, n);
        }

        public Piece(StructurePieceType structurePieceType, Direction direction, BlockBox blockBox) {
            super(structurePieceType, 1);
            this.setOrientation(direction);
            this.boundingBox = blockBox;
        }

        protected Piece(StructurePieceType structurePieceType, int n, Direction direction, PieceSetting pieceSetting, int n2, int n3, int n4) {
            super(structurePieceType, n);
            this.setOrientation(direction);
            this.setting = pieceSetting;
            _snowman = pieceSetting.roomIndex;
            _snowman = _snowman % 5;
            _snowman = _snowman / 5 % 5;
            _snowman = _snowman / 25;
            this.boundingBox = direction == Direction.NORTH || direction == Direction.SOUTH ? new BlockBox(0, 0, 0, n2 * 8 - 1, n3 * 4 - 1, n4 * 8 - 1) : new BlockBox(0, 0, 0, n4 * 8 - 1, n3 * 4 - 1, n2 * 8 - 1);
            switch (direction) {
                case NORTH: {
                    this.boundingBox.move(_snowman * 8, _snowman * 4, -(_snowman + n4) * 8 + 1);
                    break;
                }
                case SOUTH: {
                    this.boundingBox.move(_snowman * 8, _snowman * 4, _snowman * 8);
                    break;
                }
                case WEST: {
                    this.boundingBox.move(-(_snowman + n4) * 8 + 1, _snowman * 4, _snowman * 8);
                    break;
                }
                default: {
                    this.boundingBox.move(_snowman * 8, _snowman * 4, _snowman * 8);
                }
            }
        }

        public Piece(StructurePieceType structurePieceType, CompoundTag compoundTag) {
            super(structurePieceType, compoundTag);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
        }

        protected void setAirAndWater(StructureWorldAccess structureWorldAccess, BlockBox blockBox, int x, int y, int z, int width, int height, int depth) {
            for (int i = y; i <= height; ++i) {
                for (_snowman = x; _snowman <= width; ++_snowman) {
                    for (_snowman = z; _snowman <= depth; ++_snowman) {
                        BlockState blockState = this.getBlockAt(structureWorldAccess, _snowman, i, _snowman, blockBox);
                        if (ICE_BLOCKS.contains(blockState.getBlock())) continue;
                        if (this.applyYTransform(i) >= structureWorldAccess.getSeaLevel() && blockState != WATER) {
                            this.addBlock(structureWorldAccess, Blocks.AIR.getDefaultState(), _snowman, i, _snowman, blockBox);
                            continue;
                        }
                        this.addBlock(structureWorldAccess, WATER, _snowman, i, _snowman, blockBox);
                    }
                }
            }
        }

        protected void method_14774(StructureWorldAccess structureWorldAccess, BlockBox blockBox, int n, int n2, boolean bl) {
            if (bl) {
                this.fillWithOutline(structureWorldAccess, blockBox, n + 0, 0, n2 + 0, n + 2, 0, n2 + 8 - 1, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 5, 0, n2 + 0, n + 8 - 1, 0, n2 + 8 - 1, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 3, 0, n2 + 0, n + 4, 0, n2 + 2, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 3, 0, n2 + 5, n + 4, 0, n2 + 8 - 1, PRISMARINE, PRISMARINE, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 3, 0, n2 + 2, n + 4, 0, n2 + 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 3, 0, n2 + 5, n + 4, 0, n2 + 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 2, 0, n2 + 3, n + 2, 0, n2 + 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
                this.fillWithOutline(structureWorldAccess, blockBox, n + 5, 0, n2 + 3, n + 5, 0, n2 + 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            } else {
                this.fillWithOutline(structureWorldAccess, blockBox, n + 0, 0, n2 + 0, n + 8 - 1, 0, n2 + 8 - 1, PRISMARINE, PRISMARINE, false);
            }
        }

        protected void method_14771(StructureWorldAccess structureWorldAccess, BlockBox blockBox, int n, int n2, int n3, int n4, int n5, int n6, BlockState blockState) {
            for (int i = n2; i <= n5; ++i) {
                for (_snowman = n; _snowman <= n4; ++_snowman) {
                    for (_snowman = n3; _snowman <= n6; ++_snowman) {
                        if (this.getBlockAt(structureWorldAccess, _snowman, i, _snowman, blockBox) != WATER) continue;
                        this.addBlock(structureWorldAccess, blockState, _snowman, i, _snowman, blockBox);
                    }
                }
            }
        }

        protected boolean method_14775(BlockBox blockBox, int n, int n2, int n3, int n4) {
            _snowman = this.applyXTransform(n, n2);
            _snowman = this.applyZTransform(n, n2);
            _snowman = this.applyXTransform(n3, n4);
            _snowman = this.applyZTransform(n3, n4);
            return blockBox.intersectsXZ(Math.min(_snowman, _snowman), Math.min(_snowman, _snowman), Math.max(_snowman, _snowman), Math.max(_snowman, _snowman));
        }

        protected boolean method_14772(StructureWorldAccess structureWorldAccess, BlockBox blockBox, int n, int n2, int n3) {
            _snowman = this.applyXTransform(n, n3);
            if (blockBox.contains(new BlockPos(_snowman, _snowman = this.applyYTransform(n2), _snowman = this.applyZTransform(n, n3)))) {
                ElderGuardianEntity elderGuardianEntity = EntityType.ELDER_GUARDIAN.create(structureWorldAccess.toServerWorld());
                elderGuardianEntity.heal(elderGuardianEntity.getMaxHealth());
                elderGuardianEntity.refreshPositionAndAngles((double)_snowman + 0.5, _snowman, (double)_snowman + 0.5, 0.0f, 0.0f);
                elderGuardianEntity.initialize(structureWorldAccess, structureWorldAccess.getLocalDifficulty(elderGuardianEntity.getBlockPos()), SpawnReason.STRUCTURE, null, null);
                structureWorldAccess.spawnEntityAndPassengers(elderGuardianEntity);
                return true;
            }
            return false;
        }
    }
}

