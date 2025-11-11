public class cxv {
   private final fx a;
   private final int b;
   private final int c;

   public cxv(fx var1, int var2, int var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public static cxv a(md var0) {
      fx _snowman = mp.b(_snowman.p("Pos"));
      int _snowmanx = _snowman.h("Rotation");
      int _snowmanxx = _snowman.h("EntityId");
      return new cxv(_snowman, _snowmanx, _snowmanxx);
   }

   public md a() {
      md _snowman = new md();
      _snowman.a("Pos", mp.a(this.a));
      _snowman.b("Rotation", this.b);
      _snowman.b("EntityId", this.c);
      return _snowman;
   }

   public fx b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public String e() {
      return a(this.a);
   }

   public static String a(fx var0) {
      return "frame-" + _snowman.u() + "," + _snowman.v() + "," + _snowman.w();
   }
}
