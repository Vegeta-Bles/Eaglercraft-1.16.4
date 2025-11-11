import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;

public class bna extends blx {
   private static final Map<aqe<?>, bna> a = Maps.newIdentityHashMap();
   private final int b;
   private final int c;
   private final aqe<?> d;

   public bna(aqe<?> var1, int var2, int var3, blx.a var4) {
      super(_snowman);
      this.d = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      a.put(_snowman, this);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      if (!(_snowman instanceof aag)) {
         return aou.a;
      } else {
         bmb _snowmanx = _snowman.m();
         fx _snowmanxx = _snowman.a();
         gc _snowmanxxx = _snowman.j();
         ceh _snowmanxxxx = _snowman.d_(_snowmanxx);
         if (_snowmanxxxx.a(bup.bP)) {
            ccj _snowmanxxxxx = _snowman.c(_snowmanxx);
            if (_snowmanxxxxx instanceof cdi) {
               bqz _snowmanxxxxxx = ((cdi)_snowmanxxxxx).d();
               aqe<?> _snowmanxxxxxxx = this.a(_snowmanx.o());
               _snowmanxxxxxx.a(_snowmanxxxxxxx);
               _snowmanxxxxx.X_();
               _snowman.a(_snowmanxx, _snowmanxxxx, _snowmanxxxx, 3);
               _snowmanx.g(1);
               return aou.b;
            }
         }

         fx _snowmanxxxxx;
         if (_snowmanxxxx.k(_snowman, _snowmanxx).b()) {
            _snowmanxxxxx = _snowmanxx;
         } else {
            _snowmanxxxxx = _snowmanxx.a(_snowmanxxx);
         }

         aqe<?> _snowmanxxxxxx = this.a(_snowmanx.o());
         if (_snowmanxxxxxx.a((aag)_snowman, _snowmanx, _snowman.n(), _snowmanxxxxx, aqp.m, true, !Objects.equals(_snowmanxx, _snowmanxxxxx) && _snowmanxxx == gc.b) != null) {
            _snowmanx.g(1);
         }

         return aou.b;
      }
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      dcl _snowmanx = a(_snowman, _snowman, brf.b.b);
      if (_snowmanx.c() != dcl.a.b) {
         return aov.c(_snowman);
      } else if (!(_snowman instanceof aag)) {
         return aov.a(_snowman);
      } else {
         dcj _snowmanxx = (dcj)_snowmanx;
         fx _snowmanxxx = _snowmanxx.a();
         if (!(_snowman.d_(_snowmanxxx).b() instanceof byb)) {
            return aov.c(_snowman);
         } else if (_snowman.a(_snowman, _snowmanxxx) && _snowman.a(_snowmanxxx, _snowmanxx.b(), _snowman)) {
            aqe<?> _snowmanxxxx = this.a(_snowman.o());
            if (_snowmanxxxx.a((aag)_snowman, _snowman, _snowman, _snowmanxxx, aqp.m, false, false) == null) {
               return aov.c(_snowman);
            } else {
               if (!_snowman.bC.d) {
                  _snowman.g(1);
               }

               _snowman.b(aea.c.b(this));
               return aov.b(_snowman);
            }
         } else {
            return aov.d(_snowman);
         }
      }
   }

   public boolean a(@Nullable md var1, aqe<?> var2) {
      return Objects.equals(this.a(_snowman), _snowman);
   }

   public int a(int var1) {
      return _snowman == 0 ? this.b : this.c;
   }

   @Nullable
   public static bna a(@Nullable aqe<?> var0) {
      return a.get(_snowman);
   }

   public static Iterable<bna> f() {
      return Iterables.unmodifiableIterable(a.values());
   }

   public aqe<?> a(@Nullable md var1) {
      if (_snowman != null && _snowman.c("EntityTag", 10)) {
         md _snowman = _snowman.p("EntityTag");
         if (_snowman.c("id", 8)) {
            return aqe.a(_snowman.l("id")).orElse(this.d);
         }
      }

      return this.d;
   }

   public Optional<aqn> a(bfw var1, aqn var2, aqe<? extends aqn> var3, aag var4, dcn var5, bmb var6) {
      if (!this.a(_snowman.o(), _snowman)) {
         return Optional.empty();
      } else {
         aqn _snowman;
         if (_snowman instanceof apy) {
            _snowman = ((apy)_snowman).a(_snowman, (apy)_snowman);
         } else {
            _snowman = _snowman.a(_snowman);
         }

         if (_snowman == null) {
            return Optional.empty();
         } else {
            _snowman.a(true);
            if (!_snowman.w_()) {
               return Optional.empty();
            } else {
               _snowman.b(_snowman.a(), _snowman.b(), _snowman.c(), 0.0F, 0.0F);
               _snowman.l(_snowman);
               if (_snowman.t()) {
                  _snowman.a(_snowman.r());
               }

               if (!_snowman.bC.d) {
                  _snowman.g(1);
               }

               return Optional.of(_snowman);
            }
         }
      }
   }
}
