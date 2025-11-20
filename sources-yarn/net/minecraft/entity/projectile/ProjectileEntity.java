package net.minecraft.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   public ProjectileEntity(EntityType<? extends ProjectileEntity> type, World world) {
      super(type, world);
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
      Entity lv = this.getOwner();
      if (lv != null) {
         for (Entity lv2 : this.world
            .getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), arg -> !arg.isSpectator() && arg.collides())) {
            if (lv2.getRootVehicle() == lv.getRootVehicle()) {
               return false;
            }
         }
      }

      return true;
   }

   public void setVelocity(double x, double y, double z, float speed, float divergence) {
      Vec3d lv = new Vec3d(x, y, z)
         .normalize()
         .add(
            this.random.nextGaussian() * 0.0075F * (double)divergence,
            this.random.nextGaussian() * 0.0075F * (double)divergence,
            this.random.nextGaussian() * 0.0075F * (double)divergence
         )
         .multiply((double)speed);
      this.setVelocity(lv);
      float i = MathHelper.sqrt(squaredHorizontalLength(lv));
      this.yaw = (float)(MathHelper.atan2(lv.x, lv.z) * 180.0F / (float)Math.PI);
      this.pitch = (float)(MathHelper.atan2(lv.y, (double)i) * 180.0F / (float)Math.PI);
      this.prevYaw = this.yaw;
      this.prevPitch = this.pitch;
   }

   public void setProperties(Entity user, float pitch, float yaw, float roll, float modifierZ, float modifierXYZ) {
      float k = -MathHelper.sin(yaw * (float) (Math.PI / 180.0)) * MathHelper.cos(pitch * (float) (Math.PI / 180.0));
      float l = -MathHelper.sin((pitch + roll) * (float) (Math.PI / 180.0));
      float m = MathHelper.cos(yaw * (float) (Math.PI / 180.0)) * MathHelper.cos(pitch * (float) (Math.PI / 180.0));
      this.setVelocity((double)k, (double)l, (double)m, modifierZ, modifierXYZ);
      Vec3d lv = user.getVelocity();
      this.setVelocity(this.getVelocity().add(lv.x, user.isOnGround() ? 0.0 : lv.y, lv.z));
   }

   protected void onCollision(HitResult hitResult) {
      HitResult.Type lv = hitResult.getType();
      if (lv == HitResult.Type.ENTITY) {
         this.onEntityHit((EntityHitResult)hitResult);
      } else if (lv == HitResult.Type.BLOCK) {
         this.onBlockHit((BlockHitResult)hitResult);
      }
   }

   protected void onEntityHit(EntityHitResult entityHitResult) {
   }

   protected void onBlockHit(BlockHitResult blockHitResult) {
      BlockState lv2 = this.world.getBlockState(blockHitResult.getBlockPos());
      lv2.onProjectileHit(this.world, lv2, blockHitResult, this);
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void setVelocityClient(double x, double y, double z) {
      this.setVelocity(x, y, z);
      if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
         float g = MathHelper.sqrt(x * x + z * z);
         this.pitch = (float)(MathHelper.atan2(y, (double)g) * 180.0F / (float)Math.PI);
         this.yaw = (float)(MathHelper.atan2(x, z) * 180.0F / (float)Math.PI);
         this.prevPitch = this.pitch;
         this.prevYaw = this.yaw;
         this.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
      }
   }

   protected boolean method_26958(Entity arg) {
      if (!arg.isSpectator() && arg.isAlive() && arg.collides()) {
         Entity lv = this.getOwner();
         return lv == null || this.leftOwner || !lv.isConnectedThroughVehicle(arg);
      } else {
         return false;
      }
   }

   protected void method_26962() {
      Vec3d lv = this.getVelocity();
      float f = MathHelper.sqrt(squaredHorizontalLength(lv));
      this.pitch = updateRotation(this.prevPitch, (float)(MathHelper.atan2(lv.y, (double)f) * 180.0F / (float)Math.PI));
      this.yaw = updateRotation(this.prevYaw, (float)(MathHelper.atan2(lv.x, lv.z) * 180.0F / (float)Math.PI));
   }

   public static float updateRotation(float f, float g) {
      while (g - f < -180.0F) {
         f -= 360.0F;
      }

      while (g - f >= 180.0F) {
         f += 360.0F;
      }

      return MathHelper.lerp(0.2F, f, g);
   }
}
