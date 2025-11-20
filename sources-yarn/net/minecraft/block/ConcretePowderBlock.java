package net.minecraft.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ConcretePowderBlock extends FallingBlock {
   private final BlockState hardenedState;

   public ConcretePowderBlock(Block hardened, AbstractBlock.Settings settings) {
      super(settings);
      this.hardenedState = hardened.getDefaultState();
   }

   @Override
   public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
      if (shouldHarden(world, pos, currentStateInPos)) {
         world.setBlockState(pos, this.hardenedState, 3);
      }
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockView lv = ctx.getWorld();
      BlockPos lv2 = ctx.getBlockPos();
      BlockState lv3 = lv.getBlockState(lv2);
      return shouldHarden(lv, lv2, lv3) ? this.hardenedState : super.getPlacementState(ctx);
   }

   private static boolean shouldHarden(BlockView world, BlockPos pos, BlockState state) {
      return hardensIn(state) || hardensOnAnySide(world, pos);
   }

   private static boolean hardensOnAnySide(BlockView world, BlockPos pos) {
      boolean bl = false;
      BlockPos.Mutable lv = pos.mutableCopy();

      for (Direction lv2 : Direction.values()) {
         BlockState lv3 = world.getBlockState(lv);
         if (lv2 != Direction.DOWN || hardensIn(lv3)) {
            lv.set(pos, lv2);
            lv3 = world.getBlockState(lv);
            if (hardensIn(lv3) && !lv3.isSideSolidFullSquare(world, pos, lv2.getOpposite())) {
               bl = true;
               break;
            }
         }
      }

      return bl;
   }

   private static boolean hardensIn(BlockState state) {
      return state.getFluidState().isIn(FluidTags.WATER);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return hardensOnAnySide(world, pos) ? this.hardenedState : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Environment(EnvType.CLIENT)
   @Override
   public int getColor(BlockState state, BlockView world, BlockPos pos) {
      return state.getTopMaterialColor(world, pos).color;
   }
}
