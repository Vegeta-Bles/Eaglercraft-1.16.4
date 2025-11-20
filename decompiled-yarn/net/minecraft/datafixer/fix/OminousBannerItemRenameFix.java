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

public class OminousBannerItemRenameFix extends DataFix {
   public OminousBannerItemRenameFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   private Dynamic<?> fixBannerName(Dynamic<?> _snowman) {
      Optional<? extends Dynamic<?>> _snowmanx = _snowman.get("display").result();
      if (_snowmanx.isPresent()) {
         Dynamic<?> _snowmanxx = (Dynamic<?>)_snowmanx.get();
         Optional<String> _snowmanxxx = _snowmanxx.get("Name").asString().result();
         if (_snowmanxxx.isPresent()) {
            String _snowmanxxxx = _snowmanxxx.get();
            _snowmanxxxx = _snowmanxxxx.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
            _snowmanxx = _snowmanxx.set("Name", _snowmanxx.createString(_snowmanxxxx));
         }

         return _snowman.set("display", _snowmanxx);
      } else {
         return _snowman;
      }
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped("OminousBannerRenameFix", _snowman, _snowmanxxx -> {
         Optional<Pair<String, String>> _snowmanxxxx = _snowmanxxx.getOptional(_snowman);
         if (_snowmanxxxx.isPresent() && Objects.equals(_snowmanxxxx.get().getSecond(), "minecraft:white_banner")) {
            Optional<? extends Typed<?>> _snowmanx = _snowmanxxx.getOptionalTyped(_snowman);
            if (_snowmanx.isPresent()) {
               Typed<?> _snowmanxxx = (Typed<?>)_snowmanx.get();
               Dynamic<?> _snowmanxxxx = (Dynamic<?>)_snowmanxxx.get(DSL.remainderFinder());
               return _snowmanxxx.set(_snowman, _snowmanxxx.set(DSL.remainderFinder(), this.fixBannerName(_snowmanxxxx)));
            }
         }

         return _snowmanxxx;
      });
   }
}
