import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class mk extends mc<ml> {
   public static final mv<mk> a = new mv<mk>() {
      public mk a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(192L);
         int _snowman = _snowman.readInt();
         _snowman.a(64L * (long)_snowman);
         long[] _snowmanx = new long[_snowman];

         for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
            _snowmanx[_snowmanxx] = _snowman.readLong();
         }

         return new mk(_snowmanx);
      }

      @Override
      public String a() {
         return "LONG[]";
      }

      @Override
      public String b() {
         return "TAG_Long_Array";
      }
   };
   private long[] b;

   public mk(long[] var1) {
      this.b = _snowman;
   }

   public mk(LongSet var1) {
      this.b = _snowman.toLongArray();
   }

   public mk(List<Long> var1) {
      this(a(_snowman));
   }

   private static long[] a(List<Long> var0) {
      long[] _snowman = new long[_snowman.size()];

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         Long _snowmanxx = _snowman.get(_snowmanx);
         _snowman[_snowmanx] = _snowmanxx == null ? 0L : _snowmanxx;
      }

      return _snowman;
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      _snowman.writeInt(this.b.length);

      for (long _snowman : this.b) {
         _snowman.writeLong(_snowman);
      }
   }

   @Override
   public byte a() {
      return 12;
   }

   @Override
   public mv<mk> b() {
      return a;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("[L;");

      for (int _snowmanx = 0; _snowmanx < this.b.length; _snowmanx++) {
         if (_snowmanx != 0) {
            _snowman.append(',');
         }

         _snowman.append(this.b[_snowmanx]).append('L');
      }

      return _snowman.append(']').toString();
   }

   public mk d() {
      long[] _snowman = new long[this.b.length];
      System.arraycopy(this.b, 0, _snowman, 0, this.b.length);
      return new mk(_snowman);
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof mk && Arrays.equals(this.b, ((mk)_snowman).b);
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.b);
   }

   @Override
   public nr a(String var1, int var2) {
      nr _snowman = new oe("L").a(g);
      nx _snowmanx = new oe("[").a(_snowman).c(";");

      for (int _snowmanxx = 0; _snowmanxx < this.b.length; _snowmanxx++) {
         nx _snowmanxxx = new oe(String.valueOf(this.b[_snowmanxx])).a(f);
         _snowmanx.c(" ").a(_snowmanxxx).a(_snowman);
         if (_snowmanxx != this.b.length - 1) {
            _snowmanx.c(",");
         }
      }

      _snowmanx.c("]");
      return _snowmanx;
   }

   public long[] g() {
      return this.b;
   }

   @Override
   public int size() {
      return this.b.length;
   }

   public ml a(int var1) {
      return ml.a(this.b[_snowman]);
   }

   public ml a(int var1, ml var2) {
      long _snowman = this.b[_snowman];
      this.b[_snowman] = _snowman.e();
      return ml.a(_snowman);
   }

   public void b(int var1, ml var2) {
      this.b = ArrayUtils.add(this.b, _snowman, _snowman.e());
   }

   @Override
   public boolean a(int var1, mt var2) {
      if (_snowman instanceof mq) {
         this.b[_snowman] = ((mq)_snowman).e();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b(int var1, mt var2) {
      if (_snowman instanceof mq) {
         this.b = ArrayUtils.add(this.b, _snowman, ((mq)_snowman).e());
         return true;
      } else {
         return false;
      }
   }

   public ml b(int var1) {
      long _snowman = this.b[_snowman];
      this.b = ArrayUtils.remove(this.b, _snowman);
      return ml.a(_snowman);
   }

   @Override
   public byte d_() {
      return 4;
   }

   @Override
   public void clear() {
      this.b = new long[0];
   }
}
