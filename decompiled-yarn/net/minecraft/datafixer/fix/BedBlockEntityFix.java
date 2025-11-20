package net.minecraft.datafixer.fix;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;

public class BedBlockEntityFix extends DataFix {
   public BedBlockEntityFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(TypeReferences.CHUNK);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      Type<?> _snowmanxx = _snowmanx.findFieldType("TileEntities");
      if (!(_snowmanxx instanceof ListType)) {
         throw new IllegalStateException("Tile entity type is not a list type.");
      } else {
         ListType<?> _snowmanxxx = (ListType<?>)_snowmanxx;
         return this.method_15506(_snowmanx, _snowmanxxx);
      }
   }

   private <TE> TypeRewriteRule method_15506(Type<?> _snowman, ListType<TE> _snowman) {
      Type<TE> _snowmanxx = _snowman.getElement();
      OpticFinder<?> _snowmanxxx = DSL.fieldFinder("Level", _snowman);
      OpticFinder<List<TE>> _snowmanxxxx = DSL.fieldFinder("TileEntities", _snowman);
      int _snowmanxxxxx = 416;
      return TypeRewriteRule.seq(
         this.fixTypeEverywhere(
            "InjectBedBlockEntityType",
            this.getInputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY),
            this.getOutputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY),
            _snowmanxxxxxx -> _snowmanxxxxxxx -> _snowmanxxxxxxx
         ),
         this.fixTypeEverywhereTyped(
            "BedBlockEntityInjecter",
            this.getOutputSchema().getType(TypeReferences.CHUNK),
            _snowmanxxxxxxxxxxxxxxxxxxx -> {
               Typed<?> _snowmanxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.getTyped(_snowman);
               Dynamic<?> _snowmanxxxxx = (Dynamic<?>)_snowmanxxxx.get(DSL.remainderFinder());
               int _snowmanxxxxxx = _snowmanxxxxx.get("xPos").asInt(0);
               int _snowmanxxxxxxx = _snowmanxxxxx.get("zPos").asInt(0);
               List<TE> _snowmanxxxxxxxx = Lists.newArrayList((Iterable)_snowmanxxxx.getOrCreate(_snowman));
               List<? extends Dynamic<?>> _snowmanxxxxxxxxx = _snowmanxxxxx.get("Sections").asList(Function.identity());

               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowmanxxxxxxxxx.size(); _snowmanxxxxxxxxxx++) {
                  Dynamic<?> _snowmanxxxxxxxxxxx = (Dynamic<?>)_snowmanxxxxxxxxx.get(_snowmanxxxxxxxxxx);
                  int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.get("Y").asInt(0);
                  Stream<Integer> _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.get("Blocks").asStream().map(_snowmanxxxxxxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxxxxxxx.asInt(0));
                  int _snowmanxxxxxxxxxxxxxx = 0;

                  for (int _snowmanxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxx::iterator) {
                     if (416 == (_snowmanxxxxxxxxxxxxxxx & 0xFF) << 4) {
                        int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx & 15;
                        int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx >> 8 & 15;
                        int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx >> 4 & 15;
                        Map<Dynamic<?>, Dynamic<?>> _snowmanxxxxxxxxxxxxxxxxxxxxx = Maps.newHashMap();
                        _snowmanxxxxxxxxxxxxxxxxxxxxx.put(_snowmanxxxxxxxxxxx.createString("id"), _snowmanxxxxxxxxxxx.createString("minecraft:bed"));
                        _snowmanxxxxxxxxxxxxxxxxxxxxx.put(_snowmanxxxxxxxxxxx.createString("x"), _snowmanxxxxxxxxxxx.createInt(_snowmanxxxxxxxxxxxxxxxx + (_snowmanxxxxxx << 4)));
                        _snowmanxxxxxxxxxxxxxxxxxxxxx.put(_snowmanxxxxxxxxxxx.createString("y"), _snowmanxxxxxxxxxxx.createInt(_snowmanxxxxxxxxxxxxxxxxxxx + (_snowmanxxxxxxxxxxxx << 4)));
                        _snowmanxxxxxxxxxxxxxxxxxxxxx.put(_snowmanxxxxxxxxxxx.createString("z"), _snowmanxxxxxxxxxxx.createInt(_snowmanxxxxxxxxxxxxxxxxxxxx + (_snowmanxxxxxxx << 4)));
                        _snowmanxxxxxxxxxxxxxxxxxxxxx.put(_snowmanxxxxxxxxxxx.createString("color"), _snowmanxxxxxxxxxxx.createShort((short)14));
                        _snowmanxxxxxxxx.add(
                           (TE)((Pair)_snowman.read(_snowmanxxxxxxxxxxx.createMap(_snowmanxxxxxxxxxxxxxxxxxxxxx))
                                 .result()
                                 .orElseThrow(() -> new IllegalStateException("Could not parse newly created bed block entity.")))
                              .getFirst()
                        );
                     }

                     _snowmanxxxxxxxxxxxxxx++;
                  }
               }

               return !_snowmanxxxxxxxx.isEmpty() ? _snowmanxxxxxxxxxxxxxxxxxxx.set(_snowman, _snowmanxxxx.set(_snowman, _snowmanxxxxxxxx)) : _snowmanxxxxxxxxxxxxxxxxxxx;
            }
         )
      );
   }
}
