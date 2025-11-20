/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SeekSkyTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;

public class CelebrateRaidWinTask
extends Task<VillagerEntity> {
    @Nullable
    private Raid raid;

    public CelebrateRaidWinTask(int minRunTime, int maxRunTime) {
        super((Map<MemoryModuleType<?>, MemoryModuleState>)ImmutableMap.of(), minRunTime, maxRunTime);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        BlockPos blockPos = villagerEntity.getBlockPos();
        this.raid = serverWorld.getRaidAt(blockPos);
        return this.raid != null && this.raid.hasWon() && SeekSkyTask.isSkyVisible(serverWorld, villagerEntity, blockPos);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return this.raid != null && !this.raid.hasStopped();
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        this.raid = null;
        villagerEntity.getBrain().refreshActivities(serverWorld.getTimeOfDay(), serverWorld.getTime());
    }

    @Override
    protected void keepRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        Random random = villagerEntity.getRandom();
        if (random.nextInt(100) == 0) {
            villagerEntity.playCelebrateSound();
        }
        if (random.nextInt(200) == 0 && SeekSkyTask.isSkyVisible(serverWorld, villagerEntity, villagerEntity.getBlockPos())) {
            DyeColor dyeColor = Util.getRandom(DyeColor.values(), random);
            int _snowman2 = random.nextInt(3);
            ItemStack _snowman3 = this.createFirework(dyeColor, _snowman2);
            FireworkRocketEntity _snowman4 = new FireworkRocketEntity(villagerEntity.world, villagerEntity, villagerEntity.getX(), villagerEntity.getEyeY(), villagerEntity.getZ(), _snowman3);
            villagerEntity.world.spawnEntity(_snowman4);
        }
    }

    private ItemStack createFirework(DyeColor color, int flight) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET, 1);
        _snowman = new ItemStack(Items.FIREWORK_STAR);
        CompoundTag _snowman2 = _snowman.getOrCreateSubTag("Explosion");
        ArrayList _snowman3 = Lists.newArrayList();
        _snowman3.add(color.getFireworkColor());
        _snowman2.putIntArray("Colors", _snowman3);
        _snowman2.putByte("Type", (byte)FireworkItem.Type.BURST.getId());
        CompoundTag _snowman4 = itemStack.getOrCreateSubTag("Fireworks");
        ListTag _snowman5 = new ListTag();
        CompoundTag _snowman6 = _snowman.getSubTag("Explosion");
        if (_snowman6 != null) {
            _snowman5.add(_snowman6);
        }
        _snowman4.putByte("Flight", (byte)flight);
        if (!_snowman5.isEmpty()) {
            _snowman4.put("Explosions", _snowman5);
        }
        return itemStack;
    }

    @Override
    protected /* synthetic */ boolean shouldKeepRunning(ServerWorld world, LivingEntity entity, long time) {
        return this.shouldKeepRunning(world, (VillagerEntity)entity, time);
    }

    @Override
    protected /* synthetic */ void finishRunning(ServerWorld world, LivingEntity entity, long time) {
        this.finishRunning(world, (VillagerEntity)entity, time);
    }

    @Override
    protected /* synthetic */ void keepRunning(ServerWorld world, LivingEntity entity, long time) {
        this.keepRunning(world, (VillagerEntity)entity, time);
    }
}

