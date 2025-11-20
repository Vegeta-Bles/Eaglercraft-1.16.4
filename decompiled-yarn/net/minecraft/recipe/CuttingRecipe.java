package net.minecraft.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public abstract class CuttingRecipe implements Recipe<Inventory> {
   protected final Ingredient input;
   protected final ItemStack output;
   private final RecipeType<?> type;
   private final RecipeSerializer<?> serializer;
   protected final Identifier id;
   protected final String group;

   public CuttingRecipe(RecipeType<?> type, RecipeSerializer<?> serializer, Identifier id, String group, Ingredient input, ItemStack output) {
      this.type = type;
      this.serializer = serializer;
      this.id = id;
      this.group = group;
      this.input = input;
      this.output = output;
   }

   @Override
   public RecipeType<?> getType() {
      return this.type;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return this.serializer;
   }

   @Override
   public Identifier getId() {
      return this.id;
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
      DefaultedList<Ingredient> _snowman = DefaultedList.of();
      _snowman.add(this.input);
      return _snowman;
   }

   @Override
   public boolean fits(int width, int height) {
      return true;
   }

   @Override
   public ItemStack craft(Inventory inv) {
      return this.output.copy();
   }

   public static class Serializer<T extends CuttingRecipe> implements RecipeSerializer<T> {
      final CuttingRecipe.Serializer.RecipeFactory<T> recipeFactory;

      protected Serializer(CuttingRecipe.Serializer.RecipeFactory<T> _snowman) {
         this.recipeFactory = _snowman;
      }

      public T read(Identifier _snowman, JsonObject _snowman) {
         String _snowmanxx = JsonHelper.getString(_snowman, "group", "");
         Ingredient _snowmanxxx;
         if (JsonHelper.hasArray(_snowman, "ingredient")) {
            _snowmanxxx = Ingredient.fromJson(JsonHelper.getArray(_snowman, "ingredient"));
         } else {
            _snowmanxxx = Ingredient.fromJson(JsonHelper.getObject(_snowman, "ingredient"));
         }

         String _snowmanxxxx = JsonHelper.getString(_snowman, "result");
         int _snowmanxxxxx = JsonHelper.getInt(_snowman, "count");
         ItemStack _snowmanxxxxxx = new ItemStack(Registry.ITEM.get(new Identifier(_snowmanxxxx)), _snowmanxxxxx);
         return this.recipeFactory.create(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxxxx);
      }

      public T read(Identifier _snowman, PacketByteBuf _snowman) {
         String _snowmanxx = _snowman.readString(32767);
         Ingredient _snowmanxxx = Ingredient.fromPacket(_snowman);
         ItemStack _snowmanxxxx = _snowman.readItemStack();
         return this.recipeFactory.create(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }

      public void write(PacketByteBuf _snowman, T _snowman) {
         _snowman.writeString(_snowman.group);
         _snowman.input.write(_snowman);
         _snowman.writeItemStack(_snowman.output);
      }

      interface RecipeFactory<T extends CuttingRecipe> {
         T create(Identifier var1, String var2, Ingredient var3, ItemStack var4);
      }
   }
}
