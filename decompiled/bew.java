import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;

public class bew {
   protected static arf<?> a(bev var0, arf<bev> var1) {
      b(_snowman, _snowman);
      c(_snowman, _snowman);
      d(_snowman, _snowman);
      _snowman.a(ImmutableSet.of(bhf.a));
      _snowman.b(bhf.b);
      _snowman.e();
      return _snowman;
   }

   protected static void a(bev var0) {
      gf _snowman = gf.a(_snowman.l.Y(), _snowman.cB());
      _snowman.cJ().a(ayd.b, _snowman);
   }

   private static void b(bev var0, arf<bev> var1) {
      _snowman.a(bhf.a, 0, ImmutableList.of(new asu(45, 90), new asy(), new asp(), new atz()));
   }

   private static void c(bev var0, arf<bev> var1) {
      _snowman.a(bhf.b, 10, ImmutableList.of(new atw<>(bew::a), a(), b(), new atn(aqe.bc, 4)));
   }

   private static void d(bev var0, arf<bev> var1) {
      _snowman.a(bhf.k, 10, ImmutableList.of(new aty(var1x -> !a((ber)_snowman, var1x)), new atq(1.0F), new asv(20)), ayd.o);
   }

   private static ati<bev> a() {
      return new ati<>(
         ImmutableList.of(
            Pair.of(new atl(aqe.bc, 8.0F), 1),
            Pair.of(new atl(aqe.ai, 8.0F), 1),
            Pair.of(new atl(aqe.aj, 8.0F), 1),
            Pair.of(new atl(8.0F), 1),
            Pair.of(new asc(30, 60), 1)
         )
      );
   }

   private static ati<bev> b() {
      return new ati<>(
         ImmutableList.of(
            Pair.of(new atc(0.6F), 2),
            Pair.of(aso.a(aqe.ai, 8, ayd.q, 0.6F, 2), 2),
            Pair.of(aso.a(aqe.aj, 8, ayd.q, 0.6F, 2), 2),
            Pair.of(new aub(ayd.b, 0.6F, 2, 100), 2),
            Pair.of(new aua(ayd.b, 0.6F, 5), 2),
            Pair.of(new asc(30, 60), 1)
         )
      );
   }

   protected static void b(bev var0) {
      arf<bev> _snowman = _snowman.cJ();
      bhf _snowmanx = _snowman.f().orElse(null);
      _snowman.a(ImmutableList.of(bhf.k, bhf.b));
      bhf _snowmanxx = _snowman.f().orElse(null);
      if (_snowmanx != _snowmanxx) {
         d(_snowman);
      }

      _snowman.s(_snowman.a(ayd.o));
   }

   private static boolean a(ber var0, aqm var1) {
      return a(_snowman).filter(var1x -> var1x == _snowman).isPresent();
   }

   private static Optional<? extends aqm> a(ber var0) {
      Optional<aqm> _snowman = arw.a(_snowman, ayd.L);
      if (_snowman.isPresent() && a(_snowman.get())) {
         return _snowman;
      } else {
         Optional<? extends aqm> _snowmanx = a(_snowman, ayd.l);
         return _snowmanx.isPresent() ? _snowmanx : _snowman.cJ().c(ayd.K);
      }
   }

   private static boolean a(aqm var0) {
      return aqd.f.test(_snowman);
   }

   private static Optional<? extends aqm> a(ber var0, ayd<? extends aqm> var1) {
      return _snowman.cJ().c(_snowman).filter(var1x -> var1x.a(_snowman, 12.0));
   }

   protected static void a(bev var0, aqm var1) {
      if (!(_snowman instanceof ber)) {
         bet.a(_snowman, _snowman);
      }
   }

   protected static void c(bev var0) {
      if ((double)_snowman.l.t.nextFloat() < 0.0125) {
         d(_snowman);
      }
   }

   private static void d(bev var0) {
      _snowman.cJ().f().ifPresent(var1 -> {
         if (var1 == bhf.k) {
            _snowman.eT();
         }
      });
   }
}
