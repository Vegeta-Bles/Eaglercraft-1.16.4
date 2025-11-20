package net.minecraft.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class CookingRecipeSerializer<T extends AbstractCookingRecipe> implements RecipeSerializer<T> {
   private final int cookingTime;
   private final CookingRecipeSerializer.RecipeFactory<T> recipeFactory;

   public CookingRecipeSerializer(CookingRecipeSerializer.RecipeFactory<T> recipeFactory, int cookingTime) {
      this.cookingTime = cookingTime;
      this.recipeFactory = recipeFactory;
   }

   public T read(Identifier _snowman, JsonObject _snowman) {
      String _snowmanxx = JsonHelper.getString(_snowman, "group", "");
      JsonElement _snowmanxxx = (JsonElement)(JsonHelper.hasArray(_snowman, "ingredient") ? JsonHelper.getArray(_snowman, "ingredient") : JsonHelper.getObject(_snowman, "ingredient"));
      Ingredient _snowmanxxxx = Ingredient.fromJson(_snowmanxxx);
      String _snowmanxxxxx = JsonHelper.getString(_snowman, "result");
      Identifier _snowmanxxxxxx = new Identifier(_snowmanxxxxx);
      ItemStack _snowmanxxxxxxx = new ItemStack(Registry.ITEM.getOrEmpty(_snowmanxxxxxx).orElseThrow(() -> new IllegalStateException("Item: " + _snowman + " does not exist")));
      float _snowmanxxxxxxxx = JsonHelper.getFloat(_snowman, "experience", 0.0F);
      int _snowmanxxxxxxxxx = JsonHelper.getInt(_snowman, "cookingtime", this.cookingTime);
      return this.recipeFactory.create(_snowman, _snowmanxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
   }

   public T read(Identifier _snowman, PacketByteBuf _snowman) {
      String _snowmanxx = _snowman.readString(32767);
      Ingredient _snowmanxxx = Ingredient.fromPacket(_snowman);
      ItemStack _snowmanxxxx = _snowman.readItemStack();
      float _snowmanxxxxx = _snowman.readFloat();
      int _snowmanxxxxxx = _snowman.readVarInt();
      return this.recipeFactory.create(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
   }

   public void write(PacketByteBuf _snowman, T _snowman) {
      _snowman.writeString(_snowman.group);
      _snowman.input.write(_snowman);
      _snowman.writeItemStack(_snowman.output);
      _snowman.writeFloat(_snowman.experience);
      _snowman.writeVarInt(_snowman.cookTime);
   }

   interface RecipeFactory<T extends AbstractCookingRecipe> {
      T create(Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime);
   }
}
