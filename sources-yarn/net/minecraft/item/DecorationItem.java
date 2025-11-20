package net.minecraft.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DecorationItem extends Item {
   private final EntityType<? extends AbstractDecorationEntity> entityType;

   public DecorationItem(EntityType<? extends AbstractDecorationEntity> type, Item.Settings settings) {
      super(settings);
      this.entityType = type;
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      BlockPos lv = context.getBlockPos();
      Direction lv2 = context.getSide();
      BlockPos lv3 = lv.offset(lv2);
      PlayerEntity lv4 = context.getPlayer();
      ItemStack lv5 = context.getStack();
      if (lv4 != null && !this.canPlaceOn(lv4, lv2, lv5, lv3)) {
         return ActionResult.FAIL;
      } else {
         World lv6 = context.getWorld();
         AbstractDecorationEntity lv7;
         if (this.entityType == EntityType.PAINTING) {
            lv7 = new PaintingEntity(lv6, lv3, lv2);
         } else {
            if (this.entityType != EntityType.ITEM_FRAME) {
               return ActionResult.success(lv6.isClient);
            }

            lv7 = new ItemFrameEntity(lv6, lv3, lv2);
         }

         CompoundTag lv10 = lv5.getTag();
         if (lv10 != null) {
            EntityType.loadFromEntityTag(lv6, lv4, lv7, lv10);
         }

         if (lv7.canStayAttached()) {
            if (!lv6.isClient) {
               lv7.onPlace();
               lv6.spawnEntity(lv7);
            }

            lv5.decrement(1);
            return ActionResult.success(lv6.isClient);
         } else {
            return ActionResult.CONSUME;
         }
      }
   }

   protected boolean canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos) {
      return !side.getAxis().isVertical() && player.canPlaceOn(pos, side, stack);
   }
}
