package net.minecraft.block;

import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DragonEggBlock extends FallingBlock {
   protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public DragonEggBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      this.teleport(state, world, pos);
      return ActionResult.success(world.isClient);
   }

   @Override
   public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
      this.teleport(state, world, pos);
   }

   private void teleport(BlockState state, World world, BlockPos pos) {
      for (int _snowman = 0; _snowman < 1000; _snowman++) {
         BlockPos _snowmanx = pos.add(
            world.random.nextInt(16) - world.random.nextInt(16),
            world.random.nextInt(8) - world.random.nextInt(8),
            world.random.nextInt(16) - world.random.nextInt(16)
         );
         if (world.getBlockState(_snowmanx).isAir()) {
            if (world.isClient) {
               for (int _snowmanxx = 0; _snowmanxx < 128; _snowmanxx++) {
                  double _snowmanxxx = world.random.nextDouble();
                  float _snowmanxxxx = (world.random.nextFloat() - 0.5F) * 0.2F;
                  float _snowmanxxxxx = (world.random.nextFloat() - 0.5F) * 0.2F;
                  float _snowmanxxxxxx = (world.random.nextFloat() - 0.5F) * 0.2F;
                  double _snowmanxxxxxxx = MathHelper.lerp(_snowmanxxx, (double)_snowmanx.getX(), (double)pos.getX()) + (world.random.nextDouble() - 0.5) + 0.5;
                  double _snowmanxxxxxxxx = MathHelper.lerp(_snowmanxxx, (double)_snowmanx.getY(), (double)pos.getY()) + world.random.nextDouble() - 0.5;
                  double _snowmanxxxxxxxxx = MathHelper.lerp(_snowmanxxx, (double)_snowmanx.getZ(), (double)pos.getZ()) + (world.random.nextDouble() - 0.5) + 0.5;
                  world.addParticle(ParticleTypes.PORTAL, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, (double)_snowmanxxxx, (double)_snowmanxxxxx, (double)_snowmanxxxxxx);
               }
            } else {
               world.setBlockState(_snowmanx, state, 2);
               world.removeBlock(pos, false);
            }

            return;
         }
      }
   }

   @Override
   protected int getFallDelay() {
      return 5;
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
