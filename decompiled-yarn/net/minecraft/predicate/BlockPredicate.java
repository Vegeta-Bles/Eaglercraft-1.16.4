package net.minecraft.predicate;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class BlockPredicate {
   public static final BlockPredicate ANY = new BlockPredicate(null, null, StatePredicate.ANY, NbtPredicate.ANY);
   @Nullable
   private final Tag<Block> tag;
   @Nullable
   private final Block block;
   private final StatePredicate state;
   private final NbtPredicate nbt;

   public BlockPredicate(@Nullable Tag<Block> tag, @Nullable Block block, StatePredicate state, NbtPredicate nbt) {
      this.tag = tag;
      this.block = block;
      this.state = state;
      this.nbt = nbt;
   }

   public boolean test(ServerWorld world, BlockPos pos) {
      if (this == ANY) {
         return true;
      } else if (!world.canSetBlock(pos)) {
         return false;
      } else {
         BlockState _snowman = world.getBlockState(pos);
         Block _snowmanx = _snowman.getBlock();
         if (this.tag != null && !this.tag.contains(_snowmanx)) {
            return false;
         } else if (this.block != null && _snowmanx != this.block) {
            return false;
         } else if (!this.state.test(_snowman)) {
            return false;
         } else {
            if (this.nbt != NbtPredicate.ANY) {
               BlockEntity _snowmanxx = world.getBlockEntity(pos);
               if (_snowmanxx == null || !this.nbt.test(_snowmanxx.toTag(new CompoundTag()))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static BlockPredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "block");
         NbtPredicate _snowmanx = NbtPredicate.fromJson(_snowman.get("nbt"));
         Block _snowmanxx = null;
         if (_snowman.has("block")) {
            Identifier _snowmanxxx = new Identifier(JsonHelper.getString(_snowman, "block"));
            _snowmanxx = Registry.BLOCK.get(_snowmanxxx);
         }

         Tag<Block> _snowmanxxx = null;
         if (_snowman.has("tag")) {
            Identifier _snowmanxxxx = new Identifier(JsonHelper.getString(_snowman, "tag"));
            _snowmanxxx = ServerTagManagerHolder.getTagManager().getBlocks().getTag(_snowmanxxxx);
            if (_snowmanxxx == null) {
               throw new JsonSyntaxException("Unknown block tag '" + _snowmanxxxx + "'");
            }
         }

         StatePredicate _snowmanxxxx = StatePredicate.fromJson(_snowman.get("state"));
         return new BlockPredicate(_snowmanxxx, _snowmanxx, _snowmanxxxx, _snowmanx);
      } else {
         return ANY;
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (this.block != null) {
            _snowman.addProperty("block", Registry.BLOCK.getId(this.block).toString());
         }

         if (this.tag != null) {
            _snowman.addProperty("tag", ServerTagManagerHolder.getTagManager().getBlocks().getTagId(this.tag).toString());
         }

         _snowman.add("nbt", this.nbt.toJson());
         _snowman.add("state", this.state.toJson());
         return _snowman;
      }
   }

   public static class Builder {
      @Nullable
      private Block block;
      @Nullable
      private Tag<Block> tag;
      private StatePredicate state = StatePredicate.ANY;
      private NbtPredicate nbt = NbtPredicate.ANY;

      private Builder() {
      }

      public static BlockPredicate.Builder create() {
         return new BlockPredicate.Builder();
      }

      public BlockPredicate.Builder block(Block block) {
         this.block = block;
         return this;
      }

      public BlockPredicate.Builder method_29233(Tag<Block> _snowman) {
         this.tag = _snowman;
         return this;
      }

      public BlockPredicate.Builder state(StatePredicate state) {
         this.state = state;
         return this;
      }

      public BlockPredicate build() {
         return new BlockPredicate(this.tag, this.block, this.state, this.nbt);
      }
   }
}
