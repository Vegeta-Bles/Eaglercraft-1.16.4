import com.google.common.base.Predicates;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public final class aqd {
   public static final Predicate<aqa> a = aqa::aX;
   public static final Predicate<aqm> b = aqm::aX;
   public static final Predicate<aqa> c = var0 -> var0.aX() && !var0.bs() && !var0.br();
   public static final Predicate<aqa> d = var0 -> var0 instanceof aon && var0.aX();
   public static final Predicate<aqa> e = var0 -> !(var0 instanceof bfw) || !var0.a_() && !((bfw)var0).b_();
   public static final Predicate<aqa> f = var0 -> !(var0 instanceof bfw) || !var0.a_() && !((bfw)var0).b_() && var0.l.ad() != aor.a;
   public static final Predicate<aqa> g = var0 -> !var0.a_();

   public static Predicate<aqa> a(double var0, double var2, double var4, double var6) {
      double _snowman = _snowman * _snowman;
      return var8x -> var8x != null && var8x.h(_snowman, _snowman, _snowman) <= _snowman;
   }

   public static Predicate<aqa> a(aqa var0) {
      ddp _snowman = _snowman.bG();
      ddp.a _snowmanx = _snowman == null ? ddp.a.a : _snowman.l();
      return (Predicate<aqa>)(_snowmanx == ddp.a.b ? Predicates.alwaysFalse() : g.and(var3 -> {
         if (!var3.aU()) {
            return false;
         } else if (!_snowman.l.v || var3 instanceof bfw && ((bfw)var3).ez()) {
            ddp _snowmanxx = var3.bG();
            ddp.a _snowmanx = _snowmanxx == null ? ddp.a.a : _snowmanxx.l();
            if (_snowmanx == ddp.a.b) {
               return false;
            } else {
               boolean _snowmanxx = _snowman != null && _snowman.a(_snowmanxx);
               return (_snowman == ddp.a.d || _snowmanx == ddp.a.d) && _snowmanxx ? false : _snowman != ddp.a.c && _snowmanx != ddp.a.c || _snowmanxx;
            }
         } else {
            return false;
         }
      }));
   }

   public static Predicate<aqa> b(aqa var0) {
      return var1 -> {
         while (var1.br()) {
            var1 = var1.ct();
            if (var1 == _snowman) {
               return false;
            }
         }

         return true;
      };
   }

   public static class a implements Predicate<aqa> {
      private final bmb a;

      public a(bmb var1) {
         this.a = _snowman;
      }

      public boolean a(@Nullable aqa var1) {
         if (!_snowman.aX()) {
            return false;
         } else if (!(_snowman instanceof aqm)) {
            return false;
         } else {
            aqm _snowman = (aqm)_snowman;
            return _snowman.e(this.a);
         }
      }
   }
}
