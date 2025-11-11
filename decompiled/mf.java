import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class mf implements mt {
   public static final mv<mf> a = new mv<mf>() {
      public mf a(DataInput var1, int var2, mm var3) {
         _snowman.a(64L);
         return mf.b;
      }

      @Override
      public String a() {
         return "END";
      }

      @Override
      public String b() {
         return "TAG_End";
      }

      @Override
      public boolean c() {
         return true;
      }
   };
   public static final mf b = new mf();

   private mf() {
   }

   @Override
   public void a(DataOutput var1) throws IOException {
   }

   @Override
   public byte a() {
      return 0;
   }

   @Override
   public mv<mf> b() {
      return a;
   }

   @Override
   public String toString() {
      return "END";
   }

   public mf d() {
      return this;
   }

   @Override
   public nr a(String var1, int var2) {
      return oe.d;
   }
}
