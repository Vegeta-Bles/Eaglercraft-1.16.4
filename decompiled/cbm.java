import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class cbm extends btq {
   public static final cfb a = bxm.aq;
   private static final Map<gc, ddh> b = Maps.newEnumMap(
      ImmutableMap.of(
         gc.c,
         buo.a(4.0, 4.0, 8.0, 12.0, 12.0, 16.0),
         gc.d,
         buo.a(4.0, 4.0, 0.0, 12.0, 12.0, 8.0),
         gc.f,
         buo.a(0.0, 4.0, 4.0, 8.0, 12.0, 12.0),
         gc.e,
         buo.a(8.0, 4.0, 4.0, 16.0, 12.0, 12.0)
      )
   );

   protected cbm(bzv.a var1, ceg.c var2) {
      super(_snowman, _snowman);
      this.j(this.n.b().a(a, gc.c));
   }

   @Override
   public String i() {
      return this.h().a();
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b.get(_snowman.c(a));
   }

   @Override
   public ceh a(bny var1) {
      ceh _snowman = this.n();
      brc _snowmanx = _snowman.p();
      fx _snowmanxx = _snowman.a();
      gc[] _snowmanxxx = _snowman.e();

      for (gc _snowmanxxxx : _snowmanxxx) {
         if (_snowmanxxxx.n().d()) {
            gc _snowmanxxxxx = _snowmanxxxx.f();
            _snowman = _snowman.a(a, _snowmanxxxxx);
            if (!_snowmanx.d_(_snowmanxx.a(_snowmanxxxx)).a(_snowman)) {
               return _snowman;
            }
         }
      }

      return null;
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
