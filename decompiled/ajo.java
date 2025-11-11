import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ajo extends DataFix {
   static final Map<String, String> a = x.a(Maps.newHashMap(), var0 -> {
      var0.put("0", "minecraft:ocean");
      var0.put("1", "minecraft:plains");
      var0.put("2", "minecraft:desert");
      var0.put("3", "minecraft:mountains");
      var0.put("4", "minecraft:forest");
      var0.put("5", "minecraft:taiga");
      var0.put("6", "minecraft:swamp");
      var0.put("7", "minecraft:river");
      var0.put("8", "minecraft:nether");
      var0.put("9", "minecraft:the_end");
      var0.put("10", "minecraft:frozen_ocean");
      var0.put("11", "minecraft:frozen_river");
      var0.put("12", "minecraft:snowy_tundra");
      var0.put("13", "minecraft:snowy_mountains");
      var0.put("14", "minecraft:mushroom_fields");
      var0.put("15", "minecraft:mushroom_field_shore");
      var0.put("16", "minecraft:beach");
      var0.put("17", "minecraft:desert_hills");
      var0.put("18", "minecraft:wooded_hills");
      var0.put("19", "minecraft:taiga_hills");
      var0.put("20", "minecraft:mountain_edge");
      var0.put("21", "minecraft:jungle");
      var0.put("22", "minecraft:jungle_hills");
      var0.put("23", "minecraft:jungle_edge");
      var0.put("24", "minecraft:deep_ocean");
      var0.put("25", "minecraft:stone_shore");
      var0.put("26", "minecraft:snowy_beach");
      var0.put("27", "minecraft:birch_forest");
      var0.put("28", "minecraft:birch_forest_hills");
      var0.put("29", "minecraft:dark_forest");
      var0.put("30", "minecraft:snowy_taiga");
      var0.put("31", "minecraft:snowy_taiga_hills");
      var0.put("32", "minecraft:giant_tree_taiga");
      var0.put("33", "minecraft:giant_tree_taiga_hills");
      var0.put("34", "minecraft:wooded_mountains");
      var0.put("35", "minecraft:savanna");
      var0.put("36", "minecraft:savanna_plateau");
      var0.put("37", "minecraft:badlands");
      var0.put("38", "minecraft:wooded_badlands_plateau");
      var0.put("39", "minecraft:badlands_plateau");
      var0.put("40", "minecraft:small_end_islands");
      var0.put("41", "minecraft:end_midlands");
      var0.put("42", "minecraft:end_highlands");
      var0.put("43", "minecraft:end_barrens");
      var0.put("44", "minecraft:warm_ocean");
      var0.put("45", "minecraft:lukewarm_ocean");
      var0.put("46", "minecraft:cold_ocean");
      var0.put("47", "minecraft:deep_warm_ocean");
      var0.put("48", "minecraft:deep_lukewarm_ocean");
      var0.put("49", "minecraft:deep_cold_ocean");
      var0.put("50", "minecraft:deep_frozen_ocean");
      var0.put("127", "minecraft:the_void");
      var0.put("129", "minecraft:sunflower_plains");
      var0.put("130", "minecraft:desert_lakes");
      var0.put("131", "minecraft:gravelly_mountains");
      var0.put("132", "minecraft:flower_forest");
      var0.put("133", "minecraft:taiga_mountains");
      var0.put("134", "minecraft:swamp_hills");
      var0.put("140", "minecraft:ice_spikes");
      var0.put("149", "minecraft:modified_jungle");
      var0.put("151", "minecraft:modified_jungle_edge");
      var0.put("155", "minecraft:tall_birch_forest");
      var0.put("156", "minecraft:tall_birch_hills");
      var0.put("157", "minecraft:dark_forest_hills");
      var0.put("158", "minecraft:snowy_taiga_mountains");
      var0.put("160", "minecraft:giant_spruce_taiga");
      var0.put("161", "minecraft:giant_spruce_taiga_hills");
      var0.put("162", "minecraft:modified_gravelly_mountains");
      var0.put("163", "minecraft:shattered_savanna");
      var0.put("164", "minecraft:shattered_savanna_plateau");
      var0.put("165", "minecraft:eroded_badlands");
      var0.put("166", "minecraft:modified_wooded_badlands_plateau");
      var0.put("167", "minecraft:modified_badlands_plateau");
   });

   public ajo(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(akn.a);
      return this.fixTypeEverywhereTyped(
         "LevelDataGeneratorOptionsFix", this.getInputSchema().getType(akn.a), _snowman, var1x -> (Typed)var1x.write().flatMap(var1xx -> {
               Optional<String> _snowmanx = var1xx.get("generatorOptions").asString().result();
               Dynamic<?> _snowmanx;
               if ("flat".equalsIgnoreCase(var1xx.get("generatorName").asString(""))) {
                  String _snowmanxx = _snowmanx.orElse("");
                  _snowmanx = var1xx.set("generatorOptions", a(_snowmanxx, var1xx.getOps()));
               } else if ("buffet".equalsIgnoreCase(var1xx.get("generatorName").asString("")) && _snowmanx.isPresent()) {
                  Dynamic<JsonElement> _snowmanxx = new Dynamic(JsonOps.INSTANCE, afd.a(_snowmanx.get(), true));
                  _snowmanx = var1xx.set("generatorOptions", _snowmanxx.convert(var1xx.getOps()));
               } else {
                  _snowmanx = var1xx;
               }

               return _snowman.readTyped(_snowmanx);
            }).map(Pair::getFirst).result().orElseThrow(() -> new IllegalStateException("Could not read new level type."))
      );
   }

   private static <T> Dynamic<T> a(String var0, DynamicOps<T> var1) {
      Iterator<String> _snowman = Splitter.on(';').split(_snowman).iterator();
      String _snowmanx = "minecraft:plains";
      Map<String, Map<String, String>> _snowmanxx = Maps.newHashMap();
      List<Pair<Integer, String>> _snowmanxxx;
      if (!_snowman.isEmpty() && _snowman.hasNext()) {
         _snowmanxxx = b(_snowman.next());
         if (!_snowmanxxx.isEmpty()) {
            if (_snowman.hasNext()) {
               _snowmanx = a.getOrDefault(_snowman.next(), "minecraft:plains");
            }

            if (_snowman.hasNext()) {
               String[] _snowmanxxxx = _snowman.next().toLowerCase(Locale.ROOT).split(",");

               for (String _snowmanxxxxx : _snowmanxxxx) {
                  String[] _snowmanxxxxxx = _snowmanxxxxx.split("\\(", 2);
                  if (!_snowmanxxxxxx[0].isEmpty()) {
                     _snowmanxx.put(_snowmanxxxxxx[0], Maps.newHashMap());
                     if (_snowmanxxxxxx.length > 1 && _snowmanxxxxxx[1].endsWith(")") && _snowmanxxxxxx[1].length() > 1) {
                        String[] _snowmanxxxxxxx = _snowmanxxxxxx[1].substring(0, _snowmanxxxxxx[1].length() - 1).split(" ");

                        for (String _snowmanxxxxxxxx : _snowmanxxxxxxx) {
                           String[] _snowmanxxxxxxxxx = _snowmanxxxxxxxx.split("=", 2);
                           if (_snowmanxxxxxxxxx.length == 2) {
                              _snowmanxx.get(_snowmanxxxxxx[0]).put(_snowmanxxxxxxxxx[0], _snowmanxxxxxxxxx[1]);
                           }
                        }
                     }
                  }
               }
            } else {
               _snowmanxx.put("village", Maps.newHashMap());
            }
         }
      } else {
         _snowmanxxx = Lists.newArrayList();
         _snowmanxxx.add(Pair.of(1, "minecraft:bedrock"));
         _snowmanxxx.add(Pair.of(2, "minecraft:dirt"));
         _snowmanxxx.add(Pair.of(1, "minecraft:grass_block"));
         _snowmanxx.put("village", Maps.newHashMap());
      }

      T _snowmanxxxx = (T)_snowman.createList(
         _snowmanxxx.stream()
            .map(
               var1x -> _snowman.createMap(
                     ImmutableMap.of(
                        _snowman.createString("height"), _snowman.createInt((Integer)var1x.getFirst()), _snowman.createString("block"), _snowman.createString((String)var1x.getSecond())
                     )
                  )
            )
      );
      T _snowmanxxxxxx = (T)_snowman.createMap(
         _snowmanxx.entrySet()
            .stream()
            .map(
               var1x -> Pair.of(
                     _snowman.createString(var1x.getKey().toLowerCase(Locale.ROOT)),
                     _snowman.createMap(
                        var1x.getValue()
                           .entrySet()
                           .stream()
                           .map(var1xx -> Pair.of(_snowman.createString(var1xx.getKey()), _snowman.createString(var1xx.getValue())))
                           .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
                     )
                  )
            )
            .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
      );
      return new Dynamic(
         _snowman, _snowman.createMap(ImmutableMap.of(_snowman.createString("layers"), _snowmanxxxx, _snowman.createString("biome"), _snowman.createString(_snowmanx), _snowman.createString("structures"), _snowmanxxxxxx))
      );
   }

   @Nullable
   private static Pair<Integer, String> a(String var0) {
      String[] _snowman = _snowman.split("\\*", 2);
      int _snowmanx;
      if (_snowman.length == 2) {
         try {
            _snowmanx = Integer.parseInt(_snowman[0]);
         } catch (NumberFormatException var4) {
            return null;
         }
      } else {
         _snowmanx = 1;
      }

      String _snowmanxx = _snowman[_snowman.length - 1];
      return Pair.of(_snowmanx, _snowmanxx);
   }

   private static List<Pair<Integer, String>> b(String var0) {
      List<Pair<Integer, String>> _snowman = Lists.newArrayList();
      String[] _snowmanx = _snowman.split(",");

      for (String _snowmanxx : _snowmanx) {
         Pair<Integer, String> _snowmanxxx = a(_snowmanxx);
         if (_snowmanxxx == null) {
            return Collections.emptyList();
         }

         _snowman.add(_snowmanxxx);
      }

      return _snowman;
   }
}
