/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.hit;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public abstract class HitResult {
    protected final Vec3d pos;

    protected HitResult(Vec3d pos) {
        this.pos = pos;
    }

    public double squaredDistanceTo(Entity entity) {
        double d = this.pos.x - entity.getX();
        _snowman = this.pos.y - entity.getY();
        _snowman = this.pos.z - entity.getZ();
        return d * d + _snowman * _snowman + _snowman * _snowman;
    }

    public abstract Type getType();

    public Vec3d getPos() {
        return this.pos;
    }

    public static enum Type {
        MISS,
        BLOCK,
        ENTITY;

    }
}

