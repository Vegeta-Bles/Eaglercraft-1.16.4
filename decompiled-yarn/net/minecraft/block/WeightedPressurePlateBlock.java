package net.minecraft.block;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class WeightedPressurePlateBlock extends AbstractPressurePlateBlock {
   public static final IntProperty POWER = Properties.POWER;
   private final int weight;

   protected WeightedPressurePlateBlock(int weight, AbstractBlock.Settings settings) {
      super(settings);
      this.setDefaultState(this.stateManager.getDefaultState().with(POWER, Integer.valueOf(0)));
      this.weight = weight;
   }

   @Override
   protected int getRedstoneOutput(World world, BlockPos pos) {
      int _snowman = Math.min(world.getNonSpectatingEntities(Entity.class, BOX.offset(pos)).size(), this.weight);
      if (_snowman > 0) {
         float _snowmanx = (float)Math.min(this.weight, _snowman) / (float)this.weight;
         return MathHelper.ceil(_snowmanx * 15.0F);
      } else {
         return 0;
      }
   }

   @Override
   protected void playPressSound(WorldAccess world, BlockPos pos) {
      world.playSound(null, pos, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.90000004F);
   }

   @Override
   protected void playDepressSound(WorldAccess world, BlockPos pos) {
      world.playSound(null, pos, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.75F);
   }

   @Override
   protected int getRedstoneOutput(BlockState state) {
      return state.get(POWER);
   }

   @Override
   protected BlockState setRedstoneOutput(BlockState state, int rsOut) {
      return state.with(POWER, Integer.valueOf(rsOut));
   }

   @Override
   protected int getTickRate() {
      return 10;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(POWER);
   }
}
