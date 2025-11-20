package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public abstract class ItemNameFix extends DataFix {
   private final String name;

   public ItemNameFix(Schema outputSchema, String name) {
      super(outputSchema, false);
      this.name = name;
   }

   public TypeRewriteRule makeRule() {
      Type<Pair<String, String>> _snowman = DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType());
      if (!Objects.equals(this.getInputSchema().getType(TypeReferences.ITEM_NAME), _snowman)) {
         throw new IllegalStateException("item name type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.name, _snowman, _snowmanx -> _snowmanxx -> _snowmanxx.mapSecond(this::rename));
      }
   }

   protected abstract String rename(String input);

   public static DataFix create(Schema outputSchema, String name, Function<String, String> rename) {
      return new ItemNameFix(outputSchema, name) {
         @Override
         protected String rename(String input) {
            return rename.apply(input);
         }
      };
   }
}
