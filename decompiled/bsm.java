public class bsm extends afz.a {
   private final md b;

   public bsm() {
      super(1);
      this.b = new md();
      this.b.a("id", "minecraft:pig");
   }

   public bsm(md var1) {
      this(_snowman.c("Weight", 99) ? _snowman.h("Weight") : 1, _snowman.p("Entity"));
   }

   public bsm(int var1, md var2) {
      super(_snowman);
      this.b = _snowman;
      vk _snowman = vk.a(_snowman.l("id"));
      if (_snowman != null) {
         _snowman.a("id", _snowman.toString());
      } else {
         _snowman.a("id", "minecraft:pig");
      }
   }

   public md a() {
      md _snowman = new md();
      _snowman.a("Entity", this.b);
      _snowman.b("Weight", this.a);
      return _snowman;
   }

   public md b() {
      return this.b;
   }
}
