package net.minecraft.client.render.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.block.entity.BellBlockEntityRenderer;
import net.minecraft.client.render.block.entity.ConduitBlockEntityRenderer;
import net.minecraft.client.render.block.entity.EnchantingTableBlockEntityRenderer;
import net.minecraft.client.render.model.json.ItemModelGenerator;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelVariantMap;
import net.minecraft.client.render.model.json.MultipartModelComponent;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.AffineTransformation;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelLoader {
   public static final SpriteIdentifier FIRE_0 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/fire_0"));
   public static final SpriteIdentifier FIRE_1 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/fire_1"));
   public static final SpriteIdentifier LAVA_FLOW = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/lava_flow"));
   public static final SpriteIdentifier WATER_FLOW = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/water_flow"));
   public static final SpriteIdentifier WATER_OVERLAY = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/water_overlay"));
   public static final SpriteIdentifier BANNER_BASE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/banner_base"));
   public static final SpriteIdentifier SHIELD_BASE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/shield_base"));
   public static final SpriteIdentifier SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(
      SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/shield_base_nopattern")
   );
   public static final List<Identifier> BLOCK_DESTRUCTION_STAGES = IntStream.range(0, 10)
      .mapToObj(_snowman -> new Identifier("block/destroy_stage_" + _snowman))
      .collect(Collectors.toList());
   public static final List<Identifier> BLOCK_DESTRUCTION_STAGE_TEXTURES = BLOCK_DESTRUCTION_STAGES.stream()
      .map(_snowman -> new Identifier("textures/" + _snowman.getPath() + ".png"))
      .collect(Collectors.toList());
   public static final List<RenderLayer> BLOCK_DESTRUCTION_RENDER_LAYERS = BLOCK_DESTRUCTION_STAGE_TEXTURES.stream()
      .map(RenderLayer::getBlockBreaking)
      .collect(Collectors.toList());
   private static final Set<SpriteIdentifier> DEFAULT_TEXTURES = Util.make(Sets.newHashSet(), _snowman -> {
      _snowman.add(WATER_FLOW);
      _snowman.add(LAVA_FLOW);
      _snowman.add(WATER_OVERLAY);
      _snowman.add(FIRE_0);
      _snowman.add(FIRE_1);
      _snowman.add(BellBlockEntityRenderer.BELL_BODY_TEXTURE);
      _snowman.add(ConduitBlockEntityRenderer.BASE_TEXTURE);
      _snowman.add(ConduitBlockEntityRenderer.CAGE_TEXTURE);
      _snowman.add(ConduitBlockEntityRenderer.WIND_TEXTURE);
      _snowman.add(ConduitBlockEntityRenderer.WIND_VERTICAL_TEXTURE);
      _snowman.add(ConduitBlockEntityRenderer.OPEN_EYE_TEXTURE);
      _snowman.add(ConduitBlockEntityRenderer.CLOSED_EYE_TEXTURE);
      _snowman.add(EnchantingTableBlockEntityRenderer.BOOK_TEXTURE);
      _snowman.add(BANNER_BASE);
      _snowman.add(SHIELD_BASE);
      _snowman.add(SHIELD_BASE_NO_PATTERN);

      for (Identifier _snowmanx : BLOCK_DESTRUCTION_STAGES) {
         _snowman.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, _snowmanx));
      }

      _snowman.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_HELMET_SLOT_TEXTURE));
      _snowman.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_CHESTPLATE_SLOT_TEXTURE));
      _snowman.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_LEGGINGS_SLOT_TEXTURE));
      _snowman.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_BOOTS_SLOT_TEXTURE));
      _snowman.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT));
      TexturedRenderLayers.addDefaultTextures(_snowman::add);
   });
   private static final Logger LOGGER = LogManager.getLogger();
   public static final ModelIdentifier MISSING = new ModelIdentifier("builtin/missing", "missing");
   private static final String field_21773 = MISSING.toString();
   @VisibleForTesting
   public static final String MISSING_DEFINITION = ("{    'textures': {       'particle': '"
         + MissingSprite.getMissingSpriteId().getPath()
         + "',       'missingno': '"
         + MissingSprite.getMissingSpriteId().getPath()
         + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}")
      .replace('\'', '"');
   private static final Map<String, String> BUILTIN_MODEL_DEFINITIONS = Maps.newHashMap(ImmutableMap.of("missing", MISSING_DEFINITION));
   private static final Splitter COMMA_SPLITTER = Splitter.on(',');
   private static final Splitter KEY_VALUE_SPLITTER = Splitter.on('=').limit(2);
   public static final JsonUnbakedModel GENERATION_MARKER = Util.make(
      JsonUnbakedModel.deserialize("{\"gui_light\": \"front\"}"), _snowman -> _snowman.id = "generation marker"
   );
   public static final JsonUnbakedModel BLOCK_ENTITY_MARKER = Util.make(
      JsonUnbakedModel.deserialize("{\"gui_light\": \"side\"}"), _snowman -> _snowman.id = "block entity marker"
   );
   private static final StateManager<Block, BlockState> ITEM_FRAME_STATE_FACTORY = new StateManager.Builder<Block, BlockState>(Blocks.AIR)
      .add(BooleanProperty.of("map"))
      .build(Block::getDefaultState, BlockState::new);
   private static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();
   private static final Map<Identifier, StateManager<Block, BlockState>> STATIC_DEFINITIONS = ImmutableMap.of(
      new Identifier("item_frame"), ITEM_FRAME_STATE_FACTORY
   );
   private final ResourceManager resourceManager;
   @Nullable
   private SpriteAtlasManager spriteAtlasManager;
   private final BlockColors blockColors;
   private final Set<Identifier> modelsToLoad = Sets.newHashSet();
   private final ModelVariantMap.DeserializationContext variantMapDeserializationContext = new ModelVariantMap.DeserializationContext();
   private final Map<Identifier, UnbakedModel> unbakedModels = Maps.newHashMap();
   private final Map<Triple<Identifier, AffineTransformation, Boolean>, BakedModel> bakedModelCache = Maps.newHashMap();
   private final Map<Identifier, UnbakedModel> modelsToBake = Maps.newHashMap();
   private final Map<Identifier, BakedModel> bakedModels = Maps.newHashMap();
   private final Map<Identifier, Pair<SpriteAtlasTexture, SpriteAtlasTexture.Data>> spriteAtlasData;
   private int nextStateId = 1;
   private final Object2IntMap<BlockState> stateLookup = Util.make(new Object2IntOpenHashMap(), _snowmanxxx -> _snowmanxxx.defaultReturnValue(-1));

   public ModelLoader(ResourceManager resourceManager, BlockColors _snowman, Profiler _snowman, int _snowman) {
      this.resourceManager = resourceManager;
      this.blockColors = _snowman;
      _snowman.push("missing_model");

      try {
         this.unbakedModels.put(MISSING, this.loadModelFromJson(MISSING));
         this.addModel(MISSING);
      } catch (IOException var12) {
         LOGGER.error("Error loading missing model, should never happen :(", var12);
         throw new RuntimeException(var12);
      }

      _snowman.swap("static_definitions");
      STATIC_DEFINITIONS.forEach((_snowmanxxxx, _snowmanxxx) -> _snowmanxxx.getStates().forEach(_snowmanxxxxx -> this.addModel(BlockModels.getModelId(_snowmanxxxx, _snowmanxxxxx))));
      _snowman.swap("blocks");

      for (Block _snowmanxxx : Registry.BLOCK) {
         _snowmanxxx.getStateManager().getStates().forEach(_snowmanxxxx -> this.addModel(BlockModels.getModelId(_snowmanxxxx)));
      }

      _snowman.swap("items");

      for (Identifier _snowmanxxx : Registry.ITEM.getIds()) {
         this.addModel(new ModelIdentifier(_snowmanxxx, "inventory"));
      }

      _snowman.swap("special");
      this.addModel(new ModelIdentifier("minecraft:trident_in_hand#inventory"));
      _snowman.swap("textures");
      Set<Pair<String, String>> _snowmanxxx = Sets.newLinkedHashSet();
      Set<SpriteIdentifier> _snowmanxxxx = this.modelsToBake
         .values()
         .stream()
         .flatMap(_snowmanxxxxx -> _snowmanxxxxx.getTextureDependencies(this::getOrLoadModel, _snowman).stream())
         .collect(Collectors.toSet());
      _snowmanxxxx.addAll(DEFAULT_TEXTURES);
      _snowmanxxx.stream()
         .filter(_snowmanxxxxx -> !((String)_snowmanxxxxx.getSecond()).equals(field_21773))
         .forEach(_snowmanxxxxx -> LOGGER.warn("Unable to resolve texture reference: {} in {}", _snowmanxxxxx.getFirst(), _snowmanxxxxx.getSecond()));
      Map<Identifier, List<SpriteIdentifier>> _snowmanxxxxx = _snowmanxxxx.stream().collect(Collectors.groupingBy(SpriteIdentifier::getAtlasId));
      _snowman.swap("stitching");
      this.spriteAtlasData = Maps.newHashMap();

      for (Entry<Identifier, List<SpriteIdentifier>> _snowmanxxxxxx : _snowmanxxxxx.entrySet()) {
         SpriteAtlasTexture _snowmanxxxxxxx = new SpriteAtlasTexture(_snowmanxxxxxx.getKey());
         SpriteAtlasTexture.Data _snowmanxxxxxxxx = _snowmanxxxxxxx.stitch(this.resourceManager, _snowmanxxxxxx.getValue().stream().map(SpriteIdentifier::getTextureId), _snowman, _snowman);
         this.spriteAtlasData.put(_snowmanxxxxxx.getKey(), Pair.of(_snowmanxxxxxxx, _snowmanxxxxxxxx));
      }

      _snowman.pop();
   }

   public SpriteAtlasManager upload(TextureManager _snowman, Profiler _snowman) {
      _snowman.push("atlas");

      for (Pair<SpriteAtlasTexture, SpriteAtlasTexture.Data> _snowmanxx : this.spriteAtlasData.values()) {
         SpriteAtlasTexture _snowmanxxx = (SpriteAtlasTexture)_snowmanxx.getFirst();
         SpriteAtlasTexture.Data _snowmanxxxx = (SpriteAtlasTexture.Data)_snowmanxx.getSecond();
         _snowmanxxx.upload(_snowmanxxxx);
         _snowman.registerTexture(_snowmanxxx.getId(), _snowmanxxx);
         _snowman.bindTexture(_snowmanxxx.getId());
         _snowmanxxx.applyTextureFilter(_snowmanxxxx);
      }

      this.spriteAtlasManager = new SpriteAtlasManager(
         this.spriteAtlasData.values().stream().<SpriteAtlasTexture>map(Pair::getFirst).collect(Collectors.toList())
      );
      _snowman.swap("baking");
      this.modelsToBake.keySet().forEach(_snowmanxxx -> {
         BakedModel _snowmanx = null;

         try {
            _snowmanx = this.bake(_snowmanxxx, ModelRotation.X0_Y0);
         } catch (Exception var4x) {
            LOGGER.warn("Unable to bake model: '{}': {}", _snowmanxxx, var4x);
         }

         if (_snowmanx != null) {
            this.bakedModels.put(_snowmanxxx, _snowmanx);
         }
      });
      _snowman.pop();
      return this.spriteAtlasManager;
   }

   private static Predicate<BlockState> stateKeyToPredicate(StateManager<Block, BlockState> stateFactory, String key) {
      Map<Property<?>, Comparable<?>> _snowman = Maps.newHashMap();

      for (String _snowmanx : COMMA_SPLITTER.split(key)) {
         Iterator<String> _snowmanxx = KEY_VALUE_SPLITTER.split(_snowmanx).iterator();
         if (_snowmanxx.hasNext()) {
            String _snowmanxxx = _snowmanxx.next();
            Property<?> _snowmanxxxx = stateFactory.getProperty(_snowmanxxx);
            if (_snowmanxxxx != null && _snowmanxx.hasNext()) {
               String _snowmanxxxxx = _snowmanxx.next();
               Comparable<?> _snowmanxxxxxx = getPropertyValue((Property<Comparable<?>>)_snowmanxxxx, _snowmanxxxxx);
               if (_snowmanxxxxxx == null) {
                  throw new RuntimeException("Unknown value: '" + _snowmanxxxxx + "' for blockstate property: '" + _snowmanxxx + "' " + _snowmanxxxx.getValues());
               }

               _snowman.put(_snowmanxxxx, _snowmanxxxxxx);
            } else if (!_snowmanxxx.isEmpty()) {
               throw new RuntimeException("Unknown blockstate property: '" + _snowmanxxx + "'");
            }
         }
      }

      Block _snowmanxx = stateFactory.getOwner();
      return _snowmanxxx -> {
         if (_snowmanxxx != null && _snowman == _snowmanxxx.getBlock()) {
            for (Entry<Property<?>, Comparable<?>> _snowmanxxxx : _snowman.entrySet()) {
               if (!Objects.equals(_snowmanxxx.get(_snowmanxxxx.getKey()), _snowmanxxxx.getValue())) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      };
   }

   @Nullable
   static <T extends Comparable<T>> T getPropertyValue(Property<T> property, String string) {
      return property.parse(string).orElse(null);
   }

   public UnbakedModel getOrLoadModel(Identifier id) {
      if (this.unbakedModels.containsKey(id)) {
         return this.unbakedModels.get(id);
      } else if (this.modelsToLoad.contains(id)) {
         throw new IllegalStateException("Circular reference while loading " + id);
      } else {
         this.modelsToLoad.add(id);
         UnbakedModel _snowman = this.unbakedModels.get(MISSING);

         while (!this.modelsToLoad.isEmpty()) {
            Identifier _snowmanx = this.modelsToLoad.iterator().next();

            try {
               if (!this.unbakedModels.containsKey(_snowmanx)) {
                  this.loadModel(_snowmanx);
               }
            } catch (ModelLoader.ModelLoaderException var9) {
               LOGGER.warn(var9.getMessage());
               this.unbakedModels.put(_snowmanx, _snowman);
            } catch (Exception var10) {
               LOGGER.warn("Unable to load model: '{}' referenced from: {}: {}", _snowmanx, id, var10);
               this.unbakedModels.put(_snowmanx, _snowman);
            } finally {
               this.modelsToLoad.remove(_snowmanx);
            }
         }

         return this.unbakedModels.getOrDefault(id, _snowman);
      }
   }

   private void loadModel(Identifier id) throws Exception {
      if (!(id instanceof ModelIdentifier)) {
         this.putModel(id, this.loadModelFromJson(id));
      } else {
         ModelIdentifier _snowman = (ModelIdentifier)id;
         if (Objects.equals(_snowman.getVariant(), "inventory")) {
            Identifier _snowmanx = new Identifier(id.getNamespace(), "item/" + id.getPath());
            JsonUnbakedModel _snowmanxx = this.loadModelFromJson(_snowmanx);
            this.putModel(_snowman, _snowmanxx);
            this.unbakedModels.put(_snowmanx, _snowmanxx);
         } else {
            Identifier _snowmanx = new Identifier(id.getNamespace(), id.getPath());
            StateManager<Block, BlockState> _snowmanxx = Optional.ofNullable(STATIC_DEFINITIONS.get(_snowmanx)).orElseGet(() -> Registry.BLOCK.get(_snowman).getStateManager());
            this.variantMapDeserializationContext.setStateFactory(_snowmanxx);
            List<Property<?>> _snowmanxxx = ImmutableList.copyOf(this.blockColors.getProperties(_snowmanxx.getOwner()));
            ImmutableList<BlockState> _snowmanxxxx = _snowmanxx.getStates();
            Map<ModelIdentifier, BlockState> _snowmanxxxxx = Maps.newHashMap();
            _snowmanxxxx.forEach(_snowmanxxxxxx -> {
               BlockState var10000 = _snowman.put(BlockModels.getModelId(_snowman, _snowmanxxxxxx), _snowmanxxxxxx);
            });
            Map<BlockState, Pair<UnbakedModel, Supplier<ModelLoader.ModelDefinition>>> _snowmanxxxxxx = Maps.newHashMap();
            Identifier _snowmanxxxxxxx = new Identifier(id.getNamespace(), "blockstates/" + id.getPath() + ".json");
            UnbakedModel _snowmanxxxxxxxx = this.unbakedModels.get(MISSING);
            ModelLoader.ModelDefinition _snowmanxxxxxxxxx = new ModelLoader.ModelDefinition(ImmutableList.of(_snowmanxxxxxxxx), ImmutableList.of());
            Pair<UnbakedModel, Supplier<ModelLoader.ModelDefinition>> _snowmanxxxxxxxxxx = Pair.of(_snowmanxxxxxxxx, (Supplier<ModelLoader.ModelDefinition>)() -> _snowman);

            try {
               List<Pair<String, ModelVariantMap>> _snowmanxxxxxxxxxxx;
               try {
                  _snowmanxxxxxxxxxxx = this.resourceManager
                     .getAllResources(_snowmanxxxxxxx)
                     .stream()
                     .map(
                        _snowmanxxxxxxxxxxxx -> {
                           try (InputStream _snowmanx = _snowmanxxxxxxxxxxxx.getInputStream()) {
                              return Pair.of(
                                 _snowmanxxxxxxxxxxxx.getResourcePackName(),
                                 ModelVariantMap.deserialize(this.variantMapDeserializationContext, new InputStreamReader(_snowmanx, StandardCharsets.UTF_8))
                              );
                           } catch (Exception var16x) {
                              throw new ModelLoader.ModelLoaderException(
                                 String.format(
                                    "Exception loading blockstate definition: '%s' in resourcepack: '%s': %s",
                                    _snowmanxxxxxxxxxxxx.getId(),
                                    _snowmanxxxxxxxxxxxx.getResourcePackName(),
                                    var16x.getMessage()
                                 )
                              );
                           }
                        }
                     )
                     .collect(Collectors.toList());
               } catch (IOException var25) {
                  LOGGER.warn("Exception loading blockstate definition: {}: {}", _snowmanxxxxxxx, var25);
                  return;
               }

               for (Pair<String, ModelVariantMap> _snowmanxxxxxxxxxxxx : _snowmanxxxxxxxxxxx) {
                  ModelVariantMap _snowmanxxxxxxxxxxxxx = (ModelVariantMap)_snowmanxxxxxxxxxxxx.getSecond();
                  Map<BlockState, Pair<UnbakedModel, Supplier<ModelLoader.ModelDefinition>>> _snowmanxxxxxxxxxxxxxx = Maps.newIdentityHashMap();
                  MultipartUnbakedModel _snowmanxxxxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxx.hasMultipartModel()) {
                     _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getMultipartModel();
                     _snowmanxxxx.forEach(
                        _snowmanxxxxxxxxxxxxxxxx -> {
                           Pair var10000 = _snowman.put(
                              _snowmanxxxxxxxxxxxxxxxx, Pair.of(_snowman, (Supplier<ModelLoader.ModelDefinition>)() -> ModelLoader.ModelDefinition.create(_snowmanxxx, _snowman, _snowman))
                           );
                        }
                     );
                  } else {
                     _snowmanxxxxxxxxxxxxxxx = null;
                  }

                  _snowmanxxxxxxxxxxxxx.getVariantMap()
                     .forEach(
                        (_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx) -> {
                           try {
                              _snowman.stream()
                                 .filter(stateKeyToPredicate(_snowman, _snowmanxxxxxxxxxxxxxxxx))
                                 .forEach(
                                    _snowmanxxxxxxxxxxxxxxxxxx -> {
                                       Pair<UnbakedModel, Supplier<ModelLoader.ModelDefinition>> _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.put(
                                          _snowmanxxxxxxxxxxxxxxxxxx,
                                          Pair.of(
                                             _snowmanxxxxxxxxxx,
                                             (Supplier<ModelLoader.ModelDefinition>)() -> ModelLoader.ModelDefinition.create(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowman)
                                          )
                                       );
                                       if (_snowmanxxxxxxxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxxxxxxxx.getFirst() != _snowman) {
                                          _snowman.put(_snowmanxxxxxxxxxxxxxxxxxx, _snowman);
                                          throw new RuntimeException(
                                             "Overlapping definition with: "
                                                + _snowman.getVariantMap()
                                                   .entrySet()
                                                   .stream()
                                                   .filter(_snowmanxxxxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxxxxx.getValue() == _snowmanxxxxxxxxxxxx.getFirst())
                                                   .findFirst()
                                                   .get()
                                                   .getKey()
                                          );
                                       }
                                    }
                                 );
                           } catch (Exception var12x) {
                              LOGGER.warn(
                                 "Exception loading blockstate definition: '{}' in resourcepack: '{}' for variant: '{}': {}",
                                 _snowman,
                                 _snowman.getFirst(),
                                 _snowmanxxxxxxxxxxxxxxxx,
                                 var12x.getMessage()
                              );
                           }
                        }
                     );
                  _snowmanxxxxxx.putAll(_snowmanxxxxxxxxxxxxxx);
               }
            } catch (ModelLoader.ModelLoaderException var26) {
               throw var26;
            } catch (Exception var27) {
               throw new ModelLoader.ModelLoaderException(String.format("Exception loading blockstate definition: '%s': %s", _snowmanxxxxxxx, var27));
            } finally {
               Map<ModelLoader.ModelDefinition, Set<BlockState>> _snowmanxxxxxxxxxxx = Maps.newHashMap();
               _snowmanxxxxx.forEach((_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx) -> {
                  Pair<UnbakedModel, Supplier<ModelLoader.ModelDefinition>> _snowmanxxxxxxxxxxxxxx = _snowman.get(_snowmanxxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxxx == null) {
                     LOGGER.warn("Exception loading blockstate definition: '{}' missing model for variant: '{}'", _snowman, _snowmanxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxx = _snowman;
                  }

                  this.putModel(_snowmanxxxxxxxxxxxx, (UnbakedModel)_snowmanxxxxxxxxxxxxxx.getFirst());

                  try {
                     ModelLoader.ModelDefinition _snowmanx = (ModelLoader.ModelDefinition)((Supplier)_snowmanxxxxxxxxxxxxxx.getSecond()).get();
                     _snowman.computeIfAbsent(_snowmanx, _snowmanxxxxxxxxxxxxxxxx -> Sets.newIdentityHashSet()).add(_snowmanxxxxxxxxxxxxx);
                  } catch (Exception var9x) {
                     LOGGER.warn("Exception evaluating model definition: '{}'", _snowmanxxxxxxxxxxxx, var9x);
                  }
               });
               _snowmanxxxxxxxxxxx.forEach((_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx) -> {
                  Iterator<BlockState> _snowmanxx = _snowmanxxxxxxxxxxxxx.iterator();

                  while (_snowmanxx.hasNext()) {
                     BlockState _snowmanxxx = _snowmanxx.next();
                     if (_snowmanxxx.getRenderType() != BlockRenderType.MODEL) {
                        _snowmanxx.remove();
                        this.stateLookup.put(_snowmanxxx, 0);
                     }
                  }

                  if (_snowmanxxxxxxxxxxxxx.size() > 1) {
                     this.addStates(_snowmanxxxxxxxxxxxxx);
                  }
               });
            }
         }
      }
   }

   private void putModel(Identifier id, UnbakedModel unbakedModel) {
      this.unbakedModels.put(id, unbakedModel);
      this.modelsToLoad.addAll(unbakedModel.getModelDependencies());
   }

   private void addModel(ModelIdentifier modelId) {
      UnbakedModel _snowman = this.getOrLoadModel(modelId);
      this.unbakedModels.put(modelId, _snowman);
      this.modelsToBake.put(modelId, _snowman);
   }

   private void addStates(Iterable<BlockState> states) {
      int _snowman = this.nextStateId++;
      states.forEach(_snowmanx -> this.stateLookup.put(_snowmanx, _snowman));
   }

   @Nullable
   public BakedModel bake(Identifier _snowman, ModelBakeSettings settings) {
      Triple<Identifier, AffineTransformation, Boolean> _snowmanx = Triple.of(_snowman, settings.getRotation(), settings.isShaded());
      if (this.bakedModelCache.containsKey(_snowmanx)) {
         return this.bakedModelCache.get(_snowmanx);
      } else if (this.spriteAtlasManager == null) {
         throw new IllegalStateException("bake called too early");
      } else {
         UnbakedModel _snowmanxx = this.getOrLoadModel(_snowman);
         if (_snowmanxx instanceof JsonUnbakedModel) {
            JsonUnbakedModel _snowmanxxx = (JsonUnbakedModel)_snowmanxx;
            if (_snowmanxxx.getRootModel() == GENERATION_MARKER) {
               return ITEM_MODEL_GENERATOR.create(this.spriteAtlasManager::getSprite, _snowmanxxx)
                  .bake(this, _snowmanxxx, this.spriteAtlasManager::getSprite, settings, _snowman, false);
            }
         }

         BakedModel _snowmanxxx = _snowmanxx.bake(this, this.spriteAtlasManager::getSprite, settings, _snowman);
         this.bakedModelCache.put(_snowmanx, _snowmanxxx);
         return _snowmanxxx;
      }
   }

   private JsonUnbakedModel loadModelFromJson(Identifier id) throws IOException {
      Reader _snowman = null;
      Resource _snowmanx = null;

      JsonUnbakedModel _snowmanxx;
      try {
         String _snowmanxxx = id.getPath();
         if ("builtin/generated".equals(_snowmanxxx)) {
            return GENERATION_MARKER;
         }

         if (!"builtin/entity".equals(_snowmanxxx)) {
            if (_snowmanxxx.startsWith("builtin/")) {
               String _snowmanxxxx = _snowmanxxx.substring("builtin/".length());
               String _snowmanxxxxx = BUILTIN_MODEL_DEFINITIONS.get(_snowmanxxxx);
               if (_snowmanxxxxx == null) {
                  throw new FileNotFoundException(id.toString());
               }

               _snowman = new StringReader(_snowmanxxxxx);
            } else {
               _snowmanx = this.resourceManager.getResource(new Identifier(id.getNamespace(), "models/" + id.getPath() + ".json"));
               _snowman = new InputStreamReader(_snowmanx.getInputStream(), StandardCharsets.UTF_8);
            }

            _snowmanxx = JsonUnbakedModel.deserialize(_snowman);
            _snowmanxx.id = id.toString();
            return _snowmanxx;
         }

         _snowmanxx = BLOCK_ENTITY_MARKER;
      } finally {
         IOUtils.closeQuietly(_snowman);
         IOUtils.closeQuietly(_snowmanx);
      }

      return _snowmanxx;
   }

   public Map<Identifier, BakedModel> getBakedModelMap() {
      return this.bakedModels;
   }

   public Object2IntMap<BlockState> getStateLookup() {
      return this.stateLookup;
   }

   static class ModelDefinition {
      private final List<UnbakedModel> components;
      private final List<Object> values;

      public ModelDefinition(List<UnbakedModel> components, List<Object> values) {
         this.components = components;
         this.values = values;
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof ModelLoader.ModelDefinition)) {
            return false;
         } else {
            ModelLoader.ModelDefinition _snowman = (ModelLoader.ModelDefinition)o;
            return Objects.equals(this.components, _snowman.components) && Objects.equals(this.values, _snowman.values);
         }
      }

      @Override
      public int hashCode() {
         return 31 * this.components.hashCode() + this.values.hashCode();
      }

      public static ModelLoader.ModelDefinition create(BlockState state, MultipartUnbakedModel rawModel, Collection<Property<?>> properties) {
         StateManager<Block, BlockState> _snowman = state.getBlock().getStateManager();
         List<UnbakedModel> _snowmanx = rawModel.getComponents()
            .stream()
            .filter(_snowmanxx -> _snowmanxx.getPredicate(_snowman).test(state))
            .map(MultipartModelComponent::getModel)
            .collect(ImmutableList.toImmutableList());
         List<Object> _snowmanxx = getStateValues(state, properties);
         return new ModelLoader.ModelDefinition(_snowmanx, _snowmanxx);
      }

      public static ModelLoader.ModelDefinition create(BlockState state, UnbakedModel rawModel, Collection<Property<?>> properties) {
         List<Object> _snowman = getStateValues(state, properties);
         return new ModelLoader.ModelDefinition(ImmutableList.of(rawModel), _snowman);
      }

      private static List<Object> getStateValues(BlockState state, Collection<Property<?>> properties) {
         return properties.stream().map(state::get).collect(ImmutableList.toImmutableList());
      }
   }

   static class ModelLoaderException extends RuntimeException {
      public ModelLoaderException(String message) {
         super(message);
      }
   }
}
