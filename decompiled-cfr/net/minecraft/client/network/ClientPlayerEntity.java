/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.network;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.screen.ingame.CommandBlockScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.JigsawBlockScreen;
import net.minecraft.client.gui.screen.ingame.MinecartCommandBlockScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.gui.screen.ingame.StructureBlockScreen;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.sound.AmbientSoundLoops;
import net.minecraft.client.sound.AmbientSoundPlayer;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.BubbleColumnSoundPlayer;
import net.minecraft.client.sound.ElytraSoundInstance;
import net.minecraft.client.sound.MinecartInsideSoundInstance;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.RecipeBookDataC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Recipe;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.StatHandler;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.CommandBlockExecutor;

public class ClientPlayerEntity
extends AbstractClientPlayerEntity {
    public final ClientPlayNetworkHandler networkHandler;
    private final StatHandler statHandler;
    private final ClientRecipeBook recipeBook;
    private final List<ClientPlayerTickable> tickables = Lists.newArrayList();
    private int clientPermissionLevel = 0;
    private double lastX;
    private double lastBaseY;
    private double lastZ;
    private float lastYaw;
    private float lastPitch;
    private boolean lastOnGround;
    private boolean inSneakingPose;
    private boolean lastSneaking;
    private boolean lastSprinting;
    private int ticksSinceLastPositionPacketSent;
    private boolean healthInitialized;
    private String serverBrand;
    public Input input;
    protected final MinecraftClient client;
    protected int ticksLeftToDoubleTapSprint;
    public int ticksSinceSprintingChanged;
    public float renderYaw;
    public float renderPitch;
    public float lastRenderYaw;
    public float lastRenderPitch;
    private int field_3938;
    private float field_3922;
    public float nextNauseaStrength;
    public float lastNauseaStrength;
    private boolean usingItem;
    private Hand activeHand;
    private boolean riding;
    private boolean autoJumpEnabled = true;
    private int ticksToNextAutojump;
    private boolean field_3939;
    private int underwaterVisibilityTicks;
    private boolean showsDeathScreen = true;

    public ClientPlayerEntity(MinecraftClient client, ClientWorld world, ClientPlayNetworkHandler networkHandler, StatHandler stats, ClientRecipeBook recipeBook, boolean lastSneaking, boolean lastSprinting) {
        super(world, networkHandler.getProfile());
        this.client = client;
        this.networkHandler = networkHandler;
        this.statHandler = stats;
        this.recipeBook = recipeBook;
        this.lastSneaking = lastSneaking;
        this.lastSprinting = lastSprinting;
        this.tickables.add(new AmbientSoundPlayer(this, client.getSoundManager()));
        this.tickables.add(new BubbleColumnSoundPlayer(this));
        this.tickables.add(new BiomeEffectSoundPlayer(this, client.getSoundManager(), world.getBiomeAccess()));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void heal(float amount) {
    }

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        if (!super.startRiding(entity, force)) {
            return false;
        }
        if (entity instanceof AbstractMinecartEntity) {
            this.client.getSoundManager().play(new MinecartInsideSoundInstance(this, (AbstractMinecartEntity)entity));
        }
        if (entity instanceof BoatEntity) {
            this.prevYaw = entity.yaw;
            this.yaw = entity.yaw;
            this.setHeadYaw(entity.yaw);
        }
        return true;
    }

    @Override
    public void method_29239() {
        super.method_29239();
        this.riding = false;
    }

    @Override
    public float getPitch(float tickDelta) {
        return this.pitch;
    }

    @Override
    public float getYaw(float tickDelta) {
        if (this.hasVehicle()) {
            return super.getYaw(tickDelta);
        }
        return this.yaw;
    }

    @Override
    public void tick() {
        if (!this.world.isChunkLoaded(new BlockPos(this.getX(), 0.0, this.getZ()))) {
            return;
        }
        super.tick();
        if (this.hasVehicle()) {
            this.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookOnly(this.yaw, this.pitch, this.onGround));
            this.networkHandler.sendPacket(new PlayerInputC2SPacket(this.sidewaysSpeed, this.forwardSpeed, this.input.jumping, this.input.sneaking));
            Entity entity = this.getRootVehicle();
            if (entity != this && entity.isLogicalSideForUpdatingMovement()) {
                this.networkHandler.sendPacket(new VehicleMoveC2SPacket(entity));
            }
        } else {
            this.sendMovementPackets();
        }
        for (ClientPlayerTickable clientPlayerTickable : this.tickables) {
            clientPlayerTickable.tick();
        }
    }

    public float getMoodPercentage() {
        for (ClientPlayerTickable clientPlayerTickable : this.tickables) {
            if (!(clientPlayerTickable instanceof BiomeEffectSoundPlayer)) continue;
            return ((BiomeEffectSoundPlayer)clientPlayerTickable).getMoodPercentage();
        }
        return 0.0f;
    }

    private void sendMovementPackets() {
        boolean bl;
        boolean bl2 = this.isSprinting();
        if (bl2 != this.lastSprinting) {
            ClientCommandC2SPacket.Mode mode = bl2 ? ClientCommandC2SPacket.Mode.START_SPRINTING : ClientCommandC2SPacket.Mode.STOP_SPRINTING;
            this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, mode));
            this.lastSprinting = bl2;
        }
        if ((bl = this.isSneaking()) != this.lastSneaking) {
            ClientCommandC2SPacket.Mode mode = bl ? ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY : ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY;
            this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, mode));
            this.lastSneaking = bl;
        }
        if (this.isCamera()) {
            boolean bl3;
            double d = this.getX() - this.lastX;
            _snowman = this.getY() - this.lastBaseY;
            _snowman = this.getZ() - this.lastZ;
            _snowman = this.yaw - this.lastYaw;
            _snowman = this.pitch - this.lastPitch;
            ++this.ticksSinceLastPositionPacketSent;
            boolean _snowman2 = d * d + _snowman * _snowman + _snowman * _snowman > 9.0E-4 || this.ticksSinceLastPositionPacketSent >= 20;
            boolean bl4 = bl3 = _snowman != 0.0 || _snowman != 0.0;
            if (this.hasVehicle()) {
                Vec3d vec3d = this.getVelocity();
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Both(vec3d.x, -999.0, vec3d.z, this.yaw, this.pitch, this.onGround));
                _snowman2 = false;
            } else if (_snowman2 && bl3) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Both(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch, this.onGround));
            } else if (_snowman2) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(this.getX(), this.getY(), this.getZ(), this.onGround));
            } else if (bl3) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookOnly(this.yaw, this.pitch, this.onGround));
            } else if (this.lastOnGround != this.onGround) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket(this.onGround));
            }
            if (_snowman2) {
                this.lastX = this.getX();
                this.lastBaseY = this.getY();
                this.lastZ = this.getZ();
                this.ticksSinceLastPositionPacketSent = 0;
            }
            if (bl3) {
                this.lastYaw = this.yaw;
                this.lastPitch = this.pitch;
            }
            this.lastOnGround = this.onGround;
            this.autoJumpEnabled = this.client.options.autoJump;
        }
    }

    @Override
    public boolean dropSelectedItem(boolean dropEntireStack) {
        PlayerActionC2SPacket.Action action = dropEntireStack ? PlayerActionC2SPacket.Action.DROP_ALL_ITEMS : PlayerActionC2SPacket.Action.DROP_ITEM;
        this.networkHandler.sendPacket(new PlayerActionC2SPacket(action, BlockPos.ORIGIN, Direction.DOWN));
        return this.inventory.removeStack(this.inventory.selectedSlot, dropEntireStack && !this.inventory.getMainHandStack().isEmpty() ? this.inventory.getMainHandStack().getCount() : 1) != ItemStack.EMPTY;
    }

    public void sendChatMessage(String message) {
        this.networkHandler.sendPacket(new ChatMessageC2SPacket(message));
    }

    @Override
    public void swingHand(Hand hand) {
        super.swingHand(hand);
        this.networkHandler.sendPacket(new HandSwingC2SPacket(hand));
    }

    @Override
    public void requestRespawn() {
        this.networkHandler.sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
    }

    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return;
        }
        this.setHealth(this.getHealth() - amount);
    }

    @Override
    public void closeHandledScreen() {
        this.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(this.currentScreenHandler.syncId));
        this.closeScreen();
    }

    public void closeScreen() {
        this.inventory.setCursorStack(ItemStack.EMPTY);
        super.closeHandledScreen();
        this.client.openScreen(null);
    }

    public void updateHealth(float health) {
        if (this.healthInitialized) {
            float f = this.getHealth() - health;
            if (f <= 0.0f) {
                this.setHealth(health);
                if (f < 0.0f) {
                    this.timeUntilRegen = 10;
                }
            } else {
                this.lastDamageTaken = f;
                this.setHealth(this.getHealth());
                this.timeUntilRegen = 20;
                this.applyDamage(DamageSource.GENERIC, f);
                this.hurtTime = this.maxHurtTime = 10;
            }
        } else {
            this.setHealth(health);
            this.healthInitialized = true;
        }
    }

    @Override
    public void sendAbilitiesUpdate() {
        this.networkHandler.sendPacket(new UpdatePlayerAbilitiesC2SPacket(this.abilities));
    }

    @Override
    public boolean isMainPlayer() {
        return true;
    }

    @Override
    public boolean isHoldingOntoLadder() {
        return !this.abilities.flying && super.isHoldingOntoLadder();
    }

    @Override
    public boolean shouldSpawnSprintingParticles() {
        return !this.abilities.flying && super.shouldSpawnSprintingParticles();
    }

    @Override
    public boolean shouldDisplaySoulSpeedEffects() {
        return !this.abilities.flying && super.shouldDisplaySoulSpeedEffects();
    }

    protected void startRidingJump() {
        this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, ClientCommandC2SPacket.Mode.START_RIDING_JUMP, MathHelper.floor(this.method_3151() * 100.0f)));
    }

    public void openRidingInventory() {
        this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, ClientCommandC2SPacket.Mode.OPEN_INVENTORY));
    }

    public void setServerBrand(String serverBrand) {
        this.serverBrand = serverBrand;
    }

    public String getServerBrand() {
        return this.serverBrand;
    }

    public StatHandler getStatHandler() {
        return this.statHandler;
    }

    public ClientRecipeBook getRecipeBook() {
        return this.recipeBook;
    }

    public void onRecipeDisplayed(Recipe<?> recipe) {
        if (this.recipeBook.shouldDisplay(recipe)) {
            this.recipeBook.onRecipeDisplayed(recipe);
            this.networkHandler.sendPacket(new RecipeBookDataC2SPacket(recipe));
        }
    }

    @Override
    protected int getPermissionLevel() {
        return this.clientPermissionLevel;
    }

    public void setClientPermissionLevel(int clientPermissionLevel) {
        this.clientPermissionLevel = clientPermissionLevel;
    }

    @Override
    public void sendMessage(Text message, boolean actionBar) {
        if (actionBar) {
            this.client.inGameHud.setOverlayMessage(message, false);
        } else {
            this.client.inGameHud.getChatHud().addMessage(message);
        }
    }

    private void pushOutOfBlocks(double x, double d) {
        BlockPos blockPos = new BlockPos(x, this.getY(), d);
        if (!this.wouldCollideAt(blockPos)) {
            return;
        }
        double _snowman2 = x - (double)blockPos.getX();
        double _snowman3 = d - (double)blockPos.getZ();
        Direction _snowman4 = null;
        double _snowman5 = Double.MAX_VALUE;
        for (Direction direction : _snowman = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}) {
            double d2 = direction.getAxis().choose(_snowman2, 0.0, _snowman3);
            double d3 = _snowman = direction.getDirection() == Direction.AxisDirection.POSITIVE ? 1.0 - d2 : d2;
            if (!(_snowman < _snowman5) || this.wouldCollideAt(blockPos.offset(direction))) continue;
            _snowman5 = _snowman;
            _snowman4 = direction;
        }
        if (_snowman4 != null) {
            Vec3d vec3d = this.getVelocity();
            if (_snowman4.getAxis() == Direction.Axis.X) {
                this.setVelocity(0.1 * (double)_snowman4.getOffsetX(), vec3d.y, vec3d.z);
            } else {
                this.setVelocity(vec3d.x, vec3d.y, 0.1 * (double)_snowman4.getOffsetZ());
            }
        }
    }

    private boolean wouldCollideAt(BlockPos blockPos2) {
        Box box = this.getBoundingBox();
        _snowman = new Box(blockPos2.getX(), box.minY, blockPos2.getZ(), (double)blockPos2.getX() + 1.0, box.maxY, (double)blockPos2.getZ() + 1.0).contract(1.0E-7);
        return !this.world.isBlockSpaceEmpty(this, _snowman, (blockState, blockPos) -> blockState.shouldSuffocate(this.world, (BlockPos)blockPos));
    }

    @Override
    public void setSprinting(boolean sprinting) {
        super.setSprinting(sprinting);
        this.ticksSinceSprintingChanged = 0;
    }

    public void setExperience(float progress, int total, int level) {
        this.experienceProgress = progress;
        this.totalExperience = total;
        this.experienceLevel = level;
    }

    @Override
    public void sendSystemMessage(Text message, UUID senderUuid) {
        this.client.inGameHud.getChatHud().addMessage(message);
    }

    @Override
    public void handleStatus(byte status) {
        if (status >= 24 && status <= 28) {
            this.setClientPermissionLevel(status - 24);
        } else {
            super.handleStatus(status);
        }
    }

    public void setShowsDeathScreen(boolean shouldShow) {
        this.showsDeathScreen = shouldShow;
    }

    public boolean showsDeathScreen() {
        return this.showsDeathScreen;
    }

    @Override
    public void playSound(SoundEvent sound, float volume, float pitch) {
        this.world.playSound(this.getX(), this.getY(), this.getZ(), sound, this.getSoundCategory(), volume, pitch, false);
    }

    @Override
    public void playSound(SoundEvent event, SoundCategory category, float volume, float pitch) {
        this.world.playSound(this.getX(), this.getY(), this.getZ(), event, category, volume, pitch, false);
    }

    @Override
    public boolean canMoveVoluntarily() {
        return true;
    }

    @Override
    public void setCurrentHand(Hand hand) {
        ItemStack itemStack = this.getStackInHand(hand);
        if (itemStack.isEmpty() || this.isUsingItem()) {
            return;
        }
        super.setCurrentHand(hand);
        this.usingItem = true;
        this.activeHand = hand;
    }

    @Override
    public boolean isUsingItem() {
        return this.usingItem;
    }

    @Override
    public void clearActiveItem() {
        super.clearActiveItem();
        this.usingItem = false;
    }

    @Override
    public Hand getActiveHand() {
        return this.activeHand;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (LIVING_FLAGS.equals(data)) {
            boolean bl = ((Byte)this.dataTracker.get(LIVING_FLAGS) & 1) > 0;
            Hand hand = _snowman = ((Byte)this.dataTracker.get(LIVING_FLAGS) & 2) > 0 ? Hand.OFF_HAND : Hand.MAIN_HAND;
            if (bl && !this.usingItem) {
                this.setCurrentHand(_snowman);
            } else if (!bl && this.usingItem) {
                this.clearActiveItem();
            }
        }
        if (FLAGS.equals(data) && this.isFallFlying() && !this.field_3939) {
            this.client.getSoundManager().play(new ElytraSoundInstance(this));
        }
    }

    public boolean hasJumpingMount() {
        Entity entity = this.getVehicle();
        return this.hasVehicle() && entity instanceof JumpingMount && ((JumpingMount)((Object)entity)).canJump();
    }

    public float method_3151() {
        return this.field_3922;
    }

    @Override
    public void openEditSignScreen(SignBlockEntity sign) {
        this.client.openScreen(new SignEditScreen(sign));
    }

    @Override
    public void openCommandBlockMinecartScreen(CommandBlockExecutor commandBlockExecutor) {
        this.client.openScreen(new MinecartCommandBlockScreen(commandBlockExecutor));
    }

    @Override
    public void openCommandBlockScreen(CommandBlockBlockEntity commandBlock) {
        this.client.openScreen(new CommandBlockScreen(commandBlock));
    }

    @Override
    public void openStructureBlockScreen(StructureBlockBlockEntity structureBlock) {
        this.client.openScreen(new StructureBlockScreen(structureBlock));
    }

    @Override
    public void openJigsawScreen(JigsawBlockEntity jigsaw) {
        this.client.openScreen(new JigsawBlockScreen(jigsaw));
    }

    @Override
    public void openEditBookScreen(ItemStack book, Hand hand) {
        Item item = book.getItem();
        if (item == Items.WRITABLE_BOOK) {
            this.client.openScreen(new BookEditScreen(this, book, hand));
        }
    }

    @Override
    public void addCritParticles(Entity target) {
        this.client.particleManager.addEmitter(target, ParticleTypes.CRIT);
    }

    @Override
    public void addEnchantedHitParticles(Entity target) {
        this.client.particleManager.addEmitter(target, ParticleTypes.ENCHANTED_HIT);
    }

    @Override
    public boolean isSneaking() {
        return this.input != null && this.input.sneaking;
    }

    @Override
    public boolean isInSneakingPose() {
        return this.inSneakingPose;
    }

    public boolean shouldSlowDown() {
        return this.isInSneakingPose() || this.shouldLeaveSwimmingPose();
    }

    @Override
    public void tickNewAi() {
        super.tickNewAi();
        if (this.isCamera()) {
            this.sidewaysSpeed = this.input.movementSideways;
            this.forwardSpeed = this.input.movementForward;
            this.jumping = this.input.jumping;
            this.lastRenderYaw = this.renderYaw;
            this.lastRenderPitch = this.renderPitch;
            this.renderPitch = (float)((double)this.renderPitch + (double)(this.pitch - this.renderPitch) * 0.5);
            this.renderYaw = (float)((double)this.renderYaw + (double)(this.yaw - this.renderYaw) * 0.5);
        }
    }

    protected boolean isCamera() {
        return this.client.getCameraEntity() == this;
    }

    @Override
    public void tickMovement() {
        int n;
        ++this.ticksSinceSprintingChanged;
        if (this.ticksLeftToDoubleTapSprint > 0) {
            --this.ticksLeftToDoubleTapSprint;
        }
        this.updateNausea();
        boolean bl = this.input.jumping;
        _snowman = this.input.sneaking;
        _snowman = this.isWalking();
        this.inSneakingPose = !this.abilities.flying && !this.isSwimming() && this.wouldPoseNotCollide(EntityPose.CROUCHING) && (this.isSneaking() || !this.isSleeping() && !this.wouldPoseNotCollide(EntityPose.STANDING));
        this.input.tick(this.shouldSlowDown());
        this.client.getTutorialManager().onMovement(this.input);
        if (this.isUsingItem() && !this.hasVehicle()) {
            this.input.movementSideways *= 0.2f;
            this.input.movementForward *= 0.2f;
            this.ticksLeftToDoubleTapSprint = 0;
        }
        _snowman = false;
        if (this.ticksToNextAutojump > 0) {
            --this.ticksToNextAutojump;
            _snowman = true;
            this.input.jumping = true;
        }
        if (!this.noClip) {
            this.pushOutOfBlocks(this.getX() - (double)this.getWidth() * 0.35, this.getZ() + (double)this.getWidth() * 0.35);
            this.pushOutOfBlocks(this.getX() - (double)this.getWidth() * 0.35, this.getZ() - (double)this.getWidth() * 0.35);
            this.pushOutOfBlocks(this.getX() + (double)this.getWidth() * 0.35, this.getZ() - (double)this.getWidth() * 0.35);
            this.pushOutOfBlocks(this.getX() + (double)this.getWidth() * 0.35, this.getZ() + (double)this.getWidth() * 0.35);
        }
        if (_snowman) {
            this.ticksLeftToDoubleTapSprint = 0;
        }
        boolean bl2 = _snowman = (float)this.getHungerManager().getFoodLevel() > 6.0f || this.abilities.allowFlying;
        if (!(!this.onGround && !this.isSubmergedInWater() || _snowman || _snowman || !this.isWalking() || this.isSprinting() || !_snowman || this.isUsingItem() || this.hasStatusEffect(StatusEffects.BLINDNESS))) {
            if (this.ticksLeftToDoubleTapSprint > 0 || this.client.options.keySprint.isPressed()) {
                this.setSprinting(true);
            } else {
                this.ticksLeftToDoubleTapSprint = 7;
            }
        }
        if (!this.isSprinting() && (!this.isTouchingWater() || this.isSubmergedInWater()) && this.isWalking() && _snowman && !this.isUsingItem() && !this.hasStatusEffect(StatusEffects.BLINDNESS) && this.client.options.keySprint.isPressed()) {
            this.setSprinting(true);
        }
        if (this.isSprinting()) {
            _snowman = !this.input.hasForwardMovement() || !_snowman;
            int n2 = n = _snowman || this.horizontalCollision || this.isTouchingWater() && !this.isSubmergedInWater() ? 1 : 0;
            if (this.isSwimming()) {
                if (!this.onGround && !this.input.sneaking && _snowman || !this.isTouchingWater()) {
                    this.setSprinting(false);
                }
            } else if (n != 0) {
                this.setSprinting(false);
            }
        }
        _snowman = false;
        if (this.abilities.allowFlying) {
            if (this.client.interactionManager.isFlyingLocked()) {
                if (!this.abilities.flying) {
                    this.abilities.flying = true;
                    _snowman = true;
                    this.sendAbilitiesUpdate();
                }
            } else if (!bl && this.input.jumping && !_snowman) {
                if (this.abilityResyncCountdown == 0) {
                    this.abilityResyncCountdown = 7;
                } else if (!this.isSwimming()) {
                    this.abilities.flying = !this.abilities.flying;
                    _snowman = true;
                    this.sendAbilitiesUpdate();
                    this.abilityResyncCountdown = 0;
                }
            }
        }
        if (this.input.jumping && !_snowman && !bl && !this.abilities.flying && !this.hasVehicle() && !this.isClimbing() && (_snowman = this.getEquippedStack(EquipmentSlot.CHEST)).getItem() == Items.ELYTRA && ElytraItem.isUsable(_snowman) && this.checkFallFlying()) {
            this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
        }
        this.field_3939 = this.isFallFlying();
        if (this.isTouchingWater() && this.input.sneaking && this.method_29920()) {
            this.knockDownwards();
        }
        if (this.isSubmergedIn(FluidTags.WATER)) {
            n = this.isSpectator() ? 10 : 1;
            this.underwaterVisibilityTicks = MathHelper.clamp(this.underwaterVisibilityTicks + n, 0, 600);
        } else if (this.underwaterVisibilityTicks > 0) {
            this.isSubmergedIn(FluidTags.WATER);
            this.underwaterVisibilityTicks = MathHelper.clamp(this.underwaterVisibilityTicks - 10, 0, 600);
        }
        if (this.abilities.flying && this.isCamera()) {
            n = 0;
            if (this.input.sneaking) {
                --n;
            }
            if (this.input.jumping) {
                ++n;
            }
            if (n != 0) {
                this.setVelocity(this.getVelocity().add(0.0, (float)n * this.abilities.getFlySpeed() * 3.0f, 0.0));
            }
        }
        if (this.hasJumpingMount()) {
            JumpingMount jumpingMount = (JumpingMount)((Object)this.getVehicle());
            if (this.field_3938 < 0) {
                ++this.field_3938;
                if (this.field_3938 == 0) {
                    this.field_3922 = 0.0f;
                }
            }
            if (bl && !this.input.jumping) {
                this.field_3938 = -10;
                jumpingMount.setJumpStrength(MathHelper.floor(this.method_3151() * 100.0f));
                this.startRidingJump();
            } else if (!bl && this.input.jumping) {
                this.field_3938 = 0;
                this.field_3922 = 0.0f;
            } else if (bl) {
                ++this.field_3938;
                this.field_3922 = this.field_3938 < 10 ? (float)this.field_3938 * 0.1f : 0.8f + 2.0f / (float)(this.field_3938 - 9) * 0.1f;
            }
        } else {
            this.field_3922 = 0.0f;
        }
        super.tickMovement();
        if (this.onGround && this.abilities.flying && !this.client.interactionManager.isFlyingLocked()) {
            this.abilities.flying = false;
            this.sendAbilitiesUpdate();
        }
    }

    private void updateNausea() {
        this.lastNauseaStrength = this.nextNauseaStrength;
        if (this.inNetherPortal) {
            if (this.client.currentScreen != null && !this.client.currentScreen.isPauseScreen()) {
                if (this.client.currentScreen instanceof HandledScreen) {
                    this.closeHandledScreen();
                }
                this.client.openScreen(null);
            }
            if (this.nextNauseaStrength == 0.0f) {
                this.client.getSoundManager().play(PositionedSoundInstance.ambient(SoundEvents.BLOCK_PORTAL_TRIGGER, this.random.nextFloat() * 0.4f + 0.8f, 0.25f));
            }
            this.nextNauseaStrength += 0.0125f;
            if (this.nextNauseaStrength >= 1.0f) {
                this.nextNauseaStrength = 1.0f;
            }
            this.inNetherPortal = false;
        } else if (this.hasStatusEffect(StatusEffects.NAUSEA) && this.getStatusEffect(StatusEffects.NAUSEA).getDuration() > 60) {
            this.nextNauseaStrength += 0.006666667f;
            if (this.nextNauseaStrength > 1.0f) {
                this.nextNauseaStrength = 1.0f;
            }
        } else {
            if (this.nextNauseaStrength > 0.0f) {
                this.nextNauseaStrength -= 0.05f;
            }
            if (this.nextNauseaStrength < 0.0f) {
                this.nextNauseaStrength = 0.0f;
            }
        }
        this.tickNetherPortalCooldown();
    }

    @Override
    public void tickRiding() {
        super.tickRiding();
        this.riding = false;
        if (this.getVehicle() instanceof BoatEntity) {
            BoatEntity boatEntity = (BoatEntity)this.getVehicle();
            boatEntity.setInputs(this.input.pressingLeft, this.input.pressingRight, this.input.pressingForward, this.input.pressingBack);
            this.riding |= this.input.pressingLeft || this.input.pressingRight || this.input.pressingForward || this.input.pressingBack;
        }
    }

    public boolean isRiding() {
        return this.riding;
    }

    @Override
    @Nullable
    public StatusEffectInstance removeStatusEffectInternal(@Nullable StatusEffect type) {
        if (type == StatusEffects.NAUSEA) {
            this.lastNauseaStrength = 0.0f;
            this.nextNauseaStrength = 0.0f;
        }
        return super.removeStatusEffectInternal(type);
    }

    @Override
    public void move(MovementType type, Vec3d movement) {
        double d = this.getX();
        _snowman = this.getZ();
        super.move(type, movement);
        this.autoJump((float)(this.getX() - d), (float)(this.getZ() - _snowman));
    }

    public boolean isAutoJumpEnabled() {
        return this.autoJumpEnabled;
    }

    protected void autoJump(float dx, float dz) {
        float _snowman25;
        Vec3d _snowman8;
        float _snowman6;
        if (!this.shouldAutoJump()) {
            return;
        }
        Vec3d vec3d = this.getPos();
        _snowman = vec3d.add(dx, 0.0, dz);
        _snowman8 = new Vec3d(dx, 0.0, dz);
        float _snowman2 = this.getMovementSpeed();
        float _snowman3 = (float)_snowman8.lengthSquared();
        if (_snowman3 <= 0.001f) {
            Vec2f vec2f = this.input.getMovementInput();
            float _snowman4 = _snowman2 * vec2f.x;
            float _snowman5 = _snowman2 * vec2f.y;
            _snowman6 = MathHelper.sin(this.yaw * ((float)Math.PI / 180));
            float _snowman7 = MathHelper.cos(this.yaw * ((float)Math.PI / 180));
            _snowman8 = new Vec3d(_snowman4 * _snowman7 - _snowman5 * _snowman6, _snowman8.y, _snowman5 * _snowman7 + _snowman4 * _snowman6);
            _snowman3 = (float)_snowman8.lengthSquared();
            if (_snowman3 <= 0.001f) {
                return;
            }
        }
        float f = MathHelper.fastInverseSqrt(_snowman3);
        Vec3d _snowman9 = _snowman8.multiply(f);
        Vec3d _snowman10 = this.getRotationVecClient();
        _snowman6 = (float)(_snowman10.x * _snowman9.x + _snowman10.z * _snowman9.z);
        if (_snowman6 < -0.15f) {
            return;
        }
        ShapeContext _snowman11 = ShapeContext.of(this);
        BlockPos _snowman12 = new BlockPos(this.getX(), this.getBoundingBox().maxY, this.getZ());
        BlockState _snowman13 = this.world.getBlockState(_snowman12);
        if (!_snowman13.getCollisionShape(this.world, _snowman12, _snowman11).isEmpty()) {
            return;
        }
        BlockState _snowman14 = this.world.getBlockState(_snowman12 = _snowman12.up());
        if (!_snowman14.getCollisionShape(this.world, _snowman12, _snowman11).isEmpty()) {
            return;
        }
        _snowman = 7.0f;
        _snowman = 1.2f;
        if (this.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
            _snowman += (float)(this.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1) * 0.75f;
        }
        _snowman = Math.max(_snowman2 * 7.0f, 1.0f / f);
        Vec3d _snowman15 = vec3d;
        Vec3d _snowman16 = _snowman.add(_snowman9.multiply(_snowman));
        _snowman = this.getWidth();
        _snowman = this.getHeight();
        Box _snowman17 = new Box(_snowman15, _snowman16.add(0.0, _snowman, 0.0)).expand(_snowman, 0.0, _snowman);
        _snowman15 = _snowman15.add(0.0, 0.51f, 0.0);
        _snowman16 = _snowman16.add(0.0, 0.51f, 0.0);
        Vec3d _snowman18 = _snowman9.crossProduct(new Vec3d(0.0, 1.0, 0.0));
        Vec3d _snowman19 = _snowman18.multiply(_snowman * 0.5f);
        Vec3d _snowman20 = _snowman15.subtract(_snowman19);
        Vec3d _snowman21 = _snowman16.subtract(_snowman19);
        Vec3d _snowman22 = _snowman15.add(_snowman19);
        Vec3d _snowman23 = _snowman16.add(_snowman19);
        Iterator _snowman24 = this.world.getCollisions(this, _snowman17, entity -> true).flatMap(voxelShape -> voxelShape.getBoundingBoxes().stream()).iterator();
        _snowman25 = Float.MIN_VALUE;
        while (_snowman24.hasNext()) {
            Box box = (Box)_snowman24.next();
            if (!box.intersects(_snowman20, _snowman21) && !box.intersects(_snowman22, _snowman23)) continue;
            _snowman25 = (float)box.maxY;
            Vec3d _snowman26 = box.getCenter();
            BlockPos _snowman27 = new BlockPos(_snowman26);
            int _snowman28 = 1;
            while ((float)_snowman28 < _snowman) {
                BlockPos blockPos = _snowman27.up(_snowman28);
                BlockState _snowman29 = this.world.getBlockState(blockPos);
                VoxelShape _snowman30 = _snowman29.getCollisionShape(this.world, blockPos, _snowman11);
                if (!_snowman30.isEmpty() && (double)(_snowman25 = (float)_snowman30.getMax(Direction.Axis.Y) + (float)blockPos.getY()) - this.getY() > (double)_snowman) {
                    return;
                }
                if (_snowman28 > 1 && !(_snowman = this.world.getBlockState(_snowman12 = _snowman12.up())).getCollisionShape(this.world, _snowman12, _snowman11).isEmpty()) {
                    return;
                }
                ++_snowman28;
            }
            break block0;
        }
        if (_snowman25 == Float.MIN_VALUE) {
            return;
        }
        float f2 = (float)((double)_snowman25 - this.getY());
        if (f2 <= 0.5f || f2 > _snowman) {
            return;
        }
        this.ticksToNextAutojump = 1;
    }

    private boolean shouldAutoJump() {
        return this.isAutoJumpEnabled() && this.ticksToNextAutojump <= 0 && this.onGround && !this.clipAtLedge() && !this.hasVehicle() && this.hasMovementInput() && (double)this.getJumpVelocityMultiplier() >= 1.0;
    }

    private boolean hasMovementInput() {
        Vec2f vec2f = this.input.getMovementInput();
        return vec2f.x != 0.0f || vec2f.y != 0.0f;
    }

    private boolean isWalking() {
        double d = 0.8;
        return this.isSubmergedInWater() ? this.input.hasForwardMovement() : (double)this.input.movementForward >= 0.8;
    }

    public float getUnderwaterVisibility() {
        if (!this.isSubmergedIn(FluidTags.WATER)) {
            return 0.0f;
        }
        float f = 600.0f;
        _snowman = 100.0f;
        if ((float)this.underwaterVisibilityTicks >= 600.0f) {
            return 1.0f;
        }
        _snowman = MathHelper.clamp((float)this.underwaterVisibilityTicks / 100.0f, 0.0f, 1.0f);
        _snowman = (float)this.underwaterVisibilityTicks < 100.0f ? 0.0f : MathHelper.clamp(((float)this.underwaterVisibilityTicks - 100.0f) / 500.0f, 0.0f, 1.0f);
        return _snowman * 0.6f + _snowman * 0.39999998f;
    }

    @Override
    public boolean isSubmergedInWater() {
        return this.isSubmergedInWater;
    }

    @Override
    protected boolean updateWaterSubmersionState() {
        boolean bl = this.isSubmergedInWater;
        _snowman = super.updateWaterSubmersionState();
        if (this.isSpectator()) {
            return this.isSubmergedInWater;
        }
        if (!bl && _snowman) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundCategory.AMBIENT, 1.0f, 1.0f, false);
            this.client.getSoundManager().play(new AmbientSoundLoops.Underwater(this));
        }
        if (bl && !_snowman) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundCategory.AMBIENT, 1.0f, 1.0f, false);
        }
        return this.isSubmergedInWater;
    }

    @Override
    public Vec3d method_30951(float f) {
        if (this.client.options.getPerspective().isFirstPerson()) {
            _snowman = MathHelper.lerp(f * 0.5f, this.yaw, this.prevYaw) * ((float)Math.PI / 180);
            _snowman = MathHelper.lerp(f * 0.5f, this.pitch, this.prevPitch) * ((float)Math.PI / 180);
            double d = this.getMainArm() == Arm.RIGHT ? -1.0 : 1.0;
            Vec3d _snowman2 = new Vec3d(0.39 * d, -0.6, 0.3);
            return _snowman2.rotateX(-_snowman).rotateY(-_snowman).add(this.getCameraPosVec(f));
        }
        return super.method_30951(f);
    }
}

