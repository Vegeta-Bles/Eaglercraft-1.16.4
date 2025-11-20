package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MobSpawnerLogic {
   private static final Logger LOGGER = LogManager.getLogger();
   private int spawnDelay = 20;
   private final List<MobSpawnerEntry> spawnPotentials = Lists.newArrayList();
   private MobSpawnerEntry spawnEntry = new MobSpawnerEntry();
   private double field_9161;
   private double field_9159;
   private int minSpawnDelay = 200;
   private int maxSpawnDelay = 800;
   private int spawnCount = 4;
   @Nullable
   private Entity renderedEntity;
   private int maxNearbyEntities = 6;
   private int requiredPlayerRange = 16;
   private int spawnRange = 4;

   public MobSpawnerLogic() {
   }

   @Nullable
   private Identifier getEntityId() {
      String _snowman = this.spawnEntry.getEntityTag().getString("id");

      try {
         return ChatUtil.isEmpty(_snowman) ? null : new Identifier(_snowman);
      } catch (InvalidIdentifierException var4) {
         BlockPos _snowmanx = this.getPos();
         LOGGER.warn("Invalid entity id '{}' at spawner {}:[{},{},{}]", _snowman, this.getWorld().getRegistryKey().getValue(), _snowmanx.getX(), _snowmanx.getY(), _snowmanx.getZ());
         return null;
      }
   }

   public void setEntityId(EntityType<?> type) {
      this.spawnEntry.getEntityTag().putString("id", Registry.ENTITY_TYPE.getId(type).toString());
   }

   private boolean isPlayerInRange() {
      BlockPos _snowman = this.getPos();
      return this.getWorld().isPlayerInRange((double)_snowman.getX() + 0.5, (double)_snowman.getY() + 0.5, (double)_snowman.getZ() + 0.5, (double)this.requiredPlayerRange);
   }

   public void update() {
      if (!this.isPlayerInRange()) {
         this.field_9159 = this.field_9161;
      } else {
         World _snowman = this.getWorld();
         BlockPos _snowmanx = this.getPos();
         if (!(_snowman instanceof ServerWorld)) {
            double _snowmanxx = (double)_snowmanx.getX() + _snowman.random.nextDouble();
            double _snowmanxxx = (double)_snowmanx.getY() + _snowman.random.nextDouble();
            double _snowmanxxxx = (double)_snowmanx.getZ() + _snowman.random.nextDouble();
            _snowman.addParticle(ParticleTypes.SMOKE, _snowmanxx, _snowmanxxx, _snowmanxxxx, 0.0, 0.0, 0.0);
            _snowman.addParticle(ParticleTypes.FLAME, _snowmanxx, _snowmanxxx, _snowmanxxxx, 0.0, 0.0, 0.0);
            if (this.spawnDelay > 0) {
               this.spawnDelay--;
            }

            this.field_9159 = this.field_9161;
            this.field_9161 = (this.field_9161 + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0;
         } else {
            if (this.spawnDelay == -1) {
               this.updateSpawns();
            }

            if (this.spawnDelay > 0) {
               this.spawnDelay--;
               return;
            }

            boolean _snowmanxx = false;

            for (int _snowmanxxx = 0; _snowmanxxx < this.spawnCount; _snowmanxxx++) {
               CompoundTag _snowmanxxxx = this.spawnEntry.getEntityTag();
               Optional<EntityType<?>> _snowmanxxxxx = EntityType.fromTag(_snowmanxxxx);
               if (!_snowmanxxxxx.isPresent()) {
                  this.updateSpawns();
                  return;
               }

               ListTag _snowmanxxxxxx = _snowmanxxxx.getList("Pos", 6);
               int _snowmanxxxxxxx = _snowmanxxxxxx.size();
               double _snowmanxxxxxxxx = _snowmanxxxxxxx >= 1
                  ? _snowmanxxxxxx.getDouble(0)
                  : (double)_snowmanx.getX() + (_snowman.random.nextDouble() - _snowman.random.nextDouble()) * (double)this.spawnRange + 0.5;
               double _snowmanxxxxxxxxx = _snowmanxxxxxxx >= 2 ? _snowmanxxxxxx.getDouble(1) : (double)(_snowmanx.getY() + _snowman.random.nextInt(3) - 1);
               double _snowmanxxxxxxxxxx = _snowmanxxxxxxx >= 3
                  ? _snowmanxxxxxx.getDouble(2)
                  : (double)_snowmanx.getZ() + (_snowman.random.nextDouble() - _snowman.random.nextDouble()) * (double)this.spawnRange + 0.5;
               if (_snowman.isSpaceEmpty(_snowmanxxxxx.get().createSimpleBoundingBox(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx))) {
                  ServerWorld _snowmanxxxxxxxxxxx = (ServerWorld)_snowman;
                  if (SpawnRestriction.canSpawn(
                     _snowmanxxxxx.get(), _snowmanxxxxxxxxxxx, SpawnReason.SPAWNER, new BlockPos(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx), _snowman.getRandom()
                  )) {
                     Entity _snowmanxxxxxxxxxxxx = EntityType.loadEntityWithPassengers(_snowmanxxxx, _snowman, _snowmanxxxxxxxxxxxxx -> {
                        _snowmanxxxxxxxxxxxxx.refreshPositionAndAngles(_snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxxx.yaw, _snowmanxxxxxxxxxxxxx.pitch);
                        return _snowmanxxxxxxxxxxxxx;
                     });
                     if (_snowmanxxxxxxxxxxxx == null) {
                        this.updateSpawns();
                        return;
                     }

                     int _snowmanxxxxxxxxxxxxx = _snowman.getNonSpectatingEntities(
                           _snowmanxxxxxxxxxxxx.getClass(),
                           new Box(
                                 (double)_snowmanx.getX(),
                                 (double)_snowmanx.getY(),
                                 (double)_snowmanx.getZ(),
                                 (double)(_snowmanx.getX() + 1),
                                 (double)(_snowmanx.getY() + 1),
                                 (double)(_snowmanx.getZ() + 1)
                              )
                              .expand((double)this.spawnRange)
                        )
                        .size();
                     if (_snowmanxxxxxxxxxxxxx >= this.maxNearbyEntities) {
                        this.updateSpawns();
                        return;
                     }

                     _snowmanxxxxxxxxxxxx.refreshPositionAndAngles(
                        _snowmanxxxxxxxxxxxx.getX(), _snowmanxxxxxxxxxxxx.getY(), _snowmanxxxxxxxxxxxx.getZ(), _snowman.random.nextFloat() * 360.0F, 0.0F
                     );
                     if (_snowmanxxxxxxxxxxxx instanceof MobEntity) {
                        MobEntity _snowmanxxxxxxxxxxxxxx = (MobEntity)_snowmanxxxxxxxxxxxx;
                        if (!_snowmanxxxxxxxxxxxxxx.canSpawn(_snowman, SpawnReason.SPAWNER) || !_snowmanxxxxxxxxxxxxxx.canSpawn(_snowman)) {
                           continue;
                        }

                        if (this.spawnEntry.getEntityTag().getSize() == 1 && this.spawnEntry.getEntityTag().contains("id", 8)) {
                           ((MobEntity)_snowmanxxxxxxxxxxxx)
                              .initialize(_snowmanxxxxxxxxxxx, _snowman.getLocalDifficulty(_snowmanxxxxxxxxxxxx.getBlockPos()), SpawnReason.SPAWNER, null, null);
                        }
                     }

                     if (!_snowmanxxxxxxxxxxx.shouldCreateNewEntityWithPassenger(_snowmanxxxxxxxxxxxx)) {
                        this.updateSpawns();
                        return;
                     }

                     _snowman.syncWorldEvent(2004, _snowmanx, 0);
                     if (_snowmanxxxxxxxxxxxx instanceof MobEntity) {
                        ((MobEntity)_snowmanxxxxxxxxxxxx).playSpawnEffects();
                     }

                     _snowmanxx = true;
                  }
               }
            }

            if (_snowmanxx) {
               this.updateSpawns();
            }
         }
      }
   }

   private void updateSpawns() {
      if (this.maxSpawnDelay <= this.minSpawnDelay) {
         this.spawnDelay = this.minSpawnDelay;
      } else {
         this.spawnDelay = this.minSpawnDelay + this.getWorld().random.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
      }

      if (!this.spawnPotentials.isEmpty()) {
         this.setSpawnEntry(WeightedPicker.getRandom(this.getWorld().random, this.spawnPotentials));
      }

      this.sendStatus(1);
   }

   public void fromTag(CompoundTag tag) {
      this.spawnDelay = tag.getShort("Delay");
      this.spawnPotentials.clear();
      if (tag.contains("SpawnPotentials", 9)) {
         ListTag _snowman = tag.getList("SpawnPotentials", 10);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            this.spawnPotentials.add(new MobSpawnerEntry(_snowman.getCompound(_snowmanx)));
         }
      }

      if (tag.contains("SpawnData", 10)) {
         this.setSpawnEntry(new MobSpawnerEntry(1, tag.getCompound("SpawnData")));
      } else if (!this.spawnPotentials.isEmpty()) {
         this.setSpawnEntry(WeightedPicker.getRandom(this.getWorld().random, this.spawnPotentials));
      }

      if (tag.contains("MinSpawnDelay", 99)) {
         this.minSpawnDelay = tag.getShort("MinSpawnDelay");
         this.maxSpawnDelay = tag.getShort("MaxSpawnDelay");
         this.spawnCount = tag.getShort("SpawnCount");
      }

      if (tag.contains("MaxNearbyEntities", 99)) {
         this.maxNearbyEntities = tag.getShort("MaxNearbyEntities");
         this.requiredPlayerRange = tag.getShort("RequiredPlayerRange");
      }

      if (tag.contains("SpawnRange", 99)) {
         this.spawnRange = tag.getShort("SpawnRange");
      }

      if (this.getWorld() != null) {
         this.renderedEntity = null;
      }
   }

   public CompoundTag toTag(CompoundTag tag) {
      Identifier _snowman = this.getEntityId();
      if (_snowman == null) {
         return tag;
      } else {
         tag.putShort("Delay", (short)this.spawnDelay);
         tag.putShort("MinSpawnDelay", (short)this.minSpawnDelay);
         tag.putShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
         tag.putShort("SpawnCount", (short)this.spawnCount);
         tag.putShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
         tag.putShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
         tag.putShort("SpawnRange", (short)this.spawnRange);
         tag.put("SpawnData", this.spawnEntry.getEntityTag().copy());
         ListTag _snowmanx = new ListTag();
         if (this.spawnPotentials.isEmpty()) {
            _snowmanx.add(this.spawnEntry.serialize());
         } else {
            for (MobSpawnerEntry _snowmanxx : this.spawnPotentials) {
               _snowmanx.add(_snowmanxx.serialize());
            }
         }

         tag.put("SpawnPotentials", _snowmanx);
         return tag;
      }
   }

   @Nullable
   public Entity getRenderedEntity() {
      if (this.renderedEntity == null) {
         this.renderedEntity = EntityType.loadEntityWithPassengers(this.spawnEntry.getEntityTag(), this.getWorld(), Function.identity());
         if (this.spawnEntry.getEntityTag().getSize() == 1 && this.spawnEntry.getEntityTag().contains("id", 8) && this.renderedEntity instanceof MobEntity) {
         }
      }

      return this.renderedEntity;
   }

   public boolean method_8275(int _snowman) {
      if (_snowman == 1 && this.getWorld().isClient) {
         this.spawnDelay = this.minSpawnDelay;
         return true;
      } else {
         return false;
      }
   }

   public void setSpawnEntry(MobSpawnerEntry spawnEntry) {
      this.spawnEntry = spawnEntry;
   }

   public abstract void sendStatus(int status);

   public abstract World getWorld();

   public abstract BlockPos getPos();

   public double method_8278() {
      return this.field_9161;
   }

   public double method_8279() {
      return this.field_9159;
   }
}
