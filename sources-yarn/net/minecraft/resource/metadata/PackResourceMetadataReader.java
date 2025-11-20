package net.minecraft.resource.metadata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;

public class PackResourceMetadataReader implements ResourceMetadataReader<PackResourceMetadata> {
   public PackResourceMetadataReader() {
   }

   public PackResourceMetadata fromJson(JsonObject jsonObject) {
      Text lv = Text.Serializer.fromJson(jsonObject.get("description"));
      if (lv == null) {
         throw new JsonParseException("Invalid/missing description!");
      } else {
         int i = JsonHelper.getInt(jsonObject, "pack_format");
         return new PackResourceMetadata(lv, i);
      }
   }

   @Override
   public String getKey() {
      return "pack";
   }
}
