/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public enum ScaleLayer implements ParentedLayer
{
    NORMAL,
    FUZZY{

        protected int sample(LayerSampleContext<?> context, int n, int n2, int n3, int n4) {
            return context.choose(n, n2, n3, n4);
        }
    };


    @Override
    public int transformX(int x) {
        return x >> 1;
    }

    @Override
    public int transformZ(int y) {
        return y >> 1;
    }

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        int n = parent.sample(this.transformX(x), this.transformZ(z));
        context.initSeed(x >> 1 << 1, z >> 1 << 1);
        _snowman = x & 1;
        _snowman = z & 1;
        if (_snowman == 0 && _snowman == 0) {
            return n;
        }
        _snowman = parent.sample(this.transformX(x), this.transformZ(z + 1));
        _snowman = context.choose(n, _snowman);
        if (_snowman == 0 && _snowman == 1) {
            return _snowman;
        }
        _snowman = parent.sample(this.transformX(x + 1), this.transformZ(z));
        _snowman = context.choose(n, _snowman);
        if (_snowman == 1 && _snowman == 0) {
            return _snowman;
        }
        _snowman = parent.sample(this.transformX(x + 1), this.transformZ(z + 1));
        return this.sample(context, n, _snowman, _snowman, _snowman);
    }

    protected int sample(LayerSampleContext<?> context, int n, int n2, int n3, int n4) {
        if (n2 == n3 && n3 == n4) {
            return n2;
        }
        if (n == n2 && n == n3) {
            return n;
        }
        if (n == n2 && n == n4) {
            return n;
        }
        if (n == n3 && n == n4) {
            return n;
        }
        if (n == n2 && n3 != n4) {
            return n;
        }
        if (n == n3 && n2 != n4) {
            return n;
        }
        if (n == n4 && n2 != n3) {
            return n;
        }
        if (n2 == n3 && n != n4) {
            return n2;
        }
        if (n2 == n4 && n != n3) {
            return n2;
        }
        if (n3 == n4 && n != n2) {
            return n3;
        }
        return context.choose(n, n2, n3, n4);
    }
}

