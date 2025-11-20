/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class WitherSkullEntity
extends ExplosiveProjectileEntity {
    private static final TrackedData<Boolean> CHARGED = DataTracker.registerData(WitherSkullEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public WitherSkullEntity(EntityType<? extends WitherSkullEntity> entityType, World world) {
        super((EntityType<? extends ExplosiveProjectileEntity>)entityType, world);
    }

    public WitherSkullEntity(World world, LivingEntity owner, double directionX, double directionY, double directionZ) {
        super(EntityType.WITHER_SKULL, owner, directionX, directionY, directionZ, world);
    }

    public WitherSkullEntity(World world, double x, double y, double z, double directionX, double directionY, double directionZ) {
        super(EntityType.WITHER_SKULL, x, y, z, directionX, directionY, directionZ, world);
    }

    @Override
    protected float getDrag() {
        return this.isCharged() ? 0.73f : super.getDrag();
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public float getEffectiveExplosionResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState, float max) {
        if (this.isCharged() && WitherEntity.canDestroy(blockState)) {
            return Math.min(0.8f, max);
        }
        return max;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        boolean _snowman2;
        super.onEntityHit(entityHitResult);
        if (this.world.isClient) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        _snowman = this.getOwner();
        if (_snowman instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)_snowman;
            _snowman2 = entity.damage(DamageSource.witherSkull(this, livingEntity), 8.0f);
            if (_snowman2) {
                if (entity.isAlive()) {
                    this.dealDamage(livingEntity, entity);
                } else {
                    livingEntity.heal(5.0f);
                }
            }
        } else {
            _snowman2 = entity.damage(DamageSource.MAGIC, 5.0f);
        }
        if (_snowman2 && entity instanceof LivingEntity) {
            int _snowman3 = 0;
            if (this.world.getDifficulty() == Difficulty.NORMAL) {
                _snowman3 = 10;
            } else if (this.world.getDifficulty() == Difficulty.HARD) {
                _snowman3 = 40;
            }
            if (_snowman3 > 0) {
                ((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20 * _snowman3, 1));
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 1.0f, false, destructionType);
            this.remove();
        }
    }

    @Override
    public boolean collides() {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(CHARGED, false);
    }

    public boolean isCharged() {
        return this.dataTracker.get(CHARGED);
    }

    public void setCharged(boolean charged) {
        this.dataTracker.set(CHARGED, charged);
    }

    @Override
    protected boolean isBurning() {
        return false;
    }
}

