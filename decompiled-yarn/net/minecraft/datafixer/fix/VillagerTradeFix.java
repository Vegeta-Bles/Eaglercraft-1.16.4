package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class VillagerTradeFix extends ChoiceFix {
   public VillagerTradeFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "Villager trade fix", TypeReferences.ENTITY, "minecraft:villager");
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      OpticFinder<?> _snowman = inputType.getType().findField("Offers");
      OpticFinder<?> _snowmanx = _snowman.type().findField("Recipes");
      Type<?> _snowmanxx = _snowmanx.type();
      if (!(_snowmanxx instanceof ListType)) {
         throw new IllegalStateException("Recipes are expected to be a list.");
      } else {
         ListType<?> _snowmanxxx = (ListType<?>)_snowmanxx;
         Type<?> _snowmanxxxx = _snowmanxxx.getElement();
         OpticFinder<?> _snowmanxxxxx = DSL.typeFinder(_snowmanxxxx);
         OpticFinder<?> _snowmanxxxxxx = _snowmanxxxx.findField("buy");
         OpticFinder<?> _snowmanxxxxxxx = _snowmanxxxx.findField("buyB");
         OpticFinder<?> _snowmanxxxxxxxx = _snowmanxxxx.findField("sell");
         OpticFinder<Pair<String, String>> _snowmanxxxxxxxxx = DSL.fieldFinder(
            "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
         );
         Function<Typed<?>, Typed<?>> _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxx -> this.fixPumpkinTrade(_snowman, _snowmanxxxxxxxxxxx);
         return inputType.updateTyped(
            _snowman,
            _snowmanxxxxxxxxxxx -> _snowmanxxxxxxxxxxx.updateTyped(
                  _snowman, _snowmanxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxx.updateTyped(_snowman, _snowmanxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxx.updateTyped(_snowman, _snowman).updateTyped(_snowman, _snowman).updateTyped(_snowman, _snowman))
               )
         );
      }
   }

   private Typed<?> fixPumpkinTrade(OpticFinder<Pair<String, String>> _snowman, Typed<?> _snowman) {
      return _snowman.update(_snowman, _snowmanxx -> _snowmanxx.mapSecond(_snowmanxxx -> Objects.equals(_snowmanxxx, "minecraft:carved_pumpkin") ? "minecraft:pumpkin" : _snowmanxxx));
   }
}
