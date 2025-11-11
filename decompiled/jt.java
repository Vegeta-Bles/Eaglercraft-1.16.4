import java.nio.file.Path;

public class jt extends jw<aqe<?>> {
   public jt(hl var1) {
      super(_snowman, gm.S);
   }

   @Override
   protected void b() {
      this.a(aee.b).a(aqe.av, aqe.aE, aqe.aU);
      this.a(aee.c).a(aqe.w, aqe.ak, aqe.ap, aqe.aQ, aqe.J, aqe.aS);
      this.a(aee.d).a(aqe.e);
      this.a(aee.e).a(aqe.c, aqe.aB);
      this.a(aee.f).a(aee.e).a(aqe.aA, aqe.N, aqe.ay, aqe.aG, aqe.aK, aqe.p, aqe.aV);
   }

   @Override
   protected Path a(vk var1) {
      return this.b.b().resolve("data/" + _snowman.b() + "/tags/entity_types/" + _snowman.a() + ".json");
   }

   @Override
   public String a() {
      return "Entity Type Tags";
   }
}
