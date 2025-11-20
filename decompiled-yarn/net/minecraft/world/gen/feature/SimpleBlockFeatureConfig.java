package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.block.BlockState;

public class SimpleBlockFeatureConfig implements FeatureConfig {
   public static final Codec<SimpleBlockFeatureConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               BlockState.CODEC.fieldOf("to_place").forGetter(_snowmanx -> _snowmanx.toPlace),
               BlockState.CODEC.listOf().fieldOf("place_on").forGetter(_snowmanx -> _snowmanx.placeOn),
               BlockState.CODEC.listOf().fieldOf("place_in").forGetter(_snowmanx -> _snowmanx.placeIn),
               BlockState.CODEC.listOf().fieldOf("place_under").forGetter(_snowmanx -> _snowmanx.placeUnder)
            )
            .apply(_snowman, SimpleBlockFeatureConfig::new)
   );
   public final BlockState toPlace;
   public final List<BlockState> placeOn;
   public final List<BlockState> placeIn;
   public final List<BlockState> placeUnder;

   public SimpleBlockFeatureConfig(BlockState toPlace, List<BlockState> placeOn, List<BlockState> placeIn, List<BlockState> placeUnder) {
      this.toPlace = toPlace;
      this.placeOn = placeOn;
      this.placeIn = placeIn;
      this.placeUnder = placeUnder;
   }
}
