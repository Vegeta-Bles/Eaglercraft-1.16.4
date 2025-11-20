package net.minecraft.entity.mob;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class RavagerEntity extends RaiderEntity {
   private static final Predicate<Entity> IS_NOT_RAVAGER = entity -> entity.isAlive() && !(entity instanceof RavagerEntity);
   private int attackTick;
   private int stunTick;
   private int roarTick;

   public RavagerEntity(EntityType<? extends RavagerEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.stepHeight = 1.0F;
      this.experiencePoints = 20;
   }

   @Override
   protected void initGoals() {
      super.initGoals();
      this.goalSelector.add(0, new SwimGoal(this));
      this.goalSelector.add(4, new RavagerEntity.AttackGoal());
      this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.4));
      this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
      this.targetSelector.add(2, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge());
      this.targetSelector.add(3, new FollowTargetGoal<>(this, PlayerEntity.class, true));
      this.targetSelector.add(4, new FollowTargetGoal<>(this, MerchantEntity.class, true));
      this.targetSelector.add(4, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
   }

   @Override
   protected void updateGoalControls() {
      boolean _snowman = !(this.getPrimaryPassenger() instanceof MobEntity) || this.getPrimaryPassenger().getType().isIn(EntityTypeTags.RAIDERS);
      boolean _snowmanx = !(this.getVehicle() instanceof BoatEntity);
      this.goalSelector.setControlEnabled(Goal.Control.MOVE, _snowman);
      this.goalSelector.setControlEnabled(Goal.Control.JUMP, _snowman && _snowmanx);
      this.goalSelector.setControlEnabled(Goal.Control.LOOK, _snowman);
      this.goalSelector.setControlEnabled(Goal.Control.TARGET, _snowman);
   }

   public static DefaultAttributeContainer.Builder createRavagerAttributes() {
      return HostileEntity.createHostileAttributes()
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
         .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.75)
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0)
         .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5)
         .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("AttackTick", this.attackTick);
      tag.putInt("StunTick", this.stunTick);
      tag.putInt("RoarTick", this.roarTick);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.attackTick = tag.getInt("AttackTick");
      this.stunTick = tag.getInt("StunTick");
      this.roarTick = tag.getInt("RoarTick");
   }

   @Override
   public SoundEvent getCelebratingSound() {
      return SoundEvents.ENTITY_RAVAGER_CELEBRATE;
   }

   @Override
   protected EntityNavigation createNavigation(World world) {
      return new RavagerEntity.Navigation(this, world);
   }

   @Override
   public int getBodyYawSpeed() {
      return 45;
   }

   @Override
   public double getMountedHeightOffset() {
      return 2.1;
   }

   @Override
   public boolean canBeControlledByRider() {
      return !this.isAiDisabled() && this.getPrimaryPassenger() instanceof LivingEntity;
   }

   @Nullable
   @Override
   public Entity getPrimaryPassenger() {
      return this.getPassengerList().isEmpty() ? null : this.getPassengerList().get(0);
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      if (this.isAlive()) {
         if (this.isImmobile()) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.0);
         } else {
            double _snowman = this.getTarget() != null ? 0.35 : 0.3;
            double _snowmanx = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getBaseValue();
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(MathHelper.lerp(0.1, _snowmanx, _snowman));
         }

         if (this.horizontalCollision && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            boolean _snowman = false;
            Box _snowmanx = this.getBoundingBox().expand(0.2);

            for (BlockPos _snowmanxx : BlockPos.iterate(
               MathHelper.floor(_snowmanx.minX),
               MathHelper.floor(_snowmanx.minY),
               MathHelper.floor(_snowmanx.minZ),
               MathHelper.floor(_snowmanx.maxX),
               MathHelper.floor(_snowmanx.maxY),
               MathHelper.floor(_snowmanx.maxZ)
            )) {
               BlockState _snowmanxxx = this.world.getBlockState(_snowmanxx);
               Block _snowmanxxxx = _snowmanxxx.getBlock();
               if (_snowmanxxxx instanceof LeavesBlock) {
                  _snowman = this.world.breakBlock(_snowmanxx, true, this) || _snowman;
               }
            }

            if (!_snowman && this.onGround) {
               this.jump();
            }
         }

         if (this.roarTick > 0) {
            this.roarTick--;
            if (this.roarTick == 10) {
               this.roar();
            }
         }

         if (this.attackTick > 0) {
            this.attackTick--;
         }

         if (this.stunTick > 0) {
            this.stunTick--;
            this.spawnStunnedParticles();
            if (this.stunTick == 0) {
               this.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 1.0F, 1.0F);
               this.roarTick = 20;
            }
         }
      }
   }

   private void spawnStunnedParticles() {
      if (this.random.nextInt(6) == 0) {
         double _snowman = this.getX()
            - (double)this.getWidth() * Math.sin((double)(this.bodyYaw * (float) (Math.PI / 180.0)))
            + (this.random.nextDouble() * 0.6 - 0.3);
         double _snowmanx = this.getY() + (double)this.getHeight() - 0.3;
         double _snowmanxx = this.getZ()
            + (double)this.getWidth() * Math.cos((double)(this.bodyYaw * (float) (Math.PI / 180.0)))
            + (this.random.nextDouble() * 0.6 - 0.3);
         this.world.addParticle(ParticleTypes.ENTITY_EFFECT, _snowman, _snowmanx, _snowmanxx, 0.4980392156862745, 0.5137254901960784, 0.5725490196078431);
      }
   }

   @Override
   protected boolean isImmobile() {
      return super.isImmobile() || this.attackTick > 0 || this.stunTick > 0 || this.roarTick > 0;
   }

   @Override
   public boolean canSee(Entity entity) {
      return this.stunTick <= 0 && this.roarTick <= 0 ? super.canSee(entity) : false;
   }

   @Override
   protected void knockback(LivingEntity target) {
      if (this.roarTick == 0) {
         if (this.random.nextDouble() < 0.5) {
            this.stunTick = 40;
            this.playSound(SoundEvents.ENTITY_RAVAGER_STUNNED, 1.0F, 1.0F);
            this.world.sendEntityStatus(this, (byte)39);
            target.pushAwayFrom(this);
         } else {
            this.knockBack(target);
         }

         target.velocityModified = true;
      }
   }

   private void roar() {
      if (this.isAlive()) {
         for (Entity _snowman : this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(4.0), IS_NOT_RAVAGER)) {
            if (!(_snowman instanceof IllagerEntity)) {
               _snowman.damage(DamageSource.mob(this), 6.0F);
            }

            this.knockBack(_snowman);
         }

         Vec3d _snowman = this.getBoundingBox().getCenter();

         for (int _snowmanx = 0; _snowmanx < 40; _snowmanx++) {
            double _snowmanxx = this.random.nextGaussian() * 0.2;
            double _snowmanxxx = this.random.nextGaussian() * 0.2;
            double _snowmanxxxx = this.random.nextGaussian() * 0.2;
            this.world.addParticle(ParticleTypes.POOF, _snowman.x, _snowman.y, _snowman.z, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         }
      }
   }

   private void knockBack(Entity entity) {
      double _snowman = entity.getX() - this.getX();
      double _snowmanx = entity.getZ() - this.getZ();
      double _snowmanxx = Math.max(_snowman * _snowman + _snowmanx * _snowmanx, 0.001);
      entity.addVelocity(_snowman / _snowmanxx * 4.0, 0.2, _snowmanx / _snowmanxx * 4.0);
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 4) {
         this.attackTick = 10;
         this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
      } else if (status == 39) {
         this.stunTick = 40;
      }

      super.handleStatus(status);
   }

   public int getAttackTick() {
      return this.attackTick;
   }

   public int getStunTick() {
      return this.stunTick;
   }

   public int getRoarTick() {
      return this.roarTick;
   }

   @Override
   public boolean tryAttack(Entity target) {
      this.attackTick = 10;
      this.world.sendEntityStatus(this, (byte)4);
      this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
      return super.tryAttack(target);
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_RAVAGER_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_RAVAGER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_RAVAGER_DEATH;
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.15F, 1.0F);
   }

   @Override
   public boolean canSpawn(WorldView world) {
      return !world.containsFluid(this.getBoundingBox());
   }

   @Override
   public void addBonusForWave(int wave, boolean unused) {
   }

   @Override
   public boolean canLead() {
      return false;
   }

   class AttackGoal extends MeleeAttackGoal {
      public AttackGoal() {
         super(RavagerEntity.this, 1.0, true);
      }

      @Override
      protected double getSquaredMaxAttackDistance(LivingEntity entity) {
         float _snowman = RavagerEntity.this.getWidth() - 0.1F;
         return (double)(_snowman * 2.0F * _snowman * 2.0F + entity.getWidth());
      }
   }

   static class Navigation extends MobNavigation {
      public Navigation(MobEntity _snowman, World _snowman) {
         super(_snowman, _snowman);
      }

      @Override
      protected PathNodeNavigator createPathNodeNavigator(int range) {
         this.nodeMaker = new RavagerEntity.PathNodeMaker();
         return new PathNodeNavigator(this.nodeMaker, range);
      }
   }

   static class PathNodeMaker extends LandPathNodeMaker {
      private PathNodeMaker() {
      }

      @Override
      protected PathNodeType adjustNodeType(BlockView world, boolean canOpenDoors, boolean canEnterOpenDoors, BlockPos pos, PathNodeType type) {
         return type == PathNodeType.LEAVES ? PathNodeType.OPEN : super.adjustNodeType(world, canOpenDoors, canEnterOpenDoors, pos, type);
      }
   }
}
