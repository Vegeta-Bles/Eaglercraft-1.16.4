package net.minecraft.entity.passive;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.AttackGoal;
import net.minecraft.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.FollowTargetIfTamedGoal;
import net.minecraft.entity.ai.goal.GoToOwnerAndPurrGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

public class CatEntity extends TameableEntity {
   private static final Ingredient TAMING_INGREDIENT = Ingredient.ofItems(Items.COD, Items.SALMON);
   private static final TrackedData<Integer> CAT_TYPE = DataTracker.registerData(CatEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Boolean> SLEEPING_WITH_OWNER = DataTracker.registerData(CatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Boolean> HEAD_DOWN = DataTracker.registerData(CatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Integer> COLLAR_COLOR = DataTracker.registerData(CatEntity.class, TrackedDataHandlerRegistry.INTEGER);
   public static final Map<Integer, Identifier> TEXTURES = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put(0, new Identifier("textures/entity/cat/tabby.png"));
      _snowman.put(1, new Identifier("textures/entity/cat/black.png"));
      _snowman.put(2, new Identifier("textures/entity/cat/red.png"));
      _snowman.put(3, new Identifier("textures/entity/cat/siamese.png"));
      _snowman.put(4, new Identifier("textures/entity/cat/british_shorthair.png"));
      _snowman.put(5, new Identifier("textures/entity/cat/calico.png"));
      _snowman.put(6, new Identifier("textures/entity/cat/persian.png"));
      _snowman.put(7, new Identifier("textures/entity/cat/ragdoll.png"));
      _snowman.put(8, new Identifier("textures/entity/cat/white.png"));
      _snowman.put(9, new Identifier("textures/entity/cat/jellie.png"));
      _snowman.put(10, new Identifier("textures/entity/cat/all_black.png"));
   });
   private CatEntity.CatFleeGoal<PlayerEntity> fleeGoal;
   private net.minecraft.entity.ai.goal.TemptGoal temptGoal;
   private float sleepAnimation;
   private float prevSleepAnimation;
   private float tailCurlAnimation;
   private float prevTailCurlAnimation;
   private float headDownAnimation;
   private float prevHeadDownAnimation;

   public CatEntity(EntityType<? extends CatEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public Identifier getTexture() {
      return TEXTURES.getOrDefault(this.getCatType(), TEXTURES.get(0));
   }

   @Override
   protected void initGoals() {
      this.temptGoal = new CatEntity.TemptGoal(this, 0.6, TAMING_INGREDIENT, true);
      this.goalSelector.add(1, new SwimGoal(this));
      this.goalSelector.add(1, new SitGoal(this));
      this.goalSelector.add(2, new CatEntity.SleepWithOwnerGoal(this));
      this.goalSelector.add(3, this.temptGoal);
      this.goalSelector.add(5, new GoToOwnerAndPurrGoal(this, 1.1, 8));
      this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0, 10.0F, 5.0F, false));
      this.goalSelector.add(7, new CatSitOnBlockGoal(this, 0.8));
      this.goalSelector.add(8, new PounceAtTargetGoal(this, 0.3F));
      this.goalSelector.add(9, new AttackGoal(this));
      this.goalSelector.add(10, new AnimalMateGoal(this, 0.8));
      this.goalSelector.add(11, new WanderAroundFarGoal(this, 0.8, 1.0000001E-5F));
      this.goalSelector.add(12, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
      this.targetSelector.add(1, new FollowTargetIfTamedGoal<>(this, RabbitEntity.class, false, null));
      this.targetSelector.add(1, new FollowTargetIfTamedGoal<>(this, TurtleEntity.class, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
   }

   public int getCatType() {
      return this.dataTracker.get(CAT_TYPE);
   }

   public void setCatType(int type) {
      if (type < 0 || type >= 11) {
         type = this.random.nextInt(10);
      }

      this.dataTracker.set(CAT_TYPE, type);
   }

   public void setSleepingWithOwner(boolean sleeping) {
      this.dataTracker.set(SLEEPING_WITH_OWNER, sleeping);
   }

   public boolean isSleepingWithOwner() {
      return this.dataTracker.get(SLEEPING_WITH_OWNER);
   }

   public void setHeadDown(boolean headDown) {
      this.dataTracker.set(HEAD_DOWN, headDown);
   }

   public boolean isHeadDown() {
      return this.dataTracker.get(HEAD_DOWN);
   }

   public DyeColor getCollarColor() {
      return DyeColor.byId(this.dataTracker.get(COLLAR_COLOR));
   }

   public void setCollarColor(DyeColor color) {
      this.dataTracker.set(COLLAR_COLOR, color.getId());
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(CAT_TYPE, 1);
      this.dataTracker.startTracking(SLEEPING_WITH_OWNER, false);
      this.dataTracker.startTracking(HEAD_DOWN, false);
      this.dataTracker.startTracking(COLLAR_COLOR, DyeColor.RED.getId());
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("CatType", this.getCatType());
      tag.putByte("CollarColor", (byte)this.getCollarColor().getId());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.setCatType(tag.getInt("CatType"));
      if (tag.contains("CollarColor", 99)) {
         this.setCollarColor(DyeColor.byId(tag.getInt("CollarColor")));
      }
   }

   @Override
   public void mobTick() {
      if (this.getMoveControl().isMoving()) {
         double _snowman = this.getMoveControl().getSpeed();
         if (_snowman == 0.6) {
            this.setPose(EntityPose.CROUCHING);
            this.setSprinting(false);
         } else if (_snowman == 1.33) {
            this.setPose(EntityPose.STANDING);
            this.setSprinting(true);
         } else {
            this.setPose(EntityPose.STANDING);
            this.setSprinting(false);
         }
      } else {
         this.setPose(EntityPose.STANDING);
         this.setSprinting(false);
      }
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      if (this.isTamed()) {
         if (this.isInLove()) {
            return SoundEvents.ENTITY_CAT_PURR;
         } else {
            return this.random.nextInt(4) == 0 ? SoundEvents.ENTITY_CAT_PURREOW : SoundEvents.ENTITY_CAT_AMBIENT;
         }
      } else {
         return SoundEvents.ENTITY_CAT_STRAY_AMBIENT;
      }
   }

   @Override
   public int getMinAmbientSoundDelay() {
      return 120;
   }

   public void hiss() {
      this.playSound(SoundEvents.ENTITY_CAT_HISS, this.getSoundVolume(), this.getSoundPitch());
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_CAT_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_CAT_DEATH;
   }

   public static DefaultAttributeContainer.Builder createCatAttributes() {
      return MobEntity.createMobAttributes()
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F)
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      return false;
   }

   @Override
   protected void eat(PlayerEntity player, ItemStack stack) {
      if (this.isBreedingItem(stack)) {
         this.playSound(SoundEvents.ENTITY_CAT_EAT, 1.0F, 1.0F);
      }

      super.eat(player, stack);
   }

   private float getAttackDamage() {
      return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
   }

   @Override
   public boolean tryAttack(Entity target) {
      return target.damage(DamageSource.mob(this), this.getAttackDamage());
   }

   @Override
   public void tick() {
      super.tick();
      if (this.temptGoal != null && this.temptGoal.isActive() && !this.isTamed() && this.age % 100 == 0) {
         this.playSound(SoundEvents.ENTITY_CAT_BEG_FOR_FOOD, 1.0F, 1.0F);
      }

      this.updateAnimations();
   }

   private void updateAnimations() {
      if ((this.isSleepingWithOwner() || this.isHeadDown()) && this.age % 5 == 0) {
         this.playSound(SoundEvents.ENTITY_CAT_PURR, 0.6F + 0.4F * (this.random.nextFloat() - this.random.nextFloat()), 1.0F);
      }

      this.updateSleepAnimation();
      this.updateHeadDownAnimation();
   }

   private void updateSleepAnimation() {
      this.prevSleepAnimation = this.sleepAnimation;
      this.prevTailCurlAnimation = this.tailCurlAnimation;
      if (this.isSleepingWithOwner()) {
         this.sleepAnimation = Math.min(1.0F, this.sleepAnimation + 0.15F);
         this.tailCurlAnimation = Math.min(1.0F, this.tailCurlAnimation + 0.08F);
      } else {
         this.sleepAnimation = Math.max(0.0F, this.sleepAnimation - 0.22F);
         this.tailCurlAnimation = Math.max(0.0F, this.tailCurlAnimation - 0.13F);
      }
   }

   private void updateHeadDownAnimation() {
      this.prevHeadDownAnimation = this.headDownAnimation;
      if (this.isHeadDown()) {
         this.headDownAnimation = Math.min(1.0F, this.headDownAnimation + 0.1F);
      } else {
         this.headDownAnimation = Math.max(0.0F, this.headDownAnimation - 0.13F);
      }
   }

   public float getSleepAnimation(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.prevSleepAnimation, this.sleepAnimation);
   }

   public float getTailCurlAnimation(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.prevTailCurlAnimation, this.tailCurlAnimation);
   }

