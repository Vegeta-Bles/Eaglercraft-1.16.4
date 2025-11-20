/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class EntityDimensions {
    public final float width;
    public final float height;
    public final boolean fixed;

    public EntityDimensions(float width, float height, boolean fixed) {
        this.width = width;
        this.height = height;
        this.fixed = fixed;
    }

    public Box method_30757(Vec3d vec3d) {
        return this.method_30231(vec3d.x, vec3d.y, vec3d.z);
    }

    public Box method_30231(double d, double d2, double d3) {
        float f = this.width / 2.0f;
        _snowman = this.height;
        return new Box(d - (double)f, d2, d3 - (double)f, d + (double)f, d2 + (double)_snowman, d3 + (double)f);
    }

    public EntityDimensions scaled(float ratio) {
        return this.scaled(ratio, ratio);
    }

    public EntityDimensions scaled(float widthRatio, float heightRatio) {
        if (this.fixed || widthRatio == 1.0f && heightRatio == 1.0f) {
            return this;
        }
        return EntityDimensions.changing(this.width * widthRatio, this.height * heightRatio);
    }

    public static EntityDimensions changing(float width, float height) {
        return new EntityDimensions(width, height, false);
    }

    public static EntityDimensions fixed(float width, float height) {
        return new EntityDimensions(width, height, true);
    }

    public String toString() {
        return "EntityDimensions w=" + this.width + ", h=" + this.height + ", fixed=" + this.fixed;
    }
}

