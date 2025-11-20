/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.structure;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlackstoneReplacementStructureProcessor;
import net.minecraft.structure.processor.BlockAgeStructureProcessor;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.LavaSubmergedBlockStructureProcessor;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RuinedPortalStructurePiece
extends SimpleStructurePiece {
    private static final Logger field_24992 = LogManager.getLogger();
    private final Identifier template;
    private final BlockRotation rotation;
    private final BlockMirror mirror;
    private final VerticalPlacement verticalPlacement;
    private final Properties properties;

    public RuinedPortalStructurePiece(BlockPos pos, VerticalPlacement verticalPlacement, Properties properties, Identifier template, Structure structure, BlockRotation rotation, BlockMirror mirror, BlockPos center) {
        super(StructurePieceType.RUINED_PORTAL, 0);
        this.pos = pos;
        this.template = template;
        this.rotation = rotation;
        this.mirror = mirror;
        this.verticalPlacement = verticalPlacement;
        this.properties = properties;
        this.processProperties(structure, center);
    }

    public RuinedPortalStructurePiece(StructureManager manager, CompoundTag tag) {
        super(StructurePieceType.RUINED_PORTAL, tag);
        this.template = new Identifier(tag.getString("Template"));
        this.rotation = BlockRotation.valueOf(tag.getString("Rotation"));
        this.mirror = BlockMirror.valueOf(tag.getString("Mirror"));
        this.verticalPlacement = VerticalPlacement.getFromId(tag.getString("VerticalPlacement"));
        this.properties = (Properties)Properties.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, (Object)tag.get("Properties"))).getOrThrow(true, arg_0 -> ((Logger)field_24992).error(arg_0));
        Structure structure = manager.getStructureOrBlank(this.template);
        this.processProperties(structure, new BlockPos(structure.getSize().getX() / 2, 0, structure.getSize().getZ() / 2));
    }

    @Override
    protected void toNbt(CompoundTag tag2) {
        super.toNbt(tag2);
        tag2.putString("Template", this.template.toString());
        tag2.putString("Rotation", this.rotation.name());
        tag2.putString("Mirror", this.mirror.name());
        tag2.putString("VerticalPlacement", this.verticalPlacement.getId());
        Properties.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, (Object)this.properties).resultOrPartial(arg_0 -> ((Logger)field_24992).error(arg_0)).ifPresent(tag -> tag2.put("Properties", (Tag)tag));
    }

    private void processProperties(Structure structure, BlockPos center) {
        BlockIgnoreStructureProcessor blockIgnoreStructureProcessor = this.properties.airPocket ? BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS : BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS;
        ArrayList _snowman2 = Lists.newArrayList();
        _snowman2.add(RuinedPortalStructurePiece.createReplacementRule(Blocks.GOLD_BLOCK, 0.3f, Blocks.AIR));
        _snowman2.add(this.createLavaReplacementRule());
        if (!this.properties.cold) {
            _snowman2.add(RuinedPortalStructurePiece.createReplacementRule(Blocks.NETHERRACK, 0.07f, Blocks.MAGMA_BLOCK));
        }
        StructurePlacementData _snowman3 = new StructurePlacementData().setRotation(this.rotation).setMirror(this.mirror).setPosition(center).addProcessor(blockIgnoreStructureProcessor).addProcessor(new RuleStructureProcessor(_snowman2)).addProcessor(new BlockAgeStructureProcessor(this.properties.mossiness)).addProcessor(new LavaSubmergedBlockStructureProcessor());
        if (this.properties.replaceWithBlackstone) {
            _snowman3.addProcessor(BlackstoneReplacementStructureProcessor.INSTANCE);
        }
        this.setStructureData(structure, this.pos, _snowman3);
    }

    private StructureProcessorRule createLavaReplacementRule() {
        if (this.verticalPlacement == VerticalPlacement.ON_OCEAN_FLOOR) {
            return RuinedPortalStructurePiece.createReplacementRule(Blocks.LAVA, Blocks.MAGMA_BLOCK);
        }
        if (this.properties.cold) {
            return RuinedPortalStructurePiece.createReplacementRule(Blocks.LAVA, Blocks.NETHERRACK);
        }
        return RuinedPortalStructurePiece.createReplacementRule(Blocks.LAVA, 0.2f, Blocks.MAGMA_BLOCK);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos2) {
        if (!boundingBox.contains(this.pos)) {
            return true;
        }
        boundingBox.encompass(this.structure.calculateBoundingBox(this.placementData, this.pos));
        boolean bl = super.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos2);
        this.placeNetherrackBase(random, structureWorldAccess);
        this.updateNetherracksInBound(random, structureWorldAccess);
        if (this.properties.vines || this.properties.overgrown) {
            BlockPos.stream(this.getBoundingBox()).forEach(blockPos -> {
                if (this.properties.vines) {
                    this.generateVines(random, structureWorldAccess, (BlockPos)blockPos);
                }
                if (this.properties.overgrown) {
                    this.generateOvergrownLeaves(random, structureWorldAccess, (BlockPos)blockPos);
                }
            });
        }
        return bl;
    }

    @Override
    protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {
    }

    private void generateVines(Random random, WorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isAir() || blockState.isOf(Blocks.VINE)) {
            return;
        }
        Direction _snowman2 = Direction.Type.HORIZONTAL.random(random);
        BlockPos _snowman3 = pos.offset(_snowman2);
        _snowman = world.getBlockState(_snowman3);
        if (!_snowman.isAir()) {
            return;
        }
        if (!Block.isFaceFullSquare(blockState.getCollisionShape(world, pos), _snowman2)) {
            return;
        }
        BooleanProperty _snowman4 = VineBlock.getFacingProperty(_snowman2.getOpposite());
        world.setBlockState(_snowman3, (BlockState)Blocks.VINE.getDefaultState().with(_snowman4, true), 3);
    }

    private void generateOvergrownLeaves(Random random, WorldAccess world, BlockPos pos) {
        if (random.nextFloat() < 0.5f && world.getBlockState(pos).isOf(Blocks.NETHERRACK) && world.getBlockState(pos.up()).isAir()) {
            world.setBlockState(pos.up(), (BlockState)Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 3);
        }
    }

    private void updateNetherracksInBound(Random random, WorldAccess world) {
        for (int i = this.boundingBox.minX + 1; i < this.boundingBox.maxX; ++i) {
            for (_snowman = this.boundingBox.minZ + 1; _snowman < this.boundingBox.maxZ; ++_snowman) {
                BlockPos blockPos = new BlockPos(i, this.boundingBox.minY, _snowman);
                if (!world.getBlockState(blockPos).isOf(Blocks.NETHERRACK)) continue;
                this.updateNetherracks(random, world, blockPos.down());
            }
        }
    }

    private void updateNetherracks(Random random, WorldAccess world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        this.placeNetherrackBottom(random, world, mutable);
        for (int i = 8; i > 0 && random.nextFloat() < 0.5f; --i) {
            mutable.move(Direction.DOWN);
            this.placeNetherrackBottom(random, world, mutable);
        }
    }

    private void placeNetherrackBase(Random random, WorldAccess world) {
        boolean bl = this.verticalPlacement == VerticalPlacement.ON_LAND_SURFACE || this.verticalPlacement == VerticalPlacement.ON_OCEAN_FLOOR;
        Vec3i _snowman2 = this.boundingBox.getCenter();
        int _snowman3 = _snowman2.getX();
        int _snowman4 = _snowman2.getZ();
        float[] _snowman5 = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.9f, 0.9f, 0.8f, 0.7f, 0.6f, 0.4f, 0.2f};
        int _snowman6 = _snowman5.length;
        int _snowman7 = (this.boundingBox.getBlockCountX() + this.boundingBox.getBlockCountZ()) / 2;
        int _snowman8 = random.nextInt(Math.max(1, 8 - _snowman7 / 2));
        int _snowman9 = 3;
        BlockPos.Mutable _snowman10 = BlockPos.ORIGIN.mutableCopy();
        for (int i = _snowman3 - _snowman6; i <= _snowman3 + _snowman6; ++i) {
            for (_snowman = _snowman4 - _snowman6; _snowman <= _snowman4 + _snowman6; ++_snowman) {
                _snowman = Math.abs(i - _snowman3) + Math.abs(_snowman - _snowman4);
                _snowman = Math.max(0, _snowman + _snowman8);
                if (_snowman >= _snowman6) continue;
                float f = _snowman5[_snowman];
                if (!(random.nextDouble() < (double)f)) continue;
                int _snowman11 = RuinedPortalStructurePiece.getBaseHeight(world, i, _snowman, this.verticalPlacement);
                int _snowman12 = bl ? _snowman11 : Math.min(this.boundingBox.minY, _snowman11);
                _snowman10.set(i, _snowman12, _snowman);
                if (Math.abs(_snowman12 - this.boundingBox.minY) > 3 || !this.canFillNetherrack(world, _snowman10)) continue;
                this.placeNetherrackBottom(random, world, _snowman10);
                if (this.properties.overgrown) {
                    this.generateOvergrownLeaves(random, world, _snowman10);
                }
                this.updateNetherracks(random, world, (BlockPos)_snowman10.down());
            }
        }
    }

    private boolean canFillNetherrack(WorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return !blockState.isOf(Blocks.AIR) && !blockState.isOf(Blocks.OBSIDIAN) && !blockState.isOf(Blocks.CHEST) && (this.verticalPlacement == VerticalPlacement.IN_NETHER || !blockState.isOf(Blocks.LAVA));
    }

    private void placeNetherrackBottom(Random random, WorldAccess world, BlockPos pos) {
        if (!this.properties.cold && random.nextFloat() < 0.07f) {
            world.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState(), 3);
        } else {
            world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState(), 3);
        }
    }

    private static int getBaseHeight(WorldAccess world, int x, int y, VerticalPlacement verticalPlacement) {
        return world.getTopY(RuinedPortalStructurePiece.getHeightmapType(verticalPlacement), x, y) - 1;
    }

    public static Heightmap.Type getHeightmapType(VerticalPlacement verticalPlacement) {
        return verticalPlacement == VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Type.OCEAN_FLOOR_WG : Heightmap.Type.WORLD_SURFACE_WG;
    }

    private static StructureProcessorRule createReplacementRule(Block old, float chance, Block updated) {
        return new StructureProcessorRule(new RandomBlockMatchRuleTest(old, chance), AlwaysTrueRuleTest.INSTANCE, updated.getDefaultState());
    }

    private static StructureProcessorRule createReplacementRule(Block old, Block updated) {
        return new StructureProcessorRule(new BlockMatchRuleTest(old), AlwaysTrueRuleTest.INSTANCE, updated.getDefaultState());
    }

    public static enum VerticalPlacement {
        ON_LAND_SURFACE("on_land_surface"),
        PARTLY_BURIED("partly_buried"),
        ON_OCEAN_FLOOR("on_ocean_floor"),
        IN_MOUNTAIN("in_mountain"),
        UNDERGROUND("underground"),
        IN_NETHER("in_nether");

        private static final Map<String, VerticalPlacement> VERTICAL_PLACEMENTS;
        private final String id;

        private VerticalPlacement(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public static VerticalPlacement getFromId(String id) {
            return VERTICAL_PLACEMENTS.get(id);
        }

        static {
            VERTICAL_PLACEMENTS = Arrays.stream(VerticalPlacement.values()).collect(Collectors.toMap(VerticalPlacement::getId, verticalPlacement -> verticalPlacement));
        }
    }

    public static class Properties {
        public static final Codec<Properties> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.BOOL.fieldOf("cold").forGetter(properties -> properties.cold), (App)Codec.FLOAT.fieldOf("mossiness").forGetter(properties -> Float.valueOf(properties.mossiness)), (App)Codec.BOOL.fieldOf("air_pocket").forGetter(properties -> properties.airPocket), (App)Codec.BOOL.fieldOf("overgrown").forGetter(properties -> properties.overgrown), (App)Codec.BOOL.fieldOf("vines").forGetter(properties -> properties.vines), (App)Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(properties -> properties.replaceWithBlackstone)).apply((Applicative)instance, Properties::new));
        public boolean cold;
        public float mossiness = 0.2f;
        public boolean airPocket;
        public boolean overgrown;
        public boolean vines;
        public boolean replaceWithBlackstone;

        public Properties() {
        }

        public <T> Properties(boolean cold, float mossiness, boolean airPocket, boolean overgrown, boolean vines, boolean replaceWithBlackstone) {
            this.cold = cold;
            this.mossiness = mossiness;
            this.airPocket = airPocket;
            this.overgrown = overgrown;
            this.vines = vines;
            this.replaceWithBlackstone = replaceWithBlackstone;
        }
    }
}

