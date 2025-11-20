package net.minecraft.resource.metadata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;

public class PackResourceMetadataReader implements ResourceMetadataReader<PackResourceMetadata> {
   public PackResourceMetadataReader() {
   }

   public PackResourceMetadata fromJson(JsonObject _snowman) {
      Text _snowmanx = Text.Serializer.fromJson(_snowman.get("description"));
      if (_snowmanx == null) {
         throw new JsonParseException("Invalid/missing description!");
      } else {
         int _snowmanxx = JsonHelper.getInt(_snowman, "pack_format");
         return new PackResourceMetadata(_snowmanx, _snowmanxx);
      }
   }

   @Override
   public String getKey() {
      return "pack";
   }
}
