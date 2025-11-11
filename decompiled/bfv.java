import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;

public class bfv implements aon, aoy {
   public final gj<bmb> a = gj.a(36, bmb.b);
   public final gj<bmb> b = gj.a(4, bmb.b);
   public final gj<bmb> c = gj.a(1, bmb.b);
   private final List<gj<bmb>> f = ImmutableList.of(this.a, this.b, this.c);
   public int d;
   public final bfw e;
   private bmb g = bmb.b;
   private int h;

   public bfv(bfw var1) {
      this.e = _snowman;
   }

   public bmb f() {
      return d(this.d) ? this.a.get(this.d) : bmb.b;
   }

   public static int g() {
      return 9;
   }

   private boolean a(bmb var1, bmb var2) {
      return !_snowman.a() && this.b(_snowman, _snowman) && _snowman.d() && _snowman.E() < _snowman.c() && _snowman.E() < this.V_();
   }

   private boolean b(bmb var1, bmb var2) {
      return _snowman.b() == _snowman.b() && bmb.a(_snowman, _snowman);
   }

   public int h() {
      for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
         if (this.a.get(_snowman).a()) {
            return _snowman;
         }
      }

      return -1;
   }

   public void a(bmb var1) {
      int _snowman = this.b(_snowman);
      if (d(_snowman)) {
         this.d = _snowman;
      } else {
         if (_snowman == -1) {
            this.d = this.i();
            if (!this.a.get(this.d).a()) {
               int _snowmanx = this.h();
               if (_snowmanx != -1) {
                  this.a.set(_snowmanx, this.a.get(this.d));
               }
            }

            this.a.set(this.d, _snowman);
         } else {
            this.c(_snowman);
         }
      }
   }

   public void c(int var1) {
      this.d = this.i();
      bmb _snowman = this.a.get(this.d);
      this.a.set(this.d, this.a.get(_snowman));
      this.a.set(_snowman, _snowman);
   }

   public static boolean d(int var0) {
      return _snowman >= 0 && _snowman < 9;
   }

   public int b(bmb var1) {
      for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
         if (!this.a.get(_snowman).a() && this.b(_snowman, this.a.get(_snowman))) {
            return _snowman;
         }
      }

      return -1;
   }

   public int c(bmb var1) {
      for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
         bmb _snowmanx = this.a.get(_snowman);
         if (!this.a.get(_snowman).a() && this.b(_snowman, this.a.get(_snowman)) && !this.a.get(_snowman).f() && !_snowmanx.x() && !_snowmanx.t()) {
            return _snowman;
         }
      }

      return -1;
   }

   public int i() {
      for (int _snowman = 0; _snowman < 9; _snowman++) {
         int _snowmanx = (this.d + _snowman) % 9;
         if (this.a.get(_snowmanx).a()) {
            return _snowmanx;
         }
      }

      for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
         int _snowmanxx = (this.d + _snowmanx) % 9;
         if (!this.a.get(_snowmanxx).x()) {
            return _snowmanxx;
         }
      }

      return this.d;
   }

   public void a(double var1) {
      if (_snowman > 0.0) {
         _snowman = 1.0;
      }

      if (_snowman < 0.0) {
         _snowman = -1.0;
      }

      this.d = (int)((double)this.d - _snowman);

      while (this.d < 0) {
         this.d += 9;
      }

      while (this.d >= 9) {
         this.d -= 9;
      }
   }

   public int a(Predicate<bmb> var1, int var2, aon var3) {
      int _snowman = 0;
      boolean _snowmanx = _snowman == 0;
      _snowman += aoo.a(this, _snowman, _snowman - _snowman, _snowmanx);
      _snowman += aoo.a(_snowman, _snowman, _snowman - _snowman, _snowmanx);
      _snowman += aoo.a(this.g, _snowman, _snowman - _snowman, _snowmanx);
      if (this.g.a()) {
         this.g = bmb.b;
      }

      return _snowman;
   }

   private int i(bmb var1) {
      int _snowman = this.d(_snowman);
      if (_snowman == -1) {
         _snowman = this.h();
      }

      return _snowman == -1 ? _snowman.E() : this.d(_snowman, _snowman);
   }

   private int d(int var1, bmb var2) {
      blx _snowman = _snowman.b();
      int _snowmanx = _snowman.E();
      bmb _snowmanxx = this.a(_snowman);
      if (_snowmanxx.a()) {
         _snowmanxx = new bmb(_snowman, 0);
         if (_snowman.n()) {
            _snowmanxx.c(_snowman.o().g());
         }

         this.a(_snowman, _snowmanxx);
      }

      int _snowmanxxx = _snowmanx;
      if (_snowmanx > _snowmanxx.c() - _snowmanxx.E()) {
         _snowmanxxx = _snowmanxx.c() - _snowmanxx.E();
      }

      if (_snowmanxxx > this.V_() - _snowmanxx.E()) {
         _snowmanxxx = this.V_() - _snowmanxx.E();
      }

      if (_snowmanxxx == 0) {
         return _snowmanx;
      } else {
         _snowmanx -= _snowmanxxx;
         _snowmanxx.f(_snowmanxxx);
         _snowmanxx.d(5);
         return _snowmanx;
      }
   }

   public int d(bmb var1) {
      if (this.a(this.a(this.d), _snowman)) {
         return this.d;
      } else if (this.a(this.a(40), _snowman)) {
         return 40;
      } else {
         for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
            if (this.a(this.a.get(_snowman), _snowman)) {
               return _snowman;
            }
         }

         return -1;
      }
   }

   public void j() {
      for (gj<bmb> _snowman : this.f) {
         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            if (!_snowman.get(_snowmanx).a()) {
               _snowman.get(_snowmanx).a(this.e.l, this.e, _snowmanx, this.d == _snowmanx);
            }
         }
      }
   }

   public boolean e(bmb var1) {
      return this.c(-1, _snowman);
   }

   public boolean c(int var1, bmb var2) {
      if (_snowman.a()) {
         return false;
      } else {
         try {
            if (_snowman.f()) {
               if (_snowman == -1) {
                  _snowman = this.h();
               }

               if (_snowman >= 0) {
                  this.a.set(_snowman, _snowman.i());
                  this.a.get(_snowman).d(5);
                  _snowman.e(0);
                  return true;
               } else if (this.e.bC.d) {
                  _snowman.e(0);
                  return true;
               } else {
                  return false;
               }
            } else {
               int _snowman;
               do {
                  _snowman = _snowman.E();
                  if (_snowman == -1) {
                     _snowman.e(this.i(_snowman));
                  } else {
                     _snowman.e(this.d(_snowman, _snowman));
                  }
               } while (!_snowman.a() && _snowman.E() < _snowman);

               if (_snowman.E() == _snowman && this.e.bC.d) {
                  _snowman.e(0);
                  return true;
               } else {
                  return _snowman.E() < _snowman;
               }
            }
         } catch (Throwable var6) {
            l _snowmanx = l.a(var6, "Adding item to inventory");
            m _snowmanxx = _snowmanx.a("Item being added");
            _snowmanxx.a("Item ID", blx.a(_snowman.b()));
            _snowmanxx.a("Item data", _snowman.g());
            _snowmanxx.a("Item name", () -> _snowman.r().getString());
            throw new u(_snowmanx);
         }
      }
   }

   public void a(brx var1, bmb var2) {
      if (!_snowman.v) {
         while (!_snowman.a()) {
            int _snowman = this.d(_snowman);
            if (_snowman == -1) {
               _snowman = this.h();
            }

            if (_snowman == -1) {
               this.e.a(_snowman, false);
               break;
            }

            int _snowmanx = _snowman.c() - this.a(_snowman).E();
            if (this.c(_snowman, _snowman.a(_snowmanx))) {
               ((aah)this.e).b.a(new pi(-2, _snowman, this.a(_snowman)));
            }
         }
      }
   }

   @Override
   public bmb a(int var1, int var2) {
      List<bmb> _snowman = null;

      for (gj<bmb> _snowmanx : this.f) {
         if (_snowman < _snowmanx.size()) {
            _snowman = _snowmanx;
            break;
         }

         _snowman -= _snowmanx.size();
      }

      return _snowman != null && !_snowman.get(_snowman).a() ? aoo.a(_snowman, _snowman, _snowman) : bmb.b;
   }

   public void f(bmb var1) {
      for (gj<bmb> _snowman : this.f) {
         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            if (_snowman.get(_snowmanx) == _snowman) {
               _snowman.set(_snowmanx, bmb.b);
               break;
            }
         }
      }
   }

   @Override
   public bmb b(int var1) {
      gj<bmb> _snowman = null;

      for (gj<bmb> _snowmanx : this.f) {
         if (_snowman < _snowmanx.size()) {
            _snowman = _snowmanx;
            break;
         }

         _snowman -= _snowmanx.size();
      }

      if (_snowman != null && !_snowman.get(_snowman).a()) {
         bmb _snowmanx = _snowman.get(_snowman);
         _snowman.set(_snowman, bmb.b);
         return _snowmanx;
      } else {
         return bmb.b;
      }
   }

   @Override
   public void a(int var1, bmb var2) {
      gj<bmb> _snowman = null;

      for (gj<bmb> _snowmanx : this.f) {
         if (_snowman < _snowmanx.size()) {
            _snowman = _snowmanx;
            break;
         }

         _snowman -= _snowmanx.size();
      }

      if (_snowman != null) {
         _snowman.set(_snowman, _snowman);
      }
   }

   public float a(ceh var1) {
      return this.a.get(this.d).a(_snowman);
   }

   public mj a(mj var1) {
      for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
         if (!this.a.get(_snowman).a()) {
            md _snowmanx = new md();
            _snowmanx.a("Slot", (byte)_snowman);
            this.a.get(_snowman).b(_snowmanx);
            _snowman.add(_snowmanx);
         }
      }

      for (int _snowmanx = 0; _snowmanx < this.b.size(); _snowmanx++) {
         if (!this.b.get(_snowmanx).a()) {
            md _snowmanxx = new md();
            _snowmanxx.a("Slot", (byte)(_snowmanx + 100));
            this.b.get(_snowmanx).b(_snowmanxx);
            _snowman.add(_snowmanxx);
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < this.c.size(); _snowmanxx++) {
         if (!this.c.get(_snowmanxx).a()) {
            md _snowmanxxx = new md();
            _snowmanxxx.a("Slot", (byte)(_snowmanxx + 150));
            this.c.get(_snowmanxx).b(_snowmanxxx);
            _snowman.add(_snowmanxxx);
         }
      }

      return _snowman;
   }

   public void b(mj var1) {
      this.a.clear();
      this.b.clear();
      this.c.clear();

      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         md _snowmanx = _snowman.a(_snowman);
         int _snowmanxx = _snowmanx.f("Slot") & 255;
         bmb _snowmanxxx = bmb.a(_snowmanx);
         if (!_snowmanxxx.a()) {
            if (_snowmanxx >= 0 && _snowmanxx < this.a.size()) {
               this.a.set(_snowmanxx, _snowmanxxx);
            } else if (_snowmanxx >= 100 && _snowmanxx < this.b.size() + 100) {
               this.b.set(_snowmanxx - 100, _snowmanxxx);
            } else if (_snowmanxx >= 150 && _snowmanxx < this.c.size() + 150) {
               this.c.set(_snowmanxx - 150, _snowmanxxx);
            }
         }
      }
   }

   @Override
   public int Z_() {
      return this.a.size() + this.b.size() + this.c.size();
   }

   @Override
   public boolean c() {
      for (bmb _snowman : this.a) {
         if (!_snowman.a()) {
            return false;
         }
      }

      for (bmb _snowmanx : this.b) {
         if (!_snowmanx.a()) {
            return false;
         }
      }

      for (bmb _snowmanxx : this.c) {
         if (!_snowmanxx.a()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public bmb a(int var1) {
      List<bmb> _snowman = null;

      for (gj<bmb> _snowmanx : this.f) {
         if (_snowman < _snowmanx.size()) {
            _snowman = _snowmanx;
            break;
         }

         _snowman -= _snowmanx.size();
      }

      return _snowman == null ? bmb.b : _snowman.get(_snowman);
   }

   @Override
   public nr R() {
      return new of("container.inventory");
   }

   public bmb e(int var1) {
      return this.b.get(_snowman);
   }

   public void a(apk var1, float var2) {
      if (!(_snowman <= 0.0F)) {
         _snowman /= 4.0F;
         if (_snowman < 1.0F) {
            _snowman = 1.0F;
         }

         for (int _snowman = 0; _snowman < this.b.size(); _snowman++) {
            bmb _snowmanx = this.b.get(_snowman);
            if ((!_snowman.p() || !_snowmanx.b().u()) && _snowmanx.b() instanceof bjy) {
               int _snowmanxx = _snowman;
               _snowmanx.a((int)_snowman, this.e, var1x -> var1x.c(aqf.a(aqf.a.b, _snowman)));
            }
         }
      }
   }

   public void k() {
      for (List<bmb> _snowman : this.f) {
         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            bmb _snowmanxx = _snowman.get(_snowmanx);
            if (!_snowmanxx.a()) {
               this.e.a(_snowmanxx, true, false);
               _snowman.set(_snowmanx, bmb.b);
            }
         }
      }
   }

   @Override
   public void X_() {
      this.h++;
   }

   public int l() {
      return this.h;
   }

   public void g(bmb var1) {
      this.g = _snowman;
   }

   public bmb m() {
      return this.g;
   }

   @Override
   public boolean a(bfw var1) {
      return this.e.y ? false : !(_snowman.h(this.e) > 64.0);
   }

   public boolean h(bmb var1) {
      for (List<bmb> _snowman : this.f) {
         for (bmb _snowmanx : _snowman) {
            if (!_snowmanx.a() && _snowmanx.a(_snowman)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean a(ael<blx> var1) {
      for (List<bmb> _snowman : this.f) {
         for (bmb _snowmanx : _snowman) {
            if (!_snowmanx.a() && _snowman.a(_snowmanx.b())) {
               return true;
            }
         }
      }

      return false;
   }

   public void a(bfv var1) {
      for (int _snowman = 0; _snowman < this.Z_(); _snowman++) {
         this.a(_snowman, _snowman.a(_snowman));
      }

      this.d = _snowman.d;
   }

   @Override
   public void Y_() {
      for (List<bmb> _snowman : this.f) {
         _snowman.clear();
      }
   }

   public void a(bfy var1) {
      for (bmb _snowman : this.a) {
         _snowman.a(_snowman);
      }
   }
}
