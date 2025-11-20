package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;

public class EndSpikeFeatureConfig implements FeatureConfig {
   public static final Codec<EndSpikeFeatureConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Codec.BOOL.fieldOf("crystal_invulnerable").orElse(false).forGetter(_snowmanx -> _snowmanx.crystalInvulnerable),
               EndSpikeFeature.Spike.CODEC.listOf().fieldOf("spikes").forGetter(_snowmanx -> _snowmanx.spikes),
               BlockPos.CODEC.optionalFieldOf("crystal_beam_target").forGetter(_snowmanx -> Optional.ofNullable(_snowmanx.crystalBeamTarget))
            )
            .apply(_snowman, EndSpikeFeatureConfig::new)
   );
   private final boolean crystalInvulnerable;
   private final List<EndSpikeFeature.Spike> spikes;
   @Nullable
   private final BlockPos crystalBeamTarget;

   public EndSpikeFeatureConfig(boolean crystalInvulnerable, List<EndSpikeFeature.Spike> spikes, @Nullable BlockPos crystalBeamTarget) {
      this(crystalInvulnerable, spikes, Optional.ofNullable(crystalBeamTarget));
   }

   private EndSpikeFeatureConfig(boolean crystalInvulnerable, List<EndSpikeFeature.Spike> spikes, Optional<BlockPos> crystalBeamTarget) {
      this.crystalInvulnerable = crystalInvulnerable;
      this.spikes = spikes;
      this.crystalBeamTarget = crystalBeamTarget.orElse(null);
   }

   public boolean isCrystalInvulnerable() {
      return this.crystalInvulnerable;
   }

   public List<EndSpikeFeature.Spike> getSpikes() {
      return this.spikes;
   }

   @Nullable
   public BlockPos getPos() {
      return this.crystalBeamTarget;
   }
}
