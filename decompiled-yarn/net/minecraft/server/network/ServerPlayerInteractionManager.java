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
      this.tickCounter++;
      if (this.failedToMine) {
         BlockState _snowman = this.world.getBlockState(this.failedMiningPos);
         if (_snowman.isAir()) {
            this.failedToMine = false;
         } else {
            float _snowmanx = this.continueMining(_snowman, this.failedMiningPos, this.failedStartMiningTime);
            if (_snowmanx >= 1.0F) {
               this.failedToMine = false;
               this.tryBreakBlock(this.failedMiningPos);
            }
         }
      } else if (this.mining) {
         BlockState _snowman = this.world.getBlockState(this.miningPos);
         if (_snowman.isAir()) {
            this.world.setBlockBreakingInfo(this.player.getEntityId(), this.miningPos, -1);
            this.blockBreakingProgress = -1;
            this.mining = false;
         } else {
            this.continueMining(_snowman, this.miningPos, this.startMiningTime);
         }
      }
   }

   private float continueMining(BlockState state, BlockPos pos, int _snowman) {
      int _snowmanx = this.tickCounter - _snowman;
      float _snowmanxx = state.calcBlockBreakingDelta(this.player, this.player.world, pos) * (float)(_snowmanx + 1);
      int _snowmanxxx = (int)(_snowmanxx * 10.0F);
      if (_snowmanxxx != this.blockBreakingProgress) {
         this.world.setBlockBreakingInfo(this.player.getEntityId(), pos, _snowmanxxx);
         this.blockBreakingProgress = _snowmanxxx;
      }

      return _snowmanxx;
   }

   public void processBlockBreakingAction(BlockPos pos, PlayerActionC2SPacket.Action action, Direction direction, int worldHeight) {
      double _snowman = this.player.getX() - ((double)pos.getX() + 0.5);
      double _snowmanx = this.player.getY() - ((double)pos.getY() + 0.5) + 1.5;
      double _snowmanxx = this.player.getZ() - ((double)pos.getZ() + 0.5);
      double _snowmanxxx = _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
      if (_snowmanxxx > 36.0) {
         this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "too far"));
      } else if (pos.getY() >= worldHeight) {
         this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "too high"));
      } else {
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
               this.player
                  .networkHandler
                  .sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "block action restricted"));
               return;
            }

            this.startMiningTime = this.tickCounter;
            float _snowmanxxxx = 1.0F;
            BlockState _snowmanxxxxx = this.world.getBlockState(pos);
            if (!_snowmanxxxxx.isAir()) {
               _snowmanxxxxx.onBlockBreakStart(this.world, pos, this.player);
               _snowmanxxxx = _snowmanxxxxx.calcBlockBreakingDelta(this.player, this.player.world, pos);
            }

            if (!_snowmanxxxxx.isAir() && _snowmanxxxx >= 1.0F) {
               this.finishMining(pos, action, "insta mine");
            } else {
               if (this.mining) {
                  this.player
                     .networkHandler
                     .sendPacket(
                        new PlayerActionResponseS2CPacket(
                           this.miningPos,
                           this.world.getBlockState(this.miningPos),
                           PlayerActionC2SPacket.Action.START_DESTROY_BLOCK,
                           false,
                           "abort destroying since another started (client insta mine, server disagreed)"
                        )
                     );
               }

               this.mining = true;
               this.miningPos = pos.toImmutable();
               int _snowmanxxxxxx = (int)(_snowmanxxxx * 10.0F);
               this.world.setBlockBreakingInfo(this.player.getEntityId(), pos, _snowmanxxxxxx);
               this.player
                  .networkHandler
                  .sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, true, "actual start of destroying"));
               this.blockBreakingProgress = _snowmanxxxxxx;
            }
         } else if (action == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
            if (pos.equals(this.miningPos)) {
               int _snowmanxxxxxx = this.tickCounter - this.startMiningTime;
               BlockState _snowmanxxxxxxx = this.world.getBlockState(pos);
               if (!_snowmanxxxxxxx.isAir()) {
                  float _snowmanxxxxxxxx = _snowmanxxxxxxx.calcBlockBreakingDelta(this.player, this.player.world, pos) * (float)(_snowmanxxxxxx + 1);
                  if (_snowmanxxxxxxxx >= 0.7F) {
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
               this.player
                  .networkHandler
                  .sendPacket(
                     new PlayerActionResponseS2CPacket(this.miningPos, this.world.getBlockState(this.miningPos), action, true, "aborted mismatched destroying")
                  );
            }

            this.world.setBlockBreakingInfo(this.player.getEntityId(), pos, -1);
            this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, true, "aborted destroying"));
         }
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
      BlockState _snowman = this.world.getBlockState(pos);
      if (!this.player.getMainHandStack().getItem().canMine(_snowman, this.world, pos, this.player)) {
         return false;
      } else {
         BlockEntity _snowmanx = this.world.getBlockEntity(pos);
         Block _snowmanxx = _snowman.getBlock();
         if ((_snowmanxx instanceof CommandBlock || _snowmanxx instanceof StructureBlock || _snowmanxx instanceof JigsawBlock) && !this.player.isCreativeLevelTwoOp()) {
            this.world.updateListeners(pos, _snowman, _snowman, 3);
            return false;
         } else if (this.player.isBlockBreakingRestricted(this.world, pos, this.gameMode)) {
            return false;
         } else {
            _snowmanxx.onBreak(this.world, pos, _snowman, this.player);
            boolean _snowmanxxx = this.world.removeBlock(pos, false);
            if (_snowmanxxx) {
               _snowmanxx.onBroken(this.world, pos, _snowman);
            }

            if (this.isCreative()) {
               return true;
            } else {
               ItemStack _snowmanxxxx = this.player.getMainHandStack();
               ItemStack _snowmanxxxxx = _snowmanxxxx.copy();
               boolean _snowmanxxxxxx = this.player.isUsingEffectiveTool(_snowman);
               _snowmanxxxx.postMine(this.world, _snowman, pos, this.player);
               if (_snowmanxxx && _snowmanxxxxxx) {
                  _snowmanxx.afterBreak(this.world, this.player, pos, _snowman, _snowmanx, _snowmanxxxxx);
               }

               return true;
            }
         }
      }
   }

   public ActionResult interactItem(ServerPlayerEntity player, World world, ItemStack stack, Hand hand) {
      if (this.gameMode == GameMode.SPECTATOR) {
         return ActionResult.PASS;
      } else if (player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
         return ActionResult.PASS;
      } else {
         int _snowman = stack.getCount();
         int _snowmanx = stack.getDamage();
         TypedActionResult<ItemStack> _snowmanxx = stack.use(world, player, hand);
         ItemStack _snowmanxxx = _snowmanxx.getValue();
         if (_snowmanxxx == stack && _snowmanxxx.getCount() == _snowman && _snowmanxxx.getMaxUseTime() <= 0 && _snowmanxxx.getDamage() == _snowmanx) {
            return _snowmanxx.getResult();
         } else if (_snowmanxx.getResult() == ActionResult.FAIL && _snowmanxxx.getMaxUseTime() > 0 && !player.isUsingItem()) {
            return _snowmanxx.getResult();
         } else {
            player.setStackInHand(hand, _snowmanxxx);
            if (this.isCreative()) {
               _snowmanxxx.setCount(_snowman);
               if (_snowmanxxx.isDamageable() && _snowmanxxx.getDamage() != _snowmanx) {
                  _snowmanxxx.setDamage(_snowmanx);
               }
            }

            if (_snowmanxxx.isEmpty()) {
               player.setStackInHand(hand, ItemStack.EMPTY);
            }

            if (!player.isUsingItem()) {
               player.refreshScreenHandler(player.playerScreenHandler);
            }

            return _snowmanxx.getResult();
         }
      }
   }

   public ActionResult interactBlock(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult) {
      BlockPos _snowman = hitResult.getBlockPos();
      BlockState _snowmanx = world.getBlockState(_snowman);
      if (this.gameMode == GameMode.SPECTATOR) {
         NamedScreenHandlerFactory _snowmanxx = _snowmanx.createScreenHandlerFactory(world, _snowman);
         if (_snowmanxx != null) {
            player.openHandledScreen(_snowmanxx);
            return ActionResult.SUCCESS;
         } else {
            return ActionResult.PASS;
         }
      } else {
         boolean _snowmanxx = !player.getMainHandStack().isEmpty() || !player.getOffHandStack().isEmpty();
         boolean _snowmanxxx = player.shouldCancelInteraction() && _snowmanxx;
         ItemStack _snowmanxxxx = stack.copy();
         if (!_snowmanxxx) {
            ActionResult _snowmanxxxxx = _snowmanx.onUse(world, player, hand, hitResult);
            if (_snowmanxxxxx.isAccepted()) {
               Criteria.ITEM_USED_ON_BLOCK.test(player, _snowman, _snowmanxxxx);
               return _snowmanxxxxx;
            }
         }

         if (!stack.isEmpty() && !player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            ItemUsageContext _snowmanxxxxx = new ItemUsageContext(player, hand, hitResult);
            ActionResult _snowmanxxxxxx;
            if (this.isCreative()) {
               int _snowmanxxxxxxx = stack.getCount();
               _snowmanxxxxxx = stack.useOnBlock(_snowmanxxxxx);
               stack.setCount(_snowmanxxxxxxx);
            } else {
               _snowmanxxxxxx = stack.useOnBlock(_snowmanxxxxx);
            }

            if (_snowmanxxxxxx.isAccepted()) {
               Criteria.ITEM_USED_ON_BLOCK.test(player, _snowman, _snowmanxxxx);
            }

            return _snowmanxxxxxx;
         } else {
            return ActionResult.PASS;
         }
      }
   }

   public void setWorld(ServerWorld world) {
      this.world = world;
   }
}
