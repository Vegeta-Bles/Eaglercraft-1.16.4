import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alx extends aln {
   public alx(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(_snowman, _snowman, _snowman);
      _snowman.registerType(
         false,
         akn.c,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields(
                  "Entities",
                  DSL.list(akn.o.in(_snowman)),
                  "TileEntities",
                  DSL.list(akn.k.in(_snowman)),
                  "TileTicks",
                  DSL.list(DSL.fields("i", akn.q.in(_snowman))),
                  "Sections",
                  DSL.list(DSL.optionalFields("Palette", DSL.list(akn.m.in(_snowman))))
               )
            )
      );
   }
}
