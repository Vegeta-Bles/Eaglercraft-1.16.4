/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.model;

import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;

public enum CubeFace {
    DOWN(new Corner(DirectionIds.WEST, DirectionIds.DOWN, DirectionIds.SOUTH), new Corner(DirectionIds.WEST, DirectionIds.DOWN, DirectionIds.NORTH), new Corner(DirectionIds.EAST, DirectionIds.DOWN, DirectionIds.NORTH), new Corner(DirectionIds.EAST, DirectionIds.DOWN, DirectionIds.SOUTH)),
    UP(new Corner(DirectionIds.WEST, DirectionIds.UP, DirectionIds.NORTH), new Corner(DirectionIds.WEST, DirectionIds.UP, DirectionIds.SOUTH), new Corner(DirectionIds.EAST, DirectionIds.UP, DirectionIds.SOUTH), new Corner(DirectionIds.EAST, DirectionIds.UP, DirectionIds.NORTH)),
    NORTH(new Corner(DirectionIds.EAST, DirectionIds.UP, DirectionIds.NORTH), new Corner(DirectionIds.EAST, DirectionIds.DOWN, DirectionIds.NORTH), new Corner(DirectionIds.WEST, DirectionIds.DOWN, DirectionIds.NORTH), new Corner(DirectionIds.WEST, DirectionIds.UP, DirectionIds.NORTH)),
    SOUTH(new Corner(DirectionIds.WEST, DirectionIds.UP, DirectionIds.SOUTH), new Corner(DirectionIds.WEST, DirectionIds.DOWN, DirectionIds.SOUTH), new Corner(DirectionIds.EAST, DirectionIds.DOWN, DirectionIds.SOUTH), new Corner(DirectionIds.EAST, DirectionIds.UP, DirectionIds.SOUTH)),
    WEST(new Corner(DirectionIds.WEST, DirectionIds.UP, DirectionIds.NORTH), new Corner(DirectionIds.WEST, DirectionIds.DOWN, DirectionIds.NORTH), new Corner(DirectionIds.WEST, DirectionIds.DOWN, DirectionIds.SOUTH), new Corner(DirectionIds.WEST, DirectionIds.UP, DirectionIds.SOUTH)),
    EAST(new Corner(DirectionIds.EAST, DirectionIds.UP, DirectionIds.SOUTH), new Corner(DirectionIds.EAST, DirectionIds.DOWN, DirectionIds.SOUTH), new Corner(DirectionIds.EAST, DirectionIds.DOWN, DirectionIds.NORTH), new Corner(DirectionIds.EAST, DirectionIds.UP, DirectionIds.NORTH));

    private static final CubeFace[] DIRECTION_LOOKUP;
    private final Corner[] corners;

    public static CubeFace getFace(Direction direction) {
        return DIRECTION_LOOKUP[direction.getId()];
    }

    private CubeFace(Corner ... corners) {
        this.corners = corners;
    }

    public Corner getCorner(int corner) {
        return this.corners[corner];
    }

    static {
        DIRECTION_LOOKUP = Util.make(new CubeFace[6], cubeFaceArray -> {
            cubeFaceArray[DirectionIds.DOWN] = DOWN;
            cubeFaceArray[DirectionIds.UP] = UP;
            cubeFaceArray[DirectionIds.NORTH] = NORTH;
            cubeFaceArray[DirectionIds.SOUTH] = SOUTH;
            cubeFaceArray[DirectionIds.WEST] = WEST;
            cubeFaceArray[DirectionIds.EAST] = EAST;
        });
    }

    public static class Corner {
        public final int xSide;
        public final int ySide;
        public final int zSide;

        private Corner(int x, int y, int z) {
            this.xSide = x;
            this.ySide = y;
            this.zSide = z;
        }
    }

    public static final class DirectionIds {
        public static final int SOUTH = Direction.SOUTH.getId();
        public static final int UP = Direction.UP.getId();
        public static final int EAST = Direction.EAST.getId();
        public static final int NORTH = Direction.NORTH.getId();
        public static final int DOWN = Direction.DOWN.getId();
        public static final int WEST = Direction.WEST.getId();
    }
}

