import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class buc extends btz {
   public static final cfb a = bxm.aq;
   private static final Map<gc, ddh> c = Maps.newEnumMap(
      ImmutableMap.of(
         gc.c,
         buo.a(0.0, 4.0, 5.0, 16.0, 12.0, 16.0),
         gc.d,
         buo.a(0.0, 4.0, 0.0, 16.0, 12.0, 11.0),
         gc.e,
         buo.a(5.0, 4.0, 0.0, 16.0, 12.0, 16.0),
         gc.f,
         buo.a(0.0, 4.0, 0.0, 11.0, 12.0, 16.0)
      )
   );

   protected buc(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, Boolean.valueOf(true)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return c.get(_snowman.c(a));
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
      _snowman.a(a, b);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(b)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return _snowman.f() == _snowman.c(a) && !_snowman.a(_snowman, _snowman) ? bup.a.n() : _snowman;
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      gc _snowman = _snowman.c(a);
      fx _snowmanx = _snowman.a(_snowman.f());
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      return _snowmanxx.d(_snowman, _snowmanx, _snowman);
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      ceh _snowman = super.a(_snowman);
      brz _snowmanx = _snowman.p();
      fx _snowmanxx = _snowman.a();
      gc[] _snowmanxxx = _snowman.e();

      for (gc _snowmanxxxx : _snowmanxxx) {
         if (_snowmanxxxx.n().d()) {
            _snowman = _snowman.a(a, _snowmanxxxx.f());
            if (_snowman.a(_snowmanx, _snowmanxx)) {
               return _snowman;
            }
         }
      }

      return null;
   }
}
