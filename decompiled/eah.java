import java.util.Optional;

public class eah implements eag {
   private final eag.a a;
   private final eag.a b = eag.a(new dfh(256));
   private int c = 255;
   private int d = 255;
   private int e = 255;
   private int f = 255;

   public eah(eag.a var1) {
      this.a = _snowman;
   }

   @Override
   public dfq getBuffer(eao var1) {
      if (_snowman.z()) {
         dfq _snowman = this.b.getBuffer(_snowman);
         return new eah.a(_snowman, this.c, this.d, this.e, this.f);
      } else {
         dfq _snowman = this.a.getBuffer(_snowman);
         Optional<eao> _snowmanx = _snowman.y();
         if (_snowmanx.isPresent()) {
            dfq _snowmanxx = this.b.getBuffer(_snowmanx.get());
            eah.a _snowmanxxx = new eah.a(_snowmanxx, this.c, this.d, this.e, this.f);
            return dft.a(_snowmanxxx, _snowman);
         } else {
            return _snowman;
         }
      }
   }

   public void a(int var1, int var2, int var3, int var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public void a() {
      this.b.a();
   }

   static class a extends dfl {
      private final dfq g;
      private double h;
      private double i;
      private double j;
      private float k;
      private float l;

      private a(dfq var1, int var2, int var3, int var4, int var5) {
         this.g = _snowman;
         super.b(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public void b(int var1, int var2, int var3, int var4) {
      }

      @Override
      public dfq a(double var1, double var3, double var5) {
         this.h = _snowman;
         this.i = _snowman;
         this.j = _snowman;
         return this;
      }

      @Override
      public dfq a(int var1, int var2, int var3, int var4) {
         return this;
      }

      @Override
      public dfq a(float var1, float var2) {
         this.k = _snowman;
         this.l = _snowman;
         return this;
      }

      @Override
      public dfq a(int var1, int var2) {
         return this;
      }

      @Override
      public dfq b(int var1, int var2) {
         return this;
      }

      @Override
      public dfq b(float var1, float var2, float var3) {
         return this;
      }

      @Override
      public void a(
         float var1,
         float var2,
         float var3,
         float var4,
         float var5,
         float var6,
         float var7,
         float var8,
         float var9,
         int var10,
         int var11,
         float var12,
         float var13,
         float var14
      ) {
         this.g.a((double)_snowman, (double)_snowman, (double)_snowman).a(this.b, this.c, this.d, this.e).a(_snowman, _snowman).d();
      }

      @Override
      public void d() {
         this.g.a(this.h, this.i, this.j).a(this.b, this.c, this.d, this.e).a(this.k, this.l).d();
      }
   }
}
