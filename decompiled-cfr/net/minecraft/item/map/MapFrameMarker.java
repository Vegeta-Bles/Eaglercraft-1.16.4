/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item.map;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;

public class MapFrameMarker {
    private final BlockPos pos;
    private final int rotation;
    private final int entityId;

    public MapFrameMarker(BlockPos pos, int rotation, int entityId) {
        this.pos = pos;
        this.rotation = rotation;
        this.entityId = entityId;
    }

    public static MapFrameMarker fromTag(CompoundTag tag) {
        BlockPos blockPos = NbtHelper.toBlockPos(tag.getCompound("Pos"));
        int _snowman2 = tag.getInt("Rotation");
        int _snowman3 = tag.getInt("EntityId");
        return new MapFrameMarker(blockPos, _snowman2, _snowman3);
    }

    public CompoundTag toTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("Pos", NbtHelper.fromBlockPos(this.pos));
        compoundTag.putInt("Rotation", this.rotation);
        compoundTag.putInt("EntityId", this.entityId);
        return compoundTag;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public int getRotation() {
        return this.rotation;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public String getKey() {
        return MapFrameMarker.getKey(this.pos);
    }

    public static String getKey(BlockPos pos) {
        return "frame-" + pos.getX() + "," + pos.getY() + "," + pos.getZ();
    }
}

