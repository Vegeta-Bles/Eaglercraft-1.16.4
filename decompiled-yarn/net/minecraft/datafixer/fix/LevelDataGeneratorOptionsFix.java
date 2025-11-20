package net.minecraft.datafixer.fix;

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
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;

public class LevelDataGeneratorOptionsFix extends DataFix {
   static final Map<String, String> NUMERICAL_IDS_TO_BIOME_IDS = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("0", "minecraft:ocean");
      _snowman.put("1", "minecraft:plains");
      _snowman.put("2", "minecraft:desert");
      _snowman.put("3", "minecraft:mountains");
      _snowman.put("4", "minecraft:forest");
      _snowman.put("5", "minecraft:taiga");
      _snowman.put("6", "minecraft:swamp");
      _snowman.put("7", "minecraft:river");
      _snowman.put("8", "minecraft:nether");
      _snowman.put("9", "minecraft:the_end");
      _snowman.put("10", "minecraft:frozen_ocean");
      _snowman.put("11", "minecraft:frozen_river");
      _snowman.put("12", "minecraft:snowy_tundra");
      _snowman.put("13", "minecraft:snowy_mountains");
      _snowman.put("14", "minecraft:mushroom_fields");
      _snowman.put("15", "minecraft:mushroom_field_shore");
      _snowman.put("16", "minecraft:beach");
      _snowman.put("17", "minecraft:desert_hills");
      _snowman.put("18", "minecraft:wooded_hills");
      _snowman.put("19", "minecraft:taiga_hills");
      _snowman.put("20", "minecraft:mountain_edge");
      _snowman.put("21", "minecraft:jungle");
      _snowman.put("22", "minecraft:jungle_hills");
      _snowman.put("23", "minecraft:jungle_edge");
      _snowman.put("24", "minecraft:deep_ocean");
      _snowman.put("25", "minecraft:stone_shore");
      _snowman.put("26", "minecraft:snowy_beach");
      _snowman.put("27", "minecraft:birch_forest");
      _snowman.put("28", "minecraft:birch_forest_hills");
      _snowman.put("29", "minecraft:dark_forest");
      _snowman.put("30", "minecraft:snowy_taiga");
      _snowman.put("31", "minecraft:snowy_taiga_hills");
      _snowman.put("32", "minecraft:giant_tree_taiga");
      _snowman.put("33", "minecraft:giant_tree_taiga_hills");
      _snowman.put("34", "minecraft:wooded_mountains");
      _snowman.put("35", "minecraft:savanna");
      _snowman.put("36", "minecraft:savanna_plateau");
      _snowman.put("37", "minecraft:badlands");
      _snowman.put("38", "minecraft:wooded_badlands_plateau");
      _snowman.put("39", "minecraft:badlands_plateau");
      _snowman.put("40", "minecraft:small_end_islands");
      _snowman.put("41", "minecraft:end_midlands");
      _snowman.put("42", "minecraft:end_highlands");
      _snowman.put("43", "minecraft:end_barrens");
      _snowman.put("44", "minecraft:warm_ocean");
      _snowman.put("45", "minecraft:lukewarm_ocean");
      _snowman.put("46", "minecraft:cold_ocean");
      _snowman.put("47", "minecraft:deep_warm_ocean");
      _snowman.put("48", "minecraft:deep_lukewarm_ocean");
      _snowman.put("49", "minecraft:deep_cold_ocean");
      _snowman.put("50", "minecraft:deep_frozen_ocean");
      _snowman.put("127", "minecraft:the_void");
      _snowman.put("129", "minecraft:sunflower_plains");
      _snowman.put("130", "minecraft:desert_lakes");
      _snowman.put("131", "minecraft:gravelly_mountains");
      _snowman.put("132", "minecraft:flower_forest");
      _snowman.put("133", "minecraft:taiga_mountains");
      _snowman.put("134", "minecraft:swamp_hills");
      _snowman.put("140", "minecraft:ice_spikes");
      _snowman.put("149", "minecraft:modified_jungle");
      _snowman.put("151", "minecraft:modified_jungle_edge");
      _snowman.put("155", "minecraft:tall_birch_forest");
      _snowman.put("156", "minecraft:tall_birch_hills");
      _snowman.put("157", "minecraft:dark_forest_hills");
      _snowman.put("158", "minecraft:snowy_taiga_mountains");
      _snowman.put("160", "minecraft:giant_spruce_taiga");
      _snowman.put("161", "minecraft:giant_spruce_taiga_hills");
      _snowman.put("162", "minecraft:modified_gravelly_mountains");
      _snowman.put("163", "minecraft:shattered_savanna");
      _snowman.put("164", "minecraft:shattered_savanna_plateau");
      _snowman.put("165", "minecraft:eroded_badlands");
      _snowman.put("166", "minecraft:modified_wooded_badlands_plateau");
      _snowman.put("167", "minecraft:modified_badlands_plateau");
   });

   public LevelDataGeneratorOptionsFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(TypeReferences.LEVEL);
      return this.fixTypeEverywhereTyped(
         "LevelDataGeneratorOptionsFix", this.getInputSchema().getType(TypeReferences.LEVEL), _snowman, _snowmanx -> (Typed)_snowmanx.write().flatMap(_snowmanxxxxxxx -> {
               Optional<String> _snowmanxx = _snowmanxxxxxxx.get("generatorOptions").asString().result();
               Dynamic<?> _snowmanxxx;
               if ("flat".equalsIgnoreCase(_snowmanxxxxxxx.get("generatorName").asString(""))) {
                  String _snowmanxxxx = _snowmanxx.orElse("");
                  _snowmanxxx = _snowmanxxxxxxx.set("generatorOptions", fixGeneratorOptions(_snowmanxxxx, _snowmanxxxxxxx.getOps()));
               } else if ("buffet".equalsIgnoreCase(_snowmanxxxxxxx.get("generatorName").asString("")) && _snowmanxx.isPresent()) {
                  Dynamic<JsonElement> _snowmanxxxx = new Dynamic(JsonOps.INSTANCE, JsonHelper.deserialize(_snowmanxx.get(), true));
                  _snowmanxxx = _snowmanxxxxxxx.set("generatorOptions", _snowmanxxxx.convert(_snowmanxxxxxxx.getOps()));
               } else {
                  _snowmanxxx = _snowmanxxxxxxx;
               }

               return _snowman.readTyped(_snowmanxxx);
            }).map(Pair::getFirst).result().orElseThrow(() -> new IllegalStateException("Could not read new level type."))
      );
   }

   private static <T> Dynamic<T> fixGeneratorOptions(String generatorOptions, DynamicOps<T> _snowman) {
      Iterator<String> _snowmanx = Splitter.on(';').split(generatorOptions).iterator();
      String _snowmanxx = "minecraft:plains";
      Map<String, Map<String, String>> _snowmanxxx = Maps.newHashMap();
      List<Pair<Integer, String>> _snowmanxxxx;
      if (!generatorOptions.isEmpty() && _snowmanx.hasNext()) {
         _snowmanxxxx = parseFlatLayers(_snowmanx.next());
         if (!_snowmanxxxx.isEmpty()) {
            if (_snowmanx.hasNext()) {
               _snowmanxx = NUMERICAL_IDS_TO_BIOME_IDS.getOrDefault(_snowmanx.next(), "minecraft:plains");
            }

            if (_snowmanx.hasNext()) {
               String[] _snowmanxxxxx = _snowmanx.next().toLowerCase(Locale.ROOT).split(",");

               for (String _snowmanxxxxxx : _snowmanxxxxx) {
                  String[] _snowmanxxxxxxx = _snowmanxxxxxx.split("\\(", 2);
                  if (!_snowmanxxxxxxx[0].isEmpty()) {
                     _snowmanxxx.put(_snowmanxxxxxxx[0], Maps.newHashMap());
                     if (_snowmanxxxxxxx.length > 1 && _snowmanxxxxxxx[1].endsWith(")") && _snowmanxxxxxxx[1].length() > 1) {
                        String[] _snowmanxxxxxxxx = _snowmanxxxxxxx[1].substring(0, _snowmanxxxxxxx[1].length() - 1).split(" ");

                        for (String _snowmanxxxxxxxxx : _snowmanxxxxxxxx) {
                           String[] _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.split("=", 2);
                           if (_snowmanxxxxxxxxxx.length == 2) {
                              _snowmanxxx.get(_snowmanxxxxxxx[0]).put(_snowmanxxxxxxxxxx[0], _snowmanxxxxxxxxxx[1]);
                           }
                        }
                     }
                  }
               }
            } else {
               _snowmanxxx.put("village", Maps.newHashMap());
            }
         }
      } else {
         _snowmanxxxx = Lists.newArrayList();
         _snowmanxxxx.add(Pair.of(1, "minecraft:bedrock"));
         _snowmanxxxx.add(Pair.of(2, "minecraft:dirt"));
         _snowmanxxxx.add(Pair.of(1, "minecraft:grass_block"));
         _snowmanxxx.put("village", Maps.newHashMap());
      }

      T _snowmanxxxxx = (T)_snowman.createList(
         _snowmanxxxx.stream()
            .map(
               _snowmanxxxxxxx -> _snowman.createMap(
                     ImmutableMap.of(
                        _snowman.createString("height"),
                        _snowman.createInt((Integer)_snowmanxxxxxxx.getFirst()),
                        _snowman.createString("block"),
                        _snowman.createString((String)_snowmanxxxxxxx.getSecond())
                     )
                  )
            )
      );
      T _snowmanxxxxxxx = (T)_snowman.createMap(
         _snowmanxxx.entrySet()
            .stream()
            .map(
               _snowmanxxxxxxxx -> Pair.of(
                     _snowman.createString(((String)_snowmanxxxxxxxx.getKey()).toLowerCase(Locale.ROOT)),
                     _snowman.createMap(
                        ((Map)_snowmanxxxxxxxx.getValue())
                           .entrySet()
                           .stream()
                           .map(_snowmanxxxxxxxxxx -> Pair.of(_snowman.createString((String)_snowmanxxxxxxxxxx.getKey()), _snowman.createString((String)_snowmanxxxxxxxxxx.getValue())))
                           .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
                     )
                  )
            )
            .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
      );
      return new Dynamic(
         _snowman,
         _snowman.createMap(ImmutableMap.of(_snowman.createString("layers"), _snowmanxxxxx, _snowman.createString("biome"), _snowman.createString(_snowmanxx), _snowman.createString("structures"), _snowmanxxxxxxx))
      );
   }

   @Nullable
   private static Pair<Integer, String> parseFlatLayer(String layer) {
      String[] _snowman = layer.split("\\*", 2);
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

   private static List<Pair<Integer, String>> parseFlatLayers(String layers) {
      List<Pair<Integer, String>> _snowman = Lists.newArrayList();
      String[] _snowmanx = layers.split(",");

      for (String _snowmanxx : _snowmanx) {
         Pair<Integer, String> _snowmanxxx = parseFlatLayer(_snowmanxx);
         if (_snowmanxxx == null) {
            return Collections.emptyList();
         }

         _snowman.add(_snowmanxxx);
      }

      return _snowman;
   }
}
