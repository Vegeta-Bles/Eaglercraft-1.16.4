import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;

public class ajr extends DataFix {
   public ajr(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.h);
      OpticFinder<?> _snowmanx = _snowman.findField("data");
      return this.fixTypeEverywhereTyped("Map id fix", _snowman, var1x -> {
         Optional<? extends Typed<?>> _snowmanxx = var1x.getOptionalTyped(_snowman);
         return _snowmanxx.isPresent() ? var1x : var1x.update(DSL.remainderFinder(), var0x -> var0x.createMap(ImmutableMap.of(var0x.createString("data"), var0x)));
      });
   }
}
