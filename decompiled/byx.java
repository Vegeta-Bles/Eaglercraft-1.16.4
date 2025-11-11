public class byx extends bug {
   public static final cfe<cfk> c = cex.ad;
   public static final cey d = cex.w;

   protected byx(ceg.c var1) {
      super(true, _snowman);
      this.j(this.n.b().a(c, cfk.a).a(d, Boolean.valueOf(false)));
   }

   protected boolean a(brx var1, fx var2, ceh var3, boolean var4, int var5) {
      if (_snowman >= 8) {
         return false;
      } else {
         int _snowman = _snowman.u();
         int _snowmanx = _snowman.v();
         int _snowmanxx = _snowman.w();
         boolean _snowmanxxx = true;
         cfk _snowmanxxxx = _snowman.c(c);
         switch (_snowmanxxxx) {
            case a:
               if (_snowman) {
                  _snowmanxx++;
               } else {
                  _snowmanxx--;
               }
               break;
            case b:
               if (_snowman) {
                  _snowman--;
               } else {
                  _snowman++;
               }
               break;
            case c:
               if (_snowman) {
                  _snowman--;
               } else {
                  _snowman++;
                  _snowmanx++;
                  _snowmanxxx = false;
               }

               _snowmanxxxx = cfk.b;
               break;
            case d:
               if (_snowman) {
                  _snowman--;
                  _snowmanx++;
                  _snowmanxxx = false;
               } else {
                  _snowman++;
               }

               _snowmanxxxx = cfk.b;
               break;
            case e:
               if (_snowman) {
                  _snowmanxx++;
               } else {
                  _snowmanxx--;
                  _snowmanx++;
                  _snowmanxxx = false;
               }

               _snowmanxxxx = cfk.a;
               break;
            case f:
               if (_snowman) {
                  _snowmanxx++;
                  _snowmanx++;
                  _snowmanxxx = false;
               } else {
                  _snowmanxx--;
               }

               _snowmanxxxx = cfk.a;
         }

         return this.a(_snowman, new fx(_snowman, _snowmanx, _snowmanxx), _snowman, _snowman, _snowmanxxxx) ? true : _snowmanxxx && this.a(_snowman, new fx(_snowman, _snowmanx - 1, _snowmanxx), _snowman, _snowman, _snowmanxxxx);
      }
   }

   protected boolean a(brx var1, fx var2, boolean var3, int var4, cfk var5) {
      ceh _snowman = _snowman.d_(_snowman);
      if (!_snowman.a(this)) {
         return false;
      } else {
         cfk _snowmanx = _snowman.c(c);
         if (_snowman != cfk.b || _snowmanx != cfk.a && _snowmanx != cfk.e && _snowmanx != cfk.f) {
            if (_snowman != cfk.a || _snowmanx != cfk.b && _snowmanx != cfk.c && _snowmanx != cfk.d) {
               if (!_snowman.c(d)) {
                  return false;
               } else {
                  return _snowman.r(_snowman) ? true : this.a(_snowman, _snowman, _snowman, _snowman, _snowman + 1);
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   protected void a(ceh var1, brx var2, fx var3, buo var4) {
      boolean _snowman = _snowman.c(d);
      boolean _snowmanx = _snowman.r(_snowman) || this.a(_snowman, _snowman, _snowman, true, 0) || this.a(_snowman, _snowman, _snowman, false, 0);
      if (_snowmanx != _snowman) {
         _snowman.a(_snowman, _snowman.a(d, Boolean.valueOf(_snowmanx)), 3);
         _snowman.b(_snowman.c(), this);
         if (_snowman.c(c).c()) {
            _snowman.b(_snowman.b(), this);
         }
      }
   }

   @Override
   public cfj<cfk> d() {
      return c;
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      switch (_snowman) {
         case c:
            switch ((cfk)_snowman.c(c)) {
               case c:
                  return _snowman.a(c, cfk.d);
               case d:
                  return _snowman.a(c, cfk.c);
               case e:
                  return _snowman.a(c, cfk.f);
               case f:
                  return _snowman.a(c, cfk.e);
               case g:
                  return _snowman.a(c, cfk.i);
               case h:
                  return _snowman.a(c, cfk.j);
               case i:
                  return _snowman.a(c, cfk.g);
               case j:
                  return _snowman.a(c, cfk.h);
            }
         case d:
            switch ((cfk)_snowman.c(c)) {
               case a:
                  return _snowman.a(c, cfk.b);
               case b:
                  return _snowman.a(c, cfk.a);
               case c:
                  return _snowman.a(c, cfk.e);
               case d:
                  return _snowman.a(c, cfk.f);
               case e:
                  return _snowman.a(c, cfk.d);
               case f:
                  return _snowman.a(c, cfk.c);
               case g:
                  return _snowman.a(c, cfk.j);
               case h:
                  return _snowman.a(c, cfk.g);
               case i:
                  return _snowman.a(c, cfk.h);
               case j:
                  return _snowman.a(c, cfk.i);
            }
         case b:
            switch ((cfk)_snowman.c(c)) {
               case a:
                  return _snowman.a(c, cfk.b);
               case b:
                  return _snowman.a(c, cfk.a);
               case c:
                  return _snowman.a(c, cfk.f);
               case d:
                  return _snowman.a(c, cfk.e);
               case e:
                  return _snowman.a(c, cfk.c);
               case f:
                  return _snowman.a(c, cfk.d);
               case g:
                  return _snowman.a(c, cfk.h);
               case h:
                  return _snowman.a(c, cfk.i);
               case i:
                  return _snowman.a(c, cfk.j);
               case j:
                  return _snowman.a(c, cfk.g);
            }
         default:
            return _snowman;
      }
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      cfk _snowman = _snowman.c(c);
      switch (_snowman) {
         case b:
            switch (_snowman) {
               case e:
                  return _snowman.a(c, cfk.f);
               case f:
                  return _snowman.a(c, cfk.e);
               case g:
                  return _snowman.a(c, cfk.j);
               case h:
                  return _snowman.a(c, cfk.i);
               case i:
                  return _snowman.a(c, cfk.h);
               case j:
                  return _snowman.a(c, cfk.g);
               default:
                  return super.a(_snowman, _snowman);
            }
         case c:
            switch (_snowman) {
               case c:
                  return _snowman.a(c, cfk.d);
               case d:
                  return _snowman.a(c, cfk.c);
               case e:
               case f:
               default:
                  break;
               case g:
                  return _snowman.a(c, cfk.h);
               case h:
                  return _snowman.a(c, cfk.g);
               case i:
                  return _snowman.a(c, cfk.j);
               case j:
                  return _snowman.a(c, cfk.i);
            }
      }

      return super.a(_snowman, _snowman);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(c, d);
   }
}
