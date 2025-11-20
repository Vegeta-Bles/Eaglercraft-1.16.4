package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;

public class PhantomSpawner implements Spawner {
   private int ticksUntilNextSpawn;

   public PhantomSpawner() {
   }

   @Override
   public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
      if (!spawnMonsters) {
         return 0;
      } else if (!world.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) {
         return 0;
      } else {
         Random random = world.random;
         this.ticksUntilNextSpawn--;
         if (this.ticksUntilNextSpawn > 0) {
            return 0;
         } else {
            this.ticksUntilNextSpawn = this.ticksUntilNextSpawn + (60 + random.nextInt(60)) * 20;
            if (world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
               return 0;
            } else {
               int i = 0;

               for (PlayerEntity lv : world.getPlayers()) {
                  if (!lv.isSpectator()) {
                     BlockPos lv2 = lv.getBlockPos();
                     if (!world.getDimension().hasSkyLight() || lv2.getY() >= world.getSeaLevel() && world.isSkyVisible(lv2)) {
                        LocalDifficulty lv3 = world.getLocalDifficulty(lv2);
                        if (lv3.isHarderThan(random.nextFloat() * 3.0F)) {
                           ServerStatHandler lv4 = ((ServerPlayerEntity)lv).getStatHandler();
                           int j = MathHelper.clamp(lv4.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                           int k = 24000;
                           if (random.nextInt(j) >= 72000) {
                              BlockPos lv5 = lv2.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21));
                              BlockState lv6 = world.getBlockState(lv5);
                              FluidState lv7 = world.getFluidState(lv5);
                              if (SpawnHelper.isClearForSpawn(world, lv5, lv6, lv7, EntityType.PHANTOM)) {
                                 EntityData lv8 = null;
                                 int l = 1 + random.nextInt(lv3.getGlobalDifficulty().getId() + 1);

                                 for (int m = 0; m < l; m++) {
                                    PhantomEntity lv9 = EntityType.PHANTOM.create(world);
                                    lv9.refreshPositionAndAngles(lv5, 0.0F, 0.0F);
                                    lv8 = lv9.initialize(world, lv3, SpawnReason.NATURAL, lv8, null);
                                    world.spawnEntityAndPassengers(lv9);
                                 }

                                 i += l;
                              }
                           }
                        }
                     }
                  }
               }

               return i;
            }
         }
      }
   }
}
