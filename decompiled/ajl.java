import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ajl extends ajv {
   public ajl(Schema var1, boolean var2) {
      super(_snowman, _snowman, "JigsawPropertiesFix", akn.k, "minecraft:jigsaw");
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      String _snowman = _snowman.get("attachement_type").asString("minecraft:empty");
      String _snowmanx = _snowman.get("target_pool").asString("minecraft:empty");
      return _snowman.set("name", _snowman.createString(_snowman)).set("target", _snowman.createString(_snowman)).remove("attachement_type").set("pool", _snowman.createString(_snowmanx)).remove("target_pool");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), ajl::a);
   }
}
