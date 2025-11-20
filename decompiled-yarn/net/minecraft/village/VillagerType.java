package net.minecraft.village;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public final class VillagerType {
   public static final VillagerType DESERT = create("desert");
   public static final VillagerType JUNGLE = create("jungle");
   public static final VillagerType PLAINS = create("plains");
   public static final VillagerType SAVANNA = create("savanna");
   public static final VillagerType SNOW = create("snow");
   public static final VillagerType SWAMP = create("swamp");
   public static final VillagerType TAIGA = create("taiga");
   private final String field_26690;
   private static final Map<RegistryKey<Biome>, VillagerType> BIOME_TO_TYPE = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put(BiomeKeys.BADLANDS, DESERT);
      _snowman.put(BiomeKeys.BADLANDS_PLATEAU, DESERT);
      _snowman.put(BiomeKeys.DESERT, DESERT);
      _snowman.put(BiomeKeys.DESERT_HILLS, DESERT);
      _snowman.put(BiomeKeys.DESERT_LAKES, DESERT);
      _snowman.put(BiomeKeys.ERODED_BADLANDS, DESERT);
      _snowman.put(BiomeKeys.MODIFIED_BADLANDS_PLATEAU, DESERT);
      _snowman.put(BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU, DESERT);
      _snowman.put(BiomeKeys.WOODED_BADLANDS_PLATEAU, DESERT);
      _snowman.put(BiomeKeys.BAMBOO_JUNGLE, JUNGLE);
      _snowman.put(BiomeKeys.BAMBOO_JUNGLE_HILLS, JUNGLE);
      _snowman.put(BiomeKeys.JUNGLE, JUNGLE);
      _snowman.put(BiomeKeys.JUNGLE_EDGE, JUNGLE);
      _snowman.put(BiomeKeys.JUNGLE_HILLS, JUNGLE);
      _snowman.put(BiomeKeys.MODIFIED_JUNGLE, JUNGLE);
      _snowman.put(BiomeKeys.MODIFIED_JUNGLE_EDGE, JUNGLE);
      _snowman.put(BiomeKeys.SAVANNA_PLATEAU, SAVANNA);
      _snowman.put(BiomeKeys.SAVANNA, SAVANNA);
      _snowman.put(BiomeKeys.SHATTERED_SAVANNA, SAVANNA);
      _snowman.put(BiomeKeys.SHATTERED_SAVANNA_PLATEAU, SAVANNA);
      _snowman.put(BiomeKeys.DEEP_FROZEN_OCEAN, SNOW);
      _snowman.put(BiomeKeys.FROZEN_OCEAN, SNOW);
      _snowman.put(BiomeKeys.FROZEN_RIVER, SNOW);
      _snowman.put(BiomeKeys.ICE_SPIKES, SNOW);
      _snowman.put(BiomeKeys.SNOWY_BEACH, SNOW);
      _snowman.put(BiomeKeys.SNOWY_MOUNTAINS, SNOW);
      _snowman.put(BiomeKeys.SNOWY_TAIGA, SNOW);
      _snowman.put(BiomeKeys.SNOWY_TAIGA_HILLS, SNOW);
      _snowman.put(BiomeKeys.SNOWY_TAIGA_MOUNTAINS, SNOW);
      _snowman.put(BiomeKeys.SNOWY_TUNDRA, SNOW);
      _snowman.put(BiomeKeys.SWAMP, SWAMP);
      _snowman.put(BiomeKeys.SWAMP_HILLS, SWAMP);
      _snowman.put(BiomeKeys.GIANT_SPRUCE_TAIGA, TAIGA);
      _snowman.put(BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS, TAIGA);
      _snowman.put(BiomeKeys.GIANT_TREE_TAIGA, TAIGA);
      _snowman.put(BiomeKeys.GIANT_TREE_TAIGA_HILLS, TAIGA);
      _snowman.put(BiomeKeys.GRAVELLY_MOUNTAINS, TAIGA);
      _snowman.put(BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS, TAIGA);
      _snowman.put(BiomeKeys.MOUNTAIN_EDGE, TAIGA);
      _snowman.put(BiomeKeys.MOUNTAINS, TAIGA);
      _snowman.put(BiomeKeys.TAIGA, TAIGA);
      _snowman.put(BiomeKeys.TAIGA_HILLS, TAIGA);
      _snowman.put(BiomeKeys.TAIGA_MOUNTAINS, TAIGA);
      _snowman.put(BiomeKeys.WOODED_MOUNTAINS, TAIGA);
   });

   private VillagerType(String _snowman) {
      this.field_26690 = _snowman;
   }

   @Override
   public String toString() {
      return this.field_26690;
   }

   private static VillagerType create(String id) {
      return Registry.register(Registry.VILLAGER_TYPE, new Identifier(id), new VillagerType(id));
   }

   public static VillagerType forBiome(Optional<RegistryKey<Biome>> _snowman) {
      return _snowman.<VillagerType>flatMap(_snowmanx -> Optional.ofNullable(BIOME_TO_TYPE.get(_snowmanx))).orElse(PLAINS);
   }
}
