package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class EntityHorseSaddleFix extends ChoiceFix {
   public EntityHorseSaddleFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "EntityHorseSaddleFix", TypeReferences.ENTITY, "EntityHorse");
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      OpticFinder<Pair<String, String>> _snowman = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      Type<?> _snowmanx = this.getInputSchema().getTypeRaw(TypeReferences.ITEM_STACK);
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("SaddleItem", _snowmanx);
      Optional<? extends Typed<?>> _snowmanxxx = inputType.getOptionalTyped(_snowmanxx);
      Dynamic<?> _snowmanxxxx = (Dynamic<?>)inputType.get(DSL.remainderFinder());
      if (!_snowmanxxx.isPresent() && _snowmanxxxx.get("Saddle").asBoolean(false)) {
         Typed<?> _snowmanxxxxx = (Typed<?>)_snowmanx.pointTyped(inputType.getOps()).orElseThrow(IllegalStateException::new);
         _snowmanxxxxx = _snowmanxxxxx.set(_snowman, Pair.of(TypeReferences.ITEM_NAME.typeName(), "minecraft:saddle"));
         Dynamic<?> _snowmanxxxxxx = _snowmanxxxx.emptyMap();
         _snowmanxxxxxx = _snowmanxxxxxx.set("Count", _snowmanxxxxxx.createByte((byte)1));
         _snowmanxxxxxx = _snowmanxxxxxx.set("Damage", _snowmanxxxxxx.createShort((short)0));
         _snowmanxxxxx = _snowmanxxxxx.set(DSL.remainderFinder(), _snowmanxxxxxx);
         _snowmanxxxx.remove("Saddle");
         return inputType.set(_snowmanxx, _snowmanxxxxx).set(DSL.remainderFinder(), _snowmanxxxx);
      } else {
         return inputType;
      }
   }
}
