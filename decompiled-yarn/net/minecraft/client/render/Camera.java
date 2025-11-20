package net.minecraft.client.render;

import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;

public class Camera {
   private boolean ready;
   private BlockView area;
   private Entity focusedEntity;
   private Vec3d pos = Vec3d.ZERO;
   private final BlockPos.Mutable blockPos = new BlockPos.Mutable();
   private final Vector3f horizontalPlane = new Vector3f(0.0F, 0.0F, 1.0F);
   private final Vector3f verticalPlane = new Vector3f(0.0F, 1.0F, 0.0F);
   private final Vector3f diagonalPlane = new Vector3f(1.0F, 0.0F, 0.0F);
   private float pitch;
   private float yaw;
   private final Quaternion rotation = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
   private boolean thirdPerson;
   private boolean inverseView;
   private float cameraY;
   private float lastCameraY;

   public Camera() {
   }

   public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta) {
      this.ready = true;
      this.area = area;
      this.focusedEntity = focusedEntity;
      this.thirdPerson = thirdPerson;
      this.inverseView = inverseView;
      this.setRotation(focusedEntity.getYaw(tickDelta), focusedEntity.getPitch(tickDelta));
      this.setPos(
         MathHelper.lerp((double)tickDelta, focusedEntity.prevX, focusedEntity.getX()),
         MathHelper.lerp((double)tickDelta, focusedEntity.prevY, focusedEntity.getY()) + (double)MathHelper.lerp(tickDelta, this.lastCameraY, this.cameraY),
         MathHelper.lerp((double)tickDelta, focusedEntity.prevZ, focusedEntity.getZ())
      );
      if (thirdPerson) {
         if (inverseView) {
            this.setRotation(this.yaw + 180.0F, -this.pitch);
         }

         this.moveBy(-this.clipToSpace(4.0), 0.0, 0.0);
      } else if (focusedEntity instanceof LivingEntity && ((LivingEntity)focusedEntity).isSleeping()) {
         Direction _snowman = ((LivingEntity)focusedEntity).getSleepingDirection();
         this.setRotation(_snowman != null ? _snowman.asRotation() - 180.0F : 0.0F, 0.0F);
         this.moveBy(0.0, 0.3, 0.0);
      }
   }

   public void updateEyeHeight() {
      if (this.focusedEntity != null) {
         this.lastCameraY = this.cameraY;
         this.cameraY = this.cameraY + (this.focusedEntity.getStandingEyeHeight() - this.cameraY) * 0.5F;
      }
   }

   private double clipToSpace(double desiredCameraDistance) {
      for (int _snowman = 0; _snowman < 8; _snowman++) {
         float _snowmanx = (float)((_snowman & 1) * 2 - 1);
         float _snowmanxx = (float)((_snowman >> 1 & 1) * 2 - 1);
         float _snowmanxxx = (float)((_snowman >> 2 & 1) * 2 - 1);
         _snowmanx *= 0.1F;
         _snowmanxx *= 0.1F;
         _snowmanxxx *= 0.1F;
         Vec3d _snowmanxxxx = this.pos.add((double)_snowmanx, (double)_snowmanxx, (double)_snowmanxxx);
         Vec3d _snowmanxxxxx = new Vec3d(
            this.pos.x - (double)this.horizontalPlane.getX() * desiredCameraDistance + (double)_snowmanx + (double)_snowmanxxx,
            this.pos.y - (double)this.horizontalPlane.getY() * desiredCameraDistance + (double)_snowmanxx,
            this.pos.z - (double)this.horizontalPlane.getZ() * desiredCameraDistance + (double)_snowmanxxx
         );
         HitResult _snowmanxxxxxx = this.area
            .raycast(new RaycastContext(_snowmanxxxx, _snowmanxxxxx, RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, this.focusedEntity));
         if (_snowmanxxxxxx.getType() != HitResult.Type.MISS) {
            double _snowmanxxxxxxx = _snowmanxxxxxx.getPos().distanceTo(this.pos);
            if (_snowmanxxxxxxx < desiredCameraDistance) {
               desiredCameraDistance = _snowmanxxxxxxx;
            }
         }
      }

      return desiredCameraDistance;
   }

   protected void moveBy(double x, double y, double z) {
      double _snowman = (double)this.horizontalPlane.getX() * x + (double)this.verticalPlane.getX() * y + (double)this.diagonalPlane.getX() * z;
      double _snowmanx = (double)this.horizontalPlane.getY() * x + (double)this.verticalPlane.getY() * y + (double)this.diagonalPlane.getY() * z;
      double _snowmanxx = (double)this.horizontalPlane.getZ() * x + (double)this.verticalPlane.getZ() * y + (double)this.diagonalPlane.getZ() * z;
      this.setPos(new Vec3d(this.pos.x + _snowman, this.pos.y + _snowmanx, this.pos.z + _snowmanxx));
   }

   protected void setRotation(float yaw, float pitch) {
      this.pitch = pitch;
      this.yaw = yaw;
      this.rotation.set(0.0F, 0.0F, 0.0F, 1.0F);
      this.rotation.hamiltonProduct(Vector3f.POSITIVE_Y.getDegreesQuaternion(-yaw));
      this.rotation.hamiltonProduct(Vector3f.POSITIVE_X.getDegreesQuaternion(pitch));
      this.horizontalPlane.set(0.0F, 0.0F, 1.0F);
      this.horizontalPlane.rotate(this.rotation);
      this.verticalPlane.set(0.0F, 1.0F, 0.0F);
      this.verticalPlane.rotate(this.rotation);
      this.diagonalPlane.set(1.0F, 0.0F, 0.0F);
      this.diagonalPlane.rotate(this.rotation);
   }

   protected void setPos(double x, double y, double z) {
      this.setPos(new Vec3d(x, y, z));
   }

   protected void setPos(Vec3d pos) {
      this.pos = pos;
      this.blockPos.set(pos.x, pos.y, pos.z);
   }

   public Vec3d getPos() {
      return this.pos;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }

   public float getPitch() {
      return this.pitch;
   }

   public float getYaw() {
      return this.yaw;
   }

   public Quaternion getRotation() {
      return this.rotation;
   }

   public Entity getFocusedEntity() {
      return this.focusedEntity;
   }

   public boolean isReady() {
      return this.ready;
   }

   public boolean isThirdPerson() {
      return this.thirdPerson;
   }

   public FluidState getSubmergedFluidState() {
      if (!this.ready) {
         return Fluids.EMPTY.getDefaultState();
      } else {
         FluidState _snowman = this.area.getFluidState(this.blockPos);
         return !_snowman.isEmpty() && this.pos.y >= (double)((float)this.blockPos.getY() + _snowman.getHeight(this.area, this.blockPos))
            ? Fluids.EMPTY.getDefaultState()
            : _snowman;
      }
   }

   public final Vector3f getHorizontalPlane() {
      return this.horizontalPlane;
   }

   public final Vector3f getVerticalPlane() {
      return this.verticalPlane;
   }

   public void reset() {
      this.area = null;
      this.focusedEntity = null;
      this.ready = false;
   }
}
