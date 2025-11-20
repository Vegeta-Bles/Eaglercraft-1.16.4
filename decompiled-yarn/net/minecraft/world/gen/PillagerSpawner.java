package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;

public class PillagerSpawner implements Spawner {
   private int ticksUntilNextSpawn;

   public PillagerSpawner() {
   }

   @Override
   public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
      if (!spawnMonsters) {
         return 0;
      } else if (!world.getGameRules().getBoolean(GameRules.DO_PATROL_SPAWNING)) {
         return 0;
      } else {
         Random _snowman = world.random;
         this.ticksUntilNextSpawn--;
         if (this.ticksUntilNextSpawn > 0) {
            return 0;
         } else {
            this.ticksUntilNextSpawn = this.ticksUntilNextSpawn + 12000 + _snowman.nextInt(1200);
            long _snowmanx = world.getTimeOfDay() / 24000L;
            if (_snowmanx < 5L || !world.isDay()) {
               return 0;
            } else if (_snowman.nextInt(5) != 0) {
               return 0;
            } else {
               int _snowmanxx = world.getPlayers().size();
               if (_snowmanxx < 1) {
                  return 0;
               } else {
                  PlayerEntity _snowmanxxx = world.getPlayers().get(_snowman.nextInt(_snowmanxx));
                  if (_snowmanxxx.isSpectator()) {
                     return 0;
                  } else if (world.isNearOccupiedPointOfInterest(_snowmanxxx.getBlockPos(), 2)) {
                     return 0;
                  } else {
                     int _snowmanxxxx = (24 + _snowman.nextInt(24)) * (_snowman.nextBoolean() ? -1 : 1);
                     int _snowmanxxxxx = (24 + _snowman.nextInt(24)) * (_snowman.nextBoolean() ? -1 : 1);
                     BlockPos.Mutable _snowmanxxxxxx = _snowmanxxx.getBlockPos().mutableCopy().move(_snowmanxxxx, 0, _snowmanxxxxx);
                     if (!world.isRegionLoaded(
                        _snowmanxxxxxx.getX() - 10, _snowmanxxxxxx.getY() - 10, _snowmanxxxxxx.getZ() - 10, _snowmanxxxxxx.getX() + 10, _snowmanxxxxxx.getY() + 10, _snowmanxxxxxx.getZ() + 10
                     )) {
                        return 0;
                     } else {
                        Biome _snowmanxxxxxxx = world.getBiome(_snowmanxxxxxx);
                        Biome.Category _snowmanxxxxxxxx = _snowmanxxxxxxx.getCategory();
                        if (_snowmanxxxxxxxx == Biome.Category.MUSHROOM) {
                           return 0;
                        } else {
                           int _snowmanxxxxxxxxx = 0;
                           int _snowmanxxxxxxxxxx = (int)Math.ceil((double)world.getLocalDifficulty(_snowmanxxxxxx).getLocalDifficulty()) + 1;

                           for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxx++) {
                              _snowmanxxxxxxxxx++;
                              _snowmanxxxxxx.setY(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, _snowmanxxxxxx).getY());
                              if (_snowmanxxxxxxxxxxx == 0) {
                                 if (!this.spawnPillager(world, _snowmanxxxxxx, _snowman, true)) {
                                    break;
                                 }
                              } else {
                                 this.spawnPillager(world, _snowmanxxxxxx, _snowman, false);
                              }

                              _snowmanxxxxxx.setX(_snowmanxxxxxx.getX() + _snowman.nextInt(5) - _snowman.nextInt(5));
                              _snowmanxxxxxx.setZ(_snowmanxxxxxx.getZ() + _snowman.nextInt(5) - _snowman.nextInt(5));
                           }

                           return _snowmanxxxxxxxxx;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean spawnPillager(ServerWorld world, BlockPos pos, Random random, boolean captain) {
      BlockState _snowman = world.getBlockState(pos);
      if (!SpawnHelper.isClearForSpawn(world, pos, _snowman, _snowman.getFluidState(), EntityType.PILLAGER)) {
         return false;
      } else if (!PatrolEntity.canSpawn(EntityType.PILLAGER, world, SpawnReason.PATROL, pos, random)) {
         return false;
      } else {
         PatrolEntity _snowmanx = EntityType.PILLAGER.create(world);
         if (_snowmanx != null) {
            if (captain) {
               _snowmanx.setPatrolLeader(true);
               _snowmanx.setRandomPatrolTarget();
            }

            _snowmanx.updatePosition((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
            _snowmanx.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, null, null);
            world.spawnEntityAndPassengers(_snowmanx);
            return true;
         } else {
            return false;
         }
      }
   }
}
