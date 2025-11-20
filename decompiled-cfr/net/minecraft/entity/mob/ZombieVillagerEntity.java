/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity.mob;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;

public class ZombieVillagerEntity
extends ZombieEntity
implements VillagerDataContainer {
    private static final TrackedData<Boolean> CONVERTING = DataTracker.registerData(ZombieVillagerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<VillagerData> VILLAGER_DATA = DataTracker.registerData(ZombieVillagerEntity.class, TrackedDataHandlerRegistry.VILLAGER_DATA);
    private int conversionTimer;
    private UUID converter;
    private Tag gossipData;
    private CompoundTag offerData;
    private int xp;

    public ZombieVillagerEntity(EntityType<? extends ZombieVillagerEntity> entityType, World world) {
        super((EntityType<? extends ZombieEntity>)entityType, world);
        this.setVillagerData(this.getVillagerData().withProfession(Registry.VILLAGER_PROFESSION.getRandom(this.random)));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CONVERTING, false);
        this.dataTracker.startTracking(VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag2) {
        super.writeCustomDataToTag(tag2);
        VillagerData.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, (Object)this.getVillagerData()).resultOrPartial(arg_0 -> ((Logger)LOGGER).error(arg_0)).ifPresent(tag -> tag2.put("VillagerData", (Tag)tag));
        if (this.offerData != null) {
            tag2.put("Offers", this.offerData);
        }
        if (this.gossipData != null) {
            tag2.put("Gossips", this.gossipData);
        }
        tag2.putInt("ConversionTime", this.isConverting() ? this.conversionTimer : -1);
        if (this.converter != null) {
            tag2.putUuid("ConversionPlayer", this.converter);
        }
        tag2.putInt("Xp", this.xp);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains("VillagerData", 10)) {
            DataResult dataResult = VillagerData.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, (Object)tag.get("VillagerData")));
            dataResult.resultOrPartial(arg_0 -> ((Logger)LOGGER).error(arg_0)).ifPresent(this::setVillagerData);
        }
        if (tag.contains("Offers", 10)) {
            this.offerData = tag.getCompound("Offers");
        }
        if (tag.contains("Gossips", 10)) {
            this.gossipData = tag.getList("Gossips", 10);
        }
        if (tag.contains("ConversionTime", 99) && tag.getInt("ConversionTime") > -1) {
            this.setConverting(tag.containsUuid("ConversionPlayer") ? tag.getUuid("ConversionPlayer") : null, tag.getInt("ConversionTime"));
        }
        if (tag.contains("Xp", 3)) {
            this.xp = tag.getInt("Xp");
        }
    }

    @Override
    public void tick() {
        if (!this.world.isClient && this.isAlive() && this.isConverting()) {
            int n = this.getConversionRate();
            this.conversionTimer -= n;
            if (this.conversionTimer <= 0) {
                this.finishConversion((ServerWorld)this.world);
            }
        }
        super.tick();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.GOLDEN_APPLE) {
            if (this.hasStatusEffect(StatusEffects.WEAKNESS)) {
                if (!player.abilities.creativeMode) {
                    itemStack.decrement(1);
                }
                if (!this.world.isClient) {
                    this.setConverting(player.getUuid(), this.random.nextInt(2401) + 3600);
                }
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected boolean canConvertInWater() {
        return false;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isConverting() && this.xp == 0;
    }

    public boolean isConverting() {
        return this.getDataTracker().get(CONVERTING);
    }

    private void setConverting(@Nullable UUID uuid, int delay) {
        this.converter = uuid;
        this.conversionTimer = delay;
        this.getDataTracker().set(CONVERTING, true);
        this.removeStatusEffect(StatusEffects.WEAKNESS);
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, delay, Math.min(this.world.getDifficulty().getId() - 1, 0)));
        this.world.sendEntityStatus(this, (byte)16);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 16) {
            if (!this.isSilent()) {
                this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, this.getSoundCategory(), 1.0f + this.random.nextFloat(), this.random.nextFloat() * 0.7f + 0.3f, false);
            }
            return;
        }
        super.handleStatus(status);
    }

    private void finishConversion(ServerWorld world) {
        VillagerEntity villagerEntity = this.method_29243(EntityType.VILLAGER, false);
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = this.getEquippedStack(equipmentSlot);
            if (itemStack.isEmpty()) continue;
            if (EnchantmentHelper.hasBindingCurse(itemStack)) {
                villagerEntity.equip(equipmentSlot.getEntitySlotId() + 300, itemStack);
                continue;
            }
            double _snowman2 = this.getDropChance(equipmentSlot);
            if (!(_snowman2 > 1.0)) continue;
            this.dropStack(itemStack);
        }
        villagerEntity.setVillagerData(this.getVillagerData());
        if (this.gossipData != null) {
            villagerEntity.setGossipDataFromTag(this.gossipData);
        }
        if (this.offerData != null) {
            villagerEntity.setOffers(new TradeOfferList(this.offerData));
        }
        villagerEntity.setExperience(this.xp);
        villagerEntity.initialize(world, world.getLocalDifficulty(villagerEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
        if (this.converter != null && (_snowman = world.getPlayerByUuid(this.converter)) instanceof ServerPlayerEntity) {
            Criteria.CURED_ZOMBIE_VILLAGER.trigger((ServerPlayerEntity)_snowman, this, villagerEntity);
            world.handleInteraction(EntityInteraction.ZOMBIE_VILLAGER_CURED, _snowman, villagerEntity);
        }
        villagerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
        if (!this.isSilent()) {
            world.syncWorldEvent(null, 1027, this.getBlockPos(), 0);
        }
    }

    private int getConversionRate() {
        int n = 1;
        if (this.random.nextFloat() < 0.01f) {
            _snowman = 0;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = (int)this.getX() - 4; i < (int)this.getX() + 4 && _snowman < 14; ++i) {
                for (_snowman = (int)this.getY() - 4; _snowman < (int)this.getY() + 4 && _snowman < 14; ++_snowman) {
                    for (_snowman = (int)this.getZ() - 4; _snowman < (int)this.getZ() + 4 && _snowman < 14; ++_snowman) {
                        Block block = this.world.getBlockState(mutable.set(i, _snowman, _snowman)).getBlock();
                        if (block != Blocks.IRON_BARS && !(block instanceof BedBlock)) continue;
                        if (this.random.nextFloat() < 0.3f) {
                            ++n;
                        }
                        ++_snowman;
                    }
                }
            }
        }
        return n;
    }

    @Override
    protected float getSoundPitch() {
        if (this.isBaby()) {
            return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 2.0f;
        }
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_DEATH;
    }

    @Override
    public SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP;
    }

    @Override
    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }

    public void setOfferData(CompoundTag offerTag) {
        this.offerData = offerTag;
    }

    public void setGossipData(Tag gossipTag) {
        this.gossipData = gossipTag;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        this.setVillagerData(this.getVillagerData().withType(VillagerType.forBiome(world.method_31081(this.getBlockPos()))));
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    public void setVillagerData(VillagerData data) {
        VillagerData villagerData = this.getVillagerData();
        if (villagerData.getProfession() != data.getProfession()) {
            this.offerData = null;
        }
        this.dataTracker.set(VILLAGER_DATA, data);
    }

    @Override
    public VillagerData getVillagerData() {
        return this.dataTracker.get(VILLAGER_DATA);
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}

