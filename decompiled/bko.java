import javax.annotation.Nullable;

public class bko extends blx {
   private final cuw a;

   public bko(cuw var1, blx.a var2) {
      super(_snowman);
      this.a = _snowman;
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      dcl _snowmanx = a(_snowman, _snowman, this.a == cuy.a ? brf.b.b : brf.b.a);
      if (_snowmanx.c() == dcl.a.a) {
         return aov.c(_snowman);
      } else if (_snowmanx.c() != dcl.a.b) {
         return aov.c(_snowman);
      } else {
         dcj _snowmanxx = (dcj)_snowmanx;
         fx _snowmanxxx = _snowmanxx.a();
         gc _snowmanxxxx = _snowmanxx.b();
         fx _snowmanxxxxx = _snowmanxxx.a(_snowmanxxxx);
         if (!_snowman.a(_snowman, _snowmanxxx) || !_snowman.a(_snowmanxxxxx, _snowmanxxxx, _snowman)) {
            return aov.d(_snowman);
         } else if (this.a == cuy.a) {
            ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxx);
            if (_snowmanxxxxxx.b() instanceof but) {
               cuw _snowmanxxxxxxx = ((but)_snowmanxxxxxx.b()).b(_snowman, _snowmanxxx, _snowmanxxxxxx);
               if (_snowmanxxxxxxx != cuy.a) {
                  _snowman.b(aea.c.b(this));
                  _snowman.a(_snowmanxxxxxxx.a(aef.c) ? adq.bo : adq.bm, 1.0F, 1.0F);
                  bmb _snowmanxxxxxxxx = bmc.a(_snowman, _snowman, new bmb(_snowmanxxxxxxx.a()));
                  if (!_snowman.v) {
                     ac.j.a((aah)_snowman, new bmb(_snowmanxxxxxxx.a()));
                  }

                  return aov.a(_snowmanxxxxxxxx, _snowman.s_());
               }
            }

            return aov.d(_snowman);
         } else {
            ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxx);
            fx _snowmanxxxxxxx = _snowmanxxxxxx.b() instanceof byc && this.a == cuy.c ? _snowmanxxx : _snowmanxxxxx;
            if (this.a(_snowman, _snowman, _snowmanxxxxxxx, _snowmanxx)) {
               this.a(_snowman, _snowman, _snowmanxxxxxxx);
               if (_snowman instanceof aah) {
                  ac.y.a((aah)_snowman, _snowmanxxxxxxx, _snowman);
               }

               _snowman.b(aea.c.b(this));
               return aov.a(this.a(_snowman, _snowman), _snowman.s_());
            } else {
               return aov.d(_snowman);
            }
         }
      }
   }

   protected bmb a(bmb var1, bfw var2) {
      return !_snowman.bC.d ? new bmb(bmd.lK) : _snowman;
   }

   public void a(brx var1, bmb var2, fx var3) {
   }

   public boolean a(@Nullable bfw var1, brx var2, fx var3, @Nullable dcj var4) {
      if (!(this.a instanceof cuv)) {
         return false;
      } else {
         ceh _snowman = _snowman.d_(_snowman);
         buo _snowmanx = _snowman.b();
         cva _snowmanxx = _snowman.c();
         boolean _snowmanxxx = _snowman.a(this.a);
         boolean _snowmanxxxx = _snowman.g() || _snowmanxxx || _snowmanx instanceof byc && ((byc)_snowmanx).a(_snowman, _snowman, _snowman, this.a);
         if (!_snowmanxxxx) {
            return _snowman != null && this.a(_snowman, _snowman, _snowman.a().a(_snowman.b()), null);
         } else if (_snowman.k().d() && this.a.a(aef.b)) {
            int _snowmanxxxxx = _snowman.u();
            int _snowmanxxxxxx = _snowman.v();
            int _snowmanxxxxxxx = _snowman.w();
            _snowman.a(_snowman, _snowman, adq.ej, adr.e, 0.5F, 2.6F + (_snowman.t.nextFloat() - _snowman.t.nextFloat()) * 0.8F);

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 8; _snowmanxxxxxxxx++) {
               _snowman.a(hh.L, (double)_snowmanxxxxx + Math.random(), (double)_snowmanxxxxxx + Math.random(), (double)_snowmanxxxxxxx + Math.random(), 0.0, 0.0, 0.0);
            }

            return true;
         } else if (_snowmanx instanceof byc && this.a == cuy.c) {
            ((byc)_snowmanx).a(_snowman, _snowman, _snowman, ((cuv)this.a).a(false));
            this.a(_snowman, _snowman, _snowman);
            return true;
         } else {
            if (!_snowman.v && _snowmanxxx && !_snowmanxx.a()) {
               _snowman.b(_snowman, true);
            }

            if (!_snowman.a(_snowman, this.a.h().g(), 11) && !_snowman.m().b()) {
               return false;
            } else {
               this.a(_snowman, _snowman, _snowman);
               return true;
            }
         }
      }
   }

   protected void a(@Nullable bfw var1, bry var2, fx var3) {
      adp _snowman = this.a.a(aef.c) ? adq.bl : adq.bj;
      _snowman.a(_snowman, _snowman, _snowman, adr.e, 1.0F, 1.0F);
   }
}
