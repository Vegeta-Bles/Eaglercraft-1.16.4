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
      WorldView _snowman, BlockPos pos, BlockPos _snowman, Structure.StructureBlockInfo _snowman, Structure.StructureBlockInfo _snowman, StructurePlacementData _snowman
   ) {
      BlockState _snowmanxxxxx = _snowman.state;
      if (_snowmanxxxxx.isOf(Blocks.JIGSAW)) {
         String _snowmanxxxxxx = _snowman.tag.getString("final_state");
         BlockArgumentParser _snowmanxxxxxxx = new BlockArgumentParser(new StringReader(_snowmanxxxxxx), false);

         try {
            _snowmanxxxxxxx.parse(true);
         } catch (CommandSyntaxException var11) {
            throw new RuntimeException(var11);
         }

         return _snowmanxxxxxxx.getBlockState().isOf(Blocks.STRUCTURE_VOID) ? null : new Structure.StructureBlockInfo(_snowman.pos, _snowmanxxxxxxx.getBlockState(), null);
      } else {
         return _snowman;
      }
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.JIGSAW_REPLACEMENT;
   }
}
