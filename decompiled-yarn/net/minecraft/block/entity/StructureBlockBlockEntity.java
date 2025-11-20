package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
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
      int _snowman = MathHelper.clamp(tag.getInt("posX"), -48, 48);
      int _snowmanx = MathHelper.clamp(tag.getInt("posY"), -48, 48);
      int _snowmanxx = MathHelper.clamp(tag.getInt("posZ"), -48, 48);
      this.offset = new BlockPos(_snowman, _snowmanx, _snowmanxx);
      int _snowmanxxx = MathHelper.clamp(tag.getInt("sizeX"), 0, 48);
      int _snowmanxxxx = MathHelper.clamp(tag.getInt("sizeY"), 0, 48);
      int _snowmanxxxxx = MathHelper.clamp(tag.getInt("sizeZ"), 0, 48);
      this.size = new BlockPos(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);

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
         BlockPos _snowman = this.getPos();
         BlockState _snowmanx = this.world.getBlockState(_snowman);
         if (_snowmanx.isOf(Blocks.STRUCTURE_BLOCK)) {
            this.world.setBlockState(_snowman, _snowmanx.with(StructureBlock.MODE, this.mode), 2);
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

   public void setStructureName(@Nullable Identifier _snowman) {
      this.structureName = _snowman;
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
      BlockState _snowman = this.world.getBlockState(this.getPos());
      if (_snowman.isOf(Blocks.STRUCTURE_BLOCK)) {
         this.world.setBlockState(this.getPos(), _snowman.with(StructureBlock.MODE, mode), 2);
      }
   }

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
      } else {
         BlockPos _snowman = this.getPos();
         int _snowmanx = 80;
         BlockPos _snowmanxx = new BlockPos(_snowman.getX() - 80, 0, _snowman.getZ() - 80);
         BlockPos _snowmanxxx = new BlockPos(_snowman.getX() + 80, 255, _snowman.getZ() + 80);
         List<StructureBlockBlockEntity> _snowmanxxxx = this.findStructureBlockEntities(_snowmanxx, _snowmanxxx);
         List<StructureBlockBlockEntity> _snowmanxxxxx = this.findCorners(_snowmanxxxx);
         if (_snowmanxxxxx.size() < 1) {
            return false;
         } else {
            BlockBox _snowmanxxxxxx = this.makeBoundingBox(_snowman, _snowmanxxxxx);
            if (_snowmanxxxxxx.maxX - _snowmanxxxxxx.minX > 1 && _snowmanxxxxxx.maxY - _snowmanxxxxxx.minY > 1 && _snowmanxxxxxx.maxZ - _snowmanxxxxxx.minZ > 1) {
               this.offset = new BlockPos(_snowmanxxxxxx.minX - _snowman.getX() + 1, _snowmanxxxxxx.minY - _snowman.getY() + 1, _snowmanxxxxxx.minZ - _snowman.getZ() + 1);
               this.size = new BlockPos(_snowmanxxxxxx.maxX - _snowmanxxxxxx.minX - 1, _snowmanxxxxxx.maxY - _snowmanxxxxxx.minY - 1, _snowmanxxxxxx.maxZ - _snowmanxxxxxx.minZ - 1);
               this.markDirty();
               BlockState _snowmanxxxxxxx = this.world.getBlockState(_snowman);
               this.world.updateListeners(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxx, 3);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private List<StructureBlockBlockEntity> findCorners(List<StructureBlockBlockEntity> structureBlockEntities) {
      Predicate<StructureBlockBlockEntity> _snowman = _snowmanx -> _snowmanx.mode == StructureBlockMode.CORNER && Objects.equals(this.structureName, _snowmanx.structureName);
      return structureBlockEntities.stream().filter(_snowman).collect(Collectors.toList());
   }

   private List<StructureBlockBlockEntity> findStructureBlockEntities(BlockPos pos1, BlockPos pos2) {
      List<StructureBlockBlockEntity> _snowman = Lists.newArrayList();

      for (BlockPos _snowmanx : BlockPos.iterate(pos1, pos2)) {
         BlockState _snowmanxx = this.world.getBlockState(_snowmanx);
         if (_snowmanxx.isOf(Blocks.STRUCTURE_BLOCK)) {
            BlockEntity _snowmanxxx = this.world.getBlockEntity(_snowmanx);
            if (_snowmanxxx != null && _snowmanxxx instanceof StructureBlockBlockEntity) {
               _snowman.add((StructureBlockBlockEntity)_snowmanxxx);
            }
         }
      }

      return _snowman;
   }

   private BlockBox makeBoundingBox(BlockPos center, List<StructureBlockBlockEntity> corners) {
      BlockBox _snowman;
      if (corners.size() > 1) {
         BlockPos _snowmanx = corners.get(0).getPos();
         _snowman = new BlockBox(_snowmanx, _snowmanx);
      } else {
         _snowman = new BlockBox(center, center);
      }

      for (StructureBlockBlockEntity _snowmanx : corners) {
         BlockPos _snowmanxx = _snowmanx.getPos();
         if (_snowmanxx.getX() < _snowman.minX) {
            _snowman.minX = _snowmanxx.getX();
         } else if (_snowmanxx.getX() > _snowman.maxX) {
            _snowman.maxX = _snowmanxx.getX();
         }

         if (_snowmanxx.getY() < _snowman.minY) {
            _snowman.minY = _snowmanxx.getY();
         } else if (_snowmanxx.getY() > _snowman.maxY) {
            _snowman.maxY = _snowmanxx.getY();
         }

         if (_snowmanxx.getZ() < _snowman.minZ) {
            _snowman.minZ = _snowmanxx.getZ();
         } else if (_snowmanxx.getZ() > _snowman.maxZ) {
            _snowman.maxZ = _snowmanxx.getZ();
         }
      }

      return _snowman;
   }

   public boolean saveStructure() {
      return this.saveStructure(true);
   }

   public boolean saveStructure(boolean _snowman) {
      if (this.mode == StructureBlockMode.SAVE && !this.world.isClient && this.structureName != null) {
         BlockPos _snowmanx = this.getPos().add(this.offset);
         ServerWorld _snowmanxx = (ServerWorld)this.world;
         StructureManager _snowmanxxx = _snowmanxx.getStructureManager();

         Structure _snowmanxxxx;
         try {
            _snowmanxxxx = _snowmanxxx.getStructureOrBlank(this.structureName);
         } catch (InvalidIdentifierException var8) {
            return false;
         }

         _snowmanxxxx.saveFromWorld(this.world, _snowmanx, this.size, !this.ignoreEntities, Blocks.STRUCTURE_VOID);
         _snowmanxxxx.setAuthor(this.author);
         if (_snowman) {
            try {
               return _snowmanxxx.saveStructure(this.structureName);
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

   public boolean loadStructure(ServerWorld _snowman) {
      return this.loadStructure(_snowman, true);
   }

   private static Random createRandom(long seed) {
      return seed == 0L ? new Random(Util.getMeasuringTimeMs()) : new Random(seed);
   }

   public boolean loadStructure(ServerWorld _snowman, boolean _snowman) {
      if (this.mode == StructureBlockMode.LOAD && this.structureName != null) {
         StructureManager _snowmanxx = _snowman.getStructureManager();

         Structure _snowmanxxx;
         try {
            _snowmanxxx = _snowmanxx.getStructure(this.structureName);
         } catch (InvalidIdentifierException var6) {
            return false;
         }

         return _snowmanxxx == null ? false : this.place(_snowman, _snowman, _snowmanxxx);
      } else {
         return false;
      }
   }

   public boolean place(ServerWorld _snowman, boolean _snowman, Structure _snowman) {
      BlockPos _snowmanxxx = this.getPos();
      if (!ChatUtil.isEmpty(_snowman.getAuthor())) {
         this.author = _snowman.getAuthor();
      }

      BlockPos _snowmanxxxx = _snowman.getSize();
      boolean _snowmanxxxxx = this.size.equals(_snowmanxxxx);
      if (!_snowmanxxxxx) {
         this.size = _snowmanxxxx;
         this.markDirty();
         BlockState _snowmanxxxxxx = _snowman.getBlockState(_snowmanxxx);
         _snowman.updateListeners(_snowmanxxx, _snowmanxxxxxx, _snowmanxxxxxx, 3);
      }

      if (_snowman && !_snowmanxxxxx) {
         return false;
      } else {
         StructurePlacementData _snowmanxxxxxx = new StructurePlacementData()
            .setMirror(this.mirror)
            .setRotation(this.rotation)
            .setIgnoreEntities(this.ignoreEntities)
            .setChunkPosition(null);
         if (this.integrity < 1.0F) {
            _snowmanxxxxxx.clearProcessors()
               .addProcessor(new BlockRotStructureProcessor(MathHelper.clamp(this.integrity, 0.0F, 1.0F)))
               .setRandom(createRandom(this.seed));
         }

         BlockPos _snowmanxxxxxxx = _snowmanxxx.add(this.offset);
         _snowman.place(_snowman, _snowmanxxxxxxx, _snowmanxxxxxx, createRandom(this.seed));
         return true;
      }
   }

   public void unloadStructure() {
      if (this.structureName != null) {
         ServerWorld _snowman = (ServerWorld)this.world;
         StructureManager _snowmanx = _snowman.getStructureManager();
         _snowmanx.unloadStructure(this.structureName);
      }
   }

   public boolean isStructureAvailable() {
      if (this.mode == StructureBlockMode.LOAD && !this.world.isClient && this.structureName != null) {
         ServerWorld _snowman = (ServerWorld)this.world;
         StructureManager _snowmanx = _snowman.getStructureManager();

         try {
            return _snowmanx.getStructure(this.structureName) != null;
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

      private Action() {
      }
   }
}
