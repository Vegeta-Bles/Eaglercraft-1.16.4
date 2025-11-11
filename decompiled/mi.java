import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class mi extends mq {
   public static final mv<mi> a = new mv<mi>() {
      public mi a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(96L);
         return mi.a(_snowman.readInt());
      }

      @Override
      public String a() {
         return "INT";
      }

      @Override
      public String b() {
         return "TAG_Int";
      }

      @Override
      public boolean c() {
         return true;
      }
   };
   private final int b;

   private mi(int var1) {
      this.b = _snowman;
   }

   public static mi a(int var0) {
      return _snowman >= -128 && _snowman <= 1024 ? mi.a.a[_snowman + 128] : new mi(_snowman);
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      _snowman.writeInt(this.b);
   }

   @Override
   public byte a() {
      return 3;
   }

   @Override
   public mv<mi> b() {
      return a;
   }

   @Override
   public String toString() {
      return String.valueOf(this.b);
   }

   public mi d() {
      return this;
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof mi && this.b == ((mi)_snowman).b;
   }

   @Override
   public int hashCode() {
      return this.b;
   }

   @Override
   public nr a(String var1, int var2) {
      return new oe(String.valueOf(this.b)).a(f);
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
      return (short)(this.b & 65535);
   }

   @Override
   public byte h() {
      return (byte)(this.b & 0xFF);
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
      static final mi[] a = new mi[1153];

      static {
         for (int _snowman = 0; _snowman < a.length; _snowman++) {
            a[_snowman] = new mi(-128 + _snowman);
         }
      }
   }
}
