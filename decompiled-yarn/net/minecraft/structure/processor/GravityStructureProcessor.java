package net.minecraft.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldView;

public class GravityStructureProcessor extends StructureProcessor {
   public static final Codec<GravityStructureProcessor> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Heightmap.Type.CODEC.fieldOf("heightmap").orElse(Heightmap.Type.WORLD_SURFACE_WG).forGetter(_snowmanx -> _snowmanx.heightmap),
               Codec.INT.fieldOf("offset").orElse(0).forGetter(_snowmanx -> _snowmanx.offset)
            )
            .apply(_snowman, GravityStructureProcessor::new)
   );
   private final Heightmap.Type heightmap;
   private final int offset;

   public GravityStructureProcessor(Heightmap.Type heightmap, int offset) {
      this.heightmap = heightmap;
      this.offset = offset;
   }

   @Nullable
   @Override
   public Structure.StructureBlockInfo process(
      WorldView _snowman, BlockPos pos, BlockPos _snowman, Structure.StructureBlockInfo _snowman, Structure.StructureBlockInfo _snowman, StructurePlacementData _snowman
   ) {
      Heightmap.Type _snowmanxxxxx;
      if (_snowman instanceof ServerWorld) {
         if (this.heightmap == Heightmap.Type.WORLD_SURFACE_WG) {
            _snowmanxxxxx = Heightmap.Type.WORLD_SURFACE;
         } else if (this.heightmap == Heightmap.Type.OCEAN_FLOOR_WG) {
            _snowmanxxxxx = Heightmap.Type.OCEAN_FLOOR;
         } else {
            _snowmanxxxxx = this.heightmap;
         }
      } else {
         _snowmanxxxxx = this.heightmap;
      }

      int _snowmanxxxxxx = _snowman.getTopY(_snowmanxxxxx, _snowman.pos.getX(), _snowman.pos.getZ()) + this.offset;
      int _snowmanxxxxxxx = _snowman.pos.getY();
      return new Structure.StructureBlockInfo(new BlockPos(_snowman.pos.getX(), _snowmanxxxxxx + _snowmanxxxxxxx, _snowman.pos.getZ()), _snowman.state, _snowman.tag);
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.GRAVITY;
   }
}
