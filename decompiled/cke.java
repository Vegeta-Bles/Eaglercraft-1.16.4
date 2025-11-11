import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;

public class cke extends cla<cmh> {
   private static final List<btg.c> u = ImmutableList.of(
      new btg.c(aqe.f, 10, 2, 3), new btg.c(aqe.bb, 5, 4, 4), new btg.c(aqe.aU, 8, 5, 5), new btg.c(aqe.av, 2, 5, 5), new btg.c(aqe.S, 3, 4, 4)
   );

   public cke(Codec<cmh> var1) {
      super(_snowman);
   }

   protected boolean a(cfy var1, bsy var2, long var3, chx var5, int var6, int var7, bsv var8, brd var9, cmh var10) {
      return _snowman.nextInt(5) < 2;
   }

   @Override
   public cla.a<cmh> a() {
      return cke.a::new;
   }

   @Override
   public List<btg.c> c() {
      return u;
   }

   public static class a extends crv<cmh> {
      public a(cla<cmh> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmh var7) {
         cri.q _snowman = new cri.q(this.d, (_snowman << 4) + 2, (_snowman << 4) + 2);
         this.b.add(_snowman);
         _snowman.a(_snowman, this.b, this.d);
         List<cru> _snowmanx = _snowman.d;

         while (!_snowmanx.isEmpty()) {
            int _snowmanxx = this.d.nextInt(_snowmanx.size());
            cru _snowmanxxx = _snowmanx.remove(_snowmanxx);
            _snowmanxxx.a(_snowman, this.b, this.d);
         }

         this.b();
         this.a(this.d, 48, 70);
      }
   }
}
