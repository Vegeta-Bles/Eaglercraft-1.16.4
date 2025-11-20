package net.minecraft.entity.projectile;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class ShulkerBulletEntity extends ProjectileEntity {
   private Entity target;
   @Nullable
   private Direction direction;
   private int stepCount;
   private double targetX;
   private double targetY;
   private double targetZ;
   @Nullable
   private UUID targetUuid;

   public ShulkerBulletEntity(EntityType<? extends ShulkerBulletEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.noClip = true;
   }

   public ShulkerBulletEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      this(EntityType.SHULKER_BULLET, world);
      this.refreshPositionAndAngles(x, y, z, this.yaw, this.pitch);
      this.setVelocity(velocityX, velocityY, velocityZ);
   }

   public ShulkerBulletEntity(World world, LivingEntity owner, Entity target, Direction.Axis _snowman) {
      this(EntityType.SHULKER_BULLET, world);
      this.setOwner(owner);
      BlockPos _snowmanx = owner.getBlockPos();
      double _snowmanxx = (double)_snowmanx.getX() + 0.5;
      double _snowmanxxx = (double)_snowmanx.getY() + 0.5;
      double _snowmanxxxx = (double)_snowmanx.getZ() + 0.5;
      this.refreshPositionAndAngles(_snowmanxx, _snowmanxxx, _snowmanxxxx, this.yaw, this.pitch);
      this.target = target;
      this.direction = Direction.UP;
      this.method_7486(_snowman);
   }

   @Override
   public SoundCategory getSoundCategory() {
      return SoundCategory.HOSTILE;
   }

   @Override
   protected void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      if (this.target != null) {
         tag.putUuid("Target", this.target.getUuid());
      }

      if (this.direction != null) {
         tag.putInt("Dir", this.direction.getId());
      }

      tag.putInt("Steps", this.stepCount);
      tag.putDouble("TXD", this.targetX);
      tag.putDouble("TYD", this.targetY);
      tag.putDouble("TZD", this.targetZ);
   }

   @Override
   protected void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.stepCount = tag.getInt("Steps");
      this.targetX = tag.getDouble("TXD");
      this.targetY = tag.getDouble("TYD");
      this.targetZ = tag.getDouble("TZD");
      if (tag.contains("Dir", 99)) {
         this.direction = Direction.byId(tag.getInt("Dir"));
      }

      if (tag.containsUuid("Target")) {
         this.targetUuid = tag.getUuid("Target");
      }
   }

   @Override
   protected void initDataTracker() {
   }

   private void setDirection(@Nullable Direction _snowman) {
      this.direction = _snowman;
   }

   private void method_7486(@Nullable Direction.Axis _snowman) {
      double _snowmanx = 0.5;
      BlockPos _snowmanxx;
      if (this.target == null) {
         _snowmanxx = this.getBlockPos().down();
      } else {
         _snowmanx = (double)this.target.getHeight() * 0.5;
         _snowmanxx = new BlockPos(this.target.getX(), this.target.getY() + _snowmanx, this.target.getZ());
      }

      double _snowmanxxx = (double)_snowmanxx.getX() + 0.5;
      double _snowmanxxxx = (double)_snowmanxx.getY() + _snowmanx;
      double _snowmanxxxxx = (double)_snowmanxx.getZ() + 0.5;
      Direction _snowmanxxxxxx = null;
      if (!_snowmanxx.isWithinDistance(this.getPos(), 2.0)) {
         BlockPos _snowmanxxxxxxx = this.getBlockPos();
         List<Direction> _snowmanxxxxxxxx = Lists.newArrayList();
         if (_snowman != Direction.Axis.X) {
            if (_snowmanxxxxxxx.getX() < _snowmanxx.getX() && this.world.isAir(_snowmanxxxxxxx.east())) {
               _snowmanxxxxxxxx.add(Direction.EAST);
            } else if (_snowmanxxxxxxx.getX() > _snowmanxx.getX() && this.world.isAir(_snowmanxxxxxxx.west())) {
               _snowmanxxxxxxxx.add(Direction.WEST);
            }
         }

         if (_snowman != Direction.Axis.Y) {
            if (_snowmanxxxxxxx.getY() < _snowmanxx.getY() && this.world.isAir(_snowmanxxxxxxx.up())) {
               _snowmanxxxxxxxx.add(Direction.UP);
            } else if (_snowmanxxxxxxx.getY() > _snowmanxx.getY() && this.world.isAir(_snowmanxxxxxxx.down())) {
               _snowmanxxxxxxxx.add(Direction.DOWN);
            }
         }

         if (_snowman != Direction.Axis.Z) {
            if (_snowmanxxxxxxx.getZ() < _snowmanxx.getZ() && this.world.isAir(_snowmanxxxxxxx.south())) {
               _snowmanxxxxxxxx.add(Direction.SOUTH);
            } else if (_snowmanxxxxxxx.getZ() > _snowmanxx.getZ() && this.world.isAir(_snowmanxxxxxxx.north())) {
               _snowmanxxxxxxxx.add(Direction.NORTH);
            }
         }

         _snowmanxxxxxx = Direction.random(this.random);
         if (_snowmanxxxxxxxx.isEmpty()) {
            for (int _snowmanxxxxxxxxx = 5; !this.world.isAir(_snowmanxxxxxxx.offset(_snowmanxxxxxx)) && _snowmanxxxxxxxxx > 0; _snowmanxxxxxxxxx--) {
               _snowmanxxxxxx = Direction.random(this.random);
            }
         } else {
            _snowmanxxxxxx = _snowmanxxxxxxxx.get(this.random.nextInt(_snowmanxxxxxxxx.size()));
         }

         _snowmanxxx = this.getX() + (double)_snowmanxxxxxx.getOffsetX();
         _snowmanxxxx = this.getY() + (double)_snowmanxxxxxx.getOffsetY();
         _snowmanxxxxx = this.getZ() + (double)_snowmanxxxxxx.getOffsetZ();
      }

      this.setDirection(_snowmanxxxxxx);
      double _snowmanxxxxxxxxx = _snowmanxxx - this.getX();
      double _snowmanxxxxxxxxxx = _snowmanxxxx - this.getY();
      double _snowmanxxxxxxxxxxx = _snowmanxxxxx - this.getZ();
      double _snowmanxxxxxxxxxxxx = (double)MathHelper.sqrt(_snowmanxxxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx);
      if (_snowmanxxxxxxxxxxxx == 0.0) {
         this.targetX = 0.0;
         this.targetY = 0.0;
         this.targetZ = 0.0;
      } else {
         this.targetX = _snowmanxxxxxxxxx / _snowmanxxxxxxxxxxxx * 0.15;
         this.targetY = _snowmanxxxxxxxxxx / _snowmanxxxxxxxxxxxx * 0.15;
         this.targetZ = _snowmanxxxxxxxxxxx / _snowmanxxxxxxxxxxxx * 0.15;
      }

      this.velocityDirty = true;
      this.stepCount = 10 + this.random.nextInt(5) * 10;
   }

   @Override
   public void checkDespawn() {
      if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
         this.remove();
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.world.isClient) {
         if (this.target == null && this.targetUuid != null) {
            this.target = ((ServerWorld)this.world).getEntity(this.targetUuid);
            if (this.target == null) {
               this.targetUuid = null;
            }
         }

         if (this.target == null || !this.target.isAlive() || this.target instanceof PlayerEntity && ((PlayerEntity)this.target).isSpectator()) {
            if (!this.hasNoGravity()) {
               this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
            }
         } else {
            this.targetX = MathHelper.clamp(this.targetX * 1.025, -1.0, 1.0);
            this.targetY = MathHelper.clamp(this.targetY * 1.025, -1.0, 1.0);
            this.targetZ = MathHelper.clamp(this.targetZ * 1.025, -1.0, 1.0);
            Vec3d _snowman = this.getVelocity();
            this.setVelocity(_snowman.add((this.targetX - _snowman.x) * 0.2, (this.targetY - _snowman.y) * 0.2, (this.targetZ - _snowman.z) * 0.2));
         }

         HitResult _snowman = ProjectileUtil.getCollision(this, this::method_26958);
         if (_snowman.getType() != HitResult.Type.MISS) {
            this.onCollision(_snowman);
         }
      }

      this.checkBlockCollision();
      Vec3d _snowman = this.getVelocity();
      this.updatePosition(this.getX() + _snowman.x, this.getY() + _snowman.y, this.getZ() + _snowman.z);
      ProjectileUtil.method_7484(this, 0.5F);
      if (this.world.isClient) {
         this.world.addParticle(ParticleTypes.END_ROD, this.getX() - _snowman.x, this.getY() - _snowman.y + 0.15, this.getZ() - _snowman.z, 0.0, 0.0, 0.0);
      } else if (this.target != null && !this.target.removed) {
         if (this.stepCount > 0) {
            this.stepCount--;
            if (this.stepCount == 0) {
               this.method_7486(this.direction == null ? null : this.direction.getAxis());
            }
         }

         if (this.direction != null) {
            BlockPos _snowmanx = this.getBlockPos();
            Direction.Axis _snowmanxx = this.direction.getAxis();
            if (this.world.isTopSolid(_snowmanx.offset(this.direction), this)) {
               this.method_7486(_snowmanxx);
            } else {
               BlockPos _snowmanxxx = this.target.getBlockPos();
               if (_snowmanxx == Direction.Axis.X && _snowmanx.getX() == _snowmanxxx.getX()
                  || _snowmanxx == Direction.Axis.Z && _snowmanx.getZ() == _snowmanxxx.getZ()
                  || _snowmanxx == Direction.Axis.Y && _snowmanx.getY() == _snowmanxxx.getY()) {
                  this.method_7486(_snowmanxx);
               }
            }
         }
      }
   }

   @Override
   protected boolean method_26958(Entity _snowman) {
      return super.method_26958(_snowman) && !_snowman.noClip;
   }

   @Override
   public boolean isOnFire() {
      return false;
   }

   @Override
   public boolean shouldRender(double distance) {
      return distance < 16384.0;
   }

   @Override
   public float getBrightnessAtEyes() {
      return 1.0F;
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      Entity _snowman = entityHitResult.getEntity();
      Entity _snowmanx = this.getOwner();
      LivingEntity _snowmanxx = _snowmanx instanceof LivingEntity ? (LivingEntity)_snowmanx : null;
      boolean _snowmanxxx = _snowman.damage(DamageSource.mobProjectile(this, _snowmanxx).setProjectile(), 4.0F);
      if (_snowmanxxx) {
         this.dealDamage(_snowmanxx, _snowman);
         if (_snowman instanceof LivingEntity) {
            ((LivingEntity)_snowman).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200));
         }
      }
   }

   @Override
   protected void onBlockHit(BlockHitResult blockHitResult) {
      super.onBlockHit(blockHitResult);
      ((ServerWorld)this.world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
      this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
   }

   @Override
   protected void onCollision(HitResult hitResult) {
      super.onCollision(hitResult);
      this.remove();
   }

   @Override
   public boolean collides() {
      return true;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (!this.world.isClient) {
         this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HURT, 1.0F, 1.0F);
         ((ServerWorld)this.world).spawnParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 15, 0.2, 0.2, 0.2, 0.0);
         this.remove();
      }

      return true;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new EntitySpawnS2CPacket(this);
   }
}
