package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.world.Heightmap;

public class TopSolidHeightmapDecorator extends HeightmapDecorator<NopeDecoratorConfig> {
   public TopSolidHeightmapDecorator(Codec<NopeDecoratorConfig> codec) {
      super(codec);
   }

   protected Heightmap.Type getHeightmapType(NopeDecoratorConfig arg) {
      return Heightmap.Type.OCEAN_FLOOR_WG;
   }
}
