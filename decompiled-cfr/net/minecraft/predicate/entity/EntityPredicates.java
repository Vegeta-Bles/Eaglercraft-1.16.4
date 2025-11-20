/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicates
 *  javax.annotation.Nullable
 */
package net.minecraft.predicate.entity;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.world.Difficulty;

public final class EntityPredicates {
    public static final Predicate<Entity> VALID_ENTITY = Entity::isAlive;
    public static final Predicate<LivingEntity> VALID_LIVING_ENTITY = LivingEntity::isAlive;
    public static final Predicate<Entity> NOT_MOUNTED = entity -> entity.isAlive() && !entity.hasPassengers() && !entity.hasVehicle();
    public static final Predicate<Entity> VALID_INVENTORIES = entity -> entity instanceof Inventory && entity.isAlive();
    public static final Predicate<Entity> EXCEPT_CREATIVE_OR_SPECTATOR = entity -> !(entity instanceof PlayerEntity) || !entity.isSpectator() && !((PlayerEntity)entity).isCreative();
    public static final Predicate<Entity> EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL = entity -> !(entity instanceof PlayerEntity) || !entity.isSpectator() && !((PlayerEntity)entity).isCreative() && entity.world.getDifficulty() != Difficulty.PEACEFUL;
    public static final Predicate<Entity> EXCEPT_SPECTATOR = entity -> !entity.isSpectator();

    public static Predicate<Entity> maxDistance(double x, double y, double z, double d) {
        _snowman = d * d;
        return entity -> entity != null && entity.squaredDistanceTo(x, y, z) <= _snowman;
    }

    public static Predicate<Entity> canBePushedBy(Entity entity) {
        AbstractTeam abstractTeam = entity.getScoreboardTeam();
        AbstractTeam.CollisionRule collisionRule = _snowman = abstractTeam == null ? AbstractTeam.CollisionRule.ALWAYS : abstractTeam.getCollisionRule();
        if (_snowman == AbstractTeam.CollisionRule.NEVER) {
            return Predicates.alwaysFalse();
        }
        return EXCEPT_SPECTATOR.and(entity2 -> {
            if (!entity2.isPushable()) {
                return false;
            }
            if (!(!entity.world.isClient || entity2 instanceof PlayerEntity && ((PlayerEntity)entity2).isMainPlayer())) {
                return false;
            }
            AbstractTeam abstractTeam2 = entity2.getScoreboardTeam();
            AbstractTeam.CollisionRule collisionRule2 = _snowman = abstractTeam2 == null ? AbstractTeam.CollisionRule.ALWAYS : abstractTeam2.getCollisionRule();
            if (_snowman == AbstractTeam.CollisionRule.NEVER) {
                return false;
            }
            boolean bl = _snowman = abstractTeam != null && abstractTeam.isEqual(abstractTeam2);
            if ((_snowman == AbstractTeam.CollisionRule.PUSH_OWN_TEAM || _snowman == AbstractTeam.CollisionRule.PUSH_OWN_TEAM) && _snowman) {
                return false;
            }
            return _snowman != AbstractTeam.CollisionRule.PUSH_OTHER_TEAMS && _snowman != AbstractTeam.CollisionRule.PUSH_OTHER_TEAMS || _snowman;
        });
    }

    public static Predicate<Entity> rides(Entity entity) {
        return entity2 -> {
            while (entity2.hasVehicle()) {
                if ((entity2 = entity2.getVehicle()) != entity) continue;
                return false;
            }
            return true;
        };
    }

    public static class Equipable
    implements Predicate<Entity> {
        private final ItemStack stack;

        public Equipable(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public boolean test(@Nullable Entity entity) {
            if (!entity.isAlive()) {
                return false;
            }
            if (!(entity instanceof LivingEntity)) {
                return false;
            }
            LivingEntity livingEntity = (LivingEntity)entity;
            return livingEntity.canEquip(this.stack);
        }

        @Override
        public /* synthetic */ boolean test(@Nullable Object context) {
            return this.test((Entity)context);
        }
    }
}

