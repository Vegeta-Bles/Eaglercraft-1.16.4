package net.minecraft.client.render.model.json;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;

public class SimpleMultipartModelSelector implements MultipartModelSelector {
   private static final Splitter VALUE_SPLITTER = Splitter.on('|').omitEmptyStrings();
   private final String key;
   private final String valueString;

   public SimpleMultipartModelSelector(String key, String valueString) {
      this.key = key;
      this.valueString = valueString;
   }

   @Override
   public Predicate<BlockState> getPredicate(StateManager<Block, BlockState> _snowman) {
      Property<?> _snowmanx = _snowman.getProperty(this.key);
      if (_snowmanx == null) {
         throw new RuntimeException(String.format("Unknown property '%s' on '%s'", this.key, _snowman.getOwner().toString()));
      } else {
         String _snowmanxx = this.valueString;
         boolean _snowmanxxx = !_snowmanxx.isEmpty() && _snowmanxx.charAt(0) == '!';
         if (_snowmanxxx) {
            _snowmanxx = _snowmanxx.substring(1);
         }

         List<String> _snowmanxxxx = VALUE_SPLITTER.splitToList(_snowmanxx);
         if (_snowmanxxxx.isEmpty()) {
            throw new RuntimeException(String.format("Empty value '%s' for property '%s' on '%s'", this.valueString, this.key, _snowman.getOwner().toString()));
         } else {
            Predicate<BlockState> _snowmanxxxxx;
            if (_snowmanxxxx.size() == 1) {
               _snowmanxxxxx = this.createPredicate(_snowman, _snowmanx, _snowmanxx);
            } else {
               List<Predicate<BlockState>> _snowmanxxxxxx = _snowmanxxxx.stream().map(_snowmanxxxxxxx -> this.createPredicate(_snowman, _snowman, _snowmanxxxxxxx)).collect(Collectors.toList());
               _snowmanxxxxx = _snowmanxxxxxxx -> _snowman.stream().anyMatch(_snowmanxxxxxxxx -> _snowmanxxxxxxxx.test(_snowmanxx));
            }

            return _snowmanxxx ? _snowmanxxxxx.negate() : _snowmanxxxxx;
         }
      }
   }

   private Predicate<BlockState> createPredicate(StateManager<Block, BlockState> stateFactory, Property<?> property, String valueString) {
      Optional<?> _snowman = property.parse(valueString);
      if (!_snowman.isPresent()) {
         throw new RuntimeException(
            String.format("Unknown value '%s' for property '%s' on '%s' in '%s'", valueString, this.key, stateFactory.getOwner().toString(), this.valueString)
         );
      } else {
         return _snowmanxx -> _snowmanxx.get(property).equals(_snowman.get());
      }
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("key", this.key).add("value", this.valueString).toString();
   }
}
