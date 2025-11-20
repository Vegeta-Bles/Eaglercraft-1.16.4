package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class IglooMetadataRemovalFix extends DataFix {
   public IglooMetadataRemovalFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE);
      Type<?> _snowmanx = this.getOutputSchema().getType(TypeReferences.STRUCTURE_FEATURE);
      return this.writeFixAndRead("IglooMetadataRemovalFix", _snowman, _snowmanx, IglooMetadataRemovalFix::removeMetadata);
   }

   private static <T> Dynamic<T> removeMetadata(Dynamic<T> _snowman) {
      boolean _snowmanx = _snowman.get("Children").asStreamOpt().map(_snowmanxx -> _snowmanxx.allMatch(IglooMetadataRemovalFix::isIgloo)).result().orElse(false);
      return _snowmanx ? _snowman.set("id", _snowman.createString("Igloo")).remove("Children") : _snowman.update("Children", IglooMetadataRemovalFix::removeIgloos);
   }

   private static <T> Dynamic<T> removeIgloos(Dynamic<T> _snowman) {
      return _snowman.asStreamOpt().map(_snowmanx -> _snowmanx.filter(_snowmanxx -> !isIgloo(_snowmanxx))).map(_snowman::createList).result().orElse(_snowman);
   }

   private static boolean isIgloo(Dynamic<?> _snowman) {
      return _snowman.get("id").asString("").equals("Iglu");
   }
}
