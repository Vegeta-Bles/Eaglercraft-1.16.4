package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class BlockNameFlatteningFix extends DataFix {
   public BlockNameFlatteningFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.BLOCK_NAME);
      Type<?> _snowmanx = this.getOutputSchema().getType(TypeReferences.BLOCK_NAME);
      Type<Pair<String, Either<Integer, String>>> _snowmanxx = DSL.named(
         TypeReferences.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), IdentifierNormalizingSchema.getIdentifierType())
      );
      Type<Pair<String, String>> _snowmanxxx = DSL.named(TypeReferences.BLOCK_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType());
      if (Objects.equals(_snowman, _snowmanxx) && Objects.equals(_snowmanx, _snowmanxxx)) {
         return this.fixTypeEverywhere(
            "BlockNameFlatteningFix",
            _snowmanxx,
            _snowmanxxx,
            _snowmanxxxx -> _snowmanxxxxx -> _snowmanxxxxx.mapSecond(
                     _snowmanxxxxxx -> (String)_snowmanxxxxxx.map(
                           BlockStateFlattening::lookupStateBlock,
                           _snowmanxxxxxxx -> BlockStateFlattening.lookupBlock(IdentifierNormalizingSchema.normalize(_snowmanxxxxxxx))
                        )
                  )
         );
      } else {
         throw new IllegalStateException("Expected and actual types don't match.");
      }
   }
}
