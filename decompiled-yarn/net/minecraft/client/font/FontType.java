package net.minecraft.client.font;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.util.Util;

public enum FontType {
   BITMAP("bitmap", BitmapFont.Loader::fromJson),
   TTF("ttf", TrueTypeFontLoader::fromJson),
   LEGACY_UNICODE("legacy_unicode", UnicodeTextureFont.Loader::fromJson);

   private static final Map<String, FontType> REGISTRY = Util.make(Maps.newHashMap(), _snowman -> {
      for (FontType _snowmanx : values()) {
         _snowman.put(_snowmanx.id, _snowmanx);
      }
   });
   private final String id;
   private final Function<JsonObject, FontLoader> loaderFactory;

   private FontType(String id, Function<JsonObject, FontLoader> factory) {
      this.id = id;
      this.loaderFactory = factory;
   }

   public static FontType byId(String id) {
      FontType _snowman = REGISTRY.get(id);
      if (_snowman == null) {
         throw new IllegalArgumentException("Invalid type: " + id);
      } else {
         return _snowman;
      }
   }

   public FontLoader createLoader(JsonObject json) {
      return this.loaderFactory.apply(json);
   }
}
