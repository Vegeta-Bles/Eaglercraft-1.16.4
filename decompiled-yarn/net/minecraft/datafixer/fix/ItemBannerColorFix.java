package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemBannerColorFix extends DataFix {
   public ItemBannerColorFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      OpticFinder<?> _snowmanxxx = _snowmanxx.type().findField("BlockEntityTag");
      return this.fixTypeEverywhereTyped(
         "ItemBannerColorFix",
         _snowman,
         _snowmanxxxx -> {
            Optional<Pair<String, String>> _snowmanxxxxx = _snowmanxxxx.getOptional(_snowman);
            if (_snowmanxxxxx.isPresent() && Objects.equals(_snowmanxxxxx.get().getSecond(), "minecraft:banner")) {
               Dynamic<?> _snowmanx = (Dynamic<?>)_snowmanxxxx.get(DSL.remainderFinder());
               Optional<? extends Typed<?>> _snowmanxx = _snowmanxxxx.getOptionalTyped(_snowman);
               if (_snowmanxx.isPresent()) {
                  Typed<?> _snowmanxxxx = (Typed<?>)_snowmanxx.get();
                  Optional<? extends Typed<?>> _snowmanxxxxx = _snowmanxxxx.getOptionalTyped(_snowman);
                  if (_snowmanxxxxx.isPresent()) {
                     Typed<?> _snowmanxxxxxx = (Typed<?>)_snowmanxxxxx.get();
                     Dynamic<?> _snowmanxxxxxxx = (Dynamic<?>)_snowmanxxxx.get(DSL.remainderFinder());
                     Dynamic<?> _snowmanxxxxxxxx = (Dynamic<?>)_snowmanxxxxxx.getOrCreate(DSL.remainderFinder());
                     if (_snowmanxxxxxxxx.get("Base").asNumber().result().isPresent()) {
                        _snowmanx = _snowmanx.set("Damage", _snowmanx.createShort((short)(_snowmanxxxxxxxx.get("Base").asInt(0) & 15)));
                        Optional<? extends Dynamic<?>> _snowmanxxxxxxxxx = _snowmanxxxxxxx.get("display").result();
                        if (_snowmanxxxxxxxxx.isPresent()) {
                           Dynamic<?> _snowmanxxxxxxxxxx = (Dynamic<?>)_snowmanxxxxxxxxx.get();
                           Dynamic<?> _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.createMap(
                              ImmutableMap.of(_snowmanxxxxxxxxxx.createString("Lore"), _snowmanxxxxxxxxxx.createList(Stream.of(_snowmanxxxxxxxxxx.createString("(+NBT"))))
                           );
                           if (Objects.equals(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx)) {
                              return _snowmanxxxx.set(DSL.remainderFinder(), _snowmanx);
                           }
                        }

                        _snowmanxxxxxxxx.remove("Base");
                        return _snowmanxxxx.set(DSL.remainderFinder(), _snowmanx).set(_snowman, _snowmanxxxx.set(_snowman, _snowmanxxxxxx.set(DSL.remainderFinder(), _snowmanxxxxxxxx)));
                     }
                  }
               }

               return _snowmanxxxx.set(DSL.remainderFinder(), _snowmanx);
            } else {
               return _snowmanxxxx;
            }
         }
      );
   }
}
