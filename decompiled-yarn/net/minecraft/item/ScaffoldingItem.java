package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ScaffoldingItem extends BlockItem {
   public ScaffoldingItem(Block _snowman, Item.Settings _snowman) {
      super(_snowman, _snowman);
   }

   @Nullable
   @Override
   public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
      BlockPos _snowman = context.getBlockPos();
      World _snowmanx = context.getWorld();
      BlockState _snowmanxx = _snowmanx.getBlockState(_snowman);
      Block _snowmanxxx = this.getBlock();
      if (!_snowmanxx.isOf(_snowmanxxx)) {
         return ScaffoldingBlock.calculateDistance(_snowmanx, _snowman) == 7 ? null : context;
      } else {
         Direction _snowmanxxxx;
         if (context.shouldCancelInteraction()) {
            _snowmanxxxx = context.hitsInsideBlock() ? context.getSide().getOpposite() : context.getSide();
         } else {
            _snowmanxxxx = context.getSide() == Direction.UP ? context.getPlayerFacing() : Direction.UP;
         }

         int _snowmanxxxxx = 0;
         BlockPos.Mutable _snowmanxxxxxx = _snowman.mutableCopy().move(_snowmanxxxx);

         while (_snowmanxxxxx < 7) {
            if (!_snowmanx.isClient && !World.isInBuildLimit(_snowmanxxxxxx)) {
               PlayerEntity _snowmanxxxxxxx = context.getPlayer();
               int _snowmanxxxxxxxx = _snowmanx.getHeight();
               if (_snowmanxxxxxxx instanceof ServerPlayerEntity && _snowmanxxxxxx.getY() >= _snowmanxxxxxxxx) {
                  GameMessageS2CPacket _snowmanxxxxxxxxx = new GameMessageS2CPacket(
                     new TranslatableText("build.tooHigh", _snowmanxxxxxxxx).formatted(Formatting.RED), MessageType.GAME_INFO, Util.NIL_UUID
                  );
                  ((ServerPlayerEntity)_snowmanxxxxxxx).networkHandler.sendPacket(_snowmanxxxxxxxxx);
               }
               break;
            }

            _snowmanxx = _snowmanx.getBlockState(_snowmanxxxxxx);
            if (!_snowmanxx.isOf(this.getBlock())) {
               if (_snowmanxx.canReplace(context)) {
                  return ItemPlacementContext.offset(context, _snowmanxxxxxx, _snowmanxxxx);
               }
               break;
            }

            _snowmanxxxxxx.move(_snowmanxxxx);
            if (_snowmanxxxx.getAxis().isHorizontal()) {
               _snowmanxxxxx++;
            }
         }

         return null;
      }
   }

   @Override
   protected boolean checkStatePlacement() {
      return false;
   }
}
