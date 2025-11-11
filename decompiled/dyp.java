public class dyp extends dzb {
   protected final dyw a;
   private final float b;
   private float D = 0.91F;
   private float E;
   private float F;
   private float G;
   private boolean H;

   protected dyp(dwt var1, double var2, double var4, double var6, dyw var8, float var9) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.a = _snowman;
      this.b = _snowman;
   }

   public void b(int var1) {
      float _snowman = (float)((_snowman & 0xFF0000) >> 16) / 255.0F;
      float _snowmanx = (float)((_snowman & 0xFF00) >> 8) / 255.0F;
      float _snowmanxx = (float)((_snowman & 0xFF) >> 0) / 255.0F;
      float _snowmanxxx = 1.0F;
      this.a(_snowman * 1.0F, _snowmanx * 1.0F, _snowmanxx * 1.0F);
   }

   public void c(int var1) {
      this.E = (float)((_snowman & 0xFF0000) >> 16) / 255.0F;
      this.F = (float)((_snowman & 0xFF00) >> 8) / 255.0F;
      this.G = (float)((_snowman & 0xFF) >> 0) / 255.0F;
      this.H = true;
   }

   @Override
   public dyk b() {
      return dyk.c;
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ >= this.t) {
         this.j();
      } else {
         this.b(this.a);
         if (this.s > this.t / 2) {
            this.e(1.0F - ((float)this.s - (float)(this.t / 2)) / (float)this.t);
            if (this.H) {
               this.v = this.v + (this.E - this.v) * 0.2F;
               this.w = this.w + (this.F - this.w) * 0.2F;
               this.x = this.x + (this.G - this.x) * 0.2F;
            }
         }

         this.k = this.k + (double)this.b;
         this.a(this.j, this.k, this.l);
         this.j = this.j * (double)this.D;
         this.k = this.k * (double)this.D;
         this.l = this.l * (double)this.D;
         if (this.m) {
            this.j *= 0.7F;
            this.l *= 0.7F;
         }
      }
   }

   @Override
   public int a(float var1) {
      return 15728880;
   }

   protected void f(float var1) {
      this.D = _snowman;
   }
}
