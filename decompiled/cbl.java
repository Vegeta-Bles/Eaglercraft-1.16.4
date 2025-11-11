import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class cbl extends bzt {
   public static final cfb c = bxm.aq;
   private static final Map<gc, ddh> d = Maps.newEnumMap(
      ImmutableMap.of(
         gc.c,
         buo.a(0.0, 4.5, 14.0, 16.0, 12.5, 16.0),
         gc.d,
         buo.a(0.0, 4.5, 0.0, 16.0, 12.5, 2.0),
         gc.f,
         buo.a(0.0, 4.5, 0.0, 2.0, 12.5, 16.0),
         gc.e,
         buo.a(14.0, 4.5, 0.0, 16.0, 12.5, 16.0)
      )
   );

   public cbl(ceg.c var1, cfq var2) {
      super(_snowman, _snowman);
      this.j(this.n.b().a(c, gc.c).a(a, Boolean.valueOf(false)));
   }

   @Override
   public String i() {
      return this.h().a();
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return d.get(_snowman.c(c));
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return _snowman.d_(_snowman.a(_snowman.c(c).f())).c().b();
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      ceh _snowman = this.n();
      cux _snowmanx = _snowman.p().b(_snowman.a());
      brz _snowmanxx = _snowman.p();
      fx _snowmanxxx = _snowman.a();
      gc[] _snowmanxxxx = _snowman.e();

      for (gc _snowmanxxxxx : _snowmanxxxx) {
         if (_snowmanxxxxx.n().d()) {
            gc _snowmanxxxxxx = _snowmanxxxxx.f();
            _snowman = _snowman.a(c, _snowmanxxxxxx);
            if (_snowman.a(_snowmanxx, _snowmanxxx)) {
               return _snowman.a(a, Boolean.valueOf(_snowmanx.a() == cuy.c));
            }
         }
      }

      return null;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman.f() == _snowman.c(c) && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(c, _snowman.a(_snowman.c(c)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(_snowman.a(_snowman.c(c)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(c, a);
   }
}
