package net.minecraft.datafixer.fix;

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
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class NewVillageFix extends DataFix {
   public NewVillageFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      CompoundListType<String, ?> _snowman = DSL.compoundList(DSL.string(), this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE));
      OpticFinder<? extends List<? extends Pair<String, ?>>> _snowmanx = _snowman.finder();
      return this.method_17334(_snowman);
   }

   private <SF> TypeRewriteRule method_17334(CompoundListType<String, SF> _snowman) {
      Type<?> _snowmanx = this.getInputSchema().getType(TypeReferences.CHUNK);
      Type<?> _snowmanxx = this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE);
      OpticFinder<?> _snowmanxxx = _snowmanx.findField("Level");
      OpticFinder<?> _snowmanxxxx = _snowmanxxx.type().findField("Structures");
      OpticFinder<?> _snowmanxxxxx = _snowmanxxxx.type().findField("Starts");
      OpticFinder<List<Pair<String, SF>>> _snowmanxxxxxx = _snowman.finder();
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped(
            "NewVillageFix",
            _snowmanx,
            _snowmanxxxxxxx -> _snowmanxxxxxxx.updateTyped(
                  _snowman,
                  _snowmanxxxxxxxx -> _snowmanxxxxxxxx.updateTyped(
                        _snowman,
                        _snowmanxxxxxxxxxxx -> _snowmanxxxxxxxxxxx.updateTyped(
                                 _snowman,
                                 _snowmanxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxx.update(
                                       _snowman,
                                       _snowmanxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxx.stream()
                                             .filter(_snowmanxxxxxxxxxxxxxxxx -> !Objects.equals(_snowmanxxxxxxxxxxxxxxxx.getFirst(), "Village"))
                                             .map(
                                                _snowmanxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxx.mapFirst(
                                                      _snowmanxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxx.equals("New_Village") ? "Village" : _snowmanxxxxxxxxxxxxxxxxx
                                                   )
                                             )
                                             .collect(Collectors.toList())
                                    )
                              )
                              .update(
                                 DSL.remainderFinder(),
                                 _snowmanxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxx.update(
                                       "References",
                                       _snowmanxxxxxxxxxxxxxx -> {
                                          Optional<? extends Dynamic<?>> _snowmanx = _snowmanxxxxxxxxxxxxxx.get("New_Village").result();
                                          return ((Dynamic)DataFixUtils.orElse(
                                                _snowmanx.map(_snowmanxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxx.remove("New_Village").set("Village", _snowmanxxxxxxxxxxxxxxxxx)),
                                                _snowmanxxxxxxxxxxxxxx
                                             ))
                                             .remove("Village");
                                       }
                                    )
                              )
                     )
               )
         ),
         this.fixTypeEverywhereTyped(
            "NewVillageStartFix",
            _snowmanxx,
            _snowmanxxxxxxx -> _snowmanxxxxxxx.update(
                  DSL.remainderFinder(),
                  _snowmanxxxxxxxx -> _snowmanxxxxxxxx.update(
                        "id",
                        _snowmanxxxxxxxxx -> Objects.equals(IdentifierNormalizingSchema.normalize(_snowmanxxxxxxxxx.asString("")), "minecraft:new_village")
                              ? _snowmanxxxxxxxxx.createString("minecraft:village")
                              : _snowmanxxxxxxxxx
                     )
               )
         )
      );
   }
}
