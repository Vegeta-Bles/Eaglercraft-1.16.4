package net.minecraft.entity.passive;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.IronGolemLookGoal;
import net.minecraft.entity.ai.goal.IronGolemWanderAroundGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TrackIronGolemTargetGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.ai.goal.WanderAroundPointOfInterestGoal;
import net.minecraft.entity.ai.goal.WanderNearTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.IntRange;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class IronGolemEntity extends GolemEntity implements Angerable {
   protected static final TrackedData<Byte> IRON_GOLEM_FLAGS = DataTracker.registerData(IronGolemEntity.class, TrackedDataHandlerRegistry.BYTE);
   private int attackTicksLeft;
   private int lookingAtVillagerTicksLeft;
   private static final IntRange ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
   private int angerTime;
   private UUID angryAt;

   public IronGolemEntity(EntityType<? extends IronGolemEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.stepHeight = 1.0F;
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9, 32.0F));
      this.goalSelector.add(2, new WanderAroundPointOfInterestGoal(this, 0.6, false));
      this.goalSelector.add(4, new IronGolemWanderAroundGoal(this, 0.6));
      this.goalSelector.add(5, new IronGolemLookGoal(this));
      this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.add(8, new LookAroundGoal(this));
      this.targetSelector.add(1, new TrackIronGolemTargetGoal(this));
      this.targetSelector.add(2, new RevengeGoal(this));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, MobEntity.class, 5, false, false, _snowman -> _snowman instanceof Monster && !(_snowman instanceof CreeperEntity)));
      this.targetSelector.add(4, new UniversalAngerGoal<>(this, false));
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(IRON_GOLEM_FLAGS, (byte)0);
   }

   public static DefaultAttributeContainer.Builder createIronGolemAttributes() {
      return MobEntity.createMobAttributes()
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
         .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0);
   }

   @Override
   protected int getNextAirUnderwater(int air) {
      return air;
   }

   @Override
   protected void pushAway(Entity entity) {
      if (entity instanceof Monster && !(entity instanceof CreeperEntity) && this.getRandom().nextInt(20) == 0) {
         this.setTarget((LivingEntity)entity);
      }

      super.pushAway(entity);
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      if (this.attackTicksLeft > 0) {
         this.attackTicksLeft--;
      }

      if (this.lookingAtVillagerTicksLeft > 0) {
         this.lookingAtVillagerTicksLeft--;
      }

      if (squaredHorizontalLength(this.getVelocity()) > 2.5000003E-7F && this.random.nextInt(5) == 0) {
         int _snowman = MathHelper.floor(this.getX());
         int _snowmanx = MathHelper.floor(this.getY() - 0.2F);
         int _snowmanxx = MathHelper.floor(this.getZ());
         BlockState _snowmanxxx = this.world.getBlockState(new BlockPos(_snowman, _snowmanx, _snowmanxx));
         if (!_snowmanxxx.isAir()) {
            this.world
               .addParticle(
                  new BlockStateParticleEffect(ParticleTypes.BLOCK, _snowmanxxx),
                  this.getX() + ((double)this.random.nextFloat() - 0.5) * (double)this.getWidth(),
                  this.getY() + 0.1,
                  this.getZ() + ((double)this.random.nextFloat() - 0.5) * (double)this.getWidth(),
                  4.0 * ((double)this.random.nextFloat() - 0.5),
                  0.5,
                  ((double)this.random.nextFloat() - 0.5) * 4.0
               );
         }
      }

      if (!this.world.isClient) {
         this.tickAngerLogic((ServerWorld)this.world, true);
      }
   }

   @Override
   public boolean canTarget(EntityType<?> type) {
      if (this.isPlayerCreated() && type == EntityType.PLAYER) {
         return false;
      } else {
         return type == EntityType.CREEPER ? false : super.canTarget(type);
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putBoolean("PlayerCreated", this.isPlayerCreated());
      this.angerToTag(tag);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.setPlayerCreated(tag.getBoolean("PlayerCreated"));
      this.angerFromTag((ServerWorld)this.world, tag);
   }

   @Override
   public void chooseRandomAngerTime() {
      this.setAngerTime(ANGER_TIME_RANGE.choose(this.random));
   }

   @Override
   public void setAngerTime(int ticks) {
      this.angerTime = ticks;
   }

   @Override
   public int getAngerTime() {
      return this.angerTime;
   }

   @Override
   public void setAngryAt(@Nullable UUID uuid) {
      this.angryAt = uuid;
   }

   @Override
   public UUID getAngryAt() {
      return this.angryAt;
   }

   private float getAttackDamage() {
      return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
   }

   @Override
   public boolean tryAttack(Entity target) {
      this.attackTicksLeft = 10;
      this.world.sendEntityStatus(this, (byte)4);
      float _snowman = this.getAttackDamage();
      float _snowmanx = (int)_snowman > 0 ? _snowman / 2.0F + (float)this.random.nextInt((int)_snowman) : _snowman;
      boolean _snowmanxx = target.damage(DamageSource.mob(this), _snowmanx);
      if (_snowmanxx) {
         target.setVelocity(target.getVelocity().add(0.0, 0.4F, 0.0));
         this.dealDamage(this, target);
      }

      this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
      return _snowmanxx;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      IronGolemEntity.Crack _snowman = this.getCrack();
      boolean _snowmanx = super.damage(source, amount);
      if (_snowmanx && this.getCrack() != _snowman) {
         this.playSound(SoundEvents.ENTITY_IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
      }

      return _snowmanx;
   }

   public IronGolemEntity.Crack getCrack() {
      return IronGolemEntity.Crack.from(this.getHealth() / this.getMaxHealth());
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 4) {
         this.attackTicksLeft = 10;
         this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
      } else if (status == 11) {
         this.lookingAtVillagerTicksLeft = 400;
      } else if (status == 34) {
         this.lookingAtVillagerTicksLeft = 0;
      } else {
         super.handleStatus(status);
      }
   }

   public int getAttackTicksLeft() {
      return this.attackTicksLeft;
   }

   public void setLookingAtVillager(boolean lookingAtVillager) {
      if (lookingAtVillager) {
         this.lookingAtVillagerTicksLeft = 400;
         this.world.sendEntityStatus(this, (byte)11);
      } else {
         this.lookingAtVillagerTicksLeft = 0;
         this.world.sendEntityStatus(this, (byte)34);
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_IRON_GOLEM_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_IRON_GOLEM_DEATH;
   }

   @Override
   protected ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      Item _snowmanx = _snowman.getItem();
      if (_snowmanx != Items.IRON_INGOT) {
         return ActionResult.PASS;
      } else {
         float _snowmanxx = this.getHealth();
         this.heal(25.0F);
         if (this.getHealth() == _snowmanxx) {
            return ActionResult.PASS;
         } else {
            float _snowmanxxx = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
            this.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0F, _snowmanxxx);
            if (!player.abilities.creativeMode) {
               _snowman.decrement(1);
            }

            return ActionResult.success(this.world.isClient);
         }
      }
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundEvents.ENTITY_IRON_GOLEM_STEP, 1.0F, 1.0F);
   }

   public int getLookingAtVillagerTicks() {
      return this.lookingAtVillagerTicksLeft;
   }

   public boolean isPlayerCreated() {
      return (this.dataTracker.get(IRON_GOLEM_FLAGS) & 1) != 0;
   }

   public void setPlayerCreated(boolean playerCreated) {
      byte _snowman = this.dataTracker.get(IRON_GOLEM_FLAGS);
      if (playerCreated) {
         this.dataTracker.set(IRON_GOLEM_FLAGS, (byte)(_snowman | 1));
      } else {
         this.dataTracker.set(IRON_GOLEM_FLAGS, (byte)(_snowman & -2));
      }
   }

   @Override
   public void onDeath(DamageSource source) {
      super.onDeath(source);
   }

   @Override
   public boolean canSpawn(WorldView world) {
      BlockPos _snowman = this.getBlockPos();
      BlockPos _snowmanx = _snowman.down();
      BlockState _snowmanxx = world.getBlockState(_snowmanx);
      if (!_snowmanxx.hasSolidTopSurface(world, _snowmanx, this)) {
         return false;
      } else {
         for (int _snowmanxxx = 1; _snowmanxxx < 3; _snowmanxxx++) {
            BlockPos _snowmanxxxx = _snowman.up(_snowmanxxx);
            BlockState _snowmanxxxxx = world.getBlockState(_snowmanxxxx);
            if (!SpawnHelper.isClearForSpawn(world, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxx.getFluidState(), EntityType.IRON_GOLEM)) {
               return false;
            }
         }

         return SpawnHelper.isClearForSpawn(world, _snowman, world.getBlockState(_snowman), Fluids.EMPTY.getDefaultState(), EntityType.IRON_GOLEM)
            && world.intersectsEntities(this);
      }
   }

   @Override
   public Vec3d method_29919() {
      return new Vec3d(0.0, (double)(0.875F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
   }

   public static enum Crack {
      NONE(1.0F),
      LOW(0.75F),
      MEDIUM(0.5F),
      HIGH(0.25F);

      private static final List<IronGolemEntity.Crack> VALUES = Stream.of(values())
         .sorted(Comparator.comparingDouble(_snowman -> (double)_snowman.maxHealthFraction))
         .collect(ImmutableList.toImmutableList());
      private final float maxHealthFraction;

      private Crack(float maxHealthFraction) {
         this.maxHealthFraction = maxHealthFraction;
      }

      public static IronGolemEntity.Crack from(float healthFraction) {
         for (IronGolemEntity.Crack _snowman : VALUES) {
            if (healthFraction < _snowman.maxHealthFraction) {
               return _snowman;
            }
         }

         return NONE;
      }
   }
}
