package net.minecraft.client.font;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;

public class TrueTypeFontLoader implements FontLoader {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Identifier filename;
   private final float size;
   private final float oversample;
   private final float shiftX;
   private final float shiftY;
   private final String excludedCharacters;

   public TrueTypeFontLoader(Identifier filename, float size, float oversample, float shiftX, float shiftY, String excludedCharacters) {
      this.filename = filename;
      this.size = size;
      this.oversample = oversample;
      this.shiftX = shiftX;
      this.shiftY = shiftY;
      this.excludedCharacters = excludedCharacters;
   }

   public static FontLoader fromJson(JsonObject json) {
      float _snowman = 0.0F;
      float _snowmanx = 0.0F;
      if (json.has("shift")) {
         JsonArray _snowmanxx = json.getAsJsonArray("shift");
         if (_snowmanxx.size() != 2) {
            throw new JsonParseException("Expected 2 elements in 'shift', found " + _snowmanxx.size());
         }

         _snowman = JsonHelper.asFloat(_snowmanxx.get(0), "shift[0]");
         _snowmanx = JsonHelper.asFloat(_snowmanxx.get(1), "shift[1]");
      }

      StringBuilder _snowmanxx = new StringBuilder();
      if (json.has("skip")) {
         JsonElement _snowmanxxx = json.get("skip");
         if (_snowmanxxx.isJsonArray()) {
            JsonArray _snowmanxxxx = JsonHelper.asArray(_snowmanxxx, "skip");

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
               _snowmanxx.append(JsonHelper.asString(_snowmanxxxx.get(_snowmanxxxxx), "skip[" + _snowmanxxxxx + "]"));
            }
         } else {
            _snowmanxx.append(JsonHelper.asString(_snowmanxxx, "skip"));
         }
      }

      return new TrueTypeFontLoader(
         new Identifier(JsonHelper.getString(json, "file")),
         JsonHelper.getFloat(json, "size", 11.0F),
         JsonHelper.getFloat(json, "oversample", 1.0F),
         _snowman,
         _snowmanx,
         _snowmanxx.toString()
      );
   }

   @Nullable
   @Override
   public Font load(ResourceManager manager) {
      STBTTFontinfo _snowman = null;
      ByteBuffer _snowmanx = null;

      try (Resource _snowmanxx = manager.getResource(new Identifier(this.filename.getNamespace(), "font/" + this.filename.getPath()))) {
         LOGGER.debug("Loading font {}", this.filename);
         _snowman = STBTTFontinfo.malloc();
         _snowmanx = TextureUtil.readAllToByteBuffer(_snowmanxx.getInputStream());
         ((Buffer)_snowmanx).flip();
         LOGGER.debug("Reading font {}", this.filename);
         if (!STBTruetype.stbtt_InitFont(_snowman, _snowmanx)) {
            throw new IOException("Invalid ttf");
         } else {
            return new TrueTypeFont(_snowmanx, _snowman, this.size, this.oversample, this.shiftX, this.shiftY, this.excludedCharacters);
         }
      } catch (Exception var18) {
         LOGGER.error("Couldn't load truetype font {}", this.filename, var18);
         if (_snowman != null) {
            _snowman.free();
         }

         MemoryUtil.memFree(_snowmanx);
         return null;
      }
   }
}
