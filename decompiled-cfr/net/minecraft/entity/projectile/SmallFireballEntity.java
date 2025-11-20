/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.projectile;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SmallFireballEntity
extends AbstractFireballEntity {
    public SmallFireballEntity(EntityType<? extends SmallFireballEntity> entityType, World world) {
        super((EntityType<? extends AbstractFireballEntity>)entityType, world);
    }

    public SmallFireballEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super((EntityType<? extends AbstractFireballEntity>)EntityType.SMALL_FIREBALL, owner, velocityX, velocityY, velocityZ, world);
    }

    public SmallFireballEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super((EntityType<? extends AbstractFireballEntity>)EntityType.SMALL_FIREBALL, x, y, z, velocityX, velocityY, velocityZ, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.world.isClient) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        if (!entity.isFireImmune()) {
            _snowman = this.getOwner();
            int n = entity.getFireTicks();
            entity.setOnFireFor(5);
            boolean _snowman2 = entity.damage(DamageSource.fireball(this, _snowman), 5.0f);
            if (!_snowman2) {
                entity.setFireTicks(n);
            } else if (_snowman instanceof LivingEntity) {
                this.dealDamage((LivingEntity)_snowman, entity);
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (this.world.isClient) {
            return;
        }
        Entity entity = this.getOwner();
        if ((entity == null || !(entity instanceof MobEntity) || this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && this.world.isAir(_snowman = (_snowman = blockHitResult).getBlockPos().offset(_snowman.getSide()))) {
            this.world.setBlockState(_snowman, AbstractFireBlock.getState(this.world, _snowman));
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
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
}

