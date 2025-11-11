import java.util.Random;

public abstract class crq extends cru {
   protected final int a;
   protected final int b;
   protected final int c;
   protected int d = -1;

   protected crq(clb var1, Random var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      super(_snowman, 0);
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.a(gc.c.a.a(_snowman));
      if (this.i().n() == gc.a.c) {
         this.n = new cra(_snowman, _snowman, _snowman, _snowman + _snowman - 1, _snowman + _snowman - 1, _snowman + _snowman - 1);
      } else {
         this.n = new cra(_snowman, _snowman, _snowman, _snowman + _snowman - 1, _snowman + _snowman - 1, _snowman + _snowman - 1);
      }
   }

   protected crq(clb var1, md var2) {
      super(_snowman, _snowman);
      this.a = _snowman.h("Width");
      this.b = _snowman.h("Height");
      this.c = _snowman.h("Depth");
      this.d = _snowman.h("HPos");
   }

   @Override
   protected void a(md var1) {
      _snowman.b("Width", this.a);
      _snowman.b("Height", this.b);
      _snowman.b("Depth", this.c);
      _snowman.b("HPos", this.d);
   }

   protected boolean a(bry var1, cra var2, int var3) {
      if (this.d >= 0) {
         return true;
      } else {
         int _snowman = 0;
         int _snowmanx = 0;
         fx.a _snowmanxx = new fx.a();

         for (int _snowmanxxx = this.n.c; _snowmanxxx <= this.n.f; _snowmanxxx++) {
            for (int _snowmanxxxx = this.n.a; _snowmanxxxx <= this.n.d; _snowmanxxxx++) {
               _snowmanxx.d(_snowmanxxxx, 64, _snowmanxxx);
               if (_snowman.b(_snowmanxx)) {
                  _snowman += _snowman.a(chn.a.f, _snowmanxx).v();
                  _snowmanx++;
               }
            }
         }

         if (_snowmanx == 0) {
            return false;
         } else {
            this.d = _snowman / _snowmanx;
            this.n.a(0, this.d - this.n.b + _snowman, 0);
            return true;
         }
      }
   }
}
