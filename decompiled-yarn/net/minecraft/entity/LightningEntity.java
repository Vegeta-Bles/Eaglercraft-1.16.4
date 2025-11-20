package net.minecraft.entity;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class LightningEntity extends Entity {
   private int ambientTick;
   public long seed;
   private int remainingActions;
   private boolean cosmetic;
   @Nullable
   private ServerPlayerEntity channeler;

   public LightningEntity(EntityType<? extends LightningEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.ignoreCameraFrustum = true;
      this.ambientTick = 2;
      this.seed = this.random.nextLong();
      this.remainingActions = this.random.nextInt(3) + 1;
   }

   public void setCosmetic(boolean cosmetic) {
      this.cosmetic = cosmetic;
   }

   @Override
   public SoundCategory getSoundCategory() {
      return SoundCategory.WEATHER;
   }

   public void setChanneler(@Nullable ServerPlayerEntity channeler) {
      this.channeler = channeler;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.ambientTick == 2) {
         Difficulty _snowman = this.world.getDifficulty();
         if (_snowman == Difficulty.NORMAL || _snowman == Difficulty.HARD) {
            this.spawnFire(4);
         }

         this.world
            .playSound(
               null,
               this.getX(),
               this.getY(),
               this.getZ(),
               SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER,
               SoundCategory.WEATHER,
               10000.0F,
               0.8F + this.random.nextFloat() * 0.2F
            );
         this.world
            .playSound(
               null,
               this.getX(),
               this.getY(),
               this.getZ(),
               SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT,
               SoundCategory.WEATHER,
               2.0F,
               0.5F + this.random.nextFloat() * 0.2F
            );
      }

      this.ambientTick--;
      if (this.ambientTick < 0) {
         if (this.remainingActions == 0) {
            this.remove();
         } else if (this.ambientTick < -this.random.nextInt(10)) {
            this.remainingActions--;
            this.ambientTick = 1;
            this.seed = this.random.nextLong();
            this.spawnFire(0);
         }
      }

      if (this.ambientTick >= 0) {
         if (!(this.world instanceof ServerWorld)) {
            this.world.setLightningTicksLeft(2);
         } else if (!this.cosmetic) {
            double _snowman = 3.0;
            List<Entity> _snowmanx = this.world
               .getOtherEntities(
                  this,
                  new Box(this.getX() - 3.0, this.getY() - 3.0, this.getZ() - 3.0, this.getX() + 3.0, this.getY() + 6.0 + 3.0, this.getZ() + 3.0),
                  Entity::isAlive
               );

            for (Entity _snowmanxx : _snowmanx) {
               _snowmanxx.onStruckByLightning((ServerWorld)this.world, this);
            }

            if (this.channeler != null) {
               Criteria.CHANNELED_LIGHTNING.trigger(this.channeler, _snowmanx);
            }
         }
      }
   }

   private void spawnFire(int spreadAttempts) {
      if (!this.cosmetic && !this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
         BlockPos _snowman = this.getBlockPos();
         BlockState _snowmanx = AbstractFireBlock.getState(this.world, _snowman);
         if (this.world.getBlockState(_snowman).isAir() && _snowmanx.canPlaceAt(this.world, _snowman)) {
            this.world.setBlockState(_snowman, _snowmanx);
         }

         for (int _snowmanxx = 0; _snowmanxx < spreadAttempts; _snowmanxx++) {
            BlockPos _snowmanxxx = _snowman.add(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
            _snowmanx = AbstractFireBlock.getState(this.world, _snowmanxxx);
            if (this.world.getBlockState(_snowmanxxx).isAir() && _snowmanx.canPlaceAt(this.world, _snowmanxxx)) {
               this.world.setBlockState(_snowmanxxx, _snowmanx);
            }
         }
      }
   }

   @Override
   public boolean shouldRender(double distance) {
      double _snowman = 64.0 * getRenderDistanceMultiplier();
      return distance < _snowman * _snowman;
   }

   @Override
   protected void initDataTracker() {
   }

   @Override
   protected void readCustomDataFromTag(CompoundTag tag) {
   }

   @Override
   protected void writeCustomDataToTag(CompoundTag tag) {
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new EntitySpawnS2CPacket(this);
   }
}
