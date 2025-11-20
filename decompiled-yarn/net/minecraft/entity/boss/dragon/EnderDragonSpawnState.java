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
      public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int _snowman, BlockPos _snowman) {
         BlockPos _snowmanxx = new BlockPos(0, 128, 0);

         for (EndCrystalEntity _snowmanxxx : crystals) {
            _snowmanxxx.setBeamTarget(_snowmanxx);
         }

         fight.setSpawnState(PREPARING_TO_SUMMON_PILLARS);
      }
   },
   PREPARING_TO_SUMMON_PILLARS {
      @Override
      public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int _snowman, BlockPos _snowman) {
         if (_snowman < 100) {
            if (_snowman == 0 || _snowman == 50 || _snowman == 51 || _snowman == 52 || _snowman >= 95) {
               world.syncWorldEvent(3001, new BlockPos(0, 128, 0), 0);
            }
         } else {
            fight.setSpawnState(SUMMONING_PILLARS);
         }
      }
   },
   SUMMONING_PILLARS {
      @Override
      public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int _snowman, BlockPos _snowman) {
         int _snowmanxx = 40;
         boolean _snowmanxxx = _snowman % 40 == 0;
         boolean _snowmanxxxx = _snowman % 40 == 39;
         if (_snowmanxxx || _snowmanxxxx) {
            List<EndSpikeFeature.Spike> _snowmanxxxxx = EndSpikeFeature.getSpikes(world);
            int _snowmanxxxxxx = _snowman / 40;
            if (_snowmanxxxxxx < _snowmanxxxxx.size()) {
               EndSpikeFeature.Spike _snowmanxxxxxxx = _snowmanxxxxx.get(_snowmanxxxxxx);
               if (_snowmanxxx) {
                  for (EndCrystalEntity _snowmanxxxxxxxx : crystals) {
                     _snowmanxxxxxxxx.setBeamTarget(new BlockPos(_snowmanxxxxxxx.getCenterX(), _snowmanxxxxxxx.getHeight() + 1, _snowmanxxxxxxx.getCenterZ()));
                  }
               } else {
                  int _snowmanxxxxxxxx = 10;

                  for (BlockPos _snowmanxxxxxxxxx : BlockPos.iterate(
                     new BlockPos(_snowmanxxxxxxx.getCenterX() - 10, _snowmanxxxxxxx.getHeight() - 10, _snowmanxxxxxxx.getCenterZ() - 10),
                     new BlockPos(_snowmanxxxxxxx.getCenterX() + 10, _snowmanxxxxxxx.getHeight() + 10, _snowmanxxxxxxx.getCenterZ() + 10)
                  )) {
                     world.removeBlock(_snowmanxxxxxxxxx, false);
                  }

                  world.createExplosion(
                     null,
                     (double)((float)_snowmanxxxxxxx.getCenterX() + 0.5F),
                     (double)_snowmanxxxxxxx.getHeight(),
                     (double)((float)_snowmanxxxxxxx.getCenterZ() + 0.5F),
                     5.0F,
                     Explosion.DestructionType.DESTROY
                  );
                  EndSpikeFeatureConfig _snowmanxxxxxxxxx = new EndSpikeFeatureConfig(true, ImmutableList.of(_snowmanxxxxxxx), new BlockPos(0, 128, 0));
                  Feature.END_SPIKE
                     .configure(_snowmanxxxxxxxxx)
                     .generate(world, world.getChunkManager().getChunkGenerator(), new Random(), new BlockPos(_snowmanxxxxxxx.getCenterX(), 45, _snowmanxxxxxxx.getCenterZ()));
               }
            } else if (_snowmanxxx) {
               fight.setSpawnState(SUMMONING_DRAGON);
            }
         }
      }
   },
   SUMMONING_DRAGON {
      @Override
      public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int _snowman, BlockPos _snowman) {
         if (_snowman >= 100) {
            fight.setSpawnState(END);
            fight.resetEndCrystals();

            for (EndCrystalEntity _snowmanxx : crystals) {
               _snowmanxx.setBeamTarget(null);
               world.createExplosion(_snowmanxx, _snowmanxx.getX(), _snowmanxx.getY(), _snowmanxx.getZ(), 6.0F, Explosion.DestructionType.NONE);
               _snowmanxx.remove();
            }
         } else if (_snowman >= 80) {
            world.syncWorldEvent(3001, new BlockPos(0, 128, 0), 0);
         } else if (_snowman == 0) {
            for (EndCrystalEntity _snowmanxx : crystals) {
               _snowmanxx.setBeamTarget(new BlockPos(0, 128, 0));
            }
         } else if (_snowman < 5) {
            world.syncWorldEvent(3001, new BlockPos(0, 128, 0), 0);
         }
      }
   },
   END {
      @Override
      public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int _snowman, BlockPos _snowman) {
      }
   };

   private EnderDragonSpawnState() {
   }

   public abstract void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int var4, BlockPos var5);
}
