import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class cbj extends btm {
   public static final cfb a = bxm.aq;
   private static final Map<gc, ddh> b = Maps.newEnumMap(
      ImmutableMap.of(
         gc.c,
         buo.a(0.0, 0.0, 14.0, 16.0, 12.5, 16.0),
         gc.d,
         buo.a(0.0, 0.0, 0.0, 16.0, 12.5, 2.0),
         gc.e,
         buo.a(14.0, 0.0, 0.0, 16.0, 12.5, 16.0),
         gc.f,
         buo.a(0.0, 0.0, 0.0, 2.0, 12.5, 16.0)
      )
   );

   public cbj(bkx var1, ceg.c var2) {
      super(_snowman, _snowman);
      this.j(this.n.b().a(a, gc.c));
   }

   @Override
   public String i() {
      return this.h().a();
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return _snowman.d_(_snowman.a(_snowman.c(a).f())).c().b();
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman == _snowman.c(a).f() && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b.get(_snowman.c(a));
   }

   @Override
   public ceh a(bny var1) {
      ceh _snowman = this.n();
      brz _snowmanx = _snowman.p();
      fx _snowmanxx = _snowman.a();
      gc[] _snowmanxxx = _snowman.e();

      for (gc _snowmanxxxx : _snowmanxxx) {
         if (_snowmanxxxx.n().d()) {
            gc _snowmanxxxxx = _snowmanxxxx.f();
            _snowman = _snowman.a(a, _snowmanxxxxx);
            if (_snowman.a(_snowmanx, _snowmanxx)) {
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
