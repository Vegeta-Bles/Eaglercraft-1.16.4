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

   public DungeonFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DefaultFeatureConfig arg4) {
      int i = 3;
      int j = random.nextInt(2) + 2;
      int k = -j - 1;
      int l = j + 1;
      int m = -1;
      int n = 4;
      int o = random.nextInt(2) + 2;
      int p = -o - 1;
      int q = o + 1;
      int r = 0;

      for (int s = k; s <= l; s++) {
         for (int t = -1; t <= 4; t++) {
            for (int u = p; u <= q; u++) {
               BlockPos lv = arg3.add(s, t, u);
               Material lv2 = arg.getBlockState(lv).getMaterial();
               boolean bl = lv2.isSolid();
               if (t == -1 && !bl) {
                  return false;
               }

               if (t == 4 && !bl) {
                  return false;
               }

               if ((s == k || s == l || u == p || u == q) && t == 0 && arg.isAir(lv) && arg.isAir(lv.up())) {
                  r++;
               }
            }
         }
      }

      if (r >= 1 && r <= 5) {
         for (int v = k; v <= l; v++) {
            for (int w = 3; w >= -1; w--) {
               for (int x = p; x <= q; x++) {
                  BlockPos lv3 = arg3.add(v, w, x);
                  BlockState lv4 = arg.getBlockState(lv3);
                  if (v != k && w != -1 && x != p && v != l && w != 4 && x != q) {
                     if (!lv4.isOf(Blocks.CHEST) && !lv4.isOf(Blocks.SPAWNER)) {
                        arg.setBlockState(lv3, AIR, 2);
                     }
                  } else if (lv3.getY() >= 0 && !arg.getBlockState(lv3.down()).getMaterial().isSolid()) {
                     arg.setBlockState(lv3, AIR, 2);
                  } else if (lv4.getMaterial().isSolid() && !lv4.isOf(Blocks.CHEST)) {
                     if (w == -1 && random.nextInt(4) != 0) {
                        arg.setBlockState(lv3, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
                     } else {
                        arg.setBlockState(lv3, Blocks.COBBLESTONE.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         for (int y = 0; y < 2; y++) {
            for (int z = 0; z < 3; z++) {
               int aa = arg3.getX() + random.nextInt(j * 2 + 1) - j;
               int ab = arg3.getY();
               int ac = arg3.getZ() + random.nextInt(o * 2 + 1) - o;
               BlockPos lv5 = new BlockPos(aa, ab, ac);
               if (arg.isAir(lv5)) {
                  int ad = 0;

                  for (Direction lv6 : Direction.Type.HORIZONTAL) {
                     if (arg.getBlockState(lv5.offset(lv6)).getMaterial().isSolid()) {
                        ad++;
                     }
                  }

                  if (ad == 1) {
                     arg.setBlockState(lv5, StructurePiece.orientateChest(arg, lv5, Blocks.CHEST.getDefaultState()), 2);
                     LootableContainerBlockEntity.setLootTable(arg, random, lv5, LootTables.SIMPLE_DUNGEON_CHEST);
                     break;
                  }
               }
            }
         }

         arg.setBlockState(arg3, Blocks.SPAWNER.getDefaultState(), 2);
         BlockEntity lv7 = arg.getBlockEntity(arg3);
         if (lv7 instanceof MobSpawnerBlockEntity) {
            ((MobSpawnerBlockEntity)lv7).getLogic().setEntityId(this.getMobSpawnerEntity(random));
         } else {
            LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", arg3.getX(), arg3.getY(), arg3.getZ());
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
