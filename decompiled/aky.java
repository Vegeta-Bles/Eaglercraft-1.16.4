import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class aky extends ajv {
   public aky(Schema var1, boolean var2) {
      super(_snowman, _snowman, "StriderGravityFix", akn.p, "minecraft:strider");
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      return _snowman.get("NoGravity").asBoolean(false) ? _snowman.set("NoGravity", _snowman.createBoolean(false)) : _snowman;
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }
}
