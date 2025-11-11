import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;

public class dnj implements dng {
   private static final Logger a = LogManager.getLogger();
   private final vk b;
   private final float c;
   private final float d;
   private final float e;
   private final float f;
   private final String g;

   public dnj(vk var1, float var2, float var3, float var4, float var5, String var6) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
   }

   public static dng a(JsonObject var0) {
      float _snowman = 0.0F;
      float _snowmanx = 0.0F;
      if (_snowman.has("shift")) {
         JsonArray _snowmanxx = _snowman.getAsJsonArray("shift");
         if (_snowmanxx.size() != 2) {
            throw new JsonParseException("Expected 2 elements in 'shift', found " + _snowmanxx.size());
         }

         _snowman = afd.e(_snowmanxx.get(0), "shift[0]");
         _snowmanx = afd.e(_snowmanxx.get(1), "shift[1]");
      }

      StringBuilder _snowmanxx = new StringBuilder();
      if (_snowman.has("skip")) {
         JsonElement _snowmanxxx = _snowman.get("skip");
         if (_snowmanxxx.isJsonArray()) {
            JsonArray _snowmanxxxx = afd.n(_snowmanxxx, "skip");

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
               _snowmanxx.append(afd.a(_snowmanxxxx.get(_snowmanxxxxx), "skip[" + _snowmanxxxxx + "]"));
            }
         } else {
            _snowmanxx.append(afd.a(_snowmanxxx, "skip"));
         }
      }

      return new dnj(new vk(afd.h(_snowman, "file")), afd.a(_snowman, "size", 11.0F), afd.a(_snowman, "oversample", 1.0F), _snowman, _snowmanx, _snowmanxx.toString());
   }

   @Nullable
   @Override
   public deb a(ach var1) {
      STBTTFontinfo _snowman = null;
      ByteBuffer _snowmanx = null;

      try (acg _snowmanxx = _snowman.a(new vk(this.b.b(), "font/" + this.b.a()))) {
         a.debug("Loading font {}", this.b);
         _snowman = STBTTFontinfo.malloc();
         _snowmanx = dex.a(_snowmanxx.b());
         ((Buffer)_snowmanx).flip();
         a.debug("Reading font {}", this.b);
         if (!STBTruetype.stbtt_InitFont(_snowman, _snowmanx)) {
            throw new IOException("Invalid ttf");
         } else {
            return new ded(_snowmanx, _snowman, this.c, this.d, this.e, this.f, this.g);
         }
      } catch (Exception var18) {
         a.error("Couldn't load truetype font {}", this.b, var18);
         if (_snowman != null) {
            _snowman.free();
         }

         MemoryUtil.memFree(_snowmanx);
         return null;
      }
   }
}
