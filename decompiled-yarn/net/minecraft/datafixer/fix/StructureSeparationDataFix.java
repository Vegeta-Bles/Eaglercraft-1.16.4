package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class StructureSeparationDataFix extends DataFix {
   private static final ImmutableMap<String, StructureSeparationDataFix.Information> STRUCTURE_SPACING = ImmutableMap.builder()
      .put("minecraft:village", new StructureSeparationDataFix.Information(32, 8, 10387312))
      .put("minecraft:desert_pyramid", new StructureSeparationDataFix.Information(32, 8, 14357617))
      .put("minecraft:igloo", new StructureSeparationDataFix.Information(32, 8, 14357618))
      .put("minecraft:jungle_pyramid", new StructureSeparationDataFix.Information(32, 8, 14357619))
      .put("minecraft:swamp_hut", new StructureSeparationDataFix.Information(32, 8, 14357620))
      .put("minecraft:pillager_outpost", new StructureSeparationDataFix.Information(32, 8, 165745296))
      .put("minecraft:monument", new StructureSeparationDataFix.Information(32, 5, 10387313))
      .put("minecraft:endcity", new StructureSeparationDataFix.Information(20, 11, 10387313))
      .put("minecraft:mansion", new StructureSeparationDataFix.Information(80, 20, 10387319))
      .build();

   public StructureSeparationDataFix(Schema _snowman) {
      super(_snowman, true);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "WorldGenSettings building",
         this.getInputSchema().getType(TypeReferences.CHUNK_GENERATOR_SETTINGS),
         _snowman -> _snowman.update(DSL.remainderFinder(), StructureSeparationDataFix::method_28271)
      );
   }

   private static <T> Dynamic<T> method_28268(long _snowman, DynamicLike<T> _snowman, Dynamic<T> _snowman, Dynamic<T> _snowman) {
      return _snowman.createMap(
         ImmutableMap.of(
            _snowman.createString("type"),
            _snowman.createString("minecraft:noise"),
            _snowman.createString("biome_source"),
            _snowman,
            _snowman.createString("seed"),
            _snowman.createLong(_snowman),
            _snowman.createString("settings"),
            _snowman
         )
      );
   }

   private static <T> Dynamic<T> method_28272(Dynamic<T> _snowman, long _snowman, boolean _snowman, boolean _snowman) {
      Builder<Dynamic<T>, Dynamic<T>> _snowmanxxxx = ImmutableMap.builder()
         .put(_snowman.createString("type"), _snowman.createString("minecraft:vanilla_layered"))
         .put(_snowman.createString("seed"), _snowman.createLong(_snowman))
         .put(_snowman.createString("large_biomes"), _snowman.createBoolean(_snowman));
      if (_snowman) {
         _snowmanxxxx.put(_snowman.createString("legacy_biome_init_layer"), _snowman.createBoolean(_snowman));
      }

      return _snowman.createMap(_snowmanxxxx.build());
   }

   private static <T> Dynamic<T> method_28271(Dynamic<T> _snowman) {
      DynamicOps<T> _snowmanx = _snowman.getOps();
      long _snowmanxx = _snowman.get("RandomSeed").asLong(0L);
      Optional<String> _snowmanxxx = _snowman.get("generatorName").asString().map(_snowmanxxxx -> _snowmanxxxx.toLowerCase(Locale.ROOT)).result();
      Optional<String> _snowmanxxxx = _snowman.get("legacy_custom_options")
         .asString()
         .result()
         .map(Optional::of)
         .orElseGet(() -> _snowman.equals(Optional.of("customized")) ? _snowman.get("generatorOptions").asString().result() : Optional.empty());
      boolean _snowmanxxxxx = false;
      Dynamic<T> _snowmanxxxxxx;
      if (_snowmanxxx.equals(Optional.of("customized"))) {
         _snowmanxxxxxx = method_29916(_snowman, _snowmanxx);
      } else if (!_snowmanxxx.isPresent()) {
         _snowmanxxxxxx = method_29916(_snowman, _snowmanxx);
      } else {
         String var8 = _snowmanxxx.get();
         switch (var8) {
            case "flat":
               OptionalDynamic<T> _snowmanxxxxxxx = _snowman.get("generatorOptions");
               Map<Dynamic<T>, Dynamic<T>> _snowmanxxxxxxxx = method_28275(_snowmanx, _snowmanxxxxxxx);
               _snowmanxxxxxx = _snowman.createMap(
                  ImmutableMap.of(
                     _snowman.createString("type"),
                     _snowman.createString("minecraft:flat"),
                     _snowman.createString("settings"),
                     _snowman.createMap(
                        ImmutableMap.of(
                           _snowman.createString("structures"),
                           _snowman.createMap(_snowmanxxxxxxxx),
                           _snowman.createString("layers"),
                           _snowmanxxxxxxx.get("layers")
                              .result()
                              .orElseGet(
                                 () -> _snowman.createList(
                                       Stream.of(
                                          _snowman.createMap(
                                             ImmutableMap.of(
                                                _snowman.createString("height"), _snowman.createInt(1), _snowman.createString("block"), _snowman.createString("minecraft:bedrock")
                                             )
                                          ),
                                          _snowman.createMap(
                                             ImmutableMap.of(
                                                _snowman.createString("height"), _snowman.createInt(2), _snowman.createString("block"), _snowman.createString("minecraft:dirt")
                                             )
                                          ),
                                          _snowman.createMap(
                                             ImmutableMap.of(
                                                _snowman.createString("height"), _snowman.createInt(1), _snowman.createString("block"), _snowman.createString("minecraft:grass_block")
                                             )
                                          )
                                       )
                                    )
                              ),
                           _snowman.createString("biome"),
                           _snowman.createString(_snowmanxxxxxxx.get("biome").asString("minecraft:plains"))
                        )
                     )
                  )
               );
               break;
            case "debug_all_block_states":
               _snowmanxxxxxx = _snowman.createMap(ImmutableMap.of(_snowman.createString("type"), _snowman.createString("minecraft:debug")));
               break;
            case "buffet":
               OptionalDynamic<T> _snowmanxxxxxxxxx = _snowman.get("generatorOptions");
               OptionalDynamic<?> _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.get("chunk_generator");
               Optional<String> _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.get("type").asString().result();
               Dynamic<T> _snowmanxxxxxxxxxxxx;
               if (Objects.equals(_snowmanxxxxxxxxxxx, Optional.of("minecraft:caves"))) {
                  _snowmanxxxxxxxxxxxx = _snowman.createString("minecraft:caves");
                  _snowmanxxxxx = true;
               } else if (Objects.equals(_snowmanxxxxxxxxxxx, Optional.of("minecraft:floating_islands"))) {
                  _snowmanxxxxxxxxxxxx = _snowman.createString("minecraft:floating_islands");
               } else {
                  _snowmanxxxxxxxxxxxx = _snowman.createString("minecraft:overworld");
               }

               Dynamic<T> _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx.get("biome_source")
                  .result()
                  .orElseGet(() -> _snowman.createMap(ImmutableMap.of(_snowman.createString("type"), _snowman.createString("minecraft:fixed"))));
               Dynamic<T> _snowmanxxxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxxx.get("type").asString().result().equals(Optional.of("minecraft:fixed"))) {
                  String _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.get("options")
                     .get("biomes")
                     .asStream()
                     .findFirst()
                     .flatMap(_snowmanxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxx.asString().result())
                     .orElse("minecraft:ocean");
                  _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.remove("options").set("biome", _snowman.createString(_snowmanxxxxxxxxxxxxxxx));
               } else {
                  _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
               }

               _snowmanxxxxxx = method_28268(_snowmanxx, _snowman, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
               break;
            default:
               boolean _snowmanxxxxxxxxxxxxx = _snowmanxxx.get().equals("default");
               boolean _snowmanxxxxxxxxxxxxxx = _snowmanxxx.get().equals("default_1_1") || _snowmanxxxxxxxxxxxxx && _snowman.get("generatorVersion").asInt(0) == 0;
               boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxx.get().equals("amplified");
               boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanxxx.get().equals("largebiomes");
               _snowmanxxxxxx = method_28268(
                  _snowmanxx,
                  _snowman,
                  _snowman.createString(_snowmanxxxxxxxxxxxxxxx ? "minecraft:amplified" : "minecraft:overworld"),
                  method_28272(_snowman, _snowmanxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx)
               );
         }
      }

      boolean _snowmanxxxxxxx = _snowman.get("MapFeatures").asBoolean(true);
      boolean _snowmanxxxxxxxx = _snowman.get("BonusChest").asBoolean(false);
      Builder<T, T> _snowmanxxxxxxxxxxxxx = ImmutableMap.builder();
      _snowmanxxxxxxxxxxxxx.put(_snowmanx.createString("seed"), _snowmanx.createLong(_snowmanxx));
      _snowmanxxxxxxxxxxxxx.put(_snowmanx.createString("generate_features"), _snowmanx.createBoolean(_snowmanxxxxxxx));
      _snowmanxxxxxxxxxxxxx.put(_snowmanx.createString("bonus_chest"), _snowmanx.createBoolean(_snowmanxxxxxxxx));
      _snowmanxxxxxxxxxxxxx.put(_snowmanx.createString("dimensions"), method_29917(_snowman, _snowmanxx, _snowmanxxxxxx, _snowmanxxxxx));
      _snowmanxxxx.ifPresent(_snowmanxxxxxxxxxxxxxx -> _snowman.put(_snowman.createString("legacy_custom_options"), _snowman.createString(_snowmanxxxxxxxxxxxxxx)));
      return new Dynamic(_snowmanx, _snowmanx.createMap(_snowmanxxxxxxxxxxxxx.build()));
   }

   protected static <T> Dynamic<T> method_29916(Dynamic<T> _snowman, long _snowman) {
      return method_28268(_snowman, _snowman, _snowman.createString("minecraft:overworld"), method_28272(_snowman, _snowman, false, false));
   }

   protected static <T> T method_29917(Dynamic<T> _snowman, long _snowman, Dynamic<T> _snowman, boolean _snowman) {
      DynamicOps<T> _snowmanxxxx = _snowman.getOps();
      return (T)_snowmanxxxx.createMap(
         ImmutableMap.of(
            _snowmanxxxx.createString("minecraft:overworld"),
            _snowmanxxxx.createMap(
               ImmutableMap.of(
                  _snowmanxxxx.createString("type"), _snowmanxxxx.createString("minecraft:overworld" + (_snowman ? "_caves" : "")), _snowmanxxxx.createString("generator"), _snowman.getValue()
               )
            ),
            _snowmanxxxx.createString("minecraft:the_nether"),
            _snowmanxxxx.createMap(
               ImmutableMap.of(
                  _snowmanxxxx.createString("type"),
                  _snowmanxxxx.createString("minecraft:the_nether"),
                  _snowmanxxxx.createString("generator"),
                  method_28268(
                        _snowman,
                        _snowman,
                        _snowman.createString("minecraft:nether"),
                        _snowman.createMap(
                           ImmutableMap.of(
                              _snowman.createString("type"),
                              _snowman.createString("minecraft:multi_noise"),
                              _snowman.createString("seed"),
                              _snowman.createLong(_snowman),
                              _snowman.createString("preset"),
                              _snowman.createString("minecraft:nether")
                           )
                        )
                     )
                     .getValue()
               )
            ),
            _snowmanxxxx.createString("minecraft:the_end"),
            _snowmanxxxx.createMap(
               ImmutableMap.of(
                  _snowmanxxxx.createString("type"),
                  _snowmanxxxx.createString("minecraft:the_end"),
                  _snowmanxxxx.createString("generator"),
                  method_28268(
                        _snowman,
                        _snowman,
                        _snowman.createString("minecraft:end"),
                        _snowman.createMap(ImmutableMap.of(_snowman.createString("type"), _snowman.createString("minecraft:the_end"), _snowman.createString("seed"), _snowman.createLong(_snowman)))
                     )
                     .getValue()
               )
            )
         )
      );
   }

   private static <T> Map<Dynamic<T>, Dynamic<T>> method_28275(DynamicOps<T> _snowman, OptionalDynamic<T> _snowman) {
      MutableInt _snowmanxx = new MutableInt(32);
      MutableInt _snowmanxxx = new MutableInt(3);
      MutableInt _snowmanxxxx = new MutableInt(128);
      MutableBoolean _snowmanxxxxx = new MutableBoolean(false);
      Map<String, StructureSeparationDataFix.Information> _snowmanxxxxxx = Maps.newHashMap();
      if (!_snowman.result().isPresent()) {
         _snowmanxxxxx.setTrue();
         _snowmanxxxxxx.put("minecraft:village", (StructureSeparationDataFix.Information)STRUCTURE_SPACING.get("minecraft:village"));
      }

      _snowman.get("structures")
         .flatMap(Dynamic::getMapValues)
         .result()
         .ifPresent(
            _snowmanxxxxxxx -> _snowmanxxxxxxx.forEach(
                  (_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx) -> _snowmanxxxxxxxxx.getMapValues()
                        .result()
                        .ifPresent(
                           _snowmanxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxx.forEach(
                                 (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx) -> {
                                    String _snowmanxxxxxxxx = _snowmanxxxxxxxxxx.asString("");
                                    String _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.asString("");
                                    String _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.asString("");
                                    if ("stronghold".equals(_snowmanxxxxxxxx)) {
                                       _snowman.setTrue();
                                       switch (_snowmanxxxxxxxxx) {
                                          case "distance":
                                             _snowman.setValue(method_28280(_snowmanxxxxxxxxxx, _snowman.getValue(), 1));
                                             return;
                                          case "spread":
                                             _snowman.setValue(method_28280(_snowmanxxxxxxxxxx, _snowman.getValue(), 1));
                                             return;
                                          case "count":
                                             _snowman.setValue(method_28280(_snowmanxxxxxxxxxx, _snowman.getValue(), 1));
                                             return;
                                       }
                                    } else {
                                       switch (_snowmanxxxxxxxxx) {
                                          case "distance":
                                             switch (_snowmanxxxxxxxx) {
                                                case "village":
                                                   method_28281(_snowman, "minecraft:village", _snowmanxxxxxxxxxx, 9);
                                                   return;
                                                case "biome_1":
                                                   method_28281(_snowman, "minecraft:desert_pyramid", _snowmanxxxxxxxxxx, 9);
                                                   method_28281(_snowman, "minecraft:igloo", _snowmanxxxxxxxxxx, 9);
                                                   method_28281(_snowman, "minecraft:jungle_pyramid", _snowmanxxxxxxxxxx, 9);
                                                   method_28281(_snowman, "minecraft:swamp_hut", _snowmanxxxxxxxxxx, 9);
                                                   method_28281(_snowman, "minecraft:pillager_outpost", _snowmanxxxxxxxxxx, 9);
                                                   return;
                                                case "endcity":
                                                   method_28281(_snowman, "minecraft:endcity", _snowmanxxxxxxxxxx, 1);
                                                   return;
                                                case "mansion":
                                                   method_28281(_snowman, "minecraft:mansion", _snowmanxxxxxxxxxx, 1);
                                                   return;
                                                default:
                                                   return;
                                             }
                                          case "separation":
                                             if ("oceanmonument".equals(_snowmanxxxxxxxx)) {
                                                StructureSeparationDataFix.Information _snowmanxxxxxxxxxxx = _snowman.getOrDefault(
                                                   "minecraft:monument", (StructureSeparationDataFix.Information)STRUCTURE_SPACING.get("minecraft:monument")
                                                );
                                                int _snowmanxxxxxxxxxxxx = method_28280(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx.separation, 1);
                                                _snowman.put(
                                                   "minecraft:monument",
                                                   new StructureSeparationDataFix.Information(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx.separation, _snowmanxxxxxxxxxxx.salt)
                                                );
                                             }

                                             return;
                                          case "spacing":
                                             if ("oceanmonument".equals(_snowmanxxxxxxxx)) {
                                                method_28281(_snowman, "minecraft:monument", _snowmanxxxxxxxxxx, 1);
                                             }

                                             return;
                                       }
                                    }
                                 }
                              )
                        )
               )
         );
      Builder<Dynamic<T>, Dynamic<T>> _snowmanxxxxxxx = ImmutableMap.builder();
      _snowmanxxxxxxx.put(
         _snowman.createString("structures"),
         _snowman.createMap(
            _snowmanxxxxxx.entrySet()
               .stream()
               .collect(
                  Collectors.toMap(
                     _snowmanxxxxxxxx -> _snowman.createString((String)_snowmanxxxxxxxx.getKey()),
                     _snowmanxxxxxxxx -> ((StructureSeparationDataFix.Information)_snowmanxxxxxxxx.getValue()).method_28288(_snowman)
                  )
               )
         )
      );
      if (_snowmanxxxxx.isTrue()) {
         _snowmanxxxxxxx.put(
            _snowman.createString("stronghold"),
            _snowman.createMap(
               ImmutableMap.of(
                  _snowman.createString("distance"),
                  _snowman.createInt(_snowmanxx.getValue()),
                  _snowman.createString("spread"),
                  _snowman.createInt(_snowmanxxx.getValue()),
                  _snowman.createString("count"),
                  _snowman.createInt(_snowmanxxxx.getValue())
               )
            )
         );
      }

      return _snowmanxxxxxxx.build();
   }

   private static int method_28279(String _snowman, int _snowman) {
      return NumberUtils.toInt(_snowman, _snowman);
   }

   private static int method_28280(String _snowman, int _snowman, int _snowman) {
      return Math.max(_snowman, method_28279(_snowman, _snowman));
   }

   private static void method_28281(Map<String, StructureSeparationDataFix.Information> _snowman, String _snowman, String _snowman, int _snowman) {
      StructureSeparationDataFix.Information _snowmanxxxx = _snowman.getOrDefault(_snowman, (StructureSeparationDataFix.Information)STRUCTURE_SPACING.get(_snowman));
      int _snowmanxxxxx = method_28280(_snowman, _snowmanxxxx.spacing, _snowman);
      _snowman.put(_snowman, new StructureSeparationDataFix.Information(_snowmanxxxxx, _snowmanxxxx.separation, _snowmanxxxx.salt));
   }

   static final class Information {
      public static final Codec<StructureSeparationDataFix.Information> CODEC = RecordCodecBuilder.create(
         _snowman -> _snowman.group(
                  Codec.INT.fieldOf("spacing").forGetter(_snowmanx -> _snowmanx.spacing),
                  Codec.INT.fieldOf("separation").forGetter(_snowmanx -> _snowmanx.separation),
                  Codec.INT.fieldOf("salt").forGetter(_snowmanx -> _snowmanx.salt)
               )
               .apply(_snowman, StructureSeparationDataFix.Information::new)
      );
      private final int spacing;
      private final int separation;
      private final int salt;

      public Information(int spacing, int separation, int salt) {
         this.spacing = spacing;
         this.separation = separation;
         this.salt = salt;
      }

      public <T> Dynamic<T> method_28288(DynamicOps<T> _snowman) {
         return new Dynamic(_snowman, CODEC.encodeStart(_snowman, this).result().orElse(_snowman.emptyMap()));
      }
   }
}
