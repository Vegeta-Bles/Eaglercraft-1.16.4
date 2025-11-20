package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class MountainSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   public MountainSurfaceBuilder(Codec<TernarySurfaceConfig> _snowman) {
      super(_snowman);
   }

   public void generate(Random _snowman, Chunk _snowman, Biome _snowman, int _snowman, int _snowman, int _snowman, double _snowman, BlockState _snowman, BlockState _snowman, int _snowman, long _snowman, TernarySurfaceConfig _snowman) {
      if (_snowman > 1.0) {
         SurfaceBuilder.DEFAULT.generate(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, SurfaceBuilder.STONE_CONFIG);
      } else {
         SurfaceBuilder.DEFAULT.generate(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, SurfaceBuilder.GRASS_CONFIG);
      }
   }
}
