import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class akm extends DataFix {
   public akm(Schema var1) {
      super(_snowman, false);
   }

   protected TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      return this.fixTypeEverywhereTyped("RedstoneConnectionsFix", _snowman.getType(akn.m), var1x -> var1x.update(DSL.remainderFinder(), this::a));
   }

   private <T> Dynamic<T> a(Dynamic<T> var1) {
      boolean _snowman = _snowman.get("Name").asString().result().filter("minecraft:redstone_wire"::equals).isPresent();
      return !_snowman
         ? _snowman
         : _snowman.update(
            "Properties",
            var0 -> {
               String _snowmanx = var0.get("east").asString("none");
               String _snowmanx = var0.get("west").asString("none");
               String _snowmanxx = var0.get("north").asString("none");
               String _snowmanxxx = var0.get("south").asString("none");
               boolean _snowmanxxxx = a(_snowmanx) || a(_snowmanx);
               boolean _snowmanxxxxx = a(_snowmanxx) || a(_snowmanxxx);
               String _snowmanxxxxxx = !a(_snowmanx) && !_snowmanxxxxx ? "side" : _snowmanx;
               String _snowmanxxxxxxx = !a(_snowmanx) && !_snowmanxxxxx ? "side" : _snowmanx;
               String _snowmanxxxxxxxx = !a(_snowmanxx) && !_snowmanxxxx ? "side" : _snowmanxx;
               String _snowmanxxxxxxxxx = !a(_snowmanxxx) && !_snowmanxxxx ? "side" : _snowmanxxx;
               return var0.update("east", var1x -> var1x.createString(_snowman))
                  .update("west", var1x -> var1x.createString(_snowman))
                  .update("north", var1x -> var1x.createString(_snowman))
                  .update("south", var1x -> var1x.createString(_snowman));
            }
         );
   }

   private static boolean a(String var0) {
      return !"none".equals(_snowman);
   }
}
