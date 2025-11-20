package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemStackUuidFix extends AbstractUuidFix {
   public ItemStackUuidFix(Schema outputSchema) {
      super(outputSchema, TypeReferences.ITEM_STACK);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<Pair<String, String>> _snowman = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      return this.fixTypeEverywhereTyped("ItemStackUUIDFix", this.getInputSchema().getType(this.typeReference), _snowmanx -> {
         OpticFinder<?> _snowmanx = _snowmanx.getType().findField("tag");
         return _snowmanx.updateTyped(_snowmanx, _snowmanxx -> _snowmanxx.update(DSL.remainderFinder(), _snowmanxxxxx -> {
               _snowmanxxxxx = this.method_26297(_snowmanxxxxx);
               if (_snowmanx.getOptional(_snowman).map(_snowmanxxxxxxxx -> "minecraft:player_head".equals(_snowmanxxxxxxxx.getSecond())).orElse(false)) {
                  _snowmanxxxxx = this.method_26298(_snowmanxxxxx);
               }

               return _snowmanxxxxx;
            }));
      });
   }

   private Dynamic<?> method_26297(Dynamic<?> _snowman) {
      return _snowman.update("AttributeModifiers", _snowmanxx -> _snowman.createList(_snowmanxx.asStream().map(_snowmanxxx -> (Dynamic)updateRegularMostLeast(_snowmanxxx, "UUID", "UUID").orElse(_snowmanxxx))));
   }

   private Dynamic<?> method_26298(Dynamic<?> _snowman) {
      return _snowman.update("SkullOwner", _snowmanx -> updateStringUuid(_snowmanx, "Id", "Id").orElse(_snowmanx));
   }
}
