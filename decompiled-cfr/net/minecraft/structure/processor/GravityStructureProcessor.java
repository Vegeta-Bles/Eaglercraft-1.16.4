/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.structure.processor;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldView;

public class GravityStructureProcessor
extends StructureProcessor {
    public static final Codec<GravityStructureProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Heightmap.Type.CODEC.fieldOf("heightmap").orElse((Object)Heightmap.Type.WORLD_SURFACE_WG).forGetter(gravityStructureProcessor -> gravityStructureProcessor.heightmap), (App)Codec.INT.fieldOf("offset").orElse((Object)0).forGetter(gravityStructureProcessor -> gravityStructureProcessor.offset)).apply((Applicative)instance, GravityStructureProcessor::new));
    private final Heightmap.Type heightmap;
    private final int offset;

    public GravityStructureProcessor(Heightmap.Type heightmap, int offset) {
        this.heightmap = heightmap;
        this.offset = offset;
    }

    @Override
    @Nullable
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo structureBlockInfo2, StructurePlacementData structurePlacementData) {
        Heightmap.Type type = worldView instanceof ServerWorld ? (this.heightmap == Heightmap.Type.WORLD_SURFACE_WG ? Heightmap.Type.WORLD_SURFACE : (this.heightmap == Heightmap.Type.OCEAN_FLOOR_WG ? Heightmap.Type.OCEAN_FLOOR : this.heightmap)) : this.heightmap;
        int _snowman2 = worldView.getTopY(type, structureBlockInfo2.pos.getX(), structureBlockInfo2.pos.getZ()) + this.offset;
        int _snowman3 = structureBlockInfo.pos.getY();
        return new Structure.StructureBlockInfo(new BlockPos(structureBlockInfo2.pos.getX(), _snowman2 + _snowman3, structureBlockInfo2.pos.getZ()), structureBlockInfo2.state, structureBlockInfo2.tag);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return StructureProcessorType.GRAVITY;
    }
}

