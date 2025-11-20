/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class JigsawBlockEntity
extends BlockEntity {
    private Identifier name = new Identifier("empty");
    private Identifier target = new Identifier("empty");
    private Identifier pool = new Identifier("empty");
    private Joint joint = Joint.ROLLABLE;
    private String finalState = "minecraft:air";

    public JigsawBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    public JigsawBlockEntity() {
        this(BlockEntityType.JIGSAW);
    }

    public Identifier getName() {
        return this.name;
    }

    public Identifier getTarget() {
        return this.target;
    }

    public Identifier getPool() {
        return this.pool;
    }

    public String getFinalState() {
        return this.finalState;
    }

    public Joint getJoint() {
        return this.joint;
    }

    public void setAttachmentType(Identifier value) {
        this.name = value;
    }

    public void setTargetPool(Identifier target) {
        this.target = target;
    }

    public void setPool(Identifier pool) {
        this.pool = pool;
    }

    public void setFinalState(String finalState) {
        this.finalState = finalState;
    }

    public void setJoint(Joint joint) {
        this.joint = joint;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putString("name", this.name.toString());
        tag.putString("target", this.target.toString());
        tag.putString("pool", this.pool.toString());
        tag.putString("final_state", this.finalState);
        tag.putString("joint", this.joint.asString());
        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.name = new Identifier(tag.getString("name"));
        this.target = new Identifier(tag.getString("target"));
        this.pool = new Identifier(tag.getString("pool"));
        this.finalState = tag.getString("final_state");
        this.joint = Joint.byName(tag.getString("joint")).orElseGet(() -> JigsawBlock.getFacing(state).getAxis().isHorizontal() ? Joint.ALIGNED : Joint.ROLLABLE);
    }

    @Override
    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 12, this.toInitialChunkDataTag());
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        return this.toTag(new CompoundTag());
    }

    public void generate(ServerWorld world, int maxDepth, boolean keepJigsaws) {
        ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
        StructureManager _snowman2 = world.getStructureManager();
        StructureAccessor _snowman3 = world.getStructureAccessor();
        Random _snowman4 = world.getRandom();
        BlockPos _snowman5 = this.getPos();
        ArrayList _snowman6 = Lists.newArrayList();
        Structure _snowman7 = new Structure();
        _snowman7.saveFromWorld(world, _snowman5, new BlockPos(1, 1, 1), false, null);
        SinglePoolElement _snowman8 = new SinglePoolElement(_snowman7);
        PoolStructurePiece _snowman9 = new PoolStructurePiece(_snowman2, _snowman8, _snowman5, 1, BlockRotation.NONE, new BlockBox(_snowman5, _snowman5));
        StructurePoolBasedGenerator.method_27230(world.getRegistryManager(), _snowman9, maxDepth, PoolStructurePiece::new, chunkGenerator, _snowman2, _snowman6, _snowman4);
        for (PoolStructurePiece poolStructurePiece : _snowman6) {
            poolStructurePiece.method_27236(world, _snowman3, chunkGenerator, _snowman4, BlockBox.infinite(), _snowman5, keepJigsaws);
        }
    }

    public static enum Joint implements StringIdentifiable
    {
        ROLLABLE("rollable"),
        ALIGNED("aligned");

        private final String name;

        private Joint(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }

        public static Optional<Joint> byName(String name) {
            return Arrays.stream(Joint.values()).filter(joint -> joint.asString().equals(name)).findFirst();
        }
    }
}

