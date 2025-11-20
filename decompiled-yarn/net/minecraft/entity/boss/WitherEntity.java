package net.minecraft.entity.boss;

import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class WitherEntity extends HostileEntity implements SkinOverlayOwner, RangedAttackMob {
   private static final TrackedData<Integer> TRACKED_ENTITY_ID_1 = DataTracker.registerData(WitherEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Integer> TRACKED_ENTITY_ID_2 = DataTracker.registerData(WitherEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Integer> TRACKED_ENTITY_ID_3 = DataTracker.registerData(WitherEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final List<TrackedData<Integer>> TRACKED_ENTITY_IDS = ImmutableList.of(TRACKED_ENTITY_ID_1, TRACKED_ENTITY_ID_2, TRACKED_ENTITY_ID_3);
   private static final TrackedData<Integer> INVUL_TIMER = DataTracker.registerData(WitherEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private final float[] sideHeadPitches = new float[2];
   private final float[] sideHeadYaws = new float[2];
   private final float[] prevSideHeadPitches = new float[2];
   private final float[] prevSideHeadYaws = new float[2];
   private final int[] field_7091 = new int[2];
   private final int[] field_7092 = new int[2];
   private int field_7082;
   private final ServerBossBar bossBar = (ServerBossBar)new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS)
      .setDarkenSky(true);
   private static final Predicate<LivingEntity> CAN_ATTACK_PREDICATE = _snowman -> _snowman.getGroup() != EntityGroup.UNDEAD && _snowman.isMobOrPlayer();
   private static final TargetPredicate HEAD_TARGET_PREDICATE = new TargetPredicate().setBaseMaxDistance(20.0).setPredicate(CAN_ATTACK_PREDICATE);

   public WitherEntity(EntityType<? extends WitherEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.setHealth(this.getMaxHealth());
      this.getNavigation().setCanSwim(true);
      this.experiencePoints = 50;
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(0, new WitherEntity.DescendAtHalfHealthGoal());
      this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0, 40, 20.0F));
      this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
      this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.add(7, new LookAroundGoal(this));
      this.targetSelector.add(1, new RevengeGoal(this));
      this.targetSelector.add(2, new FollowTargetGoal<>(this, MobEntity.class, 0, false, false, CAN_ATTACK_PREDICATE));
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(TRACKED_ENTITY_ID_1, 0);
      this.dataTracker.startTracking(TRACKED_ENTITY_ID_2, 0);
      this.dataTracker.startTracking(TRACKED_ENTITY_ID_3, 0);
      this.dataTracker.startTracking(INVUL_TIMER, 0);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("Invul", this.getInvulnerableTimer());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.setInvulTimer(tag.getInt("Invul"));
      if (this.hasCustomName()) {
         this.bossBar.setName(this.getDisplayName());
      }
   }

   @Override
   public void setCustomName(@Nullable Text name) {
      super.setCustomName(name);
      this.bossBar.setName(this.getDisplayName());
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_WITHER_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_WITHER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_WITHER_DEATH;
   }

   @Override
   public void tickMovement() {
      Vec3d _snowman = this.getVelocity().multiply(1.0, 0.6, 1.0);
      if (!this.world.isClient && this.getTrackedEntityId(0) > 0) {
         Entity _snowmanx = this.world.getEntityById(this.getTrackedEntityId(0));
         if (_snowmanx != null) {
            double _snowmanxx = _snowman.y;
            if (this.getY() < _snowmanx.getY() || !this.shouldRenderOverlay() && this.getY() < _snowmanx.getY() + 5.0) {
               _snowmanxx = Math.max(0.0, _snowmanxx);
               _snowmanxx += 0.3 - _snowmanxx * 0.6F;
            }

            _snowman = new Vec3d(_snowman.x, _snowmanxx, _snowman.z);
            Vec3d _snowmanxxx = new Vec3d(_snowmanx.getX() - this.getX(), 0.0, _snowmanx.getZ() - this.getZ());
            if (squaredHorizontalLength(_snowmanxxx) > 9.0) {
               Vec3d _snowmanxxxx = _snowmanxxx.normalize();
               _snowman = _snowman.add(_snowmanxxxx.x * 0.3 - _snowman.x * 0.6, 0.0, _snowmanxxxx.z * 0.3 - _snowman.z * 0.6);
            }
         }
      }

      this.setVelocity(_snowman);
      if (squaredHorizontalLength(_snowman) > 0.05) {
         this.yaw = (float)MathHelper.atan2(_snowman.z, _snowman.x) * (180.0F / (float)Math.PI) - 90.0F;
      }

      super.tickMovement();

      for (int _snowmanx = 0; _snowmanx < 2; _snowmanx++) {
         this.prevSideHeadYaws[_snowmanx] = this.sideHeadYaws[_snowmanx];
         this.prevSideHeadPitches[_snowmanx] = this.sideHeadPitches[_snowmanx];
      }

      for (int _snowmanx = 0; _snowmanx < 2; _snowmanx++) {
         int _snowmanxxx = this.getTrackedEntityId(_snowmanx + 1);
         Entity _snowmanxxxx = null;
         if (_snowmanxxx > 0) {
            _snowmanxxxx = this.world.getEntityById(_snowmanxxx);
         }

         if (_snowmanxxxx != null) {
            double _snowmanxxxxx = this.getHeadX(_snowmanx + 1);
            double _snowmanxxxxxx = this.getHeadY(_snowmanx + 1);
            double _snowmanxxxxxxx = this.getHeadZ(_snowmanx + 1);
            double _snowmanxxxxxxxx = _snowmanxxxx.getX() - _snowmanxxxxx;
            double _snowmanxxxxxxxxx = _snowmanxxxx.getEyeY() - _snowmanxxxxxx;
            double _snowmanxxxxxxxxxx = _snowmanxxxx.getZ() - _snowmanxxxxxxx;
            double _snowmanxxxxxxxxxxx = (double)MathHelper.sqrt(_snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx);
            float _snowmanxxxxxxxxxxxx = (float)(MathHelper.atan2(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx) * 180.0F / (float)Math.PI) - 90.0F;
            float _snowmanxxxxxxxxxxxxx = (float)(-(MathHelper.atan2(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx) * 180.0F / (float)Math.PI));
            this.sideHeadPitches[_snowmanx] = this.getNextAngle(this.sideHeadPitches[_snowmanx], _snowmanxxxxxxxxxxxxx, 40.0F);
            this.sideHeadYaws[_snowmanx] = this.getNextAngle(this.sideHeadYaws[_snowmanx], _snowmanxxxxxxxxxxxx, 10.0F);
         } else {
            this.sideHeadYaws[_snowmanx] = this.getNextAngle(this.sideHeadYaws[_snowmanx], this.bodyYaw, 10.0F);
         }
      }

      boolean _snowmanx = this.shouldRenderOverlay();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 3; _snowmanxxxxx++) {
         double _snowmanxxxxxx = this.getHeadX(_snowmanxxxxx);
         double _snowmanxxxxxxx = this.getHeadY(_snowmanxxxxx);
         double _snowmanxxxxxxxx = this.getHeadZ(_snowmanxxxxx);
         this.world
            .addParticle(
               ParticleTypes.SMOKE,
               _snowmanxxxxxx + this.random.nextGaussian() * 0.3F,
               _snowmanxxxxxxx + this.random.nextGaussian() * 0.3F,
               _snowmanxxxxxxxx + this.random.nextGaussian() * 0.3F,
               0.0,
               0.0,
               0.0
            );
         if (_snowmanx && this.world.random.nextInt(4) == 0) {
            this.world
               .addParticle(
                  ParticleTypes.ENTITY_EFFECT,
                  _snowmanxxxxxx + this.random.nextGaussian() * 0.3F,
                  _snowmanxxxxxxx + this.random.nextGaussian() * 0.3F,
                  _snowmanxxxxxxxx + this.random.nextGaussian() * 0.3F,
                  0.7F,
                  0.7F,
                  0.5
               );
         }
      }

      if (this.getInvulnerableTimer() > 0) {
         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 3; _snowmanxxxxxx++) {
            this.world
               .addParticle(
                  ParticleTypes.ENTITY_EFFECT,
                  this.getX() + this.random.nextGaussian(),
                  this.getY() + (double)(this.random.nextFloat() * 3.3F),
                  this.getZ() + this.random.nextGaussian(),
                  0.7F,
                  0.7F,
                  0.9F
               );
         }
      }
   }

   @Override
   protected void mobTick() {
      if (this.getInvulnerableTimer() > 0) {
         int _snowman = this.getInvulnerableTimer() - 1;
         if (_snowman <= 0) {
            Explosion.DestructionType _snowmanx = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)
               ? Explosion.DestructionType.DESTROY
               : Explosion.DestructionType.NONE;
            this.world.createExplosion(this, this.getX(), this.getEyeY(), this.getZ(), 7.0F, false, _snowmanx);
            if (!this.isSilent()) {
               this.world.syncGlobalEvent(1023, this.getBlockPos(), 0);
            }
         }

         this.setInvulTimer(_snowman);
         if (this.age % 10 == 0) {
            this.heal(10.0F);
         }
      } else {
         super.mobTick();

         for (int _snowmanx = 1; _snowmanx < 3; _snowmanx++) {
            if (this.age >= this.field_7091[_snowmanx - 1]) {
               this.field_7091[_snowmanx - 1] = this.age + 10 + this.random.nextInt(10);
               if ((this.world.getDifficulty() == Difficulty.NORMAL || this.world.getDifficulty() == Difficulty.HARD) && this.field_7092[_snowmanx - 1]++ > 15) {
                  float _snowmanxx = 10.0F;
                  float _snowmanxxx = 5.0F;
                  double _snowmanxxxx = MathHelper.nextDouble(this.random, this.getX() - 10.0, this.getX() + 10.0);
                  double _snowmanxxxxx = MathHelper.nextDouble(this.random, this.getY() - 5.0, this.getY() + 5.0);
                  double _snowmanxxxxxx = MathHelper.nextDouble(this.random, this.getZ() - 10.0, this.getZ() + 10.0);
                  this.method_6877(_snowmanx + 1, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, true);
                  this.field_7092[_snowmanx - 1] = 0;
               }

               int _snowmanxx = this.getTrackedEntityId(_snowmanx);
               if (_snowmanxx > 0) {
                  Entity _snowmanxxx = this.world.getEntityById(_snowmanxx);
                  if (_snowmanxxx == null || !_snowmanxxx.isAlive() || this.squaredDistanceTo(_snowmanxxx) > 900.0 || !this.canSee(_snowmanxxx)) {
                     this.setTrackedEntityId(_snowmanx, 0);
                  } else if (_snowmanxxx instanceof PlayerEntity && ((PlayerEntity)_snowmanxxx).abilities.invulnerable) {
                     this.setTrackedEntityId(_snowmanx, 0);
                  } else {
                     this.method_6878(_snowmanx + 1, (LivingEntity)_snowmanxxx);
                     this.field_7091[_snowmanx - 1] = this.age + 40 + this.random.nextInt(20);
                     this.field_7092[_snowmanx - 1] = 0;
                  }
               } else {
                  List<LivingEntity> _snowmanxxx = this.world
                     .getTargets(LivingEntity.class, HEAD_TARGET_PREDICATE, this, this.getBoundingBox().expand(20.0, 8.0, 20.0));

                  for (int _snowmanxxxx = 0; _snowmanxxxx < 10 && !_snowmanxxx.isEmpty(); _snowmanxxxx++) {
                     LivingEntity _snowmanxxxxx = _snowmanxxx.get(this.random.nextInt(_snowmanxxx.size()));
                     if (_snowmanxxxxx != this && _snowmanxxxxx.isAlive() && this.canSee(_snowmanxxxxx)) {
                        if (_snowmanxxxxx instanceof PlayerEntity) {
                           if (!((PlayerEntity)_snowmanxxxxx).abilities.invulnerable) {
                              this.setTrackedEntityId(_snowmanx, _snowmanxxxxx.getEntityId());
                           }
                        } else {
                           this.setTrackedEntityId(_snowmanx, _snowmanxxxxx.getEntityId());
                        }
                        break;
                     }

                     _snowmanxxx.remove(_snowmanxxxxx);
                  }
               }
            }
         }

         if (this.getTarget() != null) {
            this.setTrackedEntityId(0, this.getTarget().getEntityId());
         } else {
            this.setTrackedEntityId(0, 0);
         }

         if (this.field_7082 > 0) {
            this.field_7082--;
            if (this.field_7082 == 0 && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
               int _snowmanxx = MathHelper.floor(this.getY());
               int _snowmanxxx = MathHelper.floor(this.getX());
               int _snowmanxxxx = MathHelper.floor(this.getZ());
               boolean _snowmanxxxxx = false;

               for (int _snowmanxxxxxx = -1; _snowmanxxxxxx <= 1; _snowmanxxxxxx++) {
                  for (int _snowmanxxxxxxx = -1; _snowmanxxxxxxx <= 1; _snowmanxxxxxxx++) {
                     for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx <= 3; _snowmanxxxxxxxx++) {
                        int _snowmanxxxxxxxxx = _snowmanxxx + _snowmanxxxxxx;
                        int _snowmanxxxxxxxxxx = _snowmanxx + _snowmanxxxxxxxx;
                        int _snowmanxxxxxxxxxxx = _snowmanxxxx + _snowmanxxxxxxx;
                        BlockPos _snowmanxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                        BlockState _snowmanxxxxxxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxxxxxxx);
                        if (canDestroy(_snowmanxxxxxxxxxxxxx)) {
                           _snowmanxxxxx = this.world.breakBlock(_snowmanxxxxxxxxxxxx, true, this) || _snowmanxxxxx;
                        }
                     }
                  }
               }

               if (_snowmanxxxxx) {
                  this.world.syncWorldEvent(null, 1022, this.getBlockPos(), 0);
               }
            }
         }

         if (this.age % 20 == 0) {
            this.heal(1.0F);
         }

         this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
      }
   }

   public static boolean canDestroy(BlockState block) {
      return !block.isAir() && !BlockTags.WITHER_IMMUNE.contains(block.getBlock());
   }

   public void method_6885() {
      this.setInvulTimer(220);
      this.setHealth(this.getMaxHealth() / 3.0F);
   }

   @Override
   public void slowMovement(BlockState state, Vec3d multiplier) {
   }

   @Override
   public void onStartedTrackingBy(ServerPlayerEntity player) {
      super.onStartedTrackingBy(player);
      this.bossBar.addPlayer(player);
   }

   @Override
   public void onStoppedTrackingBy(ServerPlayerEntity player) {
      super.onStoppedTrackingBy(player);
      this.bossBar.removePlayer(player);
   }

   private double getHeadX(int headIndex) {
      if (headIndex <= 0) {
         return this.getX();
      } else {
         float _snowman = (this.bodyYaw + (float)(180 * (headIndex - 1))) * (float) (Math.PI / 180.0);
         float _snowmanx = MathHelper.cos(_snowman);
         return this.getX() + (double)_snowmanx * 1.3;
      }
   }

   private double getHeadY(int headIndex) {
      return headIndex <= 0 ? this.getY() + 3.0 : this.getY() + 2.2;
   }

   private double getHeadZ(int headIndex) {
      if (headIndex <= 0) {
         return this.getZ();
      } else {
         float _snowman = (this.bodyYaw + (float)(180 * (headIndex - 1))) * (float) (Math.PI / 180.0);
         float _snowmanx = MathHelper.sin(_snowman);
         return this.getZ() + (double)_snowmanx * 1.3;
      }
   }

   private float getNextAngle(float prevAngle, float desiredAngle, float maxDifference) {
      float _snowman = MathHelper.wrapDegrees(desiredAngle - prevAngle);
      if (_snowman > maxDifference) {
         _snowman = maxDifference;
      }

      if (_snowman < -maxDifference) {
         _snowman = -maxDifference;
      }

      return prevAngle + _snowman;
   }

   private void method_6878(int _snowman, LivingEntity _snowman) {
      this.method_6877(_snowman, _snowman.getX(), _snowman.getY() + (double)_snowman.getStandingEyeHeight() * 0.5, _snowman.getZ(), _snowman == 0 && this.random.nextFloat() < 0.001F);
   }

   private void method_6877(int headIndex, double _snowman, double _snowman, double _snowman, boolean _snowman) {
      if (!this.isSilent()) {
         this.world.syncWorldEvent(null, 1024, this.getBlockPos(), 0);
      }

      double _snowmanxxxx = this.getHeadX(headIndex);
      double _snowmanxxxxx = this.getHeadY(headIndex);
      double _snowmanxxxxxx = this.getHeadZ(headIndex);
      double _snowmanxxxxxxx = _snowman - _snowmanxxxx;
      double _snowmanxxxxxxxx = _snowman - _snowmanxxxxx;
      double _snowmanxxxxxxxxx = _snowman - _snowmanxxxxxx;
      WitherSkullEntity _snowmanxxxxxxxxxx = new WitherSkullEntity(this.world, this, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
      _snowmanxxxxxxxxxx.setOwner(this);
      if (_snowman) {
         _snowmanxxxxxxxxxx.setCharged(true);
      }

      _snowmanxxxxxxxxxx.setPos(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      this.world.spawnEntity(_snowmanxxxxxxxxxx);
   }

   @Override
   public void attack(LivingEntity target, float pullProgress) {
      this.method_6878(0, target);
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else if (source == DamageSource.DROWN || source.getAttacker() instanceof WitherEntity) {
         return false;
      } else if (this.getInvulnerableTimer() > 0 && source != DamageSource.OUT_OF_WORLD) {
         return false;
      } else {
         if (this.shouldRenderOverlay()) {
            Entity _snowman = source.getSource();
            if (_snowman instanceof PersistentProjectileEntity) {
               return false;
            }
         }

         Entity _snowman = source.getAttacker();
         if (_snowman != null && !(_snowman instanceof PlayerEntity) && _snowman instanceof LivingEntity && ((LivingEntity)_snowman).getGroup() == this.getGroup()) {
            return false;
         } else {
            if (this.field_7082 <= 0) {
               this.field_7082 = 20;
            }

            for (int _snowmanx = 0; _snowmanx < this.field_7092.length; _snowmanx++) {
               this.field_7092[_snowmanx] = this.field_7092[_snowmanx] + 3;
            }

            return super.damage(source, amount);
         }
      }
   }

   @Override
   protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
      super.dropEquipment(source, lootingMultiplier, allowDrops);
      ItemEntity _snowman = this.dropItem(Items.NETHER_STAR);
      if (_snowman != null) {
         _snowman.setCovetedItem();
      }
   }

   @Override
   public void checkDespawn() {
      if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
         this.remove();
      } else {
         this.despawnCounter = 0;
      }
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      return false;
   }

   @Override
   public boolean addStatusEffect(StatusEffectInstance effect) {
      return false;
   }

   public static DefaultAttributeContainer.Builder createWitherAttributes() {
      return HostileEntity.createHostileAttributes()
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 300.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6F)
         .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0)
         .add(EntityAttributes.GENERIC_ARMOR, 4.0);
   }

   public float getHeadYaw(int headIndex) {
      return this.sideHeadYaws[headIndex];
   }

   public float getHeadPitch(int headIndex) {
      return this.sideHeadPitches[headIndex];
   }

   public int getInvulnerableTimer() {
      return this.dataTracker.get(INVUL_TIMER);
   }

   public void setInvulTimer(int ticks) {
      this.dataTracker.set(INVUL_TIMER, ticks);
   }

   public int getTrackedEntityId(int headIndex) {
      return this.dataTracker.get(TRACKED_ENTITY_IDS.get(headIndex));
   }

   public void setTrackedEntityId(int headIndex, int id) {
      this.dataTracker.set(TRACKED_ENTITY_IDS.get(headIndex), id);
   }

   @Override
   public boolean shouldRenderOverlay() {
      return this.getHealth() <= this.getMaxHealth() / 2.0F;
   }

   @Override
   public EntityGroup getGroup() {
      return EntityGroup.UNDEAD;
   }

   @Override
   protected boolean canStartRiding(Entity entity) {
      return false;
   }

   @Override
   public boolean canUsePortals() {
      return false;
   }

   @Override
   public boolean canHaveStatusEffect(StatusEffectInstance effect) {
      return effect.getEffectType() == StatusEffects.WITHER ? false : super.canHaveStatusEffect(effect);
   }

   class DescendAtHalfHealthGoal extends Goal {
      public DescendAtHalfHealthGoal() {
         this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP, Goal.Control.LOOK));
      }

      @Override
      public boolean canStart() {
         return WitherEntity.this.getInvulnerableTimer() > 0;
      }
   }
}
