/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ShapelessRecipe
implements CraftingRecipe {
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

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        RecipeFinder recipeFinder = new RecipeFinder();
        int _snowman2 = 0;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            ++_snowman2;
            recipeFinder.method_20478(itemStack, 1);
        }
        return _snowman2 == this.input.size() && recipeFinder.findRecipe(this, null);
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= this.input.size();
    }

    public static class Serializer
    implements RecipeSerializer<ShapelessRecipe> {
        @Override
        public ShapelessRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            DefaultedList<Ingredient> _snowman2 = Serializer.getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));
            if (_snowman2.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }
            if (_snowman2.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            }
            ItemStack _snowman3 = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
            return new ShapelessRecipe(identifier, string, _snowman3, _snowman2);
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();
            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (ingredient.isEmpty()) continue;
                defaultedList.add(ingredient);
            }
            return defaultedList;
        }

        @Override
        public ShapelessRecipe read(Identifier identifier, PacketByteBuf packetByteBuf2) {
            PacketByteBuf packetByteBuf2;
            String string = packetByteBuf2.readString(Short.MAX_VALUE);
            int _snowman2 = packetByteBuf2.readVarInt();
            DefaultedList<Ingredient> _snowman3 = DefaultedList.ofSize(_snowman2, Ingredient.EMPTY);
            for (int i = 0; i < _snowman3.size(); ++i) {
                _snowman3.set(i, Ingredient.fromPacket(packetByteBuf2));
            }
            ItemStack _snowman4 = packetByteBuf2.readItemStack();
            return new ShapelessRecipe(identifier, string, _snowman4, _snowman3);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf2, ShapelessRecipe shapelessRecipe) {
            PacketByteBuf packetByteBuf2;
            packetByteBuf2.writeString(shapelessRecipe.group);
            packetByteBuf2.writeVarInt(shapelessRecipe.input.size());
            for (Ingredient ingredient : shapelessRecipe.input) {
                ingredient.write(packetByteBuf2);
            }
            packetByteBuf2.writeItemStack(shapelessRecipe.output);
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

