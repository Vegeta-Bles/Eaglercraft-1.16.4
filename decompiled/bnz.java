public class bnz extends bny {
   private final gc b;

   public bnz(brx var1, fx var2, gc var3, bmb var4, gc var5) {
      super(_snowman, null, aot.a, _snowman, new dcj(dcn.c(_snowman), _snowman, _snowman, false));
      this.b = _snowman;
   }

   @Override
   public fx a() {
      return this.i().a();
   }

   @Override
   public boolean b() {
      return this.p().d_(this.i().a()).a(this);
   }

   @Override
   public boolean c() {
      return this.b();
   }

   @Override
   public gc d() {
      return gc.a;
   }

   @Override
   public gc[] e() {
      switch (this.b) {
         case a:
         default:
            return new gc[]{gc.a, gc.c, gc.f, gc.d, gc.e, gc.b};
         case b:
            return new gc[]{gc.a, gc.b, gc.c, gc.f, gc.d, gc.e};
         case c:
            return new gc[]{gc.a, gc.c, gc.f, gc.e, gc.b, gc.d};
         case d:
            return new gc[]{gc.a, gc.d, gc.f, gc.e, gc.b, gc.c};
         case e:
            return new gc[]{gc.a, gc.e, gc.d, gc.b, gc.c, gc.f};
         case f:
            return new gc[]{gc.a, gc.f, gc.d, gc.b, gc.c, gc.e};
      }
   }

   @Override
   public gc f() {
      return this.b.n() == gc.a.b ? gc.c : this.b;
   }

   @Override
   public boolean g() {
      return false;
   }

   @Override
   public float h() {
      return (float)(this.b.d() * 90);
   }
}
