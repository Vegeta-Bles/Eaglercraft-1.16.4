public interface dec extends dea {
   int d();

   int e();

   void a(int var1, int var2);

   boolean f();

   float g();

   default float h() {
      return this.a();
   }

   default float i() {
      return this.h() + (float)this.d() / this.g();
   }

   default float j() {
      return this.l();
   }

   default float k() {
      return this.j() + (float)this.e() / this.g();
   }

   default float l() {
      return 3.0F;
   }
}
