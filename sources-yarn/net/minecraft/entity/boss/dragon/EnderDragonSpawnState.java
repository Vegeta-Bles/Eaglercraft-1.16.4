package net.minecraft.entity.boss.dragon;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public enum EnderDragonSpawnState {
   START {
      @Override
      public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos arg3) {
         BlockPos lv = new BlockPos(0, 128, 0);

         for (EndCrystalEntity lv2 : crystals) {
            lv2.setBeamTarget(lv);
         }

         fight.setSpawnState(PREPARING_TO_SUMMON_PILLARS);
      }
   },
   PREPARING_TO_SUMMON_PILLARS {
      @Override
      public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos arg3) {
         if (i < 100) {
            if (i == 0 || i == 50 || i == 51 || i == 52 || i >= 95) {
               world.syncWorldEvent(3001, new BlockPos(0, 128, 0), 0);
            }
         } else {
            fight.setSpawnState(SUMMONING_PILLARS);
         }
      }
   },
   SUMMONING_PILLARS {
      @Override
      public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos arg3) {
         int j = 40;
         boolean bl = i % 40 == 0;
         boolean bl2 = i % 40 == 39;
         if (bl || bl2) {
            List<EndSpikeFeature.Spike> list2 = EndSpikeFeature.getSpikes(world);
            int k = i / 40;
            if (k < list2.size()) {
               EndSpikeFeature.Spike lv = list2.get(k);
               if (bl) {
                  for (EndCrystalEntity lv2 : crystals) {
                     lv2.setBeamTarget(new BlockPos(lv.getCenterX(), lv.getHeight() + 1, lv.getCenterZ()));
                  }
               } else {
                  int l = 10;

                  for (BlockPos lv3 : BlockPos.iterate(
                     new BlockPos(lv.getCenterX() - 10, lv.getHeight() - 10, lv.getCenterZ() - 10),
                     new BlockPos(lv.getCenterX() + 10, lv.getHeight() + 10, lv.getCenterZ() + 10)
                  )) {
                     world.removeBlock(lv3, false);
                  }

                  world.createExplosion(
                     null,
                     (double)((float)lv.getCenterX() + 0.5F),
                     (double)lv.getHeight(),
                     (double)((float)lv.getCenterZ() + 0.5F),
                     5.0F,
                     Explosion.DestructionType.DESTROY
                  );
                  EndSpikeFeatureConfig lv4 = new EndSpikeFeatureConfig(true, ImmutableList.of(lv), new BlockPos(0, 128, 0));
                  Feature.END_SPIKE
                     .configure(lv4)
                     .generate(world, world.getChunkManager().getChunkGenerator(), new Random(), new BlockPos(lv.getCenterX(), 45, lv.getCenterZ()));
               }
            } else if (bl) {
               fight.setSpawnState(SUMMONING_DRAGON);
            }
         }
      }
   },
   SUMMONING_DRAGON {
      @Override
      public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos arg3) {
         if (i >= 100) {
            fight.setSpawnState(END);
            fight.resetEndCrystals();

            for (EndCrystalEntity lv : crystals) {
               lv.setBeamTarget(null);
               world.createExplosion(lv, lv.getX(), lv.getY(), lv.getZ(), 6.0F, Explosion.DestructionType.NONE);
               lv.remove();
            }
         } else if (i >= 80) {
            world.syncWorldEvent(3001, new BlockPos(0, 128, 0), 0);
         } else if (i == 0) {
            for (EndCrystalEntity lv2 : crystals) {
               lv2.setBeamTarget(new BlockPos(0, 128, 0));
            }
         } else if (i < 5) {
            world.syncWorldEvent(3001, new BlockPos(0, 128, 0), 0);
         }
      }
   },
   END {
      @Override
      public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos arg3) {
      }
   };

   private EnderDragonSpawnState() {
   }

   public abstract void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos arg3);
}
