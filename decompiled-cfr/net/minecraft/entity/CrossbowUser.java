/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

public interface CrossbowUser
extends RangedAttackMob {
    public void setCharging(boolean var1);

    public void shoot(LivingEntity var1, ItemStack var2, ProjectileEntity var3, float var4);

    @Nullable
    public LivingEntity getTarget();

    public void postShoot();

    default public void shoot(LivingEntity entity, float speed) {
        Hand hand = ProjectileUtil.getHandPossiblyHolding(entity, Items.CROSSBOW);
        ItemStack _snowman2 = entity.getStackInHand(hand);
        if (entity.isHolding(Items.CROSSBOW)) {
            CrossbowItem.shootAll(entity.world, entity, hand, _snowman2, speed, 14 - entity.world.getDifficulty().getId() * 4);
        }
        this.postShoot();
    }

    default public void shoot(LivingEntity entity, LivingEntity target, ProjectileEntity projectile, float multishotSpray, float speed) {
        ProjectileEntity projectileEntity = projectile;
        double _snowman2 = target.getX() - entity.getX();
        double _snowman3 = target.getZ() - entity.getZ();
        double _snowman4 = MathHelper.sqrt(_snowman2 * _snowman2 + _snowman3 * _snowman3);
        double _snowman5 = target.getBodyY(0.3333333333333333) - projectileEntity.getY() + _snowman4 * (double)0.2f;
        Vector3f _snowman6 = this.getProjectileLaunchVelocity(entity, new Vec3d(_snowman2, _snowman5, _snowman3), multishotSpray);
        projectile.setVelocity(_snowman6.getX(), _snowman6.getY(), _snowman6.getZ(), speed, 14 - entity.world.getDifficulty().getId() * 4);
        entity.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0f, 1.0f / (entity.getRandom().nextFloat() * 0.4f + 0.8f));
    }

    default public Vector3f getProjectileLaunchVelocity(LivingEntity entity, Vec3d positionDelta, float multishotSpray) {
        Vec3d vec3d = positionDelta.normalize();
        _snowman = vec3d.crossProduct(new Vec3d(0.0, 1.0, 0.0));
        if (_snowman.lengthSquared() <= 1.0E-7) {
            _snowman = vec3d.crossProduct(entity.getOppositeRotationVector(1.0f));
        }
        Quaternion _snowman2 = new Quaternion(new Vector3f(_snowman), 90.0f, true);
        Vector3f _snowman3 = new Vector3f(vec3d);
        _snowman3.rotate(_snowman2);
        Quaternion _snowman4 = new Quaternion(_snowman3, multishotSpray, true);
        Vector3f _snowman5 = new Vector3f(vec3d);
        _snowman5.rotate(_snowman4);
        return _snowman5;
    }
}

