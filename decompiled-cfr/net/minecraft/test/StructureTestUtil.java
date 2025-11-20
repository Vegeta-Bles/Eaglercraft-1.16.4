/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 */
package net.minecraft.test;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.server.world.ServerTickScheduler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import org.apache.commons.io.IOUtils;

public class StructureTestUtil {
    public static String testStructuresDirectoryName = "gameteststructures";

    public static BlockRotation method_29408(int n) {
        switch (n) {
            case 0: {
                return BlockRotation.NONE;
            }
            case 1: {
                return BlockRotation.CLOCKWISE_90;
            }
            case 2: {
                return BlockRotation.CLOCKWISE_180;
            }
            case 3: {
                return BlockRotation.COUNTERCLOCKWISE_90;
            }
        }
        throw new IllegalArgumentException("rotationSteps must be a value from 0-3. Got value " + n);
    }

    public static Box getStructureBoundingBox(StructureBlockBlockEntity structureBlockEntity) {
        BlockPos blockPos = structureBlockEntity.getPos();
        _snowman = blockPos.add(structureBlockEntity.getSize().add(-1, -1, -1));
        _snowman = Structure.transformAround(_snowman, BlockMirror.NONE, structureBlockEntity.getRotation(), blockPos);
        return new Box(blockPos, _snowman);
    }

    public static BlockBox method_29410(StructureBlockBlockEntity structureBlockBlockEntity) {
        BlockPos blockPos = structureBlockBlockEntity.getPos();
        _snowman = blockPos.add(structureBlockBlockEntity.getSize().add(-1, -1, -1));
        _snowman = Structure.transformAround(_snowman, BlockMirror.NONE, structureBlockBlockEntity.getRotation(), blockPos);
        return new BlockBox(blockPos, _snowman);
    }

    public static void placeStartButton(BlockPos blockPos, BlockPos blockPos2, BlockRotation blockRotation, ServerWorld serverWorld) {
        BlockPos blockPos3 = Structure.transformAround(blockPos.add(blockPos2), BlockMirror.NONE, blockRotation, blockPos);
        serverWorld.setBlockState(blockPos3, Blocks.COMMAND_BLOCK.getDefaultState());
        CommandBlockBlockEntity _snowman2 = (CommandBlockBlockEntity)serverWorld.getBlockEntity(blockPos3);
        _snowman2.getCommandExecutor().setCommand("test runthis");
        _snowman = Structure.transformAround(blockPos3.add(0, 0, -1), BlockMirror.NONE, blockRotation, blockPos3);
        serverWorld.setBlockState(_snowman, Blocks.STONE_BUTTON.getDefaultState().rotate(blockRotation));
    }

    public static void createTestArea(String structure, BlockPos pos, BlockPos size, BlockRotation blockRotation, ServerWorld world) {
        BlockBox blockBox = StructureTestUtil.method_29409(pos, size, blockRotation);
        StructureTestUtil.clearArea(blockBox, pos.getY(), world);
        world.setBlockState(pos, Blocks.STRUCTURE_BLOCK.getDefaultState());
        StructureBlockBlockEntity _snowman2 = (StructureBlockBlockEntity)world.getBlockEntity(pos);
        _snowman2.setIgnoreEntities(false);
        _snowman2.setStructureName(new Identifier(structure));
        _snowman2.setSize(size);
        _snowman2.setMode(StructureBlockMode.SAVE);
        _snowman2.setShowBoundingBox(true);
    }

    public static StructureBlockBlockEntity method_22250(String string, BlockPos blockPos, BlockRotation blockRotation, int n, ServerWorld serverWorld, boolean bl) {
        BlockPos blockPos2 = StructureTestUtil.createStructure(string, serverWorld).getSize();
        BlockBox _snowman2 = StructureTestUtil.method_29409(blockPos, blockPos2, blockRotation);
        if (blockRotation == BlockRotation.NONE) {
            _snowman = blockPos;
        } else if (blockRotation == BlockRotation.CLOCKWISE_90) {
            _snowman = blockPos.add(blockPos2.getZ() - 1, 0, 0);
        } else if (blockRotation == BlockRotation.CLOCKWISE_180) {
            _snowman = blockPos.add(blockPos2.getX() - 1, 0, blockPos2.getZ() - 1);
        } else if (blockRotation == BlockRotation.COUNTERCLOCKWISE_90) {
            _snowman = blockPos.add(0, 0, blockPos2.getX() - 1);
        } else {
            throw new IllegalArgumentException("Invalid rotation: " + (Object)((Object)blockRotation));
        }
        StructureTestUtil.forceLoadNearbyChunks(blockPos, serverWorld);
        StructureTestUtil.clearArea(_snowman2, blockPos.getY(), serverWorld);
        StructureBlockBlockEntity _snowman3 = StructureTestUtil.placeStructure(string, _snowman, blockRotation, serverWorld, bl);
        ((ServerTickScheduler)serverWorld.getBlockTickScheduler()).getScheduledTicks(_snowman2, true, false);
        serverWorld.clearUpdatesInArea(_snowman2);
        return _snowman3;
    }

