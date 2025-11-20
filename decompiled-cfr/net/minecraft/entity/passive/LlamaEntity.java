/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.FormCaravanGoal;
import net.minecraft.entity.ai.goal.HorseBondWithPlayerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class LlamaEntity
extends AbstractDonkeyEntity
implements RangedAttackMob {
    private static final Ingredient TAMING_INGREDIENT = Ingredient.ofItems(Items.WHEAT, Blocks.HAY_BLOCK.asItem());
    private static final TrackedData<Integer> STRENGTH = DataTracker.registerData(LlamaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> CARPET_COLOR = DataTracker.registerData(LlamaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(LlamaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private boolean spit;
    @Nullable
    private LlamaEntity following;
    @Nullable
    private LlamaEntity follower;

    public LlamaEntity(EntityType<? extends LlamaEntity> entityType, World world) {
        super((EntityType<? extends AbstractDonkeyEntity>)entityType, world);
    }

    public boolean isTrader() {
        return false;
    }

    private void setStrength(int strength) {
        this.dataTracker.set(STRENGTH, Math.max(1, Math.min(5, strength)));
    }

    private void initializeStrength() {
        int n = this.random.nextFloat() < 0.04f ? 5 : 3;
        this.setStrength(1 + this.random.nextInt(n));
    }

    public int getStrength() {
        return this.dataTracker.get(STRENGTH);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("Variant", this.getVariant());
        tag.putInt("Strength", this.getStrength());
        if (!this.items.getStack(1).isEmpty()) {
            tag.put("DecorItem", this.items.getStack(1).toTag(new CompoundTag()));
        }
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        this.setStrength(tag.getInt("Strength"));
        super.readCustomDataFromTag(tag);
        this.setVariant(tag.getInt("Variant"));
        if (tag.contains("DecorItem", 10)) {
            this.items.setStack(1, ItemStack.fromTag(tag.getCompound("DecorItem")));
        }
        this.updateSaddle();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new HorseBondWithPlayerGoal(this, 1.2));
        this.goalSelector.add(2, new FormCaravanGoal(this, 2.1f));
        this.goalSelector.add(3, new ProjectileAttackGoal(this, 1.25, 40, 20.0f));
        this.goalSelector.add(3, new EscapeDangerGoal(this, 1.2));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.0));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.7));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new SpitRevengeGoal(this));
        this.targetSelector.add(2, new ChaseWolvesGoal(this));
    }

    public static DefaultAttributeContainer.Builder createLlamaAttributes() {
        return LlamaEntity.createAbstractDonkeyAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(STRENGTH, 0);
        this.dataTracker.startTracking(CARPET_COLOR, -1);
        this.dataTracker.startTracking(VARIANT, 0);
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataTracker.get(VARIANT), 0, 3);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    protected int getInventorySize() {
        if (this.hasChest()) {
            return 2 + 3 * this.getInventoryColumns();
        }
        return super.getInventorySize();
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        if (!this.hasPassenger(passenger)) {
            return;
        }
        float f = MathHelper.cos(this.bodyYaw * ((float)Math.PI / 180));
        _snowman = MathHelper.sin(this.bodyYaw * ((float)Math.PI / 180));
        _snowman = 0.3f;
        passenger.updatePosition(this.getX() + (double)(0.3f * _snowman), this.getY() + this.getMountedHeightOffset() + passenger.getHeightOffset(), this.getZ() - (double)(0.3f * f));
    }

    @Override
    public double getMountedHeightOffset() {
        return (double)this.getHeight() * 0.67;
    }

    @Override
    public boolean canBeControlledByRider() {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return TAMING_INGREDIENT.test(stack);
    }

    @Override
    protected boolean receiveFood(PlayerEntity player, ItemStack item) {
        int n = 0;
        _snowman = 0;
        float _snowman2 = 0.0f;
        boolean _snowman3 = false;
        Item _snowman4 = item.getItem();
        if (_snowman4 == Items.WHEAT) {
            n = 10;
            _snowman = 3;
            _snowman2 = 2.0f;
        } else if (_snowman4 == Blocks.HAY_BLOCK.asItem()) {
            n = 90;
            _snowman = 6;
            _snowman2 = 10.0f;
            if (this.isTame() && this.getBreedingAge() == 0 && this.canEat()) {
                _snowman3 = true;
                this.lovePlayer(player);
            }
        }
        if (this.getHealth() < this.getMaxHealth() && _snowman2 > 0.0f) {
            this.heal(_snowman2);
            _snowman3 = true;
        }
        if (this.isBaby() && n > 0) {
            this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
            if (!this.world.isClient) {
                this.growUp(n);
            }
            _snowman3 = true;
        }
        if (_snowman > 0 && (_snowman3 || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
            _snowman3 = true;
            if (!this.world.isClient) {
                this.addTemper(_snowman);
            }
        }
        if (_snowman3 && !this.isSilent() && (_snowman = this.getEatSound()) != null) {
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), this.getEatSound(), this.getSoundCategory(), 1.0f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f);
        }
        return _snowman3;
    }

    @Override
    protected boolean isImmobile() {
        return this.isDead() || this.isEatingGrass();
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        int n;
        this.initializeStrength();
        if (entityData instanceof LlamaData) {
            n = ((LlamaData)entityData).variant;
        } else {
            n = this.random.nextInt(4);
            entityData = new LlamaData(n);
        }
        this.setVariant(n);
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_LLAMA_ANGRY;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_LLAMA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_LLAMA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_LLAMA_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getEatSound() {
        return SoundEvents.ENTITY_LLAMA_EAT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15f, 1.0f);
    }

    @Override
    protected void playAddChestSound() {
        this.playSound(SoundEvents.ENTITY_LLAMA_CHEST, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
    }

    @Override
    public void playAngrySound() {
        SoundEvent soundEvent = this.getAngrySound();
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Override
    public int getInventoryColumns() {
        return this.getStrength();
    }

    @Override
    public boolean hasArmorSlot() {
        return true;
    }

    @Override
    public boolean hasArmorInSlot() {
        return !this.items.getStack(1).isEmpty();
    }

    @Override
    public boolean isHorseArmor(ItemStack item) {
        Item item2 = item.getItem();
        return ItemTags.CARPETS.contains(item2);
    }

    @Override
    public boolean canBeSaddled() {
        return false;
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
        DyeColor dyeColor = this.getCarpetColor();
        super.onInventoryChanged(sender);
        _snowman = this.getCarpetColor();
        if (this.age > 20 && _snowman != null && _snowman != dyeColor) {
            this.playSound(SoundEvents.ENTITY_LLAMA_SWAG, 0.5f, 1.0f);
        }
    }

    @Override
    protected void updateSaddle() {
        if (this.world.isClient) {
            return;
        }
        super.updateSaddle();
        this.setCarpetColor(LlamaEntity.getColorFromCarpet(this.items.getStack(1)));
    }

    private void setCarpetColor(@Nullable DyeColor color) {
        this.dataTracker.set(CARPET_COLOR, color == null ? -1 : color.getId());
    }

    @Nullable
    private static DyeColor getColorFromCarpet(ItemStack color) {
        Block block = Block.getBlockFromItem(color.getItem());
        if (block instanceof CarpetBlock) {
            return ((CarpetBlock)block).getColor();
        }
        return null;
    }

    @Nullable
    public DyeColor getCarpetColor() {
        int n = this.dataTracker.get(CARPET_COLOR);
        return n == -1 ? null : DyeColor.byId(n);
    }

    @Override
    public int getMaxTemper() {
        return 30;
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        return other != this && other instanceof LlamaEntity && this.canBreed() && ((LlamaEntity)other).canBreed();
    }

    @Override
    public LlamaEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        LlamaEntity llamaEntity = this.createChild();
        this.setChildAttributes(passiveEntity, llamaEntity);
        _snowman = (LlamaEntity)passiveEntity;
        int _snowman2 = this.random.nextInt(Math.max(this.getStrength(), _snowman.getStrength())) + 1;
        if (this.random.nextFloat() < 0.03f) {
            ++_snowman2;
        }
        llamaEntity.setStrength(_snowman2);
        llamaEntity.setVariant(this.random.nextBoolean() ? this.getVariant() : _snowman.getVariant());
        return llamaEntity;
    }

    protected LlamaEntity createChild() {
        return EntityType.LLAMA.create(this.world);
    }

    private void spitAt(LivingEntity target) {
        LlamaSpitEntity llamaSpitEntity = new LlamaSpitEntity(this.world, this);
        double _snowman2 = target.getX() - this.getX();
        double _snowman3 = target.getBodyY(0.3333333333333333) - llamaSpitEntity.getY();
        double _snowman4 = target.getZ() - this.getZ();
        float _snowman5 = MathHelper.sqrt(_snowman2 * _snowman2 + _snowman4 * _snowman4) * 0.2f;
        llamaSpitEntity.setVelocity(_snowman2, _snowman3 + (double)_snowman5, _snowman4, 1.5f, 10.0f);
        if (!this.isSilent()) {
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f);
        }
        this.world.spawnEntity(llamaSpitEntity);
        this.spit = true;
    }

    private void setSpit(boolean spit) {
        this.spit = spit;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        int n = this.computeFallDamage(fallDistance, damageMultiplier);
        if (n <= 0) {
            return false;
        }
        if (fallDistance >= 6.0f) {
            this.damage(DamageSource.FALL, n);
            if (this.hasPassengers()) {
                for (Entity entity : this.getPassengersDeep()) {
                    entity.damage(DamageSource.FALL, n);
                }
            }
        }
        this.playBlockFallSound();
        return true;
    }

    public void stopFollowing() {
        if (this.following != null) {
            this.following.follower = null;
        }
        this.following = null;
    }

    public void follow(LlamaEntity llama) {
        this.following = llama;
        this.following.follower = this;
    }

    public boolean hasFollower() {
        return this.follower != null;
    }

    public boolean isFollowing() {
        return this.following != null;
    }

    @Nullable
    public LlamaEntity getFollowing() {
        return this.following;
    }

    @Override
    protected double getRunFromLeashSpeed() {
        return 2.0;
    }

    @Override
    protected void walkToParent() {
        if (!this.isFollowing() && this.isBaby()) {
            super.walkToParent();
        }
    }

    @Override
    public boolean eatsGrass() {
        return false;
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        this.spitAt(target);
    }

    @Override
    public Vec3d method_29919() {
        return new Vec3d(0.0, 0.75 * (double)this.getStandingEyeHeight(), (double)this.getWidth() * 0.5);
    }

    @Override
    public /* synthetic */ PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return this.createChild(world, entity);
    }

    static class ChaseWolvesGoal
    extends FollowTargetGoal<WolfEntity> {
        public ChaseWolvesGoal(LlamaEntity llama) {
            super(llama, WolfEntity.class, 16, false, true, livingEntity -> !((WolfEntity)livingEntity).isTamed());
        }

        @Override
        protected double getFollowRange() {
            return super.getFollowRange() * 0.25;
        }
    }

    static class SpitRevengeGoal
    extends RevengeGoal {
        public SpitRevengeGoal(LlamaEntity llama) {
            super(llama, new Class[0]);
        }

        @Override
        public boolean shouldContinue() {
            LlamaEntity llamaEntity;
            if (this.mob instanceof LlamaEntity && (llamaEntity = (LlamaEntity)this.mob).spit) {
                llamaEntity.setSpit(false);
                return false;
            }
            return super.shouldContinue();
        }
    }

    static class LlamaData
    extends PassiveEntity.PassiveData {
        public final int variant;

        private LlamaData(int variant) {
            super(true);
            this.variant = variant;
        }
    }
}

