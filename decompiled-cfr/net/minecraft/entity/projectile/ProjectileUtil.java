/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.projectile;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public final class ProjectileUtil {
    public static HitResult getCollision(Entity entity, Predicate<Entity> predicate) {
        Vec3d vec3d = entity.getVelocity();
        World _snowman2 = entity.world;
        _snowman = entity.getPos();
        HitResult _snowman3 = _snowman2.raycast(new RaycastContext(_snowman, _snowman = _snowman.add(vec3d), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
        if (((HitResult)_snowman3).getType() != HitResult.Type.MISS) {
            _snowman = _snowman3.getPos();
        }
        if ((_snowman = ProjectileUtil.getEntityCollision(_snowman2, entity, _snowman, _snowman, entity.getBoundingBox().stretch(entity.getVelocity()).expand(1.0), predicate)) != null) {
            _snowman3 = _snowman;
        }
        return _snowman3;
    }

    @Nullable
    public static EntityHitResult raycast(Entity entity, Vec3d vec3d, Vec3d vec3d2, Box box, Predicate<Entity> predicate, double d) {
        World world = entity.world;
        double _snowman2 = d;
        Entity _snowman3 = null;
        Vec3d _snowman4 = null;
        for (Entity entity2 : world.getOtherEntities(entity, box, predicate)) {
            Box box2 = entity2.getBoundingBox().expand(entity2.getTargetingMargin());
            Optional<Vec3d> _snowman5 = box2.raycast(vec3d, vec3d2);
            if (box2.contains(vec3d)) {
                if (!(_snowman2 >= 0.0)) continue;
                _snowman3 = entity2;
                _snowman4 = _snowman5.orElse(vec3d);
                _snowman2 = 0.0;
                continue;
            }
            if (!_snowman5.isPresent() || !((_snowman = vec3d.squaredDistanceTo(_snowman = _snowman5.get())) < _snowman2) && _snowman2 != 0.0) continue;
            if (entity2.getRootVehicle() == entity.getRootVehicle()) {
                if (_snowman2 != 0.0) continue;
                _snowman3 = entity2;
                _snowman4 = _snowman;
                continue;
            }
            _snowman3 = entity2;
            _snowman4 = _snowman;
            _snowman2 = _snowman;
        }
        if (_snowman3 == null) {
            return null;
        }
        return new EntityHitResult(_snowman3, _snowman4);
    }

    @Nullable
    public static EntityHitResult getEntityCollision(World world, Entity entity, Vec3d vec3d, Vec3d vec3d2, Box box, Predicate<Entity> predicate) {
        double _snowman4 = Double.MAX_VALUE;
        Entity _snowman2 = null;
        for (Entity entity2 : world.getOtherEntities(entity, box, predicate)) {
            Box box2 = entity2.getBoundingBox().expand(0.3f);
            Optional<Vec3d> _snowman3 = box2.raycast(vec3d, vec3d2);
            if (!_snowman3.isPresent() || !((_snowman = vec3d.squaredDistanceTo(_snowman3.get())) < _snowman4)) continue;
            _snowman2 = entity2;
            _snowman4 = _snowman;
        }
        if (_snowman2 == null) {
            return null;
        }
        return new EntityHitResult(_snowman2);
    }

    public static final void method_7484(Entity entity, float f) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.lengthSquared() == 0.0) {
            return;
        }
        float _snowman2 = MathHelper.sqrt(Entity.squaredHorizontalLength(vec3d));
        entity.yaw = (float)(MathHelper.atan2(vec3d.z, vec3d.x) * 57.2957763671875) + 90.0f;
        entity.pitch = (float)(MathHelper.atan2(_snowman2, vec3d.y) * 57.2957763671875) - 90.0f;
        while (entity.pitch - entity.prevPitch < -180.0f) {
            entity.prevPitch -= 360.0f;
        }
        while (entity.pitch - entity.prevPitch >= 180.0f) {
            entity.prevPitch += 360.0f;
        }
        while (entity.yaw - entity.prevYaw < -180.0f) {
            entity.prevYaw -= 360.0f;
        }
        while (entity.yaw - entity.prevYaw >= 180.0f) {
            entity.prevYaw += 360.0f;
        }
        entity.pitch = MathHelper.lerp(f, entity.prevPitch, entity.pitch);
        entity.yaw = MathHelper.lerp(f, entity.prevYaw, entity.yaw);
    }

    public static Hand getHandPossiblyHolding(LivingEntity entity, Item item) {
        return entity.getMainHandStack().getItem() == item ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }

    public static PersistentProjectileEntity createArrowProjectile(LivingEntity entity, ItemStack stack, float damageModifier) {
        ArrowItem arrowItem = (ArrowItem)(stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);
        PersistentProjectileEntity _snowman2 = arrowItem.createArrow(entity.world, stack, entity);
        _snowman2.applyEnchantmentEffects(entity, damageModifier);
        if (stack.getItem() == Items.TIPPED_ARROW && _snowman2 instanceof ArrowEntity) {
            ((ArrowEntity)_snowman2).initFromStack(stack);
        }
        return _snowman2;
    }
}

