package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;

public class EndCityGenerator {
   private static final StructurePlacementData PLACEMENT_DATA = new StructurePlacementData()
      .setIgnoreEntities(true)
      .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
   private static final StructurePlacementData IGNORE_AIR_PLACEMENT_DATA = new StructurePlacementData()
      .setIgnoreEntities(true)
      .addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
   private static final EndCityGenerator.Part BUILDING = new EndCityGenerator.Part() {
      @Override
      public void init() {
      }

      @Override
      public boolean create(StructureManager manager, int depth, EndCityGenerator.Piece root, BlockPos pos, List<StructurePiece> pieces, Random random) {
         if (depth > 8) {
            return false;
         } else {
            BlockRotation _snowman = root.placementData.getRotation();
            EndCityGenerator.Piece _snowmanx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, root, pos, "base_floor", _snowman, true));
            int _snowmanxx = random.nextInt(3);
            if (_snowmanxx == 0) {
               _snowmanx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanx, new BlockPos(-1, 4, -1), "base_roof", _snowman, true));
            } else if (_snowmanxx == 1) {
               _snowmanx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanx, new BlockPos(-1, 0, -1), "second_floor_2", _snowman, false));
               _snowmanx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanx, new BlockPos(-1, 8, -1), "second_roof", _snowman, false));
               EndCityGenerator.createPart(manager, EndCityGenerator.SMALL_TOWER, depth + 1, _snowmanx, null, pieces, random);
            } else if (_snowmanxx == 2) {
               _snowmanx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanx, new BlockPos(-1, 0, -1), "second_floor_2", _snowman, false));
               _snowmanx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanx, new BlockPos(-1, 4, -1), "third_floor_2", _snowman, false));
               _snowmanx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanx, new BlockPos(-1, 8, -1), "third_roof", _snowman, true));
               EndCityGenerator.createPart(manager, EndCityGenerator.SMALL_TOWER, depth + 1, _snowmanx, null, pieces, random);
            }

            return true;
         }
      }
   };
   private static final List<Pair<BlockRotation, BlockPos>> SMALL_TOWER_BRIDGE_ATTACHMENTS = Lists.newArrayList(
      new Pair[]{
         new Pair<>(BlockRotation.NONE, new BlockPos(1, -1, 0)),
         new Pair<>(BlockRotation.CLOCKWISE_90, new BlockPos(6, -1, 1)),
         new Pair<>(BlockRotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)),
         new Pair<>(BlockRotation.CLOCKWISE_180, new BlockPos(5, -1, 6))
      }
   );
   private static final EndCityGenerator.Part SMALL_TOWER = new EndCityGenerator.Part() {
      @Override
      public void init() {
      }

      @Override
      public boolean create(StructureManager manager, int depth, EndCityGenerator.Piece root, BlockPos pos, List<StructurePiece> pieces, Random random) {
         BlockRotation _snowman = root.placementData.getRotation();
         EndCityGenerator.Piece var8 = EndCityGenerator.addPiece(
            pieces, EndCityGenerator.createPiece(manager, root, new BlockPos(3 + random.nextInt(2), -3, 3 + random.nextInt(2)), "tower_base", _snowman, true)
         );
         var8 = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, var8, new BlockPos(0, 7, 0), "tower_piece", _snowman, true));
         EndCityGenerator.Piece _snowmanx = random.nextInt(3) == 0 ? var8 : null;
         int _snowmanxx = 1 + random.nextInt(3);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
            var8 = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, var8, new BlockPos(0, 4, 0), "tower_piece", _snowman, true));
            if (_snowmanxxx < _snowmanxx - 1 && random.nextBoolean()) {
               _snowmanx = var8;
            }
         }

         if (_snowmanx != null) {
            for (Pair<BlockRotation, BlockPos> _snowmanxxxx : EndCityGenerator.SMALL_TOWER_BRIDGE_ATTACHMENTS) {
               if (random.nextBoolean()) {
                  EndCityGenerator.Piece _snowmanxxxxx = EndCityGenerator.addPiece(
                     pieces, EndCityGenerator.createPiece(manager, _snowmanx, _snowmanxxxx.getRight(), "bridge_end", _snowman.rotate(_snowmanxxxx.getLeft()), true)
                  );
                  EndCityGenerator.createPart(manager, EndCityGenerator.BRIDGE_PIECE, depth + 1, _snowmanxxxxx, null, pieces, random);
               }
            }

            var8 = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, var8, new BlockPos(-1, 4, -1), "tower_top", _snowman, true));
         } else {
            if (depth != 7) {
               return EndCityGenerator.createPart(manager, EndCityGenerator.FAT_TOWER, depth + 1, var8, null, pieces, random);
            }

            var8 = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, var8, new BlockPos(-1, 4, -1), "tower_top", _snowman, true));
         }

         return true;
      }
   };
   private static final EndCityGenerator.Part BRIDGE_PIECE = new EndCityGenerator.Part() {
      public boolean shipGenerated;

      @Override
      public void init() {
         this.shipGenerated = false;
      }

      @Override
      public boolean create(StructureManager manager, int depth, EndCityGenerator.Piece root, BlockPos pos, List<StructurePiece> pieces, Random random) {
         BlockRotation _snowman = root.placementData.getRotation();
         int _snowmanx = random.nextInt(4) + 1;
         EndCityGenerator.Piece _snowmanxx = EndCityGenerator.addPiece(
            pieces, EndCityGenerator.createPiece(manager, root, new BlockPos(0, 0, -4), "bridge_piece", _snowman, true)
         );
         _snowmanxx.chainLength = -1;
         int _snowmanxxx = 0;

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanx; _snowmanxxxx++) {
            if (random.nextBoolean()) {
               _snowmanxx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanxx, new BlockPos(0, _snowmanxxx, -4), "bridge_piece", _snowman, true));
               _snowmanxxx = 0;
            } else {
               if (random.nextBoolean()) {
                  _snowmanxx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanxx, new BlockPos(0, _snowmanxxx, -4), "bridge_steep_stairs", _snowman, true));
               } else {
                  _snowmanxx = EndCityGenerator.addPiece(
                     pieces, EndCityGenerator.createPiece(manager, _snowmanxx, new BlockPos(0, _snowmanxxx, -8), "bridge_gentle_stairs", _snowman, true)
                  );
               }

               _snowmanxxx = 4;
            }
         }

         if (!this.shipGenerated && random.nextInt(10 - depth) == 0) {
            EndCityGenerator.addPiece(
               pieces, EndCityGenerator.createPiece(manager, _snowmanxx, new BlockPos(-8 + random.nextInt(8), _snowmanxxx, -70 + random.nextInt(10)), "ship", _snowman, true)
            );
            this.shipGenerated = true;
         } else if (!EndCityGenerator.createPart(manager, EndCityGenerator.BUILDING, depth + 1, _snowmanxx, new BlockPos(-3, _snowmanxxx + 1, -11), pieces, random)) {
            return false;
         }

         _snowmanxx = EndCityGenerator.addPiece(
            pieces, EndCityGenerator.createPiece(manager, _snowmanxx, new BlockPos(4, _snowmanxxx, 0), "bridge_end", _snowman.rotate(BlockRotation.CLOCKWISE_180), true)
         );
         _snowmanxx.chainLength = -1;
         return true;
      }
   };
   private static final List<Pair<BlockRotation, BlockPos>> FAT_TOWER_BRIDGE_ATTACHMENTS = Lists.newArrayList(
      new Pair[]{
         new Pair<>(BlockRotation.NONE, new BlockPos(4, -1, 0)),
         new Pair<>(BlockRotation.CLOCKWISE_90, new BlockPos(12, -1, 4)),
         new Pair<>(BlockRotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)),
         new Pair<>(BlockRotation.CLOCKWISE_180, new BlockPos(8, -1, 12))
      }
   );
   private static final EndCityGenerator.Part FAT_TOWER = new EndCityGenerator.Part() {
      @Override
      public void init() {
      }

      @Override
      public boolean create(StructureManager manager, int depth, EndCityGenerator.Piece root, BlockPos pos, List<StructurePiece> pieces, Random random) {
         BlockRotation _snowman = root.placementData.getRotation();
         EndCityGenerator.Piece _snowmanx = EndCityGenerator.addPiece(
            pieces, EndCityGenerator.createPiece(manager, root, new BlockPos(-3, 4, -3), "fat_tower_base", _snowman, true)
         );
         _snowmanx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanx, new BlockPos(0, 4, 0), "fat_tower_middle", _snowman, true));

         for (int _snowmanxx = 0; _snowmanxx < 2 && random.nextInt(3) != 0; _snowmanxx++) {
            _snowmanx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanx, new BlockPos(0, 8, 0), "fat_tower_middle", _snowman, true));

            for (Pair<BlockRotation, BlockPos> _snowmanxxx : EndCityGenerator.FAT_TOWER_BRIDGE_ATTACHMENTS) {
               if (random.nextBoolean()) {
                  EndCityGenerator.Piece _snowmanxxxx = EndCityGenerator.addPiece(
                     pieces, EndCityGenerator.createPiece(manager, _snowmanx, _snowmanxxx.getRight(), "bridge_end", _snowman.rotate(_snowmanxxx.getLeft()), true)
                  );
                  EndCityGenerator.createPart(manager, EndCityGenerator.BRIDGE_PIECE, depth + 1, _snowmanxxxx, null, pieces, random);
               }
            }
         }

         _snowmanx = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowmanx, new BlockPos(-2, 8, -2), "fat_tower_top", _snowman, true));
         return true;
      }
   };

   private static EndCityGenerator.Piece createPiece(
      StructureManager structureManager,
      EndCityGenerator.Piece lastPiece,
      BlockPos relativePosition,
      String template,
      BlockRotation rotation,
      boolean ignoreAir
   ) {
      EndCityGenerator.Piece _snowman = new EndCityGenerator.Piece(structureManager, template, lastPiece.pos, rotation, ignoreAir);
      BlockPos _snowmanx = lastPiece.structure.transformBox(lastPiece.placementData, relativePosition, _snowman.placementData, BlockPos.ORIGIN);
      _snowman.translate(_snowmanx.getX(), _snowmanx.getY(), _snowmanx.getZ());
      return _snowman;
   }

   public static void addPieces(StructureManager structureManager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random) {
      FAT_TOWER.init();
      BUILDING.init();
      BRIDGE_PIECE.init();
      SMALL_TOWER.init();
      EndCityGenerator.Piece _snowman = addPiece(pieces, new EndCityGenerator.Piece(structureManager, "base_floor", pos, rotation, true));
      _snowman = addPiece(pieces, createPiece(structureManager, _snowman, new BlockPos(-1, 0, -1), "second_floor_1", rotation, false));
      _snowman = addPiece(pieces, createPiece(structureManager, _snowman, new BlockPos(-1, 4, -1), "third_floor_1", rotation, false));
      _snowman = addPiece(pieces, createPiece(structureManager, _snowman, new BlockPos(-1, 8, -1), "third_roof", rotation, true));
      createPart(structureManager, SMALL_TOWER, 1, _snowman, null, pieces, random);
   }

   private static EndCityGenerator.Piece addPiece(List<StructurePiece> pieces, EndCityGenerator.Piece piece) {
      pieces.add(piece);
      return piece;
   }

   private static boolean createPart(
      StructureManager manager, EndCityGenerator.Part piece, int depth, EndCityGenerator.Piece parent, BlockPos pos, List<StructurePiece> pieces, Random random
   ) {
      if (depth > 8) {
         return false;
      } else {
         List<StructurePiece> _snowman = Lists.newArrayList();
         if (piece.create(manager, depth, parent, pos, _snowman, random)) {
            boolean _snowmanx = false;
            int _snowmanxx = random.nextInt();

            for (StructurePiece _snowmanxxx : _snowman) {
               _snowmanxxx.chainLength = _snowmanxx;
               StructurePiece _snowmanxxxx = StructurePiece.getOverlappingPiece(pieces, _snowmanxxx.getBoundingBox());
               if (_snowmanxxxx != null && _snowmanxxxx.chainLength != parent.chainLength) {
                  _snowmanx = true;
                  break;
               }
            }

            if (!_snowmanx) {
               pieces.addAll(_snowman);
               return true;
            }
         }

         return false;
      }
   }

   interface Part {
      void init();

      boolean create(StructureManager manager, int depth, EndCityGenerator.Piece root, BlockPos pos, List<StructurePiece> pieces, Random random);
   }

   public static class Piece extends SimpleStructurePiece {
      private final String template;
      private final BlockRotation rotation;
      private final boolean ignoreAir;

      public Piece(StructureManager manager, String template, BlockPos pos, BlockRotation rotation, boolean ignoreAir) {
         super(StructurePieceType.END_CITY, 0);
         this.template = template;
         this.pos = pos;
         this.rotation = rotation;
         this.ignoreAir = ignoreAir;
         this.initializeStructureData(manager);
      }

      public Piece(StructureManager manager, CompoundTag tag) {
         super(StructurePieceType.END_CITY, tag);
         this.template = tag.getString("Template");
         this.rotation = BlockRotation.valueOf(tag.getString("Rot"));
         this.ignoreAir = tag.getBoolean("OW");
         this.initializeStructureData(manager);
      }

      private void initializeStructureData(StructureManager manager) {
         Structure _snowman = manager.getStructureOrBlank(new Identifier("end_city/" + this.template));
         StructurePlacementData _snowmanx = (this.ignoreAir ? EndCityGenerator.PLACEMENT_DATA : EndCityGenerator.IGNORE_AIR_PLACEMENT_DATA)
            .copy()
            .setRotation(this.rotation);
         this.setStructureData(_snowman, this.pos, _snowmanx);
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putString("Template", this.template);
         tag.putString("Rot", this.rotation.name());
         tag.putBoolean("OW", this.ignoreAir);
      }

      @Override
      protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess _snowman, Random random, BlockBox boundingBox) {
         if (metadata.startsWith("Chest")) {
            BlockPos _snowmanx = pos.down();
            if (boundingBox.contains(_snowmanx)) {
               LootableContainerBlockEntity.setLootTable(_snowman, random, _snowmanx, LootTables.END_CITY_TREASURE_CHEST);
            }
         } else if (metadata.startsWith("Sentry")) {
            ShulkerEntity _snowmanx = EntityType.SHULKER.create(_snowman.toServerWorld());
            _snowmanx.updatePosition((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5);
            _snowmanx.setAttachedBlock(pos);
            _snowman.spawnEntity(_snowmanx);
         } else if (metadata.startsWith("Elytra")) {
            ItemFrameEntity _snowmanx = new ItemFrameEntity(_snowman.toServerWorld(), pos, this.rotation.rotate(Direction.SOUTH));
            _snowmanx.setHeldItemStack(new ItemStack(Items.ELYTRA), false);
            _snowman.spawnEntity(_snowmanx);
         }
      }
   }
}
