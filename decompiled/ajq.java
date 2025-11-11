import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ajq extends agd {
   public ajq(Schema var1) {
      super(_snowman, akn.a);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "LevelUUIDFix",
         this.getInputSchema().getType(this.b),
         var1 -> var1.updateTyped(DSL.remainderFinder(), var1x -> var1x.update(DSL.remainderFinder(), var1xx -> {
                  var1xx = this.d(var1xx);
                  var1xx = this.c(var1xx);
                  return this.b(var1xx);
               }))
      );
   }

   private Dynamic<?> b(Dynamic<?> var1) {
      return a(_snowman, "WanderingTraderId", "WanderingTraderId").orElse(_snowman);
   }

   private Dynamic<?> c(Dynamic<?> var1) {
      return _snowman.update(
         "DimensionData",
         var0 -> var0.updateMapValues(
               var0x -> var0x.mapSecond(var0xx -> var0xx.update("DragonFight", var0xxx -> c(var0xxx, "DragonUUID", "Dragon").orElse(var0xxx)))
            )
      );
   }

   private Dynamic<?> d(Dynamic<?> var1) {
      return _snowman.update(
         "CustomBossEvents",
         var0 -> var0.updateMapValues(
               var0x -> var0x.mapSecond(
                     var0xx -> var0xx.update(
                           "Players", var1x -> var0xx.createList(var1x.asStream().map(var0xxx -> (Dynamic)a((Dynamic<?>)var0xxx).orElseGet(() -> {
                                    a.warn("CustomBossEvents contains invalid UUIDs.");
                                    return (Dynamic<?>)var0xxx;
                                 })))
                        )
                  )
            )
      );
   }
}
