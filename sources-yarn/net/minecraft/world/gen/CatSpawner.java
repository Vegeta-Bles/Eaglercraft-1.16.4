package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameRules;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class CatSpawner implements Spawner {
   private int ticksUntilNextSpawn;

   public CatSpawner() {
   }

   @Override
   public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
      if (spawnAnimals && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
         this.ticksUntilNextSpawn--;
         if (this.ticksUntilNextSpawn > 0) {
            return 0;
         } else {
            this.ticksUntilNextSpawn = 1200;
            PlayerEntity lv = world.getRandomAlivePlayer();
            if (lv == null) {
               return 0;
            } else {
               Random random = world.random;
               int i = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
               int j = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
               BlockPos lv2 = lv.getBlockPos().add(i, 0, j);
               if (!world.isRegionLoaded(lv2.getX() - 10, lv2.getY() - 10, lv2.getZ() - 10, lv2.getX() + 10, lv2.getY() + 10, lv2.getZ() + 10)) {
                  return 0;
               } else {
                  if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, lv2, EntityType.CAT)) {
                     if (world.isNearOccupiedPointOfInterest(lv2, 2)) {
                        return this.spawnInHouse(world, lv2);
                     }

                     if (world.getStructureAccessor().getStructureAt(lv2, true, StructureFeature.SWAMP_HUT).hasChildren()) {
                        return this.spawnInSwampHut(world, lv2);
                     }
                  }

                  return 0;
               }
            }
         }
      } else {
         return 0;
      }
   }

   private int spawnInHouse(ServerWorld world, BlockPos pos) {
      int i = 48;
      if (world.getPointOfInterestStorage()
            .count(PointOfInterestType.HOME.getCompletionCondition(), pos, 48, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED)
         > 4L) {
         List<CatEntity> list = world.getNonSpectatingEntities(CatEntity.class, new Box(pos).expand(48.0, 8.0, 48.0));
         if (list.size() < 5) {
            return this.spawn(pos, world);
         }
      }

      return 0;
   }

   private int spawnInSwampHut(ServerWorld world, BlockPos pos) {
      int i = 16;
      List<CatEntity> list = world.getNonSpectatingEntities(CatEntity.class, new Box(pos).expand(16.0, 8.0, 16.0));
      return list.size() < 1 ? this.spawn(pos, world) : 0;
   }

   private int spawn(BlockPos pos, ServerWorld world) {
      CatEntity lv = EntityType.CAT.create(world);
      if (lv == null) {
         return 0;
      } else {
         lv.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, null, null);
         lv.refreshPositionAndAngles(pos, 0.0F, 0.0F);
         world.spawnEntityAndPassengers(lv);
         return 1;
      }
   }
}
