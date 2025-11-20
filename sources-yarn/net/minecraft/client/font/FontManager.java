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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class FontManager implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Identifier MISSING_STORAGE_ID = new Identifier("minecraft", "missing");
   private final FontStorage missingStorage;
   private final Map<Identifier, FontStorage> fontStorages = Maps.newHashMap();
   private final TextureManager textureManager;
   private Map<Identifier, Identifier> idOverrides = ImmutableMap.of();
   private final ResourceReloadListener resourceReloadListener = new SinglePreparationResourceReloadListener<Map<Identifier, List<Font>>>() {
      protected Map<Identifier, List<Font>> prepare(ResourceManager arg, Profiler arg2) {
         arg2.startTick();
         Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
         Map<Identifier, List<Font>> map = Maps.newHashMap();

         for (Identifier lv : arg.findResources("font", stringx -> stringx.endsWith(".json"))) {
            String string = lv.getPath();
            Identifier lv2 = new Identifier(lv.getNamespace(), string.substring("font/".length(), string.length() - ".json".length()));
            List<Font> list = map.computeIfAbsent(lv2, argx -> Lists.newArrayList(new Font[]{new BlankFont()}));
            arg2.push(lv2::toString);

            try {
               for (Resource lv3 : arg.getAllResources(lv)) {
                  arg2.push(lv3::getResourcePackName);

                  try (
                     InputStream inputStream = lv3.getInputStream();
                     Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                  ) {
                     arg2.push("reading");
                     JsonArray jsonArray = JsonHelper.getArray(JsonHelper.deserialize(gson, reader, JsonObject.class), "providers");
                     arg2.swap("parsing");

                     for (int i = jsonArray.size() - 1; i >= 0; i--) {
                        JsonObject jsonObject = JsonHelper.asObject(jsonArray.get(i), "providers[" + i + "]");

                        try {
                           String string2 = JsonHelper.getString(jsonObject, "type");
                           FontType lv4 = FontType.byId(string2);
                           arg2.push(string2);
                           Font lv5 = lv4.createLoader(jsonObject).load(arg);
                           if (lv5 != null) {
                              list.add(lv5);
                           }

                           arg2.pop();
                        } catch (RuntimeException var49) {
                           FontManager.LOGGER
                              .warn(
                                 "Unable to read definition '{}' in fonts.json in resourcepack: '{}': {}", lv2, lv3.getResourcePackName(), var49.getMessage()
                              );
                        }
                     }

                     arg2.pop();
                  } catch (RuntimeException var54) {
                     FontManager.LOGGER
                        .warn("Unable to load font '{}' in fonts.json in resourcepack: '{}': {}", lv2, lv3.getResourcePackName(), var54.getMessage());
                  }

                  arg2.pop();
               }
            } catch (IOException var55) {
               FontManager.LOGGER.warn("Unable to load font '{}' in fonts.json: {}", lv2, var55.getMessage());
            }

            arg2.push("caching");
            IntSet intSet = new IntOpenHashSet();

            for (Font lv6 : list) {
               intSet.addAll(lv6.getProvidedGlyphs());
            }

            intSet.forEach(ix -> {
               if (ix != 32) {
                  for (Font lvx : Lists.reverse(list)) {
                     if (lvx.getGlyph(ix) != null) {
                        break;
                     }
                  }
               }
            });
            arg2.pop();
            arg2.pop();
         }

         arg2.endTick();
         return map;
      }

      protected void apply(Map<Identifier, List<Font>> map, ResourceManager arg, Profiler arg2) {
         arg2.startTick();
         arg2.push("closing");
         FontManager.this.fontStorages.values().forEach(FontStorage::close);
         FontManager.this.fontStorages.clear();
         arg2.swap("reloading");
         map.forEach((argx, list) -> {
            FontStorage lv = new FontStorage(FontManager.this.textureManager, argx);
            lv.setFonts(Lists.reverse(list));
            FontManager.this.fontStorages.put(argx, lv);
         });
         arg2.pop();
         arg2.endTick();
      }

      @Override
      public String getName() {
         return "FontManager";
      }
   };

   public FontManager(TextureManager manager) {
      this.textureManager = manager;
      this.missingStorage = Util.make(new FontStorage(manager, MISSING_STORAGE_ID), arg -> arg.setFonts(Lists.newArrayList(new Font[]{new BlankFont()})));
   }

   public void setIdOverrides(Map<Identifier, Identifier> overrides) {
      this.idOverrides = overrides;
   }

   public TextRenderer createTextRenderer() {
      return new TextRenderer(arg -> this.fontStorages.getOrDefault(this.idOverrides.getOrDefault(arg, arg), this.missingStorage));
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
