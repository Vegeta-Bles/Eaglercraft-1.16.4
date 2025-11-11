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

public class agi extends DataFix {
   public agi(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(akn.c);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      Type<?> _snowmanxx = _snowmanx.findFieldType("TileEntities");
      if (!(_snowmanxx instanceof ListType)) {
         throw new IllegalStateException("Tile entity type is not a list type.");
      } else {
         ListType<?> _snowmanxxx = (ListType<?>)_snowmanxx;
         return this.a(_snowmanx, _snowmanxxx);
      }
   }

   private <TE> TypeRewriteRule a(Type<?> var1, ListType<TE> var2) {
      Type<TE> _snowman = _snowman.getElement();
      OpticFinder<?> _snowmanx = DSL.fieldFinder("Level", _snowman);
      OpticFinder<List<TE>> _snowmanxx = DSL.fieldFinder("TileEntities", _snowman);
      int _snowmanxxx = 416;
      return TypeRewriteRule.seq(
         this.fixTypeEverywhere(
            "InjectBedBlockEntityType", this.getInputSchema().findChoiceType(akn.k), this.getOutputSchema().findChoiceType(akn.k), var0 -> var0x -> var0x
         ),
         this.fixTypeEverywhereTyped(
            "BedBlockEntityInjecter",
            this.getOutputSchema().getType(akn.c),
            var3x -> {
               Typed<?> _snowmanxxxx = var3x.getTyped(_snowman);
               Dynamic<?> _snowmanx = (Dynamic<?>)_snowmanxxxx.get(DSL.remainderFinder());
               int _snowmanxx = _snowmanx.get("xPos").asInt(0);
               int _snowmanxxx = _snowmanx.get("zPos").asInt(0);
               List<TE> _snowmanxxxx = Lists.newArrayList((Iterable)_snowmanxxxx.getOrCreate(_snowman));
               List<? extends Dynamic<?>> _snowmanxxxxx = _snowmanx.get("Sections").asList(Function.identity());

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.size(); _snowmanxxxxxx++) {
                  Dynamic<?> _snowmanxxxxxxx = (Dynamic<?>)_snowmanxxxxx.get(_snowmanxxxxxx);
                  int _snowmanxxxxxxxx = _snowmanxxxxxxx.get("Y").asInt(0);
                  Stream<Integer> _snowmanxxxxxxxxx = _snowmanxxxxxxx.get("Blocks").asStream().map(var0x -> var0x.asInt(0));
                  int _snowmanxxxxxxxxxx = 0;

                  for (int _snowmanxxxxxxxxxxx : _snowmanxxxxxxxxx::iterator) {
                     if (416 == (_snowmanxxxxxxxxxxx & 0xFF) << 4) {
                        int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx & 15;
                        int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx >> 8 & 15;
                        int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx >> 4 & 15;
                        Map<Dynamic<?>, Dynamic<?>> _snowmanxxxxxxxxxxxxxxx = Maps.newHashMap();
                        _snowmanxxxxxxxxxxxxxxx.put(_snowmanxxxxxxx.createString("id"), _snowmanxxxxxxx.createString("minecraft:bed"));
                        _snowmanxxxxxxxxxxxxxxx.put(_snowmanxxxxxxx.createString("x"), _snowmanxxxxxxx.createInt(_snowmanxxxxxxxxxxxx + (_snowmanxx << 4)));
                        _snowmanxxxxxxxxxxxxxxx.put(_snowmanxxxxxxx.createString("y"), _snowmanxxxxxxx.createInt(_snowmanxxxxxxxxxxxxx + (_snowmanxxxxxxxx << 4)));
                        _snowmanxxxxxxxxxxxxxxx.put(_snowmanxxxxxxx.createString("z"), _snowmanxxxxxxx.createInt(_snowmanxxxxxxxxxxxxxx + (_snowmanxxx << 4)));
                        _snowmanxxxxxxxxxxxxxxx.put(_snowmanxxxxxxx.createString("color"), _snowmanxxxxxxx.createShort((short)14));
                        _snowmanxxxx.add(
                           (TE)((Pair)_snowman.read(_snowmanxxxxxxx.createMap(_snowmanxxxxxxxxxxxxxxx))
                                 .result()
                                 .orElseThrow(() -> new IllegalStateException("Could not parse newly created bed block entity.")))
                              .getFirst()
                        );
                     }

                     _snowmanxxxxxxxxxx++;
                  }
               }

               return !_snowmanxxxx.isEmpty() ? var3x.set(_snowman, _snowmanxxxx.set(_snowman, _snowmanxxxx)) : var3x;
            }
         )
      );
   }
}
