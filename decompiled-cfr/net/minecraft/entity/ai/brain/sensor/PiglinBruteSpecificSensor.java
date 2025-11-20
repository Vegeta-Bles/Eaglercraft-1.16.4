/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.server.world.ServerWorld;

public class PiglinBruteSpecificSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEARBY_ADULT_PIGLINS);
    }

    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {
        Brain<?> brain = entity.getBrain();
        Optional<Object> _snowman2 = Optional.empty();
        ArrayList _snowman3 = Lists.newArrayList();
        List<LivingEntity> _snowman4 = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse((List<LivingEntity>)ImmutableList.of());
        for (LivingEntity livingEntity : _snowman4) {
            if (!(livingEntity instanceof WitherSkeletonEntity) && !(livingEntity instanceof WitherEntity)) continue;
            _snowman2 = Optional.of((MobEntity)livingEntity);
            break;
        }
        List<LivingEntity> _snowman5 = brain.getOptionalMemory(MemoryModuleType.MOBS).orElse((List<LivingEntity>)ImmutableList.of());
        for (LivingEntity livingEntity : _snowman5) {
            if (!(livingEntity instanceof AbstractPiglinEntity) || !((AbstractPiglinEntity)livingEntity).isAdult()) continue;
            _snowman3.add((AbstractPiglinEntity)livingEntity);
        }
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, _snowman2);
        brain.remember(MemoryModuleType.NEARBY_ADULT_PIGLINS, _snowman3);
    }
}

