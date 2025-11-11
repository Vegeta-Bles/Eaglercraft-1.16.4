import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ale extends ajv {
   public ale(Schema var1) {
      super(_snowman, false, "Villager Follow Range Fix", akn.p, "minecraft:villager");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), ale::a);
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      return _snowman.update(
         "Attributes",
         var1 -> _snowman.createList(
               var1.asStream()
                  .map(
                     var0x -> var0x.get("Name").asString("").equals("generic.follow_range") && var0x.get("Base").asDouble(0.0) == 16.0
                           ? var0x.set("Base", var0x.createDouble(48.0))
                           : var0x
                  )
            )
      );
   }
}
