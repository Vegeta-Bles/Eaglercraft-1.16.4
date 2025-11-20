package net.minecraft.data.client.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BlockStateVariant implements Supplier<JsonElement> {
   private final Map<VariantSetting<?>, VariantSetting<?>.Value> properties = Maps.newLinkedHashMap();

   public BlockStateVariant() {
   }

   public <T> BlockStateVariant put(VariantSetting<T> key, T value) {
      VariantSetting<?>.Value _snowman = this.properties.put(key, key.evaluate(value));
      if (_snowman != null) {
         throw new IllegalStateException("Replacing value of " + _snowman + " with " + value);
      } else {
         return this;
      }
   }

   public static BlockStateVariant create() {
      return new BlockStateVariant();
   }

   public static BlockStateVariant union(BlockStateVariant first, BlockStateVariant second) {
      BlockStateVariant _snowman = new BlockStateVariant();
      _snowman.properties.putAll(first.properties);
      _snowman.properties.putAll(second.properties);
      return _snowman;
   }

   public JsonElement get() {
      JsonObject _snowman = new JsonObject();
      this.properties.values().forEach(_snowmanx -> _snowmanx.writeTo(_snowman));
      return _snowman;
   }

   public static JsonElement toJson(List<BlockStateVariant> variants) {
      if (variants.size() == 1) {
         return variants.get(0).get();
      } else {
         JsonArray _snowman = new JsonArray();
         variants.forEach(_snowmanx -> _snowman.add(_snowmanx.get()));
         return _snowman;
      }
   }
}
