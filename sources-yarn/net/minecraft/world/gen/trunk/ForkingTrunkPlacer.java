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
   public static final Codec<ForkingTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> method_28904(instance).apply(instance, ForkingTrunkPlacer::new));

   public ForkingTrunkPlacer(int i, int j, int k) {
      super(i, j, k);
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
      List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
      Direction lv = Direction.Type.HORIZONTAL.random(random);
      int j = trunkHeight - random.nextInt(4) - 1;
      int k = 3 - random.nextInt(3);
      BlockPos.Mutable lv2 = new BlockPos.Mutable();
      int l = pos.getX();
      int m = pos.getZ();
      int n = 0;

      for (int o = 0; o < trunkHeight; o++) {
         int p = pos.getY() + o;
         if (o >= j && k > 0) {
            l += lv.getOffsetX();
            m += lv.getOffsetZ();
            k--;
         }

         if (getAndSetState(world, random, lv2.set(l, p, m), placedStates, box, config)) {
            n = p + 1;
         }
      }

      list.add(new FoliagePlacer.TreeNode(new BlockPos(l, n, m), 1, false));
      l = pos.getX();
      m = pos.getZ();
      Direction lv3 = Direction.Type.HORIZONTAL.random(random);
      if (lv3 != lv) {
         int q = j - random.nextInt(2) - 1;
         int r = 1 + random.nextInt(3);
         n = 0;

         for (int s = q; s < trunkHeight && r > 0; r--) {
            if (s >= 1) {
               int t = pos.getY() + s;
               l += lv3.getOffsetX();
               m += lv3.getOffsetZ();
               if (getAndSetState(world, random, lv2.set(l, t, m), placedStates, box, config)) {
                  n = t + 1;
               }
            }

            s++;
         }

         if (n > 1) {
            list.add(new FoliagePlacer.TreeNode(new BlockPos(l, n, m), 0, false));
         }
      }

      return list;
   }
}
