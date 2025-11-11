import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.OptionalDynamic;
import java.util.List;

public class aid extends DataFix {
   private static final Codec<List<Float>> a = Codec.FLOAT.listOf();

   public aid(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityRedundantChanceTagsFix", this.getInputSchema().getType(akn.p), var0 -> var0.update(DSL.remainderFinder(), var0x -> {
               if (a(var0x.get("HandDropChances"), 2)) {
                  var0x = var0x.remove("HandDropChances");
               }

               if (a(var0x.get("ArmorDropChances"), 4)) {
                  var0x = var0x.remove("ArmorDropChances");
               }

               return var0x;
            })
      );
   }

   private static boolean a(OptionalDynamic<?> var0, int var1) {
      return _snowman.flatMap(a::parse).map(var1x -> var1x.size() == _snowman && var1x.stream().allMatch(var0x -> var0x == 0.0F)).result().orElse(false);
   }
}
