import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class ma extends mc<mb> {
   public static final mv<ma> a = new mv<ma>() {
      public ma a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(192L);
         int _snowman = _snowman.readInt();
         _snowman.a(8L * (long)_snowman);
         byte[] _snowmanx = new byte[_snowman];
         _snowman.readFully(_snowmanx);
         return new ma(_snowmanx);
      }

      @Override
      public String a() {
         return "BYTE[]";
      }

      @Override
      public String b() {
         return "TAG_Byte_Array";
      }
   };
   private byte[] b;

   public ma(byte[] var1) {
      this.b = _snowman;
   }

   public ma(List<Byte> var1) {
      this(a(_snowman));
   }

   private static byte[] a(List<Byte> var0) {
      byte[] _snowman = new byte[_snowman.size()];

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         Byte _snowmanxx = _snowman.get(_snowmanx);
         _snowman[_snowmanx] = _snowmanxx == null ? 0 : _snowmanxx;
      }

      return _snowman;
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      _snowman.writeInt(this.b.length);
      _snowman.write(this.b);
   }

   @Override
   public byte a() {
      return 7;
   }

   @Override
   public mv<ma> b() {
      return a;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("[B;");

      for (int _snowmanx = 0; _snowmanx < this.b.length; _snowmanx++) {
         if (_snowmanx != 0) {
            _snowman.append(',');
         }

         _snowman.append(this.b[_snowmanx]).append('B');
      }

      return _snowman.append(']').toString();
   }

   @Override
   public mt c() {
      byte[] _snowman = new byte[this.b.length];
      System.arraycopy(this.b, 0, _snowman, 0, this.b.length);
      return new ma(_snowman);
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof ma && Arrays.equals(this.b, ((ma)_snowman).b);
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.b);
   }

   @Override
   public nr a(String var1, int var2) {
      nr _snowman = new oe("B").a(g);
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

   public byte[] d() {
      return this.b;
   }

   @Override
   public int size() {
      return this.b.length;
   }

   public mb a(int var1) {
      return mb.a(this.b[_snowman]);
   }

   public mb a(int var1, mb var2) {
      byte _snowman = this.b[_snowman];
      this.b[_snowman] = _snowman.h();
      return mb.a(_snowman);
   }

   public void b(int var1, mb var2) {
      this.b = ArrayUtils.add(this.b, _snowman, _snowman.h());
   }

   @Override
   public boolean a(int var1, mt var2) {
      if (_snowman instanceof mq) {
         this.b[_snowman] = ((mq)_snowman).h();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b(int var1, mt var2) {
      if (_snowman instanceof mq) {
         this.b = ArrayUtils.add(this.b, _snowman, ((mq)_snowman).h());
         return true;
      } else {
         return false;
      }
   }

   public mb b(int var1) {
      byte _snowman = this.b[_snowman];
      this.b = ArrayUtils.remove(this.b, _snowman);
      return mb.a(_snowman);
   }

   @Override
   public byte d_() {
      return 1;
   }

   @Override
   public void clear() {
      this.b = new byte[0];
   }
}
