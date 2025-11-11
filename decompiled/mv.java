import java.io.DataInput;
import java.io.IOException;

public interface mv<T extends mt> {
   T b(DataInput var1, int var2, mm var3) throws IOException;

   default boolean c() {
      return false;
   }

   String a();

   String b();

   static mv<mf> a(final int var0) {
      return new mv<mf>() {
         public mf a(DataInput var1, int var2, mm var3) throws IOException {
            throw new IllegalArgumentException("Invalid tag id: " + _snowman);
         }

         @Override
         public String a() {
            return "INVALID[" + _snowman + "]";
         }

         @Override
         public String b() {
            return "UNKNOWN_" + _snowman;
         }
      };
   }
}
