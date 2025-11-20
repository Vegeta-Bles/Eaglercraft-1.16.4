package net.minecraft.datafixer.fix;

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
import net.minecraft.datafixer.TypeReferences;

public class ChunkToProtoChunkFix extends DataFix {
   public ChunkToProtoChunkFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.CHUNK);
      Type<?> _snowmanx = this.getOutputSchema().getType(TypeReferences.CHUNK);
      Type<?> _snowmanxx = _snowman.findFieldType("Level");
      Type<?> _snowmanxxx = _snowmanx.findFieldType("Level");
      Type<?> _snowmanxxxx = _snowmanxx.findFieldType("TileTicks");
      OpticFinder<?> _snowmanxxxxx = DSL.fieldFinder("Level", _snowmanxx);
      OpticFinder<?> _snowmanxxxxxx = DSL.fieldFinder("TileTicks", _snowmanxxxx);
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped(
            "ChunkToProtoChunkFix",
            _snowman,
            this.getOutputSchema().getType(TypeReferences.CHUNK),
            _snowmanxxxxxxx -> _snowmanxxxxxxx.updateTyped(
                  _snowman,
                  _snowman,
                  _snowmanxxxxxxxxxxxxxxx -> {
                     Optional<? extends Stream<? extends Dynamic<?>>> _snowmanxxx = _snowmanxxxxxxxxxxxxxxx.getOptionalTyped(_snowman)
                        .flatMap(_snowmanxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxx.write().result())
                        .flatMap(_snowmanxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxx.asStreamOpt().result());
                     Dynamic<?> _snowmanxxxx = (Dynamic<?>)_snowmanxxxxxxxxxxxxxxx.get(DSL.remainderFinder());
                     boolean _snowmanxxxxx = _snowmanxxxx.get("TerrainPopulated").asBoolean(false)
                        && (!_snowmanxxxx.get("LightPopulated").asNumber().result().isPresent() || _snowmanxxxx.get("LightPopulated").asBoolean(false));
                     _snowmanxxxx = _snowmanxxxx.set("Status", _snowmanxxxx.createString(_snowmanxxxxx ? "mobs_spawned" : "empty"));
                     _snowmanxxxx = _snowmanxxxx.set("hasLegacyStructureData", _snowmanxxxx.createBoolean(true));
                     Dynamic<?> _snowmanxxxxxx;
                     if (_snowmanxxxxx) {
                        Optional<ByteBuffer> _snowmanxxxxxxx = _snowmanxxxx.get("Biomes").asByteBufferOpt().result();
                        if (_snowmanxxxxxxx.isPresent()) {
                           ByteBuffer _snowmanxxxxxxxx = _snowmanxxxxxxx.get();
                           int[] _snowmanxxxxxxxxx = new int[256];

                           for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxxxxx.length; _snowmanxxxxxxxxxxxxxxx++) {
                              if (_snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxxxx.capacity()) {
                                 _snowmanxxxxxxxxx[_snowmanxxxxxxxxxxxxxxx] = _snowmanxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxx) & 255;
                              }
                           }

                           _snowmanxxxx = _snowmanxxxx.set("Biomes", _snowmanxxxx.createIntList(Arrays.stream(_snowmanxxxxxxxxx)));
                        }

                        Dynamic<?> _snowmanxxxxxxxx = _snowmanxxxx;
                        List<ShortList> _snowmanxxxxxxxxx = IntStream.range(0, 16).mapToObj(_snowmanxxxxxxxxxxxxxxxxx -> new ShortArrayList()).collect(Collectors.toList());
                        if (_snowmanxxx.isPresent()) {
                           _snowmanxxx.get().forEach(_snowmanxxxxxxxxxxxxxxxxxxxxxx -> {
                              int _snowmanxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.get("x").asInt(0);
                              int _snowmanxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.get("y").asInt(0);
                              int _snowmanxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.get("z").asInt(0);
                              short _snowmanxxxxx = method_15675(_snowmanxx, _snowmanxxx, _snowmanxxxx);
                              _snowmanxxxxx.get(_snowmanxxx >> 4).add(_snowmanxxxxx);
                           });
                           _snowmanxxxx = _snowmanxxxx.set(
                              "ToBeTicked",
                              _snowmanxxxx.createList(
                                 _snowmanxxxxxxxxx.stream().map(_snowmanxxxxxxxxxxxxxxxxxx -> _snowmanxxxxxx.createList(_snowmanxxxxxxxxxxxxxxxxxx.stream().map(_snowmanxxxxxx::createShort)))
                              )
                           );
                        }

                        _snowmanxxxxxx = (Dynamic<?>)DataFixUtils.orElse(_snowmanxxxxxxxxxxxxxxx.set(DSL.remainderFinder(), _snowmanxxxx).write().result(), _snowmanxxxx);
                     } else {
                        _snowmanxxxxxx = _snowmanxxxx;
                     }

                     return (Typed)((Pair)_snowman.readTyped(_snowmanxxxxxx).result().orElseThrow(() -> new IllegalStateException("Could not read the new chunk")))
                        .getFirst();
                  }
               )
         ),
         this.writeAndRead(
            "Structure biome inject",
            this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE),
            this.getOutputSchema().getType(TypeReferences.STRUCTURE_FEATURE)
         )
      );
   }

   private static short method_15675(int _snowman, int _snowman, int _snowman) {
      return (short)(_snowman & 15 | (_snowman & 15) << 4 | (_snowman & 15) << 8);
   }
}
