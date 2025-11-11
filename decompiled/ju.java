import java.nio.file.Path;

public class ju extends jw<cuw> {
   public ju(hl var1) {
      super(_snowman, gm.O);
   }

   @Override
   protected void b() {
      this.a(aef.b).a(cuy.c, cuy.b);
      this.a(aef.c).a(cuy.e, cuy.d);
   }

   @Override
   protected Path a(vk var1) {
      return this.b.b().resolve("data/" + _snowman.b() + "/tags/fluids/" + _snowman.a() + ".json");
   }

   @Override
   public String a() {
      return "Fluid Tags";
   }
}
