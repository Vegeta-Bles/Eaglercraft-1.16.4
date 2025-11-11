import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList.CompoundListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ajw extends DataFix {
   public ajw(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      CompoundListType<String, ?> _snowman = DSL.compoundList(DSL.string(), this.getInputSchema().getType(akn.t));
      OpticFinder<? extends List<? extends Pair<String, ?>>> _snowmanx = _snowman.finder();
      return this.a(_snowman);
   }

   private <SF> TypeRewriteRule a(CompoundListType<String, SF> var1) {
      Type<?> _snowman = this.getInputSchema().getType(akn.c);
      Type<?> _snowmanx = this.getInputSchema().getType(akn.t);
      OpticFinder<?> _snowmanxx = _snowman.findField("Level");
      OpticFinder<?> _snowmanxxx = _snowmanxx.type().findField("Structures");
      OpticFinder<?> _snowmanxxxx = _snowmanxxx.type().findField("Starts");
      OpticFinder<List<Pair<String, SF>>> _snowmanxxxxx = _snowman.finder();
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped(
            "NewVillageFix",
            _snowman,
            var4x -> var4x.updateTyped(
                  _snowman,
                  var3x -> var3x.updateTyped(
                        _snowman,
                        var2x -> var2x.updateTyped(
                                 _snowman,
                                 var1x -> var1x.update(
                                       _snowman,
                                       var0x -> var0x.stream()
                                             .filter(var0xx -> !Objects.equals(var0xx.getFirst(), "Village"))
                                             .map(var0xx -> var0xx.mapFirst(var0xxx -> var0xxx.equals("New_Village") ? "Village" : var0xxx))
                                             .collect(Collectors.toList())
                                    )
                              )
                              .update(
                                 DSL.remainderFinder(),
                                 var0x -> var0x.update(
                                       "References",
                                       var0xx -> {
                                          Optional<? extends Dynamic<?>> _snowmanxxxxxx = var0xx.get("New_Village").result();
                                          return ((Dynamic)DataFixUtils.orElse(_snowmanxxxxxx.map(var1x -> var0xx.remove("New_Village").set("Village", var1x)), var0xx))
                                             .remove("Village");
                                       }
                                    )
                              )
                     )
               )
         ),
         this.fixTypeEverywhereTyped(
            "NewVillageStartFix",
            _snowmanx,
            var0 -> var0.update(
                  DSL.remainderFinder(),
                  var0x -> var0x.update(
                        "id", var0xx -> Objects.equals(aln.a(var0xx.asString("")), "minecraft:new_village") ? var0xx.createString("minecraft:village") : var0xx
                     )
               )
         )
      );
   }
}
