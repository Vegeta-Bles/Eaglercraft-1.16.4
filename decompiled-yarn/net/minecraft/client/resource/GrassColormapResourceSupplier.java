package net.minecraft.client.resource;

import java.io.IOException;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.util.RawTextureDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

public class GrassColormapResourceSupplier extends SinglePreparationResourceReloadListener<int[]> {
   private static final Identifier GRASS_COLORMAP_LOC = new Identifier("textures/colormap/grass.png");

   public GrassColormapResourceSupplier() {
   }

   protected int[] method_18662(ResourceManager _snowman, Profiler _snowman) {
      try {
         return RawTextureDataLoader.loadRawTextureData(_snowman, GRASS_COLORMAP_LOC);
      } catch (IOException var4) {
         throw new IllegalStateException("Failed to load grass color texture", var4);
      }
   }

   protected void apply(int[] _snowman, ResourceManager _snowman, Profiler _snowman) {
      GrassColors.setColorMap(_snowman);
   }
}
