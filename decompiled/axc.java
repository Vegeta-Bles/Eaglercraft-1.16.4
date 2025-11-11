import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;

public class axc extends avv {
   private final aqu a;
   private final int b;
   @Nullable
   private fx c;

   public axc(aqu var1, int var2) {
      this.a = _snowman;
      this.b = _snowman;
      this.a(EnumSet.of(avv.a.a));
   }

   @Override
   public boolean a() {
      if (this.a.bs()) {
         return false;
      } else if (this.a.l.M()) {
         return false;
      } else if (this.a.cY().nextInt(this.b) != 0) {
         return false;
      } else {
         aag _snowman = (aag)this.a.l;
         fx _snowmanx = this.a.cB();
         if (!_snowman.a(_snowmanx, 6)) {
            return false;
         } else {
            dcn _snowmanxx = azj.a(this.a, 15, 7, var1x -> (double)(-_snowman.b(gp.a(var1x))));
            this.c = _snowmanxx == null ? null : new fx(_snowmanxx);
            return this.c != null;
         }
      }
   }

   @Override
   public boolean b() {
      return this.c != null && !this.a.x().m() && this.a.x().h().equals(this.c);
   }

   @Override
   public void e() {
      if (this.c != null) {
         ayj _snowman = this.a.x();
         if (_snowman.m() && !this.c.a(this.a.cA(), 10.0)) {
            dcn _snowmanx = dcn.c(this.c);
            dcn _snowmanxx = this.a.cA();
            dcn _snowmanxxx = _snowmanxx.d(_snowmanx);
            _snowmanx = _snowmanxxx.a(0.4).e(_snowmanx);
            dcn _snowmanxxxx = _snowmanx.d(_snowmanxx).d().a(10.0).e(_snowmanxx);
            fx _snowmanxxxxx = new fx(_snowmanxxxx);
            _snowmanxxxxx = this.a.l.a(chn.a.f, _snowmanxxxxx);
            if (!_snowman.a((double)_snowmanxxxxx.u(), (double)_snowmanxxxxx.v(), (double)_snowmanxxxxx.w(), 1.0)) {
               this.g();
            }
         }
      }
   }

   private void g() {
      Random _snowman = this.a.cY();
      fx _snowmanx = this.a.l.a(chn.a.f, this.a.cB().b(-8 + _snowman.nextInt(16), 0, -8 + _snowman.nextInt(16)));
      this.a.x().a((double)_snowmanx.u(), (double)_snowmanx.v(), (double)_snowmanx.w(), 1.0);
   }
}
