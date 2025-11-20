package net.minecraft.world.gen.chunk;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;

public class FlatChunkGenerator extends ChunkGenerator {
   public static final Codec<FlatChunkGenerator> CODEC = FlatChunkGeneratorConfig.CODEC
      .fieldOf("settings")
      .xmap(FlatChunkGenerator::new, FlatChunkGenerator::getConfig)
      .codec();
   private final FlatChunkGeneratorConfig config;

   public FlatChunkGenerator(FlatChunkGeneratorConfig config) {
      super(new FixedBiomeSource(config.createBiome()), new FixedBiomeSource(config.getBiome()), config.getStructuresConfig(), 0L);
      this.config = config;
   }

   @Override
   protected Codec<? extends ChunkGenerator> getCodec() {
      return CODEC;
   }

   @Override
   public ChunkGenerator withSeed(long seed) {
      return this;
   }

   public FlatChunkGeneratorConfig getConfig() {
      return this.config;
   }

   @Override
   public void buildSurface(ChunkRegion region, Chunk chunk) {
   }

   @Override
   public int getSpawnHeight() {
      BlockState[] _snowman = this.config.getLayerBlocks();

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         BlockState _snowmanxx = _snowman[_snowmanx] == null ? Blocks.AIR.getDefaultState() : _snowman[_snowmanx];
         if (!Heightmap.Type.MOTION_BLOCKING.getBlockPredicate().test(_snowmanxx)) {
            return _snowmanx - 1;
         }
      }

      return _snowman.length;
   }

   @Override
   public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
      BlockState[] _snowman = this.config.getLayerBlocks();
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();
      Heightmap _snowmanxx = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
      Heightmap _snowmanxxx = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.length; _snowmanxxxx++) {
         BlockState _snowmanxxxxx = _snowman[_snowmanxxxx];
         if (_snowmanxxxxx != null) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
                  chunk.setBlockState(_snowmanx.set(_snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxxxx), _snowmanxxxxx, false);
                  _snowmanxx.trackUpdate(_snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxx);
                  _snowmanxxx.trackUpdate(_snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxx);
               }
            }
         }
      }
   }

   @Override
   public int getHeight(int x, int z, Heightmap.Type heightmapType) {
      BlockState[] _snowman = this.config.getLayerBlocks();

      for (int _snowmanx = _snowman.length - 1; _snowmanx >= 0; _snowmanx--) {
         BlockState _snowmanxx = _snowman[_snowmanx];
         if (_snowmanxx != null && heightmapType.getBlockPredicate().test(_snowmanxx)) {
            return _snowmanx + 1;
         }
      }

      return 0;
   }

   @Override
   public BlockView getColumnSample(int x, int z) {
      return new VerticalBlockSample(
         Arrays.stream(this.config.getLayerBlocks()).map(state -> state == null ? Blocks.AIR.getDefaultState() : state).toArray(BlockState[]::new)
      );
   }
}
