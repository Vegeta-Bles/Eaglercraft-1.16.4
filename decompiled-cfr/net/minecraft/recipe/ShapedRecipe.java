/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSyntaxException
 */
package net.minecraft.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ShapedRecipe
implements CraftingRecipe {
    private final int width;
    private final int height;
    private final DefaultedList<Ingredient> inputs;
    private final ItemStack output;
    private final Identifier id;
    private final String group;

    public ShapedRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> ingredients, ItemStack output) {
        this.id = id;
        this.group = group;
        this.width = width;
        this.height = height;
        this.inputs = ingredients;
        this.output = output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.SHAPED;
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
        return this.inputs;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        for (int i = 0; i <= craftingInventory.getWidth() - this.width; ++i) {
            for (_snowman = 0; _snowman <= craftingInventory.getHeight() - this.height; ++_snowman) {
                if (this.matchesSmall(craftingInventory, i, _snowman, true)) {
                    return true;
                }
                if (!this.matchesSmall(craftingInventory, i, _snowman, false)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean matchesSmall(CraftingInventory inv, int offsetX, int offsetY, boolean bl) {
        for (int i = 0; i < inv.getWidth(); ++i) {
            for (_snowman = 0; _snowman < inv.getHeight(); ++_snowman) {
                _snowman = i - offsetX;
                _snowman = _snowman - offsetY;
                Ingredient ingredient = Ingredient.EMPTY;
                if (_snowman >= 0 && _snowman >= 0 && _snowman < this.width && _snowman < this.height) {
                    ingredient = bl ? this.inputs.get(this.width - _snowman - 1 + _snowman * this.width) : this.inputs.get(_snowman + _snowman * this.width);
                }
                if (ingredient.test(inv.getStack(i + _snowman * inv.getWidth()))) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        return this.getOutput().copy();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private static DefaultedList<Ingredient> getIngredients(String[] pattern, Map<String, Ingredient> key, int width, int height) {
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
        HashSet _snowman2 = Sets.newHashSet(key.keySet());
        _snowman2.remove(" ");
        for (int i = 0; i < pattern.length; ++i) {
            for (_snowman = 0; _snowman < pattern[i].length(); ++_snowman) {
                String string = pattern[i].substring(_snowman, _snowman + 1);
                Ingredient _snowman3 = key.get(string);
                if (_snowman3 == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + string + "' but it's not defined in the key");
                }
                _snowman2.remove(string);
                defaultedList.set(_snowman + width * i, _snowman3);
            }
        }
        if (!_snowman2.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + _snowman2);
        }
        return defaultedList;
    }

    @VisibleForTesting
    static String[] combinePattern(String ... lines) {
        int _snowman5;
        int _snowman4;
        int _snowman2 = Integer.MAX_VALUE;
        _snowman4 = 0;
        _snowman = 0;
        _snowman5 = 0;
        for (_snowman = 0; _snowman < lines.length; ++_snowman) {
            String string = lines[_snowman];
            _snowman2 = Math.min(_snowman2, ShapedRecipe.findNextIngredient(string));
            int _snowman3 = ShapedRecipe.findNextIngredientReverse(string);
            _snowman4 = Math.max(_snowman4, _snowman3);
            if (_snowman3 < 0) {
                if (_snowman == _snowman) {
                    ++_snowman;
                }
                ++_snowman5;
                continue;
            }
            _snowman5 = 0;
        }
        if (lines.length == _snowman5) {
            return new String[0];
        }
        String[] stringArray = new String[lines.length - _snowman5 - _snowman];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = lines[i + _snowman].substring(_snowman2, _snowman4 + 1);
        }
        return stringArray;
    }

    private static int findNextIngredient(String pattern) {
        int n;
        for (n = 0; n < pattern.length() && pattern.charAt(n) == ' '; ++n) {
        }
        return n;
    }

    private static int findNextIngredientReverse(String pattern) {
        int n;
        for (n = pattern.length() - 1; n >= 0 && pattern.charAt(n) == ' '; --n) {
        }
        return n;
    }

    private static String[] getPattern(JsonArray json) {
        String[] stringArray = new String[json.size()];
        if (stringArray.length > 3) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
        }
        if (stringArray.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        }
        for (int i = 0; i < stringArray.length; ++i) {
            String string = JsonHelper.asString(json.get(i), "pattern[" + i + "]");
            if (string.length() > 3) {
                throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
            }
            if (i > 0 && stringArray[0].length() != string.length()) {
                throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }
            stringArray[i] = string;
        }
        return stringArray;
    }

    private static Map<String, Ingredient> getComponents(JsonObject json) {
        HashMap hashMap = Maps.newHashMap();
        for (Map.Entry entry : json.entrySet()) {
            if (((String)entry.getKey()).length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            hashMap.put(entry.getKey(), Ingredient.fromJson((JsonElement)entry.getValue()));
        }
        hashMap.put(" ", Ingredient.EMPTY);
        return hashMap;
    }

    public static ItemStack getItemStack(JsonObject json) {
        String string = JsonHelper.getString(json, "item");
        Item _snowman2 = Registry.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + string + "'"));
        if (json.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        }
        int _snowman3 = JsonHelper.getInt(json, "count", 1);
        return new ItemStack(_snowman2, _snowman3);
    }

    public static class Serializer
    implements RecipeSerializer<ShapedRecipe> {
        @Override
        public ShapedRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            Map _snowman2 = ShapedRecipe.getComponents(JsonHelper.getObject(jsonObject, "key"));
            String[] _snowman3 = ShapedRecipe.combinePattern(ShapedRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern")));
            int _snowman4 = _snowman3[0].length();
            int _snowman5 = _snowman3.length;
            DefaultedList _snowman6 = ShapedRecipe.getIngredients(_snowman3, _snowman2, _snowman4, _snowman5);
            ItemStack _snowman7 = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
            return new ShapedRecipe(identifier, string, _snowman4, _snowman5, _snowman6, _snowman7);
        }

        @Override
        public ShapedRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            int n = packetByteBuf.readVarInt();
            _snowman = packetByteBuf.readVarInt();
            String _snowman2 = packetByteBuf.readString(Short.MAX_VALUE);
            DefaultedList<Ingredient> _snowman3 = DefaultedList.ofSize(n * _snowman, Ingredient.EMPTY);
            for (_snowman = 0; _snowman < _snowman3.size(); ++_snowman) {
                _snowman3.set(_snowman, Ingredient.fromPacket(packetByteBuf));
            }
            ItemStack _snowman4 = packetByteBuf.readItemStack();
            return new ShapedRecipe(identifier, _snowman2, n, _snowman, _snowman3, _snowman4);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf2, ShapedRecipe shapedRecipe) {
            PacketByteBuf packetByteBuf2;
            packetByteBuf2.writeVarInt(shapedRecipe.width);
            packetByteBuf2.writeVarInt(shapedRecipe.height);
            packetByteBuf2.writeString(shapedRecipe.group);
            for (Ingredient ingredient : shapedRecipe.inputs) {
                ingredient.write(packetByteBuf2);
            }
            packetByteBuf2.writeItemStack(shapedRecipe.output);
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

