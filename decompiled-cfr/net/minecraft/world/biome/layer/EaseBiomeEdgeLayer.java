/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum EaseBiomeEdgeLayer implements CrossSamplingLayer
{
    INSTANCE;


    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        int[] nArray = new int[1];
        if (this.method_15841(nArray, center) || this.method_15840(nArray, n, e, s, w, center, 38, 37) || this.method_15840(nArray, n, e, s, w, center, 39, 37) || this.method_15840(nArray, n, e, s, w, center, 32, 5)) {
            return nArray[0];
        }
        if (center == 2 && (n == 12 || e == 12 || w == 12 || s == 12)) {
            return 34;
        }
        if (center == 6) {
            if (n == 2 || e == 2 || w == 2 || s == 2 || n == 30 || e == 30 || w == 30 || s == 30 || n == 12 || e == 12 || w == 12 || s == 12) {
                return 1;
            }
            if (n == 21 || s == 21 || e == 21 || w == 21 || n == 168 || s == 168 || e == 168 || w == 168) {
                return 23;
            }
        }
        return center;
    }

    private boolean method_15841(int[] nArray, int n) {
        if (!BiomeLayers.areSimilar(n, 3)) {
            return false;
        }
        nArray[0] = n;
        return true;
    }

    private boolean method_15840(int[] nArray, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n5 != n6) {
            return false;
        }
        nArray[0] = BiomeLayers.areSimilar(n, n6) && BiomeLayers.areSimilar(n2, n6) && BiomeLayers.areSimilar(n4, n6) && BiomeLayers.areSimilar(n3, n6) ? n5 : n7;
        return true;
    }
}

