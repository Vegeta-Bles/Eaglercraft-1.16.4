/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.mob;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.IntRange;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class EndermanEntity
extends HostileEntity
implements Angerable {
    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final EntityAttributeModifier ATTACKING_SPEED_BOOST = new EntityAttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", (double)0.15f, EntityAttributeModifier.Operation.ADDITION);
    private static final TrackedData<Optional<BlockState>> CARRIED_BLOCK = DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE);
    private static final TrackedData<Boolean> ANGRY = DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> PROVOKED = DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final Predicate<LivingEntity> PLAYER_ENDERMITE_PREDICATE = livingEntity -> livingEntity instanceof EndermiteEntity && ((EndermiteEntity)livingEntity).isPlayerSpawned();
    private int lastAngrySoundAge = Integer.MIN_VALUE;
    private int ageWhenTargetSet;
    private static final IntRange ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
    private int angerTime;
    private UUID targetUuid;

    public EndermanEntity(EntityType<? extends EndermanEntity> entityType, World world) {
        super((EntityType<? extends HostileEntity>)entityType, world);
        this.stepHeight = 1.0f;
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new ChasePlayerGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(7, new WanderAroundFarGoal((PathAwareEntity)this, 1.0, 0.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(10, new PlaceBlockGoal(this));
        this.goalSelector.add(11, new PickUpBlockGoal(this));
        this.targetSelector.add(1, new TeleportTowardsPlayerGoal(this, this::shouldAngerAt));
        this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(3, new FollowTargetGoal<EndermiteEntity>(this, EndermiteEntity.class, 10, true, false, PLAYER_ENDERMITE_PREDICATE));
        this.targetSelector.add(4, new UniversalAngerGoal<EndermanEntity>(this, false));
    }

    public static DefaultAttributeContainer.Builder createEndermanAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (target == null) {
            this.ageWhenTargetSet = 0;
            this.dataTracker.set(ANGRY, false);
            this.dataTracker.set(PROVOKED, false);
            entityAttributeInstance.removeModifier(ATTACKING_SPEED_BOOST);
        } else {
            this.ageWhenTargetSet = this.age;
            this.dataTracker.set(ANGRY, true);
            if (!entityAttributeInstance.hasModifier(ATTACKING_SPEED_BOOST)) {
                entityAttributeInstance.addTemporaryModifier(ATTACKING_SPEED_BOOST);
            }
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CARRIED_BLOCK, Optional.empty());
        this.dataTracker.startTracking(ANGRY, false);
        this.dataTracker.startTracking(PROVOKED, false);
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.choose(this.random));
    }

    @Override
    public void setAngerTime(int ticks) {
        this.angerTime = ticks;
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        this.targetUuid = uuid;
    }

    @Override
    public UUID getAngryAt() {
        return this.targetUuid;
    }

    public void playAngrySound() {
        if (this.age >= this.lastAngrySoundAge + 400) {
            this.lastAngrySoundAge = this.age;
            if (!this.isSilent()) {
                this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ENDERMAN_STARE, this.getSoundCategory(), 2.5f, 1.0f, false);
            }
        }
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (ANGRY.equals(data) && this.isProvoked() && this.world.isClient) {
            this.playAngrySound();
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        BlockState blockState = this.getCarriedBlock();
        if (blockState != null) {
            tag.put("carriedBlockState", NbtHelper.fromBlockState(blockState));
        }
        this.angerToTag(tag);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        BlockState blockState = null;
        if (tag.contains("carriedBlockState", 10) && (blockState = NbtHelper.toBlockState(tag.getCompound("carriedBlockState"))).isAir()) {
            blockState = null;
        }
        this.setCarriedBlock(blockState);
        this.angerFromTag((ServerWorld)this.world, tag);
    }

    private boolean isPlayerStaring(PlayerEntity player) {
        ItemStack itemStack = player.inventory.armor.get(3);
        if (itemStack.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
            return false;
        }
        Vec3d _snowman2 = player.getRotationVec(1.0f).normalize();
        Vec3d _snowman3 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double _snowman4 = _snowman3.length();
        double _snowman5 = _snowman2.dotProduct(_snowman3 = _snowman3.normalize());
        if (_snowman5 > 1.0 - 0.025 / _snowman4) {
            return player.canSee(this);
        }
        return false;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 2.55f;
    }

    @Override
    public void tickMovement() {
        if (this.world.isClient) {
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.PORTAL, this.getParticleX(0.5), this.getRandomBodyY() - 0.25, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
        this.jumping = false;
        if (!this.world.isClient) {
            this.tickAngerLogic((ServerWorld)this.world, true);
        }
        super.tickMovement();
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    protected void mobTick() {
        float f;
        if (this.world.isDay() && this.age >= this.ageWhenTargetSet + 600 && (f = this.getBrightnessAtEyes()) > 0.5f && this.world.isSkyVisible(this.getBlockPos()) && this.random.nextFloat() * 30.0f < (f - 0.4f) * 2.0f) {
            this.setTarget(null);
            this.teleportRandomly();
        }
        super.mobTick();
    }

    protected boolean teleportRandomly() {
        if (this.world.isClient() || !this.isAlive()) {
            return false;
        }
        double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
        _snowman = this.getY() + (double)(this.random.nextInt(64) - 32);
        _snowman = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(d, _snowman, _snowman);
    }

    private boolean teleportTo(Entity entity) {
        Vec3d vec3d = new Vec3d(this.getX() - entity.getX(), this.getBodyY(0.5) - entity.getEyeY(), this.getZ() - entity.getZ());
        vec3d = vec3d.normalize();
        double _snowman2 = 16.0;
        double _snowman3 = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - vec3d.x * 16.0;
        double _snowman4 = this.getY() + (double)(this.random.nextInt(16) - 8) - vec3d.y * 16.0;
        double _snowman5 = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - vec3d.z * 16.0;
        return this.teleportTo(_snowman3, _snowman4, _snowman5);
    }

    private boolean teleportTo(double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        while (mutable.getY() > 0 && !this.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState _snowman2 = this.world.getBlockState(mutable);
        boolean _snowman3 = _snowman2.getMaterial().blocksMovement();
        boolean _snowman4 = _snowman2.getFluidState().isIn(FluidTags.WATER);
        if (!_snowman3 || _snowman4) {
            return false;
        }
        boolean _snowman5 = this.teleport(x, y, z, true);
        if (_snowman5 && !this.isSilent()) {
            this.world.playSound(null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0f, 1.0f);
            this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
        }
        return _snowman5;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isAngry() ? SoundEvents.ENTITY_ENDERMAN_SCREAM : SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMAN_DEATH;
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        BlockState blockState = this.getCarriedBlock();
        if (blockState != null) {
            this.dropItem(blockState.getBlock());
        }
    }

    public void setCarriedBlock(@Nullable BlockState state) {
        this.dataTracker.set(CARRIED_BLOCK, Optional.ofNullable(state));
    }

    @Nullable
    public BlockState getCarriedBlock() {
        return this.dataTracker.get(CARRIED_BLOCK).orElse(null);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (source instanceof ProjectileDamageSource) {
            for (int i = 0; i < 64; ++i) {
                if (!this.teleportRandomly()) continue;
                return true;
            }
            return false;
        }
        boolean bl = super.damage(source, amount);
        if (!this.world.isClient() && !(source.getAttacker() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
            this.teleportRandomly();
        }
        return bl;
    }

    public boolean isAngry() {
        return this.dataTracker.get(ANGRY);
    }

    public boolean isProvoked() {
        return this.dataTracker.get(PROVOKED);
    }

    public void setProvoked() {
        this.dataTracker.set(PROVOKED, true);
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.getCarriedBlock() != null;
    }

    static class PickUpBlockGoal
    extends Goal {
        private final EndermanEntity enderman;

        public PickUpBlockGoal(EndermanEntity enderman) {
            this.enderman = enderman;
        }

        @Override
        public boolean canStart() {
            if (this.enderman.getCarriedBlock() != null) {
                return false;
            }
            if (!this.enderman.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return false;
            }
            return this.enderman.getRandom().nextInt(20) == 0;
        }

        @Override
        public void tick() {
            Random random = this.enderman.getRandom();
            World _snowman2 = this.enderman.world;
            int _snowman3 = MathHelper.floor(this.enderman.getX() - 2.0 + random.nextDouble() * 4.0);
            int _snowman4 = MathHelper.floor(this.enderman.getY() + random.nextDouble() * 3.0);
            int _snowman5 = MathHelper.floor(this.enderman.getZ() - 2.0 + random.nextDouble() * 4.0);
            BlockPos _snowman6 = new BlockPos(_snowman3, _snowman4, _snowman5);
            BlockState _snowman7 = _snowman2.getBlockState(_snowman6);
            Block _snowman8 = _snowman7.getBlock();
            Vec3d _snowman9 = new Vec3d((double)MathHelper.floor(this.enderman.getX()) + 0.5, (double)_snowman4 + 0.5, (double)MathHelper.floor(this.enderman.getZ()) + 0.5);
            Vec3d _snowman10 = new Vec3d((double)_snowman3 + 0.5, (double)_snowman4 + 0.5, (double)_snowman5 + 0.5);
            BlockHitResult _snowman11 = _snowman2.raycast(new RaycastContext(_snowman9, _snowman10, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, this.enderman));
            boolean _snowman12 = _snowman11.getBlockPos().equals(_snowman6);
            if (_snowman8.isIn(BlockTags.ENDERMAN_HOLDABLE) && _snowman12) {
                _snowman2.removeBlock(_snowman6, false);
                this.enderman.setCarriedBlock(_snowman7.getBlock().getDefaultState());
            }
        }
    }

    static class PlaceBlockGoal
    extends Goal {
        private final EndermanEntity enderman;

        public PlaceBlockGoal(EndermanEntity enderman) {
            this.enderman = enderman;
        }

        @Override
        public boolean canStart() {
            if (this.enderman.getCarriedBlock() == null) {
                return false;
            }
            if (!this.enderman.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return false;
            }
            return this.enderman.getRandom().nextInt(2000) == 0;
        }

        @Override
        public void tick() {
            Random random = this.enderman.getRandom();
            World _snowman2 = this.enderman.world;
            int _snowman3 = MathHelper.floor(this.enderman.getX() - 1.0 + random.nextDouble() * 2.0);
            int _snowman4 = MathHelper.floor(this.enderman.getY() + random.nextDouble() * 2.0);
            int _snowman5 = MathHelper.floor(this.enderman.getZ() - 1.0 + random.nextDouble() * 2.0);
            BlockPos _snowman6 = new BlockPos(_snowman3, _snowman4, _snowman5);
            BlockState _snowman7 = _snowman2.getBlockState(_snowman6);
            BlockPos _snowman8 = _snowman6.down();
            BlockState _snowman9 = _snowman2.getBlockState(_snowman8);
            BlockState _snowman10 = this.enderman.getCarriedBlock();
            if (_snowman10 == null) {
                return;
            }
            if (this.canPlaceOn(_snowman2, _snowman6, _snowman10 = Block.postProcessState(_snowman10, this.enderman.world, _snowman6), _snowman7, _snowman9, _snowman8)) {
                _snowman2.setBlockState(_snowman6, _snowman10, 3);
                this.enderman.setCarriedBlock(null);
            }
        }

        private boolean canPlaceOn(World world, BlockPos posAbove, BlockState carriedState, BlockState stateAbove, BlockState state, BlockPos pos) {
            return stateAbove.isAir() && !state.isAir() && !state.isOf(Blocks.BEDROCK) && state.isFullCube(world, pos) && carriedState.canPlaceAt(world, posAbove) && world.getOtherEntities(this.enderman, Box.method_29968(Vec3d.of(posAbove))).isEmpty();
        }
    }

    static class ChasePlayerGoal
    extends Goal {
        private final EndermanEntity enderman;
        private LivingEntity target;

        public ChasePlayerGoal(EndermanEntity enderman) {
            this.enderman = enderman;
            this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            this.target = this.enderman.getTarget();
            if (!(this.target instanceof PlayerEntity)) {
                return false;
            }
            double d = this.target.squaredDistanceTo(this.enderman);
            if (d > 256.0) {
                return false;
            }
            return this.enderman.isPlayerStaring((PlayerEntity)this.target);
        }

        @Override
        public void start() {
            this.enderman.getNavigation().stop();
        }

        @Override
        public void tick() {
            this.enderman.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }

    static class TeleportTowardsPlayerGoal
    extends FollowTargetGoal<PlayerEntity> {
        private final EndermanEntity enderman;
        private PlayerEntity targetPlayer;
        private int lookAtPlayerWarmup;
        private int ticksSinceUnseenTeleport;
        private final TargetPredicate staringPlayerPredicate;
        private final TargetPredicate validTargetPredicate = new TargetPredicate().includeHidden();

        public TeleportTowardsPlayerGoal(EndermanEntity enderman, @Nullable Predicate<LivingEntity> predicate) {
            super(enderman, PlayerEntity.class, 10, false, false, predicate);
            this.enderman = enderman;
            this.staringPlayerPredicate = new TargetPredicate().setBaseMaxDistance(this.getFollowRange()).setPredicate(playerEntity -> enderman.isPlayerStaring((PlayerEntity)playerEntity));
        }

        @Override
        public boolean canStart() {
            this.targetPlayer = this.enderman.world.getClosestPlayer(this.staringPlayerPredicate, this.enderman);
            return this.targetPlayer != null;
        }

        @Override
        public void start() {
            this.lookAtPlayerWarmup = 5;
            this.ticksSinceUnseenTeleport = 0;
            this.enderman.setProvoked();
        }

        @Override
        public void stop() {
            this.targetPlayer = null;
            super.stop();
        }

        @Override
        public boolean shouldContinue() {
            if (this.targetPlayer != null) {
                if (!this.enderman.isPlayerStaring(this.targetPlayer)) {
                    return false;
                }
                this.enderman.lookAtEntity(this.targetPlayer, 10.0f, 10.0f);
                return true;
            }
            if (this.targetEntity != null && this.validTargetPredicate.test(this.enderman, this.targetEntity)) {
                return true;
            }
            return super.shouldContinue();
        }

        @Override
        public void tick() {
            if (this.enderman.getTarget() == null) {
                super.setTargetEntity(null);
            }
            if (this.targetPlayer != null) {
                if (--this.lookAtPlayerWarmup <= 0) {
                    this.targetEntity = this.targetPlayer;
                    this.targetPlayer = null;
                    super.start();
                }
            } else {
                if (this.targetEntity != null && !this.enderman.hasVehicle()) {
                    if (this.enderman.isPlayerStaring((PlayerEntity)this.targetEntity)) {
                        if (this.targetEntity.squaredDistanceTo(this.enderman) < 16.0) {
                            this.enderman.teleportRandomly();
                        }
                        this.ticksSinceUnseenTeleport = 0;
                    } else if (this.targetEntity.squaredDistanceTo(this.enderman) > 256.0 && this.ticksSinceUnseenTeleport++ >= 30 && this.enderman.teleportTo(this.targetEntity)) {
                        this.ticksSinceUnseenTeleport = 0;
                    }
                }
                super.tick();
            }
        }
    }
}

