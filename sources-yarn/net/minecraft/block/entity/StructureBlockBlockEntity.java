package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StructureBlock;
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

public class StructureBlockBlockEntity extends BlockEntity {
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
   private float integrity = 1.0F;
   private long seed;

   public StructureBlockBlockEntity() {
      super(BlockEntityType.STRUCTURE_BLOCK);
   }

   @Environment(EnvType.CLIENT)
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
      int i = MathHelper.clamp(tag.getInt("posX"), -48, 48);
      int j = MathHelper.clamp(tag.getInt("posY"), -48, 48);
      int k = MathHelper.clamp(tag.getInt("posZ"), -48, 48);
      this.offset = new BlockPos(i, j, k);
      int l = MathHelper.clamp(tag.getInt("sizeX"), 0, 48);
      int m = MathHelper.clamp(tag.getInt("sizeY"), 0, 48);
      int n = MathHelper.clamp(tag.getInt("sizeZ"), 0, 48);
      this.size = new BlockPos(l, m, n);

      try {
         this.rotation = BlockRotation.valueOf(tag.getString("rotation"));
      } catch (IllegalArgumentException var12) {
         this.rotation = BlockRotation.NONE;
      }

      try {
         this.mirror = BlockMirror.valueOf(tag.getString("mirror"));
      } catch (IllegalArgumentException var11) {
         this.mirror = BlockMirror.NONE;
      }

      try {
         this.mode = StructureBlockMode.valueOf(tag.getString("mode"));
      } catch (IllegalArgumentException var10) {
         this.mode = StructureBlockMode.DATA;
      }

      this.ignoreEntities = tag.getBoolean("ignoreEntities");
      this.powered = tag.getBoolean("powered");
      this.showAir = tag.getBoolean("showair");
      this.showBoundingBox = tag.getBoolean("showboundingbox");
      if (tag.contains("integrity")) {
         this.integrity = tag.getFloat("integrity");
      } else {
         this.integrity = 1.0F;
      }

