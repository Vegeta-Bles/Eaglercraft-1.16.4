/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.mob;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.control.JumpControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.mob.MobVisibilityCache;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraft.loot.context.LootContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class MobEntity
extends LivingEntity {
    private static final TrackedData<Byte> MOB_FLAGS = DataTracker.registerData(MobEntity.class, TrackedDataHandlerRegistry.BYTE);
    public int ambientSoundChance;
    protected int experiencePoints;
    protected LookControl lookControl;
    protected MoveControl moveControl;
    protected JumpControl jumpControl;
    private final BodyControl bodyControl;
    protected EntityNavigation navigation;
    protected final GoalSelector goalSelector;
    protected final GoalSelector targetSelector;
    private LivingEntity target;
    private final MobVisibilityCache visibilityCache;
    private final DefaultedList<ItemStack> handItems = DefaultedList.ofSize(2, ItemStack.EMPTY);
    protected final float[] handDropChances = new float[2];
    private final DefaultedList<ItemStack> armorItems = DefaultedList.ofSize(4, ItemStack.EMPTY);
    protected final float[] armorDropChances = new float[4];
    private boolean pickUpLoot;
    private boolean persistent;
    private final Map<PathNodeType, Float> pathfindingPenalties = Maps.newEnumMap(PathNodeType.class);
    private Identifier lootTable;
    private long lootTableSeed;
    @Nullable
    private Entity holdingEntity;
    private int holdingEntityId;
    @Nullable
    private CompoundTag leashTag;
    private BlockPos positionTarget = BlockPos.ORIGIN;
    private float positionTargetRange = -1.0f;

    protected MobEntity(EntityType<? extends MobEntity> entityType, World world) {
        super((EntityType<? extends LivingEntity>)entityType, world);
        this.goalSelector = new GoalSelector(world.getProfilerSupplier());
        this.targetSelector = new GoalSelector(world.getProfilerSupplier());
        this.lookControl = new LookControl(this);
        this.moveControl = new MoveControl(this);
        this.jumpControl = new JumpControl(this);
        this.bodyControl = this.createBodyControl();
        this.navigation = this.createNavigation(world);
        this.visibilityCache = new MobVisibilityCache(this);
        Arrays.fill(this.armorDropChances, 0.085f);
        Arrays.fill(this.handDropChances, 0.085f);
        if (world != null && !world.isClient) {
            this.initGoals();
        }
    }

    protected void initGoals() {
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
    }

    protected EntityNavigation createNavigation(World world) {
        return new MobNavigation(this, world);
    }

    protected boolean movesIndependently() {
        return false;
    }

    public float getPathfindingPenalty(PathNodeType nodeType) {
        MobEntity mobEntity = this.getVehicle() instanceof MobEntity && ((MobEntity)this.getVehicle()).movesIndependently() ? (MobEntity)this.getVehicle() : this;
        Float _snowman2 = mobEntity.pathfindingPenalties.get((Object)nodeType);
        return _snowman2 == null ? nodeType.getDefaultPenalty() : _snowman2.floatValue();
    }

    public void setPathfindingPenalty(PathNodeType nodeType, float penalty) {
        this.pathfindingPenalties.put(nodeType, Float.valueOf(penalty));
    }

    public boolean method_29244(PathNodeType pathNodeType) {
        return pathNodeType != PathNodeType.DANGER_FIRE && pathNodeType != PathNodeType.DANGER_CACTUS && pathNodeType != PathNodeType.DANGER_OTHER && pathNodeType != PathNodeType.WALKABLE_DOOR;
    }

    protected BodyControl createBodyControl() {
        return new BodyControl(this);
    }

    public LookControl getLookControl() {
        return this.lookControl;
    }

    public MoveControl getMoveControl() {
        if (this.hasVehicle() && this.getVehicle() instanceof MobEntity) {
            MobEntity mobEntity = (MobEntity)this.getVehicle();
            return mobEntity.getMoveControl();
        }
        return this.moveControl;
    }

    public JumpControl getJumpControl() {
        return this.jumpControl;
    }

    public EntityNavigation getNavigation() {
        if (this.hasVehicle() && this.getVehicle() instanceof MobEntity) {
            MobEntity mobEntity = (MobEntity)this.getVehicle();
            return mobEntity.getNavigation();
        }
        return this.navigation;
    }

    public MobVisibilityCache getVisibilityCache() {
        return this.visibilityCache;
    }

    @Nullable
    public LivingEntity getTarget() {
        return this.target;
    }

    public void setTarget(@Nullable LivingEntity target) {
        this.target = target;
    }

    @Override
    public boolean canTarget(EntityType<?> type) {
        return type != EntityType.GHAST;
    }

    public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
        return false;
    }

    public void onEatingGrass() {
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MOB_FLAGS, (byte)0);
    }

    public int getMinAmbientSoundDelay() {
        return 80;
    }

    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.world.getProfiler().push("mobBaseTick");
        if (this.isAlive() && this.random.nextInt(1000) < this.ambientSoundChance++) {
            this.resetSoundDelay();
            this.playAmbientSound();
        }
        this.world.getProfiler().pop();
    }

    @Override
    protected void playHurtSound(DamageSource source) {
        this.resetSoundDelay();
        super.playHurtSound(source);
    }

    private void resetSoundDelay() {
        this.ambientSoundChance = -this.getMinAmbientSoundDelay();
    }

    @Override
    protected int getCurrentExperience(PlayerEntity player) {
        if (this.experiencePoints > 0) {
            int n = this.experiencePoints;
            for (_snowman = 0; _snowman < this.armorItems.size(); ++_snowman) {
                if (this.armorItems.get(_snowman).isEmpty() || !(this.armorDropChances[_snowman] <= 1.0f)) continue;
                n += 1 + this.random.nextInt(3);
            }
            for (_snowman = 0; _snowman < this.handItems.size(); ++_snowman) {
                if (this.handItems.get(_snowman).isEmpty() || !(this.handDropChances[_snowman] <= 1.0f)) continue;
                n += 1 + this.random.nextInt(3);
            }
            return n;
        }
        return this.experiencePoints;
    }

    public void playSpawnEffects() {
        if (this.world.isClient) {
            for (int i = 0; i < 20; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                _snowman = this.random.nextGaussian() * 0.02;
                _snowman = this.random.nextGaussian() * 0.02;
                _snowman = 10.0;
                this.world.addParticle(ParticleTypes.POOF, this.offsetX(1.0) - d * 10.0, this.getRandomBodyY() - _snowman * 10.0, this.getParticleZ(1.0) - _snowman * 10.0, d, _snowman, _snowman);
            }
        } else {
            this.world.sendEntityStatus(this, (byte)20);
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 20) {
            this.playSpawnEffects();
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient) {
            this.updateLeash();
            if (this.age % 5 == 0) {
                this.updateGoalControls();
            }
        }
    }

    protected void updateGoalControls() {
        boolean bl = !(this.getPrimaryPassenger() instanceof MobEntity);
        _snowman = !(this.getVehicle() instanceof BoatEntity);
        this.goalSelector.setControlEnabled(Goal.Control.MOVE, bl);
        this.goalSelector.setControlEnabled(Goal.Control.JUMP, bl && _snowman);
        this.goalSelector.setControlEnabled(Goal.Control.LOOK, bl);
    }

    @Override
    protected float turnHead(float bodyRotation, float headRotation) {
        this.bodyControl.tick();
        return headRotation;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putBoolean("CanPickUpLoot", this.canPickUpLoot());
        tag.putBoolean("PersistenceRequired", this.persistent);
        ListTag listTag2 = new ListTag();
        for (ItemStack itemStack : this.armorItems) {
            CompoundTag compoundTag = new CompoundTag();
            if (!itemStack.isEmpty()) {
                itemStack.toTag(compoundTag);
            }
            listTag2.add(compoundTag);
        }
        tag.put("ArmorItems", listTag2);
        ListTag listTag = new ListTag();
        for (ItemStack itemStack : this.handItems) {
            CompoundTag compoundTag = new CompoundTag();
            if (!itemStack.isEmpty()) {
                itemStack.toTag(compoundTag);
            }
            listTag.add(compoundTag);
        }
        tag.put("HandItems", listTag);
        ListTag listTag3 = new ListTag();
        for (float object3 : this.armorDropChances) {
            listTag3.add(FloatTag.of(object3));
        }
        tag.put("ArmorDropChances", listTag3);
        ListTag listTag4 = new ListTag();
        for (float f : this.handDropChances) {
            listTag4.add(FloatTag.of(f));
        }
        tag.put("HandDropChances", listTag4);
        if (this.holdingEntity != null) {
            CompoundTag compoundTag = new CompoundTag();
            if (this.holdingEntity instanceof LivingEntity) {
                UUID uUID = this.holdingEntity.getUuid();
                compoundTag.putUuid("UUID", uUID);
            } else if (this.holdingEntity instanceof AbstractDecorationEntity) {
                BlockPos blockPos = ((AbstractDecorationEntity)this.holdingEntity).getDecorationBlockPos();
                compoundTag.putInt("X", blockPos.getX());
                compoundTag.putInt("Y", blockPos.getY());
                compoundTag.putInt("Z", blockPos.getZ());
            }
            tag.put("Leash", compoundTag);
        } else if (this.leashTag != null) {
            tag.put("Leash", this.leashTag.copy());
        }
        tag.putBoolean("LeftHanded", this.isLeftHanded());
        if (this.lootTable != null) {
            tag.putString("DeathLootTable", this.lootTable.toString());
            if (this.lootTableSeed != 0L) {
                tag.putLong("DeathLootTableSeed", this.lootTableSeed);
            }
        }
        if (this.isAiDisabled()) {
            tag.putBoolean("NoAI", this.isAiDisabled());
        }
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        int n;
        ListTag listTag;
        super.readCustomDataFromTag(tag);
        if (tag.contains("CanPickUpLoot", 1)) {
            this.setCanPickUpLoot(tag.getBoolean("CanPickUpLoot"));
        }
        this.persistent = tag.getBoolean("PersistenceRequired");
        if (tag.contains("ArmorItems", 9)) {
            listTag = tag.getList("ArmorItems", 10);
            for (n = 0; n < this.armorItems.size(); ++n) {
                this.armorItems.set(n, ItemStack.fromTag(listTag.getCompound(n)));
            }
        }
        if (tag.contains("HandItems", 9)) {
            listTag = tag.getList("HandItems", 10);
            for (n = 0; n < this.handItems.size(); ++n) {
                this.handItems.set(n, ItemStack.fromTag(listTag.getCompound(n)));
            }
        }
        if (tag.contains("ArmorDropChances", 9)) {
            listTag = tag.getList("ArmorDropChances", 5);
            for (n = 0; n < listTag.size(); ++n) {
                this.armorDropChances[n] = listTag.getFloat(n);
            }
        }
        if (tag.contains("HandDropChances", 9)) {
            listTag = tag.getList("HandDropChances", 5);
            for (n = 0; n < listTag.size(); ++n) {
                this.handDropChances[n] = listTag.getFloat(n);
            }
        }
        if (tag.contains("Leash", 10)) {
            this.leashTag = tag.getCompound("Leash");
        }
        this.setLeftHanded(tag.getBoolean("LeftHanded"));
        if (tag.contains("DeathLootTable", 8)) {
            this.lootTable = new Identifier(tag.getString("DeathLootTable"));
            this.lootTableSeed = tag.getLong("DeathLootTableSeed");
        }
        this.setAiDisabled(tag.getBoolean("NoAI"));
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        super.dropLoot(source, causedByPlayer);
        this.lootTable = null;
    }

    @Override
    protected LootContext.Builder getLootContextBuilder(boolean causedByPlayer, DamageSource source) {
        return super.getLootContextBuilder(causedByPlayer, source).random(this.lootTableSeed, this.random);
    }

    @Override
    public final Identifier getLootTable() {
        return this.lootTable == null ? this.getLootTableId() : this.lootTable;
    }

    protected Identifier getLootTableId() {
        return super.getLootTable();
    }

    public void setForwardSpeed(float forwardSpeed) {
        this.forwardSpeed = forwardSpeed;
    }

    public void setUpwardSpeed(float upwardSpeed) {
        this.upwardSpeed = upwardSpeed;
    }

    public void setSidewaysSpeed(float sidewaysMovement) {
        this.sidewaysSpeed = sidewaysMovement;
    }

    @Override
    public void setMovementSpeed(float movementSpeed) {
        super.setMovementSpeed(movementSpeed);
        this.setForwardSpeed(movementSpeed);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.world.getProfiler().push("looting");
        if (!this.world.isClient && this.canPickUpLoot() && this.isAlive() && !this.dead && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            List<ItemEntity> list = this.world.getNonSpectatingEntities(ItemEntity.class, this.getBoundingBox().expand(1.0, 0.0, 1.0));
            for (ItemEntity itemEntity : list) {
                if (itemEntity.removed || itemEntity.getStack().isEmpty() || itemEntity.cannotPickup() || !this.canGather(itemEntity.getStack())) continue;
                this.loot(itemEntity);
            }
        }
        this.world.getProfiler().pop();
    }

    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        if (this.tryEquip(itemStack)) {
            this.method_29499(item);
            this.sendPickup(item, itemStack.getCount());
            item.remove();
        }
    }

    public boolean tryEquip(ItemStack equipment) {
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(equipment);
        ItemStack _snowman2 = this.getEquippedStack(equipmentSlot);
        boolean _snowman3 = this.prefersNewEquipment(equipment, _snowman2);
        if (_snowman3 && this.canPickupItem(equipment)) {
            double d = this.getDropChance(equipmentSlot);
            if (!_snowman2.isEmpty() && (double)Math.max(this.random.nextFloat() - 0.1f, 0.0f) < d) {
                this.dropStack(_snowman2);
            }
            this.equipLootStack(equipmentSlot, equipment);
            this.onEquipStack(equipment);
            return true;
        }
        return false;
    }

    protected void equipLootStack(EquipmentSlot slot, ItemStack stack) {
        this.equipStack(slot, stack);
        this.updateDropChances(slot);
        this.persistent = true;
    }

    public void updateDropChances(EquipmentSlot slot) {
        switch (slot.getType()) {
            case HAND: {
                this.handDropChances[slot.getEntitySlotId()] = 2.0f;
                break;
            }
            case ARMOR: {
                this.armorDropChances[slot.getEntitySlotId()] = 2.0f;
            }
        }
    }

    protected boolean prefersNewEquipment(ItemStack newStack, ItemStack oldStack) {
        if (oldStack.isEmpty()) {
            return true;
        }
        if (newStack.getItem() instanceof SwordItem) {
            if (!(oldStack.getItem() instanceof SwordItem)) {
                return true;
            }
            SwordItem swordItem = (SwordItem)newStack.getItem();
            _snowman = (SwordItem)oldStack.getItem();
            if (swordItem.getAttackDamage() != _snowman.getAttackDamage()) {
                return swordItem.getAttackDamage() > _snowman.getAttackDamage();
            }
            return this.prefersNewDamageableItem(newStack, oldStack);
        }
        if (newStack.getItem() instanceof BowItem && oldStack.getItem() instanceof BowItem) {
            return this.prefersNewDamageableItem(newStack, oldStack);
        }
        if (newStack.getItem() instanceof CrossbowItem && oldStack.getItem() instanceof CrossbowItem) {
            return this.prefersNewDamageableItem(newStack, oldStack);
        }
        if (newStack.getItem() instanceof ArmorItem) {
            if (EnchantmentHelper.hasBindingCurse(oldStack)) {
                return false;
            }
            if (!(oldStack.getItem() instanceof ArmorItem)) {
                return true;
            }
            ArmorItem armorItem = (ArmorItem)newStack.getItem();
            _snowman = (ArmorItem)oldStack.getItem();
            if (armorItem.getProtection() != _snowman.getProtection()) {
                return armorItem.getProtection() > _snowman.getProtection();
            }
            if (armorItem.method_26353() != _snowman.method_26353()) {
                return armorItem.method_26353() > _snowman.method_26353();
            }
            return this.prefersNewDamageableItem(newStack, oldStack);
        }
        if (newStack.getItem() instanceof MiningToolItem) {
            if (oldStack.getItem() instanceof BlockItem) {
                return true;
            }
            if (oldStack.getItem() instanceof MiningToolItem) {
                MiningToolItem miningToolItem = (MiningToolItem)newStack.getItem();
                _snowman = (MiningToolItem)oldStack.getItem();
                if (miningToolItem.getAttackDamage() != _snowman.getAttackDamage()) {
                    return miningToolItem.getAttackDamage() > _snowman.getAttackDamage();
                }
                return this.prefersNewDamageableItem(newStack, oldStack);
            }
        }
        return false;
    }

    public boolean prefersNewDamageableItem(ItemStack newStack, ItemStack oldStack) {
        if (newStack.getDamage() < oldStack.getDamage() || newStack.hasTag() && !oldStack.hasTag()) {
            return true;
        }
        if (newStack.hasTag() && oldStack.hasTag()) {
            return newStack.getTag().getKeys().stream().anyMatch(string -> !string.equals("Damage")) && !oldStack.getTag().getKeys().stream().anyMatch(string -> !string.equals("Damage"));
        }
        return false;
    }

    public boolean canPickupItem(ItemStack stack) {
        return true;
    }

    public boolean canGather(ItemStack stack) {
        return this.canPickupItem(stack);
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return true;
    }

    public boolean cannotDespawn() {
        return this.hasVehicle();
    }

    protected boolean isDisallowedInPeaceful() {
        return false;
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
            this.remove();
            return;
        }
        if (this.isPersistent() || this.cannotDespawn()) {
            this.despawnCounter = 0;
            return;
        }
        PlayerEntity playerEntity = this.world.getClosestPlayer(this, -1.0);
        if (playerEntity != null) {
            double d = playerEntity.squaredDistanceTo(this);
            if (d > (double)(_snowman = (_snowman = this.getType().getSpawnGroup().getImmediateDespawnRange()) * _snowman) && this.canImmediatelyDespawn(d)) {
                this.remove();
            }
            int _snowman2 = this.getType().getSpawnGroup().getDespawnStartRange();
            int _snowman3 = _snowman2 * _snowman2;
            if (this.despawnCounter > 600 && this.random.nextInt(800) == 0 && d > (double)_snowman3 && this.canImmediatelyDespawn(d)) {
                this.remove();
            } else if (d < (double)_snowman3) {
                this.despawnCounter = 0;
            }
        }
    }

    @Override
    protected final void tickNewAi() {
        ++this.despawnCounter;
        this.world.getProfiler().push("sensing");
        this.visibilityCache.clear();
        this.world.getProfiler().pop();
        this.world.getProfiler().push("targetSelector");
        this.targetSelector.tick();
        this.world.getProfiler().pop();
        this.world.getProfiler().push("goalSelector");
        this.goalSelector.tick();
        this.world.getProfiler().pop();
        this.world.getProfiler().push("navigation");
        this.navigation.tick();
        this.world.getProfiler().pop();
        this.world.getProfiler().push("mob tick");
        this.mobTick();
        this.world.getProfiler().pop();
        this.world.getProfiler().push("controls");
        this.world.getProfiler().push("move");
        this.moveControl.tick();
        this.world.getProfiler().swap("look");
        this.lookControl.tick();
        this.world.getProfiler().swap("jump");
        this.jumpControl.tick();
        this.world.getProfiler().pop();
        this.world.getProfiler().pop();
        this.sendAiDebugData();
    }

    protected void sendAiDebugData() {
        DebugInfoSender.sendGoalSelector(this.world, this, this.goalSelector);
    }

    protected void mobTick() {
    }

    public int getLookPitchSpeed() {
        return 40;
    }

    public int getBodyYawSpeed() {
        return 75;
    }

    public int getLookYawSpeed() {
        return 10;
    }

    public void lookAtEntity(Entity targetEntity, float maxYawChange, float maxPitchChange) {
        double _snowman2;
        double d = targetEntity.getX() - this.getX();
        _snowman = targetEntity.getZ() - this.getZ();
        if (targetEntity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)targetEntity;
            _snowman2 = livingEntity.getEyeY() - this.getEyeY();
        } else {
            _snowman2 = (targetEntity.getBoundingBox().minY + targetEntity.getBoundingBox().maxY) / 2.0 - this.getEyeY();
        }
        double d2 = MathHelper.sqrt(d * d + _snowman * _snowman);
        float _snowman3 = (float)(MathHelper.atan2(_snowman, d) * 57.2957763671875) - 90.0f;
        float _snowman4 = (float)(-(MathHelper.atan2(_snowman2, d2) * 57.2957763671875));
        this.pitch = this.changeAngle(this.pitch, _snowman4, maxPitchChange);
        this.yaw = this.changeAngle(this.yaw, _snowman3, maxYawChange);
    }

    private float changeAngle(float oldAngle, float newAngle, float maxChangeInAngle) {
        float f = MathHelper.wrapDegrees(newAngle - oldAngle);
        if (f > maxChangeInAngle) {
            f = maxChangeInAngle;
        }
        if (f < -maxChangeInAngle) {
            f = -maxChangeInAngle;
        }
        return oldAngle + f;
    }

    public static boolean canMobSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockPos blockPos = pos.down();
        return spawnReason == SpawnReason.SPAWNER || world.getBlockState(blockPos).allowsSpawning(world, blockPos, type);
    }

    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    public boolean canSpawn(WorldView world) {
        return !world.containsFluid(this.getBoundingBox()) && world.intersectsEntities(this);
    }

    public int getLimitPerChunk() {
        return 4;
    }

    public boolean spawnsTooManyForEachTry(int count) {
        return false;
    }

    @Override
    public int getSafeFallDistance() {
        if (this.getTarget() == null) {
            return 3;
        }
        int n = (int)(this.getHealth() - this.getMaxHealth() * 0.33f);
        if ((n -= (3 - this.world.getDifficulty().getId()) * 4) < 0) {
            n = 0;
        }
        return n + 3;
    }

    @Override
    public Iterable<ItemStack> getItemsHand() {
        return this.handItems;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return this.armorItems;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        switch (slot.getType()) {
            case HAND: {
                return this.handItems.get(slot.getEntitySlotId());
            }
            case ARMOR: {
                return this.armorItems.get(slot.getEntitySlotId());
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        switch (slot.getType()) {
            case HAND: {
                this.handItems.set(slot.getEntitySlotId(), stack);
                break;
            }
            case ARMOR: {
                this.armorItems.set(slot.getEntitySlotId(), stack);
            }
        }
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = this.getEquippedStack(equipmentSlot);
            float _snowman2 = this.getDropChance(equipmentSlot);
            boolean bl = _snowman = _snowman2 > 1.0f;
            if (itemStack.isEmpty() || EnchantmentHelper.hasVanishingCurse(itemStack) || !allowDrops && !_snowman || !(Math.max(this.random.nextFloat() - (float)lootingMultiplier * 0.01f, 0.0f) < _snowman2)) continue;
            if (!_snowman && itemStack.isDamageable()) {
                itemStack.setDamage(itemStack.getMaxDamage() - this.random.nextInt(1 + this.random.nextInt(Math.max(itemStack.getMaxDamage() - 3, 1))));
            }
            this.dropStack(itemStack);
            this.equipStack(equipmentSlot, ItemStack.EMPTY);
        }
    }

    protected float getDropChance(EquipmentSlot slot) {
        float f;
        switch (slot.getType()) {
            case HAND: {
                f = this.handDropChances[slot.getEntitySlotId()];
                break;
            }
            case ARMOR: {
                f = this.armorDropChances[slot.getEntitySlotId()];
                break;
            }
            default: {
                f = 0.0f;
            }
        }
        return f;
    }

    protected void initEquipment(LocalDifficulty difficulty) {
        if (this.random.nextFloat() < 0.15f * difficulty.getClampedLocalDifficulty()) {
            int n = this.random.nextInt(2);
            float f = _snowman = this.world.getDifficulty() == Difficulty.HARD ? 0.1f : 0.25f;
            if (this.random.nextFloat() < 0.095f) {
                ++n;
            }
            if (this.random.nextFloat() < 0.095f) {
                ++n;
            }
            if (this.random.nextFloat() < 0.095f) {
                ++n;
            }
            boolean _snowman2 = true;
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (equipmentSlot.getType() != EquipmentSlot.Type.ARMOR) continue;
                ItemStack itemStack = this.getEquippedStack(equipmentSlot);
                if (!_snowman2 && this.random.nextFloat() < _snowman) break;
                _snowman2 = false;
                if (!itemStack.isEmpty() || (_snowman = MobEntity.getEquipmentForSlot(equipmentSlot, n)) == null) continue;
                this.equipStack(equipmentSlot, new ItemStack(_snowman));
            }
        }
    }

    public static EquipmentSlot getPreferredEquipmentSlot(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Blocks.CARVED_PUMPKIN.asItem() || item instanceof BlockItem && ((BlockItem)item).getBlock() instanceof AbstractSkullBlock) {
            return EquipmentSlot.HEAD;
        }
        if (item instanceof ArmorItem) {
            return ((ArmorItem)item).getSlotType();
        }
        if (item == Items.ELYTRA) {
            return EquipmentSlot.CHEST;
        }
        if (item == Items.SHIELD) {
            return EquipmentSlot.OFFHAND;
        }
        return EquipmentSlot.MAINHAND;
    }

    @Nullable
    public static Item getEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel) {
        switch (equipmentSlot) {
            case HEAD: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_HELMET;
                }
                if (equipmentLevel == 1) {
                    return Items.GOLDEN_HELMET;
                }
                if (equipmentLevel == 2) {
                    return Items.CHAINMAIL_HELMET;
                }
                if (equipmentLevel == 3) {
                    return Items.IRON_HELMET;
                }
                if (equipmentLevel == 4) {
                    return Items.DIAMOND_HELMET;
                }
            }
            case CHEST: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_CHESTPLATE;
                }
                if (equipmentLevel == 1) {
                    return Items.GOLDEN_CHESTPLATE;
                }
                if (equipmentLevel == 2) {
                    return Items.CHAINMAIL_CHESTPLATE;
                }
                if (equipmentLevel == 3) {
                    return Items.IRON_CHESTPLATE;
                }
                if (equipmentLevel == 4) {
                    return Items.DIAMOND_CHESTPLATE;
                }
            }
            case LEGS: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_LEGGINGS;
                }
                if (equipmentLevel == 1) {
                    return Items.GOLDEN_LEGGINGS;
                }
                if (equipmentLevel == 2) {
                    return Items.CHAINMAIL_LEGGINGS;
                }
                if (equipmentLevel == 3) {
                    return Items.IRON_LEGGINGS;
                }
                if (equipmentLevel == 4) {
                    return Items.DIAMOND_LEGGINGS;
                }
            }
            case FEET: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_BOOTS;
                }
                if (equipmentLevel == 1) {
                    return Items.GOLDEN_BOOTS;
                }
                if (equipmentLevel == 2) {
                    return Items.CHAINMAIL_BOOTS;
                }
                if (equipmentLevel == 3) {
                    return Items.IRON_BOOTS;
                }
                if (equipmentLevel != 4) break;
                return Items.DIAMOND_BOOTS;
            }
        }
        return null;
    }

    protected void updateEnchantments(LocalDifficulty difficulty) {
        float f = difficulty.getClampedLocalDifficulty();
        this.method_30759(f);
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (equipmentSlot.getType() != EquipmentSlot.Type.ARMOR) continue;
            this.method_30758(f, equipmentSlot);
        }
    }

    protected void method_30759(float f) {
        if (!this.getMainHandStack().isEmpty() && this.random.nextFloat() < 0.25f * f) {
            this.equipStack(EquipmentSlot.MAINHAND, EnchantmentHelper.enchant(this.random, this.getMainHandStack(), (int)(5.0f + f * (float)this.random.nextInt(18)), false));
        }
    }

    protected void method_30758(float f, EquipmentSlot equipmentSlot) {
        ItemStack itemStack = this.getEquippedStack(equipmentSlot);
        if (!itemStack.isEmpty() && this.random.nextFloat() < 0.5f * f) {
            this.equipStack(equipmentSlot, EnchantmentHelper.enchant(this.random, itemStack, (int)(5.0f + f * (float)this.random.nextInt(18)), false));
        }
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).addPersistentModifier(new EntityAttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        if (this.random.nextFloat() < 0.05f) {
            this.setLeftHanded(true);
        } else {
            this.setLeftHanded(false);
        }
        return entityData;
    }

    public boolean canBeControlledByRider() {
        return false;
    }

    public void setPersistent() {
        this.persistent = true;
    }

    public void setEquipmentDropChance(EquipmentSlot slot, float chance) {
        switch (slot.getType()) {
            case HAND: {
                this.handDropChances[slot.getEntitySlotId()] = chance;
                break;
            }
            case ARMOR: {
                this.armorDropChances[slot.getEntitySlotId()] = chance;
            }
        }
    }

    public boolean canPickUpLoot() {
        return this.pickUpLoot;
    }

    public void setCanPickUpLoot(boolean pickUpLoot) {
        this.pickUpLoot = pickUpLoot;
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(stack);
        return this.getEquippedStack(equipmentSlot).isEmpty() && this.canPickUpLoot();
    }

    public boolean isPersistent() {
        return this.persistent;
    }

    @Override
    public final ActionResult interact(PlayerEntity player, Hand hand) {
        if (!this.isAlive()) {
            return ActionResult.PASS;
        }
        if (this.getHoldingEntity() == player) {
            this.detachLeash(true, !player.abilities.creativeMode);
            return ActionResult.success(this.world.isClient);
        }
        ActionResult actionResult = this.method_29506(player, hand);
        if (actionResult.isAccepted()) {
            return actionResult;
        }
        actionResult = this.interactMob(player, hand);
        if (actionResult.isAccepted()) {
            return actionResult;
        }
        return super.interact(player, hand);
    }

    private ActionResult method_29506(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);
        if (itemStack.getItem() == Items.LEAD && this.canBeLeashedBy(playerEntity)) {
            this.attachLeash(playerEntity, true);
            itemStack.decrement(1);
            return ActionResult.success(this.world.isClient);
        }
        if (itemStack.getItem() == Items.NAME_TAG && ((ActionResult)((Object)(object = itemStack.useOnEntity(playerEntity, this, hand)))).isAccepted()) {
            return object;
        }
        if (itemStack.getItem() instanceof SpawnEggItem) {
            if (this.world instanceof ServerWorld) {
                Object object = (SpawnEggItem)itemStack.getItem();
                Optional<MobEntity> _snowman2 = ((SpawnEggItem)object).spawnBaby(playerEntity, this, this.getType(), (ServerWorld)this.world, this.getPos(), itemStack);
                _snowman2.ifPresent(mobEntity -> this.onPlayerSpawnedChild(playerEntity, (MobEntity)mobEntity));
                return _snowman2.isPresent() ? ActionResult.SUCCESS : ActionResult.PASS;
            }
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    protected void onPlayerSpawnedChild(PlayerEntity player, MobEntity child) {
    }

    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return ActionResult.PASS;
    }

    public boolean isInWalkTargetRange() {
        return this.isInWalkTargetRange(this.getBlockPos());
    }

    public boolean isInWalkTargetRange(BlockPos pos) {
        if (this.positionTargetRange == -1.0f) {
            return true;
        }
        return this.positionTarget.getSquaredDistance(pos) < (double)(this.positionTargetRange * this.positionTargetRange);
    }

    public void setPositionTarget(BlockPos target, int range) {
        this.positionTarget = target;
        this.positionTargetRange = range;
    }

    public BlockPos getPositionTarget() {
        return this.positionTarget;
    }

    public float getPositionTargetRange() {
        return this.positionTargetRange;
    }

    public boolean hasPositionTarget() {
        return this.positionTargetRange != -1.0f;
    }

    @Nullable
    public <T extends MobEntity> T method_29243(EntityType<T> entityType, boolean bl) {
        if (this.removed) {
            return null;
        }
        MobEntity mobEntity = (MobEntity)entityType.create(this.world);
        mobEntity.copyPositionAndRotation(this);
        mobEntity.setBaby(this.isBaby());
        mobEntity.setAiDisabled(this.isAiDisabled());
        if (this.hasCustomName()) {
            mobEntity.setCustomName(this.getCustomName());
            mobEntity.setCustomNameVisible(this.isCustomNameVisible());
        }
        if (this.isPersistent()) {
            mobEntity.setPersistent();
        }
        mobEntity.setInvulnerable(this.isInvulnerable());
        if (bl) {
            mobEntity.setCanPickUpLoot(this.canPickUpLoot());
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack itemStack = this.getEquippedStack(equipmentSlot);
                if (itemStack.isEmpty()) continue;
                mobEntity.equipStack(equipmentSlot, itemStack.copy());
                mobEntity.setEquipmentDropChance(equipmentSlot, this.getDropChance(equipmentSlot));
                itemStack.setCount(0);
            }
        }
        this.world.spawnEntity(mobEntity);
        if (this.hasVehicle()) {
            Entity entity = this.getVehicle();
            this.stopRiding();
            mobEntity.startRiding(entity, true);
        }
        this.remove();
        return (T)mobEntity;
    }

    protected void updateLeash() {
        if (this.leashTag != null) {
            this.deserializeLeashTag();
        }
        if (this.holdingEntity == null) {
            return;
        }
        if (!this.isAlive() || !this.holdingEntity.isAlive()) {
            this.detachLeash(true, true);
        }
    }

    public void detachLeash(boolean sendPacket, boolean dropItem) {
        if (this.holdingEntity != null) {
            this.teleporting = false;
            if (!(this.holdingEntity instanceof PlayerEntity)) {
                this.holdingEntity.teleporting = false;
            }
            this.holdingEntity = null;
            this.leashTag = null;
            if (!this.world.isClient && dropItem) {
                this.dropItem(Items.LEAD);
            }
            if (!this.world.isClient && sendPacket && this.world instanceof ServerWorld) {
                ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityAttachS2CPacket(this, null));
            }
        }
    }

    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isLeashed() && !(this instanceof Monster);
    }

    public boolean isLeashed() {
        return this.holdingEntity != null;
    }

    @Nullable
    public Entity getHoldingEntity() {
        if (this.holdingEntity == null && this.holdingEntityId != 0 && this.world.isClient) {
            this.holdingEntity = this.world.getEntityById(this.holdingEntityId);
        }
        return this.holdingEntity;
    }

    public void attachLeash(Entity entity, boolean sendPacket) {
        this.holdingEntity = entity;
        this.leashTag = null;
        this.teleporting = true;
        if (!(this.holdingEntity instanceof PlayerEntity)) {
            this.holdingEntity.teleporting = true;
        }
        if (!this.world.isClient && sendPacket && this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityAttachS2CPacket(this, this.holdingEntity));
        }
        if (this.hasVehicle()) {
            this.stopRiding();
        }
    }

    public void setHoldingEntityId(int id) {
        this.holdingEntityId = id;
        this.detachLeash(false, false);
    }

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        boolean bl = super.startRiding(entity, force);
        if (bl && this.isLeashed()) {
            this.detachLeash(true, true);
        }
        return bl;
    }

    private void deserializeLeashTag() {
        if (this.leashTag != null && this.world instanceof ServerWorld) {
            if (this.leashTag.containsUuid("UUID")) {
                UUID uUID = this.leashTag.getUuid("UUID");
                Entity _snowman2 = ((ServerWorld)this.world).getEntity(uUID);
                if (_snowman2 != null) {
                    this.attachLeash(_snowman2, true);
                    return;
                }
            } else if (this.leashTag.contains("X", 99) && this.leashTag.contains("Y", 99) && this.leashTag.contains("Z", 99)) {
                BlockPos _snowman3 = new BlockPos(this.leashTag.getInt("X"), this.leashTag.getInt("Y"), this.leashTag.getInt("Z"));
                this.attachLeash(LeashKnotEntity.getOrCreate(this.world, _snowman3), true);
                return;
            }
            if (this.age > 100) {
                this.dropItem(Items.LEAD);
                this.leashTag = null;
            }
        }
    }

    @Override
    public boolean equip(int slot, ItemStack item) {
        EquipmentSlot equipmentSlot;
        if (slot == 98) {
            equipmentSlot = EquipmentSlot.MAINHAND;
        } else if (slot == 99) {
            equipmentSlot = EquipmentSlot.OFFHAND;
        } else if (slot == 100 + EquipmentSlot.HEAD.getEntitySlotId()) {
            equipmentSlot = EquipmentSlot.HEAD;
        } else if (slot == 100 + EquipmentSlot.CHEST.getEntitySlotId()) {
            equipmentSlot = EquipmentSlot.CHEST;
        } else if (slot == 100 + EquipmentSlot.LEGS.getEntitySlotId()) {
            equipmentSlot = EquipmentSlot.LEGS;
        } else if (slot == 100 + EquipmentSlot.FEET.getEntitySlotId()) {
            equipmentSlot = EquipmentSlot.FEET;
        } else {
            return false;
        }
        if (item.isEmpty() || MobEntity.canEquipmentSlotContain(equipmentSlot, item) || equipmentSlot == EquipmentSlot.HEAD) {
            this.equipStack(equipmentSlot, item);
            return true;
        }
        return false;
    }

    @Override
    public boolean isLogicalSideForUpdatingMovement() {
        return this.canBeControlledByRider() && super.isLogicalSideForUpdatingMovement();
    }

    public static boolean canEquipmentSlotContain(EquipmentSlot slot, ItemStack item) {
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(item);
        return equipmentSlot == slot || equipmentSlot == EquipmentSlot.MAINHAND && slot == EquipmentSlot.OFFHAND || equipmentSlot == EquipmentSlot.OFFHAND && slot == EquipmentSlot.MAINHAND;
    }

    @Override
    public boolean canMoveVoluntarily() {
        return super.canMoveVoluntarily() && !this.isAiDisabled();
    }

    public void setAiDisabled(boolean aiDisabled) {
        byte by = this.dataTracker.get(MOB_FLAGS);
        this.dataTracker.set(MOB_FLAGS, aiDisabled ? (byte)(by | 1) : (byte)(by & 0xFFFFFFFE));
    }

    public void setLeftHanded(boolean leftHanded) {
        byte by = this.dataTracker.get(MOB_FLAGS);
        this.dataTracker.set(MOB_FLAGS, leftHanded ? (byte)(by | 2) : (byte)(by & 0xFFFFFFFD));
    }

    public void setAttacking(boolean attacking) {
        byte by = this.dataTracker.get(MOB_FLAGS);
        this.dataTracker.set(MOB_FLAGS, attacking ? (byte)(by | 4) : (byte)(by & 0xFFFFFFFB));
    }

    public boolean isAiDisabled() {
        return (this.dataTracker.get(MOB_FLAGS) & 1) != 0;
    }

    public boolean isLeftHanded() {
        return (this.dataTracker.get(MOB_FLAGS) & 2) != 0;
    }

    public boolean isAttacking() {
        return (this.dataTracker.get(MOB_FLAGS) & 4) != 0;
    }

    public void setBaby(boolean baby) {
    }

    @Override
    public Arm getMainArm() {
        return this.isLeftHanded() ? Arm.LEFT : Arm.RIGHT;
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        if (target.getType() == EntityType.PLAYER && ((PlayerEntity)target).abilities.invulnerable) {
            return false;
        }
        return super.canTarget(target);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl;
        float f = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        _snowman = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
        if (target instanceof LivingEntity) {
            f += EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity)target).getGroup());
            _snowman += (float)EnchantmentHelper.getKnockback(this);
        }
        if ((_snowman = EnchantmentHelper.getFireAspect(this)) > 0) {
            target.setOnFireFor(_snowman * 4);
        }
        if (bl = target.damage(DamageSource.mob(this), f)) {
            if (_snowman > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity)target).takeKnockback(_snowman * 0.5f, MathHelper.sin(this.yaw * ((float)Math.PI / 180)), -MathHelper.cos(this.yaw * ((float)Math.PI / 180)));
                this.setVelocity(this.getVelocity().multiply(0.6, 1.0, 0.6));
            }
            if (target instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity)target;
                this.disablePlayerShield(playerEntity, this.getMainHandStack(), playerEntity.isUsingItem() ? playerEntity.getActiveItem() : ItemStack.EMPTY);
            }
            this.dealDamage(this, target);
            this.onAttacking(target);
        }
        return bl;
    }

    private void disablePlayerShield(PlayerEntity player, ItemStack mobStack, ItemStack playerStack) {
        if (!mobStack.isEmpty() && !playerStack.isEmpty() && mobStack.getItem() instanceof AxeItem && playerStack.getItem() == Items.SHIELD) {
            float f = 0.25f + (float)EnchantmentHelper.getEfficiency(this) * 0.05f;
            if (this.random.nextFloat() < f) {
                player.getItemCooldownManager().set(Items.SHIELD, 100);
                this.world.sendEntityStatus(player, (byte)30);
            }
        }
    }

    protected boolean isAffectedByDaylight() {
        if (this.world.isDay() && !this.world.isClient) {
            float f = this.getBrightnessAtEyes();
            BlockPos blockPos = _snowman = this.getVehicle() instanceof BoatEntity ? new BlockPos(this.getX(), Math.round(this.getY()), this.getZ()).up() : new BlockPos(this.getX(), Math.round(this.getY()), this.getZ());
            if (f > 0.5f && this.random.nextFloat() * 30.0f < (f - 0.4f) * 2.0f && this.world.isSkyVisible(_snowman)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void swimUpward(Tag<Fluid> fluid) {
        if (this.getNavigation().canSwim()) {
            super.swimUpward(fluid);
        } else {
            this.setVelocity(this.getVelocity().add(0.0, 0.3, 0.0));
        }
    }

    @Override
    protected void method_30076() {
        super.method_30076();
        this.detachLeash(true, false);
    }
}

