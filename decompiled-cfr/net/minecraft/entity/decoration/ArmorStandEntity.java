/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.decoration;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ArmorStandEntity
extends LivingEntity {
    private static final EulerAngle DEFAULT_HEAD_ROTATION = new EulerAngle(0.0f, 0.0f, 0.0f);
    private static final EulerAngle DEFAULT_BODY_ROTATION = new EulerAngle(0.0f, 0.0f, 0.0f);
    private static final EulerAngle DEFAULT_LEFT_ARM_ROTATION = new EulerAngle(-10.0f, 0.0f, -10.0f);
    private static final EulerAngle DEFAULT_RIGHT_ARM_ROTATION = new EulerAngle(-15.0f, 0.0f, 10.0f);
    private static final EulerAngle DEFAULT_LEFT_LEG_ROTATION = new EulerAngle(-1.0f, 0.0f, -1.0f);
    private static final EulerAngle DEFAULT_RIGHT_LEG_ROTATION = new EulerAngle(1.0f, 0.0f, 1.0f);
    private static final EntityDimensions field_26745 = new EntityDimensions(0.0f, 0.0f, true);
    private static final EntityDimensions field_26746 = EntityType.ARMOR_STAND.getDimensions().scaled(0.5f);
    public static final TrackedData<Byte> ARMOR_STAND_FLAGS = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final TrackedData<EulerAngle> TRACKER_HEAD_ROTATION = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> TRACKER_BODY_ROTATION = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> TRACKER_LEFT_ARM_ROTATION = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> TRACKER_RIGHT_ARM_ROTATION = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> TRACKER_LEFT_LEG_ROTATION = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> TRACKER_RIGHT_LEG_ROTATION = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION);
    private static final Predicate<Entity> RIDEABLE_MINECART_PREDICATE = entity -> entity instanceof AbstractMinecartEntity && ((AbstractMinecartEntity)entity).getMinecartType() == AbstractMinecartEntity.Type.RIDEABLE;
    private final DefaultedList<ItemStack> heldItems = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> armorItems = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private boolean invisible;
    public long lastHitTime;
    private int disabledSlots;
    private EulerAngle headRotation = DEFAULT_HEAD_ROTATION;
    private EulerAngle bodyRotation = DEFAULT_BODY_ROTATION;
    private EulerAngle leftArmRotation = DEFAULT_LEFT_ARM_ROTATION;
    private EulerAngle rightArmRotation = DEFAULT_RIGHT_ARM_ROTATION;
    private EulerAngle leftLegRotation = DEFAULT_LEFT_LEG_ROTATION;
    private EulerAngle rightLegRotation = DEFAULT_RIGHT_LEG_ROTATION;

    public ArmorStandEntity(EntityType<? extends ArmorStandEntity> entityType, World world) {
        super((EntityType<? extends LivingEntity>)entityType, world);
        this.stepHeight = 0.0f;
    }

    public ArmorStandEntity(World world, double x, double y, double z) {
        this((EntityType<? extends ArmorStandEntity>)EntityType.ARMOR_STAND, world);
        this.updatePosition(x, y, z);
    }

    @Override
    public void calculateDimensions() {
        double d = this.getX();
        _snowman = this.getY();
        _snowman = this.getZ();
        super.calculateDimensions();
        this.updatePosition(d, _snowman, _snowman);
    }

    private boolean canClip() {
        return !this.isMarker() && !this.hasNoGravity();
    }

    @Override
    public boolean canMoveVoluntarily() {
        return super.canMoveVoluntarily() && this.canClip();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ARMOR_STAND_FLAGS, (byte)0);
        this.dataTracker.startTracking(TRACKER_HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
        this.dataTracker.startTracking(TRACKER_BODY_ROTATION, DEFAULT_BODY_ROTATION);
        this.dataTracker.startTracking(TRACKER_LEFT_ARM_ROTATION, DEFAULT_LEFT_ARM_ROTATION);
        this.dataTracker.startTracking(TRACKER_RIGHT_ARM_ROTATION, DEFAULT_RIGHT_ARM_ROTATION);
        this.dataTracker.startTracking(TRACKER_LEFT_LEG_ROTATION, DEFAULT_LEFT_LEG_ROTATION);
        this.dataTracker.startTracking(TRACKER_RIGHT_LEG_ROTATION, DEFAULT_RIGHT_LEG_ROTATION);
    }

    @Override
    public Iterable<ItemStack> getItemsHand() {
        return this.heldItems;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return this.armorItems;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        switch (slot.getType()) {
            case HAND: {
                return this.heldItems.get(slot.getEntitySlotId());
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
                this.onEquipStack(stack);
                this.heldItems.set(slot.getEntitySlotId(), stack);
                break;
            }
            case ARMOR: {
                this.onEquipStack(stack);
                this.armorItems.set(slot.getEntitySlotId(), stack);
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
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(stack);
        return this.getEquippedStack(equipmentSlot).isEmpty() && !this.isSlotDisabled(equipmentSlot);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
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
        for (ItemStack itemStack : this.heldItems) {
            CompoundTag compoundTag = new CompoundTag();
            if (!itemStack.isEmpty()) {
                itemStack.toTag(compoundTag);
            }
            listTag.add(compoundTag);
        }
        tag.put("HandItems", listTag);
        tag.putBoolean("Invisible", this.isInvisible());
        tag.putBoolean("Small", this.isSmall());
        tag.putBoolean("ShowArms", this.shouldShowArms());
        tag.putInt("DisabledSlots", this.disabledSlots);
        tag.putBoolean("NoBasePlate", this.shouldHideBasePlate());
        if (this.isMarker()) {
            tag.putBoolean("Marker", this.isMarker());
        }
        tag.put("Pose", this.serializePose());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        int n;
        Tag tag2;
        super.readCustomDataFromTag(tag);
        if (tag.contains("ArmorItems", 9)) {
            tag2 = tag.getList("ArmorItems", 10);
            for (n = 0; n < this.armorItems.size(); ++n) {
                this.armorItems.set(n, ItemStack.fromTag(tag2.getCompound(n)));
            }
        }
        if (tag.contains("HandItems", 9)) {
            tag2 = tag.getList("HandItems", 10);
            for (n = 0; n < this.heldItems.size(); ++n) {
                this.heldItems.set(n, ItemStack.fromTag(tag2.getCompound(n)));
            }
        }
        this.setInvisible(tag.getBoolean("Invisible"));
        this.setSmall(tag.getBoolean("Small"));
        this.setShowArms(tag.getBoolean("ShowArms"));
        this.disabledSlots = tag.getInt("DisabledSlots");
        this.setHideBasePlate(tag.getBoolean("NoBasePlate"));
        this.setMarker(tag.getBoolean("Marker"));
        this.noClip = !this.canClip();
        tag2 = tag.getCompound("Pose");
        this.deserializePose((CompoundTag)tag2);
    }

    private void deserializePose(CompoundTag compoundTag) {
        ListTag listTag = compoundTag.getList("Head", 5);
        this.setHeadRotation(listTag.isEmpty() ? DEFAULT_HEAD_ROTATION : new EulerAngle(listTag));
        _snowman = compoundTag.getList("Body", 5);
        this.setBodyRotation(_snowman.isEmpty() ? DEFAULT_BODY_ROTATION : new EulerAngle(_snowman));
        _snowman = compoundTag.getList("LeftArm", 5);
        this.setLeftArmRotation(_snowman.isEmpty() ? DEFAULT_LEFT_ARM_ROTATION : new EulerAngle(_snowman));
        _snowman = compoundTag.getList("RightArm", 5);
        this.setRightArmRotation(_snowman.isEmpty() ? DEFAULT_RIGHT_ARM_ROTATION : new EulerAngle(_snowman));
        _snowman = compoundTag.getList("LeftLeg", 5);
        this.setLeftLegRotation(_snowman.isEmpty() ? DEFAULT_LEFT_LEG_ROTATION : new EulerAngle(_snowman));
        _snowman = compoundTag.getList("RightLeg", 5);
        this.setRightLegRotation(_snowman.isEmpty() ? DEFAULT_RIGHT_LEG_ROTATION : new EulerAngle(_snowman));
    }

    private CompoundTag serializePose() {
        CompoundTag compoundTag = new CompoundTag();
        if (!DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
            compoundTag.put("Head", this.headRotation.serialize());
        }
        if (!DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
            compoundTag.put("Body", this.bodyRotation.serialize());
        }
        if (!DEFAULT_LEFT_ARM_ROTATION.equals(this.leftArmRotation)) {
            compoundTag.put("LeftArm", this.leftArmRotation.serialize());
        }
        if (!DEFAULT_RIGHT_ARM_ROTATION.equals(this.rightArmRotation)) {
            compoundTag.put("RightArm", this.rightArmRotation.serialize());
        }
        if (!DEFAULT_LEFT_LEG_ROTATION.equals(this.leftLegRotation)) {
            compoundTag.put("LeftLeg", this.leftLegRotation.serialize());
        }
        if (!DEFAULT_RIGHT_LEG_ROTATION.equals(this.rightLegRotation)) {
            compoundTag.put("RightLeg", this.rightLegRotation.serialize());
        }
        return compoundTag;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) {
    }

    @Override
    protected void tickCramming() {
        List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox(), RIDEABLE_MINECART_PREDICATE);
        for (int i = 0; i < list.size(); ++i) {
            Entity entity = list.get(i);
            if (!(this.squaredDistanceTo(entity) <= 0.2)) continue;
            entity.pushAwayFrom(this);
        }
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isMarker() || itemStack.getItem() == Items.NAME_TAG) {
            return ActionResult.PASS;
        }
        if (player.isSpectator()) {
            return ActionResult.SUCCESS;
        }
        if (player.world.isClient) {
            return ActionResult.CONSUME;
        }
        EquipmentSlot _snowman2 = MobEntity.getPreferredEquipmentSlot(itemStack);
        if (itemStack.isEmpty()) {
            EquipmentSlot equipmentSlot = this.slotFromPosition(hitPos);
            EquipmentSlot equipmentSlot2 = _snowman = this.isSlotDisabled(equipmentSlot) ? _snowman2 : equipmentSlot;
            if (this.hasStackEquipped(_snowman) && this.equip(player, _snowman, itemStack, hand)) {
                return ActionResult.SUCCESS;
            }
        } else {
            if (this.isSlotDisabled(_snowman2)) {
                return ActionResult.FAIL;
            }
            if (_snowman2.getType() == EquipmentSlot.Type.HAND && !this.shouldShowArms()) {
                return ActionResult.FAIL;
            }
            if (this.equip(player, _snowman2, itemStack, hand)) {
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private EquipmentSlot slotFromPosition(Vec3d vec3d) {
        EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
        boolean _snowman2 = this.isSmall();
        double _snowman3 = _snowman2 ? vec3d.y * 2.0 : vec3d.y;
        _snowman = EquipmentSlot.FEET;
        if (_snowman3 >= 0.1) {
            double d = _snowman2 ? 0.8 : 0.45;
            if (_snowman3 < 0.1 + d && this.hasStackEquipped(_snowman)) {
                return EquipmentSlot.FEET;
            }
        }
        double d = _snowman2 ? 0.3 : 0.0;
        if (_snowman3 >= 0.9 + d) {
            double d2 = _snowman2 ? 1.0 : 0.7;
            if (_snowman3 < 0.9 + d2 && this.hasStackEquipped(EquipmentSlot.CHEST)) {
                return EquipmentSlot.CHEST;
            }
        }
        if (_snowman3 >= 0.4) {
            double d3 = _snowman2 ? 1.0 : 0.8;
            if (_snowman3 < 0.4 + d3 && this.hasStackEquipped(EquipmentSlot.LEGS)) {
                return EquipmentSlot.LEGS;
            }
        }
        if (_snowman3 >= 1.6 && this.hasStackEquipped(EquipmentSlot.HEAD)) {
            return EquipmentSlot.HEAD;
        }
        if (this.hasStackEquipped(EquipmentSlot.MAINHAND)) return equipmentSlot;
        if (!this.hasStackEquipped(EquipmentSlot.OFFHAND)) return equipmentSlot;
        return EquipmentSlot.OFFHAND;
    }

    private boolean isSlotDisabled(EquipmentSlot slot) {
        return (this.disabledSlots & 1 << slot.getArmorStandSlotId()) != 0 || slot.getType() == EquipmentSlot.Type.HAND && !this.shouldShowArms();
    }

    private boolean equip(PlayerEntity player, EquipmentSlot slot, ItemStack stack, Hand hand) {
        ItemStack itemStack = this.getEquippedStack(slot);
        if (!itemStack.isEmpty() && (this.disabledSlots & 1 << slot.getArmorStandSlotId() + 8) != 0) {
            return false;
        }
        if (itemStack.isEmpty() && (this.disabledSlots & 1 << slot.getArmorStandSlotId() + 16) != 0) {
            return false;
        }
        if (player.abilities.creativeMode && itemStack.isEmpty() && !stack.isEmpty()) {
            _snowman = stack.copy();
            _snowman.setCount(1);
            this.equipStack(slot, _snowman);
            return true;
        }
        if (!stack.isEmpty() && stack.getCount() > 1) {
            if (!itemStack.isEmpty()) {
                return false;
            }
            _snowman = stack.copy();
            _snowman.setCount(1);
            this.equipStack(slot, _snowman);
            stack.decrement(1);
            return true;
        }
        this.equipStack(slot, stack);
        player.setStackInHand(hand, itemStack);
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.world.isClient || this.removed) {
            return false;
        }
        if (DamageSource.OUT_OF_WORLD.equals(source)) {
            this.remove();
            return false;
        }
        if (this.isInvulnerableTo(source) || this.invisible || this.isMarker()) {
            return false;
        }
        if (source.isExplosive()) {
            this.onBreak(source);
            this.remove();
            return false;
        }
        if (DamageSource.IN_FIRE.equals(source)) {
            if (this.isOnFire()) {
                this.updateHealth(source, 0.15f);
            } else {
                this.setOnFireFor(5);
            }
            return false;
        }
        if (DamageSource.ON_FIRE.equals(source) && this.getHealth() > 0.5f) {
            this.updateHealth(source, 4.0f);
            return false;
        }
        boolean bl = source.getSource() instanceof PersistentProjectileEntity;
        _snowman = bl && ((PersistentProjectileEntity)source.getSource()).getPierceLevel() > 0;
        _snowman = "player".equals(source.getName());
        if (!_snowman && !bl) {
            return false;
        }
        if (source.getAttacker() instanceof PlayerEntity && !((PlayerEntity)source.getAttacker()).abilities.allowModifyWorld) {
            return false;
        }
        if (source.isSourceCreativePlayer()) {
            this.playBreakSound();
            this.spawnBreakParticles();
            this.remove();
            return _snowman;
        }
        long _snowman2 = this.world.getTime();
        if (_snowman2 - this.lastHitTime <= 5L || bl) {
            this.breakAndDropItem(source);
            this.spawnBreakParticles();
            this.remove();
        } else {
            this.world.sendEntityStatus(this, (byte)32);
            this.lastHitTime = _snowman2;
        }
        return true;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 32) {
            if (this.world.isClient) {
                this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_HIT, this.getSoundCategory(), 0.3f, 1.0f, false);
                this.lastHitTime = this.world.getTime();
            }
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = this.getBoundingBox().getAverageSideLength() * 4.0;
        if (Double.isNaN(d) || d == 0.0) {
            d = 4.0;
        }
        return distance < (d *= 64.0) * d;
    }

    private void spawnBreakParticles() {
        if (this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()), this.getX(), this.getBodyY(0.6666666666666666), this.getZ(), 10, this.getWidth() / 4.0f, this.getHeight() / 4.0f, this.getWidth() / 4.0f, 0.05);
        }
    }

    private void updateHealth(DamageSource damageSource, float amount) {
        float f = this.getHealth();
        if ((f -= amount) <= 0.5f) {
            this.onBreak(damageSource);
            this.remove();
        } else {
            this.setHealth(f);
        }
    }

    private void breakAndDropItem(DamageSource damageSource) {
        Block.dropStack(this.world, this.getBlockPos(), new ItemStack(Items.ARMOR_STAND));
        this.onBreak(damageSource);
    }

    private void onBreak(DamageSource damageSource) {
        ItemStack itemStack;
        int n;
        this.playBreakSound();
        this.drop(damageSource);
        for (n = 0; n < this.heldItems.size(); ++n) {
            itemStack = this.heldItems.get(n);
            if (itemStack.isEmpty()) continue;
            Block.dropStack(this.world, this.getBlockPos().up(), itemStack);
            this.heldItems.set(n, ItemStack.EMPTY);
        }
        for (n = 0; n < this.armorItems.size(); ++n) {
            itemStack = this.armorItems.get(n);
            if (itemStack.isEmpty()) continue;
            Block.dropStack(this.world, this.getBlockPos().up(), itemStack);
            this.armorItems.set(n, ItemStack.EMPTY);
        }
    }

    private void playBreakSound() {
        this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, this.getSoundCategory(), 1.0f, 1.0f);
    }

    @Override
    protected float turnHead(float bodyRotation, float headRotation) {
        this.prevBodyYaw = this.prevYaw;
        this.bodyYaw = this.yaw;
        return 0.0f;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * (this.isBaby() ? 0.5f : 0.9f);
    }

    @Override
    public double getHeightOffset() {
        return this.isMarker() ? 0.0 : (double)0.1f;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (!this.canClip()) {
            return;
        }
        super.travel(movementInput);
    }

    @Override
    public void setYaw(float yaw) {
        this.prevBodyYaw = this.prevYaw = yaw;
        this.prevHeadYaw = this.headYaw = yaw;
    }

    @Override
    public void setHeadYaw(float headYaw) {
        this.prevBodyYaw = this.prevYaw = headYaw;
        this.prevHeadYaw = this.headYaw = headYaw;
    }

    @Override
    public void tick() {
        super.tick();
        EulerAngle eulerAngle = this.dataTracker.get(TRACKER_HEAD_ROTATION);
        if (!this.headRotation.equals(eulerAngle)) {
            this.setHeadRotation(eulerAngle);
        }
        if (!this.bodyRotation.equals(_snowman = this.dataTracker.get(TRACKER_BODY_ROTATION))) {
            this.setBodyRotation(_snowman);
        }
        if (!this.leftArmRotation.equals(_snowman = this.dataTracker.get(TRACKER_LEFT_ARM_ROTATION))) {
            this.setLeftArmRotation(_snowman);
        }
        if (!this.rightArmRotation.equals(_snowman = this.dataTracker.get(TRACKER_RIGHT_ARM_ROTATION))) {
            this.setRightArmRotation(_snowman);
        }
        if (!this.leftLegRotation.equals(_snowman = this.dataTracker.get(TRACKER_LEFT_LEG_ROTATION))) {
            this.setLeftLegRotation(_snowman);
        }
        if (!this.rightLegRotation.equals(_snowman = this.dataTracker.get(TRACKER_RIGHT_LEG_ROTATION))) {
            this.setRightLegRotation(_snowman);
        }
    }

    @Override
    protected void updatePotionVisibility() {
        this.setInvisible(this.invisible);
    }

    @Override
    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
        super.setInvisible(invisible);
    }

    @Override
    public boolean isBaby() {
        return this.isSmall();
    }

    @Override
    public void kill() {
        this.remove();
    }

    @Override
    public boolean isImmuneToExplosion() {
        return this.isInvisible();
    }

    @Override
    public PistonBehavior getPistonBehavior() {
        if (this.isMarker()) {
            return PistonBehavior.IGNORE;
        }
        return super.getPistonBehavior();
    }

    private void setSmall(boolean small) {
        this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), 1, small));
    }

    public boolean isSmall() {
        return (this.dataTracker.get(ARMOR_STAND_FLAGS) & 1) != 0;
    }

    private void setShowArms(boolean showArms) {
        this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), 4, showArms));
    }

    public boolean shouldShowArms() {
        return (this.dataTracker.get(ARMOR_STAND_FLAGS) & 4) != 0;
    }

    private void setHideBasePlate(boolean hideBasePlate) {
        this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), 8, hideBasePlate));
    }

    public boolean shouldHideBasePlate() {
        return (this.dataTracker.get(ARMOR_STAND_FLAGS) & 8) != 0;
    }

    private void setMarker(boolean marker) {
        this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), 16, marker));
    }

    public boolean isMarker() {
        return (this.dataTracker.get(ARMOR_STAND_FLAGS) & 0x10) != 0;
    }

    private byte setBitField(byte value, int bitField, boolean set) {
        value = set ? (byte)(value | bitField) : (byte)(value & ~bitField);
        return value;
    }

    public void setHeadRotation(EulerAngle eulerAngle) {
        this.headRotation = eulerAngle;
        this.dataTracker.set(TRACKER_HEAD_ROTATION, eulerAngle);
    }

    public void setBodyRotation(EulerAngle eulerAngle) {
        this.bodyRotation = eulerAngle;
        this.dataTracker.set(TRACKER_BODY_ROTATION, eulerAngle);
    }

    public void setLeftArmRotation(EulerAngle eulerAngle) {
        this.leftArmRotation = eulerAngle;
        this.dataTracker.set(TRACKER_LEFT_ARM_ROTATION, eulerAngle);
    }

    public void setRightArmRotation(EulerAngle eulerAngle) {
        this.rightArmRotation = eulerAngle;
        this.dataTracker.set(TRACKER_RIGHT_ARM_ROTATION, eulerAngle);
    }

    public void setLeftLegRotation(EulerAngle eulerAngle) {
        this.leftLegRotation = eulerAngle;
        this.dataTracker.set(TRACKER_LEFT_LEG_ROTATION, eulerAngle);
    }

    public void setRightLegRotation(EulerAngle eulerAngle) {
        this.rightLegRotation = eulerAngle;
        this.dataTracker.set(TRACKER_RIGHT_LEG_ROTATION, eulerAngle);
    }

    public EulerAngle getHeadRotation() {
        return this.headRotation;
    }

    public EulerAngle getBodyRotation() {
        return this.bodyRotation;
    }

    public EulerAngle getLeftArmRotation() {
        return this.leftArmRotation;
    }

    public EulerAngle getRightArmRotation() {
        return this.rightArmRotation;
    }

    public EulerAngle getLeftLegRotation() {
        return this.leftLegRotation;
    }

    public EulerAngle getRightLegRotation() {
        return this.rightLegRotation;
    }

    @Override
    public boolean collides() {
        return super.collides() && !this.isMarker();
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        return attacker instanceof PlayerEntity && !this.world.canPlayerModifyAt((PlayerEntity)attacker, this.getBlockPos());
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    protected SoundEvent getFallSound(int distance) {
        return SoundEvents.ENTITY_ARMOR_STAND_FALL;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ARMOR_STAND_HIT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ARMOR_STAND_BREAK;
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
    }

    @Override
    public boolean isAffectedBySplashPotions() {
        return false;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (ARMOR_STAND_FLAGS.equals(data)) {
            this.calculateDimensions();
            this.inanimate = !this.isMarker();
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public boolean isMobOrPlayer() {
        return false;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return this.method_31168(this.isMarker());
    }

    private EntityDimensions method_31168(boolean bl) {
        if (bl) {
            return field_26745;
        }
        return this.isBaby() ? field_26746 : this.getType().getDimensions();
    }

    @Override
    public Vec3d method_31166(float f2) {
        float f2;
        if (this.isMarker()) {
            Box box = this.method_31168(false).method_30757(this.getPos());
            BlockPos _snowman2 = this.getBlockPos();
            int _snowman3 = Integer.MIN_VALUE;
            for (BlockPos blockPos : BlockPos.iterate(new BlockPos(box.minX, box.minY, box.minZ), new BlockPos(box.maxX, box.maxY, box.maxZ))) {
                int n = Math.max(this.world.getLightLevel(LightType.BLOCK, blockPos), this.world.getLightLevel(LightType.SKY, blockPos));
                if (n == 15) {
                    return Vec3d.ofCenter(blockPos);
                }
                if (n <= _snowman3) continue;
                _snowman3 = n;
                _snowman2 = blockPos.toImmutable();
            }
            return Vec3d.ofCenter(_snowman2);
        }
        return super.method_31166(f2);
    }
}

