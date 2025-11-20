package net.minecraft.village;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Spawner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZombieSiegeManager implements Spawner {
   private static final Logger field_26390 = LogManager.getLogger();
   private boolean spawned;
   private ZombieSiegeManager.State state = ZombieSiegeManager.State.SIEGE_DONE;
   private int remaining;
   private int countdown;
   private int startX;
   private int startY;
   private int startZ;

   public ZombieSiegeManager() {
   }

   @Override
   public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
      if (!world.isDay() && spawnMonsters) {
         float _snowman = world.getSkyAngle(0.0F);
         if ((double)_snowman == 0.5) {
            this.state = world.random.nextInt(10) == 0 ? ZombieSiegeManager.State.SIEGE_TONIGHT : ZombieSiegeManager.State.SIEGE_DONE;
         }

         if (this.state == ZombieSiegeManager.State.SIEGE_DONE) {
            return 0;
         } else {
            if (!this.spawned) {
               if (!this.spawn(world)) {
                  return 0;
               }

               this.spawned = true;
            }

            if (this.countdown > 0) {
               this.countdown--;
               return 0;
            } else {
               this.countdown = 2;
               if (this.remaining > 0) {
                  this.trySpawnZombie(world);
                  this.remaining--;
               } else {
                  this.state = ZombieSiegeManager.State.SIEGE_DONE;
               }

               return 1;
            }
         }
      } else {
         this.state = ZombieSiegeManager.State.SIEGE_DONE;
         this.spawned = false;
         return 0;
      }
   }

   private boolean spawn(ServerWorld world) {
      for (PlayerEntity _snowman : world.getPlayers()) {
         if (!_snowman.isSpectator()) {
            BlockPos _snowmanx = _snowman.getBlockPos();
            if (world.isNearOccupiedPointOfInterest(_snowmanx) && world.getBiome(_snowmanx).getCategory() != Biome.Category.MUSHROOM) {
               for (int _snowmanxx = 0; _snowmanxx < 10; _snowmanxx++) {
                  float _snowmanxxx = world.random.nextFloat() * (float) (Math.PI * 2);
                  this.startX = _snowmanx.getX() + MathHelper.floor(MathHelper.cos(_snowmanxxx) * 32.0F);
                  this.startY = _snowmanx.getY();
                  this.startZ = _snowmanx.getZ() + MathHelper.floor(MathHelper.sin(_snowmanxxx) * 32.0F);
                  if (this.getSpawnVector(world, new BlockPos(this.startX, this.startY, this.startZ)) != null) {
                     this.countdown = 0;
                     this.remaining = 20;
                     break;
                  }
               }

               return true;
            }
         }
      }

      return false;
   }

   private void trySpawnZombie(ServerWorld world) {
      Vec3d _snowman = this.getSpawnVector(world, new BlockPos(this.startX, this.startY, this.startZ));
      if (_snowman != null) {
         ZombieEntity _snowmanx;
         try {
            _snowmanx = new ZombieEntity(world);
            _snowmanx.initialize(world, world.getLocalDifficulty(_snowmanx.getBlockPos()), SpawnReason.EVENT, null, null);
         } catch (Exception var5) {
            field_26390.warn("Failed to create zombie for village siege at {}", _snowman, var5);
            return;
         }

         _snowmanx.refreshPositionAndAngles(_snowman.x, _snowman.y, _snowman.z, world.random.nextFloat() * 360.0F, 0.0F);
         world.spawnEntityAndPassengers(_snowmanx);
      }
   }

   @Nullable
   private Vec3d getSpawnVector(ServerWorld world, BlockPos pos) {
      for (int _snowman = 0; _snowman < 10; _snowman++) {
         int _snowmanx = pos.getX() + world.random.nextInt(16) - 8;
         int _snowmanxx = pos.getZ() + world.random.nextInt(16) - 8;
         int _snowmanxxx = world.getTopY(Heightmap.Type.WORLD_SURFACE, _snowmanx, _snowmanxx);
         BlockPos _snowmanxxxx = new BlockPos(_snowmanx, _snowmanxxx, _snowmanxx);
         if (world.isNearOccupiedPointOfInterest(_snowmanxxxx) && HostileEntity.canSpawnInDark(EntityType.ZOMBIE, world, SpawnReason.EVENT, _snowmanxxxx, world.random)) {
            return Vec3d.ofBottomCenter(_snowmanxxxx);
         }
      }

      return null;
   }

   static enum State {
      SIEGE_CAN_ACTIVATE,
      SIEGE_TONIGHT,
      SIEGE_DONE;

      private State() {
      }
   }
}
