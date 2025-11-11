import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class cea extends bvz {
   public static final cey b = cex.g;
   protected static final ddh c = buo.a(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
   protected static final ddh d = buo.a(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final ddh e = buo.a(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
   protected static final ddh f = buo.a(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
   protected static final ddh g = buo.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
   protected static final ddh h = buo.a(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
   private final boolean i;

   public cea(boolean var1, ceg.c var2) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, Boolean.valueOf(false)));
      this.i = _snowman;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      if (_snowman.c(b)) {
         switch ((gc)_snowman.c(a)) {
            case a:
               return h;
            case b:
            default:
               return g;
            case c:
               return f;
            case d:
               return e;
            case e:
               return d;
            case f:
               return c;
         }
      } else {
         return dde.b();
      }
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, aqm var4, bmb var5) {
      if (!_snowman.v) {
         this.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (!_snowman.v) {
         this.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         if (!_snowman.v && _snowman.c(_snowman) == null) {
            this.a(_snowman, _snowman, _snowman);
         }
      }
   }

   @Override
   public ceh a(bny var1) {
      return this.n().a(a, _snowman.d().f()).a(b, Boolean.valueOf(false));
   }

   private void a(brx var1, fx var2, ceh var3) {
      gc _snowman = _snowman.c(a);
      boolean _snowmanx = this.a(_snowman, _snowman, _snowman);
      if (_snowmanx && !_snowman.c(b)) {
         if (new cee(_snowman, _snowman, _snowman, true).a()) {
            _snowman.a(_snowman, this, 0, _snowman.c());
         }
      } else if (!_snowmanx && _snowman.c(b)) {
         fx _snowmanxx = _snowman.a(_snowman, 2);
         ceh _snowmanxxx = _snowman.d_(_snowmanxx);
         int _snowmanxxxx = 1;
         if (_snowmanxxx.a(bup.bo) && _snowmanxxx.c(a) == _snowman) {
            ccj _snowmanxxxxx = _snowman.c(_snowmanxx);
            if (_snowmanxxxxx instanceof ced) {
               ced _snowmanxxxxxx = (ced)_snowmanxxxxx;
               if (_snowmanxxxxxx.d() && (_snowmanxxxxxx.a(0.0F) < 0.5F || _snowman.T() == _snowmanxxxxxx.m() || ((aag)_snowman).m_())) {
                  _snowmanxxxx = 2;
               }
            }
         }

         _snowman.a(_snowman, this, _snowmanxxxx, _snowman.c());
      }
   }

   private boolean a(brx var1, fx var2, gc var3) {
      for (gc _snowman : gc.values()) {
         if (_snowman != _snowman && _snowman.a(_snowman.a(_snowman), _snowman)) {
            return true;
         }
      }

      if (_snowman.a(_snowman, gc.a)) {
         return true;
      } else {
         fx _snowmanx = _snowman.b();

         for (gc _snowmanxx : gc.values()) {
            if (_snowmanxx != gc.a && _snowman.a(_snowmanx.a(_snowmanxx), _snowmanxx)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean a(ceh var1, brx var2, fx var3, int var4, int var5) {
      gc _snowman = _snowman.c(a);
      if (!_snowman.v) {
         boolean _snowmanx = this.a(_snowman, _snowman, _snowman);
         if (_snowmanx && (_snowman == 1 || _snowman == 2)) {
            _snowman.a(_snowman, _snowman.a(b, Boolean.valueOf(true)), 2);
            return false;
         }

         if (!_snowmanx && _snowman == 0) {
            return false;
         }
      }

      if (_snowman == 0) {
         if (!this.a(_snowman, _snowman, _snowman, true)) {
            return false;
         }

         _snowman.a(_snowman, _snowman.a(b, Boolean.valueOf(true)), 67);
         _snowman.a(null, _snowman, adq.ln, adr.e, 0.5F, _snowman.t.nextFloat() * 0.25F + 0.6F);
      } else if (_snowman == 1 || _snowman == 2) {
         ccj _snowmanxx = _snowman.c(_snowman.a(_snowman));
         if (_snowmanxx instanceof ced) {
            ((ced)_snowmanxx).l();
         }

         ceh _snowmanxxx = bup.bo.n().a(cdz.a, _snowman).a(cdz.b, this.i ? cfi.b : cfi.a);
         _snowman.a(_snowman, _snowmanxxx, 20);
         _snowman.a(_snowman, cdz.a(this.n().a(a, gc.a(_snowman & 7)), _snowman, false, true));
         _snowman.a(_snowman, _snowmanxxx.b());
         _snowmanxxx.a(_snowman, _snowman, 2);
         if (this.i) {
            fx _snowmanxxxx = _snowman.b(_snowman.i() * 2, _snowman.j() * 2, _snowman.k() * 2);
            ceh _snowmanxxxxx = _snowman.d_(_snowmanxxxx);
            boolean _snowmanxxxxxx = false;
            if (_snowmanxxxxx.a(bup.bo)) {
               ccj _snowmanxxxxxxx = _snowman.c(_snowmanxxxx);
               if (_snowmanxxxxxxx instanceof ced) {
                  ced _snowmanxxxxxxxx = (ced)_snowmanxxxxxxx;
                  if (_snowmanxxxxxxxx.f() == _snowman && _snowmanxxxxxxxx.d()) {
                     _snowmanxxxxxxxx.l();
                     _snowmanxxxxxx = true;
                  }
               }
            }

            if (!_snowmanxxxxxx) {
               if (_snowman != 1 || _snowmanxxxxx.g() || !a(_snowmanxxxxx, _snowman, _snowmanxxxx, _snowman.f(), false, _snowman) || _snowmanxxxxx.k() != cvc.a && !_snowmanxxxxx.a(bup.aW) && !_snowmanxxxxx.a(bup.aP)) {
                  _snowman.a(_snowman.a(_snowman), false);
               } else {
                  this.a(_snowman, _snowman, _snowman, false);
               }
            }
         } else {
            _snowman.a(_snowman.a(_snowman), false);
         }

         _snowman.a(null, _snowman, adq.lm, adr.e, 0.5F, _snowman.t.nextFloat() * 0.15F + 0.6F);
      }

      return true;
   }

   public static boolean a(ceh var0, brx var1, fx var2, gc var3, boolean var4, gc var5) {
      if (_snowman.v() < 0 || _snowman.v() > _snowman.L() - 1 || !_snowman.f().a(_snowman)) {
         return false;
      } else if (_snowman.g()) {
         return true;
      } else if (_snowman.a(bup.bK) || _snowman.a(bup.ni) || _snowman.a(bup.nj)) {
         return false;
      } else if (_snowman == gc.a && _snowman.v() == 0) {
         return false;
      } else if (_snowman == gc.b && _snowman.v() == _snowman.L() - 1) {
         return false;
      } else {
         if (!_snowman.a(bup.aW) && !_snowman.a(bup.aP)) {
            if (_snowman.h(_snowman, _snowman) == -1.0F) {
               return false;
            }

            switch (_snowman.k()) {
               case c:
                  return false;
               case b:
                  return _snowman;
               case e:
                  return _snowman == _snowman;
            }
         } else if (_snowman.c(b)) {
            return false;
         }

         return !_snowman.b().q();
      }
   }

   private boolean a(brx var1, fx var2, gc var3, boolean var4) {
      fx _snowman = _snowman.a(_snowman);
      if (!_snowman && _snowman.d_(_snowman).a(bup.aX)) {
         _snowman.a(_snowman, bup.a.n(), 20);
      }

      cee _snowmanx = new cee(_snowman, _snowman, _snowman, _snowman);
      if (!_snowmanx.a()) {
         return false;
      } else {
         Map<fx, ceh> _snowmanxx = Maps.newHashMap();
         List<fx> _snowmanxxx = _snowmanx.c();
         List<ceh> _snowmanxxxx = Lists.newArrayList();

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx.size(); _snowmanxxxxx++) {
            fx _snowmanxxxxxx = _snowmanxxx.get(_snowmanxxxxx);
            ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxxxxxx);
            _snowmanxxxx.add(_snowmanxxxxxxx);
            _snowmanxx.put(_snowmanxxxxxx, _snowmanxxxxxxx);
         }

         List<fx> _snowmanxxxxx = _snowmanx.d();
         ceh[] _snowmanxxxxxx = new ceh[_snowmanxxx.size() + _snowmanxxxxx.size()];
         gc _snowmanxxxxxxx = _snowman ? _snowman : _snowman.f();
         int _snowmanxxxxxxxx = 0;

         for (int _snowmanxxxxxxxxx = _snowmanxxxxx.size() - 1; _snowmanxxxxxxxxx >= 0; _snowmanxxxxxxxxx--) {
            fx _snowmanxxxxxxxxxx = _snowmanxxxxx.get(_snowmanxxxxxxxxx);
            ceh _snowmanxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxx);
            ccj _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.b().q() ? _snowman.c(_snowmanxxxxxxxxxx) : null;
            a(_snowmanxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
            _snowman.a(_snowmanxxxxxxxxxx, bup.a.n(), 18);
            _snowmanxxxxxx[_snowmanxxxxxxxx++] = _snowmanxxxxxxxxxxx;
         }

         for (int _snowmanxxxxxxxxx = _snowmanxxx.size() - 1; _snowmanxxxxxxxxx >= 0; _snowmanxxxxxxxxx--) {
            fx _snowmanxxxxxxxxxx = _snowmanxxx.get(_snowmanxxxxxxxxx);
            ceh _snowmanxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxx);
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.a(_snowmanxxxxxxx);
            _snowmanxx.remove(_snowmanxxxxxxxxxx);
            _snowman.a(_snowmanxxxxxxxxxx, bup.bo.n().a(a, _snowman), 68);
            _snowman.a(_snowmanxxxxxxxxxx, cdz.a(_snowmanxxxx.get(_snowmanxxxxxxxxx), _snowman, _snowman, false));
            _snowmanxxxxxx[_snowmanxxxxxxxx++] = _snowmanxxxxxxxxxxx;
         }

         if (_snowman) {
            cfi _snowmanxxxxxxxxx = this.i ? cfi.b : cfi.a;
            ceh _snowmanxxxxxxxxxx = bup.aX.n().a(ceb.a, _snowman).a(ceb.b, _snowmanxxxxxxxxx);
            ceh _snowmanxxxxxxxxxxx = bup.bo.n().a(cdz.a, _snowman).a(cdz.b, this.i ? cfi.b : cfi.a);
            _snowmanxx.remove(_snowman);
            _snowman.a(_snowman, _snowmanxxxxxxxxxxx, 68);
            _snowman.a(_snowman, cdz.a(_snowmanxxxxxxxxxx, _snowman, true, true));
         }

         ceh _snowmanxxxxxxxxx = bup.a.n();

         for (fx _snowmanxxxxxxxxxx : _snowmanxx.keySet()) {
            _snowman.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, 82);
         }

         for (Entry<fx, ceh> _snowmanxxxxxxxxxx : _snowmanxx.entrySet()) {
            fx _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getKey();
            ceh _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.getValue();
            _snowmanxxxxxxxxxxxx.b(_snowman, _snowmanxxxxxxxxxxx, 2);
            _snowmanxxxxxxxxx.a(_snowman, _snowmanxxxxxxxxxxx, 2);
            _snowmanxxxxxxxxx.b(_snowman, _snowmanxxxxxxxxxxx, 2);
         }

         _snowmanxxxxxxxx = 0;

         for (int _snowmanxxxxxxxxxx = _snowmanxxxxx.size() - 1; _snowmanxxxxxxxxxx >= 0; _snowmanxxxxxxxxxx--) {
            ceh _snowmanxxxxxxxxxxx = _snowmanxxxxxx[_snowmanxxxxxxxx++];
            fx _snowmanxxxxxxxxxxxx = _snowmanxxxxx.get(_snowmanxxxxxxxxxx);
            _snowmanxxxxxxxxxxx.b(_snowman, _snowmanxxxxxxxxxxxx, 2);
            _snowman.b(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx.b());
         }

         for (int _snowmanxxxxxxxxxx = _snowmanxxx.size() - 1; _snowmanxxxxxxxxxx >= 0; _snowmanxxxxxxxxxx--) {
            _snowman.b(_snowmanxxx.get(_snowmanxxxxxxxxxx), _snowmanxxxxxx[_snowmanxxxxxxxx++].b());
         }

         if (_snowman) {
            _snowman.b(_snowman, bup.aX);
         }

         return true;
      }
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a(_snowman.c(a)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(_snowman.a(_snowman.c(a)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b);
   }

   @Override
   public boolean c_(ceh var1) {
      return _snowman.c(b);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