   public float getHeadDownAnimation(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.prevHeadDownAnimation, this.headDownAnimation);
   }

   public CatEntity createChild(ServerWorld _snowman, PassiveEntity _snowman) {
      CatEntity _snowmanxx = EntityType.CAT.create(_snowman);
      if (_snowman instanceof CatEntity) {
         if (this.random.nextBoolean()) {
            _snowmanxx.setCatType(this.getCatType());
         } else {
            _snowmanxx.setCatType(((CatEntity)_snowman).getCatType());
         }

         if (this.isTamed()) {
            _snowmanxx.setOwnerUuid(this.getOwnerUuid());
            _snowmanxx.setTamed(true);
            if (this.random.nextBoolean()) {
               _snowmanxx.setCollarColor(this.getCollarColor());
            } else {
               _snowmanxx.setCollarColor(((CatEntity)_snowman).getCollarColor());
            }
         }
      }

      return _snowmanxx;
   }

   @Override
   public boolean canBreedWith(AnimalEntity other) {
      if (!this.isTamed()) {
         return false;
      } else if (!(other instanceof CatEntity)) {
         return false;
      } else {
         CatEntity _snowman = (CatEntity)other;
         return _snowman.isTamed() && super.canBreedWith(other);
      }
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      entityData = super.initialize(world, difficulty, spawnReason, entityData, entityTag);
      if (world.getMoonSize() > 0.9F) {
         this.setCatType(this.random.nextInt(11));
      } else {
         this.setCatType(this.random.nextInt(10));
      }

      World _snowman = world.toServerWorld();
      if (_snowman instanceof ServerWorld
         && ((ServerWorld)_snowman).getStructureAccessor().getStructureAt(this.getBlockPos(), true, StructureFeature.SWAMP_HUT).hasChildren()) {
         this.setCatType(10);
         this.setPersistent();
      }

      return entityData;
   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      Item _snowmanx = _snowman.getItem();
      if (this.world.isClient) {
         if (this.isTamed() && this.isOwner(player)) {
            return ActionResult.SUCCESS;
         } else {
            return !this.isBreedingItem(_snowman) || !(this.getHealth() < this.getMaxHealth()) && this.isTamed() ? ActionResult.PASS : ActionResult.SUCCESS;
         }
      } else {
         if (this.isTamed()) {
            if (this.isOwner(player)) {
               if (!(_snowmanx instanceof DyeItem)) {
                  if (_snowmanx.isFood() && this.isBreedingItem(_snowman) && this.getHealth() < this.getMaxHealth()) {
                     this.eat(player, _snowman);
                     this.heal((float)_snowmanx.getFoodComponent().getHunger());
                     return ActionResult.CONSUME;
                  }

                  ActionResult _snowmanxx = super.interactMob(player, hand);
                  if (!_snowmanxx.isAccepted() || this.isBaby()) {
                     this.setSitting(!this.isSitting());
                  }

                  return _snowmanxx;
               }

               DyeColor _snowmanxx = ((DyeItem)_snowmanx).getColor();
               if (_snowmanxx != this.getCollarColor()) {
                  this.setCollarColor(_snowmanxx);
                  if (!player.abilities.creativeMode) {
                     _snowman.decrement(1);
                  }

                  this.setPersistent();
                  return ActionResult.CONSUME;
               }
            }
         } else if (this.isBreedingItem(_snowman)) {
            this.eat(player, _snowman);
            if (this.random.nextInt(3) == 0) {
               this.setOwner(player);
               this.setSitting(true);
               this.world.sendEntityStatus(this, (byte)7);
            } else {
               this.world.sendEntityStatus(this, (byte)6);
            }

            this.setPersistent();
            return ActionResult.CONSUME;
         }

         ActionResult _snowmanxx = super.interactMob(player, hand);
         if (_snowmanxx.isAccepted()) {
            this.setPersistent();
         }

         return _snowmanxx;
      }
   }

   @Override
   public boolean isBreedingItem(ItemStack stack) {
      return TAMING_INGREDIENT.test(stack);
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return dimensions.height * 0.5F;
   }

   @Override
   public boolean canImmediatelyDespawn(double distanceSquared) {
      return !this.isTamed() && this.age > 2400;
   }

   @Override
   protected void onTamedChanged() {
      if (this.fleeGoal == null) {
         this.fleeGoal = new CatEntity.CatFleeGoal<>(this, PlayerEntity.class, 16.0F, 0.8, 1.33);
      }

      this.goalSelector.remove(this.fleeGoal);
      if (!this.isTamed()) {
         this.goalSelector.add(4, this.fleeGoal);
      }
   }

   static class CatFleeGoal<T extends LivingEntity> extends FleeEntityGoal<T> {
      private final CatEntity cat;

      public CatFleeGoal(CatEntity cat, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
         super(cat, fleeFromType, distance, slowSpeed, fastSpeed, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
         this.cat = cat;
      }

      @Override
      public boolean canStart() {
         return !this.cat.isTamed() && super.canStart();
      }

      @Override
      public boolean shouldContinue() {
         return !this.cat.isTamed() && super.shouldContinue();
      }
   }

   static class SleepWithOwnerGoal extends Goal {
      private final CatEntity cat;
      private PlayerEntity owner;
      private BlockPos bedPos;
      private int ticksOnBed;

      public SleepWithOwnerGoal(CatEntity cat) {
         this.cat = cat;
      }

      @Override
      public boolean canStart() {
         if (!this.cat.isTamed()) {
            return false;
         } else if (this.cat.isSitting()) {
            return false;
         } else {
            LivingEntity _snowman = this.cat.getOwner();
            if (_snowman instanceof PlayerEntity) {
               this.owner = (PlayerEntity)_snowman;
               if (!_snowman.isSleeping()) {
                  return false;
               }

               if (this.cat.squaredDistanceTo(this.owner) > 100.0) {
                  return false;
               }

               BlockPos _snowmanx = this.owner.getBlockPos();
               BlockState _snowmanxx = this.cat.world.getBlockState(_snowmanx);
               if (_snowmanxx.getBlock().isIn(BlockTags.BEDS)) {
                  this.bedPos = _snowmanxx.method_28500(BedBlock.FACING).map(_snowmanxxx -> _snowman.offset(_snowmanxxx.getOpposite())).orElseGet(() -> new BlockPos(_snowman));
                  return !this.method_16098();
               }
            }

            return false;
         }
      }

      private boolean method_16098() {
         for (CatEntity _snowman : this.cat.world.getNonSpectatingEntities(CatEntity.class, new Box(this.bedPos).expand(2.0))) {
            if (_snowman != this.cat && (_snowman.isSleepingWithOwner() || _snowman.isHeadDown())) {
               return true;
            }
         }

         return false;
      }

      @Override
      public boolean shouldContinue() {
         return this.cat.isTamed() && !this.cat.isSitting() && this.owner != null && this.owner.isSleeping() && this.bedPos != null && !this.method_16098();
      }

      @Override
      public void start() {
         if (this.bedPos != null) {
            this.cat.setInSittingPose(false);
            this.cat.getNavigation().startMovingTo((double)this.bedPos.getX(), (double)this.bedPos.getY(), (double)this.bedPos.getZ(), 1.1F);
         }
      }

      @Override
      public void stop() {
         this.cat.setSleepingWithOwner(false);
         float _snowman = this.cat.world.getSkyAngle(1.0F);
         if (this.owner.getSleepTimer() >= 100 && (double)_snowman > 0.77 && (double)_snowman < 0.8 && (double)this.cat.world.getRandom().nextFloat() < 0.7) {
            this.dropMorningGifts();
         }

         this.ticksOnBed = 0;
         this.cat.setHeadDown(false);
         this.cat.getNavigation().stop();
      }

      private void dropMorningGifts() {
         Random _snowman = this.cat.getRandom();
         BlockPos.Mutable _snowmanx = new BlockPos.Mutable();
         _snowmanx.set(this.cat.getBlockPos());
         this.cat.teleport((double)(_snowmanx.getX() + _snowman.nextInt(11) - 5), (double)(_snowmanx.getY() + _snowman.nextInt(5) - 2), (double)(_snowmanx.getZ() + _snowman.nextInt(11) - 5), false);
         _snowmanx.set(this.cat.getBlockPos());
         LootTable _snowmanxx = this.cat.world.getServer().getLootManager().getTable(LootTables.CAT_MORNING_GIFT_GAMEPLAY);
         LootContext.Builder _snowmanxxx = new LootContext.Builder((ServerWorld)this.cat.world)
            .parameter(LootContextParameters.ORIGIN, this.cat.getPos())
            .parameter(LootContextParameters.THIS_ENTITY, this.cat)
            .random(_snowman);

         for (ItemStack _snowmanxxxx : _snowmanxx.generateLoot(_snowmanxxx.build(LootContextTypes.GIFT))) {
            this.cat
               .world
               .spawnEntity(
                  new ItemEntity(
                     this.cat.world,
                     (double)_snowmanx.getX() - (double)MathHelper.sin(this.cat.bodyYaw * (float) (Math.PI / 180.0)),
                     (double)_snowmanx.getY(),
                     (double)_snowmanx.getZ() + (double)MathHelper.cos(this.cat.bodyYaw * (float) (Math.PI / 180.0)),
                     _snowmanxxxx
                  )
               );
         }
      }

      @Override
      public void tick() {
         if (this.owner != null && this.bedPos != null) {
            this.cat.setInSittingPose(false);
            this.cat.getNavigation().startMovingTo((double)this.bedPos.getX(), (double)this.bedPos.getY(), (double)this.bedPos.getZ(), 1.1F);
            if (this.cat.squaredDistanceTo(this.owner) < 2.5) {
               this.ticksOnBed++;
               if (this.ticksOnBed > 16) {
                  this.cat.setSleepingWithOwner(true);
                  this.cat.setHeadDown(false);
               } else {
                  this.cat.lookAtEntity(this.owner, 45.0F, 45.0F);
                  this.cat.setHeadDown(true);
               }
            } else {
               this.cat.setSleepingWithOwner(false);
            }
         }
      }
   }

   static class TemptGoal extends net.minecraft.entity.ai.goal.TemptGoal {
      @Nullable
      private PlayerEntity player;
      private final CatEntity cat;

      public TemptGoal(CatEntity cat, double speed, Ingredient food, boolean canBeScared) {
         super(cat, speed, food, canBeScared);
         this.cat = cat;
      }

      @Override
      public void tick() {
         super.tick();
         if (this.player == null && this.mob.getRandom().nextInt(600) == 0) {
            this.player = this.closestPlayer;
         } else if (this.mob.getRandom().nextInt(500) == 0) {
            this.player = null;
         }
      }

      @Override
      protected boolean canBeScared() {
         return this.player != null && this.player.equals(this.closestPlayer) ? false : super.canBeScared();
      }

      @Override
      public boolean canStart() {
         return super.canStart() && !this.cat.isTamed();
      }
   }
}
