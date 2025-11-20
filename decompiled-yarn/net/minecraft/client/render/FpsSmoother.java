package net.minecraft.client.render;

public class FpsSmoother {
   private final long[] times;
   private int size;
   private int index;

   public FpsSmoother(int size) {
      this.times = new long[size];
   }

   public long getTargetUsedTime(long time) {
      if (this.size < this.times.length) {
         this.size++;
      }

      this.times[this.index] = time;
      this.index = (this.index + 1) % this.times.length;
      long _snowman = Long.MAX_VALUE;
      long _snowmanx = Long.MIN_VALUE;
      long _snowmanxx = 0L;

      for (int _snowmanxxx = 0; _snowmanxxx < this.size; _snowmanxxx++) {
         long _snowmanxxxx = this.times[_snowmanxxx];
         _snowmanxx += _snowmanxxxx;
         _snowman = Math.min(_snowman, _snowmanxxxx);
         _snowmanx = Math.max(_snowmanx, _snowmanxxxx);
      }

      if (this.size > 2) {
         _snowmanxx -= _snowman + _snowmanx;
         return _snowmanxx / (long)(this.size - 2);
      } else {
         return _snowmanxx > 0L ? (long)this.size / _snowmanxx : 0L;
      }
   }
}
