import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amc extends aln {
   public amc(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(_snowman, _snowman, _snowman);
      Supplier<TypeTemplate> _snowman = () -> DSL.compoundList(akn.r.in(_snowman), DSL.constType(DSL.intType()));
      _snowman.registerType(
         false,
         akn.g,
         () -> DSL.optionalFields(
               "stats",
               DSL.optionalFields(
                  "minecraft:mined",
                  DSL.compoundList(akn.q.in(_snowman), DSL.constType(DSL.intType())),
                  "minecraft:crafted",
                  _snowman.get(),
                  "minecraft:used",
                  _snowman.get(),
                  "minecraft:broken",
                  _snowman.get(),
                  "minecraft:picked_up",
                  _snowman.get(),
                  DSL.optionalFields(
                     "minecraft:dropped",
                     _snowman.get(),
                     "minecraft:killed",
                     DSL.compoundList(akn.n.in(_snowman), DSL.constType(DSL.intType())),
                     "minecraft:killed_by",
                     DSL.compoundList(akn.n.in(_snowman), DSL.constType(DSL.intType())),
                     "minecraft:custom",
                     DSL.compoundList(DSL.constType(a()), DSL.constType(DSL.intType()))
                  )
               )
            )
      );
   }
}