    private static void forceLoadNearbyChunks(BlockPos pos, ServerWorld world) {
        ChunkPos chunkPos = new ChunkPos(pos);
        for (int i = -1; i < 4; ++i) {
            for (_snowman = -1; _snowman < 4; ++_snowman) {
                _snowman = chunkPos.x + i;
                _snowman = chunkPos.z + _snowman;
                world.setChunkForced(_snowman, _snowman, true);
            }
        }
    }

    public static void clearArea(BlockBox area, int n, ServerWorld world) {
        BlockBox blockBox = new BlockBox(area.minX - 2, area.minY - 3, area.minZ - 3, area.maxX + 3, area.maxY + 20, area.maxZ + 3);
        BlockPos.stream(blockBox).forEach(blockPos -> StructureTestUtil.method_22368(n, blockPos, world));
        ((ServerTickScheduler)world.getBlockTickScheduler()).getScheduledTicks(blockBox, true, false);
        world.clearUpdatesInArea(blockBox);
        Box _snowman2 = new Box(blockBox.minX, blockBox.minY, blockBox.minZ, blockBox.maxX, blockBox.maxY, blockBox.maxZ);
        List<Entity> _snowman3 = world.getEntitiesByClass(Entity.class, _snowman2, entity -> !(entity instanceof PlayerEntity));
        _snowman3.forEach(Entity::remove);
    }

    public static BlockBox method_29409(BlockPos blockPos, BlockPos blockPos2, BlockRotation blockRotation) {
        BlockPos blockPos3 = blockPos.add(blockPos2).add(-1, -1, -1);
        _snowman = Structure.transformAround(blockPos3, BlockMirror.NONE, blockRotation, blockPos);
        BlockBox _snowman2 = BlockBox.create(blockPos.getX(), blockPos.getY(), blockPos.getZ(), _snowman.getX(), _snowman.getY(), _snowman.getZ());
        int _snowman3 = Math.min(_snowman2.minX, _snowman2.maxX);
        int _snowman4 = Math.min(_snowman2.minZ, _snowman2.maxZ);
        _snowman = new BlockPos(blockPos.getX() - _snowman3, 0, blockPos.getZ() - _snowman4);
        _snowman2.move(_snowman);
        return _snowman2;
    }

    public static Optional<BlockPos> findContainingStructureBlock(BlockPos pos, int radius, ServerWorld world) {
        return StructureTestUtil.findStructureBlocks(pos, radius, world).stream().filter(blockPos2 -> StructureTestUtil.isInStructureBounds(blockPos2, pos, world)).findFirst();
    }

    @Nullable
    public static BlockPos findNearestStructureBlock(BlockPos pos, int radius, ServerWorld world) {
        Comparator<BlockPos> comparator = Comparator.comparingInt(blockPos2 -> blockPos2.getManhattanDistance(pos));
        Collection<BlockPos> _snowman2 = StructureTestUtil.findStructureBlocks(pos, radius, world);
        Optional<BlockPos> _snowman3 = _snowman2.stream().min(comparator);
        return _snowman3.orElse(null);
    }

    public static Collection<BlockPos> findStructureBlocks(BlockPos pos, int radius, ServerWorld world) {
        ArrayList arrayList = Lists.newArrayList();
        Box _snowman2 = new Box(pos);
        _snowman2 = _snowman2.expand(radius);
        for (int i = (int)_snowman2.minX; i <= (int)_snowman2.maxX; ++i) {
            for (_snowman = (int)_snowman2.minY; _snowman <= (int)_snowman2.maxY; ++_snowman) {
                for (_snowman = (int)_snowman2.minZ; _snowman <= (int)_snowman2.maxZ; ++_snowman) {
                    BlockPos blockPos = new BlockPos(i, _snowman, _snowman);
                    BlockState _snowman3 = world.getBlockState(blockPos);
                    if (!_snowman3.isOf(Blocks.STRUCTURE_BLOCK)) continue;
                    arrayList.add(blockPos);
                }
            }
        }
        return arrayList;
    }

