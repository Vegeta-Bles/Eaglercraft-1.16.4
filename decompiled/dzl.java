public class dzl extends dzk {
   private final dkd i;

   public dzl(dkd var1) {
      this.i = _snowman;
   }

   @Override
   public void a(boolean var1) {
      this.c = this.i.af.d();
      this.d = this.i.ah.d();
      this.e = this.i.ag.d();
      this.f = this.i.ai.d();
      this.b = this.c == this.d ? 0.0F : (this.c ? 1.0F : -1.0F);
      this.a = this.e == this.f ? 0.0F : (this.e ? 1.0F : -1.0F);
      this.g = this.i.aj.d();
      this.h = this.i.ak.d();
      if (_snowman) {
         this.a = (float)((double)this.a * 0.3);
         this.b = (float)((double)this.b * 0.3);
      }
   }
}