      this.seed = tag.getLong("seed");
      this.updateBlockMode();
   }

   private void updateBlockMode() {
      if (this.world != null) {
         BlockPos lv = this.getPos();
         BlockState lv2 = this.world.getBlockState(lv);
         if (lv2.isOf(Blocks.STRUCTURE_BLOCK)) {
            this.world.setBlockState(lv, lv2.with(StructureBlock.MODE, this.mode), 2);
         }
      }
   }

   @Nullable
   @Override
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
      } else {
         if (player.getEntityWorld().isClient) {
            player.openStructureBlockScreen(this);
         }

         return true;
      }
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

   public void setStructureName(@Nullable Identifier arg) {
      this.structureName = arg;
   }

   public void setAuthor(LivingEntity entity) {
      this.author = entity.getName().getString();
   }

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
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
      BlockState lv = this.world.getBlockState(this.getPos());
      if (lv.isOf(Blocks.STRUCTURE_BLOCK)) {
         this.world.setBlockState(this.getPos(), lv.with(StructureBlock.MODE, mode), 2);
      }
   }

   @Environment(EnvType.CLIENT)
   public void cycleMode() {
      switch (this.getMode()) {
         case SAVE:
            this.setMode(StructureBlockMode.LOAD);
            break;
         case LOAD:
            this.setMode(StructureBlockMode.CORNER);
            break;
         case CORNER:
            this.setMode(StructureBlockMode.DATA);
            break;
         case DATA:
            this.setMode(StructureBlockMode.SAVE);
      }
   }

   @Environment(EnvType.CLIENT)
   public boolean shouldIgnoreEntities() {
      return this.ignoreEntities;
   }

   public void setIgnoreEntities(boolean ignoreEntities) {
      this.ignoreEntities = ignoreEntities;
   }

   @Environment(EnvType.CLIENT)
   public float getIntegrity() {
      return this.integrity;
   }

   public void setIntegrity(float integrity) {
      this.integrity = integrity;
   }

   @Environment(EnvType.CLIENT)
   public long getSeed() {
      return this.seed;
   }

   public void setSeed(long seed) {
      this.seed = seed;
   }

   public boolean detectStructureSize() {
      if (this.mode != StructureBlockMode.SAVE) {
         return false;
      } else {
         BlockPos lv = this.getPos();
         int i = 80;
         BlockPos lv2 = new BlockPos(lv.getX() - 80, 0, lv.getZ() - 80);
         BlockPos lv3 = new BlockPos(lv.getX() + 80, 255, lv.getZ() + 80);
         List<StructureBlockBlockEntity> list = this.findStructureBlockEntities(lv2, lv3);
         List<StructureBlockBlockEntity> list2 = this.findCorners(list);
         if (list2.size() < 1) {
            return false;
         } else {
            BlockBox lv4 = this.makeBoundingBox(lv, list2);
            if (lv4.maxX - lv4.minX > 1 && lv4.maxY - lv4.minY > 1 && lv4.maxZ - lv4.minZ > 1) {
               this.offset = new BlockPos(lv4.minX - lv.getX() + 1, lv4.minY - lv.getY() + 1, lv4.minZ - lv.getZ() + 1);
               this.size = new BlockPos(lv4.maxX - lv4.minX - 1, lv4.maxY - lv4.minY - 1, lv4.maxZ - lv4.minZ - 1);
               this.markDirty();
               BlockState lv5 = this.world.getBlockState(lv);
               this.world.updateListeners(lv, lv5, lv5, 3);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private List<StructureBlockBlockEntity> findCorners(List<StructureBlockBlockEntity> structureBlockEntities) {
      Predicate<StructureBlockBlockEntity> predicate = arg -> arg.mode == StructureBlockMode.CORNER && Objects.equals(this.structureName, arg.structureName);
      return structureBlockEntities.stream().filter(predicate).collect(Collectors.toList());
   }

   private List<StructureBlockBlockEntity> findStructureBlockEntities(BlockPos pos1, BlockPos pos2) {
      List<StructureBlockBlockEntity> list = Lists.newArrayList();

      for (BlockPos lv : BlockPos.iterate(pos1, pos2)) {
         BlockState lv2 = this.world.getBlockState(lv);
         if (lv2.isOf(Blocks.STRUCTURE_BLOCK)) {
            BlockEntity lv3 = this.world.getBlockEntity(lv);
            if (lv3 != null && lv3 instanceof StructureBlockBlockEntity) {
               list.add((StructureBlockBlockEntity)lv3);
            }
         }
      }

      return list;
   }

   private BlockBox makeBoundingBox(BlockPos center, List<StructureBlockBlockEntity> corners) {
      BlockBox lv2;
      if (corners.size() > 1) {
         BlockPos lv = corners.get(0).getPos();
         lv2 = new BlockBox(lv, lv);
      } else {
         lv2 = new BlockBox(center, center);
      }

      for (StructureBlockBlockEntity lv4 : corners) {
         BlockPos lv5 = lv4.getPos();
         if (lv5.getX() < lv2.minX) {
            lv2.minX = lv5.getX();
         } else if (lv5.getX() > lv2.maxX) {
            lv2.maxX = lv5.getX();
         }

         if (lv5.getY() < lv2.minY) {
            lv2.minY = lv5.getY();
         } else if (lv5.getY() > lv2.maxY) {
            lv2.maxY = lv5.getY();
         }

         if (lv5.getZ() < lv2.minZ) {
            lv2.minZ = lv5.getZ();
         } else if (lv5.getZ() > lv2.maxZ) {
            lv2.maxZ = lv5.getZ();
         }
      }

      return lv2;
   }

   public boolean saveStructure() {
      return this.saveStructure(true);
   }

   public boolean saveStructure(boolean bl) {
      if (this.mode == StructureBlockMode.SAVE && !this.world.isClient && this.structureName != null) {
         BlockPos lv = this.getPos().add(this.offset);
         ServerWorld lv2 = (ServerWorld)this.world;
         StructureManager lv3 = lv2.getStructureManager();

         Structure lv4;
         try {
            lv4 = lv3.getStructureOrBlank(this.structureName);
         } catch (InvalidIdentifierException var8) {
            return false;
         }

         lv4.saveFromWorld(this.world, lv, this.size, !this.ignoreEntities, Blocks.STRUCTURE_VOID);
         lv4.setAuthor(this.author);
         if (bl) {
            try {
               return lv3.saveStructure(this.structureName);
            } catch (InvalidIdentifierException var7) {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean loadStructure(ServerWorld arg) {
      return this.loadStructure(arg, true);
   }

   private static Random createRandom(long seed) {
      return seed == 0L ? new Random(Util.getMeasuringTimeMs()) : new Random(seed);
   }

   public boolean loadStructure(ServerWorld arg, boolean bl) {
      if (this.mode == StructureBlockMode.LOAD && this.structureName != null) {
         StructureManager lv = arg.getStructureManager();

         Structure lv2;
         try {
            lv2 = lv.getStructure(this.structureName);
         } catch (InvalidIdentifierException var6) {
            return false;
         }

         return lv2 == null ? false : this.place(arg, bl, lv2);
      } else {
         return false;
      }
   }

   public boolean place(ServerWorld arg, boolean bl, Structure arg2) {
      BlockPos lv = this.getPos();
      if (!ChatUtil.isEmpty(arg2.getAuthor())) {
         this.author = arg2.getAuthor();
      }

      BlockPos lv2 = arg2.getSize();
      boolean bl2 = this.size.equals(lv2);
      if (!bl2) {
         this.size = lv2;
         this.markDirty();
         BlockState lv3 = arg.getBlockState(lv);
         arg.updateListeners(lv, lv3, lv3, 3);
      }

      if (bl && !bl2) {
         return false;
      } else {
         StructurePlacementData lv4 = new StructurePlacementData()
            .setMirror(this.mirror)
            .setRotation(this.rotation)
            .setIgnoreEntities(this.ignoreEntities)
            .setChunkPosition(null);
         if (this.integrity < 1.0F) {
            lv4.clearProcessors().addProcessor(new BlockRotStructureProcessor(MathHelper.clamp(this.integrity, 0.0F, 1.0F))).setRandom(createRandom(this.seed));
         }

         BlockPos lv5 = lv.add(this.offset);
         arg2.place(arg, lv5, lv4, createRandom(this.seed));
         return true;
      }
   }

   public void unloadStructure() {
      if (this.structureName != null) {
         ServerWorld lv = (ServerWorld)this.world;
         StructureManager lv2 = lv.getStructureManager();
         lv2.unloadStructure(this.structureName);
      }
   }

   public boolean isStructureAvailable() {
      if (this.mode == StructureBlockMode.LOAD && !this.world.isClient && this.structureName != null) {
         ServerWorld lv = (ServerWorld)this.world;
         StructureManager lv2 = lv.getStructureManager();

         try {
            return lv2.getStructure(this.structureName) != null;
         } catch (InvalidIdentifierException var4) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean isPowered() {
      return this.powered;
   }

   public void setPowered(boolean powered) {
      this.powered = powered;
   }

   @Environment(EnvType.CLIENT)
   public boolean shouldShowAir() {
      return this.showAir;
   }

   public void setShowAir(boolean showAir) {
      this.showAir = showAir;
   }

   @Environment(EnvType.CLIENT)
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

      private Action() {
      }
   }
}
