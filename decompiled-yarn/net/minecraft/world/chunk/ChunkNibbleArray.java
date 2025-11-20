package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.util.Util;

public class ChunkNibbleArray {
   @Nullable
   protected byte[] byteArray;

   public ChunkNibbleArray() {
   }

   public ChunkNibbleArray(byte[] _snowman) {
      this.byteArray = _snowman;
      if (_snowman.length != 2048) {
         throw (IllegalArgumentException)Util.throwOrPause(new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + _snowman.length));
      }
   }

   protected ChunkNibbleArray(int _snowman) {
      this.byteArray = new byte[_snowman];
   }

   public int get(int x, int y, int z) {
      return this.get(this.getIndex(x, y, z));
   }

   public void set(int x, int y, int z, int value) {
      this.set(this.getIndex(x, y, z), value);
   }

   protected int getIndex(int x, int y, int z) {
      return y << 8 | z << 4 | x;
   }

   private int get(int _snowman) {
      if (this.byteArray == null) {
         return 0;
      } else {
         int _snowmanx = this.divideByTwo(_snowman);
         return this.isEven(_snowman) ? this.byteArray[_snowmanx] & 15 : this.byteArray[_snowmanx] >> 4 & 15;
      }
   }

   private void set(int index, int value) {
      if (this.byteArray == null) {
         this.byteArray = new byte[2048];
      }

      int _snowman = this.divideByTwo(index);
      if (this.isEven(index)) {
         this.byteArray[_snowman] = (byte)(this.byteArray[_snowman] & 240 | value & 15);
      } else {
         this.byteArray[_snowman] = (byte)(this.byteArray[_snowman] & 15 | (value & 15) << 4);
      }
   }

   private boolean isEven(int n) {
      return (n & 1) == 0;
   }

   private int divideByTwo(int n) {
      return n >> 1;
   }

   public byte[] asByteArray() {
      if (this.byteArray == null) {
         this.byteArray = new byte[2048];
      }

      return this.byteArray;
   }

   public ChunkNibbleArray copy() {
      return this.byteArray == null ? new ChunkNibbleArray() : new ChunkNibbleArray((byte[])this.byteArray.clone());
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();

      for (int _snowmanx = 0; _snowmanx < 4096; _snowmanx++) {
         _snowman.append(Integer.toHexString(this.get(_snowmanx)));
         if ((_snowmanx & 15) == 15) {
            _snowman.append("\n");
         }

         if ((_snowmanx & 0xFF) == 255) {
            _snowman.append("\n");
         }
      }

      return _snowman.toString();
   }

   public boolean isUninitialized() {
      return this.byteArray == null;
   }
}
