package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockWithEntity extends Block implements BlockEntityProvider {
   protected BlockWithEntity(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.INVISIBLE;
   }

   @Override
   public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
      super.onSyncedBlockEvent(state, world, pos, type, data);
      BlockEntity _snowman = world.getBlockEntity(pos);
      return _snowman == null ? false : _snowman.onSyncedBlockEvent(type, data);
   }

   @Nullable
   @Override
   public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      return _snowman instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)_snowman : null;
   }
}
