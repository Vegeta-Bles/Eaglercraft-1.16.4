/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
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
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.World;
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

    @Nullable
    private Identifier getEntityId() {
        String string = this.spawnEntry.getEntityTag().getString("id");
        try {
            return ChatUtil.isEmpty(string) ? null : new Identifier(string);
        }
        catch (InvalidIdentifierException _snowman2) {
            BlockPos blockPos = this.getPos();
            LOGGER.warn("Invalid entity id '{}' at spawner {}:[{},{},{}]", (Object)string, (Object)this.getWorld().getRegistryKey().getValue(), (Object)blockPos.getX(), (Object)blockPos.getY(), (Object)blockPos.getZ());
            return null;
        }
    }

    public void setEntityId(EntityType<?> type) {
        this.spawnEntry.getEntityTag().putString("id", Registry.ENTITY_TYPE.getId(type).toString());
    }

    private boolean isPlayerInRange() {
        BlockPos blockPos = this.getPos();
        return this.getWorld().isPlayerInRange((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, this.requiredPlayerRange);
    }

    public void update() {
        if (!this.isPlayerInRange()) {
            this.field_9159 = this.field_9161;
            return;
        }
        World world = this.getWorld();
        BlockPos _snowman2 = this.getPos();
        if (!(world instanceof ServerWorld)) {
            double d = (double)_snowman2.getX() + world.random.nextDouble();
            _snowman = (double)_snowman2.getY() + world.random.nextDouble();
            _snowman = (double)_snowman2.getZ() + world.random.nextDouble();
            world.addParticle(ParticleTypes.SMOKE, d, _snowman, _snowman, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.FLAME, d, _snowman, _snowman, 0.0, 0.0, 0.0);
            if (this.spawnDelay > 0) {
                --this.spawnDelay;
            }
            this.field_9159 = this.field_9161;
            this.field_9161 = (this.field_9161 + (double)(1000.0f / ((float)this.spawnDelay + 200.0f))) % 360.0;
        } else {
            if (this.spawnDelay == -1) {
                this.updateSpawns();
            }
            if (this.spawnDelay > 0) {
                --this.spawnDelay;
                return;
            }
            boolean _snowman11 = false;
            for (int i = 0; i < this.spawnCount; ++i) {
                CompoundTag compoundTag = this.spawnEntry.getEntityTag();
                Optional<EntityType<?>> _snowman3 = EntityType.fromTag(compoundTag);
                if (!_snowman3.isPresent()) {
                    this.updateSpawns();
                    return;
                }
                ListTag _snowman4 = compoundTag.getList("Pos", 6);
                int _snowman5 = _snowman4.size();
                double _snowman6 = _snowman5 >= 1 ? _snowman4.getDouble(0) : (double)_snowman2.getX() + (world.random.nextDouble() - world.random.nextDouble()) * (double)this.spawnRange + 0.5;
                double _snowman7 = _snowman5 >= 2 ? _snowman4.getDouble(1) : (double)(_snowman2.getY() + world.random.nextInt(3) - 1);
                double d = _snowman = _snowman5 >= 3 ? _snowman4.getDouble(2) : (double)_snowman2.getZ() + (world.random.nextDouble() - world.random.nextDouble()) * (double)this.spawnRange + 0.5;
                if (!world.isSpaceEmpty(_snowman3.get().createSimpleBoundingBox(_snowman6, _snowman7, _snowman))) continue;
                ServerWorld _snowman8 = (ServerWorld)world;
                if (!SpawnRestriction.canSpawn(_snowman3.get(), _snowman8, SpawnReason.SPAWNER, new BlockPos(_snowman6, _snowman7, _snowman), world.getRandom())) continue;
                Entity _snowman9 = EntityType.loadEntityWithPassengers(compoundTag, world, entity -> {
                    entity.refreshPositionAndAngles(_snowman6, _snowman7, _snowman, entity.yaw, entity.pitch);
                    return entity;
                });
                if (_snowman9 == null) {
                    this.updateSpawns();
                    return;
                }
                int _snowman10 = world.getNonSpectatingEntities(_snowman9.getClass(), new Box(_snowman2.getX(), _snowman2.getY(), _snowman2.getZ(), _snowman2.getX() + 1, _snowman2.getY() + 1, _snowman2.getZ() + 1).expand(this.spawnRange)).size();
                if (_snowman10 >= this.maxNearbyEntities) {
                    this.updateSpawns();
                    return;
                }
                _snowman9.refreshPositionAndAngles(_snowman9.getX(), _snowman9.getY(), _snowman9.getZ(), world.random.nextFloat() * 360.0f, 0.0f);
                if (_snowman9 instanceof MobEntity) {
                    MobEntity mobEntity = (MobEntity)_snowman9;
                    if (!mobEntity.canSpawn(world, SpawnReason.SPAWNER) || !mobEntity.canSpawn(world)) continue;
                    if (this.spawnEntry.getEntityTag().getSize() == 1 && this.spawnEntry.getEntityTag().contains("id", 8)) {
                        ((MobEntity)_snowman9).initialize(_snowman8, world.getLocalDifficulty(_snowman9.getBlockPos()), SpawnReason.SPAWNER, null, null);
                    }
                }
                if (!_snowman8.shouldCreateNewEntityWithPassenger(_snowman9)) {
                    this.updateSpawns();
                    return;
                }
                world.syncWorldEvent(2004, _snowman2, 0);
                if (_snowman9 instanceof MobEntity) {
                    ((MobEntity)_snowman9).playSpawnEffects();
                }
                _snowman11 = true;
            }
            if (_snowman11) {
                this.updateSpawns();
            }
        }
    }

    private void updateSpawns() {
        this.spawnDelay = this.maxSpawnDelay <= this.minSpawnDelay ? this.minSpawnDelay : this.minSpawnDelay + this.getWorld().random.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
        if (!this.spawnPotentials.isEmpty()) {
            this.setSpawnEntry(WeightedPicker.getRandom(this.getWorld().random, this.spawnPotentials));
        }
        this.sendStatus(1);
    }

    public void fromTag(CompoundTag tag) {
        this.spawnDelay = tag.getShort("Delay");
        this.spawnPotentials.clear();
        if (tag.contains("SpawnPotentials", 9)) {
            ListTag listTag = tag.getList("SpawnPotentials", 10);
            for (int i = 0; i < listTag.size(); ++i) {
                this.spawnPotentials.add(new MobSpawnerEntry(listTag.getCompound(i)));
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
        Identifier identifier = this.getEntityId();
        if (identifier == null) {
            return tag;
        }
        tag.putShort("Delay", (short)this.spawnDelay);
        tag.putShort("MinSpawnDelay", (short)this.minSpawnDelay);
        tag.putShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        tag.putShort("SpawnCount", (short)this.spawnCount);
        tag.putShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
        tag.putShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
        tag.putShort("SpawnRange", (short)this.spawnRange);
        tag.put("SpawnData", this.spawnEntry.getEntityTag().copy());
        ListTag _snowman2 = new ListTag();
        if (this.spawnPotentials.isEmpty()) {
            _snowman2.add(this.spawnEntry.serialize());
        } else {
            for (MobSpawnerEntry mobSpawnerEntry : this.spawnPotentials) {
                _snowman2.add(mobSpawnerEntry.serialize());
            }
        }
        tag.put("SpawnPotentials", _snowman2);
        return tag;
    }

    @Nullable
    public Entity getRenderedEntity() {
        if (this.renderedEntity == null) {
            this.renderedEntity = EntityType.loadEntityWithPassengers(this.spawnEntry.getEntityTag(), this.getWorld(), Function.identity());
            if (this.spawnEntry.getEntityTag().getSize() != 1 || !this.spawnEntry.getEntityTag().contains("id", 8) || this.renderedEntity instanceof MobEntity) {
                // empty if block
            }
        }
        return this.renderedEntity;
    }

    public boolean method_8275(int n) {
        if (n == 1 && this.getWorld().isClient) {
            this.spawnDelay = this.minSpawnDelay;
            return true;
        }
        return false;
    }

    public void setSpawnEntry(MobSpawnerEntry spawnEntry) {
        this.spawnEntry = spawnEntry;
    }

    public abstract void sendStatus(int var1);

    public abstract World getWorld();

    public abstract BlockPos getPos();

    public double method_8278() {
        return this.field_9161;
    }

    public double method_8279() {
        return this.field_9159;
    }
}

