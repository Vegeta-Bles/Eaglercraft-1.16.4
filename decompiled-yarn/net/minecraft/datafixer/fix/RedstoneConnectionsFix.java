package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class RedstoneConnectionsFix extends DataFix {
   public RedstoneConnectionsFix(Schema outputSchema) {
      super(outputSchema, false);
   }

   protected TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      return this.fixTypeEverywhereTyped(
         "RedstoneConnectionsFix", _snowman.getType(TypeReferences.BLOCK_STATE), _snowmanx -> _snowmanx.update(DSL.remainderFinder(), this::updateBlockState)
      );
   }

   private <T> Dynamic<T> updateBlockState(Dynamic<T> _snowman) {
      boolean _snowmanx = _snowman.get("Name").asString().result().filter("minecraft:redstone_wire"::equals).isPresent();
      return !_snowmanx
         ? _snowman
         : _snowman.update(
            "Properties",
            _snowmanxx -> {
               String _snowmanx = _snowmanxx.get("east").asString("none");
               String _snowmanxx = _snowmanxx.get("west").asString("none");
               String _snowmanxxx = _snowmanxx.get("north").asString("none");
               String _snowmanxxxx = _snowmanxx.get("south").asString("none");
               boolean _snowmanxxxxx = hasObsoleteValue(_snowmanx) || hasObsoleteValue(_snowmanxx);
               boolean _snowmanxxxxxx = hasObsoleteValue(_snowmanxxx) || hasObsoleteValue(_snowmanxxxx);
               String _snowmanxxxxxxx = !hasObsoleteValue(_snowmanx) && !_snowmanxxxxxx ? "side" : _snowmanx;
               String _snowmanxxxxxxxx = !hasObsoleteValue(_snowmanxx) && !_snowmanxxxxxx ? "side" : _snowmanxx;
               String _snowmanxxxxxxxxx = !hasObsoleteValue(_snowmanxxx) && !_snowmanxxxxx ? "side" : _snowmanxxx;
               String _snowmanxxxxxxxxxxxx = !hasObsoleteValue(_snowmanxxxx) && !_snowmanxxxxx ? "side" : _snowmanxxxx;
               return _snowmanxx.update("east", _snowmanxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxx.createString(_snowmanxxxxx))
                  .update("west", _snowmanxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxx.createString(_snowmanxxxx))
                  .update("north", _snowmanxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxx.createString(_snowmanxxx))
                  .update("south", _snowmanxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxx.createString(_snowmanxx));
            }
         );
   }

   private static boolean hasObsoleteValue(String _snowman) {
      return !"none".equals(_snowman);
   }
}
