import javax.annotation.Nullable;

public class bjf implements aon {
   private final bqu a;
   private final gj<bmb> b = gj.a(3, bmb.b);
   @Nullable
   private bqv c;
   private int d;
   private int e;

   public bjf(bqu var1) {
      this.a = _snowman;
   }

   @Override
   public int Z_() {
      return this.b.size();
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
   public bmb a(int var1) {
      return this.b.get(_snowman);
   }

   @Override
   public bmb a(int var1, int var2) {
      bmb _snowman = this.b.get(_snowman);
      if (_snowman == 2 && !_snowman.a()) {
         return aoo.a(this.b, _snowman, _snowman.E());
      } else {
         bmb _snowmanx = aoo.a(this.b, _snowman, _snowman);
         if (!_snowmanx.a() && this.d(_snowman)) {
            this.f();
         }

         return _snowmanx;
      }
   }

   private boolean d(int var1) {
      return _snowman == 0 || _snowman == 1;
   }

   @Override
   public bmb b(int var1) {
      return aoo.a(this.b, _snowman);
   }

   @Override
   public void a(int var1, bmb var2) {
      this.b.set(_snowman, _snowman);
      if (!_snowman.a() && _snowman.E() > this.V_()) {
         _snowman.e(this.V_());
      }

      if (this.d(_snowman)) {
         this.f();
      }
   }

   @Override
   public boolean a(bfw var1) {
      return this.a.eM() == _snowman;
   }

   @Override
   public void X_() {
      this.f();
   }

   public void f() {
      this.c = null;
      bmb _snowman;
      bmb _snowmanx;
      if (this.b.get(0).a()) {
         _snowman = this.b.get(1);
         _snowmanx = bmb.b;
      } else {
         _snowman = this.b.get(0);
         _snowmanx = this.b.get(1);
      }

      if (_snowman.a()) {
         this.a(2, bmb.b);
         this.e = 0;
      } else {
         bqw _snowmanxx = this.a.eO();
         if (!_snowmanxx.isEmpty()) {
            bqv _snowmanxxx = _snowmanxx.a(_snowman, _snowmanx, this.d);
            if (_snowmanxxx == null || _snowmanxxx.p()) {
               this.c = _snowmanxxx;
               _snowmanxxx = _snowmanxx.a(_snowmanx, _snowman, this.d);
            }

            if (_snowmanxxx != null && !_snowmanxxx.p()) {
               this.c = _snowmanxxx;
               this.a(2, _snowmanxxx.f());
               this.e = _snowmanxxx.o();
            } else {
               this.a(2, bmb.b);
               this.e = 0;
            }
         }

         this.a.k(this.a(2));
      }
   }

   @Nullable
   public bqv g() {
      return this.c;
   }

   public void c(int var1) {
      this.d = _snowman;
      this.f();
   }

   @Override
   public void Y_() {
      this.b.clear();
   }

   public int h() {
      return this.e;
   }
}
