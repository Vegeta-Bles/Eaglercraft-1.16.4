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
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class ali extends DataFix {
   private static final ImmutableMap<String, ali.a> a = ImmutableMap.builder()
      .put("minecraft:village", new ali.a(32, 8, 10387312))
      .put("minecraft:desert_pyramid", new ali.a(32, 8, 14357617))
      .put("minecraft:igloo", new ali.a(32, 8, 14357618))
      .put("minecraft:jungle_pyramid", new ali.a(32, 8, 14357619))
      .put("minecraft:swamp_hut", new ali.a(32, 8, 14357620))
      .put("minecraft:pillager_outpost", new ali.a(32, 8, 165745296))
      .put("minecraft:monument", new ali.a(32, 5, 10387313))
      .put("minecraft:endcity", new ali.a(20, 11, 10387313))
      .put("minecraft:mansion", new ali.a(80, 20, 10387319))
      .build();

   public ali(Schema var1) {
      super(_snowman, true);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("WorldGenSettings building", this.getInputSchema().getType(akn.y), var0 -> var0.update(DSL.remainderFinder(), ali::a));
   }

   private static <T> Dynamic<T> a(long var0, DynamicLike<T> var2, Dynamic<T> var3, Dynamic<T> var4) {
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

   private static <T> Dynamic<T> a(Dynamic<T> var0, long var1, boolean var3, boolean var4) {
      Builder<Dynamic<T>, Dynamic<T>> _snowman = ImmutableMap.builder()
         .put(_snowman.createString("type"), _snowman.createString("minecraft:vanilla_layered"))
         .put(_snowman.createString("seed"), _snowman.createLong(_snowman))
         .put(_snowman.createString("large_biomes"), _snowman.createBoolean(_snowman));
      if (_snowman) {
         _snowman.put(_snowman.createString("legacy_biome_init_layer"), _snowman.createBoolean(_snowman));
      }

      return _snowman.createMap(_snowman.build());
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      DynamicOps<T> _snowman = _snowman.getOps();
      long _snowmanx = _snowman.get("RandomSeed").asLong(0L);
      Optional<String> _snowmanxx = _snowman.get("generatorName").asString().map(var0x -> var0x.toLowerCase(Locale.ROOT)).result();
      Optional<String> _snowmanxxx = _snowman.get("legacy_custom_options")
         .asString()
         .result()
         .map(Optional::of)
         .orElseGet(() -> _snowman.equals(Optional.of("customized")) ? _snowman.get("generatorOptions").asString().result() : Optional.empty());
      boolean _snowmanxxxx = false;
      Dynamic<T> _snowmanxxxxx;
      if (_snowmanxx.equals(Optional.of("customized"))) {
         _snowmanxxxxx = a(_snowman, _snowmanx);
      } else if (!_snowmanxx.isPresent()) {
         _snowmanxxxxx = a(_snowman, _snowmanx);
      } else {
         String var8 = _snowmanxx.get();
         switch (var8) {
            case "flat":
               OptionalDynamic<T> _snowmanxxxxxx = _snowman.get("generatorOptions");
               Map<Dynamic<T>, Dynamic<T>> _snowmanxxxxxxx = a(_snowman, _snowmanxxxxxx);
               _snowmanxxxxx = _snowman.createMap(
                  ImmutableMap.of(
                     _snowman.createString("type"),
                     _snowman.createString("minecraft:flat"),
                     _snowman.createString("settings"),
                     _snowman.createMap(
                        ImmutableMap.of(
                           _snowman.createString("structures"),
                           _snowman.createMap(_snowmanxxxxxxx),
                           _snowman.createString("layers"),
                           _snowmanxxxxxx.get("layers")
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
                           _snowman.createString(_snowmanxxxxxx.get("biome").asString("minecraft:plains"))
                        )
                     )
                  )
               );
               break;
            case "debug_all_block_states":
               _snowmanxxxxx = _snowman.createMap(ImmutableMap.of(_snowman.createString("type"), _snowman.createString("minecraft:debug")));
               break;
            case "buffet":
               OptionalDynamic<T> _snowmanxxxxxxxx = _snowman.get("generatorOptions");
               OptionalDynamic<?> _snowmanxxxxxxxxx = _snowmanxxxxxxxx.get("chunk_generator");
               Optional<String> _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.get("type").asString().result();
               Dynamic<T> _snowmanxxxxxxxxxxx;
               if (Objects.equals(_snowmanxxxxxxxxxx, Optional.of("minecraft:caves"))) {
                  _snowmanxxxxxxxxxxx = _snowman.createString("minecraft:caves");
                  _snowmanxxxx = true;
               } else if (Objects.equals(_snowmanxxxxxxxxxx, Optional.of("minecraft:floating_islands"))) {
                  _snowmanxxxxxxxxxxx = _snowman.createString("minecraft:floating_islands");
               } else {
                  _snowmanxxxxxxxxxxx = _snowman.createString("minecraft:overworld");
               }

               Dynamic<T> _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxx.get("biome_source")
                  .result()
                  .orElseGet(() -> _snowman.createMap(ImmutableMap.of(_snowman.createString("type"), _snowman.createString("minecraft:fixed"))));
               Dynamic<T> _snowmanxxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxx.get("type").asString().result().equals(Optional.of("minecraft:fixed"))) {
                  String _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.get("options")
                     .get("biomes")
                     .asStream()
                     .findFirst()
                     .flatMap(var0x -> var0x.asString().result())
                     .orElse("minecraft:ocean");
                  _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.remove("options").set("biome", _snowman.createString(_snowmanxxxxxxxxxxxxxx));
               } else {
                  _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
               }

               _snowmanxxxxx = a(_snowmanx, _snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
               break;
            default:
               boolean _snowmanxxxxxxxxxxxx = _snowmanxx.get().equals("default");
               boolean _snowmanxxxxxxxxxxxxx = _snowmanxx.get().equals("default_1_1") || _snowmanxxxxxxxxxxxx && _snowman.get("generatorVersion").asInt(0) == 0;
               boolean _snowmanxxxxxxxxxxxxxx = _snowmanxx.get().equals("amplified");
               boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxx.get().equals("largebiomes");
               _snowmanxxxxx = a(_snowmanx, _snowman, _snowman.createString(_snowmanxxxxxxxxxxxxxx ? "minecraft:amplified" : "minecraft:overworld"), a(_snowman, _snowmanx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx));
         }
      }

      boolean _snowmanxxxxxx = _snowman.get("MapFeatures").asBoolean(true);
      boolean _snowmanxxxxxxx = _snowman.get("BonusChest").asBoolean(false);
      Builder<T, T> _snowmanxxxxxxxxxxxx = ImmutableMap.builder();
      _snowmanxxxxxxxxxxxx.put(_snowman.createString("seed"), _snowman.createLong(_snowmanx));
      _snowmanxxxxxxxxxxxx.put(_snowman.createString("generate_features"), _snowman.createBoolean(_snowmanxxxxxx));
      _snowmanxxxxxxxxxxxx.put(_snowman.createString("bonus_chest"), _snowman.createBoolean(_snowmanxxxxxxx));
      _snowmanxxxxxxxxxxxx.put(_snowman.createString("dimensions"), a(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxxx));
      _snowmanxxx.ifPresent(var2x -> _snowman.put(_snowman.createString("legacy_custom_options"), _snowman.createString(var2x)));
      return new Dynamic(_snowman, _snowman.createMap(_snowmanxxxxxxxxxxxx.build()));
   }

   protected static <T> Dynamic<T> a(Dynamic<T> var0, long var1) {
      return a(_snowman, _snowman, _snowman.createString("minecraft:overworld"), a(_snowman, _snowman, false, false));
   }

   protected static <T> T a(Dynamic<T> var0, long var1, Dynamic<T> var3, boolean var4) {
      DynamicOps<T> _snowman = _snowman.getOps();
      return (T)_snowman.createMap(
         ImmutableMap.of(
            _snowman.createString("minecraft:overworld"),
            _snowman.createMap(
               ImmutableMap.of(_snowman.createString("type"), _snowman.createString("minecraft:overworld" + (_snowman ? "_caves" : "")), _snowman.createString("generator"), _snowman.getValue())
            ),
            _snowman.createString("minecraft:the_nether"),
            _snowman.createMap(
               ImmutableMap.of(
                  _snowman.createString("type"),
                  _snowman.createString("minecraft:the_nether"),
                  _snowman.createString("generator"),
                  a(
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
            _snowman.createString("minecraft:the_end"),
            _snowman.createMap(
               ImmutableMap.of(
                  _snowman.createString("type"),
                  _snowman.createString("minecraft:the_end"),
                  _snowman.createString("generator"),
                  a(
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

   private static <T> Map<Dynamic<T>, Dynamic<T>> a(DynamicOps<T> var0, OptionalDynamic<T> var1) {
      MutableInt _snowman = new MutableInt(32);
      MutableInt _snowmanx = new MutableInt(3);
      MutableInt _snowmanxx = new MutableInt(128);
      MutableBoolean _snowmanxxx = new MutableBoolean(false);
      Map<String, ali.a> _snowmanxxxx = Maps.newHashMap();
      if (!_snowman.result().isPresent()) {
         _snowmanxxx.setTrue();
         _snowmanxxxx.put("minecraft:village", (ali.a)a.get("minecraft:village"));
      }

      _snowman.get("structures")
         .flatMap(Dynamic::getMapValues)
         .result()
         .ifPresent(var5x -> var5x.forEach((var5xx, var6x) -> var6x.getMapValues().result().ifPresent(var6xx -> var6xx.forEach((var6xxx, var7x) -> {
                     String _snowmanxxxxx = var5xx.asString("");
                     String _snowmanx = var6xxx.asString("");
                     String _snowmanxx = var7x.asString("");
                     if ("stronghold".equals(_snowmanxxxxx)) {
                        _snowman.setTrue();
                        switch (_snowmanx) {
                           case "distance":
                              _snowman.setValue(a(_snowmanxx, _snowman.getValue(), 1));
                              return;
                           case "spread":
                              _snowman.setValue(a(_snowmanxx, _snowman.getValue(), 1));
                              return;
                           case "count":
                              _snowman.setValue(a(_snowmanxx, _snowman.getValue(), 1));
                              return;
                        }
                     } else {
                        switch (_snowmanx) {
                           case "distance":
                              switch (_snowmanxxxxx) {
                                 case "village":
                                    a(_snowman, "minecraft:village", _snowmanxx, 9);
                                    return;
                                 case "biome_1":
                                    a(_snowman, "minecraft:desert_pyramid", _snowmanxx, 9);
                                    a(_snowman, "minecraft:igloo", _snowmanxx, 9);
                                    a(_snowman, "minecraft:jungle_pyramid", _snowmanxx, 9);
                                    a(_snowman, "minecraft:swamp_hut", _snowmanxx, 9);
                                    a(_snowman, "minecraft:pillager_outpost", _snowmanxx, 9);
                                    return;
                                 case "endcity":
                                    a(_snowman, "minecraft:endcity", _snowmanxx, 1);
                                    return;
                                 case "mansion":
                                    a(_snowman, "minecraft:mansion", _snowmanxx, 1);
                                    return;
                                 default:
                                    return;
                              }
                           case "separation":
                              if ("oceanmonument".equals(_snowmanxxxxx)) {
                                 ali.a _snowmanxxx = _snowman.getOrDefault("minecraft:monument", (ali.a)a.get("minecraft:monument"));
                                 int _snowmanxxxx = a(_snowmanxx, _snowmanxxx.c, 1);
                                 _snowman.put("minecraft:monument", new ali.a(_snowmanxxxx, _snowmanxxx.c, _snowmanxxx.d));
                              }

                              return;
                           case "spacing":
                              if ("oceanmonument".equals(_snowmanxxxxx)) {
                                 a(_snowman, "minecraft:monument", _snowmanxx, 1);
                              }

                              return;
                        }
                     }
                  }))));
      Builder<Dynamic<T>, Dynamic<T>> _snowmanxxxxx = ImmutableMap.builder();
      _snowmanxxxxx.put(
         _snowman.createString("structures"),
         _snowman.createMap(_snowmanxxxx.entrySet().stream().collect(Collectors.toMap(var1x -> _snowman.createString(var1x.getKey()), var1x -> var1x.getValue().a(_snowman))))
      );
      if (_snowmanxxx.isTrue()) {
         _snowmanxxxxx.put(
            _snowman.createString("stronghold"),
            _snowman.createMap(
               ImmutableMap.of(
                  _snowman.createString("distance"),
                  _snowman.createInt(_snowman.getValue()),
                  _snowman.createString("spread"),
                  _snowman.createInt(_snowmanx.getValue()),
                  _snowman.createString("count"),
                  _snowman.createInt(_snowmanxx.getValue())
               )
            )
         );
      }

      return _snowmanxxxxx.build();
   }

   private static int a(String var0, int var1) {
      return NumberUtils.toInt(_snowman, _snowman);
   }

   private static int a(String var0, int var1, int var2) {
      return Math.max(_snowman, a(_snowman, _snowman));
   }

   private static void a(Map<String, ali.a> var0, String var1, String var2, int var3) {
      ali.a _snowman = _snowman.getOrDefault(_snowman, (ali.a)a.get(_snowman));
      int _snowmanx = a(_snowman, _snowman.b, _snowman);
      _snowman.put(_snowman, new ali.a(_snowmanx, _snowman.c, _snowman.d));
   }

   static final class a {
      public static final Codec<ali.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("spacing").forGetter(var0x -> var0x.b),
                  Codec.INT.fieldOf("separation").forGetter(var0x -> var0x.c),
                  Codec.INT.fieldOf("salt").forGetter(var0x -> var0x.d)
               )
               .apply(var0, ali.a::new)
      );
      private final int b;
      private final int c;
      private final int d;

      public a(int var1, int var2, int var3) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public <T> Dynamic<T> a(DynamicOps<T> var1) {
         return new Dynamic(_snowman, a.encodeStart(_snowman, this).result().orElse(_snowman.emptyMap()));
      }
   }
}
