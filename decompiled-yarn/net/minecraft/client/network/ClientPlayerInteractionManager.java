package net.minecraft.client.network;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CommandBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.StructureBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.packet.c2s.play.ButtonClickC2SPacket;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CraftRequestC2SPacket;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PickFromInventoryC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.StatHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientPlayerInteractionManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftClient client;
   private final ClientPlayNetworkHandler networkHandler;
   private BlockPos currentBreakingPos = new BlockPos(-1, -1, -1);
   private ItemStack selectedStack = ItemStack.EMPTY;
   private float currentBreakingProgress;
   private float blockBreakingSoundCooldown;
   private int blockBreakingCooldown;
   private boolean breakingBlock;
   private GameMode gameMode = GameMode.SURVIVAL;
   private GameMode previousGameMode = GameMode.NOT_SET;
   private final Object2ObjectLinkedOpenHashMap<Pair<BlockPos, PlayerActionC2SPacket.Action>, Vec3d> unacknowledgedPlayerActions = new Object2ObjectLinkedOpenHashMap();
   private int lastSelectedSlot;

   public ClientPlayerInteractionManager(MinecraftClient client, ClientPlayNetworkHandler networkHandler) {
      this.client = client;
      this.networkHandler = networkHandler;
   }

   public void copyAbilities(PlayerEntity player) {
      this.gameMode.setAbilities(player.abilities);
   }

   public void setPreviousGameMode(GameMode _snowman) {
      this.previousGameMode = _snowman;
   }

   public void setGameMode(GameMode gameMode) {
      if (gameMode != this.gameMode) {
         this.previousGameMode = this.gameMode;
      }

      this.gameMode = gameMode;
      this.gameMode.setAbilities(this.client.player.abilities);
   }

   public boolean hasStatusBars() {
      return this.gameMode.isSurvivalLike();
   }

   public boolean breakBlock(BlockPos pos) {
      if (this.client.player.isBlockBreakingRestricted(this.client.world, pos, this.gameMode)) {
         return false;
      } else {
         World _snowman = this.client.world;
         BlockState _snowmanx = _snowman.getBlockState(pos);
         if (!this.client.player.getMainHandStack().getItem().canMine(_snowmanx, _snowman, pos, this.client.player)) {
            return false;
         } else {
            Block _snowmanxx = _snowmanx.getBlock();
            if ((_snowmanxx instanceof CommandBlock || _snowmanxx instanceof StructureBlock || _snowmanxx instanceof JigsawBlock) && !this.client.player.isCreativeLevelTwoOp()) {
               return false;
            } else if (_snowmanx.isAir()) {
               return false;
            } else {
               _snowmanxx.onBreak(_snowman, pos, _snowmanx, this.client.player);
               FluidState _snowmanxxx = _snowman.getFluidState(pos);
               boolean _snowmanxxxx = _snowman.setBlockState(pos, _snowmanxxx.getBlockState(), 11);
               if (_snowmanxxxx) {
                  _snowmanxx.onBroken(_snowman, pos, _snowmanx);
               }

               return _snowmanxxxx;
            }
         }
      }
   }

   public boolean attackBlock(BlockPos pos, Direction direction) {
      if (this.client.player.isBlockBreakingRestricted(this.client.world, pos, this.gameMode)) {
         return false;
      } else if (!this.client.world.getWorldBorder().contains(pos)) {
         return false;
      } else {
         if (this.gameMode.isCreative()) {
            BlockState _snowman = this.client.world.getBlockState(pos);
            this.client.getTutorialManager().onBlockAttacked(this.client.world, pos, _snowman, 1.0F);
            this.sendPlayerAction(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
            this.breakBlock(pos);
            this.blockBreakingCooldown = 5;
         } else if (!this.breakingBlock || !this.isCurrentlyBreaking(pos)) {
            if (this.breakingBlock) {
               this.sendPlayerAction(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.currentBreakingPos, direction);
            }

            BlockState _snowman = this.client.world.getBlockState(pos);
            this.client.getTutorialManager().onBlockAttacked(this.client.world, pos, _snowman, 0.0F);
            this.sendPlayerAction(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
            boolean _snowmanx = !_snowman.isAir();
            if (_snowmanx && this.currentBreakingProgress == 0.0F) {
               _snowman.onBlockBreakStart(this.client.world, pos, this.client.player);
            }

            if (_snowmanx && _snowman.calcBlockBreakingDelta(this.client.player, this.client.player.world, pos) >= 1.0F) {
               this.breakBlock(pos);
            } else {
               this.breakingBlock = true;
               this.currentBreakingPos = pos;
               this.selectedStack = this.client.player.getMainHandStack();
               this.currentBreakingProgress = 0.0F;
               this.blockBreakingSoundCooldown = 0.0F;
               this.client
                  .world
                  .setBlockBreakingInfo(this.client.player.getEntityId(), this.currentBreakingPos, (int)(this.currentBreakingProgress * 10.0F) - 1);
            }
         }

         return true;
      }
   }

   public void cancelBlockBreaking() {
      if (this.breakingBlock) {
         BlockState _snowman = this.client.world.getBlockState(this.currentBreakingPos);
         this.client.getTutorialManager().onBlockAttacked(this.client.world, this.currentBreakingPos, _snowman, -1.0F);
         this.sendPlayerAction(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.currentBreakingPos, Direction.DOWN);
         this.breakingBlock = false;
         this.currentBreakingProgress = 0.0F;
         this.client.world.setBlockBreakingInfo(this.client.player.getEntityId(), this.currentBreakingPos, -1);
         this.client.player.resetLastAttackedTicks();
      }
   }

   public boolean updateBlockBreakingProgress(BlockPos pos, Direction direction) {
      this.syncSelectedSlot();
      if (this.blockBreakingCooldown > 0) {
         this.blockBreakingCooldown--;
         return true;
      } else if (this.gameMode.isCreative() && this.client.world.getWorldBorder().contains(pos)) {
         this.blockBreakingCooldown = 5;
         BlockState _snowman = this.client.world.getBlockState(pos);
         this.client.getTutorialManager().onBlockAttacked(this.client.world, pos, _snowman, 1.0F);
         this.sendPlayerAction(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
         this.breakBlock(pos);
         return true;
      } else if (this.isCurrentlyBreaking(pos)) {
         BlockState _snowman = this.client.world.getBlockState(pos);
         if (_snowman.isAir()) {
            this.breakingBlock = false;
            return false;
         } else {
            this.currentBreakingProgress = this.currentBreakingProgress + _snowman.calcBlockBreakingDelta(this.client.player, this.client.player.world, pos);
            if (this.blockBreakingSoundCooldown % 4.0F == 0.0F) {
               BlockSoundGroup _snowmanx = _snowman.getSoundGroup();
               this.client
                  .getSoundManager()
                  .play(new PositionedSoundInstance(_snowmanx.getHitSound(), SoundCategory.BLOCKS, (_snowmanx.getVolume() + 1.0F) / 8.0F, _snowmanx.getPitch() * 0.5F, pos));
            }

            this.blockBreakingSoundCooldown++;
            this.client.getTutorialManager().onBlockAttacked(this.client.world, pos, _snowman, MathHelper.clamp(this.currentBreakingProgress, 0.0F, 1.0F));
            if (this.currentBreakingProgress >= 1.0F) {
               this.breakingBlock = false;
               this.sendPlayerAction(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, direction);
               this.breakBlock(pos);
               this.currentBreakingProgress = 0.0F;
               this.blockBreakingSoundCooldown = 0.0F;
               this.blockBreakingCooldown = 5;
            }

            this.client.world.setBlockBreakingInfo(this.client.player.getEntityId(), this.currentBreakingPos, (int)(this.currentBreakingProgress * 10.0F) - 1);
            return true;
         }
      } else {
         return this.attackBlock(pos, direction);
      }
   }

   public float getReachDistance() {
      return this.gameMode.isCreative() ? 5.0F : 4.5F;
   }

   public void tick() {
      this.syncSelectedSlot();
      if (this.networkHandler.getConnection().isOpen()) {
         this.networkHandler.getConnection().tick();
      } else {
         this.networkHandler.getConnection().handleDisconnection();
      }
   }

   private boolean isCurrentlyBreaking(BlockPos pos) {
      ItemStack _snowman = this.client.player.getMainHandStack();
      boolean _snowmanx = this.selectedStack.isEmpty() && _snowman.isEmpty();
      if (!this.selectedStack.isEmpty() && !_snowman.isEmpty()) {
         _snowmanx = _snowman.getItem() == this.selectedStack.getItem()
            && ItemStack.areTagsEqual(_snowman, this.selectedStack)
            && (_snowman.isDamageable() || _snowman.getDamage() == this.selectedStack.getDamage());
      }

      return pos.equals(this.currentBreakingPos) && _snowmanx;
   }

   private void syncSelectedSlot() {
      int _snowman = this.client.player.inventory.selectedSlot;
      if (_snowman != this.lastSelectedSlot) {
         this.lastSelectedSlot = _snowman;
         this.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.lastSelectedSlot));
      }
   }

   public ActionResult interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult) {
      this.syncSelectedSlot();
      BlockPos _snowman = hitResult.getBlockPos();
      if (!this.client.world.getWorldBorder().contains(_snowman)) {
         return ActionResult.FAIL;
      } else {
         ItemStack _snowmanx = player.getStackInHand(hand);
         if (this.gameMode == GameMode.SPECTATOR) {
            this.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, hitResult));
            return ActionResult.SUCCESS;
         } else {
            boolean _snowmanxx = !player.getMainHandStack().isEmpty() || !player.getOffHandStack().isEmpty();
            boolean _snowmanxxx = player.shouldCancelInteraction() && _snowmanxx;
            if (!_snowmanxxx) {
               ActionResult _snowmanxxxx = world.getBlockState(_snowman).onUse(world, player, hand, hitResult);
               if (_snowmanxxxx.isAccepted()) {
                  this.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, hitResult));
                  return _snowmanxxxx;
               }
            }

            this.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, hitResult));
            if (!_snowmanx.isEmpty() && !player.getItemCooldownManager().isCoolingDown(_snowmanx.getItem())) {
               ItemUsageContext _snowmanxxxx = new ItemUsageContext(player, hand, hitResult);
               ActionResult _snowmanxxxxx;
               if (this.gameMode.isCreative()) {
                  int _snowmanxxxxxx = _snowmanx.getCount();
                  _snowmanxxxxx = _snowmanx.useOnBlock(_snowmanxxxx);
                  _snowmanx.setCount(_snowmanxxxxxx);
               } else {
                  _snowmanxxxxx = _snowmanx.useOnBlock(_snowmanxxxx);
               }

               return _snowmanxxxxx;
            } else {
               return ActionResult.PASS;
            }
         }
      }
   }

   public ActionResult interactItem(PlayerEntity player, World world, Hand hand) {
      if (this.gameMode == GameMode.SPECTATOR) {
         return ActionResult.PASS;
      } else {
         this.syncSelectedSlot();
         this.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(hand));
         ItemStack _snowman = player.getStackInHand(hand);
         if (player.getItemCooldownManager().isCoolingDown(_snowman.getItem())) {
            return ActionResult.PASS;
         } else {
            int _snowmanx = _snowman.getCount();
            TypedActionResult<ItemStack> _snowmanxx = _snowman.use(world, player, hand);
            ItemStack _snowmanxxx = _snowmanxx.getValue();
            if (_snowmanxxx != _snowman) {
               player.setStackInHand(hand, _snowmanxxx);
            }

            return _snowmanxx.getResult();
         }
      }
   }

   public ClientPlayerEntity createPlayer(ClientWorld world, StatHandler statHandler, ClientRecipeBook recipeBook) {
      return this.createPlayer(world, statHandler, recipeBook, false, false);
   }

   public ClientPlayerEntity createPlayer(ClientWorld world, StatHandler statHandler, ClientRecipeBook recipeBook, boolean lastSneaking, boolean lastSprinting) {
      return new ClientPlayerEntity(this.client, world, this.networkHandler, statHandler, recipeBook, lastSneaking, lastSprinting);
   }

   public void attackEntity(PlayerEntity player, Entity target) {
      this.syncSelectedSlot();
      this.networkHandler.sendPacket(new PlayerInteractEntityC2SPacket(target, player.isSneaking()));
      if (this.gameMode != GameMode.SPECTATOR) {
         player.attack(target);
         player.resetLastAttackedTicks();
      }
   }

   public ActionResult interactEntity(PlayerEntity player, Entity entity, Hand hand) {
      this.syncSelectedSlot();
      this.networkHandler.sendPacket(new PlayerInteractEntityC2SPacket(entity, hand, player.isSneaking()));
      return this.gameMode == GameMode.SPECTATOR ? ActionResult.PASS : player.interact(entity, hand);
   }

   public ActionResult interactEntityAtLocation(PlayerEntity player, Entity _snowman, EntityHitResult hitResult, Hand _snowman) {
      this.syncSelectedSlot();
      Vec3d _snowmanxx = hitResult.getPos().subtract(_snowman.getX(), _snowman.getY(), _snowman.getZ());
      this.networkHandler.sendPacket(new PlayerInteractEntityC2SPacket(_snowman, _snowman, _snowmanxx, player.isSneaking()));
      return this.gameMode == GameMode.SPECTATOR ? ActionResult.PASS : _snowman.interactAt(player, _snowmanxx, _snowman);
   }

   public ItemStack clickSlot(int syncId, int slotId, int clickData, SlotActionType actionType, PlayerEntity player) {
      short _snowman = player.currentScreenHandler.getNextActionId(player.inventory);
      ItemStack _snowmanx = player.currentScreenHandler.onSlotClick(slotId, clickData, actionType, player);
      this.networkHandler.sendPacket(new ClickSlotC2SPacket(syncId, slotId, clickData, actionType, _snowmanx, _snowman));
      return _snowmanx;
   }

   public void clickRecipe(int syncId, Recipe<?> recipe, boolean craftAll) {
      this.networkHandler.sendPacket(new CraftRequestC2SPacket(syncId, recipe, craftAll));
   }

   public void clickButton(int syncId, int buttonId) {
      this.networkHandler.sendPacket(new ButtonClickC2SPacket(syncId, buttonId));
   }

   public void clickCreativeStack(ItemStack stack, int slotId) {
      if (this.gameMode.isCreative()) {
         this.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(slotId, stack));
      }
   }

   public void dropCreativeStack(ItemStack stack) {
      if (this.gameMode.isCreative() && !stack.isEmpty()) {
         this.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(-1, stack));
      }
   }

   public void stopUsingItem(PlayerEntity player) {
      this.syncSelectedSlot();
      this.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
      player.stopUsingItem();
   }

   public boolean hasExperienceBar() {
      return this.gameMode.isSurvivalLike();
   }

   public boolean hasLimitedAttackSpeed() {
      return !this.gameMode.isCreative();
   }

   public boolean hasCreativeInventory() {
      return this.gameMode.isCreative();
   }

   public boolean hasExtendedReach() {
      return this.gameMode.isCreative();
   }

   public boolean hasRidingInventory() {
      return this.client.player.hasVehicle() && this.client.player.getVehicle() instanceof HorseBaseEntity;
   }

   public boolean isFlyingLocked() {
      return this.gameMode == GameMode.SPECTATOR;
   }

   public GameMode getPreviousGameMode() {
      return this.previousGameMode;
   }

   public GameMode getCurrentGameMode() {
      return this.gameMode;
   }

   public boolean isBreakingBlock() {
      return this.breakingBlock;
   }

   public void pickFromInventory(int slot) {
      this.networkHandler.sendPacket(new PickFromInventoryC2SPacket(slot));
   }

   private void sendPlayerAction(PlayerActionC2SPacket.Action action, BlockPos pos, Direction direction) {
      ClientPlayerEntity _snowman = this.client.player;
      this.unacknowledgedPlayerActions.put(Pair.of(pos, action), _snowman.getPos());
      this.networkHandler.sendPacket(new PlayerActionC2SPacket(action, pos, direction));
   }

   public void processPlayerActionResponse(ClientWorld world, BlockPos pos, BlockState state, PlayerActionC2SPacket.Action action, boolean approved) {
      Vec3d _snowman = (Vec3d)this.unacknowledgedPlayerActions.remove(Pair.of(pos, action));
      BlockState _snowmanx = world.getBlockState(pos);
      if ((_snowman == null || !approved || action != PlayerActionC2SPacket.Action.START_DESTROY_BLOCK && _snowmanx != state) && _snowmanx != state) {
         world.setBlockStateWithoutNeighborUpdates(pos, state);
         PlayerEntity _snowmanxx = this.client.player;
         if (_snowman != null && world == _snowmanxx.world && _snowmanxx.method_30632(pos, state)) {
            _snowmanxx.method_30634(_snowman.x, _snowman.y, _snowman.z);
         }
      }

      while (this.unacknowledgedPlayerActions.size() >= 50) {
         Pair<BlockPos, PlayerActionC2SPacket.Action> _snowmanxx = (Pair<BlockPos, PlayerActionC2SPacket.Action>)this.unacknowledgedPlayerActions.firstKey();
         this.unacknowledgedPlayerActions.removeFirst();
         LOGGER.error("Too many unacked block actions, dropping " + _snowmanxx);
      }
   }
}
