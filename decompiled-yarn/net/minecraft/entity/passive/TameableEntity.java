package net.minecraft.entity.passive;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class TameableEntity extends AnimalEntity {
   protected static final TrackedData<Byte> TAMEABLE_FLAGS = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BYTE);
   protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
   private boolean sitting;

   protected TameableEntity(EntityType<? extends TameableEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.onTamedChanged();
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(TAMEABLE_FLAGS, (byte)0);
      this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      if (this.getOwnerUuid() != null) {
         tag.putUuid("Owner", this.getOwnerUuid());
      }

      tag.putBoolean("Sitting", this.sitting);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      UUID _snowman;
      if (tag.containsUuid("Owner")) {
         _snowman = tag.getUuid("Owner");
      } else {
         String _snowmanx = tag.getString("Owner");
         _snowman = ServerConfigHandler.getPlayerUuidByName(this.getServer(), _snowmanx);
      }

      if (_snowman != null) {
         try {
            this.setOwnerUuid(_snowman);
            this.setTamed(true);
         } catch (Throwable var4) {
            this.setTamed(false);
         }
      }

      this.sitting = tag.getBoolean("Sitting");
      this.setInSittingPose(this.sitting);
   }

   @Override
   public boolean canBeLeashedBy(PlayerEntity player) {
      return !this.isLeashed();
   }

   protected void showEmoteParticle(boolean positive) {
      ParticleEffect _snowman = ParticleTypes.HEART;
      if (!positive) {
         _snowman = ParticleTypes.SMOKE;
      }

      for (int _snowmanx = 0; _snowmanx < 7; _snowmanx++) {
         double _snowmanxx = this.random.nextGaussian() * 0.02;
         double _snowmanxxx = this.random.nextGaussian() * 0.02;
         double _snowmanxxxx = this.random.nextGaussian() * 0.02;
         this.world.addParticle(_snowman, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 7) {
         this.showEmoteParticle(true);
      } else if (status == 6) {
         this.showEmoteParticle(false);
      } else {
         super.handleStatus(status);
      }
   }

   public boolean isTamed() {
      return (this.dataTracker.get(TAMEABLE_FLAGS) & 4) != 0;
   }

   public void setTamed(boolean tamed) {
      byte _snowman = this.dataTracker.get(TAMEABLE_FLAGS);
      if (tamed) {
         this.dataTracker.set(TAMEABLE_FLAGS, (byte)(_snowman | 4));
      } else {
         this.dataTracker.set(TAMEABLE_FLAGS, (byte)(_snowman & -5));
      }

      this.onTamedChanged();
   }

   protected void onTamedChanged() {
   }

   public boolean isInSittingPose() {
      return (this.dataTracker.get(TAMEABLE_FLAGS) & 1) != 0;
   }

   public void setInSittingPose(boolean inSittingPose) {
      byte _snowman = this.dataTracker.get(TAMEABLE_FLAGS);
      if (inSittingPose) {
         this.dataTracker.set(TAMEABLE_FLAGS, (byte)(_snowman | 1));
      } else {
         this.dataTracker.set(TAMEABLE_FLAGS, (byte)(_snowman & -2));
      }
   }

   @Nullable
   public UUID getOwnerUuid() {
      return this.dataTracker.get(OWNER_UUID).orElse(null);
   }

   public void setOwnerUuid(@Nullable UUID uuid) {
      this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
   }

   public void setOwner(PlayerEntity player) {
      this.setTamed(true);
      this.setOwnerUuid(player.getUuid());
      if (player instanceof ServerPlayerEntity) {
         Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
      }
   }

   @Nullable
   public LivingEntity getOwner() {
      try {
         UUID _snowman = this.getOwnerUuid();
         return _snowman == null ? null : this.world.getPlayerByUuid(_snowman);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   @Override
   public boolean canTarget(LivingEntity target) {
      return this.isOwner(target) ? false : super.canTarget(target);
   }

   public boolean isOwner(LivingEntity entity) {
      return entity == this.getOwner();
   }

   public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
      return true;
   }

   @Override
   public AbstractTeam getScoreboardTeam() {
      if (this.isTamed()) {
         LivingEntity _snowman = this.getOwner();
         if (_snowman != null) {
            return _snowman.getScoreboardTeam();
         }
      }

      return super.getScoreboardTeam();
   }

   @Override
   public boolean isTeammate(Entity other) {
      if (this.isTamed()) {
         LivingEntity _snowman = this.getOwner();
         if (other == _snowman) {
            return true;
         }

         if (_snowman != null) {
            return _snowman.isTeammate(other);
         }
      }

      return super.isTeammate(other);
   }

   @Override
   public void onDeath(DamageSource source) {
      if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
         this.getOwner().sendSystemMessage(this.getDamageTracker().getDeathMessage(), Util.NIL_UUID);
      }

      super.onDeath(source);
   }

   public boolean isSitting() {
      return this.sitting;
   }

   public void setSitting(boolean sitting) {
      this.sitting = sitting;
   }
}
