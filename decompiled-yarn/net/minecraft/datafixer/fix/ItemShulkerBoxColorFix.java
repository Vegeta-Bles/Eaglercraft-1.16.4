package net.minecraft.datafixer.fix;

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
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemShulkerBoxColorFix extends DataFix {
   public static final String[] COLORED_SHULKER_BOX_IDS = new String[]{
      "minecraft:white_shulker_box",
      "minecraft:orange_shulker_box",
      "minecraft:magenta_shulker_box",
      "minecraft:light_blue_shulker_box",
      "minecraft:yellow_shulker_box",
      "minecraft:lime_shulker_box",
      "minecraft:pink_shulker_box",
      "minecraft:gray_shulker_box",
      "minecraft:silver_shulker_box",
      "minecraft:cyan_shulker_box",
      "minecraft:purple_shulker_box",
      "minecraft:blue_shulker_box",
      "minecraft:brown_shulker_box",
      "minecraft:green_shulker_box",
      "minecraft:red_shulker_box",
      "minecraft:black_shulker_box"
   };

   public ItemShulkerBoxColorFix(Schema outputSchema, boolean changesType) {
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
         "ItemShulkerBoxColorFix",
         _snowman,
         _snowmanxxxx -> {
            Optional<Pair<String, String>> _snowmanxxxxx = _snowmanxxxx.getOptional(_snowman);
            if (_snowmanxxxxx.isPresent() && Objects.equals(_snowmanxxxxx.get().getSecond(), "minecraft:shulker_box")) {
               Optional<? extends Typed<?>> _snowmanx = _snowmanxxxx.getOptionalTyped(_snowman);
               if (_snowmanx.isPresent()) {
                  Typed<?> _snowmanxx = (Typed<?>)_snowmanx.get();
                  Optional<? extends Typed<?>> _snowmanxxxx = _snowmanxx.getOptionalTyped(_snowman);
                  if (_snowmanxxxx.isPresent()) {
                     Typed<?> _snowmanxxxxx = (Typed<?>)_snowmanxxxx.get();
                     Dynamic<?> _snowmanxxxxxx = (Dynamic<?>)_snowmanxxxxx.get(DSL.remainderFinder());
                     int _snowmanxxxxxxx = _snowmanxxxxxx.get("Color").asInt(0);
                     _snowmanxxxxxx.remove("Color");
                     return _snowmanxxxx.set(_snowman, _snowmanxx.set(_snowman, _snowmanxxxxx.set(DSL.remainderFinder(), _snowmanxxxxxx)))
                        .set(_snowman, Pair.of(TypeReferences.ITEM_NAME.typeName(), COLORED_SHULKER_BOX_IDS[_snowmanxxxxxxx % 16]));
                  }
               }
            }

            return _snowmanxxxx;
         }
      );
   }
}
