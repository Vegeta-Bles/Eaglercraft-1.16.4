import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class efr<T extends aqm, M extends duc<T>> extends eeu<T> implements egk<T, M> {
   private static final Logger a = LogManager.getLogger();
   protected M e;
   protected final List<eit<T, M>> f = Lists.newArrayList();

   public efr(eet var1, M var2, float var3) {
      super(_snowman);
      this.e = _snowman;
      this.c = _snowman;
   }

   protected final boolean a(eit<T, M> var1) {
      return this.f.add(_snowman);
   }

   @Override
   public M c() {
      return this.e;
   }

   public void a(T var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      this.e.c = this.d(_snowman, _snowman);
      this.e.d = _snowman.br();
      this.e.e = _snowman.w_();
      float _snowman = afm.h(_snowman, _snowman.aB, _snowman.aA);
      float _snowmanx = afm.h(_snowman, _snowman.aD, _snowman.aC);
      float _snowmanxx = _snowmanx - _snowman;
      if (_snowman.br() && _snowman.ct() instanceof aqm) {
         aqm _snowmanxxx = (aqm)_snowman.ct();
         _snowman = afm.h(_snowman, _snowmanxxx.aB, _snowmanxxx.aA);
         _snowmanxx = _snowmanx - _snowman;
         float _snowmanxxxx = afm.g(_snowmanxx);
         if (_snowmanxxxx < -85.0F) {
            _snowmanxxxx = -85.0F;
         }

         if (_snowmanxxxx >= 85.0F) {
            _snowmanxxxx = 85.0F;
         }

         _snowman = _snowmanx - _snowmanxxxx;
         if (_snowmanxxxx * _snowmanxxxx > 2500.0F) {
            _snowman += _snowmanxxxx * 0.2F;
         }

         _snowmanxx = _snowmanx - _snowman;
      }

      float _snowmanxxxxx = afm.g(_snowman, _snowman.s, _snowman.q);
      if (_snowman.ae() == aqx.c) {
         gc _snowmanxxxxxx = _snowman.eo();
         if (_snowmanxxxxxx != null) {
            float _snowmanxxxxxxx = _snowman.e(aqx.a) - 0.1F;
            _snowman.a((double)((float)(-_snowmanxxxxxx.i()) * _snowmanxxxxxxx), 0.0, (double)((float)(-_snowmanxxxxxx.k()) * _snowmanxxxxxxx));
         }
      }

      float _snowmanxxxxxx = this.a(_snowman, _snowman);
      this.a(_snowman, _snowman, _snowmanxxxxxx, _snowman, _snowman);
      _snowman.a(-1.0F, -1.0F, 1.0F);
      this.a(_snowman, _snowman, _snowman);
      _snowman.a(0.0, -1.501F, 0.0);
      float _snowmanxxxxxxx = 0.0F;
      float _snowmanxxxxxxxx = 0.0F;
      if (!_snowman.br() && _snowman.aX()) {
         _snowmanxxxxxxx = afm.g(_snowman, _snowman.au, _snowman.av);
         _snowmanxxxxxxxx = _snowman.aw - _snowman.av * (1.0F - _snowman);
         if (_snowman.w_()) {
            _snowmanxxxxxxxx *= 3.0F;
         }

         if (_snowmanxxxxxxx > 1.0F) {
            _snowmanxxxxxxx = 1.0F;
         }
      }

      this.e.a(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxx, _snowman);
      this.e.a(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxx, _snowmanxxxxx);
      djz _snowmanxxxxxxxxx = djz.C();
      boolean _snowmanxxxxxxxxxx = this.d(_snowman);
      boolean _snowmanxxxxxxxxxxx = !_snowmanxxxxxxxxxx && !_snowman.c(_snowmanxxxxxxxxx.s);
      boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx.b(_snowman);
      eao _snowmanxxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
      if (_snowmanxxxxxxxxxxxxx != null) {
         dfq _snowmanxxxxxxxxxxxxxx = _snowman.getBuffer(_snowmanxxxxxxxxxxxxx);
         int _snowmanxxxxxxxxxxxxxxx = c(_snowman, this.b(_snowman, _snowman));
         this.e.a(_snowman, _snowmanxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxxxx, 1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxx ? 0.15F : 1.0F);
      }

      if (!_snowman.a_()) {
         for (eit<T, M> _snowmanxxxxxxxxxxxxxx : this.f) {
            _snowmanxxxxxxxxxxxxxx.a(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxx, _snowmanxxxxxxx, _snowman, _snowmanxxxxxx, _snowmanxx, _snowmanxxxxx);
         }
      }

      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   protected eao a(T var1, boolean var2, boolean var3, boolean var4) {
      vk _snowman = this.a(_snowman);
      if (_snowman) {
         return eao.f(_snowman);
      } else if (_snowman) {
         return this.e.a(_snowman);
      } else {
         return _snowman ? eao.n(_snowman) : null;
      }
   }

   public static int c(aqm var0, float var1) {
      return ejw.a(ejw.a(_snowman), ejw.a(_snowman.an > 0 || _snowman.aq > 0));
   }

   protected boolean d(T var1) {
      return !_snowman.bF();
   }

   private static float a(gc var0) {
      switch (_snowman) {
         case d:
            return 90.0F;
         case e:
            return 0.0F;
         case c:
            return 270.0F;
         case f:
            return 180.0F;
         default:
            return 0.0F;
      }
   }

   protected boolean a(T var1) {
      return false;
   }

   protected void a(T var1, dfm var2, float var3, float var4, float var5) {
      if (this.a(_snowman)) {
         _snowman += (float)(Math.cos((double)_snowman.K * 3.25) * Math.PI * 0.4F);
      }

      aqx _snowman = _snowman.ae();
      if (_snowman != aqx.c) {
         _snowman.a(g.d.a(180.0F - _snowman));
      }

      if (_snowman.aq > 0) {
         float _snowmanx = ((float)_snowman.aq + _snowman - 1.0F) / 20.0F * 1.6F;
         _snowmanx = afm.c(_snowmanx);
         if (_snowmanx > 1.0F) {
            _snowmanx = 1.0F;
         }

         _snowman.a(g.f.a(_snowmanx * this.c(_snowman)));
      } else if (_snowman.dR()) {
         _snowman.a(g.b.a(-90.0F - _snowman.q));
         _snowman.a(g.d.a(((float)_snowman.K + _snowman) * -75.0F));
      } else if (_snowman == aqx.c) {
         gc _snowmanx = _snowman.eo();
         float _snowmanxx = _snowmanx != null ? a(_snowmanx) : _snowman;
         _snowman.a(g.d.a(_snowmanxx));
         _snowman.a(g.f.a(this.c(_snowman)));
         _snowman.a(g.d.a(270.0F));
      } else if (_snowman.S() || _snowman instanceof bfw) {
         String _snowmanx = k.a(_snowman.R().getString());
         if (("Dinnerbone".equals(_snowmanx) || "Grumm".equals(_snowmanx)) && (!(_snowman instanceof bfw) || ((bfw)_snowman).a(bfx.a))) {
            _snowman.a(0.0, (double)(_snowman.cz() + 0.1F), 0.0);
            _snowman.a(g.f.a(180.0F));
         }
      }
   }

   protected float d(T var1, float var2) {
      return _snowman.r(_snowman);
   }

   protected float a(T var1, float var2) {
      return (float)_snowman.K + _snowman;
   }

   protected float c(T var1) {
      return 90.0F;
   }

   protected float b(T var1, float var2) {
      return 0.0F;
   }

   protected void a(T var1, dfm var2, float var3) {
   }

   protected boolean b(T var1) {
      double _snowman = this.b.b(_snowman);
      float _snowmanx = _snowman.bx() ? 32.0F : 64.0F;
      if (_snowman >= (double)(_snowmanx * _snowmanx)) {
         return false;
      } else {
         djz _snowmanxx = djz.C();
         dzm _snowmanxxx = _snowmanxx.s;
         boolean _snowmanxxxx = !_snowman.c(_snowmanxxx);
         if (_snowman != _snowmanxxx) {
            ddp _snowmanxxxxx = _snowman.bG();
            ddp _snowmanxxxxxx = _snowmanxxx.bG();
            if (_snowmanxxxxx != null) {
               ddp.b _snowmanxxxxxxx = _snowmanxxxxx.j();
               switch (_snowmanxxxxxxx) {
                  case a:
                     return _snowmanxxxx;
                  case b:
                     return false;
                  case c:
                     return _snowmanxxxxxx == null ? _snowmanxxxx : _snowmanxxxxx.a(_snowmanxxxxxx) && (_snowmanxxxxx.i() || _snowmanxxxx);
                  case d:
                     return _snowmanxxxxxx == null ? _snowmanxxxx : !_snowmanxxxxx.a(_snowmanxxxxxx) && _snowmanxxxx;
                  default:
                     return true;
               }
            }
         }

         return djz.x() && _snowman != _snowmanxx.aa() && _snowmanxxxx && !_snowman.bs();
      }
   }
}
