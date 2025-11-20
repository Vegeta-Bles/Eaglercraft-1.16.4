/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;

public class SkeletonHorseTrapTriggerGoal
extends Goal {
    private final SkeletonHorseEntity skeletonHorse;

    public SkeletonHorseTrapTriggerGoal(SkeletonHorseEntity skeletonHorse) {
        this.skeletonHorse = skeletonHorse;
    }

    @Override
    public boolean canStart() {
        return this.skeletonHorse.world.isPlayerInRange(this.skeletonHorse.getX(), this.skeletonHorse.getY(), this.skeletonHorse.getZ(), 10.0);
    }

    @Override
    public void tick() {
        ServerWorld serverWorld = (ServerWorld)this.skeletonHorse.world;
        LocalDifficulty _snowman2 = serverWorld.getLocalDifficulty(this.skeletonHorse.getBlockPos());
        this.skeletonHorse.setTrapped(false);
        this.skeletonHorse.setTame(true);
        this.skeletonHorse.setBreedingAge(0);
        LightningEntity _snowman3 = EntityType.LIGHTNING_BOLT.create(serverWorld);
        _snowman3.refreshPositionAfterTeleport(this.skeletonHorse.getX(), this.skeletonHorse.getY(), this.skeletonHorse.getZ());
        _snowman3.setCosmetic(true);
        serverWorld.spawnEntity(_snowman3);
        SkeletonEntity _snowman4 = this.getSkeleton(_snowman2, this.skeletonHorse);
        _snowman4.startRiding(this.skeletonHorse);
        serverWorld.spawnEntityAndPassengers(_snowman4);
        for (int i = 0; i < 3; ++i) {
            HorseBaseEntity horseBaseEntity = this.getHorse(_snowman2);
            SkeletonEntity _snowman5 = this.getSkeleton(_snowman2, horseBaseEntity);
            _snowman5.startRiding(horseBaseEntity);
            horseBaseEntity.addVelocity(this.skeletonHorse.getRandom().nextGaussian() * 0.5, 0.0, this.skeletonHorse.getRandom().nextGaussian() * 0.5);
            serverWorld.spawnEntityAndPassengers(horseBaseEntity);
        }
    }

    private HorseBaseEntity getHorse(LocalDifficulty localDifficulty) {
        SkeletonHorseEntity skeletonHorseEntity = EntityType.SKELETON_HORSE.create(this.skeletonHorse.world);
        skeletonHorseEntity.initialize((ServerWorld)this.skeletonHorse.world, localDifficulty, SpawnReason.TRIGGERED, null, null);
        skeletonHorseEntity.updatePosition(this.skeletonHorse.getX(), this.skeletonHorse.getY(), this.skeletonHorse.getZ());
        skeletonHorseEntity.timeUntilRegen = 60;
        skeletonHorseEntity.setPersistent();
        skeletonHorseEntity.setTame(true);
        skeletonHorseEntity.setBreedingAge(0);
        return skeletonHorseEntity;
    }

    private SkeletonEntity getSkeleton(LocalDifficulty localDifficulty, HorseBaseEntity vehicle) {
        SkeletonEntity skeletonEntity = EntityType.SKELETON.create(vehicle.world);
        skeletonEntity.initialize((ServerWorld)vehicle.world, localDifficulty, SpawnReason.TRIGGERED, null, null);
        skeletonEntity.updatePosition(vehicle.getX(), vehicle.getY(), vehicle.getZ());
        skeletonEntity.timeUntilRegen = 60;
        skeletonEntity.setPersistent();
        if (skeletonEntity.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
            skeletonEntity.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        }
        skeletonEntity.equipStack(EquipmentSlot.MAINHAND, EnchantmentHelper.enchant(skeletonEntity.getRandom(), this.method_30768(skeletonEntity.getMainHandStack()), (int)(5.0f + localDifficulty.getClampedLocalDifficulty() * (float)skeletonEntity.getRandom().nextInt(18)), false));
        skeletonEntity.equipStack(EquipmentSlot.HEAD, EnchantmentHelper.enchant(skeletonEntity.getRandom(), this.method_30768(skeletonEntity.getEquippedStack(EquipmentSlot.HEAD)), (int)(5.0f + localDifficulty.getClampedLocalDifficulty() * (float)skeletonEntity.getRandom().nextInt(18)), false));
        return skeletonEntity;
    }

    private ItemStack method_30768(ItemStack itemStack) {
        itemStack.removeSubTag("Enchantments");
        return itemStack;
    }
}

