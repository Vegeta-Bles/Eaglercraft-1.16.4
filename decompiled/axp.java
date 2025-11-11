import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class axp extends axx {
   private static final azg a = new azg().c().e();
   private boolean b;
   private int c;
   private final Class<?>[] d;
   private Class<?>[] i;

   public axp(aqu var1, Class<?>... var2) {
      super(_snowman, true);
      this.d = _snowman;
      this.a(EnumSet.of(avv.a.d));
   }

   @Override
   public boolean a() {
      int _snowman = this.e.da();
      aqm _snowmanx = this.e.cZ();
      if (_snowman != this.c && _snowmanx != null) {
         if (_snowmanx.X() == aqe.bc && this.e.l.V().b(brt.G)) {
            return false;
         } else {
            for (Class<?> _snowmanxx : this.d) {
               if (_snowmanxx.isAssignableFrom(_snowmanx.getClass())) {
                  return false;
               }
            }

            return this.a(_snowmanx, a);
         }
      } else {
         return false;
      }
   }

   public axp a(Class<?>... var1) {
      this.b = true;
      this.i = _snowman;
      return this;
   }

   @Override
   public void c() {
      this.e.h(this.e.cZ());
      this.g = this.e.A();
      this.c = this.e.da();
      this.h = 300;
      if (this.b) {
         this.g();
      }

      super.c();
   }

   protected void g() {
      double _snowman = this.k();
      dci _snowmanx = dci.a(this.e.cA()).c(_snowman, 10.0, _snowman);
      List<aqn> _snowmanxx = this.e.l.b((Class<? extends aqn>)this.e.getClass(), _snowmanx);
      Iterator var5 = _snowmanxx.iterator();

      while (true) {
         aqn _snowmanxxx;
         while (true) {
            if (!var5.hasNext()) {
               return;
            }

            _snowmanxxx = (aqn)var5.next();
            if (this.e != _snowmanxxx && _snowmanxxx.A() == null && (!(this.e instanceof are) || ((are)this.e).eN() == ((are)_snowmanxxx).eN()) && !_snowmanxxx.r(this.e.cZ())) {
               if (this.i == null) {
                  break;
               }

               boolean _snowmanxxxx = false;

               for (Class<?> _snowmanxxxxx : this.i) {
                  if (_snowmanxxx.getClass() == _snowmanxxxxx) {
                     _snowmanxxxx = true;
                     break;
                  }
               }

               if (!_snowmanxxxx) {
                  break;
               }
            }
         }

         this.a(_snowmanxxx, this.e.cZ());
      }
   }

   protected void a(aqn var1, aqm var2) {
      _snowman.h(_snowman);
   }
}
