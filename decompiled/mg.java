import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class mg extends mq {
   public static final mg a = new mg(0.0F);
   public static final mv<mg> b = new mv<mg>() {
      public mg a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(96L);
         return mg.a(_snowman.readFloat());
      }

      @Override
      public String a() {
         return "FLOAT";
      }

      @Override
      public String b() {
         return "TAG_Float";
      }

      @Override
      public boolean c() {
         return true;
      }
   };
   private final float c;

   private mg(float var1) {
      this.c = _snowman;
   }

   public static mg a(float var0) {
      return _snowman == 0.0F ? a : new mg(_snowman);
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      _snowman.writeFloat(this.c);
   }

   @Override
   public byte a() {
      return 5;
   }

   @Override
   public mv<mg> b() {
      return b;
   }

   @Override
   public String toString() {
      return this.c + "f";
   }

   public mg d() {
      return this;
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof mg && this.c == ((mg)_snowman).c;
   }

   @Override
   public int hashCode() {
      return Float.floatToIntBits(this.c);
   }

   @Override
   public nr a(String var1, int var2) {
      nr _snowman = new oe("f").a(g);
      return new oe(String.valueOf(this.c)).a(_snowman).a(f);
   }

   @Override
   public long e() {
      return (long)this.c;
   }

   @Override
   public int f() {
      return afm.d(this.c);
   }

   @Override
   public short g() {
      return (short)(afm.d(this.c) & 65535);
   }

   @Override
   public byte h() {
      return (byte)(afm.d(this.c) & 0xFF);
   }

   @Override
   public double i() {
      return (double)this.c;
   }

   @Override
   public float j() {
      return this.c;
   }

   @Override
   public Number k() {
      return this.c;
   }
}
