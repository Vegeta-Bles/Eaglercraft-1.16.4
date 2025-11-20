package net.minecraft.entity.passive;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.GoToWalkTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HoldInHandsGoal;
import net.minecraft.entity.ai.goal.LookAtCustomerGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.StopAndLookAtEntityGoal;
import net.minecraft.entity.ai.goal.StopFollowingCustomerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.World;

public class WanderingTraderEntity extends MerchantEntity {
   @Nullable
   private BlockPos wanderTarget;
   private int despawnDelay;

   public WanderingTraderEntity(EntityType<? extends WanderingTraderEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.teleporting = true;
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(0, new SwimGoal(this));
      this.goalSelector
         .add(
            0,
            new HoldInHandsGoal<>(
               this,
               PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.INVISIBILITY),
               SoundEvents.ENTITY_WANDERING_TRADER_DISAPPEARED,
               _snowman -> this.world.isNight() && !_snowman.isInvisible()
            )
         );
      this.goalSelector
         .add(
            0,
            new HoldInHandsGoal<>(
               this, new ItemStack(Items.MILK_BUCKET), SoundEvents.ENTITY_WANDERING_TRADER_REAPPEARED, _snowman -> this.world.isDay() && _snowman.isInvisible()
            )
         );
      this.goalSelector.add(1, new StopFollowingCustomerGoal(this));
      this.goalSelector.add(1, new FleeEntityGoal<>(this, ZombieEntity.class, 8.0F, 0.5, 0.5));
      this.goalSelector.add(1, new FleeEntityGoal<>(this, EvokerEntity.class, 12.0F, 0.5, 0.5));
      this.goalSelector.add(1, new FleeEntityGoal<>(this, VindicatorEntity.class, 8.0F, 0.5, 0.5));
      this.goalSelector.add(1, new FleeEntityGoal<>(this, VexEntity.class, 8.0F, 0.5, 0.5));
      this.goalSelector.add(1, new FleeEntityGoal<>(this, PillagerEntity.class, 15.0F, 0.5, 0.5));
      this.goalSelector.add(1, new FleeEntityGoal<>(this, IllusionerEntity.class, 12.0F, 0.5, 0.5));
      this.goalSelector.add(1, new FleeEntityGoal<>(this, ZoglinEntity.class, 10.0F, 0.5, 0.5));
      this.goalSelector.add(1, new EscapeDangerGoal(this, 0.5));
      this.goalSelector.add(1, new LookAtCustomerGoal(this));
      this.goalSelector.add(2, new WanderingTraderEntity.WanderToTargetGoal(this, 2.0, 0.35));
      this.goalSelector.add(4, new GoToWalkTargetGoal(this, 0.35));
      this.goalSelector.add(8, new WanderAroundFarGoal(this, 0.35));
      this.goalSelector.add(9, new StopAndLookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
      this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
   }

