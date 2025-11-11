import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;

public class dqy extends dpp<bjv> {
   private static final vk A = new vk("textures/gui/container/stonecutter.png");
   private float B;
   private boolean C;
   private int D;
   private boolean E;

   public dqy(bjv var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
      _snowman.a(this::l);
      this.q--;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman);
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      this.a(_snowman);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(A);
      int _snowman = this.w;
      int _snowmanx = this.x;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
      int _snowmanxx = (int)(41.0F * this.B);
      this.b(_snowman, _snowman + 119, _snowmanx + 15 + _snowmanxx, 176 + (this.k() ? 0 : 12), 0, 12, 15);
      int _snowmanxxx = this.w + 52;
      int _snowmanxxxx = this.x + 14;
      int _snowmanxxxxx = this.D + 12;
      this.b(_snowman, _snowman, _snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      this.c(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   @Override
   protected void a(dfm var1, int var2, int var3) {
      super.a(_snowman, _snowman, _snowman);
      if (this.E) {
         int _snowman = this.w + 52;
         int _snowmanx = this.x + 14;
         int _snowmanxx = this.D + 12;
         List<bpe> _snowmanxxx = this.t.f();

         for (int _snowmanxxxx = this.D; _snowmanxxxx < _snowmanxx && _snowmanxxxx < this.t.g(); _snowmanxxxx++) {
            int _snowmanxxxxx = _snowmanxxxx - this.D;
            int _snowmanxxxxxx = _snowman + _snowmanxxxxx % 4 * 16;
            int _snowmanxxxxxxx = _snowmanx + _snowmanxxxxx / 4 * 18 + 2;
            if (_snowman >= _snowmanxxxxxx && _snowman < _snowmanxxxxxx + 16 && _snowman >= _snowmanxxxxxxx && _snowman < _snowmanxxxxxxx + 18) {
               this.a(_snowman, _snowmanxxx.get(_snowmanxxxx).c(), _snowman, _snowman);
            }
         }
      }
   }

   private void b(dfm var1, int var2, int var3, int var4, int var5, int var6) {
      for (int _snowman = this.D; _snowman < _snowman && _snowman < this.t.g(); _snowman++) {
         int _snowmanx = _snowman - this.D;
         int _snowmanxx = _snowman + _snowmanx % 4 * 16;
         int _snowmanxxx = _snowmanx / 4;
         int _snowmanxxxx = _snowman + _snowmanxxx * 18 + 2;
         int _snowmanxxxxx = this.c;
         if (_snowman == this.t.e()) {
            _snowmanxxxxx += 18;
         } else if (_snowman >= _snowmanxx && _snowman >= _snowmanxxxx && _snowman < _snowmanxx + 16 && _snowman < _snowmanxxxx + 18) {
            _snowmanxxxxx += 36;
         }

         this.b(_snowman, _snowmanxx, _snowmanxxxx - 1, 0, _snowmanxxxxx, 16, 18);
      }
   }

   private void c(int var1, int var2, int var3) {
      List<bpe> _snowman = this.t.f();

      for (int _snowmanx = this.D; _snowmanx < _snowman && _snowmanx < this.t.g(); _snowmanx++) {
         int _snowmanxx = _snowmanx - this.D;
         int _snowmanxxx = _snowman + _snowmanxx % 4 * 16;
         int _snowmanxxxx = _snowmanxx / 4;
         int _snowmanxxxxx = _snowman + _snowmanxxxx * 18 + 2;
         this.i.ad().b(_snowman.get(_snowmanx).c(), _snowmanxxx, _snowmanxxxxx);
      }
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      this.C = false;
      if (this.E) {
         int _snowman = this.w + 52;
         int _snowmanx = this.x + 14;
         int _snowmanxx = this.D + 12;

         for (int _snowmanxxx = this.D; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
            int _snowmanxxxx = _snowmanxxx - this.D;
            double _snowmanxxxxx = _snowman - (double)(_snowman + _snowmanxxxx % 4 * 16);
            double _snowmanxxxxxx = _snowman - (double)(_snowmanx + _snowmanxxxx / 4 * 18);
            if (_snowmanxxxxx >= 0.0 && _snowmanxxxxxx >= 0.0 && _snowmanxxxxx < 16.0 && _snowmanxxxxxx < 18.0 && this.t.a(this.i.s, _snowmanxxx)) {
               djz.C().W().a(emp.a(adq.pK, 1.0F));
               this.i.q.a(this.t.b, _snowmanxxx);
               return true;
            }
         }

         _snowman = this.w + 119;
         _snowmanx = this.x + 9;
         if (_snowman >= (double)_snowman && _snowman < (double)(_snowman + 12) && _snowman >= (double)_snowmanx && _snowman < (double)(_snowmanx + 54)) {
            this.C = true;
         }
      }

      return super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(double var1, double var3, int var5, double var6, double var8) {
      if (this.C && this.k()) {
         int _snowman = this.x + 14;
         int _snowmanx = _snowman + 54;
         this.B = ((float)_snowman - (float)_snowman - 7.5F) / ((float)(_snowmanx - _snowman) - 15.0F);
         this.B = afm.a(this.B, 0.0F, 1.0F);
         this.D = (int)((double)(this.B * (float)this.i()) + 0.5) * 4;
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a(double var1, double var3, double var5) {
      if (this.k()) {
         int _snowman = this.i();
         this.B = (float)((double)this.B - _snowman / (double)_snowman);
         this.B = afm.a(this.B, 0.0F, 1.0F);
         this.D = (int)((double)(this.B * (float)_snowman) + 0.5) * 4;
      }

      return true;
   }

   private boolean k() {
      return this.E && this.t.g() > 12;
   }

   protected int i() {
      return (this.t.g() + 4 - 1) / 4 - 3;
   }

   private void l() {
      this.E = this.t.h();
      if (!this.E) {
         this.B = 0.0F;
         this.D = 0;
      }
   }
}
