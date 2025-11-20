/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.network;

import java.util.Objects;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CommandBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.StructureBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerActionResponseS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPlayerInteractionManager {
    private static final Logger LOGGER = LogManager.getLogger();
    public ServerWorld world;
    public ServerPlayerEntity player;
    private GameMode gameMode = GameMode.NOT_SET;
    private GameMode previousGameMode = GameMode.NOT_SET;
    private boolean mining;
    private int startMiningTime;
    private BlockPos miningPos = BlockPos.ORIGIN;
    private int tickCounter;
    private boolean failedToMine;
    private BlockPos failedMiningPos = BlockPos.ORIGIN;
    private int failedStartMiningTime;
    private int blockBreakingProgress = -1;

    public ServerPlayerInteractionManager(ServerWorld world) {
        this.world = world;
    }

    public void setGameMode(GameMode gameMode) {
        this.setGameMode(gameMode, gameMode != this.gameMode ? this.gameMode : this.previousGameMode);
    }

    public void setGameMode(GameMode gameMode, GameMode previousGameMode) {
        this.previousGameMode = previousGameMode;
        this.gameMode = gameMode;
        gameMode.setAbilities(this.player.abilities);
        this.player.sendAbilitiesUpdate();
        this.player.server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_GAME_MODE, this.player));
        this.world.updateSleepingPlayers();
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public GameMode getPreviousGameMode() {
        return this.previousGameMode;
    }

    public boolean isSurvivalLike() {
        return this.gameMode.isSurvivalLike();
    }

    public boolean isCreative() {
        return this.gameMode.isCreative();
    }

    public void setGameModeIfNotPresent(GameMode gameMode) {
        if (this.gameMode == GameMode.NOT_SET) {
            this.gameMode = gameMode;
        }
        this.setGameMode(this.gameMode);
    }

    public void update() {
        ++this.tickCounter;
        if (this.failedToMine) {
            BlockState blockState = this.world.getBlockState(this.failedMiningPos);
            if (blockState.isAir()) {
                this.failedToMine = false;
            } else {
                float f = this.continueMining(blockState, this.failedMiningPos, this.failedStartMiningTime);
                if (f >= 1.0f) {
                    this.failedToMine = false;
                    this.tryBreakBlock(this.failedMiningPos);
                }
            }
        } else if (this.mining) {
            BlockState blockState = this.world.getBlockState(this.miningPos);
            if (blockState.isAir()) {
                this.world.setBlockBreakingInfo(this.player.getEntityId(), this.miningPos, -1);
                this.blockBreakingProgress = -1;
                this.mining = false;
            } else {
                this.continueMining(blockState, this.miningPos, this.startMiningTime);
            }
        }
    }

    private float continueMining(BlockState state, BlockPos pos, int n) {
        _snowman = this.tickCounter - n;
        float f = state.calcBlockBreakingDelta(this.player, this.player.world, pos) * (float)(_snowman + 1);
        int _snowman2 = (int)(f * 10.0f);
        if (_snowman2 != this.blockBreakingProgress) {
            this.world.setBlockBreakingInfo(this.player.getEntityId(), pos, _snowman2);
            this.blockBreakingProgress = _snowman2;
        }
        return f;
    }

    public void processBlockBreakingAction(BlockPos pos, PlayerActionC2SPacket.Action action, Direction direction, int worldHeight) {
        double d = this.player.getX() - ((double)pos.getX() + 0.5);
        _snowman = d * d + (_snowman = this.player.getY() - ((double)pos.getY() + 0.5) + 1.5) * _snowman + (_snowman = this.player.getZ() - ((double)pos.getZ() + 0.5)) * _snowman;
        if (_snowman > 36.0) {
            this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "too far"));
            return;
        }
        if (pos.getY() >= worldHeight) {
            this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "too high"));
            return;
        }
        if (action == PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) {
            if (!this.world.canPlayerModifyAt(this.player, pos)) {
                this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "may not interact"));
                return;
            }
            if (this.isCreative()) {
                this.finishMining(pos, action, "creative destroy");
                return;
            }
            if (this.player.isBlockBreakingRestricted(this.world, pos, this.gameMode)) {
                this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "block action restricted"));
                return;
            }
            this.startMiningTime = this.tickCounter;
            float f = 1.0f;
            BlockState _snowman2 = this.world.getBlockState(pos);
            if (!_snowman2.isAir()) {
                _snowman2.onBlockBreakStart(this.world, pos, this.player);
                f = _snowman2.calcBlockBreakingDelta(this.player, this.player.world, pos);
            }
            if (!_snowman2.isAir() && f >= 1.0f) {
                this.finishMining(pos, action, "insta mine");
            } else {
                if (this.mining) {
                    this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(this.miningPos, this.world.getBlockState(this.miningPos), PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, false, "abort destroying since another started (client insta mine, server disagreed)"));
                }
                this.mining = true;
                this.miningPos = pos.toImmutable();
                int n = (int)(f * 10.0f);
                this.world.setBlockBreakingInfo(this.player.getEntityId(), pos, n);
                this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, true, "actual start of destroying"));
                this.blockBreakingProgress = n;
            }
        } else if (action == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
            if (pos.equals(this.miningPos)) {
                int n = this.tickCounter - this.startMiningTime;
                BlockState _snowman3 = this.world.getBlockState(pos);
                if (!_snowman3.isAir()) {
                    float f = _snowman3.calcBlockBreakingDelta(this.player, this.player.world, pos) * (float)(n + 1);
                    if (f >= 0.7f) {
                        this.mining = false;
                        this.world.setBlockBreakingInfo(this.player.getEntityId(), pos, -1);
                        this.finishMining(pos, action, "destroyed");
                        return;
                    }
                    if (!this.failedToMine) {
                        this.mining = false;
                        this.failedToMine = true;
                        this.failedMiningPos = pos;
                        this.failedStartMiningTime = this.startMiningTime;
                    }
                }
            }
            this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, true, "stopped destroying"));
        } else if (action == PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK) {
            this.mining = false;
            if (!Objects.equals(this.miningPos, pos)) {
                LOGGER.warn("Mismatch in destroy block pos: " + this.miningPos + " " + pos);
                this.world.setBlockBreakingInfo(this.player.getEntityId(), this.miningPos, -1);
                this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(this.miningPos, this.world.getBlockState(this.miningPos), action, true, "aborted mismatched destroying"));
            }
            this.world.setBlockBreakingInfo(this.player.getEntityId(), pos, -1);
            this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, true, "aborted destroying"));
        }
    }

    public void finishMining(BlockPos pos, PlayerActionC2SPacket.Action action, String reason) {
        if (this.tryBreakBlock(pos)) {
            this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, true, reason));
        } else {
            this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, reason));
        }
    }

    public boolean tryBreakBlock(BlockPos pos) {
        BlockState blockState = this.world.getBlockState(pos);
        if (!this.player.getMainHandStack().getItem().canMine(blockState, this.world, pos, this.player)) {
            return false;
        }
        BlockEntity _snowman2 = this.world.getBlockEntity(pos);
        Block _snowman3 = blockState.getBlock();
        if ((_snowman3 instanceof CommandBlock || _snowman3 instanceof StructureBlock || _snowman3 instanceof JigsawBlock) && !this.player.isCreativeLevelTwoOp()) {
            this.world.updateListeners(pos, blockState, blockState, 3);
            return false;
        }
        if (this.player.isBlockBreakingRestricted(this.world, pos, this.gameMode)) {
            return false;
        }
        _snowman3.onBreak(this.world, pos, blockState, this.player);
        boolean _snowman4 = this.world.removeBlock(pos, false);
        if (_snowman4) {
            _snowman3.onBroken(this.world, pos, blockState);
        }
        if (this.isCreative()) {
            return true;
        }
        ItemStack _snowman5 = this.player.getMainHandStack();
        ItemStack _snowman6 = _snowman5.copy();
        boolean _snowman7 = this.player.isUsingEffectiveTool(blockState);
        _snowman5.postMine(this.world, blockState, pos, this.player);
        if (_snowman4 && _snowman7) {
            _snowman3.afterBreak(this.world, this.player, pos, blockState, _snowman2, _snowman6);
        }
        return true;
    }

    public ActionResult interactItem(ServerPlayerEntity player, World world, ItemStack stack, Hand hand) {
        if (this.gameMode == GameMode.SPECTATOR) {
            return ActionResult.PASS;
        }
        if (player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            return ActionResult.PASS;
        }
        int n = stack.getCount();
        _snowman = stack.getDamage();
        TypedActionResult<ItemStack> _snowman2 = stack.use(world, player, hand);
        ItemStack _snowman3 = _snowman2.getValue();
        if (_snowman3 == stack && _snowman3.getCount() == n && _snowman3.getMaxUseTime() <= 0 && _snowman3.getDamage() == _snowman) {
            return _snowman2.getResult();
        }
        if (_snowman2.getResult() == ActionResult.FAIL && _snowman3.getMaxUseTime() > 0 && !player.isUsingItem()) {
            return _snowman2.getResult();
        }
        player.setStackInHand(hand, _snowman3);
        if (this.isCreative()) {
            _snowman3.setCount(n);
            if (_snowman3.isDamageable() && _snowman3.getDamage() != _snowman) {
                _snowman3.setDamage(_snowman);
            }
        }
        if (_snowman3.isEmpty()) {
            player.setStackInHand(hand, ItemStack.EMPTY);
        }
        if (!player.isUsingItem()) {
            player.refreshScreenHandler(player.playerScreenHandler);
        }
        return _snowman2.getResult();
    }

    public ActionResult interactBlock(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult) {
        ActionResult _snowman5;
        BlockPos blockPos = hitResult.getBlockPos();
        BlockState _snowman2 = world.getBlockState(blockPos);
        if (this.gameMode == GameMode.SPECTATOR) {
            NamedScreenHandlerFactory namedScreenHandlerFactory = _snowman2.createScreenHandlerFactory(world, blockPos);
            if (namedScreenHandlerFactory != null) {
                player.openHandledScreen(namedScreenHandlerFactory);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }
        boolean bl = !player.getMainHandStack().isEmpty() || !player.getOffHandStack().isEmpty();
        _snowman = player.shouldCancelInteraction() && bl;
        ItemStack _snowman3 = stack.copy();
        if (!_snowman && (_snowman4 = _snowman2.onUse(world, player, hand, hitResult)).isAccepted()) {
            Criteria.ITEM_USED_ON_BLOCK.test(player, blockPos, _snowman3);
            return _snowman4;
        }
        if (stack.isEmpty() || player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            return ActionResult.PASS;
        }
        Object _snowman4 = new ItemUsageContext(player, hand, hitResult);
        if (this.isCreative()) {
            int n = stack.getCount();
            _snowman5 = stack.useOnBlock((ItemUsageContext)_snowman4);
            stack.setCount(n);
        } else {
            _snowman5 = stack.useOnBlock((ItemUsageContext)_snowman4);
        }
        if (_snowman5.isAccepted()) {
            Criteria.ITEM_USED_ON_BLOCK.test(player, blockPos, _snowman3);
        }
        return _snowman5;
    }

    public void setWorld(ServerWorld world) {
        this.world = world;
    }
}

