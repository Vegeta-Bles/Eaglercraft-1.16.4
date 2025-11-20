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
      int l = x << this.xOffset | z << this.zOffset | y;
      int m = l >> 1;
      int n = l & 1;
      return n == 0 ? this.data[m] & 15 : this.data[m] >> 4 & 15;
   }
}
