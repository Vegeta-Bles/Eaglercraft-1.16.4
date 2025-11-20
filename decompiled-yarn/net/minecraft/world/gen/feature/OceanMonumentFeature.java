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

   public OceanMonumentFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   protected boolean isUniformDistribution() {
      return false;
   }

   protected boolean shouldStartAt(ChunkGenerator _snowman, BiomeSource _snowman, long _snowman, ChunkRandom _snowman, int _snowman, int _snowman, Biome _snowman, ChunkPos _snowman, DefaultFeatureConfig _snowman) {
      for (Biome _snowmanxxxxxxxxx : _snowman.getBiomesInArea(_snowman * 16 + 9, _snowman.getSeaLevel(), _snowman * 16 + 9, 16)) {
         if (!_snowmanxxxxxxxxx.getGenerationSettings().hasStructureFeature(this)) {
            return false;
         }
      }

      for (Biome _snowmanxxxxxxxxxx : _snowman.getBiomesInArea(_snowman * 16 + 9, _snowman.getSeaLevel(), _snowman * 16 + 9, 29)) {
         if (_snowmanxxxxxxxxxx.getCategory() != Biome.Category.OCEAN && _snowmanxxxxxxxxxx.getCategory() != Biome.Category.RIVER) {
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

      public Start(StructureFeature<DefaultFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, DefaultFeatureConfig _snowman) {
         this.method_16588(_snowman, _snowman);
      }

      private void method_16588(int chunkX, int chunkZ) {
         int _snowman = chunkX * 16 - 29;
         int _snowmanx = chunkZ * 16 - 29;
         Direction _snowmanxx = Direction.Type.HORIZONTAL.random(this.random);
         this.children.add(new OceanMonumentGenerator.Base(this.random, _snowman, _snowmanx, _snowmanxx));
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
