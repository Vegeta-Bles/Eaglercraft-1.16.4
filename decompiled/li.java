import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.commons.lang3.mutable.MutableInt;

public class li {
   public static lw a = new lo();

   public static void a(lf var0, fx var1, ll var2) {
      _snowman.a();
      _snowman.a(_snowman);
      _snowman.a(new lg() {
         @Override
         public void a(lf var1) {
            li.b(_snowman, bup.dg);
         }

         @Override
         public void c(lf var1) {
            li.b(_snowman, _snowman.q() ? bup.dm : bup.cZ);
            li.b(_snowman, x.d(_snowman.n()));
            li.c(_snowman);
         }
      });
      _snowman.a(_snowman, 2);
   }

   public static Collection<lf> a(Collection<la> var0, fx var1, bzm var2, aag var3, ll var4, int var5) {
      lb _snowman = new lb(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.b();
      return _snowman.a();
   }

   public static Collection<lf> b(Collection<lu> var0, fx var1, bzm var2, aag var3, ll var4, int var5) {
      return a(a(_snowman), _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static Collection<la> a(Collection<lu> var0) {
      Map<String, Collection<lu>> _snowman = Maps.newHashMap();
      _snowman.forEach(var1x -> {
         String _snowmanx = var1x.e();
         Collection<lu> _snowmanx = _snowman.computeIfAbsent(_snowmanx, var0x -> Lists.newArrayList());
         _snowmanx.add(var1x);
      });
      return _snowman.keySet().stream().flatMap(var1x -> {
         Collection<lu> _snowmanx = _snowman.get(var1x);
         Consumer<aag> _snowmanx = lh.c(var1x);
         MutableInt _snowmanxx = new MutableInt();
         return Streams.stream(Iterables.partition(_snowmanx, 100)).map(var4x -> new la(var1x + ":" + _snowman.incrementAndGet(), _snowman, _snowman));
      }).collect(Collectors.toList());
   }

   private static void c(lf var0) {
      Throwable _snowman = _snowman.n();
      String _snowmanx = (_snowman.q() ? "" : "(optional) ") + _snowman.c() + " failed! " + x.d(_snowman);
      a(_snowman.g(), _snowman.q() ? k.m : k.o, _snowmanx);
      if (_snowman instanceof kz) {
         kz _snowmanxx = (kz)_snowman;
         a(_snowman.g(), _snowmanxx.c(), _snowmanxx.a());
      }

      a.a(_snowman);
   }

   private static void b(lf var0, buo var1) {
      aag _snowman = _snowman.g();
      fx _snowmanx = _snowman.d();
      fx _snowmanxx = new fx(-1, -1, -1);
      fx _snowmanxxx = ctb.a(_snowmanx.a(_snowmanxx), byg.a, _snowman.t(), _snowmanx);
      _snowman.a(_snowmanxxx, bup.es.n().a(_snowman.t()));
      fx _snowmanxxxx = _snowmanxxx.b(0, 1, 0);
      _snowman.a(_snowmanxxxx, _snowman.n());

      for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 1; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = -1; _snowmanxxxxxx <= 1; _snowmanxxxxxx++) {
            fx _snowmanxxxxxxx = _snowmanxxx.b(_snowmanxxxxx, -1, _snowmanxxxxxx);
            _snowman.a(_snowmanxxxxxxx, bup.bF.n());
         }
      }
   }

   private static void b(lf var0, String var1) {
      aag _snowman = _snowman.g();
      fx _snowmanx = _snowman.d();
      fx _snowmanxx = new fx(-1, 1, -1);
      fx _snowmanxxx = ctb.a(_snowmanx.a(_snowmanxx), byg.a, _snowman.t(), _snowmanx);
      _snowman.a(_snowmanxxx, bup.lY.n().a(_snowman.t()));
      ceh _snowmanxxxx = _snowman.d_(_snowmanxxx);
      bmb _snowmanxxxxx = a(_snowman.c(), _snowman.q(), _snowman);
      bxy.a(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   private static bmb a(String var0, boolean var1, String var2) {
      bmb _snowman = new bmb(bmd.oT);
      mj _snowmanx = new mj();
      StringBuffer _snowmanxx = new StringBuffer();
      Arrays.stream(_snowman.split("\\.")).forEach(var1x -> _snowman.append(var1x).append('\n'));
      if (!_snowman) {
         _snowmanxx.append("(optional)\n");
      }

      _snowmanxx.append("-------------------\n");
      _snowmanx.add(ms.a(_snowmanxx.toString() + _snowman));
      _snowman.a("pages", _snowmanx);
      return _snowman;
   }

   private static void a(aag var0, k var1, String var2) {
      _snowman.a(var0x -> true).forEach(var2x -> var2x.a(new oe(_snowman).a(_snowman), x.b));
   }

   public static void a(aag var0) {
      rz.a(_snowman);
   }

   private static void a(aag var0, fx var1, String var2) {
      rz.a(_snowman, _snowman, _snowman, -2130771968, Integer.MAX_VALUE);
   }

   public static void a(aag var0, fx var1, ll var2, int var3) {
      _snowman.a();
      fx _snowman = _snowman.b(-_snowman, 0, -_snowman);
      fx _snowmanx = _snowman.b(_snowman, 0, _snowman);
      fx.b(_snowman, _snowmanx).filter(var1x -> _snowman.d_(var1x).a(bup.mY)).forEach(var1x -> {
         cdj _snowmanxx = (cdj)_snowman.c(var1x);
         fx _snowmanx = _snowmanxx.o();
         cra _snowmanxx = lq.b(_snowmanxx);
         lq.a(_snowmanxx, _snowmanx.v(), _snowman);
      });
   }
}
