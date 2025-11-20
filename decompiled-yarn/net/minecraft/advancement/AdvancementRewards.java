package net.minecraft.advancement;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class AdvancementRewards {
   public static final AdvancementRewards NONE = new AdvancementRewards(0, new Identifier[0], new Identifier[0], CommandFunction.LazyContainer.EMPTY);
   private final int experience;
   private final Identifier[] loot;
   private final Identifier[] recipes;
   private final CommandFunction.LazyContainer function;

   public AdvancementRewards(int experience, Identifier[] loot, Identifier[] recipes, CommandFunction.LazyContainer function) {
      this.experience = experience;
      this.loot = loot;
      this.recipes = recipes;
      this.function = function;
   }

   public void apply(ServerPlayerEntity player) {
      player.addExperience(this.experience);
      LootContext _snowman = new LootContext.Builder(player.getServerWorld())
         .parameter(LootContextParameters.THIS_ENTITY, player)
         .parameter(LootContextParameters.ORIGIN, player.getPos())
         .random(player.getRandom())
         .build(LootContextTypes.ADVANCEMENT_REWARD);
      boolean _snowmanx = false;

      for (Identifier _snowmanxx : this.loot) {
         for (ItemStack _snowmanxxx : player.server.getLootManager().getTable(_snowmanxx).generateLoot(_snowman)) {
            if (player.giveItemStack(_snowmanxxx)) {
               player.world
                  .playSound(
                     null,
                     player.getX(),
                     player.getY(),
                     player.getZ(),
                     SoundEvents.ENTITY_ITEM_PICKUP,
                     SoundCategory.PLAYERS,
                     0.2F,
                     ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
                  );
               _snowmanx = true;
            } else {
               ItemEntity _snowmanxxxx = player.dropItem(_snowmanxxx, false);
               if (_snowmanxxxx != null) {
                  _snowmanxxxx.resetPickupDelay();
                  _snowmanxxxx.setOwner(player.getUuid());
               }
            }
         }
      }

      if (_snowmanx) {
         player.playerScreenHandler.sendContentUpdates();
      }

      if (this.recipes.length > 0) {
         player.unlockRecipes(this.recipes);
      }

      MinecraftServer _snowmanxx = player.server;
      this.function
         .get(_snowmanxx.getCommandFunctionManager())
         .ifPresent(_snowmanxxxx -> _snowman.getCommandFunctionManager().execute(_snowmanxxxx, player.getCommandSource().withSilent().withLevel(2)));
   }

   @Override
   public String toString() {
      return "AdvancementRewards{experience="
         + this.experience
         + ", loot="
         + Arrays.toString((Object[])this.loot)
         + ", recipes="
         + Arrays.toString((Object[])this.recipes)
         + ", function="
         + this.function
         + '}';
   }

   public JsonElement toJson() {
      if (this == NONE) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (this.experience != 0) {
            _snowman.addProperty("experience", this.experience);
         }

         if (this.loot.length > 0) {
            JsonArray _snowmanx = new JsonArray();

            for (Identifier _snowmanxx : this.loot) {
               _snowmanx.add(_snowmanxx.toString());
            }

            _snowman.add("loot", _snowmanx);
         }

         if (this.recipes.length > 0) {
            JsonArray _snowmanx = new JsonArray();

            for (Identifier _snowmanxx : this.recipes) {
               _snowmanx.add(_snowmanxx.toString());
            }

            _snowman.add("recipes", _snowmanx);
         }

         if (this.function.getId() != null) {
            _snowman.addProperty("function", this.function.getId().toString());
         }

         return _snowman;
      }
   }

   public static AdvancementRewards fromJson(JsonObject json) throws JsonParseException {
      int _snowman = JsonHelper.getInt(json, "experience", 0);
      JsonArray _snowmanx = JsonHelper.getArray(json, "loot", new JsonArray());
      Identifier[] _snowmanxx = new Identifier[_snowmanx.size()];

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.length; _snowmanxxx++) {
         _snowmanxx[_snowmanxxx] = new Identifier(JsonHelper.asString(_snowmanx.get(_snowmanxxx), "loot[" + _snowmanxxx + "]"));
      }

      JsonArray _snowmanxxx = JsonHelper.getArray(json, "recipes", new JsonArray());
      Identifier[] _snowmanxxxx = new Identifier[_snowmanxxx.size()];

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.length; _snowmanxxxxx++) {
         _snowmanxxxx[_snowmanxxxxx] = new Identifier(JsonHelper.asString(_snowmanxxx.get(_snowmanxxxxx), "recipes[" + _snowmanxxxxx + "]"));
      }

      CommandFunction.LazyContainer _snowmanxxxxx;
      if (json.has("function")) {
         _snowmanxxxxx = new CommandFunction.LazyContainer(new Identifier(JsonHelper.getString(json, "function")));
      } else {
         _snowmanxxxxx = CommandFunction.LazyContainer.EMPTY;
      }

      return new AdvancementRewards(_snowman, _snowmanxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public static class Builder {
      private int experience;
      private final List<Identifier> loot = Lists.newArrayList();
      private final List<Identifier> recipes = Lists.newArrayList();
      @Nullable
      private Identifier function;

      public Builder() {
      }

      public static AdvancementRewards.Builder experience(int experience) {
         return new AdvancementRewards.Builder().setExperience(experience);
      }

      public AdvancementRewards.Builder setExperience(int experience) {
         this.experience += experience;
         return this;
      }

      public static AdvancementRewards.Builder recipe(Identifier recipe) {
         return new AdvancementRewards.Builder().addRecipe(recipe);
      }

      public AdvancementRewards.Builder addRecipe(Identifier recipe) {
         this.recipes.add(recipe);
         return this;
      }

      public AdvancementRewards build() {
         return new AdvancementRewards(
            this.experience,
            this.loot.toArray(new Identifier[0]),
            this.recipes.toArray(new Identifier[0]),
            this.function == null ? CommandFunction.LazyContainer.EMPTY : new CommandFunction.LazyContainer(this.function)
         );
      }
   }
}
