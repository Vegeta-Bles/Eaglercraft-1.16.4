import org.apache.commons.lang3.Validate;

public class agc {
   private final long[] a;
   private final int b;
   private final long c;
   private final int d;

   public agc(int var1, int var2) {
      this(_snowman, _snowman, new long[afm.c(_snowman * _snowman, 64) / 64]);
   }

   public agc(int var1, int var2, long[] var3) {
      Validate.inclusiveBetween(1L, 32L, (long)_snowman);
      this.d = _snowman;
      this.b = _snowman;
      this.a = _snowman;
      this.c = (1L << _snowman) - 1L;
      int _snowman = afm.c(_snowman * _snowman, 64) / 64;
      if (_snowman.length != _snowman) {
         throw new IllegalArgumentException("Invalid length given for storage, got: " + _snowman.length + " but expected: " + _snowman);
      }
   }

   public void a(int var1, int var2) {
      Validate.inclusiveBetween(0L, (long)(this.d - 1), (long)_snowman);
      Validate.inclusiveBetween(0L, this.c, (long)_snowman);
      int _snowman = _snowman * this.b;
      int _snowmanx = _snowman >> 6;
      int _snowmanxx = (_snowman + 1) * this.b - 1 >> 6;
      int _snowmanxxx = _snowman ^ _snowmanx << 6;
      this.a[_snowmanx] = this.a[_snowmanx] & ~(this.c << _snowmanxxx) | ((long)_snowman & this.c) << _snowmanxxx;
      if (_snowmanx != _snowmanxx) {
         int _snowmanxxxx = 64 - _snowmanxxx;
         int _snowmanxxxxx = this.b - _snowmanxxxx;
         this.a[_snowmanxx] = this.a[_snowmanxx] >>> _snowmanxxxxx << _snowmanxxxxx | ((long)_snowman & this.c) >> _snowmanxxxx;
      }
   }

   public int a(int var1) {
      Validate.inclusiveBetween(0L, (long)(this.d - 1), (long)_snowman);
      int _snowman = _snowman * this.b;
      int _snowmanx = _snowman >> 6;
      int _snowmanxx = (_snowman + 1) * this.b - 1 >> 6;
      int _snowmanxxx = _snowman ^ _snowmanx << 6;
      if (_snowmanx == _snowmanxx) {
         return (int)(this.a[_snowmanx] >>> _snowmanxxx & this.c);
      } else {
         int _snowmanxxxx = 64 - _snowmanxxx;
         return (int)((this.a[_snowmanx] >>> _snowmanxxx | this.a[_snowmanxx] << _snowmanxxxx) & this.c);
      }
   }

   public long[] a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }
}
