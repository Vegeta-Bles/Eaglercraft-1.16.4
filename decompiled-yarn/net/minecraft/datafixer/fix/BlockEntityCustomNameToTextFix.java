package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class BlockEntityCustomNameToTextFix extends DataFix {
   public BlockEntityCustomNameToTextFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<String> _snowman = DSL.fieldFinder("id", IdentifierNormalizingSchema.getIdentifierType());
      return this.fixTypeEverywhereTyped(
         "BlockEntityCustomNameToComponentFix", this.getInputSchema().getType(TypeReferences.BLOCK_ENTITY), _snowmanx -> _snowmanx.update(DSL.remainderFinder(), _snowmanxxxx -> {
               Optional<String> _snowmanxxx = _snowmanx.getOptional(_snowman);
               return _snowmanxxx.isPresent() && Objects.equals(_snowmanxxx.get(), "minecraft:command_block") ? _snowmanxxxx : EntityCustomNameToTextFix.fixCustomName(_snowmanxxxx);
            })
      );
   }
}
