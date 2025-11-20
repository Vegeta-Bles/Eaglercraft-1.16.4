package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public abstract class BlockNameFix extends DataFix {
   private final String name;

   public BlockNameFix(Schema oldSchema, String name) {
      super(oldSchema, false);
      this.name = name;
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.BLOCK_NAME);
      Type<Pair<String, String>> _snowmanx = DSL.named(TypeReferences.BLOCK_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType());
      if (!Objects.equals(_snowman, _snowmanx)) {
         throw new IllegalStateException("block type is not what was expected.");
      } else {
         TypeRewriteRule _snowmanxx = this.fixTypeEverywhere(this.name + " for block", _snowmanx, _snowmanxxx -> _snowmanxxxx -> _snowmanxxxx.mapSecond(this::rename));
         TypeRewriteRule _snowmanxxx = this.fixTypeEverywhereTyped(
            this.name + " for block_state", this.getInputSchema().getType(TypeReferences.BLOCK_STATE), _snowmanxxxx -> _snowmanxxxx.update(DSL.remainderFinder(), _snowmanxxxxx -> {
                  Optional<String> _snowmanxxxxxx = _snowmanxxxxx.get("Name").asString().result();
                  return _snowmanxxxxxx.isPresent() ? _snowmanxxxxx.set("Name", _snowmanxxxxx.createString(this.rename(_snowmanxxxxxx.get()))) : _snowmanxxxxx;
               })
         );
         return TypeRewriteRule.seq(_snowmanxx, _snowmanxxx);
      }
   }

   protected abstract String rename(String oldName);

   public static DataFix create(Schema oldSchema, String name, Function<String, String> rename) {
      return new BlockNameFix(oldSchema, name) {
         @Override
         protected String rename(String oldName) {
            return rename.apply(oldName);
         }
      };
   }
}
