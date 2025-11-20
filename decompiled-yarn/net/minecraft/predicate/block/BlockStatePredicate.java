package net.minecraft.predicate.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;

public class BlockStatePredicate implements Predicate<BlockState> {
   public static final Predicate<BlockState> ANY = _snowman -> true;
   private final StateManager<Block, BlockState> manager;
   private final Map<Property<?>, Predicate<Object>> propertyTests = Maps.newHashMap();

   private BlockStatePredicate(StateManager<Block, BlockState> manager) {
      this.manager = manager;
   }

   public static BlockStatePredicate forBlock(Block block) {
      return new BlockStatePredicate(block.getStateManager());
   }

   public boolean test(@Nullable BlockState _snowman) {
      if (_snowman != null && _snowman.getBlock().equals(this.manager.getOwner())) {
         if (this.propertyTests.isEmpty()) {
            return true;
         } else {
            for (Entry<Property<?>, Predicate<Object>> _snowmanx : this.propertyTests.entrySet()) {
               if (!this.testProperty(_snowman, _snowmanx.getKey(), _snowmanx.getValue())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   protected <T extends Comparable<T>> boolean testProperty(BlockState blockState, Property<T> property, Predicate<Object> _snowman) {
      T _snowmanx = blockState.get(property);
      return _snowman.test(_snowmanx);
   }

   public <V extends Comparable<V>> BlockStatePredicate with(Property<V> property, Predicate<Object> _snowman) {
      if (!this.manager.getProperties().contains(property)) {
         throw new IllegalArgumentException(this.manager + " cannot support property " + property);
      } else {
         this.propertyTests.put(property, _snowman);
         return this;
      }
   }
}
