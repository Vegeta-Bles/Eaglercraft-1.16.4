package net.minecraft.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public class MegaJungleTrunkPlacer extends GiantTrunkPlacer {
   public static final Codec<MegaJungleTrunkPlacer> CODEC = RecordCodecBuilder.create(_snowman -> method_28904(_snowman).apply(_snowman, MegaJungleTrunkPlacer::new));

   public MegaJungleTrunkPlacer(int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected TrunkPlacerType<?> getType() {
      return TrunkPlacerType.MEGA_JUNGLE_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.TreeNode> generate(
      ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config
   ) {
      List<FoliagePlacer.TreeNode> _snowman = Lists.newArrayList();
      _snowman.addAll(super.generate(world, random, trunkHeight, pos, placedStates, box, config));

      for (int _snowmanx = trunkHeight - 2 - random.nextInt(4); _snowmanx > trunkHeight / 2; _snowmanx -= 2 + random.nextInt(4)) {
         float _snowmanxx = random.nextFloat() * (float) (Math.PI * 2);
         int _snowmanxxx = 0;
         int _snowmanxxxx = 0;

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < 5; _snowmanxxxxx++) {
            _snowmanxxx = (int)(1.5F + MathHelper.cos(_snowmanxx) * (float)_snowmanxxxxx);
            _snowmanxxxx = (int)(1.5F + MathHelper.sin(_snowmanxx) * (float)_snowmanxxxxx);
            BlockPos _snowmanxxxxxx = pos.add(_snowmanxxx, _snowmanx - 3 + _snowmanxxxxx / 2, _snowmanxxxx);
            getAndSetState(world, random, _snowmanxxxxxx, placedStates, box, config);
         }

         _snowman.add(new FoliagePlacer.TreeNode(pos.add(_snowmanxxx, _snowmanx, _snowmanxxxx), -2, false));
      }

      return _snowman;
   }
}
