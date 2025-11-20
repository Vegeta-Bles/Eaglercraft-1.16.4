package net.minecraft.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class SpawnerBlock extends BlockWithEntity {
   protected SpawnerBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new MobSpawnerBlockEntity();
   }

   @Override
   public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
      super.onStacksDropped(state, world, pos, stack);
      int _snowman = 15 + world.random.nextInt(15) + world.random.nextInt(15);
      this.dropExperience(world, pos, _snowman);
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Override
   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
      return ItemStack.EMPTY;
   }
}
