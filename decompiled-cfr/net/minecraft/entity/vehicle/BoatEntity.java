/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.vehicle;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.class_5459;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class BoatEntity
extends Entity {
    private static final TrackedData<Integer> DAMAGE_WOBBLE_TICKS = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DAMAGE_WOBBLE_SIDE = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> DAMAGE_WOBBLE_STRENGTH = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> BOAT_TYPE = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> LEFT_PADDLE_MOVING = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RIGHT_PADDLE_MOVING = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> BUBBLE_WOBBLE_TICKS = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final float[] paddlePhases = new float[2];
    private float velocityDecay;
    private float ticksUnderwater;
    private float yawVelocity;
    private int field_7708;
    private double x;
    private double y;
    private double z;
    private double boatYaw;
    private double boatPitch;
    private boolean pressingLeft;
    private boolean pressingRight;
    private boolean pressingForward;
    private boolean pressingBack;
    private double waterLevel;
    private float field_7714;
    private Location location;
    private Location lastLocation;
    private double fallVelocity;
    private boolean onBubbleColumnSurface;
    private boolean bubbleColumnIsDrag;
    private float bubbleWobbleStrength;
    private float bubbleWobble;
    private float lastBubbleWobble;

    public BoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
        this.inanimate = true;
    }

    public BoatEntity(World world, double x, double y, double z) {
        this((EntityType<? extends BoatEntity>)EntityType.BOAT, world);
        this.updatePosition(x, y, z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height;
    }

    @Override
    protected boolean canClimb() {
        return false;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(DAMAGE_WOBBLE_TICKS, 0);
        this.dataTracker.startTracking(DAMAGE_WOBBLE_SIDE, 1);
        this.dataTracker.startTracking(DAMAGE_WOBBLE_STRENGTH, Float.valueOf(0.0f));
        this.dataTracker.startTracking(BOAT_TYPE, Type.OAK.ordinal());
        this.dataTracker.startTracking(LEFT_PADDLE_MOVING, false);
        this.dataTracker.startTracking(RIGHT_PADDLE_MOVING, false);
        this.dataTracker.startTracking(BUBBLE_WOBBLE_TICKS, 0);
    }

    @Override
    public boolean collidesWith(Entity other) {
        return BoatEntity.method_30959(this, other);
    }

    public static boolean method_30959(Entity entity, Entity entity2) {
        return (entity2.isCollidable() || entity2.isPushable()) && !entity.isConnectedThroughVehicle(entity2);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected Vec3d method_30633(Direction.Axis axis, class_5459.class_5460 class_54602) {
        return LivingEntity.method_31079(super.method_30633(axis, class_54602));
    }

    @Override
    public double getMountedHeightOffset() {
        return -0.1;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl;
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (this.world.isClient || this.removed) {
            return true;
        }
        this.setDamageWobbleSide(-this.getDamageWobbleSide());
        this.setDamageWobbleTicks(10);
        this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0f);
        this.scheduleVelocityUpdate();
        boolean bl2 = bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).abilities.creativeMode;
        if (bl || this.getDamageWobbleStrength() > 40.0f) {
            if (!bl && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                this.dropItem(this.asItem());
            }
            this.remove();
        }
        return true;
    }

    @Override
    public void onBubbleColumnSurfaceCollision(boolean drag) {
        if (!this.world.isClient) {
            this.onBubbleColumnSurface = true;
            this.bubbleColumnIsDrag = drag;
            if (this.getBubbleWobbleTicks() == 0) {
                this.setBubbleWobbleTicks(60);
            }
        }
        this.world.addParticle(ParticleTypes.SPLASH, this.getX() + (double)this.random.nextFloat(), this.getY() + 0.7, this.getZ() + (double)this.random.nextFloat(), 0.0, 0.0, 0.0);
        if (this.random.nextInt(20) == 0) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), this.getSplashSound(), this.getSoundCategory(), 1.0f, 0.8f + 0.4f * this.random.nextFloat(), false);
        }
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (entity instanceof BoatEntity) {
            if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.pushAwayFrom(entity);
            }
        } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.pushAwayFrom(entity);
        }
    }

    public Item asItem() {
        switch (this.getBoatType()) {
            default: {
                return Items.OAK_BOAT;
            }
            case SPRUCE: {
                return Items.SPRUCE_BOAT;
            }
            case BIRCH: {
                return Items.BIRCH_BOAT;
            }
            case JUNGLE: {
                return Items.JUNGLE_BOAT;
            }
            case ACACIA: {
                return Items.ACACIA_BOAT;
            }
            case DARK_OAK: 
        }
        return Items.DARK_OAK_BOAT;
    }

    @Override
    public void animateDamage() {
        this.setDamageWobbleSide(-this.getDamageWobbleSide());
        this.setDamageWobbleTicks(10);
        this.setDamageWobbleStrength(this.getDamageWobbleStrength() * 11.0f);
    }

    @Override
    public boolean collides() {
        return !this.removed;
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.boatYaw = yaw;
        this.boatPitch = pitch;
        this.field_7708 = 10;
    }

    @Override
    public Direction getMovementDirection() {
        return this.getHorizontalFacing().rotateYClockwise();
    }

    @Override
    public void tick() {
        this.lastLocation = this.location;
        this.location = this.checkLocation();
        this.ticksUnderwater = this.location == Location.UNDER_WATER || this.location == Location.UNDER_FLOWING_WATER ? (this.ticksUnderwater += 1.0f) : 0.0f;
        if (!this.world.isClient && this.ticksUnderwater >= 60.0f) {
            this.removeAllPassengers();
        }
        if (this.getDamageWobbleTicks() > 0) {
            this.setDamageWobbleTicks(this.getDamageWobbleTicks() - 1);
        }
        if (this.getDamageWobbleStrength() > 0.0f) {
            this.setDamageWobbleStrength(this.getDamageWobbleStrength() - 1.0f);
        }
        super.tick();
        this.method_7555();
        if (this.isLogicalSideForUpdatingMovement()) {
            if (this.getPassengerList().isEmpty() || !(this.getPassengerList().get(0) instanceof PlayerEntity)) {
                this.setPaddleMovings(false, false);
            }
            this.updateVelocity();
            if (this.world.isClient) {
                this.updatePaddles();
                this.world.sendPacket(new BoatPaddleStateC2SPacket(this.isPaddleMoving(0), this.isPaddleMoving(1)));
            }
            this.move(MovementType.SELF, this.getVelocity());
        } else {
            this.setVelocity(Vec3d.ZERO);
        }
        this.handleBubbleColumn();
        for (int i = 0; i <= 1; ++i) {
            if (this.isPaddleMoving(i)) {
                if (!this.isSilent() && (double)(this.paddlePhases[i] % ((float)Math.PI * 2)) <= 0.7853981852531433 && ((double)this.paddlePhases[i] + (double)0.3926991f) % 6.2831854820251465 >= 0.7853981852531433 && (_snowman = this.getPaddleSoundEvent()) != null) {
                    Vec3d vec3d = this.getRotationVec(1.0f);
                    double _snowman2 = i == 1 ? -vec3d.z : vec3d.z;
                    double _snowman3 = i == 1 ? vec3d.x : -vec3d.x;
                    this.world.playSound(null, this.getX() + _snowman2, this.getY(), this.getZ() + _snowman3, _snowman, this.getSoundCategory(), 1.0f, 0.8f + 0.4f * this.random.nextFloat());
                }
                int n = i;
                this.paddlePhases[n] = (float)((double)this.paddlePhases[n] + (double)0.3926991f);
                continue;
            }
            this.paddlePhases[i] = 0.0f;
        }
        this.checkBlockCollision();
        List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.2f, -0.01f, 0.2f), EntityPredicates.canBePushedBy(this));
        if (!list.isEmpty()) {
            boolean bl = !this.world.isClient && !(this.getPrimaryPassenger() instanceof PlayerEntity);
            for (int i = 0; i < list.size(); ++i) {
                Entity entity = list.get(i);
                if (entity.hasPassenger(this)) continue;
                if (bl && this.getPassengerList().size() < 2 && !entity.hasVehicle() && entity.getWidth() < this.getWidth() && entity instanceof LivingEntity && !(entity instanceof WaterCreatureEntity) && !(entity instanceof PlayerEntity)) {
                    entity.startRiding(this);
                    continue;
                }
                this.pushAwayFrom(entity);
            }
        }
    }

    private void handleBubbleColumn() {
        if (this.world.isClient) {
            int n = this.getBubbleWobbleTicks();
            this.bubbleWobbleStrength = n > 0 ? (this.bubbleWobbleStrength += 0.05f) : (this.bubbleWobbleStrength -= 0.1f);
            this.bubbleWobbleStrength = MathHelper.clamp(this.bubbleWobbleStrength, 0.0f, 1.0f);
            this.lastBubbleWobble = this.bubbleWobble;
            this.bubbleWobble = 10.0f * (float)Math.sin(0.5f * (float)this.world.getTime()) * this.bubbleWobbleStrength;
        } else {
            int n;
            if (!this.onBubbleColumnSurface) {
                this.setBubbleWobbleTicks(0);
            }
            if ((n = this.getBubbleWobbleTicks()) > 0) {
                this.setBubbleWobbleTicks(--n);
                _snowman = 60 - n - 1;
                if (_snowman > 0 && n == 0) {
                    this.setBubbleWobbleTicks(0);
                    Vec3d vec3d = this.getVelocity();
                    if (this.bubbleColumnIsDrag) {
                        this.setVelocity(vec3d.add(0.0, -0.7, 0.0));
                        this.removeAllPassengers();
                    } else {
                        this.setVelocity(vec3d.x, this.hasPassengerType(PlayerEntity.class) ? 2.7 : 0.6, vec3d.z);
                    }
                }
                this.onBubbleColumnSurface = false;
            }
        }
    }

    @Nullable
    protected SoundEvent getPaddleSoundEvent() {
        switch (this.checkLocation()) {
            case IN_WATER: 
            case UNDER_WATER: 
            case UNDER_FLOWING_WATER: {
                return SoundEvents.ENTITY_BOAT_PADDLE_WATER;
            }
            case ON_LAND: {
                return SoundEvents.ENTITY_BOAT_PADDLE_LAND;
            }
        }
        return null;
    }

    private void method_7555() {
        if (this.isLogicalSideForUpdatingMovement()) {
            this.field_7708 = 0;
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
        }
        if (this.field_7708 <= 0) {
            return;
        }
        double d = this.getX() + (this.x - this.getX()) / (double)this.field_7708;
        _snowman = this.getY() + (this.y - this.getY()) / (double)this.field_7708;
        _snowman = this.getZ() + (this.z - this.getZ()) / (double)this.field_7708;
        _snowman = MathHelper.wrapDegrees(this.boatYaw - (double)this.yaw);
        this.yaw = (float)((double)this.yaw + _snowman / (double)this.field_7708);
        this.pitch = (float)((double)this.pitch + (this.boatPitch - (double)this.pitch) / (double)this.field_7708);
        --this.field_7708;
        this.updatePosition(d, _snowman, _snowman);
        this.setRotation(this.yaw, this.pitch);
    }

    public void setPaddleMovings(boolean leftMoving, boolean rightMoving) {
        this.dataTracker.set(LEFT_PADDLE_MOVING, leftMoving);
        this.dataTracker.set(RIGHT_PADDLE_MOVING, rightMoving);
    }

    public float interpolatePaddlePhase(int paddle, float tickDelta) {
        if (this.isPaddleMoving(paddle)) {
            return (float)MathHelper.clampedLerp((double)this.paddlePhases[paddle] - (double)0.3926991f, this.paddlePhases[paddle], tickDelta);
        }
        return 0.0f;
    }

    private Location checkLocation() {
        Location location = this.getUnderWaterLocation();
        if (location != null) {
            this.waterLevel = this.getBoundingBox().maxY;
            return location;
        }
        if (this.checkBoatInWater()) {
            return Location.IN_WATER;
        }
        float _snowman2 = this.method_7548();
        if (_snowman2 > 0.0f) {
            this.field_7714 = _snowman2;
            return Location.ON_LAND;
        }
        return Location.IN_AIR;
    }

    public float method_7544() {
        Box box = this.getBoundingBox();
        int _snowman2 = MathHelper.floor(box.minX);
        int _snowman3 = MathHelper.ceil(box.maxX);
        int _snowman4 = MathHelper.floor(box.maxY);
        int _snowman5 = MathHelper.ceil(box.maxY - this.fallVelocity);
        int _snowman6 = MathHelper.floor(box.minZ);
        int _snowman7 = MathHelper.ceil(box.maxZ);
        BlockPos.Mutable _snowman8 = new BlockPos.Mutable();
        block0: for (int i = _snowman4; i < _snowman5; ++i) {
            float f = 0.0f;
            for (int j = _snowman2; j < _snowman3; ++j) {
                for (_snowman = _snowman6; _snowman < _snowman7; ++_snowman) {
                    _snowman8.set(j, i, _snowman);
                    FluidState fluidState = this.world.getFluidState(_snowman8);
                    if (fluidState.isIn(FluidTags.WATER)) {
                        f = Math.max(f, fluidState.getHeight(this.world, _snowman8));
                    }
                    if (f >= 1.0f) continue block0;
                }
            }
            if (!(f < 1.0f)) continue;
            return (float)_snowman8.getY() + f;
        }
        return _snowman5 + 1;
    }

    public float method_7548() {
        Box box = this.getBoundingBox();
        _snowman = new Box(box.minX, box.minY - 0.001, box.minZ, box.maxX, box.minY, box.maxZ);
        int _snowman2 = MathHelper.floor(_snowman.minX) - 1;
        int _snowman3 = MathHelper.ceil(_snowman.maxX) + 1;
        int _snowman4 = MathHelper.floor(_snowman.minY) - 1;
        int _snowman5 = MathHelper.ceil(_snowman.maxY) + 1;
        int _snowman6 = MathHelper.floor(_snowman.minZ) - 1;
        int _snowman7 = MathHelper.ceil(_snowman.maxZ) + 1;
        VoxelShape _snowman8 = VoxelShapes.cuboid(_snowman);
        float _snowman9 = 0.0f;
        int _snowman10 = 0;
        BlockPos.Mutable _snowman11 = new BlockPos.Mutable();
        for (int i = _snowman2; i < _snowman3; ++i) {
            for (_snowman = _snowman6; _snowman < _snowman7; ++_snowman) {
                _snowman = (i == _snowman2 || i == _snowman3 - 1 ? 1 : 0) + (_snowman == _snowman6 || _snowman == _snowman7 - 1 ? 1 : 0);
                if (_snowman == 2) continue;
                for (_snowman = _snowman4; _snowman < _snowman5; ++_snowman) {
                    if (_snowman > 0 && (_snowman == _snowman4 || _snowman == _snowman5 - 1)) continue;
                    _snowman11.set(i, _snowman, _snowman);
                    BlockState blockState = this.world.getBlockState(_snowman11);
                    if (blockState.getBlock() instanceof LilyPadBlock || !VoxelShapes.matchesAnywhere(blockState.getCollisionShape(this.world, _snowman11).offset(i, _snowman, _snowman), _snowman8, BooleanBiFunction.AND)) continue;
                    _snowman9 += blockState.getBlock().getSlipperiness();
                    ++_snowman10;
                }
            }
        }
        return _snowman9 / (float)_snowman10;
    }

    private boolean checkBoatInWater() {
        Box box = this.getBoundingBox();
        int _snowman2 = MathHelper.floor(box.minX);
        int _snowman3 = MathHelper.ceil(box.maxX);
        int _snowman4 = MathHelper.floor(box.minY);
        int _snowman5 = MathHelper.ceil(box.minY + 0.001);
        int _snowman6 = MathHelper.floor(box.minZ);
        int _snowman7 = MathHelper.ceil(box.maxZ);
        boolean _snowman8 = false;
        this.waterLevel = Double.MIN_VALUE;
        BlockPos.Mutable _snowman9 = new BlockPos.Mutable();
        for (int i = _snowman2; i < _snowman3; ++i) {
            for (_snowman = _snowman4; _snowman < _snowman5; ++_snowman) {
                for (_snowman = _snowman6; _snowman < _snowman7; ++_snowman) {
                    _snowman9.set(i, _snowman, _snowman);
                    FluidState fluidState = this.world.getFluidState(_snowman9);
                    if (!fluidState.isIn(FluidTags.WATER)) continue;
                    float _snowman10 = (float)_snowman + fluidState.getHeight(this.world, _snowman9);
                    this.waterLevel = Math.max((double)_snowman10, this.waterLevel);
                    _snowman8 |= box.minY < (double)_snowman10;
                }
            }
        }
        return _snowman8;
    }

    @Nullable
    private Location getUnderWaterLocation() {
        Box box = this.getBoundingBox();
        double _snowman2 = box.maxY + 0.001;
        int _snowman3 = MathHelper.floor(box.minX);
        int _snowman4 = MathHelper.ceil(box.maxX);
        int _snowman5 = MathHelper.floor(box.maxY);
        int _snowman6 = MathHelper.ceil(_snowman2);
        int _snowman7 = MathHelper.floor(box.minZ);
        int _snowman8 = MathHelper.ceil(box.maxZ);
        boolean _snowman9 = false;
        BlockPos.Mutable _snowman10 = new BlockPos.Mutable();
        for (int i = _snowman3; i < _snowman4; ++i) {
            for (_snowman = _snowman5; _snowman < _snowman6; ++_snowman) {
                for (_snowman = _snowman7; _snowman < _snowman8; ++_snowman) {
                    _snowman10.set(i, _snowman, _snowman);
                    FluidState fluidState = this.world.getFluidState(_snowman10);
                    if (!fluidState.isIn(FluidTags.WATER) || !(_snowman2 < (double)((float)_snowman10.getY() + fluidState.getHeight(this.world, _snowman10)))) continue;
                    if (fluidState.isStill()) {
                        _snowman9 = true;
                        continue;
                    }
                    return Location.UNDER_FLOWING_WATER;
                }
            }
        }
        return _snowman9 ? Location.UNDER_WATER : null;
    }

    private void updateVelocity() {
        double d = -0.04f;
        _snowman = this.hasNoGravity() ? 0.0 : (double)-0.04f;
        _snowman = 0.0;
        this.velocityDecay = 0.05f;
        if (this.lastLocation == Location.IN_AIR && this.location != Location.IN_AIR && this.location != Location.ON_LAND) {
            this.waterLevel = this.getBodyY(1.0);
            this.updatePosition(this.getX(), (double)(this.method_7544() - this.getHeight()) + 0.101, this.getZ());
            this.setVelocity(this.getVelocity().multiply(1.0, 0.0, 1.0));
            this.fallVelocity = 0.0;
            this.location = Location.IN_WATER;
        } else {
            if (this.location == Location.IN_WATER) {
                _snowman = (this.waterLevel - this.getY()) / (double)this.getHeight();
                this.velocityDecay = 0.9f;
            } else if (this.location == Location.UNDER_FLOWING_WATER) {
                _snowman = -7.0E-4;
                this.velocityDecay = 0.9f;
            } else if (this.location == Location.UNDER_WATER) {
                _snowman = 0.01f;
                this.velocityDecay = 0.45f;
            } else if (this.location == Location.IN_AIR) {
                this.velocityDecay = 0.9f;
            } else if (this.location == Location.ON_LAND) {
                this.velocityDecay = this.field_7714;
                if (this.getPrimaryPassenger() instanceof PlayerEntity) {
                    this.field_7714 /= 2.0f;
                }
            }
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(vec3d.x * (double)this.velocityDecay, vec3d.y + _snowman, vec3d.z * (double)this.velocityDecay);
            this.yawVelocity *= this.velocityDecay;
            if (_snowman > 0.0) {
                _snowman = this.getVelocity();
                this.setVelocity(_snowman.x, (_snowman.y + _snowman * 0.06153846016296973) * 0.75, _snowman.z);
            }
        }
    }

    private void updatePaddles() {
        if (!this.hasPassengers()) {
            return;
        }
        float f = 0.0f;
        if (this.pressingLeft) {
            this.yawVelocity -= 1.0f;
        }
        if (this.pressingRight) {
            this.yawVelocity += 1.0f;
        }
        if (this.pressingRight != this.pressingLeft && !this.pressingForward && !this.pressingBack) {
            f += 0.005f;
        }
        this.yaw += this.yawVelocity;
        if (this.pressingForward) {
            f += 0.04f;
        }
        if (this.pressingBack) {
            f -= 0.005f;
        }
        this.setVelocity(this.getVelocity().add(MathHelper.sin(-this.yaw * ((float)Math.PI / 180)) * f, 0.0, MathHelper.cos(this.yaw * ((float)Math.PI / 180)) * f));
        this.setPaddleMovings(this.pressingRight && !this.pressingLeft || this.pressingForward, this.pressingLeft && !this.pressingRight || this.pressingForward);
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        if (!this.hasPassenger(passenger)) {
            return;
        }
        float _snowman2 = 0.0f;
        _snowman = (float)((this.removed ? (double)0.01f : this.getMountedHeightOffset()) + passenger.getHeightOffset());
        if (this.getPassengerList().size() > 1) {
            int n = this.getPassengerList().indexOf(passenger);
            _snowman2 = n == 0 ? 0.2f : -0.6f;
            if (passenger instanceof AnimalEntity) {
                _snowman2 = (float)((double)_snowman2 + 0.2);
            }
        }
        Vec3d vec3d = new Vec3d(_snowman2, 0.0, 0.0).rotateY(-this.yaw * ((float)Math.PI / 180) - 1.5707964f);
        passenger.updatePosition(this.getX() + vec3d.x, this.getY() + (double)_snowman, this.getZ() + vec3d.z);
        passenger.yaw += this.yawVelocity;
        passenger.setHeadYaw(passenger.getHeadYaw() + this.yawVelocity);
        this.copyEntityData(passenger);
        if (passenger instanceof AnimalEntity && this.getPassengerList().size() > 1) {
            int n = passenger.getEntityId() % 2 == 0 ? 90 : 270;
            passenger.setYaw(((AnimalEntity)passenger).bodyYaw + (float)n);
            passenger.setHeadYaw(passenger.getHeadYaw() + (float)n);
        }
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = BoatEntity.getPassengerDismountOffset(this.getWidth() * MathHelper.SQUARE_ROOT_OF_TWO, passenger.getWidth(), this.yaw);
        double _snowman2 = this.getX() + vec3d.x;
        BlockPos _snowman3 = new BlockPos(_snowman2, this.getBoundingBox().maxY, _snowman = this.getZ() + vec3d.z);
        BlockPos _snowman4 = _snowman3.down();
        if (!this.world.isWater(_snowman4)) {
            double d = (double)_snowman3.getY() + this.world.getDismountHeight(_snowman3);
            _snowman = (double)_snowman3.getY() + this.world.getDismountHeight(_snowman4);
            for (EntityPose entityPose : passenger.getPoses()) {
                Vec3d vec3d2 = Dismounting.findDismountPos(this.world, _snowman2, d, _snowman, passenger, entityPose);
                if (vec3d2 != null) {
                    passenger.setPose(entityPose);
                    return vec3d2;
                }
                _snowman = Dismounting.findDismountPos(this.world, _snowman2, _snowman, _snowman, passenger, entityPose);
                if (_snowman == null) continue;
                passenger.setPose(entityPose);
                return _snowman;
            }
        }
        return super.updatePassengerForDismount(passenger);
    }

    protected void copyEntityData(Entity entity) {
        entity.setYaw(this.yaw);
        float f = MathHelper.wrapDegrees(entity.yaw - this.yaw);
        _snowman = MathHelper.clamp(f, -105.0f, 105.0f);
        entity.prevYaw += _snowman - f;
        entity.yaw += _snowman - f;
        entity.setHeadYaw(entity.yaw);
    }

    @Override
    public void onPassengerLookAround(Entity passenger) {
        this.copyEntityData(passenger);
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.putString("Type", this.getBoatType().getName());
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        if (tag.contains("Type", 8)) {
            this.setBoatType(Type.getType(tag.getString("Type")));
        }
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        }
        if (this.ticksUnderwater < 60.0f) {
            if (!this.world.isClient) {
                return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        this.fallVelocity = this.getVelocity().y;
        if (this.hasVehicle()) {
            return;
        }
        if (onGround) {
            if (this.fallDistance > 3.0f) {
                if (this.location != Location.ON_LAND) {
                    this.fallDistance = 0.0f;
                    return;
                }
                this.handleFallDamage(this.fallDistance, 1.0f);
                if (!this.world.isClient && !this.removed) {
                    this.remove();
                    if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                        int n;
                        for (n = 0; n < 3; ++n) {
                            this.dropItem(this.getBoatType().getBaseBlock());
                        }
                        for (n = 0; n < 2; ++n) {
                            this.dropItem(Items.STICK);
                        }
                    }
                }
            }
            this.fallDistance = 0.0f;
        } else if (!this.world.getFluidState(this.getBlockPos().down()).isIn(FluidTags.WATER) && heightDifference < 0.0) {
            this.fallDistance = (float)((double)this.fallDistance - heightDifference);
        }
    }

    public boolean isPaddleMoving(int paddle) {
        return this.dataTracker.get(paddle == 0 ? LEFT_PADDLE_MOVING : RIGHT_PADDLE_MOVING) != false && this.getPrimaryPassenger() != null;
    }

    public void setDamageWobbleStrength(float wobbleStrength) {
        this.dataTracker.set(DAMAGE_WOBBLE_STRENGTH, Float.valueOf(wobbleStrength));
    }

    public float getDamageWobbleStrength() {
        return this.dataTracker.get(DAMAGE_WOBBLE_STRENGTH).floatValue();
    }

    public void setDamageWobbleTicks(int wobbleTicks) {
        this.dataTracker.set(DAMAGE_WOBBLE_TICKS, wobbleTicks);
    }

    public int getDamageWobbleTicks() {
        return this.dataTracker.get(DAMAGE_WOBBLE_TICKS);
    }

    private void setBubbleWobbleTicks(int wobbleTicks) {
        this.dataTracker.set(BUBBLE_WOBBLE_TICKS, wobbleTicks);
    }

    private int getBubbleWobbleTicks() {
        return this.dataTracker.get(BUBBLE_WOBBLE_TICKS);
    }

    public float interpolateBubbleWobble(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastBubbleWobble, this.bubbleWobble);
    }

    public void setDamageWobbleSide(int side) {
        this.dataTracker.set(DAMAGE_WOBBLE_SIDE, side);
    }

    public int getDamageWobbleSide() {
        return this.dataTracker.get(DAMAGE_WOBBLE_SIDE);
    }

    public void setBoatType(Type type) {
        this.dataTracker.set(BOAT_TYPE, type.ordinal());
    }

    public Type getBoatType() {
        return Type.getType(this.dataTracker.get(BOAT_TYPE));
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < 2 && !this.isSubmergedIn(FluidTags.WATER);
    }

    @Override
    @Nullable
    public Entity getPrimaryPassenger() {
        List<Entity> list = this.getPassengerList();
        return list.isEmpty() ? null : list.get(0);
    }

    public void setInputs(boolean pressingLeft, boolean pressingRight, boolean pressingForward, boolean pressingBack) {
        this.pressingLeft = pressingLeft;
        this.pressingRight = pressingRight;
        this.pressingForward = pressingForward;
        this.pressingBack = pressingBack;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public boolean isSubmergedInWater() {
        return this.location == Location.UNDER_WATER || this.location == Location.UNDER_FLOWING_WATER;
    }

    public static enum Type {
        OAK(Blocks.OAK_PLANKS, "oak"),
        SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
        BIRCH(Blocks.BIRCH_PLANKS, "birch"),
        JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
        ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
        DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak");

        private final String name;
        private final Block baseBlock;

        private Type(Block baseBlock, String name) {
            this.name = name;
            this.baseBlock = baseBlock;
        }

        public String getName() {
            return this.name;
        }

        public Block getBaseBlock() {
            return this.baseBlock;
        }

        public String toString() {
            return this.name;
        }

        public static Type getType(int n2) {
            Type[] typeArray = Type.values();
            if (n2 < 0 || n2 >= typeArray.length) {
                int n2 = 0;
            }
            return typeArray[n2];
        }

        public static Type getType(String string) {
            Type[] typeArray = Type.values();
            for (int i = 0; i < typeArray.length; ++i) {
                if (!typeArray[i].getName().equals(string)) continue;
                return typeArray[i];
            }
            return typeArray[0];
        }
    }

    public static enum Location {
        IN_WATER,
        UNDER_WATER,
        UNDER_FLOWING_WATER,
        ON_LAND,
        IN_AIR;

    }
}

