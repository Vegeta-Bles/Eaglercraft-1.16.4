/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 */
package net.minecraft.entity.ai.brain.sensor;

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
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;

public class HoglinSpecificSensor
extends Sensor<HoglinEntity> {
    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_REPELLENT, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, (Object[])new MemoryModuleType[0]);
    }

    @Override
    protected void sense(ServerWorld serverWorld, HoglinEntity hoglinEntity) {
        Brain<HoglinEntity> brain = hoglinEntity.getBrain();
        brain.remember(MemoryModuleType.NEAREST_REPELLENT, this.findNearestWarpedFungus(serverWorld, hoglinEntity));
        Optional<Object> _snowman2 = Optional.empty();
        int _snowman3 = 0;
        ArrayList _snowman4 = Lists.newArrayList();
        List<LivingEntity> _snowman5 = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(Lists.newArrayList());
        for (LivingEntity livingEntity : _snowman5) {
            if (livingEntity instanceof PiglinEntity && !livingEntity.isBaby()) {
                ++_snowman3;
                if (!_snowman2.isPresent()) {
                    _snowman2 = Optional.of((PiglinEntity)livingEntity);
                }
            }
            if (!(livingEntity instanceof HoglinEntity) || livingEntity.isBaby()) continue;
            _snowman4.add((HoglinEntity)livingEntity);
        }
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, _snowman2);
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, _snowman4);
        brain.remember(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, _snowman3);
        brain.remember(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, _snowman4.size());
    }

    private Optional<BlockPos> findNearestWarpedFungus(ServerWorld world, HoglinEntity hoglin) {
        return BlockPos.findClosest(hoglin.getBlockPos(), 8, 4, blockPos -> world.getBlockState((BlockPos)blockPos).isIn(BlockTags.HOGLIN_REPELLENTS));
    }
}

