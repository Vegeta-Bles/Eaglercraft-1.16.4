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
import org.apache.commons.lang3.math.NumberUtils;

public class ajp extends DataFix {
   private static final Splitter a = Splitter.on(';').limit(5);
   private static final Splitter b = Splitter.on(',');
   private static final Splitter c = Splitter.on('x').limit(2);
   private static final Splitter d = Splitter.on('*').limit(2);
   private static final Splitter e = Splitter.on(':').limit(3);

   public ajp(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("LevelFlatGeneratorInfoFix", this.getInputSchema().getType(akn.a), var1 -> var1.update(DSL.remainderFinder(), this::a));
   }

   private Dynamic<?> a(Dynamic<?> var1) {
      return _snowman.get("generatorName").asString("").equalsIgnoreCase("flat")
         ? _snowman.update("generatorOptions", var1x -> (Dynamic)DataFixUtils.orElse(var1x.asString().map(this::a).map(var1x::createString).result(), var1x))
         : _snowman;
   }

   @VisibleForTesting
   String a(String var1) {
      if (_snowman.isEmpty()) {
         return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
      } else {
         Iterator<String> _snowman = a.split(_snowman).iterator();
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
            Splitter _snowmanxxxxx = _snowmanxx < 3 ? c : d;
            _snowmanxxxx.append(StreamSupport.<String>stream(b.split(_snowmanxxx).spliterator(), false).map(var2x -> {
               List<String> _snowmanxxxxxx = _snowman.splitToList(var2x);
               int _snowmanx;
               String _snowmanxx;
               if (_snowmanxxxxxx.size() == 2) {
                  _snowmanx = NumberUtils.toInt(_snowmanxxxxxx.get(0));
                  _snowmanxx = _snowmanxxxxxx.get(1);
               } else {
                  _snowmanx = 1;
                  _snowmanxx = _snowmanxxxxxx.get(0);
               }

               List<String> _snowmanxxx = e.splitToList(_snowmanxx);
               int _snowmanxxxx = _snowmanxxx.get(0).equals("minecraft") ? 1 : 0;
               String _snowmanxxxxx = _snowmanxxx.get(_snowmanxxxx);
               int _snowmanxxxxxx = _snowman == 3 ? ahm.a("minecraft:" + _snowmanxxxxx) : NumberUtils.toInt(_snowmanxxxxx, 0);
               int _snowmanxxxxxxx = _snowmanxxxx + 1;
               int _snowmanxxxxxxxx = _snowmanxxx.size() > _snowmanxxxxxxx ? NumberUtils.toInt(_snowmanxxx.get(_snowmanxxxxxxx), 0) : 0;
               return (_snowmanx == 1 ? "" : _snowmanx + "*") + agz.b(_snowmanxxxxxx << 4 | _snowmanxxxxxxxx).get("Name").asString("");
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
