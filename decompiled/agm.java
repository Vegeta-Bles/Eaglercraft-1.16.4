import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.stream.LongStream;

public class agm extends DataFix {
   public agm(Schema var1) {
      super(_snowman, false);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.c);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("Level", _snowmanx);
      OpticFinder<?> _snowmanxxx = _snowmanxx.type().findField("Sections");
      Type<?> _snowmanxxxx = ((ListType)_snowmanxxx.type()).getElement();
      OpticFinder<?> _snowmanxxxxx = DSL.typeFinder(_snowmanxxxx);
      Type<Pair<String, Dynamic<?>>> _snowmanxxxxxx = DSL.named(akn.m.typeName(), DSL.remainderType());
      OpticFinder<List<Pair<String, Dynamic<?>>>> _snowmanxxxxxxx = DSL.fieldFinder("Palette", DSL.list(_snowmanxxxxxx));
      return this.fixTypeEverywhereTyped(
         "BitStorageAlignFix", _snowman, this.getOutputSchema().getType(akn.c), var5x -> var5x.updateTyped(_snowman, var4x -> this.a(a(_snowman, _snowman, _snowman, var4x)))
      );
   }

   private Typed<?> a(Typed<?> var1) {
      return _snowman.update(
         DSL.remainderFinder(),
         var0 -> var0.update("Heightmaps", var1x -> var1x.updateMapValues(var1xx -> var1xx.mapSecond(var1xxx -> a(var0, var1xxx, 256, 9))))
      );
   }

   private static Typed<?> a(OpticFinder<?> var0, OpticFinder<?> var1, OpticFinder<List<Pair<String, Dynamic<?>>>> var2, Typed<?> var3) {
      return _snowman.updateTyped(
         _snowman,
         var2x -> var2x.updateTyped(
               _snowman,
               var1x -> {
                  int _snowman = var1x.getOptional(_snowman).map(var0x -> Math.max(4, DataFixUtils.ceillog2(var0x.size()))).orElse(0);
                  return _snowman != 0 && !afm.d(_snowman)
                     ? var1x.update(DSL.remainderFinder(), var1xx -> var1xx.update("BlockStates", var2xx -> a(var1xx, var2xx, 4096, _snowman)))
                     : var1x;
               }
            )
      );
   }

   private static Dynamic<?> a(Dynamic<?> var0, Dynamic<?> var1, int var2, int var3) {
      long[] _snowman = _snowman.asLongStream().toArray();
      long[] _snowmanx = a(_snowman, _snowman, _snowman);
      return _snowman.createLongList(LongStream.of(_snowmanx));
   }

   public static long[] a(int var0, int var1, long[] var2) {
      int _snowman = _snowman.length;
      if (_snowman == 0) {
         return _snowman;
      } else {
         long _snowmanx = (1L << _snowman) - 1L;
         int _snowmanxx = 64 / _snowman;
         int _snowmanxxx = (_snowman + _snowmanxx - 1) / _snowmanxx;
         long[] _snowmanxxxx = new long[_snowmanxxx];
         int _snowmanxxxxx = 0;
         int _snowmanxxxxxx = 0;
         long _snowmanxxxxxxx = 0L;
         int _snowmanxxxxxxxx = 0;
         long _snowmanxxxxxxxxx = _snowman[0];
         long _snowmanxxxxxxxxxx = _snowman > 1 ? _snowman[1] : 0L;

         for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * _snowman;
            int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx >> 6;
            int _snowmanxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxx + 1) * _snowman - 1 >> 6;
            int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx ^ _snowmanxxxxxxxxxxxxx << 6;
            if (_snowmanxxxxxxxxxxxxx != _snowmanxxxxxxxx) {
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxxx;
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxx + 1 < _snowman ? _snowman[_snowmanxxxxxxxxxxxxx + 1] : 0L;
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxx;
            }

            long _snowmanxxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx >>> _snowmanxxxxxxxxxxxxxxx & _snowmanx;
            } else {
               int _snowmanxxxxxxxxxxxxxxxxx = 64 - _snowmanxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxx >>> _snowmanxxxxxxxxxxxxxxx | _snowmanxxxxxxxxxx << _snowmanxxxxxxxxxxxxxxxxx) & _snowmanx;
            }

            int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxx + _snowman;
            if (_snowmanxxxxxxxxxxxxxxxxx >= 64) {
               _snowmanxxxx[_snowmanxxxxx++] = _snowmanxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxx = _snowman;
            } else {
               _snowmanxxxxxxx |= _snowmanxxxxxxxxxxxxxxxx << _snowmanxxxxxx;
               _snowmanxxxxxx = _snowmanxxxxxxxxxxxxxxxxx;
            }
         }

         if (_snowmanxxxxxxx != 0L) {
            _snowmanxxxx[_snowmanxxxxx] = _snowmanxxxxxxx;
         }

         return _snowmanxxxx;
      }
   }
}
