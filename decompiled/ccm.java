import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class ccm extends ccj implements aol, cdm {
   private final gj<bmb> a = gj.a(4, bmb.b);
   private final int[] b = new int[4];
   private final int[] c = new int[4];

   public ccm() {
      super(cck.F);
   }

   @Override
   public void aj_() {
      boolean _snowman = this.p().c(buy.b);
      boolean _snowmanx = this.d.v;
      if (_snowmanx) {
         if (_snowman) {
            this.j();
         }
      } else {
         if (_snowman) {
            this.h();
         } else {
            for (int _snowmanxx = 0; _snowmanxx < this.a.size(); _snowmanxx++) {
               if (this.b[_snowmanxx] > 0) {
                  this.b[_snowmanxx] = afm.a(this.b[_snowmanxx] - 2, 0, this.c[_snowmanxx]);
               }
            }
         }
      }
   }

   private void h() {
      for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
         bmb _snowmanx = this.a.get(_snowman);
         if (!_snowmanx.a()) {
            this.b[_snowman]++;
            if (this.b[_snowman] >= this.c[_snowman]) {
               aon _snowmanxx = new apa(_snowmanx);
               bmb _snowmanxxx = this.d.o().a(bot.e, _snowmanxx, this.d).map(var1x -> var1x.a(_snowman)).orElse(_snowmanx);
               fx _snowmanxxxx = this.o();
               aoq.a(this.d, (double)_snowmanxxxx.u(), (double)_snowmanxxxx.v(), (double)_snowmanxxxx.w(), _snowmanxxx);
               this.a.set(_snowman, bmb.b);
               this.k();
            }
         }
      }
   }

   private void j() {
      brx _snowman = this.v();
      if (_snowman != null) {
         fx _snowmanx = this.o();
         Random _snowmanxx = _snowman.t;
         if (_snowmanxx.nextFloat() < 0.11F) {
            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.nextInt(2) + 2; _snowmanxxx++) {
               buy.a(_snowman, _snowmanx, this.p().c(buy.c), false);
            }
         }

         int _snowmanxxx = this.p().c(buy.e).d();

         for (int _snowmanxxxx = 0; _snowmanxxxx < this.a.size(); _snowmanxxxx++) {
            if (!this.a.get(_snowmanxxxx).a() && _snowmanxx.nextFloat() < 0.2F) {
               gc _snowmanxxxxx = gc.b(Math.floorMod(_snowmanxxxx + _snowmanxxx, 4));
               float _snowmanxxxxxx = 0.3125F;
               double _snowmanxxxxxxx = (double)_snowmanx.u() + 0.5 - (double)((float)_snowmanxxxxx.i() * 0.3125F) + (double)((float)_snowmanxxxxx.g().i() * 0.3125F);
               double _snowmanxxxxxxxx = (double)_snowmanx.v() + 0.5;
               double _snowmanxxxxxxxxx = (double)_snowmanx.w() + 0.5 - (double)((float)_snowmanxxxxx.k() * 0.3125F) + (double)((float)_snowmanxxxxx.g().k() * 0.3125F);

               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 4; _snowmanxxxxxxxxxx++) {
                  _snowman.a(hh.S, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.0, 5.0E-4, 0.0);
               }
            }
         }
      }
   }

   public gj<bmb> d() {
      return this.a;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.a.clear();
      aoo.b(_snowman, this.a);
      if (_snowman.c("CookingTimes", 11)) {
         int[] _snowman = _snowman.n("CookingTimes");
         System.arraycopy(_snowman, 0, this.b, 0, Math.min(this.c.length, _snowman.length));
      }

      if (_snowman.c("CookingTotalTimes", 11)) {
         int[] _snowman = _snowman.n("CookingTotalTimes");
         System.arraycopy(_snowman, 0, this.c, 0, Math.min(this.c.length, _snowman.length));
      }
   }

   @Override
   public md a(md var1) {
      this.b(_snowman);
      _snowman.a("CookingTimes", this.b);
      _snowman.a("CookingTotalTimes", this.c);
      return _snowman;
   }

   private md b(md var1) {
      super.a(_snowman);
      aoo.a(_snowman, this.a, true);
      return _snowman;
   }

   @Nullable
   @Override
   public ow a() {
      return new ow(this.e, 13, this.b());
   }

   @Override
   public md b() {
      return this.b(new md());
   }

   public Optional<boh> a(bmb var1) {
      return this.a.stream().noneMatch(bmb::a) ? Optional.empty() : this.d.o().a(bot.e, new apa(_snowman), this.d);
   }

   public boolean a(bmb var1, int var2) {
      for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
         bmb _snowmanx = this.a.get(_snowman);
         if (_snowmanx.a()) {
            this.c[_snowman] = _snowman;
            this.b[_snowman] = 0;
            this.a.set(_snowman, _snowman.a(1));
            this.k();
            return true;
         }
      }

      return false;
   }

   private void k() {
      this.X_();
      this.v().a(this.o(), this.p(), this.p(), 3);
   }

   @Override
   public void Y_() {
      this.a.clear();
   }

   public void f() {
      if (this.d != null) {
         if (!this.d.v) {
            aoq.a(this.d, this.o(), this.d());
         }

         this.k();
      }
   }
}
