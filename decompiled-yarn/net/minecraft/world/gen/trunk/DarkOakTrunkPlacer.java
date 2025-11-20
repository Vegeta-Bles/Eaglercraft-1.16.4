package net.minecraft.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public class DarkOakTrunkPlacer extends TrunkPlacer {
   public static final Codec<DarkOakTrunkPlacer> CODEC = RecordCodecBuilder.create(_snowman -> method_28904(_snowman).apply(_snowman, DarkOakTrunkPlacer::new));

   public DarkOakTrunkPlacer(int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected TrunkPlacerType<?> getType() {
      return TrunkPlacerType.DARK_OAK_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.TreeNode> generate(
      ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config
   ) {
      List<FoliagePlacer.TreeNode> _snowman = Lists.newArrayList();
      BlockPos _snowmanx = pos.down();
      setToDirt(world, _snowmanx);
      setToDirt(world, _snowmanx.east());
      setToDirt(world, _snowmanx.south());
      setToDirt(world, _snowmanx.south().east());
      Direction _snowmanxx = Direction.Type.HORIZONTAL.random(random);
      int _snowmanxxx = trunkHeight - random.nextInt(4);
      int _snowmanxxxx = 2 - random.nextInt(3);
      int _snowmanxxxxx = pos.getX();
      int _snowmanxxxxxx = pos.getY();
      int _snowmanxxxxxxx = pos.getZ();
      int _snowmanxxxxxxxx = _snowmanxxxxx;
      int _snowmanxxxxxxxxx = _snowmanxxxxxxx;
      int _snowmanxxxxxxxxxx = _snowmanxxxxxx + trunkHeight - 1;

      for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < trunkHeight; _snowmanxxxxxxxxxxx++) {
         if (_snowmanxxxxxxxxxxx >= _snowmanxxx && _snowmanxxxx > 0) {
            _snowmanxxxxxxxx += _snowmanxx.getOffsetX();
            _snowmanxxxxxxxxx += _snowmanxx.getOffsetZ();
            _snowmanxxxx--;
         }

         int _snowmanxxxxxxxxxxxx = _snowmanxxxxxx + _snowmanxxxxxxxxxxx;
         BlockPos _snowmanxxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxx);
         if (TreeFeature.isAirOrLeaves(world, _snowmanxxxxxxxxxxxxx)) {
            getAndSetState(world, random, _snowmanxxxxxxxxxxxxx, placedStates, box, config);
            getAndSetState(world, random, _snowmanxxxxxxxxxxxxx.east(), placedStates, box, config);
            getAndSetState(world, random, _snowmanxxxxxxxxxxxxx.south(), placedStates, box, config);
            getAndSetState(world, random, _snowmanxxxxxxxxxxxxx.east().south(), placedStates, box, config);
         }
      }

      _snowman.add(new FoliagePlacer.TreeNode(new BlockPos(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx), 0, true));

      for (int _snowmanxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxx <= 2; _snowmanxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxxx <= 2; _snowmanxxxxxxxxxxxx++) {
            if ((_snowmanxxxxxxxxxxx < 0 || _snowmanxxxxxxxxxxx > 1 || _snowmanxxxxxxxxxxxx < 0 || _snowmanxxxxxxxxxxxx > 1) && random.nextInt(3) <= 0) {
               int _snowmanxxxxxxxxxxxxx = random.nextInt(3) + 2;

               for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  getAndSetState(
                     world, random, new BlockPos(_snowmanxxxxx + _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx - _snowmanxxxxxxxxxxxxxx - 1, _snowmanxxxxxxx + _snowmanxxxxxxxxxxxx), placedStates, box, config
                  );
               }

               _snowman.add(new FoliagePlacer.TreeNode(new BlockPos(_snowmanxxxxxxxx + _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxx), 0, false));
            }
         }
      }

      return _snowman;
   }
}
