package net.minecraft.predicate.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
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
   public static final LocationPredicate ANY = new LocationPredicate(
      NumberRange.FloatRange.ANY,
      NumberRange.FloatRange.ANY,
      NumberRange.FloatRange.ANY,
      null,
      null,
      null,
      null,
      LightPredicate.ANY,
      BlockPredicate.ANY,
      FluidPredicate.ANY
   );
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

   public LocationPredicate(
      NumberRange.FloatRange x,
      NumberRange.FloatRange y,
      NumberRange.FloatRange z,
      @Nullable RegistryKey<Biome> _snowman,
      @Nullable StructureFeature<?> feature,
      @Nullable RegistryKey<World> dimension,
      @Nullable Boolean smokey,
      LightPredicate light,
      BlockPredicate block,
      FluidPredicate fluid
   ) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.biome = _snowman;
      this.feature = feature;
      this.dimension = dimension;
      this.smokey = smokey;
      this.light = light;
      this.block = block;
      this.fluid = fluid;
   }

   public static LocationPredicate biome(RegistryKey<Biome> _snowman) {
      return new LocationPredicate(
         NumberRange.FloatRange.ANY,
         NumberRange.FloatRange.ANY,
         NumberRange.FloatRange.ANY,
         _snowman,
         null,
         null,
         null,
         LightPredicate.ANY,
         BlockPredicate.ANY,
         FluidPredicate.ANY
      );
   }

   public static LocationPredicate dimension(RegistryKey<World> dimension) {
      return new LocationPredicate(
         NumberRange.FloatRange.ANY,
         NumberRange.FloatRange.ANY,
         NumberRange.FloatRange.ANY,
         null,
         null,
         dimension,
         null,
         LightPredicate.ANY,
         BlockPredicate.ANY,
         FluidPredicate.ANY
      );
   }

   public static LocationPredicate feature(StructureFeature<?> feature) {
      return new LocationPredicate(
         NumberRange.FloatRange.ANY,
         NumberRange.FloatRange.ANY,
         NumberRange.FloatRange.ANY,
         null,
         feature,
         null,
         null,
         LightPredicate.ANY,
         BlockPredicate.ANY,
         FluidPredicate.ANY
      );
   }

   public boolean test(ServerWorld world, double x, double y, double z) {
      return this.test(world, (float)x, (float)y, (float)z);
   }

   public boolean test(ServerWorld world, float x, float y, float z) {
      if (!this.x.test(x)) {
         return false;
      } else if (!this.y.test(y)) {
         return false;
      } else if (!this.z.test(z)) {
         return false;
      } else if (this.dimension != null && this.dimension != world.getRegistryKey()) {
         return false;
      } else {
         BlockPos _snowman = new BlockPos((double)x, (double)y, (double)z);
         boolean _snowmanx = world.canSetBlock(_snowman);
         Optional<RegistryKey<Biome>> _snowmanxx = world.getRegistryManager().get(Registry.BIOME_KEY).getKey(world.getBiome(_snowman));
         if (!_snowmanxx.isPresent()) {
            return false;
         } else if (this.biome == null || _snowmanx && this.biome == _snowmanxx.get()) {
            if (this.feature == null || _snowmanx && world.getStructureAccessor().getStructureAt(_snowman, true, this.feature).hasChildren()) {
               if (this.smokey == null || _snowmanx && this.smokey == CampfireBlock.isLitCampfireInRange(world, _snowman)) {
                  if (!this.light.test(world, _snowman)) {
                     return false;
                  } else {
                     return !this.block.test(world, _snowman) ? false : this.fluid.test(world, _snowman);
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (!this.x.isDummy() || !this.y.isDummy() || !this.z.isDummy()) {
            JsonObject _snowmanx = new JsonObject();
            _snowmanx.add("x", this.x.toJson());
            _snowmanx.add("y", this.y.toJson());
            _snowmanx.add("z", this.z.toJson());
            _snowman.add("position", _snowmanx);
         }

         if (this.dimension != null) {
            World.CODEC.encodeStart(JsonOps.INSTANCE, this.dimension).resultOrPartial(field_24732::error).ifPresent(_snowmanx -> _snowman.add("dimension", _snowmanx));
         }

         if (this.feature != null) {
            _snowman.addProperty("feature", this.feature.getName());
         }

         if (this.biome != null) {
            _snowman.addProperty("biome", this.biome.getValue().toString());
         }

         if (this.smokey != null) {
            _snowman.addProperty("smokey", this.smokey);
         }

         _snowman.add("light", this.light.toJson());
         _snowman.add("block", this.block.toJson());
         _snowman.add("fluid", this.fluid.toJson());
         return _snowman;
      }
   }

   public static LocationPredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "location");
         JsonObject _snowmanx = JsonHelper.getObject(_snowman, "position", new JsonObject());
         NumberRange.FloatRange _snowmanxx = NumberRange.FloatRange.fromJson(_snowmanx.get("x"));
         NumberRange.FloatRange _snowmanxxx = NumberRange.FloatRange.fromJson(_snowmanx.get("y"));
         NumberRange.FloatRange _snowmanxxxx = NumberRange.FloatRange.fromJson(_snowmanx.get("z"));
         RegistryKey<World> _snowmanxxxxx = _snowman.has("dimension")
            ? Identifier.CODEC
               .parse(JsonOps.INSTANCE, _snowman.get("dimension"))
               .resultOrPartial(field_24732::error)
               .map(_snowmanxxxxxx -> RegistryKey.of(Registry.DIMENSION, _snowmanxxxxxx))
               .orElse(null)
            : null;
         StructureFeature<?> _snowmanxxxxxx = _snowman.has("feature") ? (StructureFeature)StructureFeature.STRUCTURES.get(JsonHelper.getString(_snowman, "feature")) : null;
         RegistryKey<Biome> _snowmanxxxxxxx = null;
         if (_snowman.has("biome")) {
            Identifier _snowmanxxxxxxxx = new Identifier(JsonHelper.getString(_snowman, "biome"));
            _snowmanxxxxxxx = RegistryKey.of(Registry.BIOME_KEY, _snowmanxxxxxxxx);
         }

         Boolean _snowmanxxxxxxxx = _snowman.has("smokey") ? _snowman.get("smokey").getAsBoolean() : null;
         LightPredicate _snowmanxxxxxxxxx = LightPredicate.fromJson(_snowman.get("light"));
         BlockPredicate _snowmanxxxxxxxxxx = BlockPredicate.fromJson(_snowman.get("block"));
         FluidPredicate _snowmanxxxxxxxxxxx = FluidPredicate.fromJson(_snowman.get("fluid"));
         return new LocationPredicate(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
      } else {
         return ANY;
      }
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

      public Builder() {
      }

      public static LocationPredicate.Builder create() {
         return new LocationPredicate.Builder();
      }

      public LocationPredicate.Builder biome(@Nullable RegistryKey<Biome> _snowman) {
         this.biome = _snowman;
         return this;
      }

      public LocationPredicate.Builder block(BlockPredicate block) {
         this.block = block;
         return this;
      }

      public LocationPredicate.Builder smokey(Boolean smokey) {
         this.smokey = smokey;
         return this;
      }

      public LocationPredicate build() {
         return new LocationPredicate(this.x, this.y, this.z, this.biome, this.feature, this.dimension, this.smokey, this.light, this.block, this.fluid);
      }
   }
}
