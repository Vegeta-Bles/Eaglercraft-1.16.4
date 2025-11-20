package net.minecraft.client.resource.metadata;

import com.google.gson.JsonObject;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.JsonHelper;

public class TextureResourceMetadataReader implements ResourceMetadataReader<TextureResourceMetadata> {
   public TextureResourceMetadataReader() {
   }

   public TextureResourceMetadata fromJson(JsonObject _snowman) {
      boolean _snowmanx = JsonHelper.getBoolean(_snowman, "blur", false);
      boolean _snowmanxx = JsonHelper.getBoolean(_snowman, "clamp", false);
      return new TextureResourceMetadata(_snowmanx, _snowmanxx);
   }

   @Override
   public String getKey() {
      return "texture";
   }
}
