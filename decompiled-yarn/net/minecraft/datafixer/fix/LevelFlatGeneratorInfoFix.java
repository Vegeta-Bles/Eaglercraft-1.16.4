package net.minecraft.datafixer.fix;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.datafixer.TypeReferences;
import org.apache.commons.lang3.math.NumberUtils;

public class LevelFlatGeneratorInfoFix extends DataFix {
   private static final Splitter SPLIT_ON_SEMICOLON = Splitter.on(';').limit(5);
   private static final Splitter SPLIT_ON_COMMA = Splitter.on(',');
   private static final Splitter SPLIT_ON_LOWER_X = Splitter.on('x').limit(2);
   private static final Splitter SPLIT_ON_ASTERISK = Splitter.on('*').limit(2);
   private static final Splitter SPLIT_ON_COLON = Splitter.on(':').limit(3);

   public LevelFlatGeneratorInfoFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "LevelFlatGeneratorInfoFix", this.getInputSchema().getType(TypeReferences.LEVEL), _snowman -> _snowman.update(DSL.remainderFinder(), this::fixGeneratorOptions)
      );
   }

   private Dynamic<?> fixGeneratorOptions(Dynamic<?> _snowman) {
      return _snowman.get("generatorName").asString("").equalsIgnoreCase("flat")
         ? _snowman.update("generatorOptions", _snowmanx -> (Dynamic)DataFixUtils.orElse(_snowmanx.asString().map(this::fixFlatGeneratorOptions).map(_snowmanx::createString).result(), _snowmanx))
         : _snowman;
   }

   @VisibleForTesting
   String fixFlatGeneratorOptions(String generatorOptions) {
      if (generatorOptions.isEmpty()) {
         return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
      } else {
         Iterator<String> _snowman = SPLIT_ON_SEMICOLON.split(generatorOptions).iterator();
         String _snowmanx = _snowman.next();
         int _snowmanxx;
         String _snowmanxxx;
         if (_snowman.hasNext()) {
            _snowmanxx = NumberUtils.toInt(_snowmanx, 0);
            _snowmanxxx = _snowman.next();
         } else {
            _snowmanxx = 0;
            _snowmanxxx = _snowmanx;
         }

         if (_snowmanxx >= 0 && _snowmanxx <= 3) {
            StringBuilder _snowmanxxxx = new StringBuilder();
            Splitter _snowmanxxxxx = _snowmanxx < 3 ? SPLIT_ON_LOWER_X : SPLIT_ON_ASTERISK;
            _snowmanxxxx.append(StreamSupport.<String>stream(SPLIT_ON_COMMA.split(_snowmanxxx).spliterator(), false).map(_snowmanxxxxxx -> {
               List<String> _snowmanxxxxxxx = _snowman.splitToList(_snowmanxxxxxx);
               int _snowmanx;
               String _snowmanxxx;
               if (_snowmanxxxxxxx.size() == 2) {
                  _snowmanx = NumberUtils.toInt(_snowmanxxxxxxx.get(0));
                  _snowmanxxx = _snowmanxxxxxxx.get(1);
               } else {
                  _snowmanx = 1;
                  _snowmanxxx = _snowmanxxxxxxx.get(0);
               }

               List<String> _snowmanxxxx = SPLIT_ON_COLON.splitToList(_snowmanxxx);
               int _snowmanxxxxx = _snowmanxxxx.get(0).equals("minecraft") ? 1 : 0;
               String _snowmanxxxxxx = _snowmanxxxx.get(_snowmanxxxxx);
               int _snowmanxxxxxxx = _snowman == 3 ? EntityBlockStateFix.getNumericalBlockId("minecraft:" + _snowmanxxxxxx) : NumberUtils.toInt(_snowmanxxxxxx, 0);
               int _snowmanxxxxxxxx = _snowmanxxxxx + 1;
               int _snowmanxxxxxxxxx = _snowmanxxxx.size() > _snowmanxxxxxxxx ? NumberUtils.toInt(_snowmanxxxx.get(_snowmanxxxxxxxx), 0) : 0;
               return (_snowmanx == 1 ? "" : _snowmanx + "*") + BlockStateFlattening.lookupState(_snowmanxxxxxxx << 4 | _snowmanxxxxxxxxx).get("Name").asString("");
            }).collect(Collectors.joining(",")));

            while (_snowman.hasNext()) {
               _snowmanxxxx.append(';').append(_snowman.next());
            }

            return _snowmanxxxx.toString();
         } else {
            return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
         }
      }
   }
}
