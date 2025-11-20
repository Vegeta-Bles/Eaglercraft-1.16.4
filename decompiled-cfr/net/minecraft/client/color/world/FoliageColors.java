/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.color.world;

public class FoliageColors {
    private static int[] colorMap = new int[65536];

    public static void setColorMap(int[] pixels) {
        colorMap = pixels;
    }

    public static int getColor(double temperature, double humidity) {
        int n = (int)((1.0 - temperature) * 255.0);
        _snowman = (int)((1.0 - (humidity *= temperature)) * 255.0);
        return colorMap[_snowman << 8 | n];
    }

    public static int getSpruceColor() {
        return 0x619961;
    }

    public static int getBirchColor() {
        return 8431445;
    }

    public static int getDefaultColor() {
        return 4764952;
    }
}

