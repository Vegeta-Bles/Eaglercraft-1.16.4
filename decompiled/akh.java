import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public class akh extends agd {
   public akh(Schema var1) {
      super(_snowman, akn.b);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "PlayerUUIDFix",
         this.getInputSchema().getType(this.b),
         var0 -> {
            OpticFinder<?> _snowman = var0.getType().findField("RootVehicle");
            return var0.updateTyped(_snowman, _snowman.type(), var0x -> var0x.update(DSL.remainderFinder(), var0xx -> c(var0xx, "Attach", "Attach").orElse(var0xx)))
               .update(DSL.remainderFinder(), var0x -> aim.c(aim.b(var0x)));
         }
      );
   }
}
