package net.minecraft.client.resource;

import java.io.IOException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.util.RawTextureDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

@Environment(EnvType.CLIENT)
public class FoliageColormapResourceSupplier extends SinglePreparationResourceReloadListener<int[]> {
   private static final Identifier FOLIAGE_COLORMAP = new Identifier("textures/colormap/foliage.png");

   public FoliageColormapResourceSupplier() {
   }

   protected int[] reload(ResourceManager resourceManager, Profiler arg2) {
      try {
         return RawTextureDataLoader.loadRawTextureData(resourceManager, FOLIAGE_COLORMAP);
      } catch (IOException var4) {
         throw new IllegalStateException("Failed to load foliage color texture", var4);
      }
   }

   protected void apply(int[] is, ResourceManager arg, Profiler arg2) {
      FoliageColors.setColorMap(is);
   }
}
