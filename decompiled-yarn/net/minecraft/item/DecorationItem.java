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
      BlockPos _snowman = context.getBlockPos();
      Direction _snowmanx = context.getSide();
      BlockPos _snowmanxx = _snowman.offset(_snowmanx);
      PlayerEntity _snowmanxxx = context.getPlayer();
      ItemStack _snowmanxxxx = context.getStack();
      if (_snowmanxxx != null && !this.canPlaceOn(_snowmanxxx, _snowmanx, _snowmanxxxx, _snowmanxx)) {
         return ActionResult.FAIL;
      } else {
         World _snowmanxxxxx = context.getWorld();
         AbstractDecorationEntity _snowmanxxxxxx;
         if (this.entityType == EntityType.PAINTING) {
            _snowmanxxxxxx = new PaintingEntity(_snowmanxxxxx, _snowmanxx, _snowmanx);
         } else {
            if (this.entityType != EntityType.ITEM_FRAME) {
               return ActionResult.success(_snowmanxxxxx.isClient);
            }

            _snowmanxxxxxx = new ItemFrameEntity(_snowmanxxxxx, _snowmanxx, _snowmanx);
         }

         CompoundTag _snowmanxxxxxxx = _snowmanxxxx.getTag();
         if (_snowmanxxxxxxx != null) {
            EntityType.loadFromEntityTag(_snowmanxxxxx, _snowmanxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
         }

         if (_snowmanxxxxxx.canStayAttached()) {
            if (!_snowmanxxxxx.isClient) {
               _snowmanxxxxxx.onPlace();
               _snowmanxxxxx.spawnEntity(_snowmanxxxxxx);
            }

            _snowmanxxxx.decrement(1);
            return ActionResult.success(_snowmanxxxxx.isClient);
         } else {
            return ActionResult.CONSUME;
         }
      }
   }

   protected boolean canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos) {
      return !side.getAxis().isVertical() && player.canPlaceOn(pos, side, stack);
   }
}
