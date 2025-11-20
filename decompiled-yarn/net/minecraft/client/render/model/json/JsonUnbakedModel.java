package net.minecraft.client.render.model.json;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BakedQuadFactory;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.BuiltinBakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonUnbakedModel implements UnbakedModel {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final BakedQuadFactory QUAD_FACTORY = new BakedQuadFactory();
   @VisibleForTesting
   static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(JsonUnbakedModel.class, new JsonUnbakedModel.Deserializer())
      .registerTypeAdapter(ModelElement.class, new ModelElement.Deserializer())
      .registerTypeAdapter(ModelElementFace.class, new ModelElementFace.Deserializer())
      .registerTypeAdapter(ModelElementTexture.class, new ModelElementTexture.Deserializer())
      .registerTypeAdapter(Transformation.class, new Transformation.Deserializer())
      .registerTypeAdapter(ModelTransformation.class, new ModelTransformation.Deserializer())
      .registerTypeAdapter(ModelOverride.class, new ModelOverride.Deserializer())
      .create();
   private final List<ModelElement> elements;
   @Nullable
   private final JsonUnbakedModel.GuiLight guiLight;
   private final boolean ambientOcclusion;
   private final ModelTransformation transformations;
   private final List<ModelOverride> overrides;
   public String id = "";
   @VisibleForTesting
   protected final Map<String, Either<SpriteIdentifier, String>> textureMap;
   @Nullable
   protected JsonUnbakedModel parent;
   @Nullable
   protected Identifier parentId;

   public static JsonUnbakedModel deserialize(Reader input) {
      return JsonHelper.deserialize(GSON, input, JsonUnbakedModel.class);
   }

   public static JsonUnbakedModel deserialize(String json) {
      return deserialize(new StringReader(json));
   }

   public JsonUnbakedModel(
      @Nullable Identifier parentId,
      List<ModelElement> elements,
      Map<String, Either<SpriteIdentifier, String>> textureMap,
      boolean ambientOcclusion,
      @Nullable JsonUnbakedModel.GuiLight guiLight,
      ModelTransformation transformations,
      List<ModelOverride> overrides
   ) {
      this.elements = elements;
      this.ambientOcclusion = ambientOcclusion;
      this.guiLight = guiLight;
      this.textureMap = textureMap;
      this.parentId = parentId;
      this.transformations = transformations;
      this.overrides = overrides;
   }

   public List<ModelElement> getElements() {
      return this.elements.isEmpty() && this.parent != null ? this.parent.getElements() : this.elements;
   }

   public boolean useAmbientOcclusion() {
      return this.parent != null ? this.parent.useAmbientOcclusion() : this.ambientOcclusion;
   }

   public JsonUnbakedModel.GuiLight getGuiLight() {
      if (this.guiLight != null) {
         return this.guiLight;
      } else {
         return this.parent != null ? this.parent.getGuiLight() : JsonUnbakedModel.GuiLight.field_21859;
      }
   }

   public List<ModelOverride> getOverrides() {
      return this.overrides;
   }

   private ModelOverrideList compileOverrides(ModelLoader modelLoader, JsonUnbakedModel parent) {
      return this.overrides.isEmpty() ? ModelOverrideList.EMPTY : new ModelOverrideList(modelLoader, parent, modelLoader::getOrLoadModel, this.overrides);
   }

   @Override
   public Collection<Identifier> getModelDependencies() {
      Set<Identifier> _snowman = Sets.newHashSet();

      for (ModelOverride _snowmanx : this.overrides) {
         _snowman.add(_snowmanx.getModelId());
      }

      if (this.parentId != null) {
         _snowman.add(this.parentId);
      }

      return _snowman;
   }

   @Override
   public Collection<SpriteIdentifier> getTextureDependencies(
      Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences
   ) {
      Set<UnbakedModel> _snowman = Sets.newLinkedHashSet();

      for (JsonUnbakedModel _snowmanx = this; _snowmanx.parentId != null && _snowmanx.parent == null; _snowmanx = _snowmanx.parent) {
         _snowman.add(_snowmanx);
         UnbakedModel _snowmanxx = unbakedModelGetter.apply(_snowmanx.parentId);
         if (_snowmanxx == null) {
            LOGGER.warn("No parent '{}' while loading model '{}'", this.parentId, _snowmanx);
         }

         if (_snowman.contains(_snowmanxx)) {
            LOGGER.warn(
               "Found 'parent' loop while loading model '{}' in chain: {} -> {}",
               _snowmanx,
               _snowman.stream().map(Object::toString).collect(Collectors.joining(" -> ")),
               this.parentId
            );
            _snowmanxx = null;
         }

         if (_snowmanxx == null) {
            _snowmanx.parentId = ModelLoader.MISSING;
            _snowmanxx = unbakedModelGetter.apply(_snowmanx.parentId);
         }

         if (!(_snowmanxx instanceof JsonUnbakedModel)) {
            throw new IllegalStateException("BlockModel parent has to be a block model.");
         }

         _snowmanx.parent = (JsonUnbakedModel)_snowmanxx;
      }

      Set<SpriteIdentifier> _snowmanx = Sets.newHashSet(new SpriteIdentifier[]{this.resolveSprite("particle")});

      for (ModelElement _snowmanxxx : this.getElements()) {
         for (ModelElementFace _snowmanxxxx : _snowmanxxx.faces.values()) {
            SpriteIdentifier _snowmanxxxxx = this.resolveSprite(_snowmanxxxx.textureId);
            if (Objects.equals(_snowmanxxxxx.getTextureId(), MissingSprite.getMissingSpriteId())) {
               unresolvedTextureReferences.add(Pair.of(_snowmanxxxx.textureId, this.id));
            }

            _snowmanx.add(_snowmanxxxxx);
         }
      }

      this.overrides.forEach(_snowmanxxx -> {
         UnbakedModel _snowmanxxx = unbakedModelGetter.apply(_snowmanxxx.getModelId());
         if (!Objects.equals(_snowmanxxx, this)) {
            _snowman.addAll(_snowmanxxx.getTextureDependencies(unbakedModelGetter, unresolvedTextureReferences));
         }
      });
      if (this.getRootModel() == ModelLoader.GENERATION_MARKER) {
         ItemModelGenerator.LAYERS.forEach(_snowmanxxx -> _snowman.add(this.resolveSprite(_snowmanxxx)));
      }

      return _snowmanx;
   }

   @Override
   public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
      return this.bake(loader, this, textureGetter, rotationContainer, modelId, true);
   }

   public BakedModel bake(
      ModelLoader loader,
      JsonUnbakedModel parent,
      Function<SpriteIdentifier, Sprite> textureGetter,
      ModelBakeSettings settings,
      Identifier id,
      boolean hasDepth
   ) {
      Sprite _snowman = textureGetter.apply(this.resolveSprite("particle"));
      if (this.getRootModel() == ModelLoader.BLOCK_ENTITY_MARKER) {
         return new BuiltinBakedModel(this.getTransformations(), this.compileOverrides(loader, parent), _snowman, this.getGuiLight().isSide());
      } else {
         BasicBakedModel.Builder _snowmanx = new BasicBakedModel.Builder(this, this.compileOverrides(loader, parent), hasDepth).setParticle(_snowman);

         for (ModelElement _snowmanxx : this.getElements()) {
            for (Direction _snowmanxxx : _snowmanxx.faces.keySet()) {
               ModelElementFace _snowmanxxxx = _snowmanxx.faces.get(_snowmanxxx);
               Sprite _snowmanxxxxx = textureGetter.apply(this.resolveSprite(_snowmanxxxx.textureId));
               if (_snowmanxxxx.cullFace == null) {
                  _snowmanx.addQuad(createQuad(_snowmanxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx, settings, id));
               } else {
                  _snowmanx.addQuad(Direction.transform(settings.getRotation().getMatrix(), _snowmanxxxx.cullFace), createQuad(_snowmanxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx, settings, id));
               }
            }
         }

         return _snowmanx.build();
      }
   }

   private static BakedQuad createQuad(
      ModelElement element, ModelElementFace elementFace, Sprite sprite, Direction side, ModelBakeSettings settings, Identifier id
   ) {
      return QUAD_FACTORY.bake(element.from, element.to, elementFace, sprite, side, settings, element.rotation, element.shade, id);
   }

   public boolean textureExists(String name) {
      return !MissingSprite.getMissingSpriteId().equals(this.resolveSprite(name).getTextureId());
   }

   public SpriteIdentifier resolveSprite(String spriteName) {
      if (isTextureReference(spriteName)) {
         spriteName = spriteName.substring(1);
      }

      List<String> _snowman = Lists.newArrayList();

      while (true) {
         Either<SpriteIdentifier, String> _snowmanx = this.resolveTexture(spriteName);
         Optional<SpriteIdentifier> _snowmanxx = _snowmanx.left();
         if (_snowmanxx.isPresent()) {
            return _snowmanxx.get();
         }

         spriteName = (String)_snowmanx.right().get();
         if (_snowman.contains(spriteName)) {
            LOGGER.warn("Unable to resolve texture due to reference chain {}->{} in {}", Joiner.on("->").join(_snowman), spriteName, this.id);
            return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, MissingSprite.getMissingSpriteId());
         }

         _snowman.add(spriteName);
      }
   }

   private Either<SpriteIdentifier, String> resolveTexture(String name) {
      for (JsonUnbakedModel _snowman = this; _snowman != null; _snowman = _snowman.parent) {
         Either<SpriteIdentifier, String> _snowmanx = _snowman.textureMap.get(name);
         if (_snowmanx != null) {
            return _snowmanx;
         }
      }

      return Either.left(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, MissingSprite.getMissingSpriteId()));
   }

   private static boolean isTextureReference(String reference) {
      return reference.charAt(0) == '#';
   }

   public JsonUnbakedModel getRootModel() {
      return this.parent == null ? this : this.parent.getRootModel();
   }

   public ModelTransformation getTransformations() {
      Transformation _snowman = this.getTransformation(ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND);
      Transformation _snowmanx = this.getTransformation(ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND);
      Transformation _snowmanxx = this.getTransformation(ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND);
      Transformation _snowmanxxx = this.getTransformation(ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND);
      Transformation _snowmanxxxx = this.getTransformation(ModelTransformation.Mode.HEAD);
      Transformation _snowmanxxxxx = this.getTransformation(ModelTransformation.Mode.GUI);
      Transformation _snowmanxxxxxx = this.getTransformation(ModelTransformation.Mode.GROUND);
      Transformation _snowmanxxxxxxx = this.getTransformation(ModelTransformation.Mode.FIXED);
      return new ModelTransformation(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
   }

   private Transformation getTransformation(ModelTransformation.Mode renderMode) {
      return this.parent != null && !this.transformations.isTransformationDefined(renderMode)
         ? this.parent.getTransformation(renderMode)
         : this.transformations.getTransformation(renderMode);
   }

   @Override
   public String toString() {
      return this.id;
   }

   public static class Deserializer implements JsonDeserializer<JsonUnbakedModel> {
      public Deserializer() {
      }

      public JsonUnbakedModel deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = _snowman.getAsJsonObject();
         List<ModelElement> _snowmanxxxx = this.deserializeElements(_snowman, _snowmanxxx);
         String _snowmanxxxxx = this.deserializeParent(_snowmanxxx);
         Map<String, Either<SpriteIdentifier, String>> _snowmanxxxxxx = this.deserializeTextures(_snowmanxxx);
         boolean _snowmanxxxxxxx = this.deserializeAmbientOcclusion(_snowmanxxx);
         ModelTransformation _snowmanxxxxxxxx = ModelTransformation.NONE;
         if (_snowmanxxx.has("display")) {
            JsonObject _snowmanxxxxxxxxx = JsonHelper.getObject(_snowmanxxx, "display");
            _snowmanxxxxxxxx = (ModelTransformation)_snowman.deserialize(_snowmanxxxxxxxxx, ModelTransformation.class);
         }

         List<ModelOverride> _snowmanxxxxxxxxx = this.deserializeOverrides(_snowman, _snowmanxxx);
         JsonUnbakedModel.GuiLight _snowmanxxxxxxxxxx = null;
         if (_snowmanxxx.has("gui_light")) {
            _snowmanxxxxxxxxxx = JsonUnbakedModel.GuiLight.deserialize(JsonHelper.getString(_snowmanxxx, "gui_light"));
         }

         Identifier _snowmanxxxxxxxxxxx = _snowmanxxxxx.isEmpty() ? null : new Identifier(_snowmanxxxxx);
         return new JsonUnbakedModel(_snowmanxxxxxxxxxxx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
      }

      protected List<ModelOverride> deserializeOverrides(JsonDeserializationContext context, JsonObject object) {
         List<ModelOverride> _snowman = Lists.newArrayList();
         if (object.has("overrides")) {
            for (JsonElement _snowmanx : JsonHelper.getArray(object, "overrides")) {
               _snowman.add((ModelOverride)context.deserialize(_snowmanx, ModelOverride.class));
            }
         }

         return _snowman;
      }

      private Map<String, Either<SpriteIdentifier, String>> deserializeTextures(JsonObject object) {
         Identifier _snowman = SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
         Map<String, Either<SpriteIdentifier, String>> _snowmanx = Maps.newHashMap();
         if (object.has("textures")) {
            JsonObject _snowmanxx = JsonHelper.getObject(object, "textures");

            for (Entry<String, JsonElement> _snowmanxxx : _snowmanxx.entrySet()) {
               _snowmanx.put(_snowmanxxx.getKey(), resolveReference(_snowman, _snowmanxxx.getValue().getAsString()));
            }
         }

         return _snowmanx;
      }

      private static Either<SpriteIdentifier, String> resolveReference(Identifier id, String name) {
         if (JsonUnbakedModel.isTextureReference(name)) {
            return Either.right(name.substring(1));
         } else {
            Identifier _snowman = Identifier.tryParse(name);
            if (_snowman == null) {
               throw new JsonParseException(name + " is not valid resource location");
            } else {
               return Either.left(new SpriteIdentifier(id, _snowman));
            }
         }
      }

      private String deserializeParent(JsonObject json) {
         return JsonHelper.getString(json, "parent", "");
      }

      protected boolean deserializeAmbientOcclusion(JsonObject json) {
         return JsonHelper.getBoolean(json, "ambientocclusion", true);
      }

      protected List<ModelElement> deserializeElements(JsonDeserializationContext context, JsonObject json) {
         List<ModelElement> _snowman = Lists.newArrayList();
         if (json.has("elements")) {
            for (JsonElement _snowmanx : JsonHelper.getArray(json, "elements")) {
               _snowman.add((ModelElement)context.deserialize(_snowmanx, ModelElement.class));
            }
         }

         return _snowman;
      }
   }

   public static enum GuiLight {
      field_21858("front"),
      field_21859("side");

      private final String name;

      private GuiLight(String name) {
         this.name = name;
      }

      public static JsonUnbakedModel.GuiLight deserialize(String value) {
         for (JsonUnbakedModel.GuiLight _snowman : values()) {
            if (_snowman.name.equals(value)) {
               return _snowman;
            }
         }

         throw new IllegalArgumentException("Invalid gui light: " + value);
      }

      public boolean isSide() {
         return this == field_21859;
      }
   }
}
