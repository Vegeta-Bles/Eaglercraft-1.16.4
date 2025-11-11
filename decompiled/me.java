import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class me extends mq {
   public static final me a = new me(0.0);
   public static final mv<me> b = new mv<me>() {
      public me a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(128L);
         return me.a(_snowman.readDouble());
      }

      @Override
      public String a() {
         return "DOUBLE";
      }

      @Override
      public String b() {
         return "TAG_Double";
      }

      @Override
      public boolean c() {
         return true;
      }
   };
   private final double c;

   private me(double var1) {
      this.c = _snowman;
   }

   public static me a(double var0) {
      return _snowman == 0.0 ? a : new me(_snowman);
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      _snowman.writeDouble(this.c);
   }

   @Override
   public byte a() {
      return 6;
   }

   @Override
   public mv<me> b() {
      return b;
   }

   @Override
   public String toString() {
      return this.c + "d";
   }

   public me d() {
      return this;
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof me && this.c == ((me)_snowman).c;
   }

   @Override
   public int hashCode() {
      long _snowman = Double.doubleToLongBits(this.c);
      return (int)(_snowman ^ _snowman >>> 32);
   }

   @Override
   public nr a(String var1, int var2) {
      nr _snowman = new oe("d").a(g);
      return new oe(String.valueOf(this.c)).a(_snowman).a(f);
   }

   @Override
   public long e() {
      return (long)Math.floor(this.c);
   }

   @Override
   public int f() {
      return afm.c(this.c);
   }

   @Override
   public short g() {
      return (short)(afm.c(this.c) & 65535);
   }

   @Override
   public byte h() {
      return (byte)(afm.c(this.c) & 0xFF);
   }

   @Override
   public double i() {
      return this.c;
   }

   @Override
   public float j() {
      return (float)this.c;
   }

   @Override
   public Number k() {
      return this.c;
   }
}
