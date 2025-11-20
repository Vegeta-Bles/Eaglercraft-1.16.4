package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import net.minecraft.datafixer.TypeReferences;

public class BiomeFormatFix extends DataFix {
   public BiomeFormatFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.CHUNK);
      OpticFinder<?> _snowmanx = _snowman.findField("Level");
      return this.fixTypeEverywhereTyped("Leaves fix", _snowman, _snowmanxx -> _snowmanxx.updateTyped(_snowman, _snowmanxxx -> _snowmanxxx.update(DSL.remainderFinder(), _snowmanxxxx -> {
               Optional<IntStream> _snowmanx = _snowmanxxxx.get("Biomes").asIntStreamOpt().result();
               if (!_snowmanx.isPresent()) {
                  return _snowmanxxxx;
               } else {
                  int[] _snowmanxx = _snowmanx.get().toArray();
                  int[] _snowmanxxx = new int[1024];

                  for (int _snowmanxxxx = 0; _snowmanxxxx < 4; _snowmanxxxx++) {
                     for (int _snowmanxxxxx = 0; _snowmanxxxxx < 4; _snowmanxxxxx++) {
                        int _snowmanxxxxxx = (_snowmanxxxxx << 2) + 2;
                        int _snowmanxxxxxxx = (_snowmanxxxx << 2) + 2;
                        int _snowmanxxxxxxxx = _snowmanxxxxxxx << 4 | _snowmanxxxxxx;
                        _snowmanxxx[_snowmanxxxx << 2 | _snowmanxxxxx] = _snowmanxxxxxxxx < _snowmanxx.length ? _snowmanxx[_snowmanxxxxxxxx] : -1;
                     }
                  }

                  for (int _snowmanxxxxx = 1; _snowmanxxxxx < 64; _snowmanxxxxx++) {
                     System.arraycopy(_snowmanxxx, 0, _snowmanxxx, _snowmanxxxxx * 16, 16);
                  }

                  return _snowmanxxxx.set("Biomes", _snowmanxxxx.createIntList(Arrays.stream(_snowmanxxx)));
               }
            })));
   }
}
