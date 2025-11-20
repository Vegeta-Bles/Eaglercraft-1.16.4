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
   public static final Predicate<Entity> NOT_MOUNTED = _snowman -> _snowman.isAlive() && !_snowman.hasPassengers() && !_snowman.hasVehicle();
   public static final Predicate<Entity> VALID_INVENTORIES = _snowman -> _snowman instanceof Inventory && _snowman.isAlive();
   public static final Predicate<Entity> EXCEPT_CREATIVE_OR_SPECTATOR = _snowman -> !(_snowman instanceof PlayerEntity)
         || !_snowman.isSpectator() && !((PlayerEntity)_snowman).isCreative();
   public static final Predicate<Entity> EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL = _snowman -> !(_snowman instanceof PlayerEntity)
         || !_snowman.isSpectator() && !((PlayerEntity)_snowman).isCreative() && _snowman.world.getDifficulty() != Difficulty.PEACEFUL;
   public static final Predicate<Entity> EXCEPT_SPECTATOR = _snowman -> !_snowman.isSpectator();

   public static Predicate<Entity> maxDistance(double x, double y, double z, double _snowman) {
      double _snowmanx = _snowman * _snowman;
      return _snowmanxx -> _snowmanxx != null && _snowmanxx.squaredDistanceTo(x, y, z) <= _snowman;
   }

   public static Predicate<Entity> canBePushedBy(Entity entity) {
      AbstractTeam _snowman = entity.getScoreboardTeam();
      AbstractTeam.CollisionRule _snowmanx = _snowman == null ? AbstractTeam.CollisionRule.ALWAYS : _snowman.getCollisionRule();
      return (Predicate<Entity>)(_snowmanx == AbstractTeam.CollisionRule.NEVER
         ? Predicates.alwaysFalse()
         : EXCEPT_SPECTATOR.and(
            _snowmanxxx -> {
               if (!_snowmanxxx.isPushable()) {
                  return false;
               } else if (!entity.world.isClient || _snowmanxxx instanceof PlayerEntity && ((PlayerEntity)_snowmanxxx).isMainPlayer()) {
                  AbstractTeam _snowmanxx = _snowmanxxx.getScoreboardTeam();
                  AbstractTeam.CollisionRule _snowmanx = _snowmanxx == null ? AbstractTeam.CollisionRule.ALWAYS : _snowmanxx.getCollisionRule();
                  if (_snowmanx == AbstractTeam.CollisionRule.NEVER) {
                     return false;
                  } else {
                     boolean _snowmanxx = _snowman != null && _snowman.isEqual(_snowmanxx);
                     return (_snowman == AbstractTeam.CollisionRule.PUSH_OWN_TEAM || _snowmanx == AbstractTeam.CollisionRule.PUSH_OWN_TEAM) && _snowmanxx
                        ? false
                        : _snowman != AbstractTeam.CollisionRule.PUSH_OTHER_TEAMS && _snowmanx != AbstractTeam.CollisionRule.PUSH_OTHER_TEAMS || _snowmanxx;
                  }
               } else {
                  return false;
               }
            }
         ));
   }

   public static Predicate<Entity> rides(Entity entity) {
      return _snowmanx -> {
         while (_snowmanx.hasVehicle()) {
            _snowmanx = _snowmanx.getVehicle();
            if (_snowmanx == entity) {
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

      public boolean test(@Nullable Entity _snowman) {
         if (!_snowman.isAlive()) {
            return false;
         } else if (!(_snowman instanceof LivingEntity)) {
            return false;
         } else {
            LivingEntity _snowmanx = (LivingEntity)_snowman;
            return _snowmanx.canEquip(this.stack);
         }
      }
   }
}
