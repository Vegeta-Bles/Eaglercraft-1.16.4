/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.projectile;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FishingBobberEntity
extends ProjectileEntity {
    private final Random velocityRandom = new Random();
    private boolean caughtFish;
    private int outOfOpenWaterTicks;
    private static final TrackedData<Integer> HOOK_ENTITY_ID = DataTracker.registerData(FishingBobberEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> CAUGHT_FISH = DataTracker.registerData(FishingBobberEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int removalTimer;
    private int hookCountdown;
    private int waitCountdown;
    private int fishTravelCountdown;
    private float fishAngle;
    private boolean inOpenWater = true;
    private Entity hookedEntity;
    private State state = State.FLYING;
    private final int luckOfTheSeaLevel;
    private final int lureLevel;

    private FishingBobberEntity(World world, PlayerEntity owner, int lureLevel, int luckOfTheSeaLevel) {
        super((EntityType<? extends ProjectileEntity>)EntityType.FISHING_BOBBER, world);
        this.ignoreCameraFrustum = true;
        this.setOwner(owner);
        owner.fishHook = this;
        this.luckOfTheSeaLevel = Math.max(0, lureLevel);
        this.lureLevel = Math.max(0, luckOfTheSeaLevel);
    }

    public FishingBobberEntity(World world, PlayerEntity thrower, double x, double y, double z) {
        this(world, thrower, 0, 0);
        this.updatePosition(x, y, z);
        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();
    }

    public FishingBobberEntity(PlayerEntity thrower, World world, int lureLevel, int luckOfTheSeaLevel) {
        this(world, thrower, lureLevel, luckOfTheSeaLevel);
        float f = thrower.pitch;
        _snowman = thrower.yaw;
        _snowman = MathHelper.cos(-_snowman * ((float)Math.PI / 180) - (float)Math.PI);
        _snowman = MathHelper.sin(-_snowman * ((float)Math.PI / 180) - (float)Math.PI);
        _snowman = -MathHelper.cos(-f * ((float)Math.PI / 180));
        _snowman = MathHelper.sin(-f * ((float)Math.PI / 180));
        double _snowman2 = thrower.getX() - (double)_snowman * 0.3;
        double _snowman3 = thrower.getEyeY();
        double _snowman4 = thrower.getZ() - (double)_snowman * 0.3;
        this.refreshPositionAndAngles(_snowman2, _snowman3, _snowman4, _snowman, f);
        Vec3d _snowman5 = new Vec3d(-_snowman, MathHelper.clamp(-(_snowman / _snowman), -5.0f, 5.0f), -_snowman);
        double _snowman6 = _snowman5.length();
        _snowman5 = _snowman5.multiply(0.6 / _snowman6 + 0.5 + this.random.nextGaussian() * 0.0045, 0.6 / _snowman6 + 0.5 + this.random.nextGaussian() * 0.0045, 0.6 / _snowman6 + 0.5 + this.random.nextGaussian() * 0.0045);
        this.setVelocity(_snowman5);
        this.yaw = (float)(MathHelper.atan2(_snowman5.x, _snowman5.z) * 57.2957763671875);
        this.pitch = (float)(MathHelper.atan2(_snowman5.y, MathHelper.sqrt(FishingBobberEntity.squaredHorizontalLength(_snowman5))) * 57.2957763671875);
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(HOOK_ENTITY_ID, 0);
        this.getDataTracker().startTracking(CAUGHT_FISH, false);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (HOOK_ENTITY_ID.equals(data)) {
            int n = this.getDataTracker().get(HOOK_ENTITY_ID);
            Entity entity = this.hookedEntity = n > 0 ? this.world.getEntityById(n - 1) : null;
        }
        if (CAUGHT_FISH.equals(data)) {
            this.caughtFish = this.getDataTracker().get(CAUGHT_FISH);
            if (this.caughtFish) {
                this.setVelocity(this.getVelocity().x, -0.4f * MathHelper.nextFloat(this.velocityRandom, 0.6f, 1.0f), this.getVelocity().z);
            }
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = 64.0;
        return distance < 4096.0;
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
    }

    @Override
    public void tick() {
        this.velocityRandom.setSeed(this.getUuid().getLeastSignificantBits() ^ this.world.getTime());
        super.tick();
        PlayerEntity playerEntity = this.getPlayerOwner();
        if (playerEntity == null) {
            this.remove();
            return;
        }
        if (!this.world.isClient && this.removeIfInvalid(playerEntity)) {
            return;
        }
        if (this.onGround) {
            ++this.removalTimer;
            if (this.removalTimer >= 1200) {
                this.remove();
                return;
            }
        } else {
            this.removalTimer = 0;
        }
        float _snowman2 = 0.0f;
        BlockPos _snowman3 = this.getBlockPos();
        FluidState _snowman4 = this.world.getFluidState(_snowman3);
        if (_snowman4.isIn(FluidTags.WATER)) {
            _snowman2 = _snowman4.getHeight(this.world, _snowman3);
        }
        boolean bl = _snowman = _snowman2 > 0.0f;
        if (this.state == State.FLYING) {
            if (this.hookedEntity != null) {
                this.setVelocity(Vec3d.ZERO);
                this.state = State.HOOKED_IN_ENTITY;
                return;
            }
            if (_snowman) {
                this.setVelocity(this.getVelocity().multiply(0.3, 0.2, 0.3));
                this.state = State.BOBBING;
                return;
            }
            this.checkForCollision();
        } else {
            if (this.state == State.HOOKED_IN_ENTITY) {
                if (this.hookedEntity != null) {
                    if (this.hookedEntity.removed) {
                        this.hookedEntity = null;
                        this.state = State.FLYING;
                    } else {
                        this.updatePosition(this.hookedEntity.getX(), this.hookedEntity.getBodyY(0.8), this.hookedEntity.getZ());
                    }
                }
                return;
            }
            if (this.state == State.BOBBING) {
                Vec3d vec3d = this.getVelocity();
                double _snowman5 = this.getY() + vec3d.y - (double)_snowman3.getY() - (double)_snowman2;
                if (Math.abs(_snowman5) < 0.01) {
                    _snowman5 += Math.signum(_snowman5) * 0.1;
                }
                this.setVelocity(vec3d.x * 0.9, vec3d.y - _snowman5 * (double)this.random.nextFloat() * 0.2, vec3d.z * 0.9);
                this.inOpenWater = this.hookCountdown > 0 || this.fishTravelCountdown > 0 ? this.inOpenWater && this.outOfOpenWaterTicks < 10 && this.isOpenOrWaterAround(_snowman3) : true;
                if (_snowman) {
                    this.outOfOpenWaterTicks = Math.max(0, this.outOfOpenWaterTicks - 1);
                    if (this.caughtFish) {
                        this.setVelocity(this.getVelocity().add(0.0, -0.1 * (double)this.velocityRandom.nextFloat() * (double)this.velocityRandom.nextFloat(), 0.0));
                    }
                    if (!this.world.isClient) {
                        this.tickFishingLogic(_snowman3);
                    }
                } else {
                    this.outOfOpenWaterTicks = Math.min(10, this.outOfOpenWaterTicks + 1);
                }
            }
        }
        if (!_snowman4.isIn(FluidTags.WATER)) {
            this.setVelocity(this.getVelocity().add(0.0, -0.03, 0.0));
        }
        this.move(MovementType.SELF, this.getVelocity());
        this.method_26962();
        if (this.state == State.FLYING && (this.onGround || this.horizontalCollision)) {
            this.setVelocity(Vec3d.ZERO);
        }
        double d = 0.92;
        this.setVelocity(this.getVelocity().multiply(0.92));
        this.refreshPosition();
    }

    private boolean removeIfInvalid(PlayerEntity playerEntity) {
        ItemStack itemStack = playerEntity.getMainHandStack();
        _snowman = playerEntity.getOffHandStack();
        boolean _snowman2 = itemStack.getItem() == Items.FISHING_ROD;
        boolean bl = _snowman = _snowman.getItem() == Items.FISHING_ROD;
        if (playerEntity.removed || !playerEntity.isAlive() || !_snowman2 && !_snowman || this.squaredDistanceTo(playerEntity) > 1024.0) {
            this.remove();
            return true;
        }
        return false;
    }

    private void checkForCollision() {
        HitResult hitResult = ProjectileUtil.getCollision(this, this::method_26958);
        this.onCollision(hitResult);
    }

    @Override
    protected boolean method_26958(Entity entity) {
        return super.method_26958(entity) || entity.isAlive() && entity instanceof ItemEntity;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.world.isClient) {
            this.hookedEntity = entityHitResult.getEntity();
            this.updateHookedEntityId();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.setVelocity(this.getVelocity().normalize().multiply(blockHitResult.squaredDistanceTo(this)));
    }

    private void updateHookedEntityId() {
        this.getDataTracker().set(HOOK_ENTITY_ID, this.hookedEntity.getEntityId() + 1);
    }

    private void tickFishingLogic(BlockPos pos) {
        ServerWorld serverWorld = (ServerWorld)this.world;
        int _snowman2 = 1;
        BlockPos _snowman3 = pos.up();
        if (this.random.nextFloat() < 0.25f && this.world.hasRain(_snowman3)) {
            ++_snowman2;
        }
        if (this.random.nextFloat() < 0.5f && !this.world.isSkyVisible(_snowman3)) {
            --_snowman2;
        }
        if (this.hookCountdown > 0) {
            --this.hookCountdown;
            if (this.hookCountdown <= 0) {
                this.waitCountdown = 0;
                this.fishTravelCountdown = 0;
                this.getDataTracker().set(CAUGHT_FISH, false);
            }
        } else if (this.fishTravelCountdown > 0) {
            this.fishTravelCountdown -= _snowman2;
            if (this.fishTravelCountdown > 0) {
                this.fishAngle = (float)((double)this.fishAngle + this.random.nextGaussian() * 4.0);
                float f = this.fishAngle * ((float)Math.PI / 180);
                _snowman = MathHelper.sin(f);
                _snowman = MathHelper.cos(f);
                double _snowman4 = this.getX() + (double)(_snowman * (float)this.fishTravelCountdown * 0.1f);
                BlockState _snowman5 = serverWorld.getBlockState(new BlockPos(_snowman4, (_snowman = (double)((float)MathHelper.floor(this.getY()) + 1.0f)) - 1.0, _snowman = this.getZ() + (double)(_snowman * (float)this.fishTravelCountdown * 0.1f)));
                if (_snowman5.isOf(Blocks.WATER)) {
                    if (this.random.nextFloat() < 0.15f) {
                        serverWorld.spawnParticles(ParticleTypes.BUBBLE, _snowman4, _snowman - (double)0.1f, _snowman, 1, _snowman, 0.1, _snowman, 0.0);
                    }
                    _snowman = _snowman * 0.04f;
                    _snowman = _snowman * 0.04f;
                    serverWorld.spawnParticles(ParticleTypes.FISHING, _snowman4, _snowman, _snowman, 0, _snowman, 0.01, -_snowman, 1.0);
                    serverWorld.spawnParticles(ParticleTypes.FISHING, _snowman4, _snowman, _snowman, 0, -_snowman, 0.01, _snowman, 1.0);
                }
            } else {
                this.playSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, 0.25f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.4f);
                double _snowman6 = this.getY() + 0.5;
                serverWorld.spawnParticles(ParticleTypes.BUBBLE, this.getX(), _snowman6, this.getZ(), (int)(1.0f + this.getWidth() * 20.0f), this.getWidth(), 0.0, this.getWidth(), 0.2f);
                serverWorld.spawnParticles(ParticleTypes.FISHING, this.getX(), _snowman6, this.getZ(), (int)(1.0f + this.getWidth() * 20.0f), this.getWidth(), 0.0, this.getWidth(), 0.2f);
                this.hookCountdown = MathHelper.nextInt(this.random, 20, 40);
                this.getDataTracker().set(CAUGHT_FISH, true);
            }
        } else if (this.waitCountdown > 0) {
            this.waitCountdown -= _snowman2;
            _snowman = 0.15f;
            if (this.waitCountdown < 20) {
                _snowman = (float)((double)_snowman + (double)(20 - this.waitCountdown) * 0.05);
            } else if (this.waitCountdown < 40) {
                _snowman = (float)((double)_snowman + (double)(40 - this.waitCountdown) * 0.02);
            } else if (this.waitCountdown < 60) {
                _snowman = (float)((double)_snowman + (double)(60 - this.waitCountdown) * 0.01);
            }
            if (this.random.nextFloat() < _snowman) {
                _snowman = MathHelper.nextFloat(this.random, 0.0f, 360.0f) * ((float)Math.PI / 180);
                _snowman = MathHelper.nextFloat(this.random, 25.0f, 60.0f);
                double _snowman7 = this.getX() + (double)(MathHelper.sin(_snowman) * _snowman * 0.1f);
                BlockState _snowman8 = serverWorld.getBlockState(new BlockPos(_snowman7, (_snowman = (double)((float)MathHelper.floor(this.getY()) + 1.0f)) - 1.0, _snowman = this.getZ() + (double)(MathHelper.cos(_snowman) * _snowman * 0.1f)));
                if (_snowman8.isOf(Blocks.WATER)) {
                    serverWorld.spawnParticles(ParticleTypes.SPLASH, _snowman7, _snowman, _snowman, 2 + this.random.nextInt(2), 0.1f, 0.0, 0.1f, 0.0);
                }
            }
            if (this.waitCountdown <= 0) {
                this.fishAngle = MathHelper.nextFloat(this.random, 0.0f, 360.0f);
                this.fishTravelCountdown = MathHelper.nextInt(this.random, 20, 80);
            }
        } else {
            this.waitCountdown = MathHelper.nextInt(this.random, 100, 600);
            this.waitCountdown -= this.lureLevel * 20 * 5;
        }
    }

    private boolean isOpenOrWaterAround(BlockPos pos) {
        PositionType positionType = PositionType.INVALID;
        for (int i = -1; i <= 2; ++i) {
            PositionType positionType2 = this.getPositionType(pos.add(-2, i, -2), pos.add(2, i, 2));
            switch (positionType2) {
                case INVALID: {
                    return false;
                }
                case ABOVE_WATER: {
                    if (positionType != PositionType.INVALID) break;
                    return false;
                }
                case INSIDE_WATER: {
                    if (positionType != PositionType.ABOVE_WATER) break;
                    return false;
                }
            }
            positionType = positionType2;
        }
        return true;
    }

    private PositionType getPositionType(BlockPos start, BlockPos end) {
        return BlockPos.stream(start, end).map(this::getPositionType).reduce((positionType, positionType2) -> positionType == positionType2 ? positionType : PositionType.INVALID).orElse(PositionType.INVALID);
    }

    private PositionType getPositionType(BlockPos pos) {
        BlockState blockState = this.world.getBlockState(pos);
        if (blockState.isAir() || blockState.isOf(Blocks.LILY_PAD)) {
            return PositionType.ABOVE_WATER;
        }
        FluidState _snowman2 = blockState.getFluidState();
        if (_snowman2.isIn(FluidTags.WATER) && _snowman2.isStill() && blockState.getCollisionShape(this.world, pos).isEmpty()) {
            return PositionType.INSIDE_WATER;
        }
        return PositionType.INVALID;
    }

    public boolean isInOpenWater() {
        return this.inOpenWater;
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
    }

    public int use(ItemStack usedItem) {
        PlayerEntity playerEntity = this.getPlayerOwner();
        if (this.world.isClient || playerEntity == null) {
            return 0;
        }
        int _snowman2 = 0;
        if (this.hookedEntity != null) {
            this.pullHookedEntity();
            Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)playerEntity, usedItem, this, Collections.emptyList());
            this.world.sendEntityStatus(this, (byte)31);
            _snowman2 = this.hookedEntity instanceof ItemEntity ? 3 : 5;
        } else if (this.hookCountdown > 0) {
            LootContext.Builder builder = new LootContext.Builder((ServerWorld)this.world).parameter(LootContextParameters.ORIGIN, this.getPos()).parameter(LootContextParameters.TOOL, usedItem).parameter(LootContextParameters.THIS_ENTITY, this).random(this.random).luck((float)this.luckOfTheSeaLevel + playerEntity.getLuck());
            LootTable _snowman3 = this.world.getServer().getLootManager().getTable(LootTables.FISHING_GAMEPLAY);
            List<ItemStack> _snowman4 = _snowman3.generateLoot(builder.build(LootContextTypes.FISHING));
            Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)playerEntity, usedItem, this, _snowman4);
            for (ItemStack itemStack : _snowman4) {
                ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), itemStack);
                double _snowman5 = playerEntity.getX() - this.getX();
                double _snowman6 = playerEntity.getY() - this.getY();
                double _snowman7 = playerEntity.getZ() - this.getZ();
                double _snowman8 = 0.1;
                itemEntity.setVelocity(_snowman5 * 0.1, _snowman6 * 0.1 + Math.sqrt(Math.sqrt(_snowman5 * _snowman5 + _snowman6 * _snowman6 + _snowman7 * _snowman7)) * 0.08, _snowman7 * 0.1);
                this.world.spawnEntity(itemEntity);
                playerEntity.world.spawnEntity(new ExperienceOrbEntity(playerEntity.world, playerEntity.getX(), playerEntity.getY() + 0.5, playerEntity.getZ() + 0.5, this.random.nextInt(6) + 1));
                if (!itemStack.getItem().isIn(ItemTags.FISHES)) continue;
                playerEntity.increaseStat(Stats.FISH_CAUGHT, 1);
            }
            _snowman2 = 1;
        }
        if (this.onGround) {
            _snowman2 = 2;
        }
        this.remove();
        return _snowman2;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 31 && this.world.isClient && this.hookedEntity instanceof PlayerEntity && ((PlayerEntity)this.hookedEntity).isMainPlayer()) {
            this.pullHookedEntity();
        }
        super.handleStatus(status);
    }

    protected void pullHookedEntity() {
        Entity entity = this.getOwner();
        if (entity == null) {
            return;
        }
        Vec3d _snowman2 = new Vec3d(entity.getX() - this.getX(), entity.getY() - this.getY(), entity.getZ() - this.getZ()).multiply(0.1);
        this.hookedEntity.setVelocity(this.hookedEntity.getVelocity().add(_snowman2));
    }

    @Override
    protected boolean canClimb() {
        return false;
    }

    @Override
    public void remove() {
        super.remove();
        PlayerEntity playerEntity = this.getPlayerOwner();
        if (playerEntity != null) {
            playerEntity.fishHook = null;
        }
    }

    @Nullable
    public PlayerEntity getPlayerOwner() {
        Entity entity = this.getOwner();
        return entity instanceof PlayerEntity ? (PlayerEntity)entity : null;
    }

    @Nullable
    public Entity getHookedEntity() {
        return this.hookedEntity;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        Entity entity = this.getOwner();
        return new EntitySpawnS2CPacket(this, entity == null ? this.getEntityId() : entity.getEntityId());
    }

    static enum PositionType {
        ABOVE_WATER,
        INSIDE_WATER,
        INVALID;

    }

    static enum State {
        FLYING,
        HOOKED_IN_ENTITY,
        BOBBING;

    }
}

