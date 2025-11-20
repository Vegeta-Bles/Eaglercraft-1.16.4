/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.font;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.font.BlankFont;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.FontType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FontManager
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Identifier MISSING_STORAGE_ID = new Identifier("minecraft", "missing");
    private final FontStorage missingStorage;
    private final Map<Identifier, FontStorage> fontStorages = Maps.newHashMap();
    private final TextureManager textureManager;
    private Map<Identifier, Identifier> idOverrides = ImmutableMap.of();
    private final ResourceReloadListener resourceReloadListener = new SinglePreparationResourceReloadListener<Map<Identifier, List<Font>>>(){

        @Override
        protected Map<Identifier, List<Font>> prepare(ResourceManager resourceManager, Profiler profiler2) {
            Profiler profiler2;
            profiler2.startTick();
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            HashMap _snowman2 = Maps.newHashMap();
            for (Identifier identifier2 : resourceManager.findResources("font", string -> string.endsWith(".json"))) {
                String string2 = identifier2.getPath();
                Identifier _snowman3 = new Identifier(identifier2.getNamespace(), string2.substring("font/".length(), string2.length() - ".json".length()));
                List _snowman4 = _snowman2.computeIfAbsent(_snowman3, identifier -> Lists.newArrayList((Object[])new Font[]{new BlankFont()}));
                profiler2.push(_snowman3::toString);
                try {
                    for (Resource resource : resourceManager.getAllResources(identifier2)) {
                        profiler2.push(resource::getResourcePackName);
                        try (Closeable closeable = resource.getInputStream();
                             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)closeable, StandardCharsets.UTF_8));){
                            profiler2.push("reading");
                            JsonArray jsonArray = JsonHelper.getArray(JsonHelper.deserialize(gson, (Reader)bufferedReader, JsonObject.class), "providers");
                            profiler2.swap("parsing");
                            for (int i = jsonArray.size() - 1; i >= 0; --i) {
                                JsonObject jsonObject = JsonHelper.asObject(jsonArray.get(i), "providers[" + i + "]");
                                try {
                                    String string3 = JsonHelper.getString(jsonObject, "type");
                                    FontType _snowman5 = FontType.byId(string3);
                                    profiler2.push(string3);
                                    Font _snowman6 = _snowman5.createLoader(jsonObject).load(resourceManager);
                                    if (_snowman6 != null) {
                                        _snowman4.add(_snowman6);
                                    }
                                    profiler2.pop();
                                    continue;
                                }
                                catch (RuntimeException runtimeException) {
                                    LOGGER.warn("Unable to read definition '{}' in fonts.json in resourcepack: '{}': {}", (Object)_snowman3, (Object)resource.getResourcePackName(), (Object)runtimeException.getMessage());
                                }
                            }
                            profiler2.pop();
                        }
                        catch (RuntimeException runtimeException) {
                            LOGGER.warn("Unable to load font '{}' in fonts.json in resourcepack: '{}': {}", (Object)_snowman3, (Object)resource.getResourcePackName(), (Object)runtimeException.getMessage());
                        }
                        profiler2.pop();
                    }
                }
                catch (IOException iOException) {
                    LOGGER.warn("Unable to load font '{}' in fonts.json: {}", (Object)_snowman3, (Object)iOException.getMessage());
                }
                profiler2.push("caching");
                IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
                for (Closeable closeable : _snowman4) {
                    intOpenHashSet.addAll((IntCollection)closeable.getProvidedGlyphs());
                }
                intOpenHashSet.forEach(n -> {
                    if (n == 32) {
                        return;
                    }
                    Iterator iterator = Lists.reverse((List)_snowman4).iterator();
                    while (iterator.hasNext() && (_snowman = (Font)iterator.next()).getGlyph(n) == null) {
                    }
                });
                profiler2.pop();
                profiler2.pop();
            }
            profiler2.endTick();
            return _snowman2;
        }

        @Override
        protected void apply(Map<Identifier, List<Font>> map, ResourceManager resourceManager, Profiler profiler) {
            profiler.startTick();
            profiler.push("closing");
            FontManager.this.fontStorages.values().forEach(FontStorage::close);
            FontManager.this.fontStorages.clear();
            profiler.swap("reloading");
            map.forEach((identifier, list) -> {
                FontStorage fontStorage = new FontStorage(FontManager.this.textureManager, (Identifier)identifier);
                fontStorage.setFonts(Lists.reverse((List)list));
                FontManager.this.fontStorages.put(identifier, fontStorage);
            });
            profiler.pop();
            profiler.endTick();
        }

        @Override
        public String getName() {
            return "FontManager";
        }

        @Override
        protected /* synthetic */ Object prepare(ResourceManager manager, Profiler profiler) {
            return this.prepare(manager, profiler);
        }
    };

    public FontManager(TextureManager manager) {
        this.textureManager = manager;
        this.missingStorage = Util.make(new FontStorage(manager, MISSING_STORAGE_ID), fontStorage -> fontStorage.setFonts(Lists.newArrayList((Object[])new Font[]{new BlankFont()})));
    }

    public void setIdOverrides(Map<Identifier, Identifier> overrides) {
        this.idOverrides = overrides;
    }

    public TextRenderer createTextRenderer() {
        return new TextRenderer(identifier -> this.fontStorages.getOrDefault(this.idOverrides.getOrDefault(identifier, (Identifier)identifier), this.missingStorage));
    }

    public ResourceReloadListener getResourceReloadListener() {
        return this.resourceReloadListener;
    }

    @Override
    public void close() {
        this.fontStorages.values().forEach(FontStorage::close);
        this.missingStorage.close();
    }
}

