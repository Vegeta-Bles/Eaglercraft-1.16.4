import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class ams extends aln {
   public ams(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerEntities(_snowman);
      _snowman.register(
         _snowman,
         "minecraft:wandering_trader",
         var1x -> DSL.optionalFields(
               "Inventory",
               DSL.list(akn.l.in(_snowman)),
               "Offers",
               DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", akn.l.in(_snowman), "buyB", akn.l.in(_snowman), "sell", akn.l.in(_snowman)))),
               alo.a(_snowman)
            )
      );
      _snowman.register(
         _snowman,
         "minecraft:trader_llama",
         var1x -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "SaddleItem", akn.l.in(_snowman), "DecorItem", akn.l.in(_snowman), alo.a(_snowman))
      );
      return _snowman;
   }
}
