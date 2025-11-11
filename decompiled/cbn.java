import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public class cbn extends cba {
   public static final cfb a = bxm.aq;
   private static final Map<gc, ddh> b = Maps.newEnumMap(
      ImmutableMap.of(
         gc.c,
         buo.a(5.5, 3.0, 11.0, 10.5, 13.0, 16.0),
         gc.d,
         buo.a(5.5, 3.0, 0.0, 10.5, 13.0, 5.0),
         gc.e,
         buo.a(11.0, 3.0, 5.5, 16.0, 13.0, 10.5),
         gc.f,
         buo.a(0.0, 3.0, 5.5, 5.0, 13.0, 10.5)
      )
   );

   protected cbn(ceg.c var1, hf var2) {
      super(_snowman, _snowman);
      this.j(this.n.b().a(a, gc.c));
   }

   @Override
   public String i() {
      return this.h().a();
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return h(_snowman);
   }

   public static ddh h(ceh var0) {
      return b.get(_snowman.c(a));
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
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman.f() == _snowman.c(a) && !_snowman.a(_snowman, _snowman) ? bup.a.n() : _snowman;
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      gc _snowman = _snowman.c(a);
      double _snowmanx = (double)_snowman.u() + 0.5;
      double _snowmanxx = (double)_snowman.v() + 0.7;
      double _snowmanxxx = (double)_snowman.w() + 0.5;
      double _snowmanxxxx = 0.22;
      double _snowmanxxxxx = 0.27;
      gc _snowmanxxxxxx = _snowman.f();
      _snowman.a(hh.S, _snowmanx + 0.27 * (double)_snowmanxxxxxx.i(), _snowmanxx + 0.22, _snowmanxxx + 0.27 * (double)_snowmanxxxxxx.k(), 0.0, 0.0, 0.0);
      _snowman.a(this.e, _snowmanx + 0.27 * (double)_snowmanxxxxxx.i(), _snowmanxx + 0.22, _snowmanxxx + 0.27 * (double)_snowmanxxxxxx.k(), 0.0, 0.0, 0.0);
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
