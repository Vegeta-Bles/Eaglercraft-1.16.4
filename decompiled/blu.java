import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;

public class blu extends bkv {
   private static final Set<buo> c = ImmutableSet.of(
      bup.iK, bup.mn, bup.gA, bup.ke, bup.nb, bup.mw, new buo[]{bup.an, bup.ao, bup.ak, bup.ah, bup.ai, bup.am, bup.al, bup.aj}
   );
   protected static final Map<buo, ceh> a = Maps.newHashMap(ImmutableMap.of(bup.i, bup.bX.n(), bup.iE, bup.bX.n(), bup.j, bup.bX.n(), bup.k, bup.j.n()));

   protected blu(bnh var1, int var2, float var3, blx.a var4) {
      super((float)_snowman, _snowman, _snowman, c, _snowman);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      if (_snowman.j() != gc.a && _snowman.d_(_snowmanx.b()).g()) {
         ceh _snowmanxx = a.get(_snowman.d_(_snowmanx).b());
         if (_snowmanxx != null) {
            bfw _snowmanxxx = _snowman.n();
            _snowman.a(_snowmanxxx, _snowmanx, adq.fA, adr.e, 1.0F, 1.0F);
            if (!_snowman.v) {
               _snowman.a(_snowmanx, _snowmanxx, 11);
               if (_snowmanxxx != null) {
                  _snowman.m().a(1, _snowmanxxx, var1x -> var1x.d(_snowman.o()));
               }
            }

            return aou.a(_snowman.v);
         }
      }

      return aou.c;
   }
}
