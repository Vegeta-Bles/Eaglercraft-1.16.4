package net.minecraft.recipe;

import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   @Environment(EnvType.CLIENT)
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
      DefaultedList<Ingredient> lv = DefaultedList.of();
      lv.add(this.input);
      return lv;
   }

   @Environment(EnvType.CLIENT)
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

      protected Serializer(CuttingRecipe.Serializer.RecipeFactory<T> arg) {
         this.recipeFactory = arg;
      }

      public T read(Identifier arg, JsonObject jsonObject) {
         String string = JsonHelper.getString(jsonObject, "group", "");
         Ingredient lv;
         if (JsonHelper.hasArray(jsonObject, "ingredient")) {
            lv = Ingredient.fromJson(JsonHelper.getArray(jsonObject, "ingredient"));
         } else {
            lv = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "ingredient"));
         }

         String string2 = JsonHelper.getString(jsonObject, "result");
         int i = JsonHelper.getInt(jsonObject, "count");
         ItemStack lv3 = new ItemStack(Registry.ITEM.get(new Identifier(string2)), i);
         return this.recipeFactory.create(arg, string, lv, lv3);
      }

      public T read(Identifier arg, PacketByteBuf arg2) {
         String string = arg2.readString(32767);
         Ingredient lv = Ingredient.fromPacket(arg2);
         ItemStack lv2 = arg2.readItemStack();
         return this.recipeFactory.create(arg, string, lv, lv2);
      }

      public void write(PacketByteBuf arg, T arg2) {
         arg.writeString(arg2.group);
         arg2.input.write(arg);
         arg.writeItemStack(arg2.output);
      }

      interface RecipeFactory<T extends CuttingRecipe> {
         T create(Identifier arg, String string, Ingredient arg2, ItemStack arg3);
      }
   }
}
