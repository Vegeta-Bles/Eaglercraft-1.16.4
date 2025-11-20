package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.world.Heightmap;

public class HeightmapWorldSurfaceDecorator extends HeightmapDecorator<NopeDecoratorConfig> {
   public HeightmapWorldSurfaceDecorator(Codec<NopeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   protected Heightmap.Type getHeightmapType(NopeDecoratorConfig _snowman) {
      return Heightmap.Type.WORLD_SURFACE_WG;
   }
}
