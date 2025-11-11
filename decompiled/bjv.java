import com.google.common.collect.Lists;
import java.util.List;

public class bjv extends bic {
   private final bim f;
   private final biq g = biq.a();
   private final brx h;
   private List<bpe> i = Lists.newArrayList();
   private bmb j = bmb.b;
   private long k;
   final bjr c;
   final bjr d;
   private Runnable l = () -> {
   };
   public final aon e = new apa(1) {
      @Override
      public void X_() {
         super.X_();
         bjv.this.a(this);
         bjv.this.l.run();
      }
   };
   private final bjm m = new bjm();

   public bjv(int var1, bfv var2) {
      this(_snowman, _snowman, bim.a);
   }

   public bjv(int var1, bfv var2, final bim var3) {
      super(bje.x, _snowman);
      this.f = _snowman;
      this.h = _snowman.e.l;
      this.c = this.a(new bjr(this.e, 0, 20, 33));
      this.d = this.a(new bjr(this.m, 1, 143, 33) {
         @Override
         public boolean a(bmb var1) {
            return false;
         }

         @Override
         public bmb a(bfw var1, bmb var2) {
            _snowman.a(_snowman.l, _snowman, _snowman.E());
            bjv.this.m.b(_snowman);
            bmb _snowman = bjv.this.c.a(1);
            if (!_snowman.a()) {
               bjv.this.i();
            }

            _snowman.a((var1x, var2x) -> {
               long _snowmanx = var1x.T();
               if (bjv.this.k != _snowmanx) {
                  var1x.a(null, var2x, adq.pJ, adr.e, 1.0F, 1.0F);
                  bjv.this.k = _snowmanx;
               }
            });
            return super.a(_snowman, _snowman);
         }
      });

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.a(new bjr(_snowman, _snowman, 8 + _snowman * 18, 142));
      }

      this.a(this.g);
   }

   public int e() {
      return this.g.b();
   }

   public List<bpe> f() {
      return this.i;
   }

   public int g() {
      return this.i.size();
   }

   public boolean h() {
      return this.c.f() && !this.i.isEmpty();
   }

   @Override
   public boolean a(bfw var1) {
      return a(this.f, _snowman, bup.ma);
   }

   @Override
   public boolean a(bfw var1, int var2) {
      if (this.d(_snowman)) {
         this.g.a(_snowman);
         this.i();
      }

      return true;
   }

   private boolean d(int var1) {
      return _snowman >= 0 && _snowman < this.i.size();
   }

   @Override
   public void a(aon var1) {
      bmb _snowman = this.c.e();
      if (_snowman.b() != this.j.b()) {
         this.j = _snowman.i();
         this.a(_snowman, _snowman);
      }
   }

   private void a(aon var1, bmb var2) {
      this.i.clear();
      this.g.a(-1);
      this.d.d(bmb.b);
      if (!_snowman.a()) {
         this.i = this.h.o().b(bot.f, _snowman, this.h);
      }
   }

   private void i() {
      if (!this.i.isEmpty() && this.d(this.g.b())) {
         bpe _snowman = this.i.get(this.g.b());
         this.m.a(_snowman);
         this.d.d(_snowman.a(this.e));
      } else {
         this.d.d(bmb.b);
      }

      this.c();
   }

   @Override
   public bje<?> a() {
      return bje.x;
   }

   public void a(Runnable var1) {
      this.l = _snowman;
   }

   @Override
   public boolean a(bmb var1, bjr var2) {
      return _snowman.c != this.m && super.a(_snowman, _snowman);
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         blx _snowmanxxx = _snowmanxx.b();
         _snowman = _snowmanxx.i();
         if (_snowman == 1) {
            _snowmanxxx.b(_snowmanxx, _snowman.l, _snowman);
            if (!this.a(_snowmanxx, 2, 38, true)) {
               return bmb.b;
            }

            _snowmanx.a(_snowmanxx, _snowman);
         } else if (_snowman == 0) {
            if (!this.a(_snowmanxx, 2, 38, false)) {
               return bmb.b;
            }
         } else if (this.h.o().a(bot.f, new apa(_snowmanxx), this.h).isPresent()) {
            if (!this.a(_snowmanxx, 0, 1, false)) {
               return bmb.b;
            }
         } else if (_snowman >= 2 && _snowman < 29) {
            if (!this.a(_snowmanxx, 29, 38, false)) {
               return bmb.b;
            }
         } else if (_snowman >= 29 && _snowman < 38 && !this.a(_snowmanxx, 2, 29, false)) {
            return bmb.b;
         }

         if (_snowmanxx.a()) {
            _snowmanx.d(bmb.b);
         }

         _snowmanx.d();
         if (_snowmanxx.E() == _snowman.E()) {
            return bmb.b;
         }

         _snowmanx.a(_snowman, _snowmanxx);
         this.c();
      }

      return _snowman;
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.m.b(1);
      this.f.a((var2, var3) -> this.a(_snowman, _snowman.l, this.e));
   }
}
