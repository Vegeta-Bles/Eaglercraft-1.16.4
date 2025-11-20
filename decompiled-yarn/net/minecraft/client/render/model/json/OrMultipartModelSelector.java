package net.minecraft.client.render.model.json;

import com.google.common.collect.Streams;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;

public class OrMultipartModelSelector implements MultipartModelSelector {
   private final Iterable<? extends MultipartModelSelector> selectors;

   public OrMultipartModelSelector(Iterable<? extends MultipartModelSelector> selectors) {
      this.selectors = selectors;
   }

   @Override
   public Predicate<BlockState> getPredicate(StateManager<Block, BlockState> _snowman) {
      List<Predicate<BlockState>> _snowmanx = Streams.stream(this.selectors).map(_snowmanxx -> _snowmanxx.getPredicate(_snowman)).collect(Collectors.toList());
      return _snowmanxx -> _snowman.stream().anyMatch(_snowmanxxxx -> _snowmanxxxx.test(_snowmanxx));
   }
}
