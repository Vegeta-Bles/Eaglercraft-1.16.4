package net.minecraft.block;

import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LilyPadBlock extends PlantBlock {
   protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

   protected LilyPadBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      super.onEntityCollision(state, world, pos, entity);
      if (world instanceof ServerWorld && entity instanceof BoatEntity) {
         world.breakBlock(new BlockPos(pos), true, entity);
      }
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
      FluidState _snowman = world.getFluidState(pos);
      FluidState _snowmanx = world.getFluidState(pos.up());
      return (_snowman.getFluid() == Fluids.WATER || floor.getMaterial() == Material.ICE) && _snowmanx.getFluid() == Fluids.EMPTY;
   }
}
