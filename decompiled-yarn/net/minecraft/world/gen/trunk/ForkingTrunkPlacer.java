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
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public class ForkingTrunkPlacer extends TrunkPlacer {
   public static final Codec<ForkingTrunkPlacer> CODEC = RecordCodecBuilder.create(_snowman -> method_28904(_snowman).apply(_snowman, ForkingTrunkPlacer::new));

   public ForkingTrunkPlacer(int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected TrunkPlacerType<?> getType() {
      return TrunkPlacerType.FORKING_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.TreeNode> generate(
      ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config
   ) {
      setToDirt(world, pos.down());
      List<FoliagePlacer.TreeNode> _snowman = Lists.newArrayList();
      Direction _snowmanx = Direction.Type.HORIZONTAL.random(random);
      int _snowmanxx = trunkHeight - random.nextInt(4) - 1;
      int _snowmanxxx = 3 - random.nextInt(3);
      BlockPos.Mutable _snowmanxxxx = new BlockPos.Mutable();
      int _snowmanxxxxx = pos.getX();
      int _snowmanxxxxxx = pos.getZ();
      int _snowmanxxxxxxx = 0;

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < trunkHeight; _snowmanxxxxxxxx++) {
         int _snowmanxxxxxxxxx = pos.getY() + _snowmanxxxxxxxx;
         if (_snowmanxxxxxxxx >= _snowmanxx && _snowmanxxx > 0) {
            _snowmanxxxxx += _snowmanx.getOffsetX();
            _snowmanxxxxxx += _snowmanx.getOffsetZ();
            _snowmanxxx--;
         }

         if (getAndSetState(world, random, _snowmanxxxx.set(_snowmanxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx), placedStates, box, config)) {
            _snowmanxxxxxxx = _snowmanxxxxxxxxx + 1;
         }
      }

      _snowman.add(new FoliagePlacer.TreeNode(new BlockPos(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx), 1, false));
      _snowmanxxxxx = pos.getX();
      _snowmanxxxxxx = pos.getZ();
      Direction _snowmanxxxxxxxx = Direction.Type.HORIZONTAL.random(random);
      if (_snowmanxxxxxxxx != _snowmanx) {
         int _snowmanxxxxxxxxxx = _snowmanxx - random.nextInt(2) - 1;
         int _snowmanxxxxxxxxxxx = 1 + random.nextInt(3);
         _snowmanxxxxxxx = 0;

         for (int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxx < trunkHeight && _snowmanxxxxxxxxxxx > 0; _snowmanxxxxxxxxxxx--) {
            if (_snowmanxxxxxxxxxxxx >= 1) {
               int _snowmanxxxxxxxxxxxxx = pos.getY() + _snowmanxxxxxxxxxxxx;
               _snowmanxxxxx += _snowmanxxxxxxxx.getOffsetX();
               _snowmanxxxxxx += _snowmanxxxxxxxx.getOffsetZ();
               if (getAndSetState(world, random, _snowmanxxxx.set(_snowmanxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxx), placedStates, box, config)) {
                  _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxx + 1;
               }
            }

            _snowmanxxxxxxxxxxxx++;
         }

         if (_snowmanxxxxxxx > 1) {
            _snowman.add(new FoliagePlacer.TreeNode(new BlockPos(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx), 0, false));
         }
      }

      return _snowman;
   }
}
