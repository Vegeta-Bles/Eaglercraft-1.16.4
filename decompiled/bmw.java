import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;

public class bmw extends bkv {
   private static final Set<buo> c = Sets.newHashSet(
      new buo[]{
         bup.cG,
         bup.j,
         bup.k,
         bup.l,
         bup.bX,
         bup.i,
         bup.E,
         bup.dT,
         bup.C,
         bup.D,
         bup.cE,
         bup.cC,
         bup.cM,
         bup.iE,
         bup.jM,
         bup.jN,
         bup.jO,
         bup.jP,
         bup.jQ,
         bup.jR,
         bup.jS,
         bup.jT,
         bup.jU,
         bup.jV,
         bup.jW,
         bup.jX,
         bup.jY,
         bup.jZ,
         bup.ka,
         bup.kb,
         bup.cN
      }
   );
   protected static final Map<buo, ceh> a = Maps.newHashMap(ImmutableMap.of(bup.i, bup.iE.n()));

   public bmw(bnh var1, float var2, float var3, blx.a var4) {
      super(_snowman, _snowman, _snowman, c, _snowman);
   }

   @Override
   public boolean b(ceh var1) {
      return _snowman.a(bup.cC) || _snowman.a(bup.cE);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      if (_snowman.j() == gc.a) {
         return aou.c;
      } else {
         bfw _snowmanxxx = _snowman.n();
         ceh _snowmanxxxx = a.get(_snowmanxx.b());
         ceh _snowmanxxxxx = null;
         if (_snowmanxxxx != null && _snowman.d_(_snowmanx.b()).g()) {
            _snowman.a(_snowmanxxx, _snowmanx, adq.nb, adr.e, 1.0F, 1.0F);
            _snowmanxxxxx = _snowmanxxxx;
         } else if (_snowmanxx.b() instanceof buy && _snowmanxx.c(buy.b)) {
            if (!_snowman.s_()) {
               _snowman.a(null, 1009, _snowmanx, 0);
            }

            buy.c(_snowman, _snowmanx, _snowmanxx);
            _snowmanxxxxx = _snowmanxx.a(buy.b, Boolean.valueOf(false));
         }

         if (_snowmanxxxxx != null) {
            if (!_snowman.v) {
               _snowman.a(_snowmanx, _snowmanxxxxx, 11);
               if (_snowmanxxx != null) {
                  _snowman.m().a(1, _snowmanxxx, var1x -> var1x.d(_snowman.o()));
               }
            }

            return aou.a(_snowman.v);
         } else {
            return aou.c;
         }
      }
   }
}
