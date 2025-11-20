package net.minecraft.client.font;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
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

public class FontManager implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Identifier MISSING_STORAGE_ID = new Identifier("minecraft", "missing");
   private final FontStorage missingStorage;
   private final Map<Identifier, FontStorage> fontStorages = Maps.newHashMap();
   private final TextureManager textureManager;
   private Map<Identifier, Identifier> idOverrides = ImmutableMap.of();
   private final ResourceReloadListener resourceReloadListener = new SinglePreparationResourceReloadListener<Map<Identifier, List<Font>>>() {
      protected Map<Identifier, List<Font>> prepare(ResourceManager _snowman, Profiler _snowman) {
         _snowman.startTick();
         Gson _snowmanxx = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
         Map<Identifier, List<Font>> _snowmanxxx = Maps.newHashMap();

         for (Identifier _snowmanxxxx : _snowman.findResources("font", _snowmanxxxxx -> _snowmanxxxxx.endsWith(".json"))) {
            String _snowmanxxxxx = _snowmanxxxx.getPath();
            Identifier _snowmanxxxxxx = new Identifier(_snowmanxxxx.getNamespace(), _snowmanxxxxx.substring("font/".length(), _snowmanxxxxx.length() - ".json".length()));
            List<Font> _snowmanxxxxxxx = _snowmanxxx.computeIfAbsent(_snowmanxxxxxx, _snowmanxxxxxxxx -> Lists.newArrayList(new Font[]{new BlankFont()}));
            _snowman.push(_snowmanxxxxxx::toString);

            try {
               for (Resource _snowmanxxxxxxxx : _snowman.getAllResources(_snowmanxxxx)) {
                  _snowman.push(_snowmanxxxxxxxx::getResourcePackName);

                  try (
                     InputStream _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getInputStream();
                     Reader _snowmanxxxxxxxxxx = new BufferedReader(new InputStreamReader(_snowmanxxxxxxxxx, StandardCharsets.UTF_8));
                  ) {
                     _snowman.push("reading");
                     JsonArray _snowmanxxxxxxxxxxx = JsonHelper.getArray(JsonHelper.deserialize(_snowmanxx, _snowmanxxxxxxxxxx, JsonObject.class), "providers");
                     _snowman.swap("parsing");

                     for (int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.size() - 1; _snowmanxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxx--) {
                        JsonObject _snowmanxxxxxxxxxxxxx = JsonHelper.asObject(_snowmanxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxx), "providers[" + _snowmanxxxxxxxxxxxx + "]");

                        try {
                           String _snowmanxxxxxxxxxxxxxx = JsonHelper.getString(_snowmanxxxxxxxxxxxxx, "type");
                           FontType _snowmanxxxxxxxxxxxxxxx = FontType.byId(_snowmanxxxxxxxxxxxxxx);
                           _snowman.push(_snowmanxxxxxxxxxxxxxx);
                           Font _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.createLoader(_snowmanxxxxxxxxxxxxx).load(_snowman);
                           if (_snowmanxxxxxxxxxxxxxxxx != null) {
                              _snowmanxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxx);
                           }

                           _snowman.pop();
                        } catch (RuntimeException var49) {
                           FontManager.LOGGER
                              .warn(
                                 "Unable to read definition '{}' in fonts.json in resourcepack: '{}': {}",
                                 _snowmanxxxxxx,
                                 _snowmanxxxxxxxx.getResourcePackName(),
                                 var49.getMessage()
                              );
                        }
                     }

                     _snowman.pop();
                  } catch (RuntimeException var54) {
                     FontManager.LOGGER
                        .warn("Unable to load font '{}' in fonts.json in resourcepack: '{}': {}", _snowmanxxxxxx, _snowmanxxxxxxxx.getResourcePackName(), var54.getMessage());
                  }

                  _snowman.pop();
               }
            } catch (IOException var55) {
               FontManager.LOGGER.warn("Unable to load font '{}' in fonts.json: {}", _snowmanxxxxxx, var55.getMessage());
            }

            _snowman.push("caching");
            IntSet _snowmanxxxxxxxx = new IntOpenHashSet();

            for (Font _snowmanxxxxxxxxx : _snowmanxxxxxxx) {
               _snowmanxxxxxxxx.addAll(_snowmanxxxxxxxxx.getProvidedGlyphs());
            }

            _snowmanxxxxxxxx.forEach(_snowmanxxxxxxxxx -> {
               if (_snowmanxxxxxxxxx != 32) {
                  for (Font _snowmanxxxxxxxxxx : Lists.reverse(_snowman)) {
                     if (_snowmanxxxxxxxxxx.getGlyph(_snowmanxxxxxxxxx) != null) {
                        break;
                     }
                  }
               }
            });
            _snowman.pop();
            _snowman.pop();
         }

         _snowman.endTick();
         return _snowmanxxx;
      }

      protected void apply(Map<Identifier, List<Font>> _snowman, ResourceManager _snowman, Profiler _snowman) {
         _snowman.startTick();
         _snowman.push("closing");
         FontManager.this.fontStorages.values().forEach(FontStorage::close);
         FontManager.this.fontStorages.clear();
         _snowman.swap("reloading");
         _snowman.forEach((_snowmanxxxxx, _snowmanxxxx) -> {
            FontStorage _snowmanxx = new FontStorage(FontManager.this.textureManager, _snowmanxxxxx);
            _snowmanxx.setFonts(Lists.reverse(_snowmanxxxx));
            FontManager.this.fontStorages.put(_snowmanxxxxx, _snowmanxx);
         });
         _snowman.pop();
         _snowman.endTick();
      }

      @Override
      public String getName() {
         return "FontManager";
      }
   };

   public FontManager(TextureManager manager) {
      this.textureManager = manager;
      this.missingStorage = Util.make(new FontStorage(manager, MISSING_STORAGE_ID), _snowman -> _snowman.setFonts(Lists.newArrayList(new Font[]{new BlankFont()})));
   }

   public void setIdOverrides(Map<Identifier, Identifier> overrides) {
      this.idOverrides = overrides;
   }

   public TextRenderer createTextRenderer() {
      return new TextRenderer(_snowman -> this.fontStorages.getOrDefault(this.idOverrides.getOrDefault(_snowman, _snowman), this.missingStorage));
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
