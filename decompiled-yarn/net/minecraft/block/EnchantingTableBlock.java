package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Nameable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EnchantingTableBlock extends BlockWithEntity {
   protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

   protected EnchantingTableBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public boolean hasSidedTransparency(BlockState state) {
      return true;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      super.randomDisplayTick(state, world, pos, random);

      for (int _snowman = -2; _snowman <= 2; _snowman++) {
         for (int _snowmanx = -2; _snowmanx <= 2; _snowmanx++) {
            if (_snowman > -2 && _snowman < 2 && _snowmanx == -1) {
               _snowmanx = 2;
            }

            if (random.nextInt(16) == 0) {
               for (int _snowmanxx = 0; _snowmanxx <= 1; _snowmanxx++) {
                  BlockPos _snowmanxxx = pos.add(_snowman, _snowmanxx, _snowmanx);
                  if (world.getBlockState(_snowmanxxx).isOf(Blocks.BOOKSHELF)) {
                     if (!world.isAir(pos.add(_snowman / 2, 0, _snowmanx / 2))) {
                        break;
                     }

                     world.addParticle(
                        ParticleTypes.ENCHANT,
                        (double)pos.getX() + 0.5,
                        (double)pos.getY() + 2.0,
                        (double)pos.getZ() + 0.5,
                        (double)((float)_snowman + random.nextFloat()) - 0.5,
                        (double)((float)_snowmanxx - random.nextFloat() - 1.0F),
                        (double)((float)_snowmanx + random.nextFloat()) - 0.5
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new EnchantingTableBlockEntity();
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (world.isClient) {
         return ActionResult.SUCCESS;
      } else {
         player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
         return ActionResult.CONSUME;
      }
   }

   @Nullable
   @Override
   public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof EnchantingTableBlockEntity) {
         Text _snowmanx = ((Nameable)_snowman).getDisplayName();
         return new SimpleNamedScreenHandlerFactory((_snowmanxx, _snowmanxxx, _snowmanxxxx) -> new EnchantmentScreenHandler(_snowmanxx, _snowmanxxx, ScreenHandlerContext.create(world, pos)), _snowmanx);
      } else {
         return null;
      }
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
      if (itemStack.hasCustomName()) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof EnchantingTableBlockEntity) {
            ((EnchantingTableBlockEntity)_snowman).setCustomName(itemStack.getName());
         }
      }
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
