package net.minecraft.entity.mob;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EvokerEntity extends SpellcastingIllagerEntity {
   private SheepEntity wololoTarget;

   public EvokerEntity(EntityType<? extends EvokerEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.experiencePoints = 10;
   }

   @Override
   protected void initGoals() {
      super.initGoals();
      this.goalSelector.add(0, new SwimGoal(this));
      this.goalSelector.add(1, new EvokerEntity.LookAtTargetOrWololoTarget());
      this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.6, 1.0));
      this.goalSelector.add(4, new EvokerEntity.SummonVexGoal());
      this.goalSelector.add(5, new EvokerEntity.ConjureFangsGoal());
      this.goalSelector.add(6, new EvokerEntity.WololoGoal());
      this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
      this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
      this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
      this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge());
      this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true).setMaxTimeWithoutVisibility(300));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, MerchantEntity.class, false).setMaxTimeWithoutVisibility(300));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, false));
   }

   public static DefaultAttributeContainer.Builder createEvokerAttributes() {
      return HostileEntity.createHostileAttributes()
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
         .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0)
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
   }

   @Override
   public SoundEvent getCelebratingSound() {
      return SoundEvents.ENTITY_EVOKER_CELEBRATE;
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
   }

   @Override
   protected void mobTick() {
      super.mobTick();
   }

   @Override
   public boolean isTeammate(Entity other) {
      if (other == null) {
         return false;
      } else if (other == this) {
         return true;
      } else if (super.isTeammate(other)) {
         return true;
      } else if (other instanceof VexEntity) {
         return this.isTeammate(((VexEntity)other).getOwner());
      } else {
         return other instanceof LivingEntity && ((LivingEntity)other).getGroup() == EntityGroup.ILLAGER
            ? this.getScoreboardTeam() == null && other.getScoreboardTeam() == null
            : false;
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_EVOKER_AMBIENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_EVOKER_DEATH;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_EVOKER_HURT;
   }

   private void setWololoTarget(@Nullable SheepEntity sheep) {
      this.wololoTarget = sheep;
   }

   @Nullable
   private SheepEntity getWololoTarget() {
      return this.wololoTarget;
   }

   @Override
   protected SoundEvent getCastSpellSound() {
      return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
   }

   @Override
   public void addBonusForWave(int wave, boolean unused) {
   }

   class ConjureFangsGoal extends SpellcastingIllagerEntity.CastSpellGoal {
      private ConjureFangsGoal() {
      }

      @Override
      protected int getSpellTicks() {
         return 40;
      }

      @Override
      protected int startTimeDelay() {
         return 100;
      }

      @Override
      protected void castSpell() {
         LivingEntity _snowman = EvokerEntity.this.getTarget();
         double _snowmanx = Math.min(_snowman.getY(), EvokerEntity.this.getY());
         double _snowmanxx = Math.max(_snowman.getY(), EvokerEntity.this.getY()) + 1.0;
         float _snowmanxxx = (float)MathHelper.atan2(_snowman.getZ() - EvokerEntity.this.getZ(), _snowman.getX() - EvokerEntity.this.getX());
         if (EvokerEntity.this.squaredDistanceTo(_snowman) < 9.0) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < 5; _snowmanxxxx++) {
               float _snowmanxxxxx = _snowmanxxx + (float)_snowmanxxxx * (float) Math.PI * 0.4F;
               this.conjureFangs(
                  EvokerEntity.this.getX() + (double)MathHelper.cos(_snowmanxxxxx) * 1.5,
                  EvokerEntity.this.getZ() + (double)MathHelper.sin(_snowmanxxxxx) * 1.5,
                  _snowmanx,
                  _snowmanxx,
                  _snowmanxxxxx,
                  0
               );
            }

            for (int _snowmanxxxx = 0; _snowmanxxxx < 8; _snowmanxxxx++) {
               float _snowmanxxxxx = _snowmanxxx + (float)_snowmanxxxx * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
               this.conjureFangs(
                  EvokerEntity.this.getX() + (double)MathHelper.cos(_snowmanxxxxx) * 2.5,
                  EvokerEntity.this.getZ() + (double)MathHelper.sin(_snowmanxxxxx) * 2.5,
                  _snowmanx,
                  _snowmanxx,
                  _snowmanxxxxx,
                  3
               );
            }
         } else {
            for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
               double _snowmanxxxxx = 1.25 * (double)(_snowmanxxxx + 1);
               int _snowmanxxxxxx = 1 * _snowmanxxxx;
               this.conjureFangs(
                  EvokerEntity.this.getX() + (double)MathHelper.cos(_snowmanxxx) * _snowmanxxxxx,
                  EvokerEntity.this.getZ() + (double)MathHelper.sin(_snowmanxxx) * _snowmanxxxxx,
                  _snowmanx,
                  _snowmanxx,
                  _snowmanxxx,
                  _snowmanxxxxxx
               );
            }
         }
      }

      private void conjureFangs(double x, double z, double maxY, double y, float yaw, int warmup) {
         BlockPos _snowman = new BlockPos(x, y, z);
         boolean _snowmanx = false;
         double _snowmanxx = 0.0;

         do {
            BlockPos _snowmanxxx = _snowman.down();
            BlockState _snowmanxxxx = EvokerEntity.this.world.getBlockState(_snowmanxxx);
            if (_snowmanxxxx.isSideSolidFullSquare(EvokerEntity.this.world, _snowmanxxx, Direction.UP)) {
               if (!EvokerEntity.this.world.isAir(_snowman)) {
                  BlockState _snowmanxxxxx = EvokerEntity.this.world.getBlockState(_snowman);
                  VoxelShape _snowmanxxxxxx = _snowmanxxxxx.getCollisionShape(EvokerEntity.this.world, _snowman);
                  if (!_snowmanxxxxxx.isEmpty()) {
                     _snowmanxx = _snowmanxxxxxx.getMax(Direction.Axis.Y);
                  }
               }

               _snowmanx = true;
               break;
            }

            _snowman = _snowman.down();
         } while (_snowman.getY() >= MathHelper.floor(maxY) - 1);

         if (_snowmanx) {
            EvokerEntity.this.world.spawnEntity(new EvokerFangsEntity(EvokerEntity.this.world, x, (double)_snowman.getY() + _snowmanxx, z, yaw, warmup, EvokerEntity.this));
         }
      }

      @Override
      protected SoundEvent getSoundPrepare() {
         return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
      }

      @Override
      protected SpellcastingIllagerEntity.Spell getSpell() {
         return SpellcastingIllagerEntity.Spell.FANGS;
      }
   }

   class LookAtTargetOrWololoTarget extends SpellcastingIllagerEntity.LookAtTargetGoal {
      private LookAtTargetOrWololoTarget() {
      }

      @Override
      public void tick() {
         if (EvokerEntity.this.getTarget() != null) {
            EvokerEntity.this.getLookControl()
               .lookAt(EvokerEntity.this.getTarget(), (float)EvokerEntity.this.getBodyYawSpeed(), (float)EvokerEntity.this.getLookPitchSpeed());
         } else if (EvokerEntity.this.getWololoTarget() != null) {
            EvokerEntity.this.getLookControl()
               .lookAt(EvokerEntity.this.getWololoTarget(), (float)EvokerEntity.this.getBodyYawSpeed(), (float)EvokerEntity.this.getLookPitchSpeed());
         }
      }
   }

   class SummonVexGoal extends SpellcastingIllagerEntity.CastSpellGoal {
      private final TargetPredicate closeVexPredicate = new TargetPredicate()
         .setBaseMaxDistance(16.0)
         .includeHidden()
         .ignoreDistanceScalingFactor()
         .includeInvulnerable()
         .includeTeammates();

      private SummonVexGoal() {
      }

      @Override
      public boolean canStart() {
         if (!super.canStart()) {
            return false;
         } else {
            int _snowman = EvokerEntity.this.world
               .getTargets(VexEntity.class, this.closeVexPredicate, EvokerEntity.this, EvokerEntity.this.getBoundingBox().expand(16.0))
               .size();
            return EvokerEntity.this.random.nextInt(8) + 1 > _snowman;
         }
      }

      @Override
      protected int getSpellTicks() {
         return 100;
      }

      @Override
      protected int startTimeDelay() {
         return 340;
      }

      @Override
      protected void castSpell() {
         ServerWorld _snowman = (ServerWorld)EvokerEntity.this.world;

         for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
            BlockPos _snowmanxx = EvokerEntity.this.getBlockPos().add(-2 + EvokerEntity.this.random.nextInt(5), 1, -2 + EvokerEntity.this.random.nextInt(5));
            VexEntity _snowmanxxx = EntityType.VEX.create(EvokerEntity.this.world);
            _snowmanxxx.refreshPositionAndAngles(_snowmanxx, 0.0F, 0.0F);
            _snowmanxxx.initialize(_snowman, EvokerEntity.this.world.getLocalDifficulty(_snowmanxx), SpawnReason.MOB_SUMMONED, null, null);
            _snowmanxxx.setOwner(EvokerEntity.this);
            _snowmanxxx.setBounds(_snowmanxx);
            _snowmanxxx.setLifeTicks(20 * (30 + EvokerEntity.this.random.nextInt(90)));
            _snowman.spawnEntityAndPassengers(_snowmanxxx);
         }
      }

      @Override
      protected SoundEvent getSoundPrepare() {
         return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
      }

      @Override
      protected SpellcastingIllagerEntity.Spell getSpell() {
         return SpellcastingIllagerEntity.Spell.SUMMON_VEX;
      }
   }

   public class WololoGoal extends SpellcastingIllagerEntity.CastSpellGoal {
      private final TargetPredicate convertibleSheepPredicate = new TargetPredicate()
         .setBaseMaxDistance(16.0)
         .includeInvulnerable()
         .setPredicate(_snowmanx -> ((SheepEntity)_snowmanx).getColor() == DyeColor.BLUE);

      public WololoGoal() {
      }

      @Override
      public boolean canStart() {
         if (EvokerEntity.this.getTarget() != null) {
            return false;
         } else if (EvokerEntity.this.isSpellcasting()) {
            return false;
         } else if (EvokerEntity.this.age < this.startTime) {
            return false;
         } else if (!EvokerEntity.this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            return false;
         } else {
            List<SheepEntity> _snowman = EvokerEntity.this.world
               .getTargets(SheepEntity.class, this.convertibleSheepPredicate, EvokerEntity.this, EvokerEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
            if (_snowman.isEmpty()) {
               return false;
            } else {
               EvokerEntity.this.setWololoTarget(_snowman.get(EvokerEntity.this.random.nextInt(_snowman.size())));
               return true;
            }
         }
      }

      @Override
      public boolean shouldContinue() {
         return EvokerEntity.this.getWololoTarget() != null && this.spellCooldown > 0;
      }

      @Override
      public void stop() {
         super.stop();
         EvokerEntity.this.setWololoTarget(null);
      }

      @Override
      protected void castSpell() {
         SheepEntity _snowman = EvokerEntity.this.getWololoTarget();
         if (_snowman != null && _snowman.isAlive()) {
            _snowman.setColor(DyeColor.RED);
         }
      }

      @Override
      protected int getInitialCooldown() {
         return 40;
      }

      @Override
      protected int getSpellTicks() {
         return 60;
      }

      @Override
      protected int startTimeDelay() {
         return 140;
      }

      @Override
      protected SoundEvent getSoundPrepare() {
         return SoundEvents.ENTITY_EVOKER_PREPARE_WOLOLO;
      }

      @Override
      protected SpellcastingIllagerEntity.Spell getSpell() {
         return SpellcastingIllagerEntity.Spell.WOLOLO;
      }
   }
}
