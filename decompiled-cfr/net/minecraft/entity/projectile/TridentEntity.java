/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TridentEntity
extends PersistentProjectileEntity {
    private static final TrackedData<Byte> LOYALTY = DataTracker.registerData(TridentEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(TridentEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private ItemStack tridentStack = new ItemStack(Items.TRIDENT);
    private boolean dealtDamage;
    public int returnTimer;

    public TridentEntity(EntityType<? extends TridentEntity> entityType, World world) {
        super((EntityType<? extends PersistentProjectileEntity>)entityType, world);
    }

    public TridentEntity(World world, LivingEntity owner, ItemStack stack) {
        super(EntityType.TRIDENT, owner, world);
        this.tridentStack = stack.copy();
        this.dataTracker.set(LOYALTY, (byte)EnchantmentHelper.getLoyalty(stack));
        this.dataTracker.set(ENCHANTED, stack.hasGlint());
    }

    public TridentEntity(World world, double x, double y, double z) {
        super(EntityType.TRIDENT, x, y, z, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(LOYALTY, (byte)0);
        this.dataTracker.startTracking(ENCHANTED, false);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }
        Entity entity = this.getOwner();
        if ((this.dealtDamage || this.isNoClip()) && entity != null) {
            byte by = this.dataTracker.get(LOYALTY);
            if (by > 0 && !this.isOwnerAlive()) {
                if (!this.world.isClient && this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1f);
                }
                this.remove();
            } else if (by > 0) {
                this.setNoClip(true);
                Vec3d vec3d = new Vec3d(entity.getX() - this.getX(), entity.getEyeY() - this.getY(), entity.getZ() - this.getZ());
                this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * (double)by, this.getZ());
                if (this.world.isClient) {
                    this.lastRenderY = this.getY();
                }
                double _snowman2 = 0.05 * (double)by;
                this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(_snowman2)));
                if (this.returnTimer == 0) {
                    this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0f, 1.0f);
                }
                ++this.returnTimer;
            }
        }
        super.tick();
    }

    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity == null || !entity.isAlive()) {
            return false;
        }
        return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
    }

    @Override
    protected ItemStack asItemStack() {
        return this.tridentStack.copy();
    }

    public boolean isEnchanted() {
        return this.dataTracker.get(ENCHANTED);
    }

    @Override
    @Nullable
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        if (this.dealtDamage) {
            return null;
        }
        return super.getEntityCollision(currentPosition, nextPosition);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float _snowman2 = 8.0f;
        if (entity instanceof LivingEntity) {
            _snowman = (LivingEntity)entity;
            _snowman2 += EnchantmentHelper.getAttackDamage(this.tridentStack, ((LivingEntity)_snowman).getGroup());
        }
        DamageSource _snowman3 = DamageSource.trident(this, (_snowman = this.getOwner()) == null ? this : _snowman);
        this.dealtDamage = true;
        SoundEvent _snowman4 = SoundEvents.ITEM_TRIDENT_HIT;
        if (entity.damage(_snowman3, _snowman2)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                if (_snowman instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, _snowman);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)_snowman, livingEntity);
                }
                this.onHit(livingEntity);
            }
        }
        this.setVelocity(this.getVelocity().multiply(-0.01, -0.1, -0.01));
        float _snowman5 = 1.0f;
        if (this.world instanceof ServerWorld && this.world.isThundering() && EnchantmentHelper.hasChanneling(this.tridentStack) && this.world.isSkyVisible(_snowman = entity.getBlockPos())) {
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this.world);
            lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(_snowman));
            lightningEntity.setChanneler(_snowman instanceof ServerPlayerEntity ? (ServerPlayerEntity)_snowman : null);
            this.world.spawnEntity(lightningEntity);
            _snowman4 = SoundEvents.ITEM_TRIDENT_THUNDER;
            _snowman5 = 5.0f;
        }
        this.playSound(_snowman4, _snowman5, 1.0f);
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        Entity entity = this.getOwner();
        if (entity != null && entity.getUuid() != player.getUuid()) {
            return;
        }
        super.onPlayerCollision(player);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains("Trident", 10)) {
            this.tridentStack = ItemStack.fromTag(tag.getCompound("Trident"));
        }
        this.dealtDamage = tag.getBoolean("DealtDamage");
        this.dataTracker.set(LOYALTY, (byte)EnchantmentHelper.getLoyalty(this.tridentStack));
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("Trident", this.tridentStack.toTag(new CompoundTag()));
        tag.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    public void age() {
        byte by = this.dataTracker.get(LOYALTY);
        if (this.pickupType != PersistentProjectileEntity.PickupPermission.ALLOWED || by <= 0) {
            super.age();
        }
    }

    @Override
    protected float getDragInWater() {
        return 0.99f;
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }
}

