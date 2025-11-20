package net.minecraft.predicate;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
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
      } else if (!world.canSetBlock(pos)) {
         return false;
      } else {
         FluidState _snowman = world.getFluidState(pos);
         Fluid _snowmanx = _snowman.getFluid();
         if (this.tag != null && !this.tag.contains(_snowmanx)) {
            return false;
         } else {
            return this.fluid != null && _snowmanx != this.fluid ? false : this.state.test(_snowman);
         }
      }
   }

   public static FluidPredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "fluid");
         Fluid _snowmanx = null;
         if (_snowman.has("fluid")) {
            Identifier _snowmanxx = new Identifier(JsonHelper.getString(_snowman, "fluid"));
            _snowmanx = Registry.FLUID.get(_snowmanxx);
         }

         Tag<Fluid> _snowmanxx = null;
         if (_snowman.has("tag")) {
            Identifier _snowmanxxx = new Identifier(JsonHelper.getString(_snowman, "tag"));
            _snowmanxx = ServerTagManagerHolder.getTagManager().getFluids().getTag(_snowmanxxx);
            if (_snowmanxx == null) {
               throw new JsonSyntaxException("Unknown fluid tag '" + _snowmanxxx + "'");
            }
         }

         StatePredicate _snowmanxxx = StatePredicate.fromJson(_snowman.get("state"));
         return new FluidPredicate(_snowmanxx, _snowmanx, _snowmanxxx);
      } else {
         return ANY;
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (this.fluid != null) {
            _snowman.addProperty("fluid", Registry.FLUID.getId(this.fluid).toString());
         }

         if (this.tag != null) {
            _snowman.addProperty("tag", ServerTagManagerHolder.getTagManager().getFluids().getTagId(this.tag).toString());
         }

         _snowman.add("state", this.state.toJson());
         return _snowman;
      }
   }
}
