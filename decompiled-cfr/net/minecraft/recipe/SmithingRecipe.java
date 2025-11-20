/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class SmithingRecipe
implements Recipe<Inventory> {
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
        ItemStack itemStack = this.result.copy();
        CompoundTag _snowman2 = inv.getStack(0).getTag();
        if (_snowman2 != null) {
            itemStack.setTag(_snowman2.copy());
        }
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    public boolean method_30029(ItemStack itemStack) {
        return this.addition.test(itemStack);
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

    public static class Serializer
    implements RecipeSerializer<SmithingRecipe> {
        @Override
        public SmithingRecipe read(Identifier identifier, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson((JsonElement)JsonHelper.getObject(jsonObject, "base"));
            _snowman = Ingredient.fromJson((JsonElement)JsonHelper.getObject(jsonObject, "addition"));
            ItemStack _snowman2 = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
            return new SmithingRecipe(identifier, ingredient, _snowman, _snowman2);
        }

        @Override
        public SmithingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
            _snowman = Ingredient.fromPacket(packetByteBuf);
            ItemStack _snowman2 = packetByteBuf.readItemStack();
            return new SmithingRecipe(identifier, ingredient, _snowman, _snowman2);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, SmithingRecipe smithingRecipe) {
            smithingRecipe.base.write(packetByteBuf);
            smithingRecipe.addition.write(packetByteBuf);
            packetByteBuf.writeItemStack(smithingRecipe.result);
        }

        @Override
        public /* synthetic */ Recipe read(Identifier id, PacketByteBuf buf) {
            return this.read(id, buf);
        }

        @Override
        public /* synthetic */ Recipe read(Identifier id, JsonObject json) {
            return this.read(id, json);
        }
    }
}

