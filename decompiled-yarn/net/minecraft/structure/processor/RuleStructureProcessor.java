package net.minecraft.structure.processor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldView;

public class RuleStructureProcessor extends StructureProcessor {
   public static final Codec<RuleStructureProcessor> CODEC = StructureProcessorRule.CODEC
      .listOf()
      .fieldOf("rules")
      .xmap(RuleStructureProcessor::new, _snowman -> _snowman.rules)
      .codec();
   private final ImmutableList<StructureProcessorRule> rules;

   public RuleStructureProcessor(List<? extends StructureProcessorRule> rules) {
      this.rules = ImmutableList.copyOf(rules);
   }

   @Nullable
   @Override
   public Structure.StructureBlockInfo process(
      WorldView _snowman, BlockPos pos, BlockPos _snowman, Structure.StructureBlockInfo _snowman, Structure.StructureBlockInfo _snowman, StructurePlacementData _snowman
   ) {
      Random _snowmanxxxxx = new Random(MathHelper.hashCode(_snowman.pos));
      BlockState _snowmanxxxxxx = _snowman.getBlockState(_snowman.pos);
      UnmodifiableIterator var9 = this.rules.iterator();

      while (var9.hasNext()) {
         StructureProcessorRule _snowmanxxxxxxx = (StructureProcessorRule)var9.next();
         if (_snowmanxxxxxxx.test(_snowman.state, _snowmanxxxxxx, _snowman.pos, _snowman.pos, _snowman, _snowmanxxxxx)) {
            return new Structure.StructureBlockInfo(_snowman.pos, _snowmanxxxxxxx.getOutputState(), _snowmanxxxxxxx.getTag());
         }
      }

      return _snowman;
   }

   @Override
   protected StructureProcessorType<?> getType() {
      return StructureProcessorType.RULE;
   }
}
