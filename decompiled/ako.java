import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ako extends ajv {
   public ako(Schema var1, boolean var2) {
      super(_snowman, _snowman, "Remove Golem Gossip Fix", akn.p, "minecraft:villager");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), ako::a);
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      return _snowman.update("Gossips", var1 -> _snowman.createList(var1.asStream().filter(var0x -> !var0x.get("Type").asString("").equals("golem"))));
   }
}
