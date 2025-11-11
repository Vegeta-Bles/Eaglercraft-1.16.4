import java.util.Objects;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class brh extends AbstractSpliterator<ddh> {
   @Nullable
   private final aqa a;
   private final dci b;
   private final dcs c;
   private final ga d;
   private final fx.a e;
   private final ddh f;
   private final brg g;
   private boolean h;
   private final BiPredicate<ceh, fx> i;

   public brh(brg var1, @Nullable aqa var2, dci var3) {
      this(_snowman, _snowman, _snowman, (var0, var1x) -> true);
   }

   public brh(brg var1, @Nullable aqa var2, dci var3, BiPredicate<ceh, fx> var4) {
      super(Long.MAX_VALUE, 1280);
      this.c = _snowman == null ? dcs.a() : dcs.a(_snowman);
      this.e = new fx.a();
      this.f = dde.a(_snowman);
      this.g = _snowman;
      this.h = _snowman != null;
      this.a = _snowman;
      this.b = _snowman;
      this.i = _snowman;
      int _snowman = afm.c(_snowman.a - 1.0E-7) - 1;
      int _snowmanx = afm.c(_snowman.d + 1.0E-7) + 1;
      int _snowmanxx = afm.c(_snowman.b - 1.0E-7) - 1;
      int _snowmanxxx = afm.c(_snowman.e + 1.0E-7) + 1;
      int _snowmanxxxx = afm.c(_snowman.c - 1.0E-7) - 1;
      int _snowmanxxxxx = afm.c(_snowman.f + 1.0E-7) + 1;
      this.d = new ga(_snowman, _snowmanxx, _snowmanxxxx, _snowmanx, _snowmanxxx, _snowmanxxxxx);
   }

   @Override
   public boolean tryAdvance(Consumer<? super ddh> var1) {
      return this.h && this.b(_snowman) || this.a(_snowman);
   }

   boolean a(Consumer<? super ddh> var1) {
      while (this.d.a()) {
         int _snowman = this.d.b();
         int _snowmanx = this.d.c();
         int _snowmanxx = this.d.d();
         int _snowmanxxx = this.d.e();
         if (_snowmanxxx != 3) {
            brc _snowmanxxxx = this.a(_snowman, _snowmanxx);
            if (_snowmanxxxx != null) {
               this.e.d(_snowman, _snowmanx, _snowmanxx);
               ceh _snowmanxxxxx = _snowmanxxxx.d_(this.e);
               if (this.i.test(_snowmanxxxxx, this.e) && (_snowmanxxx != 1 || _snowmanxxxxx.d()) && (_snowmanxxx != 2 || _snowmanxxxxx.a(bup.bo))) {
                  ddh _snowmanxxxxxx = _snowmanxxxxx.b(this.g, this.e, this.c);
                  if (_snowmanxxxxxx == dde.b()) {
                     if (this.b.a((double)_snowman, (double)_snowmanx, (double)_snowmanxx, (double)_snowman + 1.0, (double)_snowmanx + 1.0, (double)_snowmanxx + 1.0)) {
                        _snowman.accept(_snowmanxxxxxx.a((double)_snowman, (double)_snowmanx, (double)_snowmanxx));
                        return true;
                     }
                  } else {
                     ddh _snowmanxxxxxxx = _snowmanxxxxxx.a((double)_snowman, (double)_snowmanx, (double)_snowmanxx);
                     if (dde.c(_snowmanxxxxxxx, this.f, dcr.i)) {
                        _snowman.accept(_snowmanxxxxxxx);
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   @Nullable
   private brc a(int var1, int var2) {
      int _snowman = _snowman >> 4;
      int _snowmanx = _snowman >> 4;
      return this.g.c(_snowman, _snowmanx);
   }

   boolean b(Consumer<? super ddh> var1) {
      Objects.requireNonNull(this.a);
      this.h = false;
      cfu _snowman = this.g.f();
      dci _snowmanx = this.a.cc();
      if (!a(_snowman, _snowmanx)) {
         ddh _snowmanxx = _snowman.c();
         if (!b(_snowmanxx, _snowmanx) && a(_snowmanxx, _snowmanx)) {
            _snowman.accept(_snowmanxx);
            return true;
         }
      }

      return false;
   }

   private static boolean a(ddh var0, dci var1) {
      return dde.c(_snowman, dde.a(_snowman.g(1.0E-7)), dcr.i);
   }

   private static boolean b(ddh var0, dci var1) {
      return dde.c(_snowman, dde.a(_snowman.h(1.0E-7)), dcr.i);
   }

   public static boolean a(cfu var0, dci var1) {
      double _snowman = (double)afm.c(_snowman.e());
      double _snowmanx = (double)afm.c(_snowman.f());
      double _snowmanxx = (double)afm.f(_snowman.g());
      double _snowmanxxx = (double)afm.f(_snowman.h());
      return _snowman.a > _snowman && _snowman.a < _snowmanxx && _snowman.c > _snowmanx && _snowman.c < _snowmanxxx && _snowman.d > _snowman && _snowman.d < _snowmanxx && _snowman.f > _snowmanx && _snowman.f < _snowmanxxx;
   }
}
