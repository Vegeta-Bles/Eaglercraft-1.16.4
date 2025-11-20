package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class StructureBlock extends BlockWithEntity {
   public static final EnumProperty<StructureBlockMode> MODE = Properties.STRUCTURE_BLOCK_MODE;

   protected StructureBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new StructureBlockBlockEntity();
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof StructureBlockBlockEntity) {
         return ((StructureBlockBlockEntity)_snowman).openScreen(player) ? ActionResult.success(world.isClient) : ActionResult.PASS;
      } else {
         return ActionResult.PASS;
      }
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
      if (!world.isClient) {
         if (placer != null) {
            BlockEntity _snowman = world.getBlockEntity(pos);
            if (_snowman instanceof StructureBlockBlockEntity) {
               ((StructureBlockBlockEntity)_snowman).setAuthor(placer);
            }
         }
      }
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.getDefaultState().with(MODE, StructureBlockMode.DATA);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(MODE);
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      if (world instanceof ServerWorld) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof StructureBlockBlockEntity) {
            StructureBlockBlockEntity _snowmanx = (StructureBlockBlockEntity)_snowman;
            boolean _snowmanxx = world.isReceivingRedstonePower(pos);
            boolean _snowmanxxx = _snowmanx.isPowered();
            if (_snowmanxx && !_snowmanxxx) {
               _snowmanx.setPowered(true);
               this.doAction((ServerWorld)world, _snowmanx);
            } else if (!_snowmanxx && _snowmanxxx) {
               _snowmanx.setPowered(false);
            }
         }
      }
   }

   private void doAction(ServerWorld _snowman, StructureBlockBlockEntity _snowman) {
      switch (_snowman.getMode()) {
         case SAVE:
            _snowman.saveStructure(false);
            break;
         case LOAD:
            _snowman.loadStructure(_snowman, false);
            break;
         case CORNER:
            _snowman.unloadStructure();
         case DATA:
      }
   }
}