    private static Structure createStructure(String structureId, ServerWorld world) {
        StructureManager structureManager = world.getStructureManager();
        Structure _snowman2 = structureManager.getStructure(new Identifier(structureId));
        if (_snowman2 != null) {
            return _snowman2;
        }
        String _snowman3 = structureId + ".snbt";
        Path _snowman4 = Paths.get(testStructuresDirectoryName, _snowman3);
        CompoundTag _snowman5 = StructureTestUtil.loadSnbt(_snowman4);
        if (_snowman5 == null) {
            throw new RuntimeException("Could not find structure file " + _snowman4 + ", and the structure is not available in the world structures either.");
        }
        return structureManager.createStructure(_snowman5);
    }

    private static StructureBlockBlockEntity placeStructure(String name, BlockPos pos, BlockRotation blockRotation, ServerWorld serverWorld, boolean bl) {
        serverWorld.setBlockState(pos, Blocks.STRUCTURE_BLOCK.getDefaultState());
        StructureBlockBlockEntity structureBlockBlockEntity = (StructureBlockBlockEntity)serverWorld.getBlockEntity(pos);
        structureBlockBlockEntity.setMode(StructureBlockMode.LOAD);
        structureBlockBlockEntity.setRotation(blockRotation);
        structureBlockBlockEntity.setIgnoreEntities(false);
        structureBlockBlockEntity.setStructureName(new Identifier(name));
        structureBlockBlockEntity.loadStructure(serverWorld, bl);
        if (structureBlockBlockEntity.getSize() != BlockPos.ORIGIN) {
            return structureBlockBlockEntity;
        }
        Structure _snowman2 = StructureTestUtil.createStructure(name, serverWorld);
        structureBlockBlockEntity.place(serverWorld, bl, _snowman2);
        if (structureBlockBlockEntity.getSize() == BlockPos.ORIGIN) {
            throw new RuntimeException("Failed to load structure " + name);
        }
        return structureBlockBlockEntity;
    }

    @Nullable
    private static CompoundTag loadSnbt(Path path) {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            String _snowman2 = IOUtils.toString((Reader)bufferedReader);
            return StringNbtReader.parse(_snowman2);
        }
        catch (IOException iOException) {
            return null;
        }
        catch (CommandSyntaxException commandSyntaxException) {
            throw new RuntimeException("Error while trying to load structure " + path, commandSyntaxException);
        }
    }

    private static void method_22368(int altitude, BlockPos pos, ServerWorld world) {
        Object _snowman3;
        BlockState blockState = null;
        FlatChunkGeneratorConfig _snowman2 = FlatChunkGeneratorConfig.getDefaultConfig(world.getRegistryManager().get(Registry.BIOME_KEY));
        if (_snowman2 instanceof FlatChunkGeneratorConfig) {
            _snowman3 = _snowman2.getLayerBlocks();
            if (pos.getY() < altitude && pos.getY() <= ((BlockState[])_snowman3).length) {
                blockState = _snowman3[pos.getY() - 1];
            }
        } else if (pos.getY() == altitude - 1) {
            blockState = world.getBiome(pos).getGenerationSettings().getSurfaceConfig().getTopMaterial();
        } else if (pos.getY() < altitude - 1) {
            blockState = world.getBiome(pos).getGenerationSettings().getSurfaceConfig().getUnderMaterial();
        }
        if (blockState == null) {
            blockState = Blocks.AIR.getDefaultState();
        }
        _snowman3 = new BlockStateArgument(blockState, Collections.emptySet(), null);
        ((BlockStateArgument)_snowman3).setBlockState(world, pos, 2);
        world.updateNeighbors(pos, blockState.getBlock());
    }

    private static boolean isInStructureBounds(BlockPos structureBlockPos, BlockPos pos, ServerWorld world) {
        StructureBlockBlockEntity structureBlockBlockEntity = (StructureBlockBlockEntity)world.getBlockEntity(structureBlockPos);
        Box _snowman2 = StructureTestUtil.getStructureBoundingBox(structureBlockBlockEntity).expand(1.0);
        return _snowman2.contains(Vec3d.ofCenter(pos));
    }
}

