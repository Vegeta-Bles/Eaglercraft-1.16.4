package net.minecraft.client.render.model.json;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;

@Environment(EnvType.CLIENT)
public class SimpleMultipartModelSelector implements MultipartModelSelector {
   private static final Splitter VALUE_SPLITTER = Splitter.on('|').omitEmptyStrings();
   private final String key;
   private final String valueString;

   public SimpleMultipartModelSelector(String key, String valueString) {
      this.key = key;
      this.valueString = valueString;
   }

   @Override
   public Predicate<BlockState> getPredicate(StateManager<Block, BlockState> arg) {
      Property<?> lv = arg.getProperty(this.key);
      if (lv == null) {
         throw new RuntimeException(String.format("Unknown property '%s' on '%s'", this.key, arg.getOwner().toString()));
      } else {
         String string = this.valueString;
         boolean bl = !string.isEmpty() && string.charAt(0) == '!';
         if (bl) {
            string = string.substring(1);
         }

         List<String> list = VALUE_SPLITTER.splitToList(string);
         if (list.isEmpty()) {
            throw new RuntimeException(String.format("Empty value '%s' for property '%s' on '%s'", this.valueString, this.key, arg.getOwner().toString()));
         } else {
            Predicate<BlockState> predicate;
            if (list.size() == 1) {
               predicate = this.createPredicate(arg, lv, string);
            } else {
               List<Predicate<BlockState>> list2 = list.stream().map(stringx -> this.createPredicate(arg, lv, stringx)).collect(Collectors.toList());
               predicate = argx -> list2.stream().anyMatch(predicatex -> predicatex.test(argx));
            }

            return bl ? predicate.negate() : predicate;
         }
      }
   }

   private Predicate<BlockState> createPredicate(StateManager<Block, BlockState> stateFactory, Property<?> property, String valueString) {
      Optional<?> optional = property.parse(valueString);
      if (!optional.isPresent()) {
         throw new RuntimeException(
            String.format("Unknown value '%s' for property '%s' on '%s' in '%s'", valueString, this.key, stateFactory.getOwner().toString(), this.valueString)
         );
      } else {
         return arg2 -> arg2.get(property).equals(optional.get());
      }
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("key", this.key).add("value", this.valueString).toString();
   }
}
