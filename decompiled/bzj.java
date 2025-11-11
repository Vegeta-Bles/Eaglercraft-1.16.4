import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Optional;
import java.util.Random;

public class bzj extends buo {
   public static final cfg a = cex.aC;
   private static final ImmutableList<gr> b = ImmutableList.of(
      new gr(0, 0, -1), new gr(-1, 0, 0), new gr(0, 0, 1), new gr(1, 0, 0), new gr(-1, 0, -1), new gr(1, 0, -1), new gr(-1, 0, 1), new gr(1, 0, 1)
   );
   private static final ImmutableList<gr> c = new Builder()
      .addAll(b)
      .addAll(b.stream().map(gr::n).iterator())
      .addAll(b.stream().map(gr::o).iterator())
      .add(new gr(0, 1, 0))
      .build();

   public bzj(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman == aot.a && !a(_snowman) && a(_snowman.b(aot.b))) {
         return aou.c;
      } else if (a(_snowman) && h(_snowman)) {
         a(_snowman, _snowman, _snowman);
         if (!_snowman.bC.d) {
            _snowman.g(1);
         }

         return aou.a(_snowman.v);
      } else if (_snowman.c(a) == 0) {
         return aou.c;
      } else if (!a(_snowman)) {
         if (!_snowman.v) {
            this.d(_snowman, _snowman, _snowman);
         }

         return aou.a(_snowman.v);
      } else {
         if (!_snowman.v) {
            aah _snowmanx = (aah)_snowman;
            if (_snowmanx.M() != _snowman.Y() || !_snowmanx.K().equals(_snowman)) {
               _snowmanx.a(_snowman.Y(), _snowman, 0.0F, false, true);
               _snowman.a(null, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, adq.mA, adr.e, 1.0F, 1.0F);
               return aou.a;
            }
         }

         return aou.b;
      }
   }

   private static boolean a(bmb var0) {
      return _snowman.b() == bmd.dq;
   }

   private static boolean h(ceh var0) {
      return _snowman.c(a) < 4;
   }

   private static boolean a(fx var0, brx var1) {
      cux _snowman = _snowman.b(_snowman);
      if (!_snowman.a(aef.b)) {
         return false;
      } else if (_snowman.b()) {
         return true;
      } else {
         float _snowmanx = (float)_snowman.e();
         if (_snowmanx < 2.0F) {
            return false;
         } else {
            cux _snowmanxx = _snowman.b(_snowman.c());
            return !_snowmanxx.a(aef.b);
         }
      }
   }

   private void d(ceh var1, brx var2, final fx var3) {
      _snowman.a(_snowman, false);
      boolean _snowman = gc.c.a.a().map(_snowman::a).anyMatch(var1x -> a(var1x, _snowman));
      final boolean _snowmanx = _snowman || _snowman.b(_snowman.b()).a(aef.b);
      brq _snowmanxx = new brq() {
         @Override
         public Optional<Float> a(brp var1, brc var2, fx var3x, ceh var4, cux var5x) {
            return _snowman.equals(_snowman) && _snowman ? Optional.of(bup.A.f()) : super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
         }
      };
      _snowman.a(null, apk.a(), _snowmanxx, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, 5.0F, true, brp.a.c);
   }

   public static boolean a(brx var0) {
      return _snowman.k().i();
   }

   public static void a(brx var0, fx var1, ceh var2) {
      _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman.c(a) + 1)), 3);
      _snowman.a(null, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, adq.my, adr.e, 1.0F, 1.0F);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.c(a) != 0) {
         if (_snowman.nextInt(100) == 0) {
            _snowman.a(null, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, adq.mx, adr.e, 1.0F, 1.0F);
         }

         double _snowman = (double)_snowman.u() + 0.5 + (0.5 - _snowman.nextDouble());
         double _snowmanx = (double)_snowman.v() + 1.0;
         double _snowmanxx = (double)_snowman.w() + 0.5 + (0.5 - _snowman.nextDouble());
         double _snowmanxxx = (double)_snowman.nextFloat() * 0.04;
         _snowman.a(hh.as, _snowman, _snowmanx, _snowmanxx, 0.0, _snowmanxxx, 0.0);
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   public static int a(ceh var0, int var1) {
      return afm.d((float)(_snowman.c(a) - 0) / 4.0F * (float)_snowman);
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      return a(_snowman, 15);
   }

   public static Optional<dcn> a(aqe<?> var0, brg var1, fx var2) {
      Optional<dcn> _snowman = a(_snowman, _snowman, _snowman, true);
      return _snowman.isPresent() ? _snowman : a(_snowman, _snowman, _snowman, false);
   }

   private static Optional<dcn> a(aqe<?> var0, brg var1, fx var2, boolean var3) {
      fx.a _snowman = new fx.a();
      UnmodifiableIterator var5 = c.iterator();

      while (var5.hasNext()) {
         gr _snowmanx = (gr)var5.next();
         _snowman.g(_snowman).h(_snowmanx);
         dcn _snowmanxx = bho.a(_snowman, _snowman, _snowman, _snowman);
         if (_snowmanxx != null) {
            return Optional.of(_snowmanxx);
         }
      }

      return Optional.empty();
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
