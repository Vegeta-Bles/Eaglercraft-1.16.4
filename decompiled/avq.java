import java.util.List;

public class avq extends avv {
   private int a;
   private final aqu b;
   private bfw c;
   private avf d;

   public avq(aqu var1) {
      this.b = _snowman;
   }

   @Override
   public boolean a() {
      List<bhn> _snowman = this.b.l.a(bhn.class, this.b.cc().g(5.0));
      boolean _snowmanx = false;

      for (bhn _snowmanxx : _snowman) {
         aqa _snowmanxxx = _snowmanxx.cm();
         if (_snowmanxxx instanceof bfw && (afm.e(((bfw)_snowmanxxx).aR) > 0.0F || afm.e(((bfw)_snowmanxxx).aT) > 0.0F)) {
            _snowmanx = true;
            break;
         }
      }

      return this.c != null && (afm.e(this.c.aR) > 0.0F || afm.e(this.c.aT) > 0.0F) || _snowmanx;
   }

   @Override
   public boolean C_() {
      return true;
   }

   @Override
   public boolean b() {
      return this.c != null && this.c.br() && (afm.e(this.c.aR) > 0.0F || afm.e(this.c.aT) > 0.0F);
   }

   @Override
   public void c() {
      for (bhn _snowman : this.b.l.a(bhn.class, this.b.cc().g(5.0))) {
         if (_snowman.cm() != null && _snowman.cm() instanceof bfw) {
            this.c = (bfw)_snowman.cm();
            break;
         }
      }

      this.a = 0;
      this.d = avf.a;
   }

   @Override
   public void d() {
      this.c = null;
   }

   @Override
   public void e() {
      boolean _snowman = afm.e(this.c.aR) > 0.0F || afm.e(this.c.aT) > 0.0F;
      float _snowmanx = this.d == avf.b ? (_snowman ? 0.01F : 0.0F) : 0.015F;
      this.b.a(_snowmanx, new dcn((double)this.b.aR, (double)this.b.aS, (double)this.b.aT));
      this.b.a(aqr.a, this.b.cC());
      if (--this.a <= 0) {
         this.a = 10;
         if (this.d == avf.a) {
            fx _snowmanxx = this.c.cB().a(this.c.bZ().f());
            _snowmanxx = _snowmanxx.b(0, -1, 0);
            this.b.x().a((double)_snowmanxx.u(), (double)_snowmanxx.v(), (double)_snowmanxx.w(), 1.0);
            if (this.b.g(this.c) < 4.0F) {
               this.a = 0;
               this.d = avf.b;
            }
         } else if (this.d == avf.b) {
            gc _snowmanxx = this.c.ca();
            fx _snowmanxxx = this.c.cB().a(_snowmanxx, 10);
            this.b.x().a((double)_snowmanxxx.u(), (double)(_snowmanxxx.v() - 1), (double)_snowmanxxx.w(), 1.0);
            if (this.b.g(this.c) > 12.0F) {
               this.a = 0;
               this.d = avf.a;
            }
         }
      }
   }
}
