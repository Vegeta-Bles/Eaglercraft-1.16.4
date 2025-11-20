package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public abstract class PassiveEntity extends PathAwareEntity {
   private static final TrackedData<Boolean> CHILD = DataTracker.registerData(PassiveEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   protected int breedingAge;
   protected int forcedAge;
   protected int happyTicksRemaining;

   protected PassiveEntity(EntityType<? extends PassiveEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      if (entityData == null) {
         entityData = new PassiveEntity.PassiveData(true);
      }

      PassiveEntity.PassiveData _snowman = (PassiveEntity.PassiveData)entityData;
      if (_snowman.canSpawnBaby() && _snowman.getSpawnedCount() > 0 && this.random.nextFloat() <= _snowman.getBabyChance()) {
         this.setBreedingAge(-24000);
      }

      _snowman.countSpawned();
      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   @Nullable
   public abstract PassiveEntity createChild(ServerWorld world, PassiveEntity entity);

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(CHILD, false);
   }

   public boolean isReadyToBreed() {
      return false;
   }

   public int getBreedingAge() {
      if (this.world.isClient) {
         return this.dataTracker.get(CHILD) ? -1 : 1;
      } else {
         return this.breedingAge;
      }
   }

   public void growUp(int age, boolean overGrow) {
      int _snowman = this.getBreedingAge();
      _snowman += age * 20;
      if (_snowman > 0) {
         _snowman = 0;
      }

      int _snowmanx = _snowman - _snowman;
      this.setBreedingAge(_snowman);
      if (overGrow) {
         this.forcedAge += _snowmanx;
         if (this.happyTicksRemaining == 0) {
            this.happyTicksRemaining = 40;
         }
      }

      if (this.getBreedingAge() == 0) {
         this.setBreedingAge(this.forcedAge);
      }
   }

   public void growUp(int age) {
      this.growUp(age, false);
   }

   public void setBreedingAge(int age) {
      int _snowman = this.breedingAge;
      this.breedingAge = age;
      if (_snowman < 0 && age >= 0 || _snowman >= 0 && age < 0) {
         this.dataTracker.set(CHILD, age < 0);
         this.onGrowUp();
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("Age", this.getBreedingAge());
      tag.putInt("ForcedAge", this.forcedAge);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.setBreedingAge(tag.getInt("Age"));
      this.forcedAge = tag.getInt("ForcedAge");
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      if (CHILD.equals(data)) {
         this.calculateDimensions();
      }

      super.onTrackedDataSet(data);
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      if (this.world.isClient) {
         if (this.happyTicksRemaining > 0) {
            if (this.happyTicksRemaining % 4 == 0) {
               this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
            }

            this.happyTicksRemaining--;
         }
      } else if (this.isAlive()) {
         int _snowman = this.getBreedingAge();
         if (_snowman < 0) {
            this.setBreedingAge(++_snowman);
         } else if (_snowman > 0) {
            this.setBreedingAge(--_snowman);
         }
      }
   }

   protected void onGrowUp() {
   }

   @Override
   public boolean isBaby() {
      return this.getBreedingAge() < 0;
   }

   @Override
   public void setBaby(boolean baby) {
      this.setBreedingAge(baby ? -24000 : 0);
   }

   public static class PassiveData implements EntityData {
      private int spawnCount;
      private final boolean babyAllowed;
      private final float babyChance;

      private PassiveData(boolean _snowman, float _snowman) {
         this.babyAllowed = _snowman;
         this.babyChance = _snowman;
      }

      public PassiveData(boolean _snowman) {
         this(_snowman, 0.05F);
      }

      public PassiveData(float _snowman) {
         this(true, _snowman);
      }

      public int getSpawnedCount() {
         return this.spawnCount;
      }

      public void countSpawned() {
         this.spawnCount++;
      }

      public boolean canSpawnBaby() {
         return this.babyAllowed;
      }

      public float getBabyChance() {
         return this.babyChance;
      }
   }
}
