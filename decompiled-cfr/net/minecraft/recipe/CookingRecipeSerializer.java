/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class CookingRecipeSerializer<T extends AbstractCookingRecipe>
implements RecipeSerializer<T> {
    private final int cookingTime;
    private final RecipeFactory<T> recipeFactory;

    public CookingRecipeSerializer(RecipeFactory<T> recipeFactory, int cookingTime) {
        this.cookingTime = cookingTime;
        this.recipeFactory = recipeFactory;
    }

    @Override
    public T read(Identifier identifier, JsonObject jsonObject) {
        String string = JsonHelper.getString(jsonObject, "group", "");
        JsonArray _snowman2 = JsonHelper.hasArray(jsonObject, "ingredient") ? JsonHelper.getArray(jsonObject, "ingredient") : JsonHelper.getObject(jsonObject, "ingredient");
        Ingredient _snowman3 = Ingredient.fromJson((JsonElement)_snowman2);
        _snowman = JsonHelper.getString(jsonObject, "result");
        Identifier _snowman4 = new Identifier(_snowman);
        ItemStack _snowman5 = new ItemStack(Registry.ITEM.getOrEmpty(_snowman4).orElseThrow(() -> new IllegalStateException("Item: " + _snowman + " does not exist")));
        float _snowman6 = JsonHelper.getFloat(jsonObject, "experience", 0.0f);
        int _snowman7 = JsonHelper.getInt(jsonObject, "cookingtime", this.cookingTime);
        return this.recipeFactory.create(identifier, string, _snowman3, _snowman5, _snowman6, _snowman7);
    }

    @Override
    public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
        String string = packetByteBuf.readString(Short.MAX_VALUE);
        Ingredient _snowman2 = Ingredient.fromPacket(packetByteBuf);
        ItemStack _snowman3 = packetByteBuf.readItemStack();
        float _snowman4 = packetByteBuf.readFloat();
        int _snowman5 = packetByteBuf.readVarInt();
        return this.recipeFactory.create(identifier, string, _snowman2, _snowman3, _snowman4, _snowman5);
    }

    @Override
    public void write(PacketByteBuf packetByteBuf, T t) {
        packetByteBuf.writeString(((AbstractCookingRecipe)t).group);
        ((AbstractCookingRecipe)t).input.write(packetByteBuf);
        packetByteBuf.writeItemStack(((AbstractCookingRecipe)t).output);
        packetByteBuf.writeFloat(((AbstractCookingRecipe)t).experience);
        packetByteBuf.writeVarInt(((AbstractCookingRecipe)t).cookTime);
    }

    @Override
    public /* synthetic */ Recipe read(Identifier id, PacketByteBuf buf) {
        return this.read(id, buf);
    }

    @Override
    public /* synthetic */ Recipe read(Identifier id, JsonObject json) {
        return this.read(id, json);
    }

    static interface RecipeFactory<T extends AbstractCookingRecipe> {
        public T create(Identifier var1, String var2, Ingredient var3, ItemStack var4, float var5, int var6);
    }
}

