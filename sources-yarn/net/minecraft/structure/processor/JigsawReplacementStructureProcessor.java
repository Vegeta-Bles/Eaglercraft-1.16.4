package net.minecraft.structure.processor;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class JigsawReplacementStructureProcessor extends StructureProcessor {
   public static final Codec<JigsawReplacementStructureProcessor> CODEC = Codec.unit(() -> JigsawReplacementStructureProcessor.INSTANCE);
   public static final JigsawReplacementStructureProcessor INSTANCE = new JigsawReplacementStructureProcessor();

   private JigsawReplacementStructureProcessor() {
   }

   @Nullable
   @Override
   public Structure.StructureBlockInfo process(
      WorldView arg, BlockPos pos, BlockPos arg3, Structure.StructureBlockInfo arg4, Structure.StructureBlockInfo arg5, StructurePlacementData arg6
   ) {
      BlockState lv = arg5.state;
      if (lv.isOf(Blocks.JIGSAW)) {
         String string = arg5.tag.getString("final_state");
         BlockArgumentParser lv2 = new BlockArgumentParser(new StringReader(string), false);

         try {
            lv2.parse(true);
         } catch (CommandSyntaxException var11) {
            throw new RuntimeException(var11);
         }

         return lv2.getBlockState().isOf(Blocks.STRUCTURE_VOID) ? null : new Structure.StructureBlockInfo(arg5.pos, lv2.getBlockState(), null);
      } else {
         return arg5;
      }
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.JIGSAW_REPLACEMENT;
   }
}
