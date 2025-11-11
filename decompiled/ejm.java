import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;

public class ejm {
   private static final Map<vk, ejn> a = Maps.newHashMap();
   private static final vk b = new vk("damaged");
   private static final vk c = new vk("damage");
   private static final ejn d = (var0, var1, var2) -> var0.f() ? 1.0F : 0.0F;
   private static final ejn e = (var0, var1, var2) -> afm.a((float)var0.g() / (float)var0.h(), 0.0F, 1.0F);
   private static final Map<blx, Map<vk, ejn>> f = Maps.newHashMap();

   private static ejn a(vk var0, ejn var1) {
      a.put(_snowman, _snowman);
      return _snowman;
   }

   private static void a(blx var0, vk var1, ejn var2) {
      f.computeIfAbsent(_snowman, var0x -> Maps.newHashMap()).put(_snowman, _snowman);
   }

   @Nullable
   public static ejn a(blx var0, vk var1) {
      if (_snowman.j() > 0) {
         if (c.equals(_snowman)) {
            return e;
         }

         if (b.equals(_snowman)) {
            return d;
         }
      }

      ejn _snowman = a.get(_snowman);
      if (_snowman != null) {
         return _snowman;
      } else {
         Map<vk, ejn> _snowmanx = f.get(_snowman);
         return _snowmanx == null ? null : _snowmanx.get(_snowman);
      }
   }

