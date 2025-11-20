package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class EntityCustomNameToTextFix extends DataFix {
   public EntityCustomNameToTextFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<String> _snowman = DSL.fieldFinder("id", IdentifierNormalizingSchema.getIdentifierType());
      return this.fixTypeEverywhereTyped(
         "EntityCustomNameToComponentFix", this.getInputSchema().getType(TypeReferences.ENTITY), _snowmanx -> _snowmanx.update(DSL.remainderFinder(), _snowmanxxxx -> {
               Optional<String> _snowmanxxx = _snowmanx.getOptional(_snowman);
               return _snowmanxxx.isPresent() && Objects.equals(_snowmanxxx.get(), "minecraft:commandblock_minecart") ? _snowmanxxxx : fixCustomName(_snowmanxxxx);
            })
      );
   }

   public static Dynamic<?> fixCustomName(Dynamic<?> _snowman) {
      String _snowmanx = _snowman.get("CustomName").asString("");
      return _snowmanx.isEmpty() ? _snowman.remove("CustomName") : _snowman.set("CustomName", _snowman.createString(Text.Serializer.toJson(new LiteralText(_snowmanx))));
   }
}
