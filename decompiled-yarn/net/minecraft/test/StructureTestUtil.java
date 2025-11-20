package net.minecraft.test;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

   public static BlockRotation method_29408(int _snowman) {
      switch (_snowman) {
         case 0:
            return BlockRotation.NONE;
         case 1:
            return BlockRotation.CLOCKWISE_90;
         case 2:
            return BlockRotation.CLOCKWISE_180;
         case 3:
            return BlockRotation.COUNTERCLOCKWISE_90;
         default:
            throw new IllegalArgumentException("rotationSteps must be a value from 0-3. Got value " + _snowman);
      }
   }

   public static Box getStructureBoundingBox(StructureBlockBlockEntity structureBlockEntity) {
      BlockPos _snowman = structureBlockEntity.getPos();
      BlockPos _snowmanx = _snowman.add(structureBlockEntity.getSize().add(-1, -1, -1));
      BlockPos _snowmanxx = Structure.transformAround(_snowmanx, BlockMirror.NONE, structureBlockEntity.getRotation(), _snowman);
      return new Box(_snowman, _snowmanxx);
   }

   public static BlockBox method_29410(StructureBlockBlockEntity _snowman) {
      BlockPos _snowmanx = _snowman.getPos();
      BlockPos _snowmanxx = _snowmanx.add(_snowman.getSize().add(-1, -1, -1));
      BlockPos _snowmanxxx = Structure.transformAround(_snowmanxx, BlockMirror.NONE, _snowman.getRotation(), _snowmanx);
      return new BlockBox(_snowmanx, _snowmanxxx);
   }

   public static void placeStartButton(BlockPos _snowman, BlockPos _snowman, BlockRotation _snowman, ServerWorld _snowman) {
      BlockPos _snowmanxxxx = Structure.transformAround(_snowman.add(_snowman), BlockMirror.NONE, _snowman, _snowman);
      _snowman.setBlockState(_snowmanxxxx, Blocks.COMMAND_BLOCK.getDefaultState());
      CommandBlockBlockEntity _snowmanxxxxx = (CommandBlockBlockEntity)_snowman.getBlockEntity(_snowmanxxxx);
      _snowmanxxxxx.getCommandExecutor().setCommand("test runthis");
      BlockPos _snowmanxxxxxx = Structure.transformAround(_snowmanxxxx.add(0, 0, -1), BlockMirror.NONE, _snowman, _snowmanxxxx);
      _snowman.setBlockState(_snowmanxxxxxx, Blocks.STONE_BUTTON.getDefaultState().rotate(_snowman));
   }

   public static void createTestArea(String structure, BlockPos pos, BlockPos size, BlockRotation _snowman, ServerWorld world) {
      BlockBox _snowmanx = method_29409(pos, size, _snowman);
      clearArea(_snowmanx, pos.getY(), world);
      world.setBlockState(pos, Blocks.STRUCTURE_BLOCK.getDefaultState());
      StructureBlockBlockEntity _snowmanxx = (StructureBlockBlockEntity)world.getBlockEntity(pos);
      _snowmanxx.setIgnoreEntities(false);
      _snowmanxx.setStructureName(new Identifier(structure));
      _snowmanxx.setSize(size);
      _snowmanxx.setMode(StructureBlockMode.SAVE);
      _snowmanxx.setShowBoundingBox(true);
   }

   public static StructureBlockBlockEntity method_22250(String _snowman, BlockPos _snowman, BlockRotation _snowman, int _snowman, ServerWorld _snowman, boolean _snowman) {
      BlockPos _snowmanxxxxxx = createStructure(_snowman, _snowman).getSize();
      BlockBox _snowmanxxxxxxx = method_29409(_snowman, _snowmanxxxxxx, _snowman);
      BlockPos _snowmanxxxxxxxx;
      if (_snowman == BlockRotation.NONE) {
         _snowmanxxxxxxxx = _snowman;
      } else if (_snowman == BlockRotation.CLOCKWISE_90) {
         _snowmanxxxxxxxx = _snowman.add(_snowmanxxxxxx.getZ() - 1, 0, 0);
      } else if (_snowman == BlockRotation.CLOCKWISE_180) {
         _snowmanxxxxxxxx = _snowman.add(_snowmanxxxxxx.getX() - 1, 0, _snowmanxxxxxx.getZ() - 1);
      } else {
         if (_snowman != BlockRotation.COUNTERCLOCKWISE_90) {
            throw new IllegalArgumentException("Invalid rotation: " + _snowman);
         }

         _snowmanxxxxxxxx = _snowman.add(0, 0, _snowmanxxxxxx.getX() - 1);
      }

      forceLoadNearbyChunks(_snowman, _snowman);
      clearArea(_snowmanxxxxxxx, _snowman.getY(), _snowman);
      StructureBlockBlockEntity _snowmanxxxxxxxxx = placeStructure(_snowman, _snowmanxxxxxxxx, _snowman, _snowman, _snowman);
      _snowman.getBlockTickScheduler().getScheduledTicks(_snowmanxxxxxxx, true, false);
      _snowman.clearUpdatesInArea(_snowmanxxxxxxx);
      return _snowmanxxxxxxxxx;
   }

   private static void forceLoadNearbyChunks(BlockPos pos, ServerWorld world) {
      ChunkPos _snowman = new ChunkPos(pos);

      for (int _snowmanx = -1; _snowmanx < 4; _snowmanx++) {
         for (int _snowmanxx = -1; _snowmanxx < 4; _snowmanxx++) {
            int _snowmanxxx = _snowman.x + _snowmanx;
            int _snowmanxxxx = _snowman.z + _snowmanxx;
            world.setChunkForced(_snowmanxxx, _snowmanxxxx, true);
         }
      }
   }

   public static void clearArea(BlockBox area, int _snowman, ServerWorld world) {
      BlockBox _snowmanx = new BlockBox(area.minX - 2, area.minY - 3, area.minZ - 3, area.maxX + 3, area.maxY + 20, area.maxZ + 3);
      BlockPos.stream(_snowmanx).forEach(_snowmanxx -> method_22368(_snowman, _snowmanxx, world));
      world.getBlockTickScheduler().getScheduledTicks(_snowmanx, true, false);
      world.clearUpdatesInArea(_snowmanx);
      Box _snowmanxx = new Box((double)_snowmanx.minX, (double)_snowmanx.minY, (double)_snowmanx.minZ, (double)_snowmanx.maxX, (double)_snowmanx.maxY, (double)_snowmanx.maxZ);
      List<Entity> _snowmanxxx = world.getEntitiesByClass(Entity.class, _snowmanxx, _snowmanxxxx -> !(_snowmanxxxx instanceof PlayerEntity));
      _snowmanxxx.forEach(Entity::remove);
   }

   public static BlockBox method_29409(BlockPos _snowman, BlockPos _snowman, BlockRotation _snowman) {
      BlockPos _snowmanxxx = _snowman.add(_snowman).add(-1, -1, -1);
      BlockPos _snowmanxxxx = Structure.transformAround(_snowmanxxx, BlockMirror.NONE, _snowman, _snowman);
      BlockBox _snowmanxxxxx = BlockBox.create(_snowman.getX(), _snowman.getY(), _snowman.getZ(), _snowmanxxxx.getX(), _snowmanxxxx.getY(), _snowmanxxxx.getZ());
      int _snowmanxxxxxx = Math.min(_snowmanxxxxx.minX, _snowmanxxxxx.maxX);
      int _snowmanxxxxxxx = Math.min(_snowmanxxxxx.minZ, _snowmanxxxxx.maxZ);
      BlockPos _snowmanxxxxxxxx = new BlockPos(_snowman.getX() - _snowmanxxxxxx, 0, _snowman.getZ() - _snowmanxxxxxxx);
      _snowmanxxxxx.move(_snowmanxxxxxxxx);
      return _snowmanxxxxx;
   }

   public static Optional<BlockPos> findContainingStructureBlock(BlockPos pos, int radius, ServerWorld world) {
      return findStructureBlocks(pos, radius, world).stream().filter(_snowmanxx -> isInStructureBounds(_snowmanxx, pos, world)).findFirst();
   }

   @Nullable
   public static BlockPos findNearestStructureBlock(BlockPos pos, int radius, ServerWorld world) {
      Comparator<BlockPos> _snowman = Comparator.comparingInt(_snowmanx -> _snowmanx.getManhattanDistance(pos));
      Collection<BlockPos> _snowmanx = findStructureBlocks(pos, radius, world);
      Optional<BlockPos> _snowmanxx = _snowmanx.stream().min(_snowman);
      return _snowmanxx.orElse(null);
   }

   public static Collection<BlockPos> findStructureBlocks(BlockPos pos, int radius, ServerWorld world) {
      Collection<BlockPos> _snowman = Lists.newArrayList();
      Box _snowmanx = new Box(pos);
      _snowmanx = _snowmanx.expand((double)radius);

      for (int _snowmanxx = (int)_snowmanx.minX; _snowmanxx <= (int)_snowmanx.maxX; _snowmanxx++) {
         for (int _snowmanxxx = (int)_snowmanx.minY; _snowmanxxx <= (int)_snowmanx.maxY; _snowmanxxx++) {
            for (int _snowmanxxxx = (int)_snowmanx.minZ; _snowmanxxxx <= (int)_snowmanx.maxZ; _snowmanxxxx++) {
               BlockPos _snowmanxxxxx = new BlockPos(_snowmanxx, _snowmanxxx, _snowmanxxxx);
               BlockState _snowmanxxxxxx = world.getBlockState(_snowmanxxxxx);
               if (_snowmanxxxxxx.isOf(Blocks.STRUCTURE_BLOCK)) {
                  _snowman.add(_snowmanxxxxx);
               }
            }
         }
      }

      return _snowman;
   }

   private static Structure createStructure(String structureId, ServerWorld world) {
      StructureManager _snowman = world.getStructureManager();
      Structure _snowmanx = _snowman.getStructure(new Identifier(structureId));
      if (_snowmanx != null) {
         return _snowmanx;
      } else {
         String _snowmanxx = structureId + ".snbt";
         Path _snowmanxxx = Paths.get(testStructuresDirectoryName, _snowmanxx);
         CompoundTag _snowmanxxxx = loadSnbt(_snowmanxxx);
         if (_snowmanxxxx == null) {
            throw new RuntimeException("Could not find structure file " + _snowmanxxx + ", and the structure is not available in the world structures either.");
         } else {
            return _snowman.createStructure(_snowmanxxxx);
         }
      }
   }

   private static StructureBlockBlockEntity placeStructure(String name, BlockPos pos, BlockRotation _snowman, ServerWorld _snowman, boolean _snowman) {
      _snowman.setBlockState(pos, Blocks.STRUCTURE_BLOCK.getDefaultState());
      StructureBlockBlockEntity _snowmanxxx = (StructureBlockBlockEntity)_snowman.getBlockEntity(pos);
      _snowmanxxx.setMode(StructureBlockMode.LOAD);
      _snowmanxxx.setRotation(_snowman);
      _snowmanxxx.setIgnoreEntities(false);
      _snowmanxxx.setStructureName(new Identifier(name));
      _snowmanxxx.loadStructure(_snowman, _snowman);
      if (_snowmanxxx.getSize() != BlockPos.ORIGIN) {
         return _snowmanxxx;
      } else {
         Structure _snowmanxxxx = createStructure(name, _snowman);
         _snowmanxxx.place(_snowman, _snowman, _snowmanxxxx);
         if (_snowmanxxx.getSize() == BlockPos.ORIGIN) {
            throw new RuntimeException("Failed to load structure " + name);
         } else {
            return _snowmanxxx;
         }
      }
   }

   @Nullable
   private static CompoundTag loadSnbt(Path path) {
      try {
         BufferedReader _snowman = Files.newBufferedReader(path);
         String _snowmanx = IOUtils.toString(_snowman);
         return StringNbtReader.parse(_snowmanx);
      } catch (IOException var3) {
         return null;
      } catch (CommandSyntaxException var4) {
         throw new RuntimeException("Error while trying to load structure " + path, var4);
      }
   }

   private static void method_22368(int altitude, BlockPos pos, ServerWorld world) {
      BlockState _snowman = null;
      FlatChunkGeneratorConfig _snowmanx = FlatChunkGeneratorConfig.getDefaultConfig(world.getRegistryManager().get(Registry.BIOME_KEY));
      if (_snowmanx instanceof FlatChunkGeneratorConfig) {
         BlockState[] _snowmanxx = _snowmanx.getLayerBlocks();
         if (pos.getY() < altitude && pos.getY() <= _snowmanxx.length) {
            _snowman = _snowmanxx[pos.getY() - 1];
         }
      } else if (pos.getY() == altitude - 1) {
         _snowman = world.getBiome(pos).getGenerationSettings().getSurfaceConfig().getTopMaterial();
      } else if (pos.getY() < altitude - 1) {
         _snowman = world.getBiome(pos).getGenerationSettings().getSurfaceConfig().getUnderMaterial();
      }

      if (_snowman == null) {
         _snowman = Blocks.AIR.getDefaultState();
      }

      BlockStateArgument _snowmanxx = new BlockStateArgument(_snowman, Collections.emptySet(), null);
      _snowmanxx.setBlockState(world, pos, 2);
      world.updateNeighbors(pos, _snowman.getBlock());
   }

   private static boolean isInStructureBounds(BlockPos structureBlockPos, BlockPos pos, ServerWorld world) {
      StructureBlockBlockEntity _snowman = (StructureBlockBlockEntity)world.getBlockEntity(structureBlockPos);
      Box _snowmanx = getStructureBoundingBox(_snowman).expand(1.0);
      return _snowmanx.contains(Vec3d.ofCenter(pos));
   }
}