   static {
      a(new vk("lefthanded"), (var0, var1, var2) -> var2 != null && var2.dV() != aqi.b ? 1.0F : 0.0F);
      a(new vk("cooldown"), (var0, var1, var2) -> var2 instanceof bfw ? ((bfw)var2).eT().a(var0.b(), 0.0F) : 0.0F);
      a(new vk("custom_model_data"), (var0, var1, var2) -> var0.n() ? (float)var0.o().h("CustomModelData") : 0.0F);
      a(bmd.kc, new vk("pull"), (var0, var1, var2) -> {
         if (var2 == null) {
            return 0.0F;
         } else {
            return var2.dY() != var0 ? 0.0F : (float)(var0.k() - var2.dZ()) / 20.0F;
         }
      });
      a(bmd.kc, new vk("pulling"), (var0, var1, var2) -> var2 != null && var2.dW() && var2.dY() == var0 ? 1.0F : 0.0F);
      a(bmd.mj, new vk("time"), new ejn() {
         private double a;
         private double b;
         private long c;

         @Override
         public float call(bmb var1, @Nullable dwt var2, @Nullable aqm var3) {
            aqa _snowman = (aqa)(_snowman != null ? _snowman : _snowman.A());
            if (_snowman == null) {
               return 0.0F;
            } else {
               if (_snowman == null && _snowman.l instanceof dwt) {
                  _snowman = (dwt)_snowman.l;
               }

               if (_snowman == null) {
                  return 0.0F;
               } else {
                  double _snowmanx;
                  if (_snowman.k().e()) {
                     _snowmanx = (double)_snowman.f(1.0F);
                  } else {
                     _snowmanx = Math.random();
                  }

                  _snowmanx = this.a(_snowman, _snowmanx);
                  return (float)_snowmanx;
               }
            }
         }

         private double a(brx var1, double var2) {
            if (_snowman.T() != this.c) {
               this.c = _snowman.T();
               double _snowman = _snowman - this.a;
               _snowman = afm.c(_snowman + 0.5, 1.0) - 0.5;
               this.b += _snowman * 0.1;
               this.b *= 0.9;
               this.a = afm.c(this.a + this.b, 1.0);
            }

            return this.a;
         }
      });
      a(bmd.mh, new vk("angle"), new ejn() {
         private final ejm.a a = new ejm.a();
         private final ejm.a b = new ejm.a();

         @Override
         public float call(bmb var1, @Nullable dwt var2, @Nullable aqm var3) {
            aqa _snowman = (aqa)(_snowman != null ? _snowman : _snowman.A());
            if (_snowman == null) {
               return 0.0F;
            } else {
               if (_snowman == null && _snowman.l instanceof dwt) {
                  _snowman = (dwt)_snowman.l;
               }

               fx _snowmanx = bkq.d(_snowman) ? this.a(_snowman, _snowman.p()) : this.a(_snowman);
               long _snowmanxx = _snowman.T();
               if (_snowmanx != null && !(_snowman.cA().c((double)_snowmanx.u() + 0.5, _snowman.cA().b(), (double)_snowmanx.w() + 0.5) < 1.0E-5F)) {
                  boolean _snowmanxxx = _snowman instanceof bfw && ((bfw)_snowman).ez();
                  double _snowmanxxxx = 0.0;
                  if (_snowmanxxx) {
                     _snowmanxxxx = (double)_snowman.p;
                  } else if (_snowman instanceof bcp) {
                     _snowmanxxxx = this.a((bcp)_snowman);
                  } else if (_snowman instanceof bcv) {
                     _snowmanxxxx = (double)(180.0F - ((bcv)_snowman).a(0.5F) / (float) (Math.PI * 2) * 360.0F);
                  } else if (_snowman != null) {
                     _snowmanxxxx = (double)_snowman.aA;
                  }

                  _snowmanxxxx = afm.c(_snowmanxxxx / 360.0, 1.0);
                  double _snowmanxxxxx = this.a(dcn.a(_snowmanx), _snowman) / (float) (Math.PI * 2);
                  double _snowmanxxxxxx;
                  if (_snowmanxxx) {
                     if (this.a.a(_snowmanxx)) {
                        this.a.a(_snowmanxx, 0.5 - (_snowmanxxxx - 0.25));
                     }

                     _snowmanxxxxxx = _snowmanxxxxx + this.a.a;
                  } else {
                     _snowmanxxxxxx = 0.5 - (_snowmanxxxx - 0.25 - _snowmanxxxxx);
                  }

                  return afm.b((float)_snowmanxxxxxx, 1.0F);
               } else {
                  if (this.b.a(_snowmanxx)) {
                     this.b.a(_snowmanxx, Math.random());
                  }

                  double _snowmanxxxxx = this.b.a + (double)((float)_snowman.hashCode() / 2.1474836E9F);
                  return afm.b((float)_snowmanxxxxx, 1.0F);
               }
            }
         }

         @Nullable
         private fx a(dwt var1) {
            return _snowman.k().e() ? _snowman.u() : null;
         }

         @Nullable
         private fx a(brx var1, md var2) {
            boolean _snowman = _snowman.e("LodestonePos");
            boolean _snowmanx = _snowman.e("LodestoneDimension");
            if (_snowman && _snowmanx) {
               Optional<vj<brx>> _snowmanxx = bkq.a(_snowman);
               if (_snowmanxx.isPresent() && _snowman.Y() == _snowmanxx.get()) {
                  return mp.b(_snowman.p("LodestonePos"));
               }
            }

            return null;
         }

         private double a(bcp var1) {
            gc _snowman = _snowman.bZ();
            int _snowmanx = _snowman.n().c() ? 90 * _snowman.e().a() : 0;
            return (double)afm.b(180 + _snowman.d() * 90 + _snowman.p() * 45 + _snowmanx);
         }

         private double a(dcn var1, aqa var2) {
            return Math.atan2(_snowman.c() - _snowman.cH(), _snowman.a() - _snowman.cD());
         }
      });
      a(bmd.qQ, new vk("pull"), (var0, var1, var2) -> {
         if (var2 == null) {
            return 0.0F;
         } else {
            return bkt.d(var0) ? 0.0F : (float)(var0.k() - var2.dZ()) / (float)bkt.g(var0);
         }
      });
      a(bmd.qQ, new vk("pulling"), (var0, var1, var2) -> var2 != null && var2.dW() && var2.dY() == var0 && !bkt.d(var0) ? 1.0F : 0.0F);
      a(bmd.qQ, new vk("charged"), (var0, var1, var2) -> var2 != null && bkt.d(var0) ? 1.0F : 0.0F);
      a(bmd.qQ, new vk("firework"), (var0, var1, var2) -> var2 != null && bkt.d(var0) && bkt.a(var0, bmd.po) ? 1.0F : 0.0F);
      a(bmd.qo, new vk("broken"), (var0, var1, var2) -> bld.d(var0) ? 0.0F : 1.0F);
      a(bmd.mi, new vk("cast"), (var0, var1, var2) -> {
         if (var2 == null) {
            return 0.0F;
         } else {
            boolean _snowman = var2.dD() == var0;
            boolean _snowmanx = var2.dE() == var0;
            if (var2.dD().b() instanceof blp) {
               _snowmanx = false;
            }

            return (_snowman || _snowmanx) && var2 instanceof bfw && ((bfw)var2).bI != null ? 1.0F : 0.0F;
         }
      });
      a(bmd.qn, new vk("blocking"), (var0, var1, var2) -> var2 != null && var2.dW() && var2.dY() == var0 ? 1.0F : 0.0F);
      a(bmd.qM, new vk("throwing"), (var0, var1, var2) -> var2 != null && var2.dW() && var2.dY() == var0 ? 1.0F : 0.0F);
   }

   static class a {
      private double a;
      private double b;
      private long c;

      private a() {
      }

      private boolean a(long var1) {
         return this.c != _snowman;
      }

      private void a(long var1, double var3) {
         this.c = _snowman;
         double _snowman = _snowman - this.a;
         _snowman = afm.c(_snowman + 0.5, 1.0) - 0.5;
         this.b += _snowman * 0.1;
         this.b *= 0.8;
         this.a = afm.c(this.a + this.b, 1.0);
      }
   }
}
