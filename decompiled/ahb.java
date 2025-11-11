import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ahb extends ajv {
   public ahb(Schema var1, boolean var2) {
      super(_snowman, _snowman, "CatTypeFix", akn.p, "minecraft:cat");
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      return _snowman.get("CatType").asInt(0) == 9 ? _snowman.set("CatType", _snowman.createInt(10)) : _snowman;
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }
}
