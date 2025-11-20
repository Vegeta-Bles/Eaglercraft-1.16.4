/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage;

public class AlphaChunkDataArray {
    public final byte[] data;
    private final int zOffset;
    private final int xOffset;

    public AlphaChunkDataArray(byte[] data, int yCoordinateBits) {
        this.data = data;
        this.zOffset = yCoordinateBits;
        this.xOffset = yCoordinateBits + 4;
    }

    public int get(int x, int y, int z) {
        int n = x << this.xOffset | z << this.zOffset | y;
        _snowman = n >> 1;
        _snowman = n & 1;
        if (_snowman == 0) {
            return this.data[_snowman] & 0xF;
        }
        return this.data[_snowman] >> 4 & 0xF;
    }
}

