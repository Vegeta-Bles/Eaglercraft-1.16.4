import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;

public class ben {
   private static final afh a = afu.a(5, 20);
   private static final afh b = afh.a(5, 16);

   protected static arf<?> a(arf<bem> var0) {
      b(_snowman);
      c(_snowman);
      d(_snowman);
      e(_snowman);
      _snowman.a(ImmutableSet.of(bhf.a));
      _snowman.b(bhf.b);
      _snowman.e();
      return _snowman;
   }

   private static void b(arf<bem> var0) {
      _snowman.a(bhf.a, 0, ImmutableList.of(new asu(45, 90), new asy()));
   }

   private static void c(arf<bem> var0) {
      _snowman.a(
         bhf.b,
         10,
         ImmutableList.of(
            new aru(ayd.ag, 200),
            new arq(aqe.G, 0.6F),
            atp.a(ayd.ag, 1.0F, 8, true),
            new atw<>(ben::d),
            new ath<>(bem::eL, (arv<? super aqu>)atp.b(ayd.aa, 0.4F, 8, false)),
            new atj<>(new atl(8.0F), afh.a(30, 60)),
            new ars(b, 0.6F),
            a()
         )
      );
   }

   private static void d(arf<bem> var0) {
      _snowman.a(
         bhf.k,
         10,
         ImmutableList.of(
            new aru(ayd.ag, 200),
            new arq(aqe.G, 0.6F),
            new atq(1.0F),
            new ath<>(bem::eL, new asv(40)),
            new ath<>(apy::w_, new asv(15)),
            new aty(),
            new ase<>(ben::i, ayd.o)
         ),
         ayd.o
      );
   }

   private static void e(arf<bem> var0) {
      _snowman.a(bhf.n, 10, ImmutableList.of(atp.b(ayd.z, 1.3F, 15, false), a(), new atj<>(new atl(8.0F), afh.a(30, 60)), new ase<>(ben::e, ayd.z)), ayd.z);
   }

   private static ati<bem> a() {
      return new ati<>(ImmutableList.of(Pair.of(new atc(0.4F), 2), Pair.of(new ats(0.4F, 3), 2), Pair.of(new asc(30, 60), 1)));
   }

   protected static void a(bem var0) {
      arf<bem> _snowman = _snowman.cJ();
      bhf _snowmanx = _snowman.f().orElse(null);
      _snowman.a(ImmutableList.of(bhf.k, bhf.n, bhf.b));
      bhf _snowmanxx = _snowman.f().orElse(null);
      if (_snowmanx != _snowmanxx) {
         b(_snowman).ifPresent(_snowman::a);
      }

      _snowman.s(_snowman.a(ayd.o));
   }

   protected static void a(bem var0, aqm var1) {
      if (!_snowman.w_()) {
         if (_snowman.X() == aqe.ai && f(_snowman)) {
            e(_snowman, _snowman);
            c(_snowman, _snowman);
         } else {
            h(_snowman, _snowman);
         }
      }
   }

   private static void c(bem var0, aqm var1) {
      g(_snowman).forEach(var1x -> d(var1x, _snowman));
   }

   private static void d(bem var0, aqm var1) {
      arf<bem> _snowman = _snowman.cJ();
      aqm var2 = arw.a(_snowman, _snowman.c(ayd.z), _snowman);
      var2 = arw.a(_snowman, _snowman.c(ayd.o), var2);
      e(_snowman, var2);
   }

   private static void e(bem var0, aqm var1) {
      _snowman.cJ().b(ayd.o);
      _snowman.cJ().b(ayd.m);
      _snowman.cJ().a(ayd.z, _snowman, (long)a.a(_snowman.l.t));
   }

   private static Optional<? extends aqm> d(bem var0) {
      return !c(_snowman) && !i(_snowman) ? _snowman.cJ().c(ayd.l) : Optional.empty();
   }

   static boolean a(bem var0, fx var1) {
      Optional<fx> _snowman = _snowman.cJ().c(ayd.ag);
      return _snowman.isPresent() && _snowman.get().a(_snowman, 8.0);
   }

   private static boolean e(bem var0) {
      return _snowman.eL() && !f(_snowman);
   }

   private static boolean f(bem var0) {
      if (_snowman.w_()) {
         return false;
      } else {
         int _snowman = _snowman.cJ().c(ayd.ac).orElse(0);
         int _snowmanx = _snowman.cJ().c(ayd.ad).orElse(0) + 1;
         return _snowman > _snowmanx;
      }
   }

   protected static void b(bem var0, aqm var1) {
      arf<bem> _snowman = _snowman.cJ();
      _snowman.b(ayd.ah);
      _snowman.b(ayd.r);
      if (_snowman.w_()) {
         d(_snowman, _snowman);
      } else {
         f(_snowman, _snowman);
      }
   }

   private static void f(bem var0, aqm var1) {
      if (!_snowman.cJ().c(bhf.n) || _snowman.X() != aqe.ai) {
         if (aqd.f.test(_snowman)) {
            if (_snowman.X() != aqe.G) {
               if (!arw.a(_snowman, _snowman, 4.0)) {
                  g(_snowman, _snowman);
                  h(_snowman, _snowman);
               }
            }
         }
      }
   }

   private static void g(bem var0, aqm var1) {
      arf<bem> _snowman = _snowman.cJ();
      _snowman.b(ayd.D);
      _snowman.b(ayd.r);
      _snowman.a(ayd.o, _snowman, 200L);
   }

   private static void h(bem var0, aqm var1) {
      g(_snowman).forEach(var1x -> i(var1x, _snowman));
   }

   private static void i(bem var0, aqm var1) {
      if (!c(_snowman)) {
         Optional<aqm> _snowman = _snowman.cJ().c(ayd.o);
         aqm _snowmanx = arw.a(_snowman, _snowman, _snowman);
         g(_snowman, _snowmanx);
      }
   }

   public static Optional<adp> b(bem var0) {
      return _snowman.cJ().f().map(var1 -> a(_snowman, var1));
   }

   private static adp a(bem var0, bhf var1) {
      if (_snowman == bhf.n || _snowman.eN()) {
         return adq.fH;
      } else if (_snowman == bhf.k) {
         return adq.fC;
      } else {
         return h(_snowman) ? adq.fH : adq.fB;
      }
   }

   private static List<bem> g(bem var0) {
      return _snowman.cJ().c(ayd.Z).orElse(ImmutableList.of());
   }

   private static boolean h(bem var0) {
      return _snowman.cJ().a(ayd.ag);
   }

   private static boolean i(bem var0) {
      return _snowman.cJ().a(ayd.r);
   }

   protected static boolean c(bem var0) {
      return _snowman.cJ().a(ayd.ah);
   }
}
