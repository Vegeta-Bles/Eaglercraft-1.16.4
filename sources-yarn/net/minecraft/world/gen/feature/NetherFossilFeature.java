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
   public NetherFossilFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   @Override
   public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
      return NetherFossilFeature.Start::new;
   }

   public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {
      public Start(StructureFeature<DefaultFeatureConfig> arg, int i, int j, BlockBox arg2, int k, long l) {
         super(arg, i, j, arg2, k, l);
      }

      public void init(DynamicRegistryManager arg, ChunkGenerator arg2, StructureManager arg3, int i, int j, Biome arg4, DefaultFeatureConfig arg5) {
         ChunkPos lv = new ChunkPos(i, j);
         int k = lv.getStartX() + this.random.nextInt(16);
         int l = lv.getStartZ() + this.random.nextInt(16);
         int m = arg2.getSeaLevel();
         int n = m + this.random.nextInt(arg2.getWorldHeight() - 2 - m);
         BlockView lv2 = arg2.getColumnSample(k, l);

         for (BlockPos.Mutable lv3 = new BlockPos.Mutable(k, n, l); n > m; n--) {
            BlockState lv4 = lv2.getBlockState(lv3);
            lv3.move(Direction.DOWN);
            BlockState lv5 = lv2.getBlockState(lv3);
            if (lv4.isAir() && (lv5.isOf(Blocks.SOUL_SAND) || lv5.isSideSolidFullSquare(lv2, lv3, Direction.UP))) {
               break;
            }
         }

         if (n > m) {
            NetherFossilGenerator.addPieces(arg3, this.children, this.random, new BlockPos(k, n, l));
            this.setBoundingBoxFromChildren();
         }
      }
   }
}
