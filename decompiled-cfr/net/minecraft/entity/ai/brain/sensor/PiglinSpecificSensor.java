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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;

public class PiglinSpecificSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.MOBS, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, (Object[])new MemoryModuleType[]{MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEARBY_ADULT_PIGLINS, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryModuleType.NEAREST_REPELLENT});
    }

    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {
        Brain<?> brain = entity.getBrain();
        brain.remember(MemoryModuleType.NEAREST_REPELLENT, PiglinSpecificSensor.findSoulFire(world, entity));
        Optional<Object> _snowman2 = Optional.empty();
        Optional<Object> _snowman3 = Optional.empty();
        Optional<Object> _snowman4 = Optional.empty();
        Optional<Object> _snowman5 = Optional.empty();
        Optional<Object> _snowman6 = Optional.empty();
        Optional<Object> _snowman7 = Optional.empty();
        Optional<Object> _snowman8 = Optional.empty();
        int _snowman9 = 0;
        ArrayList _snowman10 = Lists.newArrayList();
        ArrayList _snowman11 = Lists.newArrayList();
        List<LivingEntity> _snowman12 = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse((List<LivingEntity>)ImmutableList.of());
        for (LivingEntity livingEntity : _snowman12) {
            if (livingEntity instanceof HoglinEntity) {
                livingEntity = (HoglinEntity)livingEntity;
                if (((PassiveEntity)livingEntity).isBaby() && !_snowman4.isPresent()) {
                    _snowman4 = Optional.of(livingEntity);
                    continue;
                }
                if (!((HoglinEntity)livingEntity).isAdult()) continue;
                ++_snowman9;
                if (_snowman3.isPresent() || !((HoglinEntity)livingEntity).canBeHunted()) continue;
                _snowman3 = Optional.of(livingEntity);
                continue;
            }
            if (livingEntity instanceof PiglinBruteEntity) {
                _snowman10.add((PiglinBruteEntity)livingEntity);
                continue;
            }
            if (livingEntity instanceof PiglinEntity) {
                livingEntity = (PiglinEntity)livingEntity;
                if (((PiglinEntity)livingEntity).isBaby() && !_snowman5.isPresent()) {
                    _snowman5 = Optional.of(livingEntity);
                    continue;
                }
                if (!((AbstractPiglinEntity)livingEntity).isAdult()) continue;
                _snowman10.add(livingEntity);
                continue;
            }
            if (livingEntity instanceof PlayerEntity) {
                livingEntity = (PlayerEntity)livingEntity;
                if (!_snowman7.isPresent() && EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(livingEntity) && !PiglinBrain.wearsGoldArmor(livingEntity)) {
                    _snowman7 = Optional.of(livingEntity);
                }
                if (_snowman8.isPresent() || ((PlayerEntity)livingEntity).isSpectator() || !PiglinBrain.isGoldHoldingPlayer(livingEntity)) continue;
                _snowman8 = Optional.of(livingEntity);
                continue;
            }
            if (!_snowman2.isPresent() && (livingEntity instanceof WitherSkeletonEntity || livingEntity instanceof WitherEntity)) {
                _snowman2 = Optional.of((MobEntity)livingEntity);
                continue;
            }
            if (_snowman6.isPresent() || !PiglinBrain.isZombified(livingEntity.getType())) continue;
            _snowman6 = Optional.of(livingEntity);
        }
        List<LivingEntity> _snowman13 = brain.getOptionalMemory(MemoryModuleType.MOBS).orElse((List<LivingEntity>)ImmutableList.of());
        for (LivingEntity livingEntity : _snowman13) {
            if (!(livingEntity instanceof AbstractPiglinEntity) || !((AbstractPiglinEntity)livingEntity).isAdult()) continue;
            _snowman11.add((AbstractPiglinEntity)livingEntity);
        }
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, _snowman2);
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, _snowman3);
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, _snowman4);
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, _snowman6);
        brain.remember(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, _snowman7);
        brain.remember(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, _snowman8);
        brain.remember(MemoryModuleType.NEARBY_ADULT_PIGLINS, _snowman11);
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, _snowman10);
        brain.remember(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, _snowman10.size());
        brain.remember(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, _snowman9);
    }

    private static Optional<BlockPos> findSoulFire(ServerWorld world, LivingEntity entity) {
        return BlockPos.findClosest(entity.getBlockPos(), 8, 4, blockPos -> PiglinSpecificSensor.method_24648(world, blockPos));
    }

    private static boolean method_24648(ServerWorld serverWorld, BlockPos blockPos) {
        BlockState blockState = serverWorld.getBlockState(blockPos);
        boolean _snowman2 = blockState.isIn(BlockTags.PIGLIN_REPELLENTS);
        if (_snowman2 && blockState.isOf(Blocks.SOUL_CAMPFIRE)) {
            return CampfireBlock.isLitCampfire(blockState);
        }
        return _snowman2;
    }
}

