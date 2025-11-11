import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class dwd extends dum<bee> {
   private final dwn a;
   private final dwn b;

   public dwd() {
      super(0.0F, 0.0F, 64, 64);
      this.l.h = false;
      this.g.h = false;
      this.k = new dwn(this, 32, 0);
      this.k.a(-1.0F, -1.0F, -2.0F, 6.0F, 10.0F, 4.0F, 0.0F);
      this.k.a(-1.9F, 12.0F, 0.0F);
      this.b = new dwn(this, 0, 32);
      this.b.a(-20.0F, 0.0F, 0.0F, 20.0F, 12.0F, 1.0F);
      this.a = new dwn(this, 0, 32);
      this.a.g = true;
      this.a.a(0.0F, 0.0F, 0.0F, 20.0F, 12.0F, 1.0F);
   }

   @Override
   protected Iterable<dwn> b() {
      return Iterables.concat(super.b(), ImmutableList.of(this.b, this.a));
   }

   public void a(bee var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.eM()) {
         if (_snowman.dD().a()) {
            this.i.d = (float) (Math.PI * 3.0 / 2.0);
            this.j.d = (float) (Math.PI * 3.0 / 2.0);
         } else if (_snowman.dV() == aqi.b) {
            this.i.d = 3.7699115F;
         } else {
            this.j.d = 3.7699115F;
         }
      }

      this.k.d += (float) (Math.PI / 5);
      this.b.c = 2.0F;
      this.a.c = 2.0F;
      this.b.b = 1.0F;
      this.a.b = 1.0F;
      this.b.e = 0.47123894F + afm.b(_snowman * 0.8F) * (float) Math.PI * 0.05F;
      this.a.e = -this.b.e;
      this.a.f = -0.47123894F;
      this.a.d = 0.47123894F;
      this.b.d = 0.47123894F;
      this.b.f = 0.47123894F;
   }
}
