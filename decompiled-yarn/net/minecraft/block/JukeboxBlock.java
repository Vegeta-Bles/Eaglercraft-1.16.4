package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class JukeboxBlock extends BlockWithEntity {
   public static final BooleanProperty HAS_RECORD = Properties.HAS_RECORD;

   protected JukeboxBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(HAS_RECORD, Boolean.valueOf(false)));
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
      super.onPlaced(world, pos, state, placer, itemStack);
      CompoundTag _snowman = itemStack.getOrCreateTag();
      if (_snowman.contains("BlockEntityTag")) {
         CompoundTag _snowmanx = _snowman.getCompound("BlockEntityTag");
         if (_snowmanx.contains("RecordItem")) {
            world.setBlockState(pos, state.with(HAS_RECORD, Boolean.valueOf(true)), 2);
         }
      }
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (state.get(HAS_RECORD)) {
         this.removeRecord(world, pos);
         state = state.with(HAS_RECORD, Boolean.valueOf(false));
         world.setBlockState(pos, state, 2);
         return ActionResult.success(world.isClient);
      } else {
         return ActionResult.PASS;
      }
   }

   public void setRecord(WorldAccess world, BlockPos pos, BlockState state, ItemStack stack) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof JukeboxBlockEntity) {
         ((JukeboxBlockEntity)_snowman).setRecord(stack.copy());
         world.setBlockState(pos, state.with(HAS_RECORD, Boolean.valueOf(true)), 2);
      }
   }

   private void removeRecord(World world, BlockPos pos) {
      if (!world.isClient) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof JukeboxBlockEntity) {
            JukeboxBlockEntity _snowmanx = (JukeboxBlockEntity)_snowman;
            ItemStack _snowmanxx = _snowmanx.getRecord();
            if (!_snowmanxx.isEmpty()) {
               world.syncWorldEvent(1010, pos, 0);
               _snowmanx.clear();
               float _snowmanxxx = 0.7F;
               double _snowmanxxxx = (double)(world.random.nextFloat() * 0.7F) + 0.15F;
               double _snowmanxxxxx = (double)(world.random.nextFloat() * 0.7F) + 0.060000002F + 0.6;
               double _snowmanxxxxxx = (double)(world.random.nextFloat() * 0.7F) + 0.15F;
               ItemStack _snowmanxxxxxxx = _snowmanxx.copy();
               ItemEntity _snowmanxxxxxxxx = new ItemEntity(world, (double)pos.getX() + _snowmanxxxx, (double)pos.getY() + _snowmanxxxxx, (double)pos.getZ() + _snowmanxxxxxx, _snowmanxxxxxxx);
               _snowmanxxxxxxxx.setToDefaultPickupDelay();
               world.spawnEntity(_snowmanxxxxxxxx);
            }
         }
      }
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!state.isOf(newState.getBlock())) {
         this.removeRecord(world, pos);
         super.onStateReplaced(state, world, pos, newState, moved);
      }
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new JukeboxBlockEntity();
   }

   @Override
   public boolean hasComparatorOutput(BlockState state) {
      return true;
   }

   @Override
   public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof JukeboxBlockEntity) {
         Item _snowmanx = ((JukeboxBlockEntity)_snowman).getRecord().getItem();
         if (_snowmanx instanceof MusicDiscItem) {
            return ((MusicDiscItem)_snowmanx).getComparatorOutput();
         }
      }

      return 0;
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(HAS_RECORD);
   }
}
