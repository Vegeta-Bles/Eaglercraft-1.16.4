/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.predicate.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.predicate.NumberRange;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class EnchantmentPredicate {
    public static final EnchantmentPredicate ANY = new EnchantmentPredicate();
    public static final EnchantmentPredicate[] ARRAY_OF_ANY = new EnchantmentPredicate[0];
    private final Enchantment enchantment;
    private final NumberRange.IntRange levels;

    public EnchantmentPredicate() {
        this.enchantment = null;
        this.levels = NumberRange.IntRange.ANY;
    }

    public EnchantmentPredicate(@Nullable Enchantment enchantment, NumberRange.IntRange levels) {
        this.enchantment = enchantment;
        this.levels = levels;
    }

    public boolean test(Map<Enchantment, Integer> map2) {
        if (this.enchantment != null) {
            if (!map2.containsKey(this.enchantment)) {
                return false;
            }
            int n = map2.get(this.enchantment);
            if (this.levels != null && !this.levels.test(n)) {
                return false;
            }
        } else if (this.levels != null) {
            Map<Enchantment, Integer> map2;
            for (Integer _snowman2 : map2.values()) {
                if (!this.levels.test(_snowman2)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (this.enchantment != null) {
            jsonObject.addProperty("enchantment", Registry.ENCHANTMENT.getId(this.enchantment).toString());
        }
        jsonObject.add("levels", this.levels.toJson());
        return jsonObject;
    }

    public static EnchantmentPredicate deserialize(@Nullable JsonElement el) {
        Object _snowman3;
        if (el == null || el.isJsonNull()) {
            return ANY;
        }
        JsonObject jsonObject = JsonHelper.asObject(el, "enchantment");
        Enchantment _snowman2 = null;
        if (jsonObject.has("enchantment")) {
            _snowman3 = new Identifier(JsonHelper.getString(jsonObject, "enchantment"));
            _snowman2 = Registry.ENCHANTMENT.getOrEmpty((Identifier)_snowman3).orElseThrow(() -> EnchantmentPredicate.method_17849((Identifier)_snowman3));
        }
        _snowman3 = NumberRange.IntRange.fromJson(jsonObject.get("levels"));
        return new EnchantmentPredicate(_snowman2, (NumberRange.IntRange)_snowman3);
    }

    public static EnchantmentPredicate[] deserializeAll(@Nullable JsonElement el) {
        if (el == null || el.isJsonNull()) {
            return ARRAY_OF_ANY;
        }
        JsonArray jsonArray = JsonHelper.asArray(el, "enchantments");
        EnchantmentPredicate[] _snowman2 = new EnchantmentPredicate[jsonArray.size()];
        for (int i = 0; i < _snowman2.length; ++i) {
            _snowman2[i] = EnchantmentPredicate.deserialize(jsonArray.get(i));
        }
        return _snowman2;
    }

    private static /* synthetic */ JsonSyntaxException method_17849(Identifier identifier) {
        return new JsonSyntaxException("Unknown enchantment '" + identifier + "'");
    }
}

