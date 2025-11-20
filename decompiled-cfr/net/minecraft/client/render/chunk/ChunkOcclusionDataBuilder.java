/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue
 */
package net.minecraft.client.render.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.render.chunk.ChunkOcclusionData;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ChunkOcclusionDataBuilder {
    private static final int STEP_X = (int)Math.pow(16.0, 0.0);
    private static final int STEP_Z = (int)Math.pow(16.0, 1.0);
    private static final int STEP_Y = (int)Math.pow(16.0, 2.0);
    private static final Direction[] DIRECTIONS = Direction.values();
    private final BitSet closed = new BitSet(4096);
    private static final int[] EDGE_POINTS = Util.make(new int[1352], nArray -> {
        boolean bl = false;
        int _snowman2 = 15;
        int _snowman3 = 0;
        for (int i = 0; i < 16; ++i) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                for (_snowman = 0; _snowman < 16; ++_snowman) {
                    if (i != 0 && i != 15 && _snowman != 0 && _snowman != 15 && _snowman != 0 && _snowman != 15) continue;
                    nArray[_snowman3++] = ChunkOcclusionDataBuilder.pack(i, _snowman, _snowman);
                }
            }
        }
    });
    private int openCount = 4096;

    public void markClosed(BlockPos pos) {
        this.closed.set(ChunkOcclusionDataBuilder.pack(pos), true);
        --this.openCount;
    }

    private static int pack(BlockPos pos) {
        return ChunkOcclusionDataBuilder.pack(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF);
    }

    private static int pack(int x, int y, int z) {
        return x << 0 | y << 8 | z << 4;
    }

    public ChunkOcclusionData build() {
        ChunkOcclusionData chunkOcclusionData = new ChunkOcclusionData();
        if (4096 - this.openCount < 256) {
            chunkOcclusionData.fill(true);
        } else if (this.openCount == 0) {
            chunkOcclusionData.fill(false);
        } else {
            for (int n : EDGE_POINTS) {
                if (this.closed.get(n)) continue;
                chunkOcclusionData.addOpenEdgeFaces(this.getOpenFaces(n));
            }
        }
        return chunkOcclusionData;
    }

    private Set<Direction> getOpenFaces(int pos) {
        EnumSet<Direction> enumSet = EnumSet.noneOf(Direction.class);
        IntArrayFIFOQueue _snowman2 = new IntArrayFIFOQueue();
        _snowman2.enqueue(pos);
        this.closed.set(pos, true);
        while (!_snowman2.isEmpty()) {
            int n = _snowman2.dequeueInt();
            this.addEdgeFaces(n, enumSet);
            for (Direction direction : DIRECTIONS) {
                int n2 = this.offset(n, direction);
                if (n2 < 0 || this.closed.get(n2)) continue;
                this.closed.set(n2, true);
                _snowman2.enqueue(n2);
            }
        }
        return enumSet;
    }

    private void addEdgeFaces(int pos, Set<Direction> openFaces) {
        int n = pos >> 0 & 0xF;
        if (n == 0) {
            openFaces.add(Direction.WEST);
        } else if (n == 15) {
            openFaces.add(Direction.EAST);
        }
        _snowman = pos >> 8 & 0xF;
        if (_snowman == 0) {
            openFaces.add(Direction.DOWN);
        } else if (_snowman == 15) {
            openFaces.add(Direction.UP);
        }
        _snowman = pos >> 4 & 0xF;
        if (_snowman == 0) {
            openFaces.add(Direction.NORTH);
        } else if (_snowman == 15) {
            openFaces.add(Direction.SOUTH);
        }
    }

    private int offset(int pos, Direction direction) {
        switch (direction) {
            case DOWN: {
                if ((pos >> 8 & 0xF) == 0) {
                    return -1;
                }
                return pos - STEP_Y;
            }
            case UP: {
                if ((pos >> 8 & 0xF) == 15) {
                    return -1;
                }
                return pos + STEP_Y;
            }
            case NORTH: {
                if ((pos >> 4 & 0xF) == 0) {
                    return -1;
                }
                return pos - STEP_Z;
            }
            case SOUTH: {
                if ((pos >> 4 & 0xF) == 15) {
                    return -1;
                }
                return pos + STEP_Z;
            }
            case WEST: {
                if ((pos >> 0 & 0xF) == 0) {
                    return -1;
                }
                return pos - STEP_X;
            }
            case EAST: {
                if ((pos >> 0 & 0xF) == 15) {
                    return -1;
                }
                return pos + STEP_X;
            }
        }
        return -1;
    }
}

