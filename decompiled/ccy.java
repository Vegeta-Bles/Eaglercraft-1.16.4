import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public class ccy extends cdd implements ccx, cdm {
   private gj<bmb> i = gj.a(5, bmb.b);
   private int j = -1;
   private long k;

   public ccy() {
      super(cck.q);
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.i = gj.a(this.Z_(), bmb.b);
      if (!this.b(_snowman)) {
         aoo.b(_snowman, this.i);
      }

      this.j = _snowman.h("TransferCooldown");
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      if (!this.c(_snowman)) {
         aoo.a(_snowman, this.i);
      }

      _snowman.b("TransferCooldown", this.j);
      return _snowman;
   }

   @Override
   public int Z_() {
      return this.i.size();
   }

   @Override
   public bmb a(int var1, int var2) {
      this.d(null);
      return aoo.a(this.f(), _snowman, _snowman);
   }

   @Override
   public void a(int var1, bmb var2) {
      this.d(null);
      this.f().set(_snowman, _snowman);
      if (_snowman.E() > this.V_()) {
         _snowman.e(this.V_());
      }
   }

   @Override
   protected nr g() {
      return new of("container.hopper");
   }

   @Override
   public void aj_() {
      if (this.d != null && !this.d.v) {
         this.j--;
         this.k = this.d.T();
         if (!this.m()) {
            this.c(0);
            this.a(() -> a(this));
         }
      }
   }

   private boolean a(Supplier<Boolean> var1) {
      if (this.d != null && !this.d.v) {
         if (!this.m() && this.p().c(bxl.b)) {
            boolean _snowman = false;
            if (!this.c()) {
               _snowman = this.k();
            }

            if (!this.j()) {
               _snowman |= _snowman.get();
            }

            if (_snowman) {
               this.c(8);
               this.X_();
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean j() {
      for (bmb _snowman : this.i) {
         if (_snowman.a() || _snowman.E() != _snowman.c()) {
            return false;
         }
      }

      return true;
   }

   private boolean k() {
      aon _snowman = this.l();
      if (_snowman == null) {
         return false;
      } else {
         gc _snowmanx = this.p().c(bxl.a).f();
         if (this.b(_snowman, _snowmanx)) {
            return false;
         } else {
            for (int _snowmanxx = 0; _snowmanxx < this.Z_(); _snowmanxx++) {
               if (!this.a(_snowmanxx).a()) {
                  bmb _snowmanxxx = this.a(_snowmanxx).i();
                  bmb _snowmanxxxx = a(this, _snowman, this.a(_snowmanxx, 1), _snowmanx);
                  if (_snowmanxxxx.a()) {
                     _snowman.X_();
                     return true;
                  }

                  this.a(_snowmanxx, _snowmanxxx);
               }
            }

            return false;
         }
      }
   }

   private static IntStream a(aon var0, gc var1) {
      return _snowman instanceof ape ? IntStream.of(((ape)_snowman).a(_snowman)) : IntStream.range(0, _snowman.Z_());
   }

   private boolean b(aon var1, gc var2) {
      return a(_snowman, _snowman).allMatch(var1x -> {
         bmb _snowman = _snowman.a(var1x);
         return _snowman.E() >= _snowman.c();
      });
   }

   private static boolean c(aon var0, gc var1) {
      return a(_snowman, _snowman).allMatch(var1x -> _snowman.a(var1x).a());
   }

   public static boolean a(ccx var0) {
      aon _snowman = b(_snowman);
      if (_snowman != null) {
         gc _snowmanx = gc.a;
         return c(_snowman, _snowmanx) ? false : a(_snowman, _snowmanx).anyMatch(var3x -> a(_snowman, _snowman, var3x, _snowman));
      } else {
         for (bcv _snowmanx : c(_snowman)) {
            if (a(_snowman, _snowmanx)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean a(ccx var0, aon var1, int var2, gc var3) {
      bmb _snowman = _snowman.a(_snowman);
      if (!_snowman.a() && b(_snowman, _snowman, _snowman, _snowman)) {
         bmb _snowmanx = _snowman.i();
         bmb _snowmanxx = a(_snowman, _snowman, _snowman.a(_snowman, 1), null);
         if (_snowmanxx.a()) {
            _snowman.X_();
            return true;
         }

         _snowman.a(_snowman, _snowmanx);
      }

      return false;
   }

   public static boolean a(aon var0, bcv var1) {
      boolean _snowman = false;
      bmb _snowmanx = _snowman.g().i();
      bmb _snowmanxx = a(null, _snowman, _snowmanx, null);
      if (_snowmanxx.a()) {
         _snowman = true;
         _snowman.ad();
      } else {
         _snowman.b(_snowmanxx);
      }

      return _snowman;
   }

   public static bmb a(@Nullable aon var0, aon var1, bmb var2, @Nullable gc var3) {
      if (_snowman instanceof ape && _snowman != null) {
         ape _snowman = (ape)_snowman;
         int[] _snowmanx = _snowman.a(_snowman);

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length && !_snowman.a(); _snowmanxx++) {
            _snowman = a(_snowman, _snowman, _snowman, _snowmanx[_snowmanxx], _snowman);
         }
      } else {
         int _snowman = _snowman.Z_();

         for (int _snowmanx = 0; _snowmanx < _snowman && !_snowman.a(); _snowmanx++) {
            _snowman = a(_snowman, _snowman, _snowman, _snowmanx, _snowman);
         }
      }

      return _snowman;
   }

   private static boolean a(aon var0, bmb var1, int var2, @Nullable gc var3) {
      return !_snowman.b(_snowman, _snowman) ? false : !(_snowman instanceof ape) || ((ape)_snowman).a(_snowman, _snowman, _snowman);
   }

   private static boolean b(aon var0, bmb var1, int var2, gc var3) {
      return !(_snowman instanceof ape) || ((ape)_snowman).b(_snowman, _snowman, _snowman);
   }

   private static bmb a(@Nullable aon var0, aon var1, bmb var2, int var3, @Nullable gc var4) {
      bmb _snowman = _snowman.a(_snowman);
      if (a(_snowman, _snowman, _snowman, _snowman)) {
         boolean _snowmanx = false;
         boolean _snowmanxx = _snowman.c();
         if (_snowman.a()) {
            _snowman.a(_snowman, _snowman);
            _snowman = bmb.b;
            _snowmanx = true;
         } else if (a(_snowman, _snowman)) {
            int _snowmanxxx = _snowman.c() - _snowman.E();
            int _snowmanxxxx = Math.min(_snowman.E(), _snowmanxxx);
            _snowman.g(_snowmanxxxx);
            _snowman.f(_snowmanxxxx);
            _snowmanx = _snowmanxxxx > 0;
         }

         if (_snowmanx) {
            if (_snowmanxx && _snowman instanceof ccy) {
               ccy _snowmanxxx = (ccy)_snowman;
               if (!_snowmanxxx.y()) {
                  int _snowmanxxxx = 0;
                  if (_snowman instanceof ccy) {
                     ccy _snowmanxxxxx = (ccy)_snowman;
                     if (_snowmanxxx.k >= _snowmanxxxxx.k) {
                        _snowmanxxxx = 1;
                     }
                  }

                  _snowmanxxx.c(8 - _snowmanxxxx);
               }
            }

            _snowman.X_();
         }
      }

      return _snowman;
   }

   @Nullable
   private aon l() {
      gc _snowman = this.p().c(bxl.a);
      return b(this.v(), this.e.a(_snowman));
   }

   @Nullable
   public static aon b(ccx var0) {
      return a(_snowman.v(), _snowman.x(), _snowman.z() + 1.0, _snowman.A());
   }

   public static List<bcv> c(ccx var0) {
      return _snowman.aa_()
         .d()
         .stream()
         .flatMap(var1 -> _snowman.v().a(bcv.class, var1.d(_snowman.x() - 0.5, _snowman.z() - 0.5, _snowman.A() - 0.5), aqd.a).stream())
         .collect(Collectors.toList());
   }

   @Nullable
   public static aon b(brx var0, fx var1) {
      return a(_snowman, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5);
   }

   @Nullable
   public static aon a(brx var0, double var1, double var3, double var5) {
      aon _snowman = null;
      fx _snowmanx = new fx(_snowman, _snowman, _snowman);
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      buo _snowmanxxx = _snowmanxx.b();
      if (_snowmanxxx instanceof apf) {
         _snowman = ((apf)_snowmanxxx).a(_snowmanxx, _snowman, _snowmanx);
      } else if (_snowmanxxx.q()) {
         ccj _snowmanxxxx = _snowman.c(_snowmanx);
         if (_snowmanxxxx instanceof aon) {
            _snowman = (aon)_snowmanxxxx;
            if (_snowman instanceof ccn && _snowmanxxx instanceof bve) {
               _snowman = bve.a((bve)_snowmanxxx, _snowmanxx, _snowman, _snowmanx, true);
            }
         }
      }

      if (_snowman == null) {
         List<aqa> _snowmanxxxx = _snowman.a((aqa)null, new dci(_snowman - 0.5, _snowman - 0.5, _snowman - 0.5, _snowman + 0.5, _snowman + 0.5, _snowman + 0.5), aqd.d);
         if (!_snowmanxxxx.isEmpty()) {
            _snowman = (aon)_snowmanxxxx.get(_snowman.t.nextInt(_snowmanxxxx.size()));
         }
      }

      return _snowman;
   }

   private static boolean a(bmb var0, bmb var1) {
      if (_snowman.b() != _snowman.b()) {
         return false;
      } else if (_snowman.g() != _snowman.g()) {
         return false;
      } else {
         return _snowman.E() > _snowman.c() ? false : bmb.a(_snowman, _snowman);
      }
   }

   @Override
   public double x() {
      return (double)this.e.u() + 0.5;
   }

   @Override
   public double z() {
      return (double)this.e.v() + 0.5;
   }

   @Override
   public double A() {
      return (double)this.e.w() + 0.5;
   }

   private void c(int var1) {
      this.j = _snowman;
   }

   private boolean m() {
      return this.j > 0;
   }

   private boolean y() {
      return this.j > 8;
   }

   @Override
   protected gj<bmb> f() {
      return this.i;
   }

   @Override
   protected void a(gj<bmb> var1) {
      this.i = _snowman;
   }

   public void a(aqa var1) {
      if (_snowman instanceof bcv) {
         fx _snowman = this.o();
         if (dde.c(dde.a(_snowman.cc().d((double)(-_snowman.u()), (double)(-_snowman.v()), (double)(-_snowman.w()))), this.aa_(), dcr.i)) {
            this.a(() -> a(this, (bcv)_snowman));
         }
      }
   }

   @Override
   protected bic a(int var1, bfv var2) {
      return new bix(_snowman, _snowman, this);
   }
}
