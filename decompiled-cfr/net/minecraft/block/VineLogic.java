/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.BlockState;

public class VineLogic {
    public static boolean isValidForWeepingStem(BlockState state) {
        return state.isAir();
    }

    public static int method_26381(Random random) {
        double d = 1.0;
        int _snowman2 = 0;
        while (random.nextDouble() < d) {
            d *= 0.826;
            ++_snowman2;
        }
        return _snowman2;
    }
}

