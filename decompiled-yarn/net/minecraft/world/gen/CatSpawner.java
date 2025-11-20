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
            PlayerEntity _snowman = world.getRandomAlivePlayer();
            if (_snowman == null) {
               return 0;
            } else {
               Random _snowmanx = world.random;
               int _snowmanxx = (8 + _snowmanx.nextInt(24)) * (_snowmanx.nextBoolean() ? -1 : 1);
               int _snowmanxxx = (8 + _snowmanx.nextInt(24)) * (_snowmanx.nextBoolean() ? -1 : 1);
               BlockPos _snowmanxxxx = _snowman.getBlockPos().add(_snowmanxx, 0, _snowmanxxx);
               if (!world.isRegionLoaded(_snowmanxxxx.getX() - 10, _snowmanxxxx.getY() - 10, _snowmanxxxx.getZ() - 10, _snowmanxxxx.getX() + 10, _snowmanxxxx.getY() + 10, _snowmanxxxx.getZ() + 10)) {
                  return 0;
               } else {
                  if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, _snowmanxxxx, EntityType.CAT)) {
                     if (world.isNearOccupiedPointOfInterest(_snowmanxxxx, 2)) {
                        return this.spawnInHouse(world, _snowmanxxxx);
                     }

                     if (world.getStructureAccessor().getStructureAt(_snowmanxxxx, true, StructureFeature.SWAMP_HUT).hasChildren()) {
                        return this.spawnInSwampHut(world, _snowmanxxxx);
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
      int _snowman = 48;
      if (world.getPointOfInterestStorage()
            .count(PointOfInterestType.HOME.getCompletionCondition(), pos, 48, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED)
         > 4L) {
         List<CatEntity> _snowmanx = world.getNonSpectatingEntities(CatEntity.class, new Box(pos).expand(48.0, 8.0, 48.0));
         if (_snowmanx.size() < 5) {
            return this.spawn(pos, world);
         }
      }

      return 0;
   }

   private int spawnInSwampHut(ServerWorld world, BlockPos pos) {
      int _snowman = 16;
      List<CatEntity> _snowmanx = world.getNonSpectatingEntities(CatEntity.class, new Box(pos).expand(16.0, 8.0, 16.0));
      return _snowmanx.size() < 1 ? this.spawn(pos, world) : 0;
   }

   private int spawn(BlockPos pos, ServerWorld world) {
      CatEntity _snowman = EntityType.CAT.create(world);
      if (_snowman == null) {
         return 0;
      } else {
         _snowman.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, null, null);
         _snowman.refreshPositionAndAngles(pos, 0.0F, 0.0F);
         world.spawnEntityAndPassengers(_snowman);
         return 1;
      }
   }
}
