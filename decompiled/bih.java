public class bih extends bic {
   private final aon c;
   private final bil d;
   private final bjr e;

   public bih(int var1, bfv var2) {
      this(_snowman, _snowman, new apa(5), new bjq(2));
   }

   public bih(int var1, bfv var2, aon var3, bil var4) {
      super(bje.k, _snowman);
      a(_snowman, 5);
      a(_snowman, 2);
      this.c = _snowman;
      this.d = _snowman;
      this.a(new bih.c(_snowman, 0, 56, 51));
      this.a(new bih.c(_snowman, 1, 79, 58));
      this.a(new bih.c(_snowman, 2, 102, 51));
      this.e = this.a(new bih.b(_snowman, 3, 79, 17));
      this.a(new bih.a(_snowman, 4, 17, 17));
      this.a(_snowman);

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.a(new bjr(_snowman, _snowman, 8 + _snowman * 18, 142));
      }
   }

   @Override
   public boolean a(bfw var1) {
      return this.c.a(_snowman);
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         if ((_snowman < 0 || _snowman > 2) && _snowman != 3 && _snowman != 4) {
            if (bih.a.a_(_snowman)) {
               if (this.a(_snowmanxx, 4, 5, false) || this.e.a(_snowmanxx) && !this.a(_snowmanxx, 3, 4, false)) {
                  return bmb.b;
               }
            } else if (this.e.a(_snowmanxx)) {
               if (!this.a(_snowmanxx, 3, 4, false)) {
                  return bmb.b;
               }
            } else if (bih.c.b_(_snowman) && _snowman.E() == 1) {
               if (!this.a(_snowmanxx, 0, 3, false)) {
                  return bmb.b;
               }
            } else if (_snowman >= 5 && _snowman < 32) {
               if (!this.a(_snowmanxx, 32, 41, false)) {
                  return bmb.b;
               }
            } else if (_snowman >= 32 && _snowman < 41) {
               if (!this.a(_snowmanxx, 5, 32, false)) {
                  return bmb.b;
               }
            } else if (!this.a(_snowmanxx, 5, 41, false)) {
               return bmb.b;
            }
         } else {
            if (!this.a(_snowmanxx, 5, 41, true)) {
               return bmb.b;
            }

            _snowmanx.a(_snowmanxx, _snowman);
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
      return this.d.a(1);
   }

   public int f() {
      return this.d.a(0);
   }

   static class a extends bjr {
      public a(aon var1, int var2, int var3, int var4) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a(bmb var1) {
         return a_(_snowman);
      }

      public static boolean a_(bmb var0) {
         return _snowman.b() == bmd.nz;
      }

      @Override
      public int a() {
         return 64;
      }
   }

   static class b extends bjr {
      public b(aon var1, int var2, int var3, int var4) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a(bmb var1) {
         return bnu.a(_snowman);
      }

      @Override
      public int a() {
         return 64;
      }
   }

   static class c extends bjr {
      public c(aon var1, int var2, int var3, int var4) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a(bmb var1) {
         return b_(_snowman);
      }

      @Override
      public int a() {
         return 1;
      }

      @Override
      public bmb a(bfw var1, bmb var2) {
         bnt _snowman = bnv.d(_snowman);
         if (_snowman instanceof aah) {
            ac.k.a((aah)_snowman, _snowman);
         }

         super.a(_snowman, _snowman);
         return _snowman;
      }

      public static boolean b_(bmb var0) {
         blx _snowman = _snowman.b();
         return _snowman == bmd.nv || _snowman == bmd.qj || _snowman == bmd.qm || _snowman == bmd.nw;
      }
   }
}
