import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;

public class blm extends blx {
   public blm(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      if (!_snowman.v) {
         bmb _snowmanx = _snowman.m();
         dcn _snowmanxx = _snowman.k();
         gc _snowmanxxx = _snowman.j();
         bgh _snowmanxxxx = new bgh(_snowman, _snowman.n(), _snowmanxx.b + (double)_snowmanxxx.i() * 0.15, _snowmanxx.c + (double)_snowmanxxx.j() * 0.15, _snowmanxx.d + (double)_snowmanxxx.k() * 0.15, _snowmanx);
         _snowman.c(_snowmanxxxx);
         _snowmanx.g(1);
      }

      return aou.a(_snowman.v);
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      if (_snowman.ef()) {
         bmb _snowman = _snowman.b(_snowman);
         if (!_snowman.v) {
            _snowman.c(new bgh(_snowman, _snowman, _snowman));
            if (!_snowman.bC.d) {
               _snowman.g(1);
            }
         }

         return aov.a(_snowman.b(_snowman), _snowman.s_());
      } else {
         return aov.c(_snowman.b(_snowman));
      }
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      md _snowman = _snowman.b("Fireworks");
      if (_snowman != null) {
         if (_snowman.c("Flight", 99)) {
            _snowman.add(new of("item.minecraft.firework_rocket.flight").c(" ").c(String.valueOf(_snowman.f("Flight"))).a(k.h));
         }

         mj _snowmanx = _snowman.d("Explosions", 10);
         if (!_snowmanx.isEmpty()) {
            for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
               md _snowmanxxx = _snowmanx.a(_snowmanxx);
               List<nr> _snowmanxxxx = Lists.newArrayList();
               bln.a(_snowmanxxx, _snowmanxxxx);
               if (!_snowmanxxxx.isEmpty()) {
                  for (int _snowmanxxxxx = 1; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
                     _snowmanxxxx.set(_snowmanxxxxx, new oe("  ").a(_snowmanxxxx.get(_snowmanxxxxx)).a(k.h));
                  }

                  _snowman.addAll(_snowmanxxxx);
               }
            }
         }
      }
   }

   public static enum a {
      a(0, "small_ball"),
      b(1, "large_ball"),
      c(2, "star"),
      d(3, "creeper"),
      e(4, "burst");

      private static final blm.a[] f = Arrays.stream(values()).sorted(Comparator.comparingInt(var0 -> var0.g)).toArray(blm.a[]::new);
      private final int g;
      private final String h;

      private a(int var3, String var4) {
         this.g = _snowman;
         this.h = _snowman;
      }

      public int a() {
         return this.g;
      }

      public String b() {
         return this.h;
      }

      public static blm.a a(int var0) {
         return _snowman >= 0 && _snowman < f.length ? f[_snowman] : a;
      }
   }
}
