import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

public class ahc extends DataFix {
   public ahc(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.c);
      OpticFinder<?> _snowmanx = _snowman.findField("Level");
      return this.fixTypeEverywhereTyped("Leaves fix", _snowman, var1x -> var1x.updateTyped(_snowman, var0x -> var0x.update(DSL.remainderFinder(), var0xx -> {
               Optional<IntStream> _snowmanxx = var0xx.get("Biomes").asIntStreamOpt().result();
               if (!_snowmanxx.isPresent()) {
                  return var0xx;
               } else {
                  int[] _snowmanx = _snowmanxx.get().toArray();
                  int[] _snowmanxx = new int[1024];

                  for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
                     for (int _snowmanxxxx = 0; _snowmanxxxx < 4; _snowmanxxxx++) {
                        int _snowmanxxxxx = (_snowmanxxxx << 2) + 2;
                        int _snowmanxxxxxx = (_snowmanxxx << 2) + 2;
                        int _snowmanxxxxxxx = _snowmanxxxxxx << 4 | _snowmanxxxxx;
                        _snowmanxx[_snowmanxxx << 2 | _snowmanxxxx] = _snowmanxxxxxxx < _snowmanx.length ? _snowmanx[_snowmanxxxxxxx] : -1;
                     }
                  }

                  for (int _snowmanxxx = 1; _snowmanxxx < 64; _snowmanxxx++) {
                     System.arraycopy(_snowmanxx, 0, _snowmanxx, _snowmanxxx * 16, 16);
                  }

                  return var0xx.set("Biomes", var0xx.createIntList(Arrays.stream(_snowmanxx)));
               }
            })));
   }
}
