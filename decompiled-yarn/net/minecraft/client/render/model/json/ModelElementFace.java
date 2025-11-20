package net.minecraft.client.render.model.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Direction;

public class ModelElementFace {
   public final Direction cullFace;
   public final int tintIndex;
   public final String textureId;
   public final ModelElementTexture textureData;

   public ModelElementFace(@Nullable Direction cullFace, int tintIndex, String textureId, ModelElementTexture textureData) {
      this.cullFace = cullFace;
      this.tintIndex = tintIndex;
      this.textureId = textureId;
      this.textureData = textureData;
   }

   public static class Deserializer implements JsonDeserializer<ModelElementFace> {
      protected Deserializer() {
      }

      public ModelElementFace deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = _snowman.getAsJsonObject();
         Direction _snowmanxxxx = this.deserializeCullFace(_snowmanxxx);
         int _snowmanxxxxx = this.deserializeTintIndex(_snowmanxxx);
         String _snowmanxxxxxx = this.deserializeTexture(_snowmanxxx);
         ModelElementTexture _snowmanxxxxxxx = (ModelElementTexture)_snowman.deserialize(_snowmanxxx, ModelElementTexture.class);
         return new ModelElementFace(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
      }

      protected int deserializeTintIndex(JsonObject object) {
         return JsonHelper.getInt(object, "tintindex", -1);
      }

      private String deserializeTexture(JsonObject object) {
         return JsonHelper.getString(object, "texture");
      }

      @Nullable
      private Direction deserializeCullFace(JsonObject object) {
         String _snowman = JsonHelper.getString(object, "cullface", "");
         return Direction.byName(_snowman);
      }
   }
}
