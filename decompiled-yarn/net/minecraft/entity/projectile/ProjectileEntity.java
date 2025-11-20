package net.minecraft.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class ProjectileEntity extends Entity {
   private UUID ownerUuid;
   private int ownerEntityId;
   private boolean leftOwner;

   ProjectileEntity(EntityType<? extends ProjectileEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public void setOwner(@Nullable Entity entity) {
      if (entity != null) {
         this.ownerUuid = entity.getUuid();
         this.ownerEntityId = entity.getEntityId();
      }
   }

   @Nullable
   public Entity getOwner() {
      if (this.ownerUuid != null && this.world instanceof ServerWorld) {
         return ((ServerWorld)this.world).getEntity(this.ownerUuid);
      } else {
         return this.ownerEntityId != 0 ? this.world.getEntityById(this.ownerEntityId) : null;
      }
   }

   @Override
   protected void writeCustomDataToTag(CompoundTag tag) {
      if (this.ownerUuid != null) {
         tag.putUuid("Owner", this.ownerUuid);
      }

      if (this.leftOwner) {
         tag.putBoolean("LeftOwner", true);
      }
   }

   @Override
   protected void readCustomDataFromTag(CompoundTag tag) {
      if (tag.containsUuid("Owner")) {
         this.ownerUuid = tag.getUuid("Owner");
      }

      this.leftOwner = tag.getBoolean("LeftOwner");
   }

   @Override
   public void tick() {
      if (!this.leftOwner) {
         this.leftOwner = this.method_26961();
      }

      super.tick();
   }

   private boolean method_26961() {
      Entity _snowman = this.getOwner();
      if (_snowman != null) {
         for (Entity _snowmanx : this.world
            .getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), _snowmanxx -> !_snowmanxx.isSpectator() && _snowmanxx.collides())) {
            if (_snowmanx.getRootVehicle() == _snowman.getRootVehicle()) {
               return false;
            }
         }
      }

      return true;
   }

   public void setVelocity(double x, double y, double z, float speed, float divergence) {
      Vec3d _snowman = new Vec3d(x, y, z)
         .normalize()
         .add(
            this.random.nextGaussian() * 0.0075F * (double)divergence,
            this.random.nextGaussian() * 0.0075F * (double)divergence,
            this.random.nextGaussian() * 0.0075F * (double)divergence
         )
         .multiply((double)speed);
      this.setVelocity(_snowman);
      float _snowmanx = MathHelper.sqrt(squaredHorizontalLength(_snowman));
      this.yaw = (float)(MathHelper.atan2(_snowman.x, _snowman.z) * 180.0F / (float)Math.PI);
      this.pitch = (float)(MathHelper.atan2(_snowman.y, (double)_snowmanx) * 180.0F / (float)Math.PI);
      this.prevYaw = this.yaw;
      this.prevPitch = this.pitch;
   }

   public void setProperties(Entity user, float pitch, float yaw, float roll, float modifierZ, float modifierXYZ) {
      float _snowman = -MathHelper.sin(yaw * (float) (Math.PI / 180.0)) * MathHelper.cos(pitch * (float) (Math.PI / 180.0));
      float _snowmanx = -MathHelper.sin((pitch + roll) * (float) (Math.PI / 180.0));
      float _snowmanxx = MathHelper.cos(yaw * (float) (Math.PI / 180.0)) * MathHelper.cos(pitch * (float) (Math.PI / 180.0));
      this.setVelocity((double)_snowman, (double)_snowmanx, (double)_snowmanxx, modifierZ, modifierXYZ);
      Vec3d _snowmanxxx = user.getVelocity();
      this.setVelocity(this.getVelocity().add(_snowmanxxx.x, user.isOnGround() ? 0.0 : _snowmanxxx.y, _snowmanxxx.z));
   }

   protected void onCollision(HitResult hitResult) {
      HitResult.Type _snowman = hitResult.getType();
      if (_snowman == HitResult.Type.ENTITY) {
         this.onEntityHit((EntityHitResult)hitResult);
      } else if (_snowman == HitResult.Type.BLOCK) {
         this.onBlockHit((BlockHitResult)hitResult);
      }
   }

   protected void onEntityHit(EntityHitResult entityHitResult) {
   }

   protected void onBlockHit(BlockHitResult blockHitResult) {
      BlockState _snowman = this.world.getBlockState(blockHitResult.getBlockPos());
      _snowman.onProjectileHit(this.world, _snowman, blockHitResult, this);
   }

   @Override
   public void setVelocityClient(double x, double y, double z) {
      this.setVelocity(x, y, z);
      if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
         float _snowman = MathHelper.sqrt(x * x + z * z);
         this.pitch = (float)(MathHelper.atan2(y, (double)_snowman) * 180.0F / (float)Math.PI);
         this.yaw = (float)(MathHelper.atan2(x, z) * 180.0F / (float)Math.PI);
         this.prevPitch = this.pitch;
         this.prevYaw = this.yaw;
         this.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
      }
   }

   protected boolean method_26958(Entity _snowman) {
      if (!_snowman.isSpectator() && _snowman.isAlive() && _snowman.collides()) {
         Entity _snowmanx = this.getOwner();
         return _snowmanx == null || this.leftOwner || !_snowmanx.isConnectedThroughVehicle(_snowman);
      } else {
         return false;
      }
   }

   protected void method_26962() {
      Vec3d _snowman = this.getVelocity();
      float _snowmanx = MathHelper.sqrt(squaredHorizontalLength(_snowman));
      this.pitch = updateRotation(this.prevPitch, (float)(MathHelper.atan2(_snowman.y, (double)_snowmanx) * 180.0F / (float)Math.PI));
      this.yaw = updateRotation(this.prevYaw, (float)(MathHelper.atan2(_snowman.x, _snowman.z) * 180.0F / (float)Math.PI));
   }

   protected static float updateRotation(float _snowman, float _snowman) {
      while (_snowman - _snowman < -180.0F) {
         _snowman -= 360.0F;
      }

      while (_snowman - _snowman >= 180.0F) {
         _snowman += 360.0F;
      }

      return MathHelper.lerp(0.2F, _snowman, _snowman);
   }
}
