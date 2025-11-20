/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.block.BlockState;
import net.minecraft.structure.RuinedPortalStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.RuinedPortalFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class RuinedPortalFeature
extends StructureFeature<RuinedPortalFeatureConfig> {
    private static final String[] COMMON_PORTAL_STRUCTURE_IDS = new String[]{"ruined_portal/portal_1", "ruined_portal/portal_2", "ruined_portal/portal_3", "ruined_portal/portal_4", "ruined_portal/portal_5", "ruined_portal/portal_6", "ruined_portal/portal_7", "ruined_portal/portal_8", "ruined_portal/portal_9", "ruined_portal/portal_10"};
    private static final String[] RARE_PORTAL_STRUCTURE_IDS = new String[]{"ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"};

    public RuinedPortalFeature(Codec<RuinedPortalFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureFeature.StructureStartFactory<RuinedPortalFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    private static boolean method_27209(BlockPos blockPos, Biome biome) {
        return biome.getTemperature(blockPos) < 0.15f;
    }

    private static int method_27211(Random random, ChunkGenerator chunkGenerator, RuinedPortalStructurePiece.VerticalPlacement verticalPlacement, boolean bl, int n4, int n2, BlockBox blockBox2) {
        int n3;
        BlockBox blockBox2;
        int n4;
        if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.IN_NETHER) {
            int n5 = bl ? RuinedPortalFeature.choose(random, 32, 100) : (random.nextFloat() < 0.5f ? RuinedPortalFeature.choose(random, 27, 29) : RuinedPortalFeature.choose(random, 29, 100));
        } else if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.IN_MOUNTAIN) {
            _snowman = n4 - n2;
            n5 = RuinedPortalFeature.choosePlacementHeight(random, 70, _snowman);
        } else if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.UNDERGROUND) {
            _snowman = n4 - n2;
            n5 = RuinedPortalFeature.choosePlacementHeight(random, 15, _snowman);
        } else {
            n5 = verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.PARTLY_BURIED ? n4 - n2 + RuinedPortalFeature.choose(random, 2, 8) : n4;
        }
        ImmutableList _snowman2 = ImmutableList.of((Object)new BlockPos(blockBox2.minX, 0, blockBox2.minZ), (Object)new BlockPos(blockBox2.maxX, 0, blockBox2.minZ), (Object)new BlockPos(blockBox2.minX, 0, blockBox2.maxZ), (Object)new BlockPos(blockBox2.maxX, 0, blockBox2.maxZ));
        List _snowman3 = _snowman2.stream().map(blockPos -> chunkGenerator.getColumnSample(blockPos.getX(), blockPos.getZ())).collect(Collectors.toList());
        Heightmap.Type _snowman4 = verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Type.OCEAN_FLOOR_WG : Heightmap.Type.WORLD_SURFACE_WG;
        BlockPos.Mutable _snowman5 = new BlockPos.Mutable();
        block0: for (n3 = n5; n3 > 15; --n3) {
            _snowman = 0;
            _snowman5.set(0, n3, 0);
            for (BlockView blockView : _snowman3) {
                BlockState blockState = blockView.getBlockState(_snowman5);
                if (blockState == null || !_snowman4.getBlockPredicate().test(blockState) || ++_snowman != 3) continue;
                break block0;
            }
        }
        return n3;
    }

    private static int choose(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private static int choosePlacementHeight(Random random, int min, int max) {
        if (min < max) {
            return RuinedPortalFeature.choose(random, min, max);
        }
        return max;
    }

    public static enum Type implements StringIdentifiable
    {
        STANDARD("standard"),
        DESERT("desert"),
        JUNGLE("jungle"),
        SWAMP("swamp"),
        MOUNTAIN("mountain"),
        OCEAN("ocean"),
        NETHER("nether");

        public static final Codec<Type> CODEC;
        private static final Map<String, Type> BY_NAME;
        private final String name;

        private Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static Type byName(String name) {
            return BY_NAME.get(name);
        }

        @Override
        public String asString() {
            return this.name;
        }

        static {
            CODEC = StringIdentifiable.createCodec(Type::values, Type::byName);
            BY_NAME = Arrays.stream(Type.values()).collect(Collectors.toMap(Type::getName, type -> type));
        }
    }

    public static class Start
    extends StructureStart<RuinedPortalFeatureConfig> {
        protected Start(StructureFeature<RuinedPortalFeatureConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, RuinedPortalFeatureConfig ruinedPortalFeatureConfig2) {
            RuinedPortalFeatureConfig ruinedPortalFeatureConfig2;
            RuinedPortalStructurePiece.VerticalPlacement _snowman2;
            RuinedPortalStructurePiece.Properties properties = new RuinedPortalStructurePiece.Properties();
            if (ruinedPortalFeatureConfig2.portalType == Type.DESERT) {
                _snowman2 = RuinedPortalStructurePiece.VerticalPlacement.PARTLY_BURIED;
                properties.airPocket = false;
                properties.mossiness = 0.0f;
            } else if (ruinedPortalFeatureConfig2.portalType == Type.JUNGLE) {
                _snowman2 = RuinedPortalStructurePiece.VerticalPlacement.ON_LAND_SURFACE;
                properties.airPocket = this.random.nextFloat() < 0.5f;
                properties.mossiness = 0.8f;
                properties.overgrown = true;
                properties.vines = true;
            } else if (ruinedPortalFeatureConfig2.portalType == Type.SWAMP) {
                _snowman2 = RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR;
                properties.airPocket = false;
                properties.mossiness = 0.5f;
                properties.vines = true;
            } else if (ruinedPortalFeatureConfig2.portalType == Type.MOUNTAIN) {
                boolean bl = this.random.nextFloat() < 0.5f;
                _snowman2 = bl ? RuinedPortalStructurePiece.VerticalPlacement.IN_MOUNTAIN : RuinedPortalStructurePiece.VerticalPlacement.ON_LAND_SURFACE;
                properties.airPocket = bl || this.random.nextFloat() < 0.5f;
            } else if (ruinedPortalFeatureConfig2.portalType == Type.OCEAN) {
                _snowman2 = RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR;
                properties.airPocket = false;
                properties.mossiness = 0.8f;
            } else if (ruinedPortalFeatureConfig2.portalType == Type.NETHER) {
                _snowman2 = RuinedPortalStructurePiece.VerticalPlacement.IN_NETHER;
                properties.airPocket = this.random.nextFloat() < 0.5f;
                properties.mossiness = 0.0f;
                properties.replaceWithBlackstone = true;
            } else {
                bl = this.random.nextFloat() < 0.5f;
                _snowman2 = bl ? RuinedPortalStructurePiece.VerticalPlacement.UNDERGROUND : RuinedPortalStructurePiece.VerticalPlacement.ON_LAND_SURFACE;
                properties.airPocket = bl || this.random.nextFloat() < 0.5f;
            }
            Identifier identifier = this.random.nextFloat() < 0.05f ? new Identifier(RARE_PORTAL_STRUCTURE_IDS[this.random.nextInt(RARE_PORTAL_STRUCTURE_IDS.length)]) : new Identifier(COMMON_PORTAL_STRUCTURE_IDS[this.random.nextInt(COMMON_PORTAL_STRUCTURE_IDS.length)]);
            Structure _snowman3 = structureManager.getStructureOrBlank(identifier);
            BlockRotation _snowman4 = Util.getRandom(BlockRotation.values(), (Random)this.random);
            BlockMirror _snowman5 = this.random.nextFloat() < 0.5f ? BlockMirror.NONE : BlockMirror.FRONT_BACK;
            BlockPos _snowman6 = new BlockPos(_snowman3.getSize().getX() / 2, 0, _snowman3.getSize().getZ() / 2);
            BlockPos _snowman7 = new ChunkPos(n, n2).getStartPos();
            BlockBox _snowman8 = _snowman3.method_27267(_snowman7, _snowman4, _snowman6, _snowman5);
            Vec3i _snowman9 = _snowman8.getCenter();
            int _snowman10 = _snowman9.getX();
            int _snowman11 = _snowman9.getZ();
            int _snowman12 = chunkGenerator.getHeight(_snowman10, _snowman11, RuinedPortalStructurePiece.getHeightmapType(_snowman2)) - 1;
            int _snowman13 = RuinedPortalFeature.method_27211(this.random, chunkGenerator, _snowman2, properties.airPocket, _snowman12, _snowman8.getBlockCountY(), _snowman8);
            BlockPos _snowman14 = new BlockPos(_snowman7.getX(), _snowman13, _snowman7.getZ());
            if (ruinedPortalFeatureConfig2.portalType == Type.MOUNTAIN || ruinedPortalFeatureConfig2.portalType == Type.OCEAN || ruinedPortalFeatureConfig2.portalType == Type.STANDARD) {
                properties.cold = RuinedPortalFeature.method_27209(_snowman14, biome);
            }
            this.children.add(new RuinedPortalStructurePiece(_snowman14, _snowman2, properties, identifier, _snowman3, _snowman4, _snowman5, _snowman6));
            this.setBoundingBoxFromChildren();
        }
    }
}

