import java.util.EnumSet;

public abstract class awj extends avv {
   protected final aqu a;
   public final double b;
   protected int c;
   protected int d;
   private int g;
   protected fx e = fx.b;
   private boolean h;
   private final int i;
   private final int j;
   protected int f;

   public awj(aqu var1, double var2, int var4) {
      this(_snowman, _snowman, _snowman, 1);
   }

   public awj(aqu var1, double var2, int var4, int var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.i = _snowman;
      this.f = 0;
      this.j = _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.c));
   }

   @Override
   public boolean a() {
      if (this.c > 0) {
         this.c--;
         return false;
      } else {
         this.c = this.a(this.a);
         return this.m();
      }
   }

   protected int a(aqu var1) {
      return 200 + _snowman.cY().nextInt(200);
   }

   @Override
   public boolean b() {
      return this.d >= -this.g && this.d <= 1200 && this.a(this.a.l, this.e);
   }

   @Override
   public void c() {
      this.g();
      this.d = 0;
      this.g = this.a.cY().nextInt(this.a.cY().nextInt(1200) + 1200) + 1200;
   }

   protected void g() {
      this.a.x().a((double)((float)this.e.u()) + 0.5, (double)(this.e.v() + 1), (double)((float)this.e.w()) + 0.5, this.b);
   }

   public double h() {
      return 1.0;
   }

   protected fx j() {
      return this.e.b();
   }

   @Override
   public void e() {
      fx _snowman = this.j();
      if (!_snowman.a(this.a.cA(), this.h())) {
         this.h = false;
         this.d++;
         if (this.k()) {
            this.a.x().a((double)((float)_snowman.u()) + 0.5, (double)_snowman.v(), (double)((float)_snowman.w()) + 0.5, this.b);
         }
      } else {
         this.h = true;
         this.d--;
      }
   }

   public boolean k() {
      return this.d % 40 == 0;
   }

   protected boolean l() {
      return this.h;
   }

   protected boolean m() {
      int _snowman = this.i;
      int _snowmanx = this.j;
      fx _snowmanxx = this.a.cB();
      fx.a _snowmanxxx = new fx.a();

      for (int _snowmanxxxx = this.f; _snowmanxxxx <= _snowmanx; _snowmanxxxx = _snowmanxxxx > 0 ? -_snowmanxxxx : 1 - _snowmanxxxx) {
         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= _snowmanxxxxx; _snowmanxxxxxx = _snowmanxxxxxx > 0 ? -_snowmanxxxxxx : 1 - _snowmanxxxxxx) {
               for (int _snowmanxxxxxxx = _snowmanxxxxxx < _snowmanxxxxx && _snowmanxxxxxx > -_snowmanxxxxx ? _snowmanxxxxx : 0; _snowmanxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxx = _snowmanxxxxxxx > 0 ? -_snowmanxxxxxxx : 1 - _snowmanxxxxxxx) {
                  _snowmanxxx.a(_snowmanxx, _snowmanxxxxxx, _snowmanxxxx - 1, _snowmanxxxxxxx);
                  if (this.a.a(_snowmanxxx) && this.a(this.a.l, _snowmanxxx)) {
                     this.e = _snowmanxxx;
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   protected abstract boolean a(brz var1, fx var2);
}
