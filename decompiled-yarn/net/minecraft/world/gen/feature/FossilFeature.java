package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.BlockRotStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class FossilFeature extends Feature<DefaultFeatureConfig> {
   private static final Identifier SPINE_1 = new Identifier("fossil/spine_1");
   private static final Identifier SPINE_2 = new Identifier("fossil/spine_2");
   private static final Identifier SPINE_3 = new Identifier("fossil/spine_3");
   private static final Identifier SPINE_4 = new Identifier("fossil/spine_4");
   private static final Identifier SPINE_1_COAL = new Identifier("fossil/spine_1_coal");
   private static final Identifier SPINE_2_COAL = new Identifier("fossil/spine_2_coal");
   private static final Identifier SPINE_3_COAL = new Identifier("fossil/spine_3_coal");
   private static final Identifier SPINE_4_COAL = new Identifier("fossil/spine_4_coal");
   private static final Identifier SKULL_1 = new Identifier("fossil/skull_1");
   private static final Identifier SKULL_2 = new Identifier("fossil/skull_2");
   private static final Identifier SKULL_3 = new Identifier("fossil/skull_3");
   private static final Identifier SKULL_4 = new Identifier("fossil/skull_4");
   private static final Identifier SKULL_1_COAL = new Identifier("fossil/skull_1_coal");
   private static final Identifier SKULL_2_COAL = new Identifier("fossil/skull_2_coal");
   private static final Identifier SKULL_3_COAL = new Identifier("fossil/skull_3_coal");
   private static final Identifier SKULL_4_COAL = new Identifier("fossil/skull_4_coal");
   private static final Identifier[] FOSSILS = new Identifier[]{SPINE_1, SPINE_2, SPINE_3, SPINE_4, SKULL_1, SKULL_2, SKULL_3, SKULL_4};
   private static final Identifier[] COAL_FOSSILS = new Identifier[]{
      SPINE_1_COAL, SPINE_2_COAL, SPINE_3_COAL, SPINE_4_COAL, SKULL_1_COAL, SKULL_2_COAL, SKULL_3_COAL, SKULL_4_COAL
   };

   public FossilFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      BlockRotation _snowmanxxxxx = BlockRotation.random(_snowman);
      int _snowmanxxxxxx = _snowman.nextInt(FOSSILS.length);
      StructureManager _snowmanxxxxxxx = _snowman.toServerWorld().getServer().getStructureManager();
      Structure _snowmanxxxxxxxx = _snowmanxxxxxxx.getStructureOrBlank(FOSSILS[_snowmanxxxxxx]);
      Structure _snowmanxxxxxxxxx = _snowmanxxxxxxx.getStructureOrBlank(COAL_FOSSILS[_snowmanxxxxxx]);
      ChunkPos _snowmanxxxxxxxxxx = new ChunkPos(_snowman);
      BlockBox _snowmanxxxxxxxxxxx = new BlockBox(_snowmanxxxxxxxxxx.getStartX(), 0, _snowmanxxxxxxxxxx.getStartZ(), _snowmanxxxxxxxxxx.getEndX(), 256, _snowmanxxxxxxxxxx.getEndZ());
      StructurePlacementData _snowmanxxxxxxxxxxxx = new StructurePlacementData()
         .setRotation(_snowmanxxxxx)
         .setBoundingBox(_snowmanxxxxxxxxxxx)
         .setRandom(_snowman)
         .addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
      BlockPos _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx.getRotatedSize(_snowmanxxxxx);
      int _snowmanxxxxxxxxxxxxxx = _snowman.nextInt(16 - _snowmanxxxxxxxxxxxxx.getX());
      int _snowmanxxxxxxxxxxxxxxx = _snowman.nextInt(16 - _snowmanxxxxxxxxxxxxx.getZ());
      int _snowmanxxxxxxxxxxxxxxxx = 256;

      for (int _snowmanxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx.getX(); _snowmanxxxxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx.getZ(); _snowmanxxxxxxxxxxxxxxxxxx++) {
            _snowmanxxxxxxxxxxxxxxxx = Math.min(
               _snowmanxxxxxxxxxxxxxxxx,
               _snowman.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, _snowman.getX() + _snowmanxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx, _snowman.getZ() + _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx)
            );
         }
      }

      int _snowmanxxxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxxxxx - 15 - _snowman.nextInt(10), 10);
      BlockPos _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.offsetByTransformedSize(_snowman.add(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx), BlockMirror.NONE, _snowmanxxxxx);
      BlockRotStructureProcessor _snowmanxxxxxxxxxxxxxxxxxxx = new BlockRotStructureProcessor(0.9F);
      _snowmanxxxxxxxxxxxx.clearProcessors().addProcessor(_snowmanxxxxxxxxxxxxxxxxxxx);
      _snowmanxxxxxxxx.place(_snowman, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowman, 4);
      _snowmanxxxxxxxxxxxx.removeProcessor(_snowmanxxxxxxxxxxxxxxxxxxx);
      BlockRotStructureProcessor _snowmanxxxxxxxxxxxxxxxxxxxx = new BlockRotStructureProcessor(0.1F);
      _snowmanxxxxxxxxxxxx.clearProcessors().addProcessor(_snowmanxxxxxxxxxxxxxxxxxxxx);
      _snowmanxxxxxxxxx.place(_snowman, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowman, 4);
      return true;
   }
}
