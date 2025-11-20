/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Queues
 *  org.apache.commons.lang3.mutable.MutableObject
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.structure.pool;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import net.minecraft.block.JigsawBlock;
import net.minecraft.structure.JigsawJunction;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.EmptyPoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
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

    public static void method_30419(DynamicRegistryManager dynamicRegistryManager, StructurePoolFeatureConfig structurePoolFeatureConfig, PieceFactory pieceFactory, ChunkGenerator chunkGenerator, StructureManager structureManager, BlockPos blockPos, List<? super PoolStructurePiece> list, Random random, boolean bl, boolean bl2) {
        StructureFeature.method_28664();
        MutableRegistry<StructurePool> mutableRegistry = dynamicRegistryManager.get(Registry.TEMPLATE_POOL_WORLDGEN);
        BlockRotation _snowman2 = BlockRotation.random(random);
        StructurePool _snowman3 = structurePoolFeatureConfig.getStartPool().get();
        StructurePoolElement _snowman4 = _snowman3.getRandomElement(random);
        PoolStructurePiece _snowman5 = pieceFactory.create(structureManager, _snowman4, blockPos, _snowman4.getGroundLevelDelta(), _snowman2, _snowman4.getBoundingBox(structureManager, blockPos, _snowman2));
        BlockBox _snowman6 = _snowman5.getBoundingBox();
        int _snowman7 = (_snowman6.maxX + _snowman6.minX) / 2;
        int _snowman8 = (_snowman6.maxZ + _snowman6.minZ) / 2;
        int _snowman9 = bl2 ? blockPos.getY() + chunkGenerator.getHeightOnGround(_snowman7, _snowman8, Heightmap.Type.WORLD_SURFACE_WG) : blockPos.getY();
        int _snowman10 = _snowman6.minY + _snowman5.getGroundLevelDelta();
        _snowman5.translate(0, _snowman9 - _snowman10, 0);
        list.add(_snowman5);
        if (structurePoolFeatureConfig.getSize() <= 0) {
            return;
        }
        int _snowman11 = 80;
        Box _snowman12 = new Box(_snowman7 - 80, _snowman9 - 80, _snowman8 - 80, _snowman7 + 80 + 1, _snowman9 + 80 + 1, _snowman8 + 80 + 1);
        StructurePoolGenerator _snowman13 = new StructurePoolGenerator(mutableRegistry, structurePoolFeatureConfig.getSize(), pieceFactory, chunkGenerator, structureManager, list, random);
        _snowman13.structurePieces.addLast(new ShapedPoolStructurePiece(_snowman5, new MutableObject((Object)VoxelShapes.combineAndSimplify(VoxelShapes.cuboid(_snowman12), VoxelShapes.cuboid(Box.from(_snowman6)), BooleanBiFunction.ONLY_FIRST)), _snowman9 + 80, 0));
        while (!_snowman13.structurePieces.isEmpty()) {
            ShapedPoolStructurePiece shapedPoolStructurePiece = (ShapedPoolStructurePiece)_snowman13.structurePieces.removeFirst();
            _snowman13.generatePiece(shapedPoolStructurePiece.piece, (MutableObject<VoxelShape>)shapedPoolStructurePiece.pieceShape, shapedPoolStructurePiece.minY, shapedPoolStructurePiece.currentSize, bl);
        }
    }

    public static void method_27230(DynamicRegistryManager dynamicRegistryManager, PoolStructurePiece poolStructurePiece, int n, PieceFactory pieceFactory, ChunkGenerator chunkGenerator, StructureManager structureManager, List<? super PoolStructurePiece> list, Random random) {
        MutableRegistry<StructurePool> mutableRegistry = dynamicRegistryManager.get(Registry.TEMPLATE_POOL_WORLDGEN);
        StructurePoolGenerator _snowman2 = new StructurePoolGenerator(mutableRegistry, n, pieceFactory, chunkGenerator, structureManager, list, random);
        _snowman2.structurePieces.addLast(new ShapedPoolStructurePiece(poolStructurePiece, new MutableObject((Object)VoxelShapes.UNBOUNDED), 0, 0));
        while (!_snowman2.structurePieces.isEmpty()) {
            ShapedPoolStructurePiece shapedPoolStructurePiece = (ShapedPoolStructurePiece)_snowman2.structurePieces.removeFirst();
            _snowman2.generatePiece(shapedPoolStructurePiece.piece, (MutableObject<VoxelShape>)shapedPoolStructurePiece.pieceShape, shapedPoolStructurePiece.minY, shapedPoolStructurePiece.currentSize, false);
        }
    }

    public static interface PieceFactory {
        public PoolStructurePiece create(StructureManager var1, StructurePoolElement var2, BlockPos var3, int var4, BlockRotation var5, BlockBox var6);
    }

    static final class StructurePoolGenerator {
        private final Registry<StructurePool> field_25852;
        private final int maxSize;
        private final PieceFactory pieceFactory;
        private final ChunkGenerator chunkGenerator;
        private final StructureManager structureManager;
        private final List<? super PoolStructurePiece> children;
        private final Random random;
        private final Deque<ShapedPoolStructurePiece> structurePieces = Queues.newArrayDeque();

        private StructurePoolGenerator(Registry<StructurePool> registry, int n, PieceFactory pieceFactory, ChunkGenerator chunkGenerator, StructureManager structureManager, List<? super PoolStructurePiece> list, Random random) {
            this.field_25852 = registry;
            this.maxSize = n;
            this.pieceFactory = pieceFactory;
            this.chunkGenerator = chunkGenerator;
            this.structureManager = structureManager;
            this.children = list;
            this.random = random;
        }

        private void generatePiece(PoolStructurePiece piece, MutableObject<VoxelShape> mutableObject, int minY, int currentSize, boolean bl) {
            StructurePoolElement structurePoolElement = piece.getPoolElement();
            BlockPos _snowman2 = piece.getPos();
            BlockRotation _snowman3 = piece.getRotation();
            StructurePool.Projection _snowman4 = structurePoolElement.getProjection();
            boolean _snowman5 = _snowman4 == StructurePool.Projection.RIGID;
            MutableObject<VoxelShape> _snowman6 = new MutableObject<VoxelShape>();
            BlockBox _snowman7 = piece.getBoundingBox();
            int _snowman8 = _snowman7.minY;
            block0: for (Structure.StructureBlockInfo structureBlockInfo2 : structurePoolElement.getStructureBlockInfos(this.structureManager, _snowman2, _snowman3, this.random)) {
                int _snowman18;
                Direction direction = JigsawBlock.getFacing(structureBlockInfo2.state);
                BlockPos _snowman9 = structureBlockInfo2.pos;
                BlockPos _snowman10 = _snowman9.offset(direction);
                int _snowman11 = _snowman9.getY() - _snowman8;
                int _snowman12 = -1;
                Identifier _snowman13 = new Identifier(structureBlockInfo2.tag.getString("pool"));
                Optional<StructurePool> _snowman14 = this.field_25852.getOrEmpty(_snowman13);
                if (!_snowman14.isPresent() || _snowman14.get().getElementCount() == 0 && !Objects.equals(_snowman13, StructurePools.EMPTY.getValue())) {
                    LOGGER.warn("Empty or none existent pool: {}", (Object)_snowman13);
                    continue;
                }
                Identifier _snowman15 = _snowman14.get().getTerminatorsId();
                Optional<StructurePool> _snowman16 = this.field_25852.getOrEmpty(_snowman15);
                if (!_snowman16.isPresent() || _snowman16.get().getElementCount() == 0 && !Objects.equals(_snowman15, StructurePools.EMPTY.getValue())) {
                    LOGGER.warn("Empty or none existent fallback pool: {}", (Object)_snowman15);
                    continue;
                }
                boolean _snowman17 = _snowman7.contains(_snowman10);
                if (_snowman17) {
                    MutableObject<VoxelShape> mutableObject2 = _snowman6;
                    _snowman18 = _snowman8;
                    if (_snowman6.getValue() == null) {
                        _snowman6.setValue((Object)VoxelShapes.cuboid(Box.from(_snowman7)));
                    }
                } else {
                    mutableObject2 = mutableObject;
                    _snowman18 = minY;
                }
                ArrayList arrayList = Lists.newArrayList();
                if (currentSize != this.maxSize) {
                    arrayList.addAll(_snowman14.get().getElementIndicesInRandomOrder(this.random));
                }
                arrayList.addAll(_snowman16.get().getElementIndicesInRandomOrder(this.random));
                Iterator iterator = arrayList.iterator();
                while (iterator.hasNext() && (_snowman = (StructurePoolElement)iterator.next()) != EmptyPoolElement.INSTANCE) {
                    for (BlockRotation blockRotation : BlockRotation.randomRotationOrder(this.random)) {
                        List<Structure.StructureBlockInfo> list = _snowman.getStructureBlockInfos(this.structureManager, BlockPos.ORIGIN, blockRotation, this.random);
                        BlockBox _snowman19 = _snowman.getBoundingBox(this.structureManager, BlockPos.ORIGIN, blockRotation);
                        int _snowman20 = !bl || _snowman19.getBlockCountY() > 16 ? 0 : list.stream().mapToInt(structureBlockInfo -> {
                            if (!_snowman19.contains(structureBlockInfo.pos.offset(JigsawBlock.getFacing(structureBlockInfo.state)))) {
                                return 0;
                            }
                            Identifier identifier = new Identifier(structureBlockInfo.tag.getString("pool"));
                            Optional<StructurePool> _snowman2 = this.field_25852.getOrEmpty(identifier);
                            Optional<Integer> _snowman3 = _snowman2.flatMap(structurePool -> this.field_25852.getOrEmpty(structurePool.getTerminatorsId()));
                            int _snowman4 = _snowman2.map(structurePool -> structurePool.getHighestY(this.structureManager)).orElse(0);
                            int _snowman5 = _snowman3.map(structurePool -> structurePool.getHighestY(this.structureManager)).orElse(0);
                            return Math.max(_snowman4, _snowman5);
                        }).max().orElse(0);
                        for (Structure.StructureBlockInfo structureBlockInfo3 : list) {
                            int n;
                            if (!JigsawBlock.attachmentMatches(structureBlockInfo2, structureBlockInfo3)) continue;
                            BlockPos blockPos = structureBlockInfo3.pos;
                            _snowman = new BlockPos(_snowman10.getX() - blockPos.getX(), _snowman10.getY() - blockPos.getY(), _snowman10.getZ() - blockPos.getZ());
                            BlockBox _snowman21 = _snowman.getBoundingBox(this.structureManager, _snowman, blockRotation);
                            int _snowman22 = _snowman21.minY;
                            StructurePool.Projection _snowman23 = _snowman.getProjection();
                            boolean _snowman24 = _snowman23 == StructurePool.Projection.RIGID;
                            int _snowman25 = blockPos.getY();
                            int _snowman26 = _snowman11 - _snowman25 + JigsawBlock.getFacing(structureBlockInfo2.state).getOffsetY();
                            if (_snowman5 && _snowman24) {
                                n = _snowman8 + _snowman26;
                            } else {
                                if (_snowman12 == -1) {
                                    _snowman12 = this.chunkGenerator.getHeightOnGround(_snowman9.getX(), _snowman9.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                }
                                n = _snowman12 - _snowman25;
                            }
                            _snowman = n - _snowman22;
                            BlockBox _snowman27 = _snowman21.offset(0, _snowman, 0);
                            BlockPos _snowman28 = _snowman.add(0, _snowman, 0);
                            if (_snowman20 > 0) {
                                _snowman = Math.max(_snowman20 + 1, _snowman27.maxY - _snowman27.minY);
                                _snowman27.maxY = _snowman27.minY + _snowman;
                            }
                            if (VoxelShapes.matchesAnywhere((VoxelShape)mutableObject2.getValue(), VoxelShapes.cuboid(Box.from(_snowman27).contract(0.25)), BooleanBiFunction.ONLY_SECOND)) continue;
                            mutableObject2.setValue((Object)VoxelShapes.combine((VoxelShape)mutableObject2.getValue(), VoxelShapes.cuboid(Box.from(_snowman27)), BooleanBiFunction.ONLY_FIRST));
                            _snowman = piece.getGroundLevelDelta();
                            _snowman = _snowman24 ? _snowman - _snowman26 : _snowman.getGroundLevelDelta();
                            PoolStructurePiece _snowman29 = this.pieceFactory.create(this.structureManager, _snowman, _snowman28, _snowman, blockRotation, _snowman27);
                            if (_snowman5) {
                                _snowman = _snowman8 + _snowman11;
                            } else if (_snowman24) {
                                _snowman = n + _snowman25;
                            } else {
                                if (_snowman12 == -1) {
                                    _snowman12 = this.chunkGenerator.getHeightOnGround(_snowman9.getX(), _snowman9.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                }
                                _snowman = _snowman12 + _snowman26 / 2;
                            }
                            piece.addJunction(new JigsawJunction(_snowman10.getX(), _snowman - _snowman11 + _snowman, _snowman10.getZ(), _snowman26, _snowman23));
                            _snowman29.addJunction(new JigsawJunction(_snowman9.getX(), _snowman - _snowman25 + _snowman, _snowman9.getZ(), -_snowman26, _snowman4));
                            this.children.add(_snowman29);
                            if (currentSize + 1 > this.maxSize) continue block0;
                            this.structurePieces.addLast(new ShapedPoolStructurePiece(_snowman29, mutableObject2, _snowman18, currentSize + 1));
                            continue block0;
                        }
                    }
                }
            }
        }
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
}

