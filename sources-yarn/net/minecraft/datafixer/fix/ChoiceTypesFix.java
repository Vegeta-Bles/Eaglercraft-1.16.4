package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;

public class ChoiceTypesFix extends DataFix {
   private final String name;
   private final TypeReference types;

   public ChoiceTypesFix(Schema outputSchema, String name, TypeReference types) {
      super(outputSchema, true);
      this.name = name;
      this.types = types;
   }

   @SuppressWarnings("unchecked")
   public TypeRewriteRule makeRule() {
      TaggedChoiceType<Object> taggedChoiceType = (TaggedChoiceType<Object>)this.getInputSchema().findChoiceType(this.types);
      TaggedChoiceType<Object> taggedChoiceType2 = (TaggedChoiceType<Object>)this.getOutputSchema().findChoiceType(this.types);
      return this.fixChoiceTypes(this.name, taggedChoiceType, taggedChoiceType2);
   }

   protected final <K> TypeRewriteRule fixChoiceTypes(String name, TaggedChoiceType<K> inputChoiceType, TaggedChoiceType<K> outputChoiceType) {
      if (inputChoiceType.getKeyType() != outputChoiceType.getKeyType()) {
         throw new IllegalStateException("Could not inject: key type is not the same");
      } else {
         return this.fixTypeEverywhere(name, inputChoiceType, outputChoiceType, dynamicOps -> pair -> {
               if (!outputChoiceType.hasType(pair.getFirst())) {
                  throw new IllegalArgumentException(String.format("Unknown type %s in %s ", pair.getFirst(), this.types));
               } else {
                  return pair;
               }
            });
      }
   }
}
