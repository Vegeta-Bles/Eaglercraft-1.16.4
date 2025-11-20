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
import java.util.Set;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class SecondaryPointsOfInterestSensor
extends Sensor<VillagerEntity> {
    public SecondaryPointsOfInterestSensor() {
        super(40);
    }

    @Override
    protected void sense(ServerWorld serverWorld, VillagerEntity villagerEntity2) {
        VillagerEntity villagerEntity2;
        RegistryKey<World> registryKey = serverWorld.getRegistryKey();
        BlockPos _snowman2 = villagerEntity2.getBlockPos();
        ArrayList _snowman3 = Lists.newArrayList();
        int _snowman4 = 4;
        for (int i = -4; i <= 4; ++i) {
            for (_snowman = -2; _snowman <= 2; ++_snowman) {
                for (_snowman = -4; _snowman <= 4; ++_snowman) {
                    BlockPos blockPos = _snowman2.add(i, _snowman, _snowman);
                    if (!villagerEntity2.getVillagerData().getProfession().getSecondaryJobSites().contains((Object)serverWorld.getBlockState(blockPos).getBlock())) continue;
                    _snowman3.add(GlobalPos.create(registryKey, blockPos));
                }
            }
        }
        Brain<VillagerEntity> _snowman5 = villagerEntity2.getBrain();
        if (!_snowman3.isEmpty()) {
            _snowman5.remember(MemoryModuleType.SECONDARY_JOB_SITE, _snowman3);
        } else {
            _snowman5.forget(MemoryModuleType.SECONDARY_JOB_SITE);
        }
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.SECONDARY_JOB_SITE);
    }
}

