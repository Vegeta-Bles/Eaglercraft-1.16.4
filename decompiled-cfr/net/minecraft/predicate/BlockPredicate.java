/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.predicate;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.StatePredicate;
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
        }
        if (!world.canSetBlock(pos)) {
            return false;
        }
        BlockState blockState = world.getBlockState(pos);
        Block _snowman2 = blockState.getBlock();
        if (this.tag != null && !this.tag.contains(_snowman2)) {
            return false;
        }
        if (this.block != null && _snowman2 != this.block) {
            return false;
        }
        if (!this.state.test(blockState)) {
            return false;
        }
        return this.nbt == NbtPredicate.ANY || (_snowman = world.getBlockEntity(pos)) != null && this.nbt.test(_snowman.toTag(new CompoundTag()));
    }

    public static BlockPredicate fromJson(@Nullable JsonElement json) {
        Object _snowman5;
        Tag<Block> _snowman4;
        if (json == null || json.isJsonNull()) {
            return ANY;
        }
        JsonObject jsonObject = JsonHelper.asObject(json, "block");
        NbtPredicate _snowman2 = NbtPredicate.fromJson(jsonObject.get("nbt"));
        Block _snowman3 = null;
        if (jsonObject.has("block")) {
            _snowman4 = new Identifier(JsonHelper.getString(jsonObject, "block"));
            _snowman3 = Registry.BLOCK.get((Identifier)((Object)_snowman4));
        }
        _snowman4 = null;
        if (jsonObject.has("tag")) {
            _snowman5 = new Identifier(JsonHelper.getString(jsonObject, "tag"));
            _snowman4 = ServerTagManagerHolder.getTagManager().getBlocks().getTag((Identifier)_snowman5);
            if (_snowman4 == null) {
                throw new JsonSyntaxException("Unknown block tag '" + _snowman5 + "'");
            }
        }
        _snowman5 = StatePredicate.fromJson(jsonObject.get("state"));
        return new BlockPredicate(_snowman4, _snowman3, (StatePredicate)_snowman5, _snowman2);
    }

    public JsonElement toJson() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (this.block != null) {
            jsonObject.addProperty("block", Registry.BLOCK.getId(this.block).toString());
        }
        if (this.tag != null) {
            jsonObject.addProperty("tag", ServerTagManagerHolder.getTagManager().getBlocks().getTagId(this.tag).toString());
        }
        jsonObject.add("nbt", this.nbt.toJson());
        jsonObject.add("state", this.state.toJson());
        return jsonObject;
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

        public static Builder create() {
            return new Builder();
        }

        public Builder block(Block block) {
            this.block = block;
            return this;
        }

        public Builder method_29233(Tag<Block> tag) {
            this.tag = tag;
            return this;
        }

        public Builder state(StatePredicate state) {
            this.state = state;
            return this;
        }

        public BlockPredicate build() {
            return new BlockPredicate(this.tag, this.block, this.state, this.nbt);
        }
    }
}

