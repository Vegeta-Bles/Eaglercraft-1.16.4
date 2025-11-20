package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class LeadItem extends Item {
   public LeadItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World _snowman = context.getWorld();
      BlockPos _snowmanx = context.getBlockPos();
      Block _snowmanxx = _snowman.getBlockState(_snowmanx).getBlock();
      if (_snowmanxx.isIn(BlockTags.FENCES)) {
         PlayerEntity _snowmanxxx = context.getPlayer();
         if (!_snowman.isClient && _snowmanxxx != null) {
            attachHeldMobsToBlock(_snowmanxxx, _snowman, _snowmanx);
         }

         return ActionResult.success(_snowman.isClient);
      } else {
         return ActionResult.PASS;
      }
   }

   public static ActionResult attachHeldMobsToBlock(PlayerEntity _snowman, World _snowman, BlockPos _snowman) {
      LeashKnotEntity _snowmanxxx = null;
      boolean _snowmanxxxx = false;
      double _snowmanxxxxx = 7.0;
      int _snowmanxxxxxx = _snowman.getX();
      int _snowmanxxxxxxx = _snowman.getY();
      int _snowmanxxxxxxxx = _snowman.getZ();

      for (MobEntity _snowmanxxxxxxxxx : _snowman.getNonSpectatingEntities(
         MobEntity.class,
         new Box((double)_snowmanxxxxxx - 7.0, (double)_snowmanxxxxxxx - 7.0, (double)_snowmanxxxxxxxx - 7.0, (double)_snowmanxxxxxx + 7.0, (double)_snowmanxxxxxxx + 7.0, (double)_snowmanxxxxxxxx + 7.0)
      )) {
         if (_snowmanxxxxxxxxx.getHoldingEntity() == _snowman) {
            if (_snowmanxxx == null) {
               _snowmanxxx = LeashKnotEntity.getOrCreate(_snowman, _snowman);
            }

            _snowmanxxxxxxxxx.attachLeash(_snowmanxxx, true);
            _snowmanxxxx = true;
         }
      }

      return _snowmanxxxx ? ActionResult.SUCCESS : ActionResult.PASS;
   }
}
