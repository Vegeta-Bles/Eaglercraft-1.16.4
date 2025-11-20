package net.minecraft.client.render.entity.feature;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class VillagerResourceMetadata {
   public static final VillagerResourceMetadataReader READER = new VillagerResourceMetadataReader();
   private final VillagerResourceMetadata.HatType hatType;

   public VillagerResourceMetadata(VillagerResourceMetadata.HatType _snowman) {
      this.hatType = _snowman;
   }

   public VillagerResourceMetadata.HatType getHatType() {
      return this.hatType;
   }

   public static enum HatType {
      NONE("none"),
      PARTIAL("partial"),
      FULL("full");

      private static final Map<String, VillagerResourceMetadata.HatType> byName = Arrays.stream(values())
         .collect(Collectors.toMap(VillagerResourceMetadata.HatType::getName, _snowman -> (VillagerResourceMetadata.HatType)_snowman));
      private final String name;

      private HatType(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public static VillagerResourceMetadata.HatType from(String name) {
         return byName.getOrDefault(name, NONE);
      }
   }
}