   @Nullable
   @Override
   public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
      return null;
   }

   @Override
   public boolean isLeveledMerchant() {
      return false;
   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (_snowman.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.hasCustomer() && !this.isBaby()) {
         if (hand == Hand.MAIN_HAND) {
            player.incrementStat(Stats.TALKED_TO_VILLAGER);
         }

         if (this.getOffers().isEmpty()) {
            return ActionResult.success(this.world.isClient);
         } else {
            if (!this.world.isClient) {
               this.setCurrentCustomer(player);
               this.sendOffers(player, this.getDisplayName(), 1);
            }

            return ActionResult.success(this.world.isClient);
         }
      } else {
         return super.interactMob(player, hand);
      }
   }

   @Override
   protected void fillRecipes() {
      TradeOffers.Factory[] _snowman = (TradeOffers.Factory[])TradeOffers.WANDERING_TRADER_TRADES.get(1);
      TradeOffers.Factory[] _snowmanx = (TradeOffers.Factory[])TradeOffers.WANDERING_TRADER_TRADES.get(2);
      if (_snowman != null && _snowmanx != null) {
         TradeOfferList _snowmanxx = this.getOffers();
         this.fillRecipesFromPool(_snowmanxx, _snowman, 5);
         int _snowmanxxx = this.random.nextInt(_snowmanx.length);
         TradeOffers.Factory _snowmanxxxx = _snowmanx[_snowmanxxx];
         TradeOffer _snowmanxxxxx = _snowmanxxxx.create(this, this.random);
         if (_snowmanxxxxx != null) {
            _snowmanxx.add(_snowmanxxxxx);
         }
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("DespawnDelay", this.despawnDelay);
      if (this.wanderTarget != null) {
         tag.put("WanderTarget", NbtHelper.fromBlockPos(this.wanderTarget));
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("DespawnDelay", 99)) {
         this.despawnDelay = tag.getInt("DespawnDelay");
      }

      if (tag.contains("WanderTarget")) {
         this.wanderTarget = NbtHelper.toBlockPos(tag.getCompound("WanderTarget"));
      }

      this.setBreedingAge(Math.max(0, this.getBreedingAge()));
   }

   @Override
   public boolean canImmediatelyDespawn(double distanceSquared) {
      return false;
   }

   @Override
   protected void afterUsing(TradeOffer offer) {
      if (offer.shouldRewardPlayerExperience()) {
         int _snowman = 3 + this.random.nextInt(4);
         this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY() + 0.5, this.getZ(), _snowman));
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.hasCustomer() ? SoundEvents.ENTITY_WANDERING_TRADER_TRADE : SoundEvents.ENTITY_WANDERING_TRADER_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_WANDERING_TRADER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_WANDERING_TRADER_DEATH;
   }

   @Override
   protected SoundEvent getDrinkSound(ItemStack stack) {
      Item _snowman = stack.getItem();
      return _snowman == Items.MILK_BUCKET ? SoundEvents.ENTITY_WANDERING_TRADER_DRINK_MILK : SoundEvents.ENTITY_WANDERING_TRADER_DRINK_POTION;
   }

   @Override
   protected SoundEvent getTradingSound(boolean sold) {
      return sold ? SoundEvents.ENTITY_WANDERING_TRADER_YES : SoundEvents.ENTITY_WANDERING_TRADER_NO;
   }

   @Override
   public SoundEvent getYesSound() {
      return SoundEvents.ENTITY_WANDERING_TRADER_YES;
   }

   public void setDespawnDelay(int delay) {
      this.despawnDelay = delay;
   }

   public int getDespawnDelay() {
      return this.despawnDelay;
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      if (!this.world.isClient) {
         this.tickDespawnDelay();
      }
   }

   private void tickDespawnDelay() {
      if (this.despawnDelay > 0 && !this.hasCustomer() && --this.despawnDelay == 0) {
         this.remove();
      }
   }

   public void setWanderTarget(@Nullable BlockPos pos) {
      this.wanderTarget = pos;
   }

   @Nullable
   private BlockPos getWanderTarget() {
      return this.wanderTarget;
   }

   class WanderToTargetGoal extends Goal {
      final WanderingTraderEntity trader;
      final double proximityDistance;
      final double speed;

      WanderToTargetGoal(WanderingTraderEntity trader, double proximityDistance, double speed) {
         this.trader = trader;
         this.proximityDistance = proximityDistance;
         this.speed = speed;
         this.setControls(EnumSet.of(Goal.Control.MOVE));
      }

      @Override
      public void stop() {
         this.trader.setWanderTarget(null);
         WanderingTraderEntity.this.navigation.stop();
      }

      @Override
      public boolean canStart() {
         BlockPos _snowman = this.trader.getWanderTarget();
         return _snowman != null && this.isTooFarFrom(_snowman, this.proximityDistance);
      }

      @Override
      public void tick() {
         BlockPos _snowman = this.trader.getWanderTarget();
         if (_snowman != null && WanderingTraderEntity.this.navigation.isIdle()) {
            if (this.isTooFarFrom(_snowman, 10.0)) {
               Vec3d _snowmanx = new Vec3d((double)_snowman.getX() - this.trader.getX(), (double)_snowman.getY() - this.trader.getY(), (double)_snowman.getZ() - this.trader.getZ())
                  .normalize();
               Vec3d _snowmanxx = _snowmanx.multiply(10.0).add(this.trader.getX(), this.trader.getY(), this.trader.getZ());
               WanderingTraderEntity.this.navigation.startMovingTo(_snowmanxx.x, _snowmanxx.y, _snowmanxx.z, this.speed);
            } else {
               WanderingTraderEntity.this.navigation.startMovingTo((double)_snowman.getX(), (double)_snowman.getY(), (double)_snowman.getZ(), this.speed);
            }
         }
      }

      private boolean isTooFarFrom(BlockPos pos, double proximityDistance) {
         return !pos.isWithinDistance(this.trader.getPos(), proximityDistance);
      }
   }
}
