package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public abstract class EntityRenameFix extends DataFix {
   private final String name;

   public EntityRenameFix(String name, Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
      this.name = name;
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> _snowman = this.getInputSchema().findChoiceType(TypeReferences.ENTITY);
      TaggedChoiceType<String> _snowmanx = this.getOutputSchema().findChoiceType(TypeReferences.ENTITY);
      Type<Pair<String, String>> _snowmanxx = DSL.named(TypeReferences.ENTITY_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType());
      if (!Objects.equals(this.getOutputSchema().getType(TypeReferences.ENTITY_NAME), _snowmanxx)) {
         throw new IllegalStateException("Entity name type is not what was expected.");
      } else {
         return TypeRewriteRule.seq(this.fixTypeEverywhere(this.name, _snowman, _snowmanx, _snowmanxxx -> _snowmanxxxx -> _snowmanxxxx.mapFirst(_snowmanxxxxxxxxx -> {
                  String _snowmanxxx = this.rename(_snowmanxxxxxxxxx);
                  Type<?> _snowmanxxxx = (Type<?>)_snowman.types().get(_snowmanxxxxxxxxx);
                  Type<?> _snowmanxxxxx = (Type<?>)_snowman.types().get(_snowmanxxx);
                  if (!_snowmanxxxxx.equals(_snowmanxxxx, true, true)) {
                     throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", _snowmanxxxxx, _snowmanxxxx));
                  } else {
                     return _snowmanxxx;
                  }
               })), this.fixTypeEverywhere(this.name + " for entity name", _snowmanxx, _snowmanxxx -> _snowmanxxxx -> _snowmanxxxx.mapSecond(this::rename)));
      }
   }

   protected abstract String rename(String oldName);
}
