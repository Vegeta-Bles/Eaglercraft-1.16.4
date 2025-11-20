package net.minecraft.data.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;

public class MultipartBlockStateSupplier implements BlockStateSupplier {
   private final Block block;
   private final List<MultipartBlockStateSupplier.Multipart> multiparts = Lists.newArrayList();

   private MultipartBlockStateSupplier(Block block) {
      this.block = block;
   }

   @Override
   public Block getBlock() {
      return this.block;
   }

   public static MultipartBlockStateSupplier create(Block block) {
      return new MultipartBlockStateSupplier(block);
   }

   public MultipartBlockStateSupplier with(List<BlockStateVariant> list) {
      this.multiparts.add(new MultipartBlockStateSupplier.Multipart(list));
      return this;
   }

   public MultipartBlockStateSupplier with(BlockStateVariant arg) {
      return this.with(ImmutableList.of(arg));
   }

   public MultipartBlockStateSupplier with(When arg, List<BlockStateVariant> list) {
      this.multiparts.add(new MultipartBlockStateSupplier.ConditionalMultipart(arg, list));
      return this;
   }

   public MultipartBlockStateSupplier with(When arg, BlockStateVariant... args) {
      return this.with(arg, ImmutableList.copyOf(args));
   }

   public MultipartBlockStateSupplier with(When arg, BlockStateVariant arg2) {
      return this.with(arg, ImmutableList.of(arg2));
   }

   public JsonElement get() {
      StateManager<Block, BlockState> lv = this.block.getStateManager();
      this.multiparts.forEach(arg2 -> arg2.validate(lv));
      JsonArray jsonArray = new JsonArray();
      this.multiparts.stream().map(MultipartBlockStateSupplier.Multipart::get).forEach(jsonArray::add);
      JsonObject jsonObject = new JsonObject();
      jsonObject.add("multipart", jsonArray);
      return jsonObject;
   }

   static class ConditionalMultipart extends MultipartBlockStateSupplier.Multipart {
      private final When when;

      private ConditionalMultipart(When when, List<BlockStateVariant> variants) {
         super(variants);
         this.when = when;
      }

      @Override
      public void validate(StateManager<?, ?> stateManager) {
         this.when.validate(stateManager);
      }

      @Override
      public void extraToJson(JsonObject json) {
         json.add("when", this.when.get());
      }
   }

   static class Multipart implements Supplier<JsonElement> {
      private final List<BlockStateVariant> variants;

      private Multipart(List<BlockStateVariant> variants) {
         this.variants = variants;
      }

      public void validate(StateManager<?, ?> stateManager) {
      }

      public void extraToJson(JsonObject json) {
      }

      public JsonElement get() {
         JsonObject jsonObject = new JsonObject();
         this.extraToJson(jsonObject);
         jsonObject.add("apply", BlockStateVariant.toJson(this.variants));
         return jsonObject;
      }
   }
}
