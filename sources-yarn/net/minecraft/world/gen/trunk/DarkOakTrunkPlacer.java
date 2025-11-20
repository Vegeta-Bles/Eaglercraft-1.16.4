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
   public static final Codec<DarkOakTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> method_28904(instance).apply(instance, DarkOakTrunkPlacer::new));

   public DarkOakTrunkPlacer(int i, int j, int k) {
      super(i, j, k);
   }

   @Override
   protected TrunkPlacerType<?> getType() {
      return TrunkPlacerType.DARK_OAK_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.TreeNode> generate(
      ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config
   ) {
      List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
      BlockPos lv = pos.down();
      setToDirt(world, lv);
      setToDirt(world, lv.east());
      setToDirt(world, lv.south());
      setToDirt(world, lv.south().east());
      Direction lv2 = Direction.Type.HORIZONTAL.random(random);
      int j = trunkHeight - random.nextInt(4);
      int k = 2 - random.nextInt(3);
      int l = pos.getX();
      int m = pos.getY();
      int n = pos.getZ();
      int o = l;
      int p = n;
      int q = m + trunkHeight - 1;

      for (int r = 0; r < trunkHeight; r++) {
         if (r >= j && k > 0) {
            o += lv2.getOffsetX();
            p += lv2.getOffsetZ();
            k--;
         }

         int s = m + r;
         BlockPos lv3 = new BlockPos(o, s, p);
         if (TreeFeature.isAirOrLeaves(world, lv3)) {
            getAndSetState(world, random, lv3, placedStates, box, config);
            getAndSetState(world, random, lv3.east(), placedStates, box, config);
            getAndSetState(world, random, lv3.south(), placedStates, box, config);
            getAndSetState(world, random, lv3.east().south(), placedStates, box, config);
         }
      }

      list.add(new FoliagePlacer.TreeNode(new BlockPos(o, q, p), 0, true));

      for (int t = -1; t <= 2; t++) {
         for (int u = -1; u <= 2; u++) {
            if ((t < 0 || t > 1 || u < 0 || u > 1) && random.nextInt(3) <= 0) {
               int v = random.nextInt(3) + 2;

               for (int w = 0; w < v; w++) {
                  getAndSetState(world, random, new BlockPos(l + t, q - w - 1, n + u), placedStates, box, config);
               }

               list.add(new FoliagePlacer.TreeNode(new BlockPos(o + t, q, p + u), 0, false));
            }
         }
      }

      return list;
   }
}
