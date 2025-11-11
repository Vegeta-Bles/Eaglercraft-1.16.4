import java.util.function.IntConsumer;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public class aer {
   private static final int[] a = new int[]{
      -1,
      -1,
      0,
      Integer.MIN_VALUE,
      0,
      0,
      1431655765,
      1431655765,
      0,
      Integer.MIN_VALUE,
      0,
      1,
      858993459,
      858993459,
      0,
      715827882,
      715827882,
      0,
      613566756,
      613566756,
      0,
      Integer.MIN_VALUE,
      0,
      2,
      477218588,
      477218588,
      0,
      429496729,
      429496729,
      0,
      390451572,
      390451572,
      0,
      357913941,
      357913941,
      0,
      330382099,
      330382099,
      0,
      306783378,
      306783378,
      0,
      286331153,
      286331153,
      0,
      Integer.MIN_VALUE,
      0,
      3,
      252645135,
      252645135,
      0,
      238609294,
      238609294,
      0,
      226050910,
      226050910,
      0,
      214748364,
      214748364,
      0,
      204522252,
      204522252,
      0,
      195225786,
      195225786,
      0,
      186737708,
      186737708,
      0,
      178956970,
      178956970,
      0,
      171798691,
      171798691,
      0,
      165191049,
      165191049,
      0,
      159072862,
      159072862,
      0,
      153391689,
      153391689,
      0,
      148102320,
      148102320,
      0,
      143165576,
      143165576,
      0,
      138547332,
      138547332,
      0,
      Integer.MIN_VALUE,
      0,
      4,
      130150524,
      130150524,
      0,
      126322567,
      126322567,
      0,
      122713351,
      122713351,
      0,
      119304647,
      119304647,
      0,
      116080197,
      116080197,
      0,
      113025455,
      113025455,
      0,
      110127366,
      110127366,
      0,
      107374182,
      107374182,
      0,
      104755299,
      104755299,
      0,
      102261126,
      102261126,
      0,
      99882960,
      99882960,
      0,
      97612893,
      97612893,
      0,
      95443717,
      95443717,
      0,
      93368854,
      93368854,
      0,
      91382282,
      91382282,
      0,
      89478485,
      89478485,
      0,
      87652393,
      87652393,
      0,
      85899345,
      85899345,
      0,
      84215045,
      84215045,
      0,
      82595524,
      82595524,
      0,
      81037118,
      81037118,
      0,
      79536431,
      79536431,
      0,
      78090314,
      78090314,
      0,
      76695844,
      76695844,
      0,
      75350303,
      75350303,
      0,
      74051160,
      74051160,
      0,
      72796055,
      72796055,
      0,
      71582788,
      71582788,
      0,
      70409299,
      70409299,
      0,
      69273666,
      69273666,
      0,
      68174084,
      68174084,
      0,
      Integer.MIN_VALUE,
      0,
      5
   };
   private final long[] b;
   private final int c;
   private final long d;
   private final int e;
   private final int f;
   private final int g;
   private final int h;
   private final int i;

   public aer(int var1, int var2) {
      this(_snowman, _snowman, null);
   }

   public aer(int var1, int var2, @Nullable long[] var3) {
      Validate.inclusiveBetween(1L, 32L, (long)_snowman);
      this.e = _snowman;
      this.c = _snowman;
      this.d = (1L << _snowman) - 1L;
      this.f = (char)(64 / _snowman);
      int _snowman = 3 * (this.f - 1);
      this.g = a[_snowman + 0];
      this.h = a[_snowman + 1];
      this.i = a[_snowman + 2];
      int _snowmanx = (_snowman + this.f - 1) / this.f;
      if (_snowman != null) {
         if (_snowman.length != _snowmanx) {
            throw (RuntimeException)x.c(new RuntimeException("Invalid length given for storage, got: " + _snowman.length + " but expected: " + _snowmanx));
         }

         this.b = _snowman;
      } else {
         this.b = new long[_snowmanx];
      }
   }

   private int b(int var1) {
      long _snowman = Integer.toUnsignedLong(this.g);
      long _snowmanx = Integer.toUnsignedLong(this.h);
      return (int)((long)_snowman * _snowman + _snowmanx >> 32 >> this.i);
   }

   public int a(int var1, int var2) {
      Validate.inclusiveBetween(0L, (long)(this.e - 1), (long)_snowman);
      Validate.inclusiveBetween(0L, this.d, (long)_snowman);
      int _snowman = this.b(_snowman);
      long _snowmanx = this.b[_snowman];
      int _snowmanxx = (_snowman - _snowman * this.f) * this.c;
      int _snowmanxxx = (int)(_snowmanx >> _snowmanxx & this.d);
      this.b[_snowman] = _snowmanx & ~(this.d << _snowmanxx) | ((long)_snowman & this.d) << _snowmanxx;
      return _snowmanxxx;
   }

   public void b(int var1, int var2) {
      Validate.inclusiveBetween(0L, (long)(this.e - 1), (long)_snowman);
      Validate.inclusiveBetween(0L, this.d, (long)_snowman);
      int _snowman = this.b(_snowman);
      long _snowmanx = this.b[_snowman];
      int _snowmanxx = (_snowman - _snowman * this.f) * this.c;
      this.b[_snowman] = _snowmanx & ~(this.d << _snowmanxx) | ((long)_snowman & this.d) << _snowmanxx;
   }

   public int a(int var1) {
      Validate.inclusiveBetween(0L, (long)(this.e - 1), (long)_snowman);
      int _snowman = this.b(_snowman);
      long _snowmanx = this.b[_snowman];
      int _snowmanxx = (_snowman - _snowman * this.f) * this.c;
      return (int)(_snowmanx >> _snowmanxx & this.d);
   }

   public long[] a() {
      return this.b;
   }

   public int b() {
      return this.e;
   }

   public void a(IntConsumer var1) {
      int _snowman = 0;

      for (long _snowmanx : this.b) {
         for (int _snowmanxx = 0; _snowmanxx < this.f; _snowmanxx++) {
            _snowman.accept((int)(_snowmanx & this.d));
            _snowmanx >>= this.c;
            if (++_snowman >= this.e) {
               return;
            }
         }
      }
   }
}
