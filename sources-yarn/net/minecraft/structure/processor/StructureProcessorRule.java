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
      instance -> instance.group(
               RuleTest.field_25012.fieldOf("input_predicate").forGetter(arg -> arg.inputPredicate),
               RuleTest.field_25012.fieldOf("location_predicate").forGetter(arg -> arg.locationPredicate),
               PosRuleTest.field_25007.optionalFieldOf("position_predicate", AlwaysTruePosRuleTest.INSTANCE).forGetter(arg -> arg.positionPredicate),
               BlockState.CODEC.fieldOf("output_state").forGetter(arg -> arg.outputState),
               CompoundTag.CODEC.optionalFieldOf("output_nbt").forGetter(arg -> Optional.ofNullable(arg.tag))
            )
            .apply(instance, StructureProcessorRule::new)
   );
   private final RuleTest inputPredicate;
   private final RuleTest locationPredicate;
   private final PosRuleTest positionPredicate;
   private final BlockState outputState;
   @Nullable
   private final CompoundTag tag;

   public StructureProcessorRule(RuleTest arg, RuleTest arg2, BlockState arg3) {
      this(arg, arg2, AlwaysTruePosRuleTest.INSTANCE, arg3, Optional.empty());
   }

   public StructureProcessorRule(RuleTest arg, RuleTest arg2, PosRuleTest arg3, BlockState arg4) {
      this(arg, arg2, arg3, arg4, Optional.empty());
   }

   public StructureProcessorRule(RuleTest arg, RuleTest arg2, PosRuleTest arg3, BlockState arg4, Optional<CompoundTag> optional) {
      this.inputPredicate = arg;
      this.locationPredicate = arg2;
      this.positionPredicate = arg3;
      this.outputState = arg4;
      this.tag = optional.orElse(null);
   }

   public boolean test(BlockState input, BlockState location, BlockPos arg3, BlockPos arg4, BlockPos arg5, Random random) {
      return this.inputPredicate.test(input, random) && this.locationPredicate.test(location, random) && this.positionPredicate.test(arg3, arg4, arg5, random);
   }

   public BlockState getOutputState() {
      return this.outputState;
   }

   @Nullable
   public CompoundTag getTag() {
      return this.tag;
   }
}
