/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.network;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CommandBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.StructureBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
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

    public void setPreviousGameMode(GameMode gameMode) {
        this.previousGameMode = gameMode;
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
        }
        ClientWorld clientWorld = this.client.world;
        BlockState _snowman2 = clientWorld.getBlockState(pos);
        if (!this.client.player.getMainHandStack().getItem().canMine(_snowman2, clientWorld, pos, this.client.player)) {
            return false;
        }
        Block _snowman3 = _snowman2.getBlock();
        if ((_snowman3 instanceof CommandBlock || _snowman3 instanceof StructureBlock || _snowman3 instanceof JigsawBlock) && !this.client.player.isCreativeLevelTwoOp()) {
            return false;
        }
        if (_snowman2.isAir()) {
            return false;
        }
        _snowman3.onBreak(clientWorld, pos, _snowman2, this.client.player);
        FluidState _snowman4 = clientWorld.getFluidState(pos);
        boolean _snowman5 = clientWorld.setBlockState(pos, _snowman4.getBlockState(), 11);
        if (_snowman5) {
            _snowman3.onBroken(clientWorld, pos, _snowman2);
        }
        return _snowman5;
    }

    public boolean attackBlock(BlockPos pos, Direction direction) {
        if (this.client.player.isBlockBreakingRestricted(this.client.world, pos, this.gameMode)) {
            return false;
        }
        if (!this.client.world.getWorldBorder().contains(pos)) {
            return false;
        }
        if (this.gameMode.isCreative()) {
            BlockState blockState = this.client.world.getBlockState(pos);
            this.client.getTutorialManager().onBlockAttacked(this.client.world, pos, blockState, 1.0f);
            this.sendPlayerAction(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
            this.breakBlock(pos);
            this.blockBreakingCooldown = 5;
        } else if (!this.breakingBlock || !this.isCurrentlyBreaking(pos)) {
            if (this.breakingBlock) {
                this.sendPlayerAction(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.currentBreakingPos, direction);
            }
            BlockState blockState = this.client.world.getBlockState(pos);
            this.client.getTutorialManager().onBlockAttacked(this.client.world, pos, blockState, 0.0f);
            this.sendPlayerAction(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
            boolean bl = _snowman = !blockState.isAir();
            if (_snowman && this.currentBreakingProgress == 0.0f) {
                blockState.onBlockBreakStart(this.client.world, pos, this.client.player);
            }
            if (_snowman && blockState.calcBlockBreakingDelta(this.client.player, this.client.player.world, pos) >= 1.0f) {
                this.breakBlock(pos);
            } else {
                this.breakingBlock = true;
                this.currentBreakingPos = pos;
                this.selectedStack = this.client.player.getMainHandStack();
                this.currentBreakingProgress = 0.0f;
                this.blockBreakingSoundCooldown = 0.0f;
                this.client.world.setBlockBreakingInfo(this.client.player.getEntityId(), this.currentBreakingPos, (int)(this.currentBreakingProgress * 10.0f) - 1);
            }
        }
        return true;
    }

    public void cancelBlockBreaking() {
        if (this.breakingBlock) {
            BlockState blockState = this.client.world.getBlockState(this.currentBreakingPos);
            this.client.getTutorialManager().onBlockAttacked(this.client.world, this.currentBreakingPos, blockState, -1.0f);
            this.sendPlayerAction(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.currentBreakingPos, Direction.DOWN);
            this.breakingBlock = false;
            this.currentBreakingProgress = 0.0f;
            this.client.world.setBlockBreakingInfo(this.client.player.getEntityId(), this.currentBreakingPos, -1);
            this.client.player.resetLastAttackedTicks();
        }
    }

    public boolean updateBlockBreakingProgress(BlockPos pos, Direction direction) {
        this.syncSelectedSlot();
        if (this.blockBreakingCooldown > 0) {
            --this.blockBreakingCooldown;
            return true;
        }
        if (this.gameMode.isCreative() && this.client.world.getWorldBorder().contains(pos)) {
            this.blockBreakingCooldown = 5;
            BlockState blockState = this.client.world.getBlockState(pos);
            this.client.getTutorialManager().onBlockAttacked(this.client.world, pos, blockState, 1.0f);
            this.sendPlayerAction(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
            this.breakBlock(pos);
            return true;
        }
        if (this.isCurrentlyBreaking(pos)) {
            BlockState blockState = this.client.world.getBlockState(pos);
            if (blockState.isAir()) {
                this.breakingBlock = false;
                return false;
            }
            this.currentBreakingProgress += blockState.calcBlockBreakingDelta(this.client.player, this.client.player.world, pos);
            if (this.blockBreakingSoundCooldown % 4.0f == 0.0f) {
                BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
                this.client.getSoundManager().play(new PositionedSoundInstance(blockSoundGroup.getHitSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 8.0f, blockSoundGroup.getPitch() * 0.5f, pos));
            }
            this.blockBreakingSoundCooldown += 1.0f;
            this.client.getTutorialManager().onBlockAttacked(this.client.world, pos, blockState, MathHelper.clamp(this.currentBreakingProgress, 0.0f, 1.0f));
            if (this.currentBreakingProgress >= 1.0f) {
                this.breakingBlock = false;
                this.sendPlayerAction(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, direction);
                this.breakBlock(pos);
                this.currentBreakingProgress = 0.0f;
                this.blockBreakingSoundCooldown = 0.0f;
                this.blockBreakingCooldown = 5;
            }
        } else {
            return this.attackBlock(pos, direction);
        }
        this.client.world.setBlockBreakingInfo(this.client.player.getEntityId(), this.currentBreakingPos, (int)(this.currentBreakingProgress * 10.0f) - 1);
        return true;
    }

    public float getReachDistance() {
        if (this.gameMode.isCreative()) {
            return 5.0f;
        }
        return 4.5f;
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
        boolean bl;
        ItemStack itemStack = this.client.player.getMainHandStack();
        boolean bl2 = bl = this.selectedStack.isEmpty() && itemStack.isEmpty();
        if (!this.selectedStack.isEmpty() && !itemStack.isEmpty()) {
            bl = itemStack.getItem() == this.selectedStack.getItem() && ItemStack.areTagsEqual(itemStack, this.selectedStack) && (itemStack.isDamageable() || itemStack.getDamage() == this.selectedStack.getDamage());
        }
        return pos.equals(this.currentBreakingPos) && bl;
    }

    private void syncSelectedSlot() {
        int n = this.client.player.inventory.selectedSlot;
        if (n != this.lastSelectedSlot) {
            this.lastSelectedSlot = n;
            this.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.lastSelectedSlot));
        }
    }

    public ActionResult interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult) {
        ActionResult _snowman5;
        this.syncSelectedSlot();
        BlockPos blockPos = hitResult.getBlockPos();
        if (!this.client.world.getWorldBorder().contains(blockPos)) {
            return ActionResult.FAIL;
        }
        ItemStack _snowman2 = player.getStackInHand(hand);
        if (this.gameMode == GameMode.SPECTATOR) {
            this.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, hitResult));
            return ActionResult.SUCCESS;
        }
        boolean _snowman3 = !player.getMainHandStack().isEmpty() || !player.getOffHandStack().isEmpty();
        boolean bl = _snowman = player.shouldCancelInteraction() && _snowman3;
        if (!_snowman && (_snowman5 = world.getBlockState(blockPos).onUse(world, player, hand, hitResult)).isAccepted()) {
            this.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, hitResult));
            return _snowman5;
        }
        this.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, hitResult));
        if (_snowman2.isEmpty() || player.getItemCooldownManager().isCoolingDown(_snowman2.getItem())) {
            return ActionResult.PASS;
        }
        ItemUsageContext _snowman4 = new ItemUsageContext(player, hand, hitResult);
        if (this.gameMode.isCreative()) {
            int n = _snowman2.getCount();
            _snowman5 = _snowman2.useOnBlock(_snowman4);
            _snowman2.setCount(n);
        } else {
            _snowman5 = _snowman2.useOnBlock(_snowman4);
        }
        return _snowman5;
    }

    public ActionResult interactItem(PlayerEntity player, World world, Hand hand) {
        if (this.gameMode == GameMode.SPECTATOR) {
            return ActionResult.PASS;
        }
        this.syncSelectedSlot();
        this.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(hand));
        ItemStack itemStack = player.getStackInHand(hand);
        if (player.getItemCooldownManager().isCoolingDown(itemStack.getItem())) {
            return ActionResult.PASS;
        }
        int _snowman2 = itemStack.getCount();
        TypedActionResult<ItemStack> _snowman3 = itemStack.use(world, player, hand);
        _snowman = _snowman3.getValue();
        if (_snowman != itemStack) {
            player.setStackInHand(hand, _snowman);
        }
        return _snowman3.getResult();
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
        if (this.gameMode == GameMode.SPECTATOR) {
            return ActionResult.PASS;
        }
        return player.interact(entity, hand);
    }

    public ActionResult interactEntityAtLocation(PlayerEntity player, Entity entity, EntityHitResult hitResult, Hand hand) {
        this.syncSelectedSlot();
        Vec3d vec3d = hitResult.getPos().subtract(entity.getX(), entity.getY(), entity.getZ());
        this.networkHandler.sendPacket(new PlayerInteractEntityC2SPacket(entity, hand, vec3d, player.isSneaking()));
        if (this.gameMode == GameMode.SPECTATOR) {
            return ActionResult.PASS;
        }
        return entity.interactAt(player, vec3d, hand);
    }

    public ItemStack clickSlot(int syncId, int slotId, int clickData, SlotActionType actionType, PlayerEntity player) {
        short s = player.currentScreenHandler.getNextActionId(player.inventory);
        ItemStack _snowman2 = player.currentScreenHandler.onSlotClick(slotId, clickData, actionType, player);
        this.networkHandler.sendPacket(new ClickSlotC2SPacket(syncId, slotId, clickData, actionType, _snowman2, s));
        return _snowman2;
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
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        this.unacknowledgedPlayerActions.put((Object)Pair.of((Object)pos, (Object)((Object)action)), (Object)clientPlayerEntity.getPos());
        this.networkHandler.sendPacket(new PlayerActionC2SPacket(action, pos, direction));
    }

    public void processPlayerActionResponse(ClientWorld world, BlockPos pos, BlockState state, PlayerActionC2SPacket.Action action, boolean approved) {
        ClientPlayerEntity clientPlayerEntity;
        Vec3d vec3d = (Vec3d)this.unacknowledgedPlayerActions.remove((Object)Pair.of((Object)pos, (Object)((Object)action)));
        BlockState _snowman2 = world.getBlockState(pos);
        if ((vec3d == null || !approved || action != PlayerActionC2SPacket.Action.START_DESTROY_BLOCK && _snowman2 != state) && _snowman2 != state) {
            world.setBlockStateWithoutNeighborUpdates(pos, state);
            clientPlayerEntity = this.client.player;
            if (vec3d != null && world == clientPlayerEntity.world && clientPlayerEntity.method_30632(pos, state)) {
                clientPlayerEntity.method_30634(vec3d.x, vec3d.y, vec3d.z);
            }
        }
        while (this.unacknowledgedPlayerActions.size() >= 50) {
            clientPlayerEntity = (Pair)this.unacknowledgedPlayerActions.firstKey();
            this.unacknowledgedPlayerActions.removeFirst();
            LOGGER.error("Too many unacked block actions, dropping " + clientPlayerEntity);
        }
    }
}

