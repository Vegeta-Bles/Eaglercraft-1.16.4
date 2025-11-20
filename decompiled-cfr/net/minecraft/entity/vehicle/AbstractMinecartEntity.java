/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.UnmodifiableIterator
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.vehicle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.enums.RailShape;
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
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.entity.vehicle.CommandBlockMinecartEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.entity.vehicle.SpawnerMinecartEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class AbstractMinecartEntity
extends Entity {
    private static final TrackedData<Integer> DAMAGE_WOBBLE_TICKS = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DAMAGE_WOBBLE_SIDE = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> DAMAGE_WOBBLE_STRENGTH = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> CUSTOM_BLOCK_ID = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> CUSTOM_BLOCK_OFFSET = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> CUSTOM_BLOCK_PRESENT = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final ImmutableMap<EntityPose, ImmutableList<Integer>> DISMOUNT_FREE_Y_SPACES_NEEDED = ImmutableMap.of((Object)((Object)EntityPose.STANDING), (Object)ImmutableList.of((Object)0, (Object)1, (Object)-1), (Object)((Object)EntityPose.CROUCHING), (Object)ImmutableList.of((Object)0, (Object)1, (Object)-1), (Object)((Object)EntityPose.SWIMMING), (Object)ImmutableList.of((Object)0, (Object)1));
    private boolean yawFlipped;
    private static final Map<RailShape, Pair<Vec3i, Vec3i>> ADJACENT_RAIL_POSITIONS_BY_SHAPE = Util.make(Maps.newEnumMap(RailShape.class), enumMap -> {
        Vec3i vec3i = Direction.WEST.getVector();
        _snowman = Direction.EAST.getVector();
        _snowman = Direction.NORTH.getVector();
        _snowman = Direction.SOUTH.getVector();
        _snowman = vec3i.down();
        _snowman = _snowman.down();
        _snowman = _snowman.down();
        _snowman = _snowman.down();
        enumMap.put(RailShape.NORTH_SOUTH, Pair.of((Object)_snowman, (Object)_snowman));
        enumMap.put(RailShape.EAST_WEST, Pair.of((Object)vec3i, (Object)_snowman));
        enumMap.put(RailShape.ASCENDING_EAST, Pair.of((Object)_snowman, (Object)_snowman));
        enumMap.put(RailShape.ASCENDING_WEST, Pair.of((Object)vec3i, (Object)_snowman));
        enumMap.put(RailShape.ASCENDING_NORTH, Pair.of((Object)_snowman, (Object)_snowman));
        enumMap.put(RailShape.ASCENDING_SOUTH, Pair.of((Object)_snowman, (Object)_snowman));
        enumMap.put(RailShape.SOUTH_EAST, Pair.of((Object)_snowman, (Object)_snowman));
        enumMap.put(RailShape.SOUTH_WEST, Pair.of((Object)_snowman, (Object)vec3i));
        enumMap.put(RailShape.NORTH_WEST, Pair.of((Object)_snowman, (Object)vec3i));
        enumMap.put(RailShape.NORTH_EAST, Pair.of((Object)_snowman, (Object)_snowman));
    });
    private int clientInterpolationSteps;
    private double clientX;
    private double clientY;
    private double clientZ;
    private double clientYaw;
    private double clientPitch;
    private double clientXVelocity;
    private double clientYVelocity;
    private double clientZVelocity;

    protected AbstractMinecartEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.inanimate = true;
    }

    protected AbstractMinecartEntity(EntityType<?> type, World world, double x, double y, double z) {
        this(type, world);
        this.updatePosition(x, y, z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    public static AbstractMinecartEntity create(World world, double x, double y, double z, Type type) {
        if (type == Type.CHEST) {
            return new ChestMinecartEntity(world, x, y, z);
        }
        if (type == Type.FURNACE) {
            return new FurnaceMinecartEntity(world, x, y, z);
        }
        if (type == Type.TNT) {
            return new TntMinecartEntity(world, x, y, z);
        }
        if (type == Type.SPAWNER) {
            return new SpawnerMinecartEntity(world, x, y, z);
        }
        if (type == Type.HOPPER) {
            return new HopperMinecartEntity(world, x, y, z);
        }
        if (type == Type.COMMAND_BLOCK) {
            return new CommandBlockMinecartEntity(world, x, y, z);
        }
        return new MinecartEntity(world, x, y, z);
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
        this.dataTracker.startTracking(CUSTOM_BLOCK_ID, Block.getRawIdFromState(Blocks.AIR.getDefaultState()));
        this.dataTracker.startTracking(CUSTOM_BLOCK_OFFSET, 6);
        this.dataTracker.startTracking(CUSTOM_BLOCK_PRESENT, false);
    }

    @Override
    public boolean collidesWith(Entity other) {
        return BoatEntity.method_30959(this, other);
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
        return 0.0;
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.updatePassengerForDismount(passenger);
        }
        int[][] _snowman2 = Dismounting.getDismountOffsets(direction);
        BlockPos _snowman3 = this.getBlockPos();
        BlockPos.Mutable _snowman4 = new BlockPos.Mutable();
        ImmutableList<EntityPose> _snowman5 = passenger.getPoses();
        for (EntityPose entityPose : _snowman5) {
            EntityDimensions entityDimensions = passenger.getDimensions(entityPose);
            float _snowman6 = Math.min(entityDimensions.width, 1.0f) / 2.0f;
            UnmodifiableIterator unmodifiableIterator = ((ImmutableList)DISMOUNT_FREE_Y_SPACES_NEEDED.get((Object)entityPose)).iterator();
            while (unmodifiableIterator.hasNext()) {
                int n = (Integer)unmodifiableIterator.next();
                for (int[] nArray : _snowman2) {
                    _snowman4.set(_snowman3.getX() + nArray[0], _snowman3.getY() + n, _snowman3.getZ() + nArray[1]);
                    double d = this.world.getDismountHeight(Dismounting.getCollisionShape(this.world, _snowman4), () -> Dismounting.getCollisionShape(this.world, (BlockPos)_snowman4.down()));
                    if (!Dismounting.canDismountInBlock(d) || !Dismounting.canPlaceEntityAt(this.world, passenger, (_snowman = new Box(-_snowman6, 0.0, -_snowman6, _snowman6, entityDimensions.height, _snowman6)).offset(_snowman = Vec3d.ofCenter(_snowman4, d)))) continue;
                    passenger.setPose(entityPose);
                    return _snowman;
                }
            }
        }
        double d = this.getBoundingBox().maxY;
        _snowman4.set((double)_snowman3.getX(), d, (double)_snowman3.getZ());
        for (EntityPose entityPose : _snowman5) {
            double d2 = passenger.getDimensions((EntityPose)entityPose).height;
            int _snowman7 = MathHelper.ceil(d - (double)_snowman4.getY() + d2);
            _snowman = Dismounting.getCeilingHeight(_snowman4, _snowman7, blockPos -> this.world.getBlockState((BlockPos)blockPos).getCollisionShape(this.world, (BlockPos)blockPos));
            if (!(d + d2 <= _snowman)) continue;
            passenger.setPose(entityPose);
            break;
        }
        return super.updatePassengerForDismount(passenger);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl;
        if (this.world.isClient || this.removed) {
            return true;
        }
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        this.setDamageWobbleSide(-this.getDamageWobbleSide());
        this.setDamageWobbleTicks(10);
        this.scheduleVelocityUpdate();
        this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0f);
        boolean bl2 = bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).abilities.creativeMode;
        if (bl || this.getDamageWobbleStrength() > 40.0f) {
            this.removeAllPassengers();
            if (!bl || this.hasCustomName()) {
                this.dropItems(source);
            } else {
                this.remove();
            }
        }
        return true;
    }

    @Override
    protected float getVelocityMultiplier() {
        BlockState blockState = this.world.getBlockState(this.getBlockPos());
        if (blockState.isIn(BlockTags.RAILS)) {
            return 1.0f;
        }
        return super.getVelocityMultiplier();
    }

    public void dropItems(DamageSource damageSource) {
        this.remove();
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            ItemStack itemStack = new ItemStack(Items.MINECART);
            if (this.hasCustomName()) {
                itemStack.setCustomName(this.getCustomName());
            }
            this.dropStack(itemStack);
        }
    }

    @Override
    public void animateDamage() {
        this.setDamageWobbleSide(-this.getDamageWobbleSide());
        this.setDamageWobbleTicks(10);
        this.setDamageWobbleStrength(this.getDamageWobbleStrength() + this.getDamageWobbleStrength() * 10.0f);
    }

    @Override
    public boolean collides() {
        return !this.removed;
    }

    private static Pair<Vec3i, Vec3i> getAdjacentRailPositionsByShape(RailShape shape) {
        return ADJACENT_RAIL_POSITIONS_BY_SHAPE.get(shape);
    }

    @Override
    public Direction getMovementDirection() {
        return this.yawFlipped ? this.getHorizontalFacing().getOpposite().rotateYClockwise() : this.getHorizontalFacing().rotateYClockwise();
    }

    @Override
    public void tick() {
        int n;
        if (this.getDamageWobbleTicks() > 0) {
            this.setDamageWobbleTicks(this.getDamageWobbleTicks() - 1);
        }
        if (this.getDamageWobbleStrength() > 0.0f) {
            this.setDamageWobbleStrength(this.getDamageWobbleStrength() - 1.0f);
        }
        if (this.getY() < -64.0) {
            this.destroy();
        }
        this.tickNetherPortal();
        if (this.world.isClient) {
            if (this.clientInterpolationSteps > 0) {
                double d = this.getX() + (this.clientX - this.getX()) / (double)this.clientInterpolationSteps;
                _snowman = this.getY() + (this.clientY - this.getY()) / (double)this.clientInterpolationSteps;
                _snowman = this.getZ() + (this.clientZ - this.getZ()) / (double)this.clientInterpolationSteps;
                _snowman = MathHelper.wrapDegrees(this.clientYaw - (double)this.yaw);
                this.yaw = (float)((double)this.yaw + _snowman / (double)this.clientInterpolationSteps);
                this.pitch = (float)((double)this.pitch + (this.clientPitch - (double)this.pitch) / (double)this.clientInterpolationSteps);
                --this.clientInterpolationSteps;
                this.updatePosition(d, _snowman, _snowman);
                this.setRotation(this.yaw, this.pitch);
            } else {
                this.refreshPosition();
                this.setRotation(this.yaw, this.pitch);
            }
            return;
        }
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }
        if (this.world.getBlockState(new BlockPos(n = MathHelper.floor(this.getX()), (_snowman = MathHelper.floor(this.getY())) - 1, _snowman = MathHelper.floor(this.getZ()))).isIn(BlockTags.RAILS)) {
            --_snowman;
        }
        if (AbstractRailBlock.isRail(_snowman = this.world.getBlockState(_snowman = new BlockPos(n, _snowman, _snowman)))) {
            this.moveOnRail(_snowman, _snowman);
            if (_snowman.isOf(Blocks.ACTIVATOR_RAIL)) {
                this.onActivatorRail(n, _snowman, _snowman, _snowman.get(PoweredRailBlock.POWERED));
            }
        } else {
            this.moveOffRail();
        }
        this.checkBlockCollision();
        this.pitch = 0.0f;
        double _snowman2 = this.prevX - this.getX();
        double _snowman3 = this.prevZ - this.getZ();
        if (_snowman2 * _snowman2 + _snowman3 * _snowman3 > 0.001) {
            this.yaw = (float)(MathHelper.atan2(_snowman3, _snowman2) * 180.0 / Math.PI);
            if (this.yawFlipped) {
                this.yaw += 180.0f;
            }
        }
        if ((_snowman = (double)MathHelper.wrapDegrees(this.yaw - this.prevYaw)) < -170.0 || _snowman >= 170.0) {
            this.yaw += 180.0f;
            this.yawFlipped = !this.yawFlipped;
        }
        this.setRotation(this.yaw, this.pitch);
        if (this.getMinecartType() == Type.RIDEABLE && AbstractMinecartEntity.squaredHorizontalLength(this.getVelocity()) > 0.01) {
            List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f), EntityPredicates.canBePushedBy(this));
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); ++i) {
                    Entity entity = list.get(i);
                    if (entity instanceof PlayerEntity || entity instanceof IronGolemEntity || entity instanceof AbstractMinecartEntity || this.hasPassengers() || entity.hasVehicle()) {
                        entity.pushAwayFrom(this);
                        continue;
                    }
                    entity.startRiding(this);
                }
            }
        } else {
            for (Entity entity : this.world.getOtherEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f))) {
                if (this.hasPassenger(entity) || !entity.isPushable() || !(entity instanceof AbstractMinecartEntity)) continue;
                entity.pushAwayFrom(this);
            }
        }
        this.updateWaterState();
        if (this.isInLava()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5f;
        }
        this.firstUpdate = false;
    }

    protected double getMaxOffRailSpeed() {
        return 0.4;
    }

    public void onActivatorRail(int x, int y, int z, boolean powered) {
    }

    protected void moveOffRail() {
        double d = this.getMaxOffRailSpeed();
        Vec3d _snowman2 = this.getVelocity();
        this.setVelocity(MathHelper.clamp(_snowman2.x, -d, d), _snowman2.y, MathHelper.clamp(_snowman2.z, -d, d));
        if (this.onGround) {
            this.setVelocity(this.getVelocity().multiply(0.5));
        }
        this.move(MovementType.SELF, this.getVelocity());
        if (!this.onGround) {
            this.setVelocity(this.getVelocity().multiply(0.95));
        }
    }

    protected void moveOnRail(BlockPos pos, BlockState state) {
        double _snowman15;
        Vec3d vec3d;
        this.fallDistance = 0.0f;
        double d = this.getX();
        _snowman = this.getY();
        _snowman = this.getZ();
        Vec3d _snowman2 = this.snapPositionToRail(d, _snowman, _snowman);
        _snowman = pos.getY();
        boolean _snowman3 = false;
        boolean _snowman4 = false;
        AbstractRailBlock _snowman5 = (AbstractRailBlock)state.getBlock();
        if (_snowman5 == Blocks.POWERED_RAIL) {
            _snowman3 = state.get(PoweredRailBlock.POWERED);
            _snowman4 = !_snowman3;
        }
        _snowman = 0.0078125;
        Vec3d _snowman6 = this.getVelocity();
        RailShape _snowman7 = state.get(_snowman5.getShapeProperty());
        switch (_snowman7) {
            case ASCENDING_EAST: {
                this.setVelocity(_snowman6.add(-0.0078125, 0.0, 0.0));
                _snowman += 1.0;
                break;
            }
            case ASCENDING_WEST: {
                this.setVelocity(_snowman6.add(0.0078125, 0.0, 0.0));
                _snowman += 1.0;
                break;
            }
            case ASCENDING_NORTH: {
                this.setVelocity(_snowman6.add(0.0, 0.0, 0.0078125));
                _snowman += 1.0;
                break;
            }
            case ASCENDING_SOUTH: {
                this.setVelocity(_snowman6.add(0.0, 0.0, -0.0078125));
                _snowman += 1.0;
            }
        }
        _snowman6 = this.getVelocity();
        Pair<Vec3i, Vec3i> _snowman8 = AbstractMinecartEntity.getAdjacentRailPositionsByShape(_snowman7);
        Vec3i _snowman9 = (Vec3i)_snowman8.getFirst();
        Vec3i _snowman10 = (Vec3i)_snowman8.getSecond();
        _snowman = _snowman10.getX() - _snowman9.getX();
        _snowman = _snowman10.getZ() - _snowman9.getZ();
        _snowman = Math.sqrt(_snowman * _snowman + _snowman * _snowman);
        _snowman = _snowman6.x * _snowman + _snowman6.z * _snowman;
        if (_snowman < 0.0) {
            _snowman = -_snowman;
            _snowman = -_snowman;
        }
        _snowman = Math.min(2.0, Math.sqrt(AbstractMinecartEntity.squaredHorizontalLength(_snowman6)));
        _snowman6 = new Vec3d(_snowman * _snowman / _snowman, _snowman6.y, _snowman * _snowman / _snowman);
        this.setVelocity(_snowman6);
        Entity entity = _snowman = this.getPassengerList().isEmpty() ? null : this.getPassengerList().get(0);
        if (_snowman instanceof PlayerEntity) {
            Vec3d vec3d2 = _snowman.getVelocity();
            double _snowman11 = AbstractMinecartEntity.squaredHorizontalLength(vec3d2);
            double _snowman12 = AbstractMinecartEntity.squaredHorizontalLength(this.getVelocity());
            if (_snowman11 > 1.0E-4 && _snowman12 < 0.01) {
                this.setVelocity(this.getVelocity().add(vec3d2.x * 0.1, 0.0, vec3d2.z * 0.1));
                _snowman4 = false;
            }
        }
        if (_snowman4) {
            double _snowman13 = Math.sqrt(AbstractMinecartEntity.squaredHorizontalLength(this.getVelocity()));
            if (_snowman13 < 0.03) {
                this.setVelocity(Vec3d.ZERO);
            } else {
                this.setVelocity(this.getVelocity().multiply(0.5, 0.0, 0.5));
            }
        }
        double d2 = (double)pos.getX() + 0.5 + (double)_snowman9.getX() * 0.5;
        _snowman = (double)pos.getZ() + 0.5 + (double)_snowman9.getZ() * 0.5;
        _snowman = (double)pos.getX() + 0.5 + (double)_snowman10.getX() * 0.5;
        _snowman = (double)pos.getZ() + 0.5 + (double)_snowman10.getZ() * 0.5;
        _snowman = _snowman - d2;
        _snowman = _snowman - _snowman;
        if (_snowman == 0.0) {
            _snowman = _snowman - (double)pos.getZ();
        } else if (_snowman == 0.0) {
            _snowman = d - (double)pos.getX();
        } else {
            _snowman = d - d2;
            _snowman = _snowman - _snowman;
            _snowman = (_snowman * _snowman + _snowman * _snowman) * 2.0;
        }
        d = d2 + _snowman * _snowman;
        _snowman = _snowman + _snowman * _snowman;
        this.updatePosition(d, _snowman, _snowman);
        _snowman = this.hasPassengers() ? 0.75 : 1.0;
        _snowman = this.getMaxOffRailSpeed();
        _snowman6 = this.getVelocity();
        this.move(MovementType.SELF, new Vec3d(MathHelper.clamp(_snowman * _snowman6.x, -_snowman, _snowman), 0.0, MathHelper.clamp(_snowman * _snowman6.z, -_snowman, _snowman)));
        if (_snowman9.getY() != 0 && MathHelper.floor(this.getX()) - pos.getX() == _snowman9.getX() && MathHelper.floor(this.getZ()) - pos.getZ() == _snowman9.getZ()) {
            this.updatePosition(this.getX(), this.getY() + (double)_snowman9.getY(), this.getZ());
        } else if (_snowman10.getY() != 0 && MathHelper.floor(this.getX()) - pos.getX() == _snowman10.getX() && MathHelper.floor(this.getZ()) - pos.getZ() == _snowman10.getZ()) {
            this.updatePosition(this.getX(), this.getY() + (double)_snowman10.getY(), this.getZ());
        }
        this.applySlowdown();
        Vec3d _snowman14 = this.snapPositionToRail(this.getX(), this.getY(), this.getZ());
        if (_snowman14 != null && _snowman2 != null) {
            _snowman = (_snowman2.y - _snowman14.y) * 0.05;
            vec3d = this.getVelocity();
            _snowman15 = Math.sqrt(AbstractMinecartEntity.squaredHorizontalLength(vec3d));
            if (_snowman15 > 0.0) {
                this.setVelocity(vec3d.multiply((_snowman15 + _snowman) / _snowman15, 1.0, (_snowman15 + _snowman) / _snowman15));
            }
            this.updatePosition(this.getX(), _snowman14.y, this.getZ());
        }
        int n = MathHelper.floor(this.getX());
        _snowman = MathHelper.floor(this.getZ());
        if (n != pos.getX() || _snowman != pos.getZ()) {
            vec3d = this.getVelocity();
            _snowman15 = Math.sqrt(AbstractMinecartEntity.squaredHorizontalLength(vec3d));
            this.setVelocity(_snowman15 * (double)(n - pos.getX()), vec3d.y, _snowman15 * (double)(_snowman - pos.getZ()));
        }
        if (_snowman3) {
            vec3d = this.getVelocity();
            _snowman15 = Math.sqrt(AbstractMinecartEntity.squaredHorizontalLength(vec3d));
            if (_snowman15 > 0.01) {
                double d3 = 0.06;
                this.setVelocity(vec3d.add(vec3d.x / _snowman15 * 0.06, 0.0, vec3d.z / _snowman15 * 0.06));
            } else {
                Vec3d vec3d3 = this.getVelocity();
                double _snowman16 = vec3d3.x;
                double _snowman17 = vec3d3.z;
                if (_snowman7 == RailShape.EAST_WEST) {
                    if (this.willHitBlockAt(pos.west())) {
                        _snowman16 = 0.02;
                    } else if (this.willHitBlockAt(pos.east())) {
                        _snowman16 = -0.02;
                    }
                } else if (_snowman7 == RailShape.NORTH_SOUTH) {
                    if (this.willHitBlockAt(pos.north())) {
                        _snowman17 = 0.02;
                    } else if (this.willHitBlockAt(pos.south())) {
                        _snowman17 = -0.02;
                    }
                } else {
                    return;
                }
                this.setVelocity(_snowman16, vec3d3.y, _snowman17);
            }
        }
    }

    private boolean willHitBlockAt(BlockPos pos) {
        return this.world.getBlockState(pos).isSolidBlock(this.world, pos);
    }

    protected void applySlowdown() {
        double d = this.hasPassengers() ? 0.997 : 0.96;
        this.setVelocity(this.getVelocity().multiply(d, 0.0, d));
    }

    @Nullable
    public Vec3d snapPositionToRailWithOffset(double x, double y, double z, double offset) {
        int n = MathHelper.floor(x);
        if (this.world.getBlockState(new BlockPos(n, (_snowman = MathHelper.floor(y)) - 1, _snowman = MathHelper.floor(z))).isIn(BlockTags.RAILS)) {
            --_snowman;
        }
        if (AbstractRailBlock.isRail(_snowman = this.world.getBlockState(new BlockPos(n, _snowman, _snowman)))) {
            RailShape railShape = _snowman.get(((AbstractRailBlock)_snowman.getBlock()).getShapeProperty());
            y = _snowman;
            if (railShape.isAscending()) {
                y = _snowman + 1;
            }
            Pair<Vec3i, Vec3i> _snowman2 = AbstractMinecartEntity.getAdjacentRailPositionsByShape(railShape);
            Vec3i _snowman3 = (Vec3i)_snowman2.getFirst();
            Vec3i _snowman4 = (Vec3i)_snowman2.getSecond();
            double _snowman5 = _snowman4.getX() - _snowman3.getX();
            double _snowman6 = _snowman4.getZ() - _snowman3.getZ();
            double _snowman7 = Math.sqrt(_snowman5 * _snowman5 + _snowman6 * _snowman6);
            if (_snowman3.getY() != 0 && MathHelper.floor(x += (_snowman5 /= _snowman7) * offset) - n == _snowman3.getX() && MathHelper.floor(z += (_snowman6 /= _snowman7) * offset) - _snowman == _snowman3.getZ()) {
                y += (double)_snowman3.getY();
            } else if (_snowman4.getY() != 0 && MathHelper.floor(x) - n == _snowman4.getX() && MathHelper.floor(z) - _snowman == _snowman4.getZ()) {
                y += (double)_snowman4.getY();
            }
            return this.snapPositionToRail(x, y, z);
        }
        return null;
    }

    @Nullable
    public Vec3d snapPositionToRail(double x, double y, double z) {
        int n = MathHelper.floor(x);
        if (this.world.getBlockState(new BlockPos(n, (_snowman = MathHelper.floor(y)) - 1, _snowman = MathHelper.floor(z))).isIn(BlockTags.RAILS)) {
            --_snowman;
        }
        if (AbstractRailBlock.isRail(_snowman = this.world.getBlockState(new BlockPos(n, _snowman, _snowman)))) {
            double _snowman14;
            RailShape railShape = _snowman.get(((AbstractRailBlock)_snowman.getBlock()).getShapeProperty());
            Pair<Vec3i, Vec3i> _snowman2 = AbstractMinecartEntity.getAdjacentRailPositionsByShape(railShape);
            Vec3i _snowman3 = (Vec3i)_snowman2.getFirst();
            Vec3i _snowman4 = (Vec3i)_snowman2.getSecond();
            double _snowman5 = (double)n + 0.5 + (double)_snowman3.getX() * 0.5;
            double _snowman6 = (double)_snowman + 0.0625 + (double)_snowman3.getY() * 0.5;
            double _snowman7 = (double)_snowman + 0.5 + (double)_snowman3.getZ() * 0.5;
            double _snowman8 = (double)n + 0.5 + (double)_snowman4.getX() * 0.5;
            double _snowman9 = (double)_snowman + 0.0625 + (double)_snowman4.getY() * 0.5;
            double _snowman10 = (double)_snowman + 0.5 + (double)_snowman4.getZ() * 0.5;
            double _snowman11 = _snowman8 - _snowman5;
            double _snowman12 = (_snowman9 - _snowman6) * 2.0;
            double _snowman13 = _snowman10 - _snowman7;
            if (_snowman11 == 0.0) {
                _snowman14 = z - (double)_snowman;
            } else if (_snowman13 == 0.0) {
                _snowman14 = x - (double)n;
            } else {
                double _snowman15 = x - _snowman5;
                double _snowman16 = z - _snowman7;
                _snowman14 = (_snowman15 * _snowman11 + _snowman16 * _snowman13) * 2.0;
            }
            x = _snowman5 + _snowman11 * _snowman14;
            y = _snowman6 + _snowman12 * _snowman14;
            z = _snowman7 + _snowman13 * _snowman14;
            if (_snowman12 < 0.0) {
                y += 1.0;
            } else if (_snowman12 > 0.0) {
                y += 0.5;
            }
            return new Vec3d(x, y, z);
        }
        return null;
    }

    @Override
    public Box getVisibilityBoundingBox() {
        Box box = this.getBoundingBox();
        if (this.hasCustomBlock()) {
            return box.expand((double)Math.abs(this.getBlockOffset()) / 16.0);
        }
        return box;
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        if (tag.getBoolean("CustomDisplayTile")) {
            this.setCustomBlock(NbtHelper.toBlockState(tag.getCompound("DisplayState")));
            this.setCustomBlockOffset(tag.getInt("DisplayOffset"));
        }
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        if (this.hasCustomBlock()) {
            tag.putBoolean("CustomDisplayTile", true);
            tag.put("DisplayState", NbtHelper.fromBlockState(this.getContainedBlock()));
            tag.putInt("DisplayOffset", this.getBlockOffset());
        }
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (this.world.isClient) {
            return;
        }
        if (entity.noClip || this.noClip) {
            return;
        }
        if (this.hasPassenger(entity)) {
            return;
        }
        double d = entity.getX() - this.getX();
        _snowman = d * d + (_snowman = entity.getZ() - this.getZ()) * _snowman;
        if (_snowman >= (double)1.0E-4f) {
            _snowman = MathHelper.sqrt(_snowman);
            d /= _snowman;
            _snowman /= _snowman;
            _snowman = 1.0 / _snowman;
            if (_snowman > 1.0) {
                _snowman = 1.0;
            }
            d *= _snowman;
            _snowman *= _snowman;
            d *= (double)0.1f;
            _snowman *= (double)0.1f;
            d *= (double)(1.0f - this.pushSpeedReduction);
            _snowman *= (double)(1.0f - this.pushSpeedReduction);
            d *= 0.5;
            _snowman *= 0.5;
            if (entity instanceof AbstractMinecartEntity) {
                _snowman = entity.getX() - this.getX();
                Vec3d vec3d = new Vec3d(_snowman, 0.0, _snowman = entity.getZ() - this.getZ()).normalize();
                double _snowman2 = Math.abs(vec3d.dotProduct(_snowman = new Vec3d(MathHelper.cos(this.yaw * ((float)Math.PI / 180)), 0.0, MathHelper.sin(this.yaw * ((float)Math.PI / 180))).normalize()));
                if (_snowman2 < (double)0.8f) {
                    return;
                }
                _snowman = this.getVelocity();
                _snowman = entity.getVelocity();
                if (((AbstractMinecartEntity)entity).getMinecartType() == Type.FURNACE && this.getMinecartType() != Type.FURNACE) {
                    this.setVelocity(_snowman.multiply(0.2, 1.0, 0.2));
                    this.addVelocity(_snowman.x - d, 0.0, _snowman.z - _snowman);
                    entity.setVelocity(_snowman.multiply(0.95, 1.0, 0.95));
                } else if (((AbstractMinecartEntity)entity).getMinecartType() != Type.FURNACE && this.getMinecartType() == Type.FURNACE) {
                    entity.setVelocity(_snowman.multiply(0.2, 1.0, 0.2));
                    entity.addVelocity(_snowman.x + d, 0.0, _snowman.z + _snowman);
                    this.setVelocity(_snowman.multiply(0.95, 1.0, 0.95));
                } else {
                    double d2 = (_snowman.x + _snowman.x) / 2.0;
                    _snowman = (_snowman.z + _snowman.z) / 2.0;
                    this.setVelocity(_snowman.multiply(0.2, 1.0, 0.2));
                    this.addVelocity(d2 - d, 0.0, _snowman - _snowman);
                    entity.setVelocity(_snowman.multiply(0.2, 1.0, 0.2));
                    entity.addVelocity(d2 + d, 0.0, _snowman + _snowman);
                }
            } else {
                this.addVelocity(-d, 0.0, -_snowman);
                entity.addVelocity(d / 4.0, 0.0, _snowman / 4.0);
            }
        }
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.clientX = x;
        this.clientY = y;
        this.clientZ = z;
        this.clientYaw = yaw;
        this.clientPitch = pitch;
        this.clientInterpolationSteps = interpolationSteps + 2;
        this.setVelocity(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
        this.clientXVelocity = x;
        this.clientYVelocity = y;
        this.clientZVelocity = z;
        this.setVelocity(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
    }

    public void setDamageWobbleStrength(float f) {
        this.dataTracker.set(DAMAGE_WOBBLE_STRENGTH, Float.valueOf(f));
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

    public void setDamageWobbleSide(int wobbleSide) {
        this.dataTracker.set(DAMAGE_WOBBLE_SIDE, wobbleSide);
    }

    public int getDamageWobbleSide() {
        return this.dataTracker.get(DAMAGE_WOBBLE_SIDE);
    }

    public abstract Type getMinecartType();

    public BlockState getContainedBlock() {
        if (!this.hasCustomBlock()) {
            return this.getDefaultContainedBlock();
        }
        return Block.getStateFromRawId(this.getDataTracker().get(CUSTOM_BLOCK_ID));
    }

    public BlockState getDefaultContainedBlock() {
        return Blocks.AIR.getDefaultState();
    }

    public int getBlockOffset() {
        if (!this.hasCustomBlock()) {
            return this.getDefaultBlockOffset();
        }
        return this.getDataTracker().get(CUSTOM_BLOCK_OFFSET);
    }

    public int getDefaultBlockOffset() {
        return 6;
    }

    public void setCustomBlock(BlockState state) {
        this.getDataTracker().set(CUSTOM_BLOCK_ID, Block.getRawIdFromState(state));
        this.setCustomBlockPresent(true);
    }

    public void setCustomBlockOffset(int offset) {
        this.getDataTracker().set(CUSTOM_BLOCK_OFFSET, offset);
        this.setCustomBlockPresent(true);
    }

    public boolean hasCustomBlock() {
        return this.getDataTracker().get(CUSTOM_BLOCK_PRESENT);
    }

    public void setCustomBlockPresent(boolean present) {
        this.getDataTracker().set(CUSTOM_BLOCK_PRESENT, present);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    public static enum Type {
        RIDEABLE,
        CHEST,
        FURNACE,
        TNT,
        SPAWNER,
        HOPPER,
        COMMAND_BLOCK;

    }
}

