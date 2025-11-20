/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;

public class GolemLastSeenSensor
extends Sensor<LivingEntity> {
    public GolemLastSeenSensor() {
        this(200);
    }

    public GolemLastSeenSensor(int n) {
        super(n);
    }

    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {
        GolemLastSeenSensor.senseIronGolem(entity);
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.MOBS);
    }

    public static void senseIronGolem(LivingEntity livingEntity2) {
        Optional<List<LivingEntity>> optional = livingEntity2.getBrain().getOptionalMemory(MemoryModuleType.MOBS);
        if (!optional.isPresent()) {
            return;
        }
        boolean _snowman2 = optional.get().stream().anyMatch(livingEntity -> livingEntity.getType().equals(EntityType.IRON_GOLEM));
        if (_snowman2) {
            GolemLastSeenSensor.method_30233(livingEntity2);
        }
    }

    public static void method_30233(LivingEntity livingEntity) {
        livingEntity.getBrain().remember(MemoryModuleType.GOLEM_DETECTED_RECENTLY, true, 600L);
    }
}

