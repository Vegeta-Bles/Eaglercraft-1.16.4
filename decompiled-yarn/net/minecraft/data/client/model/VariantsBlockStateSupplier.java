package net.minecraft.data.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.state.property.Property;
import net.minecraft.util.Util;

public class VariantsBlockStateSupplier implements BlockStateSupplier {
   private final Block block;
   private final List<BlockStateVariant> variants;
   private final Set<Property<?>> definedProperties = Sets.newHashSet();
   private final List<BlockStateVariantMap> variantMaps = Lists.newArrayList();

   private VariantsBlockStateSupplier(Block block, List<BlockStateVariant> variants) {
      this.block = block;
      this.variants = variants;
   }

   public VariantsBlockStateSupplier coordinate(BlockStateVariantMap map) {
      map.getProperties().forEach(_snowman -> {
         if (this.block.getStateManager().getProperty(_snowman.getName()) != _snowman) {
            throw new IllegalStateException("Property " + _snowman + " is not defined for block " + this.block);
         } else if (!this.definedProperties.add((Property<?>)_snowman)) {
            throw new IllegalStateException("Values of property " + _snowman + " already defined for block " + this.block);
         }
      });
      this.variantMaps.add(map);
      return this;
   }

   public JsonElement get() {
      Stream<Pair<PropertiesMap, List<BlockStateVariant>>> _snowman = Stream.of(Pair.of(PropertiesMap.empty(), this.variants));

      for (BlockStateVariantMap _snowmanx : this.variantMaps) {
         Map<PropertiesMap, List<BlockStateVariant>> _snowmanxx = _snowmanx.getVariants();
         _snowman = _snowman.flatMap(_snowmanxxx -> _snowman.entrySet().stream().map(_snowmanxxxxx -> {
               PropertiesMap _snowmanxx = ((PropertiesMap)_snowmanx.getFirst()).with((PropertiesMap)_snowmanxxxxx.getKey());
               List<BlockStateVariant> _snowmanxxxxx = intersect((List<BlockStateVariant>)_snowmanx.getSecond(), (List<BlockStateVariant>)_snowmanxxxxx.getValue());
               return Pair.of(_snowmanxx, _snowmanxxxxx);
            }));
      }

      Map<String, JsonElement> _snowmanx = new TreeMap<>();
      _snowman.forEach(_snowmanxx -> {
         JsonElement var10000 = _snowman.put(((PropertiesMap)_snowmanxx.getFirst()).asString(), BlockStateVariant.toJson((List<BlockStateVariant>)_snowmanxx.getSecond()));
      });
      JsonObject _snowmanxx = new JsonObject();
      _snowmanxx.add("variants", Util.make(new JsonObject(), _snowmanxxx -> _snowman.forEach(_snowmanxxx::add)));
      return _snowmanxx;
   }

   private static List<BlockStateVariant> intersect(List<BlockStateVariant> _snowman, List<BlockStateVariant> _snowman) {
      Builder<BlockStateVariant> _snowmanxx = ImmutableList.builder();
      _snowman.forEach(_snowmanxxx -> _snowman.forEach(_snowmanxxxxxx -> _snowman.add(BlockStateVariant.union(_snowmanxxx, _snowmanxxxxxx))));
      return _snowmanxx.build();
   }

   @Override
   public Block getBlock() {
      return this.block;
   }

   public static VariantsBlockStateSupplier create(Block block) {
      return new VariantsBlockStateSupplier(block, ImmutableList.of(BlockStateVariant.create()));
   }

   public static VariantsBlockStateSupplier create(Block block, BlockStateVariant variant) {
      return new VariantsBlockStateSupplier(block, ImmutableList.of(variant));
   }

   public static VariantsBlockStateSupplier create(Block block, BlockStateVariant... variants) {
      return new VariantsBlockStateSupplier(block, ImmutableList.copyOf(variants));
   }
}
