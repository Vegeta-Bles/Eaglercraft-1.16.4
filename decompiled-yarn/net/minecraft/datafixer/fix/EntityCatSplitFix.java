package net.minecraft.datafixer.fix;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class EntityCatSplitFix extends EntitySimpleTransformFix {
   public EntityCatSplitFix(Schema outputSchema, boolean changesType) {
      super("EntityCatSplitFix", outputSchema, changesType);
   }

   @Override
   protected Pair<String, Dynamic<?>> transform(String choice, Dynamic<?> _snowman) {
      if (Objects.equals("minecraft:ocelot", choice)) {
         int _snowmanx = _snowman.get("CatType").asInt(0);
         if (_snowmanx == 0) {
            String _snowmanxx = _snowman.get("Owner").asString("");
            String _snowmanxxx = _snowman.get("OwnerUUID").asString("");
            if (_snowmanxx.length() > 0 || _snowmanxxx.length() > 0) {
               _snowman.set("Trusting", _snowman.createBoolean(true));
            }
         } else if (_snowmanx > 0 && _snowmanx < 4) {
            _snowman = _snowman.set("CatType", _snowman.createInt(_snowmanx));
            _snowman = _snowman.set("OwnerUUID", _snowman.createString(_snowman.get("OwnerUUID").asString("")));
            return Pair.of("minecraft:cat", _snowman);
         }
      }

      return Pair.of(choice, _snowman);
   }
}
