import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import java.util.Optional;

public class agp extends DataFix {
   public agp(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<String> _snowman = DSL.fieldFinder("id", aln.a());
      return this.fixTypeEverywhereTyped(
         "BlockEntityCustomNameToComponentFix", this.getInputSchema().getType(akn.k), var1x -> var1x.update(DSL.remainderFinder(), var2 -> {
               Optional<String> _snowmanx = var1x.getOptional(_snowman);
               return _snowmanx.isPresent() && Objects.equals(_snowmanx.get(), "minecraft:command_block") ? var2 : ahp.a(var2);
            })
      );
   }
}
