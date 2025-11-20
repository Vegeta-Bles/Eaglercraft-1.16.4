/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.BlockRotStructureProcessor;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.OceanRuinFeature;
import net.minecraft.world.gen.feature.OceanRuinFeatureConfig;

public class OceanRuinGenerator {
    private static final Identifier[] WARM_RUINS = new Identifier[]{new Identifier("underwater_ruin/warm_1"), new Identifier("underwater_ruin/warm_2"), new Identifier("underwater_ruin/warm_3"), new Identifier("underwater_ruin/warm_4"), new Identifier("underwater_ruin/warm_5"), new Identifier("underwater_ruin/warm_6"), new Identifier("underwater_ruin/warm_7"), new Identifier("underwater_ruin/warm_8")};
    private static final Identifier[] BRICK_RUINS = new Identifier[]{new Identifier("underwater_ruin/brick_1"), new Identifier("underwater_ruin/brick_2"), new Identifier("underwater_ruin/brick_3"), new Identifier("underwater_ruin/brick_4"), new Identifier("underwater_ruin/brick_5"), new Identifier("underwater_ruin/brick_6"), new Identifier("underwater_ruin/brick_7"), new Identifier("underwater_ruin/brick_8")};
    private static final Identifier[] CRACKED_RUINS = new Identifier[]{new Identifier("underwater_ruin/cracked_1"), new Identifier("underwater_ruin/cracked_2"), new Identifier("underwater_ruin/cracked_3"), new Identifier("underwater_ruin/cracked_4"), new Identifier("underwater_ruin/cracked_5"), new Identifier("underwater_ruin/cracked_6"), new Identifier("underwater_ruin/cracked_7"), new Identifier("underwater_ruin/cracked_8")};
    private static final Identifier[] MOSSY_RUINS = new Identifier[]{new Identifier("underwater_ruin/mossy_1"), new Identifier("underwater_ruin/mossy_2"), new Identifier("underwater_ruin/mossy_3"), new Identifier("underwater_ruin/mossy_4"), new Identifier("underwater_ruin/mossy_5"), new Identifier("underwater_ruin/mossy_6"), new Identifier("underwater_ruin/mossy_7"), new Identifier("underwater_ruin/mossy_8")};
    private static final Identifier[] BIG_BRICK_RUINS = new Identifier[]{new Identifier("underwater_ruin/big_brick_1"), new Identifier("underwater_ruin/big_brick_2"), new Identifier("underwater_ruin/big_brick_3"), new Identifier("underwater_ruin/big_brick_8")};
    private static final Identifier[] BIG_MOSSY_RUINS = new Identifier[]{new Identifier("underwater_ruin/big_mossy_1"), new Identifier("underwater_ruin/big_mossy_2"), new Identifier("underwater_ruin/big_mossy_3"), new Identifier("underwater_ruin/big_mossy_8")};
    private static final Identifier[] BIG_CRACKED_RUINS = new Identifier[]{new Identifier("underwater_ruin/big_cracked_1"), new Identifier("underwater_ruin/big_cracked_2"), new Identifier("underwater_ruin/big_cracked_3"), new Identifier("underwater_ruin/big_cracked_8")};
    private static final Identifier[] BIG_WARM_RUINS = new Identifier[]{new Identifier("underwater_ruin/big_warm_4"), new Identifier("underwater_ruin/big_warm_5"), new Identifier("underwater_ruin/big_warm_6"), new Identifier("underwater_ruin/big_warm_7")};

    private static Identifier getRandomWarmRuin(Random random) {
        return Util.getRandom(WARM_RUINS, random);
    }

