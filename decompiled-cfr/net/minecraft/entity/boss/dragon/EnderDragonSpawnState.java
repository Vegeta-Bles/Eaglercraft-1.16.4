/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.entity.boss.dragon;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public enum EnderDragonSpawnState {
    START{

        public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int n, BlockPos blockPos) {
            _snowman = new BlockPos(0, 128, 0);
            for (EndCrystalEntity endCrystalEntity : crystals) {
                endCrystalEntity.setBeamTarget(_snowman);
            }
            fight.setSpawnState(PREPARING_TO_SUMMON_PILLARS);
        }
    }
    ,
    PREPARING_TO_SUMMON_PILLARS{

        public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int n, BlockPos blockPos) {
            if (n < 100) {
                if (n == 0 || n == 50 || n == 51 || n == 52 || n >= 95) {
                    world.syncWorldEvent(3001, new BlockPos(0, 128, 0), 0);
                }
            } else {
                fight.setSpawnState(SUMMONING_PILLARS);
            }
        }
    }
    ,
    SUMMONING_PILLARS{

        public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int n, BlockPos blockPos) {
            int n2 = 40;
            boolean _snowman2 = n % 40 == 0;
            boolean bl = _snowman = n % 40 == 39;
            if (_snowman2 || _snowman) {
                _snowman = n / 40;
                List<EndSpikeFeature.Spike> list = EndSpikeFeature.getSpikes(world);
                if (_snowman < list.size()) {
                    EndSpikeFeature.Spike spike = list.get(_snowman);
                    if (_snowman2) {
                        for (EndCrystalEntity _snowman3 : crystals) {
                            _snowman3.setBeamTarget(new BlockPos(spike.getCenterX(), spike.getHeight() + 1, spike.getCenterZ()));
                        }
                    } else {
                        int n3 = 10;
                        for (BlockPos blockPos2 : BlockPos.iterate(new BlockPos(spike.getCenterX() - 10, spike.getHeight() - 10, spike.getCenterZ() - 10), new BlockPos(spike.getCenterX() + 10, spike.getHeight() + 10, spike.getCenterZ() + 10))) {
                            world.removeBlock(blockPos2, false);
                        }
                        world.createExplosion(null, (float)spike.getCenterX() + 0.5f, spike.getHeight(), (float)spike.getCenterZ() + 0.5f, 5.0f, Explosion.DestructionType.DESTROY);
                        EndSpikeFeatureConfig _snowman3 = new EndSpikeFeatureConfig(true, (List<EndSpikeFeature.Spike>)ImmutableList.of((Object)spike), new BlockPos(0, 128, 0));
                        Feature.END_SPIKE.configure(_snowman3).generate(world, world.getChunkManager().getChunkGenerator(), new Random(), new BlockPos(spike.getCenterX(), 45, spike.getCenterZ()));
                    }
                } else if (_snowman2) {
                    fight.setSpawnState(SUMMONING_DRAGON);
                }
            }
        }
    }
    ,
    SUMMONING_DRAGON{

        public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int n2, BlockPos blockPos) {
            int n2;
            if (n2 >= 100) {
                fight.setSpawnState(END);
                fight.resetEndCrystals();
                for (EndCrystalEntity endCrystalEntity : crystals) {
                    endCrystalEntity.setBeamTarget(null);
                    world.createExplosion(endCrystalEntity, endCrystalEntity.getX(), endCrystalEntity.getY(), endCrystalEntity.getZ(), 6.0f, Explosion.DestructionType.NONE);
                    endCrystalEntity.remove();
                }
            } else if (n2 >= 80) {
                world.syncWorldEvent(3001, new BlockPos(0, 128, 0), 0);
            } else if (n2 == 0) {
                for (EndCrystalEntity endCrystalEntity : crystals) {
                    endCrystalEntity.setBeamTarget(new BlockPos(0, 128, 0));
                }
            } else if (n2 < 5) {
                world.syncWorldEvent(3001, new BlockPos(0, 128, 0), 0);
            }
        }
    }
    ,
    END{

        public void run(ServerWorld world, EnderDragonFight fight, List<EndCrystalEntity> crystals, int n, BlockPos blockPos) {
        }
    };


    public abstract void run(ServerWorld var1, EnderDragonFight var2, List<EndCrystalEntity> var3, int var4, BlockPos var5);
}

