public class bft {
   public boolean a;
   public boolean b;
   public boolean c;
   public boolean d;
   public boolean e = true;
   private float f = 0.05F;
   private float g = 0.1F;

   public bft() {
   }

   public void a(md var1) {
      md _snowman = new md();
      _snowman.a("invulnerable", this.a);
      _snowman.a("flying", this.b);
      _snowman.a("mayfly", this.c);
      _snowman.a("instabuild", this.d);
      _snowman.a("mayBuild", this.e);
      _snowman.a("flySpeed", this.f);
      _snowman.a("walkSpeed", this.g);
      _snowman.a("abilities", _snowman);
   }

   public void b(md var1) {
      if (_snowman.c("abilities", 10)) {
         md _snowman = _snowman.p("abilities");
         this.a = _snowman.q("invulnerable");
         this.b = _snowman.q("flying");
         this.c = _snowman.q("mayfly");
         this.d = _snowman.q("instabuild");
         if (_snowman.c("flySpeed", 99)) {
            this.f = _snowman.j("flySpeed");
            this.g = _snowman.j("walkSpeed");
         }

         if (_snowman.c("mayBuild", 1)) {
            this.e = _snowman.q("mayBuild");
         }
      }
   }

   public float a() {
      return this.f;
   }

   public void a(float var1) {
      this.f = _snowman;
   }

   public float b() {
      return this.g;
   }

   public void b(float var1) {
      this.g = _snowman;
   }
}
