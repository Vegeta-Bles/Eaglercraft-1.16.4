/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.model;

import net.minecraft.client.util.math.AffineTransformation;

public interface ModelBakeSettings {
    default public AffineTransformation getRotation() {
        return AffineTransformation.identity();
    }

    default public boolean isShaded() {
        return false;
    }
}

