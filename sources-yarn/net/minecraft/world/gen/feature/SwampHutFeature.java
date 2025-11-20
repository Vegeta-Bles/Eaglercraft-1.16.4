package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.SwampHutGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SwampHutFeature extends StructureFeature<DefaultFeatureConfig> {
   private static final List<SpawnSettings.SpawnEntry> MONSTER_SPAWNS = ImmutableList.of(new SpawnSettings.SpawnEntry(EntityType.WITCH, 1, 1, 1));
   private static final List<SpawnSettings.SpawnEntry> CREATURE_SPAWNS = ImmutableList.of(new SpawnSettings.SpawnEntry(EntityType.CAT, 1, 1, 1));

   public SwampHutFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   @Override
   public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
      return SwampHutFeature.Start::new;
   }

   @Override
   public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
      return MONSTER_SPAWNS;
   }

   @Override
   public List<SpawnSettings.SpawnEntry> getCreatureSpawns() {
      return CREATURE_SPAWNS;
   }

   public static class Start extends StructureStart<DefaultFeatureConfig> {
      public Start(StructureFeature<DefaultFeatureConfig> arg, int i, int j, BlockBox arg2, int k, long l) {
         super(arg, i, j, arg2, k, l);
      }

      public void init(DynamicRegistryManager arg, ChunkGenerator arg2, StructureManager arg3, int i, int j, Biome arg4, DefaultFeatureConfig arg5) {
         SwampHutGenerator lv = new SwampHutGenerator(this.random, i * 16, j * 16);
         this.children.add(lv);
         this.setBoundingBoxFromChildren();
      }
   }
}
