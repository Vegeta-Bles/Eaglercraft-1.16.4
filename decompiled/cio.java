import com.mojang.serialization.Codec;

public class cio extends cjx {
   public cio(Codec<cmc> var1) {
      super(_snowman, 33, false, false);
   }

   protected boolean a(cfy var1, bsy var2, long var3, chx var5, int var6, int var7, bsv var8, brd var9, cmc var10) {
      return _snowman.nextInt(5) >= 2;
   }
}
