package net.minecraft.entity.mob;

import java.util.Collection;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class CreeperEntity extends HostileEntity implements SkinOverlayOwner {
   private static final TrackedData<Integer> FUSE_SPEED = DataTracker.registerData(CreeperEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Boolean> CHARGED = DataTracker.registerData(CreeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Boolean> IGNITED = DataTracker.registerData(CreeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private int lastFuseTime;
   private int currentFuseTime;
   private int fuseTime = 30;
   private int explosionRadius = 3;
   private int headsDropped;

   public CreeperEntity(EntityType<? extends CreeperEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(1, new SwimGoal(this));
      this.goalSelector.add(2, new CreeperIgniteGoal(this));
      this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0, 1.2));
      this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0, 1.2));
      this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
      this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.add(6, new LookAroundGoal(this));
      this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, true));
      this.targetSelector.add(2, new RevengeGoal(this));
   }

   public static DefaultAttributeContainer.Builder createCreeperAttributes() {
      return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
   }

   @Override
   public int getSafeFallDistance() {
      return this.getTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      boolean _snowman = super.handleFallDamage(fallDistance, damageMultiplier);
      this.currentFuseTime = (int)((float)this.currentFuseTime + fallDistance * 1.5F);
      if (this.currentFuseTime > this.fuseTime - 5) {
         this.currentFuseTime = this.fuseTime - 5;
      }

      return _snowman;
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(FUSE_SPEED, -1);
      this.dataTracker.startTracking(CHARGED, false);
      this.dataTracker.startTracking(IGNITED, false);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      if (this.dataTracker.get(CHARGED)) {
         tag.putBoolean("powered", true);
      }

      tag.putShort("Fuse", (short)this.fuseTime);
      tag.putByte("ExplosionRadius", (byte)this.explosionRadius);
      tag.putBoolean("ignited", this.getIgnited());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.dataTracker.set(CHARGED, tag.getBoolean("powered"));
      if (tag.contains("Fuse", 99)) {
         this.fuseTime = tag.getShort("Fuse");
      }

      if (tag.contains("ExplosionRadius", 99)) {
         this.explosionRadius = tag.getByte("ExplosionRadius");
      }

      if (tag.getBoolean("ignited")) {
         this.ignite();
      }
   }

   @Override
   public void tick() {
      if (this.isAlive()) {
         this.lastFuseTime = this.currentFuseTime;
         if (this.getIgnited()) {
            this.setFuseSpeed(1);
         }

         int _snowman = this.getFuseSpeed();
         if (_snowman > 0 && this.currentFuseTime == 0) {
            this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
         }

         this.currentFuseTime += _snowman;
         if (this.currentFuseTime < 0) {
            this.currentFuseTime = 0;
         }

         if (this.currentFuseTime >= this.fuseTime) {
            this.currentFuseTime = this.fuseTime;
            this.explode();
         }
      }

      super.tick();
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_CREEPER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_CREEPER_DEATH;
   }

   @Override
   protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
      super.dropEquipment(source, lootingMultiplier, allowDrops);
      Entity _snowman = source.getAttacker();
      if (_snowman != this && _snowman instanceof CreeperEntity) {
         CreeperEntity _snowmanx = (CreeperEntity)_snowman;
         if (_snowmanx.shouldDropHead()) {
            _snowmanx.onHeadDropped();
            this.dropItem(Items.CREEPER_HEAD);
         }
      }
   }

   @Override
   public boolean tryAttack(Entity target) {
      return true;
   }

   @Override
   public boolean shouldRenderOverlay() {
      return this.dataTracker.get(CHARGED);
   }

   public float getClientFuseTime(float timeDelta) {
      return MathHelper.lerp(timeDelta, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
   }

   public int getFuseSpeed() {
      return this.dataTracker.get(FUSE_SPEED);
   }

   public void setFuseSpeed(int fuseSpeed) {
      this.dataTracker.set(FUSE_SPEED, fuseSpeed);
   }

   @Override
   public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
      super.onStruckByLightning(world, lightning);
      this.dataTracker.set(CHARGED, true);
   }

   @Override
   protected ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (_snowman.getItem() == Items.FLINT_AND_STEEL) {
         this.world
            .playSound(
               player,
               this.getX(),
               this.getY(),
               this.getZ(),
               SoundEvents.ITEM_FLINTANDSTEEL_USE,
               this.getSoundCategory(),
               1.0F,
               this.random.nextFloat() * 0.4F + 0.8F
            );
         if (!this.world.isClient) {
            this.ignite();
            _snowman.damage(1, player, _snowmanx -> _snowmanx.sendToolBreakStatus(hand));
         }

         return ActionResult.success(this.world.isClient);
      } else {
         return super.interactMob(player, hand);
      }
   }

   private void explode() {
      if (!this.world.isClient) {
         Explosion.DestructionType _snowman = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)
            ? Explosion.DestructionType.DESTROY
            : Explosion.DestructionType.NONE;
         float _snowmanx = this.shouldRenderOverlay() ? 2.0F : 1.0F;
         this.dead = true;
         this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * _snowmanx, _snowman);
         this.remove();
         this.spawnEffectsCloud();
      }
   }

   private void spawnEffectsCloud() {
      Collection<StatusEffectInstance> _snowman = this.getStatusEffects();
      if (!_snowman.isEmpty()) {
         AreaEffectCloudEntity _snowmanx = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
         _snowmanx.setRadius(2.5F);
         _snowmanx.setRadiusOnUse(-0.5F);
         _snowmanx.setWaitTime(10);
         _snowmanx.setDuration(_snowmanx.getDuration() / 2);
         _snowmanx.setRadiusGrowth(-_snowmanx.getRadius() / (float)_snowmanx.getDuration());

         for (StatusEffectInstance _snowmanxx : _snowman) {
            _snowmanx.addEffect(new StatusEffectInstance(_snowmanxx));
         }

         this.world.spawnEntity(_snowmanx);
      }
   }

   public boolean getIgnited() {
      return this.dataTracker.get(IGNITED);
   }

   public void ignite() {
      this.dataTracker.set(IGNITED, true);
   }

   public boolean shouldDropHead() {
      return this.shouldRenderOverlay() && this.headsDropped < 1;
   }

   public void onHeadDropped() {
      this.headsDropped++;
   }
}
