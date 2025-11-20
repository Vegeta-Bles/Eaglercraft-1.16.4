package net.minecraft.structure.pool;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import net.minecraft.block.JigsawBlock;
import net.minecraft.structure.JigsawJunction;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructurePoolBasedGenerator {
   private static final Logger LOGGER = LogManager.getLogger();

   public static void method_30419(
      DynamicRegistryManager _snowman,
      StructurePoolFeatureConfig _snowman,
      StructurePoolBasedGenerator.PieceFactory _snowman,
      ChunkGenerator _snowman,
      StructureManager _snowman,
      BlockPos _snowman,
      List<? super PoolStructurePiece> _snowman,
      Random _snowman,
      boolean _snowman,
      boolean _snowman
   ) {
      StructureFeature.method_28664();
      MutableRegistry<StructurePool> _snowmanxxxxxxxxxx = _snowman.get(Registry.TEMPLATE_POOL_WORLDGEN);
      BlockRotation _snowmanxxxxxxxxxxx = BlockRotation.random(_snowman);
      StructurePool _snowmanxxxxxxxxxxxx = _snowman.getStartPool().get();
      StructurePoolElement _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getRandomElement(_snowman);
      PoolStructurePiece _snowmanxxxxxxxxxxxxxx = _snowman.create(
         _snowman, _snowmanxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxx.getGroundLevelDelta(), _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx.getBoundingBox(_snowman, _snowman, _snowmanxxxxxxxxxxx)
      );
      BlockBox _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.getBoundingBox();
      int _snowmanxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxx.maxX + _snowmanxxxxxxxxxxxxxxx.minX) / 2;
      int _snowmanxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxx.maxZ + _snowmanxxxxxxxxxxxxxxx.minZ) / 2;
      int _snowmanxxxxxxxxxxxxxxxxxx;
      if (_snowman) {
         _snowmanxxxxxxxxxxxxxxxxxx = _snowman.getY() + _snowman.getHeightOnGround(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, Heightmap.Type.WORLD_SURFACE_WG);
      } else {
         _snowmanxxxxxxxxxxxxxxxxxx = _snowman.getY();
      }

      int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.minY + _snowmanxxxxxxxxxxxxxx.getGroundLevelDelta();
      _snowmanxxxxxxxxxxxxxx.translate(0, _snowmanxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxx, 0);
      _snowman.add(_snowmanxxxxxxxxxxxxxx);
      if (_snowman.getSize() > 0) {
         int _snowmanxxxxxxxxxxxxxxxxxxxx = 80;
         Box _snowmanxxxxxxxxxxxxxxxxxxxxx = new Box(
            (double)(_snowmanxxxxxxxxxxxxxxxx - 80),
            (double)(_snowmanxxxxxxxxxxxxxxxxxx - 80),
            (double)(_snowmanxxxxxxxxxxxxxxxxx - 80),
            (double)(_snowmanxxxxxxxxxxxxxxxx + 80 + 1),
            (double)(_snowmanxxxxxxxxxxxxxxxxxx + 80 + 1),
            (double)(_snowmanxxxxxxxxxxxxxxxxx + 80 + 1)
         );
         StructurePoolBasedGenerator.StructurePoolGenerator _snowmanxxxxxxxxxxxxxxxxxxxxxx = new StructurePoolBasedGenerator.StructurePoolGenerator(
            _snowmanxxxxxxxxxx, _snowman.getSize(), _snowman, _snowman, _snowman, _snowman, _snowman
         );
         _snowmanxxxxxxxxxxxxxxxxxxxxxx.structurePieces
            .addLast(
               new StructurePoolBasedGenerator.ShapedPoolStructurePiece(
                  _snowmanxxxxxxxxxxxxxx,
                  new MutableObject(
                     VoxelShapes.combineAndSimplify(
                        VoxelShapes.cuboid(_snowmanxxxxxxxxxxxxxxxxxxxxx), VoxelShapes.cuboid(Box.from(_snowmanxxxxxxxxxxxxxxx)), BooleanBiFunction.ONLY_FIRST
                     )
                  ),
                  _snowmanxxxxxxxxxxxxxxxxxx + 80,
                  0
               )
            );

         while (!_snowmanxxxxxxxxxxxxxxxxxxxxxx.structurePieces.isEmpty()) {
            StructurePoolBasedGenerator.ShapedPoolStructurePiece _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.structurePieces.removeFirst();
            _snowmanxxxxxxxxxxxxxxxxxxxxxx.generatePiece(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx.piece, _snowmanxxxxxxxxxxxxxxxxxxxxxxx.pieceShape, _snowmanxxxxxxxxxxxxxxxxxxxxxxx.minY, _snowmanxxxxxxxxxxxxxxxxxxxxxxx.currentSize, _snowman
            );
         }
      }
   }

   public static void method_27230(
      DynamicRegistryManager _snowman,
      PoolStructurePiece _snowman,
      int _snowman,
      StructurePoolBasedGenerator.PieceFactory _snowman,
      ChunkGenerator _snowman,
      StructureManager _snowman,
      List<? super PoolStructurePiece> _snowman,
      Random _snowman
   ) {
      MutableRegistry<StructurePool> _snowmanxxxxxxxx = _snowman.get(Registry.TEMPLATE_POOL_WORLDGEN);
      StructurePoolBasedGenerator.StructurePoolGenerator _snowmanxxxxxxxxx = new StructurePoolBasedGenerator.StructurePoolGenerator(_snowmanxxxxxxxx, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowmanxxxxxxxxx.structurePieces.addLast(new StructurePoolBasedGenerator.ShapedPoolStructurePiece(_snowman, new MutableObject(VoxelShapes.UNBOUNDED), 0, 0));

      while (!_snowmanxxxxxxxxx.structurePieces.isEmpty()) {
         StructurePoolBasedGenerator.ShapedPoolStructurePiece _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.structurePieces.removeFirst();
         _snowmanxxxxxxxxx.generatePiece(_snowmanxxxxxxxxxx.piece, _snowmanxxxxxxxxxx.pieceShape, _snowmanxxxxxxxxxx.minY, _snowmanxxxxxxxxxx.currentSize, false);
      }
   }

   public interface PieceFactory {
      PoolStructurePiece create(
         StructureManager structureManager, StructurePoolElement poolElement, BlockPos pos, int var4, BlockRotation rotation, BlockBox elementBounds
      );
   }

   static final class ShapedPoolStructurePiece {
      private final PoolStructurePiece piece;
      private final MutableObject<VoxelShape> pieceShape;
      private final int minY;
      private final int currentSize;

      private ShapedPoolStructurePiece(PoolStructurePiece piece, MutableObject<VoxelShape> _snowman, int minY, int currentSize) {
         this.piece = piece;
         this.pieceShape = _snowman;
         this.minY = minY;
         this.currentSize = currentSize;
      }
   }

   static final class StructurePoolGenerator {
      private final Registry<StructurePool> field_25852;
      private final int maxSize;
      private final StructurePoolBasedGenerator.PieceFactory pieceFactory;
      private final ChunkGenerator chunkGenerator;
      private final StructureManager structureManager;
      private final List<? super PoolStructurePiece> children;
      private final Random random;
      private final Deque<StructurePoolBasedGenerator.ShapedPoolStructurePiece> structurePieces = Queues.newArrayDeque();

      private StructurePoolGenerator(
         Registry<StructurePool> _snowman,
         int _snowman,
         StructurePoolBasedGenerator.PieceFactory _snowman,
         ChunkGenerator _snowman,
         StructureManager _snowman,
         List<? super PoolStructurePiece> _snowman,
         Random _snowman
      ) {
         this.field_25852 = _snowman;
         this.maxSize = _snowman;
         this.pieceFactory = _snowman;
         this.chunkGenerator = _snowman;
         this.structureManager = _snowman;
         this.children = _snowman;
         this.random = _snowman;
      }

      private void generatePiece(PoolStructurePiece piece, MutableObject<VoxelShape> _snowman, int minY, int currentSize, boolean _snowman) {
         StructurePoolElement _snowmanxx = piece.getPoolElement();
         BlockPos _snowmanxxx = piece.getPos();
         BlockRotation _snowmanxxxx = piece.getRotation();
         StructurePool.Projection _snowmanxxxxx = _snowmanxx.getProjection();
         boolean _snowmanxxxxxx = _snowmanxxxxx == StructurePool.Projection.RIGID;
         MutableObject<VoxelShape> _snowmanxxxxxxx = new MutableObject();
         BlockBox _snowmanxxxxxxxx = piece.getBoundingBox();
         int _snowmanxxxxxxxxx = _snowmanxxxxxxxx.minY;

         label137:
         for (Structure.StructureBlockInfo _snowmanxxxxxxxxxx : _snowmanxx.getStructureBlockInfos(this.structureManager, _snowmanxxx, _snowmanxxxx, this.random)) {
            Direction _snowmanxxxxxxxxxxx = JigsawBlock.getFacing(_snowmanxxxxxxxxxx.state);
            BlockPos _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.pos;
            BlockPos _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.offset(_snowmanxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getY() - _snowmanxxxxxxxxx;
            int _snowmanxxxxxxxxxxxxxxx = -1;
            Identifier _snowmanxxxxxxxxxxxxxxxx = new Identifier(_snowmanxxxxxxxxxx.tag.getString("pool"));
            Optional<StructurePool> _snowmanxxxxxxxxxxxxxxxxx = this.field_25852.getOrEmpty(_snowmanxxxxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxxxxx.isPresent()
               && (_snowmanxxxxxxxxxxxxxxxxx.get().getElementCount() != 0 || Objects.equals(_snowmanxxxxxxxxxxxxxxxx, StructurePools.EMPTY.getValue()))) {
               Identifier _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.get().getTerminatorsId();
               Optional<StructurePool> _snowmanxxxxxxxxxxxxxxxxxxx = this.field_25852.getOrEmpty(_snowmanxxxxxxxxxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxxxxxxxx.isPresent()
                  && (_snowmanxxxxxxxxxxxxxxxxxxx.get().getElementCount() != 0 || Objects.equals(_snowmanxxxxxxxxxxxxxxxxxx, StructurePools.EMPTY.getValue()))) {
                  boolean _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.contains(_snowmanxxxxxxxxxxxxx);
                  MutableObject<VoxelShape> _snowmanxxxxxxxxxxxxxxxxxxxxx;
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx;
                     if (_snowmanxxxxxxx.getValue() == null) {
                        _snowmanxxxxxxx.setValue(VoxelShapes.cuboid(Box.from(_snowmanxxxxxxxx)));
                     }
                  } else {
                     _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxx = minY;
                  }

                  List<StructurePoolElement> _snowmanxxxxxxxxxxxxxxxxxxxxxxx = Lists.newArrayList();
                  if (currentSize != this.maxSize) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx.addAll(_snowmanxxxxxxxxxxxxxxxxx.get().getElementIndicesInRandomOrder(this.random));
                  }

                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx.addAll(_snowmanxxxxxxxxxxxxxxxxxxx.get().getElementIndicesInRandomOrder(this.random));

                  for (StructurePoolElement _snowmanxxxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxxxxxx) {
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx == EmptyPoolElement.INSTANCE) {
                        break;
                     }

                     for (BlockRotation _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx : BlockRotation.randomRotationOrder(this.random)) {
                        List<Structure.StructureBlockInfo> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getStructureBlockInfos(
                           this.structureManager, BlockPos.ORIGIN, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, this.random
                        );
                        BlockBox _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getBoundingBox(
                           this.structureManager, BlockPos.ORIGIN, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                        if (_snowman && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.getBlockCountY() <= 16) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.stream()
                              .mapToInt(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> {
                                    if (!_snowman.contains(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.pos.offset(JigsawBlock.getFacing(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.state)))) {
                                       return 0;
                                    } else {
                                       Identifier _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Identifier(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.tag.getString("pool"));
                                       Optional<StructurePool> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.field_25852.getOrEmpty(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                                       Optional<StructurePool> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.flatMap(
                                          _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> this.field_25852
                                                .getOrEmpty(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getTerminatorsId())
                                       );
                                       int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.<Integer>map(
                                             _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getHighestY(this.structureManager)
                                          )
                                          .orElse(0);
                                       int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.<Integer>map(
                                             _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getHighestY(this.structureManager)
                                          )
                                          .orElse(0);
                                       return Math.max(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                                    }
                                 }
                              )
                              .max()
                              .orElse(0);
                        } else {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
                        }

                        for (Structure.StructureBlockInfo _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                           if (JigsawBlock.attachmentMatches(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)) {
                              BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.pos;
                              BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new BlockPos(
                                 _snowmanxxxxxxxxxxxxx.getX() - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getX(),
                                 _snowmanxxxxxxxxxxxxx.getY() - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getY(),
                                 _snowmanxxxxxxxxxxxxx.getZ() - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getZ()
                              );
                              BlockBox _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getBoundingBox(
                                 this.structureManager, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx
                              );
                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.minY;
                              StructurePool.Projection _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getProjection();
                              boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == StructurePool.Projection.RIGID;
                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getY();
                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx
                                 - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                 + JigsawBlock.getFacing(_snowmanxxxxxxxxxx.state).getOffsetY();
                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              if (_snowmanxxxxxx && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              } else {
                                 if (_snowmanxxxxxxxxxxxxxxx == -1) {
                                    _snowmanxxxxxxxxxxxxxxx = this.chunkGenerator
                                       .getHeightOnGround(_snowmanxxxxxxxxxxxx.getX(), _snowmanxxxxxxxxxxxx.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                 }

                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              }

                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              BlockBox _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.offset(
                                 0, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0
                              );
                              BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.add(
                                 0, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0
                              );
                              if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx > 0) {
                                 int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1,
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.maxY - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.minY
                                 );
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.maxY = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.minY
                                    + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              }

                              if (!VoxelShapes.matchesAnywhere(
                                 (VoxelShape)_snowmanxxxxxxxxxxxxxxxxxxxxx.getValue(),
                                 VoxelShapes.cuboid(Box.from(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx).contract(0.25)),
                                 BooleanBiFunction.ONLY_SECOND
                              )) {
                                 _snowmanxxxxxxxxxxxxxxxxxxxxx.setValue(
                                    VoxelShapes.combine(
                                       (VoxelShape)_snowmanxxxxxxxxxxxxxxxxxxxxx.getValue(),
                                       VoxelShapes.cuboid(Box.from(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)),
                                       BooleanBiFunction.ONLY_FIRST
                                    )
                                 );
                                 int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = piece.getGroundLevelDelta();
                                 int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                       - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 } else {
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getGroundLevelDelta();
                                 }

                                 PoolStructurePiece _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.pieceFactory
                                    .create(
                                       this.structureManager,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                    );
                                 int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 if (_snowmanxxxxxx) {
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxx;
                                 } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                       + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 } else {
                                    if (_snowmanxxxxxxxxxxxxxxx == -1) {
                                       _snowmanxxxxxxxxxxxxxxx = this.chunkGenerator
                                          .getHeightOnGround(_snowmanxxxxxxxxxxxx.getX(), _snowmanxxxxxxxxxxxx.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                    }

                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx / 2;
                                 }

                                 piece.addJunction(
                                    new JigsawJunction(
                                       _snowmanxxxxxxxxxxxxx.getX(),
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxx.getZ(),
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                    )
                                 );
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.addJunction(
                                    new JigsawJunction(
                                       _snowmanxxxxxxxxxxxx.getX(),
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                          - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                          + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxx.getZ(),
                                       -_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxx
                                    )
                                 );
                                 this.children.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                                 if (currentSize + 1 <= this.maxSize) {
                                    this.structurePieces
                                       .addLast(
                                          new StructurePoolBasedGenerator.ShapedPoolStructurePiece(
                                             _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, currentSize + 1
                                          )
                                       );
                                 }
                                 continue label137;
                              }
                           }
                        }
                     }
                  }
               } else {
                  StructurePoolBasedGenerator.LOGGER.warn("Empty or none existent fallback pool: {}", _snowmanxxxxxxxxxxxxxxxxxx);
               }
            } else {
               StructurePoolBasedGenerator.LOGGER.warn("Empty or none existent pool: {}", _snowmanxxxxxxxxxxxxxxxx);
            }
         }
      }
   }
}
