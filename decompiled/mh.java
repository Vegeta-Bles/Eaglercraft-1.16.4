import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class mh extends mc<mi> {
   public static final mv<mh> a = new mv<mh>() {
      public mh a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(192L);
         int _snowman = _snowman.readInt();
         _snowman.a(32L * (long)_snowman);
         int[] _snowmanx = new int[_snowman];

         for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
            _snowmanx[_snowmanxx] = _snowman.readInt();
         }

         return new mh(_snowmanx);
      }

      @Override
      public String a() {
         return "INT[]";
      }

      @Override
      public String b() {
         return "TAG_Int_Array";
      }
   };
   private int[] b;

   public mh(int[] var1) {
      this.b = _snowman;
   }

   public mh(List<Integer> var1) {
      this(a(_snowman));
   }

   private static int[] a(List<Integer> var0) {
      int[] _snowman = new int[_snowman.size()];

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         Integer _snowmanxx = _snowman.get(_snowmanx);
         _snowman[_snowmanx] = _snowmanxx == null ? 0 : _snowmanxx;
      }

      return _snowman;
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      _snowman.writeInt(this.b.length);

      for (int _snowman : this.b) {
         _snowman.writeInt(_snowman);
      }
   }

   @Override
   public byte a() {
      return 11;
   }

   @Override
   public mv<mh> b() {
      return a;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("[I;");

      for (int _snowmanx = 0; _snowmanx < this.b.length; _snowmanx++) {
         if (_snowmanx != 0) {
            _snowman.append(',');
         }

         _snowman.append(this.b[_snowmanx]);
      }

      return _snowman.append(']').toString();
   }

   public mh d() {
      int[] _snowman = new int[this.b.length];
      System.arraycopy(this.b, 0, _snowman, 0, this.b.length);
      return new mh(_snowman);
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof mh && Arrays.equals(this.b, ((mh)_snowman).b);
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.b);
   }

   public int[] g() {
      return this.b;
   }

   @Override
   public nr a(String var1, int var2) {
      nr _snowman = new oe("I").a(g);
      nx _snowmanx = new oe("[").a(_snowman).c(";");

      for (int _snowmanxx = 0; _snowmanxx < this.b.length; _snowmanxx++) {
         _snowmanx.c(" ").a(new oe(String.valueOf(this.b[_snowmanxx])).a(f));
         if (_snowmanxx != this.b.length - 1) {
            _snowmanx.c(",");
         }
      }

      _snowmanx.c("]");
      return _snowmanx;
   }

   @Override
   public int size() {
      return this.b.length;
   }

   public mi a(int var1) {
      return mi.a(this.b[_snowman]);
   }

   public mi a(int var1, mi var2) {
      int _snowman = this.b[_snowman];
      this.b[_snowman] = _snowman.f();
      return mi.a(_snowman);
   }

   public void b(int var1, mi var2) {
      this.b = ArrayUtils.add(this.b, _snowman, _snowman.f());
   }

   @Override
   public boolean a(int var1, mt var2) {
      if (_snowman instanceof mq) {
         this.b[_snowman] = ((mq)_snowman).f();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b(int var1, mt var2) {
      if (_snowman instanceof mq) {
         this.b = ArrayUtils.add(this.b, _snowman, ((mq)_snowman).f());
         return true;
      } else {
         return false;
      }
   }

   public mi b(int var1) {
      int _snowman = this.b[_snowman];
      this.b = ArrayUtils.remove(this.b, _snowman);
      return mi.a(_snowman);
   }

   @Override
   public byte d_() {
      return 3;
   }

   @Override
   public void clear() {
      this.b = new int[0];
   }
}
