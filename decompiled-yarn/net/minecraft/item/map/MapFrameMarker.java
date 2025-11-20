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
      BlockPos _snowman = NbtHelper.toBlockPos(tag.getCompound("Pos"));
      int _snowmanx = tag.getInt("Rotation");
      int _snowmanxx = tag.getInt("EntityId");
      return new MapFrameMarker(_snowman, _snowmanx, _snowmanxx);
   }

   public CompoundTag toTag() {
      CompoundTag _snowman = new CompoundTag();
      _snowman.put("Pos", NbtHelper.fromBlockPos(this.pos));
      _snowman.putInt("Rotation", this.rotation);
      _snowman.putInt("EntityId", this.entityId);
      return _snowman;
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
      return getKey(this.pos);
   }

   public static String getKey(BlockPos pos) {
      return "frame-" + pos.getX() + "," + pos.getY() + "," + pos.getZ();
   }
}
