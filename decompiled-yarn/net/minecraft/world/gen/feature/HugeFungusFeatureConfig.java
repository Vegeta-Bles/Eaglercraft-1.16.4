package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class HugeFungusFeatureConfig implements FeatureConfig {
   public static final Codec<HugeFungusFeatureConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               BlockState.CODEC.fieldOf("valid_base_block").forGetter(_snowmanx -> _snowmanx.validBaseBlock),
               BlockState.CODEC.fieldOf("stem_state").forGetter(_snowmanx -> _snowmanx.stemState),
               BlockState.CODEC.fieldOf("hat_state").forGetter(_snowmanx -> _snowmanx.hatState),
               BlockState.CODEC.fieldOf("decor_state").forGetter(_snowmanx -> _snowmanx.decorationState),
               Codec.BOOL.fieldOf("planted").orElse(false).forGetter(_snowmanx -> _snowmanx.planted)
            )
            .apply(_snowman, HugeFungusFeatureConfig::new)
   );
   public static final HugeFungusFeatureConfig CRIMSON_FUNGUS_CONFIG = new HugeFungusFeatureConfig(
      Blocks.CRIMSON_NYLIUM.getDefaultState(),
      Blocks.CRIMSON_STEM.getDefaultState(),
      Blocks.NETHER_WART_BLOCK.getDefaultState(),
      Blocks.SHROOMLIGHT.getDefaultState(),
      true
   );
   public static final HugeFungusFeatureConfig CRIMSON_FUNGUS_NOT_PLANTED_CONFIG;
   public static final HugeFungusFeatureConfig WARPED_FUNGUS_CONFIG = new HugeFungusFeatureConfig(
      Blocks.WARPED_NYLIUM.getDefaultState(),
      Blocks.WARPED_STEM.getDefaultState(),
      Blocks.WARPED_WART_BLOCK.getDefaultState(),
      Blocks.SHROOMLIGHT.getDefaultState(),
      true
   );
   public static final HugeFungusFeatureConfig WARPED_FUNGUS_NOT_PLANTED_CONFIG;
   public final BlockState validBaseBlock;
   public final BlockState stemState;
   public final BlockState hatState;
   public final BlockState decorationState;
   public final boolean planted;

   public HugeFungusFeatureConfig(BlockState validBaseBlock, BlockState stemState, BlockState hatState, BlockState decorationState, boolean planted) {
      this.validBaseBlock = validBaseBlock;
      this.stemState = stemState;
      this.hatState = hatState;
      this.decorationState = decorationState;
      this.planted = planted;
   }

   static {
      CRIMSON_FUNGUS_NOT_PLANTED_CONFIG = new HugeFungusFeatureConfig(
         CRIMSON_FUNGUS_CONFIG.validBaseBlock, CRIMSON_FUNGUS_CONFIG.stemState, CRIMSON_FUNGUS_CONFIG.hatState, CRIMSON_FUNGUS_CONFIG.decorationState, false
      );
      WARPED_FUNGUS_NOT_PLANTED_CONFIG = new HugeFungusFeatureConfig(
         WARPED_FUNGUS_CONFIG.validBaseBlock, WARPED_FUNGUS_CONFIG.stemState, WARPED_FUNGUS_CONFIG.hatState, WARPED_FUNGUS_CONFIG.decorationState, false
      );
   }
}
