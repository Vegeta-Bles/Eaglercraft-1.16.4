package net.minecraft.client.render.model.json;

import com.google.common.collect.Streams;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;

@Environment(EnvType.CLIENT)
public class AndMultipartModelSelector implements MultipartModelSelector {
   private final Iterable<? extends MultipartModelSelector> selectors;

   public AndMultipartModelSelector(Iterable<? extends MultipartModelSelector> selectors) {
      this.selectors = selectors;
   }

   @Override
   public Predicate<BlockState> getPredicate(StateManager<Block, BlockState> arg) {
      List<Predicate<BlockState>> list = Streams.stream(this.selectors).map(arg2 -> arg2.getPredicate(arg)).collect(Collectors.toList());
      return argx -> list.stream().allMatch(predicate -> predicate.test(argx));
   }
}
