import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class btt extends buu {
   public static final cfb a = bxm.aq;
   private final can b;
   private static final Map<gc, ddh> c = Maps.newEnumMap(
      ImmutableMap.of(
         gc.d,
         buo.a(6.0, 0.0, 6.0, 10.0, 10.0, 16.0),
         gc.e,
         buo.a(0.0, 0.0, 6.0, 10.0, 10.0, 10.0),
         gc.c,
         buo.a(6.0, 0.0, 0.0, 10.0, 10.0, 10.0),
         gc.f,
         buo.a(6.0, 0.0, 6.0, 16.0, 10.0, 10.0)
      )
   );

   protected btt(can var1, ceg.c var2) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c));
      this.b = _snowman;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return c.get(_snowman.c(a));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return !_snowman.a(this.b) && _snowman == _snowman.c(a) ? this.b.c().n().a(cam.a, Integer.valueOf(7)) : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected boolean c(ceh var1, brc var2, fx var3) {
      return _snowman.a(bup.bX);
   }

   protected blx b() {
      if (this.b == bup.cK) {
         return bmd.nj;
      } else {
         return this.b == bup.dK ? bmd.nk : bmd.a;
      }
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return new bmb(this.b());
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a(_snowman.c(a)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(_snowman.a(_snowman.c(a)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }
}
