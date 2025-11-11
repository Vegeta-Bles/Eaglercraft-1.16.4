import java.util.EnumSet;

public class awd extends avv {
   protected final aqn a;
   protected aqa b;
   protected final float c;
   private int g;
   protected final float d;
   protected final Class<? extends aqm> e;
   protected final azg f;

   public awd(aqn var1, Class<? extends aqm> var2, float var3) {
      this(_snowman, _snowman, _snowman, 0.02F);
   }

   public awd(aqn var1, Class<? extends aqm> var2, float var3, float var4) {
      this.a = _snowman;
      this.e = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.a(EnumSet.of(avv.a.b));
      if (_snowman == bfw.class) {
         this.f = new azg().a((double)_snowman).b().a().d().a(var1x -> aqd.b(_snowman).test(var1x));
      } else {
         this.f = new azg().a((double)_snowman).b().a().d();
      }
   }

   @Override
   public boolean a() {
      if (this.a.cY().nextFloat() >= this.d) {
         return false;
      } else {
         if (this.a.A() != null) {
            this.b = this.a.A();
         }

         if (this.e == bfw.class) {
            this.b = this.a.l.a(this.f, this.a, this.a.cD(), this.a.cG(), this.a.cH());
         } else {
            this.b = this.a.l.b(this.e, this.f, this.a, this.a.cD(), this.a.cG(), this.a.cH(), this.a.cc().c((double)this.c, 3.0, (double)this.c));
         }

         return this.b != null;
      }
   }

   @Override
   public boolean b() {
      if (!this.b.aX()) {
         return false;
      } else {
         return this.a.h(this.b) > (double)(this.c * this.c) ? false : this.g > 0;
      }
   }

   @Override
   public void c() {
      this.g = 40 + this.a.cY().nextInt(40);
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void e() {
      this.a.t().a(this.b.cD(), this.b.cG(), this.b.cH());
      this.g--;
   }
}
