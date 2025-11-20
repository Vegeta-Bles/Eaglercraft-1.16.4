/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.predicate.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.CampfireBlock;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.FluidPredicate;
import net.minecraft.predicate.LightPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocationPredicate {
    private static final Logger field_24732 = LogManager.getLogger();
    public static final LocationPredicate ANY = new LocationPredicate(NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, null, null, null, null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
    private final NumberRange.FloatRange x;
    private final NumberRange.FloatRange y;
    private final NumberRange.FloatRange z;
    @Nullable
    private final RegistryKey<Biome> biome;
    @Nullable
    private final StructureFeature<?> feature;
    @Nullable
    private final RegistryKey<World> dimension;
    @Nullable
    private final Boolean smokey;
    private final LightPredicate light;
    private final BlockPredicate block;
    private final FluidPredicate fluid;

    public LocationPredicate(NumberRange.FloatRange x, NumberRange.FloatRange y, NumberRange.FloatRange z, @Nullable RegistryKey<Biome> registryKey, @Nullable StructureFeature<?> feature, @Nullable RegistryKey<World> dimension, @Nullable Boolean smokey, LightPredicate light, BlockPredicate block, FluidPredicate fluid) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.biome = registryKey;
        this.feature = feature;
        this.dimension = dimension;
        this.smokey = smokey;
        this.light = light;
        this.block = block;
        this.fluid = fluid;
    }

    public static LocationPredicate biome(RegistryKey<Biome> registryKey) {
        return new LocationPredicate(NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, registryKey, null, null, null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
    }

    public static LocationPredicate dimension(RegistryKey<World> dimension) {
        return new LocationPredicate(NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, null, null, dimension, null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
    }

    public static LocationPredicate feature(StructureFeature<?> feature) {
        return new LocationPredicate(NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, NumberRange.FloatRange.ANY, null, feature, null, null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
    }

    public boolean test(ServerWorld world, double x, double y, double z) {
        return this.test(world, (float)x, (float)y, (float)z);
    }

    public boolean test(ServerWorld world, float x, float y, float z) {
        if (!this.x.test(x)) {
            return false;
        }
        if (!this.y.test(y)) {
            return false;
        }
        if (!this.z.test(z)) {
            return false;
        }
        if (this.dimension != null && this.dimension != world.getRegistryKey()) {
            return false;
        }
        BlockPos blockPos = new BlockPos(x, y, z);
        boolean _snowman2 = world.canSetBlock(blockPos);
        Optional<RegistryKey<Biome>> _snowman3 = world.getRegistryManager().get(Registry.BIOME_KEY).getKey(world.getBiome(blockPos));
        if (!_snowman3.isPresent()) {
            return false;
        }
        if (!(this.biome == null || _snowman2 && this.biome == _snowman3.get())) {
            return false;
        }
        if (!(this.feature == null || _snowman2 && world.getStructureAccessor().getStructureAt(blockPos, true, this.feature).hasChildren())) {
            return false;
        }
        if (!(this.smokey == null || _snowman2 && this.smokey == CampfireBlock.isLitCampfireInRange(world, blockPos))) {
            return false;
        }
        if (!this.light.test(world, blockPos)) {
            return false;
        }
        if (!this.block.test(world, blockPos)) {
            return false;
        }
        return this.fluid.test(world, blockPos);
    }

    public JsonElement toJson() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (!(this.x.isDummy() && this.y.isDummy() && this.z.isDummy())) {
            _snowman = new JsonObject();
            _snowman.add("x", this.x.toJson());
            _snowman.add("y", this.y.toJson());
            _snowman.add("z", this.z.toJson());
            jsonObject.add("position", (JsonElement)_snowman);
        }
        if (this.dimension != null) {
            World.CODEC.encodeStart((DynamicOps)JsonOps.INSTANCE, this.dimension).resultOrPartial(arg_0 -> ((Logger)field_24732).error(arg_0)).ifPresent(jsonElement -> jsonObject.add("dimension", jsonElement));
        }
        if (this.feature != null) {
            jsonObject.addProperty("feature", this.feature.getName());
        }
        if (this.biome != null) {
            jsonObject.addProperty("biome", this.biome.getValue().toString());
        }
        if (this.smokey != null) {
            jsonObject.addProperty("smokey", this.smokey);
        }
        jsonObject.add("light", this.light.toJson());
        jsonObject.add("block", this.block.toJson());
        jsonObject.add("fluid", this.fluid.toJson());
        return jsonObject;
    }

    public static LocationPredicate fromJson(@Nullable JsonElement json) {
        Identifier _snowman8;
        if (json == null || json.isJsonNull()) {
            return ANY;
        }
        JsonObject jsonObject = JsonHelper.asObject(json, "location");
        _snowman = JsonHelper.getObject(jsonObject, "position", new JsonObject());
        NumberRange.FloatRange _snowman2 = NumberRange.FloatRange.fromJson(_snowman.get("x"));
        NumberRange.FloatRange _snowman3 = NumberRange.FloatRange.fromJson(_snowman.get("y"));
        NumberRange.FloatRange _snowman4 = NumberRange.FloatRange.fromJson(_snowman.get("z"));
        RegistryKey _snowman5 = jsonObject.has("dimension") ? (RegistryKey)Identifier.CODEC.parse((DynamicOps)JsonOps.INSTANCE, (Object)jsonObject.get("dimension")).resultOrPartial(arg_0 -> ((Logger)field_24732).error(arg_0)).map(identifier -> RegistryKey.of(Registry.DIMENSION, identifier)).orElse(null) : null;
        StructureFeature _snowman6 = jsonObject.has("feature") ? (StructureFeature)StructureFeature.STRUCTURES.get((Object)JsonHelper.getString(jsonObject, "feature")) : null;
        RegistryKey<Biome> _snowman7 = null;
        if (jsonObject.has("biome")) {
            _snowman8 = new Identifier(JsonHelper.getString(jsonObject, "biome"));
            _snowman7 = RegistryKey.of(Registry.BIOME_KEY, _snowman8);
        }
        _snowman8 = jsonObject.has("smokey") ? Boolean.valueOf(jsonObject.get("smokey").getAsBoolean()) : null;
        LightPredicate _snowman9 = LightPredicate.fromJson(jsonObject.get("light"));
        BlockPredicate _snowman10 = BlockPredicate.fromJson(jsonObject.get("block"));
        FluidPredicate _snowman11 = FluidPredicate.fromJson(jsonObject.get("fluid"));
        return new LocationPredicate(_snowman2, _snowman3, _snowman4, _snowman7, _snowman6, _snowman5, (Boolean)((Object)_snowman8), _snowman9, _snowman10, _snowman11);
    }

    public static class Builder {
        private NumberRange.FloatRange x = NumberRange.FloatRange.ANY;
        private NumberRange.FloatRange y = NumberRange.FloatRange.ANY;
        private NumberRange.FloatRange z = NumberRange.FloatRange.ANY;
        @Nullable
        private RegistryKey<Biome> biome;
        @Nullable
        private StructureFeature<?> feature;
        @Nullable
        private RegistryKey<World> dimension;
        @Nullable
        private Boolean smokey;
        private LightPredicate light = LightPredicate.ANY;
        private BlockPredicate block = BlockPredicate.ANY;
        private FluidPredicate fluid = FluidPredicate.ANY;

        public static Builder create() {
            return new Builder();
        }

        public Builder biome(@Nullable RegistryKey<Biome> registryKey) {
            this.biome = registryKey;
            return this;
        }

        public Builder block(BlockPredicate block) {
            this.block = block;
            return this;
        }

        public Builder smokey(Boolean smokey) {
            this.smokey = smokey;
            return this;
        }

        public LocationPredicate build() {
            return new LocationPredicate(this.x, this.y, this.z, this.biome, this.feature, this.dimension, this.smokey, this.light, this.block, this.fluid);
        }
    }
}

