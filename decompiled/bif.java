import javax.annotation.Nullable;

public class bif extends bic {
   private final aon c = new apa(1) {
      @Override
      public boolean b(int var1, bmb var2) {
         return _snowman.b().a(aeg.aa);
      }

      @Override
      public int V_() {
         return 1;
      }
   };
   private final bif.a d;
   private final bim e;
   private final bil f;

   public bif(int var1, aon var2) {
      this(_snowman, _snowman, new bjq(3), bim.a);
   }

   public bif(int var1, aon var2, bil var3, bim var4) {
      super(bje.i, _snowman);
      a(_snowman, 3);
      this.f = _snowman;
      this.e = _snowman;
      this.d = new bif.a(this.c, 0, 136, 110);
      this.a(this.d);
      this.a(_snowman);
      int _snowman = 36;
      int _snowmanx = 137;

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
            this.a(new bjr(_snowman, _snowmanxxx + _snowmanxx * 9 + 9, 36 + _snowmanxxx * 18, 137 + _snowmanxx * 18));
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
         this.a(new bjr(_snowman, _snowmanxx, 36 + _snowmanxx * 18, 195));
      }
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      if (!_snowman.l.v) {
         bmb _snowman = this.d.a(this.d.a());
         if (!_snowman.a()) {
            _snowman.a(_snowman, false);
         }
      }
   }

   @Override
   public boolean a(bfw var1) {
      return a(this.e, _snowman, bup.es);
   }

   @Override
   public void a(int var1, int var2) {
      super.a(_snowman, _snowman);
      this.c();
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         if (_snowman == 0) {
            if (!this.a(_snowmanxx, 1, 37, true)) {
               return bmb.b;
            }

            _snowmanx.a(_snowmanxx, _snowman);
         } else if (!this.d.f() && this.d.a(_snowmanxx) && _snowmanxx.E() == 1) {
            if (!this.a(_snowmanxx, 0, 1, false)) {
               return bmb.b;
            }
         } else if (_snowman >= 1 && _snowman < 28) {
            if (!this.a(_snowmanxx, 28, 37, false)) {
               return bmb.b;
            }
         } else if (_snowman >= 28 && _snowman < 37) {
            if (!this.a(_snowmanxx, 1, 28, false)) {
               return bmb.b;
            }
         } else if (!this.a(_snowmanxx, 1, 37, false)) {
            return bmb.b;
         }

         if (_snowmanxx.a()) {
            _snowmanx.d(bmb.b);
         } else {
            _snowmanx.d();
         }

         if (_snowmanxx.E() == _snowman.E()) {
            return bmb.b;
         }

         _snowmanx.a(_snowman, _snowmanxx);
      }

      return _snowman;
   }

   public int e() {
      return this.f.a(0);
   }

   @Nullable
   public aps f() {
      return aps.a(this.f.a(1));
   }

   @Nullable
   public aps g() {
      return aps.a(this.f.a(2));
   }

   public void c(int var1, int var2) {
      if (this.d.f()) {
         this.f.a(1, _snowman);
         this.f.a(2, _snowman);
         this.d.a(1);
      }
   }

   public boolean h() {
      return !this.c.a(0).a();
   }

   class a extends bjr {
      public a(aon var2, int var3, int var4, int var5) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a(bmb var1) {
         return _snowman.b().a(aeg.aa);
      }

      @Override
      public int a() {
         return 1;
      }
   }
}
