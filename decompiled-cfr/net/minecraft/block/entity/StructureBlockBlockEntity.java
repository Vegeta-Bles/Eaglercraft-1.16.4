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
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StructureBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockRotStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class StructureBlockBlockEntity
extends BlockEntity {
    private Identifier structureName;
    private String author = "";
    private String metadata = "";
    private BlockPos offset = new BlockPos(0, 1, 0);
    private BlockPos size = BlockPos.ORIGIN;
    private BlockMirror mirror = BlockMirror.NONE;
    private BlockRotation rotation = BlockRotation.NONE;
    private StructureBlockMode mode = StructureBlockMode.DATA;
    private boolean ignoreEntities = true;
    private boolean powered;
    private boolean showAir;
    private boolean showBoundingBox = true;
    private float integrity = 1.0f;
    private long seed;

    public StructureBlockBlockEntity() {
        super(BlockEntityType.STRUCTURE_BLOCK);
    }

    @Override
    public double getSquaredRenderDistance() {
        return 96.0;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putString("name", this.getStructureName());
        tag.putString("author", this.author);
        tag.putString("metadata", this.metadata);
        tag.putInt("posX", this.offset.getX());
        tag.putInt("posY", this.offset.getY());
        tag.putInt("posZ", this.offset.getZ());
        tag.putInt("sizeX", this.size.getX());
        tag.putInt("sizeY", this.size.getY());
        tag.putInt("sizeZ", this.size.getZ());
        tag.putString("rotation", this.rotation.toString());
        tag.putString("mirror", this.mirror.toString());
        tag.putString("mode", this.mode.toString());
        tag.putBoolean("ignoreEntities", this.ignoreEntities);
        tag.putBoolean("powered", this.powered);
        tag.putBoolean("showair", this.showAir);
        tag.putBoolean("showboundingbox", this.showBoundingBox);
        tag.putFloat("integrity", this.integrity);
        tag.putLong("seed", this.seed);
        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.setStructureName(tag.getString("name"));
        this.author = tag.getString("author");
        this.metadata = tag.getString("metadata");
        int n = MathHelper.clamp(tag.getInt("posX"), -48, 48);
        _snowman = MathHelper.clamp(tag.getInt("posY"), -48, 48);
        _snowman = MathHelper.clamp(tag.getInt("posZ"), -48, 48);
        this.offset = new BlockPos(n, _snowman, _snowman);
        _snowman = MathHelper.clamp(tag.getInt("sizeX"), 0, 48);
        _snowman = MathHelper.clamp(tag.getInt("sizeY"), 0, 48);
        _snowman = MathHelper.clamp(tag.getInt("sizeZ"), 0, 48);
        this.size = new BlockPos(_snowman, _snowman, _snowman);
        try {
            this.rotation = BlockRotation.valueOf(tag.getString("rotation"));
        }
        catch (IllegalArgumentException _snowman2) {
            this.rotation = BlockRotation.NONE;
        }
        try {
            this.mirror = BlockMirror.valueOf(tag.getString("mirror"));
        }
        catch (IllegalArgumentException _snowman3) {
            this.mirror = BlockMirror.NONE;
        }
        try {
            this.mode = StructureBlockMode.valueOf(tag.getString("mode"));
        }
        catch (IllegalArgumentException _snowman4) {
            this.mode = StructureBlockMode.DATA;
        }
        this.ignoreEntities = tag.getBoolean("ignoreEntities");
        this.powered = tag.getBoolean("powered");
        this.showAir = tag.getBoolean("showair");
        this.showBoundingBox = tag.getBoolean("showboundingbox");
        this.integrity = tag.contains("integrity") ? tag.getFloat("integrity") : 1.0f;
        this.seed = tag.getLong("seed");
        this.updateBlockMode();
    }

    private void updateBlockMode() {
        if (this.world == null) {
            return;
        }
        BlockPos blockPos = this.getPos();
        BlockState _snowman2 = this.world.getBlockState(blockPos);
        if (_snowman2.isOf(Blocks.STRUCTURE_BLOCK)) {
            this.world.setBlockState(blockPos, (BlockState)_snowman2.with(StructureBlock.MODE, this.mode), 2);
        }
    }

    @Override
    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 7, this.toInitialChunkDataTag());
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        return this.toTag(new CompoundTag());
    }

    public boolean openScreen(PlayerEntity player) {
        if (!player.isCreativeLevelTwoOp()) {
            return false;
        }
        if (player.getEntityWorld().isClient) {
            player.openStructureBlockScreen(this);
        }
        return true;
    }

    public String getStructureName() {
        return this.structureName == null ? "" : this.structureName.toString();
    }

    public String getStructurePath() {
        return this.structureName == null ? "" : this.structureName.getPath();
    }

    public boolean hasStructureName() {
        return this.structureName != null;
    }

    public void setStructureName(@Nullable String name) {
        this.setStructureName(ChatUtil.isEmpty(name) ? null : Identifier.tryParse(name));
    }

    public void setStructureName(@Nullable Identifier identifier) {
        this.structureName = identifier;
    }

    public void setAuthor(LivingEntity entity) {
        this.author = entity.getName().getString();
    }

    public BlockPos getOffset() {
        return this.offset;
    }

    public void setOffset(BlockPos pos) {
        this.offset = pos;
    }

    public BlockPos getSize() {
        return this.size;
    }

    public void setSize(BlockPos pos) {
        this.size = pos;
    }

    public BlockMirror getMirror() {
        return this.mirror;
    }

    public void setMirror(BlockMirror mirror) {
        this.mirror = mirror;
    }

    public BlockRotation getRotation() {
        return this.rotation;
    }

    public void setRotation(BlockRotation rotation) {
        this.rotation = rotation;
    }

    public String getMetadata() {
        return this.metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public StructureBlockMode getMode() {
        return this.mode;
    }

    public void setMode(StructureBlockMode mode) {
        this.mode = mode;
        BlockState blockState = this.world.getBlockState(this.getPos());
        if (blockState.isOf(Blocks.STRUCTURE_BLOCK)) {
            this.world.setBlockState(this.getPos(), (BlockState)blockState.with(StructureBlock.MODE, mode), 2);
        }
    }

    public void cycleMode() {
        switch (this.getMode()) {
            case SAVE: {
                this.setMode(StructureBlockMode.LOAD);
                break;
            }
            case LOAD: {
                this.setMode(StructureBlockMode.CORNER);
                break;
            }
            case CORNER: {
                this.setMode(StructureBlockMode.DATA);
                break;
            }
            case DATA: {
                this.setMode(StructureBlockMode.SAVE);
            }
        }
    }

    public boolean shouldIgnoreEntities() {
        return this.ignoreEntities;
    }

    public void setIgnoreEntities(boolean ignoreEntities) {
        this.ignoreEntities = ignoreEntities;
    }

    public float getIntegrity() {
        return this.integrity;
    }

    public void setIntegrity(float integrity) {
        this.integrity = integrity;
    }

    public long getSeed() {
        return this.seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public boolean detectStructureSize() {
        if (this.mode != StructureBlockMode.SAVE) {
            return false;
        }
        BlockPos blockPos = this.getPos();
        int _snowman2 = 80;
        _snowman = new BlockPos(blockPos.getX() - 80, 0, blockPos.getZ() - 80);
        List<StructureBlockBlockEntity> _snowman3 = this.findStructureBlockEntities(_snowman, _snowman = new BlockPos(blockPos.getX() + 80, 255, blockPos.getZ() + 80));
        List<StructureBlockBlockEntity> _snowman4 = this.findCorners(_snowman3);
        if (_snowman4.size() < 1) {
            return false;
        }
        BlockBox _snowman5 = this.makeBoundingBox(blockPos, _snowman4);
        if (_snowman5.maxX - _snowman5.minX > 1 && _snowman5.maxY - _snowman5.minY > 1 && _snowman5.maxZ - _snowman5.minZ > 1) {
            this.offset = new BlockPos(_snowman5.minX - blockPos.getX() + 1, _snowman5.minY - blockPos.getY() + 1, _snowman5.minZ - blockPos.getZ() + 1);
            this.size = new BlockPos(_snowman5.maxX - _snowman5.minX - 1, _snowman5.maxY - _snowman5.minY - 1, _snowman5.maxZ - _snowman5.minZ - 1);
            this.markDirty();
            BlockState blockState = this.world.getBlockState(blockPos);
            this.world.updateListeners(blockPos, blockState, blockState, 3);
            return true;
        }
        return false;
    }

    private List<StructureBlockBlockEntity> findCorners(List<StructureBlockBlockEntity> structureBlockEntities) {
        Predicate<StructureBlockBlockEntity> predicate = structureBlockBlockEntity -> structureBlockBlockEntity.mode == StructureBlockMode.CORNER && Objects.equals(this.structureName, structureBlockBlockEntity.structureName);
        return structureBlockEntities.stream().filter(predicate).collect(Collectors.toList());
    }

    private List<StructureBlockBlockEntity> findStructureBlockEntities(BlockPos pos1, BlockPos pos2) {
        ArrayList arrayList = Lists.newArrayList();
        for (BlockPos blockPos : BlockPos.iterate(pos1, pos2)) {
            BlockState blockState = this.world.getBlockState(blockPos);
            if (!blockState.isOf(Blocks.STRUCTURE_BLOCK) || (_snowman = this.world.getBlockEntity(blockPos)) == null || !(_snowman instanceof StructureBlockBlockEntity)) continue;
            arrayList.add((StructureBlockBlockEntity)_snowman);
        }
        return arrayList;
    }

    private BlockBox makeBoundingBox(BlockPos center, List<StructureBlockBlockEntity> corners) {
        BlockBox _snowman2;
        if (corners.size() > 1) {
            BlockPos blockPos = corners.get(0).getPos();
            _snowman2 = new BlockBox(blockPos, blockPos);
        } else {
            _snowman2 = new BlockBox(center, center);
        }
        for (StructureBlockBlockEntity _snowman3 : corners) {
            _snowman = _snowman3.getPos();
            if (_snowman.getX() < _snowman2.minX) {
                _snowman2.minX = _snowman.getX();
            } else if (_snowman.getX() > _snowman2.maxX) {
                _snowman2.maxX = _snowman.getX();
            }
            if (_snowman.getY() < _snowman2.minY) {
                _snowman2.minY = _snowman.getY();
            } else if (_snowman.getY() > _snowman2.maxY) {
                _snowman2.maxY = _snowman.getY();
            }
            if (_snowman.getZ() < _snowman2.minZ) {
                _snowman2.minZ = _snowman.getZ();
                continue;
            }
            if (_snowman.getZ() <= _snowman2.maxZ) continue;
            _snowman2.maxZ = _snowman.getZ();
        }
        return _snowman2;
    }

    public boolean saveStructure() {
        return this.saveStructure(true);
    }

    public boolean saveStructure(boolean bl) {
        if (this.mode != StructureBlockMode.SAVE || this.world.isClient || this.structureName == null) {
            return false;
        }
        BlockPos blockPos = this.getPos().add(this.offset);
        ServerWorld _snowman2 = (ServerWorld)this.world;
        StructureManager _snowman3 = _snowman2.getStructureManager();
        try {
            Structure structure = _snowman3.getStructureOrBlank(this.structureName);
        }
        catch (InvalidIdentifierException invalidIdentifierException) {
            return false;
        }
        structure.saveFromWorld(this.world, blockPos, this.size, !this.ignoreEntities, Blocks.STRUCTURE_VOID);
        structure.setAuthor(this.author);
        if (bl) {
            try {
                return _snowman3.saveStructure(this.structureName);
            }
            catch (InvalidIdentifierException invalidIdentifierException) {
                return false;
            }
        }
        return true;
    }

    public boolean loadStructure(ServerWorld serverWorld) {
        return this.loadStructure(serverWorld, true);
    }

    private static Random createRandom(long seed) {
        if (seed == 0L) {
            return new Random(Util.getMeasuringTimeMs());
        }
        return new Random(seed);
    }

    public boolean loadStructure(ServerWorld serverWorld, boolean bl) {
        if (this.mode != StructureBlockMode.LOAD || this.structureName == null) {
            return false;
        }
        StructureManager structureManager = serverWorld.getStructureManager();
        try {
            Structure structure = structureManager.getStructure(this.structureName);
        }
        catch (InvalidIdentifierException invalidIdentifierException) {
            return false;
        }
        if (structure == null) {
            return false;
        }
        return this.place(serverWorld, bl, structure);
    }

    public boolean place(ServerWorld serverWorld, boolean bl2, Structure structure) {
        boolean bl2;
        Object object;
        BlockPos blockPos = this.getPos();
        if (!ChatUtil.isEmpty(structure.getAuthor())) {
            this.author = structure.getAuthor();
        }
        if (!(_snowman = this.size.equals(_snowman = structure.getSize()))) {
            this.size = _snowman;
            this.markDirty();
            object = serverWorld.getBlockState(blockPos);
            serverWorld.updateListeners(blockPos, (BlockState)object, (BlockState)object, 3);
        }
        if (!bl2 || _snowman) {
            object = new StructurePlacementData().setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities).setChunkPosition(null);
            if (this.integrity < 1.0f) {
                ((StructurePlacementData)object).clearProcessors().addProcessor(new BlockRotStructureProcessor(MathHelper.clamp(this.integrity, 0.0f, 1.0f))).setRandom(StructureBlockBlockEntity.createRandom(this.seed));
            }
            BlockPos _snowman2 = blockPos.add(this.offset);
            structure.place(serverWorld, _snowman2, (StructurePlacementData)object, StructureBlockBlockEntity.createRandom(this.seed));
            return true;
        }
        return false;
    }

    public void unloadStructure() {
        if (this.structureName == null) {
            return;
        }
        ServerWorld serverWorld = (ServerWorld)this.world;
        StructureManager _snowman2 = serverWorld.getStructureManager();
        _snowman2.unloadStructure(this.structureName);
    }

    public boolean isStructureAvailable() {
        if (this.mode != StructureBlockMode.LOAD || this.world.isClient || this.structureName == null) {
            return false;
        }
        ServerWorld serverWorld = (ServerWorld)this.world;
        StructureManager _snowman2 = serverWorld.getStructureManager();
        try {
            return _snowman2.getStructure(this.structureName) != null;
        }
        catch (InvalidIdentifierException _snowman3) {
            return false;
        }
    }

    public boolean isPowered() {
        return this.powered;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    public boolean shouldShowAir() {
        return this.showAir;
    }

    public void setShowAir(boolean showAir) {
        this.showAir = showAir;
    }

    public boolean shouldShowBoundingBox() {
        return this.showBoundingBox;
    }

    public void setShowBoundingBox(boolean showBoundingBox) {
        this.showBoundingBox = showBoundingBox;
    }

    public static enum Action {
        UPDATE_DATA,
        SAVE_AREA,
        LOAD_AREA,
        SCAN_AREA;

    }
}

