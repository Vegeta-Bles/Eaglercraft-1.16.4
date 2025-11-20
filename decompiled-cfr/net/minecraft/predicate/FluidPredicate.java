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
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class FluidPredicate {
    public static final FluidPredicate ANY = new FluidPredicate(null, null, StatePredicate.ANY);
    @Nullable
    private final Tag<Fluid> tag;
    @Nullable
    private final Fluid fluid;
    private final StatePredicate state;

    public FluidPredicate(@Nullable Tag<Fluid> tag, @Nullable Fluid fluid, StatePredicate state) {
        this.tag = tag;
        this.fluid = fluid;
        this.state = state;
    }

    public boolean test(ServerWorld world, BlockPos pos) {
        if (this == ANY) {
            return true;
        }
        if (!world.canSetBlock(pos)) {
            return false;
        }
        FluidState fluidState = world.getFluidState(pos);
        Fluid _snowman2 = fluidState.getFluid();
        if (this.tag != null && !this.tag.contains(_snowman2)) {
            return false;
        }
        if (this.fluid != null && _snowman2 != this.fluid) {
            return false;
        }
        return this.state.test(fluidState);
    }

    public static FluidPredicate fromJson(@Nullable JsonElement json) {
        Object _snowman4;
        Tag<Fluid> _snowman3;
        if (json == null || json.isJsonNull()) {
            return ANY;
        }
        JsonObject jsonObject = JsonHelper.asObject(json, "fluid");
        Fluid _snowman2 = null;
        if (jsonObject.has("fluid")) {
            _snowman3 = new Identifier(JsonHelper.getString(jsonObject, "fluid"));
            _snowman2 = Registry.FLUID.get((Identifier)((Object)_snowman3));
        }
        _snowman3 = null;
        if (jsonObject.has("tag")) {
            _snowman4 = new Identifier(JsonHelper.getString(jsonObject, "tag"));
            _snowman3 = ServerTagManagerHolder.getTagManager().getFluids().getTag((Identifier)_snowman4);
            if (_snowman3 == null) {
                throw new JsonSyntaxException("Unknown fluid tag '" + _snowman4 + "'");
            }
        }
        _snowman4 = StatePredicate.fromJson(jsonObject.get("state"));
        return new FluidPredicate(_snowman3, _snowman2, (StatePredicate)_snowman4);
    }

    public JsonElement toJson() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (this.fluid != null) {
            jsonObject.addProperty("fluid", Registry.FLUID.getId(this.fluid).toString());
        }
        if (this.tag != null) {
            jsonObject.addProperty("tag", ServerTagManagerHolder.getTagManager().getFluids().getTagId(this.tag).toString());
        }
        jsonObject.add("state", this.state.toJson());
        return jsonObject;
    }
}

