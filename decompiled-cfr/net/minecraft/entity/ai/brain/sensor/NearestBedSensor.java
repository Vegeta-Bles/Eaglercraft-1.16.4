/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  it.unimi.dsi.fastutil.longs.Long2LongMap
 *  it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class NearestBedSensor
extends Sensor<MobEntity> {
    private final Long2LongMap positionToExpiryTime = new Long2LongOpenHashMap();
    private int tries;
    private long expiryTime;

    public NearestBedSensor() {
        super(20);
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_BED);
    }

    @Override
    protected void sense(ServerWorld serverWorld, MobEntity mobEntity) {
        if (!mobEntity.isBaby()) {
            return;
        }
        this.tries = 0;
        this.expiryTime = serverWorld.getTime() + (long)serverWorld.getRandom().nextInt(20);
        PointOfInterestStorage pointOfInterestStorage = serverWorld.getPointOfInterestStorage();
        Predicate<BlockPos> _snowman2 = blockPos -> {
            long l = blockPos.asLong();
            if (this.positionToExpiryTime.containsKey(l)) {
                return false;
            }
            if (++this.tries >= 5) {
                return false;
            }
            this.positionToExpiryTime.put(l, this.expiryTime + 40L);
            return true;
        };
        Stream<BlockPos> _snowman3 = pointOfInterestStorage.getPositions(PointOfInterestType.HOME.getCompletionCondition(), _snowman2, mobEntity.getBlockPos(), 48, PointOfInterestStorage.OccupationStatus.ANY);
        Path _snowman4 = mobEntity.getNavigation().findPathToAny(_snowman3, PointOfInterestType.HOME.getSearchDistance());
        if (_snowman4 != null && _snowman4.reachesTarget()) {
            BlockPos blockPos2 = _snowman4.getTarget();
            Optional<PointOfInterestType> _snowman5 = pointOfInterestStorage.getType(blockPos2);
            if (_snowman5.isPresent()) {
                mobEntity.getBrain().remember(MemoryModuleType.NEAREST_BED, blockPos2);
            }
        } else if (this.tries < 5) {
            this.positionToExpiryTime.long2LongEntrySet().removeIf(entry -> entry.getLongValue() < this.expiryTime);
        }
    }
}

