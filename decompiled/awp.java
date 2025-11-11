import java.util.EnumSet;
import javax.annotation.Nullable;

public class awp extends avv {
   protected final aqu a;
   protected final double b;
   protected double c;
   protected double d;
   protected double e;
   protected boolean f;

   public awp(aqu var1, double var2) {
      this.a = _snowman;
      this.b = _snowman;
      this.a(EnumSet.of(avv.a.a));
   }

   @Override
   public boolean a() {
      if (this.a.cZ() == null && !this.a.bq()) {
         return false;
      } else {
         if (this.a.bq()) {
            fx _snowman = this.a(this.a.l, this.a, 5, 4);
            if (_snowman != null) {
               this.c = (double)_snowman.u();
               this.d = (double)_snowman.v();
               this.e = (double)_snowman.w();
               return true;
            }
         }

         return this.g();
      }
   }

   protected boolean g() {
      dcn _snowman = azj.a(this.a, 5, 4);
      if (_snowman == null) {
         return false;
      } else {
         this.c = _snowman.b;
         this.d = _snowman.c;
         this.e = _snowman.d;
         return true;
      }
   }

   public boolean h() {
      return this.f;
   }

   @Override
   public void c() {
      this.a.x().a(this.c, this.d, this.e, this.b);
      this.f = true;
   }

   @Override
   public void d() {
      this.f = false;
   }

   @Override
   public boolean b() {
      return !this.a.x().m();
   }

   @Nullable
   protected fx a(brc var1, aqa var2, int var3, int var4) {
      fx _snowman = _snowman.cB();
      int _snowmanx = _snowman.u();
      int _snowmanxx = _snowman.v();
      int _snowmanxxx = _snowman.w();
      float _snowmanxxxx = (float)(_snowman * _snowman * _snowman * 2);
      fx _snowmanxxxxx = null;
      fx.a _snowmanxxxxxx = new fx.a();

      for (int _snowmanxxxxxxx = _snowmanx - _snowman; _snowmanxxxxxxx <= _snowmanx + _snowman; _snowmanxxxxxxx++) {
         for (int _snowmanxxxxxxxx = _snowmanxx - _snowman; _snowmanxxxxxxxx <= _snowmanxx + _snowman; _snowmanxxxxxxxx++) {
            for (int _snowmanxxxxxxxxx = _snowmanxxx - _snowman; _snowmanxxxxxxxxx <= _snowmanxxx + _snowman; _snowmanxxxxxxxxx++) {
               _snowmanxxxxxx.d(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
               if (_snowman.b(_snowmanxxxxxx).a(aef.b)) {
                  float _snowmanxxxxxxxxxx = (float)(
                     (_snowmanxxxxxxx - _snowmanx) * (_snowmanxxxxxxx - _snowmanx) + (_snowmanxxxxxxxx - _snowmanxx) * (_snowmanxxxxxxxx - _snowmanxx) + (_snowmanxxxxxxxxx - _snowmanxxx) * (_snowmanxxxxxxxxx - _snowmanxxx)
                  );
                  if (_snowmanxxxxxxxxxx < _snowmanxxxx) {
                     _snowmanxxxx = _snowmanxxxxxxxxxx;
                     _snowmanxxxxx = new fx(_snowmanxxxxxx);
                  }
               }
            }
         }
      }

      return _snowmanxxxxx;
   }
}
