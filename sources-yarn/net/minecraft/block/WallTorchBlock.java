package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   protected WallTorchBlock(AbstractBlock.Settings arg, ParticleEffect arg2) {
      super(arg, arg2);
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
      Direction lv = state.get(FACING);
      BlockPos lv2 = pos.offset(lv.getOpposite());
      BlockState lv3 = world.getBlockState(lv2);
      return lv3.isSideSolidFullSquare(world, lv2, lv);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState lv = this.getDefaultState();
      WorldView lv2 = ctx.getWorld();
      BlockPos lv3 = ctx.getBlockPos();
      Direction[] lvs = ctx.getPlacementDirections();

      for (Direction lv4 : lvs) {
         if (lv4.getAxis().isHorizontal()) {
            Direction lv5 = lv4.getOpposite();
            lv = lv.with(FACING, lv5);
            if (lv.canPlaceAt(lv2, lv3)) {
               return lv;
            }
         }
      }

      return null;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : state;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      Direction lv = state.get(FACING);
      double d = (double)pos.getX() + 0.5;
      double e = (double)pos.getY() + 0.7;
      double f = (double)pos.getZ() + 0.5;
      double g = 0.22;
      double h = 0.27;
      Direction lv2 = lv.getOpposite();
      world.addParticle(ParticleTypes.SMOKE, d + 0.27 * (double)lv2.getOffsetX(), e + 0.22, f + 0.27 * (double)lv2.getOffsetZ(), 0.0, 0.0, 0.0);
      world.addParticle(this.particle, d + 0.27 * (double)lv2.getOffsetX(), e + 0.22, f + 0.27 * (double)lv2.getOffsetZ(), 0.0, 0.0, 0.0);
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
