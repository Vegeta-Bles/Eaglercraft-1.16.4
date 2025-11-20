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

   public boolean test(Map<Enchantment, Integer> _snowman) {
      if (this.enchantment != null) {
         if (!_snowman.containsKey(this.enchantment)) {
            return false;
         }

         int _snowmanx = _snowman.get(this.enchantment);
         if (this.levels != null && !this.levels.test(_snowmanx)) {
            return false;
         }
      } else if (this.levels != null) {
         for (Integer _snowmanx : _snowman.values()) {
            if (this.levels.test(_snowmanx)) {
               return true;
            }
         }

         return false;
      }

      return true;
   }

   public JsonElement serialize() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (this.enchantment != null) {
            _snowman.addProperty("enchantment", Registry.ENCHANTMENT.getId(this.enchantment).toString());
         }

         _snowman.add("levels", this.levels.toJson());
         return _snowman;
      }
   }

   public static EnchantmentPredicate deserialize(@Nullable JsonElement el) {
      if (el != null && !el.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(el, "enchantment");
         Enchantment _snowmanx = null;
         if (_snowman.has("enchantment")) {
            Identifier _snowmanxx = new Identifier(JsonHelper.getString(_snowman, "enchantment"));
            _snowmanx = Registry.ENCHANTMENT.getOrEmpty(_snowmanxx).orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + _snowman + "'"));
         }

         NumberRange.IntRange _snowmanxx = NumberRange.IntRange.fromJson(_snowman.get("levels"));
         return new EnchantmentPredicate(_snowmanx, _snowmanxx);
      } else {
         return ANY;
      }
   }

   public static EnchantmentPredicate[] deserializeAll(@Nullable JsonElement el) {
      if (el != null && !el.isJsonNull()) {
         JsonArray _snowman = JsonHelper.asArray(el, "enchantments");
         EnchantmentPredicate[] _snowmanx = new EnchantmentPredicate[_snowman.size()];

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
            _snowmanx[_snowmanxx] = deserialize(_snowman.get(_snowmanxx));
         }

         return _snowmanx;
      } else {
         return ARRAY_OF_ANY;
      }
   }
}
