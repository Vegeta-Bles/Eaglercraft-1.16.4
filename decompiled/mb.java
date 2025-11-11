import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class mb extends mq {
   public static final mv<mb> a = new mv<mb>() {
      public mb a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(72L);
         return mb.a(_snowman.readByte());
      }

      @Override
      public String a() {
         return "BYTE";
      }

      @Override
      public String b() {
         return "TAG_Byte";
      }

      @Override
      public boolean c() {
         return true;
      }
   };
   public static final mb b = a((byte)0);
   public static final mb c = a((byte)1);
   private final byte h;

   private mb(byte var1) {
      this.h = _snowman;
   }

   public static mb a(byte var0) {
      return mb.a.a[128 + _snowman];
   }

   public static mb a(boolean var0) {
      return _snowman ? c : b;
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      _snowman.writeByte(this.h);
   }

   @Override
   public byte a() {
      return 1;
   }

   @Override
   public mv<mb> b() {
      return a;
   }

   @Override
   public String toString() {
      return this.h + "b";
   }

   public mb d() {
      return this;
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof mb && this.h == ((mb)_snowman).h;
   }

   @Override
   public int hashCode() {
      return this.h;
   }

   @Override
   public nr a(String var1, int var2) {
      nr _snowman = new oe("b").a(g);
      return new oe(String.valueOf(this.h)).a(_snowman).a(f);
   }

   @Override
   public long e() {
      return (long)this.h;
   }

   @Override
   public int f() {
      return this.h;
   }

   @Override
   public short g() {
      return (short)this.h;
   }

   @Override
   public byte h() {
      return this.h;
   }

   @Override
   public double i() {
      return (double)this.h;
   }

   @Override
   public float j() {
      return (float)this.h;
   }

   @Override
   public Number k() {
      return this.h;
   }

   static class a {
      private static final mb[] a = new mb[256];

      static {
         for (int _snowman = 0; _snowman < a.length; _snowman++) {
            a[_snowman] = new mb((byte)(_snowman - 128));
         }
      }
   }
}
