package net.minecraft.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.rule.AlwaysTruePosRuleTest;
import net.minecraft.structure.rule.PosRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.math.BlockPos;

public class StructureProcessorRule {
   public static final Codec<StructureProcessorRule> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               RuleTest.field_25012.fieldOf("input_predicate").forGetter(_snowmanx -> _snowmanx.inputPredicate),
               RuleTest.field_25012.fieldOf("location_predicate").forGetter(_snowmanx -> _snowmanx.locationPredicate),
               PosRuleTest.field_25007.optionalFieldOf("position_predicate", AlwaysTruePosRuleTest.INSTANCE).forGetter(_snowmanx -> _snowmanx.positionPredicate),
               BlockState.CODEC.fieldOf("output_state").forGetter(_snowmanx -> _snowmanx.outputState),
               CompoundTag.CODEC.optionalFieldOf("output_nbt").forGetter(_snowmanx -> Optional.ofNullable(_snowmanx.tag))
            )
            .apply(_snowman, StructureProcessorRule::new)
   );
   private final RuleTest inputPredicate;
   private final RuleTest locationPredicate;
   private final PosRuleTest positionPredicate;
   private final BlockState outputState;
   @Nullable
   private final CompoundTag tag;

   public StructureProcessorRule(RuleTest _snowman, RuleTest _snowman, BlockState _snowman) {
      this(_snowman, _snowman, AlwaysTruePosRuleTest.INSTANCE, _snowman, Optional.empty());
   }

   public StructureProcessorRule(RuleTest _snowman, RuleTest _snowman, PosRuleTest _snowman, BlockState _snowman) {
      this(_snowman, _snowman, _snowman, _snowman, Optional.empty());
   }

   public StructureProcessorRule(RuleTest _snowman, RuleTest _snowman, PosRuleTest _snowman, BlockState _snowman, Optional<CompoundTag> _snowman) {
      this.inputPredicate = _snowman;
      this.locationPredicate = _snowman;
      this.positionPredicate = _snowman;
      this.outputState = _snowman;
      this.tag = _snowman.orElse(null);
   }

   public boolean test(BlockState input, BlockState location, BlockPos _snowman, BlockPos _snowman, BlockPos _snowman, Random _snowman) {
      return this.inputPredicate.test(input, _snowman) && this.locationPredicate.test(location, _snowman) && this.positionPredicate.test(_snowman, _snowman, _snowman, _snowman);
   }

   public BlockState getOutputState() {
      return this.outputState;
   }

   @Nullable
   public CompoundTag getTag() {
      return this.tag;
   }
}
