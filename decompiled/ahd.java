import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;

public class ahd extends DataFix {
   public ahd(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.c);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("Level", _snowmanx);
      return this.fixTypeEverywhereTyped(
         "ChunkLightRemoveFix",
         _snowman,
         this.getOutputSchema().getType(akn.c),
         var1x -> var1x.updateTyped(_snowman, var0x -> var0x.update(DSL.remainderFinder(), var0xx -> var0xx.remove("isLightOn")))
      );
   }
}
