package net.minecraft.client.resource;

import java.io.IOException;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.util.RawTextureDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

public class FoliageColormapResourceSupplier extends SinglePreparationResourceReloadListener<int[]> {
   private static final Identifier FOLIAGE_COLORMAP = new Identifier("textures/colormap/foliage.png");

   public FoliageColormapResourceSupplier() {
   }

   protected int[] reload(ResourceManager resourceManager, Profiler _snowman) {
      try {
         return RawTextureDataLoader.loadRawTextureData(resourceManager, FOLIAGE_COLORMAP);
      } catch (IOException var4) {
         throw new IllegalStateException("Failed to load foliage color texture", var4);
      }
   }

   protected void apply(int[] _snowman, ResourceManager _snowman, Profiler _snowman) {
      FoliageColors.setColorMap(_snowman);
   }
}
