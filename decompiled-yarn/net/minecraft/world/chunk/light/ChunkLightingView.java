package net.minecraft.world.chunk.light;

import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.ChunkNibbleArray;

public interface ChunkLightingView extends LightingView {
   @Nullable
   ChunkNibbleArray getLightSection(ChunkSectionPos pos);

   int getLightLevel(BlockPos var1);

   public static enum Empty implements ChunkLightingView {
      INSTANCE;

      private Empty() {
      }

      @Nullable
      @Override
      public ChunkNibbleArray getLightSection(ChunkSectionPos pos) {
         return null;
      }

      @Override
      public int getLightLevel(BlockPos _snowman) {
         return 0;
      }

      @Override
      public void setSectionStatus(ChunkSectionPos pos, boolean notReady) {
      }
   }
}
