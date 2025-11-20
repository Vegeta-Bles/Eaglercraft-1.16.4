package net.minecraft.world;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.TraderLlamaEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class WanderingTraderManager implements Spawner {
   private final Random random = new Random();
   private final ServerWorldProperties properties;
   private int spawnTimer;
   private int spawnDelay;
   private int spawnChance;

   public WanderingTraderManager(ServerWorldProperties properties) {
      this.properties = properties;
      this.spawnTimer = 1200;
      this.spawnDelay = properties.getWanderingTraderSpawnDelay();
      this.spawnChance = properties.getWanderingTraderSpawnChance();
      if (this.spawnDelay == 0 && this.spawnChance == 0) {
         this.spawnDelay = 24000;
         properties.setWanderingTraderSpawnDelay(this.spawnDelay);
         this.spawnChance = 25;
         properties.setWanderingTraderSpawnChance(this.spawnChance);
      }
   }

   @Override
   public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
      if (!world.getGameRules().getBoolean(GameRules.DO_TRADER_SPAWNING)) {
         return 0;
      } else if (--this.spawnTimer > 0) {
         return 0;
      } else {
         this.spawnTimer = 1200;
         this.spawnDelay -= 1200;
         this.properties.setWanderingTraderSpawnDelay(this.spawnDelay);
         if (this.spawnDelay > 0) {
            return 0;
         } else {
            this.spawnDelay = 24000;
            if (!world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
               return 0;
            } else {
               int _snowman = this.spawnChance;
               this.spawnChance = MathHelper.clamp(this.spawnChance + 25, 25, 75);
               this.properties.setWanderingTraderSpawnChance(this.spawnChance);
               if (this.random.nextInt(100) > _snowman) {
                  return 0;
               } else if (this.method_18018(world)) {
                  this.spawnChance = 25;
                  return 1;
               } else {
                  return 0;
               }
            }
         }
      }
   }

   private boolean method_18018(ServerWorld _snowman) {
      PlayerEntity _snowmanx = _snowman.getRandomAlivePlayer();
      if (_snowmanx == null) {
         return true;
      } else if (this.random.nextInt(10) != 0) {
         return false;
      } else {
         BlockPos _snowmanxx = _snowmanx.getBlockPos();
         int _snowmanxxx = 48;
         PointOfInterestStorage _snowmanxxxx = _snowman.getPointOfInterestStorage();
         Optional<BlockPos> _snowmanxxxxx = _snowmanxxxx.getPosition(
            PointOfInterestType.MEETING.getCompletionCondition(), _snowmanxxxxxx -> true, _snowmanxx, 48, PointOfInterestStorage.OccupationStatus.ANY
         );
         BlockPos _snowmanxxxxxx = _snowmanxxxxx.orElse(_snowmanxx);
         BlockPos _snowmanxxxxxxx = this.getNearbySpawnPos(_snowman, _snowmanxxxxxx, 48);
         if (_snowmanxxxxxxx != null && this.doesNotSuffocateAt(_snowman, _snowmanxxxxxxx)) {
            if (_snowman.method_31081(_snowmanxxxxxxx).equals(Optional.of(BiomeKeys.THE_VOID))) {
               return false;
            }

            WanderingTraderEntity _snowmanxxxxxxxx = EntityType.WANDERING_TRADER.spawn(_snowman, null, null, null, _snowmanxxxxxxx, SpawnReason.EVENT, false, false);
            if (_snowmanxxxxxxxx != null) {
               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 2; _snowmanxxxxxxxxx++) {
                  this.spawnLlama(_snowman, _snowmanxxxxxxxx, 4);
               }

               this.properties.setWanderingTraderId(_snowmanxxxxxxxx.getUuid());
               _snowmanxxxxxxxx.setDespawnDelay(48000);
               _snowmanxxxxxxxx.setWanderTarget(_snowmanxxxxxx);
               _snowmanxxxxxxxx.setPositionTarget(_snowmanxxxxxx, 16);
               return true;
            }
         }

         return false;
      }
   }

   private void spawnLlama(ServerWorld _snowman, WanderingTraderEntity _snowman, int _snowman) {
      BlockPos _snowmanxxx = this.getNearbySpawnPos(_snowman, _snowman.getBlockPos(), _snowman);
      if (_snowmanxxx != null) {
         TraderLlamaEntity _snowmanxxxx = EntityType.TRADER_LLAMA.spawn(_snowman, null, null, null, _snowmanxxx, SpawnReason.EVENT, false, false);
         if (_snowmanxxxx != null) {
            _snowmanxxxx.attachLeash(_snowman, true);
         }
      }
   }

   @Nullable
   private BlockPos getNearbySpawnPos(WorldView _snowman, BlockPos _snowman, int _snowman) {
      BlockPos _snowmanxxx = null;

      for (int _snowmanxxxx = 0; _snowmanxxxx < 10; _snowmanxxxx++) {
         int _snowmanxxxxx = _snowman.getX() + this.random.nextInt(_snowman * 2) - _snowman;
         int _snowmanxxxxxx = _snowman.getZ() + this.random.nextInt(_snowman * 2) - _snowman;
         int _snowmanxxxxxxx = _snowman.getTopY(Heightmap.Type.WORLD_SURFACE, _snowmanxxxxx, _snowmanxxxxxx);
         BlockPos _snowmanxxxxxxxx = new BlockPos(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx);
         if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, _snowman, _snowmanxxxxxxxx, EntityType.WANDERING_TRADER)) {
            _snowmanxxx = _snowmanxxxxxxxx;
            break;
         }
      }

      return _snowmanxxx;
   }

   private boolean doesNotSuffocateAt(BlockView _snowman, BlockPos _snowman) {
      for (BlockPos _snowmanxx : BlockPos.iterate(_snowman, _snowman.add(1, 2, 1))) {
         if (!_snowman.getBlockState(_snowmanxx).getCollisionShape(_snowman, _snowmanxx).isEmpty()) {
            return false;
         }
      }

      return true;
   }
}
