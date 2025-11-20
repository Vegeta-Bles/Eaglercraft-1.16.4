package net.minecraft.recipe;

import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class SmithingRecipe implements Recipe<Inventory> {
   private final Ingredient base;
   private final Ingredient addition;
   private final ItemStack result;
   private final Identifier id;

   public SmithingRecipe(Identifier id, Ingredient base, Ingredient addition, ItemStack result) {
      this.id = id;
      this.base = base;
      this.addition = addition;
      this.result = result;
   }

   @Override
   public boolean matches(Inventory inv, World world) {
      return this.base.test(inv.getStack(0)) && this.addition.test(inv.getStack(1));
   }

   @Override
   public ItemStack craft(Inventory inv) {
      ItemStack _snowman = this.result.copy();
      CompoundTag _snowmanx = inv.getStack(0).getTag();
      if (_snowmanx != null) {
         _snowman.setTag(_snowmanx.copy());
      }

      return _snowman;
   }

   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public ItemStack getOutput() {
      return this.result;
   }

   public boolean method_30029(ItemStack _snowman) {
      return this.addition.test(_snowman);
   }

   @Override
   public ItemStack getRecipeKindIcon() {
      return new ItemStack(Blocks.SMITHING_TABLE);
   }

   @Override
   public Identifier getId() {
      return this.id;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SMITHING;
   }

   @Override
   public RecipeType<?> getType() {
      return RecipeType.SMITHING;
   }

   public static class Serializer implements RecipeSerializer<SmithingRecipe> {
      public Serializer() {
      }

      public SmithingRecipe read(Identifier _snowman, JsonObject _snowman) {
         Ingredient _snowmanxx = Ingredient.fromJson(JsonHelper.getObject(_snowman, "base"));
         Ingredient _snowmanxxx = Ingredient.fromJson(JsonHelper.getObject(_snowman, "addition"));
         ItemStack _snowmanxxxx = ShapedRecipe.getItemStack(JsonHelper.getObject(_snowman, "result"));
         return new SmithingRecipe(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }

      public SmithingRecipe read(Identifier _snowman, PacketByteBuf _snowman) {
         Ingredient _snowmanxx = Ingredient.fromPacket(_snowman);
         Ingredient _snowmanxxx = Ingredient.fromPacket(_snowman);
         ItemStack _snowmanxxxx = _snowman.readItemStack();
         return new SmithingRecipe(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }

      public void write(PacketByteBuf _snowman, SmithingRecipe _snowman) {
         _snowman.base.write(_snowman);
         _snowman.addition.write(_snowman);
         _snowman.writeItemStack(_snowman.result);
      }
   }
}
