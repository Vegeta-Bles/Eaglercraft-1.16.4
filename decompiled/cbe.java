import com.google.common.base.MoreObjects;
import java.util.Random;
import javax.annotation.Nullable;

public class cbe extends buo {
   public static final cfb a = bxm.aq;
   public static final cey b = cex.w;
   public static final cey c = cex.a;
   protected static final ddh d = buo.a(5.0, 0.0, 10.0, 11.0, 10.0, 16.0);
   protected static final ddh e = buo.a(5.0, 0.0, 0.0, 11.0, 10.0, 6.0);
   protected static final ddh f = buo.a(10.0, 0.0, 5.0, 16.0, 10.0, 11.0);
   protected static final ddh g = buo.a(0.0, 0.0, 5.0, 6.0, 10.0, 11.0);

   public cbe(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      switch ((gc)_snowman.c(a)) {
         case f:
         default:
            return g;
         case e:
            return f;
         case d:
            return e;
         case c:
            return d;
      }
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      gc _snowman = _snowman.c(a);
      fx _snowmanx = _snowman.a(_snowman.f());
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      return _snowman.n().d() && _snowmanxx.d(_snowman, _snowmanx, _snowman);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman.f() == _snowman.c(a) && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      ceh _snowman = this.n().a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false));
      brz _snowmanx = _snowman.p();
      fx _snowmanxx = _snowman.a();
      gc[] _snowmanxxx = _snowman.e();

      for (gc _snowmanxxxx : _snowmanxxx) {
         if (_snowmanxxxx.n().d()) {
            gc _snowmanxxxxx = _snowmanxxxx.f();
            _snowman = _snowman.a(a, _snowmanxxxxx);
            if (_snowman.a(_snowmanx, _snowmanxx)) {
               return _snowman;
            }
         }
      }

      return null;
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, aqm var4, bmb var5) {
      this.a(_snowman, _snowman, _snowman, false, false, -1, null);
   }

   public void a(brx var1, fx var2, ceh var3, boolean var4, boolean var5, int var6, @Nullable ceh var7) {
      gc _snowman = _snowman.c(a);
      boolean _snowmanx = _snowman.c(c);
      boolean _snowmanxx = _snowman.c(b);
      boolean _snowmanxxx = !_snowman;
      boolean _snowmanxxxx = false;
      int _snowmanxxxxx = 0;
      ceh[] _snowmanxxxxxx = new ceh[42];

      for (int _snowmanxxxxxxx = 1; _snowmanxxxxxxx < 42; _snowmanxxxxxxx++) {
         fx _snowmanxxxxxxxx = _snowman.a(_snowman, _snowmanxxxxxxx);
         ceh _snowmanxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxx);
         if (_snowmanxxxxxxxxx.a(bup.el)) {
            if (_snowmanxxxxxxxxx.c(a) == _snowman.f()) {
               _snowmanxxxxx = _snowmanxxxxxxx;
            }
            break;
         }

         if (!_snowmanxxxxxxxxx.a(bup.em) && _snowmanxxxxxxx != _snowman) {
            _snowmanxxxxxx[_snowmanxxxxxxx] = null;
            _snowmanxxx = false;
         } else {
            if (_snowmanxxxxxxx == _snowman) {
               _snowmanxxxxxxxxx = (ceh)MoreObjects.firstNonNull(_snowman, _snowmanxxxxxxxxx);
            }

            boolean _snowmanxxxxxxxxxx = !_snowmanxxxxxxxxx.c(cbd.c);
            boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.c(cbd.a);
            _snowmanxxxx |= _snowmanxxxxxxxxxx && _snowmanxxxxxxxxxxx;
            _snowmanxxxxxx[_snowmanxxxxxxx] = _snowmanxxxxxxxxx;
            if (_snowmanxxxxxxx == _snowman) {
               _snowman.J().a(_snowman, this, 10);
               _snowmanxxx &= _snowmanxxxxxxxxxx;
            }
         }
      }

      _snowmanxxx &= _snowmanxxxxx > 1;
      _snowmanxxxx &= _snowmanxxx;
      ceh _snowmanxxxxxxx = this.n().a(c, Boolean.valueOf(_snowmanxxx)).a(b, Boolean.valueOf(_snowmanxxxx));
      if (_snowmanxxxxx > 0) {
         fx _snowmanxxxxxxxxxx = _snowman.a(_snowman, _snowmanxxxxx);
         gc _snowmanxxxxxxxxxxx = _snowman.f();
         _snowman.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxx.a(a, _snowmanxxxxxxxxxxx), 3);
         this.a(_snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
         this.a(_snowman, _snowmanxxxxxxxxxx, _snowmanxxx, _snowmanxxxx, _snowmanx, _snowmanxx);
      }

      this.a(_snowman, _snowman, _snowmanxxx, _snowmanxxxx, _snowmanx, _snowmanxx);
      if (!_snowman) {
         _snowman.a(_snowman, _snowmanxxxxxxx.a(a, _snowman), 3);
         if (_snowman) {
            this.a(_snowman, _snowman, _snowman);
         }
      }

      if (_snowmanx != _snowmanxxx) {
         for (int _snowmanxxxxxxxxxx = 1; _snowmanxxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxxx++) {
            fx _snowmanxxxxxxxxxxx = _snowman.a(_snowman, _snowmanxxxxxxxxxx);
            ceh _snowmanxxxxxxxxxxxx = _snowmanxxxxxx[_snowmanxxxxxxxxxx];
            if (_snowmanxxxxxxxxxxxx != null) {
               _snowman.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx.a(c, Boolean.valueOf(_snowmanxxx)), 3);
               if (!_snowman.d_(_snowmanxxxxxxxxxxx).g()) {
               }
            }
         }
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      this.a(_snowman, _snowman, _snowman, false, true, -1, null);
   }

   private void a(brx var1, fx var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      if (_snowman && !_snowman) {
         _snowman.a(null, _snowman, adq.pn, adr.e, 0.4F, 0.6F);
      } else if (!_snowman && _snowman) {
         _snowman.a(null, _snowman, adq.pm, adr.e, 0.4F, 0.5F);
      } else if (_snowman && !_snowman) {
         _snowman.a(null, _snowman, adq.pl, adr.e, 0.4F, 0.7F);
      } else if (!_snowman && _snowman) {
         _snowman.a(null, _snowman, adq.po, adr.e, 0.4F, 1.2F / (_snowman.t.nextFloat() * 0.2F + 0.9F));
      }
   }

   private void a(brx var1, fx var2, gc var3) {
      _snowman.b(_snowman, this);
      _snowman.b(_snowman.a(_snowman.f()), this);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman && !_snowman.a(_snowman.b())) {
         boolean _snowman = _snowman.c(c);
         boolean _snowmanx = _snowman.c(b);
         if (_snowman || _snowmanx) {
            this.a(_snowman, _snowman, _snowman, true, false, -1, null);
         }

         if (_snowmanx) {
            _snowman.b(_snowman, this);
            _snowman.b(_snowman.a(_snowman.c(a).f()), this);
         }

         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman.c(b) ? 15 : 0;
   }

   @Override
   public int b(ceh var1, brc var2, fx var3, gc var4) {
      if (!_snowman.c(b)) {
         return 0;
      } else {
         return _snowman.c(a) == _snowman ? 15 : 0;
      }
   }

   @Override
   public boolean b_(ceh var1) {
      return true;
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
      _snowman.a(a, b, c);
   }
}
