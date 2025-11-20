/*
 * Decompiled with CFR 0.152.
 */
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
    private final Vector3f horizontalPlane = new Vector3f(0.0f, 0.0f, 1.0f);
    private final Vector3f verticalPlane = new Vector3f(0.0f, 1.0f, 0.0f);
    private final Vector3f diagonalPlane = new Vector3f(1.0f, 0.0f, 0.0f);
    private float pitch;
    private float yaw;
    private final Quaternion rotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
    private boolean thirdPerson;
    private boolean inverseView;
    private float cameraY;
    private float lastCameraY;

    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta) {
        this.ready = true;
        this.area = area;
        this.focusedEntity = focusedEntity;
        this.thirdPerson = thirdPerson;
        this.inverseView = inverseView;
        this.setRotation(focusedEntity.getYaw(tickDelta), focusedEntity.getPitch(tickDelta));
        this.setPos(MathHelper.lerp((double)tickDelta, focusedEntity.prevX, focusedEntity.getX()), MathHelper.lerp((double)tickDelta, focusedEntity.prevY, focusedEntity.getY()) + (double)MathHelper.lerp(tickDelta, this.lastCameraY, this.cameraY), MathHelper.lerp((double)tickDelta, focusedEntity.prevZ, focusedEntity.getZ()));
        if (thirdPerson) {
            if (inverseView) {
                this.setRotation(this.yaw + 180.0f, -this.pitch);
            }
            this.moveBy(-this.clipToSpace(4.0), 0.0, 0.0);
        } else if (focusedEntity instanceof LivingEntity && ((LivingEntity)focusedEntity).isSleeping()) {
            Direction direction = ((LivingEntity)focusedEntity).getSleepingDirection();
            this.setRotation(direction != null ? direction.asRotation() - 180.0f : 0.0f, 0.0f);
            this.moveBy(0.0, 0.3, 0.0);
        }
    }

    public void updateEyeHeight() {
        if (this.focusedEntity != null) {
            this.lastCameraY = this.cameraY;
            this.cameraY += (this.focusedEntity.getStandingEyeHeight() - this.cameraY) * 0.5f;
        }
    }

    private double clipToSpace(double desiredCameraDistance) {
        for (int i = 0; i < 8; ++i) {
            float f = (i & 1) * 2 - 1;
            _snowman = (i >> 1 & 1) * 2 - 1;
            _snowman = (i >> 2 & 1) * 2 - 1;
            Vec3d _snowman2 = this.pos.add(f *= 0.1f, _snowman *= 0.1f, _snowman *= 0.1f);
            if (((HitResult)(_snowman = this.area.raycast(new RaycastContext(_snowman2, _snowman = new Vec3d(this.pos.x - (double)this.horizontalPlane.getX() * desiredCameraDistance + (double)f + (double)_snowman, this.pos.y - (double)this.horizontalPlane.getY() * desiredCameraDistance + (double)_snowman, this.pos.z - (double)this.horizontalPlane.getZ() * desiredCameraDistance + (double)_snowman), RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, this.focusedEntity)))).getType() == HitResult.Type.MISS || !((_snowman = _snowman.getPos().distanceTo(this.pos)) < desiredCameraDistance)) continue;
            desiredCameraDistance = _snowman;
        }
        return desiredCameraDistance;
    }

    protected void moveBy(double x, double y, double z) {
        double d = (double)this.horizontalPlane.getX() * x + (double)this.verticalPlane.getX() * y + (double)this.diagonalPlane.getX() * z;
        _snowman = (double)this.horizontalPlane.getY() * x + (double)this.verticalPlane.getY() * y + (double)this.diagonalPlane.getY() * z;
        _snowman = (double)this.horizontalPlane.getZ() * x + (double)this.verticalPlane.getZ() * y + (double)this.diagonalPlane.getZ() * z;
        this.setPos(new Vec3d(this.pos.x + d, this.pos.y + _snowman, this.pos.z + _snowman));
    }

    protected void setRotation(float yaw, float pitch) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.rotation.set(0.0f, 0.0f, 0.0f, 1.0f);
        this.rotation.hamiltonProduct(Vector3f.POSITIVE_Y.getDegreesQuaternion(-yaw));
        this.rotation.hamiltonProduct(Vector3f.POSITIVE_X.getDegreesQuaternion(pitch));
        this.horizontalPlane.set(0.0f, 0.0f, 1.0f);
        this.horizontalPlane.rotate(this.rotation);
        this.verticalPlane.set(0.0f, 1.0f, 0.0f);
        this.verticalPlane.rotate(this.rotation);
        this.diagonalPlane.set(1.0f, 0.0f, 0.0f);
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
        }
        FluidState fluidState = this.area.getFluidState(this.blockPos);
        if (!fluidState.isEmpty() && this.pos.y >= (double)((float)this.blockPos.getY() + fluidState.getHeight(this.area, this.blockPos))) {
            return Fluids.EMPTY.getDefaultState();
        }
        return fluidState;
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

