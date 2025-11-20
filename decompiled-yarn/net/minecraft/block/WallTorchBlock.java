package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WallTorchBlock extends TorchBlock {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.createCuboidShape(5.5, 3.0, 11.0, 10.5, 13.0, 16.0),
         Direction.SOUTH,
         Block.createCuboidShape(5.5, 3.0, 0.0, 10.5, 13.0, 5.0),
         Direction.WEST,
         Block.createCuboidShape(11.0, 3.0, 5.5, 16.0, 13.0, 10.5),
         Direction.EAST,
         Block.createCuboidShape(0.0, 3.0, 5.5, 5.0, 13.0, 10.5)
      )
   );

   protected WallTorchBlock(AbstractBlock.Settings _snowman, ParticleEffect _snowman) {
      super(_snowman, _snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
   }

   @Override
   public String getTranslationKey() {
      return this.asItem().getTranslationKey();
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return getBoundingShape(state);
   }

   public static VoxelShape getBoundingShape(BlockState state) {
      return BOUNDING_SHAPES.get(state.get(FACING));
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      Direction _snowman = state.get(FACING);
      BlockPos _snowmanx = pos.offset(_snowman.getOpposite());
      BlockState _snowmanxx = world.getBlockState(_snowmanx);
      return _snowmanxx.isSideSolidFullSquare(world, _snowmanx, _snowman);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState _snowman = this.getDefaultState();
      WorldView _snowmanx = ctx.getWorld();
      BlockPos _snowmanxx = ctx.getBlockPos();
      Direction[] _snowmanxxx = ctx.getPlacementDirections();

      for (Direction _snowmanxxxx : _snowmanxxx) {
         if (_snowmanxxxx.getAxis().isHorizontal()) {
            Direction _snowmanxxxxx = _snowmanxxxx.getOpposite();
            _snowman = _snowman.with(FACING, _snowmanxxxxx);
            if (_snowman.canPlaceAt(_snowmanx, _snowmanxx)) {
               return _snowman;
            }
         }
      }

      return null;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : state;
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      Direction _snowman = state.get(FACING);
      double _snowmanx = (double)pos.getX() + 0.5;
      double _snowmanxx = (double)pos.getY() + 0.7;
      double _snowmanxxx = (double)pos.getZ() + 0.5;
      double _snowmanxxxx = 0.22;
      double _snowmanxxxxx = 0.27;
      Direction _snowmanxxxxxx = _snowman.getOpposite();
      world.addParticle(ParticleTypes.SMOKE, _snowmanx + 0.27 * (double)_snowmanxxxxxx.getOffsetX(), _snowmanxx + 0.22, _snowmanxxx + 0.27 * (double)_snowmanxxxxxx.getOffsetZ(), 0.0, 0.0, 0.0);
      world.addParticle(this.particle, _snowmanx + 0.27 * (double)_snowmanxxxxxx.getOffsetX(), _snowmanxx + 0.22, _snowmanxxx + 0.27 * (double)_snowmanxxxxxx.getOffsetZ(), 0.0, 0.0, 0.0);
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation(state.get(FACING)));
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING);
   }
}
