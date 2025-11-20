package net.minecraft.world.chunk;

public class ColumnChunkNibbleArray extends ChunkNibbleArray {
   public ColumnChunkNibbleArray() {
      super(128);
   }

   public ColumnChunkNibbleArray(ChunkNibbleArray _snowman, int _snowman) {
      super(128);
      System.arraycopy(_snowman.asByteArray(), _snowman * 128, this.byteArray, 0, 128);
   }

   @Override
   protected int getIndex(int x, int y, int z) {
      return z << 4 | x;
   }

   @Override
   public byte[] asByteArray() {
      byte[] _snowman = new byte[2048];

      for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
         System.arraycopy(this.byteArray, 0, _snowman, _snowmanx * 128, 128);
      }

      return _snowman;
   }
}
