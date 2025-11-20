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
      DynamicRegistryManager arg,
      StructurePoolFeatureConfig arg2,
      StructurePoolBasedGenerator.PieceFactory arg3,
      ChunkGenerator arg4,
      StructureManager arg5,
      BlockPos arg6,
      List<? super PoolStructurePiece> list,
      Random random,
      boolean bl,
      boolean bl2
   ) {
      StructureFeature.method_28664();
      MutableRegistry<StructurePool> lv = arg.get(Registry.TEMPLATE_POOL_WORLDGEN);
      BlockRotation lv2 = BlockRotation.random(random);
      StructurePool lv3 = arg2.getStartPool().get();
      StructurePoolElement lv4 = lv3.getRandomElement(random);
      PoolStructurePiece lv5 = arg3.create(arg5, lv4, arg6, lv4.getGroundLevelDelta(), lv2, lv4.getBoundingBox(arg5, arg6, lv2));
      BlockBox lv6 = lv5.getBoundingBox();
      int i = (lv6.maxX + lv6.minX) / 2;
      int j = (lv6.maxZ + lv6.minZ) / 2;
      int k;
      if (bl2) {
         k = arg6.getY() + arg4.getHeightOnGround(i, j, Heightmap.Type.WORLD_SURFACE_WG);
      } else {
         k = arg6.getY();
      }

      int m = lv6.minY + lv5.getGroundLevelDelta();
      lv5.translate(0, k - m, 0);
      list.add(lv5);
      if (arg2.getSize() > 0) {
         int n = 80;
         Box lv7 = new Box((double)(i - 80), (double)(k - 80), (double)(j - 80), (double)(i + 80 + 1), (double)(k + 80 + 1), (double)(j + 80 + 1));
         StructurePoolBasedGenerator.StructurePoolGenerator lv8 = new StructurePoolBasedGenerator.StructurePoolGenerator(
            lv, arg2.getSize(), arg3, arg4, arg5, list, random
         );
         lv8.structurePieces
            .addLast(
               new StructurePoolBasedGenerator.ShapedPoolStructurePiece(
                  lv5,
                  new MutableObject(VoxelShapes.combineAndSimplify(VoxelShapes.cuboid(lv7), VoxelShapes.cuboid(Box.from(lv6)), BooleanBiFunction.ONLY_FIRST)),
                  k + 80,
                  0
               )
            );

         while (!lv8.structurePieces.isEmpty()) {
            StructurePoolBasedGenerator.ShapedPoolStructurePiece lv9 = lv8.structurePieces.removeFirst();
            lv8.generatePiece(lv9.piece, lv9.pieceShape, lv9.minY, lv9.currentSize, bl);
         }
      }
   }

   public static void method_27230(
      DynamicRegistryManager arg,
      PoolStructurePiece arg2,
      int i,
      StructurePoolBasedGenerator.PieceFactory arg3,
      ChunkGenerator arg4,
      StructureManager arg5,
      List<? super PoolStructurePiece> list,
      Random random
   ) {
      MutableRegistry<StructurePool> lv = arg.get(Registry.TEMPLATE_POOL_WORLDGEN);
      StructurePoolBasedGenerator.StructurePoolGenerator lv2 = new StructurePoolBasedGenerator.StructurePoolGenerator(lv, i, arg3, arg4, arg5, list, random);
      lv2.structurePieces.addLast(new StructurePoolBasedGenerator.ShapedPoolStructurePiece(arg2, new MutableObject(VoxelShapes.UNBOUNDED), 0, 0));

      while (!lv2.structurePieces.isEmpty()) {
         StructurePoolBasedGenerator.ShapedPoolStructurePiece lv3 = lv2.structurePieces.removeFirst();
         lv2.generatePiece(lv3.piece, lv3.pieceShape, lv3.minY, lv3.currentSize, false);
      }
   }

   public interface PieceFactory {
      PoolStructurePiece create(
         StructureManager structureManager, StructurePoolElement poolElement, BlockPos pos, int i, BlockRotation rotation, BlockBox elementBounds
      );
   }

   static final class ShapedPoolStructurePiece {
      private final PoolStructurePiece piece;
      private final MutableObject<VoxelShape> pieceShape;
      private final int minY;
      private final int currentSize;

      private ShapedPoolStructurePiece(PoolStructurePiece piece, MutableObject<VoxelShape> mutableObject, int minY, int currentSize) {
         this.piece = piece;
         this.pieceShape = mutableObject;
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
         Registry<StructurePool> arg,
         int i,
         StructurePoolBasedGenerator.PieceFactory arg2,
         ChunkGenerator arg3,
         StructureManager arg4,
         List<? super PoolStructurePiece> list,
         Random random
      ) {
         this.field_25852 = arg;
         this.maxSize = i;
         this.pieceFactory = arg2;
         this.chunkGenerator = arg3;
         this.structureManager = arg4;
         this.children = list;
         this.random = random;
      }

      private void generatePiece(PoolStructurePiece piece, MutableObject<VoxelShape> mutableObject, int minY, int currentSize, boolean bl) {
         StructurePoolElement lv = piece.getPoolElement();
         BlockPos lv2 = piece.getPos();
         BlockRotation lv3 = piece.getRotation();
         StructurePool.Projection lv4 = lv.getProjection();
         boolean bl2 = lv4 == StructurePool.Projection.RIGID;
         MutableObject<VoxelShape> mutableObject2 = new MutableObject();
         BlockBox lv5 = piece.getBoundingBox();
         int k = lv5.minY;

         label137:
         for (Structure.StructureBlockInfo lv6 : lv.getStructureBlockInfos(this.structureManager, lv2, lv3, this.random)) {
            Direction lv7 = JigsawBlock.getFacing(lv6.state);
            BlockPos lv8 = lv6.pos;
            BlockPos lv9 = lv8.offset(lv7);
            int l = lv8.getY() - k;
            int m = -1;
            Identifier lv10 = new Identifier(lv6.tag.getString("pool"));
            Optional<StructurePool> optional = this.field_25852.getOrEmpty(lv10);
            if (optional.isPresent() && (optional.get().getElementCount() != 0 || Objects.equals(lv10, StructurePools.EMPTY.getValue()))) {
               Identifier lv11 = optional.get().getTerminatorsId();
               Optional<StructurePool> optional2 = this.field_25852.getOrEmpty(lv11);
               if (optional2.isPresent() && (optional2.get().getElementCount() != 0 || Objects.equals(lv11, StructurePools.EMPTY.getValue()))) {
                  boolean bl3 = lv5.contains(lv9);
                  MutableObject<VoxelShape> mutableObject3;
                  int n;
                  if (bl3) {
                     mutableObject3 = mutableObject2;
                     n = k;
                     if (mutableObject2.getValue() == null) {
                        mutableObject2.setValue(VoxelShapes.cuboid(Box.from(lv5)));
                     }
                  } else {
                     mutableObject3 = mutableObject;
                     n = minY;
                  }

                  List<StructurePoolElement> list = Lists.newArrayList();
                  if (currentSize != this.maxSize) {
                     list.addAll(optional.get().getElementIndicesInRandomOrder(this.random));
                  }

                  list.addAll(optional2.get().getElementIndicesInRandomOrder(this.random));

                  for (StructurePoolElement lv12 : list) {
                     if (lv12 == EmptyPoolElement.INSTANCE) {
                        break;
                     }

                     for (BlockRotation lv13 : BlockRotation.randomRotationOrder(this.random)) {
                        List<Structure.StructureBlockInfo> list2 = lv12.getStructureBlockInfos(this.structureManager, BlockPos.ORIGIN, lv13, this.random);
                        BlockBox lv14 = lv12.getBoundingBox(this.structureManager, BlockPos.ORIGIN, lv13);
                        int q;
                        if (bl && lv14.getBlockCountY() <= 16) {
                           q = list2.stream().mapToInt(arg2 -> {
                              if (!lv14.contains(arg2.pos.offset(JigsawBlock.getFacing(arg2.state)))) {
                                 return 0;
                              } else {
                                 Identifier lvx = new Identifier(arg2.tag.getString("pool"));
                                 Optional<StructurePool> optionalx = this.field_25852.getOrEmpty(lvx);
                                 Optional<StructurePool> optional2x = optionalx.flatMap(arg -> this.field_25852.getOrEmpty(arg.getTerminatorsId()));
                                 int i = optionalx.<Integer>map(arg -> arg.getHighestY(this.structureManager)).orElse(0);
                                 int j = optional2x.<Integer>map(arg -> arg.getHighestY(this.structureManager)).orElse(0);
                                 return Math.max(i, j);
                              }
                           }).max().orElse(0);
                        } else {
                           q = 0;
                        }

                        for (Structure.StructureBlockInfo lv15 : list2) {
                           if (JigsawBlock.attachmentMatches(lv6, lv15)) {
                              BlockPos lv16 = lv15.pos;
                              BlockPos lv17 = new BlockPos(lv9.getX() - lv16.getX(), lv9.getY() - lv16.getY(), lv9.getZ() - lv16.getZ());
                              BlockBox lv18 = lv12.getBoundingBox(this.structureManager, lv17, lv13);
                              int r = lv18.minY;
                              StructurePool.Projection lv19 = lv12.getProjection();
                              boolean bl4 = lv19 == StructurePool.Projection.RIGID;
                              int s = lv16.getY();
                              int t = l - s + JigsawBlock.getFacing(lv6.state).getOffsetY();
                              int u;
                              if (bl2 && bl4) {
                                 u = k + t;
                              } else {
                                 if (m == -1) {
                                    m = this.chunkGenerator.getHeightOnGround(lv8.getX(), lv8.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                 }

                                 u = m - s;
                              }

                              int w = u - r;
                              BlockBox lv20 = lv18.offset(0, w, 0);
                              BlockPos lv21 = lv17.add(0, w, 0);
                              if (q > 0) {
                                 int x = Math.max(q + 1, lv20.maxY - lv20.minY);
                                 lv20.maxY = lv20.minY + x;
                              }

                              if (!VoxelShapes.matchesAnywhere(
                                 (VoxelShape)mutableObject3.getValue(), VoxelShapes.cuboid(Box.from(lv20).contract(0.25)), BooleanBiFunction.ONLY_SECOND
                              )) {
                                 mutableObject3.setValue(
                                    VoxelShapes.combine((VoxelShape)mutableObject3.getValue(), VoxelShapes.cuboid(Box.from(lv20)), BooleanBiFunction.ONLY_FIRST)
                                 );
                                 int y = piece.getGroundLevelDelta();
                                 int z;
                                 if (bl4) {
                                    z = y - t;
                                 } else {
                                    z = lv12.getGroundLevelDelta();
                                 }

                                 PoolStructurePiece lv22 = this.pieceFactory.create(this.structureManager, lv12, lv21, z, lv13, lv20);
                                 int ab;
                                 if (bl2) {
                                    ab = k + l;
                                 } else if (bl4) {
                                    ab = u + s;
                                 } else {
                                    if (m == -1) {
                                       m = this.chunkGenerator.getHeightOnGround(lv8.getX(), lv8.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                    }

                                    ab = m + t / 2;
                                 }

                                 piece.addJunction(new JigsawJunction(lv9.getX(), ab - l + y, lv9.getZ(), t, lv19));
                                 lv22.addJunction(new JigsawJunction(lv8.getX(), ab - s + z, lv8.getZ(), -t, lv4));
                                 this.children.add(lv22);
                                 if (currentSize + 1 <= this.maxSize) {
                                    this.structurePieces
                                       .addLast(new StructurePoolBasedGenerator.ShapedPoolStructurePiece(lv22, mutableObject3, n, currentSize + 1));
                                 }
                                 continue label137;
                              }
                           }
                        }
                     }
                  }
               } else {
                  StructurePoolBasedGenerator.LOGGER.warn("Empty or none existent fallback pool: {}", lv11);
               }
            } else {
               StructurePoolBasedGenerator.LOGGER.warn("Empty or none existent pool: {}", lv10);
            }
         }
      }
   }
}
