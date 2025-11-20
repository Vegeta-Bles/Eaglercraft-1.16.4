package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemPlacementContext extends ItemUsageContext {
   private final BlockPos placementPos;
   protected boolean canReplaceExisting = true;

   public ItemPlacementContext(PlayerEntity _snowman, Hand _snowman, ItemStack _snowman, BlockHitResult _snowman) {
      this(_snowman.world, _snowman, _snowman, _snowman, _snowman);
   }

   public ItemPlacementContext(ItemUsageContext context) {
      this(context.getWorld(), context.getPlayer(), context.getHand(), context.getStack(), context.getHitResult());
   }

   protected ItemPlacementContext(World _snowman, @Nullable PlayerEntity _snowman, Hand _snowman, ItemStack _snowman, BlockHitResult _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.placementPos = _snowman.getBlockPos().offset(_snowman.getSide());
      this.canReplaceExisting = _snowman.getBlockState(_snowman.getBlockPos()).canReplace(this);
   }

   public static ItemPlacementContext offset(ItemPlacementContext context, BlockPos pos, Direction side) {
      return new ItemPlacementContext(
         context.getWorld(),
         context.getPlayer(),
         context.getHand(),
         context.getStack(),
         new BlockHitResult(
            new Vec3d(
               (double)pos.getX() + 0.5 + (double)side.getOffsetX() * 0.5,
               (double)pos.getY() + 0.5 + (double)side.getOffsetY() * 0.5,
               (double)pos.getZ() + 0.5 + (double)side.getOffsetZ() * 0.5
            ),
            side,
            pos,
            false
         )
      );
   }

   @Override
   public BlockPos getBlockPos() {
      return this.canReplaceExisting ? super.getBlockPos() : this.placementPos;
   }

   public boolean canPlace() {
      return this.canReplaceExisting || this.getWorld().getBlockState(this.getBlockPos()).canReplace(this);
   }

   public boolean canReplaceExisting() {
      return this.canReplaceExisting;
   }

   public Direction getPlayerLookDirection() {
      return Direction.getEntityFacingOrder(this.getPlayer())[0];
   }

   public Direction[] getPlacementDirections() {
      Direction[] _snowman = Direction.getEntityFacingOrder(this.getPlayer());
      if (this.canReplaceExisting) {
         return _snowman;
      } else {
         Direction _snowmanx = this.getSide();
         int _snowmanxx = 0;

         while (_snowmanxx < _snowman.length && _snowman[_snowmanxx] != _snowmanx.getOpposite()) {
            _snowmanxx++;
         }

         if (_snowmanxx > 0) {
            System.arraycopy(_snowman, 0, _snowman, 1, _snowmanxx);
            _snowman[0] = _snowmanx.getOpposite();
         }

         return _snowman;
      }
   }
}
