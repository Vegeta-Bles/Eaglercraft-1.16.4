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

public class GiantTrunkPlacer extends TrunkPlacer {
   public static final Codec<GiantTrunkPlacer> CODEC = RecordCodecBuilder.create(_snowman -> method_28904(_snowman).apply(_snowman, GiantTrunkPlacer::new));

   public GiantTrunkPlacer(int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected TrunkPlacerType<?> getType() {
      return TrunkPlacerType.GIANT_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.TreeNode> generate(
      ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config
   ) {
      BlockPos _snowman = pos.down();
      setToDirt(world, _snowman);
      setToDirt(world, _snowman.east());
      setToDirt(world, _snowman.south());
      setToDirt(world, _snowman.south().east());
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();

      for (int _snowmanxx = 0; _snowmanxx < trunkHeight; _snowmanxx++) {
         method_27399(world, random, _snowmanx, placedStates, box, config, pos, 0, _snowmanxx, 0);
         if (_snowmanxx < trunkHeight - 1) {
            method_27399(world, random, _snowmanx, placedStates, box, config, pos, 1, _snowmanxx, 0);
            method_27399(world, random, _snowmanx, placedStates, box, config, pos, 1, _snowmanxx, 1);
            method_27399(world, random, _snowmanx, placedStates, box, config, pos, 0, _snowmanxx, 1);
         }
      }

      return ImmutableList.of(new FoliagePlacer.TreeNode(pos.up(trunkHeight), 0, true));
   }

   private static void method_27399(
      ModifiableTestableWorld _snowman, Random _snowman, BlockPos.Mutable _snowman, Set<BlockPos> _snowman, BlockBox _snowman, TreeFeatureConfig _snowman, BlockPos _snowman, int _snowman, int _snowman, int _snowman
   ) {
      _snowman.set(_snowman, _snowman, _snowman, _snowman);
      trySetState(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
