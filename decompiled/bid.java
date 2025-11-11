public abstract class bid extends bjj<aon> {
   private final aon d;
   private final bil e;
   protected final brx c;
   private final bot<? extends boc> f;
   private final bjk g;

   protected bid(bje<?> var1, bot<? extends boc> var2, bjk var3, int var4, bfv var5) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, new apa(3), new bjq(4));
   }

   protected bid(bje<?> var1, bot<? extends boc> var2, bjk var3, int var4, bfv var5, aon var6, bil var7) {
      super(_snowman, _snowman);
      this.f = _snowman;
      this.g = _snowman;
      a(_snowman, 3);
      a(_snowman, 4);
      this.d = _snowman;
      this.e = _snowman;
      this.c = _snowman.e.l;
      this.a(new bjr(_snowman, 0, 56, 17));
      this.a(new bit(this, _snowman, 1, 56, 53));
      this.a(new biv(_snowman.e, _snowman, 2, 116, 35));

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.a(new bjr(_snowman, _snowman, 8 + _snowman * 18, 142));
      }

      this.a(_snowman);
   }

   @Override
   public void a(bfy var1) {
      if (this.d instanceof bju) {
         ((bju)this.d).a(_snowman);
      }
   }

   @Override
   public void e() {
      this.d.Y_();
   }

   @Override
   public void a(boolean var1, boq<?> var2, aah var3) {
      new vb<>(this).a(_snowman, (boq<aon>)_snowman, _snowman);
   }

   @Override
   public boolean a(boq<? super aon> var1) {
      return _snowman.a(this.d, this.c);
   }

   @Override
   public int f() {
      return 2;
   }

   @Override
   public int g() {
      return 1;
   }

   @Override
   public int h() {
      return 1;
   }

   @Override
   public int i() {
      return 3;
   }

   @Override
   public boolean a(bfw var1) {
      return this.d.a(_snowman);
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         if (_snowman == 2) {
            if (!this.a(_snowmanxx, 3, 39, true)) {
               return bmb.b;
            }

            _snowmanx.a(_snowmanxx, _snowman);
         } else if (_snowman != 1 && _snowman != 0) {
            if (this.a(_snowmanxx)) {
               if (!this.a(_snowmanxx, 0, 1, false)) {
                  return bmb.b;
               }
            } else if (this.b(_snowmanxx)) {
               if (!this.a(_snowmanxx, 1, 2, false)) {
                  return bmb.b;
               }
            } else if (_snowman >= 3 && _snowman < 30) {
               if (!this.a(_snowmanxx, 30, 39, false)) {
                  return bmb.b;
               }
            } else if (_snowman >= 30 && _snowman < 39 && !this.a(_snowmanxx, 3, 30, false)) {
               return bmb.b;
            }
         } else if (!this.a(_snowmanxx, 3, 39, false)) {
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

   protected boolean a(bmb var1) {
      return this.c.o().a(this.f, new apa(_snowman), this.c).isPresent();
   }

   protected boolean b(bmb var1) {
      return cbz.b(_snowman);
   }

   public int j() {
      int _snowman = this.e.a(2);
      int _snowmanx = this.e.a(3);
      return _snowmanx != 0 && _snowman != 0 ? _snowman * 24 / _snowmanx : 0;
   }

   public int k() {
      int _snowman = this.e.a(1);
      if (_snowman == 0) {
         _snowman = 200;
      }

      return this.e.a(0) * 13 / _snowman;
   }

   public boolean l() {
      return this.e.a(0) > 0;
   }

   @Override
   public bjk m() {
      return this.g;
   }
}
