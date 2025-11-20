/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;

public class WoodlandMansionGenerator {
    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<Piece> pieces, Random random) {
        MansionParameters mansionParameters = new MansionParameters(random);
        LayoutGenerator _snowman2 = new LayoutGenerator(manager, random);
        _snowman2.generate(pos, rotation, pieces, mansionParameters);
    }

    static class ThirdFloorRoomPool
    extends SecondFloorRoomPool {
        private ThirdFloorRoomPool() {
        }
    }

    static class SecondFloorRoomPool
    extends RoomPool {
        private SecondFloorRoomPool() {
        }

        @Override
        public String getSmallRoom(Random random) {
            return "1x1_b" + (random.nextInt(4) + 1);
        }

        @Override
        public String getSmallSecretRoom(Random random) {
            return "1x1_as" + (random.nextInt(4) + 1);
        }

        @Override
        public String getMediumFunctionalRoom(Random random, boolean staircase) {
            if (staircase) {
                return "1x2_c_stairs";
            }
            return "1x2_c" + (random.nextInt(4) + 1);
        }

        @Override
        public String getMediumGenericRoom(Random random, boolean staircase) {
            if (staircase) {
                return "1x2_d_stairs";
            }
            return "1x2_d" + (random.nextInt(5) + 1);
        }

        @Override
        public String getMediumSecretRoom(Random random) {
            return "1x2_se" + (random.nextInt(1) + 1);
        }

        @Override
        public String getBigRoom(Random random) {
            return "2x2_b" + (random.nextInt(5) + 1);
        }

        @Override
        public String getBigSecretRoom(Random random) {
            return "2x2_s1";
        }
    }

    static class FirstFloorRoomPool
    extends RoomPool {
        private FirstFloorRoomPool() {
        }

        @Override
        public String getSmallRoom(Random random) {
            return "1x1_a" + (random.nextInt(5) + 1);
        }

        @Override
        public String getSmallSecretRoom(Random random) {
            return "1x1_as" + (random.nextInt(4) + 1);
        }

        @Override
        public String getMediumFunctionalRoom(Random random, boolean staircase) {
            return "1x2_a" + (random.nextInt(9) + 1);
        }

        @Override
        public String getMediumGenericRoom(Random random, boolean staircase) {
            return "1x2_b" + (random.nextInt(5) + 1);
        }

        @Override
        public String getMediumSecretRoom(Random random) {
            return "1x2_s" + (random.nextInt(2) + 1);
        }

        @Override
        public String getBigRoom(Random random) {
            return "2x2_a" + (random.nextInt(4) + 1);
        }

        @Override
        public String getBigSecretRoom(Random random) {
            return "2x2_s1";
        }
    }

    static abstract class RoomPool {
        private RoomPool() {
        }

        public abstract String getSmallRoom(Random var1);

        public abstract String getSmallSecretRoom(Random var1);

        public abstract String getMediumFunctionalRoom(Random var1, boolean var2);

        public abstract String getMediumGenericRoom(Random var1, boolean var2);

        public abstract String getMediumSecretRoom(Random var1);

        public abstract String getBigRoom(Random var1);

        public abstract String getBigSecretRoom(Random var1);
    }

    static class FlagMatrix {
        private final int[][] array;
        private final int n;
        private final int m;
        private final int fallback;

        public FlagMatrix(int n, int m, int fallback) {
            this.n = n;
            this.m = m;
            this.fallback = fallback;
            this.array = new int[n][m];
        }

        public void set(int i, int j, int value) {
            if (i >= 0 && i < this.n && j >= 0 && j < this.m) {
                this.array[i][j] = value;
            }
        }

        public void fill(int i0, int j0, int i1, int j1, int value) {
            for (int i = j0; i <= j1; ++i) {
                for (_snowman = i0; _snowman <= i1; ++_snowman) {
                    this.set(_snowman, i, value);
                }
            }
        }

        public int get(int i, int j) {
            if (i >= 0 && i < this.n && j >= 0 && j < this.m) {
                return this.array[i][j];
            }
            return this.fallback;
        }

        public void update(int i, int j, int expected, int newValue) {
            if (this.get(i, j) == expected) {
                this.set(i, j, newValue);
            }
        }

        public boolean anyMatchAround(int i, int j, int value) {
            return this.get(i - 1, j) == value || this.get(i + 1, j) == value || this.get(i, j + 1) == value || this.get(i, j - 1) == value;
        }
    }

    static class MansionParameters {
        private final Random random;
        private final FlagMatrix field_15440;
        private final FlagMatrix field_15439;
        private final FlagMatrix[] field_15443;
        private final int field_15442;
        private final int field_15441;

        public MansionParameters(Random random) {
            this.random = random;
            int n = 11;
            this.field_15442 = 7;
            this.field_15441 = 4;
            this.field_15440 = new FlagMatrix(11, 11, 5);
            this.field_15440.fill(this.field_15442, this.field_15441, this.field_15442 + 1, this.field_15441 + 1, 3);
            this.field_15440.fill(this.field_15442 - 1, this.field_15441, this.field_15442 - 1, this.field_15441 + 1, 2);
            this.field_15440.fill(this.field_15442 + 2, this.field_15441 - 2, this.field_15442 + 3, this.field_15441 + 3, 5);
            this.field_15440.fill(this.field_15442 + 1, this.field_15441 - 2, this.field_15442 + 1, this.field_15441 - 1, 1);
            this.field_15440.fill(this.field_15442 + 1, this.field_15441 + 2, this.field_15442 + 1, this.field_15441 + 3, 1);
            this.field_15440.set(this.field_15442 - 1, this.field_15441 - 1, 1);
            this.field_15440.set(this.field_15442 - 1, this.field_15441 + 2, 1);
            this.field_15440.fill(0, 0, 11, 1, 5);
            this.field_15440.fill(0, 9, 11, 11, 5);
            this.method_15045(this.field_15440, this.field_15442, this.field_15441 - 2, Direction.WEST, 6);
            this.method_15045(this.field_15440, this.field_15442, this.field_15441 + 3, Direction.WEST, 6);
            this.method_15045(this.field_15440, this.field_15442 - 2, this.field_15441 - 1, Direction.WEST, 3);
            this.method_15045(this.field_15440, this.field_15442 - 2, this.field_15441 + 2, Direction.WEST, 3);
            while (this.method_15046(this.field_15440)) {
            }
            this.field_15443 = new FlagMatrix[3];
            this.field_15443[0] = new FlagMatrix(11, 11, 5);
            this.field_15443[1] = new FlagMatrix(11, 11, 5);
            this.field_15443[2] = new FlagMatrix(11, 11, 5);
            this.method_15042(this.field_15440, this.field_15443[0]);
            this.method_15042(this.field_15440, this.field_15443[1]);
            this.field_15443[0].fill(this.field_15442 + 1, this.field_15441, this.field_15442 + 1, this.field_15441 + 1, 0x800000);
            this.field_15443[1].fill(this.field_15442 + 1, this.field_15441, this.field_15442 + 1, this.field_15441 + 1, 0x800000);
            this.field_15439 = new FlagMatrix(this.field_15440.n, this.field_15440.m, 5);
            this.method_15048();
            this.method_15042(this.field_15439, this.field_15443[2]);
        }

        public static boolean method_15047(FlagMatrix flagMatrix, int n, int n2) {
            _snowman = flagMatrix.get(n, n2);
            return _snowman == 1 || _snowman == 2 || _snowman == 3 || _snowman == 4;
        }

        public boolean method_15039(FlagMatrix flagMatrix, int n, int n2, int n3, int n4) {
            return (this.field_15443[n3].get(n, n2) & 0xFFFF) == n4;
        }

        @Nullable
        public Direction method_15040(FlagMatrix flagMatrix, int n, int n2, int n3, int n4) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                if (!this.method_15039(flagMatrix, n + direction.getOffsetX(), n2 + direction.getOffsetZ(), n3, n4)) continue;
                return direction;
            }
            return null;
        }

        private void method_15045(FlagMatrix flagMatrix, int n, int n2, Direction direction, int n3) {
            if (n3 <= 0) {
                return;
            }
            flagMatrix.set(n, n2, 1);
            flagMatrix.update(n + direction.getOffsetX(), n2 + direction.getOffsetZ(), 0, 1);
            for (_snowman = 0; _snowman < 8; ++_snowman) {
                Direction direction2 = Direction.fromHorizontal(this.random.nextInt(4));
                if (direction2 == direction.getOpposite() || direction2 == Direction.EAST && this.random.nextBoolean()) continue;
                int _snowman2 = n + direction.getOffsetX();
                int _snowman3 = n2 + direction.getOffsetZ();
                if (flagMatrix.get(_snowman2 + direction2.getOffsetX(), _snowman3 + direction2.getOffsetZ()) != 0 || flagMatrix.get(_snowman2 + direction2.getOffsetX() * 2, _snowman3 + direction2.getOffsetZ() * 2) != 0) continue;
                this.method_15045(flagMatrix, n + direction.getOffsetX() + direction2.getOffsetX(), n2 + direction.getOffsetZ() + direction2.getOffsetZ(), direction2, n3 - 1);
                break;
            }
            _snowman = direction.rotateYClockwise();
            direction2 = direction.rotateYCounterclockwise();
            flagMatrix.update(n + _snowman.getOffsetX(), n2 + _snowman.getOffsetZ(), 0, 2);
            flagMatrix.update(n + direction2.getOffsetX(), n2 + direction2.getOffsetZ(), 0, 2);
            flagMatrix.update(n + direction.getOffsetX() + _snowman.getOffsetX(), n2 + direction.getOffsetZ() + _snowman.getOffsetZ(), 0, 2);
            flagMatrix.update(n + direction.getOffsetX() + direction2.getOffsetX(), n2 + direction.getOffsetZ() + direction2.getOffsetZ(), 0, 2);
            flagMatrix.update(n + direction.getOffsetX() * 2, n2 + direction.getOffsetZ() * 2, 0, 2);
            flagMatrix.update(n + _snowman.getOffsetX() * 2, n2 + _snowman.getOffsetZ() * 2, 0, 2);
            flagMatrix.update(n + direction2.getOffsetX() * 2, n2 + direction2.getOffsetZ() * 2, 0, 2);
        }

        private boolean method_15046(FlagMatrix flagMatrix) {
            boolean _snowman2 = false;
            for (int i = 0; i < flagMatrix.m; ++i) {
                for (_snowman = 0; _snowman < flagMatrix.n; ++_snowman) {
                    int n;
                    if (flagMatrix.get(_snowman, i) != 0) continue;
                    n = 0;
                    n += MansionParameters.method_15047(flagMatrix, _snowman + 1, i) ? 1 : 0;
                    n += MansionParameters.method_15047(flagMatrix, _snowman - 1, i) ? 1 : 0;
                    n += MansionParameters.method_15047(flagMatrix, _snowman, i + 1) ? 1 : 0;
                    if ((n += MansionParameters.method_15047(flagMatrix, _snowman, i - 1) ? 1 : 0) >= 3) {
                        flagMatrix.set(_snowman, i, 2);
                        _snowman2 = true;
                        continue;
                    }
                    if (n != 2) continue;
                    _snowman = 0;
                    _snowman += MansionParameters.method_15047(flagMatrix, _snowman + 1, i + 1) ? 1 : 0;
                    _snowman += MansionParameters.method_15047(flagMatrix, _snowman - 1, i + 1) ? 1 : 0;
                    _snowman += MansionParameters.method_15047(flagMatrix, _snowman + 1, i - 1) ? 1 : 0;
                    if ((_snowman += MansionParameters.method_15047(flagMatrix, _snowman - 1, i - 1) ? 1 : 0) > 1) continue;
                    flagMatrix.set(_snowman, i, 2);
                    _snowman2 = true;
                }
            }
            return _snowman2;
        }

        private void method_15048() {
            ArrayList arrayList = Lists.newArrayList();
            FlagMatrix _snowman2 = this.field_15443[1];
            for (int i = 0; i < this.field_15439.m; ++i) {
                for (_snowman4 = 0; _snowman4 < this.field_15439.n; ++_snowman4) {
                    _snowman = _snowman2.get(_snowman4, i);
                    _snowman6 = _snowman & 0xF0000;
                    if (_snowman6 != 131072 || (_snowman & 0x200000) != 0x200000) continue;
                    arrayList.add(new Pair<Integer, Integer>(_snowman4, i));
                }
            }
            if (arrayList.isEmpty()) {
                this.field_15439.fill(0, 0, this.field_15439.n, this.field_15439.m, 5);
                return;
            }
            Pair _snowman3 = (Pair)arrayList.get(this.random.nextInt(arrayList.size()));
            int _snowman4 = _snowman2.get((Integer)_snowman3.getLeft(), (Integer)_snowman3.getRight());
            _snowman2.set((Integer)_snowman3.getLeft(), (Integer)_snowman3.getRight(), _snowman4 | 0x400000);
            Direction _snowman5 = this.method_15040(this.field_15440, (Integer)_snowman3.getLeft(), (Integer)_snowman3.getRight(), 1, _snowman4 & 0xFFFF);
            int _snowman6 = (Integer)_snowman3.getLeft() + _snowman5.getOffsetX();
            int _snowman7 = (Integer)_snowman3.getRight() + _snowman5.getOffsetZ();
            for (int i = 0; i < this.field_15439.m; ++i) {
                for (_snowman8 = 0; _snowman8 < this.field_15439.n; ++_snowman8) {
                    if (!MansionParameters.method_15047(this.field_15440, _snowman8, i)) {
                        this.field_15439.set(_snowman8, i, 5);
                        continue;
                    }
                    if (_snowman8 == (Integer)_snowman3.getLeft() && i == (Integer)_snowman3.getRight()) {
                        this.field_15439.set(_snowman8, i, 3);
                        continue;
                    }
                    if (_snowman8 != _snowman6 || i != _snowman7) continue;
                    this.field_15439.set(_snowman8, i, 3);
                    this.field_15443[2].set(_snowman8, i, 0x800000);
                }
            }
            ArrayList arrayList2 = Lists.newArrayList();
            for (Direction direction : Direction.Type.HORIZONTAL) {
                if (this.field_15439.get(_snowman6 + direction.getOffsetX(), _snowman7 + direction.getOffsetZ()) != 0) continue;
                arrayList2.add(direction);
            }
            if (arrayList2.isEmpty()) {
                this.field_15439.fill(0, 0, this.field_15439.n, this.field_15439.m, 5);
                _snowman2.set((Integer)_snowman3.getLeft(), (Integer)_snowman3.getRight(), _snowman4);
                return;
            }
            Direction _snowman8 = (Direction)arrayList2.get(this.random.nextInt(arrayList2.size()));
            this.method_15045(this.field_15439, _snowman6 + _snowman8.getOffsetX(), _snowman7 + _snowman8.getOffsetZ(), _snowman8, 4);
            while (this.method_15046(this.field_15439)) {
            }
        }

        private void method_15042(FlagMatrix flagMatrix, FlagMatrix flagMatrix2) {
            int _snowman2;
            ArrayList arrayList = Lists.newArrayList();
            for (_snowman2 = 0; _snowman2 < flagMatrix.m; ++_snowman2) {
                for (_snowman = 0; _snowman < flagMatrix.n; ++_snowman) {
                    if (flagMatrix.get(_snowman, _snowman2) != 2) continue;
                    arrayList.add(new Pair<Integer, Integer>(_snowman, _snowman2));
                }
            }
            Collections.shuffle(arrayList, this.random);
            _snowman2 = 10;
            for (Pair pair : arrayList) {
                int n = (Integer)pair.getLeft();
                if (flagMatrix2.get(n, _snowman = ((Integer)pair.getRight()).intValue()) != 0) continue;
                _snowman = n;
                _snowman = n;
                _snowman = _snowman;
                _snowman = _snowman;
                _snowman = 65536;
                if (flagMatrix2.get(n + 1, _snowman) == 0 && flagMatrix2.get(n, _snowman + 1) == 0 && flagMatrix2.get(n + 1, _snowman + 1) == 0 && flagMatrix.get(n + 1, _snowman) == 2 && flagMatrix.get(n, _snowman + 1) == 2 && flagMatrix.get(n + 1, _snowman + 1) == 2) {
                    ++_snowman;
                    ++_snowman;
                    _snowman = 262144;
                } else if (flagMatrix2.get(n - 1, _snowman) == 0 && flagMatrix2.get(n, _snowman + 1) == 0 && flagMatrix2.get(n - 1, _snowman + 1) == 0 && flagMatrix.get(n - 1, _snowman) == 2 && flagMatrix.get(n, _snowman + 1) == 2 && flagMatrix.get(n - 1, _snowman + 1) == 2) {
                    --_snowman;
                    ++_snowman;
                    _snowman = 262144;
                } else if (flagMatrix2.get(n - 1, _snowman) == 0 && flagMatrix2.get(n, _snowman - 1) == 0 && flagMatrix2.get(n - 1, _snowman - 1) == 0 && flagMatrix.get(n - 1, _snowman) == 2 && flagMatrix.get(n, _snowman - 1) == 2 && flagMatrix.get(n - 1, _snowman - 1) == 2) {
                    --_snowman;
                    --_snowman;
                    _snowman = 262144;
                } else if (flagMatrix2.get(n + 1, _snowman) == 0 && flagMatrix.get(n + 1, _snowman) == 2) {
                    ++_snowman;
                    _snowman = 131072;
                } else if (flagMatrix2.get(n, _snowman + 1) == 0 && flagMatrix.get(n, _snowman + 1) == 2) {
                    ++_snowman;
                    _snowman = 131072;
                } else if (flagMatrix2.get(n - 1, _snowman) == 0 && flagMatrix.get(n - 1, _snowman) == 2) {
                    --_snowman;
                    _snowman = 131072;
                } else if (flagMatrix2.get(n, _snowman - 1) == 0 && flagMatrix.get(n, _snowman - 1) == 2) {
                    --_snowman;
                    _snowman = 131072;
                }
                _snowman = this.random.nextBoolean() ? _snowman : _snowman;
                _snowman = this.random.nextBoolean() ? _snowman : _snowman;
                _snowman = 0x200000;
                if (!flagMatrix.anyMatchAround(_snowman, _snowman, 1)) {
                    _snowman = _snowman == _snowman ? _snowman : _snowman;
                    int n2 = _snowman = _snowman == _snowman ? _snowman : _snowman;
                    if (!flagMatrix.anyMatchAround(_snowman, _snowman, 1)) {
                        int n3 = _snowman = _snowman == _snowman ? _snowman : _snowman;
                        if (!flagMatrix.anyMatchAround(_snowman, _snowman, 1)) {
                            _snowman = _snowman == _snowman ? _snowman : _snowman;
                            int n4 = _snowman = _snowman == _snowman ? _snowman : _snowman;
                            if (!flagMatrix.anyMatchAround(_snowman, _snowman, 1)) {
                                _snowman = 0;
                                _snowman = _snowman;
                                _snowman = _snowman;
                            }
                        }
                    }
                }
                for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                    for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                        if (_snowman == _snowman && _snowman == _snowman) {
                            flagMatrix2.set(_snowman, _snowman, 0x100000 | _snowman | _snowman | _snowman2);
                            continue;
                        }
                        flagMatrix2.set(_snowman, _snowman, _snowman | _snowman2);
                    }
                }
                ++_snowman2;
            }
        }
    }

    static class LayoutGenerator {
        private final StructureManager manager;
        private final Random random;
        private int field_15446;
        private int field_15445;

        public LayoutGenerator(StructureManager manager, Random random) {
            this.manager = manager;
            this.random = random;
        }

        public void generate(BlockPos pos, BlockRotation rotation, List<Piece> pieces, MansionParameters mansionParameters) {
            GenerationPiece generationPiece = new GenerationPiece();
            generationPiece.position = pos;
            generationPiece.rotation = rotation;
            generationPiece.template = "wall_flat";
            _snowman = new GenerationPiece();
            this.addEntrance(pieces, generationPiece);
            _snowman.position = generationPiece.position.up(8);
            _snowman.rotation = generationPiece.rotation;
            _snowman.template = "wall_window";
            if (!pieces.isEmpty()) {
                // empty if block
            }
            FlagMatrix _snowman2 = mansionParameters.field_15440;
            FlagMatrix _snowman3 = mansionParameters.field_15439;
            this.field_15446 = mansionParameters.field_15442 + 1;
            this.field_15445 = mansionParameters.field_15441 + 1;
            int _snowman4 = mansionParameters.field_15442 + 1;
            int _snowman5 = mansionParameters.field_15441;
            this.addRoof(pieces, generationPiece, _snowman2, Direction.SOUTH, this.field_15446, this.field_15445, _snowman4, _snowman5);
            this.addRoof(pieces, _snowman, _snowman2, Direction.SOUTH, this.field_15446, this.field_15445, _snowman4, _snowman5);
            _snowman = new GenerationPiece();
            _snowman.position = generationPiece.position.up(19);
            _snowman.rotation = generationPiece.rotation;
            _snowman.template = "wall_window";
            boolean _snowman6 = false;
            for (int i = 0; i < _snowman3.m && !_snowman6; ++i) {
                for (i = _snowman3.n - 1; i >= 0 && !_snowman6; --i) {
                    if (!MansionParameters.method_15047(_snowman3, i, i)) continue;
                    _snowman.position = _snowman.position.offset(rotation.rotate(Direction.SOUTH), 8 + (i - this.field_15445) * 8);
                    _snowman.position = _snowman.position.offset(rotation.rotate(Direction.EAST), (i - this.field_15446) * 8);
                    this.method_15052(pieces, _snowman);
                    this.addRoof(pieces, _snowman, _snowman3, Direction.SOUTH, i, i, i, i);
                    _snowman6 = true;
                }
            }
            this.method_15055(pieces, pos.up(16), rotation, _snowman2, _snowman3);
            this.method_15055(pieces, pos.up(27), rotation, _snowman3, null);
            if (!pieces.isEmpty()) {
                // empty if block
            }
            RoomPool[] roomPoolArray = new RoomPool[]{new FirstFloorRoomPool(), new SecondFloorRoomPool(), new ThirdFloorRoomPool()};
            for (int i = 0; i < 3; ++i) {
                Object _snowman13;
                BlockPos blockPos = pos.up(8 * i + (i == 2 ? 3 : 0));
                FlagMatrix _snowman7 = mansionParameters.field_15443[i];
                FlagMatrix _snowman8 = i == 2 ? _snowman3 : _snowman2;
                String _snowman9 = i == 0 ? "carpet_south_1" : "carpet_south_2";
                String _snowman10 = i == 0 ? "carpet_west_1" : "carpet_west_2";
                for (int j = 0; j < _snowman8.m; ++j) {
                    for (_snowman = 0; _snowman < _snowman8.n; ++_snowman) {
                        if (_snowman8.get(_snowman, j) != 1) continue;
                        _snowman13 = blockPos.offset(rotation.rotate(Direction.SOUTH), 8 + (j - this.field_15445) * 8);
                        _snowman13 = ((BlockPos)_snowman13).offset(rotation.rotate(Direction.EAST), (_snowman - this.field_15446) * 8);
                        pieces.add(new Piece(this.manager, "corridor_floor", (BlockPos)_snowman13, rotation));
                        if (_snowman8.get(_snowman, j - 1) == 1 || (_snowman7.get(_snowman, j - 1) & 0x800000) == 0x800000) {
                            pieces.add(new Piece(this.manager, "carpet_north", ((BlockPos)_snowman13).offset(rotation.rotate(Direction.EAST), 1).up(), rotation));
                        }
                        if (_snowman8.get(_snowman + 1, j) == 1 || (_snowman7.get(_snowman + 1, j) & 0x800000) == 0x800000) {
                            pieces.add(new Piece(this.manager, "carpet_east", ((BlockPos)_snowman13).offset(rotation.rotate(Direction.SOUTH), 1).offset(rotation.rotate(Direction.EAST), 5).up(), rotation));
                        }
                        if (_snowman8.get(_snowman, j + 1) == 1 || (_snowman7.get(_snowman, j + 1) & 0x800000) == 0x800000) {
                            pieces.add(new Piece(this.manager, _snowman9, ((BlockPos)_snowman13).offset(rotation.rotate(Direction.SOUTH), 5).offset(rotation.rotate(Direction.WEST), 1), rotation));
                        }
                        if (_snowman8.get(_snowman - 1, j) != 1 && (_snowman7.get(_snowman - 1, j) & 0x800000) != 0x800000) continue;
                        pieces.add(new Piece(this.manager, _snowman10, ((BlockPos)_snowman13).offset(rotation.rotate(Direction.WEST), 1).offset(rotation.rotate(Direction.NORTH), 1), rotation));
                    }
                }
                String _snowman11 = i == 0 ? "indoors_wall_1" : "indoors_wall_2";
                String _snowman12 = i == 0 ? "indoors_door_1" : "indoors_door_2";
                _snowman13 = Lists.newArrayList();
                for (_snowman = 0; _snowman < _snowman8.m; ++_snowman) {
                    for (n2 = 0; n2 < _snowman8.n; ++n2) {
                        int n;
                        int n2;
                        Object object;
                        Object _snowman142;
                        boolean bl = bl2 = i == 2 && _snowman8.get(n2, _snowman) == 3;
                        if (_snowman8.get(n2, _snowman) != 2 && !bl2) continue;
                        _snowman = _snowman7.get(n2, _snowman);
                        n = _snowman & 0xF0000;
                        _snowman = _snowman & 0xFFFF;
                        boolean bl2 = bl2 && (_snowman & 0x800000) == 0x800000;
                        _snowman13.clear();
                        if ((_snowman & 0x200000) == 0x200000) {
                            for (Object _snowman142 : Direction.Type.HORIZONTAL) {
                                if (_snowman8.get(n2 + ((Direction)_snowman142).getOffsetX(), _snowman + ((Direction)_snowman142).getOffsetZ()) != 1) continue;
                                _snowman13.add(_snowman142);
                            }
                        }
                        Direction direction = null;
                        if (!_snowman13.isEmpty()) {
                            direction = (Direction)_snowman13.get(this.random.nextInt(_snowman13.size()));
                        } else if ((_snowman & 0x100000) == 0x100000) {
                            direction = Direction.UP;
                        }
                        _snowman142 = blockPos.offset(rotation.rotate(Direction.SOUTH), 8 + (_snowman - this.field_15445) * 8);
                        _snowman142 = ((BlockPos)_snowman142).offset(rotation.rotate(Direction.EAST), -1 + (n2 - this.field_15446) * 8);
                        if (MansionParameters.method_15047(_snowman8, n2 - 1, _snowman) && !mansionParameters.method_15039(_snowman8, n2 - 1, _snowman, i, _snowman)) {
                            pieces.add(new Piece(this.manager, direction == Direction.WEST ? _snowman12 : _snowman11, (BlockPos)_snowman142, rotation));
                        }
                        if (_snowman8.get(n2 + 1, _snowman) == 1 && !bl2) {
                            object = ((BlockPos)_snowman142).offset(rotation.rotate(Direction.EAST), 8);
                            pieces.add(new Piece(this.manager, direction == Direction.EAST ? _snowman12 : _snowman11, (BlockPos)object, rotation));
                        }
                        if (MansionParameters.method_15047(_snowman8, n2, _snowman + 1) && !mansionParameters.method_15039(_snowman8, n2, _snowman + 1, i, _snowman)) {
                            object = ((BlockPos)_snowman142).offset(rotation.rotate(Direction.SOUTH), 7);
                            object = ((BlockPos)object).offset(rotation.rotate(Direction.EAST), 7);
                            pieces.add(new Piece(this.manager, direction == Direction.SOUTH ? _snowman12 : _snowman11, (BlockPos)object, rotation.rotate(BlockRotation.CLOCKWISE_90)));
                        }
                        if (_snowman8.get(n2, _snowman - 1) == 1 && !bl2) {
                            object = ((BlockPos)_snowman142).offset(rotation.rotate(Direction.NORTH), 1);
                            object = ((BlockPos)object).offset(rotation.rotate(Direction.EAST), 7);
                            pieces.add(new Piece(this.manager, direction == Direction.NORTH ? _snowman12 : _snowman11, (BlockPos)object, rotation.rotate(BlockRotation.CLOCKWISE_90)));
                        }
                        if (n == 65536) {
                            this.addSmallRoom(pieces, (BlockPos)_snowman142, rotation, direction, roomPoolArray[i]);
                            continue;
                        }
                        if (n == 131072 && direction != null) {
                            object = mansionParameters.method_15040(_snowman8, n2, _snowman, i, _snowman);
                            boolean _snowman15 = (_snowman & 0x400000) == 0x400000;
                            this.addMediumRoom(pieces, (BlockPos)_snowman142, rotation, (Direction)object, direction, roomPoolArray[i], _snowman15);
                            continue;
                        }
                        if (n == 262144 && direction != null && direction != Direction.UP) {
                            object = direction.rotateYClockwise();
                            if (!mansionParameters.method_15039(_snowman8, n2 + ((Direction)object).getOffsetX(), _snowman + ((Direction)object).getOffsetZ(), i, _snowman)) {
                                object = ((Direction)object).getOpposite();
                            }
                            this.addBigRoom(pieces, (BlockPos)_snowman142, rotation, (Direction)object, direction, roomPoolArray[i]);
                            continue;
                        }
                        if (n != 262144 || direction != Direction.UP) continue;
                        this.addBigSecretRoom(pieces, (BlockPos)_snowman142, rotation, roomPoolArray[i]);
                    }
                }
            }
        }

        private void addRoof(List<Piece> list, GenerationPiece generationPiece, FlagMatrix flagMatrix, Direction direction, int n, int n2, int n3, int n4) {
            _snowman = n;
            _snowman = n2;
            Direction direction2 = direction;
            do {
                if (!MansionParameters.method_15047(flagMatrix, _snowman + direction.getOffsetX(), _snowman + direction.getOffsetZ())) {
                    this.method_15058(list, generationPiece);
                    direction = direction.rotateYClockwise();
                    if (_snowman == n3 && _snowman == n4 && direction2 == direction) continue;
                    this.method_15052(list, generationPiece);
                    continue;
                }
                if (MansionParameters.method_15047(flagMatrix, _snowman + direction.getOffsetX(), _snowman + direction.getOffsetZ()) && MansionParameters.method_15047(flagMatrix, _snowman + direction.getOffsetX() + direction.rotateYCounterclockwise().getOffsetX(), _snowman + direction.getOffsetZ() + direction.rotateYCounterclockwise().getOffsetZ())) {
                    this.method_15060(list, generationPiece);
                    _snowman += direction.getOffsetX();
                    _snowman += direction.getOffsetZ();
                    direction = direction.rotateYCounterclockwise();
                    continue;
                }
                if ((_snowman += direction.getOffsetX()) == n3 && (_snowman += direction.getOffsetZ()) == n4 && direction2 == direction) continue;
                this.method_15052(list, generationPiece);
            } while (_snowman != n3 || _snowman != n4 || direction2 != direction);
        }

        private void method_15055(List<Piece> list, BlockPos blockPos, BlockRotation blockRotation, FlagMatrix flagMatrix, @Nullable FlagMatrix flagMatrix22) {
            boolean _snowman2;
            FlagMatrix flagMatrix22;
            BlockPos blockPos2;
            int n;
            for (n = 0; n < flagMatrix.m; ++n) {
                for (_snowman = 0; _snowman < flagMatrix.n; ++_snowman) {
                    blockPos2 = blockPos;
                    blockPos2 = blockPos2.offset(blockRotation.rotate(Direction.SOUTH), 8 + (n - this.field_15445) * 8);
                    blockPos2 = blockPos2.offset(blockRotation.rotate(Direction.EAST), (_snowman - this.field_15446) * 8);
                    boolean bl = _snowman2 = flagMatrix22 != null && MansionParameters.method_15047(flagMatrix22, _snowman, n);
                    if (!MansionParameters.method_15047(flagMatrix, _snowman, n) || _snowman2) continue;
                    list.add(new Piece(this.manager, "roof", blockPos2.up(3), blockRotation));
                    if (!MansionParameters.method_15047(flagMatrix, _snowman + 1, n)) {
                        _snowman = blockPos2.offset(blockRotation.rotate(Direction.EAST), 6);
                        list.add(new Piece(this.manager, "roof_front", _snowman, blockRotation));
                    }
                    if (!MansionParameters.method_15047(flagMatrix, _snowman - 1, n)) {
                        _snowman = blockPos2.offset(blockRotation.rotate(Direction.EAST), 0);
                        _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 7);
                        list.add(new Piece(this.manager, "roof_front", _snowman, blockRotation.rotate(BlockRotation.CLOCKWISE_180)));
                    }
                    if (!MansionParameters.method_15047(flagMatrix, _snowman, n - 1)) {
                        _snowman = blockPos2.offset(blockRotation.rotate(Direction.WEST), 1);
                        list.add(new Piece(this.manager, "roof_front", _snowman, blockRotation.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                    }
                    if (MansionParameters.method_15047(flagMatrix, _snowman, n + 1)) continue;
                    _snowman = blockPos2.offset(blockRotation.rotate(Direction.EAST), 6);
                    _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 6);
                    list.add(new Piece(this.manager, "roof_front", _snowman, blockRotation.rotate(BlockRotation.CLOCKWISE_90)));
                }
            }
            if (flagMatrix22 != null) {
                for (n = 0; n < flagMatrix.m; ++n) {
                    for (_snowman = 0; _snowman < flagMatrix.n; ++_snowman) {
                        blockPos2 = blockPos;
                        blockPos2 = blockPos2.offset(blockRotation.rotate(Direction.SOUTH), 8 + (n - this.field_15445) * 8);
                        blockPos2 = blockPos2.offset(blockRotation.rotate(Direction.EAST), (_snowman - this.field_15446) * 8);
                        _snowman2 = MansionParameters.method_15047(flagMatrix22, _snowman, n);
                        if (!MansionParameters.method_15047(flagMatrix, _snowman, n) || !_snowman2) continue;
                        if (!MansionParameters.method_15047(flagMatrix, _snowman + 1, n)) {
                            _snowman = blockPos2.offset(blockRotation.rotate(Direction.EAST), 7);
                            list.add(new Piece(this.manager, "small_wall", _snowman, blockRotation));
                        }
                        if (!MansionParameters.method_15047(flagMatrix, _snowman - 1, n)) {
                            _snowman = blockPos2.offset(blockRotation.rotate(Direction.WEST), 1);
                            _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 6);
                            list.add(new Piece(this.manager, "small_wall", _snowman, blockRotation.rotate(BlockRotation.CLOCKWISE_180)));
                        }
                        if (!MansionParameters.method_15047(flagMatrix, _snowman, n - 1)) {
                            _snowman = blockPos2.offset(blockRotation.rotate(Direction.WEST), 0);
                            _snowman = _snowman.offset(blockRotation.rotate(Direction.NORTH), 1);
                            list.add(new Piece(this.manager, "small_wall", _snowman, blockRotation.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                        }
                        if (!MansionParameters.method_15047(flagMatrix, _snowman, n + 1)) {
                            _snowman = blockPos2.offset(blockRotation.rotate(Direction.EAST), 6);
                            _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 7);
                            list.add(new Piece(this.manager, "small_wall", _snowman, blockRotation.rotate(BlockRotation.CLOCKWISE_90)));
                        }
                        if (!MansionParameters.method_15047(flagMatrix, _snowman + 1, n)) {
                            if (!MansionParameters.method_15047(flagMatrix, _snowman, n - 1)) {
                                _snowman = blockPos2.offset(blockRotation.rotate(Direction.EAST), 7);
                                _snowman = _snowman.offset(blockRotation.rotate(Direction.NORTH), 2);
                                list.add(new Piece(this.manager, "small_wall_corner", _snowman, blockRotation));
                            }
                            if (!MansionParameters.method_15047(flagMatrix, _snowman, n + 1)) {
                                _snowman = blockPos2.offset(blockRotation.rotate(Direction.EAST), 8);
                                _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 7);
                                list.add(new Piece(this.manager, "small_wall_corner", _snowman, blockRotation.rotate(BlockRotation.CLOCKWISE_90)));
                            }
                        }
                        if (MansionParameters.method_15047(flagMatrix, _snowman - 1, n)) continue;
                        if (!MansionParameters.method_15047(flagMatrix, _snowman, n - 1)) {
                            _snowman = blockPos2.offset(blockRotation.rotate(Direction.WEST), 2);
                            _snowman = _snowman.offset(blockRotation.rotate(Direction.NORTH), 1);
                            list.add(new Piece(this.manager, "small_wall_corner", _snowman, blockRotation.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                        }
                        if (MansionParameters.method_15047(flagMatrix, _snowman, n + 1)) continue;
                        _snowman = blockPos2.offset(blockRotation.rotate(Direction.WEST), 1);
                        _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 8);
                        list.add(new Piece(this.manager, "small_wall_corner", _snowman, blockRotation.rotate(BlockRotation.CLOCKWISE_180)));
                    }
                }
            }
            for (n = 0; n < flagMatrix.m; ++n) {
                for (_snowman = 0; _snowman < flagMatrix.n; ++_snowman) {
                    blockPos2 = blockPos;
                    blockPos2 = blockPos2.offset(blockRotation.rotate(Direction.SOUTH), 8 + (n - this.field_15445) * 8);
                    blockPos2 = blockPos2.offset(blockRotation.rotate(Direction.EAST), (_snowman - this.field_15446) * 8);
                    boolean bl = _snowman2 = flagMatrix22 != null && MansionParameters.method_15047(flagMatrix22, _snowman, n);
                    if (!MansionParameters.method_15047(flagMatrix, _snowman, n) || _snowman2) continue;
                    if (!MansionParameters.method_15047(flagMatrix, _snowman + 1, n)) {
                        _snowman = blockPos2.offset(blockRotation.rotate(Direction.EAST), 6);
                        if (!MansionParameters.method_15047(flagMatrix, _snowman, n + 1)) {
                            _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 6);
                            list.add(new Piece(this.manager, "roof_corner", _snowman, blockRotation));
                        } else if (MansionParameters.method_15047(flagMatrix, _snowman + 1, n + 1)) {
                            _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 5);
                            list.add(new Piece(this.manager, "roof_inner_corner", _snowman, blockRotation));
                        }
                        if (!MansionParameters.method_15047(flagMatrix, _snowman, n - 1)) {
                            list.add(new Piece(this.manager, "roof_corner", _snowman, blockRotation.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                        } else if (MansionParameters.method_15047(flagMatrix, _snowman + 1, n - 1)) {
                            _snowman = blockPos2.offset(blockRotation.rotate(Direction.EAST), 9);
                            _snowman = _snowman.offset(blockRotation.rotate(Direction.NORTH), 2);
                            list.add(new Piece(this.manager, "roof_inner_corner", _snowman, blockRotation.rotate(BlockRotation.CLOCKWISE_90)));
                        }
                    }
                    if (MansionParameters.method_15047(flagMatrix, _snowman - 1, n)) continue;
                    _snowman = blockPos2.offset(blockRotation.rotate(Direction.EAST), 0);
                    _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 0);
                    if (!MansionParameters.method_15047(flagMatrix, _snowman, n + 1)) {
                        _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 6);
                        list.add(new Piece(this.manager, "roof_corner", _snowman, blockRotation.rotate(BlockRotation.CLOCKWISE_90)));
                    } else if (MansionParameters.method_15047(flagMatrix, _snowman - 1, n + 1)) {
                        _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 8);
                        _snowman = _snowman.offset(blockRotation.rotate(Direction.WEST), 3);
                        list.add(new Piece(this.manager, "roof_inner_corner", _snowman, blockRotation.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                    }
                    if (!MansionParameters.method_15047(flagMatrix, _snowman, n - 1)) {
                        list.add(new Piece(this.manager, "roof_corner", _snowman, blockRotation.rotate(BlockRotation.CLOCKWISE_180)));
                        continue;
                    }
                    if (!MansionParameters.method_15047(flagMatrix, _snowman - 1, n - 1)) continue;
                    _snowman = _snowman.offset(blockRotation.rotate(Direction.SOUTH), 1);
                    list.add(new Piece(this.manager, "roof_inner_corner", _snowman, blockRotation.rotate(BlockRotation.CLOCKWISE_180)));
                }
            }
        }

        private void addEntrance(List<Piece> list, GenerationPiece generationPiece) {
            Direction direction = generationPiece.rotation.rotate(Direction.WEST);
            list.add(new Piece(this.manager, "entrance", generationPiece.position.offset(direction, 9), generationPiece.rotation));
            generationPiece.position = generationPiece.position.offset(generationPiece.rotation.rotate(Direction.SOUTH), 16);
        }

        private void method_15052(List<Piece> list, GenerationPiece generationPiece) {
            list.add(new Piece(this.manager, generationPiece.template, generationPiece.position.offset(generationPiece.rotation.rotate(Direction.EAST), 7), generationPiece.rotation));
            generationPiece.position = generationPiece.position.offset(generationPiece.rotation.rotate(Direction.SOUTH), 8);
        }

        private void method_15058(List<Piece> list, GenerationPiece generationPiece) {
            generationPiece.position = generationPiece.position.offset(generationPiece.rotation.rotate(Direction.SOUTH), -1);
            list.add(new Piece(this.manager, "wall_corner", generationPiece.position, generationPiece.rotation));
            generationPiece.position = generationPiece.position.offset(generationPiece.rotation.rotate(Direction.SOUTH), -7);
            generationPiece.position = generationPiece.position.offset(generationPiece.rotation.rotate(Direction.WEST), -6);
            generationPiece.rotation = generationPiece.rotation.rotate(BlockRotation.CLOCKWISE_90);
        }

        private void method_15060(List<Piece> list, GenerationPiece generationPiece) {
            generationPiece.position = generationPiece.position.offset(generationPiece.rotation.rotate(Direction.SOUTH), 6);
            generationPiece.position = generationPiece.position.offset(generationPiece.rotation.rotate(Direction.EAST), 8);
            generationPiece.rotation = generationPiece.rotation.rotate(BlockRotation.COUNTERCLOCKWISE_90);
        }

        private void addSmallRoom(List<Piece> list, BlockPos blockPos, BlockRotation blockRotation, Direction direction, RoomPool roomPool) {
            BlockRotation blockRotation2 = BlockRotation.NONE;
            String _snowman2 = roomPool.getSmallRoom(this.random);
            if (direction != Direction.EAST) {
                if (direction == Direction.NORTH) {
                    blockRotation2 = blockRotation2.rotate(BlockRotation.COUNTERCLOCKWISE_90);
                } else if (direction == Direction.WEST) {
                    blockRotation2 = blockRotation2.rotate(BlockRotation.CLOCKWISE_180);
                } else if (direction == Direction.SOUTH) {
                    blockRotation2 = blockRotation2.rotate(BlockRotation.CLOCKWISE_90);
                } else {
                    _snowman2 = roomPool.getSmallSecretRoom(this.random);
                }
            }
            BlockPos _snowman3 = Structure.applyTransformedOffset(new BlockPos(1, 0, 0), BlockMirror.NONE, blockRotation2, 7, 7);
            blockRotation2 = blockRotation2.rotate(blockRotation);
            _snowman3 = _snowman3.rotate(blockRotation);
            BlockPos _snowman4 = blockPos.add(_snowman3.getX(), 0, _snowman3.getZ());
            list.add(new Piece(this.manager, _snowman2, _snowman4, blockRotation2));
        }

        private void addMediumRoom(List<Piece> list, BlockPos blockPos, BlockRotation blockRotation, Direction direction, Direction direction22, RoomPool roomPool, boolean staircase) {
            Direction direction22;
            if (direction22 == Direction.EAST && direction == Direction.SOUTH) {
                BlockPos blockPos2 = blockPos.offset(blockRotation.rotate(Direction.EAST), 1);
                list.add(new Piece(this.manager, roomPool.getMediumFunctionalRoom(this.random, staircase), blockPos2, blockRotation));
            } else if (direction22 == Direction.EAST && direction == Direction.NORTH) {
                BlockPos blockPos3 = blockPos.offset(blockRotation.rotate(Direction.EAST), 1);
                blockPos3 = blockPos3.offset(blockRotation.rotate(Direction.SOUTH), 6);
                list.add(new Piece(this.manager, roomPool.getMediumFunctionalRoom(this.random, staircase), blockPos3, blockRotation, BlockMirror.LEFT_RIGHT));
            } else if (direction22 == Direction.WEST && direction == Direction.NORTH) {
                BlockPos blockPos4 = blockPos.offset(blockRotation.rotate(Direction.EAST), 7);
                blockPos4 = blockPos4.offset(blockRotation.rotate(Direction.SOUTH), 6);
                list.add(new Piece(this.manager, roomPool.getMediumFunctionalRoom(this.random, staircase), blockPos4, blockRotation.rotate(BlockRotation.CLOCKWISE_180)));
            } else if (direction22 == Direction.WEST && direction == Direction.SOUTH) {
                BlockPos blockPos5 = blockPos.offset(blockRotation.rotate(Direction.EAST), 7);
                list.add(new Piece(this.manager, roomPool.getMediumFunctionalRoom(this.random, staircase), blockPos5, blockRotation, BlockMirror.FRONT_BACK));
            } else if (direction22 == Direction.SOUTH && direction == Direction.EAST) {
                BlockPos blockPos6 = blockPos.offset(blockRotation.rotate(Direction.EAST), 1);
                list.add(new Piece(this.manager, roomPool.getMediumFunctionalRoom(this.random, staircase), blockPos6, blockRotation.rotate(BlockRotation.CLOCKWISE_90), BlockMirror.LEFT_RIGHT));
            } else if (direction22 == Direction.SOUTH && direction == Direction.WEST) {
                BlockPos blockPos7 = blockPos.offset(blockRotation.rotate(Direction.EAST), 7);
                list.add(new Piece(this.manager, roomPool.getMediumFunctionalRoom(this.random, staircase), blockPos7, blockRotation.rotate(BlockRotation.CLOCKWISE_90)));
            } else if (direction22 == Direction.NORTH && direction == Direction.WEST) {
                BlockPos blockPos8 = blockPos.offset(blockRotation.rotate(Direction.EAST), 7);
                blockPos8 = blockPos8.offset(blockRotation.rotate(Direction.SOUTH), 6);
                list.add(new Piece(this.manager, roomPool.getMediumFunctionalRoom(this.random, staircase), blockPos8, blockRotation.rotate(BlockRotation.CLOCKWISE_90), BlockMirror.FRONT_BACK));
            } else if (direction22 == Direction.NORTH && direction == Direction.EAST) {
                BlockPos blockPos9 = blockPos.offset(blockRotation.rotate(Direction.EAST), 1);
                blockPos9 = blockPos9.offset(blockRotation.rotate(Direction.SOUTH), 6);
                list.add(new Piece(this.manager, roomPool.getMediumFunctionalRoom(this.random, staircase), blockPos9, blockRotation.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
            } else if (direction22 == Direction.SOUTH && direction == Direction.NORTH) {
                BlockPos blockPos10 = blockPos.offset(blockRotation.rotate(Direction.EAST), 1);
                blockPos10 = blockPos10.offset(blockRotation.rotate(Direction.NORTH), 8);
                list.add(new Piece(this.manager, roomPool.getMediumGenericRoom(this.random, staircase), blockPos10, blockRotation));
            } else if (direction22 == Direction.NORTH && direction == Direction.SOUTH) {
                BlockPos blockPos11 = blockPos.offset(blockRotation.rotate(Direction.EAST), 7);
                blockPos11 = blockPos11.offset(blockRotation.rotate(Direction.SOUTH), 14);
                list.add(new Piece(this.manager, roomPool.getMediumGenericRoom(this.random, staircase), blockPos11, blockRotation.rotate(BlockRotation.CLOCKWISE_180)));
            } else if (direction22 == Direction.WEST && direction == Direction.EAST) {
                BlockPos blockPos12 = blockPos.offset(blockRotation.rotate(Direction.EAST), 15);
                list.add(new Piece(this.manager, roomPool.getMediumGenericRoom(this.random, staircase), blockPos12, blockRotation.rotate(BlockRotation.CLOCKWISE_90)));
            } else if (direction22 == Direction.EAST && direction == Direction.WEST) {
                BlockPos blockPos13 = blockPos.offset(blockRotation.rotate(Direction.WEST), 7);
                blockPos13 = blockPos13.offset(blockRotation.rotate(Direction.SOUTH), 6);
                list.add(new Piece(this.manager, roomPool.getMediumGenericRoom(this.random, staircase), blockPos13, blockRotation.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
            } else if (direction22 == Direction.UP && direction == Direction.EAST) {
                BlockPos blockPos14 = blockPos.offset(blockRotation.rotate(Direction.EAST), 15);
                list.add(new Piece(this.manager, roomPool.getMediumSecretRoom(this.random), blockPos14, blockRotation.rotate(BlockRotation.CLOCKWISE_90)));
            } else if (direction22 == Direction.UP && direction == Direction.SOUTH) {
                BlockPos blockPos15 = blockPos.offset(blockRotation.rotate(Direction.EAST), 1);
                blockPos15 = blockPos15.offset(blockRotation.rotate(Direction.NORTH), 0);
                list.add(new Piece(this.manager, roomPool.getMediumSecretRoom(this.random), blockPos15, blockRotation));
            }
        }

        private void addBigRoom(List<Piece> list, BlockPos blockPos, BlockRotation blockRotation, Direction direction, Direction direction2, RoomPool roomPool) {
            int n = 0;
            _snowman = 0;
            BlockRotation _snowman2 = blockRotation;
            BlockMirror _snowman3 = BlockMirror.NONE;
            if (direction2 == Direction.EAST && direction == Direction.SOUTH) {
                n = -7;
            } else if (direction2 == Direction.EAST && direction == Direction.NORTH) {
                n = -7;
                _snowman = 6;
                _snowman3 = BlockMirror.LEFT_RIGHT;
            } else if (direction2 == Direction.NORTH && direction == Direction.EAST) {
                n = 1;
                _snowman = 14;
                _snowman2 = blockRotation.rotate(BlockRotation.COUNTERCLOCKWISE_90);
            } else if (direction2 == Direction.NORTH && direction == Direction.WEST) {
                n = 7;
                _snowman = 14;
                _snowman2 = blockRotation.rotate(BlockRotation.COUNTERCLOCKWISE_90);
                _snowman3 = BlockMirror.LEFT_RIGHT;
            } else if (direction2 == Direction.SOUTH && direction == Direction.WEST) {
                n = 7;
                _snowman = -8;
                _snowman2 = blockRotation.rotate(BlockRotation.CLOCKWISE_90);
            } else if (direction2 == Direction.SOUTH && direction == Direction.EAST) {
                n = 1;
                _snowman = -8;
                _snowman2 = blockRotation.rotate(BlockRotation.CLOCKWISE_90);
                _snowman3 = BlockMirror.LEFT_RIGHT;
            } else if (direction2 == Direction.WEST && direction == Direction.NORTH) {
                n = 15;
                _snowman = 6;
                _snowman2 = blockRotation.rotate(BlockRotation.CLOCKWISE_180);
            } else if (direction2 == Direction.WEST && direction == Direction.SOUTH) {
                n = 15;
                _snowman3 = BlockMirror.FRONT_BACK;
            }
            BlockPos _snowman4 = blockPos.offset(blockRotation.rotate(Direction.EAST), n);
            _snowman4 = _snowman4.offset(blockRotation.rotate(Direction.SOUTH), _snowman);
            list.add(new Piece(this.manager, roomPool.getBigRoom(this.random), _snowman4, _snowman2, _snowman3));
        }

        private void addBigSecretRoom(List<Piece> list, BlockPos blockPos, BlockRotation blockRotation, RoomPool roomPool) {
            BlockPos blockPos2 = blockPos.offset(blockRotation.rotate(Direction.EAST), 1);
            list.add(new Piece(this.manager, roomPool.getBigSecretRoom(this.random), blockPos2, blockRotation, BlockMirror.NONE));
        }
    }

    static class GenerationPiece {
        public BlockRotation rotation;
        public BlockPos position;
        public String template;

        private GenerationPiece() {
        }
    }

    public static class Piece
    extends SimpleStructurePiece {
        private final String template;
        private final BlockRotation rotation;
        private final BlockMirror mirror;

        public Piece(StructureManager structureManager, String string, BlockPos blockPos, BlockRotation blockRotation) {
            this(structureManager, string, blockPos, blockRotation, BlockMirror.NONE);
        }

        public Piece(StructureManager structureManager, String string, BlockPos blockPos, BlockRotation blockRotation, BlockMirror blockMirror) {
            super(StructurePieceType.WOODLAND_MANSION, 0);
            this.template = string;
            this.pos = blockPos;
            this.rotation = blockRotation;
            this.mirror = blockMirror;
            this.setupPlacement(structureManager);
        }

        public Piece(StructureManager structureManager, CompoundTag compoundTag) {
            super(StructurePieceType.WOODLAND_MANSION, compoundTag);
            this.template = compoundTag.getString("Template");
            this.rotation = BlockRotation.valueOf(compoundTag.getString("Rot"));
            this.mirror = BlockMirror.valueOf(compoundTag.getString("Mi"));
            this.setupPlacement(structureManager);
        }

        private void setupPlacement(StructureManager structureManager) {
            Structure structure = structureManager.getStructureOrBlank(new Identifier("woodland_mansion/" + this.template));
            StructurePlacementData _snowman2 = new StructurePlacementData().setIgnoreEntities(true).setRotation(this.rotation).setMirror(this.mirror).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure, this.pos, _snowman2);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", this.template);
            tag.putString("Rot", this.placementData.getRotation().name());
            tag.putString("Mi", this.placementData.getMirror().name());
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess2, Random random, BlockBox boundingBox) {
            if (metadata.startsWith("Chest")) {
                BlockRotation blockRotation = this.placementData.getRotation();
                BlockState _snowman2 = Blocks.CHEST.getDefaultState();
                if ("ChestWest".equals(metadata)) {
                    _snowman2 = (BlockState)_snowman2.with(ChestBlock.FACING, blockRotation.rotate(Direction.WEST));
                } else if ("ChestEast".equals(metadata)) {
                    _snowman2 = (BlockState)_snowman2.with(ChestBlock.FACING, blockRotation.rotate(Direction.EAST));
                } else if ("ChestSouth".equals(metadata)) {
                    _snowman2 = (BlockState)_snowman2.with(ChestBlock.FACING, blockRotation.rotate(Direction.SOUTH));
                } else if ("ChestNorth".equals(metadata)) {
                    _snowman2 = (BlockState)_snowman2.with(ChestBlock.FACING, blockRotation.rotate(Direction.NORTH));
                }
                this.addChest(serverWorldAccess2, boundingBox, random, pos, LootTables.WOODLAND_MANSION_CHEST, _snowman2);
            } else {
                IllagerEntity _snowman3;
                switch (metadata) {
                    case "Mage": {
                        _snowman3 = EntityType.EVOKER.create(serverWorldAccess2.toServerWorld());
                        break;
                    }
                    case "Warrior": {
                        ServerWorldAccess serverWorldAccess2;
                        _snowman3 = EntityType.VINDICATOR.create(serverWorldAccess2.toServerWorld());
                        break;
                    }
                    default: {
                        return;
                    }
                }
                _snowman3.setPersistent();
                _snowman3.refreshPositionAndAngles(pos, 0.0f, 0.0f);
                _snowman3.initialize(serverWorldAccess2, serverWorldAccess2.getLocalDifficulty(_snowman3.getBlockPos()), SpawnReason.STRUCTURE, null, null);
                serverWorldAccess2.spawnEntityAndPassengers(_snowman3);
                serverWorldAccess2.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            }
        }
    }
}

