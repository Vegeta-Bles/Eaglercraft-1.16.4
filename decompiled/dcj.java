public class dcj extends dcl {
   private final gc b;
   private final fx c;
   private final boolean d;
   private final boolean e;

   public static dcj a(dcn var0, gc var1, fx var2) {
      return new dcj(true, _snowman, _snowman, _snowman, false);
   }

   public dcj(dcn var1, gc var2, fx var3, boolean var4) {
      this(false, _snowman, _snowman, _snowman, _snowman);
   }

   private dcj(boolean var1, dcn var2, gc var3, fx var4, boolean var5) {
      super(_snowman);
      this.d = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.e = _snowman;
   }

   public dcj a(gc var1) {
      return new dcj(this.d, this.a, _snowman, this.c, this.e);
   }

   public dcj a(fx var1) {
      return new dcj(this.d, this.a, this.b, _snowman, this.e);
   }

   public fx a() {
      return this.c;
   }

   public gc b() {
      return this.b;
   }

   @Override
   public dcl.a c() {
      return this.d ? dcl.a.a : dcl.a.b;
   }

   public boolean d() {
      return this.e;
   }
}
