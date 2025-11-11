import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amf extends aln {
   public amf(int var1, Schema var2) {
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
                  DSL.list(DSL.optionalFields("Palette", DSL.list(akn.m.in(_snowman)))),
                  "Structures",
                  DSL.optionalFields("Starts", DSL.compoundList(akn.t.in(_snowman)))
               )
            )
      );
      _snowman.registerType(
         false,
         akn.t,
         () -> DSL.optionalFields(
               "Children", DSL.list(DSL.optionalFields("CA", akn.m.in(_snowman), "CB", akn.m.in(_snowman), "CC", akn.m.in(_snowman), "CD", akn.m.in(_snowman))), "biome", akn.x.in(_snowman)
            )
      );
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerBlockEntities(_snowman);
      _snowman.put("DUMMY", DSL::remainder);
      return _snowman;
   }
}
