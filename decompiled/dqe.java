import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;

public abstract class dqe<T extends bic> extends dpp<T> {
   protected boolean A;

   public dqe(T var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected void b() {
      super.b();
      this.i();
   }

   protected void i() {
      if (this.i.s.dh().isEmpty()) {
         this.w = (this.k - this.b) / 2;
         this.A = false;
      } else {
         this.w = 160 + (this.k - this.b - 200) / 2;
         this.A = true;
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (this.A) {
         this.b(_snowman);
      }
   }

   private void b(dfm var1) {
      int _snowman = this.w - 124;
      Collection<apu> _snowmanx = this.i.s.dh();
      if (!_snowmanx.isEmpty()) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int _snowmanxx = 33;
         if (_snowmanx.size() > 5) {
            _snowmanxx = 132 / (_snowmanx.size() - 1);
         }

         Iterable<apu> _snowmanxxx = Ordering.natural().sortedCopy(_snowmanx);
         this.a(_snowman, _snowman, _snowmanxx, _snowmanxxx);
         this.b(_snowman, _snowman, _snowmanxx, _snowmanxxx);
         this.c(_snowman, _snowman, _snowmanxx, _snowmanxxx);
      }
   }

   private void a(dfm var1, int var2, int var3, Iterable<apu> var4) {
      this.i.M().a(a);
      int _snowman = this.x;

      for (apu _snowmanx : _snowman) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.b(_snowman, _snowman, _snowman, 0, 166, 140, 32);
         _snowman += _snowman;
      }
   }

   private void b(dfm var1, int var2, int var3, Iterable<apu> var4) {
      ekp _snowman = this.i.at();
      int _snowmanx = this.x;

      for (apu _snowmanxx : _snowman) {
         aps _snowmanxxx = _snowmanxx.a();
         ekc _snowmanxxxx = _snowman.a(_snowmanxxx);
         this.i.M().a(_snowmanxxxx.m().g());
         a(_snowman, _snowman + 6, _snowmanx + 7, this.v(), 18, 18, _snowmanxxxx);
         _snowmanx += _snowman;
      }
   }

   private void c(dfm var1, int var2, int var3, Iterable<apu> var4) {
      int _snowman = this.x;

      for (apu _snowmanx : _snowman) {
         String _snowmanxx = ekx.a(_snowmanx.a().c());
         if (_snowmanx.c() >= 1 && _snowmanx.c() <= 9) {
            _snowmanxx = _snowmanxx + ' ' + ekx.a("enchantment.level." + (_snowmanx.c() + 1));
         }

         this.o.a(_snowman, _snowmanxx, (float)(_snowman + 10 + 18), (float)(_snowman + 6), 16777215);
         String _snowmanxxx = apv.a(_snowmanx, 1.0F);
         this.o.a(_snowman, _snowmanxxx, (float)(_snowman + 10 + 18), (float)(_snowman + 6 + 10), 8355711);
         _snowman += _snowman;
      }
   }
}
