import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ait extends ajv {
   public ait(Schema var1, String var2) {
      super(_snowman, false, "Gossip for for " + _snowman, akn.p, _snowman);
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(
         DSL.remainderFinder(),
         var0 -> var0.update(
               "Gossips",
               var0x -> (Dynamic)DataFixUtils.orElse(
                     var0x.asStreamOpt()
                        .result()
                        .map(var0xx -> var0xx.map(var0xxx -> (Dynamic)agd.c((Dynamic<?>)var0xxx, "Target", "Target").orElse((Dynamic<?>)var0xxx)))
                        .map(var0x::createList),
                     var0x
                  )
            )
      );
   }
}
