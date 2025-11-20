package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import net.minecraft.datafixer.TypeReferences;

public abstract class EntityTransformFix extends DataFix {
   protected final String name;

   public EntityTransformFix(String name, Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
      this.name = name;
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> _snowman = this.getInputSchema().findChoiceType(TypeReferences.ENTITY);
      TaggedChoiceType<String> _snowmanx = this.getOutputSchema().findChoiceType(TypeReferences.ENTITY);
      return this.fixTypeEverywhere(
         this.name,
         _snowman,
         _snowmanx,
         _snowmanxx -> _snowmanxxxxxxx -> {
               String _snowmanxxxx = (String)_snowmanxxxxxxx.getFirst();
               Type<?> _snowmanxxxxx = (Type<?>)_snowman.types().get(_snowmanxxxx);
               Pair<String, Typed<?>> _snowmanxxxxxx = this.transform(_snowmanxxxx, this.makeTyped(_snowmanxxxxxxx.getSecond(), _snowmanxx, _snowmanxxxxx));
               Type<?> _snowmanxxxxxxx = (Type<?>)_snowman.types().get(_snowmanxxxxxx.getFirst());
               if (!_snowmanxxxxxxx.equals(((Typed)_snowmanxxxxxx.getSecond()).getType(), true, true)) {
                  throw new IllegalStateException(
                     String.format("Dynamic type check failed: %s not equal to %s", _snowmanxxxxxxx, ((Typed)_snowmanxxxxxx.getSecond()).getType())
                  );
               } else {
                  return Pair.of(_snowmanxxxxxx.getFirst(), ((Typed)_snowmanxxxxxx.getSecond()).getValue());
               }
            }
      );
   }

   private <A> Typed<A> makeTyped(Object _snowman, DynamicOps<?> _snowman, Type<A> _snowman) {
      return new Typed(_snowman, _snowman, _snowman);
   }

   protected abstract Pair<String, Typed<?>> transform(String choice, Typed<?> var2);
}
