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
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public abstract class CuttingRecipe
implements Recipe<Inventory> {
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
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.input);
        return defaultedList;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack craft(Inventory inv) {
        return this.output.copy();
    }

    public static class Serializer<T extends CuttingRecipe>
    implements RecipeSerializer<T> {
        final RecipeFactory<T> recipeFactory;

        protected Serializer(RecipeFactory<T> recipeFactory) {
            this.recipeFactory = recipeFactory;
        }

        @Override
        public T read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            Ingredient _snowman2 = JsonHelper.hasArray(jsonObject, "ingredient") ? Ingredient.fromJson((JsonElement)JsonHelper.getArray(jsonObject, "ingredient")) : Ingredient.fromJson((JsonElement)JsonHelper.getObject(jsonObject, "ingredient"));
            _snowman = JsonHelper.getString(jsonObject, "result");
            int _snowman3 = JsonHelper.getInt(jsonObject, "count");
            ItemStack _snowman4 = new ItemStack(Registry.ITEM.get(new Identifier(_snowman)), _snowman3);
            return this.recipeFactory.create(identifier, string, _snowman2, _snowman4);
        }

        @Override
        public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString(Short.MAX_VALUE);
            Ingredient _snowman2 = Ingredient.fromPacket(packetByteBuf);
            ItemStack _snowman3 = packetByteBuf.readItemStack();
            return this.recipeFactory.create(identifier, string, _snowman2, _snowman3);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, T t) {
            packetByteBuf.writeString(((CuttingRecipe)t).group);
            ((CuttingRecipe)t).input.write(packetByteBuf);
            packetByteBuf.writeItemStack(((CuttingRecipe)t).output);
        }

        @Override
        public /* synthetic */ Recipe read(Identifier id, PacketByteBuf buf) {
            return this.read(id, buf);
        }

        @Override
        public /* synthetic */ Recipe read(Identifier id, JsonObject json) {
            return this.read(id, json);
        }

        static interface RecipeFactory<T extends CuttingRecipe> {
            public T create(Identifier var1, String var2, Ingredient var3, ItemStack var4);
        }
    }
}

