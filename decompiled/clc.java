import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;

public class clc extends cla<cmh> {
   private static final List<btg.c> u = ImmutableList.of(new btg.c(aqe.aS, 1, 1, 1));
   private static final List<btg.c> v = ImmutableList.of(new btg.c(aqe.h, 1, 1, 1));

   public clc(Codec<cmh> var1) {
      super(_snowman);
   }

   @Override
   public cla.a<cmh> a() {
      return clc.a::new;
   }

   @Override
   public List<btg.c> c() {
      return u;
   }

   @Override
   public List<btg.c> j() {
      return v;
   }

   public static class a extends crv<cmh> {
      public a(cla<cmh> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmh var7) {
         crw _snowman = new crw(this.d, _snowman * 16, _snowman * 16);
         this.b.add(_snowman);
         this.b();
      }
   }
}
