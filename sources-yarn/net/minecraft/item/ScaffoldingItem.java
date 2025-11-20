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
   public ScaffoldingItem(Block arg, Item.Settings arg2) {
      super(arg, arg2);
   }

   @Nullable
   @Override
   public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
      BlockPos lv = context.getBlockPos();
      World lv2 = context.getWorld();
      BlockState lv3 = lv2.getBlockState(lv);
      Block lv4 = this.getBlock();
      if (!lv3.isOf(lv4)) {
         return ScaffoldingBlock.calculateDistance(lv2, lv) == 7 ? null : context;
      } else {
         Direction lv5;
         if (context.shouldCancelInteraction()) {
            lv5 = context.hitsInsideBlock() ? context.getSide().getOpposite() : context.getSide();
         } else {
            lv5 = context.getSide() == Direction.UP ? context.getPlayerFacing() : Direction.UP;
         }

         int i = 0;
         BlockPos.Mutable lv7 = lv.mutableCopy().move(lv5);

         while (i < 7) {
            if (!lv2.isClient && !World.isInBuildLimit(lv7)) {
               PlayerEntity lv8 = context.getPlayer();
               int j = lv2.getHeight();
               if (lv8 instanceof ServerPlayerEntity && lv7.getY() >= j) {
                  GameMessageS2CPacket lv9 = new GameMessageS2CPacket(
                     new TranslatableText("build.tooHigh", j).formatted(Formatting.RED), MessageType.GAME_INFO, Util.NIL_UUID
                  );
                  ((ServerPlayerEntity)lv8).networkHandler.sendPacket(lv9);
               }
               break;
            }

            lv3 = lv2.getBlockState(lv7);
            if (!lv3.isOf(this.getBlock())) {
               if (lv3.canReplace(context)) {
                  return ItemPlacementContext.offset(context, lv7, lv5);
               }
               break;
            }

            lv7.move(lv5);
            if (lv5.getAxis().isHorizontal()) {
               i++;
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
