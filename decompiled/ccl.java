import java.util.Arrays;
import javax.annotation.Nullable;

public class ccl extends ccd implements ape, cdm {
   private static final int[] b = new int[]{3};
   private static final int[] c = new int[]{0, 1, 2, 3};
   private static final int[] g = new int[]{0, 1, 2, 4};
   private gj<bmb> h = gj.a(5, bmb.b);
   private int i;
   private boolean[] j;
   private blx k;
   private int l;
   protected final bil a = new bil() {
      @Override
      public int a(int var1) {
         switch (_snowman) {
            case 0:
               return ccl.this.i;
            case 1:
               return ccl.this.l;
            default:
               return 0;
         }
      }

      @Override
      public void a(int var1, int var2) {
         switch (_snowman) {
            case 0:
               ccl.this.i = _snowman;
               break;
            case 1:
               ccl.this.l = _snowman;
         }
      }

      @Override
      public int a() {
         return 2;
      }
   };

   public ccl() {
      super(cck.k);
   }

   @Override
   protected nr g() {
      return new of("container.brewing");
   }

   @Override
   public int Z_() {
      return this.h.size();
   }

   @Override
   public boolean c() {
      for (bmb _snowman : this.h) {
         if (!_snowman.a()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void aj_() {
      bmb _snowman = this.h.get(4);
      if (this.l <= 0 && _snowman.b() == bmd.nz) {
         this.l = 20;
         _snowman.g(1);
         this.X_();
      }

      boolean _snowmanx = this.h();
      boolean _snowmanxx = this.i > 0;
      bmb _snowmanxxx = this.h.get(3);
      if (_snowmanxx) {
         this.i--;
         boolean _snowmanxxxx = this.i == 0;
         if (_snowmanxxxx && _snowmanx) {
            this.j();
            this.X_();
         } else if (!_snowmanx) {
            this.i = 0;
            this.X_();
         } else if (this.k != _snowmanxxx.b()) {
            this.i = 0;
            this.X_();
         }
      } else if (_snowmanx && this.l > 0) {
         this.l--;
         this.i = 400;
         this.k = _snowmanxxx.b();
         this.X_();
      }

      if (!this.d.v) {
         boolean[] _snowmanxxxx = this.f();
         if (!Arrays.equals(_snowmanxxxx, this.j)) {
            this.j = _snowmanxxxx;
            ceh _snowmanxxxxx = this.d.d_(this.o());
            if (!(_snowmanxxxxx.b() instanceof bur)) {
               return;
            }

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < bur.a.length; _snowmanxxxxxx++) {
               _snowmanxxxxx = _snowmanxxxxx.a(bur.a[_snowmanxxxxxx], Boolean.valueOf(_snowmanxxxx[_snowmanxxxxxx]));
            }

            this.d.a(this.e, _snowmanxxxxx, 2);
         }
      }
   }

   public boolean[] f() {
      boolean[] _snowman = new boolean[3];

      for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
         if (!this.h.get(_snowmanx).a()) {
            _snowman[_snowmanx] = true;
         }
      }

      return _snowman;
   }

   private boolean h() {
      bmb _snowman = this.h.get(3);
      if (_snowman.a()) {
         return false;
      } else if (!bnu.a(_snowman)) {
         return false;
      } else {
         for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
            bmb _snowmanxx = this.h.get(_snowmanx);
            if (!_snowmanxx.a() && bnu.a(_snowmanxx, _snowman)) {
               return true;
            }
         }

         return false;
      }
   }

   private void j() {
      bmb _snowman = this.h.get(3);

      for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
         this.h.set(_snowmanx, bnu.d(_snowman, this.h.get(_snowmanx)));
      }

      _snowman.g(1);
      fx _snowmanx = this.o();
      if (_snowman.b().p()) {
         bmb _snowmanxx = new bmb(_snowman.b().o());
         if (_snowman.a()) {
            _snowman = _snowmanxx;
         } else if (!this.d.v) {
            aoq.a(this.d, (double)_snowmanx.u(), (double)_snowmanx.v(), (double)_snowmanx.w(), _snowmanxx);
         }
      }

      this.h.set(3, _snowman);
      this.d.c(1035, _snowmanx, 0);
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.h = gj.a(this.Z_(), bmb.b);
      aoo.b(_snowman, this.h);
      this.i = _snowman.g("BrewTime");
      this.l = _snowman.f("Fuel");
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      _snowman.a("BrewTime", (short)this.i);
      aoo.a(_snowman, this.h);
      _snowman.a("Fuel", (byte)this.l);
      return _snowman;
   }

   @Override
   public bmb a(int var1) {
      return _snowman >= 0 && _snowman < this.h.size() ? this.h.get(_snowman) : bmb.b;
   }

   @Override
   public bmb a(int var1, int var2) {
      return aoo.a(this.h, _snowman, _snowman);
   }

   @Override
   public bmb b(int var1) {
      return aoo.a(this.h, _snowman);
   }

   @Override
   public void a(int var1, bmb var2) {
      if (_snowman >= 0 && _snowman < this.h.size()) {
         this.h.set(_snowman, _snowman);
      }
   }

   @Override
   public boolean a(bfw var1) {
      return this.d.c(this.e) != this ? false : !(_snowman.h((double)this.e.u() + 0.5, (double)this.e.v() + 0.5, (double)this.e.w() + 0.5) > 64.0);
   }

   @Override
   public boolean b(int var1, bmb var2) {
      if (_snowman == 3) {
         return bnu.a(_snowman);
      } else {
         blx _snowman = _snowman.b();
         return _snowman == 4 ? _snowman == bmd.nz : (_snowman == bmd.nv || _snowman == bmd.qj || _snowman == bmd.qm || _snowman == bmd.nw) && this.a(_snowman).a();
      }
   }

   @Override
   public int[] a(gc var1) {
      if (_snowman == gc.b) {
         return b;
      } else {
         return _snowman == gc.a ? c : g;
      }
   }

   @Override
   public boolean a(int var1, bmb var2, @Nullable gc var3) {
      return this.b(_snowman, _snowman);
   }

   @Override
   public boolean b(int var1, bmb var2, gc var3) {
      return _snowman == 3 ? _snowman.b() == bmd.nw : true;
   }

   @Override
   public void Y_() {
      this.h.clear();
   }

   @Override
   protected bic a(int var1, bfv var2) {
      return new bih(_snowman, _snowman, this, this.a);
   }
}
