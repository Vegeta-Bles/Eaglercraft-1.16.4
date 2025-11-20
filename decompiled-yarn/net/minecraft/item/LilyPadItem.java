package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class LilyPadItem extends BlockItem {
   public LilyPadItem(Block _snowman, Item.Settings _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      return ActionResult.PASS;
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      BlockHitResult _snowman = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
      BlockHitResult _snowmanx = _snowman.withBlockPos(_snowman.getBlockPos().up());
      ActionResult _snowmanxx = super.useOnBlock(new ItemUsageContext(user, hand, _snowmanx));
      return new TypedActionResult<>(_snowmanxx, user.getStackInHand(hand));
   }
}
