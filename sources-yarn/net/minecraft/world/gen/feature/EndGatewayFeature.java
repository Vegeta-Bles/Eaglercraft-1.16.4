package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class EndGatewayFeature extends Feature<EndGatewayFeatureConfig> {
   public EndGatewayFeature(Codec<EndGatewayFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, EndGatewayFeatureConfig arg4) {
      for (BlockPos lv : BlockPos.iterate(arg3.add(-1, -2, -1), arg3.add(1, 2, 1))) {
         boolean bl = lv.getX() == arg3.getX();
         boolean bl2 = lv.getY() == arg3.getY();
         boolean bl3 = lv.getZ() == arg3.getZ();
         boolean bl4 = Math.abs(lv.getY() - arg3.getY()) == 2;
         if (bl && bl2 && bl3) {
            BlockPos lv2 = lv.toImmutable();
            this.setBlockState(arg, lv2, Blocks.END_GATEWAY.getDefaultState());
            arg4.getExitPos().ifPresent(arg4x -> {
               BlockEntity lvx = arg.getBlockEntity(lv2);
               if (lvx instanceof EndGatewayBlockEntity) {
                  EndGatewayBlockEntity lv2x = (EndGatewayBlockEntity)lvx;
                  lv2x.setExitPortalPos(arg4x, arg4.isExact());
                  lvx.markDirty();
               }
            });
         } else if (bl2) {
            this.setBlockState(arg, lv, Blocks.AIR.getDefaultState());
         } else if (bl4 && bl && bl3) {
            this.setBlockState(arg, lv, Blocks.BEDROCK.getDefaultState());
         } else if ((bl || bl3) && !bl4) {
            this.setBlockState(arg, lv, Blocks.BEDROCK.getDefaultState());
         } else {
            this.setBlockState(arg, lv, Blocks.AIR.getDefaultState());
         }
      }

      return true;
   }
}
