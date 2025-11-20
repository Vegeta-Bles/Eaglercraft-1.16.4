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
      instance -> instance.group(
               Heightmap.Type.CODEC.fieldOf("heightmap").orElse(Heightmap.Type.WORLD_SURFACE_WG).forGetter(arg -> arg.heightmap),
               Codec.INT.fieldOf("offset").orElse(0).forGetter(arg -> arg.offset)
            )
            .apply(instance, GravityStructureProcessor::new)
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
      WorldView arg, BlockPos pos, BlockPos arg3, Structure.StructureBlockInfo arg4, Structure.StructureBlockInfo arg5, StructurePlacementData arg6
   ) {
      Heightmap.Type lv;
      if (arg instanceof ServerWorld) {
         if (this.heightmap == Heightmap.Type.WORLD_SURFACE_WG) {
            lv = Heightmap.Type.WORLD_SURFACE;
         } else if (this.heightmap == Heightmap.Type.OCEAN_FLOOR_WG) {
            lv = Heightmap.Type.OCEAN_FLOOR;
         } else {
            lv = this.heightmap;
         }
      } else {
         lv = this.heightmap;
      }

      int i = arg.getTopY(lv, arg5.pos.getX(), arg5.pos.getZ()) + this.offset;
      int j = arg4.pos.getY();
      return new Structure.StructureBlockInfo(new BlockPos(arg5.pos.getX(), i + j, arg5.pos.getZ()), arg5.state, arg5.tag);
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.GRAVITY;
   }
}
