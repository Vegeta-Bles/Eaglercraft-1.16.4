import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ml extends mq {
   public static final mv<ml> a = new mv<ml>() {
      public ml a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(128L);
         return ml.a(_snowman.readLong());
      }

      @Override
      public String a() {
         return "LONG";
      }

      @Override
      public String b() {
         return "TAG_Long";
      }

      @Override
      public boolean c() {
         return true;
      }
   };
   private final long b;

   private ml(long var1) {
      this.b = _snowman;
   }

   public static ml a(long var0) {
      return _snowman >= -128L && _snowman <= 1024L ? ml.a.a[(int)_snowman + 128] : new ml(_snowman);
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      _snowman.writeLong(this.b);
   }

   @Override
   public byte a() {
      return 4;
   }

   @Override
   public mv<ml> b() {
      return a;
   }

   @Override
   public String toString() {
      return this.b + "L";
   }

   public ml d() {
      return this;
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof ml && this.b == ((ml)_snowman).b;
   }

   @Override
   public int hashCode() {
      return (int)(this.b ^ this.b >>> 32);
   }

   @Override
   public nr a(String var1, int var2) {
      nr _snowman = new oe("L").a(g);
      return new oe(String.valueOf(this.b)).a(_snowman).a(f);
   }

   @Override
   public long e() {
      return this.b;
   }

   @Override
   public int f() {
      return (int)(this.b & -1L);
   }

   @Override
   public short g() {
      return (short)((int)(this.b & 65535L));
   }

   @Override
   public byte h() {
      return (byte)((int)(this.b & 255L));
   }

   @Override
   public double i() {
      return (double)this.b;
   }

   @Override
   public float j() {
      return (float)this.b;
   }

   @Override
   public Number k() {
      return this.b;
   }

   static class a {
      static final ml[] a = new ml[1153];

      static {
         for (int _snowman = 0; _snowman < a.length; _snowman++) {
            a[_snowman] = new ml((long)(-128 + _snowman));
         }
      }
   }
}
