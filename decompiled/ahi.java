import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ahi extends DataFix {
   public ahi(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.c);
      Type<?> _snowmanx = this.getOutputSchema().getType(akn.c);
      Type<?> _snowmanxx = _snowman.findFieldType("Level");
      Type<?> _snowmanxxx = _snowmanx.findFieldType("Level");
      Type<?> _snowmanxxxx = _snowmanxx.findFieldType("TileTicks");
      OpticFinder<?> _snowmanxxxxx = DSL.fieldFinder("Level", _snowmanxx);
      OpticFinder<?> _snowmanxxxxxx = DSL.fieldFinder("TileTicks", _snowmanxxxx);
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped(
            "ChunkToProtoChunkFix",
            _snowman,
            this.getOutputSchema().getType(akn.c),
            var3x -> var3x.updateTyped(
                  _snowman,
                  _snowman,
                  var2x -> {
                     Optional<? extends Stream<? extends Dynamic<?>>> _snowmanxxxxxxx = var2x.getOptionalTyped(_snowman)
                        .flatMap(var0x -> var0x.write().result())
                        .flatMap(var0x -> var0x.asStreamOpt().result());
                     Dynamic<?> _snowmanx = (Dynamic<?>)var2x.get(DSL.remainderFinder());
                     boolean _snowmanxx = _snowmanx.get("TerrainPopulated").asBoolean(false)
                        && (!_snowmanx.get("LightPopulated").asNumber().result().isPresent() || _snowmanx.get("LightPopulated").asBoolean(false));
                     _snowmanx = _snowmanx.set("Status", _snowmanx.createString(_snowmanxx ? "mobs_spawned" : "empty"));
                     _snowmanx = _snowmanx.set("hasLegacyStructureData", _snowmanx.createBoolean(true));
                     Dynamic<?> _snowmanxxx;
                     if (_snowmanxx) {
                        Optional<ByteBuffer> _snowmanxxxx = _snowmanx.get("Biomes").asByteBufferOpt().result();
                        if (_snowmanxxxx.isPresent()) {
                           ByteBuffer _snowmanxxxxx = _snowmanxxxx.get();
                           int[] _snowmanxxxxxx = new int[256];

                           for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx.length; _snowmanxxxxxxx++) {
                              if (_snowmanxxxxxxx < _snowmanxxxxx.capacity()) {
                                 _snowmanxxxxxx[_snowmanxxxxxxx] = _snowmanxxxxx.get(_snowmanxxxxxxx) & 255;
                              }
                           }

                           _snowmanx = _snowmanx.set("Biomes", _snowmanx.createIntList(Arrays.stream(_snowmanxxxxxx)));
                        }

                        Dynamic<?> _snowmanxxxxx = _snowmanx;
                        List<ShortList> _snowmanxxxxxx = IntStream.range(0, 16).mapToObj(var0x -> new ShortArrayList()).collect(Collectors.toList());
                        if (_snowmanxxxxxxx.isPresent()) {
                           _snowmanxxxxxxx.get().forEach(var1x -> {
                              int _snowmanxxxxxxxx = var1x.get("x").asInt(0);
                              int _snowmanx = var1x.get("y").asInt(0);
                              int _snowmanxx = var1x.get("z").asInt(0);
                              short _snowmanxxx = a(_snowmanxxxxxxxx, _snowmanx, _snowmanxx);
                              _snowman.get(_snowmanx >> 4).add(_snowmanxxx);
                           });
                           _snowmanx = _snowmanx.set("ToBeTicked", _snowmanx.createList(_snowmanxxxxxx.stream().map(var1x -> _snowman.createList(var1x.stream().map(_snowman::createShort)))));
                        }

                        _snowmanxxx = (Dynamic<?>)DataFixUtils.orElse(var2x.set(DSL.remainderFinder(), _snowmanx).write().result(), _snowmanx);
                     } else {
                        _snowmanxxx = _snowmanx;
                     }

                     return (Typed)((Pair)_snowman.readTyped(_snowmanxxx).result().orElseThrow(() -> new IllegalStateException("Could not read the new chunk"))).getFirst();
                  }
               )
         ),
         this.writeAndRead("Structure biome inject", this.getInputSchema().getType(akn.t), this.getOutputSchema().getType(akn.t))
      );
   }

   private static short a(int var0, int var1, int var2) {
      return (short)(_snowman & 15 | (_snowman & 15) << 4 | (_snowman & 15) << 8);
   }
}
