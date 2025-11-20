package net.minecraft.util.math;

import org.apache.commons.lang3.Validate;

public class WordPackedArray {
   private final long[] array;
   private final int unitSize;
   private final long maxValue;
   private final int length;

   public WordPackedArray(int unitSize, int length) {
      this(unitSize, length, new long[MathHelper.roundUpToMultiple(length * unitSize, 64) / 64]);
   }

   public WordPackedArray(int unitSize, int length, long[] array) {
      Validate.inclusiveBetween(1L, 32L, (long)unitSize);
      this.length = length;
      this.unitSize = unitSize;
      this.array = array;
      this.maxValue = (1L << unitSize) - 1L;
      int _snowman = MathHelper.roundUpToMultiple(length * unitSize, 64) / 64;
      if (array.length != _snowman) {
         throw new IllegalArgumentException("Invalid length given for storage, got: " + array.length + " but expected: " + _snowman);
      }
   }

   public void set(int index, int value) {
      Validate.inclusiveBetween(0L, (long)(this.length - 1), (long)index);
      Validate.inclusiveBetween(0L, this.maxValue, (long)value);
      int _snowman = index * this.unitSize;
      int _snowmanx = _snowman >> 6;
      int _snowmanxx = (index + 1) * this.unitSize - 1 >> 6;
      int _snowmanxxx = _snowman ^ _snowmanx << 6;
      this.array[_snowmanx] = this.array[_snowmanx] & ~(this.maxValue << _snowmanxxx) | ((long)value & this.maxValue) << _snowmanxxx;
      if (_snowmanx != _snowmanxx) {
         int _snowmanxxxx = 64 - _snowmanxxx;
         int _snowmanxxxxx = this.unitSize - _snowmanxxxx;
         this.array[_snowmanxx] = this.array[_snowmanxx] >>> _snowmanxxxxx << _snowmanxxxxx | ((long)value & this.maxValue) >> _snowmanxxxx;
      }
   }

   public int get(int index) {
      Validate.inclusiveBetween(0L, (long)(this.length - 1), (long)index);
      int _snowman = index * this.unitSize;
      int _snowmanx = _snowman >> 6;
      int _snowmanxx = (index + 1) * this.unitSize - 1 >> 6;
      int _snowmanxxx = _snowman ^ _snowmanx << 6;
      if (_snowmanx == _snowmanxx) {
         return (int)(this.array[_snowmanx] >>> _snowmanxxx & this.maxValue);
      } else {
         int _snowmanxxxx = 64 - _snowmanxxx;
         return (int)((this.array[_snowmanx] >>> _snowmanxxx | this.array[_snowmanxx] << _snowmanxxxx) & this.maxValue);
      }
   }

   public long[] getAlignedArray() {
      return this.array;
   }

   public int getUnitSize() {
      return this.unitSize;
   }
}
