package net.minecraft.datafixer.fix;

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
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.math.MathHelper;

public class BitStorageAlignFix extends DataFix {
   public BitStorageAlignFix(Schema outputSchema) {
      super(outputSchema, false);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.CHUNK);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("Level", _snowmanx);
      OpticFinder<?> _snowmanxxx = _snowmanxx.type().findField("Sections");
      Type<?> _snowmanxxxx = ((ListType)_snowmanxxx.type()).getElement();
      OpticFinder<?> _snowmanxxxxx = DSL.typeFinder(_snowmanxxxx);
      Type<Pair<String, Dynamic<?>>> _snowmanxxxxxx = DSL.named(TypeReferences.BLOCK_STATE.typeName(), DSL.remainderType());
      OpticFinder<List<Pair<String, Dynamic<?>>>> _snowmanxxxxxxx = DSL.fieldFinder("Palette", DSL.list(_snowmanxxxxxx));
      return this.fixTypeEverywhereTyped(
         "BitStorageAlignFix",
         _snowman,
         this.getOutputSchema().getType(TypeReferences.CHUNK),
         _snowmanxxxxxxxx -> _snowmanxxxxxxxx.updateTyped(_snowman, _snowmanxxxxxxxxx -> this.method_27775(method_27774(_snowman, _snowman, _snowman, _snowmanxxxxxxxxx)))
      );
   }

   private Typed<?> method_27775(Typed<?> _snowman) {
      return _snowman.update(
         DSL.remainderFinder(),
         _snowmanx -> _snowmanx.update("Heightmaps", _snowmanxxx -> _snowmanxxx.updateMapValues(_snowmanxxxxx -> _snowmanxxxxx.mapSecond(_snowmanxxxxxxx -> method_27772(_snowmanx, _snowmanxxxxxxx, 256, 9))))
      );
   }

   private static Typed<?> method_27774(OpticFinder<?> _snowman, OpticFinder<?> _snowman, OpticFinder<List<Pair<String, Dynamic<?>>>> _snowman, Typed<?> _snowman) {
      return _snowman.updateTyped(
         _snowman,
         _snowmanxxxxx -> _snowmanxxxxx.updateTyped(
               _snowman,
               _snowmanxxxxxxxxx -> {
                  int _snowmanxx = _snowmanxxxxxxxxx.getOptional(_snowman).map(_snowmanxxxxxxxxxx -> Math.max(4, DataFixUtils.ceillog2(_snowmanxxxxxxxxxx.size()))).orElse(0);
                  return _snowmanxx != 0 && !MathHelper.isPowerOfTwo(_snowmanxx)
                     ? _snowmanxxxxxxxxx.update(
                        DSL.remainderFinder(),
                        _snowmanxxxxxxxxxxx -> _snowmanxxxxxxxxxxx.update("BlockStates", _snowmanxxxxxxxxxxxxx -> method_27772(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 4096, _snowmanxxxxxxxx))
                     )
                     : _snowmanxxxxxxxxx;
               }
            )
      );
   }

   private static Dynamic<?> method_27772(Dynamic<?> _snowman, Dynamic<?> _snowman, int _snowman, int _snowman) {
      long[] _snowmanxxxx = _snowman.asLongStream().toArray();
      long[] _snowmanxxxxx = method_27288(_snowman, _snowman, _snowmanxxxx);
      return _snowman.createLongList(LongStream.of(_snowmanxxxxx));
   }

   public static long[] method_27288(int _snowman, int _snowman, long[] _snowman) {
      int _snowmanxxx = _snowman.length;
      if (_snowmanxxx == 0) {
         return _snowman;
      } else {
         long _snowmanxxxx = (1L << _snowman) - 1L;
         int _snowmanxxxxx = 64 / _snowman;
         int _snowmanxxxxxx = (_snowman + _snowmanxxxxx - 1) / _snowmanxxxxx;
         long[] _snowmanxxxxxxx = new long[_snowmanxxxxxx];
         int _snowmanxxxxxxxx = 0;
         int _snowmanxxxxxxxxx = 0;
         long _snowmanxxxxxxxxxx = 0L;
         int _snowmanxxxxxxxxxxx = 0;
         long _snowmanxxxxxxxxxxxx = _snowman[0];
         long _snowmanxxxxxxxxxxxxx = _snowmanxxx > 1 ? _snowman[1] : 0L;

         for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * _snowman;
            int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx >> 6;
            int _snowmanxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxx + 1) * _snowman - 1 >> 6;
            int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx ^ _snowmanxxxxxxxxxxxxxxxx << 6;
            if (_snowmanxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1 < _snowmanxxx ? _snowman[_snowmanxxxxxxxxxxxxxxxx + 1] : 0L;
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
            }

            long _snowmanxxxxxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx >>> _snowmanxxxxxxxxxxxxxxxxxx & _snowmanxxxx;
            } else {
               int _snowmanxxxxxxxxxxxxxxxxxxxx = 64 - _snowmanxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxx >>> _snowmanxxxxxxxxxxxxxxxxxx | _snowmanxxxxxxxxxxxxx << _snowmanxxxxxxxxxxxxxxxxxxxx) & _snowmanxxxx;
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowman;
            if (_snowmanxxxxxxxxxxxxxxxxxxxx >= 64) {
               _snowmanxxxxxxx[_snowmanxxxxxxxx++] = _snowmanxxxxxxxxxx;
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxx = _snowman;
            } else {
               _snowmanxxxxxxxxxx |= _snowmanxxxxxxxxxxxxxxxxxxx << _snowmanxxxxxxxxx;
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx;
            }
         }

         if (_snowmanxxxxxxxxxx != 0L) {
            _snowmanxxxxxxx[_snowmanxxxxxxxx] = _snowmanxxxxxxxxxx;
         }

         return _snowmanxxxxxxx;
      }
   }
}
