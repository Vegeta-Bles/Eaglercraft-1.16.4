/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.Joiner
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.render.model.json;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
import net.minecraft.client.render.model.json.ItemModelGenerator;
import net.minecraft.client.render.model.json.ModelElement;
import net.minecraft.client.render.model.json.ModelElementFace;
import net.minecraft.client.render.model.json.ModelElementTexture;
import net.minecraft.client.render.model.json.ModelOverride;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonUnbakedModel
implements UnbakedModel {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final BakedQuadFactory QUAD_FACTORY = new BakedQuadFactory();
    @VisibleForTesting
    static final Gson GSON = new GsonBuilder().registerTypeAdapter(JsonUnbakedModel.class, (Object)new Deserializer()).registerTypeAdapter(ModelElement.class, (Object)new ModelElement.Deserializer()).registerTypeAdapter(ModelElementFace.class, (Object)new ModelElementFace.Deserializer()).registerTypeAdapter(ModelElementTexture.class, (Object)new ModelElementTexture.Deserializer()).registerTypeAdapter(Transformation.class, (Object)new Transformation.Deserializer()).registerTypeAdapter(ModelTransformation.class, (Object)new ModelTransformation.Deserializer()).registerTypeAdapter(ModelOverride.class, (Object)new ModelOverride.Deserializer()).create();
    private final List<ModelElement> elements;
    @Nullable
    private final GuiLight guiLight;
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
        return JsonUnbakedModel.deserialize(new StringReader(json));
    }

    public JsonUnbakedModel(@Nullable Identifier parentId, List<ModelElement> elements, Map<String, Either<SpriteIdentifier, String>> textureMap, boolean ambientOcclusion, @Nullable GuiLight guiLight, ModelTransformation transformations, List<ModelOverride> overrides) {
        this.elements = elements;
        this.ambientOcclusion = ambientOcclusion;
        this.guiLight = guiLight;
        this.textureMap = textureMap;
        this.parentId = parentId;
        this.transformations = transformations;
        this.overrides = overrides;
    }

    public List<ModelElement> getElements() {
        if (this.elements.isEmpty() && this.parent != null) {
            return this.parent.getElements();
        }
        return this.elements;
    }

    public boolean useAmbientOcclusion() {
        if (this.parent != null) {
            return this.parent.useAmbientOcclusion();
        }
        return this.ambientOcclusion;
    }

    public GuiLight getGuiLight() {
        if (this.guiLight != null) {
            return this.guiLight;
        }
        if (this.parent != null) {
            return this.parent.getGuiLight();
        }
        return GuiLight.field_21859;
    }

    public List<ModelOverride> getOverrides() {
        return this.overrides;
    }

    private ModelOverrideList compileOverrides(ModelLoader modelLoader, JsonUnbakedModel parent) {
        if (this.overrides.isEmpty()) {
            return ModelOverrideList.EMPTY;
        }
        return new ModelOverrideList(modelLoader, parent, modelLoader::getOrLoadModel, this.overrides);
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        HashSet hashSet = Sets.newHashSet();
        for (ModelOverride modelOverride : this.overrides) {
            hashSet.add(modelOverride.getModelId());
        }
        if (this.parentId != null) {
            hashSet.add(this.parentId);
        }
        return hashSet;
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        Object object;
        LinkedHashSet linkedHashSet = Sets.newLinkedHashSet();
        JsonUnbakedModel _snowman2 = this;
        while (_snowman2.parentId != null && _snowman2.parent == null) {
            linkedHashSet.add(_snowman2);
            object = unbakedModelGetter.apply(_snowman2.parentId);
            if (object == null) {
                LOGGER.warn("No parent '{}' while loading model '{}'", (Object)this.parentId, (Object)_snowman2);
            }
            if (linkedHashSet.contains(object)) {
                LOGGER.warn("Found 'parent' loop while loading model '{}' in chain: {} -> {}", (Object)_snowman2, (Object)linkedHashSet.stream().map(Object::toString).collect(Collectors.joining(" -> ")), (Object)this.parentId);
                object = null;
            }
            if (object == null) {
                _snowman2.parentId = ModelLoader.MISSING;
                object = unbakedModelGetter.apply(_snowman2.parentId);
            }
            if (!(object instanceof JsonUnbakedModel)) {
                throw new IllegalStateException("BlockModel parent has to be a block model.");
            }
            _snowman2.parent = (JsonUnbakedModel)object;
            _snowman2 = _snowman2.parent;
        }
        object = Sets.newHashSet((Object[])new SpriteIdentifier[]{this.resolveSprite("particle")});
        for (ModelElement modelElement : this.getElements()) {
            for (ModelElementFace modelElementFace : modelElement.faces.values()) {
                SpriteIdentifier spriteIdentifier = this.resolveSprite(modelElementFace.textureId);
                if (Objects.equals(spriteIdentifier.getTextureId(), MissingSprite.getMissingSpriteId())) {
                    unresolvedTextureReferences.add((Pair<String, String>)Pair.of((Object)modelElementFace.textureId, (Object)this.id));
                }
                object.add(spriteIdentifier);
            }
        }
        this.overrides.forEach(arg_0 -> this.method_3441(unbakedModelGetter, (Set)object, unresolvedTextureReferences, arg_0));
        if (this.getRootModel() == ModelLoader.GENERATION_MARKER) {
            ItemModelGenerator.LAYERS.forEach(arg_0 -> this.method_3435((Set)object, arg_0));
        }
        return object;
    }

    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        return this.bake(loader, this, textureGetter, rotationContainer, modelId, true);
    }

    public BakedModel bake(ModelLoader loader, JsonUnbakedModel parent, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings settings, Identifier id, boolean hasDepth) {
        Sprite sprite = textureGetter.apply(this.resolveSprite("particle"));
        if (this.getRootModel() == ModelLoader.BLOCK_ENTITY_MARKER) {
            return new BuiltinBakedModel(this.getTransformations(), this.compileOverrides(loader, parent), sprite, this.getGuiLight().isSide());
        }
        BasicBakedModel.Builder _snowman2 = new BasicBakedModel.Builder(this, this.compileOverrides(loader, parent), hasDepth).setParticle(sprite);
        for (ModelElement modelElement : this.getElements()) {
            for (Direction direction : modelElement.faces.keySet()) {
                ModelElementFace modelElementFace = modelElement.faces.get(direction);
                Sprite _snowman3 = textureGetter.apply(this.resolveSprite(modelElementFace.textureId));
                if (modelElementFace.cullFace == null) {
                    _snowman2.addQuad(JsonUnbakedModel.createQuad(modelElement, modelElementFace, _snowman3, direction, settings, id));
                    continue;
                }
                _snowman2.addQuad(Direction.transform(settings.getRotation().getMatrix(), modelElementFace.cullFace), JsonUnbakedModel.createQuad(modelElement, modelElementFace, _snowman3, direction, settings, id));
            }
        }
        return _snowman2.build();
    }

    private static BakedQuad createQuad(ModelElement element, ModelElementFace elementFace, Sprite sprite, Direction side, ModelBakeSettings settings, Identifier id) {
        return QUAD_FACTORY.bake(element.from, element.to, elementFace, sprite, side, settings, element.rotation, element.shade, id);
    }

    public boolean textureExists(String name) {
        return !MissingSprite.getMissingSpriteId().equals(this.resolveSprite(name).getTextureId());
    }

    public SpriteIdentifier resolveSprite(String spriteName) {
        if (JsonUnbakedModel.isTextureReference(spriteName)) {
            spriteName = spriteName.substring(1);
        }
        ArrayList arrayList = Lists.newArrayList();
        while (!(_snowman = (_snowman = this.resolveTexture(spriteName)).left()).isPresent()) {
            spriteName = (String)_snowman.right().get();
            if (arrayList.contains(spriteName)) {
                LOGGER.warn("Unable to resolve texture due to reference chain {}->{} in {}", (Object)Joiner.on((String)"->").join((Iterable)arrayList), (Object)spriteName, (Object)this.id);
                return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, MissingSprite.getMissingSpriteId());
            }
            arrayList.add(spriteName);
        }
        return (SpriteIdentifier)_snowman.get();
    }

    private Either<SpriteIdentifier, String> resolveTexture(String name) {
        JsonUnbakedModel _snowman2 = this;
        while (_snowman2 != null) {
            Either<SpriteIdentifier, String> either = _snowman2.textureMap.get(name);
            if (either != null) {
                return either;
            }
            _snowman2 = _snowman2.parent;
        }
        return Either.left((Object)new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, MissingSprite.getMissingSpriteId()));
    }

    private static boolean isTextureReference(String reference) {
        return reference.charAt(0) == '#';
    }

    public JsonUnbakedModel getRootModel() {
        return this.parent == null ? this : this.parent.getRootModel();
    }

    public ModelTransformation getTransformations() {
        Transformation transformation = this.getTransformation(ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND);
        _snowman = this.getTransformation(ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND);
        _snowman = this.getTransformation(ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND);
        _snowman = this.getTransformation(ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND);
        _snowman = this.getTransformation(ModelTransformation.Mode.HEAD);
        _snowman = this.getTransformation(ModelTransformation.Mode.GUI);
        _snowman = this.getTransformation(ModelTransformation.Mode.GROUND);
        _snowman = this.getTransformation(ModelTransformation.Mode.FIXED);
        return new ModelTransformation(transformation, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
    }

    private Transformation getTransformation(ModelTransformation.Mode renderMode) {
        if (this.parent != null && !this.transformations.isTransformationDefined(renderMode)) {
            return this.parent.getTransformation(renderMode);
        }
        return this.transformations.getTransformation(renderMode);
    }

    public String toString() {
        return this.id;
    }

    private /* synthetic */ void method_3435(Set set, String string) {
        set.add(this.resolveSprite(string));
    }

    private /* synthetic */ void method_3441(Function function, Set set, Set set2, ModelOverride modelOverride) {
        UnbakedModel unbakedModel = (UnbakedModel)function.apply(modelOverride.getModelId());
        if (Objects.equals(unbakedModel, this)) {
            return;
        }
        set.addAll(unbakedModel.getTextureDependencies(function, set2));
    }

    public static enum GuiLight {
        field_21858("front"),
        field_21859("side");

        private final String name;

        private GuiLight(String name) {
            this.name = name;
        }

        public static GuiLight deserialize(String value) {
            for (GuiLight guiLight : GuiLight.values()) {
                if (!guiLight.name.equals(value)) continue;
                return guiLight;
            }
            throw new IllegalArgumentException("Invalid gui light: " + value);
        }

        public boolean isSide() {
            return this == field_21859;
        }
    }

    public static class Deserializer
    implements JsonDeserializer<JsonUnbakedModel> {
        public JsonUnbakedModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext2) throws JsonParseException {
            JsonDeserializationContext jsonDeserializationContext2;
            Object _snowman7;
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            List<ModelElement> _snowman2 = this.deserializeElements(jsonDeserializationContext2, jsonObject);
            String _snowman3 = this.deserializeParent(jsonObject);
            Map<String, Either<SpriteIdentifier, String>> _snowman4 = this.deserializeTextures(jsonObject);
            boolean _snowman5 = this.deserializeAmbientOcclusion(jsonObject);
            ModelTransformation _snowman6 = ModelTransformation.NONE;
            if (jsonObject.has("display")) {
                _snowman7 = JsonHelper.getObject(jsonObject, "display");
                _snowman6 = (ModelTransformation)jsonDeserializationContext2.deserialize((JsonElement)_snowman7, ModelTransformation.class);
            }
            _snowman7 = this.deserializeOverrides(jsonDeserializationContext2, jsonObject);
            GuiLight _snowman8 = null;
            if (jsonObject.has("gui_light")) {
                _snowman8 = GuiLight.deserialize(JsonHelper.getString(jsonObject, "gui_light"));
            }
            Identifier _snowman9 = _snowman3.isEmpty() ? null : new Identifier(_snowman3);
            return new JsonUnbakedModel(_snowman9, _snowman2, _snowman4, _snowman5, _snowman8, _snowman6, (List<ModelOverride>)_snowman7);
        }

        protected List<ModelOverride> deserializeOverrides(JsonDeserializationContext context, JsonObject object) {
            ArrayList arrayList = Lists.newArrayList();
            if (object.has("overrides")) {
                JsonArray jsonArray = JsonHelper.getArray(object, "overrides");
                for (JsonElement jsonElement : jsonArray) {
                    arrayList.add(context.deserialize(jsonElement, ModelOverride.class));
                }
            }
            return arrayList;
        }

        private Map<String, Either<SpriteIdentifier, String>> deserializeTextures(JsonObject object) {
            Identifier identifier = SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
            HashMap _snowman2 = Maps.newHashMap();
            if (object.has("textures")) {
                JsonObject jsonObject = JsonHelper.getObject(object, "textures");
                for (Map.Entry entry : jsonObject.entrySet()) {
                    _snowman2.put(entry.getKey(), Deserializer.resolveReference(identifier, ((JsonElement)entry.getValue()).getAsString()));
                }
            }
            return _snowman2;
        }

        private static Either<SpriteIdentifier, String> resolveReference(Identifier id, String name) {
            if (JsonUnbakedModel.isTextureReference(name)) {
                return Either.right((Object)name.substring(1));
            }
            Identifier identifier = Identifier.tryParse(name);
            if (identifier == null) {
                throw new JsonParseException(name + " is not valid resource location");
            }
            return Either.left((Object)new SpriteIdentifier(id, identifier));
        }

        private String deserializeParent(JsonObject json) {
            return JsonHelper.getString(json, "parent", "");
        }

        protected boolean deserializeAmbientOcclusion(JsonObject json) {
            return JsonHelper.getBoolean(json, "ambientocclusion", true);
        }

        protected List<ModelElement> deserializeElements(JsonDeserializationContext context, JsonObject json) {
            ArrayList arrayList = Lists.newArrayList();
            if (json.has("elements")) {
                for (JsonElement jsonElement : JsonHelper.getArray(json, "elements")) {
                    arrayList.add(context.deserialize(jsonElement, ModelElement.class));
                }
            }
            return arrayList;
        }

        public /* synthetic */ Object deserialize(JsonElement element, Type unused, JsonDeserializationContext ctx) throws JsonParseException {
            return this.deserialize(element, unused, ctx);
        }
    }
}

