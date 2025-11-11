import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class cob extends coi {
   public static final Codec<cob> a = Codec.unit(() -> cob.b);
   public static final cob b = new cob();

   private cob() {
      super(cok.a.a);
   }

   @Override
   public List<ctb.c> a(csw var1, fx var2, bzm var3, Random var4) {
      return Collections.emptyList();
   }

   @Override
   public cra a(csw var1, fx var2, bzm var3) {
      return cra.a();
   }

   @Override
   public boolean a(csw var1, bsr var2, bsn var3, cfy var4, fx var5, fx var6, bzm var7, cra var8, Random var9, boolean var10) {
      return true;
   }

   @Override
   public coj<?> a() {
      return coj.d;
   }

   @Override
   public String toString() {
      return "Empty";
   }
}
