import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class akt extends agd {
   public akt(Schema var1) {
      super(_snowman, akn.h);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "SavedDataUUIDFix",
         this.getInputSchema().getType(this.b),
         var0 -> var0.updateTyped(
               var0.getType().findField("data"),
               var0x -> var0x.update(
                     DSL.remainderFinder(),
                     var0xx -> var0xx.update(
                           "Raids",
                           var0xxx -> var0xxx.createList(
                                 var0xxx.asStream()
                                    .map(
                                       var0xxxx -> var0xxxx.update(
                                             "HeroesOfTheVillage",
                                             var0xxxxx -> var0xxxxx.createList(
                                                   var0xxxxx.asStream()
                                                      .map(var0xxxxxx -> (Dynamic)d((Dynamic<?>)var0xxxxxx, "UUIDMost", "UUIDLeast").orElseGet(() -> {
                                                            a.warn("HeroesOfTheVillage contained invalid UUIDs.");
                                                            return (Dynamic<?>)var0xxxxxx;
                                                         }))
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }
}
