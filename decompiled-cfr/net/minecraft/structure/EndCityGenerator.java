/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
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
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;

public class EndCityGenerator {
    private static final StructurePlacementData PLACEMENT_DATA = new StructurePlacementData().setIgnoreEntities(true).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
    private static final StructurePlacementData IGNORE_AIR_PLACEMENT_DATA = new StructurePlacementData().setIgnoreEntities(true).addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
    private static final Part BUILDING = new Part(){

        public void init() {
        }

        public boolean create(StructureManager manager, int depth, Piece root, BlockPos pos, List<StructurePiece> pieces, Random random) {
            if (depth > 8) {
                return false;
            }
            BlockRotation blockRotation = root.placementData.getRotation();
            Piece _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, root, pos, "base_floor", blockRotation, true));
            int _snowman3 = random.nextInt(3);
            if (_snowman3 == 0) {
                _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(-1, 4, -1), "base_roof", blockRotation, true));
            } else if (_snowman3 == 1) {
                _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(-1, 0, -1), "second_floor_2", blockRotation, false));
                _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(-1, 8, -1), "second_roof", blockRotation, false));
                EndCityGenerator.method_14680(manager, EndCityGenerator.method_14685(), depth + 1, _snowman2, null, pieces, random);
            } else if (_snowman3 == 2) {
                _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(-1, 0, -1), "second_floor_2", blockRotation, false));
                _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(-1, 4, -1), "third_floor_2", blockRotation, false));
                _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(-1, 8, -1), "third_roof", blockRotation, true));
                EndCityGenerator.method_14680(manager, EndCityGenerator.method_14685(), depth + 1, _snowman2, null, pieces, random);
            }
            return true;
        }
    };
    private static final List<Pair<BlockRotation, BlockPos>> SMALL_TOWER_BRIDGE_ATTACHMENTS = Lists.newArrayList((Object[])new Pair[]{new Pair<BlockRotation, BlockPos>(BlockRotation.NONE, new BlockPos(1, -1, 0)), new Pair<BlockRotation, BlockPos>(BlockRotation.CLOCKWISE_90, new BlockPos(6, -1, 1)), new Pair<BlockRotation, BlockPos>(BlockRotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)), new Pair<BlockRotation, BlockPos>(BlockRotation.CLOCKWISE_180, new BlockPos(5, -1, 6))});
    private static final Part SMALL_TOWER = new Part(){

        public void init() {
        }

        public boolean create(StructureManager manager, int depth, Piece root, BlockPos pos, List<StructurePiece> pieces, Random random) {
            BlockRotation blockRotation = root.placementData.getRotation();
            Piece _snowman2 = root;
            _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(3 + random.nextInt(2), -3, 3 + random.nextInt(2)), "tower_base", blockRotation, true));
            _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(0, 7, 0), "tower_piece", blockRotation, true));
            Piece _snowman3 = random.nextInt(3) == 0 ? _snowman2 : null;
            int _snowman4 = 1 + random.nextInt(3);
            for (int i = 0; i < _snowman4; ++i) {
                _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(0, 4, 0), "tower_piece", blockRotation, true));
                if (i >= _snowman4 - 1 || !random.nextBoolean()) continue;
                _snowman3 = _snowman2;
            }
            if (_snowman3 != null) {
                for (Pair pair : EndCityGenerator.method_14672()) {
                    if (!random.nextBoolean()) continue;
                    Piece piece = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman3, (BlockPos)pair.getRight(), "bridge_end", blockRotation.rotate((BlockRotation)((Object)pair.getLeft())), true));
                    EndCityGenerator.method_14680(manager, EndCityGenerator.method_14674(), depth + 1, piece, null, pieces, random);
                }
                _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(-1, 4, -1), "tower_top", blockRotation, true));
            } else if (depth == 7) {
                _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(-1, 4, -1), "tower_top", blockRotation, true));
            } else {
                return EndCityGenerator.method_14680(manager, EndCityGenerator.method_14671(), depth + 1, _snowman2, null, pieces, random);
            }
            return true;
        }
    };
    private static final Part BRIDGE_PIECE = new Part(){
        public boolean shipGenerated;

        @Override
        public void init() {
            this.shipGenerated = false;
        }

        @Override
        public boolean create(StructureManager manager, int depth, Piece root, BlockPos pos, List<StructurePiece> pieces, Random random) {
            BlockRotation blockRotation = root.placementData.getRotation();
            int _snowman2 = random.nextInt(4) + 1;
            Piece _snowman3 = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, root, new BlockPos(0, 0, -4), "bridge_piece", blockRotation, true));
            _snowman3.chainLength = -1;
            int _snowman4 = 0;
            for (int i = 0; i < _snowman2; ++i) {
                if (random.nextBoolean()) {
                    _snowman3 = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowman3, new BlockPos(0, _snowman4, -4), "bridge_piece", blockRotation, true));
                    _snowman4 = 0;
                    continue;
                }
                _snowman3 = random.nextBoolean() ? EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowman3, new BlockPos(0, _snowman4, -4), "bridge_steep_stairs", blockRotation, true)) : EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowman3, new BlockPos(0, _snowman4, -8), "bridge_gentle_stairs", blockRotation, true));
                _snowman4 = 4;
            }
            if (this.shipGenerated || random.nextInt(10 - depth) != 0) {
                if (!EndCityGenerator.createPart(manager, BUILDING, depth + 1, _snowman3, new BlockPos(-3, _snowman4 + 1, -11), pieces, random)) {
                    return false;
                }
            } else {
                EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowman3, new BlockPos(-8 + random.nextInt(8), _snowman4, -70 + random.nextInt(10)), "ship", blockRotation, true));
                this.shipGenerated = true;
            }
            _snowman3 = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(manager, _snowman3, new BlockPos(4, _snowman4, 0), "bridge_end", blockRotation.rotate(BlockRotation.CLOCKWISE_180), true));
            _snowman3.chainLength = -1;
            return true;
        }
    };
    private static final List<Pair<BlockRotation, BlockPos>> FAT_TOWER_BRIDGE_ATTACHMENTS = Lists.newArrayList((Object[])new Pair[]{new Pair<BlockRotation, BlockPos>(BlockRotation.NONE, new BlockPos(4, -1, 0)), new Pair<BlockRotation, BlockPos>(BlockRotation.CLOCKWISE_90, new BlockPos(12, -1, 4)), new Pair<BlockRotation, BlockPos>(BlockRotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)), new Pair<BlockRotation, BlockPos>(BlockRotation.CLOCKWISE_180, new BlockPos(8, -1, 12))});
    private static final Part FAT_TOWER = new Part(){

        public void init() {
        }

        public boolean create(StructureManager manager, int depth, Piece root, BlockPos pos, List<StructurePiece> pieces, Random random) {
            BlockRotation blockRotation = root.placementData.getRotation();
            Piece _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, root, new BlockPos(-3, 4, -3), "fat_tower_base", blockRotation, true));
            _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(0, 4, 0), "fat_tower_middle", blockRotation, true));
            for (int i = 0; i < 2 && random.nextInt(3) != 0; ++i) {
                _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(0, 8, 0), "fat_tower_middle", blockRotation, true));
                for (Pair pair : EndCityGenerator.method_14678()) {
                    if (!random.nextBoolean()) continue;
                    Piece piece = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, (BlockPos)pair.getRight(), "bridge_end", blockRotation.rotate((BlockRotation)((Object)pair.getLeft())), true));
                    EndCityGenerator.method_14680(manager, EndCityGenerator.method_14674(), depth + 1, piece, null, pieces, random);
                }
            }
            _snowman2 = EndCityGenerator.method_14683(pieces, EndCityGenerator.method_14670(manager, _snowman2, new BlockPos(-2, 8, -2), "fat_tower_top", blockRotation, true));
            return true;
        }
    };

    private static Piece createPiece(StructureManager structureManager, Piece lastPiece, BlockPos relativePosition, String template, BlockRotation rotation, boolean ignoreAir) {
        Piece piece = new Piece(structureManager, template, lastPiece.pos, rotation, ignoreAir);
        BlockPos _snowman2 = lastPiece.structure.transformBox(lastPiece.placementData, relativePosition, piece.placementData, BlockPos.ORIGIN);
        piece.translate(_snowman2.getX(), _snowman2.getY(), _snowman2.getZ());
        return piece;
    }

    public static void addPieces(StructureManager structureManager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random) {
        FAT_TOWER.init();
        BUILDING.init();
        BRIDGE_PIECE.init();
        SMALL_TOWER.init();
        Piece piece = EndCityGenerator.addPiece(pieces, new Piece(structureManager, "base_floor", pos, rotation, true));
        piece = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(structureManager, piece, new BlockPos(-1, 0, -1), "second_floor_1", rotation, false));
        piece = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(structureManager, piece, new BlockPos(-1, 4, -1), "third_floor_1", rotation, false));
        piece = EndCityGenerator.addPiece(pieces, EndCityGenerator.createPiece(structureManager, piece, new BlockPos(-1, 8, -1), "third_roof", rotation, true));
        EndCityGenerator.createPart(structureManager, SMALL_TOWER, 1, piece, null, pieces, random);
    }

    private static Piece addPiece(List<StructurePiece> pieces, Piece piece) {
        pieces.add(piece);
        return piece;
    }

    private static boolean createPart(StructureManager manager, Part piece, int depth, Piece parent, BlockPos pos, List<StructurePiece> pieces, Random random) {
        if (depth > 8) {
            return false;
        }
        ArrayList arrayList = Lists.newArrayList();
        if (piece.create(manager, depth, parent, pos, arrayList, random)) {
            boolean bl = false;
            int _snowman2 = random.nextInt();
            for (StructurePiece structurePiece : arrayList) {
                structurePiece.chainLength = _snowman2;
                _snowman = StructurePiece.getOverlappingPiece(pieces, structurePiece.getBoundingBox());
                if (_snowman == null || _snowman.chainLength == parent.chainLength) continue;
                bl = true;
                break;
            }
            if (!bl) {
                pieces.addAll(arrayList);
                return true;
            }
        }
        return false;
    }

    static /* synthetic */ Part method_14685() {
        return SMALL_TOWER;
    }

    static /* synthetic */ List method_14672() {
        return SMALL_TOWER_BRIDGE_ATTACHMENTS;
    }

    static /* synthetic */ Part method_14674() {
        return BRIDGE_PIECE;
    }

    static /* synthetic */ Part method_14671() {
        return FAT_TOWER;
    }

    static /* synthetic */ List method_14678() {
        return FAT_TOWER_BRIDGE_ATTACHMENTS;
    }

    static interface Part {
        public void init();

        public boolean create(StructureManager var1, int var2, Piece var3, BlockPos var4, List<StructurePiece> var5, Random var6);
    }

    public static class Piece
    extends SimpleStructurePiece {
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
            Structure structure = manager.getStructureOrBlank(new Identifier("end_city/" + this.template));
            StructurePlacementData _snowman2 = (this.ignoreAir ? PLACEMENT_DATA : IGNORE_AIR_PLACEMENT_DATA).copy().setRotation(this.rotation);
            this.setStructureData(structure, this.pos, _snowman2);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", this.template);
            tag.putString("Rot", this.rotation.name());
            tag.putBoolean("OW", this.ignoreAir);
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess2, Random random, BlockBox boundingBox) {
            if (metadata.startsWith("Chest")) {
                BlockPos blockPos = pos.down();
                if (boundingBox.contains(blockPos)) {
                    LootableContainerBlockEntity.setLootTable(serverWorldAccess2, random, blockPos, LootTables.END_CITY_TREASURE_CHEST);
                }
            } else if (metadata.startsWith("Sentry")) {
                ServerWorldAccess serverWorldAccess2;
                ShulkerEntity _snowman2 = EntityType.SHULKER.create(serverWorldAccess2.toServerWorld());
                _snowman2.updatePosition((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5);
                _snowman2.setAttachedBlock(pos);
                serverWorldAccess2.spawnEntity(_snowman2);
            } else if (metadata.startsWith("Elytra")) {
                ItemFrameEntity _snowman3 = new ItemFrameEntity(serverWorldAccess2.toServerWorld(), pos, this.rotation.rotate(Direction.SOUTH));
                _snowman3.setHeldItemStack(new ItemStack(Items.ELYTRA), false);
                serverWorldAccess2.spawnEntity(_snowman3);
            }
        }
    }
}

