import javax.annotation.Nullable;

public class edy extends efr<bcn, dti> {
   public static final vk a = new vk("textures/entity/armorstand/wood.png");

   public edy(eet var1) {
      super(_snowman, new dtj(), 0.0F);
      this.a(new eik<>(this, new dti(0.5F), new dti(1.0F)));
      this.a(new ein<>(this));
      this.a(new eid<>(this));
      this.a(new ehz<>(this));
   }

   public vk a(bcn var1) {
      return a;
   }

   protected void a(bcn var1, dfm var2, float var3, float var4, float var5) {
      _snowman.a(g.d.a(180.0F - _snowman));
      float _snowman = (float)(_snowman.l.T() - _snowman.bi) + _snowman;
      if (_snowman < 5.0F) {
         _snowman.a(g.d.a(afm.a(_snowman / 1.5F * (float) Math.PI) * 3.0F));
      }
   }

   protected boolean b(bcn var1) {
      double _snowman = this.b.b(_snowman);
      float _snowmanx = _snowman.bz() ? 32.0F : 64.0F;
      return _snowman >= (double)(_snowmanx * _snowmanx) ? false : _snowman.bX();
   }

   @Nullable
   protected eao a(bcn var1, boolean var2, boolean var3, boolean var4) {
      if (!_snowman.q()) {
         return super.a(_snowman, _snowman, _snowman, _snowman);
      } else {
         vk _snowman = this.a(_snowman);
         if (_snowman) {
            return eao.c(_snowman, false);
         } else {
            return _snowman ? eao.a(_snowman, false) : null;
         }
      }
   }
}
