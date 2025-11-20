package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class EndCrystalItem extends Item {
   public EndCrystalItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World _snowman = context.getWorld();
      BlockPos _snowmanx = context.getBlockPos();
      BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
      if (!_snowmanxx.isOf(Blocks.OBSIDIAN) && !_snowmanxx.isOf(Blocks.BEDROCK)) {
         return ActionResult.FAIL;
      } else {
         BlockPos _snowmanxxx = _snowmanx.up();
         if (!_snowman.isAir(_snowmanxxx)) {
            return ActionResult.FAIL;
         } else {
            double _snowmanxxxx = (double)_snowmanxxx.getX();
            double _snowmanxxxxx = (double)_snowmanxxx.getY();
            double _snowmanxxxxxx = (double)_snowmanxxx.getZ();
            List<Entity> _snowmanxxxxxxx = _snowman.getOtherEntities(null, new Box(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxx + 1.0, _snowmanxxxxx + 2.0, _snowmanxxxxxx + 1.0));
            if (!_snowmanxxxxxxx.isEmpty()) {
               return ActionResult.FAIL;
            } else {
               if (_snowman instanceof ServerWorld) {
                  EndCrystalEntity _snowmanxxxxxxxx = new EndCrystalEntity(_snowman, _snowmanxxxx + 0.5, _snowmanxxxxx, _snowmanxxxxxx + 0.5);
                  _snowmanxxxxxxxx.setShowBottom(false);
                  _snowman.spawnEntity(_snowmanxxxxxxxx);
                  EnderDragonFight _snowmanxxxxxxxxx = ((ServerWorld)_snowman).getEnderDragonFight();
                  if (_snowmanxxxxxxxxx != null) {
                     _snowmanxxxxxxxxx.respawnDragon();
                  }
               }

               context.getStack().decrement(1);
               return ActionResult.success(_snowman.isClient);
            }
         }
      }
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return true;
   }
}
