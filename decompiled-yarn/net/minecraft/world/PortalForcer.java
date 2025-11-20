package net.minecraft.world;

import java.util.Comparator;
import java.util.Optional;
import net.minecraft.class_5459;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class PortalForcer {
   private final ServerWorld world;

   public PortalForcer(ServerWorld world) {
      this.world = world;
   }

   public Optional<class_5459.class_5460> method_30483(BlockPos _snowman, boolean _snowman) {
      PointOfInterestStorage _snowmanxx = this.world.getPointOfInterestStorage();
      int _snowmanxxx = _snowman ? 16 : 128;
      _snowmanxx.preloadChunks(this.world, _snowman, _snowmanxxx);
      Optional<PointOfInterest> _snowmanxxxx = _snowmanxx.getInSquare(
            _snowmanxxxxx -> _snowmanxxxxx == PointOfInterestType.NETHER_PORTAL, _snowman, _snowmanxxx, PointOfInterestStorage.OccupationStatus.ANY
         )
         .sorted(
            Comparator.<PointOfInterest>comparingDouble(_snowmanxxxxx -> _snowmanxxxxx.getPos().getSquaredDistance(_snowman)).thenComparingInt(_snowmanxxxxx -> _snowmanxxxxx.getPos().getY())
         )
         .filter(_snowmanxxxxx -> this.world.getBlockState(_snowmanxxxxx.getPos()).contains(Properties.HORIZONTAL_AXIS))
         .findFirst();
      return _snowmanxxxx.map(
         _snowmanxxxxx -> {
            BlockPos _snowmanxxxxxx = _snowmanxxxxx.getPos();
            this.world.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(_snowmanxxxxxx), 3, _snowmanxxxxxx);
            BlockState _snowmanxxxxxxx = this.world.getBlockState(_snowmanxxxxxx);
            return class_5459.method_30574(
               _snowmanxxxxxx, _snowmanxxxxxxx.get(Properties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, _snowmanxxxxxxxx -> this.world.getBlockState(_snowmanxxxxxxxx) == _snowmanxx
            );
         }
      );
   }

   public Optional<class_5459.class_5460> method_30482(BlockPos _snowman, Direction.Axis _snowman) {
      Direction _snowmanxx = Direction.get(Direction.AxisDirection.POSITIVE, _snowman);
      double _snowmanxxx = -1.0;
      BlockPos _snowmanxxxx = null;
      double _snowmanxxxxx = -1.0;
      BlockPos _snowmanxxxxxx = null;
      WorldBorder _snowmanxxxxxxx = this.world.getWorldBorder();
      int _snowmanxxxxxxxx = this.world.getDimensionHeight() - 1;
      BlockPos.Mutable _snowmanxxxxxxxxx = _snowman.mutableCopy();

      for (BlockPos.Mutable _snowmanxxxxxxxxxx : BlockPos.method_30512(_snowman, 16, Direction.EAST, Direction.SOUTH)) {
         int _snowmanxxxxxxxxxxx = Math.min(_snowmanxxxxxxxx, this.world.getTopY(Heightmap.Type.MOTION_BLOCKING, _snowmanxxxxxxxxxx.getX(), _snowmanxxxxxxxxxx.getZ()));
         int _snowmanxxxxxxxxxxxx = 1;
         if (_snowmanxxxxxxx.contains(_snowmanxxxxxxxxxx) && _snowmanxxxxxxx.contains(_snowmanxxxxxxxxxx.move(_snowmanxx, 1))) {
            _snowmanxxxxxxxxxx.move(_snowmanxx.getOpposite(), 1);

            for (int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxx--) {
               _snowmanxxxxxxxxxx.setY(_snowmanxxxxxxxxxxxxx);
               if (this.world.isAir(_snowmanxxxxxxxxxx)) {
                  int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;

                  while (_snowmanxxxxxxxxxxxxx > 0 && this.world.isAir(_snowmanxxxxxxxxxx.move(Direction.DOWN))) {
                     _snowmanxxxxxxxxxxxxx--;
                  }

                  if (_snowmanxxxxxxxxxxxxx + 4 <= _snowmanxxxxxxxx) {
                     int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx;
                     if (_snowmanxxxxxxxxxxxxxxx <= 0 || _snowmanxxxxxxxxxxxxxxx >= 3) {
                        _snowmanxxxxxxxxxx.setY(_snowmanxxxxxxxxxxxxx);
                        if (this.method_30481(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxx, 0)) {
                           double _snowmanxxxxxxxxxxxxxxxx = _snowman.getSquaredDistance(_snowmanxxxxxxxxxx);
                           if (this.method_30481(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxx, -1)
                              && this.method_30481(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxx, 1)
                              && (_snowmanxxx == -1.0 || _snowmanxxx > _snowmanxxxxxxxxxxxxxxxx)) {
                              _snowmanxxx = _snowmanxxxxxxxxxxxxxxxx;
                              _snowmanxxxx = _snowmanxxxxxxxxxx.toImmutable();
                           }

                           if (_snowmanxxx == -1.0 && (_snowmanxxxxx == -1.0 || _snowmanxxxxx > _snowmanxxxxxxxxxxxxxxxx)) {
                              _snowmanxxxxx = _snowmanxxxxxxxxxxxxxxxx;
                              _snowmanxxxxxx = _snowmanxxxxxxxxxx.toImmutable();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (_snowmanxxx == -1.0 && _snowmanxxxxx != -1.0) {
         _snowmanxxxx = _snowmanxxxxxx;
         _snowmanxxx = _snowmanxxxxx;
      }

      if (_snowmanxxx == -1.0) {
         _snowmanxxxx = new BlockPos(_snowman.getX(), MathHelper.clamp(_snowman.getY(), 70, this.world.getDimensionHeight() - 10), _snowman.getZ()).toImmutable();
         Direction _snowmanxxxxxxxxxxx = _snowmanxx.rotateYClockwise();
         if (!_snowmanxxxxxxx.contains(_snowmanxxxx)) {
            return Optional.empty();
         }

         for (int _snowmanxxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxxxxxx++) {
                  BlockState _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx < 0 ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState();
                  _snowmanxxxxxxxxx.set(
                     _snowmanxxxx,
                     _snowmanxxxxxxxxxxxxxx * _snowmanxx.getOffsetX() + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxx.getOffsetX(),
                     _snowmanxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxx * _snowmanxx.getOffsetZ() + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxx.getOffsetZ()
                  );
                  this.world.setBlockState(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
               }
            }
         }
      }

      for (int _snowmanxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxx++) {
            if (_snowmanxxxxxxxxxxx == -1 || _snowmanxxxxxxxxxxx == 2 || _snowmanxxxxxxxxxxxx == -1 || _snowmanxxxxxxxxxxxx == 3) {
               _snowmanxxxxxxxxx.set(_snowmanxxxx, _snowmanxxxxxxxxxxx * _snowmanxx.getOffsetX(), _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx * _snowmanxx.getOffsetZ());
               this.world.setBlockState(_snowmanxxxxxxxxx, Blocks.OBSIDIAN.getDefaultState(), 3);
            }
         }
      }

      BlockState _snowmanxxxxxxxxxxx = Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, _snowman);

      for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxxxxxx++) {
            _snowmanxxxxxxxxx.set(_snowmanxxxx, _snowmanxxxxxxxxxxxxxx * _snowmanxx.getOffsetX(), _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx * _snowmanxx.getOffsetZ());
            this.world.setBlockState(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, 18);
         }
      }

      return Optional.of(new class_5459.class_5460(_snowmanxxxx.toImmutable(), 2, 3));
   }

   private boolean method_30481(BlockPos _snowman, BlockPos.Mutable _snowman, Direction _snowman, int _snowman) {
      Direction _snowmanxxxx = _snowman.rotateYClockwise();

      for (int _snowmanxxxxx = -1; _snowmanxxxxx < 3; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = -1; _snowmanxxxxxx < 4; _snowmanxxxxxx++) {
            _snowman.set(_snowman, _snowman.getOffsetX() * _snowmanxxxxx + _snowmanxxxx.getOffsetX() * _snowman, _snowmanxxxxxx, _snowman.getOffsetZ() * _snowmanxxxxx + _snowmanxxxx.getOffsetZ() * _snowman);
            if (_snowmanxxxxxx < 0 && !this.world.getBlockState(_snowman).getMaterial().isSolid()) {
               return false;
            }

            if (_snowmanxxxxxx >= 0 && !this.world.isAir(_snowman)) {
               return false;
            }
         }
      }

      return true;
   }
}
