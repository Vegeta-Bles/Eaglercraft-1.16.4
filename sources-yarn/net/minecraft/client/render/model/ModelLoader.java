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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      .mapToObj(i -> new Identifier("block/destroy_stage_" + i))
      .collect(Collectors.toList());
   public static final List<Identifier> BLOCK_DESTRUCTION_STAGE_TEXTURES = BLOCK_DESTRUCTION_STAGES.stream()
      .map(arg -> new Identifier("textures/" + arg.getPath() + ".png"))
      .collect(Collectors.toList());
   public static final List<RenderLayer> BLOCK_DESTRUCTION_RENDER_LAYERS = BLOCK_DESTRUCTION_STAGE_TEXTURES.stream()
      .map(RenderLayer::getBlockBreaking)
      .collect(Collectors.toList());
   private static final Set<SpriteIdentifier> DEFAULT_TEXTURES = Util.make(Sets.newHashSet(), hashSet -> {
      hashSet.add(WATER_FLOW);
      hashSet.add(LAVA_FLOW);
      hashSet.add(WATER_OVERLAY);
      hashSet.add(FIRE_0);
      hashSet.add(FIRE_1);
      hashSet.add(BellBlockEntityRenderer.BELL_BODY_TEXTURE);
      hashSet.add(ConduitBlockEntityRenderer.BASE_TEXTURE);
      hashSet.add(ConduitBlockEntityRenderer.CAGE_TEXTURE);
      hashSet.add(ConduitBlockEntityRenderer.WIND_TEXTURE);
      hashSet.add(ConduitBlockEntityRenderer.WIND_VERTICAL_TEXTURE);
      hashSet.add(ConduitBlockEntityRenderer.OPEN_EYE_TEXTURE);
      hashSet.add(ConduitBlockEntityRenderer.CLOSED_EYE_TEXTURE);
      hashSet.add(EnchantingTableBlockEntityRenderer.BOOK_TEXTURE);
      hashSet.add(BANNER_BASE);
      hashSet.add(SHIELD_BASE);
      hashSet.add(SHIELD_BASE_NO_PATTERN);

      for (Identifier lv : BLOCK_DESTRUCTION_STAGES) {
         hashSet.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, lv));
      }

      hashSet.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_HELMET_SLOT_TEXTURE));
      hashSet.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_CHESTPLATE_SLOT_TEXTURE));
      hashSet.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_LEGGINGS_SLOT_TEXTURE));
      hashSet.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_BOOTS_SLOT_TEXTURE));
      hashSet.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT));
      TexturedRenderLayers.addDefaultTextures(hashSet::add);
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
      JsonUnbakedModel.deserialize("{\"gui_light\": \"front\"}"), arg -> arg.id = "generation marker"
   );
   public static final JsonUnbakedModel BLOCK_ENTITY_MARKER = Util.make(
      JsonUnbakedModel.deserialize("{\"gui_light\": \"side\"}"), arg -> arg.id = "block entity marker"
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
   private final Object2IntMap<BlockState> stateLookup = Util.make(
      new Object2IntOpenHashMap(), object2IntOpenHashMap -> object2IntOpenHashMap.defaultReturnValue(-1)
   );

   public ModelLoader(ResourceManager resourceManager, BlockColors arg2, Profiler arg3, int i) {
      this.resourceManager = resourceManager;
      this.blockColors = arg2;
      arg3.push("missing_model");

      try {
         this.unbakedModels.put(MISSING, this.loadModelFromJson(MISSING));
         this.addModel(MISSING);
      } catch (IOException var12) {
         LOGGER.error("Error loading missing model, should never happen :(", var12);
         throw new RuntimeException(var12);
      }

      arg3.swap("static_definitions");
      STATIC_DEFINITIONS.forEach((arg, arg2x) -> arg2x.getStates().forEach(arg2xx -> this.addModel(BlockModels.getModelId(arg, arg2xx))));
      arg3.swap("blocks");

      for (Block lv : Registry.BLOCK) {
         lv.getStateManager().getStates().forEach(arg -> this.addModel(BlockModels.getModelId(arg)));
      }

      arg3.swap("items");

      for (Identifier lv2 : Registry.ITEM.getIds()) {
         this.addModel(new ModelIdentifier(lv2, "inventory"));
      }

      arg3.swap("special");
      this.addModel(new ModelIdentifier("minecraft:trident_in_hand#inventory"));
      arg3.swap("textures");
      Set<Pair<String, String>> set = Sets.newLinkedHashSet();
      Set<SpriteIdentifier> set2 = this.modelsToBake
         .values()
         .stream()
         .flatMap(arg -> arg.getTextureDependencies(this::getOrLoadModel, set).stream())
         .collect(Collectors.toSet());
      set2.addAll(DEFAULT_TEXTURES);
      set.stream()
         .filter(pair -> !((String)pair.getSecond()).equals(field_21773))
         .forEach(pair -> LOGGER.warn("Unable to resolve texture reference: {} in {}", pair.getFirst(), pair.getSecond()));
      Map<Identifier, List<SpriteIdentifier>> map = set2.stream().collect(Collectors.groupingBy(SpriteIdentifier::getAtlasId));
      arg3.swap("stitching");
      this.spriteAtlasData = Maps.newHashMap();

      for (Entry<Identifier, List<SpriteIdentifier>> entry : map.entrySet()) {
         SpriteAtlasTexture lv3 = new SpriteAtlasTexture(entry.getKey());
         SpriteAtlasTexture.Data lv4 = lv3.stitch(this.resourceManager, entry.getValue().stream().map(SpriteIdentifier::getTextureId), arg3, i);
         this.spriteAtlasData.put(entry.getKey(), Pair.of(lv3, lv4));
      }

      arg3.pop();
   }

   public SpriteAtlasManager upload(TextureManager arg, Profiler arg2) {
      arg2.push("atlas");

      for (Pair<SpriteAtlasTexture, SpriteAtlasTexture.Data> pair : this.spriteAtlasData.values()) {
         SpriteAtlasTexture lv = (SpriteAtlasTexture)pair.getFirst();
         SpriteAtlasTexture.Data lv2 = (SpriteAtlasTexture.Data)pair.getSecond();
         lv.upload(lv2);
         arg.registerTexture(lv.getId(), lv);
         arg.bindTexture(lv.getId());
         lv.applyTextureFilter(lv2);
      }

      this.spriteAtlasManager = new SpriteAtlasManager(
         this.spriteAtlasData.values().stream().<SpriteAtlasTexture>map(Pair::getFirst).collect(Collectors.toList())
      );
      arg2.swap("baking");
      this.modelsToBake.keySet().forEach(argx -> {
         BakedModel lvx = null;

         try {
            lvx = this.bake(argx, ModelRotation.X0_Y0);
         } catch (Exception var4x) {
            LOGGER.warn("Unable to bake model: '{}': {}", argx, var4x);
         }

         if (lvx != null) {
            this.bakedModels.put(argx, lvx);
         }
      });
      arg2.pop();
      return this.spriteAtlasManager;
   }

   private static Predicate<BlockState> stateKeyToPredicate(StateManager<Block, BlockState> stateFactory, String key) {
      Map<Property<?>, Comparable<?>> map = Maps.newHashMap();

      for (String string2 : COMMA_SPLITTER.split(key)) {
         Iterator<String> iterator = KEY_VALUE_SPLITTER.split(string2).iterator();
         if (iterator.hasNext()) {
            String string3 = iterator.next();
            Property<?> lv = stateFactory.getProperty(string3);
            if (lv != null && iterator.hasNext()) {
               String string4 = iterator.next();
               Comparable<?> comparable = getPropertyValue((Property<Comparable<?>>)lv, string4);
               if (comparable == null) {
                  throw new RuntimeException("Unknown value: '" + string4 + "' for blockstate property: '" + string3 + "' " + lv.getValues());
               }

               map.put(lv, comparable);
            } else if (!string3.isEmpty()) {
               throw new RuntimeException("Unknown blockstate property: '" + string3 + "'");
            }
         }
      }

      Block lv2 = stateFactory.getOwner();
      return arg2 -> {
         if (arg2 != null && lv2 == arg2.getBlock()) {
            for (Entry<Property<?>, Comparable<?>> entry : map.entrySet()) {
               if (!Objects.equals(arg2.get(entry.getKey()), entry.getValue())) {
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
         UnbakedModel lv = this.unbakedModels.get(MISSING);

         while (!this.modelsToLoad.isEmpty()) {
            Identifier lv2 = this.modelsToLoad.iterator().next();

            try {
               if (!this.unbakedModels.containsKey(lv2)) {
                  this.loadModel(lv2);
               }
            } catch (ModelLoader.ModelLoaderException var9) {
               LOGGER.warn(var9.getMessage());
               this.unbakedModels.put(lv2, lv);
            } catch (Exception var10) {
               LOGGER.warn("Unable to load model: '{}' referenced from: {}: {}", lv2, id, var10);
               this.unbakedModels.put(lv2, lv);
            } finally {
               this.modelsToLoad.remove(lv2);
            }
         }

         return this.unbakedModels.getOrDefault(id, lv);
      }
   }

   private void loadModel(Identifier id) throws Exception {
      if (!(id instanceof ModelIdentifier)) {
         this.putModel(id, this.loadModelFromJson(id));
      } else {
         ModelIdentifier lv = (ModelIdentifier)id;
         if (Objects.equals(lv.getVariant(), "inventory")) {
            Identifier lv2 = new Identifier(id.getNamespace(), "item/" + id.getPath());
            JsonUnbakedModel lv3 = this.loadModelFromJson(lv2);
            this.putModel(lv, lv3);
            this.unbakedModels.put(lv2, lv3);
         } else {
            Identifier lv4 = new Identifier(id.getNamespace(), id.getPath());
            StateManager<Block, BlockState> lv5 = Optional.ofNullable(STATIC_DEFINITIONS.get(lv4)).orElseGet(() -> Registry.BLOCK.get(lv4).getStateManager());
            this.variantMapDeserializationContext.setStateFactory(lv5);
            List<Property<?>> list = ImmutableList.copyOf(this.blockColors.getProperties(lv5.getOwner()));
            ImmutableList<BlockState> immutableList = lv5.getStates();
            Map<ModelIdentifier, BlockState> map = Maps.newHashMap();
            immutableList.forEach(arg2 -> {
               BlockState var10000 = map.put(BlockModels.getModelId(lv4, arg2), arg2);
            });
            Map<BlockState, Pair<UnbakedModel, Supplier<ModelLoader.ModelDefinition>>> map2 = Maps.newHashMap();
            Identifier lv6 = new Identifier(id.getNamespace(), "blockstates/" + id.getPath() + ".json");
            UnbakedModel lv7 = this.unbakedModels.get(MISSING);
            ModelLoader.ModelDefinition lv8 = new ModelLoader.ModelDefinition(ImmutableList.of(lv7), ImmutableList.of());
            Pair<UnbakedModel, Supplier<ModelLoader.ModelDefinition>> pair = Pair.of(lv7, (Supplier<ModelLoader.ModelDefinition>)() -> lv8);

            try {
               List<Pair<String, ModelVariantMap>> list2;
               try {
                  list2 = this.resourceManager
                     .getAllResources(lv6)
                     .stream()
                     .map(
                        arg -> {
                           try (InputStream inputStream = arg.getInputStream()) {
                              return Pair.of(
                                 arg.getResourcePackName(),
                                 ModelVariantMap.deserialize(this.variantMapDeserializationContext, new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                              );
                           } catch (Exception var16x) {
                              throw new ModelLoader.ModelLoaderException(
                                 String.format(
                                    "Exception loading blockstate definition: '%s' in resourcepack: '%s': %s",
                                    arg.getId(),
                                    arg.getResourcePackName(),
                                    var16x.getMessage()
                                 )
                              );
                           }
                        }
                     )
                     .collect(Collectors.toList());
               } catch (IOException var25) {
                  LOGGER.warn("Exception loading blockstate definition: {}: {}", lv6, var25);
                  return;
               }

               for (Pair<String, ModelVariantMap> pair2 : list2) {
                  ModelVariantMap lv9 = (ModelVariantMap)pair2.getSecond();
                  Map<BlockState, Pair<UnbakedModel, Supplier<ModelLoader.ModelDefinition>>> map4 = Maps.newIdentityHashMap();
                  MultipartUnbakedModel lv10;
                  if (lv9.hasMultipartModel()) {
                     lv10 = lv9.getMultipartModel();
                     immutableList.forEach(
                        arg2 -> {
                           Pair var10000 = map4.put(
                              arg2, Pair.of(lv10, (Supplier<ModelLoader.ModelDefinition>)() -> ModelLoader.ModelDefinition.create(arg2, lv10, list))
                           );
                        }
                     );
                  } else {
                     lv10 = null;
                  }

                  lv9.getVariantMap()
                     .forEach(
                        (string, arg5) -> {
                           try {
                              immutableList.stream()
                                 .filter(stateKeyToPredicate(lv5, string))
                                 .forEach(
                                    arg4 -> {
                                       Pair<UnbakedModel, Supplier<ModelLoader.ModelDefinition>> pair2xx = map4.put(
                                          arg4,
                                          Pair.of(arg5, (Supplier<ModelLoader.ModelDefinition>)() -> ModelLoader.ModelDefinition.create(arg4, arg5, list))
                                       );
                                       if (pair2xx != null && pair2xx.getFirst() != lv10) {
                                          map4.put(arg4, pair);
                                          throw new RuntimeException(
                                             "Overlapping definition with: "
                                                + lv9.getVariantMap()
                                                   .entrySet()
                                                   .stream()
                                                   .filter(entry -> entry.getValue() == pair2xx.getFirst())
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
                                 lv6,
                                 pair2.getFirst(),
                                 string,
                                 var12x.getMessage()
                              );
                           }
                        }
                     );
                  map2.putAll(map4);
               }
            } catch (ModelLoader.ModelLoaderException var26) {
               throw var26;
            } catch (Exception var27) {
               throw new ModelLoader.ModelLoaderException(String.format("Exception loading blockstate definition: '%s': %s", lv6, var27));
            } finally {
               Map<ModelLoader.ModelDefinition, Set<BlockState>> map6 = Maps.newHashMap();
               map.forEach((arg2, arg3) -> {
                  Pair<UnbakedModel, Supplier<ModelLoader.ModelDefinition>> pair2x = map2.get(arg3);
                  if (pair2x == null) {
                     LOGGER.warn("Exception loading blockstate definition: '{}' missing model for variant: '{}'", lv6, arg2);
                     pair2x = pair;
                  }

                  this.putModel(arg2, (UnbakedModel)pair2x.getFirst());

                  try {
                     ModelLoader.ModelDefinition lvx = (ModelLoader.ModelDefinition)((Supplier)pair2x.getSecond()).get();
                     map6.computeIfAbsent(lvx, arg -> Sets.newIdentityHashSet()).add(arg3);
                  } catch (Exception var9x) {
                     LOGGER.warn("Exception evaluating model definition: '{}'", arg2, var9x);
                  }
               });
               map6.forEach((arg, set) -> {
                  Iterator<BlockState> iterator = set.iterator();

                  while (iterator.hasNext()) {
                     BlockState lvx = iterator.next();
                     if (lvx.getRenderType() != BlockRenderType.MODEL) {
                        iterator.remove();
                        this.stateLookup.put(lvx, 0);
                     }
                  }

                  if (set.size() > 1) {
                     this.addStates(set);
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
      UnbakedModel lv = this.getOrLoadModel(modelId);
      this.unbakedModels.put(modelId, lv);
      this.modelsToBake.put(modelId, lv);
   }

   private void addStates(Iterable<BlockState> states) {
      int i = this.nextStateId++;
      states.forEach(arg -> this.stateLookup.put(arg, i));
   }

   @Nullable
   public BakedModel bake(Identifier arg, ModelBakeSettings settings) {
      Triple<Identifier, AffineTransformation, Boolean> triple = Triple.of(arg, settings.getRotation(), settings.isShaded());
      if (this.bakedModelCache.containsKey(triple)) {
         return this.bakedModelCache.get(triple);
      } else if (this.spriteAtlasManager == null) {
         throw new IllegalStateException("bake called too early");
      } else {
         UnbakedModel lv = this.getOrLoadModel(arg);
         if (lv instanceof JsonUnbakedModel) {
            JsonUnbakedModel lv2 = (JsonUnbakedModel)lv;
            if (lv2.getRootModel() == GENERATION_MARKER) {
               return ITEM_MODEL_GENERATOR.create(this.spriteAtlasManager::getSprite, lv2)
                  .bake(this, lv2, this.spriteAtlasManager::getSprite, settings, arg, false);
            }
         }

         BakedModel lv3 = lv.bake(this, this.spriteAtlasManager::getSprite, settings, arg);
         this.bakedModelCache.put(triple, lv3);
         return lv3;
      }
   }

   private JsonUnbakedModel loadModelFromJson(Identifier id) throws IOException {
      Reader reader = null;
      Resource lv = null;

      JsonUnbakedModel lv2;
      try {
         String string = id.getPath();
         if ("builtin/generated".equals(string)) {
            return GENERATION_MARKER;
         }

         if (!"builtin/entity".equals(string)) {
            if (string.startsWith("builtin/")) {
               String string2 = string.substring("builtin/".length());
               String string3 = BUILTIN_MODEL_DEFINITIONS.get(string2);
               if (string3 == null) {
                  throw new FileNotFoundException(id.toString());
               }

               reader = new StringReader(string3);
            } else {
               lv = this.resourceManager.getResource(new Identifier(id.getNamespace(), "models/" + id.getPath() + ".json"));
               reader = new InputStreamReader(lv.getInputStream(), StandardCharsets.UTF_8);
            }

            lv2 = JsonUnbakedModel.deserialize(reader);
            lv2.id = id.toString();
            return lv2;
         }

         lv2 = BLOCK_ENTITY_MARKER;
      } finally {
         IOUtils.closeQuietly(reader);
         IOUtils.closeQuietly(lv);
      }

      return lv2;
   }

   public Map<Identifier, BakedModel> getBakedModelMap() {
      return this.bakedModels;
   }

   public Object2IntMap<BlockState> getStateLookup() {
      return this.stateLookup;
   }

   @Environment(EnvType.CLIENT)
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
            ModelLoader.ModelDefinition lv = (ModelLoader.ModelDefinition)o;
            return Objects.equals(this.components, lv.components) && Objects.equals(this.values, lv.values);
         }
      }

      @Override
      public int hashCode() {
         return 31 * this.components.hashCode() + this.values.hashCode();
      }

      public static ModelLoader.ModelDefinition create(BlockState state, MultipartUnbakedModel rawModel, Collection<Property<?>> properties) {
         StateManager<Block, BlockState> lv = state.getBlock().getStateManager();
         List<UnbakedModel> list = rawModel.getComponents()
            .stream()
            .filter(arg3 -> arg3.getPredicate(lv).test(state))
            .map(MultipartModelComponent::getModel)
            .collect(ImmutableList.toImmutableList());
         List<Object> list2 = getStateValues(state, properties);
         return new ModelLoader.ModelDefinition(list, list2);
      }

      public static ModelLoader.ModelDefinition create(BlockState state, UnbakedModel rawModel, Collection<Property<?>> properties) {
         List<Object> list = getStateValues(state, properties);
         return new ModelLoader.ModelDefinition(ImmutableList.of(rawModel), list);
      }

      private static List<Object> getStateValues(BlockState state, Collection<Property<?>> properties) {
         return properties.stream().map(state::get).collect(ImmutableList.toImmutableList());
      }
   }

   @Environment(EnvType.CLIENT)
   static class ModelLoaderException extends RuntimeException {
      public ModelLoaderException(String message) {
         super(message);
      }
   }
}
