import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bvi extends bud {
   private static final Logger c = LogManager.getLogger();
   public static final cfb a = bvz.a;
   public static final cey b = cex.c;

   public bvi(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, Boolean.valueOf(false)));
   }

   @Override
   public ccj a(brc var1) {
      cco _snowman = new cco();
      _snowman.b(this == bup.iH);
      return _snowman;
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (!_snowman.v) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof cco) {
            cco _snowmanx = (cco)_snowman;
            boolean _snowmanxx = _snowman.r(_snowman);
            boolean _snowmanxxx = _snowmanx.f();
            _snowmanx.a(_snowmanxx);
            if (!_snowmanxxx && !_snowmanx.g() && _snowmanx.m() != cco.a.a) {
               if (_snowmanxx) {
                  _snowmanx.k();
                  _snowman.J().a(_snowman, this, 1);
               }
            }
         }
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cco) {
         cco _snowmanx = (cco)_snowman;
         bqy _snowmanxx = _snowmanx.d();
         boolean _snowmanxxx = !aft.b(_snowmanxx.k());
         cco.a _snowmanxxxx = _snowmanx.m();
         boolean _snowmanxxxxx = _snowmanx.j();
         if (_snowmanxxxx == cco.a.b) {
            _snowmanx.k();
            if (_snowmanxxxxx) {
               this.a(_snowman, _snowman, _snowman, _snowmanxx, _snowmanxxx);
            } else if (_snowmanx.x()) {
               _snowmanxx.a(0);
            }

            if (_snowmanx.f() || _snowmanx.g()) {
               _snowman.j().a(_snowman, this, 1);
            }
         } else if (_snowmanxxxx == cco.a.c) {
            if (_snowmanxxxxx) {
               this.a(_snowman, _snowman, _snowman, _snowmanxx, _snowmanxxx);
            } else if (_snowmanx.x()) {
               _snowmanxx.a(0);
            }
         }

         _snowman.c(_snowman, this);
      }
   }

   private void a(ceh var1, brx var2, fx var3, bqy var4, boolean var5) {
      if (_snowman) {
         _snowman.a(_snowman);
      } else {
         _snowman.a(0);
      }

      a(_snowman, _snowman, _snowman.c(a));
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cco && _snowman.eV()) {
         _snowman.a((cco)_snowman);
         return aou.a(_snowman.v);
      } else {
         return aou.c;
      }
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      ccj _snowman = _snowman.c(_snowman);
      return _snowman instanceof cco ? ((cco)_snowman).d().i() : 0;
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, aqm var4, bmb var5) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cco) {
         cco _snowmanx = (cco)_snowman;
         bqy _snowmanxx = _snowmanx.d();
         if (_snowman.t()) {
            _snowmanxx.a(_snowman.r());
         }

         if (!_snowman.v) {
            if (_snowman.b("BlockEntityTag") == null) {
               _snowmanxx.a(_snowman.V().b(brt.n));
               _snowmanx.b(this == bup.iH);
            }

            if (_snowmanx.m() == cco.a.a) {
               boolean _snowmanxxx = _snowman.r(_snowman);
               _snowmanx.a(_snowmanxxx);
            }
         }
      }
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
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
   public ceh a(bny var1) {
      return this.n().a(a, _snowman.d().f());
   }

   private static void a(brx var0, fx var1, gc var2) {
      fx.a _snowman = _snowman.i();
      brt _snowmanx = _snowman.V();
      int _snowmanxx = _snowmanx.c(brt.v);

      while (_snowmanxx-- > 0) {
         _snowman.c(_snowman);
         ceh _snowmanxxx = _snowman.d_(_snowman);
         buo _snowmanxxxx = _snowmanxxx.b();
         if (!_snowmanxxx.a(bup.iH)) {
            break;
         }

         ccj _snowmanxxxxx = _snowman.c(_snowman);
         if (!(_snowmanxxxxx instanceof cco)) {
            break;
         }

         cco _snowmanxxxxxx = (cco)_snowmanxxxxx;
         if (_snowmanxxxxxx.m() != cco.a.a) {
            break;
         }

         if (_snowmanxxxxxx.f() || _snowmanxxxxxx.g()) {
            bqy _snowmanxxxxxxx = _snowmanxxxxxx.d();
            if (_snowmanxxxxxx.k()) {
               if (!_snowmanxxxxxxx.a(_snowman)) {
                  break;
               }

               _snowman.c(_snowman, _snowmanxxxx);
            } else if (_snowmanxxxxxx.x()) {
               _snowmanxxxxxxx.a(0);
            }
         }

         _snowman = _snowmanxxx.c(a);
      }

      if (_snowmanxx <= 0) {
         int _snowmanxxxxxxx = Math.max(_snowmanx.c(brt.v), 0);
         c.warn("Command Block chain tried to execute more than {} steps!", _snowmanxxxxxxx);
      }
   }
}
