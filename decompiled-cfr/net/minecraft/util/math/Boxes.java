/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.math;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

public class Boxes {
    public static Box stretch(Box box, Direction direction, double length) {
        double d = length * (double)direction.getDirection().offset();
        _snowman = Math.min(d, 0.0);
        _snowman = Math.max(d, 0.0);
        switch (direction) {
            case WEST: {
                return new Box(box.minX + _snowman, box.minY, box.minZ, box.minX + _snowman, box.maxY, box.maxZ);
            }
            case EAST: {
                return new Box(box.maxX + _snowman, box.minY, box.minZ, box.maxX + _snowman, box.maxY, box.maxZ);
            }
            case DOWN: {
                return new Box(box.minX, box.minY + _snowman, box.minZ, box.maxX, box.minY + _snowman, box.maxZ);
            }
            default: {
                return new Box(box.minX, box.maxY + _snowman, box.minZ, box.maxX, box.maxY + _snowman, box.maxZ);
            }
            case NORTH: {
                return new Box(box.minX, box.minY, box.minZ + _snowman, box.maxX, box.maxY, box.minZ + _snowman);
            }
            case SOUTH: 
        }
        return new Box(box.minX, box.minY, box.maxZ + _snowman, box.maxX, box.maxY, box.maxZ + _snowman);
    }
}

