package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;

public class MapIdFix extends DataFix {
   public MapIdFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.SAVED_DATA);
      OpticFinder<?> _snowmanx = _snowman.findField("data");
      return this.fixTypeEverywhereTyped("Map id fix", _snowman, _snowmanxx -> {
         Optional<? extends Typed<?>> _snowmanxxx = _snowmanxx.getOptionalTyped(_snowman);
         return _snowmanxxx.isPresent() ? _snowmanxx : _snowmanxx.update(DSL.remainderFinder(), _snowmanxxxx -> _snowmanxxxx.createMap(ImmutableMap.of(_snowmanxxxx.createString("data"), _snowmanxxxx)));
      });
   }
}
