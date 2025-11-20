package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.world.Heightmap;

public class TopSolidHeightmapDecorator extends HeightmapDecorator<NopeDecoratorConfig> {
   public TopSolidHeightmapDecorator(Codec<NopeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   protected Heightmap.Type getHeightmapType(NopeDecoratorConfig _snowman) {
      return Heightmap.Type.OCEAN_FLOOR_WG;
   }
}
