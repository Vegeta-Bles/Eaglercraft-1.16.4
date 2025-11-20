/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.passive;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.DolphinLookControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.BreatheAirGoal;
import net.minecraft.entity.ai.goal.ChaseBoatGoal;
import net.minecraft.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class DolphinEntity
extends WaterCreatureEntity {
    private static final TrackedData<BlockPos> TREASURE_POS = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private static final TrackedData<Boolean> HAS_FISH = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> MOISTNESS = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TargetPredicate CLOSE_PLAYER_PREDICATE = new TargetPredicate().setBaseMaxDistance(10.0).includeTeammates().includeInvulnerable().includeHidden();
    public static final Predicate<ItemEntity> CAN_TAKE = itemEntity -> !itemEntity.cannotPickup() && itemEntity.isAlive() && itemEntity.isTouchingWater();

    public DolphinEntity(EntityType<? extends DolphinEntity> entityType, World world) {
        super((EntityType<? extends WaterCreatureEntity>)entityType, world);
        this.moveControl = new DolphinMoveControl(this);
        this.lookControl = new DolphinLookControl(this, 10);
        this.setCanPickUpLoot(true);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        this.setAir(this.getMaxAir());
        this.pitch = 0.0f;
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    public boolean canBreatheInWater() {
        return false;
    }

    @Override
    protected void tickWaterBreathingAir(int air) {
    }

    public void setTreasurePos(BlockPos treasurePos) {
        this.dataTracker.set(TREASURE_POS, treasurePos);
    }

    public BlockPos getTreasurePos() {
        return this.dataTracker.get(TREASURE_POS);
    }

    public boolean hasFish() {
        return this.dataTracker.get(HAS_FISH);
    }

    public void setHasFish(boolean hasFish) {
        this.dataTracker.set(HAS_FISH, hasFish);
    }

    public int getMoistness() {
        return this.dataTracker.get(MOISTNESS);
    }

    public void setMoistness(int moistness) {
        this.dataTracker.set(MOISTNESS, moistness);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TREASURE_POS, BlockPos.ORIGIN);
        this.dataTracker.startTracking(HAS_FISH, false);
        this.dataTracker.startTracking(MOISTNESS, 2400);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("TreasurePosX", this.getTreasurePos().getX());
        tag.putInt("TreasurePosY", this.getTreasurePos().getY());
        tag.putInt("TreasurePosZ", this.getTreasurePos().getZ());
        tag.putBoolean("GotFish", this.hasFish());
        tag.putInt("Moistness", this.getMoistness());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        int n = tag.getInt("TreasurePosX");
        _snowman = tag.getInt("TreasurePosY");
        _snowman = tag.getInt("TreasurePosZ");
        this.setTreasurePos(new BlockPos(n, _snowman, _snowman));
        super.readCustomDataFromTag(tag);
        this.setHasFish(tag.getBoolean("GotFish"));
        this.setMoistness(tag.getInt("Moistness"));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new BreatheAirGoal(this));
        this.goalSelector.add(0, new MoveIntoWaterGoal(this));
        this.goalSelector.add(1, new LeadToNearbyTreasureGoal(this));
        this.goalSelector.add(2, new SwimWithPlayerGoal(this, 4.0));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(5, new DolphinJumpGoal(this, 10));
        this.goalSelector.add(6, new MeleeAttackGoal(this, 1.2f, true));
        this.goalSelector.add(8, new PlayWithItemsGoal());
        this.goalSelector.add(8, new ChaseBoatGoal(this));
        this.goalSelector.add(9, new FleeEntityGoal<GuardianEntity>(this, GuardianEntity.class, 8.0f, 1.0, 1.0));
        this.targetSelector.add(1, new RevengeGoal(this, GuardianEntity.class).setGroupRevenge(new Class[0]));
    }

    public static DefaultAttributeContainer.Builder createDolphinAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.2f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(DamageSource.mob(this), (int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
        if (bl) {
            this.dealDamage(this, target);
            this.playSound(SoundEvents.ENTITY_DOLPHIN_ATTACK, 1.0f, 1.0f);
        }
        return bl;
    }

    @Override
    public int getMaxAir() {
        return 4800;
    }

    @Override
    protected int getNextAirOnLand(int air) {
        return this.getMaxAir();
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.3f;
    }

    @Override
    public int getLookPitchSpeed() {
        return 1;
    }

    @Override
    public int getBodyYawSpeed() {
        return 1;
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return true;
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(stack);
        if (!this.getEquippedStack(equipmentSlot).isEmpty()) {
            return false;
        }
        return equipmentSlot == EquipmentSlot.MAINHAND && super.canEquip(stack);
    }

    @Override
    protected void loot(ItemEntity item) {
        ItemStack itemStack;
        if (this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() && this.canPickupItem(itemStack = item.getStack())) {
            this.method_29499(item);
            this.equipStack(EquipmentSlot.MAINHAND, itemStack);
            this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0f;
            this.sendPickup(item, itemStack.getCount());
            item.remove();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAiDisabled()) {
            this.setAir(this.getMaxAir());
            return;
        }
        if (this.isWet()) {
            this.setMoistness(2400);
        } else {
            this.setMoistness(this.getMoistness() - 1);
            if (this.getMoistness() <= 0) {
                this.damage(DamageSource.DRYOUT, 1.0f);
            }
            if (this.onGround) {
                this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0f - 1.0f) * 0.2f, 0.5, (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f));
                this.yaw = this.random.nextFloat() * 360.0f;
                this.onGround = false;
                this.velocityDirty = true;
            }
        }
        if (this.world.isClient && this.isTouchingWater() && this.getVelocity().lengthSquared() > 0.03) {
            Vec3d vec3d = this.getRotationVec(0.0f);
            float _snowman2 = MathHelper.cos(this.yaw * ((float)Math.PI / 180)) * 0.3f;
            float _snowman3 = MathHelper.sin(this.yaw * ((float)Math.PI / 180)) * 0.3f;
            float _snowman4 = 1.2f - this.random.nextFloat() * 0.7f;
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.DOLPHIN, this.getX() - vec3d.x * (double)_snowman4 + (double)_snowman2, this.getY() - vec3d.y, this.getZ() - vec3d.z * (double)_snowman4 + (double)_snowman3, 0.0, 0.0, 0.0);
                this.world.addParticle(ParticleTypes.DOLPHIN, this.getX() - vec3d.x * (double)_snowman4 - (double)_snowman2, this.getY() - vec3d.y, this.getZ() - vec3d.z * (double)_snowman4 - (double)_snowman3, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 38) {
            this.spawnParticlesAround(ParticleTypes.HAPPY_VILLAGER);
        } else {
            super.handleStatus(status);
        }
    }

    private void spawnParticlesAround(ParticleEffect parameters) {
        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.01;
            _snowman = this.random.nextGaussian() * 0.01;
            _snowman = this.random.nextGaussian() * 0.01;
            this.world.addParticle(parameters, this.getParticleX(1.0), this.getRandomBodyY() + 0.2, this.getParticleZ(1.0), d, _snowman, _snowman);
        }
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isEmpty() && itemStack.getItem().isIn(ItemTags.FISHES)) {
            if (!this.world.isClient) {
                this.playSound(SoundEvents.ENTITY_DOLPHIN_EAT, 1.0f, 1.0f);
            }
            this.setHasFish(true);
            if (!player.abilities.creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.success(this.world.isClient);
        }
        return super.interactMob(player, hand);
    }

    public static boolean canSpawn(EntityType<DolphinEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (pos.getY() <= 45 || pos.getY() >= world.getSeaLevel()) {
            return false;
        }
        Optional<RegistryKey<Biome>> optional = world.method_31081(pos);
        return (!Objects.equals(optional, Optional.of(BiomeKeys.OCEAN)) || !Objects.equals(optional, Optional.of(BiomeKeys.DEEP_OCEAN))) && world.getFluidState(pos).isIn(FluidTags.WATER);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_DOLPHIN_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_DOLPHIN_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isTouchingWater() ? SoundEvents.ENTITY_DOLPHIN_AMBIENT_WATER : SoundEvents.ENTITY_DOLPHIN_AMBIENT;
    }

    @Override
    protected SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_DOLPHIN_SPLASH;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_DOLPHIN_SWIM;
    }

    protected boolean isNearTarget() {
        BlockPos blockPos = this.getNavigation().getTargetPos();
        if (blockPos != null) {
            return blockPos.isWithinDistance(this.getPos(), 12.0);
        }
        return false;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    static class LeadToNearbyTreasureGoal
    extends Goal {
        private final DolphinEntity dolphin;
        private boolean noPathToStructure;

        LeadToNearbyTreasureGoal(DolphinEntity dolphin) {
            this.dolphin = dolphin;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStop() {
            return false;
        }

        @Override
        public boolean canStart() {
            return this.dolphin.hasFish() && this.dolphin.getAir() >= 100;
        }

        @Override
        public boolean shouldContinue() {
            BlockPos blockPos = this.dolphin.getTreasurePos();
            return !new BlockPos((double)blockPos.getX(), this.dolphin.getY(), (double)blockPos.getZ()).isWithinDistance(this.dolphin.getPos(), 4.0) && !this.noPathToStructure && this.dolphin.getAir() >= 100;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void start() {
            if (!(this.dolphin.world instanceof ServerWorld)) {
                return;
            }
            ServerWorld serverWorld = (ServerWorld)this.dolphin.world;
            this.noPathToStructure = false;
            this.dolphin.getNavigation().stop();
            BlockPos _snowman2 = this.dolphin.getBlockPos();
            StructureFeature<FeatureConfig> _snowman3 = (double)serverWorld.random.nextFloat() >= 0.5 ? StructureFeature.OCEAN_RUIN : StructureFeature.SHIPWRECK;
            BlockPos _snowman4 = serverWorld.locateStructure(_snowman3, _snowman2, 50, false);
            if (_snowman4 == null) {
                StructureFeature<FeatureConfig> structureFeature = _snowman3.equals(StructureFeature.OCEAN_RUIN) ? StructureFeature.SHIPWRECK : StructureFeature.OCEAN_RUIN;
                BlockPos blockPos = serverWorld.locateStructure(structureFeature, _snowman2, 50, false);
                if (blockPos == null) {
                    this.noPathToStructure = true;
                    return;
                }
                this.dolphin.setTreasurePos(blockPos);
            } else {
                this.dolphin.setTreasurePos(_snowman4);
            }
            serverWorld.sendEntityStatus(this.dolphin, (byte)38);
        }

        @Override
        public void stop() {
            BlockPos blockPos = this.dolphin.getTreasurePos();
            if (new BlockPos((double)blockPos.getX(), this.dolphin.getY(), (double)blockPos.getZ()).isWithinDistance(this.dolphin.getPos(), 4.0) || this.noPathToStructure) {
                this.dolphin.setHasFish(false);
            }
        }

        @Override
        public void tick() {
            World world = this.dolphin.world;
            if (this.dolphin.isNearTarget() || this.dolphin.getNavigation().isIdle()) {
                Vec3d vec3d = Vec3d.ofCenter(this.dolphin.getTreasurePos());
                _snowman = TargetFinder.findTargetTowards(this.dolphin, 16, 1, vec3d, 0.3926991f);
                if (_snowman == null) {
                    _snowman = TargetFinder.findTargetTowards(this.dolphin, 8, 4, vec3d);
                }
                if (!(_snowman == null || world.getFluidState(_snowman = new BlockPos(_snowman)).isIn(FluidTags.WATER) && world.getBlockState(_snowman).canPathfindThrough(world, _snowman, NavigationType.WATER))) {
                    _snowman = TargetFinder.findTargetTowards(this.dolphin, 8, 5, vec3d);
                }
                if (_snowman == null) {
                    this.noPathToStructure = true;
                    return;
                }
                this.dolphin.getLookControl().lookAt(_snowman.x, _snowman.y, _snowman.z, this.dolphin.getBodyYawSpeed() + 20, this.dolphin.getLookPitchSpeed());
                this.dolphin.getNavigation().startMovingTo(_snowman.x, _snowman.y, _snowman.z, 1.3);
                if (world.random.nextInt(80) == 0) {
                    world.sendEntityStatus(this.dolphin, (byte)38);
                }
            }
        }
    }

    static class SwimWithPlayerGoal
    extends Goal {
        private final DolphinEntity dolphin;
        private final double speed;
        private PlayerEntity closestPlayer;

        SwimWithPlayerGoal(DolphinEntity dolphin, double speed) {
            this.dolphin = dolphin;
            this.speed = speed;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            this.closestPlayer = this.dolphin.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE, this.dolphin);
            if (this.closestPlayer == null) {
                return false;
            }
            return this.closestPlayer.isSwimming() && this.dolphin.getTarget() != this.closestPlayer;
        }

        @Override
        public boolean shouldContinue() {
            return this.closestPlayer != null && this.closestPlayer.isSwimming() && this.dolphin.squaredDistanceTo(this.closestPlayer) < 256.0;
        }

        @Override
        public void start() {
            this.closestPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 100));
        }

        @Override
        public void stop() {
            this.closestPlayer = null;
            this.dolphin.getNavigation().stop();
        }

        @Override
        public void tick() {
            this.dolphin.getLookControl().lookAt(this.closestPlayer, this.dolphin.getBodyYawSpeed() + 20, this.dolphin.getLookPitchSpeed());
            if (this.dolphin.squaredDistanceTo(this.closestPlayer) < 6.25) {
                this.dolphin.getNavigation().stop();
            } else {
                this.dolphin.getNavigation().startMovingTo(this.closestPlayer, this.speed);
            }
            if (this.closestPlayer.isSwimming() && this.closestPlayer.world.random.nextInt(6) == 0) {
                this.closestPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 100));
            }
        }
    }

    class PlayWithItemsGoal
    extends Goal {
        private int field_6758;

        private PlayWithItemsGoal() {
        }

        @Override
        public boolean canStart() {
            if (this.field_6758 > DolphinEntity.this.age) {
                return false;
            }
            List<ItemEntity> list = DolphinEntity.this.world.getEntitiesByClass(ItemEntity.class, DolphinEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), CAN_TAKE);
            return !list.isEmpty() || !DolphinEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
        }

        @Override
        public void start() {
            List<ItemEntity> list = DolphinEntity.this.world.getEntitiesByClass(ItemEntity.class, DolphinEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), CAN_TAKE);
            if (!list.isEmpty()) {
                DolphinEntity.this.getNavigation().startMovingTo(list.get(0), 1.2f);
                DolphinEntity.this.playSound(SoundEvents.ENTITY_DOLPHIN_PLAY, 1.0f, 1.0f);
            }
            this.field_6758 = 0;
        }

        @Override
        public void stop() {
            ItemStack itemStack = DolphinEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                this.spitOutItem(itemStack);
                DolphinEntity.this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                this.field_6758 = DolphinEntity.this.age + DolphinEntity.this.random.nextInt(100);
            }
        }

        @Override
        public void tick() {
            List<ItemEntity> list = DolphinEntity.this.world.getEntitiesByClass(ItemEntity.class, DolphinEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), CAN_TAKE);
            ItemStack _snowman2 = DolphinEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!_snowman2.isEmpty()) {
                this.spitOutItem(_snowman2);
                DolphinEntity.this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            } else if (!list.isEmpty()) {
                DolphinEntity.this.getNavigation().startMovingTo(list.get(0), 1.2f);
            }
        }

        private void spitOutItem(ItemStack stack) {
            if (stack.isEmpty()) {
                return;
            }
            double d = DolphinEntity.this.getEyeY() - (double)0.3f;
            ItemEntity _snowman2 = new ItemEntity(DolphinEntity.this.world, DolphinEntity.this.getX(), d, DolphinEntity.this.getZ(), stack);
            _snowman2.setPickupDelay(40);
            _snowman2.setThrower(DolphinEntity.this.getUuid());
            float _snowman3 = 0.3f;
            float _snowman4 = DolphinEntity.this.random.nextFloat() * ((float)Math.PI * 2);
            float _snowman5 = 0.02f * DolphinEntity.this.random.nextFloat();
            _snowman2.setVelocity(0.3f * -MathHelper.sin(DolphinEntity.this.yaw * ((float)Math.PI / 180)) * MathHelper.cos(DolphinEntity.this.pitch * ((float)Math.PI / 180)) + MathHelper.cos(_snowman4) * _snowman5, 0.3f * MathHelper.sin(DolphinEntity.this.pitch * ((float)Math.PI / 180)) * 1.5f, 0.3f * MathHelper.cos(DolphinEntity.this.yaw * ((float)Math.PI / 180)) * MathHelper.cos(DolphinEntity.this.pitch * ((float)Math.PI / 180)) + MathHelper.sin(_snowman4) * _snowman5);
            DolphinEntity.this.world.spawnEntity(_snowman2);
        }
    }

    static class DolphinMoveControl
    extends MoveControl {
        private final DolphinEntity dolphin;

        public DolphinMoveControl(DolphinEntity dolphin) {
            super(dolphin);
            this.dolphin = dolphin;
        }

        @Override
        public void tick() {
            if (this.dolphin.isTouchingWater()) {
                this.dolphin.setVelocity(this.dolphin.getVelocity().add(0.0, 0.005, 0.0));
            }
            if (this.state != MoveControl.State.MOVE_TO || this.dolphin.getNavigation().isIdle()) {
                this.dolphin.setMovementSpeed(0.0f);
                this.dolphin.setSidewaysSpeed(0.0f);
                this.dolphin.setUpwardSpeed(0.0f);
                this.dolphin.setForwardSpeed(0.0f);
                return;
            }
            double d = this.targetX - this.dolphin.getX();
            _snowman = d * d + (_snowman = this.targetY - this.dolphin.getY()) * _snowman + (_snowman = this.targetZ - this.dolphin.getZ()) * _snowman;
            if (_snowman < 2.500000277905201E-7) {
                this.entity.setForwardSpeed(0.0f);
                return;
            }
            float _snowman2 = (float)(MathHelper.atan2(_snowman, d) * 57.2957763671875) - 90.0f;
            this.dolphin.bodyYaw = this.dolphin.yaw = this.changeAngle(this.dolphin.yaw, _snowman2, 10.0f);
            this.dolphin.headYaw = this.dolphin.yaw;
            float _snowman3 = (float)(this.speed * this.dolphin.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
            if (this.dolphin.isTouchingWater()) {
                this.dolphin.setMovementSpeed(_snowman3 * 0.02f);
                float f = -((float)(MathHelper.atan2(_snowman, MathHelper.sqrt(d * d + _snowman * _snowman)) * 57.2957763671875));
                f = MathHelper.clamp(MathHelper.wrapDegrees(f), -85.0f, 85.0f);
                this.dolphin.pitch = this.changeAngle(this.dolphin.pitch, f, 5.0f);
                _snowman = MathHelper.cos(this.dolphin.pitch * ((float)Math.PI / 180));
                _snowman = MathHelper.sin(this.dolphin.pitch * ((float)Math.PI / 180));
                this.dolphin.forwardSpeed = _snowman * _snowman3;
                this.dolphin.upwardSpeed = -_snowman * _snowman3;
            } else {
                this.dolphin.setMovementSpeed(_snowman3 * 0.1f);
            }
        }
    }
}

