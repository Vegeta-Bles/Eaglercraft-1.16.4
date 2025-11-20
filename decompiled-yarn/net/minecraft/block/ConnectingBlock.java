package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class ConnectingBlock extends Block {
   private static final Direction[] FACINGS = Direction.values();
   public static final BooleanProperty NORTH = Properties.NORTH;
   public static final BooleanProperty EAST = Properties.EAST;
   public static final BooleanProperty SOUTH = Properties.SOUTH;
   public static final BooleanProperty WEST = Properties.WEST;
   public static final BooleanProperty UP = Properties.UP;
   public static final BooleanProperty DOWN = Properties.DOWN;
   public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = Util.make(Maps.newEnumMap(Direction.class), _snowman -> {
      _snowman.put(Direction.NORTH, NORTH);
      _snowman.put(Direction.EAST, EAST);
      _snowman.put(Direction.SOUTH, SOUTH);
      _snowman.put(Direction.WEST, WEST);
      _snowman.put(Direction.UP, UP);
      _snowman.put(Direction.DOWN, DOWN);
   });
   protected final VoxelShape[] CONNECTIONS_TO_SHAPE;

   protected ConnectingBlock(float radius, AbstractBlock.Settings settings) {
      super(settings);
      this.CONNECTIONS_TO_SHAPE = this.generateFacingsToShapeMap(radius);
   }

   private VoxelShape[] generateFacingsToShapeMap(float radius) {
      float _snowman = 0.5F - radius;
      float _snowmanx = 0.5F + radius;
      VoxelShape _snowmanxx = Block.createCuboidShape(
         (double)(_snowman * 16.0F), (double)(_snowman * 16.0F), (double)(_snowman * 16.0F), (double)(_snowmanx * 16.0F), (double)(_snowmanx * 16.0F), (double)(_snowmanx * 16.0F)
      );
      VoxelShape[] _snowmanxxx = new VoxelShape[FACINGS.length];

      for (int _snowmanxxxx = 0; _snowmanxxxx < FACINGS.length; _snowmanxxxx++) {
         Direction _snowmanxxxxx = FACINGS[_snowmanxxxx];
         _snowmanxxx[_snowmanxxxx] = VoxelShapes.cuboid(
            0.5 + Math.min((double)(-radius), (double)_snowmanxxxxx.getOffsetX() * 0.5),
            0.5 + Math.min((double)(-radius), (double)_snowmanxxxxx.getOffsetY() * 0.5),
            0.5 + Math.min((double)(-radius), (double)_snowmanxxxxx.getOffsetZ() * 0.5),
            0.5 + Math.max((double)radius, (double)_snowmanxxxxx.getOffsetX() * 0.5),
            0.5 + Math.max((double)radius, (double)_snowmanxxxxx.getOffsetY() * 0.5),
            0.5 + Math.max((double)radius, (double)_snowmanxxxxx.getOffsetZ() * 0.5)
         );
      }

      VoxelShape[] _snowmanxxxx = new VoxelShape[64];

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 64; _snowmanxxxxx++) {
         VoxelShape _snowmanxxxxxx = _snowmanxx;

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < FACINGS.length; _snowmanxxxxxxx++) {
            if ((_snowmanxxxxx & 1 << _snowmanxxxxxxx) != 0) {
               _snowmanxxxxxx = VoxelShapes.union(_snowmanxxxxxx, _snowmanxxx[_snowmanxxxxxxx]);
            }
         }

         _snowmanxxxx[_snowmanxxxxx] = _snowmanxxxxxx;
      }

      return _snowmanxxxx;
   }

   @Override
   public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
      return false;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.CONNECTIONS_TO_SHAPE[this.getConnectionMask(state)];
   }

   protected int getConnectionMask(BlockState state) {
      int _snowman = 0;

      for (int _snowmanx = 0; _snowmanx < FACINGS.length; _snowmanx++) {
         if (state.get(FACING_PROPERTIES.get(FACINGS[_snowmanx]))) {
            _snowman |= 1 << _snowmanx;
         }
      }

      return _snowman;
   }
}
