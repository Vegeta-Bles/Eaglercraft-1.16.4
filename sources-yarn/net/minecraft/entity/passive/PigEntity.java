package net.minecraft.entity.passive;

import com.google.common.collect.UnmodifiableIterator;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.SaddledComponent;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class PigEntity extends AnimalEntity implements ItemSteerable, Saddleable {
   private static final TrackedData<Boolean> SADDLED = DataTracker.registerData(PigEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Integer> BOOST_TIME = DataTracker.registerData(PigEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.CARROT, Items.POTATO, Items.BEETROOT);
   private final SaddledComponent saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED);

   public PigEntity(EntityType<? extends PigEntity> arg, World arg2) {
      super(arg, arg2);
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(0, new SwimGoal(this));
      this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
      this.goalSelector.add(3, new AnimalMateGoal(this, 1.0));
      this.goalSelector.add(4, new TemptGoal(this, 1.2, Ingredient.ofItems(Items.CARROT_ON_A_STICK), false));
      this.goalSelector.add(4, new TemptGoal(this, 1.2, false, BREEDING_INGREDIENT));
      this.goalSelector.add(5, new FollowParentGoal(this, 1.1));
      this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
      this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.add(8, new LookAroundGoal(this));
   }

   public static DefaultAttributeContainer.Builder createPigAttributes() {
      return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
   }

   @Nullable
   @Override
   public Entity getPrimaryPassenger() {
      return this.getPassengerList().isEmpty() ? null : this.getPassengerList().get(0);
   }

   @Override
   public boolean canBeControlledByRider() {
      Entity lv = this.getPrimaryPassenger();
      if (!(lv instanceof PlayerEntity)) {
         return false;
      } else {
         PlayerEntity lv2 = (PlayerEntity)lv;
         return lv2.getMainHandStack().getItem() == Items.CARROT_ON_A_STICK || lv2.getOffHandStack().getItem() == Items.CARROT_ON_A_STICK;
      }
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      if (BOOST_TIME.equals(data) && this.world.isClient) {
         this.saddledComponent.boost();
      }

      super.onTrackedDataSet(data);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(SADDLED, false);
      this.dataTracker.startTracking(BOOST_TIME, 0);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      this.saddledComponent.toTag(tag);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.saddledComponent.fromTag(tag);
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_PIG_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_PIG_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_PIG_DEATH;
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      boolean bl = this.isBreedingItem(player.getStackInHand(hand));
      if (!bl && this.isSaddled() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
         if (!this.world.isClient) {
            player.startRiding(this);
         }

         return ActionResult.success(this.world.isClient);
      } else {
         ActionResult lv = super.interactMob(player, hand);
         if (!lv.isAccepted()) {
            ItemStack lv2 = player.getStackInHand(hand);
            return lv2.getItem() == Items.SADDLE ? lv2.useOnEntity(player, this, hand) : ActionResult.PASS;
         } else {
            return lv;
         }
      }
   }

   @Override
   public boolean canBeSaddled() {
      return this.isAlive() && !this.isBaby();
   }

   @Override
   protected void dropInventory() {
      super.dropInventory();
      if (this.isSaddled()) {
         this.dropItem(Items.SADDLE);
      }
   }

   @Override
   public boolean isSaddled() {
      return this.saddledComponent.isSaddled();
   }

   @Override
   public void saddle(@Nullable SoundCategory sound) {
      this.saddledComponent.setSaddled(true);
      if (sound != null) {
         this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_PIG_SADDLE, sound, 0.5F, 1.0F);
      }
   }

   @Override
   public Vec3d updatePassengerForDismount(LivingEntity passenger) {
      Direction lv = this.getMovementDirection();
      if (lv.getAxis() == Direction.Axis.Y) {
         return super.updatePassengerForDismount(passenger);
      } else {
         int[][] is = Dismounting.getDismountOffsets(lv);
         BlockPos lv2 = this.getBlockPos();
         BlockPos.Mutable lv3 = new BlockPos.Mutable();
         UnmodifiableIterator var6 = passenger.getPoses().iterator();

         while (var6.hasNext()) {
            EntityPose lv4 = (EntityPose)var6.next();
            Box lv5 = passenger.getBoundingBox(lv4);

            for (int[] js : is) {
               lv3.set(lv2.getX() + js[0], lv2.getY(), lv2.getZ() + js[1]);
               double d = this.world.getDismountHeight(lv3);
               if (Dismounting.canDismountInBlock(d)) {
                  Vec3d lv6 = Vec3d.ofCenter(lv3, d);
                  if (Dismounting.canPlaceEntityAt(this.world, passenger, lv5.offset(lv6))) {
                     passenger.setPose(lv4);
                     return lv6;
                  }
               }
            }
         }

         return super.updatePassengerForDismount(passenger);
      }
   }

   @Override
   public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
      if (world.getDifficulty() != Difficulty.PEACEFUL) {
         ZombifiedPiglinEntity lv = EntityType.ZOMBIFIED_PIGLIN.create(world);
         lv.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
         lv.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
         lv.setAiDisabled(this.isAiDisabled());
         lv.setBaby(this.isBaby());
         if (this.hasCustomName()) {
            lv.setCustomName(this.getCustomName());
            lv.setCustomNameVisible(this.isCustomNameVisible());
         }

         lv.setPersistent();
         world.spawnEntity(lv);
         this.remove();
      } else {
         super.onStruckByLightning(world, lightning);
      }
   }

   @Override
   public void travel(Vec3d movementInput) {
      this.travel(this, this.saddledComponent, movementInput);
   }

   @Override
   public float getSaddledSpeed() {
      return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.225F;
   }

   @Override
   public void setMovementInput(Vec3d movementInput) {
      super.travel(movementInput);
   }

   @Override
   public boolean consumeOnAStickItem() {
      return this.saddledComponent.boost(this.getRandom());
   }

   public PigEntity createChild(ServerWorld arg, PassiveEntity arg2) {
      return EntityType.PIG.create(arg);
   }

   @Override
   public boolean isBreedingItem(ItemStack stack) {
      return BREEDING_INGREDIENT.test(stack);
   }

   @Environment(EnvType.CLIENT)
   @Override
   public Vec3d method_29919() {
      return new Vec3d(0.0, (double)(0.6F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
   }
}
