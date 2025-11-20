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
      int _snowman = x << this.xOffset | z << this.zOffset | y;
      int _snowmanx = _snowman >> 1;
      int _snowmanxx = _snowman & 1;
      return _snowmanxx == 0 ? this.data[_snowmanx] & 15 : this.data[_snowmanx] >> 4 & 15;
   }
}
