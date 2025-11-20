package net.minecraft.entity.mob;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public interface Angerable {
   int getAngerTime();

   void setAngerTime(int ticks);

   @Nullable
   UUID getAngryAt();

   void setAngryAt(@Nullable UUID uuid);

   void chooseRandomAngerTime();

   default void angerToTag(CompoundTag tag) {
      tag.putInt("AngerTime", this.getAngerTime());
      if (this.getAngryAt() != null) {
         tag.putUuid("AngryAt", this.getAngryAt());
      }
   }

   default void angerFromTag(ServerWorld world, CompoundTag tag) {
      this.setAngerTime(tag.getInt("AngerTime"));
      if (!tag.containsUuid("AngryAt")) {
         this.setAngryAt(null);
      } else {
         UUID _snowman = tag.getUuid("AngryAt");
         this.setAngryAt(_snowman);
         Entity _snowmanx = world.getEntity(_snowman);
         if (_snowmanx != null) {
            if (_snowmanx instanceof MobEntity) {
               this.setAttacker((MobEntity)_snowmanx);
            }

            if (_snowmanx.getType() == EntityType.PLAYER) {
               this.setAttacking((PlayerEntity)_snowmanx);
            }
         }
      }
   }

   default void tickAngerLogic(ServerWorld world, boolean _snowman) {
      LivingEntity _snowmanx = this.getTarget();
      UUID _snowmanxx = this.getAngryAt();
      if ((_snowmanx == null || _snowmanx.isDead()) && _snowmanxx != null && world.getEntity(_snowmanxx) instanceof MobEntity) {
         this.stopAnger();
      } else {
         if (_snowmanx != null && !Objects.equals(_snowmanxx, _snowmanx.getUuid())) {
            this.setAngryAt(_snowmanx.getUuid());
            this.chooseRandomAngerTime();
         }

         if (this.getAngerTime() > 0 && (_snowmanx == null || _snowmanx.getType() != EntityType.PLAYER || !_snowman)) {
            this.setAngerTime(this.getAngerTime() - 1);
            if (this.getAngerTime() == 0) {
               this.stopAnger();
            }
         }
      }
   }

   default boolean shouldAngerAt(LivingEntity entity) {
      if (!EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(entity)) {
         return false;
      } else {
         return entity.getType() == EntityType.PLAYER && this.isUniversallyAngry(entity.world) ? true : entity.getUuid().equals(this.getAngryAt());
      }
   }

   default boolean isUniversallyAngry(World world) {
      return world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER) && this.hasAngerTime() && this.getAngryAt() == null;
   }

   default boolean hasAngerTime() {
      return this.getAngerTime() > 0;
   }

   default void forgive(PlayerEntity player) {
      if (player.world.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS)) {
         if (player.getUuid().equals(this.getAngryAt())) {
            this.stopAnger();
         }
      }
   }

   default void universallyAnger() {
      this.stopAnger();
      this.chooseRandomAngerTime();
   }

   default void stopAnger() {
      this.setAttacker(null);
      this.setAngryAt(null);
      this.setTarget(null);
      this.setAngerTime(0);
   }

   void setAttacker(@Nullable LivingEntity attacker);

   void setAttacking(@Nullable PlayerEntity attacking);

   void setTarget(@Nullable LivingEntity target);

   @Nullable
   LivingEntity getTarget();
}
