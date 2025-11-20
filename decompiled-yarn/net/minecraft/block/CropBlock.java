package net.minecraft.block;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CropBlock extends PlantBlock implements Fertilizable {
   public static final IntProperty AGE = Properties.AGE_7;
   private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   protected CropBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), Integer.valueOf(0)));
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
   }

   @Override
   protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
      return floor.isOf(Blocks.FARMLAND);
   }

   public IntProperty getAgeProperty() {
      return AGE;
   }

   public int getMaxAge() {
      return 7;
   }

   protected int getAge(BlockState state) {
      return state.get(this.getAgeProperty());
   }

   public BlockState withAge(int age) {
      return this.getDefaultState().with(this.getAgeProperty(), Integer.valueOf(age));
   }

   public boolean isMature(BlockState state) {
      return state.get(this.getAgeProperty()) >= this.getMaxAge();
   }

   @Override
   public boolean hasRandomTicks(BlockState state) {
      return !this.isMature(state);
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (world.getBaseLightLevel(pos, 0) >= 9) {
         int _snowman = this.getAge(state);
         if (_snowman < this.getMaxAge()) {
            float _snowmanx = getAvailableMoisture(this, world, pos);
            if (random.nextInt((int)(25.0F / _snowmanx) + 1) == 0) {
               world.setBlockState(pos, this.withAge(_snowman + 1), 2);
            }
         }
      }
   }

   public void applyGrowth(World world, BlockPos pos, BlockState state) {
      int _snowman = this.getAge(state) + this.getGrowthAmount(world);
      int _snowmanx = this.getMaxAge();
      if (_snowman > _snowmanx) {
         _snowman = _snowmanx;
      }

      world.setBlockState(pos, this.withAge(_snowman), 2);
   }

   protected int getGrowthAmount(World world) {
      return MathHelper.nextInt(world.random, 2, 5);
   }

   protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
      float _snowman = 1.0F;
      BlockPos _snowmanx = pos.down();

      for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
         for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
            float _snowmanxxxx = 0.0F;
            BlockState _snowmanxxxxx = world.getBlockState(_snowmanx.add(_snowmanxx, 0, _snowmanxxx));
            if (_snowmanxxxxx.isOf(Blocks.FARMLAND)) {
               _snowmanxxxx = 1.0F;
               if (_snowmanxxxxx.get(FarmlandBlock.MOISTURE) > 0) {
                  _snowmanxxxx = 3.0F;
               }
            }

            if (_snowmanxx != 0 || _snowmanxxx != 0) {
               _snowmanxxxx /= 4.0F;
            }

            _snowman += _snowmanxxxx;
         }
      }

      BlockPos _snowmanxx = pos.north();
      BlockPos _snowmanxxx = pos.south();
      BlockPos _snowmanxxxxxx = pos.west();
      BlockPos _snowmanxxxxxxx = pos.east();
      boolean _snowmanxxxxxxxx = block == world.getBlockState(_snowmanxxxxxx).getBlock() || block == world.getBlockState(_snowmanxxxxxxx).getBlock();
      boolean _snowmanxxxxxxxxx = block == world.getBlockState(_snowmanxx).getBlock() || block == world.getBlockState(_snowmanxxx).getBlock();
      if (_snowmanxxxxxxxx && _snowmanxxxxxxxxx) {
         _snowman /= 2.0F;
      } else {
         boolean _snowmanxxxxxxxxxx = block == world.getBlockState(_snowmanxxxxxx.north()).getBlock()
            || block == world.getBlockState(_snowmanxxxxxxx.north()).getBlock()
            || block == world.getBlockState(_snowmanxxxxxxx.south()).getBlock()
            || block == world.getBlockState(_snowmanxxxxxx.south()).getBlock();
         if (_snowmanxxxxxxxxxx) {
            _snowman /= 2.0F;
         }
      }

      return _snowman;
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos)) && super.canPlaceAt(state, world, pos);
   }

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      if (entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
         world.breakBlock(pos, true, entity);
      }

      super.onEntityCollision(state, world, pos, entity);
   }

   protected ItemConvertible getSeedsItem() {
      return Items.WHEAT_SEEDS;
   }

   @Override
   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
      return new ItemStack(this.getSeedsItem());
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      return !this.isMature(state);
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      this.applyGrowth(world, pos, state);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(AGE);
   }
}
