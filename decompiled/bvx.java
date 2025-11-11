import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bvx extends bug {
   public static final cfe<cfk> c = cex.ad;
   public static final cey d = cex.w;

   public bvx(ceg.c var1) {
      super(true, _snowman);
      this.j(this.n.b().a(d, Boolean.valueOf(false)).a(c, cfk.a));
   }

   @Override
   public boolean b_(ceh var1) {
      return true;
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (!_snowman.v) {
         if (!_snowman.c(d)) {
            this.a(_snowman, _snowman, _snowman);
         }
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.c(d)) {
         this.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman.c(d) ? 15 : 0;
   }

   @Override
   public int b(ceh var1, brc var2, fx var3, gc var4) {
      if (!_snowman.c(d)) {
         return 0;
      } else {
         return _snowman == gc.b ? 15 : 0;
      }
   }

   private void a(brx var1, fx var2, ceh var3) {
      if (this.a(_snowman, (brz)_snowman, _snowman)) {
         boolean _snowman = _snowman.c(d);
         boolean _snowmanx = false;
         List<bhl> _snowmanxx = this.a(_snowman, _snowman, bhl.class, null);
         if (!_snowmanxx.isEmpty()) {
            _snowmanx = true;
         }

         if (_snowmanx && !_snowman) {
            ceh _snowmanxxx = _snowman.a(d, Boolean.valueOf(true));
            _snowman.a(_snowman, _snowmanxxx, 3);
            this.b(_snowman, _snowman, _snowmanxxx, true);
            _snowman.b(_snowman, this);
            _snowman.b(_snowman.c(), this);
            _snowman.b(_snowman, _snowman, _snowmanxxx);
         }

         if (!_snowmanx && _snowman) {
            ceh _snowmanxxx = _snowman.a(d, Boolean.valueOf(false));
            _snowman.a(_snowman, _snowmanxxx, 3);
            this.b(_snowman, _snowman, _snowmanxxx, false);
            _snowman.b(_snowman, this);
            _snowman.b(_snowman.c(), this);
            _snowman.b(_snowman, _snowman, _snowmanxxx);
         }

         if (_snowmanx) {
            _snowman.J().a(_snowman, this, 20);
         }

         _snowman.c(_snowman, this);
      }
   }

   protected void b(brx var1, fx var2, ceh var3, boolean var4) {
      bzb _snowman = new bzb(_snowman, _snowman, _snowman);

      for (fx _snowmanx : _snowman.a()) {
         ceh _snowmanxx = _snowman.d_(_snowmanx);
         _snowmanxx.a(_snowman, _snowmanx, _snowmanxx.b(), _snowman, false);
      }
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         this.a(_snowman, _snowman, this.a(_snowman, _snowman, _snowman, _snowman));
      }
   }

   @Override
   public cfj<cfk> d() {
      return c;
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      if (_snowman.c(d)) {
         List<bhr> _snowman = this.a(_snowman, _snowman, bhr.class, null);
         if (!_snowman.isEmpty()) {
            return _snowman.get(0).u().i();
         }

         List<bhl> _snowmanx = this.a(_snowman, _snowman, bhl.class, aqd.d);
         if (!_snowmanx.isEmpty()) {
            return bic.b((aon)_snowmanx.get(0));
         }
      }

      return 0;
   }

   protected <T extends bhl> List<T> a(brx var1, fx var2, Class<T> var3, @Nullable Predicate<aqa> var4) {
      return _snowman.a(_snowman, this.a(_snowman), _snowman);
   }

   private dci a(fx var1) {
      double _snowman = 0.2;
      return new dci((double)_snowman.u() + 0.2, (double)_snowman.v(), (double)_snowman.w() + 0.2, (double)(_snowman.u() + 1) - 0.2, (double)(_snowman.v() + 1) - 0.2, (double)(_snowman.w() + 1) - 0.2);
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
               case a:
                  return _snowman.a(c, cfk.b);
               case b:
                  return _snowman.a(c, cfk.a);
            }
         case b:
            switch ((cfk)_snowman.c(c)) {
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
               case a:
                  return _snowman.a(c, cfk.b);
               case b:
                  return _snowman.a(c, cfk.a);
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
