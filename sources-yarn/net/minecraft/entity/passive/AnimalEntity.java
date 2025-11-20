package net.minecraft.entity.passive;

import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class AnimalEntity extends PassiveEntity {
   private int loveTicks;
   private UUID lovingPlayer;

   protected AnimalEntity(EntityType<? extends AnimalEntity> arg, World arg2) {
      super(arg, arg2);
      this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0F);
      this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
   }

   @Override
   protected void mobTick() {
      if (this.getBreedingAge() != 0) {
         this.loveTicks = 0;
      }

      super.mobTick();
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      if (this.getBreedingAge() != 0) {
         this.loveTicks = 0;
      }

      if (this.loveTicks > 0) {
         this.loveTicks--;
         if (this.loveTicks % 10 == 0) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.world.addParticle(ParticleTypes.HEART, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
         }
      }
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         this.loveTicks = 0;
         return super.damage(source, amount);
      }
   }

   @Override
   public float getPathfindingFavor(BlockPos pos, WorldView world) {
      return world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) ? 10.0F : world.getBrightness(pos) - 0.5F;
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("InLove", this.loveTicks);
      if (this.lovingPlayer != null) {
         tag.putUuid("LoveCause", this.lovingPlayer);
      }
   }

   @Override
   public double getHeightOffset() {
      return 0.14;
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.loveTicks = tag.getInt("InLove");
      this.lovingPlayer = tag.containsUuid("LoveCause") ? tag.getUuid("LoveCause") : null;
   }

   public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      return world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) && world.getBaseLightLevel(pos, 0) > 8;
   }

   @Override
   public int getMinAmbientSoundDelay() {
      return 120;
   }

   @Override
   public boolean canImmediatelyDespawn(double distanceSquared) {
      return false;
   }

   @Override
   protected int getCurrentExperience(PlayerEntity player) {
      return 1 + this.world.random.nextInt(3);
   }

   public boolean isBreedingItem(ItemStack stack) {
      return stack.getItem() == Items.WHEAT;
   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack lv = player.getStackInHand(hand);
      if (this.isBreedingItem(lv)) {
         int i = this.getBreedingAge();
         if (!this.world.isClient && i == 0 && this.canEat()) {
            this.eat(player, lv);
            this.lovePlayer(player);
            return ActionResult.SUCCESS;
         }

         if (this.isBaby()) {
            this.eat(player, lv);
            this.growUp((int)((float)(-i / 20) * 0.1F), true);
            return ActionResult.success(this.world.isClient);
         }

         if (this.world.isClient) {
            return ActionResult.CONSUME;
         }
      }

      return super.interactMob(player, hand);
   }

   protected void eat(PlayerEntity player, ItemStack stack) {
      if (!player.abilities.creativeMode) {
         stack.decrement(1);
      }
   }

   public boolean canEat() {
      return this.loveTicks <= 0;
   }

   public void lovePlayer(@Nullable PlayerEntity player) {
      this.loveTicks = 600;
      if (player != null) {
         this.lovingPlayer = player.getUuid();
      }

      this.world.sendEntityStatus(this, (byte)18);
   }

   public void setLoveTicks(int loveTicks) {
      this.loveTicks = loveTicks;
   }

   public int getLoveTicks() {
      return this.loveTicks;
   }

   @Nullable
   public ServerPlayerEntity getLovingPlayer() {
      if (this.lovingPlayer == null) {
         return null;
      } else {
         PlayerEntity lv = this.world.getPlayerByUuid(this.lovingPlayer);
         return lv instanceof ServerPlayerEntity ? (ServerPlayerEntity)lv : null;
      }
   }

   public boolean isInLove() {
      return this.loveTicks > 0;
   }

   public void resetLoveTicks() {
      this.loveTicks = 0;
   }

   public boolean canBreedWith(AnimalEntity other) {
      if (other == this) {
         return false;
      } else {
         return other.getClass() != this.getClass() ? false : this.isInLove() && other.isInLove();
      }
   }

   public void breed(ServerWorld arg, AnimalEntity other) {
      PassiveEntity lv = this.createChild(arg, other);
      if (lv != null) {
         ServerPlayerEntity lv2 = this.getLovingPlayer();
         if (lv2 == null && other.getLovingPlayer() != null) {
            lv2 = other.getLovingPlayer();
         }

         if (lv2 != null) {
            lv2.incrementStat(Stats.ANIMALS_BRED);
            Criteria.BRED_ANIMALS.trigger(lv2, this, other, lv);
         }

         this.setBreedingAge(6000);
         other.setBreedingAge(6000);
         this.resetLoveTicks();
         other.resetLoveTicks();
         lv.setBaby(true);
         lv.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
         arg.spawnEntityAndPassengers(lv);
         arg.sendEntityStatus(this, (byte)18);
         if (arg.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            arg.spawnEntity(new ExperienceOrbEntity(arg, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
         }
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void handleStatus(byte status) {
      if (status == 18) {
         for (int i = 0; i < 7; i++) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.world.addParticle(ParticleTypes.HEART, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
         }
      } else {
         super.handleStatus(status);
      }
   }
}
