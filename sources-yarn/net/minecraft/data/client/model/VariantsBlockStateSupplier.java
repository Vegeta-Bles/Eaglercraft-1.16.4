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
      map.getProperties().forEach(arg -> {
         if (this.block.getStateManager().getProperty(arg.getName()) != arg) {
            throw new IllegalStateException("Property " + arg + " is not defined for block " + this.block);
         } else if (!this.definedProperties.add((Property<?>)arg)) {
            throw new IllegalStateException("Values of property " + arg + " already defined for block " + this.block);
         }
      });
      this.variantMaps.add(map);
      return this;
   }

   public JsonElement get() {
      Stream<Pair<PropertiesMap, List<BlockStateVariant>>> stream = Stream.of(Pair.of(PropertiesMap.empty(), this.variants));

      for (BlockStateVariantMap lv : this.variantMaps) {
         Map<PropertiesMap, List<BlockStateVariant>> map = lv.getVariants();
         stream = stream.flatMap(pair -> map.entrySet().stream().map(entry -> {
               PropertiesMap lvx = ((PropertiesMap)pair.getFirst()).with(entry.getKey());
               List<BlockStateVariant> list = intersect((List<BlockStateVariant>)pair.getSecond(), entry.getValue());
               return Pair.of(lvx, list);
            }));
      }

      Map<String, JsonElement> map2 = new TreeMap<>();
      stream.forEach(pair -> {
         JsonElement var10000 = map2.put(((PropertiesMap)pair.getFirst()).asString(), BlockStateVariant.toJson((List<BlockStateVariant>)pair.getSecond()));
      });
      JsonObject jsonObject = new JsonObject();
      jsonObject.add("variants", Util.make(new JsonObject(), jsonObjectx -> map2.forEach(jsonObjectx::add)));
      return jsonObject;
   }

   private static List<BlockStateVariant> intersect(List<BlockStateVariant> list, List<BlockStateVariant> list2) {
      Builder<BlockStateVariant> builder = ImmutableList.builder();
      list.forEach(arg -> list2.forEach(arg2 -> builder.add(BlockStateVariant.union(arg, arg2))));
      return builder.build();
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
