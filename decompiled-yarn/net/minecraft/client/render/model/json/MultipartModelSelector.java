package net.minecraft.client.render.model.json;

import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;

@FunctionalInterface
public interface MultipartModelSelector {
   MultipartModelSelector TRUE = _snowman -> _snowmanx -> true;
   MultipartModelSelector FALSE = _snowman -> _snowmanx -> false;

   Predicate<BlockState> getPredicate(StateManager<Block, BlockState> stateFactory);
}
