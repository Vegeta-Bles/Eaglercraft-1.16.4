import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;

public class ala extends DataFix {
   public ala(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(akn.g);
      Type<?> _snowmanx = this.getInputSchema().getType(akn.g);
      OpticFinder<?> _snowmanxx = _snowmanx.findField("stats");
      OpticFinder<?> _snowmanxxx = _snowmanxx.type().findField("minecraft:custom");
      OpticFinder<String> _snowmanxxxx = aln.a().finder();
      return this.fixTypeEverywhereTyped(
         "SwimStatsRenameFix", _snowmanx, _snowman, var3x -> var3x.updateTyped(_snowman, var2x -> var2x.updateTyped(_snowman, var1x -> var1x.update(_snowman, var0x -> {
                     if (var0x.equals("minecraft:swim_one_cm")) {
                        return "minecraft:walk_on_water_one_cm";
                     } else {
                        return var0x.equals("minecraft:dive_one_cm") ? "minecraft:walk_under_water_one_cm" : var0x;
                     }
                  })))
      );
   }
}
