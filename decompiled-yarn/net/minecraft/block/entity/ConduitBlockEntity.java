package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ConduitBlockEntity extends BlockEntity implements Tickable {
   private static final Block[] ACTIVATING_BLOCKS = new Block[]{Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.SEA_LANTERN, Blocks.DARK_PRISMARINE};
   public int ticks;
   private float ticksActive;
   private boolean active;
   private boolean eyeOpen;
   private final List<BlockPos> activatingBlocks = Lists.newArrayList();
   @Nullable
   private LivingEntity targetEntity;
   @Nullable
   private UUID targetUuid;
   private long nextAmbientSoundTime;

   public ConduitBlockEntity() {
      this(BlockEntityType.CONDUIT);
   }

   public ConduitBlockEntity(BlockEntityType<?> _snowman) {
      super(_snowman);
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      if (tag.containsUuid("Target")) {
         this.targetUuid = tag.getUuid("Target");
      } else {
         this.targetUuid = null;
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      if (this.targetEntity != null) {
         tag.putUuid("Target", this.targetEntity.getUuid());
      }

      return tag;
   }

   @Nullable
   @Override
   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return new BlockEntityUpdateS2CPacket(this.pos, 5, this.toInitialChunkDataTag());
   }

   @Override
   public CompoundTag toInitialChunkDataTag() {
      return this.toTag(new CompoundTag());
   }

   @Override
   public void tick() {
      this.ticks++;
      long _snowman = this.world.getTime();
      if (_snowman % 40L == 0L) {
         this.setActive(this.updateActivatingBlocks());
         if (!this.world.isClient && this.isActive()) {
            this.givePlayersEffects();
            this.attackHostileEntity();
         }
      }

      if (_snowman % 80L == 0L && this.isActive()) {
         this.playSound(SoundEvents.BLOCK_CONDUIT_AMBIENT);
      }

      if (_snowman > this.nextAmbientSoundTime && this.isActive()) {
         this.nextAmbientSoundTime = _snowman + 60L + (long)this.world.getRandom().nextInt(40);
         this.playSound(SoundEvents.BLOCK_CONDUIT_AMBIENT_SHORT);
      }

      if (this.world.isClient) {
         this.updateTargetEntity();
         this.spawnNautilusParticles();
         if (this.isActive()) {
            this.ticksActive++;
         }
      }
   }

   private boolean updateActivatingBlocks() {
      this.activatingBlocks.clear();

      for (int _snowman = -1; _snowman <= 1; _snowman++) {
         for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
            for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
               BlockPos _snowmanxxx = this.pos.add(_snowman, _snowmanx, _snowmanxx);
               if (!this.world.isWater(_snowmanxxx)) {
                  return false;
               }
            }
         }
      }

      for (int _snowman = -2; _snowman <= 2; _snowman++) {
         for (int _snowmanx = -2; _snowmanx <= 2; _snowmanx++) {
            for (int _snowmanxxx = -2; _snowmanxxx <= 2; _snowmanxxx++) {
               int _snowmanxxxx = Math.abs(_snowman);
               int _snowmanxxxxx = Math.abs(_snowmanx);
               int _snowmanxxxxxx = Math.abs(_snowmanxxx);
               if ((_snowmanxxxx > 1 || _snowmanxxxxx > 1 || _snowmanxxxxxx > 1)
                  && (_snowman == 0 && (_snowmanxxxxx == 2 || _snowmanxxxxxx == 2) || _snowmanx == 0 && (_snowmanxxxx == 2 || _snowmanxxxxxx == 2) || _snowmanxxx == 0 && (_snowmanxxxx == 2 || _snowmanxxxxx == 2))) {
                  BlockPos _snowmanxxxxxxx = this.pos.add(_snowman, _snowmanx, _snowmanxxx);
                  BlockState _snowmanxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxx);

                  for (Block _snowmanxxxxxxxxx : ACTIVATING_BLOCKS) {
                     if (_snowmanxxxxxxxx.isOf(_snowmanxxxxxxxxx)) {
                        this.activatingBlocks.add(_snowmanxxxxxxx);
                     }
                  }
               }
            }
         }
      }

      this.setEyeOpen(this.activatingBlocks.size() >= 42);
      return this.activatingBlocks.size() >= 16;
   }

   private void givePlayersEffects() {
      int _snowman = this.activatingBlocks.size();
      int _snowmanx = _snowman / 7 * 16;
      int _snowmanxx = this.pos.getX();
      int _snowmanxxx = this.pos.getY();
      int _snowmanxxxx = this.pos.getZ();
      Box _snowmanxxxxx = new Box((double)_snowmanxx, (double)_snowmanxxx, (double)_snowmanxxxx, (double)(_snowmanxx + 1), (double)(_snowmanxxx + 1), (double)(_snowmanxxxx + 1))
         .expand((double)_snowmanx)
         .stretch(0.0, (double)this.world.getHeight(), 0.0);
      List<PlayerEntity> _snowmanxxxxxx = this.world.getNonSpectatingEntities(PlayerEntity.class, _snowmanxxxxx);
      if (!_snowmanxxxxxx.isEmpty()) {
         for (PlayerEntity _snowmanxxxxxxx : _snowmanxxxxxx) {
            if (this.pos.isWithinDistance(_snowmanxxxxxxx.getBlockPos(), (double)_snowmanx) && _snowmanxxxxxxx.isTouchingWaterOrRain()) {
               _snowmanxxxxxxx.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 260, 0, true, true));
            }
         }
      }
   }

   private void attackHostileEntity() {
      LivingEntity _snowman = this.targetEntity;
      int _snowmanx = this.activatingBlocks.size();
      if (_snowmanx < 42) {
         this.targetEntity = null;
      } else if (this.targetEntity == null && this.targetUuid != null) {
         this.targetEntity = this.findTargetEntity();
         this.targetUuid = null;
      } else if (this.targetEntity == null) {
         List<LivingEntity> _snowmanxx = this.world
            .getEntitiesByClass(LivingEntity.class, this.getAttackZone(), _snowmanxxx -> _snowmanxxx instanceof Monster && _snowmanxxx.isTouchingWaterOrRain());
         if (!_snowmanxx.isEmpty()) {
            this.targetEntity = _snowmanxx.get(this.world.random.nextInt(_snowmanxx.size()));
         }
      } else if (!this.targetEntity.isAlive() || !this.pos.isWithinDistance(this.targetEntity.getBlockPos(), 8.0)) {
         this.targetEntity = null;
      }

      if (this.targetEntity != null) {
         this.world
            .playSound(
               null,
               this.targetEntity.getX(),
               this.targetEntity.getY(),
               this.targetEntity.getZ(),
               SoundEvents.BLOCK_CONDUIT_ATTACK_TARGET,
               SoundCategory.BLOCKS,
               1.0F,
               1.0F
            );
         this.targetEntity.damage(DamageSource.MAGIC, 4.0F);
      }

      if (_snowman != this.targetEntity) {
         BlockState _snowmanxx = this.getCachedState();
         this.world.updateListeners(this.pos, _snowmanxx, _snowmanxx, 2);
      }
   }

   private void updateTargetEntity() {
      if (this.targetUuid == null) {
         this.targetEntity = null;
      } else if (this.targetEntity == null || !this.targetEntity.getUuid().equals(this.targetUuid)) {
         this.targetEntity = this.findTargetEntity();
         if (this.targetEntity == null) {
            this.targetUuid = null;
         }
      }
   }

   private Box getAttackZone() {
      int _snowman = this.pos.getX();
      int _snowmanx = this.pos.getY();
      int _snowmanxx = this.pos.getZ();
      return new Box((double)_snowman, (double)_snowmanx, (double)_snowmanxx, (double)(_snowman + 1), (double)(_snowmanx + 1), (double)(_snowmanxx + 1)).expand(8.0);
   }

   @Nullable
   private LivingEntity findTargetEntity() {
      List<LivingEntity> _snowman = this.world.getEntitiesByClass(LivingEntity.class, this.getAttackZone(), _snowmanx -> _snowmanx.getUuid().equals(this.targetUuid));
      return _snowman.size() == 1 ? _snowman.get(0) : null;
   }

   private void spawnNautilusParticles() {
      Random _snowman = this.world.random;
      double _snowmanx = (double)(MathHelper.sin((float)(this.ticks + 35) * 0.1F) / 2.0F + 0.5F);
      _snowmanx = (_snowmanx * _snowmanx + _snowmanx) * 0.3F;
      Vec3d _snowmanxx = new Vec3d((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 1.5 + _snowmanx, (double)this.pos.getZ() + 0.5);

      for (BlockPos _snowmanxxx : this.activatingBlocks) {
         if (_snowman.nextInt(50) == 0) {
            float _snowmanxxxx = -0.5F + _snowman.nextFloat();
            float _snowmanxxxxx = -2.0F + _snowman.nextFloat();
            float _snowmanxxxxxx = -0.5F + _snowman.nextFloat();
            BlockPos _snowmanxxxxxxx = _snowmanxxx.subtract(this.pos);
            Vec3d _snowmanxxxxxxxx = new Vec3d((double)_snowmanxxxx, (double)_snowmanxxxxx, (double)_snowmanxxxxxx)
               .add((double)_snowmanxxxxxxx.getX(), (double)_snowmanxxxxxxx.getY(), (double)_snowmanxxxxxxx.getZ());
            this.world.addParticle(ParticleTypes.NAUTILUS, _snowmanxx.x, _snowmanxx.y, _snowmanxx.z, _snowmanxxxxxxxx.x, _snowmanxxxxxxxx.y, _snowmanxxxxxxxx.z);
         }
      }

      if (this.targetEntity != null) {
         Vec3d _snowmanxxxx = new Vec3d(this.targetEntity.getX(), this.targetEntity.getEyeY(), this.targetEntity.getZ());
         float _snowmanxxxxx = (-0.5F + _snowman.nextFloat()) * (3.0F + this.targetEntity.getWidth());
         float _snowmanxxxxxx = -1.0F + _snowman.nextFloat() * this.targetEntity.getHeight();
         float _snowmanxxxxxxx = (-0.5F + _snowman.nextFloat()) * (3.0F + this.targetEntity.getWidth());
         Vec3d _snowmanxxxxxxxx = new Vec3d((double)_snowmanxxxxx, (double)_snowmanxxxxxx, (double)_snowmanxxxxxxx);
         this.world.addParticle(ParticleTypes.NAUTILUS, _snowmanxxxx.x, _snowmanxxxx.y, _snowmanxxxx.z, _snowmanxxxxxxxx.x, _snowmanxxxxxxxx.y, _snowmanxxxxxxxx.z);
      }
   }

   public boolean isActive() {
      return this.active;
   }

   public boolean isEyeOpen() {
      return this.eyeOpen;
   }

   private void setActive(boolean active) {
      if (active != this.active) {
         this.playSound(active ? SoundEvents.BLOCK_CONDUIT_ACTIVATE : SoundEvents.BLOCK_CONDUIT_DEACTIVATE);
      }

      this.active = active;
   }

   private void setEyeOpen(boolean eyeOpen) {
      this.eyeOpen = eyeOpen;
   }

   public float getRotation(float tickDelta) {
      return (this.ticksActive + tickDelta) * -0.0375F;
   }

   public void playSound(SoundEvent _snowman) {
      this.world.playSound(null, this.pos, _snowman, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }
}
