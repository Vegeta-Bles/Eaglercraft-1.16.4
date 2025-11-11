import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class arw {
   public static void a(aqm var0, aqm var1, float var2) {
      d(_snowman, _snowman);
      b(_snowman, _snowman, _snowman);
   }

   public static boolean a(arf<?> var0, aqm var1) {
      return _snowman.c(ayd.h).filter(var1x -> var1x.contains(_snowman)).isPresent();
   }

   public static boolean a(arf<?> var0, ayd<? extends aqm> var1, aqe<?> var2) {
      return a(_snowman, _snowman, var1x -> var1x.X() == _snowman);
   }

   private static boolean a(arf<?> var0, ayd<? extends aqm> var1, Predicate<aqm> var2) {
      return _snowman.c(_snowman).filter(_snowman).filter(aqm::aX).filter(var1x -> a(_snowman, var1x)).isPresent();
   }

   private static void d(aqm var0, aqm var1) {
      a(_snowman, _snowman);
      a(_snowman, _snowman);
   }

   public static void a(aqm var0, aqm var1) {
      _snowman.cJ().a(ayd.n, new asd(_snowman, true));
   }

   private static void b(aqm var0, aqm var1, float var2) {
      int _snowman = 2;
      a(_snowman, _snowman, _snowman, 2);
      a(_snowman, _snowman, _snowman, 2);
   }

   public static void a(aqm var0, aqa var1, float var2, int var3) {
      ayf _snowman = new ayf(new asd(_snowman, false), _snowman, _snowman);
      _snowman.cJ().a(ayd.n, new asd(_snowman, true));
      _snowman.cJ().a(ayd.m, _snowman);
   }

   public static void a(aqm var0, fx var1, float var2, int var3) {
      ayf _snowman = new ayf(new arx(_snowman), _snowman, _snowman);
      _snowman.cJ().a(ayd.n, new arx(_snowman));
      _snowman.cJ().a(ayd.m, _snowman);
   }

   public static void a(aqm var0, bmb var1, dcn var2) {
      double _snowman = _snowman.cG() - 0.3F;
      bcv _snowmanx = new bcv(_snowman.l, _snowman.cD(), _snowman, _snowman.cH(), _snowman);
      float _snowmanxx = 0.3F;
      dcn _snowmanxxx = _snowman.d(_snowman.cA());
      _snowmanxxx = _snowmanxxx.d().a(0.3F);
      _snowmanx.f(_snowmanxxx);
      _snowmanx.m();
      _snowman.l.c(_snowmanx);
   }

   public static gp a(aag var0, gp var1, int var2) {
      int _snowman = _snowman.b(_snowman);
      return gp.a(_snowman, _snowman).filter(var2x -> _snowman.b(var2x) < _snowman).min(Comparator.comparingInt(_snowman::b)).orElse(_snowman);
   }

   public static boolean a(aqn var0, aqm var1, int var2) {
      blx _snowman = _snowman.dD().b();
      if (_snowman instanceof bmo && _snowman.a((bmo)_snowman)) {
         int _snowmanx = ((bmo)_snowman).d() - _snowman;
         return _snowman.a(_snowman, (double)_snowmanx);
      } else {
         return b(_snowman, _snowman);
      }
   }

   public static boolean b(aqm var0, aqm var1) {
      double _snowman = _snowman.h(_snowman.cD(), _snowman.cE(), _snowman.cH());
      double _snowmanx = (double)(_snowman.cy() * 2.0F * _snowman.cy() * 2.0F + _snowman.cy());
      return _snowman <= _snowmanx;
   }

   public static boolean a(aqm var0, aqm var1, double var2) {
      Optional<aqm> _snowman = _snowman.cJ().c(ayd.o);
      if (!_snowman.isPresent()) {
         return false;
      } else {
         double _snowmanx = _snowman.e(_snowman.get().cA());
         double _snowmanxx = _snowman.e(_snowman.cA());
         return _snowmanxx > _snowmanx + _snowman * _snowman;
      }
   }

   public static boolean c(aqm var0, aqm var1) {
      arf<?> _snowman = _snowman.cJ();
      return !_snowman.a(ayd.h) ? false : _snowman.c(ayd.h).get().contains(_snowman);
   }

   public static aqm a(aqm var0, Optional<aqm> var1, aqm var2) {
      return !_snowman.isPresent() ? _snowman : a(_snowman, _snowman.get(), _snowman);
   }

   public static aqm a(aqm var0, aqm var1, aqm var2) {
      dcn _snowman = _snowman.cA();
      dcn _snowmanx = _snowman.cA();
      return _snowman.e(_snowman) < _snowman.e(_snowmanx) ? _snowman : _snowman;
   }

   public static Optional<aqm> a(aqm var0, ayd<UUID> var1) {
      Optional<UUID> _snowman = _snowman.cJ().c(_snowman);
      return _snowman.map(var1x -> (aqm)((aag)_snowman.l).a(var1x));
   }

   public static Stream<bfj> a(bfj var0, Predicate<bfj> var1) {
      return _snowman.cJ()
         .c(ayd.g)
         .map(var2 -> var2.stream().filter(var1x -> var1x instanceof bfj && var1x != _snowman).map(var0x -> (bfj)var0x).filter(aqm::aX).filter(_snowman))
         .orElseGet(Stream::empty);
   }
}
