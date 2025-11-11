import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class agv extends agd {
   public agv(Schema var1) {
      super(_snowman, akn.k);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("BlockEntityUUIDFix", this.getInputSchema().getType(this.b), var1 -> {
         var1 = this.a(var1, "minecraft:conduit", this::c);
         return this.a(var1, "minecraft:skull", this::b);
      });
   }

   private Dynamic<?> b(Dynamic<?> var1) {
      return _snowman.get("Owner").get().map(var0 -> a(var0, "Id", "Id").orElse(var0)).map(var1x -> _snowman.remove("Owner").set("SkullOwner", var1x)).result().orElse(_snowman);
   }

   private Dynamic<?> c(Dynamic<?> var1) {
      return b(_snowman, "target_uuid", "Target").orElse(_snowman);
   }
}
