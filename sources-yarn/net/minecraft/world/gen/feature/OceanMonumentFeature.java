package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.OceanMonumentGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class OceanMonumentFeature extends StructureFeature<DefaultFeatureConfig> {
   private static final List<SpawnSettings.SpawnEntry> MONSTER_SPAWNS = ImmutableList.of(new SpawnSettings.SpawnEntry(EntityType.GUARDIAN, 1, 2, 4));

   public OceanMonumentFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   @Override
   protected boolean isUniformDistribution() {
      return false;
   }

   protected boolean shouldStartAt(
      ChunkGenerator arg, BiomeSource arg2, long l, ChunkRandom arg3, int i, int j, Biome arg4, ChunkPos arg5, DefaultFeatureConfig arg6
   ) {
      for (Biome lv : arg2.getBiomesInArea(i * 16 + 9, arg.getSeaLevel(), j * 16 + 9, 16)) {
         if (!lv.getGenerationSettings().hasStructureFeature(this)) {
            return false;
         }
      }

      for (Biome lv2 : arg2.getBiomesInArea(i * 16 + 9, arg.getSeaLevel(), j * 16 + 9, 29)) {
         if (lv2.getCategory() != Biome.Category.OCEAN && lv2.getCategory() != Biome.Category.RIVER) {
            return false;
         }
      }

      return true;
   }

   @Override
   public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
      return OceanMonumentFeature.Start::new;
   }

   @Override
   public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
      return MONSTER_SPAWNS;
   }

   public static class Start extends StructureStart<DefaultFeatureConfig> {
      private boolean field_13717;

      public Start(StructureFeature<DefaultFeatureConfig> arg, int i, int j, BlockBox arg2, int k, long l) {
         super(arg, i, j, arg2, k, l);
      }

      public void init(DynamicRegistryManager arg, ChunkGenerator arg2, StructureManager arg3, int i, int j, Biome arg4, DefaultFeatureConfig arg5) {
         this.method_16588(i, j);
      }

      private void method_16588(int chunkX, int chunkZ) {
         int k = chunkX * 16 - 29;
         int l = chunkZ * 16 - 29;
         Direction lv = Direction.Type.HORIZONTAL.random(this.random);
         this.children.add(new OceanMonumentGenerator.Base(this.random, k, l, lv));
         this.setBoundingBoxFromChildren();
         this.field_13717 = true;
      }

      @Override
      public void generateStructure(
         StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox box, ChunkPos chunkPos
      ) {
         if (!this.field_13717) {
            this.children.clear();
            this.method_16588(this.getChunkX(), this.getChunkZ());
         }

         super.generateStructure(world, structureAccessor, chunkGenerator, random, box, chunkPos);
      }
   }
}
