import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;

public class apa implements aon, bju {
   private final int a;
   private final gj<bmb> b;
   private List<aop> c;

   public apa(int var1) {
      this.a = _snowman;
      this.b = gj.a(_snowman, bmb.b);
   }

   public apa(bmb... var1) {
      this.a = _snowman.length;
      this.b = gj.a(bmb.b, _snowman);
   }

   public void a(aop var1) {
      if (this.c == null) {
         this.c = Lists.newArrayList();
      }

      this.c.add(_snowman);
   }

   public void b(aop var1) {
      this.c.remove(_snowman);
   }

   @Override
   public bmb a(int var1) {
      return _snowman >= 0 && _snowman < this.b.size() ? this.b.get(_snowman) : bmb.b;
   }

   public List<bmb> f() {
      List<bmb> _snowman = this.b.stream().filter(var0 -> !var0.a()).collect(Collectors.toList());
      this.Y_();
      return _snowman;
   }

   @Override
   public bmb a(int var1, int var2) {
      bmb _snowman = aoo.a(this.b, _snowman, _snowman);
      if (!_snowman.a()) {
         this.X_();
      }

      return _snowman;
   }

   public bmb a(blx var1, int var2) {
      bmb _snowman = new bmb(_snowman, 0);

      for (int _snowmanx = this.a - 1; _snowmanx >= 0; _snowmanx--) {
         bmb _snowmanxx = this.a(_snowmanx);
         if (_snowmanxx.b().equals(_snowman)) {
            int _snowmanxxx = _snowman - _snowman.E();
            bmb _snowmanxxxx = _snowmanxx.a(_snowmanxxx);
            _snowman.f(_snowmanxxxx.E());
            if (_snowman.E() == _snowman) {
               break;
            }
         }
      }

      if (!_snowman.a()) {
         this.X_();
      }

      return _snowman;
   }

   public bmb a(bmb var1) {
      bmb _snowman = _snowman.i();
      this.d(_snowman);
      if (_snowman.a()) {
         return bmb.b;
      } else {
         this.c(_snowman);
         return _snowman.a() ? bmb.b : _snowman;
      }
   }

   public boolean b(bmb var1) {
      boolean _snowman = false;

      for (bmb _snowmanx : this.b) {
         if (_snowmanx.a() || this.a(_snowmanx, _snowman) && _snowmanx.E() < _snowmanx.c()) {
            _snowman = true;
            break;
         }
      }

      return _snowman;
   }

   @Override
   public bmb b(int var1) {
      bmb _snowman = this.b.get(_snowman);
      if (_snowman.a()) {
         return bmb.b;
      } else {
         this.b.set(_snowman, bmb.b);
         return _snowman;
      }
   }

   @Override
   public void a(int var1, bmb var2) {
      this.b.set(_snowman, _snowman);
      if (!_snowman.a() && _snowman.E() > this.V_()) {
         _snowman.e(this.V_());
      }

      this.X_();
   }

   @Override
   public int Z_() {
      return this.a;
   }

   @Override
   public boolean c() {
      for (bmb _snowman : this.b) {
         if (!_snowman.a()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void X_() {
      if (this.c != null) {
         for (aop _snowman : this.c) {
            _snowman.a(this);
         }
      }
   }

   @Override
   public boolean a(bfw var1) {
      return true;
   }

   @Override
   public void Y_() {
      this.b.clear();
      this.X_();
   }

   @Override
   public void a(bfy var1) {
      for (bmb _snowman : this.b) {
         _snowman.b(_snowman);
      }
   }

   @Override
   public String toString() {
      return this.b.stream().filter(var0 -> !var0.a()).collect(Collectors.toList()).toString();
   }

   private void c(bmb var1) {
      for (int _snowman = 0; _snowman < this.a; _snowman++) {
         bmb _snowmanx = this.a(_snowman);
         if (_snowmanx.a()) {
            this.a(_snowman, _snowman.i());
            _snowman.e(0);
            return;
         }
      }
   }

   private void d(bmb var1) {
      for (int _snowman = 0; _snowman < this.a; _snowman++) {
         bmb _snowmanx = this.a(_snowman);
         if (this.a(_snowmanx, _snowman)) {
            this.b(_snowman, _snowmanx);
            if (_snowman.a()) {
               return;
            }
         }
      }
   }

   private boolean a(bmb var1, bmb var2) {
      return _snowman.b() == _snowman.b() && bmb.a(_snowman, _snowman);
   }

   private void b(bmb var1, bmb var2) {
      int _snowman = Math.min(this.V_(), _snowman.c());
      int _snowmanx = Math.min(_snowman.E(), _snowman - _snowman.E());
      if (_snowmanx > 0) {
         _snowman.f(_snowmanx);
         _snowman.g(_snowmanx);
         this.X_();
      }
   }

   public void a(mj var1) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         bmb _snowmanx = bmb.a(_snowman.a(_snowman));
         if (!_snowmanx.a()) {
            this.a(_snowmanx);
         }
      }
   }

   public mj g() {
      mj _snowman = new mj();

      for (int _snowmanx = 0; _snowmanx < this.Z_(); _snowmanx++) {
         bmb _snowmanxx = this.a(_snowmanx);
         if (!_snowmanxx.a()) {
            _snowman.add(_snowmanxx.b(new md()));
         }
      }

      return _snowman;
   }
}
