package net.minecraft.client.font;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Util;

@Environment(EnvType.CLIENT)
public enum FontType {
   BITMAP("bitmap", BitmapFont.Loader::fromJson),
   TTF("ttf", TrueTypeFontLoader::fromJson),
   LEGACY_UNICODE("legacy_unicode", UnicodeTextureFont.Loader::fromJson);

   private static final Map<String, FontType> REGISTRY = Util.make(Maps.newHashMap(), hashMap -> {
      for (FontType lv : values()) {
         hashMap.put(lv.id, lv);
      }
   });
   private final String id;
   private final Function<JsonObject, FontLoader> loaderFactory;

   private FontType(String id, Function<JsonObject, FontLoader> factory) {
      this.id = id;
      this.loaderFactory = factory;
   }

   public static FontType byId(String id) {
      FontType lv = REGISTRY.get(id);
      if (lv == null) {
         throw new IllegalArgumentException("Invalid type: " + id);
      } else {
         return lv;
      }
   }

   public FontLoader createLoader(JsonObject json) {
      return this.loaderFactory.apply(json);
   }
}
