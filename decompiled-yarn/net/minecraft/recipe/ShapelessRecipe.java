package net.minecraft.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ShapelessRecipe implements CraftingRecipe {
   private final Identifier id;
   private final String group;
   private final ItemStack output;
   private final DefaultedList<Ingredient> input;

   public ShapelessRecipe(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input) {
      this.id = id;
      this.group = group;
      this.output = output;
      this.input = input;
   }

   @Override
   public Identifier getId() {
      return this.id;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SHAPELESS;
   }

   @Override
   public String getGroup() {
      return this.group;
   }

   @Override
   public ItemStack getOutput() {
      return this.output;
   }

   @Override
   public DefaultedList<Ingredient> getPreviewInputs() {
      return this.input;
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      RecipeFinder _snowmanxx = new RecipeFinder();
      int _snowmanxxx = 0;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         ItemStack _snowmanxxxxx = _snowman.getStack(_snowmanxxxx);
         if (!_snowmanxxxxx.isEmpty()) {
            _snowmanxxx++;
            _snowmanxx.method_20478(_snowmanxxxxx, 1);
         }
      }

      return _snowmanxxx == this.input.size() && _snowmanxx.findRecipe(this, null);
   }

   public ItemStack craft(CraftingInventory _snowman) {
      return this.output.copy();
   }

   @Override
   public boolean fits(int width, int height) {
      return width * height >= this.input.size();
   }

   public static class Serializer implements RecipeSerializer<ShapelessRecipe> {
      public Serializer() {
      }

      public ShapelessRecipe read(Identifier _snowman, JsonObject _snowman) {
         String _snowmanxx = JsonHelper.getString(_snowman, "group", "");
         DefaultedList<Ingredient> _snowmanxxx = getIngredients(JsonHelper.getArray(_snowman, "ingredients"));
         if (_snowmanxxx.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
         } else if (_snowmanxxx.size() > 9) {
            throw new JsonParseException("Too many ingredients for shapeless recipe");
         } else {
            ItemStack _snowmanxxxx = ShapedRecipe.getItemStack(JsonHelper.getObject(_snowman, "result"));
            return new ShapelessRecipe(_snowman, _snowmanxx, _snowmanxxxx, _snowmanxxx);
         }
      }

      private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
         DefaultedList<Ingredient> _snowman = DefaultedList.of();

         for (int _snowmanx = 0; _snowmanx < json.size(); _snowmanx++) {
            Ingredient _snowmanxx = Ingredient.fromJson(json.get(_snowmanx));
            if (!_snowmanxx.isEmpty()) {
               _snowman.add(_snowmanxx);
            }
         }

         return _snowman;
      }

      public ShapelessRecipe read(Identifier _snowman, PacketByteBuf _snowman) {
         String _snowmanxx = _snowman.readString(32767);
         int _snowmanxxx = _snowman.readVarInt();
         DefaultedList<Ingredient> _snowmanxxxx = DefaultedList.ofSize(_snowmanxxx, Ingredient.EMPTY);

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
            _snowmanxxxx.set(_snowmanxxxxx, Ingredient.fromPacket(_snowman));
         }

         ItemStack _snowmanxxxxx = _snowman.readItemStack();
         return new ShapelessRecipe(_snowman, _snowmanxx, _snowmanxxxxx, _snowmanxxxx);
      }

      public void write(PacketByteBuf _snowman, ShapelessRecipe _snowman) {
         _snowman.writeString(_snowman.group);
         _snowman.writeVarInt(_snowman.input.size());

         for (Ingredient _snowmanxx : _snowman.input) {
            _snowmanxx.write(_snowman);
         }

         _snowman.writeItemStack(_snowman.output);
      }
   }
}
