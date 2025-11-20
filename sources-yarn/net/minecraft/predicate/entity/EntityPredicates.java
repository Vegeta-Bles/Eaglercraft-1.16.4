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
   public static final Predicate<Entity> NOT_MOUNTED = arg -> arg.isAlive() && !arg.hasPassengers() && !arg.hasVehicle();
   public static final Predicate<Entity> VALID_INVENTORIES = arg -> arg instanceof Inventory && arg.isAlive();
   public static final Predicate<Entity> EXCEPT_CREATIVE_OR_SPECTATOR = arg -> !(arg instanceof PlayerEntity)
         || !arg.isSpectator() && !((PlayerEntity)arg).isCreative();
   public static final Predicate<Entity> EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL = arg -> !(arg instanceof PlayerEntity)
         || !arg.isSpectator() && !((PlayerEntity)arg).isCreative() && arg.world.getDifficulty() != Difficulty.PEACEFUL;
   public static final Predicate<Entity> EXCEPT_SPECTATOR = arg -> !arg.isSpectator();

   public static Predicate<Entity> maxDistance(double x, double y, double z, double g) {
      double h = g * g;
      return arg -> arg != null && arg.squaredDistanceTo(x, y, z) <= h;
   }

   public static Predicate<Entity> canBePushedBy(Entity entity) {
      AbstractTeam lv = entity.getScoreboardTeam();
      AbstractTeam.CollisionRule lv2 = lv == null ? AbstractTeam.CollisionRule.ALWAYS : lv.getCollisionRule();
      return (Predicate<Entity>)(lv2 == AbstractTeam.CollisionRule.NEVER
         ? Predicates.alwaysFalse()
         : EXCEPT_SPECTATOR.and(
            arg4 -> {
               if (!arg4.isPushable()) {
                  return false;
               } else if (!entity.world.isClient || arg4 instanceof PlayerEntity && ((PlayerEntity)arg4).isMainPlayer()) {
                  AbstractTeam lvx = arg4.getScoreboardTeam();
                  AbstractTeam.CollisionRule lv2x = lvx == null ? AbstractTeam.CollisionRule.ALWAYS : lvx.getCollisionRule();
                  if (lv2x == AbstractTeam.CollisionRule.NEVER) {
                     return false;
                  } else {
                     boolean bl = lv != null && lv.isEqual(lvx);
                     return (lv2 == AbstractTeam.CollisionRule.PUSH_OWN_TEAM || lv2x == AbstractTeam.CollisionRule.PUSH_OWN_TEAM) && bl
                        ? false
                        : lv2 != AbstractTeam.CollisionRule.PUSH_OTHER_TEAMS && lv2x != AbstractTeam.CollisionRule.PUSH_OTHER_TEAMS || bl;
                  }
               } else {
                  return false;
               }
            }
         ));
   }

   public static Predicate<Entity> rides(Entity entity) {
      return arg2 -> {
         while (arg2.hasVehicle()) {
            arg2 = arg2.getVehicle();
            if (arg2 == entity) {
               return false;
            }
         }

         return true;
      };
   }

   public static class Equipable implements Predicate<Entity> {
      private final ItemStack stack;

      public Equipable(ItemStack stack) {
         this.stack = stack;
      }

      public boolean test(@Nullable Entity arg) {
         if (!arg.isAlive()) {
            return false;
         } else if (!(arg instanceof LivingEntity)) {
            return false;
         } else {
            LivingEntity lv = (LivingEntity)arg;
            return lv.canEquip(this.stack);
         }
      }
   }
}
