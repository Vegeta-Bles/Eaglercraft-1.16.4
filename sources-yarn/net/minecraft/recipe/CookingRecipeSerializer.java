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

   public T read(Identifier arg, JsonObject jsonObject) {
      String string = JsonHelper.getString(jsonObject, "group", "");
      JsonElement jsonElement = (JsonElement)(JsonHelper.hasArray(jsonObject, "ingredient")
         ? JsonHelper.getArray(jsonObject, "ingredient")
         : JsonHelper.getObject(jsonObject, "ingredient"));
      Ingredient lv = Ingredient.fromJson(jsonElement);
      String string2 = JsonHelper.getString(jsonObject, "result");
      Identifier lv2 = new Identifier(string2);
      ItemStack lv3 = new ItemStack(Registry.ITEM.getOrEmpty(lv2).orElseThrow(() -> new IllegalStateException("Item: " + string2 + " does not exist")));
      float f = JsonHelper.getFloat(jsonObject, "experience", 0.0F);
      int i = JsonHelper.getInt(jsonObject, "cookingtime", this.cookingTime);
      return this.recipeFactory.create(arg, string, lv, lv3, f, i);
   }

   public T read(Identifier arg, PacketByteBuf arg2) {
      String string = arg2.readString(32767);
      Ingredient lv = Ingredient.fromPacket(arg2);
      ItemStack lv2 = arg2.readItemStack();
      float f = arg2.readFloat();
      int i = arg2.readVarInt();
      return this.recipeFactory.create(arg, string, lv, lv2, f, i);
   }

   public void write(PacketByteBuf arg, T arg2) {
      arg.writeString(arg2.group);
      arg2.input.write(arg);
      arg.writeItemStack(arg2.output);
      arg.writeFloat(arg2.experience);
      arg.writeVarInt(arg2.cookTime);
   }

   interface RecipeFactory<T extends AbstractCookingRecipe> {
      T create(Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime);
   }
}
