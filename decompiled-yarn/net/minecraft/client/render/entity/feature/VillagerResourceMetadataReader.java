package net.minecraft.client.render.entity.feature;

import com.google.gson.JsonObject;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.JsonHelper;

public class VillagerResourceMetadataReader implements ResourceMetadataReader<VillagerResourceMetadata> {
   public VillagerResourceMetadataReader() {
   }

   public VillagerResourceMetadata fromJson(JsonObject _snowman) {
      return new VillagerResourceMetadata(VillagerResourceMetadata.HatType.from(JsonHelper.getString(_snowman, "hat", "none")));
   }

   @Override
   public String getKey() {
      return "villager";
   }
}
