/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.color.world;

public class GrassColors {
    private static int[] colorMap = new int[65536];

    public static void setColorMap(int[] map) {
        colorMap = map;
    }

    public static int getColor(double temperature, double humidity) {
        int n = (int)((1.0 - (humidity *= temperature)) * 255.0);
        _snowman = (int)((1.0 - temperature) * 255.0);
        _snowman = n << 8 | _snowman;
        if (_snowman > colorMap.length) {
            return -65281;
        }
        return colorMap[_snowman];
    }
}

