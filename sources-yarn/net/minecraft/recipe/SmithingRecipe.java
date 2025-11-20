package net.minecraft.recipe;

import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
      ItemStack lv = this.result.copy();
      CompoundTag lv2 = inv.getStack(0).getTag();
      if (lv2 != null) {
         lv.setTag(lv2.copy());
      }

      return lv;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public ItemStack getOutput() {
      return this.result;
   }

   public boolean method_30029(ItemStack arg) {
      return this.addition.test(arg);
   }

   @Environment(EnvType.CLIENT)
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

      public SmithingRecipe read(Identifier arg, JsonObject jsonObject) {
         Ingredient lv = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "base"));
         Ingredient lv2 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "addition"));
         ItemStack lv3 = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
         return new SmithingRecipe(arg, lv, lv2, lv3);
      }

      public SmithingRecipe read(Identifier arg, PacketByteBuf arg2) {
         Ingredient lv = Ingredient.fromPacket(arg2);
         Ingredient lv2 = Ingredient.fromPacket(arg2);
         ItemStack lv3 = arg2.readItemStack();
         return new SmithingRecipe(arg, lv, lv2, lv3);
      }

      public void write(PacketByteBuf arg, SmithingRecipe arg2) {
         arg2.base.write(arg);
         arg2.addition.write(arg);
         arg.writeItemStack(arg2.result);
      }
   }
}
