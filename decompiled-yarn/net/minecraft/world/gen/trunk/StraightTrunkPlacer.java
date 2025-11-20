package net.minecraft.world.gen.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public class StraightTrunkPlacer extends TrunkPlacer {
   public static final Codec<StraightTrunkPlacer> CODEC = RecordCodecBuilder.create(_snowman -> method_28904(_snowman).apply(_snowman, StraightTrunkPlacer::new));

   public StraightTrunkPlacer(int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected TrunkPlacerType<?> getType() {
      return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.TreeNode> generate(
      ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config
   ) {
      setToDirt(world, pos.down());

      for (int _snowman = 0; _snowman < trunkHeight; _snowman++) {
         getAndSetState(world, random, pos.up(_snowman), placedStates, box, config);
      }

      return ImmutableList.of(new FoliagePlacer.TreeNode(pos.up(trunkHeight), 0, false));
   }
}
