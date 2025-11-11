import java.util.Optional;

public class bip extends bjj<bio> {
   private final bio c = new bio(this, 3, 3);
   private final bjm d = new bjm();
   private final bim e;
   private final bfw f;

   public bip(int var1, bfv var2) {
      this(_snowman, _snowman, bim.a);
   }

   public bip(int var1, bfv var2, bim var3) {
      super(bje.l, _snowman);
      this.e = _snowman;
      this.f = _snowman.e;
      this.a(new bjn(_snowman.e, this.c, this.d, 0, 124, 35));

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
            this.a(new bjr(this.c, _snowmanx + _snowman * 3, 30 + _snowmanx * 18, 17 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.a(new bjr(_snowman, _snowman, 8 + _snowman * 18, 142));
      }
   }

   protected static void a(int var0, brx var1, bfw var2, bio var3, bjm var4) {
      if (!_snowman.v) {
         aah _snowman = (aah)_snowman;
         bmb _snowmanx = bmb.b;
         Optional<boi> _snowmanxx = _snowman.l().aF().a(bot.a, _snowman, _snowman);
         if (_snowmanxx.isPresent()) {
            boi _snowmanxxx = _snowmanxx.get();
            if (_snowman.a(_snowman, _snowman, _snowmanxxx)) {
               _snowmanx = _snowmanxxx.a(_snowman);
            }
         }

         _snowman.a(0, _snowmanx);
         _snowman.b.a(new pi(_snowman, 0, _snowmanx));
      }
   }

   @Override
   public void a(aon var1) {
      this.e.a((var1x, var2) -> a(this.b, var1x, this.f, this.c, this.d));
   }

   @Override
   public void a(bfy var1) {
      this.c.a(_snowman);
   }

   @Override
   public void e() {
      this.c.Y_();
      this.d.Y_();
   }

   @Override
   public boolean a(boq<? super bio> var1) {
      return _snowman.a(this.c, this.f.l);
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.e.a((var2, var3) -> this.a(_snowman, var2, this.c));
   }

   @Override
   public boolean a(bfw var1) {
      return a(this.e, _snowman, bup.bV);
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         if (_snowman == 0) {
            this.e.a((var2x, var3x) -> _snowman.b().b(_snowman, var2x, _snowman));
            if (!this.a(_snowmanxx, 10, 46, true)) {
               return bmb.b;
            }

            _snowmanx.a(_snowmanxx, _snowman);
         } else if (_snowman >= 10 && _snowman < 46) {
            if (!this.a(_snowmanxx, 1, 10, false)) {
               if (_snowman < 37) {
                  if (!this.a(_snowmanxx, 37, 46, false)) {
                     return bmb.b;
                  }
               } else if (!this.a(_snowmanxx, 10, 37, false)) {
                  return bmb.b;
               }
            }
         } else if (!this.a(_snowmanxx, 10, 46, false)) {
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

         bmb _snowmanxxx = _snowmanx.a(_snowman, _snowmanxx);
         if (_snowman == 0) {
            _snowman.a(_snowmanxxx, false);
         }
      }

      return _snowman;
   }

   @Override
   public boolean a(bmb var1, bjr var2) {
      return _snowman.c != this.d && super.a(_snowman, _snowman);
   }

   @Override
   public int f() {
      return 0;
   }

   @Override
   public int g() {
      return this.c.g();
   }

   @Override
   public int h() {
      return this.c.f();
   }

   @Override
   public int i() {
      return 10;
   }

   @Override
   public bjk m() {
      return bjk.a;
   }
}
