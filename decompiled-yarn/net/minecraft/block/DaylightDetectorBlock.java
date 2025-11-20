package net.minecraft.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DaylightDetectorBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class DaylightDetectorBlock extends BlockWithEntity {
   public static final IntProperty POWER = Properties.POWER;
   public static final BooleanProperty INVERTED = Properties.INVERTED;
   protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);

   public DaylightDetectorBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(POWER, Integer.valueOf(0)).with(INVERTED, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   public boolean hasSidedTransparency(BlockState state) {
      return true;
   }

   @Override
   public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return state.get(POWER);
   }

   public static void updateState(BlockState state, World world, BlockPos pos) {
      if (world.getDimension().hasSkyLight()) {
         int _snowman = world.getLightLevel(LightType.SKY, pos) - world.getAmbientDarkness();
         float _snowmanx = world.getSkyAngleRadians(1.0F);
         boolean _snowmanxx = state.get(INVERTED);
         if (_snowmanxx) {
            _snowman = 15 - _snowman;
         } else if (_snowman > 0) {
            float _snowmanxxx = _snowmanx < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
            _snowmanx += (_snowmanxxx - _snowmanx) * 0.2F;
            _snowman = Math.round((float)_snowman * MathHelper.cos(_snowmanx));
         }

         _snowman = MathHelper.clamp(_snowman, 0, 15);
         if (state.get(POWER) != _snowman) {
            world.setBlockState(pos, state.with(POWER, Integer.valueOf(_snowman)), 3);
         }
      }
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (player.canModifyBlocks()) {
         if (world.isClient) {
            return ActionResult.SUCCESS;
         } else {
            BlockState _snowman = state.cycle(INVERTED);
            world.setBlockState(pos, _snowman, 4);
            updateState(_snowman, world, pos);
            return ActionResult.CONSUME;
         }
      } else {
         return super.onUse(state, world, pos, player, hand, hit);
      }
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Override
   public boolean emitsRedstonePower(BlockState state) {
      return true;
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new DaylightDetectorBlockEntity();
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(POWER, INVERTED);
   }
}
