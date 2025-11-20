package net.minecraft.block;

import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CauldronBlock extends Block {
   public static final IntProperty LEVEL = Properties.LEVEL_3;
   private static final VoxelShape RAY_TRACE_SHAPE = createCuboidShape(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
   protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(
      VoxelShapes.fullCube(),
      VoxelShapes.union(
         createCuboidShape(0.0, 0.0, 4.0, 16.0, 3.0, 12.0),
         createCuboidShape(4.0, 0.0, 0.0, 12.0, 3.0, 16.0),
         createCuboidShape(2.0, 0.0, 2.0, 14.0, 3.0, 14.0),
         RAY_TRACE_SHAPE
      ),
      BooleanBiFunction.ONLY_FIRST
   );

   public CauldronBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return OUTLINE_SHAPE;
   }

   @Override
   public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
      return RAY_TRACE_SHAPE;
   }

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      int _snowman = state.get(LEVEL);
      float _snowmanx = (float)pos.getY() + (6.0F + (float)(3 * _snowman)) / 16.0F;
      if (!world.isClient && entity.isOnFire() && _snowman > 0 && entity.getY() <= (double)_snowmanx) {
         entity.extinguish();
         this.setLevel(world, pos, state, _snowman - 1);
      }
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (_snowman.isEmpty()) {
         return ActionResult.PASS;
      } else {
         int _snowmanx = state.get(LEVEL);
         Item _snowmanxx = _snowman.getItem();
         if (_snowmanxx == Items.WATER_BUCKET) {
            if (_snowmanx < 3 && !world.isClient) {
               if (!player.abilities.creativeMode) {
                  player.setStackInHand(hand, new ItemStack(Items.BUCKET));
               }

               player.incrementStat(Stats.FILL_CAULDRON);
               this.setLevel(world, pos, state, 3);
               world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return ActionResult.success(world.isClient);
         } else if (_snowmanxx == Items.BUCKET) {
            if (_snowmanx == 3 && !world.isClient) {
               if (!player.abilities.creativeMode) {
                  _snowman.decrement(1);
                  if (_snowman.isEmpty()) {
                     player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
                  } else if (!player.inventory.insertStack(new ItemStack(Items.WATER_BUCKET))) {
                     player.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                  }
               }

               player.incrementStat(Stats.USE_CAULDRON);
               this.setLevel(world, pos, state, 0);
               world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return ActionResult.success(world.isClient);
         } else if (_snowmanxx == Items.GLASS_BOTTLE) {
            if (_snowmanx > 0 && !world.isClient) {
               if (!player.abilities.creativeMode) {
                  ItemStack _snowmanxxx = PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER);
                  player.incrementStat(Stats.USE_CAULDRON);
                  _snowman.decrement(1);
                  if (_snowman.isEmpty()) {
                     player.setStackInHand(hand, _snowmanxxx);
                  } else if (!player.inventory.insertStack(_snowmanxxx)) {
                     player.dropItem(_snowmanxxx, false);
                  } else if (player instanceof ServerPlayerEntity) {
                     ((ServerPlayerEntity)player).refreshScreenHandler(player.playerScreenHandler);
                  }
               }

               world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
               this.setLevel(world, pos, state, _snowmanx - 1);
            }

            return ActionResult.success(world.isClient);
         } else if (_snowmanxx == Items.POTION && PotionUtil.getPotion(_snowman) == Potions.WATER) {
            if (_snowmanx < 3 && !world.isClient) {
               if (!player.abilities.creativeMode) {
                  ItemStack _snowmanxxx = new ItemStack(Items.GLASS_BOTTLE);
                  player.incrementStat(Stats.USE_CAULDRON);
                  player.setStackInHand(hand, _snowmanxxx);
                  if (player instanceof ServerPlayerEntity) {
                     ((ServerPlayerEntity)player).refreshScreenHandler(player.playerScreenHandler);
                  }
               }

               world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
               this.setLevel(world, pos, state, _snowmanx + 1);
            }

            return ActionResult.success(world.isClient);
         } else {
            if (_snowmanx > 0 && _snowmanxx instanceof DyeableItem) {
               DyeableItem _snowmanxxx = (DyeableItem)_snowmanxx;
               if (_snowmanxxx.hasColor(_snowman) && !world.isClient) {
                  _snowmanxxx.removeColor(_snowman);
                  this.setLevel(world, pos, state, _snowmanx - 1);
                  player.incrementStat(Stats.CLEAN_ARMOR);
                  return ActionResult.SUCCESS;
               }
            }

            if (_snowmanx > 0 && _snowmanxx instanceof BannerItem) {
               if (BannerBlockEntity.getPatternCount(_snowman) > 0 && !world.isClient) {
                  ItemStack _snowmanxxx = _snowman.copy();
                  _snowmanxxx.setCount(1);
                  BannerBlockEntity.loadFromItemStack(_snowmanxxx);
                  player.incrementStat(Stats.CLEAN_BANNER);
                  if (!player.abilities.creativeMode) {
                     _snowman.decrement(1);
                     this.setLevel(world, pos, state, _snowmanx - 1);
                  }

                  if (_snowman.isEmpty()) {
                     player.setStackInHand(hand, _snowmanxxx);
                  } else if (!player.inventory.insertStack(_snowmanxxx)) {
                     player.dropItem(_snowmanxxx, false);
                  } else if (player instanceof ServerPlayerEntity) {
                     ((ServerPlayerEntity)player).refreshScreenHandler(player.playerScreenHandler);
                  }
               }

               return ActionResult.success(world.isClient);
            } else if (_snowmanx > 0 && _snowmanxx instanceof BlockItem) {
               Block _snowmanxxxx = ((BlockItem)_snowmanxx).getBlock();
               if (_snowmanxxxx instanceof ShulkerBoxBlock && !world.isClient()) {
                  ItemStack _snowmanxxxxx = new ItemStack(Blocks.SHULKER_BOX, 1);
                  if (_snowman.hasTag()) {
                     _snowmanxxxxx.setTag(_snowman.getTag().copy());
                  }

                  player.setStackInHand(hand, _snowmanxxxxx);
                  this.setLevel(world, pos, state, _snowmanx - 1);
                  player.incrementStat(Stats.CLEAN_SHULKER_BOX);
                  return ActionResult.SUCCESS;
               } else {
                  return ActionResult.CONSUME;
               }
            } else {
               return ActionResult.PASS;
            }
         }
      }
   }

   public void setLevel(World world, BlockPos pos, BlockState state, int level) {
      world.setBlockState(pos, state.with(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 3))), 2);
      world.updateComparators(pos, this);
   }

   @Override
   public void rainTick(World world, BlockPos pos) {
      if (world.random.nextInt(20) == 1) {
         float _snowman = world.getBiome(pos).getTemperature(pos);
         if (!(_snowman < 0.15F)) {
            BlockState _snowmanx = world.getBlockState(pos);
            if (_snowmanx.get(LEVEL) < 3) {
               world.setBlockState(pos, _snowmanx.cycle(LEVEL), 2);
            }
         }
      }
   }

   @Override
   public boolean hasComparatorOutput(BlockState state) {
      return true;
   }

   @Override
   public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
      return state.get(LEVEL);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(LEVEL);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
