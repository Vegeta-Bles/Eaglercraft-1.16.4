import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;

public class awi extends avv {
   protected final aqu a;
   private final double b;
   private cxd c;
   private fx d;
   private final boolean e;
   private final List<fx> f = Lists.newArrayList();
   private final int g;
   private final BooleanSupplier h;

   public awi(aqu var1, double var2, boolean var4, int var5, BooleanSupplier var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.e = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.a(EnumSet.of(avv.a.a));
      if (!azi.a(_snowman)) {
         throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
      }
   }

   @Override
   public boolean a() {
      if (!azi.a(this.a)) {
         return false;
      } else {
         this.g();
         if (this.e && this.a.l.M()) {
            return false;
         } else {
            aag _snowman = (aag)this.a.l;
            fx _snowmanx = this.a.cB();
            if (!_snowman.a(_snowmanx, 6)) {
               return false;
            } else {
               dcn _snowmanxx = azj.a(this.a, 15, 7, var3x -> {
                  if (!_snowman.a_(var3x)) {
                     return Double.NEGATIVE_INFINITY;
                  } else {
                     Optional<fx> _snowmanxxx = _snowman.y().c(azr.b, this::a, var3x, 10, azo.b.b);
                     return !_snowmanxxx.isPresent() ? Double.NEGATIVE_INFINITY : -_snowmanxxx.get().j(_snowman);
                  }
               });
               if (_snowmanxx == null) {
                  return false;
               } else {
                  Optional<fx> _snowmanxxx = _snowman.y().c(azr.b, this::a, new fx(_snowmanxx), 10, azo.b.b);
                  if (!_snowmanxxx.isPresent()) {
                     return false;
                  } else {
                     this.d = _snowmanxxx.get().h();
                     ayi _snowmanxxxx = (ayi)this.a.x();
                     boolean _snowmanxxxxx = _snowmanxxxx.f();
                     _snowmanxxxx.a(this.h.getAsBoolean());
                     this.c = _snowmanxxxx.a(this.d, 0);
                     _snowmanxxxx.a(_snowmanxxxxx);
                     if (this.c == null) {
                        dcn _snowmanxxxxxx = azj.b(this.a, 10, 7, dcn.c(this.d));
                        if (_snowmanxxxxxx == null) {
                           return false;
                        }

                        _snowmanxxxx.a(this.h.getAsBoolean());
                        this.c = this.a.x().a(_snowmanxxxxxx.b, _snowmanxxxxxx.c, _snowmanxxxxxx.d, 0);
                        _snowmanxxxx.a(_snowmanxxxxx);
                        if (this.c == null) {
                           return false;
                        }
                     }

                     for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < this.c.e(); _snowmanxxxxxxx++) {
                        cxb _snowmanxxxxxxxx = this.c.a(_snowmanxxxxxxx);
                        fx _snowmanxxxxxxxxx = new fx(_snowmanxxxxxxxx.a, _snowmanxxxxxxxx.b + 1, _snowmanxxxxxxxx.c);
                        if (bwb.a(this.a.l, _snowmanxxxxxxxxx)) {
                           this.c = this.a.x().a((double)_snowmanxxxxxxxx.a, (double)_snowmanxxxxxxxx.b, (double)_snowmanxxxxxxxx.c, 0);
                           break;
                        }
                     }

                     return this.c != null;
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean b() {
      return this.a.x().m() ? false : !this.d.a(this.a.cA(), (double)(this.a.cy() + (float)this.g));
   }

   @Override
   public void c() {
      this.a.x().a(this.c, this.b);
   }

   @Override
   public void d() {
      if (this.a.x().m() || this.d.a(this.a.cA(), (double)this.g)) {
         this.f.add(this.d);
      }
   }

   private boolean a(fx var1) {
      for (fx _snowman : this.f) {
         if (Objects.equals(_snowman, _snowman)) {
            return false;
         }
      }

      return true;
   }

   private void g() {
      if (this.f.size() > 15) {
         this.f.remove(0);
      }
   }
}
