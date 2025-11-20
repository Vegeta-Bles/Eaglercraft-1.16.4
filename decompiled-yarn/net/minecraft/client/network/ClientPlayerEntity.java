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

public class ClientPlayerEntity extends AbstractClientPlayerEntity {
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

   public ClientPlayerEntity(
      MinecraftClient client,
      ClientWorld world,
      ClientPlayNetworkHandler networkHandler,
      StatHandler stats,
      ClientRecipeBook recipeBook,
      boolean lastSneaking,
      boolean lastSprinting
   ) {
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
      } else {
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
      return this.hasVehicle() ? super.getYaw(tickDelta) : this.yaw;
   }

   @Override
   public void tick() {
      if (this.world.isChunkLoaded(new BlockPos(this.getX(), 0.0, this.getZ()))) {
         super.tick();
         if (this.hasVehicle()) {
            this.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookOnly(this.yaw, this.pitch, this.onGround));
            this.networkHandler.sendPacket(new PlayerInputC2SPacket(this.sidewaysSpeed, this.forwardSpeed, this.input.jumping, this.input.sneaking));
            Entity _snowman = this.getRootVehicle();
            if (_snowman != this && _snowman.isLogicalSideForUpdatingMovement()) {
               this.networkHandler.sendPacket(new VehicleMoveC2SPacket(_snowman));
            }
         } else {
            this.sendMovementPackets();
         }

         for (ClientPlayerTickable _snowman : this.tickables) {
            _snowman.tick();
         }
      }
   }

   public float getMoodPercentage() {
      for (ClientPlayerTickable _snowman : this.tickables) {
         if (_snowman instanceof BiomeEffectSoundPlayer) {
            return ((BiomeEffectSoundPlayer)_snowman).getMoodPercentage();
         }
      }

      return 0.0F;
   }

   private void sendMovementPackets() {
      boolean _snowman = this.isSprinting();
      if (_snowman != this.lastSprinting) {
         ClientCommandC2SPacket.Mode _snowmanx = _snowman ? ClientCommandC2SPacket.Mode.START_SPRINTING : ClientCommandC2SPacket.Mode.STOP_SPRINTING;
         this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, _snowmanx));
         this.lastSprinting = _snowman;
      }

      boolean _snowmanx = this.isSneaking();
      if (_snowmanx != this.lastSneaking) {
         ClientCommandC2SPacket.Mode _snowmanxx = _snowmanx ? ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY : ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY;
         this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, _snowmanxx));
         this.lastSneaking = _snowmanx;
      }

      if (this.isCamera()) {
         double _snowmanxx = this.getX() - this.lastX;
         double _snowmanxxx = this.getY() - this.lastBaseY;
         double _snowmanxxxx = this.getZ() - this.lastZ;
         double _snowmanxxxxx = (double)(this.yaw - this.lastYaw);
         double _snowmanxxxxxx = (double)(this.pitch - this.lastPitch);
         this.ticksSinceLastPositionPacketSent++;
         boolean _snowmanxxxxxxx = _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx > 9.0E-4 || this.ticksSinceLastPositionPacketSent >= 20;
         boolean _snowmanxxxxxxxx = _snowmanxxxxx != 0.0 || _snowmanxxxxxx != 0.0;
         if (this.hasVehicle()) {
            Vec3d _snowmanxxxxxxxxx = this.getVelocity();
            this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Both(_snowmanxxxxxxxxx.x, -999.0, _snowmanxxxxxxxxx.z, this.yaw, this.pitch, this.onGround));
            _snowmanxxxxxxx = false;
         } else if (_snowmanxxxxxxx && _snowmanxxxxxxxx) {
            this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Both(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch, this.onGround));
         } else if (_snowmanxxxxxxx) {
            this.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(this.getX(), this.getY(), this.getZ(), this.onGround));
         } else if (_snowmanxxxxxxxx) {
            this.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookOnly(this.yaw, this.pitch, this.onGround));
         } else if (this.lastOnGround != this.onGround) {
            this.networkHandler.sendPacket(new PlayerMoveC2SPacket(this.onGround));
         }

         if (_snowmanxxxxxxx) {
            this.lastX = this.getX();
            this.lastBaseY = this.getY();
            this.lastZ = this.getZ();
            this.ticksSinceLastPositionPacketSent = 0;
         }

         if (_snowmanxxxxxxxx) {
            this.lastYaw = this.yaw;
            this.lastPitch = this.pitch;
         }

         this.lastOnGround = this.onGround;
         this.autoJumpEnabled = this.client.options.autoJump;
      }
   }

   @Override
   public boolean dropSelectedItem(boolean dropEntireStack) {
      PlayerActionC2SPacket.Action _snowman = dropEntireStack ? PlayerActionC2SPacket.Action.DROP_ALL_ITEMS : PlayerActionC2SPacket.Action.DROP_ITEM;
      this.networkHandler.sendPacket(new PlayerActionC2SPacket(_snowman, BlockPos.ORIGIN, Direction.DOWN));
      return this.inventory
            .removeStack(
               this.inventory.selectedSlot, dropEntireStack && !this.inventory.getMainHandStack().isEmpty() ? this.inventory.getMainHandStack().getCount() : 1
            )
         != ItemStack.EMPTY;
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
      if (!this.isInvulnerableTo(source)) {
         this.setHealth(this.getHealth() - amount);
      }
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
         float _snowman = this.getHealth() - health;
         if (_snowman <= 0.0F) {
            this.setHealth(health);
            if (_snowman < 0.0F) {
               this.timeUntilRegen = 10;
            }
         } else {
            this.lastDamageTaken = _snowman;
            this.setHealth(this.getHealth());
            this.timeUntilRegen = 20;
            this.applyDamage(DamageSource.GENERIC, _snowman);
            this.maxHurtTime = 10;
            this.hurtTime = this.maxHurtTime;
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
      this.networkHandler
         .sendPacket(new ClientCommandC2SPacket(this, ClientCommandC2SPacket.Mode.START_RIDING_JUMP, MathHelper.floor(this.method_3151() * 100.0F)));
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

   private void pushOutOfBlocks(double x, double _snowman) {
      BlockPos _snowmanx = new BlockPos(x, this.getY(), _snowman);
      if (this.wouldCollideAt(_snowmanx)) {
         double _snowmanxx = x - (double)_snowmanx.getX();
         double _snowmanxxx = _snowman - (double)_snowmanx.getZ();
         Direction _snowmanxxxx = null;
         double _snowmanxxxxx = Double.MAX_VALUE;
         Direction[] _snowmanxxxxxx = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH};

         for (Direction _snowmanxxxxxxx : _snowmanxxxxxx) {
            double _snowmanxxxxxxxx = _snowmanxxxxxxx.getAxis().choose(_snowmanxx, 0.0, _snowmanxxx);
            double _snowmanxxxxxxxxx = _snowmanxxxxxxx.getDirection() == Direction.AxisDirection.POSITIVE ? 1.0 - _snowmanxxxxxxxx : _snowmanxxxxxxxx;
            if (_snowmanxxxxxxxxx < _snowmanxxxxx && !this.wouldCollideAt(_snowmanx.offset(_snowmanxxxxxxx))) {
               _snowmanxxxxx = _snowmanxxxxxxxxx;
               _snowmanxxxx = _snowmanxxxxxxx;
            }
         }

         if (_snowmanxxxx != null) {
            Vec3d _snowmanxxxxxxxx = this.getVelocity();
            if (_snowmanxxxx.getAxis() == Direction.Axis.X) {
               this.setVelocity(0.1 * (double)_snowmanxxxx.getOffsetX(), _snowmanxxxxxxxx.y, _snowmanxxxxxxxx.z);
            } else {
               this.setVelocity(_snowmanxxxxxxxx.x, _snowmanxxxxxxxx.y, 0.1 * (double)_snowmanxxxx.getOffsetZ());
            }
         }
      }
   }

   private boolean wouldCollideAt(BlockPos _snowman) {
      Box _snowmanx = this.getBoundingBox();
      Box _snowmanxx = new Box((double)_snowman.getX(), _snowmanx.minY, (double)_snowman.getZ(), (double)_snowman.getX() + 1.0, _snowmanx.maxY, (double)_snowman.getZ() + 1.0).contract(1.0E-7);
      return !this.world.isBlockSpaceEmpty(this, _snowmanxx, (_snowmanxxx, _snowmanxxxx) -> _snowmanxxx.shouldSuffocate(this.world, _snowmanxxxx));
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
      ItemStack _snowman = this.getStackInHand(hand);
      if (!_snowman.isEmpty() && !this.isUsingItem()) {
         super.setCurrentHand(hand);
         this.usingItem = true;
         this.activeHand = hand;
      }
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
         boolean _snowman = (this.dataTracker.get(LIVING_FLAGS) & 1) > 0;
         Hand _snowmanx = (this.dataTracker.get(LIVING_FLAGS) & 2) > 0 ? Hand.OFF_HAND : Hand.MAIN_HAND;
         if (_snowman && !this.usingItem) {
            this.setCurrentHand(_snowmanx);
         } else if (!_snowman && this.usingItem) {
            this.clearActiveItem();
         }
      }

      if (FLAGS.equals(data) && this.isFallFlying() && !this.field_3939) {
         this.client.getSoundManager().play(new ElytraSoundInstance(this));
      }
   }

   public boolean hasJumpingMount() {
      Entity _snowman = this.getVehicle();
      return this.hasVehicle() && _snowman instanceof JumpingMount && ((JumpingMount)_snowman).canJump();
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
      Item _snowman = book.getItem();
      if (_snowman == Items.WRITABLE_BOOK) {
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
      this.ticksSinceSprintingChanged++;
      if (this.ticksLeftToDoubleTapSprint > 0) {
         this.ticksLeftToDoubleTapSprint--;
      }

      this.updateNausea();
      boolean _snowman = this.input.jumping;
      boolean _snowmanx = this.input.sneaking;
      boolean _snowmanxx = this.isWalking();
      this.inSneakingPose = !this.abilities.flying
         && !this.isSwimming()
         && this.wouldPoseNotCollide(EntityPose.CROUCHING)
         && (this.isSneaking() || !this.isSleeping() && !this.wouldPoseNotCollide(EntityPose.STANDING));
      this.input.tick(this.shouldSlowDown());
      this.client.getTutorialManager().onMovement(this.input);
      if (this.isUsingItem() && !this.hasVehicle()) {
         this.input.movementSideways *= 0.2F;
         this.input.movementForward *= 0.2F;
         this.ticksLeftToDoubleTapSprint = 0;
      }

      boolean _snowmanxxx = false;
      if (this.ticksToNextAutojump > 0) {
         this.ticksToNextAutojump--;
         _snowmanxxx = true;
         this.input.jumping = true;
      }

      if (!this.noClip) {
         this.pushOutOfBlocks(this.getX() - (double)this.getWidth() * 0.35, this.getZ() + (double)this.getWidth() * 0.35);
         this.pushOutOfBlocks(this.getX() - (double)this.getWidth() * 0.35, this.getZ() - (double)this.getWidth() * 0.35);
         this.pushOutOfBlocks(this.getX() + (double)this.getWidth() * 0.35, this.getZ() - (double)this.getWidth() * 0.35);
         this.pushOutOfBlocks(this.getX() + (double)this.getWidth() * 0.35, this.getZ() + (double)this.getWidth() * 0.35);
      }

      if (_snowmanx) {
         this.ticksLeftToDoubleTapSprint = 0;
      }

      boolean _snowmanxxxx = (float)this.getHungerManager().getFoodLevel() > 6.0F || this.abilities.allowFlying;
      if ((this.onGround || this.isSubmergedInWater())
         && !_snowmanx
         && !_snowmanxx
         && this.isWalking()
         && !this.isSprinting()
         && _snowmanxxxx
         && !this.isUsingItem()
         && !this.hasStatusEffect(StatusEffects.BLINDNESS)) {
         if (this.ticksLeftToDoubleTapSprint <= 0 && !this.client.options.keySprint.isPressed()) {
            this.ticksLeftToDoubleTapSprint = 7;
         } else {
            this.setSprinting(true);
         }
      }

      if (!this.isSprinting()
         && (!this.isTouchingWater() || this.isSubmergedInWater())
         && this.isWalking()
         && _snowmanxxxx
         && !this.isUsingItem()
         && !this.hasStatusEffect(StatusEffects.BLINDNESS)
         && this.client.options.keySprint.isPressed()) {
         this.setSprinting(true);
      }

      if (this.isSprinting()) {
         boolean _snowmanxxxxx = !this.input.hasForwardMovement() || !_snowmanxxxx;
         boolean _snowmanxxxxxx = _snowmanxxxxx || this.horizontalCollision || this.isTouchingWater() && !this.isSubmergedInWater();
         if (this.isSwimming()) {
            if (!this.onGround && !this.input.sneaking && _snowmanxxxxx || !this.isTouchingWater()) {
               this.setSprinting(false);
            }
         } else if (_snowmanxxxxxx) {
            this.setSprinting(false);
         }
      }

      boolean _snowmanxxxxx = false;
      if (this.abilities.allowFlying) {
         if (this.client.interactionManager.isFlyingLocked()) {
            if (!this.abilities.flying) {
               this.abilities.flying = true;
               _snowmanxxxxx = true;
               this.sendAbilitiesUpdate();
            }
         } else if (!_snowman && this.input.jumping && !_snowmanxxx) {
            if (this.abilityResyncCountdown == 0) {
               this.abilityResyncCountdown = 7;
            } else if (!this.isSwimming()) {
               this.abilities.flying = !this.abilities.flying;
               _snowmanxxxxx = true;
               this.sendAbilitiesUpdate();
               this.abilityResyncCountdown = 0;
            }
         }
      }

      if (this.input.jumping && !_snowmanxxxxx && !_snowman && !this.abilities.flying && !this.hasVehicle() && !this.isClimbing()) {
         ItemStack _snowmanxxxxxx = this.getEquippedStack(EquipmentSlot.CHEST);
         if (_snowmanxxxxxx.getItem() == Items.ELYTRA && ElytraItem.isUsable(_snowmanxxxxxx) && this.checkFallFlying()) {
            this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
         }
      }

      this.field_3939 = this.isFallFlying();
      if (this.isTouchingWater() && this.input.sneaking && this.method_29920()) {
         this.knockDownwards();
      }

      if (this.isSubmergedIn(FluidTags.WATER)) {
         int _snowmanxxxxxx = this.isSpectator() ? 10 : 1;
         this.underwaterVisibilityTicks = MathHelper.clamp(this.underwaterVisibilityTicks + _snowmanxxxxxx, 0, 600);
      } else if (this.underwaterVisibilityTicks > 0) {
         this.isSubmergedIn(FluidTags.WATER);
         this.underwaterVisibilityTicks = MathHelper.clamp(this.underwaterVisibilityTicks - 10, 0, 600);
      }

      if (this.abilities.flying && this.isCamera()) {
         int _snowmanxxxxxx = 0;
         if (this.input.sneaking) {
            _snowmanxxxxxx--;
         }

         if (this.input.jumping) {
            _snowmanxxxxxx++;
         }

         if (_snowmanxxxxxx != 0) {
            this.setVelocity(this.getVelocity().add(0.0, (double)((float)_snowmanxxxxxx * this.abilities.getFlySpeed() * 3.0F), 0.0));
         }
      }

      if (this.hasJumpingMount()) {
         JumpingMount _snowmanxxxxxxx = (JumpingMount)this.getVehicle();
         if (this.field_3938 < 0) {
            this.field_3938++;
            if (this.field_3938 == 0) {
               this.field_3922 = 0.0F;
            }
         }

         if (_snowman && !this.input.jumping) {
            this.field_3938 = -10;
            _snowmanxxxxxxx.setJumpStrength(MathHelper.floor(this.method_3151() * 100.0F));
            this.startRidingJump();
         } else if (!_snowman && this.input.jumping) {
            this.field_3938 = 0;
            this.field_3922 = 0.0F;
         } else if (_snowman) {
            this.field_3938++;
            if (this.field_3938 < 10) {
               this.field_3922 = (float)this.field_3938 * 0.1F;
            } else {
               this.field_3922 = 0.8F + 2.0F / (float)(this.field_3938 - 9) * 0.1F;
            }
         }
      } else {
         this.field_3922 = 0.0F;
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

         if (this.nextNauseaStrength == 0.0F) {
            this.client.getSoundManager().play(PositionedSoundInstance.ambient(SoundEvents.BLOCK_PORTAL_TRIGGER, this.random.nextFloat() * 0.4F + 0.8F, 0.25F));
         }

         this.nextNauseaStrength += 0.0125F;
         if (this.nextNauseaStrength >= 1.0F) {
            this.nextNauseaStrength = 1.0F;
         }

         this.inNetherPortal = false;
      } else if (this.hasStatusEffect(StatusEffects.NAUSEA) && this.getStatusEffect(StatusEffects.NAUSEA).getDuration() > 60) {
         this.nextNauseaStrength += 0.006666667F;
         if (this.nextNauseaStrength > 1.0F) {
            this.nextNauseaStrength = 1.0F;
         }
      } else {
         if (this.nextNauseaStrength > 0.0F) {
            this.nextNauseaStrength -= 0.05F;
         }

         if (this.nextNauseaStrength < 0.0F) {
            this.nextNauseaStrength = 0.0F;
         }
      }

      this.tickNetherPortalCooldown();
   }

   @Override
   public void tickRiding() {
      super.tickRiding();
      this.riding = false;
      if (this.getVehicle() instanceof BoatEntity) {
         BoatEntity _snowman = (BoatEntity)this.getVehicle();
         _snowman.setInputs(this.input.pressingLeft, this.input.pressingRight, this.input.pressingForward, this.input.pressingBack);
         this.riding = this.riding | (this.input.pressingLeft || this.input.pressingRight || this.input.pressingForward || this.input.pressingBack);
      }
   }

   public boolean isRiding() {
      return this.riding;
   }

   @Nullable
   @Override
   public StatusEffectInstance removeStatusEffectInternal(@Nullable StatusEffect type) {
      if (type == StatusEffects.NAUSEA) {
         this.lastNauseaStrength = 0.0F;
         this.nextNauseaStrength = 0.0F;
      }

      return super.removeStatusEffectInternal(type);
   }

   @Override
   public void move(MovementType type, Vec3d movement) {
      double _snowman = this.getX();
      double _snowmanx = this.getZ();
      super.move(type, movement);
      this.autoJump((float)(this.getX() - _snowman), (float)(this.getZ() - _snowmanx));
   }

   public boolean isAutoJumpEnabled() {
      return this.autoJumpEnabled;
   }

   protected void autoJump(float dx, float dz) {
      if (this.shouldAutoJump()) {
         Vec3d _snowman = this.getPos();
         Vec3d _snowmanx = _snowman.add((double)dx, 0.0, (double)dz);
         Vec3d _snowmanxx = new Vec3d((double)dx, 0.0, (double)dz);
         float _snowmanxxx = this.getMovementSpeed();
         float _snowmanxxxx = (float)_snowmanxx.lengthSquared();
         if (_snowmanxxxx <= 0.001F) {
            Vec2f _snowmanxxxxx = this.input.getMovementInput();
            float _snowmanxxxxxx = _snowmanxxx * _snowmanxxxxx.x;
            float _snowmanxxxxxxx = _snowmanxxx * _snowmanxxxxx.y;
            float _snowmanxxxxxxxx = MathHelper.sin(this.yaw * (float) (Math.PI / 180.0));
            float _snowmanxxxxxxxxx = MathHelper.cos(this.yaw * (float) (Math.PI / 180.0));
            _snowmanxx = new Vec3d((double)(_snowmanxxxxxx * _snowmanxxxxxxxxx - _snowmanxxxxxxx * _snowmanxxxxxxxx), _snowmanxx.y, (double)(_snowmanxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxx * _snowmanxxxxxxxx));
            _snowmanxxxx = (float)_snowmanxx.lengthSquared();
            if (_snowmanxxxx <= 0.001F) {
               return;
            }
         }

         float _snowmanxxxxx = MathHelper.fastInverseSqrt(_snowmanxxxx);
         Vec3d _snowmanxxxxxx = _snowmanxx.multiply((double)_snowmanxxxxx);
         Vec3d _snowmanxxxxxxx = this.getRotationVecClient();
         float _snowmanxxxxxxxx = (float)(_snowmanxxxxxxx.x * _snowmanxxxxxx.x + _snowmanxxxxxxx.z * _snowmanxxxxxx.z);
         if (!(_snowmanxxxxxxxx < -0.15F)) {
            ShapeContext _snowmanxxxxxxxxx = ShapeContext.of(this);
            BlockPos _snowmanxxxxxxxxxx = new BlockPos(this.getX(), this.getBoundingBox().maxY, this.getZ());
            BlockState _snowmanxxxxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxx.getCollisionShape(this.world, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx).isEmpty()) {
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.up();
               BlockState _snowmanxxxxxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxx.getCollisionShape(this.world, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx).isEmpty()) {
                  float _snowmanxxxxxxxxxxxxx = 7.0F;
                  float _snowmanxxxxxxxxxxxxxx = 1.2F;
                  if (this.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
                     _snowmanxxxxxxxxxxxxxx += (float)(this.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1) * 0.75F;
                  }

                  float _snowmanxxxxxxxxxxxxxxx = Math.max(_snowmanxxx * 7.0F, 1.0F / _snowmanxxxxx);
                  Vec3d _snowmanxxxxxxxxxxxxxxxx = _snowmanx.add(_snowmanxxxxxx.multiply((double)_snowmanxxxxxxxxxxxxxxx));
                  float _snowmanxxxxxxxxxxxxxxxxx = this.getWidth();
                  float _snowmanxxxxxxxxxxxxxxxxxx = this.getHeight();
                  Box _snowmanxxxxxxxxxxxxxxxxxxx = new Box(_snowman, _snowmanxxxxxxxxxxxxxxxx.add(0.0, (double)_snowmanxxxxxxxxxxxxxxxxxx, 0.0))
                     .expand((double)_snowmanxxxxxxxxxxxxxxxxx, 0.0, (double)_snowmanxxxxxxxxxxxxxxxxx);
                  Vec3d var19 = _snowman.add(0.0, 0.51F, 0.0);
                  _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.add(0.0, 0.51F, 0.0);
                  Vec3d _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.crossProduct(new Vec3d(0.0, 1.0, 0.0));
                  Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx.multiply((double)(_snowmanxxxxxxxxxxxxxxxxx * 0.5F));
                  Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxx = var19.subtract(_snowmanxxxxxxxxxxxxxxxxxxxxx);
                  Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.subtract(_snowmanxxxxxxxxxxxxxxxxxxxxx);
                  Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = var19.add(_snowmanxxxxxxxxxxxxxxxxxxxxx);
                  Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxxxxxx);
                  Iterator<Box> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.world
                     .getCollisions(this, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx -> true)
                     .flatMap(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.getBoundingBoxes().stream())
                     .iterator();
                  float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = Float.MIN_VALUE;

                  while (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.hasNext()) {
                     Box _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.next();
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.intersects(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx)
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.intersects(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.maxY;
                        Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getCenter();
                        BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);

                        for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 1;
                           (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx;
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
                        ) {
                           BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.up(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                           BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                           VoxelShape _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                           if (!(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getCollisionShape(
                                 this.world, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx
                              ))
                              .isEmpty()) {
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getMax(Direction.Axis.Y)
                                 + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getY();
                              if ((double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx - this.getY() > (double)_snowmanxxxxxxxxxxxxxx) {
                                 return;
                              }
                           }

                           if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx > 1) {
                              _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.up();
                              BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxxxxx);
                              if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getCollisionShape(this.world, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx).isEmpty()) {
                                 return;
                              }
                           }
                        }
                        break;
                     }
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx != Float.MIN_VALUE) {
                     float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx - this.getY());
                     if (!(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx <= 0.5F) && !(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx > _snowmanxxxxxxxxxxxxxx)) {
                        this.ticksToNextAutojump = 1;
                     }
                  }
               }
            }
         }
      }
   }

   private boolean shouldAutoJump() {
      return this.isAutoJumpEnabled()
         && this.ticksToNextAutojump <= 0
         && this.onGround
         && !this.clipAtLedge()
         && !this.hasVehicle()
         && this.hasMovementInput()
         && (double)this.getJumpVelocityMultiplier() >= 1.0;
   }

   private boolean hasMovementInput() {
      Vec2f _snowman = this.input.getMovementInput();
      return _snowman.x != 0.0F || _snowman.y != 0.0F;
   }

   private boolean isWalking() {
      double _snowman = 0.8;
      return this.isSubmergedInWater() ? this.input.hasForwardMovement() : (double)this.input.movementForward >= 0.8;
   }

   public float getUnderwaterVisibility() {
      if (!this.isSubmergedIn(FluidTags.WATER)) {
         return 0.0F;
      } else {
         float _snowman = 600.0F;
         float _snowmanx = 100.0F;
         if ((float)this.underwaterVisibilityTicks >= 600.0F) {
            return 1.0F;
         } else {
            float _snowmanxx = MathHelper.clamp((float)this.underwaterVisibilityTicks / 100.0F, 0.0F, 1.0F);
            float _snowmanxxx = (float)this.underwaterVisibilityTicks < 100.0F
               ? 0.0F
               : MathHelper.clamp(((float)this.underwaterVisibilityTicks - 100.0F) / 500.0F, 0.0F, 1.0F);
            return _snowmanxx * 0.6F + _snowmanxxx * 0.39999998F;
         }
      }
   }

   @Override
   public boolean isSubmergedInWater() {
      return this.isSubmergedInWater;
   }

   @Override
   protected boolean updateWaterSubmersionState() {
      boolean _snowman = this.isSubmergedInWater;
      boolean _snowmanx = super.updateWaterSubmersionState();
      if (this.isSpectator()) {
         return this.isSubmergedInWater;
      } else {
         if (!_snowman && _snowmanx) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
            this.client.getSoundManager().play(new AmbientSoundLoops.Underwater(this));
         }

         if (_snowman && !_snowmanx) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
         }

         return this.isSubmergedInWater;
      }
   }

   @Override
   public Vec3d method_30951(float _snowman) {
      if (this.client.options.getPerspective().isFirstPerson()) {
         float _snowmanx = MathHelper.lerp(_snowman * 0.5F, this.yaw, this.prevYaw) * (float) (Math.PI / 180.0);
         float _snowmanxx = MathHelper.lerp(_snowman * 0.5F, this.pitch, this.prevPitch) * (float) (Math.PI / 180.0);
         double _snowmanxxx = this.getMainArm() == Arm.RIGHT ? -1.0 : 1.0;
         Vec3d _snowmanxxxx = new Vec3d(0.39 * _snowmanxxx, -0.6, 0.3);
         return _snowmanxxxx.rotateX(-_snowmanxx).rotateY(-_snowmanx).add(this.getCameraPosVec(_snowman));
      } else {
         return super.method_30951(_snowman);
      }
   }
}
