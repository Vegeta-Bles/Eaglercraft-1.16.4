import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class mr extends mq {
   public static final mv<mr> a = new mv<mr>() {
      public mr a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(80L);
         return mr.a(_snowman.readShort());
      }

      @Override
      public String a() {
         return "SHORT";
      }

      @Override
      public String b() {
         return "TAG_Short";
      }

      @Override
      public boolean c() {
         return true;
      }
   };
   private final short b;

   private mr(short var1) {
      this.b = _snowman;
   }

   public static mr a(short var0) {
      return _snowman >= -128 && _snowman <= 1024 ? mr.a.a[_snowman + 128] : new mr(_snowman);
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      _snowman.writeShort(this.b);
   }

   @Override
   public byte a() {
      return 2;
   }

   @Override
   public mv<mr> b() {
      return a;
   }

   @Override
   public String toString() {
      return this.b + "s";
   }

   public mr d() {
      return this;
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof mr && this.b == ((mr)_snowman).b;
   }

   @Override
   public int hashCode() {
      return this.b;
   }

   @Override
   public nr a(String var1, int var2) {
      nr _snowman = new oe("s").a(g);
      return new oe(String.valueOf(this.b)).a(_snowman).a(f);
   }

   @Override
   public long e() {
      return (long)this.b;
   }

   @Override
   public int f() {
      return this.b;
   }

   @Override
   public short g() {
      return this.b;
   }

   @Override
   public byte h() {
      return (byte)(this.b & 255);
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
      static final mr[] a = new mr[1153];

      static {
         for (int _snowman = 0; _snowman < a.length; _snowman++) {
            a[_snowman] = new mr((short)(-128 + _snowman));
         }
      }
   }
}
