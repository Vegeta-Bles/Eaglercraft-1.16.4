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

   public RuinedPortalFeature(Codec<RuinedPortalFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   public StructureFeature.StructureStartFactory<RuinedPortalFeatureConfig> getStructureStartFactory() {
      return RuinedPortalFeature.Start::new;
   }

   private static boolean method_27209(BlockPos _snowman, Biome _snowman) {
      return _snowman.getTemperature(_snowman) < 0.15F;
   }

   private static int method_27211(
      Random random, ChunkGenerator chunkGenerator, RuinedPortalStructurePiece.VerticalPlacement verticalPlacement, boolean _snowman, int _snowman, int _snowman, BlockBox _snowman
   ) {
      int _snowmanxxxx;
      if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.IN_NETHER) {
         if (_snowman) {
            _snowmanxxxx = choose(random, 32, 100);
         } else if (random.nextFloat() < 0.5F) {
            _snowmanxxxx = choose(random, 27, 29);
         } else {
            _snowmanxxxx = choose(random, 29, 100);
         }
      } else if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.IN_MOUNTAIN) {
         int _snowmanxxxxx = _snowman - _snowman;
         _snowmanxxxx = choosePlacementHeight(random, 70, _snowmanxxxxx);
      } else if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.UNDERGROUND) {
         int _snowmanxxxxx = _snowman - _snowman;
         _snowmanxxxx = choosePlacementHeight(random, 15, _snowmanxxxxx);
      } else if (verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.PARTLY_BURIED) {
         _snowmanxxxx = _snowman - _snowman + choose(random, 2, 8);
      } else {
         _snowmanxxxx = _snowman;
      }

      List<BlockPos> _snowmanxxxxx = ImmutableList.of(
         new BlockPos(_snowman.minX, 0, _snowman.minZ), new BlockPos(_snowman.maxX, 0, _snowman.minZ), new BlockPos(_snowman.minX, 0, _snowman.maxZ), new BlockPos(_snowman.maxX, 0, _snowman.maxZ)
      );
      List<BlockView> _snowmanxxxxxx = _snowmanxxxxx.stream().map(_snowmanxxxxxxx -> chunkGenerator.getColumnSample(_snowmanxxxxxxx.getX(), _snowmanxxxxxxx.getZ())).collect(Collectors.toList());
      Heightmap.Type _snowmanxxxxxxx = verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR
         ? Heightmap.Type.OCEAN_FLOOR_WG
         : Heightmap.Type.WORLD_SURFACE_WG;
      BlockPos.Mutable _snowmanxxxxxxxx = new BlockPos.Mutable();

      int _snowmanxxxxxxxxx;
      for (_snowmanxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxx > 15; _snowmanxxxxxxxxx--) {
         int _snowmanxxxxxxxxxx = 0;
         _snowmanxxxxxxxx.set(0, _snowmanxxxxxxxxx, 0);

         for (BlockView _snowmanxxxxxxxxxxx : _snowmanxxxxxx) {
            BlockState _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getBlockState(_snowmanxxxxxxxx);
            if (_snowmanxxxxxxxxxxxx != null && _snowmanxxxxxxx.getBlockPredicate().test(_snowmanxxxxxxxxxxxx)) {
               if (++_snowmanxxxxxxxxxx == 3) {
                  return _snowmanxxxxxxxxx;
               }
            }
         }
      }

      return _snowmanxxxxxxxxx;
   }

   private static int choose(Random random, int min, int max) {
      return random.nextInt(max - min + 1) + min;
   }

   private static int choosePlacementHeight(Random _snowman, int min, int max) {
      return min < max ? choose(_snowman, min, max) : max;
   }

   public static class Start extends StructureStart<RuinedPortalFeatureConfig> {
      protected Start(StructureFeature<RuinedPortalFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, RuinedPortalFeatureConfig _snowman) {
         RuinedPortalStructurePiece.Properties _snowmanxxxxxxx = new RuinedPortalStructurePiece.Properties();
         RuinedPortalStructurePiece.VerticalPlacement _snowmanxxxxxxxx;
         if (_snowman.portalType == RuinedPortalFeature.Type.DESERT) {
            _snowmanxxxxxxxx = RuinedPortalStructurePiece.VerticalPlacement.PARTLY_BURIED;
            _snowmanxxxxxxx.airPocket = false;
            _snowmanxxxxxxx.mossiness = 0.0F;
         } else if (_snowman.portalType == RuinedPortalFeature.Type.JUNGLE) {
            _snowmanxxxxxxxx = RuinedPortalStructurePiece.VerticalPlacement.ON_LAND_SURFACE;
            _snowmanxxxxxxx.airPocket = this.random.nextFloat() < 0.5F;
            _snowmanxxxxxxx.mossiness = 0.8F;
            _snowmanxxxxxxx.overgrown = true;
            _snowmanxxxxxxx.vines = true;
         } else if (_snowman.portalType == RuinedPortalFeature.Type.SWAMP) {
            _snowmanxxxxxxxx = RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR;
            _snowmanxxxxxxx.airPocket = false;
            _snowmanxxxxxxx.mossiness = 0.5F;
            _snowmanxxxxxxx.vines = true;
         } else if (_snowman.portalType == RuinedPortalFeature.Type.MOUNTAIN) {
            boolean _snowmanxxxxxxxxx = this.random.nextFloat() < 0.5F;
            _snowmanxxxxxxxx = _snowmanxxxxxxxxx ? RuinedPortalStructurePiece.VerticalPlacement.IN_MOUNTAIN : RuinedPortalStructurePiece.VerticalPlacement.ON_LAND_SURFACE;
            _snowmanxxxxxxx.airPocket = _snowmanxxxxxxxxx || this.random.nextFloat() < 0.5F;
         } else if (_snowman.portalType == RuinedPortalFeature.Type.OCEAN) {
            _snowmanxxxxxxxx = RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR;
            _snowmanxxxxxxx.airPocket = false;
            _snowmanxxxxxxx.mossiness = 0.8F;
         } else if (_snowman.portalType == RuinedPortalFeature.Type.NETHER) {
            _snowmanxxxxxxxx = RuinedPortalStructurePiece.VerticalPlacement.IN_NETHER;
            _snowmanxxxxxxx.airPocket = this.random.nextFloat() < 0.5F;
            _snowmanxxxxxxx.mossiness = 0.0F;
            _snowmanxxxxxxx.replaceWithBlackstone = true;
         } else {
            boolean _snowmanxxxxxxxxx = this.random.nextFloat() < 0.5F;
            _snowmanxxxxxxxx = _snowmanxxxxxxxxx ? RuinedPortalStructurePiece.VerticalPlacement.UNDERGROUND : RuinedPortalStructurePiece.VerticalPlacement.ON_LAND_SURFACE;
            _snowmanxxxxxxx.airPocket = _snowmanxxxxxxxxx || this.random.nextFloat() < 0.5F;
         }

         Identifier _snowmanxxxxxxxxx;
         if (this.random.nextFloat() < 0.05F) {
            _snowmanxxxxxxxxx = new Identifier(
               RuinedPortalFeature.RARE_PORTAL_STRUCTURE_IDS[this.random.nextInt(RuinedPortalFeature.RARE_PORTAL_STRUCTURE_IDS.length)]
            );
         } else {
            _snowmanxxxxxxxxx = new Identifier(
               RuinedPortalFeature.COMMON_PORTAL_STRUCTURE_IDS[this.random.nextInt(RuinedPortalFeature.COMMON_PORTAL_STRUCTURE_IDS.length)]
            );
         }

         Structure _snowmanxxxxxxxxxx = _snowman.getStructureOrBlank(_snowmanxxxxxxxxx);
         BlockRotation _snowmanxxxxxxxxxxx = Util.getRandom(BlockRotation.values(), this.random);
         BlockMirror _snowmanxxxxxxxxxxxx = this.random.nextFloat() < 0.5F ? BlockMirror.NONE : BlockMirror.FRONT_BACK;
         BlockPos _snowmanxxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxxxx.getSize().getX() / 2, 0, _snowmanxxxxxxxxxx.getSize().getZ() / 2);
         BlockPos _snowmanxxxxxxxxxxxxxx = new ChunkPos(_snowman, _snowman).getStartPos();
         BlockBox _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.method_27267(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
         Vec3i _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.getCenter();
         int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.getX();
         int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.getZ();
         int _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.getHeight(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, RuinedPortalStructurePiece.getHeightmapType(_snowmanxxxxxxxx)) - 1;
         int _snowmanxxxxxxxxxxxxxxxxxxxx = RuinedPortalFeature.method_27211(
            this.random, _snowman, _snowmanxxxxxxxx, _snowmanxxxxxxx.airPocket, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx.getBlockCountY(), _snowmanxxxxxxxxxxxxxxx
         );
         BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxxxxxxxx.getX(), _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx.getZ());
         if (_snowman.portalType == RuinedPortalFeature.Type.MOUNTAIN
            || _snowman.portalType == RuinedPortalFeature.Type.OCEAN
            || _snowman.portalType == RuinedPortalFeature.Type.STANDARD) {
            _snowmanxxxxxxx.cold = RuinedPortalFeature.method_27209(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowman);
         }

         this.children
            .add(
               new RuinedPortalStructurePiece(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx)
            );
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
         .collect(Collectors.toMap(RuinedPortalFeature.Type::getName, _snowman -> (RuinedPortalFeature.Type)_snowman));
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