    private static Identifier getRandomBigWarmRuin(Random random) {
        return Util.getRandom(BIG_WARM_RUINS, random);
    }

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random, OceanRuinFeatureConfig config) {
        boolean bl = random.nextFloat() <= config.largeProbability;
        float _snowman2 = bl ? 0.9f : 0.8f;
        OceanRuinGenerator.method_14822(manager, pos, rotation, pieces, random, config, bl, _snowman2);
        if (bl && random.nextFloat() <= config.clusterProbability) {
            OceanRuinGenerator.method_14825(manager, random, rotation, pos, config, pieces);
        }
    }

    private static void method_14825(StructureManager manager, Random random, BlockRotation rotation, BlockPos pos, OceanRuinFeatureConfig config, List<StructurePiece> pieces) {
        int n = pos.getX();
        _snowman = pos.getZ();
        BlockPos _snowman2 = Structure.transformAround(new BlockPos(15, 0, 15), BlockMirror.NONE, rotation, BlockPos.ORIGIN).add(n, 0, _snowman);
        BlockBox _snowman3 = BlockBox.create(n, 0, _snowman, _snowman2.getX(), 0, _snowman2.getZ());
        BlockPos _snowman4 = new BlockPos(Math.min(n, _snowman2.getX()), 0, Math.min(_snowman, _snowman2.getZ()));
        List<BlockPos> _snowman5 = OceanRuinGenerator.getRoomPositions(random, _snowman4.getX(), _snowman4.getZ());
        _snowman = MathHelper.nextInt(random, 4, 8);
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            if (_snowman5.isEmpty() || (_snowman = BlockBox.create(_snowman = (_snowman = _snowman5.remove(_snowman = random.nextInt(_snowman5.size()))).getX(), 0, _snowman = _snowman.getZ(), (_snowman = Structure.transformAround(new BlockPos(5, 0, 6), BlockMirror.NONE, _snowman = BlockRotation.random(random), BlockPos.ORIGIN).add(_snowman, 0, _snowman)).getX(), 0, _snowman.getZ())).intersects(_snowman3)) continue;
            OceanRuinGenerator.method_14822(manager, _snowman, _snowman, pieces, random, config, false, 0.8f);
        }
    }

    private static List<BlockPos> getRoomPositions(Random random, int x, int z) {
        ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new BlockPos(x - 16 + MathHelper.nextInt(random, 1, 8), 90, z + 16 + MathHelper.nextInt(random, 1, 7)));
        arrayList.add(new BlockPos(x - 16 + MathHelper.nextInt(random, 1, 8), 90, z + MathHelper.nextInt(random, 1, 7)));
        arrayList.add(new BlockPos(x - 16 + MathHelper.nextInt(random, 1, 8), 90, z - 16 + MathHelper.nextInt(random, 4, 8)));
        arrayList.add(new BlockPos(x + MathHelper.nextInt(random, 1, 7), 90, z + 16 + MathHelper.nextInt(random, 1, 7)));
        arrayList.add(new BlockPos(x + MathHelper.nextInt(random, 1, 7), 90, z - 16 + MathHelper.nextInt(random, 4, 6)));
        arrayList.add(new BlockPos(x + 16 + MathHelper.nextInt(random, 1, 7), 90, z + 16 + MathHelper.nextInt(random, 3, 8)));
        arrayList.add(new BlockPos(x + 16 + MathHelper.nextInt(random, 1, 7), 90, z + MathHelper.nextInt(random, 1, 7)));
        arrayList.add(new BlockPos(x + 16 + MathHelper.nextInt(random, 1, 7), 90, z - 16 + MathHelper.nextInt(random, 4, 8)));
        return arrayList;
    }

    private static void method_14822(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random, OceanRuinFeatureConfig config, boolean large, float integrity) {
        if (config.biomeType == OceanRuinFeature.BiomeType.WARM) {
            Identifier identifier = large ? OceanRuinGenerator.getRandomBigWarmRuin(random) : OceanRuinGenerator.getRandomWarmRuin(random);
            pieces.add(new Piece(manager, identifier, pos, rotation, integrity, config.biomeType, large));
        } else if (config.biomeType == OceanRuinFeature.BiomeType.COLD) {
            Identifier[] identifierArray = large ? BIG_BRICK_RUINS : BRICK_RUINS;
            _snowman = large ? BIG_CRACKED_RUINS : CRACKED_RUINS;
            _snowman = large ? BIG_MOSSY_RUINS : MOSSY_RUINS;
            int _snowman2 = random.nextInt(identifierArray.length);
            pieces.add(new Piece(manager, identifierArray[_snowman2], pos, rotation, integrity, config.biomeType, large));
            pieces.add(new Piece(manager, _snowman[_snowman2], pos, rotation, 0.7f, config.biomeType, large));
            pieces.add(new Piece(manager, _snowman[_snowman2], pos, rotation, 0.5f, config.biomeType, large));
        }
    }

    public static class Piece
    extends SimpleStructurePiece {
        private final OceanRuinFeature.BiomeType biomeType;
        private final float integrity;
        private final Identifier template;
        private final BlockRotation rotation;
        private final boolean large;

        public Piece(StructureManager structureManager, Identifier template, BlockPos pos, BlockRotation rotation, float integrity, OceanRuinFeature.BiomeType biomeType, boolean large) {
            super(StructurePieceType.OCEAN_TEMPLE, 0);
            this.template = template;
            this.pos = pos;
            this.rotation = rotation;
            this.integrity = integrity;
            this.biomeType = biomeType;
            this.large = large;
            this.initialize(structureManager);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(StructurePieceType.OCEAN_TEMPLE, tag);
            this.template = new Identifier(tag.getString("Template"));
            this.rotation = BlockRotation.valueOf(tag.getString("Rot"));
            this.integrity = tag.getFloat("Integrity");
            this.biomeType = OceanRuinFeature.BiomeType.valueOf(tag.getString("BiomeType"));
            this.large = tag.getBoolean("IsLarge");
            this.initialize(manager);
        }

        private void initialize(StructureManager structureManager) {
            Structure structure = structureManager.getStructureOrBlank(this.template);
            StructurePlacementData _snowman2 = new StructurePlacementData().setRotation(this.rotation).setMirror(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
            this.setStructureData(structure, this.pos, _snowman2);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", this.template.toString());
            tag.putString("Rot", this.rotation.name());
            tag.putFloat("Integrity", this.integrity);
            tag.putString("BiomeType", this.biomeType.toString());
            tag.putBoolean("IsLarge", this.large);
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess2, Random random, BlockBox boundingBox) {
            if ("chest".equals(metadata)) {
                serverWorldAccess2.setBlockState(pos, (BlockState)Blocks.CHEST.getDefaultState().with(ChestBlock.WATERLOGGED, serverWorldAccess2.getFluidState(pos).isIn(FluidTags.WATER)), 2);
                BlockEntity blockEntity = serverWorldAccess2.getBlockEntity(pos);
                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity)blockEntity).setLootTable(this.large ? LootTables.UNDERWATER_RUIN_BIG_CHEST : LootTables.UNDERWATER_RUIN_SMALL_CHEST, random.nextLong());
                }
            } else if ("drowned".equals(metadata)) {
                ServerWorldAccess serverWorldAccess2;
                DrownedEntity _snowman2 = EntityType.DROWNED.create(serverWorldAccess2.toServerWorld());
                _snowman2.setPersistent();
                _snowman2.refreshPositionAndAngles(pos, 0.0f, 0.0f);
                _snowman2.initialize(serverWorldAccess2, serverWorldAccess2.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null, null);
                serverWorldAccess2.spawnEntityAndPassengers(_snowman2);
                if (pos.getY() > serverWorldAccess2.getSeaLevel()) {
                    serverWorldAccess2.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                } else {
                    serverWorldAccess2.setBlockState(pos, Blocks.WATER.getDefaultState(), 2);
                }
            }
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.placementData.clearProcessors().addProcessor(new BlockRotStructureProcessor(this.integrity)).addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
            int n = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, this.pos.getX(), this.pos.getZ());
            this.pos = new BlockPos(this.pos.getX(), n, this.pos.getZ());
            BlockPos _snowman2 = Structure.transformAround(new BlockPos(this.structure.getSize().getX() - 1, 0, this.structure.getSize().getZ() - 1), BlockMirror.NONE, this.rotation, BlockPos.ORIGIN).add(this.pos);
            this.pos = new BlockPos(this.pos.getX(), this.method_14829(this.pos, structureWorldAccess, _snowman2), this.pos.getZ());
            return super.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);
        }

        private int method_14829(BlockPos blockPos, BlockView blockView, BlockPos blockPos2) {
            int n = blockPos.getY();
            _snowman = 512;
            _snowman = n - 1;
            _snowman = 0;
            for (BlockPos blockPos3 : BlockPos.iterate(blockPos, blockPos2)) {
                int n2 = blockPos3.getX();
                _snowman = blockPos3.getZ();
                _snowman = blockPos.getY() - 1;
                BlockPos.Mutable _snowman2 = new BlockPos.Mutable(n2, _snowman, _snowman);
                BlockState _snowman3 = blockView.getBlockState(_snowman2);
                FluidState _snowman4 = blockView.getFluidState(_snowman2);
                while ((_snowman3.isAir() || _snowman4.isIn(FluidTags.WATER) || _snowman3.getBlock().isIn(BlockTags.ICE)) && _snowman > 1) {
                    _snowman2.set(n2, --_snowman, _snowman);
                    _snowman3 = blockView.getBlockState(_snowman2);
                    _snowman4 = blockView.getFluidState(_snowman2);
                }
                _snowman = Math.min(_snowman, _snowman);
                if (_snowman >= _snowman - 2) continue;
                ++_snowman;
            }
            _snowman = Math.abs(blockPos.getX() - blockPos2.getX());
            if (_snowman - _snowman > 2 && _snowman > _snowman - 2) {
                n = _snowman + 1;
            }
            return n;
        }
    }
}

