package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.block.BlockState;
import net.minecraft.structure.RuinedPortalStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class RuinedPortalFeature extends StructureFeature<RuinedPortalFeatureConfig> {
   private static final String[] COMMON_PORTAL_STRUCTURE_IDS = new String[]{
      "ruined_portal/portal_1",
      "ruined_portal/portal_2",
      "ruined_portal/portal_3",
      "ruined_portal/portal_4",
      "ruined_portal/portal_5",
      "ruined_portal/portal_6",
      "ruined_portal/portal_7",
      "ruined_portal/portal_8",
      "ruined_portal/portal_9",
      "ruined_portal/portal_10"
   };
   private static final String[] RARE_PORTAL_STRUCTURE_IDS = new String[]{
      "ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"
   };

   public RuinedPortalFeature(Codec<RuinedPortalFeatureConfig> codec) {
      super(codec);
   }

   @Override
   public StructureFeature.StructureStartFactory<RuinedPortalFeatureConfig> getStructureStartFactory() {
      return RuinedPortalFeature.Start::new;
   }

   private static boolean method_27209(BlockPos arg, Biome arg2) {
      return arg2.getTemperature(arg) < 0.15F;
   }

   private static int method_27211(
      Random random, ChunkGenerator chunkGenerator, RuinedPortalStructurePiece.VerticalPlacement verticalPlacement, boolean bl, int i, int j, BlockBox arg3
   ) {
      int k;
      if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.IN_NETHER) {
         if (bl) {
            k = choose(random, 32, 100);
         } else if (random.nextFloat() < 0.5F) {
            k = choose(random, 27, 29);
         } else {
            k = choose(random, 29, 100);
         }
      } else if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.IN_MOUNTAIN) {
         int n = i - j;
         k = choosePlacementHeight(random, 70, n);
      } else if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.UNDERGROUND) {
         int p = i - j;
         k = choosePlacementHeight(random, 15, p);
      } else if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.PARTLY_BURIED) {
         k = i - j + choose(random, 2, 8);
      } else {
         k = i;
      }

      List<BlockPos> list = ImmutableList.of(
         new BlockPos(arg3.minX, 0, arg3.minZ),
         new BlockPos(arg3.maxX, 0, arg3.minZ),
         new BlockPos(arg3.minX, 0, arg3.maxZ),
         new BlockPos(arg3.maxX, 0, arg3.maxZ)
      );
      List<BlockView> list2 = list.stream().map(arg2 -> chunkGenerator.getColumnSample(arg2.getX(), arg2.getZ())).collect(Collectors.toList());
      Heightmap.Type lv = verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR
         ? Heightmap.Type.OCEAN_FLOOR_WG
         : Heightmap.Type.WORLD_SURFACE_WG;
      BlockPos.Mutable lv2 = new BlockPos.Mutable();

      int t;
      for (t = k; t > 15; t--) {
         int u = 0;
         lv2.set(0, t, 0);

         for (BlockView lv3 : list2) {
            BlockState lv4 = lv3.getBlockState(lv2);
            if (lv4 != null && lv.getBlockPredicate().test(lv4)) {
               if (++u == 3) {
                  return t;
               }
            }
         }
      }

      return t;
   }

   private static int choose(Random random, int min, int max) {
      return random.nextInt(max - min + 1) + min;
   }

   private static int choosePlacementHeight(Random random, int min, int max) {
      return min < max ? choose(random, min, max) : max;
   }

   public static class Start extends StructureStart<RuinedPortalFeatureConfig> {
      protected Start(StructureFeature<RuinedPortalFeatureConfig> arg, int i, int j, BlockBox arg2, int k, long l) {
         super(arg, i, j, arg2, k, l);
      }

      public void init(DynamicRegistryManager arg, ChunkGenerator arg2, StructureManager arg3, int i, int j, Biome arg4, RuinedPortalFeatureConfig arg5) {
         RuinedPortalStructurePiece.Properties lv = new RuinedPortalStructurePiece.Properties();
         RuinedPortalStructurePiece.VerticalPlacement lv2;
         if (arg5.portalType == RuinedPortalFeature.Type.DESERT) {
            lv2 = RuinedPortalStructurePiece.VerticalPlacement.PARTLY_BURIED;
            lv.airPocket = false;
            lv.mossiness = 0.0F;
         } else if (arg5.portalType == RuinedPortalFeature.Type.JUNGLE) {
            lv2 = RuinedPortalStructurePiece.VerticalPlacement.ON_LAND_SURFACE;
            lv.airPocket = this.random.nextFloat() < 0.5F;
            lv.mossiness = 0.8F;
            lv.overgrown = true;
            lv.vines = true;
         } else if (arg5.portalType == RuinedPortalFeature.Type.SWAMP) {
            lv2 = RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR;
            lv.airPocket = false;
            lv.mossiness = 0.5F;
            lv.vines = true;
         } else if (arg5.portalType == RuinedPortalFeature.Type.MOUNTAIN) {
            boolean bl = this.random.nextFloat() < 0.5F;
            lv2 = bl ? RuinedPortalStructurePiece.VerticalPlacement.IN_MOUNTAIN : RuinedPortalStructurePiece.VerticalPlacement.ON_LAND_SURFACE;
            lv.airPocket = bl || this.random.nextFloat() < 0.5F;
         } else if (arg5.portalType == RuinedPortalFeature.Type.OCEAN) {
            lv2 = RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR;
            lv.airPocket = false;
            lv.mossiness = 0.8F;
         } else if (arg5.portalType == RuinedPortalFeature.Type.NETHER) {
            lv2 = RuinedPortalStructurePiece.VerticalPlacement.IN_NETHER;
            lv.airPocket = this.random.nextFloat() < 0.5F;
            lv.mossiness = 0.0F;
            lv.replaceWithBlackstone = true;
         } else {
            boolean bl2 = this.random.nextFloat() < 0.5F;
            lv2 = bl2 ? RuinedPortalStructurePiece.VerticalPlacement.UNDERGROUND : RuinedPortalStructurePiece.VerticalPlacement.ON_LAND_SURFACE;
            lv.airPocket = bl2 || this.random.nextFloat() < 0.5F;
         }

         Identifier lv9;
         if (this.random.nextFloat() < 0.05F) {
            lv9 = new Identifier(RuinedPortalFeature.RARE_PORTAL_STRUCTURE_IDS[this.random.nextInt(RuinedPortalFeature.RARE_PORTAL_STRUCTURE_IDS.length)]);
         } else {
            lv9 = new Identifier(RuinedPortalFeature.COMMON_PORTAL_STRUCTURE_IDS[this.random.nextInt(RuinedPortalFeature.COMMON_PORTAL_STRUCTURE_IDS.length)]);
         }

         Structure lv11 = arg3.getStructureOrBlank(lv9);
         BlockRotation lv12 = Util.getRandom(BlockRotation.values(), this.random);
         BlockMirror lv13 = this.random.nextFloat() < 0.5F ? BlockMirror.NONE : BlockMirror.FRONT_BACK;
         BlockPos lv14 = new BlockPos(lv11.getSize().getX() / 2, 0, lv11.getSize().getZ() / 2);
         BlockPos lv15 = new ChunkPos(i, j).getStartPos();
         BlockBox lv16 = lv11.method_27267(lv15, lv12, lv14, lv13);
         Vec3i lv17 = lv16.getCenter();
         int k = lv17.getX();
         int l = lv17.getZ();
         int m = arg2.getHeight(k, l, RuinedPortalStructurePiece.getHeightmapType(lv2)) - 1;
         int n = RuinedPortalFeature.method_27211(this.random, arg2, lv2, lv.airPocket, m, lv16.getBlockCountY(), lv16);
         BlockPos lv18 = new BlockPos(lv15.getX(), n, lv15.getZ());
         if (arg5.portalType == RuinedPortalFeature.Type.MOUNTAIN
            || arg5.portalType == RuinedPortalFeature.Type.OCEAN
            || arg5.portalType == RuinedPortalFeature.Type.STANDARD) {
            lv.cold = RuinedPortalFeature.method_27209(lv18, arg4);
         }

         this.children.add(new RuinedPortalStructurePiece(lv18, lv2, lv, lv9, lv11, lv12, lv13, lv14));
         this.setBoundingBoxFromChildren();
      }
   }

   public static enum Type implements StringIdentifiable {
      STANDARD("standard"),
      DESERT("desert"),
      JUNGLE("jungle"),
      SWAMP("swamp"),
      MOUNTAIN("mountain"),
      OCEAN("ocean"),
      NETHER("nether");

      public static final Codec<RuinedPortalFeature.Type> CODEC = StringIdentifiable.createCodec(
         RuinedPortalFeature.Type::values, RuinedPortalFeature.Type::byName
      );
      private static final Map<String, RuinedPortalFeature.Type> BY_NAME = Arrays.stream(values())
         .collect(Collectors.toMap(RuinedPortalFeature.Type::getName, arg -> (RuinedPortalFeature.Type)arg));
      private final String name;

      private Type(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public static RuinedPortalFeature.Type byName(String name) {
         return BY_NAME.get(name);
      }

      @Override
      public String asString() {
         return this.name;
      }
   }
}
