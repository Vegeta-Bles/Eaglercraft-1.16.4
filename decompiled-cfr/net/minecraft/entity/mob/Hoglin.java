/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.mob;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Vec3d;

public interface Hoglin {
    public int getMovementCooldownTicks();

    public static boolean tryAttack(LivingEntity attacker, LivingEntity target) {
        float f = (float)attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        _snowman = !attacker.isBaby() && (int)f > 0 ? f / 2.0f + (float)attacker.world.random.nextInt((int)f) : f;
        boolean _snowman2 = target.damage(DamageSource.mob(attacker), _snowman);
        if (_snowman2) {
            attacker.dealDamage(attacker, target);
            if (!attacker.isBaby()) {
                Hoglin.knockback(attacker, target);
            }
        }
        return _snowman2;
    }

    public static void knockback(LivingEntity attacker, LivingEntity target) {
        double d = attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
        _snowman = d - (_snowman = target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
        if (_snowman <= 0.0) {
            return;
        }
        _snowman = target.getX() - attacker.getX();
        _snowman = target.getZ() - attacker.getZ();
        float _snowman2 = attacker.world.random.nextInt(21) - 10;
        _snowman = _snowman * (double)(attacker.world.random.nextFloat() * 0.5f + 0.2f);
        Vec3d _snowman3 = new Vec3d(_snowman, 0.0, _snowman).normalize().multiply(_snowman).rotateY(_snowman2);
        _snowman = _snowman * (double)attacker.world.random.nextFloat() * 0.5;
        target.addVelocity(_snowman3.x, _snowman, _snowman3.z);
        target.velocityModified = true;
    }
}

