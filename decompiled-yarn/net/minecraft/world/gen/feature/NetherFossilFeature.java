package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.NetherFossilGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.BlockView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class NetherFossilFeature extends StructureFeature<DefaultFeatureConfig> {
   public NetherFossilFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
      return NetherFossilFeature.Start::new;
   }

   public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {
      public Start(StructureFeature<DefaultFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, DefaultFeatureConfig _snowman) {
         ChunkPos _snowmanxxxxxxx = new ChunkPos(_snowman, _snowman);
         int _snowmanxxxxxxxx = _snowmanxxxxxxx.getStartX() + this.random.nextInt(16);
         int _snowmanxxxxxxxxx = _snowmanxxxxxxx.getStartZ() + this.random.nextInt(16);
         int _snowmanxxxxxxxxxx = _snowman.getSeaLevel();
         int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx + this.random.nextInt(_snowman.getWorldHeight() - 2 - _snowmanxxxxxxxxxx);
         BlockView _snowmanxxxxxxxxxxxx = _snowman.getColumnSample(_snowmanxxxxxxxx, _snowmanxxxxxxxxx);

         for (BlockPos.Mutable _snowmanxxxxxxxxxxxxx = new BlockPos.Mutable(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx); _snowmanxxxxxxxxxxx > _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxx--) {
            BlockState _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getBlockState(_snowmanxxxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxx.move(Direction.DOWN);
            BlockState _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getBlockState(_snowmanxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxx.isAir()
               && (_snowmanxxxxxxxxxxxxxxx.isOf(Blocks.SOUL_SAND) || _snowmanxxxxxxxxxxxxxxx.isSideSolidFullSquare(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, Direction.UP))) {
               break;
            }
         }

         if (_snowmanxxxxxxxxxxx > _snowmanxxxxxxxxxx) {
            NetherFossilGenerator.addPieces(_snowman, this.children, this.random, new BlockPos(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx));
            this.setBoundingBoxFromChildren();
         }
      }
   }
}
