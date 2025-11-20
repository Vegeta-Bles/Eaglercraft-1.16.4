/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.passive;

import com.google.common.collect.Sets;
import java.util.HashSet;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Npc;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public abstract class MerchantEntity
extends PassiveEntity
implements Npc,
Merchant {
    private static final TrackedData<Integer> HEAD_ROLLING_TIME_LEFT = DataTracker.registerData(MerchantEntity.class, TrackedDataHandlerRegistry.INTEGER);
    @Nullable
    private PlayerEntity customer;
    @Nullable
    protected TradeOfferList offers;
    private final SimpleInventory inventory = new SimpleInventory(8);

    public MerchantEntity(EntityType<? extends MerchantEntity> entityType, World world) {
        super((EntityType<? extends PassiveEntity>)entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0f);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        if (entityData == null) {
            entityData = new PassiveEntity.PassiveData(false);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    public int getHeadRollingTimeLeft() {
        return this.dataTracker.get(HEAD_ROLLING_TIME_LEFT);
    }

    public void setHeadRollingTimeLeft(int ticks) {
        this.dataTracker.set(HEAD_ROLLING_TIME_LEFT, ticks);
    }

    @Override
    public int getExperience() {
        return 0;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        if (this.isBaby()) {
            return 0.81f;
        }
        return 1.62f;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HEAD_ROLLING_TIME_LEFT, 0);
    }

    @Override
    public void setCurrentCustomer(@Nullable PlayerEntity customer) {
        this.customer = customer;
    }

    @Override
    @Nullable
    public PlayerEntity getCurrentCustomer() {
        return this.customer;
    }

    public boolean hasCustomer() {
        return this.customer != null;
    }

    @Override
    public TradeOfferList getOffers() {
        if (this.offers == null) {
            this.offers = new TradeOfferList();
            this.fillRecipes();
        }
        return this.offers;
    }

    @Override
    public void setOffersFromServer(@Nullable TradeOfferList offers) {
    }

    @Override
    public void setExperienceFromServer(int experience) {
    }

    @Override
    public void trade(TradeOffer offer) {
        offer.use();
        this.ambientSoundChance = -this.getMinAmbientSoundDelay();
        this.afterUsing(offer);
        if (this.customer instanceof ServerPlayerEntity) {
            Criteria.VILLAGER_TRADE.handle((ServerPlayerEntity)this.customer, this, offer.getMutableSellItem());
        }
    }

    protected abstract void afterUsing(TradeOffer var1);

    @Override
    public boolean isLeveledMerchant() {
        return true;
    }

    @Override
    public void onSellingItem(ItemStack stack) {
        if (!this.world.isClient && this.ambientSoundChance > -this.getMinAmbientSoundDelay() + 20) {
            this.ambientSoundChance = -this.getMinAmbientSoundDelay();
            this.playSound(this.getTradingSound(!stack.isEmpty()), this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Override
    public SoundEvent getYesSound() {
        return SoundEvents.ENTITY_VILLAGER_YES;
    }

    protected SoundEvent getTradingSound(boolean sold) {
        return sold ? SoundEvents.ENTITY_VILLAGER_YES : SoundEvents.ENTITY_VILLAGER_NO;
    }

    public void playCelebrateSound() {
        this.playSound(SoundEvents.ENTITY_VILLAGER_CELEBRATE, this.getSoundVolume(), this.getSoundPitch());
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        TradeOfferList tradeOfferList = this.getOffers();
        if (!tradeOfferList.isEmpty()) {
            tag.put("Offers", tradeOfferList.toTag());
        }
        tag.put("Inventory", this.inventory.getTags());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains("Offers", 10)) {
            this.offers = new TradeOfferList(tag.getCompound("Offers"));
        }
        this.inventory.readTags(tag.getList("Inventory", 10));
    }

    @Override
    @Nullable
    public Entity moveToWorld(ServerWorld destination) {
        this.resetCustomer();
        return super.moveToWorld(destination);
    }

    protected void resetCustomer() {
        this.setCurrentCustomer(null);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        this.resetCustomer();
    }

    protected void produceParticles(ParticleEffect parameters) {
        for (int i = 0; i < 5; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            _snowman = this.random.nextGaussian() * 0.02;
            _snowman = this.random.nextGaussian() * 0.02;
            this.world.addParticle(parameters, this.getParticleX(1.0), this.getRandomBodyY() + 1.0, this.getParticleZ(1.0), d, _snowman, _snowman);
        }
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    public SimpleInventory getInventory() {
        return this.inventory;
    }

    @Override
    public boolean equip(int slot, ItemStack item) {
        if (super.equip(slot, item)) {
            return true;
        }
        int n = slot - 300;
        if (n >= 0 && n < this.inventory.size()) {
            this.inventory.setStack(n, item);
            return true;
        }
        return false;
    }

    @Override
    public World getMerchantWorld() {
        return this.world;
    }

    protected abstract void fillRecipes();

    protected void fillRecipesFromPool(TradeOfferList recipeList, TradeOffers.Factory[] pool, int count) {
        HashSet hashSet = Sets.newHashSet();
        if (pool.length > count) {
            while (hashSet.size() < count) {
                hashSet.add(this.random.nextInt(pool.length));
            }
        } else {
            for (int i = 0; i < pool.length; ++i) {
                hashSet.add(i);
            }
        }
        for (Integer _snowman2 : hashSet) {
            TradeOffers.Factory factory = pool[_snowman2];
            TradeOffer _snowman3 = factory.create(this, this.random);
            if (_snowman3 == null) continue;
            recipeList.add(_snowman3);
        }
    }

    @Override
    public Vec3d method_30951(float f) {
        _snowman = MathHelper.lerp(f, this.prevBodyYaw, this.bodyYaw) * ((float)Math.PI / 180);
        Vec3d vec3d = new Vec3d(0.0, this.getBoundingBox().getYLength() - 1.0, 0.2);
        return this.method_30950(f).add(vec3d.rotateY(-_snowman));
    }
}

