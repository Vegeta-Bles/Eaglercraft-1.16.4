package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class EndIslandFeature extends Feature<DefaultFeatureConfig> {
   public EndIslandFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      float _snowmanxxxxx = (float)(_snowman.nextInt(3) + 4);

      for (int _snowmanxxxxxx = 0; _snowmanxxxxx > 0.5F; _snowmanxxxxxx--) {
         for (int _snowmanxxxxxxx = MathHelper.floor(-_snowmanxxxxx); _snowmanxxxxxxx <= MathHelper.ceil(_snowmanxxxxx); _snowmanxxxxxxx++) {
            for (int _snowmanxxxxxxxx = MathHelper.floor(-_snowmanxxxxx); _snowmanxxxxxxxx <= MathHelper.ceil(_snowmanxxxxx); _snowmanxxxxxxxx++) {
               if ((float)(_snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx) <= (_snowmanxxxxx + 1.0F) * (_snowmanxxxxx + 1.0F)) {
                  this.setBlockState(_snowman, _snowman.add(_snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx), Blocks.END_STONE.getDefaultState());
               }
            }
         }

         _snowmanxxxxx = (float)((double)_snowmanxxxxx - ((double)_snowman.nextInt(2) + 0.5));
      }

      return true;
   }
}
