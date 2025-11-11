import javax.annotation.concurrent.Immutable;

@Immutable
public class aos {
   private final aor a;
   private final float b;

   public aos(aor var1, long var2, long var4, float var6) {
      this.a = _snowman;
      this.b = this.a(_snowman, _snowman, _snowman, _snowman);
   }

   public aor a() {
      return this.a;
   }

   public float b() {
      return this.b;
   }

   public boolean a(float var1) {
      return this.b > _snowman;
   }

   public float d() {
      if (this.b < 2.0F) {
         return 0.0F;
      } else {
         return this.b > 4.0F ? 1.0F : (this.b - 2.0F) / 2.0F;
      }
   }

   private float a(aor var1, long var2, long var4, float var6) {
      if (_snowman == aor.a) {
         return 0.0F;
      } else {
         boolean _snowman = _snowman == aor.d;
         float _snowmanx = 0.75F;
         float _snowmanxx = afm.a(((float)_snowman + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
         _snowmanx += _snowmanxx;
         float _snowmanxxx = 0.0F;
         _snowmanxxx += afm.a((float)_snowman / 3600000.0F, 0.0F, 1.0F) * (_snowman ? 1.0F : 0.75F);
         _snowmanxxx += afm.a(_snowman * 0.25F, 0.0F, _snowmanxx);
         if (_snowman == aor.b) {
            _snowmanxxx *= 0.5F;
         }

         _snowmanx += _snowmanxxx;
         return (float)_snowman.a() * _snowmanx;
      }
   }
}
