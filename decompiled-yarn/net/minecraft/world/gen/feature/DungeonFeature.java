package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DungeonFeature extends Feature<DefaultFeatureConfig> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final EntityType<?>[] MOB_SPAWNER_ENTITIES = new EntityType[]{EntityType.SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.SPIDER};
   private static final BlockState AIR = Blocks.CAVE_AIR.getDefaultState();

   public DungeonFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      int _snowmanxxxxx = 3;
      int _snowmanxxxxxx = _snowman.nextInt(2) + 2;
      int _snowmanxxxxxxx = -_snowmanxxxxxx - 1;
      int _snowmanxxxxxxxx = _snowmanxxxxxx + 1;
      int _snowmanxxxxxxxxx = -1;
      int _snowmanxxxxxxxxxx = 4;
      int _snowmanxxxxxxxxxxx = _snowman.nextInt(2) + 2;
      int _snowmanxxxxxxxxxxxx = -_snowmanxxxxxxxxxxx - 1;
      int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx + 1;
      int _snowmanxxxxxxxxxxxxxx = 0;

      for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxxxx <= _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxxx = -1; _snowmanxxxxxxxxxxxxxxxx <= 4; _snowmanxxxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
               BlockPos _snowmanxxxxxxxxxxxxxxxxxx = _snowman.add(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
               Material _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxx).getMaterial();
               boolean _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.isSolid();
               if (_snowmanxxxxxxxxxxxxxxxx == -1 && !_snowmanxxxxxxxxxxxxxxxxxxxx) {
                  return false;
               }

               if (_snowmanxxxxxxxxxxxxxxxx == 4 && !_snowmanxxxxxxxxxxxxxxxxxxxx) {
                  return false;
               }

               if ((
                     _snowmanxxxxxxxxxxxxxxx == _snowmanxxxxxxx
                        || _snowmanxxxxxxxxxxxxxxx == _snowmanxxxxxxxx
                        || _snowmanxxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxx
                        || _snowmanxxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxx
                  )
                  && _snowmanxxxxxxxxxxxxxxxx == 0
                  && _snowman.isAir(_snowmanxxxxxxxxxxxxxxxxxx)
                  && _snowman.isAir(_snowmanxxxxxxxxxxxxxxxxxx.up())) {
                  _snowmanxxxxxxxxxxxxxx++;
               }
            }
         }
      }

      if (_snowmanxxxxxxxxxxxxxx >= 1 && _snowmanxxxxxxxxxxxxxx <= 5) {
         for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxxxx <= _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxxx = 3; _snowmanxxxxxxxxxxxxxxxx >= -1; _snowmanxxxxxxxxxxxxxxxx--) {
               for (int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
                  BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.add(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
                  BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxxxx != _snowmanxxxxxxx
                     && _snowmanxxxxxxxxxxxxxxxx != -1
                     && _snowmanxxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxxx
                     && _snowmanxxxxxxxxxxxxxxx != _snowmanxxxxxxxx
                     && _snowmanxxxxxxxxxxxxxxxx != 4
                     && _snowmanxxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxx) {
                     if (!_snowmanxxxxxxxxxxxxxxxxxxxxxx.isOf(Blocks.CHEST) && !_snowmanxxxxxxxxxxxxxxxxxxxxxx.isOf(Blocks.SPAWNER)) {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx, AIR, 2);
                     }
                  } else if (_snowmanxxxxxxxxxxxxxxxxxxxxx.getY() >= 0 && !_snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx.down()).getMaterial().isSolid()) {
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx, AIR, 2);
                  } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxx.getMaterial().isSolid() && !_snowmanxxxxxxxxxxxxxxxxxxxxxx.isOf(Blocks.CHEST)) {
                     if (_snowmanxxxxxxxxxxxxxxxx == -1 && _snowman.nextInt(4) != 0) {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
                     } else {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx, Blocks.COBBLESTONE.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.getX() + _snowman.nextInt(_snowmanxxxxxx * 2 + 1) - _snowmanxxxxxx;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getY();
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getZ() + _snowman.nextInt(_snowmanxxxxxxxxxxx * 2 + 1) - _snowmanxxxxxxxxxxx;
               BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
               if (_snowman.isAir(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx)) {
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = 0;

                  for (Direction _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx : Direction.Type.HORIZONTAL) {
                     if (_snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.offset(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx)).getMaterial().isSolid()) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx++;
                     }
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx == 1) {
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, StructurePiece.orientateChest(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, Blocks.CHEST.getDefaultState()), 2);
                     LootableContainerBlockEntity.setLootTable(_snowman, _snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, LootTables.SIMPLE_DUNGEON_CHEST);
                     break;
                  }
               }
            }
         }

         _snowman.setBlockState(_snowman, Blocks.SPAWNER.getDefaultState(), 2);
         BlockEntity _snowmanxxxxxxxxxxxxxxx = _snowman.getBlockEntity(_snowman);
         if (_snowmanxxxxxxxxxxxxxxx instanceof MobSpawnerBlockEntity) {
            ((MobSpawnerBlockEntity)_snowmanxxxxxxxxxxxxxxx).getLogic().setEntityId(this.getMobSpawnerEntity(_snowman));
         } else {
            LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", _snowman.getX(), _snowman.getY(), _snowman.getZ());
         }

         return true;
      } else {
         return false;
      }
   }

   private EntityType<?> getMobSpawnerEntity(Random random) {
      return Util.getRandom(MOB_SPAWNER_ENTITIES, random);
   }
}
