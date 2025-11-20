package net.minecraft.world.gen.chunk;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;

public class DebugChunkGenerator extends ChunkGenerator {
   public static final Codec<DebugChunkGenerator> CODEC = RegistryLookupCodec.of(Registry.BIOME_KEY)
      .xmap(DebugChunkGenerator::new, DebugChunkGenerator::getBiomeRegistry)
      .stable()
      .codec();
   private static final List<BlockState> BLOCK_STATES = StreamSupport.stream(Registry.BLOCK.spliterator(), false)
      .flatMap(_snowman -> _snowman.getStateManager().getStates().stream())
      .collect(Collectors.toList());
   private static final int X_SIDE_LENGTH = MathHelper.ceil(MathHelper.sqrt((float)BLOCK_STATES.size()));
   private static final int Z_SIDE_LENGTH = MathHelper.ceil((float)BLOCK_STATES.size() / (float)X_SIDE_LENGTH);
   protected static final BlockState AIR = Blocks.AIR.getDefaultState();
   protected static final BlockState BARRIER = Blocks.BARRIER.getDefaultState();
   private final Registry<Biome> biomeRegistry;

   public DebugChunkGenerator(Registry<Biome> biomeRegistry) {
      super(new FixedBiomeSource(biomeRegistry.getOrThrow(BiomeKeys.PLAINS)), new StructuresConfig(false));
      this.biomeRegistry = biomeRegistry;
   }

   public Registry<Biome> getBiomeRegistry() {
      return this.biomeRegistry;
   }

   @Override
   protected Codec<? extends ChunkGenerator> getCodec() {
      return CODEC;
   }

   @Override
   public ChunkGenerator withSeed(long seed) {
      return this;
   }

   @Override
   public void buildSurface(ChunkRegion region, Chunk chunk) {
   }

   @Override
   public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
   }

   @Override
   public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();
      int _snowmanx = region.getCenterChunkX();
      int _snowmanxx = region.getCenterChunkZ();

      for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
         for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
            int _snowmanxxxxx = (_snowmanx << 4) + _snowmanxxx;
            int _snowmanxxxxxx = (_snowmanxx << 4) + _snowmanxxxx;
            region.setBlockState(_snowman.set(_snowmanxxxxx, 60, _snowmanxxxxxx), BARRIER, 2);
            BlockState _snowmanxxxxxxx = getBlockState(_snowmanxxxxx, _snowmanxxxxxx);
            if (_snowmanxxxxxxx != null) {
               region.setBlockState(_snowman.set(_snowmanxxxxx, 70, _snowmanxxxxxx), _snowmanxxxxxxx, 2);
            }
         }
      }
   }

   @Override
   public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
   }

   @Override
   public int getHeight(int x, int z, Heightmap.Type heightmapType) {
      return 0;
   }

   @Override
   public BlockView getColumnSample(int x, int z) {
      return new VerticalBlockSample(new BlockState[0]);
   }

   public static BlockState getBlockState(int x, int z) {
      BlockState _snowman = AIR;
      if (x > 0 && z > 0 && x % 2 != 0 && z % 2 != 0) {
         x /= 2;
         z /= 2;
         if (x <= X_SIDE_LENGTH && z <= Z_SIDE_LENGTH) {
            int _snowmanx = MathHelper.abs(x * X_SIDE_LENGTH + z);
            if (_snowmanx < BLOCK_STATES.size()) {
               _snowman = BLOCK_STATES.get(_snowmanx);
            }
         }
      }

      return _snowman;
   }
}
