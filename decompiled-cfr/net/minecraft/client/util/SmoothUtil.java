/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.util;

import net.minecraft.util.math.MathHelper;

public class SmoothUtil {
    private double actualSum;
    private double smoothedSum;
    private double movementLatency;

    public double smooth(double original, double smoother) {
        this.actualSum += original;
        double d = this.actualSum - this.smoothedSum;
        _snowman = MathHelper.lerp(0.5, this.movementLatency, d);
        _snowman = Math.signum(d);
        if (_snowman * d > _snowman * this.movementLatency) {
            d = _snowman;
        }
        this.movementLatency = _snowman;
        this.smoothedSum += d * smoother;
        return d * smoother;
    }

    public void clear() {
        this.actualSum = 0.0;
        this.smoothedSum = 0.0;
        this.movementLatency = 0.0;
    }
}

