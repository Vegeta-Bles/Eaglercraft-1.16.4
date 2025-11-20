package net.minecraft.block.entity;

import java.util.List;
import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableInt;

public class BellBlockEntity extends BlockEntity implements Tickable {
   private long lastRingTime;
   public int ringTicks;
   public boolean ringing;
   public Direction lastSideHit;
   private List<LivingEntity> hearingEntities;
   private boolean resonating;
   private int resonateTime;

   public BellBlockEntity() {
      super(BlockEntityType.BELL);
   }

   @Override
   public boolean onSyncedBlockEvent(int type, int data) {
      if (type == 1) {
         this.notifyMemoriesOfBell();
         this.resonateTime = 0;
         this.lastSideHit = Direction.byId(data);
         this.ringTicks = 0;
         this.ringing = true;
         return true;
      } else {
         return super.onSyncedBlockEvent(type, data);
      }
   }

   @Override
   public void tick() {
      if (this.ringing) {
         this.ringTicks++;
      }

      if (this.ringTicks >= 50) {
         this.ringing = false;
         this.ringTicks = 0;
      }

      if (this.ringTicks >= 5 && this.resonateTime == 0 && this.raidersHearBell()) {
         this.resonating = true;
         this.playResonateSound();
      }

      if (this.resonating) {
         if (this.resonateTime < 40) {
            this.resonateTime++;
         } else {
            this.applyGlowToRaiders(this.world);
            this.applyParticlesToRaiders(this.world);
            this.resonating = false;
         }
      }
   }

   private void playResonateSound() {
      this.world.playSound(null, this.getPos(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }

   public void activate(Direction direction) {
      BlockPos _snowman = this.getPos();
      this.lastSideHit = direction;
      if (this.ringing) {
         this.ringTicks = 0;
      } else {
         this.ringing = true;
      }

      this.world.addSyncedBlockEvent(_snowman, this.getCachedState().getBlock(), 1, direction.getId());
   }

   private void notifyMemoriesOfBell() {
      BlockPos _snowman = this.getPos();
      if (this.world.getTime() > this.lastRingTime + 60L || this.hearingEntities == null) {
         this.lastRingTime = this.world.getTime();
         Box _snowmanx = new Box(_snowman).expand(48.0);
         this.hearingEntities = this.world.getNonSpectatingEntities(LivingEntity.class, _snowmanx);
      }

      if (!this.world.isClient) {
         for (LivingEntity _snowmanx : this.hearingEntities) {
            if (_snowmanx.isAlive() && !_snowmanx.removed && _snowman.isWithinDistance(_snowmanx.getPos(), 32.0)) {
               _snowmanx.getBrain().remember(MemoryModuleType.HEARD_BELL_TIME, this.world.getTime());
            }
         }
      }
   }

   private boolean raidersHearBell() {
      BlockPos _snowman = this.getPos();

      for (LivingEntity _snowmanx : this.hearingEntities) {
         if (_snowmanx.isAlive() && !_snowmanx.removed && _snowman.isWithinDistance(_snowmanx.getPos(), 32.0) && _snowmanx.getType().isIn(EntityTypeTags.RAIDERS)) {
            return true;
         }
      }

      return false;
   }

   private void applyGlowToRaiders(World world) {
      if (!world.isClient) {
         this.hearingEntities.stream().filter(this::isRaiderEntity).forEach(this::applyGlowToEntity);
      }
   }

   private void applyParticlesToRaiders(World world) {
      if (world.isClient) {
         BlockPos _snowman = this.getPos();
         MutableInt _snowmanx = new MutableInt(16700985);
         int _snowmanxx = (int)this.hearingEntities.stream().filter(_snowmanxxx -> _snowman.isWithinDistance(_snowmanxxx.getPos(), 48.0)).count();
         this.hearingEntities
            .stream()
            .filter(this::isRaiderEntity)
            .forEach(
               _snowmanxxxx -> {
                  float _snowmanxxx = 1.0F;
                  float _snowmanx = MathHelper.sqrt(
                     (_snowmanxxxx.getX() - (double)_snowman.getX()) * (_snowmanxxxx.getX() - (double)_snowman.getX())
                        + (_snowmanxxxx.getZ() - (double)_snowman.getZ()) * (_snowmanxxxx.getZ() - (double)_snowman.getZ())
                  );
                  double _snowmanxx = (double)((float)_snowman.getX() + 0.5F) + (double)(1.0F / _snowmanx) * (_snowmanxxxx.getX() - (double)_snowman.getX());
                  double _snowmanxxx = (double)((float)_snowman.getZ() + 0.5F) + (double)(1.0F / _snowmanx) * (_snowmanxxxx.getZ() - (double)_snowman.getZ());
                  int _snowmanxxxxx = MathHelper.clamp((_snowman - 21) / -2, 3, 15);

                  for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx; _snowmanxxxxxx++) {
                     int _snowmanxxxxxxx = _snowman.addAndGet(5);
                     double _snowmanxxxxxxxx = (double)BackgroundHelper.ColorMixer.getRed(_snowmanxxxxxxx) / 255.0;
                     double _snowmanxxxxxxxxx = (double)BackgroundHelper.ColorMixer.getGreen(_snowmanxxxxxxx) / 255.0;
                     double _snowmanxxxxxxxxxx = (double)BackgroundHelper.ColorMixer.getBlue(_snowmanxxxxxxx) / 255.0;
                     world.addParticle(ParticleTypes.ENTITY_EFFECT, _snowmanxx, (double)((float)_snowman.getY() + 0.5F), _snowmanxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
                  }
               }
            );
      }
   }

   private boolean isRaiderEntity(LivingEntity entity) {
      return entity.isAlive() && !entity.removed && this.getPos().isWithinDistance(entity.getPos(), 48.0) && entity.getType().isIn(EntityTypeTags.RAIDERS);
   }

   private void applyGlowToEntity(LivingEntity entity) {
      entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 60));
   }
}
